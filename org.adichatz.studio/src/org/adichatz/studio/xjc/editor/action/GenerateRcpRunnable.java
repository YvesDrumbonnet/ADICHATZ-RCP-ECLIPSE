package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.RcpPartType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ComponentGeneration;
import org.adichatz.scenario.generation.UIComponentGeneration;
import org.adichatz.scenario.util.AdiPropertyTester;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.editor.ScenarioFormEditor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.pde.internal.core.iproduct.IProductPlugin;
import org.eclipse.pde.internal.core.product.ProductPlugin;
import org.eclipse.pde.internal.core.product.WorkspaceProductModel;

public class GenerateRcpRunnable implements Runnable {
	private ScenarioFormEditor editor;

	private Map<String, Boolean> flagMap;

	private GenerationScenarioWrapper missingGS;

	private String customizationScenarioFileName;

	public GenerateRcpRunnable(ScenarioFormEditor editor, GenerationScenarioWrapper missingGS, String customizationScenarioFileName,
			Map<String, Boolean> flagMap) {
		this.editor = editor;
		this.flagMap = flagMap;
		this.missingGS = missingGS;
		this.customizationScenarioFileName = customizationScenarioFileName;
	}

	@SuppressWarnings("restriction")
	@Override
	public void run() {
		if (!flagMap.get("generateRcpPart"))
			return;
		ScenarioResources scenarioResources = editor.getScenarioResources();
		scenarioResources.getPluginResources().getPluginEntityTree(); // Initialized PluginEntityTree if needed
		ComponentGeneration componentGeneration = scenarioResources.getComponentGeneration();
		UIComponentGeneration uiComponentGeneration = componentGeneration.getUIComponentGeneration();
		// Set pluginResources to be synchronized wjth last changes
		scenarioResources.setPluginResources(AdichatzApplication.getPluginResources(scenarioResources.getPluginName()));

		ScenarioTreeWrapper scenarioTree = (ScenarioTreeWrapper) editor.getTreeWrapper(false, false);
		ScenarioTreeWrapper customizationScenarioTree = null;
		if (null != customizationScenarioFileName) {
			customizationScenarioTree = ScenarioUtil.getCustomScenarioTree(scenarioResources, customizationScenarioFileName);
			scenarioTree.mergeCustomization(scenarioResources, customizationScenarioTree, IPluginEntityScenario.INIT);
			scenarioResources.loadParams(true);
		}

		RcpPartType rcpPart = new RcpPartType();
		scenarioTree.getGenerationScenario().setRcpPart(rcpPart);
		if (flagMap.get("initializeRcpPart")) {
			boolean hasNavigator = flagMap.get("hasNavigator");
			rcpPart.setNavigator(hasNavigator);
			uiComponentGeneration.initializeRcp(hasNavigator);
			scenarioResources.incrementalBuildProject();
			scenarioResources.setImpactRcp(null);
		}
		if (flagMap.get("generateApplication")) {
			rcpPart.setApplication(true);
			uiComponentGeneration.buildApplicationFiles(true);
		}

		if (null != customizationScenarioTree) {
			scenarioTree.mergeCustomization(scenarioResources, customizationScenarioTree, IPluginEntityScenario.RCP_PART);
			scenarioResources.marshalScenarioFile();
		}

		if (flagMap.get("generateNavigator"))
			for (GenerationUnitType generationUnit : scenarioResources.getGenerationScenario().getNavigatorGenerationUnit())
				uiComponentGeneration.buildNavigator(generationUnit);

		if (flagMap.get("missingComponents")) {
			if (flagMap.get("intialProcess")) {
				componentGeneration.generate(GenerationEnum.DETAIL, true, true, true);
				componentGeneration.generate(GenerationEnum.TABLE, true, true, true);
				componentGeneration.generate(GenerationEnum.EDITOR, true, true, true);
				componentGeneration.generate(GenerationEnum.MESSAGE_BUNDLE, true, true, true);
			} else
				componentGeneration.generateAll(missingGS, false);
		}

		componentGeneration.refreshProject();
		if (flagMap.get("generateApplication")) {
			// Add model plugins to product files
			List<String> modelPlugins = new ArrayList<String>();
			for (String bundleName : ScenarioUtil.getRequiredBundleNames(scenarioResources.getManifestChanger())) {
				Object receiver = Platform.getBundle(bundleName);
				if (null == receiver) {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(bundleName);
					if (null == project || !project.exists())
						throw new RuntimeException(getFromGeneratorBundle("generation.invalid.bundleName", bundleName));
					receiver = project;
				}
				if (AdiPropertyTester.hasModelAspect(receiver))
					modelPlugins.add(bundleName);
			}
			if (!modelPlugins.isEmpty()) {
				try {
					IProject project = scenarioResources.getProject();
					String productFileName = project.getName() + ".product";
					scenarioResources.getAffectedFiles().add(scenarioResources.getProject().getFile(productFileName));
					WorkspaceProductModel productModel = new WorkspaceProductModel(project.getFile(productFileName), true);
					productModel.load();
					for (String modelPluginName : modelPlugins) {
						IProductPlugin plugin = new ProductPlugin(productModel);
						plugin.setId(modelPluginName);
						productModel.getProduct().addPlugins(new IProductPlugin[] { plugin });
					}
					productModel.save();
				} catch (CoreException e) {
					logError(e);
				}
			}
			componentGeneration.buildBuildPropertiesFile();
		}
	}
}
