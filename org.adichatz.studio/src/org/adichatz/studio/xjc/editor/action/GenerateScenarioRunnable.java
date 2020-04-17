package org.adichatz.studio.xjc.editor.action;

import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ComponentGeneration;

public class GenerateScenarioRunnable implements Runnable {
	private GenerationScenarioWrapper generationScenario;

	private ScenarioResources scenarioResources;

	public GenerateScenarioRunnable(GenerationScenarioWrapper generationScenario, ScenarioResources scenarioResources) {
		this.generationScenario = generationScenario;
		this.scenarioResources = scenarioResources;
	}

	@Override
	public void run() {
		scenarioResources.getGencodePath().defineClassLoader(scenarioResources.getPluginResources().getParentClassLoader());
		scenarioResources.getPluginResources().getPluginEntityTree(); // Initialized PluginEntityTree if needed
		ComponentGeneration componentGeneration = scenarioResources.getComponentGeneration();
		componentGeneration.generateAll(generationScenario, true);
	}

}
