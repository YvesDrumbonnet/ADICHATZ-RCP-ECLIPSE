package org.adichatz.studio.xjc.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.tools.AListenerTypeManager;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.xjc.ListenerType;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.miginfocom.swt.MigLayout;

public class ListenerTypeDialog extends ConfirmFormDialog {

	private AdiResourceBundle bundle;

	private ListenerTypeController listenerTypeController;

	private List<ListenerTypeEnum> listenerTypes = new ArrayList<ListenerTypeEnum>();

	private CheckboxTableViewer tableViewer;

	private Text resultText;

	private Button currentButton;

	private Button firstButton;

	private int currentCategory = -1;

	public ListenerTypeDialog(Shell shell, String title, Image image, AdiResourceBundle bundle,
			ListenerTypeController listenerTypeController) {
		super(shell, title, image, null);
		confirmContent = getConfirmContent();
		this.bundle = bundle;
		this.listenerTypeController = listenerTypeController;
	}

	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			confirmContent.okPressed(complementControls);
			okPressed();
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		}
	}

	private IConfirmContent getConfirmContent() {
		return new IConfirmContent() {

			private List<TableViewerColumn> columns;

			private IElementWrapper elementWrapper;

			private ListenerType listenerBean;

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				elementWrapper = (IElementWrapper) ((XjcEntity<?>) listenerTypeController.getEntity()).getTreeElement()
						.getParentElement().getParentElement().getEntity().getBean();
				listenerBean = (ListenerType) listenerTypeController.getEntity().getBean();
				if (!EngineTools.isEmpty(listenerBean.getListenerTypes())) {
					StringTokenizer tokenizer = new StringTokenizer(listenerBean.getListenerTypes(), "|");
					boolean first = true;
					while (tokenizer.hasMoreElements()) {
						String token = tokenizer.nextToken().trim();
						ListenerTypeEnum listenerTypeEnum = AListenerTypeManager.LISTENER_TYPE_MAP.get(token);
						if (first) {
							first = false;
							currentCategory = AListenerTypeManager.LISTENER_MANAGER_MAP.get(listenerTypeEnum).getListenerCategory();
						}
						listenerTypes.add(listenerTypeEnum);
					}
				}
				parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[][grow,fill]"));

				Group categoryGroup = new Group(parent, SWT.NONE);
				categoryGroup.setLayout(new MigLayout("wrap 4", "grow,fill"));
				toolkit.adapt(categoryGroup);

				categoryGroup.setText(bundle.getString("category"));
				createButton(toolkit, categoryGroup, "control", AListenerTypeManager.CONTROL);
				createButton(toolkit, categoryGroup, "lifeCycle", AListenerTypeManager.LIFE_CYCLE);
				createButton(toolkit, categoryGroup, "runnable", AListenerTypeManager.RUNNABLE);
				createButton(toolkit, categoryGroup, "entity", AListenerTypeManager.ENTITY);
				createButton(toolkit, categoryGroup, "entityInjection", AListenerTypeManager.BEFORE_ENTITY_INJECTION);

				tableViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER | SWT.FULL_SELECTION);
				tableViewer.getTable().setLayoutData("hmin 220, wmin 600");
				tableViewer.getTable().setHeaderVisible(true);
				tableViewer.addCheckStateListener(new ICheckStateListener() {
					@Override
					public void checkStateChanged(CheckStateChangedEvent event) {
						ListenerTypeEnum listenerType = (ListenerTypeEnum) event.getElement();
						if (event.getChecked())
							listenerTypes.add(listenerType);
						else
							listenerTypes.remove(listenerType);
						modifyResulText();
					}
				});
				columns = new ArrayList<TableViewerColumn>();
				columns.add(newColumn(tableViewer, "type", SWT.LEFT, new ColumnLabelProvider() {
					@Override
					public String getText(final Object element) {
						return ((ListenerTypeEnum) element).name();
					}
				}));
				columns.add(newColumn(tableViewer, "description", SWT.LEFT, new ColumnLabelProvider() {
					@Override
					public String getText(final Object element) {
						return bundle.getString(((ListenerTypeEnum) element).name());
					}
				}));
				tableViewer.setContentProvider(new ArrayContentProvider());
				resultText = toolkit.createText(parent, null);
				if (null != firstButton) {
					firstButton.setSelection(true);
					firstButton.notifyListeners(SWT.Selection, null);
				}

			}

			private TableViewerColumn newColumn(TableViewer tableViewer, String columnName, int style,
					ColumnLabelProvider columnLabelProvider) {
				final TableViewerColumn column = new TableViewerColumn(tableViewer, style);
				column.getColumn().setText(bundle.getString(columnName));
				column.setLabelProvider(columnLabelProvider);
				return column;
			}

			private void createButton(FormToolkit toolkit, Composite parent, String categoryName, final int category) {
				if (AListenerTypeManager.isEligible(elementWrapper, category)) {

					final Button controlButton = toolkit.createButton(parent, bundle.getString(categoryName), SWT.RADIO);
					List<ListenerTypeEnum> input = new ArrayList<ListenerTypeEnum>();
					for (Map.Entry<ListenerTypeEnum, AListenerTypeManager> entry : AListenerTypeManager.LISTENER_MANAGER_MAP
							.entrySet()) {
						if (category == entry.getValue().getListenerCategory()
								&& AListenerTypeManager.LISTENER_MANAGER_MAP.get(entry.getKey()).isEligible(elementWrapper)) {
							input.add(entry.getKey());
						}
					}
					controlButton.setData("input", input);
					controlButton.setData("category", category);
					controlButton.addSelectionListener(new SelectionAdapter() {
						@SuppressWarnings("unchecked")
						@Override
						public void widgetSelected(SelectionEvent e) {
							changeCategory(controlButton);
							currentButton = controlButton;
							tableViewer.getTable().setRedraw(false);
							List<ListenerTypeEnum> input = (List<ListenerTypeEnum>) controlButton.getData("input");
							tableViewer.setInput(input);
							for (ListenerTypeEnum listenerType : input)
								if (listenerTypes.contains(listenerType)) {
									tableViewer.setChecked(listenerType, true);
								}
							for (TableViewerColumn column : columns)
								column.getColumn().pack();
							tableViewer.getTable().setRedraw(true);
						}
					});
					if (null == firstButton && (-1 == currentCategory || currentCategory == category)) {
						firstButton = controlButton;
					}
				}
			}

			/**
			 * Ok pressed.
			 * 
			 * @param complementControls
			 *            the complement controls
			 */
			@Override
			public void okPressed(List<Control> complementControls) {
				listenerTypeController.setValue(resultText.getText());
			}
		};
	}

	private void modifyResulText() {
		boolean first = true;
		StringBuffer listTypesSB = new StringBuffer();
		for (ListenerTypeEnum listenerType : listenerTypes) {
			if (first)
				first = false;
			else
				listTypesSB.append(" | ");
			listTypesSB.append(listenerType.name());
		}
		resultText.setText(listTypesSB.toString());
	}

	private void changeCategory(Button controlButton) {
		if (!controlButton.equals(currentButton)) {
			Collections.sort(listenerTypes, new Comparator<ListenerTypeEnum>() {

				@Override
				public int compare(ListenerTypeEnum lt1, ListenerTypeEnum lt2) {
					AListenerTypeManager ltm1 = AListenerTypeManager.LISTENER_MANAGER_MAP.get(lt1);
					AListenerTypeManager ltm2 = AListenerTypeManager.LISTENER_MANAGER_MAP.get(lt1);
					return ltm1.getListenerCategory() - ltm2.getListenerCategory();
				}
			});
			tableViewer.setCheckedElements(listenerTypes.toArray());
			modifyResulText();
		}
	}
}
