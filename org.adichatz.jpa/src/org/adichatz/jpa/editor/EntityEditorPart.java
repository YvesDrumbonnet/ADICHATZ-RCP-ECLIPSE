package org.adichatz.jpa.editor;

import static org.adichatz.engine.common.LogBroker.displayError;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import javax.annotation.PostConstruct;

import org.adichatz.common.ejb.ProxyEntity;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.e4.part.BoundedPartFatalError;
import org.adichatz.engine.e4.part.BoundedPartFatalError.ERROR_TYPE;
import org.adichatz.engine.e4.part.MultiBoundedPart;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.e4.resource.PageManager;
import org.adichatz.engine.extra.IOutlinePage;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.jpa.recent.IRecentOpenEditor;
import org.adichatz.jpa.recent.RecentEditorOutlineItem;
import org.adichatz.jpa.recent.RecentUtil;

public class EntityEditorPart extends MultiBoundedPart {
	private AEntityListener entityListener;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@PostConstruct
	public void createControl() {
		super.createControl();
		editorToolBar = true;
		if (null != entity) {
			String checkBeforeOpening = (String) AdichatzApplication.getInstance()
					.getContextValue(EngineConstants.CHECK_BEFORE_OPEN_ENTITY_EDITOR);
			if (null != checkBeforeOpening && "true".equals(checkBeforeOpening.toLowerCase())) {
				try {
					((JPADataAccess) entity.getEntityMM().getDataAccess()).getPersistenceManager().find(null,
							new ProxyEntity(entity.getBeanClass(), entity.getBeanId(), null));
				} catch (Exception e) {
					new BoundedPartFatalError(this, ERROR_TYPE.mainEntityNoLongerExists, () -> {
						displayError(getFromJpaBundle("jpa.error.deleted.bean"), getFromJpaBundle(
								"jpa.error.bean.deleted.on.database", entity.getEntityMM().getEntityId(), entity.getBeanId()));
					});
				}
			}
		}
	}

	@Override
	public void addPageManager(PageManager pageManager) {
		if (null == entity) { // Creation
			String entityURI = inputPart.getParamMap().getString(ParamMap.ENTITY_URI);
			PluginEntity<?> pluginEntity = AdichatzApplication.getInstance().getPluginEntity(entityURI);
			entity = pluginEntity.getEntityMetaModel().getDataAccess().getDataCache().putNewEntity(pluginEntity.getUIBeanClass());
			// Make inputPart "not equals" for creation editor. preMarshall helps for equality and lazy fetches
			inputPart.getParamMap().put(ParamMap.ENTITY, ((IMarshalledElement) entity).preMarshal(false));
		}
		if (null == entityListener && IEntityConstants.PERSIST == entity.getStatus()) {
			inputPart.setSaveInRecentList(false);
			entityListener = new AEntityListener(this, IEventType.CHANGE_STATUS) {
				@SuppressWarnings("restriction")
				@Override
				public void handleEntityEvent(AdiEntityEvent event) {
					if (IEntityConstants.NULL != event.getOldStatus()) { // Old status == NULL ==> Remove entity from cache
																			// cancelling change
																			// Correct beanId
						entity.setBeanId(entity.getEntityMM().getId(entity.getBean()));
						inputPart.getParamMap().put(ParamMap.ENTITY, ((IMarshalledElement) entity).preMarshal(false));
						String title = entity.getEntityMM().getEntityId().concat(" ").concat(String.valueOf(entity.getBeanId()));
						inputPart.initializeLabel(title);
						inputPart.setTooltip(inputPart.getLabel());
						inputPart.setSaveInRecentList(true);
						ParamMap paramMap = inputPart.getParamMap();
						paramMap.put(ParamMap.TITLE, title);
						paramMap.put(ParamMap.TOOL_TIP_TEXT, title);
						IRecentOpenEditor recentOpenEditor = (IRecentOpenEditor) inputPart.getTransientData()
								.get(RecentUtil.RECENT_OPEN_EDITOR);
						recentOpenEditor.setLabel(inputPart.getInitialLabel());
						RecentEditorOutlineItem.getInstance().getRecentOpenEditorMap().add(recentOpenEditor);
						dispose(); // Dispose current listener
					}
				}
			};
		}
		super.addPageManager(pageManager);
	}

	public void postPersist(String... tabularRegisterIds) {
		for (String tabularRegisterId : tabularRegisterIds) {
			ATabularController<?> tabularController = (ATabularController<?>) genCode.getFromRegister(tabularRegisterId);
			if (null != tabularController) {
				tabularController.refresh();
			}
		}
	}

	@Override
	public void partActivation() {
		super.partActivation();
		IOutlinePage outlinePage = OutlinePart.getInstance().getCurrentPage();
		if (outlinePage instanceof EditorOutlinePage) {
			outlinePage.refresh();
		}
	}

}
