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
package org.adichatz.scenario.generation;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.xjc.AdichatzConnectorConfigTree;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.ApplicationServerWrapper;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ActionWhenTypeEnum;
import org.adichatz.generator.xjc.AddWhenEnum;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.ModelProcurementEnum;
import org.adichatz.generator.xjc.OneToOneType;
import org.adichatz.generator.xjc.OneToOneTypeEnum;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.ScenarioTree;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.IPojoRewriter;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.internal.InternalPolicy;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jakarta.persistence.Entity;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelComponentGeneration.
 * 
 * @author Yves Drumbonnet
 * 
 */
@SuppressWarnings("restriction")
public class ModelComponentGeneration {

	/** The scenario resources. */
	private ScenarioResources scenarioResources;

	/** The model part. */
	private ModelPartType modelPart;

	/** The sql properties. */
	private Properties sqlProperties;

	/** The component generation. */
	private ComponentGeneration componentGeneration;

	/** The monitor. */
	private IProgressMonitor monitor;

	/** The jdbc driver file name used by JSE connection (to be copied in resources/lib-jse directory). */
	private File jdbcDriverFile;

	/** The connector version. */
	private String connectorVersion;

	/** The model package name. */
	private String modelPackageName;

	/** The model file names. */
	private List<String> modelFileNames;

	/** The connection ok. */
	private boolean connectionOk;

	/** The as connector. */
	private ApplicationServerWrapper asConnector;

	/** The work plugin entity map. */
	Map<String, PluginEntityWrapper> workPluginEntityMap;

	/**
	 * Instantiates a new JPA component generation.
	 *
	 * @param componentGeneration
	 *            the component generation
	 * @param monitor
	 *            the monitor
	 */
	public ModelComponentGeneration(ComponentGeneration componentGeneration, IProgressMonitor monitor) {
		this.componentGeneration = componentGeneration;
		this.scenarioResources = componentGeneration.getScenarioResources();
		this.modelPart = scenarioResources.getGenerationScenario().getModelPart();
		this.monitor = monitor;
		connectorVersion = modelPart.getConnectorASVersion();

		sqlProperties = new Properties();
		// Puts default values
		sqlProperties.put(ISqlConnection.REVENG_BEAN_SRC_PATH, scenarioResources.getBuildPojoDir().getAbsolutePath());
		sqlProperties.put(ISqlConnection.REVENG_TEMPLATE_PATH,
				FileUtil.getPluginHome(GeneratorConstants.TEMPLATE_BUNDLE).concat("/template/pojo"));
		sqlProperties.put(ISqlConnection.REVENG_TEMPLATE_NAME, "Pojo.ftl");
		modelPackageName = modelPart.getModelPackageName();
		sqlProperties.put(ISqlConnection.REVENG_BEAN_PACKAGE_NAME, modelPackageName);

		ConnectorTreeWrapper connectorTree = scenarioResources.getConnectorTree();
		DatasourceWrapper dataSource = connectorTree.getDatasource(modelPart.getConnectorDataSource());
		sqlProperties.put(ISqlConnection.JDBC_DRIVER_JAR, dataSource.getJdbcJar());
		ConnectionUtil.addParam(dataSource.getParams(), true, sqlProperties);
		ConnectionUtil.addParam(dataSource.getBuildParams(), true, sqlProperties);
		scenarioResources.getScenarioTree().processAdditionalLibraries(scenarioResources, AddWhenEnum.MODEL);
	}

