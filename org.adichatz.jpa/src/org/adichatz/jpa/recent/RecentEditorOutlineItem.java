package org.adichatz.jpa.recent;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.common.AApplicationListener;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.core.RootCore;
import org.adichatz.engine.e4.part.AdiInputPart;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.IntroPart;
import org.adichatz.engine.e4.preference.AdiPreferenceManager;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.extra.ARecentOutlineItem;
import org.adichatz.engine.extra.IOutlinePage;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.engine.viewer.ATreeContentProvider;
import org.adichatz.engine.widgets.LimitedComposite;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.query.IQueryOutlinePage;
import org.adichatz.jpa.query.QueryToolInput;
import org.adichatz.jpa.wrapper.RecentOpenEditorTreeWrapper;
import org.adichatz.jpa.wrapper.RecentOpenEditorWrapper;
import org.adichatz.jpa.wrapper.RecentOpenQueryEditorWrapper;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;
import org.adichatz.jpa.xjc.ParamsType;
import org.adichatz.jpa.xjc.QueryContentProviderType;
import org.adichatz.jpa.xjc.RecentOpenEditorType;
import org.adichatz.jpa.xjc.RecentOpenQueryEditorType;
import org.adichatz.jpa.xjc.RecentPreferenceType;
import org.adichatz.jpa.xjc.RecentPreferencesType;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import net.miginfocom.swt.MigLayout;

public class RecentEditorOutlineItem extends ARecentOutlineItem {
	/*
	 * S T A T I C
	 */

	public static ImageDescriptor PREFERENCE_IMAGE_DESCRIPTOR = AdichatzApplication.getInstance()
			.getImageDescriptor("org.adichatz.jpa", "IMG_PREFERENCE.gif");

	/** The THIS. */
	protected static RecentEditorOutlineItem THIS;

	/**
	 * Gets the single instance of RecentOutlinePage.
	 * 
	 * @return single instance of RecentOutlinePage
	 */
	public static RecentEditorOutlineItem getInstance() {
		return THIS;
	}

	/*
	 * end S T A T I C
	 */

	/** The recent opened editor list. */
	private RecentOpenEditorMap recentOpenEditorMap;

	/** The recent preference node. */
	private IEclipsePreferences recentPreferenceNode;

	/** The recent editor cbtv. */
	private RecentTreeViewer recentEditorCBTV;

	private ATreeContentProvider recentTreeContentProvider;

	/** The open selected editor button. */
	private Button openSelectedEditorButton;

	/** The remove selected editor button. */
	private Button removeSelectedEditorButton;

	private File prefLockFile;

	/** The recent opened editor tree. */
	private RecentOpenEditorTreeWrapper recentOpenEditorTree;

	public RecentEditorOutlineItem() {
		super();
		THIS = this;
	}

	@Override
	public void init() {
		this.toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		initPreferenceManager();
		text = getFromJpaBundle("recent.open.editors");
		image = AdichatzApplication.getInstance().getImage("org.adichatz.jpa", "IMG_RECENT_EDITOR.png");
		layout = new MigLayout("wrap 1", "[grow,fill]", "grow,fill");
	}

	@Override
	public void selectItem(SelectionEvent selectionEvent) {
		if (firstSelection) {
			firstSelection = false;
			File recentOpenEditorFile = RecentUtil.getRecentOpenEditorFile();
			if (recentOpenEditorFile.exists() && recentOpenEditorFile.isFile()) {
				try {
					recentOpenEditorTree = (RecentOpenEditorTreeWrapper) FileUtility.getTreeFromXmlFile(JPAUtil.getUnmarshaller(),
							recentOpenEditorFile);
					if (null != recentOpenEditorTree)
						for (RecentOpenEditorType element : recentOpenEditorTree.getRecentOpenEditor()) {
							IRecentOpenEditor recentOpenEditor = (IRecentOpenEditor) element;
							recentOpenEditor.setParamMap(RecentUtil.getParamMap(recentOpenEditor.getParams().getParam()));
							recentOpenEditorMap.add(recentOpenEditor);
						}
				} catch (JAXBException e) {
					logError(e);
				}
			}
			try {
				prefLockFile = new File(EngineConstants.getAdiPermanentDirName().concat("/")
						.concat(RecentUtil.getRecentQueryPreferencePrefix()).concat(".lock"));
				if (prefLockFile.exists()) {
					checkPreferenceFiles();
				} else
					prefLockFile.createNewFile();
			} catch (IOException e) {
				logError(e);
			}
			createRecentEditorTreeViewer(pshelfItem.getBody());
			pshelfItem.getBody().addDisposeListener((e) -> {
				if (null != prefLockFile)
					prefLockFile.delete();
			});
			addApplicationListeners(pshelfItem);
			refresh();
		}
	}

