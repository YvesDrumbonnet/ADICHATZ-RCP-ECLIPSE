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
package org.adichatz.engine.controller.nebula;

import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.extra.ColumnViewerSorter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class TextController.
 * 
 * @param <T>
 *            the
 * @author Yves Drumbonnet
 * 
 */
public abstract class GridColumnController<T> extends AColumnController<T> {

	/** The control. */
	private GridViewerColumn gridViewerColumn;

	private GridColumn gridColumn;

	private GridColumnGroupController gridColumnGroupController;

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
	public GridColumnController(String id, ATabularController<T> tableController) {
		super(id, tableController);
	}

	@SuppressWarnings("unchecked")
	public GridColumnController(String id, GridColumnGroupController gridColumnGroupController) {
		super(id, (ATabularController<T>) gridColumnGroupController.getParentController());
		this.gridColumnGroupController = gridColumnGroupController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AComponentController#createControl()
	 */
	@SuppressWarnings("unchecked")
	public void createControl() {
		if (null != gridColumnGroupController)
			gridColumn = new GridColumn(gridColumnGroupController.getControl(), style);
		else
			gridColumn = new GridColumn((Grid) tabularController.getControl(), style);
		gridColumn.setSummary(false);

		gridViewerColumn = new GridViewerColumn((GridTableViewer) tabularController.getViewer(), gridColumn);
		gridColumn = gridViewerColumn.getColumn();
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
		gridViewerColumn.setLabelProvider(labelProvider);
		gridColumn.setMoveable(true);
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
		gridColumn.addControlListener(controlListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public GridViewerColumn getControl() {
		return gridViewerColumn;
	}

	/**
	 * Sets the column viewer sorter.
	 * 
	 * @param direction
	 *            the new column viewer sorter
	 */
	public void setColumnViewerSorter(int direction) {
		columnViewerSorter = new ColumnViewerSorter<T>(this, direction);
		gridColumn.addSelectionListener(getSortColumnListener());
	}

	@Override
	public boolean isHideable() {
		return !gridColumn.isSummary();
	}

	/**
	 * Gets the text.
	 * 
	 * @param element
	 *            the element
	 * 
	 * @return the text
	 */
	public abstract Object getValue(T element);

	@Override
	public boolean isResizable() {
		return gridColumn.getResizeable();
	}

	public void resize() {
		if (isValid() && !getControl().getColumn().isDisposed() && !resized) {
			resizing = true;
			if (null == getWidth()) {
				gridColumn.pack();
			} else {
				gridColumn.setWidth(getWidth());
				gridColumn.setResizeable(0 != getWidth());
			}
			resizing = false;
		}

	}

	public void sort(int direction) {
		ColumnViewer columnViewer = tabularController.getViewer();
		if (!gridColumn.isDisposed()) {
			if (direction == ColumnViewerSorter.NONE) {
				gridColumn.setSort(SWT.NONE);
				columnViewer.setComparator(null);
			} else {
				for (GridColumn column : gridColumn.getParent().getColumns())
					if (!column.equals(gridColumn) && SWT.NONE != column.getSort())
						column.setSort(SWT.NONE);
				columnViewerSorter.setDirection(direction);
				if (direction == ColumnViewerSorter.ASC) {
					gridColumn.setSort(SWT.DOWN);
				} else {
					gridColumn.setSort(SWT.UP);
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
		return gridColumn.getWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AColumnController#setColumnWidth()
	 */
	@Override
	public void setColumnWidth(int width) {
		gridColumn.setWidth(width);
	}

	@Override
	public void pack() {
		gridColumn.pack();
	}

	@Override
	public String getText() {
		return gridColumn.getText();
	}

	@Override
	public Object getData(String key) {
		return gridColumn.getData(key);
	}

	@Override
	public void setData(String key, Object value) {
		gridColumn.setData(key, value);

	}

	@Override
	public void setResizable(boolean resizable) {
		gridColumn.setResizeable(resizable);
	}

}
