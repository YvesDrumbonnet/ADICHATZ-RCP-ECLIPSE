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
package org.adichatz.engine.common;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.internal.InternalPolicy;
import org.osgi.framework.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiPluginResources.
 * 
 * @author Yves Drumbonnet
 * 
 */
@SuppressWarnings("restriction")
public class AdiPluginResources implements IMarshalledElement {

	/** The plugin name. */
	protected String pluginName;

	/** The plugin home. */
	protected String pluginHome;

	/** The plugin package. */
	protected String pluginPackage;

	/** The gencode path. */
	protected GencodePath gencodePath;

	/** The resource bundle map. */
	protected ResourceBundleManager resourceBundleManager;

	/** The image manager. */
	protected ImageManager imageManager;

	/** The data access. */
	protected ADataAccess dataAccess;

	/** The parent class loader. */
	private ClassLoader parentClassLoader;

	/** The meta model. */
	protected String modelPackageName;

	/** The config wrappers map. */
	protected Map<String, Object> configWrappers = new HashMap<String, Object>();

	protected APluginEntityTree pluginEntityTree;

	/**
	 * Instantiates a new adi plugin resources.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @param pluginPackage
	 *            the plugin package
	 * @param gencodePath
	 *            the gencode path
	 */
	public AdiPluginResources(String pluginName, String pluginPackage, GencodePath gencodePath) {
		init(pluginName, pluginPackage);
		this.gencodePath = gencodePath;
		parentClassLoader = gencodePath.getClassLoader().getParent();
	}

	/**
	 * Instantiates a new adi plugin resources.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @param pluginPackage
	 *            the plugin package
	 */
	public AdiPluginResources(String pluginName, String gencodePackage, String pluginPackage) {
		init(pluginName, pluginPackage);
		pluginHome = FileUtility.getPluginHome(pluginName);
		parentClassLoader = getClass().getClassLoader();
		if (null != pluginPackage)
			this.gencodePath = new GencodePath(pluginHome, pluginPackage, gencodePackage, EngineConstants.GENCODE_DIR,
					parentClassLoader);
	}

	/**
	 * Instantiates a new adi plugin resources.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @param pluginPackage
	 *            the plugin package
	 * @param parentClassLoader
	 *            the parent class loader
	 */
	public AdiPluginResources(String pluginName, String pluginPackage, String gencodePackage, ClassLoader parentClassLoader) {
		init(pluginName, pluginPackage);
		Bundle bundle = Platform.getBundle(pluginName);
		String gencodeRelativePath = EngineConstants.GENCODE_DIR;
		if (null != bundle) {
			try {
				pluginHome = FileLocator.getBundleFile(bundle).getPath();
			} catch (IOException e) {
				logError(e);
			}
			if (null == bundle.getEntry(gencodeRelativePath))
				gencodeRelativePath = "/";
		}
		this.parentClassLoader = parentClassLoader;
		this.gencodePath = new GencodePath(pluginHome, pluginPackage, gencodePackage, gencodeRelativePath, parentClassLoader);
	}

	/**
	 * Inits the.
	 * 
	 * @param pluginName
	 *            the plugin name
	 * @param pluginPackage
	 *            the plugin package
	 */
	private void init(String pluginName, String pluginPackage) {
		this.pluginName = pluginName;
		this.pluginPackage = pluginPackage;
		if (null == AdichatzApplication.getInstance())
			new AdichatzApplication();
		AdichatzApplication.getInstance().getPluginMap().put(pluginName, this);
		resourceBundleManager = new ResourceBundleManager(this);
		if (InternalPolicy.OSGI_AVAILABLE)
			imageManager = new ImageManager(this);
	}

	/**
	 * Gets the parent class loader.
	 * 
	 * @return the parent class loader
	 */
	public ClassLoader getParentClassLoader() {
		return parentClassLoader;
	}

	/**
	 * Gets the plugin name.
	 * 
	 * @return the plugin name
	 */
	public String getPluginName() {
		return pluginName;
	}

	/**
	 * Gets the plugin package.
	 * 
	 * @return the plugin package
	 */
	public String getPluginPackage() {
		return pluginPackage;
	}

	/**
	 * Gets the plugin home.
	 * 
	 * @return the plugin home
	 */
	public String getPluginHome() {
		return pluginHome;
	}

	/**
	 * Gets the data access.
	 * 
	 * @return the data access
	 */
	public ADataAccess getDataAccess() {
		return dataAccess;
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
	 * Sets the model package name.
	 * 
	 * @param modelPackageName
	 *            the new model package name
	 */
	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}