	public RecentOpenEditorMap getRecentOpenEditorMap() {
		return recentOpenEditorMap;
	}

	public RecentTreeViewer getRecentEditorCBTV() {
		return recentEditorCBTV;
	}

	private void addApplicationListeners(PShelfItem pshelfItem) {
		// Add refresh recent open editor list listener
		final AApplicationListener openPartListener = new AApplicationListener(ApplicationEvent.EVENT_TYPE.POST_OPEN_PART) {
			@SuppressWarnings("rawtypes")
			@Override
			public void handleEvent(ApplicationEvent event) {
				final AdiInputPart inputPart = (AdiInputPart) event.part;
				IRecentOpenEditor recentOpenEditor = null;
				for (IRecentOpenEditor element : recentOpenEditorMap.values()) {
					if (element.getParamMap().equals(inputPart.getParamMap())) {
						recentOpenEditor = element;
						break;
					}
				}
				if (null == recentOpenEditor) {
					IOutlinePage outlinePage = ((BoundedPart) inputPart.getObject()).getGenCode().getOutlinePage();
					if (outlinePage instanceof IQueryOutlinePage) {
						recentOpenEditor = new RecentOpenQueryEditorWrapper();
						// Adds contentProvider in Params so that RecentOpenQueryEditorWrapper.getContentProvider() method works
						ParamsType params = new ParamsType();
						QueryContentProviderType contentProvider = (QueryContentProviderType) ((IMarshalledElement) inputPart
								.getParamMap().get(ParamMap.CONTENT_PROVIDER)).preMarshal(false);
						params.getParam().add(contentProvider);
						recentOpenEditor.setParams(params);
					} else
						recentOpenEditor = new RecentOpenEditorWrapper();
					recentOpenEditor.setRecentId(UUID.randomUUID().toString());
					recentOpenEditor.setLabel(inputPart.getInitialLabel());
					recentOpenEditor.setLastOpen(EngineTools.getXMLGregorianCalendar(new Date()));
					if (inputPart.isSaveInRecentList())
						recentOpenEditorMap.add(recentOpenEditor);
					recentOpenEditor.setParamMap(inputPart.getParamMap());
				} else
					recentOpenEditor.setLastOpen(EngineTools.getXMLGregorianCalendar(new Date()));
				inputPart.getTransientData().put(RecentUtil.RECENT_OPEN_EDITOR, recentOpenEditor);
			}

		};
		AdichatzApplication.getInstance().getApplicationListeners().add(openPartListener);
		final AApplicationListener closePartListener = new AApplicationListener(ApplicationEvent.EVENT_TYPE.POST_CLOSE_PART) {
			@Override
			public void handleEvent(ApplicationEvent event) {
				AdiInputPart inputPart = (AdiInputPart) event.part;
				if (inputPart.isSaveInRecentList()) {
					IRecentOpenEditor recentOpenEditor = (IRecentOpenEditor) inputPart.getTransientData()
							.get(RecentUtil.RECENT_OPEN_EDITOR);
					if (null != recentOpenEditor) { // Object could be removed from recentOpenEditorMap when capacity is exceeded.
						for (Map.Entry<String, Object> entry : inputPart.getParamMap().entrySet()) {
							if (entry.getValue() instanceof IMarshalledElement)
								entry.setValue(((IMarshalledElement) entry.getValue()).preMarshal(true));
						}
						recentOpenEditor.setLastClose(EngineTools.getXMLGregorianCalendar(new Date()));
						if (recentOpenEditor instanceof RecentOpenQueryEditorWrapper)
							RecentUtil.rewritePreferenceURI(
									((RecentOpenQueryEditorWrapper<?>) recentOpenEditor).getContentProvider());
					}
					refresh();
				}
			}
		};
		AdichatzApplication.getInstance().getApplicationListeners().add(closePartListener);
		pshelfItem.addDisposeListener((e) -> {
			AdichatzApplication.getInstance().getApplicationListeners().remove(openPartListener);
			AdichatzApplication.getInstance().getApplicationListeners().remove(closePartListener);
		});
	}

