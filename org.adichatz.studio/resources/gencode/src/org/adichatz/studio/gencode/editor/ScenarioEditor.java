/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Fri Feb 07 15:54:03 CET 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.editor;

import java.util.function.BooleanSupplier;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.indigo.editor.AFormEditor;
import org.adichatz.engine.indigo.editor.FormEditorCore;
import org.adichatz.engine.indigo.editor.FormPageHeader;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.studio.xjc.data.XjcBindingService;

@AdiResourceURI(URI="adi://org.adichatz.studio/editor/ScenarioEditor")
public class ScenarioEditor extends FormEditorCore {
	/**
	 * Instantiates a new ScenarioEditor.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param partController
	 *            the part controller
	 * @param paramMap
	 *            the param map
	 */
	public ScenarioEditor(AdiPluginResources pluginResources, IContainerController partController, ParamMap paramMap) {
		super(pluginResources, "ScenarioEditor", "editor", paramMap);
		this.coreController = partController;
		
		
		addCustomization("featuresTBM:generateScenarioAction", new AControlListener("Customization#1", IEventType.AFTER_INITIALIZE) {
			@Override
			public void handleEvent(AdiEvent event) {
				BooleanSupplier evaluatorVALID;
				evaluatorVALID = new BooleanSupplier() {
					public boolean getAsBoolean() {
						return false;
					}
				};
				controller.addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID, evaluatorVALID));
			}
		});
		((AFormEditor) partController).setBindingService(new XjcBindingService((AFormEditor) partController));
	}

	/**
	 * Create contents for FormPage
	 */
	public void createContents(){
		// adds formPage headers or includes.
		formPageHeaderMap.put("modelPage", new FormPageHeader(context, "org.adichatz.studio.gencode.editor.ScenarioEditorModelPage", AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "scenario.model.page"),"org.adichatz.engine.indigo.editor.AdiFormPage"));
		pageIds.add("modelPage");
		formPageHeaderMap.put("generationPage", new FormPageHeader(context, "org.adichatz.studio.gencode.editor.ScenarioEditorGenerationPage", AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "scenario.generation.page"),"org.adichatz.engine.indigo.editor.AdiFormPage"));
		pageIds.add("generationPage");
		formPageHeaderMap.put("featuresPage", new FormPageHeader(context, "org.adichatz.studio.gencode.editor.ScenarioEditorFeaturesPage", AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "scenario.features.page"),"org.adichatz.engine.indigo.editor.AdiFormPage"));
		pageIds.add("featuresPage");
		formPageHeaderMap.put("customPage", new FormPageHeader(context, "org.adichatz.studio.gencode.editor.ScenarioEditorCustomPage", AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "scenario.custom.page"),"org.adichatz.engine.indigo.editor.AdiFormPage"));
		pageIds.add("customPage");
		firePostOpenPart();
	}
}