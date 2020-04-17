/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:17:28 CET 2019
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
import org.adichatz.engine.controller.field.HyperlinkController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.controller.nebula.PShelfController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.controller.utils.DataReferenceManager;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.studio.xjc.controller.BooleanRadioGroupController;
import org.adichatz.studio.xjc.controller.ClassTextController;
import org.adichatz.studio.xjc.controller.CodeTextController;
import org.adichatz.studio.xjc.controller.ColorController;
import org.adichatz.studio.xjc.controller.FontController;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.adichatz.studio.xjc.data.XjcFieldBindingManager;
import org.adichatz.studio.xjc.editor.runnable.OpenClassEditorRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.IFormColors;

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/ExtraTextPart")
public class ExtraTextPartLifeCycleItem extends EntityManagerCore {
	// the ScrolledCompositeController lifeCycleItemSCSCLC.
	protected ScrolledCompositeController lifeCycleItemSCSCLC;
	// the LabelController ref$1LBL.
	private LabelController ref$1LBL;
	// the TextController refTXT.
	private TextController refTXT;
	// the HyperlinkController controllerClassNameExtraTextPART$1HL.
	private HyperlinkController controllerClassNameExtraTextPART$1HL;
	// the ClassTextController controllerClassNameTXT.
	private ClassTextController controllerClassNameTXT;
	// the LabelController controlValueType$1LBL.
	private LabelController controlValueType$1LBL;
	// the TextController controlValueTypeTXT.
	private TextController controlValueTypeTXT;
	// the HyperlinkController fieldBindManagerClassNameExtraTextPART$1HL.
	private HyperlinkController fieldBindManagerClassNameExtraTextPART$1HL;
	// the ClassTextController fieldBindManagerClassNameTXT.
	private ClassTextController fieldBindManagerClassNameTXT;
	// the LabelController forceBinding$1LBL.
	private LabelController forceBinding$1LBL;
	// the TextController forceBindingTXT.
	private TextController forceBindingTXT;
	// the LabelController labelBackground$1LBL.
	private LabelController labelBackground$1LBL;
	// the ColorController labelBackgroundTXT.
	private ColorController labelBackgroundTXT;
	// the LabelController labelFont$1LBL.
	private LabelController labelFont$1LBL;
	// the FontController labelFontTXT.
	private FontController labelFontTXT;
	// the LabelController labelForeground$1LBL.
	private LabelController labelForeground$1LBL;
	// the ColorController labelForegroundTXT.
	private ColorController labelForegroundTXT;
	// the LabelController labelLayoutData$1LBL.
	private LabelController labelLayoutData$1LBL;
	// the TextController labelLayoutDataTXT.
	private TextController labelLayoutDataTXT;
	// the LabelController labelText$1LBL.
	private LabelController labelText$1LBL;
	// the TextController labelTextTXT.
	private TextController labelTextTXT;
	// the LabelController lazyFetches$1LBL.
	private LabelController lazyFetches$1LBL;
	// the TextController lazyFetchesTXT.
	private TextController lazyFetchesTXT;
	// the LabelController linkedControl$1LBL.
	private LabelController linkedControl$1LBL;
	// the TextController linkedControlTXT.
	private TextController linkedControlTXT;
	// the LabelController mandatory$1LBL.
	private LabelController mandatory$1LBL;
	// the BooleanRadioGroupController mandatoryRG.
	private BooleanRadioGroupController mandatoryRG;
	// the LabelController noLabel$1LBL.
	private LabelController noLabel$1LBL;
	// the BooleanRadioGroupController noLabelRG.
	private BooleanRadioGroupController noLabelRG;
	// the LabelController convertModelToTarget$1LBL.
	private LabelController convertModelToTarget$1LBL;
	// the CodeTextController convertModelToTargetTXT.
	private CodeTextController convertModelToTargetTXT;
	// the LabelController convertTargetToModel$1LBL.
	private LabelController convertTargetToModel$1LBL;
	// the CodeTextController convertTargetToModelTXT.
	private CodeTextController convertTargetToModelTXT;
	// the LabelController initialValue$1LBL.
	private LabelController initialValue$1LBL;
	// the CodeTextController initialValueTXT.
	private CodeTextController initialValueTXT;
	/**
	 * Creates ExtraTextPartLifeCycleItem.
	 *
	 * @param context
	 *            The context of the root controller
	 * @param core controller
	 *            The collection controller linked to the class
	 */
	public ExtraTextPartLifeCycleItem(final AdiContext context, IContainerController parentController) {
		super(context);
		// do not create contents when context is null because that means that class is invoked from dynamic part
		if (null != context) {
			coreController = new PShelfItemController("lifeCycleItem", parentController, this) {
				@Override
				public void createControl() {
					super.createControl();
					if (isValid()) {
						getItem().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "studio.xjcEditor.lifeCycleItem"));
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
		// Creates control for ScrolledCompositeController lifeCycleItemSCSCLC
		lifeCycleItemSCSCLC = new ScrolledCompositeController("lifeCycleItemSC", coreController, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					setDirtyManagement(false);
					getComposite().setLayout(new MigLayout("wrap 2","[align right]10[fill,grow]","[]"));
				}
			}
		};
		// Creates control for LabelController ref$1LBL
		ref$1LBL = new LabelController("ref$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "validElement", "ref").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createRef(this);
		createControllerClassNameExtraTextPART$1(this);
		createControllerClassName(this);
		// Creates control for LabelController controlValueType$1LBL
		controlValueType$1LBL = new LabelController("controlValueType$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "controlValueType").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createControlValueType(this);
		createFieldBindManagerClassNameExtraTextPART$1(this);
		createFieldBindManagerClassName(this);
		// Creates control for LabelController forceBinding$1LBL
		forceBinding$1LBL = new LabelController("forceBinding$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "forceBinding").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createForceBinding(this);
		// Creates control for LabelController labelBackground$1LBL
		labelBackground$1LBL = new LabelController("labelBackground$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "labelBackground").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLabelBackground(this);
		// Creates control for LabelController labelFont$1LBL
		labelFont$1LBL = new LabelController("labelFont$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "labelFont").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLabelFont(this);
		// Creates control for LabelController labelForeground$1LBL
		labelForeground$1LBL = new LabelController("labelForeground$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "labelForeground").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLabelForeground(this);
		// Creates control for LabelController labelLayoutData$1LBL
		labelLayoutData$1LBL = new LabelController("labelLayoutData$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "labelLayoutData").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLabelLayoutData(this);
		// Creates control for LabelController labelText$1LBL
		labelText$1LBL = new LabelController("labelText$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "labelText").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLabelText(this);
		// Creates control for LabelController lazyFetches$1LBL
		lazyFetches$1LBL = new LabelController("lazyFetches$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "lazyFetches").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLazyFetches(this);
		// Creates control for LabelController linkedControl$1LBL
		linkedControl$1LBL = new LabelController("linkedControl$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "linkedControl").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLinkedControl(this);
		// Creates control for LabelController mandatory$1LBL
		mandatory$1LBL = new LabelController("mandatory$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "mandatory").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createMandatory(this);
		// Creates control for LabelController noLabel$1LBL
		noLabel$1LBL = new LabelController("noLabel$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "noLabel").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createNoLabel(this);
		// Creates control for LabelController convertModelToTarget$1LBL
		convertModelToTarget$1LBL = new LabelController("convertModelToTarget$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "convertModelToTarget").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createConvertModelToTarget(this);
		// Creates control for LabelController convertTargetToModel$1LBL
		convertTargetToModel$1LBL = new LabelController("convertTargetToModel$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "convertTargetToModel").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createConvertTargetToModel(this);
		// Creates control for LabelController initialValue$1LBL
		initialValue$1LBL = new LabelController("initialValue$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "initialValue").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createInitialValue(this);
	}
	public TextController createRef(ControllerCore genCode) {
		// Creates control for TextController refTXT
		refTXT = new TextController("ref", getParentController(lifeCycleItemSCSCLC), genCode) {
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
	public HyperlinkController createControllerClassNameExtraTextPART$1(ControllerCore genCode) {
		// Creates control for HyperlinkController controllerClassNameExtraTextPART$1HL
		controllerClassNameExtraTextPART$1HL = new HyperlinkController("controllerClassNameExtraTextPART$1", getParentController(lifeCycleItemSCSCLC), genCode) {
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
		return controllerClassNameExtraTextPART$1HL;
	}
	public ClassTextController createControllerClassName(ControllerCore genCode) {
		// Creates control for ClassTextController controllerClassNameTXT
		controllerClassNameTXT = new ClassTextController("controllerClassName", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(controllerClassNameExtraTextPART$1HL);
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
	public TextController createControlValueType(ControllerCore genCode) {
		// Creates control for TextController controlValueTypeTXT
		controlValueTypeTXT = new TextController("controlValueType", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(controlValueType$1LBL);
				setProperty("controlValueType");
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
		return controlValueTypeTXT;
	}
	public HyperlinkController createFieldBindManagerClassNameExtraTextPART$1(ControllerCore genCode) {
		// Creates control for HyperlinkController fieldBindManagerClassNameExtraTextPART$1HL
		fieldBindManagerClassNameExtraTextPART$1HL = new HyperlinkController("fieldBindManagerClassNameExtraTextPART$1", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					runnable = new OpenClassEditorRunnable(this);
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "controlField", "fieldBindManagerClassName").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return fieldBindManagerClassNameExtraTextPART$1HL;
	}
	public ClassTextController createFieldBindManagerClassName(ControllerCore genCode) {
		// Creates control for ClassTextController fieldBindManagerClassNameTXT
		fieldBindManagerClassNameTXT = new ClassTextController("fieldBindManagerClassName", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(fieldBindManagerClassNameExtraTextPART$1HL);
				setProperty("fieldBindManagerClassName");
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
		return fieldBindManagerClassNameTXT;
	}
	public TextController createForceBinding(ControllerCore genCode) {
		// Creates control for TextController forceBindingTXT
		forceBindingTXT = new TextController("forceBinding", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(forceBinding$1LBL);
				setProperty("forceBinding");
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
		return forceBindingTXT;
	}
	public ColorController createLabelBackground(ControllerCore genCode) {
		// Creates control for ColorController labelBackgroundTXT
		labelBackgroundTXT = new ColorController("labelBackground", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(labelBackground$1LBL);
				setProperty("labelBackground");
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
		return labelBackgroundTXT;
	}
	public FontController createLabelFont(ControllerCore genCode) {
		// Creates control for FontController labelFontTXT
		labelFontTXT = new FontController("labelFont", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(labelFont$1LBL);
				setProperty("labelFont");
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
		return labelFontTXT;
	}
	public ColorController createLabelForeground(ControllerCore genCode) {
		// Creates control for ColorController labelForegroundTXT
		labelForegroundTXT = new ColorController("labelForeground", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(labelForeground$1LBL);
				setProperty("labelForeground");
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
		return labelForegroundTXT;
	}
	public TextController createLabelLayoutData(ControllerCore genCode) {
		// Creates control for TextController labelLayoutDataTXT
		labelLayoutDataTXT = new TextController("labelLayoutData", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(labelLayoutData$1LBL);
				setProperty("labelLayoutData");
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
		return labelLayoutDataTXT;
	}
	public TextController createLabelText(ControllerCore genCode) {
		// Creates control for TextController labelTextTXT
		labelTextTXT = new TextController("labelText", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(labelText$1LBL);
				setProperty("labelText");
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
		return labelTextTXT;
	}
	public TextController createLazyFetches(ControllerCore genCode) {
		// Creates control for TextController lazyFetchesTXT
		lazyFetchesTXT = new TextController("lazyFetches", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(lazyFetches$1LBL);
				setProperty("lazyFetches");
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
		return lazyFetchesTXT;
	}
	public TextController createLinkedControl(ControllerCore genCode) {
		// Creates control for TextController linkedControlTXT
		linkedControlTXT = new TextController("linkedControl", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(linkedControl$1LBL);
				setProperty("linkedControl");
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
		return linkedControlTXT;
	}
	public BooleanRadioGroupController createMandatory(ControllerCore genCode) {
		// Creates control for BooleanRadioGroupController mandatoryRG
		mandatoryRG = new BooleanRadioGroupController("mandatory", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(mandatory$1LBL);
				setProperty("mandatory");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					dataReferenceManager = new DataReferenceManager(this);
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return mandatoryRG;
	}
	public BooleanRadioGroupController createNoLabel(ControllerCore genCode) {
		// Creates control for BooleanRadioGroupController noLabelRG
		noLabelRG = new BooleanRadioGroupController("noLabel", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(noLabel$1LBL);
				setProperty("noLabel");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					dataReferenceManager = new DataReferenceManager(this);
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return noLabelRG;
	}
	public CodeTextController createConvertModelToTarget(ControllerCore genCode) {
		// Creates control for CodeTextController convertModelToTargetTXT
		convertModelToTargetTXT = new CodeTextController("convertModelToTarget", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(convertModelToTarget$1LBL);
				setProperty("convertModelToTarget");
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
		return convertModelToTargetTXT;
	}
	public CodeTextController createConvertTargetToModel(ControllerCore genCode) {
		// Creates control for CodeTextController convertTargetToModelTXT
		convertTargetToModelTXT = new CodeTextController("convertTargetToModel", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(convertTargetToModel$1LBL);
				setProperty("convertTargetToModel");
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
		return convertTargetToModelTXT;
	}
	public CodeTextController createInitialValue(ControllerCore genCode) {
		// Creates control for CodeTextController initialValueTXT
		initialValueTXT = new CodeTextController("initialValue", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(initialValue$1LBL);
				setProperty("initialValue");
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
		return initialValueTXT;
	}
}