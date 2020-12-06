/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 *
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.engine.widgets;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.extra.AFormWindow;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;

import net.miginfocom.swt.MigLayout;

/**
 * The MultiChoice class represents a selectable user interface object that combines a read-only text-field and a set of checkboxes.
 *
 * Based on org.mihalis.opal.multiChoice (see https://github.com/lcaron/opal)
 * 
 */
public class MultiChoice extends Composite {

	/** The label. */
	private Label label;

	/** The open popup button. */
	private Button openPopupButton;

	/** The number of columns. */
	private int numberOfColumns = 2;

	/** The elements. */
	private List<String> elements;

	/** The selection. */
	private Set<Integer> selection;

	/** List of checkBoxes (one checkBox by element). */
	private List<Button> checkBoxes;

	/** The selection listener. */
	private SelectionListener selectionListener;

	private boolean popupToolbar = false;

	private boolean popupTitle = false;

	private int popupMaxWidth = SWT.DEFAULT;

	private int popupMaxHeight = SWT.DEFAULT;

	/**
	 * Constructs a new instance of this class given its parent.
	 *
	 * @param toolkit
	 *            the toolkit
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot be null)
	 * @param style
	 *            not used
	 */
	public MultiChoice(final Composite parent, final int style) {
		this(parent, style, null);
	}

