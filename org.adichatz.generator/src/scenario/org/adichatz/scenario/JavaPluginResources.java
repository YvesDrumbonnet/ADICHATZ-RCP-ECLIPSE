package org.adichatz.scenario;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.scenario.util.PlainPluginResources;

public class JavaPluginResources extends PlainPluginResources {
	public JavaPluginResources(String pluginHome, String pluginName, String pluginPackage, GencodePath gencodePath) {
		super(pluginHome, pluginName, pluginPackage, gencodePath);
	}

	public Object getConfigTree(String configFileName, boolean inspectPrevious) {
		if (inspectPrevious && configWrappers.containsKey(configFileName))
			return configWrappers.get(configFileName);
		try {
			InputStream inputStream = null;
			if (this.equals(AdichatzApplication.getInstance().getApplicationPluginResources())) {
				File configFile = new File(EngineConstants.getAdiPermanentDirName().concat("/").concat(configFileName));
				if (configFile.exists()) {
					inputStream = new FileInputStream(configFile);
				}
			}
			if (null == inputStream) {
				File configFile = new File(FileUtil.getPluginHome(pluginName).concat("/").concat(EngineConstants.XML_FILES_PATH)
						.concat(configFileName));
				inputStream = new FileInputStream(configFile);
			}
			if (null != inputStream) {
				Object wrapperTree = EngineTools.getUnmarshaller().unmarshal(inputStream);
				inputStream.close();
				if (inspectPrevious)
					configWrappers.put(configFileName, wrapperTree);
				return wrapperTree;
			}
		} catch (JAXBException | IOException e) {
			logError(e);
		}
		if (inspectPrevious)
			configWrappers.put(configFileName, null);
		return null;
	}

}
