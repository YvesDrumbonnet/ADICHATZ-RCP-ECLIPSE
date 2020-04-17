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
package org.adichatz.jpa.query.action;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.core.ATabularCore;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.extra.CrossReference;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.jpa.tabular.EntitySetContentProvider;
import org.adichatz.jpa.tabular.QueryPreferenceManager;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;

// TODO: Auto-generated Javadoc
/**
 * The Class CrossReferenceMenuActions.
 */
public class CrossReferenceMenuActions {

	public CrossReferenceMenuActions() {
	}

	/**
	 * Instantiates a new column menu actions.
	 * 
	 * @param menuManagerController
	 *            the menu manager controller
	 */
	public CrossReferenceMenuActions(MenuManagerController menuManagerController) {
		final ATabularController<?> tabularController = (ATabularController<?>) menuManagerController.getMenuContainer();
		if (null != tabularController) {
			Action action;
			for (final CrossReference crossReference : ((ATabularCore<?>) tabularController.getGenCode()).getCrossReferences()) {
				action = new Action(crossReference.getDescription(), SWT.CHECK) {
					public void runWithEvent(Event event) {
						E4AdichatzApplication.openPart(tabularController.getContext(),
								getCrossRefParamMap(tabularController, crossReference));
					}
				};
				menuManagerController.getControl().add(action);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ParamMap getCrossRefParamMap(ATabularController<T> tabularController, CrossReference crossReference) {
		ATabularContentProvider<T> contentProvider = (ATabularContentProvider<T>) tabularController.getViewer()
				.getContentProvider();
		AEntityMetaModel<T> entityMM = contentProvider.getQueryManager().getEntityMM();
		Object bean = tabularController.getSelectedObject();
		IEntity<?> entity = entityMM.getPluginEntity().getPluginResources().getDataAccess().getDataCache().fetchEntity(bean);
		EntitySet<T> entitySet = (EntitySet<T>) entityMM.getEntitySet(crossReference.getEntitySetId());
		EntitySetContentProvider<T> entityContentProvider = new EntitySetContentProvider(entitySet, crossReference.getQueryURI(),
				crossReference.getPreferenceURI());

		String title = entityMM.getEntityId() + " " + entity.getBeanId() + " X " + entitySet.getFieldName();
		ParamMap paramMap = new ParamMap();
		paramMap.put(ParamMap.ADD_EDITOR_TOOLBAR, "true");
		paramMap.put(ParamMap.TITLE, title);
		paramMap.put(ParamMap.TOOL_TIP_TEXT, title);
		paramMap.put(ParamMap.EDITOR_ID, "org.adichatz.jpa.query.ListDetailFormEditor");
		paramMap.put(ParamMap.ADI_RESOURCE_URI, "adi://org.adichatz.jpa/query/ListDetailQueryForm");
		paramMap.put(ParamMap.CONTENT_PROVIDER, entityContentProvider);

		paramMap.put(ParamMap.ICON_URI, "platform:/plugin/org.adichatz.engine/resources/icons/IMG_CROSS_REFERENCE.png");
		paramMap.put("FORM_TEXT",
				tabularController.getPluginResources().getMessage(EngineTools.lowerCaseFirstLetter(entityMM.getEntityId()),
						"crossReference." + entitySet.getFieldName(), entity.getEntityMM().getIdString(entity.getBean())));

		paramMap.put(ParamMap.DETAIL_RESOURCE_URI, crossReference.getDetailURI());
		paramMap.put(ParamMap.TABLE_RESOURCE_URI, crossReference.getTableURI());
		paramMap.put(ParamMap.ENTITY,
				entityMM.getPluginEntity().getPluginResources().getDataAccess().getDataCache().fetchEntity(bean));
		paramMap.put(ParamMap.CUSTOMIZATION, new CrossReferenceCustomizationMap());
		((QueryPreferenceManager<T>) entityContentProvider.getQueryManager().getQueryPreferenceManager())
				.addParentParameter(entitySet.getParentClause(), bean);
		return paramMap;
	}
}
