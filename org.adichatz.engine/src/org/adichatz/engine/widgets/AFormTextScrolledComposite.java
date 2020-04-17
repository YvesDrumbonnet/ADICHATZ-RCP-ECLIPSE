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
package org.adichatz.engine.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.SizeCache;
import org.eclipse.ui.internal.forms.widgets.FormUtil;

// TODO: Auto-generated Javadoc
/**
 * This class is used to provide common scrolling services to a number of controls in the toolkit. Classes that extend it are not
 * required to implement any method.
 * 
 * @since 3.0
 */
@SuppressWarnings("restriction")
public abstract class AFormTextScrolledComposite extends Composite {

	/** The Constant V_SCROLL_INCREMENT. */
	private static final int V_SCROLL_INCREMENT = 64;

	/** The ignore layouts. */
	private boolean ignoreLayouts = true;

	/** The ignore resizes. */
	private boolean ignoreResizes = false;

	/** The content cache. */
	private SizeCache contentCache = new SizeCache();

	/** The content min height. */
	protected int contentMinHeight = -1;

	/** The content. */
	Control content;

	/** The content listener. */
	Listener contentListener;

	/** The filter. */
	Listener filter;

	/** The min height. */
	int minHeight = 0;

	/** The min width. */
	int minWidth = 0;

	/** The show focused control. */
	boolean showFocusedControl = false;

	/**
	 * Instantiates a new a form text scrolled composite.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public AFormTextScrolledComposite(Composite parent, int style) {
		super(parent, checkStyle(style));
		super.setLayout(new AdiScrolledCompositeLayout());
		ScrollBar vBar = getVerticalBar();
		if (vBar != null) {
			vBar.setVisible(false);
			vBar.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					vScroll();
				}
			});
		}

		contentListener = new Listener() {
			public void handleEvent(Event e) {
				if (e.type != SWT.Resize)
					return;
				layout(false);
			}
		};

		filter = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget instanceof Control) {
					Control control = (Control) event.widget;
					if (contains(control))
						showControl(control);
				}
			}
		};

		addDisposeListener((e) -> {
			getDisplay().removeFilter(SWT.FocusIn, filter);
		});
		addListener(SWT.Resize, (e) -> {
			if (!ignoreResizes) {
				reflow(false);
			}
		});
		initializeScrollBars();
	}

	/**
	 * Check style.
	 * 
	 * @param style
	 *            the style
	 * @return the int
	 */
	static int checkStyle(int style) {
		int mask = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT;
		return style & mask;
	}

	/**
	 * Contains.
	 * 
	 * @param control
	 *            the control
	 * @return true, if successful
	 */
	boolean contains(Control control) {
		if (control == null || control.isDisposed())
			return false;

		Composite parent = control.getParent();
		while (parent != null && !(parent instanceof Shell)) {
			if (this == parent)
				return true;
			parent = parent.getParent();
		}
		return false;
	}

	/**
	 * Returns the minimum width of the content control.
	 * 
	 * @return the minimum width
	 * @since 3.2
	 */
	public int getMinWidth() {
		checkWidget();
		return minWidth;
	}

	/**
	 * Returns the minimum height of the content control.
	 * 
	 * @return the minimum height
	 * @since 3.2
	 */
	public int getMinHeight() {
		checkWidget();
		return minHeight;
	}

	/**
	 * Get the content that is being scrolled.
	 * 
	 * @return the control displayed in the content area
	 */
	public Control getContent() {
		// checkWidget();
		return content;
	}

	/**
	 * Returns <code>true</code> if the receiver automatically scrolls to a focused child control to make it visible. Otherwise,
	 * returns <code>false</code>.
	 * 
	 * @return a boolean indicating whether focused child controls are automatically scrolled into the viewport
	 * @since 3.4
	 */
	public boolean getShowFocusedControl() {
		checkWidget();
		return showFocusedControl;
	}

	/**
	 * Return the point in the content that currently appears in the top left corner of the scrolled composite.
	 * 
	 * @return the point in the content that currently appears in the top left corner of the scrolled composite. If no content has
	 *         been set, this returns (0, 0).
	 * @since 2.0
	 */
	public Point getOrigin() {
		checkWidget();
		if (content == null)
			return new Point(0, 0);
		Point location = content.getLocation();
		return new Point(-location.x, -location.y);
	}

	/**
	 * Scrolls the content so that the specified point in the content is in the top left corner. If no content has been set, nothing
	 * will occur.
	 * 
	 * Negative values will be ignored. Values greater than the maximum scroll distance will result in scrolling to the end of the
	 * scrollbar.
	 * 
	 * @param origin
	 *            the point on the content to appear in the top left corner
	 * @since 2.0
	 */
	public void setOrigin(Point origin) {
		setOrigin(origin.x, origin.y);
	}

