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
package org.adichatz.engine.tabular;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.controller.collection.ATabularController;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Item;

// TODO: Auto-generated Javadoc
/**
 * This canvas draw navigation bar with {@link GC} like this :
 * 
 * <pre>
 * Previous 1 2 ...10 Next
 * </pre>
 * 
 * @author Yves Drumbonnet
 *
 * 
 *         Based on work of Angelo Zerr and Pascal Leclercq (Nebula Pagination).
 * 
 */
public class NavigationStatusBar extends ABasicTabularStatusBar {
	private static int SEPARATOR = -1;

	/** The ORANGE. */
	private static Color ORANGE = getColor(new RGB(236, 82, 16));

	/** The WHITE. */
	private static Color WHITE = getColor(new RGB(255, 255, 255));

	/** The LIGH t_ blue. */
	private static Color LIGHT_BLUE = getColor(new RGB(222, 239, 247));

	/** The BLUE. */
	private static Color BLUE = getColor(new RGB(148, 148, 231));

	/** The DAR k_ blue. */
	private static Color DARK_BLUE = getColor(new RGB(0, 49, 82));

	/** The GRAY. */
	private static Color GRAY = getColor(new RGB(239, 237, 247));

	/** The LIGH t_ gray. */
	private static Color LIGHT_GRAY = getColor(new RGB(134, 134, 134));

	/** The items. */
	private List<NavigationItem> items;

	/** The selected item. */
	private NavigationItem selectedItem;

	/** The total width. */
	private Integer totalWidth;

	/** The previous item. */
	private NavigationItem previousItem;

	/** The next item. */
	private NavigationItem nextItem;

	private NavigationItem separator;

	/** The current page. */
	private int currentPage;

	private int maximum = 10;

	private Canvas canvas;

	/**
	 * Instantiates a new navigation bar.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public NavigationStatusBar(ATabularController<?> tabularController, String key) {
		super(tabularController, key);
	}

	@Override
	protected void createControl() {
		columnConstraint = "[][grow,fill][][][]";
		canvas = new Canvas(this, SWT.NONE) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.widgets.Composite#computeSize(int, int, boolean)
			 */
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				checkWidget();