	/**
	 * Constructs a new instance of this class given its parent.
	 *
	 * @param toolkit
	 *            the toolkit
	 * @param parent
	 *            a widget which will be the parent of the new instance (cannot be null)
	 * @param style
	 *            not used
	 * @param elements
	 *            list of elements displayed by this widget
	 */
	public MultiChoice(final Composite parent, final int style, final List<String> elements) {
		super(parent, style);

		final AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		this.setLayout(new MigLayout("wrap 2, ins 0", "[grow,fill][]"));

		this.label = toolkit.createLabel(this, null, SWT.SINGLE | SWT.READ_ONLY);
		label.setLayoutData("w 20:20:n");

		this.openPopupButton = toolkit.createButton(this, null, SWT.TOGGLE);
		this.openPopupButton.setLayoutData("w 18!, h 20!, pad 0 -3 0 0");
		this.openPopupButton.setToolTipText(getFromEngineBundle("multiChoice.open.popup"));
		this.openPopupButton.setImage(toolkit.getRegisteredImage("IMG_WIDGET_OPEN"));
		this.openPopupButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createPopup();
			}
		});
		this.selection = new HashSet<Integer>();
		this.elements = elements;
	}

	/**
	 * Adds the argument to the receiver's list at the given zero-relative index.
	 *
	 * @param value
	 *            the value
	 * @param index
	 *            the index for the item
	 */
	public void add(final String value, final int index) {
		checkWidget();
		checkNullElement();
		if (value == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}

		checkRange(index - 1);

		this.elements.add(index, value);
	}

	/**
	 * Adds the argument to the end of the receiver's list.
	 *
	 * @param values
	 *            new items
	 */
	public void addAll(final List<String> values) {
		checkWidget();
		if (values == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}

		if (this.elements == null) {
			this.elements = new ArrayList<String>();
		}
		this.elements.addAll(values);
	}

	/**
	 * Adds the argument to the end of the receiver's list.
	 *
	 * @param values
	 *            new items
	 */
	public void addAll(final String[] values) {
		checkWidget();
		if (values == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		if (this.elements == null) {
			this.elements = new ArrayList<String>();
		}
		for (final String value : values) {
			this.elements.add(value);
		}
	}

	/**
	 * Returns the item at the given, zero-relative index in the receiver's list. Throws an exception if the index is out of range.
	 *
	 * @param index
	 *            the index of the item to return
	 * @return the item at the given index
	 */
	public String getItem(final int index) {
		checkWidget();
		checkNullElement();
		checkRange(index);

		return this.elements.get(index);
	}

	/**
	 * Returns the number of items contained in the receiver's list.
	 *
	 * @return the number of items
	 */
	public int getItemCount() {
		checkWidget();
		if (this.elements == null) {
			return 0;
		}

		return this.elements.size();

	}

	/**
	 * Returns the list of items in the receiver's list.
	 * <p>
	 * Note: This is not the actual structure used by the receiver to maintain its list of items, so modifying the array will not
	 * affect the receiver.
	 * </p>
	 *
	 * @return the items in the receiver's list
	 */
	public List<String> getItems() {
		checkWidget();
		if (this.elements == null) {
			return null;
		}
		return new ArrayList<String>(this.elements);
	}

	/**
	 * Removes the item from the receiver's list at the given zero-relative index.
	 *
	 * @param index
	 *            the index for the item
	 */
	public void removeAt(final int index) {
		checkWidget();
		checkNullElement();
		checkRange(index);
		final Object removedElement = this.elements.remove(index);
		this.selection.remove(removedElement);
	}

	/**
	 * Searches the receiver's list starting at the first item until an item is found that is equal to the argument, and removes
	 * that item from the list.
	 *
	 * @param value
	 *            the item to remove
	 */
	public void remove(final String value) {
		if (value == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		checkWidget();
		checkNullElement();
		int index = this.elements.indexOf(value);
		if (-1 != index) {
			this.elements.remove(index);
			this.selection.remove(Integer.valueOf(index));

		}
	}

	/**
	 * Remove all items of the receiver.
	 */
	public void removeAll() {
		checkWidget();
		if (this.elements != null) {
			this.elements.clear();
			this.selection.clear();
		}
	}

	/**
	 * Sets the selection of the receiver. If the item was already selected, it remains selected.
	 *
	 * @param selection
	 *            the new selection
	 */

	public void setSelection(final Set<Integer> selection) {
		checkWidget();
		checkNullElement();
		if (selection == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		this.selection = selection;
		updateSelection();
	}

	/**
	 * Selects all selected items in the receiver's list.
	 */
	public void selectAll() {
		checkWidget();
		checkNullElement();
		int size = elements.size();
		selection = new HashSet<Integer>(size);
		for (int i = 0; i < size; i++)
			selection.add(i);
		updateSelection();
	}

	/**
	 * Selects the item at the given zero-relative index in the receiver's list. If the item at the index was already selected, it
	 * remains selected.
	 *
	 * @param index
	 *            the index of the item to select
	 */
	public void selectAt(final int index) {
		checkWidget();
		checkNullElement();
		checkRange(index);
		this.selection.add(index);
		updateSelection();
	}

	/**
	 * Selects items in the receiver. If the items were already selected, they remain selected.
	 *
	 * @param index
	 *            the indexes of the items to select
	 */
	public void setSelectedIndex(final int[] index) {
		checkWidget();
		checkNullElement();
		for (final int i : index) {
			checkRange(i);
			this.selection.add(i);
		}
		updateSelection();
	}

	/**
	 * Returns the zero-relative indices of the items which are currently selected in the receiver. The order of the indices is
	 * unspecified. The array is empty if no items are selected.
	 * <p>
	 * Note: This is not the actual structure used by the receiver to maintain its selection, so modifying the array will not affect
	 * the receiver.
	 * </p>
	 *
	 * @return the array of indices of the selected items
	 */
	public int[] getSelectedIndex() {
		checkWidget();
		if (this.elements == null)
			return new int[] {};
		final List<Integer> selectedIndex = new ArrayList<Integer>();
		for (Integer i : selection)
			selectedIndex.add(i);

		final int[] returned = new int[selectedIndex.size()];
		for (int i = 0; i < selectedIndex.size(); i++) {
			returned[i] = selectedIndex.get(i);
		}

		return returned;
	}

	/**
	 * Returns an set of <code>Integer</code>s that are currently selected in the receiver. The order of the items is unspecified.
	 * An empty array indicates that no items are selected.
	 * <p>
	 * Note: This is not the actual structure used by the receiver to maintain its selection, so modifying the array will not affect
	 * the receiver.
	 * </p>
	 *
	 * @return an array representing the selection
	 */
	public Set<Integer> getSelection() {
		checkWidget();
		checkNullElement();
		return this.selection;
	}

	/**
	 * Deselects the item at the given zero-relative index in the receiver's list. If the item at the index was already deselected,
	 * it remains deselected. Indices that are out of range are ignored.
	 *
	 * @param index
	 *            the index of the item to deselect
	 */
	public void deselectAt(final int index) {
		checkWidget();
		checkNullElement();

		if (index < 0 || index >= this.elements.size()) {
			SWT.error(SWT.ERROR_INVALID_RANGE);
		}

		this.selection.remove(index);
		updateSelection();
	}

	/**
	 * Deselects all items in the receiver's list.
	 */
	public void deselectAll() {
		checkWidget();
		if (null != elements) {
			checkNullElement();
			this.selection.clear();
			updateSelection();
		}
	}

	public Label getLabel() {
		return label;
	}

	/**
	 * Gets the number of columns.
	 *
	 * @return the number of columns
	 */
	public int getNumberOfColumns() {
		checkWidget();
		return this.numberOfColumns;
	}

	/**
	 * Sets the number of columns.
	 *
	 * @param numberOfColumns
	 *            the number of columns
	 */
	public void setNumberOfColumns(final int numberOfColumns) {
		checkWidget();
		this.numberOfColumns = numberOfColumns;
	}

	public boolean isPopupToolbar() {
		return popupToolbar;
	}

	public void setPopupToolbar(boolean popupToolbar) {
		this.popupToolbar = popupToolbar;
	}

	public boolean isPopupTitle() {
		return popupTitle;
	}

	public void setPopupTitle(boolean popupTitle) {
		this.popupTitle = popupTitle;
	}

	public int getPopupMaxWidth() {
		return popupMaxWidth;
	}

	public void setPopupMaxWidth(int popupMaxWidth) {
		this.popupMaxWidth = popupMaxWidth;
	}

	public int getPopupMaxHeight() {
		return popupMaxHeight;
	}

	public void setPopupMaxHeight(int popupMaxHeight) {
		this.popupMaxHeight = popupMaxHeight;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled
	 *            the enabled
	 * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(final boolean enabled) {
		checkWidget();
		this.openPopupButton.setEnabled(enabled);
		this.label.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	/**
	 * Sets the tool tip text.
	 *
	 * @param txt
	 *            the tool tip text
	 * @see org.eclipse.swt.widgets.Control#setToolTipText(java.lang.String)
	 */
	@Override
	public void setToolTipText(final String txt) {
		checkWidget();
		this.label.setToolTipText(txt);
	}

	/**
	 * Gets the selection listener.
	 *
	 * @return the selection listener
	 */
	public SelectionListener getSelectionListener() {
		checkWidget();
		return this.selectionListener;
	}

	/**
	 * Sets the selection listener.
	 *
	 * @param selectionListener
	 *            the new selection listener
	 */
	public void setSelectionListener(final SelectionListener selectionListener) {
		checkWidget();
		this.selectionListener = selectionListener;
		updateSelection();
	}

	/**
	 * Update the selection.
	 */
	public void updateSelection() {
		checkWidget();
		if (null == this.checkBoxes) {
			return;
		}

		for (int i = 0; i < this.checkBoxes.size(); i++) {
			final Button currentButton = this.checkBoxes.get(i);
			if (!currentButton.isDisposed()) {
				currentButton.setSelection(this.selection.contains(i));
			}
		}
		setText();
	}

	/**
	 * Create the popup that contains all checkboxes.
	 */
	private void createPopup() {
		if (null != this.elements && !this.elements.isEmpty()) {
			AFormWindow formDialog = new AFormWindow(getShell(), SWT.TOOL | SWT.RESIZE) {
				@SuppressWarnings("serial")
				@Override
				protected void createFormContent() {
					// Reload toolkit because it could disposed by reskin process
					AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
					final Set<Integer> currentSelection = new HashSet<Integer>(selection) {
						@Override
						public void clear() {
							super.clear();
						};
					};

					final Composite composite = toolkit.createComposite(scrolledForm.getBody());
					composite.setLayout(new MigLayout("wrap " + numberOfColumns));
					String layoutData = "";
					if (SWT.DEFAULT != popupMaxHeight)
						layoutData = "hmax ".concat(String.valueOf(popupMaxHeight));
					if (SWT.DEFAULT != popupMaxWidth) {
						String member = "wmax ".concat(String.valueOf(popupMaxHeight));
						layoutData = layoutData.isEmpty() ? member : layoutData.concat(", ").concat(member);
					}
					if (!layoutData.isEmpty()) {
						scrolledForm.getParent().setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
						scrolledForm.setLayoutData(layoutData);
					}
					checkBoxes = new ArrayList<Button>(elements.size());
					int i = 0;
					for (final String label : elements) {
						final Button checkBoxButton = toolkit.createButton(composite, label, SWT.CHECK);
						checkBoxButton.setData(i);
						checkBoxButton.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(final SelectionEvent e) {
								if (checkBoxButton.getSelection()) {
									MultiChoice.this.selection.add((int) checkBoxButton.getData());
								} else {
									MultiChoice.this.selection.remove((int) checkBoxButton.getData());
								}
								setText();

								Event event = new Event();
								event.widget = scrolledForm;
								scrolledForm.notifyListeners(SWT.Skin, event);
							}
						});

						if (selectionListener != null) {
							checkBoxButton.addSelectionListener(selectionListener);
						}

						checkBoxButton.setSelection(selection.contains(i));
						checkBoxes.add(checkBoxButton);
						i++;
					}

					scrolledForm.getBody().setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
					if (popupToolbar) {
						if (popupTitle) {
							String title = getFromEngineBundle("multiChoice.dialog.title");
							scrolledForm.setText(title);
							scrolledForm.setImage(toolkit.getRegisteredImage("IMG_MULTICHOICE"));
						}
						scrolledForm.getToolBarManager().add(new Action(getFromEngineBundle("multiChoice.select.all"),
								toolkit.getRegisteredImageDescriptor("IMG_SELECT_ALL")) {
							@Override
							public void run() {
								for (Button checkBoxButton : checkBoxes) {
									checkBoxButton.setSelection(true);
									MultiChoice.this.selection.add((int) checkBoxButton.getData());
								}
								MultiChoice.this.setText();
							}
						});
						scrolledForm.getToolBarManager().add(new Action(getFromEngineBundle("multiChoice.deselect.all"),
								toolkit.getRegisteredImageDescriptor("IMG_DESELECT_ALL")) {
							@Override
							public void run() {
								for (Button checkBoxButton : checkBoxes) {
									checkBoxButton.setSelection(false);
								}
								MultiChoice.this.selection.clear();
								label.setText("");
							}
						});
						scrolledForm.getToolBarManager().add(new Action(getFromEngineBundle("multiChoice.refresh"),
								toolkit.getRegisteredImageDescriptor("IMG_REFRESH")) {
							@Override
							public void run() {
								for (Button checkBoxButton : checkBoxes) {
									checkBoxButton.setSelection(false);
								}
								MultiChoice.this.selection.clear();
								MultiChoice.this.setSelection(new HashSet<>(currentSelection));
								MultiChoice.this.setText();
							}
						});
						ControlContribution separator = new ControlContribution("separator") {
							protected Control createControl(Composite parent) {
								Label label = new Label(parent, SWT.NONE) {
									@Override
									public Point computeSize(int wHint, int hHint, boolean changed) {
										return new Point(10, 5);
									}

									@Override
									protected void checkSubclass() {
									}
								};
								return label;
							}
						};
						scrolledForm.getToolBarManager().add(separator);
						scrolledForm.getToolBarManager().add(new Action(getFromEngineBundle("multiChoice.close.window"),
								toolkit.getRegisteredImageDescriptor("IMG_CLOSE")) {
							@Override
							public void run() {
								checkBoxes.clear();
								close();
							}
						});
						scrolledForm.getToolBarManager().update(true);
					}

					layout();
					getShell().addListener(SWT.Deactivate, new Listener() {
						@Override
						public void handleEvent(Event event) {
							checkBoxes.clear();
							getShell().getDisplay().asyncExec(new Runnable() {
								// Close in an asynchronous process so that action which induces Deactivate event is not affected.
								@Override
								public void run() {
									close();
								}
							});
						}
					});
				}

				@Override
				protected void initializeBounds() {
					super.initializeBounds();
					Point originalLocation = openPopupButton.toDisplay(0, 0);
					Point size = getShell().getSize();
					Point newPoint = new Point(Math.max(originalLocation.x - size.x, 0), Math.max(originalLocation.y - size.y, 0));
					getShell().setLocation(newPoint);
					if (SWT.DEFAULT != popupMaxHeight || SWT.DEFAULT != popupMaxWidth) {
						ControlAdapter controlAdapter = new ControlAdapter() {
							@Override
							public void controlResized(ControlEvent e) {
								scrolledForm.setLayoutData("w 0:0:n, h 0:0:n");
								getShell().removeControlListener(this);
							}
						};
						getShell().addControlListener(controlAdapter);
					}
				}
			};
			formDialog.open();
		}
	}

	/**
	 * Set the text of the label, based on the selected items.
	 */
	private void setText() {
		if (this.checkBoxes == null) {
			this.label.setText("");
			return;
		}

		final List<String> values = new ArrayList<String>();
		for (final Button current : this.checkBoxes) {
			if (!current.isDisposed() && current.getSelection()) {
				values.add(current.getText());
			}
		}

		final StringBuffer sb = new StringBuffer();
		final Iterator<String> it = values.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(",");
			}
		}

		this.label.setText(sb.toString());
		notifyListeners(SWT.Modify, null);
	}

	public void setElements(String[] values) {
		elements = new ArrayList<>(values.length);
		for (String value : values)
			elements.add(value);
	}

	/**
	 * Sets the elements.
	 *
	 * @param elements
	 *            the elements
	 */
	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	/**
	 * Check if the elements attributes is not null.
	 */
	private void checkNullElement() {
		if (this.elements == null) {
			throw new NullPointerException("There is no element associated to this widget");
		}
	}

	/**
	 * Check range.
	 *
	 * @param index
	 *            the index
	 * @throws NullPointerException
	 *             the null pointer exception
	 */
	private void checkRange(final int index) throws NullPointerException {
		checkNullElement();
		if (index < 0 || index >= this.elements.size()) {
			SWT.error(SWT.ERROR_INVALID_RANGE);
		}
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified when the receiver's text is modified, by sending it one
	 * of the messages defined in the <code>ModifyListener</code> interface.
	 * 
	 * @param listener
	 *            the listener
	 * 
	 * @see ModifyListener
	 * @see #removeModifyListener
	 */
	public void addModifyListener(ModifyListener listener) {
		checkWidget();
		if (listener == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		TypedListener typedListener = new TypedListener(listener);
		addListener(SWT.Modify, typedListener);
	}

	/**
	 * Removes the modify listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeModifyListener(ModifyListener listener) {
		checkWidget();
		if (listener == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		removeListener(SWT.Modify, listener);
	}
}
