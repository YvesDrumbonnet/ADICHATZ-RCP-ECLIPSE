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
package org.adichatz.engine.validation;

import java.util.HashSet;
import java.util.Set;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.ADirtyContainerController;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.controller.ICollectionController;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityBindingDispatcher.
 * 
 * Class for managing the dispatch of dirty and locks on others controllers when entity are updated, refreshed or saved inside a BindingService.
 */
public class EntityBindingDispatcher {

	private Set<EntityInjection> entityInjections = new HashSet<EntityInjection>();

	/** The entity. */
	private IEntity<?> entity;

	private Set<String> lazyFetchMembers;

	/**
	 * Instantiates a new binding dispatcher.
	 * 
	 * @param entity
	 *            the entity
	 */
	public EntityBindingDispatcher(IEntity<?> entity) {
		this.entity = entity;
	}

	/**
	 * Handle locking event.
	 */
	public void handleLockingEvent(AWidgetController controller) {
		for (EntityInjection entityInjection : entityInjections.toArray(new EntityInjection[entityInjections.size()])) {
			AEntityManagerController entityController = entityInjection.getEntityController();
			if (null == controller || controller.equals(entityController)) {
				// When controller is set (initialization), process only for specified entity controller.  
				ABindingService bindingService = entityController.getBindingService();
				boolean lockDirtyProcess = entity.isLocked() != entityInjection.isLocked();
				if (lockDirtyProcess) {
					boolean isCurrentBSLocking = null != entity.getLockingBindingService()
							&& bindingService.equals(entity.getLockingBindingService());
					entityController.lockFieldControllers(!isCurrentBSLocking && entity.isLocked());
					boolean setDirty = entity.isLocked() && isCurrentBSLocking;
					boolean changeDirty = (entity.isLocked() && isCurrentBSLocking) || (setDirty != entityController.isDirty());
					// Lock child field controllers when entity is locked and controller is not locked and depends on another
					// binding service.
					// Do nothing for controllers which are not locked but depending on locking binding service.
					// Unlock child field controllers when entity is no longer locked and controller is locked.
					if (changeDirty) {
						for (IBoundedController boundedController : entityInjection.getBoundedControllers()) {
							if (boundedController instanceof ADirtyContainerController
									// control could be disposed (dynamic behavior).
									&& null != boundedController.getControl() //
									&& !((ADirtyContainerController) boundedController).getControl().isDisposed()) {
								((ADirtyContainerController) boundedController).setDirty(setDirty);
							}
						}
						if (isCurrentBSLocking) {
							ICollectionController parentController = entityController;
							while (null != parentController) {
								if (parentController instanceof IBoundedController) {
									if (!((IBoundedController) parentController).setDirty(entity.isLocked())) {
										parentController.getGenCode().getContext().getRootCore().getController();
										break;
									}
								}
								parentController = parentController.getParentController();
							}
						}
					}
				} else if (entityController.isDirty()) {
					entityController.setDirty(false);
					for (IBoundedController boundedController : entityInjection.getBoundedControllers())
						if (boundedController.isDirty())
							boundedController.setDirty(false);
				}
				entityInjection.setLocked(entity.isLocked());
				//			}
			}
		}
	}

	/**
	 * Handle locking event.
	 */
	public void handleLockingEvent__(AWidgetController controller) {
		for (EntityInjection entityInjection : entityInjections.toArray(new EntityInjection[entityInjections.size()])) {
			AEntityManagerController entityController = entityInjection.getEntityController();
			if (null == controller || controller.equals(entityController)) {
				// When controller is set (initialization), process only for specified entity controller.  
				ABindingService bindingService = entityController.getBindingService();
				boolean lockDirtyProcess = entity.isLocked() != entityInjection.isLocked();
				boolean sameBindingService = bindingService.equals(entity.getLockingBindingService());
				if (!lockDirtyProcess) {
					lockDirtyProcess = entityController.isLocked() && lockDirtyProcess;
				}
				if (lockDirtyProcess) {
					entityController.lockFieldControllers(entity.isLocked() && !sameBindingService);
					boolean setDirty = entity.isLocked() && sameBindingService || true;
					// Lock child field controllers when entity is locked and controller is not locked and depends on another
					// binding service.
					// Do nothing for controllers which are not locked but depending on locking binding service.
					// Unlock child field controllers when entity is no longer locked and controller is locked.
					if (setDirty) {
						for (IBoundedController boundedController : entityInjection.getBoundedControllers()) {
							if (boundedController instanceof ADirtyContainerController
									// control could be disposed (dynamic behavior).
									&& null != boundedController.getControl() //
									&& !((ADirtyContainerController) boundedController).getControl().isDisposed())
								((ADirtyContainerController) boundedController).setDirty(entity.isLocked());
						}
						if (sameBindingService) {
							ICollectionController parentController = entityController;
							while (null != parentController) {
								if (parentController instanceof IBoundedController) {
									if (!((IBoundedController) parentController).setDirty(entity.isLocked())) {
										parentController.getGenCode().getContext().getRootCore().getController();
										break;
									}
								}
								parentController = parentController.getParentController();
							}
						}
					} else if (entityController.isDirty()) {
						entityController.setDirty(false);
						for (IBoundedController boundedController : entityInjection.getBoundedControllers())
							if (boundedController.isDirty())
								boundedController.setDirty(false);
					}
					entityInjection.setLocked(entity.isLocked());
				}
			}
		}
	}

	public void addLazyFetchMembers(String... lazyFetchMembers) {
		for (String lazyFetchMember : lazyFetchMembers)
			this.lazyFetchMembers.add(lazyFetchMember);
	}

	public Set<String> getLazyFetchMembers() {
		return lazyFetchMembers;
	}

	public Set<EntityInjection> getEntityInjections() {
		return entityInjections;
	}

	public void dispose() {
		for (EntityInjection entityInjection : entityInjections) {
			entityInjection.clearTransientListeners();
		}
		entityInjections.clear();
	}
}
