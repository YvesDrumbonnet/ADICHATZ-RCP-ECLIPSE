package org.adichatz.studio.xjc.scenario;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.PluginEntityTreeGenerator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.studio.util.StudioUtil;

public class XjcPluginEntityTreeGenerator extends PluginEntityTreeGenerator {
	private Map<String, String> uriClassNameMap;

	public XjcPluginEntityTreeGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput);
	}

	@Override
	protected void preCreateClassFile() {
		super.preCreateClassFile();
		uriClassNameMap = new HashMap<>();
		for (File file : StudioUtil.getXjcDirFile().listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".java")) {
					return true;
				}
				return false;
			}
		})) {
			String fileName = file.getName();
			String entityId = fileName.substring(0, fileName.length() - 5);
			String entityClassName = "org.adichatz.generator.xjc." + entityId;
			Class<?> xjcClass = scenarioInput.getScenarioResources().getPluginResources().getGencodePath()
					.getClazz(entityClassName);
			if (null != xjcClass //
					&& null != xjcClass.getAnnotation(XmlType.class) //
					&& !xjcClass.getSuperclass().equals(Enum.class) //
					&& !XjcPluginEntityScenario.EXCLUDED_CLASSES.contains(xjcClass.getName())) {
				String entityURI = entityId;
				if (entityId.endsWith("Type"))
					entityURI = entityId.substring(0, entityId.length() - 4);
				uriClassNameMap.put("adi://org.adichatz.studio/model/".concat(entityURI).concat("MM"), entityClassName);
			}
		}
	}

	@Override
	protected void addon(ScenarioResources modelSR, PluginEntityWrapper pluginEntity, String entityName) throws IOException {
		classBodyBuffer.append(
				"pluginEntityClassMap.put(\"" + uriClassNameMap.get(pluginEntity.getEntityURI()) + "\", " + entityName + ");");
	}

	@Override
	protected void addon() throws IOException {
		super.addon();
		classBodyBuffer.append("");
		classBodyBuffer.append("@Override");
		classBodyBuffer.appendPlus("public " + getObjectName(PluginEntity.class) + " getPluginEntity(Class<?> beanClass) {");
		classBodyBuffer.append("String entityId = beanClass.getSimpleName();");
		classBodyBuffer.appendPlus("if (entityId.endsWith(\"Type\"))");
		classBodyBuffer.append("entityId = entityId.substring(0, entityId.length() - 4);");
		classBodyBuffer.addIdent(-1);
		classBodyBuffer.appendPlus("else if (entityId.endsWith(\"Wrapper\"))");
		classBodyBuffer.append("entityId = entityId.substring(0, entityId.length() - 7);");
		classBodyBuffer.addIdent(-1);
		classBodyBuffer.append("return pluginEntityURIMap.get(\"adi://" + GeneratorConstants.STUDIO_BUNDLE
				+ "/model/\".concat(entityId).concat(\"MM\"));");
		classBodyBuffer.appendMinus("}");
	}
}
