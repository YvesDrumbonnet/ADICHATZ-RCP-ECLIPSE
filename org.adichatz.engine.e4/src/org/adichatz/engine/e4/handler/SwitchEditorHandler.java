package org.adichatz.engine.e4.handler;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.e4.part.AdiInputPart;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.BoundedPartChangeManager;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.ABindingService;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

@SuppressWarnings("restriction")
public class SwitchEditorHandler {

	private CheckboxTableViewer tableViewer;

	private Font boldFont;

	private Color errorColor;

	private Button activateButton;

	private Button closeButton;

	private Button saveButton;

	private List<TableColumn> columns;

	private List<AdiInputPart> inputParts = new ArrayList<AdiInputPart>();

	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	protected Shell shell;

	@Execute
	void execute(MApplication application, EModelService modelService, final EPartService partService) {
		columns = new ArrayList<TableColumn>();
		TrayDialog trayDialog = new TrayDialog(shell) {
			@Override
			public void create() {
				setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
				super.create();
				EngineTools.centerWindow(getShell());
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected Control createDialogArea(Composite parent) {
				getShell().setSize(700, 400);
				getShell().setText(getFromEngineE4Bundle("switch.editor"));
				getShell().setImage(
						AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_E4_BUNDLE, "IMG_SWITCH_EDITORS.png"));
				initializeBounds();
				AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
				if (null == toolkit)
					toolkit = new AdiFormToolkit(Display.getCurrent());
				parent.setBackground(toolkit.getColors().getBackground());
				ScrolledForm scrolledForm = toolkit.createScrolledForm(parent);
				scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));
				Composite body = scrolledForm.getBody();
				body.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

				createTreeViewer(toolkit, body);

				scrolledForm.reflow(true);
				applyDialogFont(scrolledForm.getBody());
				parent.layout();
				return scrolledForm;
			}

