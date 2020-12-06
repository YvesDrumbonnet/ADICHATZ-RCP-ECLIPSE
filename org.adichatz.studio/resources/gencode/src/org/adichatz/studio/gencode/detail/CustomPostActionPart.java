/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:33:54 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.detail;

import javax.inject.Inject;
import net.miginfocom.swt.MigLayout;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.ScrolledCompositeController;
import org.adichatz.engine.controller.field.CheckBoxController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.adichatz.studio.xjc.data.XjcFieldBindingManager;
import org.eclipse.ui.forms.IFormColors;

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/CustomPostActionPart")
public class CustomPostActionPart extends AContainerCore {
	// the ScrolledCompositeController itemCompositeSCLC.
	protected ScrolledCompositeController itemCompositeSCLC;
	// the LabelController deployEJB$1LBL.
	private LabelController deployEJB$1LBL;
	// the CheckBoxController deployEJBCB.
	private CheckBoxController deployEJBCB;
	// the LabelController generateEJB$1LBL.
	private LabelController generateEJB$1LBL;
	// the CheckBoxController generateEJBCB.
	private CheckBoxController generateEJBCB;
	// the LabelController generatePersistenceXml$1LBL.
	private LabelController generatePersistenceXml$1LBL;
	// the CheckBoxController generatePersistenceXmlCB.
	private CheckBoxController generatePersistenceXmlCB;
	// the LabelController rewritePojos$1LBL.
	private LabelController rewritePojos$1LBL;
	// the CheckBoxController rewritePojosCB.
	private CheckBoxController rewritePojosCB;
	@Inject
	protected AdiFormToolkit toolkit;
	/**
	 * Instantiates a new CustomPostActionPart.
	 * 
	 * This constructor could be used by dynamic controller.
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public CustomPostActionPart(AdiContext parentContext, IContainerController parentController) {
		super(null, parentController, null);
		coreController = parentController;
	}

	/**
	 * Creates the part CustomPostActionPart.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param coreController
	 *            the parent controller
	 * @param paramMap
	 *            the param map
	*/
	public CustomPostActionPart(AdiPluginResources pluginResources, IContainerController coreController, ParamMap paramMap) {
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
		// Creates control for LabelController deployEJB$1LBL
		deployEJB$1LBL = new LabelController("deployEJB$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "customPostAction", "deployEJB").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createDeployEJB(this);
		// Creates control for LabelController generateEJB$1LBL
		generateEJB$1LBL = new LabelController("generateEJB$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "customPostAction", "generateEJB").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createGenerateEJB(this);
		// Creates control for LabelController generatePersistenceXml$1LBL
		generatePersistenceXml$1LBL = new LabelController("generatePersistenceXml$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "customPostAction", "generatePersistenceXml").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createGeneratePersistenceXml(this);
		// Creates control for LabelController rewritePojos$1LBL
		rewritePojos$1LBL = new LabelController("rewritePojos$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "customPostAction", "rewritePojos").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createRewritePojos(this);
	}
	public CheckBoxController createDeployEJB(ControllerCore genCode) {
		// Creates control for CheckBoxController deployEJBCB
		deployEJBCB = new CheckBoxController("deployEJB", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(deployEJB$1LBL);
				setProperty("deployEJB");
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
		return deployEJBCB;
	}
	public CheckBoxController createGenerateEJB(ControllerCore genCode) {
		// Creates control for CheckBoxController generateEJBCB
		generateEJBCB = new CheckBoxController("generateEJB", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(generateEJB$1LBL);
				setProperty("generateEJB");
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
		return generateEJBCB;
	}
	public CheckBoxController createGeneratePersistenceXml(ControllerCore genCode) {
		// Creates control for CheckBoxController generatePersistenceXmlCB
		generatePersistenceXmlCB = new CheckBoxController("generatePersistenceXml", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(generatePersistenceXml$1LBL);
				setProperty("generatePersistenceXml");
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
		return generatePersistenceXmlCB;
	}
	public CheckBoxController createRewritePojos(ControllerCore genCode) {
		// Creates control for CheckBoxController rewritePojosCB
		rewritePojosCB = new CheckBoxController("rewritePojos", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(rewritePojos$1LBL);
				setProperty("rewritePojos");
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
		return rewritePojosCB;
	}
}