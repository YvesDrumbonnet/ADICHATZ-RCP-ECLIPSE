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

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.ApplicationEvent.EVENT_TYPE;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.MessageUtility;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.jpa.data.AdiOneToOne;
import org.adichatz.jpa.data.AdiOneToOnes;

// TODO: Auto-generated Javadoc
/**
 * The Class AddOTOEntityAction.
 * 
 * @param <T>
 *            the generic type
 * @author Yves Drumbonnet
 * 
 */
public class AddOTOEntityAction<T> extends AOneToOneAction {

	private boolean forceEnabled = true;

	/**
	 * Instantiates a new adds the oto entity action.
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

		if (parentEntity.getLockingBindingService() != null
				&& !parentEntity.getLockingBindingService().equals(entityManagerController.getBindingService())) {
			MessageUtility.displayEntityMessage(parentEntity, getFromEngineBundle("detail.entityLockedInOtherEnvironment"),
					getFromJpaBundle("detail.entityCannotBeDeletedInEnvironment"), true);
			return;
		}

		ADataAccess dataAccess = parentEntity.getEntityMM().getDataAccess();
		IEntity<T> childEntity = null;
		AdiOneToOnes annotation = parentEntity.getBeanClass().getAnnotation(AdiOneToOnes.class);
		AEntityMetaModel<?> childEntityMM = null;
		String fieldName = (String) entityManagerController.getControl().getData("ONE_TO_ONE");
		for (AdiOneToOne oto : annotation.oneToOnes()) {
			if (fieldName.equals(oto.fieldName())) {
				childEntityMM = parentEntity.getEntityMM().getPluginEntity().getPluginEntityTree().getEntityMM(oto.entityURI());
				break;
			}
		}
		if (null == childEntityMM)
			logError(getFromJpaBundle("detail.no.OTO.found", fieldName,
					parentEntity.getEntityMM().getPluginEntity().getEntityURI()));
		else {
			childEntity = (IEntity<T>) dataAccess.getDataCache().putNewEntity(childEntityMM.getBeanClass());
			Method setParentMethod = ReflectionTools.getMethod(childEntity.getBeanClass(),
					"set".concat(parentEntity.getEntityMM().getEntityId()), new Class[] { parentEntity.getBeanClass() });
			try {
				setParentMethod.invoke(childEntity.getBeanInterceptor(), parentEntity.getBean());
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				logError(e);
			}

			ABindingService bindingService = entityManagerController.getParentController().getBindingService();
			if (null != bindingService) {
				dataAccess.lock(bindingService, IEntityConstants.MERGE, parentEntity);
				new EntityInjection(entityManagerController, childEntity);
				final AEntityListener postRefreshListener = new AEntityListener(entityManagerController, IEventType.POST_REFRESH) {
					@Override
					public void handleEntityEvent(AdiEntityEvent event) {
						refreshNewOTO(entity);
					}
				};
				childEntity.addEntityListener(postRefreshListener);
				childEntity.addEntityListener(new AEntityListener(entityManagerController, IEventType.POST_SAVE) {
					@Override
					public void handleEntityEvent(AdiEntityEvent event) {
						postRefreshListener.dispose();
						dispose();
					}
				});
			}

			entityManagerController.endLifeCycleAndSync();
			AdichatzApplication.fireListener(new ApplicationEvent(EVENT_TYPE.POST_CYCLE, entityManagerController));
			EngineTools.reinitMiglayout(entityManagerController.getComposite());
			if (null != bindingService) {
				childEntity.lock(true, bindingService);
				ICollectionController parentController = entityManagerController.getParentController();
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
	}

	@Override
	protected boolean enable(IEntity<?> entity) {
		return null == entity && forceEnabled;
	}

	public void setForceEnabled(boolean forceEnabled) {
		this.forceEnabled = forceEnabled;
	}
}
