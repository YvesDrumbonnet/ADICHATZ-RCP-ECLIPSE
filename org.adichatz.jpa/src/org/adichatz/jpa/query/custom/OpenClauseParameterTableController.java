package org.adichatz.jpa.query.custom;

import java.io.Serializable;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.ARefController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.CTabItemController;
import org.adichatz.engine.controller.collection.CompositeController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.nebula.PGroupController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.DynamicBindManager;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.query.NativeQueryManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.jpa.controller.RefTextController;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.query.QueryToolInput;
import org.adichatz.jpa.query.action.ValidateOpenClauseItemController;
import org.adichatz.jpa.tabular.ParameterFieldManager;
import org.adichatz.jpa.wrapper.QueryOpenClauseWrapper;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class OpenClauseParameterTableController<T> extends TableController<T> {
	private PGroupController openParameterGroup;

	private CompositeController openParameterDetailCMP;

	private JPAQueryManager<?> editorQueryManager;

	private QueryParameterWrapper openParameter;

	/** The field controller. */
	private AFieldController expressionFC;

	private QueryOpenClauseWrapper openClause;

	private JPADataAccess jpaDataAccess;

	private ADataAccess supplementDataAccess;

	private CTabItemController ctabItemController;

	public OpenClauseParameterTableController(String id, IContainerController parentController,
			ControllerCore genCode) {
		super(id, parentController, genCode);
		supplementDataAccess = this.pluginResources.getPluginEntity("adi://org.adichatz.jpa/query.model/ParameterMM")
				.getEntityMetaModel().getDataAccess();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void startLifeCycle() {
		openParameterGroup = (PGroupController) parentController;
		// Use JPAUtil.getChild rather than getFromRegister: several controllers may
		// have same id possible (one per openClause)
		CompositeController compositeController = (CompositeController) JPAUtil
				.getChild(openParameterGroup.getChildControllers(), CompositeController.class);
		openParameterDetailCMP = (CompositeController) compositeController.getChildControllers().get(0);
		ctabItemController = (CTabItemController) parentController.getParentController();

		editorQueryManager = ((QueryToolInput) getRootCore().getController().getEntity().getBean()).getQueryManager();
		jpaDataAccess = (JPADataAccess) editorQueryManager.getEntityMM().getDataAccess();
		super.startLifeCycle();
		addListener(new AControlListener("OpenClauseParameterTable#Refresh", IEventType.REFRESH) {
			@Override
			public void handleEvent(AdiEvent event) {
				if (null != openParameter) {
					openParameterDetailCMP.disposeControl();
					clearDetailChildControllers();
					openParameter = null;
				}
			}
		});
	}

	private ValidateOpenClauseItemController getValidateItem() {
		for (AWidgetController controller : openParameterGroup.getChildControllers())
			if (controller instanceof ValidateOpenClauseItemController) {
				return (ValidateOpenClauseItemController) controller;
			}
		return null;
	}

	@Override
	public void endLifeCycle() {
		super.endLifeCycle();
		final ICollectionController parentTabItem = parentController.getParentController();
		parentTabItem.addListener(new AControlListener("AFTER_ENTITY_INJECTION", IEventType.AFTER_ENTITY_INJECTION) {
			@Override
			public void handleEvent(AdiEvent event) {
				@SuppressWarnings("unchecked")
				IEntity<QueryOpenClauseWrapper> entity = (IEntity<QueryOpenClauseWrapper>) parentTabItem.getEntity();
				if (null != entity) {
					new EntityInjection((PGroupController) parentController, entity);
					((NativeQueryManager<?>) getQueryManager()).initQueryManager(entity);
				}
			}
		});
	}

	@Override
	public void synchronize() {
		super.synchronize();
		openClause = (QueryOpenClauseWrapper) openParameterGroup.getEntity().getBean();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (null != e.item) {
					QueryParameterWrapper newOpenParameter = (QueryParameterWrapper) e.item.getData();
					if (!newOpenParameter.equals(openParameter)) {
						openParameter = newOpenParameter;
						selectNewParameter();
					}
				}
			}
		});
		validateOpenClause(openClause);
		refresh();
	}

	private void selectNewParameter() {
		final Class<?> expressionClass;
		String className = openParameter.getExpressionClassName();
		if (null == className) {
			expressionClass = String.class;
		} else
			expressionClass = ReflectionTools.getClazz(className);
		if (null != openParameter.getIdValue() && null == openParameter.getValue()) {
			openParameter.setValue((Serializable) jpaDataAccess
					.findProxyEntity(expressionClass, openParameter.getIdValue()).getBean());
		}
		if (null == expressionFC || expressionFC.getControl().isDisposed()) {
			openParameterDetailCMP.setImmutableValid(true);
			openParameterDetailCMP.initialize();
			openParameterDetailCMP.startLifeCycle();
		} else {
			expressionFC.disposeControl();
			// Remove all child controllers except the first one (Label controller)
			// Indeed one ore more hidden child controllers could be created in
			// ArgumentParameter#setExpressionText()
			clearDetailChildControllers();
		}

		final IEntity<QueryParameterWrapper> parameterEntity = supplementDataAccess.getDataCache()
				.fetchEntity((QueryParameterWrapper) openParameter);
		openParameterDetailCMP.getEntityInjection().initialize(parameterEntity);
		if (parameterEntity.getListeners().isEmpty()) {
			parameterEntity.addEntityListener(new AEntityListener(openParameterGroup, IEventType.WHEN_PROPERTY_CHANGE) {
				@Override
				public void handleEntityEvent(AdiEntityEvent event) {
					ParameterFieldManager parameterFieldManager = openParameter.getParameterFieldManager(null, null);
					// Expression received from program must be set to null
					openParameter.setExpression(null);
					openParameter.setBinaryExpression(null);

					if ("value".equals(event.getPropertyName())) {
						openParameter.setColumnText(String
								.valueOf(parameterFieldManager.getValueFldCtlr().toString(openParameter.getValue())));
						try {
							AEntityMetaModel<?> refMM = jpaDataAccess.getPluginResources().getPluginEntityTree()
									.getEntityMM(expressionClass);
							openParameter.setIdValue(
									null == openParameter.getValue() ? null : refMM.getId(openParameter.getValue()));
						} catch (RuntimeException e) {
						}
					}
				}
			});
			parameterEntity
					.addEntityListener(new AEntityListener(openParameterGroup, IEventType.AFTER_PROPERTY_CHANGE) {
						@Override
						public void handleEntityEvent(AdiEntityEvent event) {
							ParameterFieldManager parameterFieldManager = openParameter.getParameterFieldManager(null,
									null);
							if ("value".equals(event.getPropertyName())) {
								openParameter.setColumnText(String.valueOf(
										parameterFieldManager.getValueFldCtlr().toString(openParameter.getValue())));
								if (parameterFieldManager.getValueFldCtlr() instanceof ARefController) {
									try {
										AEntityMetaModel<?> refMM = editorQueryManager.getEntityMM().getDataAccess()
												.getPluginResources().getPluginEntityTree()
												.getEntityMM(openParameter.getValue().getClass());
										openParameter.setEntityURI(refMM.getPluginEntity().getEntityURI());
										openParameter.setIdValue(null == openParameter.getValue() ? null
												: refMM.getId(openParameter.getValue()));
									} catch (RuntimeException e) {
									}
								}
							}
							validateOpenClause(openClause);
						}
					});
		}

		expressionFC = openParameter.getParameterFieldManager(editorQueryManager, openParameterDetailCMP)
				.newFieldController();
		if (expressionFC instanceof RefTextController)
			((RefTextController) expressionFC).setPluginResources(
					editorQueryManager.getEntityMM().getPluginEntity().getPluginEntityTree().getPluginResources());
		expressionFC.setProperty("value");
		/*
		 * query manager plugin resource must be temporary set to
		 * columnDynamicExpressionsCMP (e.g. for looking for pooled query manager in
		 * referenced controller).
		 */
		openParameterDetailCMP.setPluginResources(
				editorQueryManager.getEntityMM().getPluginEntity().getPluginEntityTree().getPluginResources());
		if (null == expressionFC.getControl()) // after refreshing, openParameterDetailCMP.dynamicCreateControl is
												// launched
			expressionFC.startLifeCycle();
		expressionFC.setFieldBindManager(new DynamicBindManager(expressionFC));
		openParameterDetailCMP.setPluginResources(getPluginResources());

		// ((FieldBindingManager)
		// expressionFC.getFieldBindManager()).setColumn("expression");
		expressionFC.endLifeCycle();
		expressionFC.setLocked(false);
		expressionFC.getValidation().getValidators().clear();

		// Use JPAUtil.getChild rather than getFromRegister: several controllers may
		// have same id possible (one per openClause)
		LabelController promptLBL = (LabelController) JPAUtil.getChild(openParameterDetailCMP.getChildControllers(),
				LabelController.class);
		promptLBL.getControl().setText(EngineTools.getMessage(openParameter.getPrompt()));
		openParameterDetailCMP.endLifeCycleAndSync();
		EngineTools.reinitMiglayout(openParameterGroup.getComposite());

		// redefine a new Layout to avoid MigLayout bug
		EngineTools.reinitMiglayout(openParameterDetailCMP.getControl());
		openParameterDetailCMP.getControl().layout();
		openParameterDetailCMP.getControl().getParent().layout();
		openParameterGroup.reflow();
		Composite columnParameterCmp = ((CompositeController) genCode.getFromRegister("columnParameterCmp"))
				.getControl();
		if (null != columnParameterCmp)
			EngineTools.reinitMiglayout(columnParameterCmp);
		openParameterGroup.getControl().getParent().layout();

	}

	private void clearDetailChildControllers() {
		AWidgetController labelController = openParameterDetailCMP.getChildControllers().get(0);
		openParameterDetailCMP.getChildControllers().clear();
		openParameterDetailCMP.getChildControllers().add(labelController);
	}

	public void validateOpenClause(QueryOpenClauseWrapper openClause) {
		boolean computedValid = openClause.computeValid();
		ValidateOpenClauseItemController validateItem = getValidateItem();
		validateItem.setEnabled(computedValid);
		Image image;
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		if (openClause.isPermanent())
			image = openClause.isValid() ? toolkit.getRegisteredImage("IMG_CANCEL")
					: toolkit.getRegisteredImage("IMG_ACCEPT");
		else
			image = openClause.isValid() ? toolkit.getRegisteredImage("IMG_CANCEL")
					: toolkit.getRegisteredImage("IMG_ACCEPT");
		validateItem.getControl().setImage(image);
		ctabItemController.getItem().setImage(openClause.getImage());
	}

}
