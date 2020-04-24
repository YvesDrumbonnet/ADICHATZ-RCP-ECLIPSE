package org.adichatz.studio.xjc.editor;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
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
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.xml.ui.internal.tabletree.XMLMultiPageEditorPart;

import net.miginfocom.swt.MigLayout;

@SuppressWarnings("restriction")
public class ExternalResourcesFormEditor extends EditorPart {

	/*
	 * S T A T I C
	 */

	public static void openExternalFile(final String fileName) {
		openExternalFile(new File(fileName));
	}

	public static void openExternalFile(final Object file) {
		if (file instanceof File) {
			String path = ((File) file).getAbsolutePath();
			IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(path));
			if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
				try {
					IDE.openEditorOnFileStore(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), fileStore);
				} catch (PartInitException exception) {
					logError(exception);
				}
			}
		} else if (file instanceof IFile) {
			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			IWorkbenchPage page = win.getActivePage();
			try {
				page.openEditor(new FileEditorInput((IFile) file), XMLMultiPageEditorPart.class.getName(), true);
			} catch (PartInitException e) {
				logError(e);
			}
		} else
			logError(AdichatzApplication.getInstance().getMessage(GeneratorConstants.STUDIO_BUNDLE, "externalResources",
					"invalid.file", ((IFile) file).getName()));
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

	private ImageManager imageManager;

	private TreeViewer recentQueryTV;

	private Button askConfirmButton;

	private Button saveBeforeDeleteButton;

	private Image xmlImage;

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
		scrolledForm.getBody().setLayout(new MigLayout("hidemode 3, ins 0, wrap 1", "grow, fill", "[][grow,fill]"));

		application = AdichatzApplication.getInstance();
		toolkit = application.getFormToolkit();
		xmlImage = application.getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_XML_EDIT.gif");

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
		imageManager = AdichatzApplication.getPluginResources(EngineConstants.JPA_BUNDLE).getImageManager();

		Composite checkComposite = toolkit.createComposite(scrolledForm.getBody(), SWT.NONE);
		checkComposite.setLayout(new MigLayout("wrap 2, ins 20", "[]100[]", null));
		askConfirmButton = toolkit.createButton(checkComposite, resourceBundle.getString("delete.ask.confirm"), SWT.CHECK);
		askConfirmButton.setToolTipText(resourceBundle.getString("delete.ask.confirm.tooltip"));
		askConfirmButton.setSelection(true);
		saveBeforeDeleteButton = toolkit.createButton(checkComposite, resourceBundle.getString("save.before.deleting"), SWT.CHECK);
		saveBeforeDeleteButton.setToolTipText(resourceBundle.getString("save.before.deleting.tooltip"));
		saveBeforeDeleteButton.setSelection(true);
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

		try {
			String bruteLocation = LaunchArgumentsHelper.getDefaultWorkspaceLocation(project.getName().concat(".product"), false);
			String location = VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(bruteLocation);
			final String worbenchXmiFileName = location.concat("/.metadata/.plugins/org.eclipse.e4.workbench/workbench.xmi");
			final File worbenchXmiFile = new File(worbenchXmiFileName);

			if (worbenchXmiFile.exists()) {
				PGroup workspacePGroup = toolkit.createPGroup(parent, SWT.SMOOTH);
				workspacePGroup.setText(resourceBundle.getString("workspace.title"));
				workspacePGroup.setToolTipText(resourceBundle.getString("workspace.toolTip"));
				workspacePGroup.setImage(application.getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_WORKSPACE.png"));
				workspacePGroup.setLayout(new FillLayout());

				addConfigFile(workspacePGroup, worbenchXmiFile, xmlImage, resourceBundle.getString("open.file"), true, false);
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
			composite.setLayout(new MigLayout("wrap 1, ins 5 5 20 5", null, null));
			if (AdiPropertyTester.isApplication(project) || AdiPropertyTester.hasModelAspect(project)) {
				if (AdiPropertyTester.isApplication(project)) {
					addConfigFile(composite, "AdichatzRcpConfig.xml");
				}
				if (AdiPropertyTester.hasModelAspect(project))
					addConfigFile(composite, "AdichatzConnectorConfig.xml");
			}
			PGroup recentPGroup = null;
			for (final File recentFile : preferenceDir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (file.getName().startsWith(JPAUtil.RECENT_FILE_PREFIX) && file.getName().endsWith(".xml"))
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

				addConfigFile(container, recentFile, recentPGroup.getImage(), resourceBundle.getString("open.file"), true, false);
				try {
					final Map<String, RecentPreferenceSet> queryPreferenceMap = getQueryPreferenceMap(recentFile);
					if (!queryPreferenceMap.isEmpty()) {
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
				} catch (JAXBException e) {
					logError(e);
				}
			}
			parent.setLayout(new MigLayout("wrap 1, ins 0 10 10 10", "grow, fill", rowConstraint));
		}
		EngineTools.reinitMiglayout(scrolledForm.getBody());
		scrolledForm.reflow(true);
	}

	private void addConfigFile(Composite parent, Object configFile, Image image, String openText, boolean delete, boolean copy) {
		String fileName;
		String absolutePath;
		if (configFile instanceof File) {
			fileName = ((File) configFile).getName();
			absolutePath = ((File) configFile).getAbsolutePath();
		} else if (configFile instanceof IFile) {
			fileName = ((IFile) configFile).getName();
			absolutePath = ((IFile) configFile).getFullPath().toString();
		} else {
			logError(resourceBundle.getValueFromBundle("invalid.file", configFile));
			return;
		}
		ImageHyperlink[] hyperlinks = new ImageHyperlink[3];
		Composite composite = AdichatzApplication.getInstance().getFormToolkit().createComposite(parent);
		CLabel label = toolkit.createCLabel(composite, resourceBundle.getValueFromBundle("file", fileName));
		label.setImage(image);
		hyperlinks[0] = toolkit.createImageHyperlink(composite, SWT.None);
		hyperlinks[0].setText(openText);
		hyperlinks[0].setToolTipText(resourceBundle.getValueFromBundle("open.file.tooltip", absolutePath));
		hyperlinks[0].setImage(toolkit.getRegisteredImage("IMG_OPEN_FILE"));
		hyperlinks[0].addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				openExternalFile(configFile);
			}
		});
		if (delete) {
			composite.setLayout(new MigLayout("wrap 3", "[]20[]20[]"));
			hyperlinks[1] = toolkit.createImageHyperlink(composite, SWT.None);
			hyperlinks[1].setText(resourceBundle.getString("delete.file"));
			hyperlinks[1].setToolTipText(resourceBundle.getValueFromBundle("delete.file.tooltip", absolutePath));
			hyperlinks[1].setImage(toolkit.getRegisteredImage("IMG_DELETE"));
			hyperlinks[1].addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					String title;
					boolean isRecentFile = fileName.startsWith(EngineConstants.RECENT_FILE_PREFIX);
					if (isRecentFile)
						title = resourceBundle.getValueFromBundle("delete.file.and.query.preference", fileName);
					else
						title = resourceBundle.getValueFromBundle("delete.file.confirm", fileName);

					if (!askConfirmButton.getSelection() || EngineTools.openConfirm(title)) {
						boolean delete = true;
						List<File> preferenceFiles = new ArrayList<>();
						if (fileName.startsWith(EngineConstants.RECENT_FILE_PREFIX)) {
							try {
								Collection<RecentPreferenceSet> queryPreferences = getQueryPreferenceMap((File) configFile)
										.values();
								Set<String> prefrenceURIs = new HashSet<>();
								for (RecentPreferenceSet recentPreferenceSet : queryPreferences) {
									for (@SuppressWarnings("rawtypes")
									RecentPreferenceWrapper recentPreference : recentPreferenceSet.getRecentPreferenceMap()
											.values()) {
										prefrenceURIs.add(recentPreference.getPreferenceURI());
									}
								}
								for (String preferenceURI : prefrenceURIs) {
									File preferenceFile = new File(((File) configFile).getParent().concat("/")
											.concat(JPAUtil.getPreferenceKey(preferenceURI)));
									if (preferenceFile.exists())
										preferenceFiles.add(preferenceFile);
								}
							} catch (JAXBException ex) {
								logError(ex);
							}
						}
						if (saveBeforeDeleteButton.getSelection()) {
							ScenarioResources.getInstance(project).createFolderIfNotExist("resources/build/save");
							delete = saveFile((File) configFile);
							if (delete && !preferenceFiles.isEmpty())
								for (File preferenceFile : preferenceFiles)
									saveFile(preferenceFile);
						}
						if (delete) {
							if (((File) configFile).delete()) {
								if (!preferenceFiles.isEmpty())
									for (File preferenceFile : preferenceFiles) {
										if (preferenceFile.delete())
											logInfo(resourceBundle.getValueFromBundle("file.deleted", preferenceFile.getName()));
										else
											logError(resourceBundle.getValueFromBundle("cannot.delete.file",
													preferenceFile.getName()));
									}
								logInfo(resourceBundle.getValueFromBundle("file.deleted", fileName));
							} else
								logError(resourceBundle.getValueFromBundle("cannot.delete.file", fileName));
						}
						refresh();
					}
				}

				private boolean saveFile(File configFile) {
					String fileName = configFile.getName();
					int index = fileName.lastIndexOf('.');
					String extension = "";
					long time = new Date().getTime();
					if (-1 < index) {
						extension = fileName.substring(index);
						fileName = fileName.substring(0, index);
					}

					IFile saveFile = project.getFile("resources/build/save/" + fileName + "_" + time + extension);
					try {
						FileInputStream inputStream = new FileInputStream(configFile);
						saveFile.create(inputStream, IResource.FORCE, null);
						inputStream.close();
						logInfo(resourceBundle.getValueFromBundle("save.file", configFile.getName()));
						saveFile.getParent().refreshLocal(IResource.DEPTH_ONE, null);
					} catch (IOException | CoreException exception) {
						logError(exception);
						return false;
					}
					return true;
				}
			});
		} else if (copy) {
			composite.setLayout(new MigLayout("wrap 3", "[]20[]20[]"));
			hyperlinks[2] = toolkit.createImageHyperlink(composite, SWT.None);
			hyperlinks[2].setText(resourceBundle.getString("copy.file"));
			hyperlinks[2].setToolTipText(resourceBundle.getValueFromBundle("copy.file.tooltip", absolutePath));
			hyperlinks[2].setImage(toolkit.getRegisteredImage("IMG_COPY"));
			hyperlinks[2].addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					IFile file = ((IFile) configFile);
					final File copyFile = new File(preferenceDir.getAbsolutePath().concat("/").concat(file.getName()));
					if (file.exists()) {
						try {
							InputStream inputStream = file.getContents();
							FileOutputStream outputStream = new FileOutputStream(copyFile);
							EngineTools.copyStream(inputStream, outputStream);
							inputStream.close();
							outputStream.close();
							refresh();
						} catch (CoreException | IOException exception) {
							logError(exception);
						}
					} else
						logError(resourceBundle.getValueFromBundle("file.not.found", file.getFullPath(), project.getName()));
				}
			});
		} else {
			composite.setLayout(new MigLayout("wrap 2", "[]20[]"));
		}
	}

	private void addConfigFile(Composite composite, String configFileName) {
		final File configFile = new File(preferenceDir.getAbsolutePath().concat("/").concat(configFileName));
		if (configFile.exists()) {
			addConfigFile(composite, configFile, xmlImage, resourceBundle.getString("open.file.config"), true, false);
		} else {
			IFile file = project.getFile(EngineConstants.XML_FILES_PATH + "/" + configFile.getName());
			addConfigFile(composite, file, xmlImage, resourceBundle.getString("open.file.plugin"), false, true);
		}

	}

	private Map<String, RecentPreferenceSet> getQueryPreferenceMap(final File recentFile) throws JAXBException {
		final RecentOpenEditorTreeWrapper recentOpenEditorTree = (RecentOpenEditorTreeWrapper) FileUtility
				.getTreeFromXmlFile(JPAUtil.getUnmarshaller(), recentFile);
		recentOpenEditorTree.setRecentOpenEditorFile(recentFile);
		final Map<String, RecentPreferenceSet> queryPreferenceMap = recentOpenEditorTree.getQueryPreferenceMap();
		return queryPreferenceMap;
	}

	private File getQueryPreferenceFile(File recentFile, RecentPreferenceType recentQueryPreference) {
		String preferenceKey = JPAUtil.getPreferenceKey(recentQueryPreference.getPreferenceURI());
		if (null == preferenceKey) {
			logError(JPAUtil.getFromJpaBundle("preference.invalid.uri", recentQueryPreference.getPreferenceURI()));
			return null;
		}
		return new File(recentFile.getParent().concat("/").concat(preferenceKey));
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