	/**
	 * Generate model resources.
	 *
	 * @param workPluginEntities
	 *            the work plugin entities
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public void generateModelResources(List<PluginEntityWrapper> workPluginEntities) throws IOException, CoreException {
		scenarioResources.setPluginResources(AdichatzApplication.getPluginResources(scenarioResources.getPluginName()));
		if (!scenarioResources.isParametersLoaded())
			scenarioResources.loadScenarioParameters();
		componentGeneration.getJavaProject(); // Build JavaProject if not exists

		buildConnectorResources();
		for (PluginEntityWrapper pluginEntity : workPluginEntities)
			scenarioResources.getPluginEntityScenario().populatePluginEntity(scenarioResources, pluginEntity,
					IPluginEntityScenario.MODEL_PART, null);

		componentGeneration.refreshProject();
	}

	/**
	 * Builds the connector resources.
	 *
	 * @throws JavaModelException
	 *             the java model exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void buildConnectorResources() throws JavaModelException, IOException {
		// Use List to respect order and check for duplicate entry when relaunching generation.
		// Do not use Set which changes orders.
		List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
		entries.addAll(Arrays.asList(componentGeneration.getJavaProject().getRawClasspath()));
		buildJavaProject(entries);

		componentGeneration.refresh("resources");
		componentGeneration.buildManifestFile();

		IProject project = scenarioResources.getProject();
		addLibToBuildPath(project, entries, "org/hibernate/annotations/GenericGenerator.class");
		if (!IScenarioConstants.JSE.equals(connectorVersion)) {
			addLibToBuildPath(project, entries, "javax/ejb/EJB.class");
			addLibToBuildPath(project, entries, "org/jboss/ejb/client/EJBClient.class");
			addLibToBuildPath(project, entries, "jakarta/ejb/EJB.class");
		}
		if (!entries.isEmpty()) {
			componentGeneration.getJavaProject().setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), monitor);
		}

		buildAdichatzConnectorConfigFile();
		scenarioResources.getScenarioTree().actionResource(scenarioResources, ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
		componentGeneration.buildManifestFile();
		componentGeneration.refresh("src");
	}

	/**
	 * Generate meta model.
	 *
	 * @param scenarioTree
	 *            the scenario tree
	 * @param generateOnlyChange
	 *            the generate only change
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CoreException
	 *             the core exception
	 */
	public void generateMetaModel(ScenarioTreeWrapper scenarioTree, ScenarioTree customizationScenarioTree,
			boolean generateOnlyChange) throws IOException, CoreException {
		// Regenerate PluginEntityTree.java
		GenerationScenarioWrapper generationScenario = (GenerationScenarioWrapper) scenarioTree.getGenerationScenario();
		if (!scenarioResources.isParametersLoaded())
			scenarioResources.loadScenarioParameters();

		// Generation of pojo means changes on plugin entity list. Tranfer new list in Scenario.xml file
		Map<String, PluginEntityType> newPluginEntityMap = new HashMap<String, PluginEntityType>();

		Map<String, PluginEntityWrapper> previousPluginEntityMap = getWorkPluginEntityMap(generationScenario);
		for (String fileName : getModelFileNames()) {
			String entityId = fileName.substring(0, fileName.length() - (fileName.endsWith(".class") ? 6 : 5));
			String entityURI = scenarioResources.getPluginEntityScenario().getEntityURI(scenarioResources, entityId);
			PluginEntityWrapper pluginEntity = previousPluginEntityMap.get(entityURI);
			if (null != pluginEntity)
				newPluginEntityMap.put(pluginEntity.getEntityURI(), pluginEntity);
		}
		generationScenario.getPluginEntity().clear();
		generationScenario.reinit();

		// Add a sorted list of plugin entities to Generation Scenario.
		List<PluginEntityType> pluginEntities = new ArrayList<PluginEntityType>();
		pluginEntities.addAll(newPluginEntityMap.values());
		Collections.sort(pluginEntities, new Comparator<PluginEntityType>() {
			@Override
			public int compare(PluginEntityType o1, PluginEntityType o2) {
				return o1.getEntityURI().compareTo(o2.getEntityURI());
			}
		});

		generationScenario.getPluginEntity().addAll(pluginEntities);

		// delete unused resources if asked
		if (generationScenario.isDeleteUnusedResources()) {
			for (PluginEntityType pluginEntity : previousPluginEntityMap.values()) {
				String pluginKey = ((PluginEntityWrapper) pluginEntity).getEntityKeys()[0];
				if ((pluginKey.equals(scenarioResources.getPluginName()))
						&& !newPluginEntityMap.containsKey(pluginEntity.getEntityURI())) {
					PluginEntityWrapper pew = new PluginEntityWrapper(scenarioResources);
					pew.setEntityURI(pluginEntity.getEntityURI());
					pew.deleteResources();
				}
			}
		}
		// Set pluginResources to be synchronized wjth last changes
		scenarioResources.setPluginResources(AdichatzApplication.getPluginResources(scenarioResources.getPluginName()));
		Map<String, PluginEntity<?>> oldPluginEntityTreeMap = new HashMap<>();

		if (scenarioResources.getBinGencodeFolder().getFile(EngineConstants.PLUGIN_ENTITY_TREE.concat(".class")).exists()) {
			scenarioResources.getPluginResources().loadPluginEntities();
			oldPluginEntityTreeMap.putAll(scenarioResources.getPluginResources().getPluginEntityTree().getPluginEntityURIMap());
		}

		componentGeneration.generatePluginEntityTreeClass();
		componentGeneration.buildActivatorFile();
		componentGeneration
				.refresh(scenarioResources.getPluginResources().getGencodePath().getGencodeRelativePath().concat("/src"));
		if (null != customizationScenarioTree && null != customizationScenarioTree.getGenerationScenario())
			generationScenario.mergeCustomization(customizationScenarioTree.getGenerationScenario(),
					scenarioResources.getPluginName(), IPluginEntityScenario.ENTITY);
		scenarioResources.marshalScenarioFile();
		scenarioResources.getPluginResources().loadPluginEntities();
		scenarioResources.getSrcGencodeFolder().getFile(EngineConstants.PLUGIN_ENTITY_TREE.concat(".java"))
				.refreshLocal(IResource.DEPTH_ZERO, null);
		GenerationScenarioWrapper workGS = new GenerationScenarioWrapper();
		if (generateOnlyChange) {
			Set<String> oldEntityURIs = new HashSet<String>();
			for (PluginEntity<?> pluginEntity : oldPluginEntityTreeMap.values())
				oldEntityURIs.add(pluginEntity.getEntityURI());

			for (PluginEntityType pluginEntity : scenarioTree.getGenerationScenario().getPluginEntity())
				if (!oldEntityURIs.contains(pluginEntity.getEntityURI()))
					workGS.getPluginEntity().add(pluginEntity);
		} else {
			workGS.getPluginEntity().addAll(generationScenario.getPluginEntity());
		}

		// Create /src/META-INF/persistence.xml file
		createPersistencesXmlFile();

		componentGeneration.generate(GenerationEnum.QUERY, workGS.getPluginEntity(), true, true, true);
		componentGeneration.generate(GenerationEnum.ENTITY, workGS.getPluginEntity(), true, true, true);

		Map<String, PluginEntity<?>> pluginEntityTreeMap = scenarioResources.getPluginResources().getPluginEntityTree()
				.getPluginEntityURIMap();
		if (generationScenario.isDeleteUnusedResources()) {
			scenarioResources.getPluginResources().getPluginEntityTree().reload();
			for (PluginEntity<?> pluginEntity : oldPluginEntityTreeMap.values()) {
				if (!pluginEntityTreeMap.containsKey(pluginEntity.getEntityURI())) {
					PluginEntityWrapper pew = new PluginEntityWrapper(scenarioResources);
					pew.setEntityURI(pluginEntity.getEntityURI());
					pew.deleteResources();
				}
			}
		}
		oldPluginEntityTreeMap.clear();
		oldPluginEntityTreeMap = null;
		modelFileNames = null;

		// rewrite pojo having parent OneToOne relationship.
		if (modelPart.getModelProcurement() == ModelProcurementEnum.HIBERNATE)
			for (PluginEntityType pluginEntityType : pluginEntities) {
				PluginEntityWrapper pluginEntity = (PluginEntityWrapper) pluginEntityType;
				EntityTreeWrapper entityTree = pluginEntity.getEntityTree();
				for (OneToOneType oneToOne : entityTree.getOneToOne())
					if (OneToOneTypeEnum.MASTER == oneToOne.getOneToOneType()) {
						Map<String, Object> params = new HashMap<>();
						params.put(IPojoRewriter.PLUGIN_ENTITY, pluginEntity);
						params.put(IPojoRewriter.BEAN_CLASS_NAME, pluginEntity.getBeanClass().getName());
						new MasterOneToOneRewriter().generate(scenarioResources, generationScenario, params);
						break;
					}
			}

		newPluginEntityMap.clear();
		newPluginEntityMap = null;
		pluginEntities.clear();
		pluginEntities = null;
		previousPluginEntityMap.clear();
		previousPluginEntityMap = null;

	}

