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
package org.adichatz.engine.widgets.imageviewer;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.extra.AFormWindow;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TypedListener;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageViewer.
 */
public class ImageViewer extends Composite {

	private static String START_ENABLED = "START_ENABLED";
	/*
	 * S T A T I C
	 */

	/**
	 * The Enum IMAGE_TYPE.
	 */
	public static enum IMAGE_TYPE {
		DATA, // DATA Type.
		URL, // URL type.
		FILE // File type.
	};

	/*
	 * end S T A T I C
	 */

	/** The current dir. */
	private String currentDir = ""; /* remembering file open directory */

	/** The image canvas. */
	private SWTImageCanvas imageCanvas;

	/** The image reference. */
	private String imageReference;

	/** The fit canvas. */
	private boolean fitCanvas = false;

	/** The enabled. */
	private boolean enabled = true;

	/** The toolbar. */
	private ToolBar toolBar;

	/** The image type. */
	private IMAGE_TYPE imageType;

	private ToolItem clearImageItem;

	/**
	 * Instantiates a new image viewer.
	 * 
	 * @param parent
	 *            the parent
	 * @param imageType
	 *            the image type
	 * @param style
	 *            the style
	 * @param imageStyle
	 *            the image style
	 */
	public ImageViewer(Composite parent, IMAGE_TYPE imageType, int style, int imageStyle, int toolBarStyle) {
		super(parent, style);
		this.imageType = imageType;
		createContents(imageStyle, toolBarStyle);
	}

	/**
	 * Creates the contents.
	 * 
	 * @param imageStyle
	 *            the image style
	 */
	private void createContents(final int imageStyle, final int toolBarStyle) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		if (0 == (toolBarStyle & AdiSWT.NO_TOOL_BAR)) {
			toolBar = new ToolBar(this, SWT.HORIZONTAL | SWT.FLAT | SWT.RIGHT_TO_LEFT);
			setLayout(new MigLayout("wrap 1, gap 0, ins 0", "[grow, fill]", "[][grow, fill]"));
		} else
			setLayout(new MigLayout("wrap 1, gap 0, ins 0", "[grow, fill]", "[grow, fill]"));
		imageCanvas = new SWTImageCanvas(this, imageStyle);
		imageCanvas.setLayout(new MigLayout("ins 0"));
		boolean editable = 0 != (toolBarStyle & AdiSWT.EDITABLE);

