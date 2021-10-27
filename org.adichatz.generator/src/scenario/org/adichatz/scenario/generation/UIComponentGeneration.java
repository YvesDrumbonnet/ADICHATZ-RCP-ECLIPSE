/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.scenario.generation;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.xjc.AdichatzRcpConfigTree;
import org.adichatz.engine.xjc.LoginTemplateEnum;
import org.adichatz.engine.xjc.LoginType;
import org.adichatz.engine.xjc.ParamType;
import org.adichatz.engine.xjc.RcpConfigurationType;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ActionWhenTypeEnum;
import org.adichatz.generator.xjc.AddWhenEnum;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.PathElementEnum;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.INavigatorScenario;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

// TODO: Auto-generated Javadoc
/**
 * The Class UIComponentGeneration.
 */
public class UIComponentGeneration {

	/** The component generation. */
	private ComponentGeneration componentGeneration;

	/** The scenario resources. */
	private ScenarioResources scenarioResources;

	/** The template dir name. */
	private String templateDirName;

	/** The plugin home. */
	private String pluginHome;

	/** The plugin name. */
	private String pluginName;

	/** The plugin package. */
	private String pluginPackage;

	/** The src path name. */
	private String srcPathName;

	private IProgressMonitor monitor;

	/**
	 * Instantiates a new UI component generation.
	 *
	 * @param componentGeneration
	 *            the component generation
	 */
	public UIComponentGeneration(ComponentGeneration componentGeneration, IProgressMonitor monitor) {
		this.componentGeneration = componentGeneration;
		this.scenarioResources = componentGeneration.getScenarioResources();
		this.monitor = monitor;
		templateDirName = componentGeneration.getTemplateDirName().concat("/");
		pluginHome = scenarioResources.getPluginHome();
		pluginName = scenarioResources.getPluginName();
		pluginPackage = scenarioResources.getPluginPackage();
		srcPathName = "src/" + pluginPackage.replace('.', '/');
		scenarioResources.createFolderIfNotExist(srcPathName);
	}

	/**
	 * Generate Navigator.
	 *
	 * @param navigatorGU
	 *            the navigator Generation Unit
	 */
	public void buildNavigator(GenerationUnitType navigatorGU) {
		INavigatorScenario navigatorScenario = (INavigatorScenario) scenarioResources.getGencodePath().instantiateClass(
				scenarioResources.getComponentClass(navigatorGU.getScenarioClassName()), new Class[] { GenerationUnitType.class },
				new Object[] { navigatorGU });

		if (null != navigatorScenario) {
			String[] instanceKey = EngineTools.getInstanceKeys(navigatorGU.getAdiResourceURI());
			scenarioResources.createFolderIfNotExist(EngineConstants.RESOURCE_MESSAGE_DIR);
			ScenarioInput scenarioInput = new ScenarioInput(scenarioResources, instanceKey[2], instanceKey[1], true, true, true,
					true);
			navigatorScenario.createNavigatorTree(scenarioInput, scenarioResources.getGenerationScenario().getPluginEntity());
			navigatorScenario.createNavigatorBundle(scenarioResources.getGenerationScenario().getPluginEntity());
		}
	}

	/**
	 * Initialize rcp.
	 *
	 * @param menu
	 *            the menu
	 */
	public void initializeRcp(boolean menu) {
		scenarioResources.getScenarioTree().actionResource(scenarioResources, ActionWhenTypeEnum.WHEN_BUILDING_UI);
		GenerationScenarioWrapper generationScenario = (GenerationScenarioWrapper) scenarioResources.getScenarioTree()
				.getGenerationScenario();

		generationScenario.addGenerationUnit(scenarioResources, GenerationEnum.DETAIL);
		generationScenario.addGenerationUnit(scenarioResources, GenerationEnum.TABLE);
		generationScenario.addGenerationUnit(scenarioResources, GenerationEnum.EDITOR);
		generationScenario.addGenerationUnit(scenarioResources, GenerationEnum.MESSAGE_BUNDLE);
		if (menu)
			generationScenario.addGenerationUnit(scenarioResources, GenerationEnum.NAVIGATOR);
		else {
			int size = generationScenario.getGenerationUnit().size();
			for (GenerationUnitType generationUnit : generationScenario.getGenerationUnit().toArray(new GenerationUnitType[size]))
				if (generationUnit.getType() == GenerationEnum.NAVIGATOR)
					generationScenario.getGenerationUnit().remove(generationUnit);
		}
		for (PluginEntityType pluginEntity : generationScenario.getPluginEntity()) {
			ScenarioResources modelSR = ScenarioResources.getInstance(((PluginEntityWrapper) pluginEntity).getEntityKeys()[0],
					scenarioResources.getPluginName());
			// Populates pluginEntity with pluginEntityScenario of entityPart
			scenarioResources.getPluginEntityScenario().populatePluginEntity(modelSR, (PluginEntityWrapper) pluginEntity,
					IPluginEntityScenario.RCP_PART, null);
		}
		scenarioResources.marshalScenarioFile();

		componentGeneration.generatePluginEntityTreeClass();
		componentGeneration.buildActivatorFile();
		componentGeneration.buildManifestFile();
	}

