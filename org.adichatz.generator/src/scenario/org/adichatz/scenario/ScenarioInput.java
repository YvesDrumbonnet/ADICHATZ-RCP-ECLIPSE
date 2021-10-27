/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 *
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.scenario;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.internal.IColumnWrapper;
import org.adichatz.generator.wrapper.internal.IRootWrapper;
import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioInput.
 */
public class ScenarioInput {

	/** The xml element. */
	private Object xmlElement;

	/** The scenario resources. */
	private ScenarioResources scenarioResources;

	/** The plugin entity. */
	protected PluginEntity<?> pluginEntity;

	/** The plugin entity Wrapper. */
	protected PluginEntityWrapper pluginEntityWrapper;

	/** The tree id. */
	protected String treeId;

	/** The sub package. */
	protected String subPackage;

	/** The compile. */
	boolean generateXml, generateJava, compile;

	/** The bundle translation. */
	private Map<String, String> bundleTranslation = new HashMap<String, String>();

	/** The register. */
	private Map<String, RegisterEntry<?>> register = new HashMap<String, RegisterEntry<?>>();

	/** The root wrapper. */
	private IRootWrapper rootWrapper;

	/** The column wrapper. */
	private IColumnWrapper columnWrapper;

	/** The model sr. */
	private ScenarioResources modelSR;

	/** The fire resource change event. */
	private boolean fireResourceChangeEvent;

	/** The xml file. */
	private IFile xmlFile;

	/** The adi resource uri. */
	private String adiResourceURI;

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param xmlElement
	 *            the xml element
	 */
	public ScenarioInput(ScenarioInput scenarioInput, Object xmlElement) {
		this.scenarioResources = scenarioInput.scenarioResources;
		this.generateXml = scenarioInput.generateXml;
		this.generateJava = scenarioInput.generateJava;
		this.compile = scenarioInput.compile;
		this.treeId = scenarioInput.treeId;
		this.subPackage = scenarioInput.subPackage;
		this.pluginEntity = scenarioInput.pluginEntity;
		this.xmlElement = xmlElement;
		this.bundleTranslation = scenarioInput.bundleTranslation;
		this.register = scenarioInput.register;
		this.rootWrapper = scenarioInput.rootWrapper;
		this.columnWrapper = scenarioInput.columnWrapper;
	}

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param xmlElement
	 *            the xml element
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 */
	public ScenarioInput(ScenarioInput scenarioInput, Object xmlElement, String treeId, String subPackage) {
		this(scenarioInput, xmlElement);
		this.treeId = treeId;
		this.subPackage = subPackage;
	}

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param xmlElement
	 *            the xml element
	 * @param adiResourceURI
	 *            the adi resource uri
	 */
	public ScenarioInput(ScenarioInput scenarioInput, Object xmlElement, String adiResourceURI) {
		this(scenarioInput, xmlElement);
		String[] instanceKeys = EngineTools.getInstanceKeys(adiResourceURI);
		this.treeId = instanceKeys[2];
		this.subPackage = instanceKeys[1];
		if (null != instanceKeys[0] && !scenarioResources.getPluginName().equals(instanceKeys[0]))
			this.scenarioResources = ScenarioResources.getInstance(instanceKeys[0], null);
	}

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 * @param generateXml
	 *            the generate xml
	 * @param generateJava
	 *            the generate java
	 * @param compile
	 *            the compile
	 */
	public ScenarioInput(ScenarioResources scenarioResources, boolean generateXml, boolean generateJava, boolean compile) {
		this.scenarioResources = scenarioResources;
		this.generateXml = generateXml;
		this.generateJava = generateJava;
		this.compile = compile;
	}

	public ScenarioInput(String adiResourceURI) {
		String[] instanceKeys = EngineTools.getInstanceKeys(adiResourceURI);
		this.scenarioResources = ScenarioResources.getInstance(instanceKeys[0], null);
		generateXml = generateJava = compile = true;
		setInput(instanceKeys[2], instanceKeys[1]);
		this.adiResourceURI = adiResourceURI;
	}

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 */
	public ScenarioInput(ScenarioResources scenarioResources, String treeId, String subPackage) {
		this.scenarioResources = scenarioResources;
		generateXml = generateJava = compile = true;
		setInput(treeId, subPackage);
	}

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * @param xmllElement
	 *            the xmll element
	 */
	public ScenarioInput(ScenarioResources scenarioResources, String treeId, String subPackage, Object xmllElement) {
		this(scenarioResources, treeId, subPackage);
		this.xmlElement = xmllElement;
	}

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * @param generateXML
	 *            the generate xml
	 * @param generateJava
	 *            the generate java
	 * @param compile
	 *            the compile
	 */
	public ScenarioInput(ScenarioResources scenarioResources, String treeId, String subPackage, boolean generateXML,
			boolean generateJava, boolean compile, boolean fireResourceChangeEvent) {
		this.scenarioResources = scenarioResources;
		this.generateXml = generateXML;
		this.generateJava = generateJava;
		this.compile = compile;
		this.fireResourceChangeEvent = fireResourceChangeEvent;
		setInput(treeId, subPackage);
	}

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 * @param pluginEntity
	 *            the plugin entity
	 */
	public ScenarioInput(ScenarioResources scenarioResources, PluginEntity<?> pluginEntity) {
		this(scenarioResources, pluginEntity, true, true, true, false);
		treeId = pluginEntity.getEntityKeys()[2];
		subPackage = pluginEntity.getEntityKeys()[1];
	}

