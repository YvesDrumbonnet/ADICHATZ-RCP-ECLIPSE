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
package org.adichatz.engine.controller.field;

import java.lang.reflect.Array;
import java.text.Format;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.extra.ColumnViewerSorter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class AColumnController.
 * 
 * @param <T>
 *            the
 * @author Yves Drumbonnet
 *
 */
public abstract class AColumnController<T> extends AWidgetController implements IController {
	/** The column viewer sorter. */
	protected ColumnViewerSorter<T> columnViewerSorter;

	/** The table controller. */
	protected ATabularController<T> tabularController;

	/** The width. */
	private Integer width;

	/** The resizing. */
	protected boolean resizing;

	/** The resized. */
	protected boolean resized = false;

	protected Format format;

	protected ColumnLabelProvider labelProvider;

	/**
	 * Instantiates a new table column controller.
	 * 
	 * @param id
	 *            the id
	 * @param columnIndex
	 *            the column index
	 * @param tabularController
	 *            the table controller
	 */
	public AColumnController(String id, ATabularController<T> tabularController) {
		super(id, tabularController, tabularController.getGenCode());
		style = SWT.LEFT;
		this.tabularController = tabularController;
	}

	/**
	 * Gets the column viewer sorter.
	 * 
	 * @return the column viewer sorter
	 */
	public ColumnViewerSorter<T> getColumnViewerSorter() {
		return columnViewerSorter;
	}

	/**
	 * Sets the column viewer sorter.
	 * 
	 * @param direction
	 *            the new column viewer sorter
	 */
	public abstract void setColumnViewerSorter(int direction);

	protected SelectionAdapter getSortColumnListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ViewerComparator viewerComparator = tabularController.getViewer().getComparator();
				if (viewerComparator != null && viewerComparator == columnViewerSorter) {
					int tdirection = columnViewerSorter.getDirection();
					if (tdirection == ColumnViewerSorter.ASC) {
						sort(ColumnViewerSorter.DESC);
					} else if (tdirection == ColumnViewerSorter.DESC) {
						sort(ColumnViewerSorter.NONE);
					} else {
						sort(ColumnViewerSorter.ASC);
					}
				} else
					sort(ColumnViewerSorter.ASC);
				if (null != tabularController.getTableRenderer())
					tabularController.getTableRenderer().postRefreshRendering();
			}
		};
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

	/**
	 * Compare.
	 * 
	 * @param e1
	 *            the e1
	 * @param e2
	 *            the e2
	 * @return the int
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(T e1, T e2) {
		Comparable value1 = (Comparable) getValue(e1);
		Comparable value2 = (Comparable) getValue(e2);
		if (null != value1 && null != value2)
			return (value1).compareTo(value2);
		return null == value1 ? (null != value2 ? -1 : 0) : 1;
	}

	/**
	 * Gets the column provider text.
	 * 
	 * @param element
	 *            the element
	 * 
	 * @return the text
	 */
	public abstract String getColumnText(T element);

	/**
	 * Gets the image.
	 * 
	 * @param element
	 *            the element
	 * 
	 * @return the image
	 */
	public Image getColumnImage(T element) {
		return null;
	}

	/**
	 * Gets the background.
	 * 
	 * @param element
	 *            the element
	 * 
	 * @return the background
	 */
	public Color getColumnBackground(T element) {
		return tabularController.getTableRenderer().getBackground(element);
	}

	/**
	 * Gets the foreground.
	 * 
	 * @param element
	 *            the element
	 * 
	 * @return the foreground
	 */
	public Color getColumnForeground(T element) {
		return tabularController.getTableRenderer().getForeground(element);
	}

	/**
	 * Gets the font.
	 * 
	 * @param element
	 *            the element
	 * @return the font
	 */
	public Font getColumnFont(T element) {
		return tabularController.getTableRenderer().getFont(element);
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 * 
	 * @param width
	 *            the new width
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * Sets the resizing.
	 * 
	 * @param resizing
	 *            the new resizing
	 */
	public void setResizing(boolean resizing) {
		this.resizing = resizing;
	}

	/**
	 * Checks if is resized.
	 * 
	 * @return true, if is resized
	 */
	public boolean isResized() {
		return resized;
	}

	/**
	 * Sets the resized.
	 * 
	 * @param resized
	 *            the new resized
	 */
	public void setResized(boolean resized) {
		this.resized = resized;
	}

	public abstract boolean isResizable();

	public abstract void resize();

	public void sort() {
		sort(columnViewerSorter.getDirection());
	}

	public boolean isHideable() {
		return true;
	}

	public Format getFormat() {
		return format;
	}

	public String getColumnId() {
		return getRegisterId().substring(getRegisterId().lastIndexOf(':') + 1);
	}

	public void hide() {
		setData(EngineConstants.RESTORED_WIDTH, null == width ? SWT.DEFAULT : width);
		setResizable(false);
		setColumnWidth(0);
		width = 0;
	}

	public void reinitSize() {
		Integer width = (Integer) getData(EngineConstants.RESTORED_WIDTH);
		if (null != width) { // No setData / Hide before
			if (SWT.DEFAULT == width) {
				setWidth(null);
			} else {
				setWidth(width);
			}
			setColumnWidth(width);
			setResizable(0 != width);
		}
	}

	public String getArrayColumnText(Object array) {
		if (null == array)
			return "";
		int len = Array.getLength(array);
		switch (len) {
		case 0:
			return "";
		case 1:
			return (null == format) ? String.valueOf(Array.get(array, 0)) : format.format(Array.get(array, 0));
		default:
			String value = (null == format) ? String.valueOf(Array.get(array, 0)) : format.format(Array.get(array, 0));
			return "[".concat(value).concat(", ...] (").concat(String.valueOf(len)).concat(")");
		}
	}

	public ColumnLabelProvider getLabelProvider() {
		return labelProvider;
	}

	/**
	 * Gets the parent controller.
	 * 
	 * @return the parent controller
	 */
	@SuppressWarnings("unchecked")
	public ATabularController<T> getParentController() {
		return (ATabularController<T>) parentController;
	}

	public abstract void sort(int direction);

	public abstract int getColumnWidth();

	public abstract void setColumnWidth(int width);

	public abstract void pack();

	public abstract String getText();

	public abstract Object getData(String key);

	public abstract void setData(String key, Object value);

	public abstract void setResizable(boolean resizable);

}
