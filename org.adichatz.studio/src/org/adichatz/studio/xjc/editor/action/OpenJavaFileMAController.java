package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.action.MenuAction;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.action.MenuActionController;
import org.adichatz.engine.controller.action.SeparatorController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.tools.InspectTools;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.editor.XjcTreeFormEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class OpenJavaFileMAController extends MenuActionController {
	private ImageDescriptor javaImageDesc;

	private ImageDescriptor javaErrorImageDesc;

	private ImageDescriptor propertyImageDesc;

	private ImageDescriptor generateJavaFiles;

	private XjcTreeFormEditor editor;

	public OpenJavaFileMAController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		menuAction = new MenuAction(this) {
			@Override
			public void run() {
				List<IResource> javaResources = getJavaFiles();
				if (!javaResources.isEmpty()) {
					try {
						openJavaFile(javaResources.get(0));
					} catch (PartInitException e) {
						logError(e);
					}
				}
			}

			@Override
			public Menu getMenu(Control parent) {
				initialize();
				Menu parentMenu = super.getMenu(parent);
				return parentMenu;
			}

			public List<ActionController> initialize() {
				childControllers.clear();
				for (final IResource javaResource : getJavaFiles()) {
					String name = javaResource.getFullPath().lastSegment();
					new ActionController(name, OpenJavaFileMAController.this, genCode) {
						@Override
						public boolean isValid() {
							return true; // Validity does not depend on parent validity
						}

						@Override
						public void createControl() {
							action = new AAction() {
								@Override
								public void runAction() {
									try {
										openJavaFile(javaResource);
									} catch (PartInitException e) {
										LogBroker.logError(e);
									}
								}

								@Override
								public ImageDescriptor getImageDescriptor() {
									try {
										if (IMarker.SEVERITY_ERROR == javaResource.findMaxProblemSeverity(IMarker.PROBLEM, true,
												IResource.DEPTH_ZERO))
											return javaErrorImageDesc;
									} catch (CoreException e) {
										logError(e);
									}
									return javaImageDesc;
								}
							};
							action.setText(getFromStudioBundle("studio.xjcEditor.open.file", name));
							action.setActionController(this);
							super.createControl();
						}
					};
				}
				try {
					ITreeWrapper treeWrapper = editor.getTreeWrapper(false, false);
					Set<String> messageBundlesURIs = InspectTools.inspectBundle(
							new ScenarioInput(editor.getScenarioResources(), treeWrapper.getTreeId(), treeWrapper.getSubPackage()));
					boolean first = true;
					for (String messageBundlesURI : messageBundlesURIs) {
						IProject project;
						String subPackage;
						String treeId;
						try {
							String[] instanceKeys = EngineTools.getInstanceKeys(messageBundlesURI);
							if (null == instanceKeys[0]) {
								project = editor.getScenarioResources().getProject();

							} else {
								project = ResourcesPlugin.getWorkspace().getRoot().getProject(instanceKeys[0]);
							}
							subPackage = instanceKeys[1];
							treeId = instanceKeys[2];
						} catch (AdichatzErrorException e) {
							project = editor.getScenarioResources().getProject();
							subPackage = ".";
							treeId = messageBundlesURI;
						}
						if (project.exists()) {
							StringBuffer radix = new StringBuffer(EngineConstants.RESOURCE_MESSAGE_DIR);
							if (!EngineTools.isEmpty(subPackage) && !".".equals(subPackage)) {
								radix.append("/").append(subPackage.replace('.', '/'));
							}
							IFolder folder = project.getFolder(radix.toString());
							for (final IResource resource : folder.members()) {
								if (resource instanceof IFile && resource.getName().startsWith(treeId)) {
									if (isPropertiesFile((IFile) resource, treeId)) {
										final String bundleFileName;
										bundleFileName = messageBundlesURI.concat(resource.getName().substring(treeId.length()));
										if (first) {
											first = false;
											new SeparatorController("bundleSeparator", OpenJavaFileMAController.this, genCode);
										}
										new ActionController(messageBundlesURI, OpenJavaFileMAController.this, genCode) {
											@Override
											public boolean isValid() {
												return true; // Validity does not depend on parent validity
											}

											@Override
											public void createControl() {
												action = new AAction() {
													@Override
													public void runAction() {
														try {
															IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
																	.getActivePage(), (IFile) resource, true);
														} catch (PartInitException e) {
															LogBroker.logError(e);
														}
													}
												};
												action.setText(getFromStudioBundle("studio.xjcEditor.open.file", bundleFileName));
												action.setImageDescriptor(propertyImageDesc);
												action.setActionController(this);
												super.createControl();
											}
										};
									}
								}
							}
						}
					}
					new SeparatorController("compileSeparator", OpenJavaFileMAController.this, genCode);
					new ActionController("compile", OpenJavaFileMAController.this, genCode) {
						@Override
						public boolean isValid() {
							return true; // Validity does not depend on parent validity
						}

						@Override
						public void createControl() {
							action = new AAction() {
								@Override
								public void runAction() {
									GenerateAction generateAction = new GenerateAction(editor.getEditorInput().getFile(),
											editor.getComposite().getDisplay());
									generateAction.setEditor(editor);
									generateAction.runAction();
								}
							};
							action.setText(getFromStudioBundle("studio.xjcEditor.regenerate.java.files"));
							action.setImageDescriptor(generateJavaFiles);
							action.setActionController(this);
							super.createControl();
						}
					};
				} catch (CoreException e) {
					logError(e);
				}
				return super.getActionControllers();
			}

			private boolean isPropertiesFile(IFile file, String treeId) {
				if (!file.getFileExtension().equals("properties"))
					return false;
				String fileName = file.getName();
				if (fileName.equals(treeId.concat(".properties")))
					return true;
				if (fileName.equals(treeId.concat("GENERATED.properties"))) {
					IFile customizedFile = file.getParent().getFile(new Path(treeId.concat(".properties")));
					return !customizedFile.exists();
				}
				int length = treeId.length();
				if (fileName.startsWith(treeId.concat("_")) && length + 14 == fileName.length()) {
					String language = fileName.substring(length + 1, length + 3);
					for (String isoLanguage : Locale.getISOLanguages())
						if (isoLanguage.equals(language))
							return true;
				}
				return false;
			}
		};
		super.createControl();
		editor = (XjcTreeFormEditor) getRootCore().getController();
		javaImageDesc = AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE,
				"IMG_GENERATED_JAVA.png");
		javaErrorImageDesc = new DecorationOverlayIcon(javaImageDesc.createImage(),
				AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "DCR_ERROR.png"),
				IDecoration.BOTTOM_LEFT);
		propertyImageDesc = AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE,
				"IMG_PROPERTIES_FILE.gif");
		generateJavaFiles = AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE,
				"IMG_GENERATE_JAVA.png");
		List<IResource> javaResources = getJavaFiles();
		if (!javaResources.isEmpty())
			menuAction.setToolTipText(
					getFromStudioBundle("studio.xjcEditor.open.file", javaResources.get(0).getFullPath().lastSegment()));
	}

	private IFile getJavaFile(final IPath javaPath) {
		final IFile javaFile = editor.getScenarioResources().getProject().getFile(javaPath.removeFirstSegments(1));
		return javaFile;
	}

	private void openJavaFile(final IResource javaFile) throws PartInitException {
		IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), (IFile) javaFile, true);
	}

	private List<IResource> getJavaFiles() {
		IFile file = editor.getEditorInput().getFile();
		String treeId = ScenarioUtil.getTreeId(file);
		List<IResource> javaResources = ScenarioUtil.getJavaFiles(file);
		// Specific sort for Menu file which and generate <TreeID>$Customization<NN>.java files.
		Collections.sort(javaResources, new Comparator<IResource>() {
			@Override
			public int compare(IResource resource1, IResource resource2) {
				String segment1 = resource1.getFullPath().lastSegment();
				if (treeId.equals(segment1))
					return -1;
				String segment2 = resource2.getFullPath().lastSegment();
				segment1 = segment1.substring(0, segment1.length() - 5);
				segment2 = segment2.substring(0, segment2.length() - 5);
				try {
					int len = treeId.length();
					String customTreeId = treeId.concat("$Customization");
					if (segment1.startsWith(customTreeId) && segment2.startsWith(customTreeId))
						len += 14;
					Integer suffix1 = Integer.valueOf(segment1.substring(len));
					Integer suffix2 = Integer.valueOf(segment2.substring(len));
					return suffix1.compareTo(suffix2);
				} catch (NumberFormatException e) {
				}
				return segment1.compareTo(segment2);
			}
		});
		return javaResources;
	}

}
