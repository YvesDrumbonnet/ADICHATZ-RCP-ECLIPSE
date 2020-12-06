/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:34:38 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.detail;

import java.util.function.BooleanSupplier;
import javax.inject.Inject;
import net.miginfocom.swt.MigLayout;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.ScrolledCompositeController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.xjc.IncludeType;
import org.adichatz.studio.xjc.controller.AdiResourceUriTextController;
import org.adichatz.studio.xjc.controller.ClassTextController;
import org.adichatz.studio.xjc.controller.CodeTextController;
import org.adichatz.studio.xjc.controller.IdTextController;
import org.adichatz.studio.xjc.controller.OutlineHyperlinkController;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.adichatz.studio.xjc.controller.XjcTextController;
import org.adichatz.studio.xjc.data.XjcFieldBindingManager;
import org.adichatz.studio.xjc.editor.runnable.OpenClassEditorRunnable;
import org.adichatz.studio.xjc.editor.runnable.OpenResourceURIRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.IFormColors;

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/IncludePart")
public class IncludePart extends AContainerCore {
	// the ScrolledCompositeController itemCompositeSCLC.
	protected ScrolledCompositeController itemCompositeSCLC;
	// the LabelController id$1LBL.
	private LabelController id$1LBL;
	// the IdTextController idTXT.
	private IdTextController idTXT;
	// the OutlineHyperlinkController adiResourceURIIncludePART$1HL.
	private OutlineHyperlinkController adiResourceURIIncludePART$1HL;
	// the AdiResourceUriTextController adiResourceURITXT.
	private AdiResourceUriTextController adiResourceURITXT;
	// the OutlineHyperlinkController controllerClassNameIncludePART$1HL.
	private OutlineHyperlinkController controllerClassNameIncludePART$1HL;
	// the ClassTextController controllerClassNameTXT.
	private ClassTextController controllerClassNameTXT;
	// the LabelController enabled$1LBL.
	private LabelController enabled$1LBL;
	// the XjcTextController enabledTXT.
	private XjcTextController enabledTXT;
	// the LabelController ref$1LBL.
	private LabelController ref$1LBL;
	// the TextController refTXT.
	private TextController refTXT;
	// the LabelController style$1LBL.
	private LabelController style$1LBL;
	// the TextController styleTXT.
	private TextController styleTXT;
	// the LabelController toolTipText$1LBL.
	private LabelController toolTipText$1LBL;
	// the TextController toolTipTextTXT.
	private TextController toolTipTextTXT;
	// the LabelController valid$1LBL.
	private LabelController valid$1LBL;
	// the XjcTextController validTXT.
	private XjcTextController validTXT;
	// the LabelController additionalCode$1LBL.
	private LabelController additionalCode$1LBL;
	// the CodeTextController additionalCodeTXT.
	private CodeTextController additionalCodeTXT;
	@Inject
	protected AdiFormToolkit toolkit;
	/**
	 * Instantiates a new IncludePart.
	 * 
	 * This constructor could be used by dynamic controller.
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public IncludePart(AdiContext parentContext, IContainerController parentController) {
		super(null, parentController, null);
		coreController = parentController;
	}

	/**
	 * Creates the part IncludePart.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param coreController
	 *            the parent controller
	 * @param paramMap
	 *            the param map
	*/
	public IncludePart(AdiPluginResources pluginResources, IContainerController coreController, ParamMap paramMap) {
		super(pluginResources, coreController, paramMap);
		ContainerController containerController = (ContainerController) coreController;
		getRegister().put("detailPart", new RegisterEntry<ContainerController>(containerController, ContainerController.class));
		createContents();
	}

