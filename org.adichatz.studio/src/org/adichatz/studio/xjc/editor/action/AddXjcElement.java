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
package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.tree.ATreeChild;
import org.adichatz.generator.xjc.EntitySetContentProviderType;
import org.adichatz.generator.xjc.ListDetailContentProviderType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.QueryContentProviderType;
import org.adichatz.studio.gencode.editor.XjcTreeManager;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.adichatz.studio.xjc.scenario.XjcEntitySet;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreePath;

// TODO: Auto-generated Javadoc
/**
 * The Class AddXjcElement.
 */
public class AddXjcElement {

	/** The add xjc element mm. */
	MenuManagerController addXjcElementMM;

	/** The tree element. */
	XjcTreeElement treeElement;

	/** The tree controller. */
	XjcTreeController treeController;

	/** The meta model. */
	// MetaModel metaModel;
	APluginEntityTree pluginEntityTree;

	/** The parent entity. */
	PluginEntity parentEntity;

	/**
	 * Instantiates a new adds the xjc element.
	 * 
	 * @param addXjcElementMM the add xjc element mm
	 */
	public AddXjcElement(MenuManagerController addXjcElementMM) {
		this.addXjcElementMM = addXjcElementMM;
		treeController = (XjcTreeController) addXjcElementMM.getMenuContainer();

		pluginEntityTree = treeController.getPluginResources().getPluginEntityTree();
		XjcTreeManager treeManager = treeController.getTreeManager();

		ITreeSelection selection = (ITreeSelection) treeController.getViewer().getSelection();
		if (1 != selection.size())
			addXjcElementMM.setEnabled(false);
		else {
			treeElement = (XjcTreeElement) treeController.getSelectedObject();
			parentEntity = pluginEntityTree.getPluginEntity(treeElement.getElement().getClass());
			TreePath parentPath = selection.getPaths()[0];
			List<AddSpecificParamAction> actions = getRefFieldAddAction(
					treeManager.getTreeChild(treeElement.getElement()), parentPath, treeElement.getElement(),
					parentEntity);

			if (!actions.isEmpty()) {
				Collections.sort(actions);
				for (AddSpecificParamAction addAction : actions)
					addXjcElementMM.getControl().add(addAction);
				addXjcElementMM.getControl().add(new Separator());
			}

			actions = getEntitySetAddAction(parentPath, parentEntity);

			Collections.sort(actions);
			for (AddSpecificParamAction addAction : actions)
				addXjcElementMM.getControl().add(addAction);
		}
	}

	/**
	 * Gets the ref field add action.
	 * 
	 * @param treeChild      the xjc tree child
	 * @param parentPath     the parent path
	 * @param selectedObject the selected object
	 * @param parentEntity   the parent entity
	 * @return the ref field add action
	 */
	public List<AddSpecificParamAction> getRefFieldAddAction(ATreeChild treeChild, TreePath parentPath,
			Object selectedObject, PluginEntity parentEntity) {
		List<AddSpecificParamAction> actions = new ArrayList<AddSpecificParamAction>();
		if (null != treeChild.getRefFieldNames())
			for (String refFieldName : treeChild.getRefFieldNames()) {
				Method getMethod = FieldTools.getGetMethod(treeElement.getEntity().getBeanClass(), refFieldName, true);
				Class<?> beanClass = getMethod.getReturnType();
				AEntityMetaModel<?> refEntityMM = pluginEntityTree.getPluginEntity(getMethod.getReturnType())
						.getEntityMetaModel();
				if (refEntityMM.getEntityId().equals("Params") //
						|| refEntityMM.getEntityId().equals("Accessibilities") //
						|| refEntityMM.getEntityId().equals("LazyFetches") //
						|| refEntityMM.getEntityId().equals("Listeners") //
						|| refEntityMM.getEntityId().equals("Filters") //
						|| refEntityMM.getEntityId().equals("Validators")) {
					Method genericMethod = beanClass.getDeclaredMethods()[0];
					AddSpecificParamAction addXmlElementAction = new AddSpecificParamAction(treeController,
							genericMethod, true);
					addXmlElementAction.setParentGetMethod(getMethod);
					actions.add(addXmlElementAction);
					if (refEntityMM.getEntityId().equals("Params")) {
						try {
							ParamsType params = (ParamsType) getMethod.invoke(selectedObject);
							if (!hasSpecificParam(params, ParamMap.CONTENT_PROVIDER)) {
								actions.add(addSpecificParam(getMethod, null, QueryContentProviderType.class));
								actions.add(addSpecificParam(getMethod, null, EntitySetContentProviderType.class));
								actions.add(addSpecificParam(getMethod, null, ListDetailContentProviderType.class));
							}
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							logError(e);
						}
					}
				} else if (null == ReflectionTools.invokeMethod(getMethod, selectedObject)) {
					if ("contentProvider".equals(refFieldName)) {
						actions.add(new AddSpecificParamAction(treeController, getMethod,
								QueryContentProviderType.class, false));
						actions.add(new AddSpecificParamAction(treeController, getMethod,
								EntitySetContentProviderType.class, false));
						actions.add(new AddSpecificParamAction(treeController, getMethod,
								ListDetailContentProviderType.class, false));
					} else
						actions.add(new AddSpecificParamAction(treeController, getMethod, false));
				}
			}
		return actions;
	}

