/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue May 12 15:04:27 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.editor;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.CTabFolderController;
import org.adichatz.engine.controller.collection.CTabItemController;
import org.adichatz.engine.core.ATabularCore;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.generator.xjc.ScenarioTree;

@AdiResourceURI(URI="adi://org.adichatz.studio/editor/ScenarioEditor")
public class ScenarioEditorGeneratorsItem extends EntityManagerCore {
	protected ATabularCore scenarioEditorGeneratorsTable;
	/**
	 * Creates ScenarioEditorGeneratorsItem.
	 *
	 * @param context
	 *            The context of the root controller
	 * @param core controller
	 *            The collection controller linked to the class
	 */
	public ScenarioEditorGeneratorsItem(final AdiContext context, IContainerController parentController) {
		super(context);
		// do not create contents when context is null because that means that class is invoked from dynamic part
		if (null != context) {
			coreController = new CTabItemController("generatorsItem", parentController, this) {
				@Override
				public void afterInstantiateController() {
					breakInjection = true;
					super.afterInstantiateController();
				}
				@Override
				public void createControl() {
					super.createControl();
					if (isValid()) {
						getItem().setText(AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "scenario.generators"));
					}
				}
				@Override
				public EntityInjection getEntityInjection() {
					if (null == entityInjection)
						entityInjection = new EntityInjection(this, getDataAccess().getDataCache().fetchEntity(((ScenarioTree) parentController.getEntity().getBean()).getGenerators()));
					return entityInjection;
				}
			};
			if (!((CTabFolderController) parentController).isDelayed())
				createContents();
		} else
			coreController = parentController;
	}

	/**
	 * creates contents for controller
	 */
	public void createContents(){
		
		// loads and instantiates the class ScenarioEditorGeneratorsTable.
		scenarioEditorGeneratorsTable  = new ScenarioEditorGeneratorsTable(context, coreController);
	}
}