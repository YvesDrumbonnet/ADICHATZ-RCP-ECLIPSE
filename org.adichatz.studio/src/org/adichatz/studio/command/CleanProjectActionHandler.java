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
package org.adichatz.studio.command;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.UnmarshalException;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.LimitedComposite;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.AdiPropertyTester;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.command.CleanProjectActionHandler.IPendingElement;
import org.adichatz.studio.command.CleanProjectActionHandler.PendingProblem;
import org.adichatz.studio.command.CleanProjectActionHandler.PendingResource;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class CleanProjectActionDelegate.
 */
public class CleanProjectActionHandler extends AAdiHandler {

	/** The Constant SAX. */
	protected static final int SAX = 1;

	/** The Constant GENERATION. */
	protected static final int GENERATION = 1 << 1;

	protected static final int JAVA = 1 << 2;

	/** The Constant MISSING. */
	protected static final int MISSING = 1 << 3;

	/** The adi project. */
	private IProject project;

	/** The scenario resources. */
	private ScenarioResources scenarioResources;

	/** The pending problems. */
	private List<PendingProblem> pendingProblems;

	/** The current generator unit. */
	private GeneratorUnit currentGeneratorUnit;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		Display.getCurrent().syncExec(() -> {
			ISelection selection = HandlerUtil.getActiveMenuSelection(event);
			if (selection instanceof ITreeSelection) {
				Object selectedObject = ((ITreeSelection) selection).getFirstElement();
				if (selectedObject instanceof IProject) {
					project = (IProject) selectedObject;
				} else if (selectedObject instanceof IJavaProject) {
					project = ((IJavaProject) selectedObject).getProject();
				}
				scenarioResources = ScenarioResources.getInstance(project);
				if (!checkProject()) {
					if (!pendingProblems.isEmpty())
						new CleanProjectFormDialog(pendingProblems).open();
				} else
					EngineTools.openDialog(MessageDialog.INFORMATION, getFromStudioBundle("studio.projectCleanAction"),
							getFromStudioBundle("studio.projectCleanAction.nothingToClean"));
			}
		});
		return null;
	}

	/**
	 * Check project.
	 * 
	 * @return true, if successful
	 */
	private boolean checkProject() {
		pendingProblems = new ArrayList<PendingProblem>();
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
			ScenarioUtil.waitForEndAutoBuilding(project);

			checkAdiResource(scenarioResources.getXmlFolder());
			checkSrcFolder(scenarioResources.getSrcGencodeFolder(), ""); // check for unnecessary hava files

		} catch (CoreException e) {
			logError(e);
		}
		return pendingProblems.isEmpty();
	}

	/**
	 * Check adi resource.
	 * 
	 * @param parentResource
	 *            the parent resource
	 * @throws CoreException
	 *             the core exception
	 */
	private void checkAdiResource(IContainer parentResource) throws CoreException {
		for (Object object : parentResource.members()) {
			IResource resource = (IResource) object;
			if (resource instanceof IFile) {
				IFile file = (IFile) resource;
				PendingProblem pendingProblem = new PendingProblem(file, file.getName());
				boolean javaProblem = false;
				try {
					if (AdiPropertyTester.isActiveFile(file)) {
						if (IMarker.SEVERITY_ERROR == file.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_ZERO)) {
							pendingProblem.problem |= SAX;
							pendingProblem.action = "studio.projectCleanAction.open.axml.file";
							pendingProblem.description = "studio.projectCleanAction.sax.desc";
						} else {
							String treeId = ScenarioUtil.getTreeId(file);
							String subPackage = ScenarioUtil.getSubPackage(file);
							ScenarioInput scenarioInput = new ScenarioInput(scenarioResources, treeId, subPackage);
							currentGeneratorUnit = new GeneratorUnit(scenarioInput);
							try {
								currentGeneratorUnit.getTreeWrapperFromXml(true);
								List<IResource> javaFiles = ScenarioUtil.getJavaFiles(file);
								for (IResource javaResource : javaFiles) {
									IPath javaPath = javaResource.getFullPath();
									PendingResource pendingResource = new PendingResource(javaResource);
									IFile javaFile = project.getFile(javaPath.removeFirstSegments(1));
									if (IMarker.SEVERITY_ERROR == javaFile.findMaxProblemSeverity(IMarker.PROBLEM, true,
											IResource.DEPTH_INFINITE)
											|| currentGeneratorUnit.getFilesWithProblem()
													.contains(javaFile.getLocation().toFile())) {
										pendingResource.action = "studio.projectCleanAction.open.java.file";
										pendingResource.description = "studio.projectCleanAction.java.desc";
										pendingProblem.problem |= JAVA;
										pendingProblem.action = "studio.projectCleanAction.open.axml.file";
										pendingProblem.description = "studio.projectCleanAction.generation.java.desc";
										javaProblem = true;
									}
									pendingProblem.javaResources.add(pendingResource);
								}
								if (0 == (pendingProblem.problem & CleanProjectActionHandler.JAVA)
										&& !currentGeneratorUnit.checkGeneration()) {
									pendingProblem.problem |= GENERATION;
									pendingProblem.action = "studio.projectCleanAction.open.axml.file";
									pendingProblem.description = "studio.projectCleanAction.obsolete.generation.desc";
								}
								if (!javaProblem)
									pendingProblem.javaResources.clear();
							} catch (RuntimeException e) {
								if (e.getCause() instanceof UnmarshalException) {
									pendingProblem.problem |= SAX;
									pendingProblem.action = "studio.projectCleanAction.open.axml.file";
									pendingProblem.description = "studio.projectCleanAction.sax.desc";
								} else
									throw e;
							}
						}
						if (0 != pendingProblem.problem || javaProblem)
							pendingProblems.add(pendingProblem);
					}
				} catch (CoreException e) {
					logError(e);
				}
			}
			if (resource instanceof IContainer)
				checkAdiResource((IContainer) resource);
		}
	}

	/**
	 * Check src folder.
	 * 
	 * @param srcFolder
	 *            the src folder
	 * @param subPackage
	 *            the sub package
	 * @throws CoreException
	 *             the core exception
	 */
	private void checkSrcFolder(IFolder srcFolder, String subPackage) throws CoreException {
		String oldTreeId = "";
		IResource[] members = srcFolder.members();
		Arrays.sort(members, new Comparator<IResource>() {
			public int compare(IResource o1, IResource o2) {
				return o1.getName().compareTo(o2.getName());
			};
		});
		PendingProblem pendingProblem = null;
		for (IResource resource : members) {
			if (resource instanceof IFolder) {
				oldTreeId = "";
				String parentSubPackage = "".equals(subPackage) ? resource.getName() : subPackage + "." + resource.getName();
				checkSrcFolder((IFolder) resource, parentSubPackage);
			} else if (resource instanceof IFile && "java".equals(resource.getFileExtension())) {
				String adiResourceURI = ScenarioUtil.getAdiResourceURI((IFile) resource);
				if (null != adiResourceURI && null == ScenarioUtil.getXmlFileFromURI(scenarioResources, adiResourceURI)) {
					String treeId = EngineTools.getInstanceKeys(adiResourceURI)[2];
					if (!treeId.equals(oldTreeId)) {
						pendingProblem = new PendingProblem(null, treeId);
						pendingProblem.problem |= MISSING;
						pendingProblems.add(pendingProblem);
					}
					PendingResource pendingResource = new PendingResource(resource);
					pendingResource.action = "studio.projectCleanAction.delete.java.files";
					pendingProblem.description = "studio.projectCleanAction.missing.desc";
					pendingProblem.javaResources.add(pendingResource);
				}
			}
		}
	}

	/**
	 * The Interface IPendingElement.
	 */
	interface IPendingElement {

		/**
		 * Gets the action.
		 * 
		 * @return the action
		 */
		public String getAction();

		/**
		 * Gets the resource.
		 *
		 * @return the resource
		 */
		public IResource getResource();

		/**
		 * Gets the description.
		 * 
		 * @return the description
		 */

		public String getDescription();
	}

	/**
	 * The Class PendingProblem.
	 */
	class PendingProblem implements IPendingElement {

		/** The file. */
		protected IFile file;

		/** The problem. */
		protected int problem;

		/** The file name. */
		protected String fileName;

		/** The java resources. */
		protected List<PendingResource> javaResources;

		/** The description. */
		private String description;

		/** The action. */
		private String action;

		/**
		 * Instantiates a new pending problem.
		 * 
		 * @param axmlFile
		 *            the axml file
		 * @param fileName
		 *            the file name
		 */
		public PendingProblem(IFile axmlFile, String fileName) {
			this.file = axmlFile;
			this.fileName = fileName;
			problem = 0;
			javaResources = new ArrayList<>();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.studio.command.CleanProjectActionHandler.IPendingElement#getAction()
		 */
		@Override
		public String getAction() {
			return action;
		}

		@Override
		public IResource getResource() {
			return file;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.studio.command.CleanProjectActionHandler.IPendingElement#getDescription()
		 */
		@Override
		public String getDescription() {
			return description;
		}

	}

	/**
	 * The Class PendingResource.
	 */
	class PendingResource implements IPendingElement {

		/** The pending problem. */
		protected PendingProblem pendingProblem;

		/** The problem. */
		protected int problem;

		/** The description. */
		private String description;

		/** The action. */
		private String action;

		/** The resource. */
		protected IResource resource;

		/**
		 * Instantiates a new pending path.
		 * 
		 * @param path
		 *            the path
		 */
		public PendingResource(IResource resource) {
			this.resource = resource;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.studio.command.CleanProjectActionHandler.IPendingElement#getAction()
		 */
		@Override
		public String getAction() {
			return action;
		}

		@Override
		public IResource getResource() {
			return resource;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.studio.command.CleanProjectActionHandler.IPendingElement#getDescription()
		 */
		@Override
		public String getDescription() {
			return description;
		}

		/**
		 * Delete java file.
		 */
		protected void deleteJavaFile() {
			try {
				resource.delete(true, null);
			} catch (CoreException e) {
				logError(e);
			}
		}
	}
}

class CleanProjectFormDialog extends TrayDialog {

	private Image xmlFileImage;

	private Image javaFileImage;

	private List<PendingProblem> pendingProblems;

	private AdiFormToolkit toolkit;

	private CheckboxTreeViewer treeViewer;

	private Set<Object> grayedElements;

	private Button okButton;

	/**
	 * Instantiates a new communication port form dialog.
	 */
	public CleanProjectFormDialog(List<PendingProblem> pendingProblems) {
		super(Display.getCurrent().getActiveShell());
		this.pendingProblems = pendingProblems;
		grayedElements = new HashSet<Object>();
		setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setSize(700, 400);
		String title = getFromStudioBundle("studio.projectClean.title");
		getShell().setText(title);
		getShell().setImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_CLEAN.png"));

		initializeBounds();
		toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);

		parent.setBackground(toolkit.getColors().getBackground());

		ScrolledForm scrolledForm = toolkit.createScrolledForm(parent);
		scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		scrolledForm.setExpandHorizontal(true);
		scrolledForm.setExpandVertical(true);
		scrolledForm.setBackground(toolkit.getColors().getBackground());
		scrolledForm.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		scrolledForm.setFont(JFaceResources.getHeaderFont());

		toolkit.decorateFormHeading(scrolledForm.getForm());

		Composite body = scrolledForm.getBody();
		body.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
		scrolledForm.setText(title);

		createTreeViewer(body);

		scrolledForm.reflow(true);
		applyDialogFont(scrolledForm.getBody());
		parent.layout();
		return scrolledForm;
	}

	protected void createTreeViewer(Composite parent) {
		xmlFileImage = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_XJC_EDITOR.png");
		javaFileImage = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_CLASS_OBJ.png");

		LimitedComposite treeComposite = new LimitedComposite(parent, SWT.NONE);
		MigLayout migLayout = new MigLayout("wrap, ins 0", "grow,fill", "[grow,fill]");
		treeComposite.setLayout(migLayout);
		treeComposite.setLayoutData("h 0:64:n, w 0:64:n");

		treeViewer = new CheckboxTreeViewer(treeComposite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		Tree tree = treeViewer.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		tree.setLayoutData("h 0:n:n, w 0:n:n");
		tree.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				event.height = 25;
			}
		});

		TreeViewerColumn fileColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		fileColumn.getColumn().setText(getFromStudioBundle("studio.projectCleanAction.file"));
		fileColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof PendingProblem) {
					PendingProblem pendingProblem = (PendingProblem) element;
					if (null == pendingProblem.getAction())
						grayedElements.add(pendingProblem);
					return pendingProblem.fileName;
				}
				if (element instanceof PendingResource) {
					PendingResource pendingResource = (PendingResource) element;
					if (null == pendingResource.getAction())
						grayedElements.add(pendingResource);
					return pendingResource.resource.getFullPath().lastSegment();
				}
				return "";
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof PendingProblem) {
					Image image = xmlFileImage;
					PendingProblem pendingProblem = (PendingProblem) element;
					if (0 != (pendingProblem.problem & CleanProjectActionHandler.SAX)) {
						ImageDescriptor imageDescriptor = new DecorationOverlayIcon(xmlFileImage, AdichatzApplication.getInstance()
								.getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "DCR_ERROR.png"), IDecoration.BOTTOM_LEFT);
						image = imageDescriptor.createImage();
					} else if (0 != (pendingProblem.problem & CleanProjectActionHandler.MISSING)) {
						ImageDescriptor imageDescriptor = new DecorationOverlayIcon(xmlFileImage, AdichatzApplication.getInstance()
								.getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "DCR_MISSING.gif"), IDecoration.BOTTOM_LEFT);
						image = imageDescriptor.createImage();
					} else if (0 != (pendingProblem.problem & CleanProjectActionHandler.GENERATION)
							|| 0 != (pendingProblem.problem & CleanProjectActionHandler.JAVA)) {
						ImageDescriptor imageDescriptor = new DecorationOverlayIcon(xmlFileImage, AdichatzApplication.getInstance()
								.getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "DCR_RED_ASTERISK.png"),
								IDecoration.BOTTOM_LEFT);
						image = imageDescriptor.createImage();
					}
					return image;
				}
				if (element instanceof PendingResource) {
					Image image = javaFileImage;
					IResource javaFile = ((PendingResource) element).resource;
					try {
						if (IMarker.SEVERITY_ERROR == javaFile.findMaxProblemSeverity(IMarker.PROBLEM, true,
								IResource.DEPTH_INFINITE)) {
							ImageDescriptor imageDescriptor = new DecorationOverlayIcon(javaFileImage, AdichatzApplication
									.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "DCR_ERROR.png"),
									IDecoration.BOTTOM_LEFT);
							image = imageDescriptor.createImage();
						}
					} catch (CoreException e) {
						logError(e);
					}
					return image;
				}
				return null;
			}
		});

		TreeViewerColumn descColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		descColumn.getColumn().setText(getFromStudioBundle("studio.projectCleanAction.description"));
		descColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IPendingElement && null != ((IPendingElement) element).getDescription())
					return getFromStudioBundle(((IPendingElement) element).getDescription());
				return "";
			}
		});

		TreeViewerColumn actionColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		actionColumn.getColumn().setText(getFromStudioBundle("studio.projectCleanAction.action"));
		actionColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IPendingElement && null != ((IPendingElement) element).getAction())
					return getFromStudioBundle(((IPendingElement) element).getAction());
				return "";
			}
		});

		treeViewer.setContentProvider(new ITreeContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof PendingProblem)
					return null != ((PendingProblem) element).javaResources && !((PendingProblem) element).javaResources.isEmpty();
				return false;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return pendingProblems.toArray();
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof PendingProblem)
					return ((PendingProblem) parentElement).javaResources.toArray();
				return null;
			}
		});

		treeViewer.setInput(pendingProblems.toArray());
		treeViewer.refresh();
		treeViewer.expandAll();

		treeViewer.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				IPendingElement pendingElement = (IPendingElement) event.getElement();
				if (grayedElements.contains(pendingElement)) {
					treeViewer.setGrayChecked(event.getElement(), true);
					if (pendingElement instanceof PendingProblem) {
						PendingProblem pendingProblem = (PendingProblem) pendingElement;
						if (0 != (pendingProblem.problem & CleanProjectActionHandler.MISSING)) {
							boolean checked = !treeViewer.getChecked(pendingProblem.javaResources.get(0));
							for (PendingResource pendingResource : pendingProblem.javaResources)
								treeViewer.setChecked(pendingResource, checked);
						}
					}
				}
				okButton.setEnabled(0 < treeViewer.getCheckedElements().length - grayedElements.size());
			}
		});

		for (Object element : grayedElements)
			treeViewer.setGrayChecked(element, true);
		fileColumn.getColumn().pack();
		descColumn.getColumn().pack();
		actionColumn.getColumn().pack();

	}

	@Override
	protected Control createContents(Composite parent) {
		getShell().setSize(600, 250);

		return super.createContents(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.FormDialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		Composite buttonBar = new Composite(parent, SWT.NONE);
		buttonBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		buttonBar.setLayout(new MigLayout("wrap 2, ins 10, al right", "[]20[]"));

		Button selectAllButton = createButton(buttonBar, getFromStudioBundle("confirmContent.selectAll"));
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for (PendingProblem pendingProblem : pendingProblems)
					treeViewer.setSubtreeChecked(pendingProblem, true);
				okButton.setEnabled(0 < treeViewer.getCheckedElements().length - grayedElements.size());
			};
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				executeAction((IPendingElement) ((StructuredSelection) event.getSelection()).getFirstElement());
				close();
			}
		});
		Button deselectAllButton = createButton(buttonBar, getFromStudioBundle("confirmContent.deselectAll"));
		deselectAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for (PendingProblem pendingProblem : pendingProblems)
					treeViewer.setSubtreeChecked(pendingProblem, false);
				for (Object element : grayedElements)
					treeViewer.setGrayChecked(element, true);
				okButton.setEnabled(0 < treeViewer.getCheckedElements().length - grayedElements.size());
			};
		});

		okButton = createButton(buttonBar, getFromStudioBundle("studio.projectClean.launch.action"));
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for (Object element : treeViewer.getCheckedElements())
					executeAction((IPendingElement) element);
				close();
			};
		});
		okButton.setEnabled(false);

		Button cancelButton = createButton(buttonBar, getFromEngineBundle("cancel"));
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				close();
			};
		});
		getShell().setDefaultButton(cancelButton);

		getDialogArea().getParent().layout();
		return buttonBar;
	}

	private Button createButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(text);
		button.setLayoutData("sg buttonBar");
		return button;
	}

	private void executeAction(IPendingElement pendingElement) {
		if (null != pendingElement.getAction()) {
			if (pendingElement.getAction().startsWith("studio.projectCleanAction.open."))
				try {
					IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
							(IFile) pendingElement.getResource(), true);
				} catch (PartInitException e) {
					LogBroker.logError(e);
				}
			if (pendingElement.getAction().equals("studio.projectCleanAction.delete.java.files")) {
				((PendingResource) pendingElement).deleteJavaFile();
				logInfo(getFromStudioBundle("studio.projectClean.delete.javaFile", pendingElement.getResource().getName()));
			}
		}
	}
}