		if (0 == (toolBarStyle & AdiSWT.NO_TOOL_BAR)) {
			if (0 != (toolBarStyle & AdiSWT.DELETE_BUTTON)) {
				clearImageItem = new ToolItem(toolBar, SWT.PUSH);
				clearImageItem.setToolTipText(getFromEngineBundle("imageViewer.clearImage"));
				clearImageItem.setData(START_ENABLED, START_ENABLED);
				clearImageItem.setImage(toolkit.getRegisteredImage("IMG_DELETE"));
				clearImageItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						clearImageAndReference();
					}
				});
			}

			if (editable) {
				if (IMAGE_TYPE.URL == imageType) {
					ToolItem openURL = new ToolItem(toolBar, SWT.PUSH);
					openURL.setToolTipText(getFromEngineBundle("imageViewer.openURL"));
					openURL.setImage(toolkit.getRegisteredImage("IMG_URL"));
					openURL.setData(START_ENABLED, START_ENABLED);
					openURL.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							displayReferenceDialog(toolkit);
						}

					});
				} else {
					ToolItem openFile = new ToolItem(toolBar, SWT.PUSH);
					openFile.setToolTipText(getFromEngineBundle("imageViewer.openFile"));
					openFile.setImage(toolkit.getRegisteredImage("IMG_FILECHOOSER"));
					openFile.setData(START_ENABLED, START_ENABLED);
					openFile.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if (enabled) {
								if (null == currentDir && !EngineTools.isEmpty(imageReference)) {
									File file = new File(imageReference);
									if (file.exists())
										currentDir = file.getParent();
								}
								FileDialog fileChooser = new FileDialog(getShell(), SWT.OPEN);
								fileChooser.setText("Open image file");
								fileChooser.setFilterPath(currentDir);
								fileChooser.setFilterExtensions(new String[] { "*.gif; *.jpg; *.jpeg; *.png; *.ico; *.bmp" });
								fileChooser.setFilterNames(new String[] { "SWT image" + " (gif, jpeg, png, ico, bmp)" });
								String fileName = fileChooser.open();
								if (fileName != null) {
									currentDir = fileChooser.getFilterPath();
									imageCanvas.loadImage(fileName);
									if (fileName.endsWith(".gif"))
										imageCanvas.setImageFormat(SWT.IMAGE_GIF);
									else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
										imageCanvas.setImageFormat(SWT.IMAGE_JPEG);
									else if (fileName.endsWith(".png"))
										imageCanvas.setImageFormat(SWT.IMAGE_PNG);
									else if (fileName.endsWith(".ico"))
										imageCanvas.setImageFormat(SWT.IMAGE_ICO);
									else if (fileName.endsWith(".bmp"))
										imageCanvas.setImageFormat(SWT.IMAGE_BMP);
									imageReference = fileName;
									enableToolItems(null != imageReference);
									if (fitCanvas)
										imageCanvas.fitCanvas();
									notifyListeners(SWT.Modify, null);
								}
							} else {
								displayReferenceDialog(toolkit);
							}
						}
					});
				}
			}

			if (0 == (toolBarStyle & AdiSWT.NO_ROTATE)) {
				ToolItem rotateItem = new ToolItem(toolBar, SWT.PUSH);
				rotateItem.setToolTipText(getFromEngineBundle("imageViewer.rotateClock"));
				rotateItem.setImage(toolkit.getRegisteredImage("IMG_ROTATE"));
				rotateItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						rotate(false);
					}
				});
			}

			if (0 == (toolBarStyle & AdiSWT.NO_ANTI_ROTATE)) {
				ToolItem antiRotateItem = new ToolItem(toolBar, SWT.PUSH);
				antiRotateItem.setToolTipText(getFromEngineBundle("imageViewer.rotateAntiClock"));
				antiRotateItem.setImage(toolkit.getRegisteredImage("IMG_ANTI_ROTATE"));
				antiRotateItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						rotate(true);
					}
				});
			}
			if (0 == (toolBarStyle & AdiSWT.NO_FIT_CANVAS)) {
				ToolItem fitCanvasItem = new ToolItem(toolBar, SWT.PUSH);
				fitCanvasItem.setToolTipText(getFromEngineBundle("imageViewer.fitWindow"));
				fitCanvasItem.setImage(toolkit.getRegisteredImage("IMG_FIT_WINDOW"));
				fitCanvasItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						imageCanvas.fitCanvas();
					}
				});
			}

			if (0 != (toolBarStyle & AdiSWT.EXPANDABLE)) {
				ToolItem expandItem = new ToolItem(toolBar, SWT.PUSH);
				expandItem.setToolTipText(getFromEngineBundle("widget.expand"));
				expandItem.setImage(toolkit.getRegisteredImage("IMG_EXPAND"));
				expandItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						AFormWindow expandWindow = new AFormWindow(toolBar.getShell()) {
							private Button okButton = null;

							@Override
							protected void createFormContent() {
								// Reload necessary due to Reskin process
								AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
								Composite parent = managedForm.getForm().getBody();
								parent.setLayout(new MigLayout("wrap", "grow,fill", "[grow,fill][]"));
								final ImageViewer imageViewer = new ImageViewer(parent, imageType, getStyle(), imageStyle,
										toolBarStyle ^ AdiSWT.EXPANDABLE);
								imageViewer.setImage(getValue());
								Composite bottomComposite = toolkit.createComposite(parent);
								bottomComposite.setLayout(new MigLayout("wrap 2, al right"));
								// imageViewer.clearImageItem.setEnabled(clearImageItem.isEnabled());
								imageViewer.setEnabled(enabled);

								Button finishButton = null;
								Button cancelButton = null;
								boolean editable = 0 != (toolBarStyle & AdiSWT.EDITABLE);
								if (editable) {
									okButton = toolkit.createButton(bottomComposite, JFaceResources.getString("ok"), SWT.PUSH);
									okButton.setEnabled(false);
									okButton.setLayoutData("sg button");
									okButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											setImage(imageViewer.getValue());
											close();
										}
									});
									cancelButton = toolkit.createButton(bottomComposite, JFaceResources.getString("cancel"),
											SWT.PUSH);
									cancelButton.setLayoutData("sg button");
									cancelButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											close();
										}
									});
									imageViewer.addModifyListener((e) -> {
										okButton.setEnabled(true);
									});
								} else {
									finishButton = toolkit.createButton(bottomComposite, JFaceResources.getString("finish"),
											SWT.PUSH);
									finishButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											close();
										}
									});
								}
								parent.layout();
							}
						};
						expandWindow.create();
						expandWindow.getShell().setMaximized(true);
						expandWindow.getShell().setMinimized(true);
						expandWindow.getShell().setSize(800, 600);
						expandWindow.open();
					}
				});
			}

			if (0 == (toolBarStyle & AdiSWT.NO_100)) {
				ToolItem originItem = new ToolItem(toolBar, SWT.PUSH);
				originItem.setToolTipText(getFromEngineBundle("imageViewer.initialAspect"));
				originItem.setImage(toolkit.getRegisteredImage("IMG_100"));
				originItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						imageCanvas.showOriginal();
					}
				});
			}

			if (0 == (toolBarStyle & AdiSWT.NO_ZOOM)) {
				ToolItem zoomIn = new ToolItem(toolBar, SWT.PUSH);
				zoomIn.setToolTipText(getFromEngineBundle("widget.zoomIn"));
				zoomIn.setImage(toolkit.getRegisteredImage("IMG_ZOOM_PLUS"));
				zoomIn.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						imageCanvas.zoomIn();
					}
				});
				ToolItem zoomOut = new ToolItem(toolBar, SWT.PUSH);
				zoomOut.setToolTipText(getFromEngineBundle("widget.zoomIn"));
				zoomOut.setImage(toolkit.getRegisteredImage("IMG_ZOOM_MINUS"));
				zoomOut.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						imageCanvas.zoomOut();
					}
				});
			}

			if (IMAGE_TYPE.DATA == imageType && 0 == (toolBarStyle & AdiSWT.NO_COPY_PASTE_BUFFER)) {
				new ToolItem(toolBar, SWT.SEPARATOR);
				ToolItem pasteBufferItem = new ToolItem(toolBar, SWT.PUSH);
				pasteBufferItem.setToolTipText(getFromEngineBundle("imageViewer.pasteFromClipboad"));
				pasteBufferItem.setImage(toolkit.getRegisteredImage("IMG_PASTE"));
				pasteBufferItem.setData(START_ENABLED, START_ENABLED);
				pasteBufferItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Clipboard clipboard = new Clipboard(getDisplay());
						Object buffer = clipboard.getContents(ImageTransfer.getInstance());
						if (null != buffer && buffer instanceof ImageData) {
							setImage(buffer);
						}
						clipboard.dispose();
					}
				});
				ToolItem copyBufferItem = new ToolItem(toolBar, SWT.PUSH);
				copyBufferItem.setToolTipText(getFromEngineBundle("imageViewer.copyFromClipboad"));
				copyBufferItem.setImage(toolkit.getRegisteredImage("IMG_COPY"));
				copyBufferItem.setData(START_ENABLED, START_ENABLED);
				copyBufferItem.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (null != imageCanvas.getSourceImage()) {
							Clipboard clipboard = new Clipboard(getDisplay());
							Transfer[] transfers = new Transfer[] { ImageTransfer.getInstance() };
							clipboard.setContents(new Object[] { imageCanvas.getImageData() }, transfers);
							clipboard.dispose();
						}
					}
				});
			}

		}
	}

	public void enableToolItems(boolean enabled) {
		for (ToolItem toolItem : toolBar.getItems()) {
			if (null == toolItem.getData(START_ENABLED) && toolItem.isEnabled() != enabled)
				toolItem.setEnabled(enabled);
		}
	}

	/**
	 * Checks if is fit canvas.
	 * 
	 * @return true, if is fit canvas
	 */
	public boolean isFitCanvas() {
		return fitCanvas;
	}

	/**
	 * Sets the fit canvas.
	 * 
	 * @param fitCanvas
	 *            the new fit canvas
	 */
	public void setFitCanvas(boolean fitCanvas) {
		if (this.fitCanvas != fitCanvas) {
			this.fitCanvas = fitCanvas;
			if (fitCanvas && null != imageCanvas)
				imageCanvas.fitCanvas();
		}
	}

	/**
	 * Gets the image type.
	 * 
	 * @return the image type
	 */
	public IMAGE_TYPE getImageType() {
		return imageType;
	}

	/**
	 * Rotate the image.
	 * 
	 * @param anti
	 *            true means anti clock rotation
	 */
	private void rotate(boolean anti) {
		if (null != imageCanvas.getSourceImage()) {
			/* rotate image anti-clockwise */
			ImageData src = imageCanvas.getImageData();
			if (src == null)
				return;
			PaletteData srcPal = src.palette;
			PaletteData destPal;
			ImageData dest;
			/* construct a new ImageData */
			if (srcPal.isDirect) {
				destPal = new PaletteData(srcPal.redMask, srcPal.greenMask, srcPal.blueMask);
			} else {
				destPal = new PaletteData(srcPal.getRGBs());
			}
			dest = new ImageData(src.height, src.width, src.depth, destPal);
			/* rotate by rearranging the pixels */
			if (anti)
				for (int i = 0; i < src.width; i++) {
					for (int j = 0; j < src.height; j++) {
						int pixel = src.getPixel(i, j);
						dest.setPixel(j, src.width - 1 - i, pixel);
					}
				}
			else
				for (int i = 0; i < src.width; i++) {
					for (int j = 0; j < src.height; j++) {
						int pixel = src.getPixel(i, j);
						dest.setPixel(src.height - 1 - j, i, pixel);
					}
				}
			imageCanvas.setImageData(dest);
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

	/**
	 * Gets the image reference.
	 * 
	 * @return the image reference
	 */
	public String getImageReference() {
		return imageReference;
	}

	public void setImage(Object imageElement) {
		if (null == imageElement)
			clearImageAndReference();
		else if (imageElement instanceof ImageData) {
			imageCanvas.setImageData((ImageData) imageElement);
			enableToolItems(true);
			if (isFitCanvas())
				imageCanvas.fitCanvas();
			notifyListeners(SWT.Modify, null);
		} else if (imageElement instanceof byte[]) {
			byte[] imageBuffer = (byte[]) imageElement;
			if (0 == imageBuffer.length)
				clearImageAndReference();
			else {
				InputStream inputStream = new ByteArrayInputStream(imageBuffer);
				imageCanvas.loadImage(inputStream);
				enableToolItems(true);
				if (isFitCanvas())
					imageCanvas.fitCanvas();
			}
		} else if (imageElement instanceof String) {
			String reference = (String) imageElement;
			if (reference.isEmpty()) {
				imageReference = reference;
				clearImageAndReference();
			} else {
				// Load image only if imageReference change.
				// setImage could be called several times during databinding process (e.g. see syncEntity.firePropertyChanges() in
				// AEntityManagerController).
				if (!Utilities.equals(imageReference, reference)) {
					imageReference = reference;
					switch (imageType) {
					case FILE:
						try {
							imageCanvas.loadImage(imageReference);
						} catch (Exception e) {
							logError(e);
						}
						break;
					case URL:
						loadImageFromUrl();
						break;
					default:
						break;
					}
				}
				enableToolItems(true);
			}
		}
	}

	/**
	 * Sets the image reference.
	 * 
	 * @param imageReference
	 *            the new image reference
	 */
	public void setImageReference(String imageReference) {
		this.imageReference = imageReference;
		if (EngineTools.isEmpty(imageReference))
			clearImageAndReference();
		else {
			switch (imageType) {
			case FILE:
				try {
					imageCanvas.loadImage(imageReference);
				} catch (Exception e) {
					logError(e);
				}
				break;
			case URL:
				loadImageFromUrl();
				break;
			case DATA:
				// InputStream inputStream = new ByteArrayInputStream(imageBuffer);
				// imageCanvas.loadImage(inputStream);
				break;
			default:
				break;
			}
			enableToolItems(true);
		}
	}

	/**
	 * Gets the image canvas.
	 * 
	 * @return the image canvas
	 */
	public SWTImageCanvas getImageCanvas() {
		return imageCanvas;
	}

	/**
	 * Load image from url.
	 */
	private void loadImageFromUrl() {
		try {
			String urlStr = EngineTools.evalParam(imageReference);
			final URL url = new URL(urlStr);

			boolean imageEnabled = enabled;
			setEnabled(false);
			try {
				final InputStream inputStream = url.openStream();
				imageCanvas.loadImage(inputStream);
				if (isFitCanvas())
					imageCanvas.fitCanvas();
			} catch (IOException e) {
				logError(e);
				clearImage();
				imageCanvas.setImageData(AdichatzApplication.getInstance()
						.getImage(EngineConstants.ENGINE_BUNDLE, "IMG_BIG_ERROR.png").getImageData());
			} finally {
				if (imageEnabled)
					setEnabled(true);
			}
		} catch (MalformedURLException e) {
			LogBroker.displayError(getFromEngineBundle("malformedURLException.title"), e,
					getFromEngineBundle("malformedURLException.message", imageReference));
		}
	}

	/**
	 * Clear image and reference.
	 */
	private void clearImageAndReference() {
		boolean change = null != imageReference || null != imageCanvas.getSourceImage();
		clearImage();
		if (change) {
			imageReference = null;
			notifyListeners(SWT.Modify, null);
		}
	}

	/**
	 * Clear image.
	 */
	private void clearImage() {
		if (null != imageCanvas.getSourceImage()) {
			imageCanvas.setSourceImage(null);
			Rectangle clientRect = imageCanvas.getClientArea(); /* Canvas' painting area */
			GC gc = new GC(imageCanvas);
			gc.setClipping(clientRect);
			gc.fillRectangle(clientRect);
			imageCanvas.initScrollBars();
			enableToolItems(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Control#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (null != clearImageItem)
			clearImageItem.setEnabled(enabled);
		enableToolItems(null != imageCanvas.getSourceImage());
	}

	/**
	 * Display reference dialog.
	 */
	private void displayReferenceDialog(AdiFormToolkit toolkit) {
		Rectangle clientArea = getDisplay().getClientArea();
		final Shell dialog = new Shell(getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText(getFromEngineBundle("imageViewer.type.url"));
		dialog.setImage(toolkit.getRegisteredImage("IMG_URL"));
		dialog.setLayout(new FillLayout());
		Composite composite = new Composite(dialog, SWT.BORDER);
		composite.setLayout(new MigLayout("wrap 2", "[fill, grow][]"));

		final Text urlText = new Text(composite, SWT.BORDER);
		urlText.setEditable(isEnabled());
		urlText.setText(null == imageReference ? "" : imageReference);
		urlText.setLayoutData("w 400:400:" + (clientArea.width - 20));

		if (isEnabled()) {
			Button ok = new Button(composite, SWT.IMAGE_BMP);
			ok.setImage(toolkit.getRegisteredImage("IMG_ACCEPT"));
			ok.setToolTipText(getFromEngineBundle("field.acceptToolTip"));
			ok.setLayoutData("align right");
			ok.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					imageReference = urlText.getText();
					loadImageFromUrl();
					notifyListeners(SWT.Modify, null);
					enableToolItems(true);
					dialog.close();
				}
			});
			dialog.setDefaultButton(ok);
		}

		dialog.pack();

		/*
		 * Compute position for the Dialog window
		 */
		Point toolbarPoint = toDisplay(0, 0);
		Point newPoint = new Point(Math.min(clientArea.width - dialog.getBounds().width - 5, toolbarPoint.x - 25),
				Math.min(clientArea.height - 25, toolbarPoint.y + 25));
		dialog.setLocation(newPoint);

		dialog.open();
	}

	public Object getValue() {
		switch (imageType) {
		case DATA: {
			byte[] data = null;
			if (null != imageCanvas.getSourceImage()) {
				try {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					DataOutputStream writeOut = new DataOutputStream(output);
					ImageLoader loader = new ImageLoader();
					loader.data = new ImageData[] { imageCanvas.getImageData() };
					loader.save(writeOut, imageCanvas.getImageFormat());
					writeOut.close();
					data = output.toByteArray();
					output.close();
					return data;
				} catch (IOException e) {
					logError(e);
				}
			}
			return data;
		}
		default:
			return imageReference;
		}
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point oldSize = getSize();
		Point size = super.computeSize(wHint, hHint, changed);
		if (!size.equals(oldSize) && isFitCanvas()) {
			imageCanvas.fitCanvas();
		}
		return size;
	}
}