	public void loadPluginEntities() {
		String className = getClassName(EngineConstants.PLUGIN_ENTITY_TREE, "");
		Class<?> pluginEntityClass = gencodePath.getClazz(className, true);
		if (null == pluginEntityClass) {
			if (gencodePath.getClassLoader() instanceof JarClassLoader) // pluginResources is a jar
				logError(getFromEngineBundle("error.generated.class.notFound", className));
			else {
				File file = new File(gencodePath.getGenCodeBinLocation());
				if (file.exists() && 0 != file.listFiles().length)
					logError(getFromEngineBundle("error.generated.class.notFound", className));
			}
		} else
			pluginEntityTree = (APluginEntityTree) gencodePath.instantiateClass(pluginEntityClass,
					new Class[] { AdiPluginResources.class }, new Object[] { this });
	}

	/**
	 * Sets the data access.
	 * 
	 * @param dataAccess
	 *            the new data access
	 */
	public void setDataAccess(ADataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	/**
	 * Gets the resource bundle manager.
	 * 
	 * @return the resource bundle manager
	 */
	public ResourceBundleManager getResourceBundleManager() {
		return resourceBundleManager;
	}

	/**
	 * Gets the message.
	 * 
	 * @param resourceKey
	 *            the resource key
	 * @param key
	 *            the key
	 * @param variables
	 *            the variables
	 * @return the message
	 */
	public String getMessage(String resourceKey, String key, Object... variables) {
		try {
			return resourceBundleManager.getResourceBundle(resourceKey).getValueFromBundle(key, variables);
		} catch (ResourceBundleException e) {
			logError(e);
			return "???" + key + "???";
		}
	}

	/**
	 * Gets the image manager.
	 * 
	 * @return the image manager
	 */
	public ImageManager getImageManager() {
		return imageManager;
	}

	/**
	 * Gets the gencode path.
	 * 
	 * @return the gencode path
	 */
	public GencodePath getGencodePath() {
		return gencodePath;
	}

	/**
	 * Sets the gencode path.
	 *
	 * @param gencodePath
	 *            the new gencode path
	 */
	public void setGencodePath(GencodePath gencodePath) {
		this.gencodePath = gencodePath;
	}

	/**
	 * Prepare and instantiate class.
	 * 
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * @param paramClasses
	 *            the param classes
	 * @param params
	 *            the params
	 * @return the object
	 */
	public Object prepareAndInstantiateClass(String treeId, String subPackage, @SuppressWarnings("rawtypes") Class[] paramClasses,
			Object[] params) {
		if (null != EngineConstants.GENERATOR) {
			EngineConstants.GENERATOR.launchGenCode(this, treeId, subPackage);
		} // end if (development)
		return gencodePath.instantiateClass(gencodePath.getClazz(getClassName(treeId, subPackage), false), paramClasses, params);
	}

	/**
	 * Gets the class name.
	 * 
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * @return the class name
	 */
	public String getClassName(String treeId, String subPackage) {
		StringBuffer className = new StringBuffer(gencodePath.getGencodePackage());
		if (!EngineTools.isEmpty(subPackage) && !".".equals(subPackage))
			className.append('.').append(subPackage);
		return className.append('.').append(treeId).toString();
	}

	/**
	 * Gets the new instance.
	 * 
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * @return the new instance
	 */
	public Object getNewInstance(String treeId, String subPackage) {
		return prepareAndInstantiateClass(treeId, subPackage, new Class[] { AdiPluginResources.class }, new Object[] { this });
	}

	/**
	 * Gets the new instance.
	 * 
	 * @param treeId
	 *            the tree id
	 * @param subPackage
	 *            the sub package
	 * @param containerController
	 *            the container controller
	 * @param paramMap
	 *            the param map
	 * @return the new instance
	 */
	public Object getNewInstance(String treeId, String subPackage, IContainerController containerController, ParamMap paramMap) {
		return prepareAndInstantiateClass(treeId, subPackage,
				new Class[] { AdiPluginResources.class, IContainerController.class, ParamMap.class },
				new Object[] { this, containerController, paramMap });

	}

	/**
	 * Gets the config tree.
	 * 
	 * @param configFileName
	 *            the config file name
	 * @return the config tree
	 */
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
				Bundle bundle = Platform.getBundle(pluginName);
				URL configUrl = bundle.getEntry(EngineConstants.XML_FILES_PATH.concat("/").concat(configFileName));
				if (null != configUrl) {
					inputStream = configUrl.openStream();
				}
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

	/**
	 * Gets the plugin entity.
	 * 
	 * @param entityURI
	 *            the entity URI
	 * @return the plugin entity
	 */
	public PluginEntity<?> getPluginEntity(String entityURI) {
		return getPluginEntityTree().getPluginEntityURIMap().get(entityURI);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.tabular.IMarshalledElement#preMarshal()
	 */
	@Override
	public Object preMarshal(boolean closingPart) {
		return pluginName;
	}

	/**
	 * Gets the plugin entity tree.
	 *
	 * @return the plugin entity tree
	 */
	public APluginEntityTree getPluginEntityTree() {
		if (null == pluginEntityTree)
			loadPluginEntities();
		return pluginEntityTree;
	}
}