	/**
	 * Builds the application files.
	 *
	 * @param force
	 *            the force
	 */
	public void buildApplicationFiles(boolean force) {
		componentGeneration.subTask(getFromGeneratorBundle("generation.application.files"));
		try {
			for (String fileName : ScenarioTreeWrapper.CONFIG_FILE_NAMES) {
				FileUtil.copyFile(templateDirName + "config/" + fileName, pluginHome + "/" + fileName,
						new String[] { "#{adichatz.project.name}", "#{adichatz.package.name}" },
						new String[] { pluginName, pluginPackage }, force);
			}

			// Generate ${pluginName}.product
			new File(pluginHome + "/resources/build").mkdirs();
			File workFile = new File(pluginHome + "/resources/build/work.product");
			File targetFile = new File(pluginHome + "/" + pluginName + ".product");
			logInfo(getFromGeneratorBundle("generation.build.product", targetFile.getName()));

			try {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				Document doc = docBuilder.newDocument();
				ProcessingInstruction processingInstruction = doc.createProcessingInstruction("pde", "data=\"3.5\"");
				doc.appendChild(processingInstruction);
				doc.appendChild(doc.createComment("#Carriage Return#")); // replaced by carriage return below to avoid ident problem

				// root elements
				Element rootElement = doc.createElement("product");
				doc.appendChild(rootElement);
				ScenarioUtil.addAttrribute(doc, rootElement, "name", pluginName);
				ScenarioUtil.addAttrribute(doc, rootElement, "id", pluginName.concat(".product"));
				ScenarioUtil.addAttrribute(doc, rootElement, "application", "org.eclipse.e4.ui.workbench.swt.E4Application");
				ScenarioUtil.addAttrribute(doc, rootElement, "version", "1.0.0.qualifier");
				ScenarioUtil.addAttrribute(doc, rootElement, "useFeatures", "false");
				ScenarioUtil.addAttrribute(doc, rootElement, "includeLaunchers", "true");

				Element configIni = doc.createElement("configIni");
				ScenarioUtil.addAttrribute(doc, configIni, "use", "default");
				rootElement.appendChild(configIni);

				Element launcherArgs = doc.createElement("launcherArgs");
				ScenarioUtil.addAttrribute(doc, configIni, "use", "default");
				rootElement.appendChild(launcherArgs);
				{
					Element vmArgsMac = doc.createElement("vmArgsMac");
					vmArgsMac.setTextContent("-XstartOnFirstThread -Dorg.eclipse.swt.internal.carbon.smallFonts");
					launcherArgs.appendChild(vmArgsMac);
				}
				Element splash = doc.createElement("splash");
				ScenarioUtil.addAttrribute(doc, splash, "location", pluginName);
				ScenarioUtil.addAttrribute(doc, splash, "startupProgressRect", "5,275,445,15");
				rootElement.appendChild(splash);

				Element launcher = doc.createElement("launcher");
				ScenarioUtil.addAttrribute(doc, launcher, "name", "adichatz");
				splash.appendChild(launcher);
				{
					Element solaris = doc.createElement("solaris");
					launcher.appendChild(solaris);

					Element win = doc.createElement("win");
					ScenarioUtil.addAttrribute(doc, win, "useIco", "false");
					launcher.appendChild(win);
					win.appendChild(doc.createElement("bmp"));
				}
				rootElement.appendChild(launcher);

				rootElement.appendChild(doc.createElement("vm"));

				Element plugins = doc.createElement("plugins");
				rootElement.appendChild(plugins);

				Version version = Platform.getBundle("org.eclipse.platform").getVersion();
				if (4 != version.getMajor())
					logError(getFromGeneratorBundle("generation.build.product.invalid.version"));
				int minor = version.getMinor();
				boolean hasJakartaVersion = null != Platform.getBundle("jakarta.xml.bind"); // Java version >= 9 and use jakarta plugin for JEE JAXB. 
				Element applicationPlugin = doc.createElement("plugin");
				ScenarioUtil.addAttrribute(doc, applicationPlugin, "id", pluginName);
				plugins.appendChild(applicationPlugin);
				/*
				 * Create a map: Key=plginName, Value=True if add fragment="True" to plusng declaration
				 */
				Map<String, Boolean> pluginMap = new HashMap<>();
				pluginMap.put("com.ibm.icu", false);
				if (hasJakartaVersion) {
					pluginMap.put("com.sun.jna", false);
					pluginMap.put("com.sun.jna.transform", false);
					pluginMap.put("com.sun.xml.bind", false);
					pluginMap.put("jakarta.xml.bind", false);
					pluginMap.put("javax.activation", false);
				}
				pluginMap.put("javax.annotation", false);
				pluginMap.put("javax.inject", false);
				if (minor < 16)
					pluginMap.put("javax.xml", false);
				if (hasJakartaVersion) {
					pluginMap.put("javax.xml", false);
					pluginMap.put("javax.xml.datatype", false);
					pluginMap.put("javax.xml.namespace", false);
					pluginMap.put("javax.xml.parsers", false);
					pluginMap.put("javax.xml.transform", false);
					pluginMap.put("javax.xml.transform.dom", false);
					pluginMap.put("javax.xml.transform.sax", false);
					pluginMap.put("javax.xml.transform.stream", false);
					pluginMap.put("javax.xml.validation", false);
				}
				pluginMap.put("org.adichatz.common", false);
				pluginMap.put("org.adichatz.css.theme", false);
				pluginMap.put("org.adichatz.engine", false);
				pluginMap.put("org.adichatz.engine.e4", false);
				pluginMap.put("org.adichatz.generator", false);
				pluginMap.put("org.adichatz.jpa", false);
				pluginMap.put("org.adichatz.resources", false);
				pluginMap.put("org.adichatz.testing", false);
				pluginMap.put("org.adichatz.tool", false);
				pluginMap.put("org.apache.ant", false);
				if (minor >= 8)
					pluginMap.put("org.apache.batik.constants", false);
				pluginMap.put("org.apache.batik.css", false);
				if (minor >= 8)
					pluginMap.put("org.apache.batik.i18n", false);
				pluginMap.put("org.apache.batik.util", false);
				// if (minor >= 8)
				// pluginMap.put("org.apache.batik.util.gui", false);
				pluginMap.put("org.apache.commons.jxpath", false);
				if (minor >= 8)
					pluginMap.put("org.apache.commons.logging", false);
				if (minor < 16) {
					pluginMap.put("org.apache.felix.gogo.command", false);
					pluginMap.put("org.apache.felix.gogo.runtime", false);
				}
				pluginMap.put("org.apache.felix.scr", false);
				if (minor >= 8) {
					pluginMap.put("org.apache.commons.io", false);
					pluginMap.put("org.apache.xmlgraphics", false);
				}
				if (minor < 16)
					pluginMap.put("org.eclipse.compare.core", false);
				pluginMap.put("org.eclipse.core.commands", false);
				pluginMap.put("org.eclipse.core.contenttype", false);
				pluginMap.put("org.eclipse.core.databinding", false);
				if (minor >= 16)
					pluginMap.put("org.eclipse.core.databinding.beans", false);
				pluginMap.put("org.eclipse.core.databinding.observable", false);
				pluginMap.put("org.eclipse.core.databinding.property", false);
				pluginMap.put("org.eclipse.core.expressions", false);
				pluginMap.put("org.eclipse.core.filesystem", false);
				if (minor < 16)
					pluginMap.put("org.eclipse.core.filesystem.win32.x86_64", true);
				pluginMap.put("org.eclipse.core.jobs", false);
				pluginMap.put("org.eclipse.core.resources", false);
				if (minor < 16)
					pluginMap.put("org.eclipse.core.resources.win32.x86_64", true);
				pluginMap.put("org.eclipse.core.runtime", false);
				if (minor < 16)
					pluginMap.put("org.eclipse.core.variables", false);
				pluginMap.put("org.eclipse.e4.core.commands", false);
				pluginMap.put("org.eclipse.e4.core.contexts", false);
				pluginMap.put("org.eclipse.e4.core.di", false);
				pluginMap.put("org.eclipse.e4.core.di.annotations", false);
				pluginMap.put("org.eclipse.e4.core.di.extensions", false);
				pluginMap.put("org.eclipse.e4.core.di.extensions.supplier", false);
				pluginMap.put("org.eclipse.e4.core.services", false);
				pluginMap.put("org.eclipse.e4.emf.xpath", false);
				pluginMap.put("org.eclipse.e4.ui.bindings", false);
				pluginMap.put("org.eclipse.e4.ui.css.core", false);
				pluginMap.put("org.eclipse.e4.ui.css.swt", false);
				pluginMap.put("org.eclipse.e4.ui.css.swt.theme", false);
				pluginMap.put("org.eclipse.e4.ui.di", false);
				if (minor >= 14)
					pluginMap.put("org.eclipse.e4.ui.dialogs", false);
				pluginMap.put("org.eclipse.e4.ui.model.workbench", false);
				pluginMap.put("org.eclipse.e4.ui.services", false);
				pluginMap.put("org.eclipse.e4.ui.widgets", false);
				pluginMap.put("org.eclipse.e4.ui.workbench", false);
				pluginMap.put("org.eclipse.e4.ui.workbench.addons.swt", false);
				pluginMap.put("org.eclipse.e4.ui.workbench.renderers.swt", false);
				pluginMap.put("org.eclipse.e4.ui.workbench.swt", false);
				pluginMap.put("org.eclipse.e4.ui.workbench3", false);
				pluginMap.put("org.eclipse.emf.common", false);
				pluginMap.put("org.eclipse.emf.ecore", false);
				pluginMap.put("org.eclipse.emf.ecore.change", false);
				if (minor >= 16)
					pluginMap.put("org.eclipse.emf.ecore.databinding", false);
				pluginMap.put("org.eclipse.emf.ecore.xmi", false);
				pluginMap.put("org.eclipse.equinox.app", false);
				pluginMap.put("org.eclipse.equinox.common", false);
				if (minor > 10 && minor < 16)
					pluginMap.put("org.eclipse.equinox.ds", false);
				if (minor >= 16)
					pluginMap.put("org.eclipse.equinox.concurrent", false);
				pluginMap.put("org.eclipse.equinox.event", false);
				pluginMap.put("org.eclipse.equinox.preferences", false);
				pluginMap.put("org.eclipse.equinox.registry", false);
				if (minor > 10 && minor < 16)
					pluginMap.put("org.eclipse.equinox.util", false);
				pluginMap.put("org.eclipse.help", false);
				pluginMap.put("org.eclipse.jdt.core", false);
				pluginMap.put("org.eclipse.jface", false);
				pluginMap.put("org.eclipse.jface.databinding", false);
				if (hasJakartaVersion)
					pluginMap.put("org.eclipse.jface.notifications", false);
				pluginMap.put("org.eclipse.jface.text", false);
				pluginMap.put("org.eclipse.osgi", false);
				pluginMap.put("org.eclipse.osgi.compatibility.state", true);
				pluginMap.put("org.eclipse.osgi.services", false);
				pluginMap.put("org.eclipse.osgi.util", false);
				pluginMap.put("org.eclipse.swt", false);
				pluginMap.put("org.eclipse.swt.win32.win32.x86_64", true);
				pluginMap.put("org.eclipse.text", false);
				pluginMap.put("org.eclipse.ui", false);
				pluginMap.put("org.eclipse.ui.forms", false);
				pluginMap.put("org.eclipse.ui.navigator", false);
				pluginMap.put("org.eclipse.ui.views", false);
				pluginMap.put("org.eclipse.ui.views.log", false);
				pluginMap.put("org.eclipse.ui.workbench", false);
				if (minor > 10 && minor < 16)
					pluginMap.put("org.eclipse.ui.workbench.texteditor", false);
				else
					pluginMap.put("org.eclipse.urischeme", false);
				pluginMap.put("org.w3c.css.sac", false);
				pluginMap.put("org.w3c.dom.events", false);
				pluginMap.put("org.w3c.dom.smil", false);
				pluginMap.put("org.w3c.dom.svg", false);
				if (null != scenarioResources.getScenarioTree().getPathElements()) {
					for (PathElementType pathElement : ScenarioUtil.getPathElements(scenarioResources,
							scenarioResources.getScenarioTree().getPathElements().getPathElement())) {
						if (pathElement.isAddToManifestFile() && (PathElementEnum.PLUGIN == pathElement.getType()
								|| PathElementEnum.PROJECT == pathElement.getType())) {
							pluginMap.put(ScenarioUtil.evalLocation(scenarioResources.getBuffer(), pathElement.getLocation()),
									false);
						}
					}
				}
				for (Map.Entry<String, Boolean> entry : pluginMap.entrySet()) {
					Element plugin = doc.createElement("plugin");
					ScenarioUtil.addAttrribute(doc, plugin, "id", entry.getKey());
					if (entry.getValue())
						ScenarioUtil.addAttrribute(doc, plugin, "fragment", "true");
					plugins.appendChild(plugin);
				}
				if (minor >= 13 && minor <= 15) {
					Element configurations = doc.createElement("configurations");
					rootElement.appendChild(configurations);
					addPluginConfigurations(doc, configurations, "org.apache.felix.scr", "2");
					addPluginConfigurations(doc, configurations, "org.eclipse.core.runtime", "0");
					addPluginConfigurations(doc, configurations, "org.eclipse.equinox.common", "2");
					addPluginConfigurations(doc, configurations, "org.eclipse.equinox.event", "2");
				}

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				transformerFactory.setAttribute("indent-number", 4);

				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				DOMSource source = new DOMSource(doc);
				FileOutputStream fileOutputStream = new FileOutputStream(workFile);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
				StreamResult result = new StreamResult(outputStreamWriter);
				transformer.transform(source, result);
				FileUtil.copyFile(workFile, targetFile, new String[] { "<!--#Carriage Return#-->" }, new String[] { "\n" }, true);
				outputStreamWriter.close();
				fileOutputStream.close();
				workFile.delete();
			} catch (ParserConfigurationException | TransformerException | IOException e) {
				logError(e);
			}

			// Generate StartupLifeCycleHandler.java
			new StartLifeCycleGenerator().generateCU(scenarioResources);
			// Copy resources/css and resources/icons directories
			FileUtil.copyDirectory(new File(templateDirName + "resources/"), new File(pluginHome + "/resources"));

			FileUtil.copyFile(templateDirName + "src/MockLogin.java", pluginHome + "/" + srcPathName + "/MockLogin.java",
					new String[] { "#{adichatz.project.name}", "#{adichatz.package.name}" },
					new String[] { pluginName, pluginPackage }, force);

			buildAdichatzRcpConfigFile();

			new ActivatorGenerator().generateCU(scenarioResources);
			scenarioResources.getScenarioTree().processAdditionalLibraries(scenarioResources, AddWhenEnum.UI);
		} catch (IOException e) {
			logError(e);
		}
	}

