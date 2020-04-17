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
package org.adichatz.jpa.extra;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.common.ResourceBundleManager;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.e4.resource.EngineE4Util;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.engine.xjc.ParamType;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.jpa.data.JPAEntity;
import org.adichatz.jpa.editor.EntityEditorPart;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.xjc.ObjectFactory;
import org.adichatz.jpa.xjc.PreferenceTree;
import org.eclipse.jface.dialogs.MessageDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAUtil.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class JPAUtil {

	/** The unmarshaller. */
	private static Unmarshaller UNMARSHALLER;

	/** The RECENT_OPEN_EDITOR. */
	public static String RECENT_OPEN_EDITOR = "RECENT_OPEN_EDITOR";

	/** The RECENT_QUERY_PREFERENCE. */
	public static String RECENT_QUERY_PREFERENCE = "RECENT_QUERY_PREFERENCE";

	/** The ENTITY_EDITOR_CONTRIBUTION_URI. */
	public static String ENTITY_EDITOR_CONTRIBUTION_URI = "bundleclass://".concat(EngineConstants.JPA_BUNDLE).concat("/")
			.concat(EntityEditorPart.class.getName());

	/** The JPA bundle manager. */
	public static ResourceBundleManager JPA_BUNDLE_MANAGER = AdichatzApplication.getPluginResources(EngineConstants.JPA_BUNDLE)
			.getResourceBundleManager();

	/** The jpa RBM. */
	private static AdiResourceBundle jpaRBM = JPA_BUNDLE_MANAGER.getResourceBundle("adichatzJpa");

	/**
	 * Gets the child.
	 * 
	 * @param childControllers
	 *            the child controllers
	 * @param controllerClass
	 *            the controller class
	 * @return the child
	 */
	public static Object getChild(List<AWidgetController> childControllers, Class<?> controllerClass) {
		String controllerClassName = controllerClass.getName();
		for (Object controller : childControllers) {
			Class<?> superClass = controller.getClass();
			while (!Object.class.equals(superClass)) {
				if (controllerClassName.equals(superClass.getName()))
					return controller;
				superClass = superClass.getSuperclass();
			}
		}
		for (Object controller : childControllers) {
			if (controller instanceof ICollectionController) {
				Object child = getChild(((ICollectionController) controller).getChildControllers(), controllerClass);
				if (null != child)
					return child;
			}
		}
		return null;
	}

	/**
	 * Gets the child controller.
	 * 
	 * @param parentController
	 *            the parent controller
	 * @param indexes
	 *            the indexes
	 * @return the child controller
	 */
	public static AWidgetController getChildController(ICollectionController parentController, int... indexes) {
		AWidgetController childController = parentController.getChildControllers().get(indexes[0]);
		if (indexes.length > 1 && childController instanceof ICollectionController)
			return getChildController((ICollectionController) childController, Arrays.copyOfRange(indexes, 1, indexes.length));
		return childController;
	}

	/**
	 * Gets the parent.
	 * 
	 * @param childController
	 *            the child controller
	 * @param controllerClass
	 *            the controller class
	 * @return the parent
	 */
	public static Object getParent(ICollectionController childController, Class<?> controllerClass) {
		String controllerClassName = controllerClass.getName();
		ICollectionController parentController = childController;
		while (null != parentController) {
			Class<?> superClass = parentController.getClass();
			while (!Object.class.equals(superClass)) {
				if (controllerClassName.equals(superClass.getName()))
					return parentController;
				superClass = superClass.getSuperclass();
			}
			parentController = parentController.getParentController();
		}
		return null;
	}

	/**
	 * Gets the param map.
	 * 
	 * @param params
	 *            the params
	 * @return the param map
	 */
	public static ParamMap getParamMap(List<ParamType> params) {
		ParamMap paramMap = new ParamMap();
		for (ParamType param : params) {
			Object value = param instanceof IMarshalledWrapper ? param : param.getValue();
			paramMap.put(param.getId(), value);
		}
		return paramMap;
	}

	/**
	 * Open form.
	 * 
	 * @param controller
	 *            the controller
	 */
	public static void openForm(Object controller) {
		ATabularController<?> tabularController = (ATabularController<?>) controller;
		Object selectedObject = tabularController.getSelectedObject();
		if (null != selectedObject) {
			ATabularContentProvider<?> cp = (ATabularContentProvider<?>) tabularController.getViewer().getContentProvider();
			JPAQueryManager<?> queryManager = ((JPAQueryManager<?>) cp.getQueryManager());
			JPADataAccess dataAccess = (JPADataAccess) cp.getQueryManager().getEntityMM().getDataAccess();
			JPAEntity<?> entity = (JPAEntity<?>) dataAccess.getDataCache().fetchEntity(selectedObject);
			// dataAccess.findEntity(proxyEntity)
			if (IEntityConstants.REMOVE == entity.getStatus()) {
				String title = getFromJpaBundle("entity.marked.deleted.title");
				String bindingTitle = null == entity.getLockingBindingService() ? "" : entity.getLockingBindingService().getTitle();
				EngineTools.openDialog(MessageDialog.INFORMATION, title, getFromJpaBundle("operation.not.possible"), title,
						getFromJpaBundle("entity.marked.deleted.message", bindingTitle, bindingTitle));
				return;
			}

			// Refresh entity if query is dirty see JPAQuerryManager#setDirty().
			for (String lazyFetchMember : queryManager.getLazyFetchMembers())
				entity.addLazyFetch(null, lazyFetchMember);
			openForm(tabularController.getContext(), tabularController.getPluginResources(), entity);
		}
	}

	/**
	 * Open form.
	 *
	 * @param eclipseContext
	 *            the eclipse context
	 * @param pluginResources
	 *            the plugin resources
	 * @param entity
	 *            the entity
	 */
	public static void openForm(Object eclipseContext, AdiPluginResources pluginResources, JPAEntity<?> entity) {
		ParamMap paramMap = new ParamMap();
		paramMap.put(ParamMap.ENTITY, entity);
		//
		PluginEntity<?> pluginEntity = pluginResources.getPluginEntity(entity.getEntityMM().getPluginEntity().getEntityURI());
		paramMap.put(ParamMap.ADI_RESOURCE_URI, pluginEntity.getAdiResourceUriMap().get("EDITOR"));
		paramMap.put(ParamMap.CONTRIBUTION_URI, JPAUtil.ENTITY_EDITOR_CONTRIBUTION_URI);
		paramMap.put(ParamMap.PLUGIN_RESOURCES, pluginResources);

		String title = entity.getEntityMM().getEntityId().concat(" ").concat(entity.getEntityMM().getIdString(entity.getBean()));
		paramMap.put(ParamMap.TITLE, title);
		paramMap.put(ParamMap.TOOL_TIP_TEXT, title);
		String imageURI = pluginEntity.getImageKeyMap().get(EngineConstants.EDITOR);
		String iconURI = null == imageURI ? "platform:/plugin/org.adichatz.engine/resources/icons/IMG_EDITOR.png"
				: EngineE4Util.getIconURI(pluginEntity.getEntityKeys()[0], imageURI);
		paramMap.put(ParamMap.ICON_URI, iconURI);

		E4AdichatzApplication.openPart(eclipseContext, paramMap);

	}

	/**
	 * Gets the value from JPA bundle.
	 * 
	 * @param key
	 *            the key
	 * @param variables
	 *            the variables
	 * @return the from bundle
	 */
	public static String getFromJpaBundle(String key, Object... variables) {
		return jpaRBM.getValueFromBundle(key, variables);
	}

	/**
	 * Gets the preference tree.
	 *
	 * @param fileName
	 *            the file name
	 * @param preferenceFile
	 *            the preference file
	 * @return the preference tree
	 */
	public static PreferenceTree getPreferenceTree(String fileName, File preferenceFile) {
		if (null == fileName)
			return null;
		if (null == preferenceFile)
			preferenceFile = new File(EngineConstants.getAdiPermanentDirName().concat("/").concat(fileName));

		if (preferenceFile.exists()) {
			try {
				return ((PreferenceTree) FileUtility.getTreeFromXmlFile(JPAUtil.getUnmarshaller(), preferenceFile));
			} catch (JAXBException e) {
				logError(getFromJpaBundle("preference.cannot.unmarshal", fileName), e);
			}
		} else
			logError(getFromJpaBundle("preference.cannot.unmarshal", fileName));
		return null;
	}

	/**
	 * Gets the preference key.
	 *
	 * @param preferenceURI
	 *            the preference URI
	 * @return the preference key
	 */
	public static String getPreferenceKey(String preferenceURI) {
		if (preferenceURI.startsWith(EngineConstants.PREF_FILE_URI_PREFIX)
				|| preferenceURI.startsWith(EngineConstants.PREF_NAME_URI_PREFIX)) {
			String preferenceKey = preferenceURI.substring(17);
			int index = preferenceKey.indexOf('/');
			return -1 == index ? preferenceKey : preferenceKey.substring(0, index);
		} else
			logError(getFromJpaBundle("preference.invalid.uri", preferenceURI));
		return null;
	}

	/**
	 * Gets the unmarshaller.
	 * 
	 * @return the unmarshaller
	 */
	public static Unmarshaller getUnmarshaller() {
		if (null == UNMARSHALLER) {
			JAXBContext jc = null;
			try {
				jc = JAXBContext.newInstance(ObjectFactory.class);
				UNMARSHALLER = jc.createUnmarshaller();
			} catch (JAXBException e) {
				logError(e);
			}
		}
		return UNMARSHALLER;
	}
}