	/**
	 * Instantiates a new scenario input.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 * @param pluginEntity
	 *            the plugin entity
	 * @param generateXml
	 *            the generate xml
	 * @param generateJava
	 *            the generate java
	 * @param compile
	 *            the compile
	 * @param fireResourceChangeEvent
	 *            the fire resource change event
	 */
	public ScenarioInput(ScenarioResources scenarioResources, PluginEntity<?> pluginEntity, boolean generateXml,
			boolean generateJava, boolean compile, boolean fireResourceChangeEvent) {
		this.scenarioResources = scenarioResources;
		this.pluginEntity = pluginEntity;
		this.generateXml = generateXml;
		this.generateJava = generateJava;
		this.compile = true;
		this.fireResourceChangeEvent = fireResourceChangeEvent;

	}

	/**
	 * Sets the input.
	 * 
	 * @param pluginEntity
	 *            the plugin entity
	 * @return the scenario input
	 */
	public ScenarioInput setInput(PluginEntity<?> pluginEntity) {
		this.pluginEntity = pluginEntity;
		return setInput(pluginEntity.getEntityKeys()[2], pluginEntity.getEntityKeys()[1]);
	}

	/**
	 * Sets the input.
	 * 
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * @return the scenario input
	 */
	public ScenarioInput setInput(String treeId, String subPackage) {
		this.treeId = treeId;
		this.subPackage = subPackage;
		this.adiResourceURI = null;
		return this;
	}

	/**
	 * Sets the xml element.
	 * 
	 * @param xmlElement
	 *            the xml element
	 * @return the scenario input
	 */
	public ScenarioInput setXmlElement(Object xmlElement) {
		this.xmlElement = xmlElement;
		return this;
	}

	/**
	 * Gets the scenario resources.
	 * 
	 * @return the scenario resources
	 */
	public ScenarioResources getScenarioResources() {
		return scenarioResources;
	}

	/**
	 * Gets the plugin entity.
	 * 
	 * @return the plugin entity
	 */
	public PluginEntity<?> getPluginEntity() {
		return pluginEntity;
	}

	/**
	 * Sets the plugin entity.
	 * 
	 * @param pluginEntity
	 *            the new plugin entity
	 */
	public void setPluginEntity(PluginEntity<?> pluginEntity) {
		this.pluginEntity = pluginEntity;
	}

	/**
	 * Gets the plugin entity wrapper.
	 * 
	 * @return the plugin entity wrapper
	 */
	public PluginEntityWrapper getPluginEntityWrapper() {
		if (null == pluginEntityWrapper && null != pluginEntity) {
			pluginEntityWrapper = getPluginEntityWrapper(pluginEntity.getEntityURI());
		}
		return pluginEntityWrapper;
	}

	/**
	 * Gets the plugin entity wrapper.
	 * 
	 * @param entityURI
	 *            the entity uri
	 * @return the plugin entity wrapper
	 */
	public PluginEntityWrapper getPluginEntityWrapper(String entityURI) {
		return scenarioResources.getPluginEntityWrapper(entityURI);
	}

	/**
	 * Sets the plugin entity.
	 *
	 * @param entityURI
	 *            the new plugin entity
	 */
	public void setPluginEntity(String entityURI) {
		if (EngineTools.isEmpty(entityURI)) {
			pluginEntity = null;
			modelSR = null;
		} else {
			pluginEntity = getScenarioResources().getPluginResources().getPluginEntity(entityURI);
			if (null == pluginEntity) {
				if (scenarioResources.getPluginResources().getPluginEntityTree().getPluginEntityURIMap().isEmpty()) {
					// Generally occured when project is not refreshed.
					logError(org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle("generation.error.noEntityFound",
							scenarioResources.getPluginName()));
					try {
						scenarioResources.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
					} catch (CoreException e) {
						logError(e);
					}
					scenarioResources.refreshPluginEntityTree();
				}
				throw new RuntimeException(
						"No entity '" + entityURI + "' was found in plugin '" + getScenarioResources().getPluginName() + "'!!");
			}
		}
	}

	/**
	 * Gets the xml element.
	 * 
	 * @return the xml element
	 */
	public Object getXmlElement() {
		return xmlElement;
	}

	/**
	 * Gets the tree id.
	 * 
	 * @return the tree id
	 */
	public String getTreeId() {
		return treeId;
	}

	/**
	 * Sets the tree id.
	 * 
	 * @param treeId
	 *            the new tree id
	 */
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	/**
	 * Gets the sub package.
	 * 
	 * @return the sub package
	 */
	public String getSubPackage() {
		return subPackage;
	}

