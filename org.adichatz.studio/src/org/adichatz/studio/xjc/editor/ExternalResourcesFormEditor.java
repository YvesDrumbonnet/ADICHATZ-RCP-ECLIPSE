package org.adichatz.studio.xjc.editor;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.common.ImageManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.viewer.ATreeContentProvider;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.recent.RecentPreferenceColumnViewerToolTipSupport;
import org.adichatz.jpa.recent.RecentPreferenceSet;
import org.adichatz.jpa.wrapper.RecentOpenEditorTreeWrapper;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;
import org.adichatz.jpa.xjc.RecentPreferenceType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.AdiPropertyTester;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.pde.internal.launching.launcher.LaunchArgumentsHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.EditorPart;

import net.miginfocom.swt.MigLayout;

@SuppressWarnings("restriction")
public class ExternalResourcesFormEditor extends EditorPart {

	/*
	 * S T A T I C
	 */

	public static void openExternalFile(final String fileName) {
		openExternalFile(new File(fileName));
	}

	public static void openExternalFile(final File file) {
		IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(file.getAbsolutePath()));
		if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
			try {
				IDE.openEditorOnFileStore(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), fileStore);
			} catch (PartInitException exception) {
				logError(exception);
			}
		}
	}

	/** The Constant ID. */
	public static final String ID = ExternalResourcesFormEditor.class.getName();

	/*
	 * end S T A T I C
	 */

	private ScrolledForm scrolledForm;

	private AdiResourceBundle resourceBundle;

	private AdichatzApplication application;

	private IProject project;

	private Composite parent;

	private AdiFormToolkit toolkit;

	private File preferenceDir;

	private Color foreground;

	private ImageManager imageManager;

	private TreeViewer recentQueryTV;

	private Button askConfirmButton;

	private Button saveBeforeDeleteButton;

	private boolean askConfirm = true;

	private boolean saveBeforeDelete = true;

	/**
	 * @see EditorPart#init
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		project = ((ExternalResourcesEditorInput) input).getProject();
		resourceBundle = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE).getResourceBundleManager()
				.getResourceBundle("externalResources");
		setPartName(resourceBundle.getValueFromBundle("editor.input.name", project.getName()));
		setTitleToolTip(resourceBundle.getValueFromBundle("editor.input.toolTipText", project.getName()));
	}

	/**
	 * @see EditorPart#createPartControl
	 */
	public void createPartControl(Composite parent) {
		final ManagedForm managedForm = new ManagedForm(parent);
		scrolledForm = managedForm.getForm();
		scrolledForm.getBody().setLayout(new MigLayout("hidemode 3, ins 0", "grow, fill", "grow, fill"));
		application = AdichatzApplication.getInstance();

		toolkit = application.getFormToolkit();
		Action refreshAction = new Action(resourceBundle.getString("refresh.content"),
				toolkit.getRegisteredImageDescriptor("IMG_REFRESH")) {
			@Override
			public void run() {
				refresh();
			}
		};
		scrolledForm.getToolBarManager().add(refreshAction);
		scrolledForm.getToolBarManager().update(true);

		toolkit.decorateFormHeading(scrolledForm.getForm());
		foreground = scrolledForm.getDisplay().getSystemColor(SWT.COLOR_BLUE);
		imageManager = AdichatzApplication.getPluginResources(EngineConstants.JPA_BUNDLE).getImageManager();
		refresh();
	}

	private void refresh() {
		if (null != parent)
			parent.dispose();

		parent = toolkit.createComposite(scrolledForm.getBody());
		final IProject project = ((ExternalResourcesEditorInput) getEditorInput()).getProject();
		preferenceDir = new File(
				Platform.getInstallLocation().getURL().getFile().concat("/configuration/").concat(project.getName()));
		String rowConstraint = null;
		ImageHyperlink deleteWorkbenchXmiHL;
		ImageHyperlink openWorkbenchXmiHL;

		try {
			String bruteLocation = LaunchArgumentsHelper.getDefaultWorkspaceLocation(project.getName().concat(".product"), false);
			String location = VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(bruteLocation);
			final String worbenchXmiFileName = location.concat("/.metadata/.plugins/org.eclipse.e4.workbench/workbench.xmi");
			if (new File(worbenchXmiFileName).exists()) {
				PGroup workspacePGroup = toolkit.createPGroup(parent, SWT.SMOOTH);
				workspacePGroup.setText(resourceBundle.getString("workspace.title"));
				workspacePGroup.setToolTipText(resourceBundle.getString("workspace.toolTip"));
				workspacePGroup.setImage(application.getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_WORKSPACE.png"));
				workspacePGroup.setLayout(new FillLayout());
				Composite composite = AdichatzApplication.getInstance().getFormToolkit().createComposite(workspacePGroup);
				composite.setLayout(new MigLayout("wrap 2, ins 5 5 20 5", "[]50[]", "20"));
				openWorkbenchXmiHL = toolkit.createImageHyperlink(composite, SWT.NULL);
				openWorkbenchXmiHL.setLayoutData("span 2");
				openWorkbenchXmiHL.setForeground(foreground);
				openWorkbenchXmiHL.setText(resourceBundle.getString("workspace.open.workbench.xmi"));
				openWorkbenchXmiHL.setToolTipText(resourceBundle.getString("workspace.open.workbench.xmi.tooltip"));
				openWorkbenchXmiHL.setImage(application.getImage(EngineConstants.ENGINE_BUNDLE, "IMG_OPEN_FILE.png"));
				openWorkbenchXmiHL.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						openExternalFile(new File(worbenchXmiFileName));
					}
				});

				deleteWorkbenchXmiHL = toolkit.createImageHyperlink(composite, SWT.NULL);
				deleteWorkbenchXmiHL.setForeground(foreground);
				deleteWorkbenchXmiHL.setText(resourceBundle.getString("workspace.delete.workbench.xmi"));
				deleteWorkbenchXmiHL.setToolTipText(resourceBundle.getString("workspace.delete.workbench.xmi.tooltip"));
				deleteWorkbenchXmiHL.setImage(application.getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_WORKBENCH.png"));
				deleteWorkbenchXmiHL.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						if (!askConfirmButton.getSelection() || EngineTools.openConfirm(
								resourceBundle.getValueFromBundle("workspace.delete.workbench.xmi.confirm", worbenchXmiFileName))) {
							boolean delete = true;
							if (saveBeforeDeleteButton.getSelection()) {
								ScenarioResources.getInstance(project).createFolderIfNotExist("resources/build/save");
								IFile saveFile = project.getFile("resources/build/save/workbench.xmi_" + new Date().getTime());
								try {
									FileInputStream inputStream = new FileInputStream(worbenchXmiFileName);
									saveFile.create(inputStream, IResource.FORCE, null);
									inputStream.close();
									logInfo(resourceBundle.getString("workspace.save.workbench.xmi"));
									refresh();
								} catch (IOException | CoreException exception) {
									logError(exception);
									delete = false;
								}
							}
							if (delete) {
								if (new File(worbenchXmiFileName).delete()) {
									logInfo(resourceBundle.getValueFromBundle("workspace.delete.workbench.xmi.deleted",
											worbenchXmiFileName));
									refresh();
								} else
									logError(resourceBundle.getValueFromBundle("workspace.delete.workbench.xmi.cannot.delete",
											worbenchXmiFileName));
							}

						}
					}
				});

				Composite checkComposite = toolkit.createComposite(composite, SWT.NONE);
				checkComposite.setLayout(new MigLayout("wrap 1, ins 0", null, null));
				askConfirmButton = toolkit.createButton(checkComposite, resourceBundle.getString("workspace.delete.ask.confirm"),
						SWT.CHECK);
				askConfirmButton.setToolTipText(
						resourceBundle.getValueFromBundle("workspace.delete.ask.confirm.tooltip", worbenchXmiFileName));
				askConfirmButton.setSelection(askConfirm);
				askConfirmButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						askConfirm = askConfirmButton.getSelection();
					};
				});
				saveBeforeDeleteButton = toolkit.createButton(checkComposite,
						resourceBundle.getString("workspace.save.before.deleting"), SWT.CHECK);
				saveBeforeDeleteButton.setToolTipText(
						resourceBundle.getValueFromBundle("workspace.save.before.deleting.tooltip", worbenchXmiFileName));
				saveBeforeDeleteButton.setSelection(saveBeforeDelete);
				saveBeforeDeleteButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						saveBeforeDelete = saveBeforeDeleteButton.getSelection();
					};
				});
			}
		} catch (CoreException e) {
			logError(e);
		}

		if (preferenceDir.exists()) {
			rowConstraint = "[]";
			PGroup configPGroup = toolkit.createPGroup(parent, SWT.SMOOTH);
			configPGroup.setText(resourceBundle.getString("config.files.text"));
			configPGroup.setToolTipText(resourceBundle.getString("config.files.toolTip"));
			configPGroup.setImage(application.getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_CONFIG.png"));
			configPGroup.setLayout(new FillLayout());
			Composite composite = AdichatzApplication.getInstance().getFormToolkit().createComposite(configPGroup);
			composite.setLayout(new MigLayout("wrap 1, ins 5 5 20 5", null, "20"));
			if (AdiPropertyTester.isApplication(project) || AdiPropertyTester.hasModelAspect(project)) {
				if (AdiPropertyTester.isApplication(project))
					addConfigFile(composite, "AdichatzRcpConfig.xml");
				if (AdiPropertyTester.hasModelAspect(project))
					addConfigFile(composite, "AdichatzConnectorConfig.xml");
			}
			PGroup recentPGroup = null;
			for (final File recentFile : preferenceDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					if (file.getName().startsWith("RecentOpenEditor_") && file.getName().endsWith(".xml"))
						return true;
					return false;
				}
			})) {
				recentPGroup = toolkit.createPGroup(parent, SWT.SMOOTH);
				String groupName = recentFile.getName().substring(17);
				groupName = groupName.substring(0, groupName.length() - 4);
				recentPGroup.setText(resourceBundle.getValueFromBundle("recent.editors.file.text", groupName));
				recentPGroup.setToolTipText(resourceBundle.getString("recent.editors.file.toolTip"));
				recentPGroup.setImage(imageManager.getImageDescriptor("IMG_RECENT_EDITOR.png").createImage());
				recentPGroup.setLayout(new FillLayout());
				Composite container = AdichatzApplication.getInstance().getFormToolkit().createComposite(recentPGroup);
				container.setLayout(new MigLayout("wrap 1, ins 0 5 20 5", null, "20"));
				deleteWorkbenchXmiHL = toolkit.createImageHyperlink(container, SWT.NULL);
				deleteWorkbenchXmiHL.setText(recentFile.getName());
				deleteWorkbenchXmiHL.setImage(recentPGroup.getImage());
				deleteWorkbenchXmiHL.setToolTipText(recentFile.getAbsolutePath());
				deleteWorkbenchXmiHL.setForeground(foreground);
				deleteWorkbenchXmiHL.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						openExternalFile(recentFile);
					}
				});
				String title = resourceBundle.getValueFromBundle("delete.file", recentFile.getName());
				String message = resourceBundle.getValueFromBundle("delete.file.confirm", recentFile.getName());
				Runnable runnable = null;

				try {
					final RecentOpenEditorTreeWrapper recentOpenEditorTree = (RecentOpenEditorTreeWrapper) FileUtility
							.getTreeFromXmlFile(JPAUtil.getUnmarshaller(), recentFile);
					recentOpenEditorTree.setRecentOpenEditorFile(recentFile);
					final Map<String, RecentPreferenceSet> queryPreferenceMap = recentOpenEditorTree.getQueryPreferenceMap();
					if (!queryPreferenceMap.isEmpty()) {
						title = resourceBundle.getValueFromBundle("delete.file.and.query.preference", recentFile.getName());
						message = resourceBundle.getValueFromBundle("delete.file.and.query.preference.confirm",
								recentFile.getName());
						runnable = () -> {
							Set<String> prefrenceURIs = new HashSet<>();
							for (RecentPreferenceSet recentPreferenceSet : queryPreferenceMap.values()) {
								for (@SuppressWarnings("rawtypes")
								RecentPreferenceWrapper recentPreference : recentPreferenceSet.getRecentPreferenceMap().values()) {
									prefrenceURIs.add(recentPreference.getPreferenceURI());
								}
							}
							for (String preferenceURI : prefrenceURIs) {
								File preferenceFile = new File(EngineConstants.getAdiPermanentDirName().concat("/")
										.concat(JPAUtil.getPreferenceKey(preferenceURI)));
								if (preferenceFile.exists())
									preferenceFile.exists();
							}
						};
						title = resourceBundle.getValueFromBundle("delete.file.and.query.preference", recentFile.getName());
						message = resourceBundle.getValueFromBundle("delete.file.and.query.preference.confirm",
								recentFile.getName());
						rowConstraint = rowConstraint.concat("[grow,fill]");
						container.setLayout(new MigLayout("wrap 1, ins 0 5 20 5", "grow,fill", "20[]5[grow,fill]"));
						recentQueryTV = new TreeViewer(container, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
						new RecentPreferenceColumnViewerToolTipSupport(recentQueryTV);
						recentQueryTV.setContentProvider(new ATreeContentProvider() {
							@SuppressWarnings("unchecked")
							public boolean hasChildren(Object element) {
								if (element instanceof Map)
									return !((Map<String, Set<RecentPreferenceWrapper<?>>>) element).isEmpty();
								if (element instanceof RecentPreferenceSet) {
									RecentPreferenceSet recentPreferenceSet = (RecentPreferenceSet) element;
									return null != recentPreferenceSet && !recentPreferenceSet.getRecentPreferenceMap().isEmpty();
								}
								return false;
							}

							@SuppressWarnings("unchecked")
							@Override
							public Object[] getChildren(Object parentElement) {
								if (hasChildren(parentElement)) {
									if (parentElement instanceof Map)
										return ((Map<String, RecentPreferenceSet>) parentElement).values().toArray();
									if (parentElement instanceof RecentPreferenceSet)
										return ((RecentPreferenceSet) parentElement).getRecentPreferenceMap().values().toArray();
								}
								return new Object[0];
							}
						});
						recentQueryTV.setInput(queryPreferenceMap);
						recentQueryTV.addDoubleClickListener(new IDoubleClickListener() {
							@Override
							public void doubleClick(DoubleClickEvent event) {
								Object selectedObject = ((IStructuredSelection) event.getSelection()).getFirstElement();
								if (selectedObject instanceof RecentPreferenceType) {
									openExternalFile(getQueryPreferenceFile(recentFile, (RecentPreferenceType) selectedObject));
								}
							}
						});
					} else
						rowConstraint = rowConstraint.concat("[]");
					deleteFile(recentFile, deleteWorkbenchXmiHL, title, message, runnable);
				} catch (JAXBException e) {
					logError(e);
				}
			}
			parent.setLayout(new MigLayout("wrap 1, ins 0", "grow, fill", rowConstraint));
		}
		EngineTools.reinitMiglayout(scrolledForm.getBody());
		scrolledForm.reflow(true);
	}

	private File getQueryPreferenceFile(File recentFile, RecentPreferenceType recentQueryPreference) {
		String preferenceKey = JPAUtil.getPreferenceKey(recentQueryPreference.getPreferenceURI());
		if (null == preferenceKey) {
			logError(JPAUtil.getFromJpaBundle("preference.invalid.uri", recentQueryPreference.getPreferenceURI()));
			return null;
		}
		return new File(recentFile.getParent().concat("/").concat(preferenceKey));
	}

	private void addConfigFile(Composite composite, String configFileName) {
		final File configFile = new File(preferenceDir.getAbsolutePath().concat("/").concat(configFileName));
		ImageHyperlink hyperlink = toolkit.createImageHyperlink(composite, SWT.NULL);
		hyperlink.setForeground(foreground);
		if (configFile.exists()) {
			hyperlink.setText(resourceBundle.getValueFromBundle("open.config.file.text", configFile.getName()));
			hyperlink.setToolTipText(resourceBundle.getValueFromBundle("open.config.file.toolTip", configFile.getAbsolutePath()));
			hyperlink.setImage(application.getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_COPY.png"));
			hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					openExternalFile(configFile);
				}
			});
			deleteFile(configFile, hyperlink, resourceBundle.getValueFromBundle("delete.file", configFile.getName()),
					resourceBundle.getValueFromBundle("delete.file.confirm", configFile.getName()), null);
		} else {
			hyperlink.setText(resourceBundle.getValueFromBundle("copy.config.file.text", configFile.getName()));
			hyperlink.setToolTipText(resourceBundle.getValueFromBundle("copy.config.file.text", configFile.getAbsolutePath()));
			hyperlink.setImage(application.getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_XML_EDIT.gif"));
			hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					String filePath = EngineConstants.XML_FILES_PATH + "/" + configFile.getName();
					IFile file = project.getFile(filePath);
					if (file.exists()) {
						try {
							InputStream inputStream = file.getContents();
							FileOutputStream outputStream = new FileOutputStream(configFile);
							EngineTools.copyStream(inputStream, outputStream);
							inputStream.close();
							outputStream.close();
							refresh();
						} catch (CoreException | IOException exception) {
							logError(exception);
						}
					} else
						logError(resourceBundle.getValueFromBundle("file.not.found", filePath, project.getName()));
				}
			});
		}
	}

	protected void deleteFile(final File file, ImageHyperlink hyperlink, final String title, final String message,
			final Runnable runnable) {
		final MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(hyperlink);
		menuManager.setRemoveAllWhenShown(true);
		hyperlink.setMenu(menu);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				menuManager.add(new Action(title,
						AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "IMG_DELETE.gif")) {
					@Override
					public void run() {
						if (EngineTools.openConfirm(message)) {
							for (IEditorReference editorReference : PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getActivePage().getEditorReferences()) {
								try {
									if (editorReference.getEditorInput() instanceof FileStoreEditorInput) {
										FileStoreEditorInput editorInput = (FileStoreEditorInput) editorReference.getEditorInput();
										if (file.toURI().equals(editorInput.getURI())) {
											IEditorPart editorPart = editorReference.getEditor(false);
											editorPart.getSite().getPage().closeEditor(editorPart, false);
										}
									}
								} catch (PartInitException e) {
									logError(e);
								}
							}
							file.delete();
							if (null != runnable)
								runnable.run();
							refresh();
						}
					}
				});
			}
		});
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
		scrolledForm.setFocus();
	}

}