	/**
	 * Creates the persistence xml file.
	 */
	public void createPersistencesXmlFile() {
		try {
			scenarioResources.createFolderIfNotExist("src/META-INF");
			String persistenceFileName = scenarioResources.getPluginHome() + "/src/META-INF/persistence.xml";
			String message = getFromGeneratorBundle("jpa.buildingPersistenceFile", persistenceFileName,
					modelPart.getConnectorASVersion());
			logInfo(message);
			componentGeneration.subTask(message);

			FileWriter persistenceFile = new FileWriter(persistenceFileName);
			if (IScenarioConstants.JSE.equals(modelPart.getConnectorASVersion()))
				createPersistencesXmlFileJSE(persistenceFile);
			else {
				createPersistencesXmlFileEJB(persistenceFile);
			}
			if ((InternalPolicy.OSGI_AVAILABLE)) {
				scenarioResources.getAffectedFiles().add(componentGeneration.getProject().getFile("src/META-INF/persistence.xml"));
				scenarioResources.incrementalBuildProject();
			}
		} catch (IOException e) {
			logError(e);
		}
	}

	/**
	 * Creates the persistences xml file ejb.
	 * 
	 * @param persistenceFileWriter
	 *            the persistence file writer
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void createPersistencesXmlFileEJB(FileWriter persistenceFileWriter) throws IOException {
		// Building persistence.xml file
		StringBuffer sb = new StringBuffer();
		sb.append("<persistence xmlns=\"http://java.sun.com/xml/ns/persistence\"\n");
		sb.append("\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
		sb.append(
				"\txsi:schemaLocation=\"http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd\"\n");
		sb.append("\tversion=\"1.0\">\n");
		sb.append("\t<persistence-unit name=\"" + modelPart.getDataSourceUnit() + "\">\n");
		sb.append("\t\t<provider>org.hibernate.ejb.HibernatePersistence</provider>\n");
		sb.append("\t\t<jta-data-source>" + "java:jboss/datasources/" + getDatasourceJNDI() + "</jta-data-source>\n");
		sb.append("\t\t<properties>\n");
		boolean hasHibernateShowSql = false;
		boolean hasHibernateEjbInterceptor = false;
		if (null != scenarioResources.getGenerationScenario().getModelPart().getParams()) {
			for (ParamType param : scenarioResources.getGenerationScenario().getModelPart().getParams().getParam()) {
				if ("hibernate.show_sql".equals(param.getId()))
					hasHibernateShowSql = true;
				else if ("hibernate.ejb.interceptor".equals(param.getId()))
					hasHibernateEjbInterceptor = true;
				if (!EngineTools.isEmpty(param.getValue()) && !EngineTools.isEmpty(param.getId()))
					sb.append("\t\t\t<property name=\"" + param.getId() + "\" value=\"" + param.getValue() + "\"/>\n");
			}
		}
		if (!hasHibernateShowSql)
			sb.append("\t\t\t<property name=\"hibernate.show_sql\" value=\"true\" />\n");
		if (!hasHibernateEjbInterceptor) {
			String defaultInterceptor = scenarioResources.getParam(IScenarioConstants.DEFAULT_HIBERNATE_INTERCEPTOR);
			if (!EngineTools.isEmpty(defaultInterceptor))
				sb.append("\t\t\t<property name=\"hibernate.ejb.interceptor\" value=\"" + defaultInterceptor + "\"/>\n");
		}
		sb.append("\t\t</properties>\n");
		sb.append("\t</persistence-unit>\n");
		sb.append("</persistence>\n");
		persistenceFileWriter.write(sb.toString());
		persistenceFileWriter.close();

	}

	/**
	 * Creates the persistences xml file jse.
	 * 
	 * @param persistenceFileWriter
	 *            the persistence file writer
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void createPersistencesXmlFileJSE(FileWriter persistenceFileWriter) throws IOException {
		Map<String, String> modelParamMap = new HashMap<>();
		if (null != scenarioResources.getGenerationScenario().getModelPart().getParams())
			for (ParamType param : scenarioResources.getGenerationScenario().getModelPart().getParams().getParam())
				modelParamMap.put(param.getId(), param.getValue());
		StringBuffer sb = new StringBuffer();
		sb.append("<persistence xmlns=\"http://java.sun.com/xml/ns/persistence\" version=\"1.0\">\n");
		sb.append("\t<persistence-unit name=\"" + modelPart.getDataSourceUnit() + "\">\n");
		for (String fileName : getModelFileNames())
			sb.append("\t\t<class>" + modelPackageName + "." + fileName.substring(0, fileName.length() - 5) + "</class>\n");
		sb.append("\t\t<properties>\n");
		String driver = addProperty(modelParamMap, sb, "hibernate.connection.driver_class", ISqlConnection.DRIVER_CLASS);
		String url = getProperty(modelParamMap, "hibernate.connection.driver_class", ISqlConnection.CONNECTION_URL);
		if (driver.contains(".mysql")) {
			int index = url.indexOf('?');
			if (-1 < index && url.length() > index) {
				String url1 = url.substring(0, index + 1);
				String url2 = url.substring(index + 1).replace("&", "&amp;");
				url = url1.concat(url2);
			}
		}
		addProperty(sb, "hibernate.connection.url", url);
		addProperty(modelParamMap, sb, "hibernate.connection.username", ISqlConnection.CONNECTION_USERNAME);
		addProperty(modelParamMap, sb, "hibernate.connection.password", ISqlConnection.CONNECTION_PASSWORD);
		addProperty(modelParamMap, sb, "hibernate.dialect", ISqlConnection.DIALECT);
		addProperty(modelParamMap, sb, "hibernate.connection.schema", ISqlConnection.CONNECTION_SCHEMA);
		sb.append("\t\t\t<property name=\"hibernate.show_sql\" value=\"true\" />\n");
		sb.append("\t\t\t<property name=\"hibernate.format_sql\" value=\"false\" />\n");
		sb.append("\t\t\t<property name=\"hibernate.hbm2ddl.auto\" value=\"none\" />\n");
		for (Map.Entry<String, String> entry : modelParamMap.entrySet())
			sb.append("\t\t\t<property name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\" />\n");
		sb.append("\t\t</properties>\n");
		sb.append("\t</persistence-unit>\n");
		sb.append("</persistence>\n");
		persistenceFileWriter.write(sb.toString());
		persistenceFileWriter.close();
	}

	private String addProperty(Map<String, String> modelParamMap, StringBuffer sb, String hibernateKey, String propertyKey) {
		String property = getProperty(modelParamMap, hibernateKey, propertyKey);
		addProperty(sb, hibernateKey, property);
		return property;
	}

	private String getProperty(Map<String, String> modelParamMap, String hibernateKey, String propertyKey) {
		String property = modelParamMap.get(hibernateKey);
		if (null == property)
			property = sqlProperties.getProperty(propertyKey);
		else {
			modelParamMap.remove(hibernateKey);
		}
		return property;
	}

	private void addProperty(StringBuffer sb, String hibernateKey, String property) {
		sb.append("\t\t\t<property name=\"").append(hibernateKey).append("\" value=\"").append(property).append("\" />\n");
	}

	/**
	 * Creates the ejb jar.
	 *
	 * @param deploy
	 *            the deploy
	 */
	public void createEJBJar(boolean deploy) {
		String ejbJarFileName = scenarioResources.getProjectEJBJarFileName();
		if (null == ejbJarFileName) {
			logError(getFromGeneratorBundle("generation.no.ejb.jar.file.name"));
			return;
		}
		scenarioResources.incrementalBuildProject();
		try {

			logInfo(getFromGeneratorBundle("jpa.generatingEJBjar"));
			File projectJarFile = new File(ejbJarFileName);
			if (projectJarFile.exists())
				projectJarFile.delete();

			// Generating EJB Jar
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("Manifest-Version: 1.0\n");
			stringBuffer.append("Ant-Version: Apache Ant 1.7.0\n");
			stringBuffer.append("Created-By: 10.0-b19 (Sun Microsystems Inc.)\n");
			stringBuffer.append("\n");
			stringBuffer.append("\n");
			Manifest manifest = new Manifest(new ByteArrayInputStream(stringBuffer.toString().getBytes("UTF-8")));

			IProject project = scenarioResources.getProject();
			String packageDir = scenarioResources.getPluginPackage().replace('.', '/');
			FileOutputStream fileOutputStream = new FileOutputStream(projectJarFile);
			JarOutputStream jarOutStream = new JarOutputStream(fileOutputStream, manifest);

			File commonPlugin;
			if (InternalPolicy.OSGI_AVAILABLE)
				commonPlugin = FileLocator.getBundleFile(Platform.getBundle(EngineConstants.COMMON_BUNDLE));
			else
				commonPlugin = new File(FileUtil.getPluginHome(EngineConstants.COMMON_BUNDLE));
			try {
				if (commonPlugin.getPath().endsWith(".jar")) {
					JarFile commonFile = new JarFile(commonPlugin.getPath());
					FileUtil.copyJarFileFromJarFile(jarOutStream, "org/adichatz/common/ejb", commonFile);
				} else {
					FileUtil.addPath2JarFile(jarOutStream, EngineConstants.COMMON_BUNDLE, "org/adichatz/common/ejb");
				}
			} catch (IOException e) {
				logError(e);
			}
			ConnectorTreeWrapper connectorTree = scenarioResources.getConnectorTree();
			File connectorFile = scenarioResources.getJpaConnectorFile();
			if (!connectorFile.exists()) { // Generally change on server application made by user
				IFile configFile = scenarioResources.getXmlFolder().getFile("AdichatzConnectorConfig.xml");
				try {
					if (configFile.exists())
						configFile.delete(true, null);
					buildAdichatzConnectorConfigFile();
					buildConnectorResources();
					componentGeneration.refreshProject();
				} catch (CoreException e) {
					logError(e);
				}
			}
			JarFile connectorJarFile = new JarFile(connectorFile);
			FileUtil.copyJarFileFromJarFile(jarOutStream, "org/adichatz/hibernate/ejb", connectorJarFile);
			FileUtil.copyJarFileFromJarFile(jarOutStream, "org/adichatz/hibernate/common", connectorJarFile);
			connectorJarFile.close();

			String modelDir = modelPart.getModelPackageName().replace('.', '/');
			FileUtil.addFolder2ZipFile(jarOutStream, modelDir + "/", project.getFolder("/bin/" + modelDir), false);
			FileUtil.addFolder2ZipFile(jarOutStream, packageDir + "/ejb/", project.getFolder("/bin/" + packageDir + "/ejb"), false);

			FileUtil.addFolder2ZipFile(jarOutStream, "META-INF/", project.getFolder("/src/META-INF"), false);
			jarOutStream.close();
			fileOutputStream.close();

			scenarioResources.getScenarioTree().actionResource(scenarioResources, ActionWhenTypeEnum.WHEN_BUILDING_EJB_JAR);
			if (deploy) {
				String ejbDeployDir = connectorTree
						.getEjbDeployDir(scenarioResources.getGenerationScenario().getModelPart().getConnectorASVersion());
				logInfo(getFromGeneratorBundle("jpa.deployingDatasource", ejbDeployDir));

				logInfo(getFromGeneratorBundle("jpa.deployingEjbJar", ejbDeployDir));
				final String sourceFileName = projectJarFile.getAbsolutePath();
				final String destinationFileName = ejbDeployDir + "/" + scenarioResources.getEjbJarFileName();
				FileUtil.copyFile(sourceFileName, destinationFileName, true);
			}
		} catch (IOException e) {
			logError("Error when generating EJB Jar!", e);
		}
	}

