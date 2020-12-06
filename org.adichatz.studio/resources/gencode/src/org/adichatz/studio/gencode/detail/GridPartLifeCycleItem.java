/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:34:29 CEST 2020
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
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ScrolledCompositeController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.controller.nebula.PShelfController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.xjc.GridType;
import org.adichatz.studio.xjc.controller.AdiResourceUriTextController;
import org.adichatz.studio.xjc.controller.ClassTextController;
import org.adichatz.studio.xjc.controller.CodeTextController;
import org.adichatz.studio.xjc.controller.OutlineHyperlinkController;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.adichatz.studio.xjc.data.XjcFieldBindingManager;
import org.adichatz.studio.xjc.editor.runnable.OpenClassEditorRunnable;
import org.adichatz.studio.xjc.editor.runnable.OpenResourceURIRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.IFormColors;

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/GridPart")
public class GridPartLifeCycleItem extends EntityManagerCore {
	// the ScrolledCompositeController lifeCycleItemSCSCLC.
	protected ScrolledCompositeController lifeCycleItemSCSCLC;
	// the OutlineHyperlinkController controllerClassNameGridPART$1HL.
	private OutlineHyperlinkController controllerClassNameGridPART$1HL;
	// the ClassTextController controllerClassNameTXT.
	private ClassTextController controllerClassNameTXT;
	// the LabelController enableRoles$1LBL.
	private LabelController enableRoles$1LBL;
	// the TextController enableRolesTXT.
	private TextController enableRolesTXT;
	// the OutlineHyperlinkController entityURIGridPART$1HL.
	private OutlineHyperlinkController entityURIGridPART$1HL;
	// the AdiResourceUriTextController entityURITXT.
	private AdiResourceUriTextController entityURITXT;
	// the LabelController lazyFetches$1LBL.
	private LabelController lazyFetches$1LBL;
	// the TextController lazyFetchesTXT.
	private TextController lazyFetchesTXT;
	// the LabelController ref$1LBL.
	private LabelController ref$1LBL;
	// the TextController refTXT.
	private TextController refTXT;
	// the LabelController refreshAtStart$1LBL.
	private LabelController refreshAtStart$1LBL;
	// the TextController refreshAtStartTXT.
	private TextController refreshAtStartTXT;
	// the LabelController sortedColumn$1LBL.
	private LabelController sortedColumn$1LBL;
	// the TextController sortedColumnTXT.
	private TextController sortedColumnTXT;
	// the LabelController statusBarKey$1LBL.
	private LabelController statusBarKey$1LBL;
	// the TextController statusBarKeyTXT.
	private TextController statusBarKeyTXT;
	// the LabelController tableRendererKey$1LBL.
	private LabelController tableRendererKey$1LBL;
	// the TextController tableRendererKeyTXT.
	private TextController tableRendererKeyTXT;
	// the LabelController validRoles$1LBL.
	private LabelController validRoles$1LBL;
	// the TextController validRolesTXT.
	private TextController validRolesTXT;
	// the LabelController visibleRoles$1LBL.
	private LabelController visibleRoles$1LBL;
	// the TextController visibleRolesTXT.
	private TextController visibleRolesTXT;
	// the LabelController additionalCode$1LBL.
	private LabelController additionalCode$1LBL;
	// the CodeTextController additionalCodeTXT.
	private CodeTextController additionalCodeTXT;
	@Inject
	protected AdiFormToolkit toolkit;
	/**
	 * Creates GridPartLifeCycleItem.
	 *
	 * @param context
	 *            The context of the root controller
	 * @param core controller
	 *            The collection controller linked to the class
	 */
	public GridPartLifeCycleItem(final AdiContext context, IContainerController parentController) {
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
		createControllerClassNameGridPART$1(this);
		createControllerClassName(this);
		// Creates control for LabelController enableRoles$1LBL
		enableRoles$1LBL = new LabelController("enableRoles$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "collection", "enableRoles").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createEnableRoles(this);
		createEntityURIGridPART$1(this);
		createEntityURI(this);
		// Creates control for LabelController lazyFetches$1LBL
		lazyFetches$1LBL = new LabelController("lazyFetches$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "lazyFetchContainer", "lazyFetches").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createLazyFetches(this);
		// Creates control for LabelController ref$1LBL
		ref$1LBL = new LabelController("ref$1", lifeCycleItemSCSCLC, this) {
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
		// Creates control for LabelController refreshAtStart$1LBL
		refreshAtStart$1LBL = new LabelController("refreshAtStart$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "set", "refreshAtStart").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createRefreshAtStart(this);
		// Creates control for LabelController sortedColumn$1LBL
		sortedColumn$1LBL = new LabelController("sortedColumn$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "tabular", "sortedColumn").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createSortedColumn(this);
		// Creates control for LabelController statusBarKey$1LBL
		statusBarKey$1LBL = new LabelController("statusBarKey$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "tabular", "statusBarKey").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createStatusBarKey(this);
		// Creates control for LabelController tableRendererKey$1LBL
		tableRendererKey$1LBL = new LabelController("tableRendererKey$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "tabular", "tableRendererKey").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createTableRendererKey(this);
		// Creates control for LabelController validRoles$1LBL
		validRoles$1LBL = new LabelController("validRoles$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "collection", "validRoles").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createValidRoles(this);
		// Creates control for LabelController visibleRoles$1LBL
		visibleRoles$1LBL = new LabelController("visibleRoles$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "collection", "visibleRoles").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createVisibleRoles(this);
		// Creates control for LabelController additionalCode$1LBL
		additionalCode$1LBL = new LabelController("additionalCode$1", lifeCycleItemSCSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "collection", "additionalCode").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createAdditionalCode(this);
	}
	public OutlineHyperlinkController createControllerClassNameGridPART$1(ControllerCore genCode) {
		// Creates control for OutlineHyperlinkController controllerClassNameGridPART$1HL
		controllerClassNameGridPART$1HL = new OutlineHyperlinkController("controllerClassNameGridPART$1", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					runnable = new OpenClassEditorRunnable(this);
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "collection", "controllerClassName").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return controllerClassNameGridPART$1HL;
	}
	public ClassTextController createControllerClassName(ControllerCore genCode) {
		// Creates control for ClassTextController controllerClassNameTXT
		controllerClassNameTXT = new ClassTextController("controllerClassName", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(controllerClassNameGridPART$1HL);
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
	public TextController createEnableRoles(ControllerCore genCode) {
		// Creates control for TextController enableRolesTXT
		enableRolesTXT = new TextController("enableRoles", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(enableRoles$1LBL);
				setProperty("enableRoles");
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
		return enableRolesTXT;
	}
	public OutlineHyperlinkController createEntityURIGridPART$1(ControllerCore genCode) {
		// Creates control for OutlineHyperlinkController entityURIGridPART$1HL
		entityURIGridPART$1HL = new OutlineHyperlinkController("entityURIGridPART$1", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				BooleanSupplier evaluatorENABLED;
				evaluatorENABLED = new BooleanSupplier() {
					public boolean getAsBoolean() {
						return null != ((GridType) parentController.getEntity().getBean()).getEntityURI();
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
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "set", "entityURI").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return entityURIGridPART$1HL;
	}
	public AdiResourceUriTextController createEntityURI(ControllerCore genCode) {
		// Creates control for AdiResourceUriTextController entityURITXT
		entityURITXT = new AdiResourceUriTextController("entityURI", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(entityURIGridPART$1HL);
				setProperty("entityURI");
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
		return entityURITXT;
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
	public TextController createRefreshAtStart(ControllerCore genCode) {
		// Creates control for TextController refreshAtStartTXT
		refreshAtStartTXT = new TextController("refreshAtStart", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(refreshAtStart$1LBL);
				setProperty("refreshAtStart");
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
		return refreshAtStartTXT;
	}
	public TextController createSortedColumn(ControllerCore genCode) {
		// Creates control for TextController sortedColumnTXT
		sortedColumnTXT = new TextController("sortedColumn", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(sortedColumn$1LBL);
				setProperty("sortedColumn");
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
		return sortedColumnTXT;
	}
	public TextController createStatusBarKey(ControllerCore genCode) {
		// Creates control for TextController statusBarKeyTXT
		statusBarKeyTXT = new TextController("statusBarKey", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(statusBarKey$1LBL);
				setProperty("statusBarKey");
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
		return statusBarKeyTXT;
	}
	public TextController createTableRendererKey(ControllerCore genCode) {
		// Creates control for TextController tableRendererKeyTXT
		tableRendererKeyTXT = new TextController("tableRendererKey", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(tableRendererKey$1LBL);
				setProperty("tableRendererKey");
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
		return tableRendererKeyTXT;
	}
	public TextController createValidRoles(ControllerCore genCode) {
		// Creates control for TextController validRolesTXT
		validRolesTXT = new TextController("validRoles", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(validRoles$1LBL);
				setProperty("validRoles");
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
		return validRolesTXT;
	}
	public TextController createVisibleRoles(ControllerCore genCode) {
		// Creates control for TextController visibleRolesTXT
		visibleRolesTXT = new TextController("visibleRoles", getParentController(lifeCycleItemSCSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(visibleRoles$1LBL);
				setProperty("visibleRoles");
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
		return visibleRolesTXT;
	}
	public CodeTextController createAdditionalCode(ControllerCore genCode) {
		// Creates control for CodeTextController additionalCodeTXT
		additionalCodeTXT = new CodeTextController("additionalCode", getParentController(lifeCycleItemSCSCLC), genCode) {
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