	/**
	 * creates contents
	 */
	public void createContents(){
		// Creates control for ScrolledCompositeController itemCompositeSCLC
		itemCompositeSCLC = new ScrolledCompositeController("itemComposite", coreController, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					setDirtyManagement(false);
					getComposite().setLayout(new MigLayout("wrap 2","[align right]10[fill,grow]","[]"));
				}
			}
		};
		// Creates control for LabelController id$1LBL
		id$1LBL = new LabelController("id$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "element", "id").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createId(this);
		createAdiResourceURIIncludePART$1(this);
		createAdiResourceURI(this);
		createControllerClassNameIncludePART$1(this);
		createControllerClassName(this);
		// Creates control for LabelController enabled$1LBL
		enabled$1LBL = new LabelController("enabled$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "widget", "enabled").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createEnabled(this);
		// Creates control for LabelController ref$1LBL
		ref$1LBL = new LabelController("ref$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "validElement", "ref").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createRef(this);
		// Creates control for LabelController style$1LBL
		style$1LBL = new LabelController("style$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "widget", "style").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createStyle(this);
		// Creates control for LabelController toolTipText$1LBL
		toolTipText$1LBL = new LabelController("toolTipText$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "widget", "toolTipText").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createToolTipText(this);
		// Creates control for LabelController valid$1LBL
		valid$1LBL = new LabelController("valid$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "validElement", "valid").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createValid(this);
		// Creates control for LabelController additionalCode$1LBL
		additionalCode$1LBL = new LabelController("additionalCode$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "widget", "additionalCode").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createAdditionalCode(this);
	}
	public IdTextController createId(ControllerCore genCode) {
		// Creates control for IdTextController idTXT
		idTXT = new IdTextController("id", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(id$1LBL);
				setProperty("id");
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
		return idTXT;
	}
	public OutlineHyperlinkController createAdiResourceURIIncludePART$1(ControllerCore genCode) {
		// Creates control for OutlineHyperlinkController adiResourceURIIncludePART$1HL
		adiResourceURIIncludePART$1HL = new OutlineHyperlinkController("adiResourceURIIncludePART$1", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				BooleanSupplier evaluatorENABLED;
				evaluatorENABLED = new BooleanSupplier() {
					public boolean getAsBoolean() {
						return null != ((IncludeType) parentController.getEntity().getBean()).getAdiResourceURI();
					}
				};
				addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.ENABLED, evaluatorENABLED));
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					runnable = new OpenResourceURIRunnable(this);
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "include", "adiResourceURI").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return adiResourceURIIncludePART$1HL;
	}
	public AdiResourceUriTextController createAdiResourceURI(ControllerCore genCode) {
		// Creates control for AdiResourceUriTextController adiResourceURITXT
		adiResourceURITXT = new AdiResourceUriTextController("adiResourceURI", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(adiResourceURIIncludePART$1HL);
				setProperty("adiResourceURI");
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
		return adiResourceURITXT;
	}
	public OutlineHyperlinkController createControllerClassNameIncludePART$1(ControllerCore genCode) {
		// Creates control for OutlineHyperlinkController controllerClassNameIncludePART$1HL
		controllerClassNameIncludePART$1HL = new OutlineHyperlinkController("controllerClassNameIncludePART$1", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					runnable = new OpenClassEditorRunnable(this);
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "widget", "controllerClassName").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return controllerClassNameIncludePART$1HL;
	}
	public ClassTextController createControllerClassName(ControllerCore genCode) {
		// Creates control for ClassTextController controllerClassNameTXT
		controllerClassNameTXT = new ClassTextController("controllerClassName", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(controllerClassNameIncludePART$1HL);
				setProperty("controllerClassName");
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
		return controllerClassNameTXT;
	}
	public XjcTextController createEnabled(ControllerCore genCode) {
		// Creates control for XjcTextController enabledTXT
		enabledTXT = new XjcTextController("enabled", getParentController(itemCompositeSCLC), genCode) {
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
	public TextController createRef(ControllerCore genCode) {
		// Creates control for TextController refTXT
		refTXT = new TextController("ref", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(ref$1LBL);
				setProperty("ref");
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
		return refTXT;
	}
	public TextController createStyle(ControllerCore genCode) {
		// Creates control for TextController styleTXT
		styleTXT = new TextController("style", getParentController(itemCompositeSCLC), genCode) {
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
		toolTipTextTXT = new TextController("toolTipText", getParentController(itemCompositeSCLC), genCode) {
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
	public XjcTextController createValid(ControllerCore genCode) {
		// Creates control for XjcTextController validTXT
		validTXT = new XjcTextController("valid", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(valid$1LBL);
				setProperty("valid");
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
		return validTXT;
	}
	public CodeTextController createAdditionalCode(ControllerCore genCode) {
		// Creates control for CodeTextController additionalCodeTXT
		additionalCodeTXT = new CodeTextController("additionalCode", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(additionalCode$1LBL);
				setProperty("additionalCode");
				super.initialize();
			}
			@Override
			public void createControl() {
				if (isValid()) {
					setStyle(SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | AdiSWT.EXPANDABLE);
				}
				super.createControl();
				if (isValid()) {
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return additionalCodeTXT;
	}
}