	/**
	 * Things to do before pojo procurement.<br>
	 * Determines set of protected Pojos.<br>
	 * Protection is determined in PluginEntityType#protectPojo
	 *
	 * @param doNotDeleteProtectedPojo
	 *            the do not delete protected pojo
	 * @return the hash set
	 */
	public HashSet<String> beforePojoProcurement(boolean doNotDeleteProtectedPojo) {
		scenarioResources.createFolderIfNotExist("bin");
		scenarioResources.createFolderIfNotExist("src/".concat(scenarioResources.getPluginPackage().replace('.', '/') + "/ejb/"));
		scenarioResources.createFolderIfNotExist("src/".concat(modelPart.getModelPackageName().replace('.', '/')));
		GenerationScenarioWrapper generationScenario = componentGeneration.getGenerationScenario();
		HashSet<String> protectedPojos = new HashSet<String>();
		if (generationScenario.isDeleteUnusedResources()) {
			IFolder modelFolder = scenarioResources.getProject()
					.getFolder("src/" + modelPart.getModelPackageName().replace(".", "/"));
			if (modelFolder.exists())
				try {
					for (IResource resource : modelFolder.members()) {
						if (resource instanceof IFile && resource.getName().endsWith(".java")) {
							String entityId = resource.getName().substring(0, resource.getName().length() - 5);
							String beanClassName = modelPart.getModelPackageName() + "." + entityId;
							Class<?> beanClass = scenarioResources.getGencodePath().getClazz(beanClassName);
							if (null != beanClass && null != beanClass.getAnnotation(Entity.class)) {
								boolean protectedPojo = false;
								if (doNotDeleteProtectedPojo) {
									AEntityMetaModel<?> entityMM;
									try {
										entityMM = scenarioResources.getPluginResources().getPluginEntityTree()
												.getEntityMM(beanClass);
										PluginEntityWrapper pluginEntityWrapper = scenarioResources
												.getPluginEntityWrapper(entityMM.getPluginEntity().getEntityURI());
										protectedPojo = null != pluginEntityWrapper && pluginEntityWrapper.isProtectPojo();
									} catch (RuntimeException e) {
									}
									if (protectedPojo)
										protectedPojos.add(resource.getName());
									else
										resource.delete(true, null);
								}
							}
						}

					}
				} catch (CoreException e) {
					logError(e);
				}
		}
		return protectedPojos;
	}

