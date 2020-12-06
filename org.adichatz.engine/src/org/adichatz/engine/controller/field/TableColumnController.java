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
package org.adichatz.engine.controller.field;

import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.extra.ColumnViewerSorter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TableColumn;

// TODO: Auto-generated Javadoc
/**
 * The Class TextController.
 * 
 * @param <T>
 *            the
 * @author Yves Drumbonnet
 * 
 */
public abstract class TableColumnController<T> extends AColumnController<T> {

	private TableViewerColumn tableViewerColumn;

	private TableColumn tableColumn;

	/**
	 * Instantiates a new table column controller.
	 * 
	 * @param id
	 *            the id
	 * @param columnIndex
	 *            the column index
	 * @param tableController
	 *            the table controller
	 */
	public TableColumnController(String id, ATabularController<T> tableController) {
		super(id, tableController);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#createControl()
	 */
	@SuppressWarnings("unchecked")
	public void createControl() {
		super.createControl();
		tableViewerColumn = new TableViewerColumn((TableViewer) tabularController.getViewer(), getStyle());
		tableColumn = tableViewerColumn.getColumn();
		labelProvider = new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return getColumnText((T) element);
			}

			@Override
			public Image getImage(Object element) {
				return getColumnImage((T) element);
			}

			@Override
			public Color getBackground(Object element) {
				return getColumnBackground((T) element);
			}

			@Override
			public Color getForeground(Object element) {
				return getColumnForeground((T) element);
			}

			@Override
			public Font getFont(Object element) {
				return getColumnFont((T) element);
			}

		};
		tableViewerColumn.setLabelProvider(labelProvider);
		tableColumn.setMoveable(true);
		ControlListener controlListener = new ControlListener() {
			@Override
			public void controlResized(ControlEvent e) {
				if (!resizing)
					resized = true;
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		};
		tableColumn.addControlListener(controlListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public TableViewerColumn getControl() {
		return tableViewerColumn;
	}

	/**
	 * Sets the column viewer sorter.
	 * 
	 * @param direction
	 *            the new column viewer sorter
	 */
	public void setColumnViewerSorter(int direction) {
		columnViewerSorter = new ColumnViewerSorter<T>(this, direction);
		tableColumn.addSelectionListener(getSortColumnListener());
	}

	@Override
	public boolean isResizable() {
		return tableColumn.getResizable();
	}

	public void resize() {
		if (isValid() && !getControl().getColumn().isDisposed()) {
			resizing = true;
			if (null == getWidth()) {
				tableColumn.pack();
			} else {
				tableColumn.setWidth(getWidth());
				tableColumn.setResizable(0 != getWidth());
			}
			resizing = false;
			setResized(false);
		}

	}

	public void sort(int direction) {
		ColumnViewer columnViewer = tabularController.getViewer();
		if (!tableColumn.isDisposed()) {
			if (direction == ColumnViewerSorter.NONE) {
				tableColumn.getParent().setSortColumn(null);
				tableColumn.getParent().setSortDirection(SWT.NONE);
				columnViewer.setComparator(null);
			} else {
				tableColumn.getParent().setSortColumn(tableColumn);
				columnViewerSorter.setDirection(direction);
				if (direction == ColumnViewerSorter.ASC) {
					tableColumn.getParent().setSortDirection(SWT.DOWN);
				} else {
					tableColumn.getParent().setSortDirection(SWT.UP);
				}

				if (columnViewerSorter.equals(columnViewer.getComparator()))
					columnViewer.refresh();
				else
					columnViewer.setComparator(columnViewerSorter);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AColumnController#getColumnWidth()
	 */
	@Override
	public int getColumnWidth() {
		return tableColumn.getWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AColumnController#setColumnWidth()
	 */
	@Override
	public void setColumnWidth(int width) {
		tableColumn.setWidth(width);
	}

	@Override
	public void pack() {
		tableColumn.pack();
	}

	@Override
	public String getText() {
		return tableColumn.getText();
	}

	@Override
	public Object getData(String key) {
		return tableColumn.getData(key);
	}

	@Override
	public void setData(String key, Object value) {
		tableColumn.setData(key, value);

	}

	@Override
	public void setResizable(boolean resizable) {
		tableColumn.setResizable(resizable);
	}
}