	/**
	 * Sets the sub package.
	 * 
	 * @param subPackage
	 *            the new sub package
	 */
	public void setSubPackage(String subPackage) {
		this.subPackage = subPackage;
	}

	/**
	 * Gets the xml file.
	 * 
	 * @return the xml file
	 */
	public IFile getXmlFile() {
		return xmlFile;
	}

	/**
	 * Sets the xml file.
	 * 
	 * @param xmlFile
	 *            the new xml file
	 */
	public void setXmlFile(IFile xmlFile) {
		this.xmlFile = xmlFile;
	}

	/**
	 * Checks if is fire resource change event.
	 * 
	 * @return true, if is fire resource change event
	 */
	public boolean isFireResourceChangeEvent() {
		return fireResourceChangeEvent;
	}

	/**
	 * Gets the bundle translation.
	 * 
	 * @return the bundle translation
	 */
	public Map<String, String> getBundleTranslation() {
		return bundleTranslation;
	}

	/**
	 * Sets the bundle translation.
	 * 
	 * @param bundleTranslation
	 *            the bundle translation
	 */
	public void setBundleTranslation(Map<String, String> bundleTranslation) {
		this.bundleTranslation = bundleTranslation;
	}

	/**
	 * Gets the register.
	 * 
	 * @return the register
	 */
	public Map<String, RegisterEntry<?>> getRegister() {
		return register;
	}

	/**
	 * Gets the root wrapper.
	 * 
	 * @return the root wrapper
	 */
	public IRootWrapper getRootWrapper() {
		return rootWrapper;
	}

	/**
	 * Sets the root wrapper.
	 * 
	 * @param rootWrapper
	 *            the new root wrapper
	 */
	public void setRootWrapper(IRootWrapper rootWrapper) {
		this.rootWrapper = rootWrapper;
	}

	/**
	 * Gets the column wrapper.
	 * 
	 * @return the column wrapper
	 */
	public IColumnWrapper getColumnWrapper() {
		return columnWrapper;
	}

	/**
	 * Sets the column wrapper.
	 * 
	 * @param controlWrapper
	 *            the new column wrapper
	 */
	public void setColumnWrapper(IColumnWrapper controlWrapper) {
		this.columnWrapper = controlWrapper;
	}

	/**
	 * Checks if is generate xml.
	 * 
	 * @return true, if is generate xml
	 */
	public boolean isGenerateXml() {
		return generateXml;
	}

	/**
	 * Checks if is generate java.
	 * 
	 * @return true, if is generate java
	 */
	public boolean isGenerateJava() {
		return generateJava;
	}

	/**
	 * Checks if is compile.
	 * 
	 * @return true, if is compile
	 */
	public boolean isCompile() {
		return compile;
	}

	/**
	 * Sets the compile.
	 * 
	 * @param compile
	 *            the new compile
	 */
	public void setCompile(boolean compile) {
		this.compile = compile;
	}

	/**
	 * Gets the meta model.
	 * 
	 * @return the meta model
	 */
	public APluginEntityTree getPluginEntityTree() {
		return getModelSR().getPluginResources().getPluginEntityTree();
	}

	/**
	 * Gets the model scenario resources.
	 * 
	 * @return the model scenario resources
	 */
	public ScenarioResources getModelSR() {
		if (null == modelSR) {
			if (null == pluginEntity)
				modelSR = scenarioResources;
			else
				try {
					modelSR = ScenarioResources.getInstance(EngineTools.getInstanceKeys(pluginEntity.getEntityURI())[0],
							scenarioResources.getPluginName());
				} catch (Exception e) {
					logWarning(e.toString());
					modelSR = scenarioResources;
				}
		}
		return modelSR;
	}

	public RegisterEntry<?> getRegisterEntry(String variable) throws NullPointerException {
		RegisterEntry<?> registerEntry = getRegister().get(variable);
		if (null != registerEntry)
			return registerEntry;
		if (null != xmlFile) {
			IMarker marker;
			try {
				marker = xmlFile.createMarker(IMarker.PROBLEM);
				marker.setAttribute(IMarker.MESSAGE,
						getFromGeneratorBundle("generation.variable.notFound.file", xmlFile.getName(), variable));
				marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			} catch (ResourceException e) {
				logError(e);
			} catch (CoreException e) {
				logError(e);
			}
			logError(getFromGeneratorBundle("generation.variable.notFound", variable));
			throw new NullPointerException();
		}

		return registerEntry;
	}

	public Class<?> getRegistryClass(String variable) throws NullPointerException {
		RegisterEntry<?> registerEntry = getRegisterEntry(variable);
		if (null == registerEntry)
			throw new RuntimeException(getFromGeneratorBundle("generation.variable.notFound", variable));
		return registerEntry.getClazz();
	}

	public String getAdiResourceURI() {
		if (null == adiResourceURI) {
			adiResourceURI = EngineTools.getAdiResourceURI(scenarioResources.getPluginName(), subPackage, treeId);
		}
		return adiResourceURI;
	}

	public void setAdiResourceURI(String adiResourceURI) {
		this.adiResourceURI = adiResourceURI;
	}
}
