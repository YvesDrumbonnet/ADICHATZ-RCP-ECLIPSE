package org.adichatz.studio.util;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.editor.XjcTreeFormEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import net.miginfocom.swt.MigLayout;

public class ErroneousFilesFormDialog {
	private ScenarioResources scenarioResources;

	private ConfirmFormDialog confirmFormDialog;

	public ErroneousFilesFormDialog(XjcTreeFormEditor editor, final Display display, final ScenarioResources scenarioResources) {
		this.scenarioResources = scenarioResources;
		// Use WorkspaceJob to avoid: "ResourceException: The resource tree is locked for modifications."
		WorkspaceJob job = new WorkspaceJob("Creating folders") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				scenarioResources.incrementalBuildProject();
				if (0 < scenarioResources.getErroneousFiles().size()) {
					display.syncExec(() -> {
						open();
						scenarioResources.getErroneousFiles().clear();
					});
				}
				if (null != editor)
					editor.checkJavaMarkers();
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}

	private void open() {
		IConfirmContent confirmContent = new IConfirmContent() {
			private AdiFormToolkit toolkit;

			private CheckboxTreeViewer treeViewer;

			@Override
			public void okPressed(List<Control> complementControls) {
				for (Object element : treeViewer.getCheckedElements()) {
					openEditor(element);
				}
			}

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				this.toolkit = toolkit;
				parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[][grow,fill]"));
				final Map<IFile, Set<IFile>> xmlFiles = new HashMap<IFile, Set<IFile>>();
				for (IFile javaFile : scenarioResources.getErroneousFiles()) {
					String uri = ScenarioUtil.getAdiResourceURI(javaFile);
					if (null != uri) {
						IFile xmlFile = ScenarioUtil.getXmlFileFromURI(scenarioResources, uri);
						Set<IFile> javaFiles = xmlFiles.get(xmlFile);
						if (null == javaFiles) {
							javaFiles = new HashSet<IFile>();
							xmlFiles.put(xmlFile, javaFiles);
						}
						javaFiles.add(javaFile);
					} else {
						Set<IFile> javaFiles = new HashSet<IFile>();
						javaFiles.add(javaFile);
						xmlFiles.put(javaFile, javaFiles);
					}
				}

				treeViewer = new CheckboxTreeViewer(parent, SWT.CENTER | SWT.BORDER | SWT.V_SCROLL);
				treeViewer.getTree().setLayoutData("h 400!");
				treeViewer.setLabelProvider(new LabelProvider() {
					@SuppressWarnings("rawtypes")
					public String getText(Object element) {
						if (element instanceof Map.Entry)
							return ((IFile) ((Map.Entry) element).getKey()).getName();
						else if (element instanceof IFile)
							return ((IFile) element).getName();
						else if (element instanceof IMarker)
							try {
								return (String) ((IMarker) element).getAttribute(IMarker.MESSAGE);
							} catch (CoreException e) {
								logError(e);
							}
						return null;
					}

					@Override
					public Image getImage(Object element) {
						if (element instanceof Map.Entry)
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
									"IMG_XJC_EDITOR.png");
						if (element instanceof IFile)
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
									"IMG_JAVA_ERROR.png");
						else
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_ERROR.png");
					}
				});
				treeViewer.setCheckStateProvider(new ICheckStateProvider() {
					@Override
					public boolean isChecked(Object element) {
						return element instanceof IMarker;
					}

					@Override
					public boolean isGrayed(Object element) {
						return element instanceof IMarker;
					}
				});
				final Tree tree = treeViewer.getTree();
				tree.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (e.item.getData() instanceof IMarker) {
							e.detail = SWT.NONE;
							e.doit = false;
							((TreeItem) e.item).setChecked(true);
							((TreeItem) e.item).setGrayed(true);
						}
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
						return element instanceof IFile || element instanceof Map.Entry;
					}

					@Override
					public Object getParent(Object element) {
						return null;
					}

					@Override
					public Object[] getElements(Object inputElement) {
						return xmlFiles.entrySet().toArray();
					}

					@SuppressWarnings("unchecked")
					@Override
					public Object[] getChildren(Object parentElement) {
						if (parentElement instanceof Map.Entry)
							return ((Map.Entry<IFile, Set<IFile>>) parentElement).getValue().toArray();
						if (parentElement instanceof IFile)
							try {
								List<IMarker> markers = new ArrayList<IMarker>();
								for (IMarker marker : ((IFile) parentElement).findMarkers(IMarker.PROBLEM, true,
										IResource.DEPTH_ZERO))
									if (marker.getAttribute(IMarker.SEVERITY).equals(IMarker.SEVERITY_ERROR))
										markers.add(marker);
								return markers.toArray();
							} catch (CoreException e) {
								logError(e);
							}
						return null;
					}
				});
				treeViewer.setInput(xmlFiles.entrySet());
				treeViewer.expandToLevel(2);
				createSelectButtons(parent, treeViewer);
				treeViewer.addDoubleClickListener(new IDoubleClickListener() {
					@Override
					public void doubleClick(DoubleClickEvent event) {
						if (openEditor(((IStructuredSelection) event.getSelection()).getFirstElement()))
							;
						confirmFormDialog.close();
					}
				});

				IToolBarManager toolBarManager = confirmFormDialog.getScrolledForm().getToolBarManager();
				// Expand
				Action expandAction = new Action() {
					@Override
					public void run() {
						treeViewer.expandAll();
					}
				};
				expandAction.setText(getFromStudioBundle("studio.editor.expandTree"));
				expandAction.setImageDescriptor(toolkit.getRegisteredImageDescriptor("IMG_EXPAND"));
				toolBarManager.add(expandAction);

				// / Collapse
				Action collapseAction = new Action() {
					@Override
					public void run() {
						treeViewer.collapseAll();
					}
				};
				collapseAction.setText(getFromStudioBundle("studio.editor.collapseTree"));
				collapseAction.setImageDescriptor(
						AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "IMG_COLLAPSE.gif"));
				toolBarManager.add(collapseAction);

				toolBarManager.update(true);
			}

			@SuppressWarnings("unchecked")
			private boolean openEditor(Object selectedObject) {
				try {
					if (selectedObject instanceof Map.Entry) {
						IFile xmlFile = ((Map.Entry<IFile, Set<IFile>>) selectedObject).getKey();
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.openEditor(new FileEditorInput(xmlFile), XjcTreeFormEditor.ID);
						return true;
					} else if (selectedObject instanceof IFile)
						JavaUI.openInEditor(((IFile) selectedObject).getAdapter(IJavaElement.class));
					return true;
				} catch (PartInitException | JavaModelException e) {
					logError(e);
				}
				return false;
			}

			private void createSelectButtons(Composite patternComposite, final CheckboxTreeViewer treeViewer) {
				Composite selectComposite = toolkit.createComposite(patternComposite);
				selectComposite.setLayout(new MigLayout("ins 0"));
				selectComposite.setLayoutData("align right");
				Button selectButton = toolkit.createButton(selectComposite, getFromStudioBundle("confirmContent.selectAll"),
						SWT.PUSH);
				selectButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						selectAll(treeViewer.getTree().getItems(), true);
					}
				});
				selectButton.setLayoutData("sg selectButtons, gapx 20!");
				selectButton.setFocus();

				Button deselectButton = toolkit.createButton(selectComposite, getFromStudioBundle("confirmContent.deselectAll"),
						SWT.PUSH);
				deselectButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						selectAll(treeViewer.getTree().getItems(), false);
					}
				});
				deselectButton.setLayoutData("sg selectButtons, gapx 20!");
			}

			private void selectAll(TreeItem[] items, boolean state) {
				for (int i = 0; i < items.length; i++) {
					TreeItem item = items[i];
					if (item.getData() != null) {
						if (item.getData() instanceof Map.Entry) {
							if (item.getChecked() != state)
								item.setChecked(state);
							selectAll(item.getItems(), state);
						} else if (item.getData() instanceof IFile) {
							if (item.getChecked() != state) {
								item.setChecked(state);
							}
						}
					}
				}
			}
		};
		confirmFormDialog = new ConfirmFormDialog(null, AdichatzApplication.getInstance().getFormToolkit(),
				getFromStudioBundle("generation.encountered.errors"),
				AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_ERROR.png"), confirmContent);
		confirmFormDialog.open();
	}
}