	/**
	 * Scrolls the content so that the specified point in the content is in the top left corner. If no content has been set, nothing
	 * will occur.
	 * 
	 * Negative values will be ignored. Values greater than the maximum scroll distance will result in scrolling to the end of the
	 * scrollbar.
	 * 
	 * @param x
	 *            the x coordinate of the content to appear in the top left corner
	 * @param y
	 *            the y coordinate of the content to appear in the top left corner
	 * @since 2.0
	 */
	public void setOrigin(int x, int y) {
		checkWidget();
		if (content == null)
			return;
		ScrollBar vBar = getVerticalBar();
		if (vBar != null) {
			vBar.setSelection(y);
			y = -vBar.getSelection();
		} else {
			y = 0;
		}
		content.setLocation(x, y);
	}

	/**
	 * Set the content that will be scrolled.
	 * 
	 * @param content
	 *            the control to be displayed in the content area
	 */
	public void setContent(Control content) {
		checkWidget();
		if (this.content != null && !this.content.isDisposed()) {
			this.content.removeListener(SWT.Resize, contentListener);
			this.content.setBounds(new Rectangle(-200, -200, 0, 0));
		}

		this.content = content;
		ScrollBar vBar = getVerticalBar();
		if (this.content != null) {
			content.setForeground(getForeground());
			content.setBackground(getBackground());
			content.setFont(getFont());
			if (vBar != null) {
				vBar.setMaximum(0);
				vBar.setThumb(0);
				vBar.setSelection(0);
			}
			content.setLocation(0, 0);
			layout(false);
			this.content.addListener(SWT.Resize, contentListener);
		}
	}

	/**
	 * Sets the layout which is associated with the receiver to be the argument which may be null.
	 * <p>
	 * Note: No Layout can be set on this Control because it already manages the size and position of its children.
	 * </p>
	 * 
	 * @param layout
	 *            the receiver's new layout or null
	 */
	public void setLayout(Layout layout) {
		checkWidget();
		return;
	}

	/**
	 * Specify the minimum width and height at which the ScrolledComposite will begin scrolling the content with the horizontal
	 * scroll bar. This value is only relevant if setExpandHorizontal(true) and setExpandVertical(true) have been set.
	 * 
	 * @param width
	 *            the minimum width or 0 for default width
	 * @param height
	 *            the minimum height or 0 for default height
	 */
	public void setMinSize(int width, int height) {
		checkWidget();
		if (width == minWidth && height == minHeight)
			return;
		minWidth = Math.max(0, width);
		minHeight = Math.max(0, height);
		layout(false);
	}

	/**
	 * Configure the receiver to automatically scroll to a focused child control to make it visible.
	 * 
	 * If show is <code>false</code>, show a focused control is off. By default, show a focused control is off.
	 * 
	 * @param show
	 *            <code>true</code> to show a focused control.
	 * @since 3.4
	 */
	public void setShowFocusedControl(boolean show) {
		checkWidget();
		if (showFocusedControl == show)
			return;
		Display display = getDisplay();
		display.removeFilter(SWT.FocusIn, filter);
		showFocusedControl = show;
		if (!showFocusedControl)
			return;
		display.addFilter(SWT.FocusIn, filter);
		Control control = display.getFocusControl();
		if (contains(control))
			showControl(control);
	}

	/**
	 * Scrolls the content of the receiver so that the control is visible.
	 * 
	 * @param control
	 *            the control to be shown
	 * @since 3.4
	 */
	public void showControl(Control control) {
		checkWidget();
		if (control == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		if (control.isDisposed())
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		if (!contains(control))
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);

		Rectangle itemRect = getDisplay().map(control.getParent(), this, control.getBounds());
		Rectangle area = getClientArea();
		Point origin = getOrigin();
		if (itemRect.x < 0) {
			origin.x = Math.max(0, origin.x + itemRect.x);
		} else {
			if (area.width < itemRect.x + itemRect.width)
				origin.x = Math.max(0, origin.x + itemRect.x + Math.min(itemRect.width, area.width) - area.width);
		}
		if (itemRect.y < 0) {
			origin.y = Math.max(0, origin.y + itemRect.y);
		} else {
			if (area.height < itemRect.y + itemRect.height)
				origin.y = Math.max(0, origin.y + itemRect.y + Math.min(itemRect.height, area.height) - area.height);
		}
		setOrigin(origin);
	}

	/**
	 * V scroll.
	 */
	void vScroll() {
		if (content == null)
			return;
		Point location = content.getLocation();
		ScrollBar vBar = getVerticalBar();
		int vSelection = vBar.getSelection();
		content.setLocation(location.x, -vSelection);
	}

