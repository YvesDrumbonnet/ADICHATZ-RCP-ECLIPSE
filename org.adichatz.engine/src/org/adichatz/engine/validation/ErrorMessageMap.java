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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.IValidableController;

// TODO: Auto-generated Javadoc
/**
 * The Class ErrorMessageMap.
 */
public class ErrorMessageMap {
	ABindingService bindingService;

	public ErrorMessageMap(ABindingService bindingService) {
		this.bindingService = bindingService;
	}

	/** The error message by controller. */
	private Map<IValidableController, Set<ErrorMessage>> errorMessageByController = new HashMap<IValidableController, Set<ErrorMessage>>();

	/** The error message by entity. */
	private Map<IEntity<?>, Set<ErrorMessage>> errorMessageByEntity = new HashMap<IEntity<?>, Set<ErrorMessage>>();

	/**
	 * Adds the.
	 * 
	 * @param errorMessage
	 *            the error message
	 */
	public boolean add(ErrorMessage errorMessage) {
		Set<ErrorMessage> errorMessages;
		if (null != errorMessage.getEntity()) {
			errorMessages = errorMessageByEntity.get(errorMessage.getEntity());
			if (null == errorMessages) {
				errorMessages = new HashSet<ErrorMessage>();
				errorMessageByEntity.put(errorMessage.getEntity(), errorMessages);
				bindingService.refreshIncludedTables(errorMessage.getEntity());
			}
			errorMessages.add(errorMessage);
		}
		errorMessages = errorMessageByController.get(errorMessage.getValidator().getTriggeringController());
		if (null == errorMessages) {
			errorMessages = new HashSet<ErrorMessage>();
			errorMessageByController.put(errorMessage.getValidator().getTriggeringController(), errorMessages);
		}
		return errorMessages.add(errorMessage);
	}

	/**
	 * Removes the.
	 * 
	 * @param errorMessage
	 *            the error message
	 */
	public boolean remove(ErrorMessage errorMessage) {
		boolean result = false;
		Set<ErrorMessage> errorMessages = errorMessageByController.get(errorMessage.getValidator().getTriggeringController());
		if (null != errorMessages) {
			result = errorMessages.remove(errorMessage);
			if (errorMessages.isEmpty())
				errorMessageByController.remove(errorMessage.getValidator().getTriggeringController());
		}

		if (null != errorMessage.getEntity()) {
			errorMessages = errorMessageByEntity.get(errorMessage.getEntity());
			if (null != errorMessages) {
				errorMessages.remove(errorMessage);
				if (errorMessages.isEmpty()) {
					errorMessageByEntity.remove(errorMessage.getEntity());
					bindingService.refreshIncludedTables(errorMessage.getEntity());
				}
			}
		}
		return result;
	}

	/**
	 * Clear.
	 */
	public void clear() {
		for (Set<ErrorMessage> errorMessages : errorMessageByController.values())
			errorMessages.clear();
		for (Set<ErrorMessage> errorMessages : errorMessageByEntity.values())
			errorMessages.clear();
		errorMessageByController.clear();
		errorMessageByEntity.clear();
	}

	/**
	 * Gets the.
	 * 
	 * @param controller
	 *            the controller
	 * @return the set
	 */
	public Set<ErrorMessage> get(IValidableController controller) {
		return errorMessageByController.get(controller);
	}

	/**
	 * Gets the.
	 * 
	 * @param entity
	 *            the entity
	 * @return the set
	 */
	public Set<ErrorMessage> get(IEntity<?> entity) {
		return errorMessageByEntity.get(entity);
	}

	/**
	 * Contains.
	 * 
	 * @param controller
	 *            the controller
	 * @return true, if successful
	 */
	public boolean contains(IValidableController controller) {
		return errorMessageByController.containsKey(controller);
	}

	/**
	 * Contains.
	 * 
	 * @param entity
	 *            the entity
	 * @return true, if successful
	 */
	public boolean contains(IEntity<?> entity) {
		return errorMessageByEntity.containsKey(entity);
	}

	/**
	 * Size.
	 * 
	 * @return the int
	 */
	public int size() {
		if (isEmpty())
			return 0;
		int size = 0;
		for (Set<ErrorMessage> errorSet : errorMessageByController.values())
			size += errorSet.size();
		return size;
	}

	/**
	 * Checks if is empty.
	 * 
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return errorMessageByController.isEmpty();
	}

	public Set<IEntity<?>> getEntities() {
		return errorMessageByEntity.keySet();
	}

	public Set<ErrorMessage> getErrorMessages() {
		Set<ErrorMessage> allMessages = new HashSet<ErrorMessage>();
		for (Set<ErrorMessage> errorMessages : errorMessageByController.values())
			allMessages.addAll(errorMessages);
		return allMessages;
	}
}