	private void addPluginConfigurations(Document doc, Element configurations, String id, String startLevel) {
		Element plugin = doc.createElement("plugin");
		ScenarioUtil.addAttrribute(doc, plugin, "id", id);
		ScenarioUtil.addAttrribute(doc, plugin, "autoStart", "true");
		ScenarioUtil.addAttrribute(doc, plugin, "startLevel", startLevel);
		configurations.appendChild(plugin);

	}

	/**
	 * Builds the adichatz rcp config file ($PROJECT/resources/xml/AdichatzRcpConfig.xml).
	 */
	public void buildAdichatzRcpConfigFile() {
		componentGeneration.subTask(getFromGeneratorBundle("generation.build.adichatzRcpConfig"));
		IFile configFile = scenarioResources.getXmlFolder().getFile("AdichatzRcpConfig.xml");
		if (!configFile.exists()) {
			try {
				configFile.create(new ByteArrayInputStream(new byte[0]), IResource.NONE, monitor);
				logInfo(getFromGeneratorBundle("generation.build.adichatzRcpConfig"));
				AdichatzRcpConfigTree configTree = new AdichatzRcpConfigTree();

				RcpConfigurationType configuration = new RcpConfigurationType();
				configTree.setRcpConfiguration(configuration);
				for (org.adichatz.generator.xjc.ParamType prm : scenarioResources.getScenarioTree().getParams().getParam())
					if (EngineConstants.RUNTIME.equals(prm.getType())) {
						ParamType param = new ParamType();
						param.setId(prm.getId());
						param.setValue(prm.getValue());
						configuration.getParam().add(param);
					}
				LoginType login = new LoginType();
				configTree.setLogin(login);
				login.setLoginClassName(pluginPackage + ".MockLogin");
				login.setLoginTemplate(LoginTemplateEnum.DIALOG);
				ScenarioUtil.createXmlFile(configFile, configTree, scenarioResources.getGenerationScenario());
			} catch (CoreException e) {
				logError(e);
			}
		}
	}
}