	/**
	 * Sets the foreground of the control and its content.
	 * 
	 * @param fg
	 *            the new foreground color
	 */
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (getContent() != null)
			getContent().setForeground(fg);
	}

	/**
	 * Sets the background of the control and its content.
	 * 
	 * @param bg
	 *            the new background color
	 */
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (getContent() != null)
			getContent().setBackground(bg);
	}

	/**
	 * Sets the font of the form. This font will be used to render the title text. It will not affect the body.
	 * 
	 * @param font
	 *            the new font
	 */
	public void setFont(Font font) {
		super.setFont(font);
		if (getContent() != null)
			getContent().setFont(font);
	}

	/**
	 * If content is set, transfers focus to the content.
	 * 
	 * @return true, if successful
	 */
	public boolean setFocus() {
		boolean result;
		FormUtil.setFocusScrollingEnabled(this, false);
		if (getContent() != null)
			result = getContent().setFocus();
		else
			result = super.setFocus();
		FormUtil.setFocusScrollingEnabled(this, true);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Composite#layout(boolean)
	 */
	public void layout(boolean changed) {
		if (ignoreLayouts) {
			return;
		}

		ignoreLayouts = true;
		ignoreResizes = true;
		super.layout(changed);
		ignoreResizes = false;
	}

	/**
	 * Recomputes the body layout and the scroll bars. The method should be used when changes somewhere in the form body invalidate
	 * the current layout and/or scroll bars.
	 * 
	 * @param flushCache
	 *            if <code>true</code>, drop the cached data
	 */
	public void reflow(boolean flushCache) {
		Composite c = (Composite) getContent();
		Rectangle clientArea = getClientArea();
		if (c == null)
			return;
		if (clientArea.width == getSize().x) {
			ScrollBar bar = getVerticalBar();
			if (bar != null) {
				clientArea.width -= bar.getSize().x;
			}
		}

		contentCache.setControl(c);
		if (flushCache) {
			contentCache.flush();
		}

		int width = ((c.getStyle() & SWT.WRAP) != 0) ? clientArea.width : SWT.DEFAULT;
		Point newSize = contentCache.computeSize(width, FormUtil.getHeightHint(clientArea.height, c));

		setMinSize(newSize.x, newSize.y);
		updatePageIncrement();

		// reduce vertical scroll increment if necessary
		ScrollBar vbar = getVerticalBar();
		if (vbar != null) {
			if (getClientArea().height - 5 < V_SCROLL_INCREMENT)
				getVerticalBar().setIncrement(getClientArea().height - 5);
			else
				getVerticalBar().setIncrement(V_SCROLL_INCREMENT);
		}

		ignoreLayouts = false;
		layout(flushCache);
		ignoreLayouts = true;

		contentCache.layoutIfNecessary();
	}

	/**
	 * Initialize scroll bars.
	 */
	private void initializeScrollBars() {
		ScrollBar vbar = getVerticalBar();
		if (vbar != null) {
			vbar.setIncrement(V_SCROLL_INCREMENT);
		}
		updatePageIncrement();
	}

	/**
	 * Update page increment.
	 */
	private void updatePageIncrement() {
		ScrollBar vBar = getVerticalBar();
		if (vBar != null) {
			Rectangle clientArea = getClientArea();
			int increment = clientArea.height - 5;
			vBar.setPageIncrement(increment);
		}
	}

	/**
	 * This class provides the layout for ScrolledComposite.
	 * 
	 */
	class AdiScrolledCompositeLayout extends Layout {

		/** The in layout. */
		boolean inLayout = false;

		/** The Constant DEFAULT_WIDTH. */
		static final int DEFAULT_WIDTH = 64;

		/** The Constant DEFAULT_HEIGHT. */
		static final int DEFAULT_HEIGHT = 64;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite, int, int, boolean)
		 */
		protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
			AFormTextScrolledComposite sc = (AFormTextScrolledComposite) composite;
			Point size = new Point(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			if (sc.getContent() != null) {
				Point preferredSize = sc.getContent().computeSize(wHint, hHint, flushCache);
				size.x = preferredSize.x;
				size.y = preferredSize.y;
			}
			size.x = Math.max(size.x, sc.getMinWidth());
			size.y = Math.max(size.y, sc.getMinHeight());
			if (wHint != SWT.DEFAULT)
				size.x = wHint;
			if (hHint != SWT.DEFAULT)
				size.y = hHint;
			return size;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.widgets.Layout#flushCache(org.eclipse.swt.widgets.Control)
		 */
		protected boolean flushCache(Control control) {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite, boolean)
		 */
		protected void layout(Composite composite, boolean flushCache) {
			if (inLayout)
				return;
			AFormTextScrolledComposite sc = (AFormTextScrolledComposite) composite;
			if (sc.getContent() == null)
				return;
			ScrollBar vBar = sc.getVerticalBar();
			if (vBar != null) {
				if (vBar.getSize().x >= sc.getSize().x) {
					return;
				}
			}
			inLayout = true;
			Rectangle contentRect = sc.getBounds();
			if (vBar != null)
				vBar.setVisible(contentMinHeight > contentRect.height);
			Rectangle hostRect = sc.getClientArea();
			hostRect.width = Math.max(sc.getMinWidth(), hostRect.width);
			hostRect.height = Math.max(sc.getMinHeight(), hostRect.height);
			hostRect.height = Math.max(contentMinHeight, hostRect.height);

			if (vBar != null) {
				vBar.setMaximum(hostRect.height);
				vBar.setThumb(Math.min(contentRect.height, hostRect.height));
				int vPage = hostRect.height - hostRect.height;
				int vSelection = vBar.getSelection();
				if (vSelection >= vPage) {
					if (vPage <= 0) {
						vSelection = 0;
						vBar.setSelection(0);
					}
					hostRect.y = -vSelection;
				}
			}

			sc.getContent().setBounds(hostRect);
			inLayout = false;
		}

	}

}
