/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Mon Jul 06 18:34:23 CEST 2020
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
import org.adichatz.engine.core.AContainerCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.engine.validation.MandatoryValidator;
import org.adichatz.studio.xjc.controller.ClassTextController;
import org.adichatz.studio.xjc.controller.OutlineHyperlinkController;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.adichatz.studio.xjc.data.XjcFieldBindingManager;
import org.adichatz.studio.xjc.editor.runnable.OpenClassEditorRunnable;

@AdiResourceURI(URI="adi://org.adichatz.studio/detail/GeneratorPart")
public class GeneratorPart extends AContainerCore {
	// the ScrolledCompositeController itemCompositeSCLC.
	protected ScrolledCompositeController itemCompositeSCLC;
	// the OutlineHyperlinkController generatorClassNameGeneratorPART$1HL.
	private OutlineHyperlinkController generatorClassNameGeneratorPART$1HL;
	// the ClassTextController generatorClassNameTXT.
	private ClassTextController generatorClassNameTXT;
	// the OutlineHyperlinkController treeClassNameGeneratorPART$1HL.
	private OutlineHyperlinkController treeClassNameGeneratorPART$1HL;
	// the ClassTextController treeClassNameTXT.
	private ClassTextController treeClassNameTXT;
	/**
	 * Instantiates a new GeneratorPart.
	 * 
	 * This constructor could be used by dynamic controller.
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public GeneratorPart(AdiContext parentContext, IContainerController parentController) {
		super(null, parentController, null);
		coreController = parentController;
	}

	/**
	 * Creates the part GeneratorPart.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param coreController
	 *            the parent controller
	 * @param paramMap
	 *            the param map
	*/
	public GeneratorPart(AdiPluginResources pluginResources, IContainerController coreController, ParamMap paramMap) {
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
		createGeneratorClassNameGeneratorPART$1(this);
		createGeneratorClassName(this);
		createTreeClassNameGeneratorPART$1(this);
		createTreeClassName(this);
	}
	public OutlineHyperlinkController createGeneratorClassNameGeneratorPART$1(ControllerCore genCode) {
		// Creates control for OutlineHyperlinkController generatorClassNameGeneratorPART$1HL
		generatorClassNameGeneratorPART$1HL = new OutlineHyperlinkController("generatorClassNameGeneratorPART$1", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					runnable = new OpenClassEditorRunnable(this);
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "generator", "generatorClassName").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return generatorClassNameGeneratorPART$1HL;
	}
	public ClassTextController createGeneratorClassName(ControllerCore genCode) {
		// Creates control for ClassTextController generatorClassNameTXT
		generatorClassNameTXT = new ClassTextController("generatorClassName", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(generatorClassNameGeneratorPART$1HL);
				setProperty("generatorClassName");
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
		return generatorClassNameTXT;
	}
	public OutlineHyperlinkController createTreeClassNameGeneratorPART$1(ControllerCore genCode) {
		// Creates control for OutlineHyperlinkController treeClassNameGeneratorPART$1HL
		treeClassNameGeneratorPART$1HL = new OutlineHyperlinkController("treeClassNameGeneratorPART$1", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					runnable = new OpenClassEditorRunnable(this);
					getControl().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "generator", "treeClassName").concat(":"));
					getControl().addFocusListener(new XjcFocusListener(this));
				}
			}
		};
		return treeClassNameGeneratorPART$1HL;
	}
	public ClassTextController createTreeClassName(ControllerCore genCode) {
		// Creates control for ClassTextController treeClassNameTXT
		treeClassNameTXT = new ClassTextController("treeClassName", getParentController(itemCompositeSCLC), genCode) {
			@Override
			public void initialize() {
				setLinkedController(treeClassNameGeneratorPART$1HL);
				setProperty("treeClassName");
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
		return treeClassNameTXT;
	}
}