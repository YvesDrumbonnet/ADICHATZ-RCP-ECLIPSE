package org.adichatz.jpa.extra;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.Map;

import org.adichatz.common.ejb.QueryResult;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.action.AAction;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.ApplicationEvent.EVENT_TYPE;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.SectionController;
import org.adichatz.engine.controller.nebula.PGroupController;
import org.adichatz.engine.core.AIncludeCore;
import org.adichatz.engine.extra.AFormWindow;
import org.adichatz.engine.extra.AdiFormInput;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.validation.ABindingListener;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.engine.validation.FormBindingService;
import org.adichatz.engine.widgets.AdiPGroupToolItem;
import org.adichatz.jpa.action.DeleteEntityAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.Section;

public class EditFormInputWindow extends AFormWindow {

	private static String CONTAINER_ID = "detailINC";

	protected AdiFormInput formInput;

	protected String title;

	protected Image image;

	protected AEntityManagerController detailController;

	protected ATabularController<?> tabularController;

	protected FormBindingService formBindingService;

	protected IEntity<?> entity;

	protected AdiResourceBundle jpaBundle;

	protected boolean newEntity;

	boolean emptyErrorMessages;

	boolean noErrorMessage = true;

	private Runnable postMessageRunnable;

	public EditFormInputWindow(Shell shell, String title, Image image, AdiFormInput formInput) {
		super(shell);
		this.title = title;
		this.image = image;
		this.formInput = formInput;
		formInput.setWindow(this);
		jpaBundle = JPAUtil.JPA_BUNDLE_MANAGER.getResourceBundle("adichatzJpa");
	}

	@Override
	protected void createFormContent() {
		tabularController = ((ATabularController<?>) formInput.getParamMap().pop("TABULAR_CONTROLLER"));
		if (null != image)
			getShell().setImage(image);
		if (null != title)
			getShell().setText(title);
		scrolledForm.getBody().setLayout(new FillLayout());
		AdichatzApplication.getInstance().getFormToolkit().decorateFormHeading(scrolledForm.getForm());
		ContainerController containerController = new ContainerController(formInput, managedForm, title, false);
		formBindingService = new FormBindingService(containerController);
		if (null != tabularController && null != tabularController.getBindingService())
			formBindingService.getProperties().putAll(tabularController.getBindingService().getProperties());
		@SuppressWarnings("unchecked")
		Map<String, Object> properties = (Map<String, Object>) formInput.getParamMap().pop(ParamMap.PROPERTIES);
		if (null != properties)
			formBindingService.getProperties().putAll(properties);

		entity = formInput.getEntity();
		containerController.setBindingService(formBindingService);
		AIncludeCore detailCore = (AIncludeCore) AdichatzApplication.prepareAndInstantiateClass(formInput.getPartURI(),
				new Class<?>[] { ParamMap.class, IContainerController.class, String.class, AdiContext.class },
				new Object[] { formInput.getParamMap(), containerController, CONTAINER_ID,
						containerController.getGenCode().getContext() });

		ContainerController coreController = (ContainerController) detailCore.getController();
		detailCore.createContents();
		coreController.initialize();
		coreController.startLifeCycle();
		new EntityInjection(coreController, entity);
		coreController.endLifeCycle();
		formBindingService.synchronize();
		AdichatzApplication.fireListener(new ApplicationEvent(EVENT_TYPE.POST_CYCLE, coreController));

		detailController = (AEntityManagerController) detailCore.getFromRegister("detailContainer");
		newEntity = IEntityConstants.PERSIST == entity.getStatus();
		Boolean mergeAuthorization = (Boolean) formBindingService.getProperties().get("AUTHORIZATION_MERGE");
		if (null == mergeAuthorization)
			mergeAuthorization = true;
		Boolean removeAuthorization = (Boolean) formBindingService.getProperties().get("AUTHORIZATION_REMOVE");
		if (null == removeAuthorization)
			removeAuthorization = true;
		if (mergeAuthorization || removeAuthorization)
			if (detailController instanceof PGroupController) {
				addPGroupToolItems(mergeAuthorization, removeAuthorization);
			} else if (detailController instanceof SectionController) {
				addSectionActions(mergeAuthorization, removeAuthorization);
			} else
				detailController.setEnabled(false);
		formBindingService.addBindingListener(new ABindingListener(IEventType.POST_MESSAGE) {
			@Override
			public void handleEvent(AdiEvent event) {
				change();
			}
		});
		entity.addEntityListener(new AEntityListener(detailController, IEventType.CHANGE_STATUS) {
			@Override
			public void handleEntityEvent(AdiEntityEvent event) {
				if (IEntityConstants.REMOVE != event.getEntity().getStatus())
					change();
			}
		});
		if (newEntity) {
			scrolledForm.setText(emptyErrorMessages ? null : "");
			containerController.getFormMessageManager().getForm().getMessageManager().update();
		}
	}