				if (wHint == SWT.DEFAULT || hHint == SWT.DEFAULT) {
					computeBoundsIfNeeded(null);
					if (totalWidth != null) {
						return new Point(totalWidth, 16);
					}
					return new Point(wHint, 16);
				}
				return super.computeSize(wHint, hHint, changed);
			}

		};
		// Create previous+next items.
		previousItem = new NavigationItem(canvas, getFromEngineBundle("pagination.previous"), null);
		nextItem = new NavigationItem(canvas, getFromEngineBundle("pagination.next"), null);

		// Add paint listener to draw the navigation page
		canvas.addPaintListener((e) -> {
			onPaint(e.gc);
		});

		// Add mouse listener to select a page item.
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				NavigationItem pageItem = getItem(e.x, e.y);
				if (null != pageItem) {
					// item selected is not the item '...'
					redraw();
					getDisplay().update();
					if (pageItem.equals(nextItem))
						setCurrentPage(currentPage + 1);
					else if (pageItem.equals(previousItem))
						setCurrentPage(currentPage - 1);
					else
						setCurrentPage(pageItem.getIndex());
				}
			}
		});

		canvas.setBackground(backgroundColor);
	}

	public void setReskinBackground() {
		super.setReskinBackground();
		setBackground(backgroundColor);
		canvas.setBackground(backgroundColor);
	}

	/**
	 * On paint.
	 * 
	 * @param gc
	 *            the gc
	 */
	private void onPaint(GC gc) {
		gc.setAdvanced(true);
		if (gc.getAdvanced())
			gc.setTextAntialias(SWT.ON);
		if (items == null) {
			return;
		}
		computeBoundsIfNeeded(gc);

		boolean isSeparator = false;
		int x, y, width, height = 0;
		boolean selected = false;
		boolean enabled = false;

		for (NavigationItem pageItem : items) {
			selected = pageItem.equals(selectedItem);
			enabled = pageItem.isEnabled();
			isSeparator = pageItem == separator;

			x = pageItem.getBounds().x;
			y = pageItem.getBounds().y;
			width = pageItem.getBounds().width;
			height = pageItem.getBounds().height;

			// Fill rectangle
			Color filledRectangleColor = getFilledRectangleColor(selected, !isSeparator ? enabled : true);
			if (filledRectangleColor != null) {
				gc.setBackground(filledRectangleColor);
				gc.fillRoundRectangle(x, y, width, height, 5, 5);
			}

			// Border rectangle
			if (!isSeparator) {
				Color borderRectangleColor = getBorderRectangleColor(selected, enabled, null);
				if (borderRectangleColor != null) {
					gc.setForeground(borderRectangleColor);
					gc.drawRoundRectangle(x, y, width, height, 5, 5);
				}
			}

			// Foreground text
			Color textColor = getTextColor(selected, enabled);
			if (textColor != null) {
				gc.setForeground(textColor);
			} else {
				gc.setForeground(null);
			}
			gc.setFont(tabularController.getBoldFont());
			gc.drawString(pageItem.getText(), x + 3, y, true);
		}
	}

	/**
	 * Gets the filled rectangle color.
	 * 
	 * @param selected
	 *            the selected
	 * @param enabled
	 *            the enabled
	 * @param bg
	 *            the bg
	 * @return the filled rectangle color
	 */
	private Color getFilledRectangleColor(boolean selected, boolean enabled) {
		if (selected) {
			return ORANGE;
		}
		if (!enabled) {
			return GRAY;
		}
		return LIGHT_BLUE;
	}

	/**
	 * Gets the border rectangle color.
	 * 
	 * @param selected
	 *            the selected
	 * @param enabled
	 *            the enabled
	 * @param bg
	 *            the bg
	 * @return the border rectangle color
	 */
	private Color getBorderRectangleColor(boolean selected, boolean enabled, Color bg) {
		if (selected) {
			return ORANGE;
		}
		if (!enabled) {
			return LIGHT_GRAY;
		}
		return BLUE;
	}

	/**
	 * Gets the text color.
	 * 
	 * @param selected
	 *            the selected
	 * @param enabled
	 *            the enabled
	 * @return the text color
	 */
	private Color getTextColor(boolean selected, boolean enabled) {
		if (selected) {
			return WHITE;
		}
		if (!enabled) {
			return LIGHT_GRAY;
		}
		return DARK_BLUE;
	}

	/**
	 * Update.
	 * 
	 * @param pageIndexes
	 *            the page indexes
	 * @param currentPage
	 *            the current page
	 */
	public void update(int currentPage) {
		int[] pageIndexes = getPageIndexes(currentPage);
		// Compute navigation item and update the selected item.
		items = new ArrayList<NavigationItem>(pageIndexes.length + 2);
		int index;
		items.add(previousItem);
		NavigationItem item = null;
		for (int i = 0; i < pageIndexes.length; i++) {
			index = pageIndexes[i] + 1;
			if (pageIndexes[i] == SEPARATOR) {
				item = new NavigationItem(canvas, "...", null);
				item.setEnabled(false);
				separator = item;
			} else {
				item = new NavigationItem(canvas, String.valueOf(index), index);
			}
			items.add(item);
			if (currentPage == index) {
				selectedItem = item;
			}
		}
		items.add(nextItem);
		// bounds must be recomputed.
		totalWidth = null;
		redraw();
	}

	/**
	 * This method loop for item and update for each item their bound.
	 * 
	 * @param gc
	 *            graphic context used to compute the size font used in page item. If GC is null, a new GC is created
	 */
	private void computeBoundsIfNeeded(GC gc) {
		if (items == null || totalWidth != null) {
			return;
		}
		GC tempGC = null;
		if (gc == null) {
			// GC null, create temporary GC
			tempGC = new GC(canvas);
			gc = tempGC;
		}
		totalWidth = 0;
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		String text = null;
		// Loop for each page item.
		for (NavigationItem pageItem : items) {
			text = pageItem.getText();
			// Get the size of the text of the current item.
			Point size = gc.stringExtent(text);

			width = size.x + (null == pageItem.getIndex() ? 12 : 8);
			height = size.y;

			pageItem.setBounds(new Rectangle(x, y, width, height));
			x += width + 5;
		}
		// Updat ethe total width of this control
		totalWidth = x + width - 3;
		if (tempGC != null) {
			// Dispose temporary GC.
			tempGC.dispose();
		}
	}

	/**
	 * Returns the item which match the given location point.Null if none item is selected or if item is disabled.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the item
	 */
	private NavigationItem getItem(int x, int y) {
		checkWidget();

		if (items == null) {
			return null;
		}

		for (NavigationItem pageItem : items) {
			if (pageItem.contains(x, y)) {
				// an item is selected
				if (!pageItem.isEnabled()) {
					// item is not enable, return null.
					return null;
				}
				// return the selected item.
				return pageItem;
			}
		}
		return null;
	}

	/**
	 * Sets the current page.
	 * 
	 * @param currentPage
	 *            the new current page
	 */
	private void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		doit = false;
		tabularController.refresh((currentPage - 1) * getMaxResults(), false);
		doit = true;
		fireWidgetSelection();
	}

	/**
	 * Gets the page indexes.
	 * 
	 * @param currentPageIndex
	 *            the current page index
	 * @return the page indexes
	 */
	public int[] getPageIndexes(int currentPageIndex) {
		maximum = maximum > pageNumber ? pageNumber : maximum;
		int[] indexes = new int[maximum];
		if (pageNumber <= maximum) {
			// ALl indexes must be filled
			for (int i = 0; i < indexes.length; i++) {
				indexes[i] = i;
			}
		} else {
			if (currentPageIndex > (pageNumber - maximum) + 3) {
				int index = pageNumber - 1;
				for (int i = indexes.length - 1; i >= 0; i--) {
					if (i == 0) {
						indexes[i] = i;
					} else if (i == 1) {
						indexes[i] = SEPARATOR;
					} else {
						indexes[i] = index--;
					}
				}
			} else {

				if (maximum - currentPageIndex > 2) {
					for (int i = 0; i < indexes.length; i++) {
						if (i == maximum - 1) {
							indexes[i] = pageNumber - 1;
						} else if (i == maximum - 2) {
							indexes[i] = SEPARATOR;
						} else {
							indexes[i] = i;
						}
					}
				} else {
					// Total page is > to nb max of pages to display
					// Compute list to have for instance 1 ... 10, 11, 12, 13,
					// 14,
					// ...
					// 20
					int middle = maximum / 2;
					int index = currentPageIndex;
					int nbItems = 0;
					for (int i = middle; i > 0 && index > 0; i--) {
						if (i == 1) {
							// before last item, check if it's 2 item
							indexes[i] = index == 2 ? index : SEPARATOR;
						} else if (i == 0) {
							indexes[i] = 0;
						} else if (index > 0) {
							indexes[i] = index--;
						}
						nbItems++;
					}

					index = currentPageIndex;
					for (int i = nbItems; i < maximum; i++) {
						if (i == maximum - 2) {
							indexes[i] = index == pageNumber ? index : SEPARATOR;
						} else if (i == maximum - 1) {
							indexes[i] = pageNumber - 1;
						} else {
							indexes[i] = index++;
						}
					}
				}
			}

		}
		return indexes;
	}

	@Override
	protected int getPageSelection() {
		return currentPage;
	}

	@Override
	public void fireWidgetSelection() {
		if (doit) {
			if (tabularController.getQueryManager().isPaginated()) {
				if (0 == getFirstResult()) // force selection to first step when relaunching query
					currentPage = 1;
				if (currentPage != previousPageSelection) {
					maximum = 10;
					update(currentPage);
					for (NavigationItem item : items)
						if (null != item.getIndex() && item.getIndex().equals(currentPage)) {
							selectedItem = item;
							break;
						}
					previousItem.setEnabled(1 != currentPage);
					nextItem.setEnabled(pageNumber != currentPage);

					canvas.redraw();
					setPageLabel();
					previousSelectionIndex = -1;
					previousPageSelection = currentPage;
				}
			} else {
				pageNumber = 1;
				canvas.setVisible(false);
				setPageLabel();
			}
			layout();
		}
	}

	@Override
	public int getHeight() {
		return 20;
	}

	/**
	 * Create or get instance of SWT {@link Color} from the given {@link RGB}.
	 * 
	 * @param rgb
	 *            the rgb
	 * @return the color
	 */
	public static Color getColor(RGB rgb) {
		String key = rgb.toString();
		Color color = JFaceResources.getColorRegistry().get(key);
		if (color == null) {
			JFaceResources.getColorRegistry().put(key, rgb);
		}
		return JFaceResources.getColorRegistry().get(key);
	}

	class NavigationItem extends Item {

		/** The index. */
		private final Integer index;

		/** The bounds. */
		private Rectangle bounds;

		/** The enabled. */
		private boolean enabled = true;

		/**
		 * Instantiates a new navigation bar item.
		 * 
		 * @param parent
		 *            the parent
		 * @param index
		 *            the index
		 */
		public NavigationItem(Canvas parent, String text, Integer index) {
			super(parent, SWT.NONE);
			this.index = index;
			setText(text);
		}

		/**
		 * Returns the index of the page item.
		 * 
		 * @return the index
		 */
		public Integer getIndex() {
			return index;
		}

		/**
		 * Set bounds of the item.
		 * 
		 * @param bounds
		 *            the new bounds
		 */
		public void setBounds(Rectangle bounds) {
			this.bounds = bounds;
		}

		/**
		 * Returns <code>true</code> if the point specified by the arguments is inside the area specified by the receiver, and
		 * <code>false</code> otherwise.
		 * 
		 * @param x
		 *            the x coordinate of the point to test for containment
		 * @param y
		 *            the y coordinate of the point to test for containment
		 * @return <code>true</code> if the rectangle contains the point and <code>false</code> otherwise
		 */
		public boolean contains(int x, int y) {
			if (bounds == null) {
				return false;
			}
			return bounds.contains(x, y);
		}

		/**
		 * Returns the bounds for the item. It can be null if bounds was not computed.
		 * 
		 * @return the bounds
		 */
		public Rectangle getBounds() {
			return bounds;
		}

		/**
		 * Set enabled of the item.o
		 * 
		 * @param enabled
		 *            the new enabled
		 */
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		/**
		 * Returns the enabled of the item.
		 * 
		 * @return true, if is enabled
		 */
		public boolean isEnabled() {
			return enabled;
		}
	}
}