	/**
	 * Creates the model part.
	 *
	 * @param datasourceName
	 *            the datasource name
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public boolean generatePojo(String datasourceId) throws IOException {

		// Test connection
		connectionOk = scenarioResources.getConnectorTree().testConnection(null, datasourceId, false);
		if (!connectionOk)
			return false;

		HashSet<String> protectedPojos = beforePojoProcurement(true);

		logInfo(getFromGeneratorBundle("jpa.generatingPojos"));

		String srcDirName = scenarioResources.getPluginHome().concat("/src");

		List<URL> urls = new ArrayList<URL>();
		if (InternalPolicy.OSGI_AVAILABLE) {
			Bundle templateBundle = Platform.getBundle(GeneratorConstants.TEMPLATE_BUNDLE);
			addURL(templateBundle, urls, "template/lib/tool/commons-logging-1.0.4.jar");
			addURL(templateBundle, urls, "template/lib/lib-jse/dom4j-2.1.1.jar");
			addURL(templateBundle, urls, "template/lib/tool/commons-collections-3.2.1.jar");
			addURL(templateBundle, urls, "template/lib/tool/freemarker.jar");
			addURL(templateBundle, urls, "template/lib/lib-jse/hibernate-commons-annotations-5.0.5.Final.jar");
			addURL(templateBundle, urls, "template/lib/lib-jse/jakarta.persistence-api-2.2.3.jar");
			addURL(templateBundle, urls, "template/lib/tool/hibernate-core-4.3.10.Final.jar");
			addURL(templateBundle, urls, "template/lib/tool/hibernate-tools-4.3.1.Final.jar");
			addURL(templateBundle, urls, "template/lib/lib-jse/jboss-logging-3.4.1.Final.jar");
			addURL(templateBundle, urls, "template/lib/lib-jse/jboss-transaction-api_1.3_spec-2.0.0.Final.jar");
			addURL(templateBundle, urls, "template/lib/tool/log4j-1.2.16.jar");
			addURL(templateBundle, urls, "template/lib/tool/slf4j-api-1.7.2-redhat-1.jar");
		} else {
			String libHome = GeneratorConstants.WORKSPACE_DIRECTORY + "/" + GeneratorConstants.TEMPLATE_BUNDLE + "/template/lib";
			urls.add(new File(libHome + "/tool/commons-logging-1.0.4.jar").toURI().toURL());
			urls.add(new File(libHome + "/lib-jse/dom4j-2.1.1.jar").toURI().toURL());
			urls.add(new File(libHome + "/lib-jse/hibernate-commons-annotations-5.0.5.Final.jar").toURI().toURL());
			urls.add(new File(libHome + "/tool/hibernate-jpa-2.1-api-1.0.0.Final.jar").toURI().toURL());
			urls.add(new File(libHome + "/tool/hibernate-tools-4.0.0-CR1.jar").toURI().toURL());
			urls.add(new File(libHome + "/lib-jse/jboss-transaction-api_1.3_spec-2.0.0.Final.jar").toURI().toURL());
			urls.add(new File(libHome + "/tool/slf4j-api-1.7.2-redhat-1.jar").toURI().toURL());
		}
		File jdbcJarFile = new File(sqlProperties.getProperty(ISqlConnection.JDBC_DRIVER_JAR));
		if (!jdbcJarFile.exists())
			throw new RuntimeException("JDBC jar file '" + jdbcJarFile.getAbsolutePath() + "' does not exist!");
		urls.add(jdbcJarFile.toURI().toURL());

		logInfo(getFromGeneratorBundle("jpa.delete.work.file"));
		File buildPojoDir = scenarioResources.getBuildPojoDir();
		if (buildPojoDir.exists())
			if (!ScenarioUtil.deleteDir(buildPojoDir))
				logError(getFromGeneratorBundle("cannot.delete.file.resources", buildPojoDir.getName()));
		buildPojoDir.mkdirs();
		try {
			URLClassLoader classLoader = ConnectionUtil.getClassLoaderConnection(urls);
			Object exporter = classLoader.loadClass("org.adichatz.generator.dynamic.AdiExporter").getDeclaredConstructor()
					.newInstance();
			ReflectionTools.invokeMethod("export", exporter, new Class[] { Properties.class }, new Object[] { sqlProperties });
		} catch (Exception e) {
			logError(e);
			return false;
		}

		logInfo(getFromGeneratorBundle("jpa.generatedPojos"));
		String modelPath = "/" + modelPackageName.replace('.', '/');

		// replace "CascadeType.ALL" by "{ Cascade{Type.PERSIST, CascadeType.MERGE }
		File buildModelDir = new File(buildPojoDir.getAbsolutePath() + modelPath);
		if (buildModelDir.exists()) {
			logInfo(getFromGeneratorBundle("jpa.replacingCascadeAll"));
			for (File file : buildModelDir.listFiles()) {
				FileUtil.replaceInFile(file, new String[] { "CascadeType.ALL" },
						new String[] { "{ CascadeType.PERSIST, CascadeType.MERGE }" });
			}
			logInfo(getFromGeneratorBundle("jpa.replacedCascadeAll"));
		}

		logInfo(getFromGeneratorBundle("jpa.copy.work.file"));
		File buildMemberDir = new File(buildPojoDir.getAbsolutePath() + modelPath);
		File pojoDirectory = new File(srcDirName + modelPath);
		pojoDirectory.mkdirs();

		for (File file : buildMemberDir.listFiles()) {
			String fileName = file.getName();
			if (file.isFile() && !protectedPojos.contains(fileName))
				FileUtil.copyFile(file, new File(pojoDirectory.getAbsolutePath().concat("/").concat(fileName)));
		}

		componentGeneration.refresh("src/".concat(scenarioResources.getPluginPackage().replace('.', '/')));
		return true;
	}

	private void addURL(Bundle templateBundle, List<URL> urls, String name) {
		URL url = templateBundle.getEntry(name);
		if (null == url)
			logError(getFromGeneratorBundle("scenario.entry.not.exists", name));
		else
			urls.add(templateBundle.getEntry(name));
	}

	/**
	 * Builds the java project.
	 *
	 * @param entries
	 *            the entries
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void buildJavaProject(Collection<IClasspathEntry> entries) {
		try {
			IFolder folder = scenarioResources.createFolderIfNotExist(IScenarioConstants.LIB_DIRECTORY);
			String connectorVersion = modelPart.getConnectorASVersion();

			IClasspathEntry entry;
			Bundle templateBundle = Platform.getBundle(GeneratorConstants.TEMPLATE_BUNDLE);
			ConnectorTreeWrapper connectorTree = scenarioResources.getConnectorTree();
			if (IScenarioConstants.JSE.equals(connectorVersion)) {
				try {
					File libDirectory = new File(componentGeneration.getTemplateDirName() + "/lib/lib-jse");
					FileUtil.copyDirectory(libDirectory, folder.getLocation().toFile());
					// Copy jdbc driver jar in lib-jse folder
					jdbcDriverFile = getJdbcDriverFile();
					String jdbcDriverFileName = jdbcDriverFile.getName();
					FileInputStream inputStream = new FileInputStream(jdbcDriverFile);
					IFile jdbcDriverIFile = folder.getFile(jdbcDriverFileName);
					if (jdbcDriverIFile.exists()) {
						jdbcDriverIFile.delete(true, monitor);
					}
					jdbcDriverIFile.create(inputStream, true, monitor);
					inputStream.close();
				} catch (IOException e) {
					logError(e);
				}
			} else {
				String jbossInstallation = connectorTree.getApplicationServerHome(connectorVersion);
				File libFolder = folder.getLocation().toFile();
				if (connectorVersion.startsWith(IScenarioConstants.JBOSS_7_1)
						|| connectorVersion.startsWith(IScenarioConstants.JBOSS_EAP_6)) {
					asConnector = (ApplicationServerWrapper) connectorTree.getApplicationServer(connectorVersion);
					copy71Libraries(libFolder, jbossInstallation, "org.antlr");
					copy71Libraries(libFolder, jbossInstallation, "org.hibernate");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.ejb-client");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.logging");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.logmanager");
					copy71Libraries(libFolder, jbossInstallation, "org.apache.log4j");
					copy71Libraries(libFolder, jbossInstallation, "javax.ejb.api");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.marshalling");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.remoting3");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.xnio");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.sasl");
					copy71Libraries(libFolder, jbossInstallation, "javax.transaction.api");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.xnio.nio");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.sasl");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.marshalling.river");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.remote-naming");
					copy71Libraries(libFolder, jbossInstallation, "org.javassist");
				} else if (connectorVersion.startsWith(IScenarioConstants.WILDFLY)) {
					boolean hasJakartaVersion = null != Platform.getBundle("jakarta.xml.bind"); // Java version >= 9 and use jakarta plugin for JEE JAXB. 
					copy71Libraries(libFolder, jbossInstallation, "org.antlr");
					copy71Libraries(libFolder, jbossInstallation, "org.hibernate");
					copy71Libraries(libFolder, jbossInstallation, "javax.ejb.api");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.ejb-client");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.logging");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.marshalling");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.marshalling.river");
					copy71Libraries(libFolder, jbossInstallation, "javax.transaction.api");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.xnio");
					copy71Libraries(libFolder, jbossInstallation, "org.jboss.xnio.nio");
					copy71Libraries(libFolder, jbossInstallation, "org.javassist");
					if (ScenarioUtil.isWildflyNewVersion(connectorVersion)) {
						copy71Libraries(libFolder, jbossInstallation, "org.wildfly.common");
						copy71Libraries(libFolder, jbossInstallation, "org.wildfly.discovery");
						FileFilter elytronFF = new FileFilter() {
							@Override
							public boolean accept(File file) {
								String fileName = file.getName();
								if ((fileName.contains("wildfly-elytron-credential") || fileName.contains("wildfly-elytron-client")
										|| fileName.contains("wildfly-elytron-base")
										|| fileName.contains("wildfly-elytron-provider-util")
										|| fileName.contains("wildfly-elytron-auth")) && !fileName.contains("http")
										&& !fileName.contains("sasl") && !fileName.contains("deprecated")
										&& !fileName.contains("wildfly-elytron-auth-util") && !fileName.contains("source")
										&& !fileName.contains("store"))
									return true;
								return false;
							}
						};
						copy71Libraries(libFolder, jbossInstallation, "org.wildfly.security.elytron-private", elytronFF);
						copy71Libraries(libFolder, jbossInstallation, "org.wildfly.naming-client");
						copy71Libraries(libFolder, jbossInstallation, "org.wildfly.transaction.client");
						File jbossCliClientJar = new File(jbossInstallation.concat("/bin/client/jboss-cli-client.jar"));
						if (jbossCliClientJar.exists())
							FileUtil.copyFile(jbossCliClientJar, new File(libFolder, jbossCliClientJar.getName()), true);
						else
							logError(getFromEngineBundle("error.file.does.not.exist", jbossCliClientJar.getAbsolutePath()));
						if (ScenarioUtil.isWildfly14Version(connectorVersion))
							copy71Libraries(libFolder, jbossInstallation, "net.bytebuddy");
					} else {
						copy71Libraries(libFolder, jbossInstallation, "org.jboss.remoting");
						copy71Libraries(libFolder, jbossInstallation, "org.jboss.sasl");
					}
					if (hasJakartaVersion) {
						copy71Libraries(libFolder, jbossInstallation, "jakarta.ejb.api"); // jakarta.ejb-api is present un ./ejb/api
					}
				}
			}
			String adichatzConnector = connectorTree.getAdichatzConnector(connectorVersion);
			IFile connectorFile = folder.getFile(adichatzConnector);

			String connectorName = "template/lib/connector/".concat(adichatzConnector);
			URL connectorUrl = templateBundle.getEntry(connectorName);
			if (null == connectorUrl)
				logError(getFromGeneratorBundle("generation.invalid.connector", adichatzConnector));
			else
				try {
					InputStream inputStream = connectorUrl.openStream();
					if (connectorFile.exists())
						connectorFile.setContents(inputStream, IResource.FORCE, null);
					else
						connectorFile.create(inputStream, IResource.FORCE, null);
					inputStream.close();
					entry = JavaCore.newLibraryEntry(connectorFile.getFullPath(), null, null);
					addEntry(entries, entry);
				} catch (IOException e) {
					logError(e);
				}
		} catch (CoreException e) {
			logError(e);
		}
	}

	/**
	 * Adds the entry.
	 *
	 * @param classPathEntries
	 *            the class path entries
	 * @param classPathEntry
	 *            the class path entry
	 */
	private void addEntry(Collection<IClasspathEntry> classPathEntries, IClasspathEntry classPathEntry) {
		if (!classPathEntries.contains(classPathEntry))
			classPathEntries.add(classPathEntry);
	}

