/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue Jul 07 12:30:04 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.jpa.gencode.query;

import java.util.function.BooleanSupplier;
import net.miginfocom.swt.MigLayout;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.FormPageController;
import org.adichatz.engine.controller.collection.IncludeController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.core.AFormPageCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;

@AdiResourceURI(URI="adi://org.adichatz.jpa/query/QueryForm")
public class QueryFormPage1 extends AFormPageCore {
	// the FormPageController page1FP.
	protected FormPageController page1FP;
	// the IncludeController fullTextIncludeINCL.
	private IncludeController fullTextIncludeINCL;
	// the IncludeController tableIncludeINCL.
	private IncludeController tableIncludeINCL;
	// the LabelController totoLBL.
	private LabelController totoLBL;
	/**
	 * Creates the formPage QueryFormPage1.
	 *
	 * @param context
	 *            The context of the root controller
	 * @param parentController
	 *            The parent controller
	 * @param managedForm
	 *            The managed form
	 */
	public QueryFormPage1(final AdiContext context, IContainerController parentController) {
		super(context);
		this.parentController = parentController;
		createContents();
	}

	/**
	 * Create contents for FormPage
	 */
	public void createContents(){
		// Creates control for FormPageController page1FP
		page1FP = new FormPageController("Page1", parentController, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					getControl().getForm().setText(AdichatzApplication.getInstance().getMessageBundle(((String) context.getParam("MESSAGE_BUNDLE")), "queryFormText"));
					getComposite().setLayout(new MigLayout("wrap, ins 0","[grow,fill]","[grow,fill]"));
				}
			}
		};
		coreController = page1FP;
		// Creates control for IncludeController fullTextIncludeINCL
		String fullTextIncludeIK = ((String) context.getParam("FULLTEXT_RESOURCE_URI"));
		if (!EngineTools.isEmpty(fullTextIncludeIK)) {
			final String[] fullTextIncludeKeys = EngineTools.getInstanceKeys(fullTextIncludeIK);
			fullTextIncludeINCL = new IncludeController("fullTextInclude", page1FP, new EntityManagerCore(getContext())) {
				@Override
				public void createContents() {
					// loads and instantiates the class for 'fullTextIncludeINCL' include wrapper.
					pluginResources = null == fullTextIncludeKeys[0] ? getPluginResources() : AdichatzApplication.getPluginResources(fullTextIncludeKeys[0]);
					instantiateIncludeClass(pluginResources, fullTextIncludeKeys[2], fullTextIncludeKeys[1], new Class[] {ParamMap.class, IContainerController.class, String.class, AdiContext.class}, new Object[] {getParamMap(), this, "fullTextInclude", context});
				}
			};
		}
		// Creates control for IncludeController tableIncludeINCL
		String tableIncludeIK = ((String) context.getParam("TABLE_RESOURCE_URI"));
		if (!EngineTools.isEmpty(tableIncludeIK)) {
			final String[] tableIncludeKeys = EngineTools.getInstanceKeys(tableIncludeIK);
			tableIncludeINCL = new IncludeController("tableInclude", page1FP, new EntityManagerCore(getContext())) {
				@Override
				public void createContents() {
					// loads and instantiates the class for 'tableIncludeINCL' include wrapper.
					pluginResources = null == tableIncludeKeys[0] ? getPluginResources() : AdichatzApplication.getPluginResources(tableIncludeKeys[0]);
					instantiateIncludeClass(pluginResources, tableIncludeKeys[2], tableIncludeKeys[1], new Class[] {ParamMap.class, IContainerController.class, String.class, AdiContext.class}, new Object[] {getParamMap(), this, "tableInclude", context});
				}
				@Override
				public void addParams() {
					getParamMap().put("CONTENT_PROVIDER", context.getParam("CONTENT_PROVIDER"));
				}
			};
		}
		// Creates control for LabelController totoLBL
		totoLBL = new LabelController("toto", page1FP, this) {
			@Override
			public void initialize() {
				BooleanSupplier evaluatorVALID;
				evaluatorVALID = new BooleanSupplier() {
					public boolean getAsBoolean() {
						return null == ((String) context.getParam("TABLE_RESOURCE_URI"));
					}
				};
				addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID, evaluatorVALID));
				super.initialize();
			}
		};
	}

}