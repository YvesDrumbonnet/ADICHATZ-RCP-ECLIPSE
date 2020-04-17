/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:17:02 CET 2019
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.detail;

import net.miginfocom.swt.MigLayout;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ScrolledCompositeController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.controller.nebula.PShelfController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.studio.xjc.controller.ColorController;
import org.adichatz.studio.xjc.controller.FontController;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.adichatz.studio.xjc.controller.XjcTextController;
import org.adichatz.studio.xjc.data.XjcFieldBindingManager;
import org.eclipse.ui.forms.IFormColors;

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/CompositeSeparatorPart")
public class CompositeSeparatorPartControlItem extends EntityManagerCore {
	// the ScrolledCompositeController controlItemSCSCLC.
	protected ScrolledCompositeController controlItemSCSCLC;
	// the LabelController background$1LBL.
	private LabelController background$1LBL;
	// the ColorController backgroundTXT.
	private ColorController backgroundTXT;
	// the LabelController backgroundImage$1LBL.
	private LabelController backgroundImage$1LBL;
	// the TextController backgroundImageTXT.
	private TextController backgroundImageTXT;
	// the LabelController bounds$1LBL.
	private LabelController bounds$1LBL;
	// the TextController boundsTXT.
	private TextController boundsTXT;
	// the LabelController capture$1LBL.
	private LabelController capture$1LBL;
	// the TextController captureTXT.
	private TextController captureTXT;
	// the LabelController enabled$1LBL.
	private LabelController enabled$1LBL;
	// the XjcTextController enabledTXT.
	private XjcTextController enabledTXT;
	// the LabelController focus$1LBL.
	private LabelController focus$1LBL;
	// the TextController focusTXT.
	private TextController focusTXT;
	// the LabelController font$1LBL.
	private LabelController font$1LBL;
	// the FontController fontTXT.
	private FontController fontTXT;
	// the LabelController foreground$1LBL.
	private LabelController foreground$1LBL;
	// the ColorController foregroundTXT.
	private ColorController foregroundTXT;
	// the LabelController layoutData$1LBL.
	private LabelController layoutData$1LBL;
	// the XjcTextController layoutDataTXT.
	private XjcTextController layoutDataTXT;
	// the LabelController location$1LBL.
	private LabelController location$1LBL;
	// the TextController locationTXT.
	private TextController locationTXT;
	// the LabelController menu$1LBL.
	private LabelController menu$1LBL;
	// the TextController menuTXT.
	private TextController menuTXT;
	// the LabelController redraw$1LBL.
	private LabelController redraw$1LBL;
	// the TextController redrawTXT.
	private TextController redrawTXT;
	// the LabelController size$1LBL.
	private LabelController size$1LBL;
	// the TextController sizeTXT.
	private TextController sizeTXT;
	// the LabelController style$1LBL.
	private LabelController style$1LBL;
	// the TextController styleTXT.
	private TextController styleTXT;
	// the LabelController toolTipText$1LBL.
	private LabelController toolTipText$1LBL;
	// the TextController toolTipTextTXT.
	private TextController toolTipTextTXT;
	// the LabelController visible$1LBL.
	private LabelController visible$1LBL;
	// the XjcTextController visibleTXT.
	private XjcTextController visibleTXT;
	/**
	 * Creates CompositeSeparatorPartControlItem.
	 *
	 * @param context
	 *            The context of the root controller
	 * @param core controller
	 *            The collection controller linked to the class
	 */
	public CompositeSeparatorPartControlItem(final AdiContext context, IContainerController parentController) {
		super(context);
		// do not create contents when context is null because that means that class is invoked from dynamic part
		if (null != context) {
			coreController = new PShelfItemController("controlItem", parentController, this) {
				@Override
				public void createControl() {
					super.createControl();
					if (isValid()) {
						getItem().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "studio.xjcEditor.controlItem"));
					}
				}
			};
			if (!((PShelfController) parentController).isDelayed())
				createContents();
		} else
			coreController = parentController;
	}

	/**
	 * creates contents for controller
	 */
	public void createContents(){
		// Creates control for ScrolledCompositeController controlItemSCSCLC
		controlItemSCSCLC = new ScrolledCompositeController("controlItemSC", coreController, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					setDirtyManagement(false);
					getComposite().setLayout(new MigLayout("wrap 2","[align right]10[fill,grow]","[]"));
				}
			}
		};
		// Creates control for LabelController background$1LBL
		background$1LBL = new LabelController("background$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "background").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createBackground(this);
		// Creates control for LabelController backgroundImage$1LBL
		backgroundImage$1LBL = new LabelController("backgroundImage$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "backgroundImage").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createBackgroundImage(this);
		// Creates control for LabelController bounds$1LBL
		bounds$1LBL = new LabelController("bounds$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "bounds").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createBounds(this);
		// Creates control for LabelController capture$1LBL
		capture$1LBL = new LabelController("capture$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "capture").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createCapture(this);
		// Creates control for LabelController enabled$1LBL
		enabled$1LBL = new LabelController("enabled$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "widget", "enabled").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createEnabled(this);
		// Creates control for LabelController focus$1LBL
		focus$1LBL = new LabelController("focus$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "focus").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createFocus(this);
		// Creates control for LabelController font$1LBL
		font$1LBL = new LabelController("font$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "font").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createFont(this);
		// Creates control for LabelController foreground$1LBL
		foreground$1LBL = new LabelController("foreground$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "foreground").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createForeground(this);
		// Creates control for LabelController layoutData$1LBL
		layoutData$1LBL = new LabelController("layoutData$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "layoutData").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLayoutData(this);
		// Creates control for LabelController location$1LBL
		location$1LBL = new LabelController("location$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "location").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLocation(this);
		// Creates control for LabelController menu$1LBL
		menu$1LBL = new LabelController("menu$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "menu").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createMenu(this);
		// Creates control for LabelController redraw$1LBL
		redraw$1LBL = new LabelController("redraw$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "redraw").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createRedraw(this);
		// Creates control for LabelController size$1LBL
		size$1LBL = new LabelController("size$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "size").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createSize(this);
		// Creates control for LabelController style$1LBL
		style$1LBL = new LabelController("style$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "widget", "style").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createStyle(this);
		// Creates control for LabelController toolTipText$1LBL
		toolTipText$1LBL = new LabelController("toolTipText$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "widget", "toolTipText").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createToolTipText(this);
		// Creates control for LabelController visible$1LBL
		visible$1LBL = new LabelController("visible$1", controlItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "control", "visible").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createVisible(this);
	}
	public ColorController createBackground(ControllerCore genCode) {
		// Creates control for ColorController backgroundTXT
		backgroundTXT = new ColorController("background", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(background$1LBL);
				setProperty("background");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return backgroundTXT;
	}
	public TextController createBackgroundImage(ControllerCore genCode) {
		// Creates control for TextController backgroundImageTXT
		backgroundImageTXT = new TextController("backgroundImage", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(backgroundImage$1LBL);
				setProperty("backgroundImage");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return backgroundImageTXT;
	}
	public TextController createBounds(ControllerCore genCode) {
		// Creates control for TextController boundsTXT
		boundsTXT = new TextController("bounds", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(bounds$1LBL);
				setProperty("bounds");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return boundsTXT;
	}
	public TextController createCapture(ControllerCore genCode) {
		// Creates control for TextController captureTXT
		captureTXT = new TextController("capture", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(capture$1LBL);
				setProperty("capture");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return captureTXT;
	}
	public XjcTextController createEnabled(ControllerCore genCode) {
		// Creates control for XjcTextController enabledTXT
		enabledTXT = new XjcTextController("enabled", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(enabled$1LBL);
				setProperty("enabled");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return enabledTXT;
	}
	public TextController createFocus(ControllerCore genCode) {
		// Creates control for TextController focusTXT
		focusTXT = new TextController("focus", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(focus$1LBL);
				setProperty("focus");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return focusTXT;
	}
	public FontController createFont(ControllerCore genCode) {
		// Creates control for FontController fontTXT
		fontTXT = new FontController("font", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(font$1LBL);
				setProperty("font");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return fontTXT;
	}
	public ColorController createForeground(ControllerCore genCode) {
		// Creates control for ColorController foregroundTXT
		foregroundTXT = new ColorController("foreground", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(foreground$1LBL);
				setProperty("foreground");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return foregroundTXT;
	}
	public XjcTextController createLayoutData(ControllerCore genCode) {
		// Creates control for XjcTextController layoutDataTXT
		layoutDataTXT = new XjcTextController("layoutData", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(layoutData$1LBL);
				setProperty("layoutData");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return layoutDataTXT;
	}
	public TextController createLocation(ControllerCore genCode) {
		// Creates control for TextController locationTXT
		locationTXT = new TextController("location", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(location$1LBL);
				setProperty("location");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return locationTXT;
	}
	public TextController createMenu(ControllerCore genCode) {
		// Creates control for TextController menuTXT
		menuTXT = new TextController("menu", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(menu$1LBL);
				setProperty("menu");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return menuTXT;
	}
	public TextController createRedraw(ControllerCore genCode) {
		// Creates control for TextController redrawTXT
		redrawTXT = new TextController("redraw", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(redraw$1LBL);
				setProperty("redraw");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return redrawTXT;
	}
	public TextController createSize(ControllerCore genCode) {
		// Creates control for TextController sizeTXT
		sizeTXT = new TextController("size", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(size$1LBL);
				setProperty("size");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return sizeTXT;
	}
	public TextController createStyle(ControllerCore genCode) {
		// Creates control for TextController styleTXT
		styleTXT = new TextController("style", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(style$1LBL);
				setProperty("style");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return styleTXT;
	}
	public TextController createToolTipText(ControllerCore genCode) {
		// Creates control for TextController toolTipTextTXT
		toolTipTextTXT = new TextController("toolTipText", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(toolTipText$1LBL);
				setProperty("toolTipText");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return toolTipTextTXT;
	}
	public XjcTextController createVisible(ControllerCore genCode) {
		// Creates control for XjcTextController visibleTXT
		visibleTXT = new XjcTextController("visible", getParentController(controlItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(visible$1LBL);
				setProperty("visible");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return visibleTXT;
	}
}