			protected Control createButtonBar(Composite parent) {
				Composite buttonsComposite = new Composite(parent, SWT.NONE);
				buttonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
				buttonsComposite.setLayout(new MigLayout("wrap 1", "[grow,fill]"));
				Composite selectionComposite = new Composite(buttonsComposite, SWT.NONE);
				selectionComposite.setLayoutData("h 80!");
				selectionComposite.setLayout(new MigLayout("wrap 3"));

				Button selectCleanButton = new Button(selectionComposite, SWT.PUSH);
				selectCleanButton.setText(getFromEngineE4Bundle("switch.editor.select.clean"));
				selectCleanButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						for (AdiInputPart inputPart : inputParts)
							tableViewer.setChecked(inputPart, !inputPart.isDirty());
						changingCheckState();
					};
				});
				Button invertSelectionButton = new Button(selectionComposite, SWT.PUSH);
				invertSelectionButton.setText(getFromEngineE4Bundle("switch.editor.invert.selection"));
				invertSelectionButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						Object[] checkedElements = tableViewer.getCheckedElements();
						tableViewer.setAllChecked(true);
						for (Object element : checkedElements)
							tableViewer.setChecked(element, false);
						changingCheckState();
					};
				});
				Button selectAllButton = new Button(selectionComposite, SWT.PUSH);
				selectAllButton.setText(getFromEngineE4Bundle("switch.editor.select.all"));
				selectAllButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						tableViewer.setAllChecked(true);
						changingCheckState();
					};
				});

				activateButton = new Button(selectionComposite, SWT.PUSH);
				activateButton.setText(getFromEngineE4Bundle("switch.editor.activate.selected"));
				activateButton.setEnabled(false);
				activateButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						for (Object element : tableViewer.getCheckedElements()) {
							AdiInputPart inputPart = (AdiInputPart) element;
							partService.showPart(inputPart, EPartService.PartState.ACTIVATE);
						}
						close();
					};
				});
				closeButton = new Button(selectionComposite, SWT.PUSH);
				closeButton.setText(getFromEngineE4Bundle("switch.editor.close.selected"));
				closeButton.setEnabled(false);
				closeButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						for (Object element : tableViewer.getCheckedElements()) {
							AdiInputPart inputPart = (AdiInputPart) element;
							BoundedPart part = (BoundedPart) inputPart.getObject();
							if (part.isDirty()) {
								if (partService.savePart(inputPart, true))
									part.close();
							} else
								part.close();
						}
						refresh();
					};
				});
				saveButton = new Button(selectionComposite, SWT.PUSH);
				saveButton.setText(getFromEngineE4Bundle("switch.editor.save"));
				saveButton.setEnabled(false);
				saveButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						for (Object element : tableViewer.getCheckedElements()) {
							AdiInputPart inputPart = (AdiInputPart) element;
							BoundedPart part = (BoundedPart) inputPart.getObject();
							if (part.isDirty())
								part.getBoundedPartChangeManager().doSave();
						}
						refresh();
					};
				});

				Composite closeComposite = new Composite(buttonsComposite, SWT.NONE);
				closeComposite.setLayout(new MigLayout("wrap 1", "push[fill]"));
				Button closeWindowButton = new Button(closeComposite, SWT.PUSH);
				closeWindowButton.setSize(200, 20);
				closeWindowButton.setText(getFromEngineE4Bundle("switch.editor.close.window"));
				closeWindowButton.setLayoutData("wmin 200");
				closeWindowButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						close();
					};
				});

				packColumns();
				closeWindowButton.setLayoutData("grow 0, push");
				getDialogArea().getParent().layout();
				return buttonsComposite;
			};

			/**
			 * Create a new <code>TreeViewer</code>.
			 * 
			 * @param parent
			 *            the parent <code>Composite</code>.
			 * @return the <code>TreeViewer</code>.
			 * @since 3.0
			 */
			protected void createTreeViewer(AdiFormToolkit toolkit, Composite parent) {
				tableViewer = CheckboxTableViewer.newCheckList(parent,
						SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
				tableViewer.getControl().setFont(JFaceResources.getDialogFont());
				tableViewer.setContentProvider(new ArrayContentProvider());
				Table table = tableViewer.getTable();
				table.setHeaderVisible(true);
				table.setLinesVisible(true);
				table.setLayout(new MigLayout("wrap", "grow,fill", "[grow,fill]"));
				table.setLayoutData("h 0:n:n, w 0:n:n");

				boldFont = EngineTools.getModifiedFont(table.getFont(), SWT.BOLD);
				errorColor = AController.ERROR_COLOR;

				// Editor title
				TableViewerColumn tableColumn = createTableViewerColumn(getFromEngineE4Bundle("switch.editor.name"), 0, SWT.NONE);
				tableColumn.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						return ((AdiInputPart) element).getLabel();
					}

					public Image getImage(Object element) {
						return EngineTools.getImage(((AdiInputPart) element).getIconURI());
					};

					public Font getFont(Object element) {
						return ((AdiInputPart) element).isDirty() ? boldFont : null;
					};

					public Color getForeground(Object element) {
						BoundedPart boundedPart = (BoundedPart) ((AdiInputPart) element).getObject();
						return boundedPart.getBoundedPartChangeManager().hasError() ? errorColor : null;
					};
				});

				// Is dirty
				tableColumn = createTableViewerColumn(getFromEngineE4Bundle("switch.editor.dirty"), 0, SWT.NONE);
				tableColumn.setLabelProvider(new ColumnLabelProvider() {
					public Image getImage(Object element) {
						return toolkit.getRegisteredImage(((AdiInputPart) element).isDirty() ? "IMG_CHECKED" : "IMG_UNCHECKED");
					};

					@Override
					public String getText(Object element) {
						return "";
					}
				});

				// Errors count
				tableColumn = createTableViewerColumn(getFromEngineE4Bundle("switch.editor.error"), 0, SWT.RIGHT);
				tableColumn.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						AdiInputPart inputPart = (AdiInputPart) element;
						if (inputPart.isDirty()) {
							BoundedPart boundedPart = (BoundedPart) inputPart.getObject();
							BoundedPartChangeManager boundedPartChangeManager = boundedPart.getBoundedPartChangeManager();
							if (boundedPartChangeManager.hasError()) {
								int errors = 0;
								for (ABindingService bindingService : boundedPart.getBindingServices())
									errors += bindingService.getErrorMessageMap().size();
								return String.valueOf(errors);
							}
						}
						return "";
					}

					public Font getFont(Object element) {
						return ((AdiInputPart) element).isDirty() ? boldFont : null;
					};

					public Color getForeground(Object element) {
						BoundedPart boundedPart = (BoundedPart) ((AdiInputPart) element).getObject();
						return boundedPart.getBoundedPartChangeManager().hasError() ? errorColor : null;
					};
				});

				// Contribution URI
				tableColumn = createTableViewerColumn(getFromEngineE4Bundle("switch.editor.contributionURI"), 0, SWT.NONE);
				tableColumn.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						return ((AdiInputPart) element).getContributionURI();
					}

					public Font getFont(Object element) {
						return ((AdiInputPart) element).isDirty() ? boldFont : null;
					};

					public Color getForeground(Object element) {
						BoundedPart boundedPart = (BoundedPart) ((AdiInputPart) element).getObject();
						return boundedPart.getBoundedPartChangeManager().hasError() ? errorColor : null;
					};
				});

				tableViewer.addCheckStateListener(new ICheckStateListener() {

					@Override
					public void checkStateChanged(CheckStateChangedEvent event) {
						changingCheckState();
					}

				});
				tableViewer.addDoubleClickListener(new IDoubleClickListener() {
					@Override
					public void doubleClick(DoubleClickEvent event) {
						AdiInputPart inputPart = (AdiInputPart) ((StructuredSelection) event.getSelection()).getFirstElement();
						partService.showPart(inputPart, EPartService.PartState.ACTIVATE);
						close();
					}
				});
				refresh();
			}
		};
		trayDialog.open();
	}

	private void refresh() {
		inputParts = new ArrayList<AdiInputPart>();
		for (MStackElement element : E4AdichatzApplication.getInstance().getEditorPartStack().getChildren())
			if (element instanceof AdiInputPart && ((AdiInputPart) element).getObject() instanceof BoundedPart)
				inputParts.add((AdiInputPart) element);
		tableViewer.setInput(inputParts);
	}

	private TableViewerColumn createTableViewerColumn(String title, final int colNumber, int style) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, style);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setResizable(true);
		column.setMoveable(true);
		columns.add(column);
		return viewerColumn;
	}

	private void packColumns() {
		for (TableColumn column : columns)
			column.pack();
	}

	private void changingCheckState() {
		Object[] selectedElements = tableViewer.getCheckedElements();
		activateButton.setEnabled(1 == selectedElements.length);
		closeButton.setEnabled(0 < selectedElements.length);
		boolean enabled = false;
		for (Object element : selectedElements) {
			AdiInputPart inputPart = (AdiInputPart) element;
			if (inputPart.isDirty() && !((BoundedPart) inputPart.getObject()).getBoundedPartChangeManager().hasError()) {
				enabled = true;
				break;
			}
		}
		saveButton.setEnabled(enabled);
	}
}
