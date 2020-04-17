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
import static org.adichatz.engine.common.LogBroker.logError;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AControlController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.nebula.widgets.formattedtext.FloatFormatter;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * Instances of this class provide a rating element.
 * 
 * Based on org.mihalis.opal.multiChoice (see https://github.com/lcaron/opal)
 *
 * @author Yves Drumbonnet
 */
public class StarRating extends Canvas {

	/** The Constant SIZE_SMALL. */
	private static final int SIZE_SMALL = 12;

	/** The Constant SIZE_MEDIUM. */
	private static final int SIZE_MEDIUM = 16;

	/** The Constant SIZE_BIG. */
	private static final int SIZE_BIG = 32;

	/** The partial12. */
	private static int[] partial12;

	/** The partial16. */
	private static int[] partial16;

	/** The partial32. */
	private static int[] partial32;

	/** The default image12. */
	private static Image defaultImage12;

	/** The hover image12. */
	private static Image hoverImage12;

	/** The selected image12. */
	private static Image selectedImage12;

	/** The selected hover image12. */
	private static Image selectedHoverImage12;

	/** The default image16. */
	private static Image defaultImage16;

	/** The hover image16. */
	private static Image hoverImage16;

	/** The selected image16. */
	private static Image selectedImage16;

	/** The selected hover image16. */
	private static Image selectedHoverImage16;

	/** The default image32. */
	private static Image defaultImage32;

	/** The hover image32. */
	private static Image hoverImage32;

	/** The selected image32. */
	private static Image selectedImage32;

	/** The selected hover image32. */
	private static Image selectedHoverImage32;

	/** The max number of stars. */
	private int maxNumberOfStars;

	/** The rating. */
	private float rating;

	/** The Constant DEFAULT_MAX_NUMBERS_OF_STARS. */
	private static final int DEFAULT_MAX_NUMBERS_OF_STARS = 5;

	/** The stars. */
	private final List<Star> stars;

	private int minWidth = -1;

	/** The size. */
	private int size;

	/** The numeric text width. */
	private int numericTextWidth;

	/** The numeric text height. */
	private int numericTextHeight;

	/** The formatted text. */
	private FormattedText formattedText;

	/** The previous layout data. */
	private String previousLayoutData;

	/** The end star pos. */
	private int endStarPos;

	/** The decimal format. */
	private DecimalFormat decimalFormat;

	/**
	 * Constructs a new instance of this class given its parent and a style value describing its behavior and appearance.
	 * <p>
	 * The style value is either one of the style constants defined in class <code>SWT</code> which is applicable to instances of
	 * this class, or must be built by <em>bitwise OR</em>'ing together (that is, using the <code>int</code> "|" operator) two or
	 * more of those <code>SWT</code> style constants. The class description lists the style constants that are applicable to the
	 * class. Style bits are also inherited from superclasses.
	 * </p>
	 *
	 * @param toolkit
	 *            the toolkit
	 * @param parent
	 *            a composite control which will be the parent of the new instance (cannot be null)
	 * @param style
	 *            the style of control to construct
	 */
	public StarRating(final Composite parent, final int style, final String numericPattern) {
		super(parent, style | SWT.DOUBLE_BUFFERED);
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		if (0 != (style & SWT.MAX))
			size = SIZE_BIG;
		else if (0 != (style & SWT.MIN))
			size = SIZE_SMALL;
		else
			size = SIZE_MEDIUM;
		if (null == numericPattern)
			setLayout(new MigLayout("wrap 1, ins 0", "", "[".concat(String.valueOf(size)).concat("]")));
		else {
			setLayout(new MigLayout("wrap 1, ins 0"));
			formattedText = toolkit.createFormattedText(this, SWT.RIGHT);
			final Text numericText = formattedText.getControl();
			numericText.addModifyListener((event) -> {
				float currentValue = 0f;
				try {
					currentValue = numericText.getText().isEmpty() ? 0f : decimalFormat.parse(numericText.getText()).floatValue();
				} catch (ParseException e) {
					logError(e);
				}
				if (rating != currentValue) {
					setRating(currentValue);
				}
				StarRating.this.notifyListeners(SWT.Modify, null);
			});
			AReskinManager reskineEngine = AReskinManager.getInstance();
			if (null == reskineEngine)
				numericText.setBackground(numericText.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			else
				numericText.setBackground(reskineEngine.getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "widget-background",
						AControlController.ADI_CSS_BACKGROUND, numericText));
			setNumericPattern(numericPattern);
			FontData[] fontData = numericText.getFont().getFontData();
			switch (size) {
			case SIZE_BIG:
				fontData[0].setHeight(12);
				numericTextHeight = 24;
				break;
			case SIZE_SMALL:
				fontData[0].setHeight(6);
				numericTextHeight = 12;
				break;
			default:
				fontData[0].setHeight(8);
				numericTextHeight = 16;
				break;
			}
			numericText.setFont(new Font(numericText.getDisplay(), fontData[0]));
		}
		if (SIZE_SMALL == size && null == defaultImage12) {
			defaultImage12 = toolkit.getRegisteredImage("IMG_STAR12");
			hoverImage12 = toolkit.getRegisteredImage("IMG_STAR_FOCUS12");
			selectedImage12 = toolkit.getRegisteredImage("IMG_STAR_MARK12");
			selectedHoverImage12 = toolkit.getRegisteredImage("IMG_STAR_MARK_FOCUS12");
			partial12 = new int[] { 0, 3, 4, 5, 5, 6, 7, 7, 8, 9, 10 };
		} else if (SIZE_BIG == size && null == defaultImage32) {
			defaultImage32 = toolkit.getRegisteredImage("IMG_STAR32");
			hoverImage32 = toolkit.getRegisteredImage("IMG_STAR_FOCUS32");
			selectedImage32 = toolkit.getRegisteredImage("IMG_STAR_MARK32");
			selectedHoverImage32 = toolkit.getRegisteredImage("IMG_STAR_MARK_FOCUS32");
			partial32 = new int[] { 0, 10, 12, 14, 14, 16, 18, 18, 20, 22, 24 };
		} else if (null == defaultImage16) {
			defaultImage16 = toolkit.getRegisteredImage("IMG_STAR16");
			hoverImage16 = toolkit.getRegisteredImage("IMG_STAR_FOCUS16");
			selectedImage16 = toolkit.getRegisteredImage("IMG_STAR_MARK16");
			selectedHoverImage16 = toolkit.getRegisteredImage("IMG_STAR_MARK_FOCUS16");
			partial16 = new int[] { 0, 5, 6, 7, 7, 8, 9, 9, 10, 11, 12 };
		}
		rating = 0;

		stars = new ArrayList<Star>();
		setMaxNumberOfStars(DEFAULT_MAX_NUMBERS_OF_STARS);

		/*
		 * Add listeners
		 */
		final Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				switch (event.type) {
				case SWT.MouseEnter:
				case SWT.MouseMove:
					onMouseEnterOrMove(event);
					break;
				case SWT.MouseExit:
					onMouseExit(event);
					break;
				case SWT.MouseUp:
					onMouseUp(event);
					break;
				case SWT.Paint:
					onPaint(event);
					break;
				}
			}
		};
		final int[] events = new int[] { SWT.MouseEnter, SWT.MouseMove, SWT.MouseExit, SWT.MouseUp, SWT.Paint, SWT.Dispose };
		for (final int event : events) {
			addListener(event, listener);
		}
	}

	/**
	 * On mouse enter or move.
	 *
	 * @param event
	 *            the event
	 */
	private void onMouseEnterOrMove(final Event event) {
		for (final Star star : stars) {
			star.hover = false;
		}

		for (final Star star : stars) {
			final boolean mouseHover = star.bounds.contains(event.x, event.y);
			star.hover = true;
			if (mouseHover) {
				break;
			}
		}
		redraw();
		update();
	}

	/**
	 * On mouse exit.
	 *
	 * @param event
	 *            the event
	 */
	private void onMouseExit(final Event event) {
		for (final Star star : stars) {
			star.hover = false;
		}
		redraw();
		update();
	}

	/**
	 * On mouse up.
	 *
	 * @param event
	 *            the event
	 */
	private void onMouseUp(final Event event) {
		for (int i = 0; i < maxNumberOfStars; i++) {
			final Star star = stars.get(i);
			final boolean selected = star.bounds.contains(event.x, event.y);
			if (selected) {
				setRating(i + 1);
				final Event newEvent = new Event();
				newEvent.widget = this;
				newEvent.display = getDisplay();
				newEvent.item = this;
				newEvent.type = SWT.Selection;
				notifyListeners(SWT.Modify, null);
				redraw();
				update();
				break;
			}
		}

	}

	/**
	 * On paint.
	 *
	 * @param event
	 *            the event
	 */
	private void onPaint(final Event event) {
		final GC gc = event.gc;
		endStarPos = 0;
		for (final Star star : stars) {
			star.draw(gc, endStarPos, 0);
			endStarPos += size;
		}
		if (rating != (int) rating) {
			Star star = stars.get((int) rating);
			int x2 = size * (int) rating;
			int dw = Math.round((rating - (int) rating) * 10);
			Image defaultImage;
			switch (size) {
			case SIZE_SMALL:
				defaultImage = defaultImage12;
				dw = partial12[dw];
				break;
			case SIZE_BIG:
				defaultImage = defaultImage32;
				dw = partial32[dw];
				break;
			default:
				defaultImage = defaultImage16;
				dw = partial16[dw];
				break;
			}
			star.partialDraw(defaultImage, gc, size, x2, dw);
		}
		if (null != formattedText) {
			final int width = maxNumberOfStars * size + numericTextWidth;
			final int borderWidth = getBorderWidth() * 2;
			Point point = new Point(width + borderWidth, size + borderWidth);
			int y = (point.y - numericTextHeight) / 2 - getBorderWidth();
			StringBuffer layoutDataSB = new StringBuffer("aligny center, w ");
			layoutDataSB.append(numericTextWidth).append("!, h ").append(numericTextHeight).append("!, pos ").append(endStarPos)
					.append("px ").append(y).append("px");
			if (!layoutDataSB.toString().equals(previousLayoutData)) {
				previousLayoutData = layoutDataSB.toString();
				formattedText.getControl().setLayoutData(previousLayoutData);
				layout();
			}
		} else
			minWidth = endStarPos + 4;
	}

	@Override
	public Rectangle computeTrim(int x, int y, int width, int height) {
		Rectangle rectangle = super.computeTrim(x, y, width, height);
		if (-1 != minWidth)
			rectangle.width = minWidth > rectangle.width ? minWidth : rectangle.width;
		return rectangle;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point point = super.computeSize(wHint, hHint, changed);
		// if (-1 != minWidth)
		// point.y = minWidth > point.y ? minWidth : point.y;
		return point;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public float getRating() {
		checkWidget();
		return rating;
	}

	/**
	 * Gets the max number of stars.
	 *
	 * @return the maximum number of stars that is displayed by this component
	 */
	public int getMaxNumberOfStars() {
		checkWidget();
		return maxNumberOfStars;
	}

	/**
	 * Set the current number of stars.
	 *
	 * @param rating
	 *            current number of stars
	 */
	public void setRating(final float rating) {
		checkWidget();
		if (rating < 0 || rating > maxNumberOfStars)
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		this.rating = rating;
		for (final Star star : stars) {
			star.marked = false;
		}

		for (int i = 0; i < rating; i++) {
			stars.get(i).marked = true;
		}
		if (null != formattedText) {
			formattedText.setValue(rating);
		}
		notifyListeners(SWT.Modify, null);
	}

	/**
	 * Set the maximum number of stars.
	 *
	 * @param maxNumberOfStars
	 *            the max number of stars
	 */
	public void setMaxNumberOfStars(final int maxNumberOfStars) {
		this.maxNumberOfStars = maxNumberOfStars;
		reinitStars();
	}

	/**
	 * Reinit stars.
	 */
	private void reinitStars() {
		stars.clear();
		for (int i = 0; i < maxNumberOfStars; i++) {
			switch (size) {
			case SIZE_SMALL:
				stars.add(new SmallStar());
				break;
			case SIZE_BIG:
				stars.add(new BigStar());
				break;
			default:
				stars.add(new Star());
				break;
			}
		}
	}

	/**
	 * Gets the numeric text.
	 *
	 * @return the numeric text
	 */
	public FormattedText getNumericText() {
		return formattedText;
	}

	/**
	 * Sets the numeric pattern.
	 *
	 * @param pattern
	 *            the new numeric pattern
	 */
	private void setNumericPattern(String pattern) {
		if (null != formattedText) {
			decimalFormat = new DecimalFormat(pattern);
			numericTextWidth = 0;
			for (char c : pattern.toCharArray()) {
				switch (c) {
				case '#':
					switch (size) {
					case SIZE_SMALL:
						numericTextWidth += 6;
						break;
					case SIZE_BIG:
						numericTextWidth += 11;
						break;
					default:
						numericTextWidth += 8;
						break;
					}
					break;
				case '.':
					switch (size) {
					case SIZE_SMALL:
						numericTextWidth += 2;
						break;
					case SIZE_BIG:
						numericTextWidth += 4;
						break;
					default:
						numericTextWidth += 3;
						break;
					}
					break;
				default:
					throw new RuntimeException(getFromEngineBundle("starRating.invalid.pattern", pattern));
				}
			}
			formattedText.setFormatter(new FloatFormatter(pattern, pattern));
		}
	}

	/**
	 * Gets the formatted text.
	 *
	 * @return the formatted text
	 */
	public FormattedText getFormattedText() {
		return formattedText;
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

	/*
	 * 3 private Star' class one by size with 3 different behaviors
	 */

	/**
	 * The Class Star.
	 *
	 * @author Yves Drumbonnet
	 */
	class Star {

		/** The hover. */
		boolean hover;

		/** The marked. */
		boolean marked;

		/** The bounds. */
		Rectangle bounds;

		/** The current image. */
		Image currentImage;

		/**
		 * Draw.
		 *
		 * @param gc
		 *            the gc
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 */
		void draw(final GC gc, final int x, final int y) {
			if (!StarRating.this.isEnabled()) {
				currentImage = defaultImage16;
			} else {
				if (marked) {
					if (hover) {
						currentImage = selectedHoverImage16;
					} else {
						currentImage = selectedImage16;
					}
				} else {
					if (hover) {
						currentImage = hoverImage16;
					} else {
						currentImage = defaultImage16;
					}
				}
			}

			gc.drawImage(currentImage, x, y);
			bounds = new Rectangle(x, y, size, size);
		}

		/**
		 * Partial draw.
		 *
		 * @param defaultImage
		 *            the default image
		 * @param gc
		 *            the gc
		 * @param width
		 *            the width
		 * @param x
		 *            the x
		 * @param dw
		 *            the dw
		 */
		public void partialDraw(Image defaultImage, GC gc, int width, int x, int dw) {
			gc.drawImage(defaultImage, dw, 0, width - dw, width, x + dw, 0, width - dw, width);
		}

	}

	/**
	 * The Class SmallStar.
	 *
	 * @author Yves Drumbonnet
	 */
	class SmallStar extends Star {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.engine.widgets.StarRating.Star#draw(org.eclipse.swt.graphics.GC, int, int)
		 */
		void draw(final GC gc, final int x, final int y) {
			if (!StarRating.this.isEnabled()) {
				currentImage = defaultImage12;
			} else {
				if (marked) {
					if (hover) {
						currentImage = selectedHoverImage12;
					} else {
						currentImage = selectedImage12;
					}
				} else {
					if (hover) {
						currentImage = hoverImage12;
					} else {
						currentImage = defaultImage12;
					}
				}
			}

			gc.drawImage(currentImage, x, y);
			bounds = new Rectangle(x, y, size, size);
		}
	}

	/**
	 * The Class BigStar.
	 *
	 * @author Yves Drumbonnet
	 */
	class BigStar extends Star {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.engine.widgets.StarRating.Star#draw(org.eclipse.swt.graphics.GC, int, int)
		 */
		void draw(final GC gc, final int x, final int y) {
			if (!StarRating.this.isEnabled()) {
				currentImage = defaultImage32;
			} else {
				if (marked) {
					if (hover) {
						currentImage = selectedHoverImage32;
					} else {
						currentImage = selectedImage32;
					}
				} else {
					if (hover) {
						currentImage = hoverImage32;
					} else {
						currentImage = defaultImage32;
					}
				}
			}

			gc.drawImage(currentImage, x, y);
			bounds = new Rectangle(x, y, size, size);
		}
	}
}
