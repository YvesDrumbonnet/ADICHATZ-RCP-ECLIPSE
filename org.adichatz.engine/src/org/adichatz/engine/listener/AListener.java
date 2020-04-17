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
package org.adichatz.engine.listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.MultiKey;

// TODO: Auto-generated Javadoc
/**
 * The Class AListener.
 * 
 * The abstract listener for receiving an event from Adichatz Application.<br>
 * There are five categories of Adichatz Listener for executing code at specific times
 * <ul>
 * <li>Action Listener when firing an action</li>
 * <li>Editor Listener when saving or refreshing editor, loading entity)</li>
 * <li>Entity Listener when status changes...</li>
 * <li>Control Listener when refreshing a table...</li>
 * <li>PostMessage Listener specific to FormMessageManager</li>
 * </ul>
 * 
 * @see AEvent
 */
public abstract class AListener {

	/*
	 * S T A T I C
	 */
	/** The dispose listeners. */
	private static Set<AListener> disposeListeners = new HashSet<AListener>();

	/**
	 * Adds the listener to dispose.
	 * 
	 * @param listener
	 *            the listener
	 */
	public static void addListenerToDispose(AListener listener) {
		disposeListeners.add(listener);
	}

	/**
	 * Fire listener.
	 *
	 * @param listenerMap
	 *            the listener map
	 * @param eventType
	 *            the event type
	 */
	public static void fireListener(Map<MultiKey, AListener> listenerMap, int eventType) {
		fireListener(listenerMap, new AdiEvent(eventType));
	}

	/**
	 * Fire listener.
	 *
	 * @param listenerMap
	 *            the listener map
	 * @param event
	 *            the event
	 */
	public static void fireListener(Map<MultiKey, AListener> listenerMap, AdiEvent event) {
		if (null != listenerMap)
			fireListener(listenerMap.values(), event);
	}

	/**
	 * Fire listener.
	 *
	 * @param listeners
	 *            the listeners
	 * @param event
	 *            the event
	 */
	public static void fireListener(Collection<? extends AListener> listeners, AdiEvent event) {
		if (null != listeners) {
			for (AListener listener : listeners.toArray(new AListener[listeners.size()])) {
				if (listener.eventType == event.getType())
					listener.handleEvent(event);
			}
			/*
			 * disposeListeners supplies a way to dispose a listener in its own handlEvent method.
			 */
			for (AListener listener : disposeListeners.toArray(new AListener[disposeListeners.size()]))
				if (listeners.contains(listener)) {
					listener.dispose();
					disposeListeners.remove(listener);
				}
		}
	}

	/*
	 * end S T A T I C
	 */

	/** The Event type. */
	public int eventType;

	/** The id. */
	private String id;

	/** The to be removed flag. */
	protected boolean toBeRemoved;

	/**
	 * Instantiates a new a listener.
	 *
	 * @param id
	 *            the id
	 * @param eventType
	 *            the event type
	 */
	public AListener(String id, int eventType) {
		this.eventType = eventType;
		this.id = id;
	}

	/**
	 * Handle event.
	 * 
	 * @param event
	 *            the event
	 */
	public abstract void handleEvent(AdiEvent event);

	/**
	 * Dispose.
	 */
	public void dispose() {
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * Checks if is to be removed.
	 *
	 * @return true, if is to be removed
	 */
	public boolean isToBeRemoved() {
		return toBeRemoved;
	}

}
