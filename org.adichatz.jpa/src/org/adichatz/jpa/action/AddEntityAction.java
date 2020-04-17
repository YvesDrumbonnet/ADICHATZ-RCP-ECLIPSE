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
package org.adichatz.jpa.action;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.jpa.data.JPAEntity;
import org.adichatz.jpa.tabular.EntitySetContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;

// TODO: Auto-generated Javadoc
/**
 * The Class AddEntityAction.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class AddEntityAction<T> extends ATabularAction {

	/**
	 * Instantiates a new delete entity action.
	 */
	public void init() {
		this.setText(getFromJpaBundle("table.addEntity"));
		this.setToolTipText(getFromJpaBundle("table.addEntityToolTip"));
		setImageDescriptor(AdichatzApplication.getInstance().getImageDescriptor(EngineConstants.ENGINE_BUNDLE, "IMG_CREATE.png"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void runAction() {
		IEntity<?> parentEntity = tabularController.getEntity();
		ADataAccess dataAccess = parentEntity.getEntityMM().getDataAccess();
		if (openDialogWhenError(null == parentEntity, "action.notPossibleAction", "table.cannotAddEntityNoParent"))
			return;
		IEntity<T> entity = (IEntity<T>) dataAccess.getDataCache().putNewEntity(tabularController.getBeanClass());
		ABindingService bindingService = tabularController.getBindingService();

		if (null != bindingService)
			entity.lock(true, bindingService);
		/*
		 * if parent entity is known, initialize the value of the corresponding child field with the parent bean.<br> This change is
		 * enough when persistent set is bound<br> in other case, you must add the entity.
		 */
		EntitySet<?> entitySet = ((EntitySetContentProvider<T>) tabularController.getViewer().getContentProvider()).getEntitySet();
		ReflectionTools.invokeMethod(entity.getEntityMM().getFieldMap().get(entitySet.getMappedBy()).getSetMethod(),
				entity.getBeanInterceptor(), new Object[] { parentEntity.getBean() });

		if (null != entity.getEntityMM().getCksFactory())
			((JPAEntity<T>) entity).setCompositeKeyStrategy(entity.getEntityMM().getCksFactory()
					.createCompositeKeyStrategy((JPAEntity<?>) parentEntity, (JPAEntity<T>) entity, entitySet));

		// Set selection must not be done in a new Thread elsewhere, BindingDispatcher is not instantiated!
		// depending of AEntityManagerController#setManagedEntity(entity).
		tabularController.setSelectInNewThread(false);
		tabularController.getViewer().setSelection(new StructuredSelection(new Object[] { entity.getBean() }));
		tabularController.setSelectInNewThread(true);

		if (null != bindingService) {
			ICollectionController parentController = tabularController.getParentController();
			while (null != parentController) {
				parentController = parentController.getParentController();
				if (parentController instanceof BoundedPart) {
					bindingService.setDirty();
					E4AdichatzApplication.getInstance().enableToolBar(parentController);
					break;
				}
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
