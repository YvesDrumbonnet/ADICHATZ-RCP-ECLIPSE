/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Thu Feb 06 11:55:42 CET 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.detail;

import net.miginfocom.swt.MigLayout;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.ScrolledCompositeController;
import org.adichatz.engine.controller.field.HyperlinkController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.studio.xjc.controller.AdiResourceUriTextController;
import org.adichatz.studio.xjc.controller.CallBackForeController;
import org.adichatz.studio.xjc.controller.ClassTextController;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.adichatz.studio.xjc.data.XjcFieldBindingManager;
import org.adichatz.studio.xjc.editor.runnable.OpenClassEditorRunnable;
import org.adichatz.studio.xjc.editor.runnable.OpenResourceURIRunnable;
import org.eclipse.ui.forms.IFormColors;

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/EntityTreePart")
public class EntityTreePart extends AContainerCore {
	// the ScrolledCompositeController itemCompositeSCLC.
	protected ScrolledCompositeController itemCompositeSCLC;
	// the LabelController callbackClassNames$1LBL.
	private LabelController callbackClassNames$1LBL;
	// the CallBackForeController callbackClassNamesTXT.
	private CallBackForeController callbackClassNamesTXT;
	// the LabelController callforeClassNames$1LBL.
	private LabelController callforeClassNames$1LBL;
	// the CallBackForeController callforeClassNamesTXT.
	private CallBackForeController callforeClassNamesTXT;
	// the HyperlinkController compositeKeyStrategyFactoryClassNameEntityTreePART$1HL.
	private HyperlinkController compositeKeyStrategyFactoryClassNameEntityTreePART$1HL;
	// the ClassTextController compositeKeyStrategyFactoryClassNameTXT.
	private ClassTextController compositeKeyStrategyFactoryClassNameTXT;
	// the HyperlinkController entityURIEntityTreePART$1HL.
	private HyperlinkController entityURIEntityTreePART$1HL;
	// the AdiResourceUriTextController entityURITXT.
	private AdiResourceUriTextController entityURITXT;
	// the LabelController idFieldName$1LBL.
	private LabelController idFieldName$1LBL;
	// the TextController idFieldNameTXT.
	private TextController idFieldNameTXT;
	// the LabelController superEntityURI$1LBL.
	private LabelController superEntityURI$1LBL;
	// the TextController superEntityURITXT.
	private TextController superEntityURITXT;
	/**
	 * Instantiates a new EntityTreePart.
	 * 
	 * This constructor could be used by dynamic controller.
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public EntityTreePart(AdiContext parentContext, IContainerController parentController) {
		super(null, parentController, null);
		coreController = parentController;
	}

	/**
	 * Creates the part EntityTreePart.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param coreController
	 *            the parent controller
	 * @param paramMap
	 *            the param map
	*/
	public EntityTreePart(AdiPluginResources pluginResources, IContainerController coreController, ParamMap paramMap) {
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
		// Creates control for LabelController callbackClassNames$1LBL
		callbackClassNames$1LBL = new LabelController("callbackClassNames$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "entityTree", "callbackClassNames").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createCallbackClassNames(this);
		// Creates control for LabelController callforeClassNames$1LBL
		callforeClassNames$1LBL = new LabelController("callforeClassNames$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "entityTree", "callforeClassNames").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createCallforeClassNames(this);
		createCompositeKeyStrategyFactoryClassNameEntityTreePART$1(this);
		createCompositeKeyStrategyFactoryClassName(this);
		createEntityURIEntityTreePART$1(this);
		createEntityURI(this);
		// Creates control for LabelController idFieldName$1LBL
		idFieldName$1LBL = new LabelController("idFieldName$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "entityTree", "idFieldName").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createIdFieldName(this);
		// Creates control for LabelController superEntityURI$1LBL
		superEntityURI$1LBL = new LabelController("superEntityURI$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "entityTree", "superEntityURI").concat(":"));
					getControl().setForeground(AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createSuperEntityURI(this);
	}
	public CallBackForeController createCallbackClassNames(ControllerCore genCode) {
		// Creates control for CallBackForeController callbackClassNamesTXT
		callbackClassNamesTXT = new CallBackForeController("callbackClassNames", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(callbackClassNames$1LBL);
				setProperty("callbackClassNames");
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
		return callbackClassNamesTXT;
	}
	public CallBackForeController createCallforeClassNames(ControllerCore genCode) {
		// Creates control for CallBackForeController callforeClassNamesTXT
		callforeClassNamesTXT = new CallBackForeController("callforeClassNames", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(callforeClassNames$1LBL);
				setProperty("callforeClassNames");
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
		return callforeClassNamesTXT;
	}
	public HyperlinkController createCompositeKeyStrategyFactoryClassNameEntityTreePART$1(ControllerCore genCode) {
		// Creates control for HyperlinkController compositeKeyStrategyFactoryClassNameEntityTreePART$1HL
		compositeKeyStrategyFactoryClassNameEntityTreePART$1HL = new HyperlinkController("compositeKeyStrategyFactoryClassNameEntityTreePART$1", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					runnable = new OpenClassEditorRunnable(this);
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "entityTree", "compositeKeyStrategyFactoryClassName").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return compositeKeyStrategyFactoryClassNameEntityTreePART$1HL;
	}
	public ClassTextController createCompositeKeyStrategyFactoryClassName(ControllerCore genCode) {
		// Creates control for ClassTextController compositeKeyStrategyFactoryClassNameTXT
		compositeKeyStrategyFactoryClassNameTXT = new ClassTextController("compositeKeyStrategyFactoryClassName", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(compositeKeyStrategyFactoryClassNameEntityTreePART$1HL);
				setProperty("compositeKeyStrategyFactoryClassName");
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
		return compositeKeyStrategyFactoryClassNameTXT;
	}
	public HyperlinkController createEntityURIEntityTreePART$1(ControllerCore genCode) {
		// Creates control for HyperlinkController entityURIEntityTreePART$1HL
		entityURIEntityTreePART$1HL = new HyperlinkController("entityURIEntityTreePART$1", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					runnable = new OpenResourceURIRunnable(this);
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "entityTree", "entityURI").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return entityURIEntityTreePART$1HL;
	}
	public AdiResourceUriTextController createEntityURI(ControllerCore genCode) {
		// Creates control for AdiResourceUriTextController entityURITXT
		entityURITXT = new AdiResourceUriTextController("entityURI", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(entityURIEntityTreePART$1HL);
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
	public TextController createIdFieldName(ControllerCore genCode) {
		// Creates control for TextController idFieldNameTXT
		idFieldNameTXT = new TextController("idFieldName", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(idFieldName$1LBL);
				setProperty("idFieldName");
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
		return idFieldNameTXT;
	}
	public TextController createSuperEntityURI(ControllerCore genCode) {
		// Creates control for TextController superEntityURITXT
		superEntityURITXT = new TextController("superEntityURI", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(superEntityURI$1LBL);
				setProperty("superEntityURI");
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
		return superEntityURITXT;
	}
}