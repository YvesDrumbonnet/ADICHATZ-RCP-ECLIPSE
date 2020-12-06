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

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;

import java.text.DecimalFormatSymbols;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.DelayedThread;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.AControlController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.controller.utils.IPostReskinListener;
import org.adichatz.engine.extra.AFormWindow;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.supplement.LatLng;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.ui.forms.IFormColors;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class GMap.
 *
 * @author Yves Drumbonnet
 */
public class GMap extends Composite {

	public static String GMAP_API_KEY;

	/** The Enum GMAP_TYPE. */
	public enum MAP_DATA_TYPE {
		COORDINATES, ADDRESS
	};

	public enum MAP_TYPE_ID {
		roadmap, hybrid, satellite, terrain
	};

	private ToolItem validateItem;

	/** The pattern. */
	private String pattern;

	/** The browser. */
	private Browser browser;

	/** The address. */
	private String address;

	/** The center. */
	private LatLng center;

	/** The zoom. */
	private int zoom = 12;

	/** The tool bar style. */
	private int toolBarStyle;

	/** The enabled. */
	private boolean enabled = true;

	/** The editable. */
	private boolean editable = true;

	/** The toolbar. */
	private ToolBar toolBar;

	/** The location text. */
	private Text locationText;

	/** The location separator. */
	private ToolItem locationSeparator;

	/** The location composite. */
	private Composite locationComposite;

	/** The location label. */
	private Label locationLabel;

	/** The map type. */
	private MAP_DATA_TYPE mapDataType;

	/** The map type id. */
	private MAP_TYPE_ID mapTypeId = MAP_TYPE_ID.roadmap;

	/** The map type control. */
	private boolean mapTypeControl = true; // Add control to HTML Map

	private boolean innerGMap; // true means gmap is a result of expand button

	private boolean addMarker = true;// Add marker to HTML Map

	private boolean changeCoordinates; // when bound changes, locationText value changes

	private int delayMillisLoading = 0; // Delay in millis for loading gmap

	private boolean displayBlankForDelaying; // delay buffer value. Value change following delay loading and actions.

	/**
	 * Instantiates a new g map.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param toolBarStyle
	 *            the tool bar style
	 */
	public GMap(Composite parent, int style, int toolBarStyle) {
		super(parent, style);
		this.toolBarStyle = toolBarStyle;
		editable = 0 != (toolBarStyle & AdiSWT.EDITABLE);
		if (null == GMAP_API_KEY)
			logWarning(getFromEngineBundle("gmap.api.key.not.defined"));
	}

	/**
	 * Creates the contents.
	 * 
	 */
	public void createContents() {
		final AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		AReskinManager reskinManager = AReskinManager.getInstance();
		if (0 == (toolBarStyle & AdiSWT.NO_TOOL_BAR)) {
			setLayout(new MigLayout("wrap 1, gap 0, ins 0", "[grow, fill]", "[][grow, fill]"));
			toolBar = new ToolBar(this, SWT.HORIZONTAL | SWT.FLAT | SWT.RIGHT_TO_LEFT);
			toolkit.adapt(toolBar);
		} else
			setLayout(new MigLayout("wrap 1, gap 0, ins 0", "[grow, fill]", "[grow, fill]"));
		browser = new Browser(this, SWT.BORDER);
		new BrowserFunction(browser, "loadingFail") {
			public Object function(Object[] arguments) {
				browser.setText("<html><body fgcolor=\"ff0000\"><big><center></br></br><font color=\"red\" size=\"6\">"
						+ getFromEngineBundle("gmap.no.goggle", getValue()) + "</font></big></center></body></html>");
				return null;
			}
		};
		new BrowserFunction(browser, "onBoundsChanged") {
			public Object function(Object[] arguments) {
				zoom = ((Double) arguments[2]).intValue();
				if (mapDataType == MAP_DATA_TYPE.COORDINATES && changeCoordinates && null != locationText) {
					locationText.setText(new LatLng((Double) arguments[0], (Double) arguments[1], pattern).getText());
				}
				return null;
			}
		};
		browser.setLayoutData("h 0:n:n, w 0:n:n");
		if (0 == (toolBarStyle & AdiSWT.NO_TOOL_BAR)) {
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
								// Reload toolkit because it could disposed by reskin process
								final AdiFormToolkit toolkit = AdichatzApplication.getInstance()
										.getContextValue(AdiFormToolkit.class);
								Composite parent = managedForm.getForm().getBody();
								parent.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "[grow,fill][]10"));
								int style = toolBarStyle ^ AdiSWT.EXPANDABLE;
								if (editable)
									style |= AdiSWT.EDITABLE;
								final GMap gmap = new GMap(parent, getStyle(), style);
								gmap.innerGMap = true;
								gmap.setPattern(pattern);
								gmap.setDelayMillisLoading(0);
								gmap.setAddMarker(addMarker);
								gmap.setChangeCoordinates(changeCoordinates);
								gmap.setMapDataType(mapDataType);
								gmap.setMapTypeControl(mapTypeControl);
								gmap.setMapTypeId(mapTypeId);
								gmap.setZoom(zoom);
								gmap.setEnabled(enabled);
								gmap.createContents();
								gmap.setValue(getValue());
								gmap.locationText.setText(locationText.getText());

