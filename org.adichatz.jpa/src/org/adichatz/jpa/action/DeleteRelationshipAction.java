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
package org.adichatz.jpa.action;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.LocalLazyNode;
import org.adichatz.engine.cache.RelationshipUpdate;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.jpa.data.ManyToManyEntitySet;
import org.adichatz.jpa.tabular.EntitySetContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteRelationshipAction.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class DeleteRelationshipAction extends ATabularAction {

	private ADataAccess dataAccess;

	/**
	 * Instantiates a new delete entity action.
	 */
	public void init() {
		setText(getFromJpaBundle("table.deleteRelationship"));
		setToolTipText(getFromJpaBundle("table.deleteRelationshipToolTip"));
		setImageDescriptor(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_ENTITY_DELETE"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void runAction() {
		EntitySetContentProvider<?> contentProvider = (EntitySetContentProvider<?>) tabularController.getViewer()
				.getContentProvider();
		if (openDialogWhenError(null == tabularController.getEntity(), "action.notPossibleAction",
				"table.cannotRemoveEntityNoParent"))
			return;
		try {
			dataAccess = contentProvider.getQueryManager().getEntityMM().getDataAccess();
			IEntity<?> entity = dataAccess.getDataCache().fetchTransientEntity(
					((IStructuredSelection) tabularController.getViewer().getSelection()).getFirstElement(), null);
			if (dataAccess.lock(tabularController.getBindingService(), IEntityConstants.MERGE, entity))
				if (contentProvider.getEntitySet() instanceof ManyToManyEntitySet) {
					removeManyToMany(entity, contentProvider.getEntitySet());
				} else {
					String mappedBy = contentProvider.getEntitySet().getMappedBy();
					ReflectionTools.invokeMethod(entity.getEntityMM().getFieldMap().get(mappedBy).getSetMethod(),
							entity.getBeanInterceptor(), new Object[] { null });
					if (null != entity.getLazyFetchManager()) {
						LocalLazyNode node = entity.getLazyFetchManager().getLazyFetchMemberMap().get(mappedBy);
						if (null != node)
							node.setBean(null);
					}

				}
		} catch (Exception e) {
			LogBroker.logError(e);
		}
	}

	/**
	 * Removes the many to many.
	 * 
	 * @param entity
	 *            the entity
	 * @param entitySet
	 *            the entity set
	 */
	private void removeManyToMany(IEntity<?> entity, EntitySet<?> entitySet) {
		/*
		 * Finds linked EntitySet
		 */
		EntitySet<?> linkedPS = entity.getEntityMM().getEntitySet(entitySet.getMappedBy());
		if (null == linkedPS) {
			EngineTools.openDialog(MessageDialog.ERROR, getFromJpaBundle("action.notPossibleAction"),
					"Linked entitySet was not found.\nDo not know how to spread update!");
			return;
		}

		IEntity<?> parentEntity = tabularController.getEntity();
		if (dataAccess.lock(tabularController.getBindingService(), IEntityConstants.MERGE, parentEntity, entity)) {
			RelationshipUpdate<?> ru1 = dataAccess.getDataCache().doRelationshipUpdate(parentEntity, entitySet.getMappedBy(),
					entity.getBean(), null, IEntityConstants.relationshipUpdateType.REMOVE_MANY_TO_MANY, entitySet.getFieldName());
			RelationshipUpdate<?> ru2 = dataAccess.getDataCache().doRelationshipUpdate(entity, linkedPS.getMappedBy(),
					parentEntity.getBean(), null, IEntityConstants.relationshipUpdateType.REMOVE_MANY_TO_MANY,
					linkedPS.getFieldName());

			ru1.setLinkedRelationship(ru2);
			ru2.setLinkedRelationship(ru1);
		} else {
			EngineTools.openDialog(MessageDialog.WARNING, getFromJpaBundle("detail.entityCannotBeLockTitle"),
					getFromJpaBundle("table.bothEntitiesCannotBeLockMessage"));
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
