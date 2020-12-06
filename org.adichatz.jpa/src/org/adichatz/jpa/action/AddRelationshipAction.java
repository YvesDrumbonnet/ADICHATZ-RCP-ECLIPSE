/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.jpa.action;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.util.Map;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.RelationshipUpdate;
import org.adichatz.engine.contentProvider.QueryContentProvider;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.jpa.data.ManyToManyEntitySet;
import org.adichatz.jpa.extra.SelectRefValueDialog;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.tabular.EntitySetContentProvider;
import org.adichatz.jpa.tabular.JPAQueryContentProvider;
import org.adichatz.jpa.tabular.QueryPreferenceManager;
import org.adichatz.jpa.xjc.QueryOpenClauseType;

// TODO: Auto-generated Javadoc
/**
 * The Class AddEntityAction.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class AddRelationshipAction<T> extends ATabularAction {

	/**
	 * Instantiates a new delete entity action.
	 */
	public void init() {
		setText(getFromJpaBundle("table.addRelationship"));
		setToolTipText(getFromJpaBundle("table.addRelationshipToolTip"));
		setImageDescriptor(toolkit.getRegisteredImageDescriptor("IMG_ADD_RELATIONSHIP"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void runAction() {
		IEntity<?> parentEntity = tabularController.getEntity();
		ADataAccess dataAccess = parentEntity.getEntityMM().getDataAccess();
		EntitySetContentProvider<T> contentProvider = (EntitySetContentProvider<T>) tabularController.getViewer()
				.getContentProvider();

		if (openDialogWhenError(null == parentEntity, "action.notPossibleAction", "table.cannotAddEntityNoParent")
				|| openDialogWhenError(IEntityConstants.PERSIST == parentEntity.getStatus(), "action.notPossibleAction",
						"table.addRelationship.error.entitySet"))
			return;

		// Use a new queryManager so that addParentParam does not involve EntitySetContentProvider
		JPAQueryManager<T> queryManager = (JPAQueryManager<T>) contentProvider.getEntitySet().getQueryManager();
		queryManager.setContentProvider(contentProvider);

		ManyToManyEntitySet<?> manyToManyEntitySet = (ManyToManyEntitySet<?>) contentProvider.getEntitySet();
		QueryOpenClauseType openClause = ((QueryPreferenceManager) queryManager.getQueryPreferenceManager())
				.addParentParameter(manyToManyEntitySet.getParentClause(), parentEntity.getBean());
		openClause.setClause(openClause.getClause().replace("in elements", "not in elements"));

		ParamMap paramMap = new ParamMap();
		paramMap.putAll(manyToManyEntitySet.getParamMap());
		for (Map.Entry<String, Object> entry : actionController.getParamMap().entrySet())
			paramMap.put(entry.getKey(), entry.getValue());
		paramMap.put(ParamMap.ENTITY, parentEntity);
		QueryContentProvider manyToManyCP = new JPAQueryContentProvider(queryManager, manyToManyEntitySet.getAdiQueryURI(),
				manyToManyEntitySet.getPreferenceURI());
		SelectRefValueDialog selectionDialog = new SelectRefValueDialog(manyToManyCP.getQueryManager().getEntityMM(), manyToManyCP,
				paramMap, null);
		selectionDialog.openSelectValue(tabularController.getPluginResources(), null);
		Object selectedValue = selectionDialog.getValue();

		if (null != selectedValue) {
			IEntity<T> entity = (IEntity<T>) dataAccess.getDataCache().fetchEntity(selectedValue);
			if (openDialogWhenWarning(contentProvider.getBeanMap().containsKey(entity.getCacheKey()), "action.notPossibleAction",
					"table.cannotAddEntityAlreadyExist")
					|| openDialogWhenWarning(
							IEntityConstants.RETRIEVE != entity.getStatus()
									&& !entity.getLockingBindingService().equals(tabularController.getBindingService()),
							"action.notPossibleAction", "table.cannotAddEntityOtherEditor"))
				return;
			/*
			 * Finds linked EntitySet
			 */
			EntitySet linkedPS = entity.getEntityMM().getEntitySet(contentProvider.getEntitySet().getMappedBy());
			if (openDialogWhenWarning(null == linkedPS, "action.notPossibleAction",
					"Linked entitySet was not found.\nDo not know how to spread update!"))
				return;

			if (dataAccess.lock(tabularController.getBindingService(), IEntityConstants.MERGE, parentEntity, entity)) {
				ABindingService.setForceRefreshing(true);
				RelationshipUpdate<?> ru1 = new RelationshipUpdate(parentEntity, contentProvider.getEntitySet().getMappedBy(), null,
						entity.getBean(), IEntityConstants.relationshipUpdateType.ADD_MANY_TO_MANY,
						contentProvider.getEntitySet().getFieldName());
				RelationshipUpdate<?> ru2 = new RelationshipUpdate(entity, linkedPS.getMappedBy(), null, parentEntity.getBean(),
						IEntityConstants.relationshipUpdateType.ADD_MANY_TO_MANY, linkedPS.getFieldName());
				ru1.setLinkedRelationship(ru2);
				ru2.setLinkedRelationship(ru1);
				ABindingService.setForceRefreshing(false);
				tabularController.update(entity, entity.getBean());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.action.ATableAction#enable(org.adichatz.engine.cache.Entity)
	 */
	@Override
	protected boolean enable(IEntity<?> entity) {
		return IEntityConstants.PERSIST != entity.getStatus() && (null == entity.getLockingBindingService()
				|| entity.getLockingBindingService().equals(actionController.getBindingService()));
	}
}