	/**
	 * Adds the first found library which contains a specific entry to build path.
	 *
	 * @param project
	 *            the project
	 * @param entries
	 *            the entries
	 * @param entryName
	 *            the entry name
	 */
	private void addLibToBuildPath(IProject project, Collection<IClasspathEntry> entries, String entryName) {
		try {
			String attribute = componentGeneration.getScenarioResources().getManifestChanger().getValue(Constants.BUNDLE_CLASSPATH);
			StringTokenizer tokenizer = new StringTokenizer(attribute, ",");
			while (tokenizer.hasMoreElements()) {
				String token = tokenizer.nextToken().trim();
				if (token.endsWith(".jar")) {
					IFile libFile = project.getFile(token);
					ZipFile zipFile = new ZipFile(new File(libFile.getLocation().toOSString()));
					if (null != zipFile.getEntry(entryName)) {
						IClasspathEntry entry = JavaCore.newLibraryEntry(libFile.getFullPath(), null, null);
						addEntry(entries, entry);
						zipFile.close();
						break;
					}
					zipFile.close();
				}
			}
		} catch (IOException | CoreException e) {
			logError(e);
		}

	}

	/**
	 * Copy71 libraries.
	 *
	 * @param libDirectory
	 *            the lib directory
	 * @param jbossInstallation
	 *            the jboss installation
	 * @param libraryName
	 *            the library name
	 */
	private void copy71Libraries(File libDirectory, String jbossInstallation, String libraryName, FileFilter... fileFilters) {
		for (File library : get71Libraries(jbossInstallation, libraryName, fileFilters)) {
			FileUtil.copyFile(library, new File(libDirectory, library.getName()), true);
		}
	}

