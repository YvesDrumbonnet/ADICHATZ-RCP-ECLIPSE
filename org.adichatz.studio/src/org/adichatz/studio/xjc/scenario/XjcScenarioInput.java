package org.adichatz.studio.xjc.scenario;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;

public class XjcScenarioInput extends ScenarioInput {
	private Set<String> generatedEntities = new HashSet<String>();

	private File wrapperFactoryFile;

	public XjcScenarioInput(ScenarioInput scenarioInput) {
		this(scenarioInput.getScenarioResources(), scenarioInput.getTreeId(), scenarioInput.getSubPackage());
		this.pluginEntity = scenarioInput.getPluginEntity();
	}

	public XjcScenarioInput(ScenarioResources scenarioResources, String treeId, String subPackage) {
		super(scenarioResources, treeId, subPackage);
		wrapperFactoryFile = new File(
				FileUtil.getPluginHome("org.adichatz.generator") + "/src/generator/org/adichatz/generator/xjc/WrapperFactory.java");
		if (!wrapperFactoryFile.exists())
			throw new RuntimeException(wrapperFactoryFile.getAbsolutePath().concat(" does not exist!"));
	}

	public XjcScenarioInput(XjcScenarioInput scenarioInput, PluginEntity pluginEntity) {
		super(scenarioInput, null);
		this.generatedEntities = scenarioInput.generatedEntities;
		this.wrapperFactoryFile = scenarioInput.wrapperFactoryFile;
		setInput(pluginEntity);
	}

	public File getWrapperFactoryFile() {
		return wrapperFactoryFile;
	}

	public Set<String> getGeneratedEntities() {
		return generatedEntities;
	}
}
