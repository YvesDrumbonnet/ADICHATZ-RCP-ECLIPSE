/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.studio.xjc.controller;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.common.ImageManager;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.AControlController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.ARootMenuCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.query.AQueryManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.engine.viewer.ATreeContentProvider;
import org.adichatz.engine.widgets.AdiControlDecoration;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.Generator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.xjc.EntityTree;
import org.adichatz.scenario.AdiResourceContainer;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.osgi.framework.Bundle;

import net.miginfocom.swt.MigLayout;

/**
 * The Class AdiResourceUriTextController.
 *
 * @author Yves Arpheuil
 */
// TODO: Auto-generated Javadoc
public class AdiResourceUriTextController extends TextController {

	/** The store. */
	protected static IPreferenceStore store = StudioRcpPlugin.getDefault().getPreferenceStore();

	/** The image manager. */
	protected static ImageManager imageManager = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE)
			.getImageManager();

	/** The uri image. */
	protected static Image uriImage = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
			"IMG_ADI_URI.png");

	/** The scenario resources. */
	private ScenarioResources scenarioResources;

	/** The tree viewer. */
	private TreeViewer treeViewer;

	/** The selected container. */
	private AdiResourceContainer selectedContainer;

	/** The selected plugin resources. */
	private AdiPluginResources selectedPluginResources;

	/** The selected bundle. */
	private Bundle selectedBundle;

	/** The selected project. */
	private IProject selectedProject;

	/** The confirm form dialog. */
	private ConfirmFormDialog confirmFormDialog;

	/** The control listener. */
	private Listener controlListener;

	/** The control decoration. */
	private AdiControlDecoration controlDecoration;

	/**
	 * Instantiates a new plugin key text controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public AdiResourceUriTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/**
	 * Creates the control.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.TextController#createControl()
	 */
	@Override
	public void createControl() {
		super.createControl();
		controlDecoration = new AdiControlDecoration(control, SWT.TOP | SWT.LEFT,
				AdiControlDecoration.CONTENT_PROPOSSAL_DECORATOR_IMAGE);
		controlDecoration.setShowOnlyOnFocus(true);
		controlListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (' ' == event.keyCode && SWT.CTRL == event.stateMask) {
					event.doit = false;
					final Shell shell = Display.getCurrent().getActiveShell();
					IConfirmContent confirmContent = getConfirmContent(null, null);
					confirmFormDialog = new ConfirmFormDialog(shell, AdichatzApplication.getInstance().getFormToolkit(),
							getFromStudioBundle("studio.resource.uri.list"), uriImage, confirmContent) {
						protected void buttonPressed(int buttonId) {
							if (IDialogConstants.OK_ID == buttonId) {
								selectURI();
							} else if (IDialogConstants.CANCEL_ID == buttonId) {
								cancelPressed();
							}
						}

						@Override
						protected void createButtonsForButtonBar(Composite parent) {
							super.createButtonsForButtonBar(parent);
							getButton(IDialogConstants.OK_ID).setEnabled(false);
						}
					};
					confirmFormDialog.open();
				}
			}
		};
		control.addListener(SWT.KeyDown, controlListener);
		control.addListener(SWT.Modify, controlListener);
		scenarioResources = ((XjcBindingService) getBindingService()).getEditor().getScenarioResources();
		final AControlController linkedController = getLinkedController();

		final int defaulLevel = getDefaultLevel();

		addValidator(new AValidator(this, "studio.instance.adiResourceURI") {
			@Override
			public int validate() {
				linkedController.setEnabled(false);
				String adiResourceURI = (String) getValue();
				int level = IMessageProvider.NONE;
				if (!EngineTools.isEmpty(adiResourceURI))
					try {
						String[] instanceKeys = EngineTools.getInstanceKeys(adiResourceURI);
						scenarioResources.getAccessibleAdiPlugins();
						String pluginKey = null == instanceKeys[0] ? scenarioResources.getPluginName() : instanceKeys[0];
						String message = null;
						if (-1 == pluginKey.indexOf('#')) { // URI could be the result of a function (e.g. #PARAM(...)).
							AdiResourceContainer container = scenarioResources.getAccessibleAdiPlugins().get(pluginKey);
							if (null == container) {
								level = defaulLevel;
								message = getFromStudioBundle("studio.invalid.pluginKey", instanceKeys[0]);
							} else if (-1 == instanceKeys[1].indexOf('#')) { // URI could be the result of a function
								if (null != container.getBundle()) {
									selectedPluginResources = getSelectedPluginResources(pluginKey);
									GencodePath gencodePath = selectedPluginResources.getGencodePath();
									String className = gencodePath.getGenCodePackage(instanceKeys[1]);
									String folderPath = getBinGencodePath(container.getBundle(), gencodePath)
											.concat(className.replace('.', '/'));
									if (null == container.getBundle().getEntry(folderPath)) {
										level = defaulLevel;
										message = getFromStudioBundle("studio.invalid.subPackage", instanceKeys[1]);
									} else if (-1 == instanceKeys[2].indexOf('#')) {
										className = className.concat(".").concat(instanceKeys[2]);
										Class<?> memberClass = gencodePath.getClazz(className, true);
										if (null != memberClass) {
											if (ReflectionTools.hasSuperClass(memberClass, AEntityMetaModel.class)
													|| ReflectionTools.hasSuperClass(memberClass, ControllerCore.class)) {
												AdiResourceURI uriAnnotation = memberClass.getAnnotation(AdiResourceURI.class);
												if (null == uriAnnotation) {
													level = defaulLevel;
													message = getFromStudioBundle("studio.no.annotation", className,
															AdiResourceURI.class.getSimpleName());
												} else if (!adiResourceURI.equals(uriAnnotation.URI())) {
													level = defaulLevel;
													message = getFromStudioBundle("studio.invalid.annotation", className,
															adiResourceURI, uriAnnotation.annotationType());
												}
											}
										} else {
											level = defaulLevel;
											message = getFromStudioBundle("studio.invalid.class", className, pluginKey);
										}
									}
								} else {
									Object folder = container.getFolder(instanceKeys[1]);
									if (null == folder) {
										level = defaulLevel;
										message = getFromStudioBundle("studio.invalid.subPackage", instanceKeys[1]);
									} else if (-1 == instanceKeys[2].indexOf('#'))
										if (!isResource(container.getBundle(), folder, instanceKeys[2])) {
											level = defaulLevel;
											// URI could be the result of a function (e.g. #PARAM(...)).
											message = getFromStudioBundle("studio.invalid.treeId", instanceKeys[2]);
										} else
											linkedController.setEnabled(true);
								}
							}
						}
						return setLevel(level, message);
					} catch (AdichatzErrorException e) {
						if (-1 != adiResourceURI.indexOf('#')) // URI could be the result of a function (e.g. #PARAM(...)).
							return setLevel(IMessageProvider.NONE, null);
						else
							return setLevel(defaulLevel, e.getMessage());
					}
				return level;
			}
		});
	}

	/**
	 * Select URI.
	 */
	private void selectURI() {
		setValue(((ITreeSelection) treeViewer.getSelection()).getFirstElement());
		confirmFormDialog.okPressed();
	}

	/**
	 * Checks if is resource.
	 *
	 * @param bundle the bundle
	 * @param folder the folder
	 * @param treeId the tree id
	 * @return true, if is resource
	 */
	public boolean isResource(Bundle bundle, Object folder, String treeId) {
		if (null != bundle) {
			return null != bundle.getEntry(((URL) folder).getPath().concat("/").concat(treeId).concat(".class"));
		} else {
			IFile axmlFile = ((IFolder) folder).getFile(treeId.concat(".axml"));
			if (!axmlFile.exists())
				axmlFile = ((IFolder) folder).getFile(treeId.concat("GENERATED.axml"));
			return axmlFile.exists();
		}
	}

	/**
	 * Gets the default level.
	 * 
	 * @return the default level
	 */
	protected int getDefaultLevel() {
		final int defaulLevel = Integer.parseInt(store.getString(
				"entityURI".equals(getProperty()) ? IStudioConstants.LEVEL_ENTITY_URI : IStudioConstants.LEVEL_ADI_RESOURCE_URI));
		return defaulLevel;
	}

	/**
	 * Dispose proposal.
	 */
	public void disposeProposal() {
		controlDecoration.dispose();
		getControl().removeListener(SWT.KeyDown, controlListener);
	}

	/**
	 * Gets the confirm content.
	 *
	 * @param sectionText the section text
	 * @param columnText the column text
	 * @return the confirm content
	 */
	private IConfirmContent getConfirmContent(final String sectionText, final String columnText) {
		return new IConfirmContent() {
			private Image folderImage;

			private Image xmlFolderImage;

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				ImageManager imageManager = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE)
						.getImageManager();
				folderImage = imageManager.getImageDescriptor("IMG_FOLDER.png").createImage();
				xmlFolderImage = imageManager.getImageDescriptor("IMG_XML_FOLDER.png").createImage();
				parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[][grow,fill]"));
				Composite composite = toolkit.createComposite(parent);
				composite.setLayout(new MigLayout("wrap 2", "[grow,fill][]"));
				Label title = toolkit.createLabel(composite, getFromStudioBundle("studio.resource.uri.list"));
				title.setLayoutData("span 2");
				title.setFont(JFaceResources.getBannerFont());

				treeViewer = new TreeViewer(composite, SWT.CENTER | SWT.BORDER | SWT.V_SCROLL);
				treeViewer.getTree().setLayoutData("hmin 200, wmin 500");
				treeViewer.getTree().setHeaderVisible(true);
				treeViewer.addDoubleClickListener(new IDoubleClickListener() {
					@Override
					public void doubleClick(DoubleClickEvent event) {
						Object resource = ((StructuredSelection) event.getSelection()).getFirstElement();
						if (resource instanceof IFile && "axml".equals(((IFile) resource).getFileExtension()))
							selectURI();
					}
				});
				treeViewer.addSelectionChangedListener((event) -> {
					Button button = confirmFormDialog.getButton(IDialogConstants.OK_ID);
					button.setEnabled(!event.getSelection().isEmpty()
							&& ((IStructuredSelection) event.getSelection()).getFirstElement() instanceof String);
				});

				treeViewer.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						if (element instanceof AdiResourceContainer) {
							AdiResourceContainer container = (AdiResourceContainer) element;
							return null == container.getBundle() ? container.getProject().getName()
									: container.getBundle().getSymbolicName();
						} else if (element instanceof IResource) {
							return ((IResource) element).getName();
						} else if (element instanceof URL) {
							String path = ((URL) element).getPath();
							if (path.endsWith("/"))
								path = path.substring(0, path.length() - 1);
							return path.substring(path.lastIndexOf('/') + 1);
						}
						return super.getText(element);
					}

					@Override
					public Image getImage(Object element) {
						if (element instanceof String)
							return uriImage;
						else if (element instanceof IFolder)
							return xmlFolderImage;
						else if (element instanceof URL)
							return folderImage;
						return null;
					}
				});
				treeViewer.setContentProvider(new ATreeContentProvider() {
					private Object[] getMembers(IFolder folder) throws CoreException {
						List<Object> children = new ArrayList<Object>();
						Set<String> uris = new HashSet<String>();
						for (IResource resource : folder.members()) {
							if (resource instanceof IFolder)
								children.add(resource);
							else if (resource instanceof IFile && "axml".equals(resource.getFileExtension())) {
								boolean skip = true;
								String bundleId = "";
								try {
									ITreeWrapper treeWrapper = (ITreeWrapper) FileUtility
											.getTreeFromXmlFile(Generator.getUnmarshaller(), ((IFile) resource).getContents());
									if (-1 < getRegisterId().indexOf("entityURI")) {
										if (treeWrapper instanceof EntityTree) {
											skip = false;
											bundleId = selectedProject.getName();
										}
									} else
										skip = !(treeWrapper instanceof EntityTree);
								} catch (JAXBException e) {
									logError(e);
								}
								if (!skip) {
									String subPackage = ScenarioUtil.getSubPackage(resource);
									String treeId = ScenarioUtil.getTreeId(resource);
									StringBuffer uriSB = new StringBuffer("adi://");
									uriSB.append(bundleId).append('/');

									uriSB.append(subPackage).append('/').append(treeId);
									uris.add(uriSB.toString());
								}
							}
						}
						children.addAll(uris);
						return children.toArray();
					}

					private Object[] getMembers(String parentPath) {
						GencodePath gencodePath = selectedPluginResources.getGencodePath();
						Enumeration<String> children = selectedBundle.getEntryPaths(parentPath);
						List<Object> members = new ArrayList<Object>();
						Set<String> uris = new HashSet<String>();
						while (children.hasMoreElements()) {
							String path = children.nextElement();
							if (path.contains(".")) {
								if (path.endsWith(".class") && !path.contains("$")) {
									String className = path.substring(getBinGencodePath(selectedBundle, gencodePath).length() - 1);
									className = className.substring(0, className.length() - 6).replace('/', '.');
									Class<?> memberClass = gencodePath.getClazz(className, true);
									if (null != memberClass) {
										boolean correctClass = false;
										if (-1 == getRegisterId().indexOf("entityURI"))
											correctClass = ReflectionTools.hasSuperClass(memberClass, ControllerCore.class)
													|| ReflectionTools.hasSuperClass(memberClass, AQueryManager.class)
													|| ReflectionTools.hasSuperClass(memberClass, ARootMenuCore.class);
										else
											correctClass = ReflectionTools.hasSuperClass(memberClass, AEntityMetaModel.class);
										if (correctClass) {
											AdiResourceURI uriAnnotation = memberClass.getAnnotation(AdiResourceURI.class);
											if (null != uriAnnotation)
												uris.add(uriAnnotation.URI());
										}
									} else
										logWarning("Class not found: " + className + "!");
								}
							} else {
								if (selectedBundle.findEntries(path, "*", false).hasMoreElements())
									members.add(selectedBundle.getEntry(path));
							}
						}
						members.addAll(uris);
						return members.toArray();
					}

					@Override
					public boolean hasChildren(Object parentElement) {
						return !(parentElement instanceof String);
					}

					@Override
					public Object[] getChildren(Object parentElement) {

						try {
							if (parentElement instanceof String)
								return null;
							if (parentElement instanceof IFolder) {
								return getMembers((IFolder) parentElement);
							} else if (parentElement instanceof URL) {
								return getMembers(((URL) parentElement).getPath());
							} else if (parentElement instanceof AdiResourceContainer) {
								if (null != selectedBundle) {
									selectedPluginResources = getSelectedPluginResources(selectedBundle.getSymbolicName());
									GencodePath gencodePath = selectedPluginResources.getGencodePath();
									String binFolder = getBinGencodePath(selectedBundle, gencodePath)
											.concat(gencodePath.getGencodePackage().replace('.', '/')); // '/resources/gencode/bin/org/sakila/model/'
									return getMembers(binFolder);
								} else
									return getMembers(selectedContainer.getXmlFolder());
							}
						} catch (CoreException e) {
							logError(e);
						}
						return null;
					}
				});

				Composite buttonComposite = toolkit.createComposite(composite);
				buttonComposite.setLayout(new MigLayout("wrap"));
				buttonComposite.setLayoutData("top");
				toolkit.createLabel(buttonComposite, getFromStudioBundle("scenario.generation.choose.plugin"));

				Button button = addButton(toolkit, buttonComposite, null,
						new AdiResourceContainer(null, scenarioResources.getProject(), scenarioResources.getPluginPackage(), true));
				button.setLayoutData("gapy 20, gap bottom 20");

				for (final Map.Entry<String, AdiResourceContainer> entry : scenarioResources.getAccessibleAdiPlugins().entrySet()) {
					addButton(toolkit, buttonComposite, entry.getKey(), entry.getValue());
				}
			}

			private Button addButton(FormToolkit toolkit, Composite buttonComposite, String pluginKey,
					AdiResourceContainer container) {
				String label;
				label = null == pluginKey ? getFromStudioBundle("studio.current.project") : pluginKey;
				final Button button = toolkit.createButton(buttonComposite, label, SWT.RADIO);
				button.setData(container);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (button.getSelection()) {
							selectedContainer = (AdiResourceContainer) button.getData();
							selectedBundle = selectedContainer.getBundle();
							selectedProject = selectedContainer.getProject();
							treeViewer.setInput(selectedContainer);
							// treeViewer.refresh();
							treeViewer.expandToLevel(2);
						}
					}
				});
				if (null == pluginKey) {
					button.setFont(JFaceResources.getBannerFont());
					button.setSelection(true);
					button.notifyListeners(SWT.Selection, null);
				} else if (pluginKey.equals(scenarioResources.getPluginName())) {
					button.setFont(JFaceResources.getBannerFont());
				}
				return button;
			}

			/**
			 * Ok pressed.
			 * 
			 * @param complementControls
			 *            the complement controls
			 */
			@Override
			public void okPressed(List<Control> complementControls) {
			}
		};
	}

	/**
	 * Gets the selected plugin resources.
	 *
	 * @param pluginKey the plugin key
	 * @return the selected plugin resources
	 */
	private AdiPluginResources getSelectedPluginResources(String pluginKey) {
		return AdichatzApplication.getPluginResources(pluginKey);
	}

	/**
	 * Gets the bin gencode path.
	 *
	 * @param bundle the bundle
	 * @param gencodePath the gencode path
	 * @return the bin gencode path
	 */
	private String getBinGencodePath(Bundle bundle, GencodePath gencodePath) {
		String binGencodePath = gencodePath.getGencodeRelativePath().concat("bin/");
		if (null == bundle.getEntry(binGencodePath))
			return "/";
		return binGencodePath;
	}
}