	protected void change() {
		// TitleRegion is sized following message existence
		emptyErrorMessages = formBindingService.getErrorMessageMap().isEmpty();
		scrolledForm.setText(emptyErrorMessages ? null : "");
		if (null != postMessageRunnable)
			postMessageRunnable.run();
		if (emptyErrorMessages != noErrorMessage) {
			noErrorMessage = emptyErrorMessages;
			Point size = getShell().getSize();
			getShell().setSize(size.x + (emptyErrorMessages ? -0 : 0), size.y + (emptyErrorMessages ? -20 : 20));
		}
	}

	protected void deleteEntity(Image deleteImage) {
		DeleteEntityAction.deleteEntity(entity, formBindingService, deleteImage, Boolean.TRUE,
				getFromEngineBundle("delete.record.message"));
		if (IEntityConstants.REMOVE == entity.getStatus())
			validChanges();
	}

	protected void addSectionActions(boolean mergeAuthorization, boolean removeAuthorization) {
		Section section = ((SectionController) detailController).getControl();
		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		ToolBar toolBar = toolBarManager.createControl(section);
		final Cursor cursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);
		toolBar.setCursor(cursor);
		// Cursor needs to be explicitly disposed
		toolBar.addDisposeListener((e) -> {
			if ((cursor != null) && (cursor.isDisposed() == false))
				cursor.dispose();
		});
		section.setTextClient(toolBar);
		if (mergeAuthorization) {
			AAction validAction = new AAction() {
				@Override
				public void runAction() {
					validChanges();
				}

				@Override
				public void init() {
					setEnabled(false);
					setImageDescriptor(
							AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_ACCEPT"));
					setText(jpaBundle.getString("save.new.entity"));
				}
			};
			postMessageRunnable = () -> {
				validAction.setEnabled(emptyErrorMessages);
			};
			toolBarManager.add(validAction);
		}
		if (!newEntity && getAuthorization("AUTHORIZATION_REMOVE") && removeAuthorization) {
			ImageDescriptor deleteID = AdichatzApplication.getInstance().getFormToolkit()
					.getRegisteredImageDescriptor("IMG_DELETE");
			AAction removeAction = new AAction() {
				@Override
				public void runAction() {
					deleteEntity(deleteID.createImage());
				}

				@Override
				public void init() {
					setEnabled(false);
					setImageDescriptor(
							AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_ACCEPT"));
					setText(jpaBundle.getString("save.new.entity"));
				}
			};
			removeAction.setImageDescriptor(deleteID);
			removeAction.setToolTipText(jpaBundle.getString("detail.deleteEntity"));
		}
		toolBarManager.update(true);
	}

	protected void addPGroupToolItems(boolean mergeAuthorization, boolean removeAuthorization) {
		if (mergeAuthorization) {
			AdiPGroupToolItem validToolItem = new AdiPGroupToolItem(((PGroupController) detailController).getControl(), SWT.NONE);
			validToolItem.setImage(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_ACCEPT"));
			validToolItem.setToolTipText(jpaBundle.getString(newEntity ? "save.new.entity" : "save.changes.entity"));
			validToolItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (validToolItem.isEnabled())
						validChanges();
				}
			});
			validToolItem.setEnabled(false);
			postMessageRunnable = () -> {
				validToolItem.setEnabled(emptyErrorMessages);
			};
		}
		if (!newEntity && getAuthorization("AUTHORIZATION_REMOVE") && removeAuthorization) {
			AdiPGroupToolItem removeToolItem = new AdiPGroupToolItem(((PGroupController) detailController).getControl(), SWT.NONE);
			Image deleteImage = AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_DELETE");
			removeToolItem.setImage(deleteImage);
			removeToolItem.setToolTipText(jpaBundle.getString("detail.deleteEntity"));
			removeToolItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					deleteEntity(deleteImage);
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	protected void validChanges() {
		int status = entity.getStatus();
		entity.getEntityMM().getDataAccess().saveEntities(formBindingService, entity);
		if (null != tabularController) {
			QueryResult queryResult = tabularController.getQueryManager().getQueryResult();
			int increment = 0;
			if (IEntityConstants.PERSIST == status) {
				queryResult.getQueryResultList().add(entity.getBean());
				((AbstractTableViewer) tabularController.getViewer()).add(entity.getBean());
				increment = 1;
			} else if (IEntityConstants.REMOVE == status) {
				queryResult.getQueryResultList().remove(entity.getBean());
				((AbstractTableViewer) tabularController.getViewer()).remove(entity.getBean());
				increment = -1;
			}
			tabularController.getViewer().refresh();
			tabularController.refreshAfterChange(false, increment);
			tabularController.getViewer().setSelection(new StructuredSelection(detailController.getEntity().getBean()));
			tabularController.invokeControlMethod(ATabularController.METHOD_NAME.setTopIndex);
		}
		close();
	}

	@Override
	public boolean close() {
		formBindingService.refreshEntities(formBindingService.getUpdatedEntities().keySet());
		formBindingService.close();
		return super.close();
	}

	private boolean getAuthorization(String authorizationType) {
		Object authorization = formInput.getParamMap().get(authorizationType);
		return Boolean.TRUE.equals(authorization);
	}
}