	/**
	 * Checks for specific param.
	 * 
	 * @param params the params
	 * @return true, if successful
	 */
	private boolean hasSpecificParam(ParamsType params, String paramKey) {
		if (null != params)
			for (ParamType param : params.getParam())
				if (paramKey.equals(param.getId()))
					return true;
		return false;
	}

	/**
	 * Gets the entity set add action.
	 * 
	 * @param parentPath   the parent path
	 * @param pluginEntity the plugin entity
	 * @return the entity set add action
	 */
	public List<AddSpecificParamAction> getEntitySetAddAction(TreePath parentPath, PluginEntity<?> pluginEntity) {
		List<AddSpecificParamAction> actions = new ArrayList<AddSpecificParamAction>();
		Set<String> elementIds = new HashSet<String>();
		while (null != pluginEntity) {
			for (PropertyField propertyField : pluginEntity.getEntityMetaModel().getFieldMap().values()) {
				if (propertyField instanceof XjcEntitySet) {
					XjcEntitySet<?> entitySet = (XjcEntitySet<?>) propertyField;
					if (null != entitySet.getElementIds()) {
						Method getMethod = FieldTools.getGetMethod(pluginEntity.getEntityMetaModel().getBeanClass(),
								entitySet.getFieldName(), true);
						for (String elementId : entitySet.getElementIds()) {
							if (!"GenericField".equals(elementId)) { // AVISATZ: GenericField must implement special
																		// rules
																		// (controllerClasssName is mandatory...). Today
																		// forget it.
								if (!elementIds.contains(elementId)) {
									AEntityMetaModel<?> entityMM = pluginEntityTree.getEntityMM(elementId);
									actions.add(new AddSpecificParamAction(treeController, getMethod, entityMM));
									elementIds.add(elementId);
								}
							}
						}
						if (ParamsType.class.equals(pluginEntity.getEntityMetaModel().getBeanClass())) {
							ParamsType params = (ParamsType) treeElement.getElement();
							if (!hasSpecificParam(params, ParamMap.CONTENT_PROVIDER)) {
								actions.add(addSpecificParam(getMethod, null, QueryContentProviderType.class));
								actions.add(addSpecificParam(getMethod, null, ListDetailContentProviderType.class));
								actions.add(addSpecificParam(getMethod, null, EntitySetContentProviderType.class));
							}
						}
					}
				}
			}
			pluginEntity = pluginEntityTree
					.getPluginEntity(pluginEntity.getEntityMetaModel().getBeanClass().getSuperclass());
		}
		return actions;
	}

	/**
	 * Adds the content provider.
	 * 
	 * @param parentPath      the parent path
	 * @param getParamMethod  the get param method
	 * @param parentGetMethod the parent get method
	 * @return the adds the xml element action
	 */
	private AddSpecificParamAction addSpecificParam(Method getParamMethod, Method parentGetMethod,
			Class<?> specificParamClass) {
		AddSpecificParamAction addSpecificParamAction = new AddSpecificParamAction(treeController, getParamMethod,
				specificParamClass, true);
		addSpecificParamAction.setParentGetMethod(parentGetMethod);
		return addSpecificParamAction;
	}
}