	/**
	 * Creates the recent editor tree viewer.
	 */
	private void createRecentEditorTreeViewer(Composite parent) {
		LimitedComposite treeComposite = new LimitedComposite(parent, SWT.NONE);
		toolkit.adapt(treeComposite);

		treeComposite.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "[grow,fill]20[]"));
		recentEditorCBTV = new RecentTreeViewer(treeComposite);
		recentEditorCBTV.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				enableEditorButtons();
			}
		});
		recentEditorCBTV.getTree().setLayoutData("h 100:n:n, w 100:n:n");

		Composite buttonComposite = toolkit.createComposite(treeComposite);
		buttonComposite.setLayout(new MigLayout("wrap 2", "push[][]", "[]"));

		openSelectedEditorButton = toolkit.createButton(buttonComposite, getFromJpaBundle("recent.openSelected"), SWT.PUSH);
		openSelectedEditorButton.setLayoutData("sg");
		openSelectedEditorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<TreeItem> list = recentEditorCBTV.getCheckedItems();
				TreeItem[] checkedItems = list.toArray(new TreeItem[list.size()]);
				for (TreeItem item : checkedItems) {
					openItem(item);
				}
				recentEditorCBTV.setCheckedElements(checkedItems);
				recentEditorCBTV.setSubtreeChecked(recentEditorCBTV.getInput(), true);
				enableEditorButtons();
			}
		});
		removeSelectedEditorButton = toolkit.createButton(buttonComposite, getFromJpaBundle("recent.removeSelected"), SWT.PUSH);
		removeSelectedEditorButton.setLayoutData("sg");
		removeSelectedEditorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StringBuffer titleSB = new StringBuffer();
				for (Object element : recentEditorCBTV.getCheckedElements()) {
					if (element instanceof IRecentOpenEditor) {
						titleSB.append(getFromJpaBundle("recent.opening.editor"));
						titleSB.append(RecentUtil.getRecentToolTipText((IRecentOpenEditor) element));
					} else {
						titleSB.append(getFromJpaBundle("recent.query.preference"));
						titleSB.append(((RecentPreferenceWrapper<?>) element).getRecentToolTipText());
					}
					titleSB.append("\n\n");
				}
				if (EngineTools.openConfirm(
						getFromJpaBundle(1 == recentEditorCBTV.getCheckedElements().length ? "recent.remove.entry.confirm"
								: "recent.remove.entries.confirm", titleSB.toString()))) {
					// First step, unselect query preference to avoid display exception.
					for (TreeItem treeItem : recentEditorCBTV.getCheckedItems()) {
						Object element = treeItem.getData();
						if (element instanceof RecentOpenQueryEditorType) {
							RecentOpenQueryEditorType recentOpenQueryEditor = (RecentOpenQueryEditorType) element;
							if (null != recentOpenQueryEditor.getRecentPreferences())
								for (RecentPreferenceType recentPreference : recentOpenQueryEditor.getRecentPreferences()
										.getRecentPreference())
									recentEditorCBTV.setChecked(recentPreference, false);
						}
					}
					for (TreeItem treeItem : recentEditorCBTV.getCheckedItems()) {
						if (!treeItem.isDisposed()) {
							Object element = treeItem.getData();
							if (element instanceof IRecentOpenEditor)
								recentOpenEditorMap.remove(((IRecentOpenEditor) element).getRecentId());
							else {
								RecentPreferenceWrapper<?> recentPreference = (RecentPreferenceWrapper<?>) element;
								RecentOpenQueryEditorWrapper<?> recentOpenQueryEditor = (RecentOpenQueryEditorWrapper<?>) treeItem
										.getParentItem().getData();
								recentOpenQueryEditor.getRecentPreferences().getRecentPreference().remove(recentPreference);
								RecentOpenEditorTreeWrapper.getInstance().removeRecentPreference(
										recentOpenQueryEditor.getContentProvider().getAdiResourceURI(), recentPreference);
							}
						}
					}
					refresh();
				}
			}
		});
		Button selectAllButton = toolkit.createButton(buttonComposite, getFromJpaBundle("recent.selectAll"), SWT.PUSH);
		selectAllButton.setLayoutData("sg");
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				recentEditorCBTV.expandAll();
				for (TreeItem editorItem : recentEditorCBTV.getTree().getItems()) {
					editorItem.setChecked(true);
					for (TreeItem preferenceItem : editorItem.getItems())
						preferenceItem.setChecked(true);
				}
				enableEditorButtons();
			}
		});
		Button deselectAllButton = toolkit.createButton(buttonComposite, getFromJpaBundle("recent.deselectAll"), SWT.PUSH);
		deselectAllButton.setLayoutData("sg");
		deselectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TreeItem editorItem : recentEditorCBTV.getTree().getItems()) {
					editorItem.setChecked(false);
					for (TreeItem preferenceItem : editorItem.getItems())
						preferenceItem.setChecked(false);
				}
				enableEditorButtons();
			}
		});

		recentTreeContentProvider = new ATreeContentProvider() {
			@Override
			public boolean hasChildren(Object element) {
				if (!(element instanceof RecentOpenQueryEditorWrapper))
					return false;
				return ((RecentOpenQueryEditorWrapper<?>) element).hasChildren();
			}

			@SuppressWarnings("unchecked")
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof List)
					return ((List<IRecentOpenEditor>) parentElement).toArray();
				if (parentElement instanceof RecentOpenQueryEditorWrapper
						&& null != ((RecentOpenQueryEditorWrapper<?>) parentElement).getRecentPreferences(true)) {
					return ((RecentOpenQueryEditorWrapper<?>) parentElement).getRecentPreferences().getRecentPreference().toArray();
				}
				return null;
			}
		};
		recentEditorCBTV.setContentProvider(recentTreeContentProvider);
		new RecentPreferenceColumnViewerToolTipSupport(recentEditorCBTV);
		final MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(recentEditorCBTV.getTree());
		menuManager.setRemoveAllWhenShown(true);
		recentEditorCBTV.getTree().setMenu(menu);
		recentEditorCBTV.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				openItem(recentEditorCBTV.getTree().getSelection()[0]);
			}
		});
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				if (!((IStructuredSelection) recentEditorCBTV.getSelection()).isEmpty()) {
					final Object recentElement = ((IStructuredSelection) recentEditorCBTV.getSelection()).getFirstElement();
					ImageDescriptor imageDescriptor;
					if (recentElement instanceof IRecentOpenEditor) {
						String iconURI = ((IRecentOpenEditor) recentElement).getParamMap().getString(ParamMap.ICON_URI);
						imageDescriptor = null == iconURI ? null : EngineTools.getImageDescriptor(iconURI);
					} else {
						imageDescriptor = PREFERENCE_IMAGE_DESCRIPTOR;
					}
					menuManager.add(new Action(getFromJpaBundle("recent.open.entry"), imageDescriptor) {
						@Override
						public void run() {
							openItem(recentEditorCBTV.getTree().getSelection()[0]);
							IntroPart.getInstance().show();
						}
					});
					if (recentElement instanceof IRecentOpenEditor) {
						menuManager.add(new Action(getFromJpaBundle("recent.edit.entry"),
								toolkit.getRegisteredImageDescriptor("IMG_EDITOR")) {
							@Override
							public void run() {
								if (0 == new EditEntryDialog(parent.getShell(), recentElement).open()) {
									recentEditorCBTV.refresh();
								}
							}
						});
					}

					menuManager.add(new Action(getFromJpaBundle("recent.remove.entry"),
							toolkit.getRegisteredImageDescriptor("IMG_DELETE")) {
						@Override
						public void run() {
							if (recentElement instanceof IRecentOpenEditor) {
								if (EngineTools.openConfirm(getFromJpaBundle("recent.remove.open.editor.confirm",
										RecentUtil.getRecentToolTipText((IRecentOpenEditor) recentElement)))) {
									recentOpenEditorMap.remove(((IRecentOpenEditor) recentElement).getRecentId());
								}
							} else {
								if (EngineTools.openConfirm(getFromJpaBundle("recent.remove.preference.editor.confirm",
										((RecentPreferenceWrapper<?>) recentElement).getRecentToolTipText()))) {
									TreePath treePath = ((ITreeSelection) recentEditorCBTV.getSelection()).getPaths()[0];
									RecentOpenQueryEditorWrapper<?> recentOpenEditor = (RecentOpenQueryEditorWrapper<?>) treePath
											.getSegment(0);
									recentOpenEditor.getRecentPreferences(true).getRecentPreference().remove(recentElement);
									RecentOpenEditorTreeWrapper.getInstance().removeRecentPreference(
											recentOpenEditor.getContentProvider().getAdiResourceURI(),
											(RecentPreferenceWrapper<?>) recentElement);
								}
							}
							refresh();
						}
					});
				}
			}
		});

	}

	@Override
	public void refresh() {
		if (null != recentEditorCBTV && !recentEditorCBTV.getControl().isDisposed()) {
			recentEditorCBTV.setInput(recentOpenEditorMap.getSortedValues());
			enableEditorButtons();
		}
	}

	/**
	 * Inits the open editor list.
	 * 
	 * @param capacity
	 *            the capacity
	 */
	private void initOpenEditorList(int capacity) {
		if (null == recentOpenEditorMap)
			recentOpenEditorMap = new RecentOpenEditorMap(capacity);
		else
			recentOpenEditorMap.setCapacity(capacity);
		refresh();
	}

	/**
	 * Inits the preference manager.
	 */
	private void initPreferenceManager() {
		recentPreferenceNode = InstanceScope.INSTANCE.getNode("org.adichatz.recent");
		Runnable injectValuesRunnable = () -> {
			initOpenEditorList(recentPreferenceNode.getInt(RecentPreferencePage.RECENTLY_OPEN_EDITORS_NUMBER, 10));
		};
		Runnable initDefaultRunnable = () -> {
			recentPreferenceNode.putInt(RecentPreferencePage.RECENTLY_OPEN_EDITORS_NUMBER, 10);
			initOpenEditorList(10);
		};
		AdiPreferenceManager preferenceManager = new AdiPreferenceManager("org.adichatz.recent",
				getFromJpaBundle("recent.preference.group"), toolkit.getRegisteredImageDescriptor("IMG_PREFERENCE"),
				RecentPreferencePage.class.getName(), injectValuesRunnable, initDefaultRunnable,
				AdichatzApplication.getPluginResources(EngineConstants.JPA_BUNDLE).getGencodePath(), null);
		AdiPreferenceManager.initPreferenceNode(preferenceManager);
	}

	/**
	 * Enable editor buttons.
	 */
	private void enableEditorButtons() {
		boolean enabled = 0 != recentEditorCBTV.getCheckedElements().length;
		openSelectedEditorButton.setEnabled(enabled);
		removeSelectedEditorButton.setEnabled(enabled);
	}

	private void openItem(TreeItem treeItem) {
		Object element = treeItem.getData();
		if (element instanceof IRecentOpenEditor) {
			openPart((IRecentOpenEditor) element);
		} else {
			RecentOpenQueryEditorWrapper<?> recentOpenEditor = (RecentOpenQueryEditorWrapper<?>) treeItem.getParentItem().getData();
			QueryContentProviderType contentProvider = recentOpenEditor.getContentProvider();
			RecentPreferenceWrapper<?> recentPreference = (RecentPreferenceWrapper<?>) element;
			if (null == recentPreference.getPreferenceTree()) {
				String preferenceURI = recentPreference.getPreferenceURI().substring(EngineConstants.PREF_FILE_URI_PREFIX.length());
				EngineTools.openDialog(MessageDialog.ERROR, getFromJpaBundle("action.notPossibleAction"),
						getFromJpaBundle("recent.missing.file", preferenceURI));
				return;
			}

			recentOpenEditor.getParamMap().put(ParamMap.CONTENT_PROVIDER, contentProvider);
			String preferenceURI;
			String originalPreferenceURI = contentProvider.getPreferenceURI();
			if (null == originalPreferenceURI)
				preferenceURI = recentPreference.getPreferenceURI();
			else {
				preferenceURI = originalPreferenceURI;
				if (!preferenceURI.endsWith("/"))
					preferenceURI = preferenceURI.concat("/");
				preferenceURI = preferenceURI.concat(JPAUtil.getPreferenceKey(recentPreference.getPreferenceURI()));
			}
			contentProvider.setPreferenceURI(preferenceURI);
			BoundedPart part = openPart(recentOpenEditor);
			QueryToolInput<?> queryToolInput = ((IQueryOutlinePage) part.getGenCode().getOutlinePage()).getQueryToolInput();
			queryToolInput.getControllerPreferenceManager().setActiveRecentPreference(recentPreference);
			queryToolInput.getQueryManager().fireListener(IEventType.AFTER_PREFERENCE_CHANGE);
			contentProvider.setPreferenceURI(originalPreferenceURI);
		}
	}

	/**
	 * Open part.
	 *
	 * @param recentOpenEditor
	 *            the recent opened editor
	 * @return the bounded part
	 */
	private BoundedPart openPart(IRecentOpenEditor recentOpenEditor) {
		try {
			ParamMap paramMap = recentOpenEditor.getParamMap().cloneAndMarshal();
			return E4AdichatzApplication.openPart(AdichatzApplication.getInstance().getContextValue(IEclipseContext.class),
					paramMap);
		} catch (Exception e) {
			logError(getFromJpaBundle("recent.opening.editor.error"));
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.IIntroOutlinePage#openRecentEditor(java.lang.String)
	 */
	public RootCore openRecentEditor(String label) {
		for (IRecentOpenEditor recentOpenEditor : recentOpenEditorMap.values()) {
			if (label.equals(recentOpenEditor.getLabel()))
				return openPart(recentOpenEditor).getGenCode();
		}
		return null;
	}

	private void checkPreferenceFiles() {
		Set<File> existingPreferenceFiles = new HashSet<>();
		if (null != recentOpenEditorTree)
			for (RecentOpenEditorType recentOpenEditor : recentOpenEditorTree.getRecentOpenEditor())
				if (recentOpenEditor instanceof RecentOpenQueryEditorType) {
					RecentPreferencesType recentPreferences = ((RecentOpenQueryEditorWrapper<?>) recentOpenEditor)
							.getRecentPreferences(true);
					if (null != recentPreferences) {
						for (RecentPreferenceType recentPreference : recentPreferences.getRecentPreference()) {
							existingPreferenceFiles.add(((RecentPreferenceWrapper<?>) recentPreference).getPreferenceFile());
						}
					}
				}
		File preferenceDir = new File(EngineConstants.getAdiPermanentDirName());
		if (preferenceDir.exists())
			for (File preferenceFile : preferenceDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return !file.equals(prefLockFile) && file.getName().startsWith(RecentUtil.getRecentQueryPreferencePrefix());
				}
			}))
				if (!existingPreferenceFiles.contains(preferenceFile)) {
					if (preferenceFile.delete())
						logWarning(getFromJpaBundle("recent.file.with.missing.preference", preferenceFile.getAbsolutePath()));
				}
	}

	// ============================================================================================================================================================

	class RecentTreeViewer extends CheckboxTreeViewer {

		public RecentTreeViewer(Composite parent) {
			super(parent, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		}

		protected List<TreeItem> getCheckedItems() {
			List<TreeItem> checkedItems = new ArrayList<>();
			internalCollectChecked(checkedItems, getTree());
			return checkedItems;
		}

		private void internalCollectChecked(List<TreeItem> result, Widget widget) {
			Item[] items = getChildren(widget);
			for (int i = 0; i < items.length; i++) {
				Item item = items[i];
				if (item instanceof TreeItem && ((TreeItem) item).getChecked()) {
					Object data = item.getData();
					if (data != null) {
						result.add((TreeItem) item);
					}
				}
				internalCollectChecked(result, item);
			}
		}
	}
}