								Composite bottomComposite = toolkit.createComposite(parent);

								if (editable) {
									bottomComposite.setLayout(new MigLayout("wrap 2, al right"));
									okButton = toolkit.createButton(bottomComposite, JFaceResources.getString("ok"), SWT.PUSH);
									okButton.setEnabled(false);
									okButton.setLayoutData("sg button");
									okButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											setValue(gmap.locationText.getText());
											close();
											layoutLocation();
											notifyListeners(SWT.Modify, null);
										}
									});
									Button cancelButton = toolkit.createButton(bottomComposite, JFaceResources.getString("cancel"),
											SWT.PUSH);
									cancelButton.setLayoutData("sg button");
									cancelButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											close();
										}
									});
									gmap.locationText.addModifyListener((e) -> {
										okButton.setEnabled(!gmap.locationText.getText().equals(locationText.getText()));
									});
								} else {
									bottomComposite.setLayout(new MigLayout("wrap 1, al right"));
									Button finishButton = toolkit.createButton(bottomComposite, JFaceResources.getString("finish"),
											SWT.PUSH);
									finishButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											close();
										}
									});
								}
							}
						};
						expandWindow.create();
						expandWindow.getShell().setMaximized(true);
						expandWindow.getShell().setMinimized(true);
						expandWindow.getShell().setSize(800, 600);
						expandWindow.open();
					}
				});

				ToolItem expandSeparator = new ToolItem(toolBar, SWT.SEPARATOR);

				Composite expandSepComposite = toolkit.createComposite(toolBar, SWT.NONE);
				expandSeparator.setControl(expandSepComposite);
			}
			if ((editable) && !innerGMap) {
				validateItem = new ToolItem(toolBar, SWT.PUSH);
				validateItem.setImage(toolkit.getRegisteredImage("IMG_ACCEPT"));
				validateItem.setEnabled(false);
				validateItem.addSelectionListener(new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e) {
						boolean processSet = false;
						if (editable) {
							if (displayBlankForDelaying)
								processSet = true;
							else
								switch (mapDataType) {
								case COORDINATES:
									processSet = !Utilities.equals(getLatLngFromString(locationText.getText()), center);
									break;
								case ADDRESS:
									processSet = !Utilities.equals(locationText.getText().trim(), address);
									break;
								default:
								}
						} else
							browserSetMapLocation();
						if (processSet) {
							boolean notify = !displayBlankForDelaying;
							if (displayBlankForDelaying)
								displayBlankForDelaying = false;
							setValue(locationText.getText());
							if (notify)
								notifyListeners(SWT.Modify, null);
							layoutLocation();
						}
						setValidateItemToolTipText();
					}
				});
			}
			if (innerGMap) {
				ToolItem innerSeparator = new ToolItem(toolBar, SWT.SEPARATOR);
				innerSeparator.setControl(toolkit.createComposite(toolBar, SWT.NONE));
			}

			locationSeparator = new ToolItem(toolBar, SWT.SEPARATOR);
			locationComposite = new Composite(toolBar, SWT.LEFT_TO_RIGHT);
			//			{ // setBackground problem when using common class!?
			//			};
			locationComposite.setLayout(new MigLayout("ins 0", "[]5[grow,fill]"));

			if (MAP_DATA_TYPE.COORDINATES == mapDataType) {
				toolkit.adapt(locationLabel, false, false);
			} else {
				locationLabel = toolkit.createLabel(locationComposite, getFromEngineBundle("gmap.address"));
			}
			locationText = toolkit.createText(locationComposite, null == center ? "" : center.toString());
			locationText.setEditable(editable);
			locationText.setLayoutData("w 50:n:n");
			locationSeparator.setControl(locationComposite);
			if (null != reskinManager) {
				Color background = reskinManager.getColor("ToolBar", "background-color", AControlController.ADI_CSS_BACKGROUND,
						locationLabel);
				locationLabel.setBackground(background);
				if (!editable)
					locationText.setBackground(background);
				locationComposite.setBackground(background);
			}
		}
		setColors(toolkit);
		if (null != reskinManager)
			reskinManager.addReskinListener(new IPostReskinListener() {
				@Override
				public void postReskin() {
					GMap.this.setColors(toolkit);
				}
			}, this);
		locationText.addModifyListener((e) -> {
			setLocationTextFont();
		});
	}

	private void setValidateItemToolTipText() {
		if (validateItem.isEnabled()) {
			if (displayBlankForDelaying)
				validateItem.setToolTipText(getFromEngineBundle("gmap.display.map"));
			else if (mapDataType == MAP_DATA_TYPE.ADDRESS)
				validateItem.setToolTipText(getFromEngineBundle("gmap.valid.address"));
			else
				validateItem.setToolTipText(getFromEngineBundle("gmap.valid.location"));
		} else
			validateItem.setToolTipText("");
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		switch (mapDataType) {
		case COORDINATES:
			return center;
		default:
			return address;
		}
	}

	/**
	 * Gets the map type id.
	 *
	 * @return the map type id
	 */
	public MAP_TYPE_ID getMapTypeId() {
		return mapTypeId;
	}

	/**
	 * Sets the map type id.
	 *
	 * @param mapTypeId
	 *            the new map type id
	 */
	public void setMapTypeId(MAP_TYPE_ID mapTypeId) {
		this.mapTypeId = mapTypeId;
	}

	public MAP_DATA_TYPE getMapDataType() {
		return mapDataType;
	}

	public void setMapDataType(MAP_DATA_TYPE mapDataType) {
		this.mapDataType = mapDataType;
	}

	public boolean isAddMarker() {
		return addMarker;
	}

	public void setAddMarker(boolean addMarker) {
		this.addMarker = addMarker;
	}

	public boolean isChangeCoordinates() {
		return changeCoordinates;
	}

	public void setChangeCoordinates(boolean changeCoordinates) {
		this.changeCoordinates = changeCoordinates;
	}

	/**
	 * Checks if is map type control.
	 *
	 * @return true, if is map type control
	 */
	public boolean isMapTypeControl() {
		return mapTypeControl;
	}

	/**
	 * Sets the map type control.
	 *
	 * @param mapTypeControl
	 *            the new map type control
	 */
	public void setMapTypeControl(boolean mapTypeControl) {
		this.mapTypeControl = mapTypeControl;
	}

	/**
	 * Sets the colors.
	 *
	 * @param toolkit
	 *            the new colors
	 */
	private void setColors(AdiFormToolkit toolkit) {
		AReskinManager reskinManager = AReskinManager.getInstance();
		if (null == reskinManager) {
			Color titleForeground = toolkit.getColors().getColor(IFormColors.TITLE);
			locationLabel.setForeground(titleForeground);
		} else {
			locationLabel.setForeground(reskinManager.getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "info-foreground-color",
					AControlController.ADI_CSS_FOREGROUND, locationLabel));
		}
	}

	/**
	 * Gets the pattern.
	 * 
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Sets the pattern.
	 * 
	 * @param pattern
	 *            the new pattern
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * Gets the zoom.
	 * 
	 * @return the zoom
	 */
	public int getZoom() {
		checkWidget();
		return zoom;
	}

	public int getDelayMillisLoading() {
		return delayMillisLoading;
	}

	public void setDelayMillisLoading(int delayMillisLoading) {
		this.delayMillisLoading = delayMillisLoading;
	}

	/**
	 * Zoom can be a value between 0 and 20. Not all areas have data for all levels.
	 * 
	 * @param zoom
	 *            the new zoom
	 */
	public void setZoom(final int zoom) {
		this.zoom = zoom;
	}

	public void initValue(Object value) {
		displayBlankForDelaying = (delayMillisLoading > 0) && null != value;
		setValue(value);
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(Object value) {
		if (null == value || value.equals(new LatLng(null, null, null))) {
			center = null;
			address = null;
			if (null != locationText)
				locationText.setText("");
			browser.setText("<html><body fgcolor=\"ff0000\"><div id=\"map_canvas\" style=\"height:100%\"></div></body></html>");
			layoutLocation();
		} else {
			if (null == mapDataType) {
				mapDataType = (value instanceof LatLng) ? MAP_DATA_TYPE.COORDINATES : MAP_DATA_TYPE.ADDRESS;
				createContents();
			}
			switch (mapDataType) {
			case COORDINATES:
				if (value instanceof String) {
					center = getLatLngFromString(value);
				} else
					center = (LatLng) value;
				if (null != locationText)
					locationText.setText(null == center ? "" : center.getText());
				break;
			default:
				address = (String) value;
				if (null != locationText)
					locationText.setText(address);
			}
			browserSetMapLocation();
		}
	}

	private void browserSetMapLocation() {
		if (delayMillisLoading > 0) {
			DelayedThread.newDelayedThread(new MultiKey(this, "#GMAP#"), delayMillisLoading, () -> {
				if (!browser.isDisposed()) { // Could be disposed due to delay 
					browser.setText(getHtmlText());
					layoutLocation();
				}
			});
		} else {
			browser.setText(getHtmlText());
			layoutLocation();
		}
	}

	private LatLng getLatLngFromString(Object value) {
		char decimalSeparator = new DecimalFormatSymbols().getDecimalSeparator();
		String temp[] = ((String) value).split(LatLng.COORD_SEPARATOR.trim());
		if (temp.length == 2) {
			try {
				double lat = Double.parseDouble(temp[0].trim().replace(decimalSeparator, '.'));
				double lon = Double.parseDouble(temp[1].trim().replace(decimalSeparator, '.'));
				return new LatLng(lat, lon, pattern);
			} catch (NumberFormatException ex) {
				logError(getFromEngineBundle("gmap.invalid.latlng.format", temp[0], temp[1]));
			}
		}
		return null;
	}

	private void setLocationTextFont() {
		if (null != locationText) {
			Object value = getValue();
			Object locationValue = getLocationValue();
			boolean bold;
			boolean error = false;
			if (value instanceof LatLng && locationValue instanceof LatLng) {
				LatLng latLng = (LatLng) locationValue;
				double latitude = latLng.getLatitude();
				double longitude = latLng.getLongitude();
				bold = true;
				if (latitude < -90 || latitude > 90) {
					logError(getFromEngineBundle("invalid.latitude.range", latitude));
					error = true;
				}
				if (longitude < -360 || longitude > 360) {
					logError(getFromEngineBundle("invalid.longitude.range", longitude));
					error = true;
				}
				if (!error)
					// Use getText because double precision is imperfect
					bold = !((LatLng) value).getText().equals(latLng.getText());
			} else
				bold = !Utilities.equals(getValue(), getLocationValue());
			if (!error) {
				locationText.setFont(EngineTools.getModifiedFont(locationText.getFont(), bold ? SWT.BOLD : SWT.NORMAL));
				if (null != validateItem) {
					validateItem.setEnabled(bold || displayBlankForDelaying);
					setValidateItemToolTipText();
				}
			}
			layoutLocation();
		}
	}

	private Object getLocationValue() {
		if (null == locationText)
			return null;
		else {
			String locationValue = locationText.getText();
			if (mapDataType == MAP_DATA_TYPE.ADDRESS)
				return locationValue;
			else
				return getLatLngFromString(locationValue);
		}

	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	protected String getHtmlText() {
		Object value = getValue();
		if (displayBlankForDelaying || null == value) {
			if (!validateItem.isEnabled() && displayBlankForDelaying) {
				validateItem.setEnabled(true);
				setValidateItemToolTipText();
			}
			return "<html><body><div id=\"map_canvas\" style=\"height:100%\"></div></body></html>";
		}
		displayBlankForDelaying = false;
		if (null != validateItem) {
			validateItem.setEnabled(false);
			setValidateItemToolTipText();
		}
		StringBuffer documentSB = new StringBuffer("<html><head>");
		if (null == GMAP_API_KEY) {
			documentSB.append("<script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=false\"></script>");
			documentSB.append("<body><div id=\"map_canvas\" style=\"height:100%\">");
		}
		documentSB.append("<script type=\"text/javascript\">");
		if (null != GMAP_API_KEY)
			documentSB.append("function initMap() {");
		documentSB.append("var geocoder = new google.maps.Geocoder();");
		switch (mapDataType) {
		case ADDRESS:
			documentSB.append("geocoder.geocode( { 'address':  \"").append(address).append("\"}, function(results, status) {");
			documentSB.append("if (status == google.maps.GeocoderStatus.OK) {");
			documentSB.append("var latitude = results[0].geometry.location.lat();");
			documentSB.append("var longitude = results[0].geometry.location.lng();");
			documentSB.append("initialize(latitude,longitude);");
			documentSB.append("} else {");
			documentSB.append("loadingFail();");
			documentSB.append("}");
			documentSB.append("})");
			break;
		default:
			documentSB.append("initialize(").append(center.getLatitude()).append(", ").append(center.getLongitude()).append(");");
			break;
		}
		if (null != GMAP_API_KEY)
			documentSB.append("}");
		documentSB.append("function initialize(latitude,longitude) {");
		documentSB.append("var latlng = new google.maps.LatLng(latitude,longitude);");
		documentSB.append("var options = {zoom:").append(zoom).append(", center: latlng, mapTypeId: '").append(mapTypeId)
				.append("', mapTypeControl: ").append(mapTypeControl).append("};");
		documentSB.append("var map = new google.maps.Map(document.getElementById(\"map_canvas\"),options);");
		documentSB.append(
				"google.maps.event.addListener(map, 'zoom_changed', function() {onBoundsChanged(map.getCenter().lat(), map.getCenter().lng(), map.getZoom()	);});");
		if (addMarker)
			documentSB.append("var marker = new google.maps.Marker({position: latlng, map: map});");
		documentSB.append("}");
		documentSB.append("</script>");
		if (null == GMAP_API_KEY) {
			documentSB.append("</div></body>");
		} else {
			documentSB.append("<script async defer src=\"https://maps.googleapis.com/maps/api/js?key=".concat(GMAP_API_KEY)
					.concat("&callback=initMap\"></script>"));
			documentSB.append("</head>");
			documentSB.append("<body><div id=\"map_canvas\" style=\"height:100%\"></div></body>");
		}
		documentSB.append("</html>");
		return documentSB.toString();
	}

	private void layoutLocation() {
		locationText.setLayoutData(null == getValue() ? "wmin 100" : null);
		locationComposite.pack();
		locationSeparator.setWidth(locationComposite.getSize().x);
	};

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

}