	/**
	 * Gets the AS connector.
	 *
	 * @return the AS connector
	 */
	private ApplicationServerWrapper getASConnector() {
		if (null == asConnector)
			asConnector = (ApplicationServerWrapper) scenarioResources.getConnectorTree().getApplicationServer(connectorVersion);
		return asConnector;
	}

	/**
	 * Gets the 71 libraries.
	 *
	 * @param jbossInstallation
	 *            the jboss installation
	 * @param libraryName
	 *            the library name
	 * @return the 71 libraries
	 */
	private File[] get71Libraries(String jbossInstallation, String libraryName, FileFilter... fileFilters) {
		StringBuffer directoryName = new StringBuffer(jbossInstallation);
		String moduleName = getASConnector().getModulesDirectory();
		if (!moduleName.startsWith("/"))
			directoryName.append("/");
		directoryName.append(moduleName);
		if (!moduleName.endsWith("/"))
			directoryName.append("/");

		StringTokenizer tokenizer = new StringTokenizer(libraryName, ".");
		while (tokenizer.hasMoreTokens())
			directoryName.append(tokenizer.nextToken().trim()).append("/");
		directoryName.append("main/");
		File directory = new File(directoryName.toString());
		if (directory.isDirectory()) {
			return directory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					boolean ok = file.getName().endsWith(".jar");
					if (ok && null != fileFilters)
						for (FileFilter fileFilter : fileFilters)
							if (!fileFilter.accept(file))
								return false;
					return ok;
				}
			});

		} else
			logError("No directory for library found in jboss installation '" + jbossInstallation + "' for '" + libraryName + "'!");
		return new File[0];
	}

	/**
	 * Gets the connector version.
	 *
	 * @return the connector version
	 */
	public String getConnectorVersion() {
		return connectorVersion;
	}

	/**
	 * Gets the jdbc driver file.
	 *
	 * @return the jdbc driver file
	 */
	public File getJdbcDriverFile() {
		if (null == jdbcDriverFile) {
			DatasourceWrapper dataSource = scenarioResources.getConnectorTree().getDatasource(modelPart.getConnectorDataSource());
			jdbcDriverFile = new File(dataSource.getJdbcJar());
		}
		return jdbcDriverFile;
	}

	/**
	 * Gets the model package name.
	 * 
	 * @return the model package name
	 */
	public String getModelPackageName() {
		return modelPackageName;
	}

	/**
	 * Gets the datasource jndi.
	 *
	 * @return the datasource jndi
	 */
	public String getDatasourceJNDI() {
		return scenarioResources.getGenerationScenario().getModelPart().getJndi();
	}

	/**
	 * Builds the adichatz connector config file.
	 */
	public void buildAdichatzConnectorConfigFile() {
		componentGeneration.subTask(getFromGeneratorBundle("generation.build.adichatzConnectorConfig"));
		IFile configFile = scenarioResources.getXmlFolder().getFile("AdichatzConnectorConfig.xml");
		if (!configFile.exists()) {
			try {
				configFile.create(new ByteArrayInputStream(new byte[0]), IResource.NONE, monitor);
				logInfo(getFromGeneratorBundle("generation.build.adichatzConnectorConfig"));
				AdichatzConnectorConfigTree configTree = new AdichatzConnectorConfigTree();

				ParamsType params = scenarioResources.getConnectorTree().getApplicationServer(modelPart.getConnectorASVersion())
						.getParams();
				org.adichatz.engine.xjc.ParamsType configParams = new org.adichatz.engine.xjc.ParamsType();
				for (ParamType param : params.getParam()) {
					if ("CONTEXT".equals(param.getType()) || "REMOTE".equals(param.getType()) || "JSE".equals(param.getType())) {
						org.adichatz.engine.xjc.ParamType configParam = new org.adichatz.engine.xjc.ParamType();
						configParam.setId(param.getId());
						configParam.setValue(param.getValue());
						configParam.setType(param.getType());
						configParams.getParam().add(configParam);
					}
				}
				configTree.setParams(configParams);
				ScenarioUtil.createXmlFile(configFile, configTree, scenarioResources.getGenerationScenario());
				configFile.refreshLocal(IResource.DEPTH_ZERO, null);
			} catch (CoreException e) {
				logError(e);
			}
			// }
		}
	}

	/**
	 * Adds the driver module.
	 */
	public void addDriverModule() {
		String jbossInstallation = scenarioResources.getConnectorTree().getApplicationServerHome(modelPart.getConnectorASVersion());
		DatasourceWrapper datasource = scenarioResources.getConnectorTree().getDatasource(modelPart.getConnectorDataSource());

		// Module = com.mysql or org.pgsql
		String module = datasource.getModule();
		File driverJarFile = new File(datasource.getJdbcJar());
		File moduleDirFile = new File(jbossInstallation + "/modules/" + module.replace('.', '/') + "/main");
		moduleDirFile.mkdirs();

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("module");
			doc.appendChild(rootElement);
			ScenarioUtil.addAttrribute(doc, rootElement, "xmlns", "urn:jboss:module:1.1");
			ScenarioUtil.addAttrribute(doc, rootElement, "name", module);

			Element resources = doc.createElement("resources");
			rootElement.appendChild(resources);

			Element resourceRoot = doc.createElement("resource-root");
			resources.appendChild(resourceRoot);
			ScenarioUtil.addAttrribute(doc, resourceRoot, "path", driverJarFile.getName());

			Element dependencies = doc.createElement("dependencies");
			rootElement.appendChild(dependencies);

			ScenarioUtil.addModule(doc, dependencies, "javax.api");
			ScenarioUtil.addModule(doc, dependencies, "javax.transaction.api");

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", 4);

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");

			DOMSource source = new DOMSource(doc);
			StreamResult result;
			result = new StreamResult(new OutputStreamWriter(new FileOutputStream(moduleDirFile + "/module.xml"), "UTF-8"));
			transformer.transform(source, result);

			FileUtil.copyFile(driverJarFile, new File(moduleDirFile + "/" + driverJarFile.getName()), true);
		} catch (ParserConfigurationException | TransformerException | UnsupportedEncodingException | FileNotFoundException e) {
			logError(e);
		}
	}

	/**
	 * Gets the model file names.
	 *
	 * @return the model file names
	 */
	private List<String> getModelFileNames() {
		if (null == modelFileNames)
			modelFileNames = scenarioResources.getModelFileNames();
		return modelFileNames;
	}

	/**
	 * Gets the work plugin entity map.
	 *
	 * @param generationScenario
	 *            the generation scenario
	 * @return the work plugin entity map
	 * @throws JavaModelException
	 *             the java model exception
	 */
	public Map<String, PluginEntityWrapper> getWorkPluginEntityMap(GenerationScenarioWrapper generationScenario)
			throws JavaModelException {
		if (null == workPluginEntityMap || workPluginEntityMap.isEmpty()) {
			workPluginEntityMap = new HashMap<String, PluginEntityWrapper>();
			for (PluginEntityType pluginEntity : generationScenario.getPluginEntity())
				workPluginEntityMap.put(pluginEntity.getEntityURI(), (PluginEntityWrapper) pluginEntity);
			for (String fileName : getModelFileNames()) {
				String entityId = fileName.substring(0, fileName.length() - (fileName.endsWith(".class") ? 6 : 5));
				String entityURI = scenarioResources.getPluginEntityScenario().getEntityURI(scenarioResources, entityId);
				PluginEntityWrapper pluginEntity = workPluginEntityMap.get(entityURI);
				if (null == pluginEntity) {
					pluginEntity = new PluginEntityWrapper(scenarioResources);
					pluginEntity.setEntityURI(entityURI);
					workPluginEntityMap.put(entityURI, pluginEntity);
				}
			}
		}
		return workPluginEntityMap;
	}
}
