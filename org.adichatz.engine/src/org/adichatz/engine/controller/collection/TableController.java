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
package org.adichatz.engine.controller.collection;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.LimitedComposite;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

// TODO: Auto-generated Javadoc
/**
 * The Class TableController.
 * 
 * @param <T>
 *            the
 * @author Yves Drumbonnet
 * 
 */
public class TableController<T> extends ATabularController<T> {

	/** The table. */
	protected Table table;

	/**
	 * Instantiates a new table controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 * @param pluginResources
	 *            the plugin resources
	 */
	public TableController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;
	}

	/**
	 * Creates the control.
	 */
	@Override
	public void internalCreateControl() {
		composite = new LimitedComposite(parentController.getComposite(), containerStyle);
		// do not use 'FormToolkit().createTable(composite, style)' because TableRendererManager process is overriden
		table = new Table(composite, style);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		viewer = new TableViewer(table);
		table.setLayoutData("h 0:n:n, w 0:n:n");
	}

	/**
	 * Gets the table.
	 * 
	 * @return the table
	 */
	@Override
	public Table getControl() {
		return table;
	}

	@Override
	public Item getSelectedItem() {
		if (null == table.getSelection())
			return null;
		return table.getSelection()[0];
	}

	@Override
	public void processRightClickMouseEvent(Point point) {
		rightClickColumnController = null;
		AColumnController<T>[] columnControllers = getColumnControllers(true);
		ScrollBar hScroll = table.getHorizontalBar();
		int offs = hScroll != null ? hScroll.getSelection() : 0;
		Rectangle area = table.getClientArea();
		int x0 = area.x + offs;
		final int xp = point.x + offs;
		if (xp >= x0) {
			int x = 0;
			int[] columnOrder = table.getColumnOrder();
			for (int column : columnOrder) {
				AColumnController<T> columnController = columnControllers[column];
				if (null != columnController && null != columnController.getControl()) { // Hidden and inactive column
					int xNext = x + (((TableViewerColumn) columnController.getControl())).getColumn().getWidth();
					if (xp >= x && xp < xNext) {
						rightClickColumnController = columnController;
					}
					x = xNext;
				}
			}
		}
	}

	@Override
	public Object invokeControlMethod(METHOD_NAME methodName, Object... params) {
		switch (methodName) {
		case getColumnCount:
			return table.getColumnCount();
		case getColumnOrder:
			return table.getColumnOrder();
		case getHeaderHeight:
			return table.getHeaderHeight();
		case setColumnOrder:
			int[] columnOrder = (int[]) params[0];
			if (null != columnOrder)
				table.setColumnOrder(columnOrder);
			return null;
		case getItems:
			return table.getItems();
		case getItemCount:
			return table.getItemCount();
		case getSelectionIndex:
			return table.getSelectionIndex();
		case setTopIndex:
			table.setTopIndex(table.getSelectionIndex());
			return null;
		case setRedraw:
			table.setRedraw((boolean) params[0]);
			return null;
		case addSelectionListener:
			table.addSelectionListener((SelectionListener) params[0]);
			return null;
		case copyToClipBoard:
			AdiFormToolkit tookit = AdichatzApplication.getInstance().getFormToolkit();
			Image checkedImage = tookit.getRegisteredImage("IMG_CHECKED");
			Image uncheckedImage = tookit.getRegisteredImage("IMG_UNCHECKED");

			// Define a writer
			StringWriter buf = new StringWriter();
			PrintWriter writer = new PrintWriter(buf);

			// writes the header
			writer.print(getFromEngineBundle("lineNumber"));
			for (int columnIndex : table.getColumnOrder()) {
				TableColumn tableColumn = table.getColumns()[columnIndex];
				if (0 < tableColumn.getWidth())
					writer.print("\t" + table.getColumns()[columnIndex].getText());
			}

			int lineNumber = 1;
			// writes the rows
			for (int rowIndex = 0; rowIndex < table.getItems().length; rowIndex++) {
				writer.println();
				writer.print(lineNumber++);
				for (int columnIndex : table.getColumnOrder()) {
					if (0 < table.getColumns()[columnIndex].getWidth()) {
						String text = table.getItems()[rowIndex].getText(columnIndex);
						if ("".equals(text)) {
							Image image = table.getItems()[rowIndex].getImage(columnIndex);
							if (null != image)
								if (image.equals(checkedImage))
									text = "true";
								else if (image.equals(uncheckedImage))
									text = "false";
						}
						writer.print("\t" + text);
					}
				}
			}

			writer.close();

			Clipboard clipboard = new Clipboard(composite.getDisplay());
			Object[] o = new Object[] { buf.toString() };
			Transfer[] t = new Transfer[] { TextTransfer.getInstance() };
			clipboard.setContents(o, t);
			clipboard.dispose();
			return null;
		default:
			throw new RuntimeException("Method " + methodName.name() + " is not implemented in Tabular controller!");
		}
	}
}
