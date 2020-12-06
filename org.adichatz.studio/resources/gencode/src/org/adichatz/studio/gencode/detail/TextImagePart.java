/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:35:39 CEST 2020
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
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.MandatoryValidator;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.adichatz.studio.xjc.data.XjcFieldBindingManager;
import org.eclipse.ui.forms.IFormColors;

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/TextImagePart")
public class TextImagePart extends AContainerCore {
	// the ScrolledCompositeController itemCompositeSCLC.
	protected ScrolledCompositeController itemCompositeSCLC;
	// the LabelController image$1LBL.
	private LabelController image$1LBL;
	// the TextController imageTXT.
	private TextController imageTXT;
	// the LabelController key$1LBL.
	private LabelController key$1LBL;
	// the TextController keyTXT.
	private TextController keyTXT;
	@Inject
	protected AdiFormToolkit toolkit;
	/**
	 * Instantiates a new TextImagePart.
	 * 
	 * This constructor could be used by dynamic controller.
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public TextImagePart(AdiContext parentContext, IContainerController parentController) {
		super(null, parentController, null);
		coreController = parentController;
	}

	/**
	 * Creates the part TextImagePart.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param coreController
	 *            the parent controller
	 * @param paramMap
	 *            the param map
	*/
	public TextImagePart(AdiPluginResources pluginResources, IContainerController coreController, ParamMap paramMap) {
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
		// Creates control for LabelController image$1LBL
		image$1LBL = new LabelController("image$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "textImage", "image").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createImage(this);
		// Creates control for LabelController key$1LBL
		key$1LBL = new LabelController("key$1", itemCompositeSCLC, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "textImage", "key").concat(":"));
					setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
				}
			}
		};
		createKey(this);
	}
	public TextController createImage(ControllerCore genCode) {
		// Creates control for TextController imageTXT
		imageTXT = new TextController("image", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(image$1LBL);
				setProperty("image");
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
		return imageTXT;
	}
	public TextController createKey(ControllerCore genCode) {
		// Creates control for TextController keyTXT
		keyTXT = new TextController("key", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(key$1LBL);
				setProperty("key");
				super.initialize();
			}
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					addValidator(new MandatoryValidator(this, "mandatoryField"));
					if (null != fieldBindManager)
						fieldBindManager = new XjcFieldBindingManager(this);
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return keyTXT;
	}
}