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
package org.adichatz.engine.listener;

// TODO: Auto-generated Javadoc
/**
 * The Interface IEventType.
 * 
 * @author Yves Drumbonnet
 * 
 */
public interface IEventType {

	//
	// Events on entity
	// ----------------
	//

	/** The Change status. */
	public static int CHANGE_STATUS = 1;

	/** The lock or unlock event entity. */
	public static int LOCK_ENTITY = 2;

	/** The before property entity change. */
	public static int BEFORE_PROPERTY_CHANGE = 3;

	/** The before property entity change. */
	public static int WHEN_PROPERTY_CHANGE = 4;

	/** The after property entity change. */
	public static int AFTER_PROPERTY_CHANGE = 5;

	/** Pre Save entities action on an editor. */
	public static int PRE_SAVE = 6;

	/** Post Save entities action on an editor. */
	public static int POST_SAVE = 7;

	/** Pre Refresh entities action on an editor. */
	public static int PRE_REFRESH = 8;

	/** Post Refresh entities action on an editor. */
	public static int POST_REFRESH = 9;

	//
	// Events on data binding
	// ----------------------
	//
	public static int PRE_SAVE_ENTITIES = 12;

	public static int POST_SAVE_ENTITIES = 13;

	public static int PRE_REFRESH_ENTITIES = 14;

	public static int POST_REFRESH_ENTITIES = 15;

	public static int ADD_ENTITY = 16;

	public static int REMOVE_ENTITY = 17;

	/** After a message is added or removed from FormMessageManager. */
	public static int POST_MESSAGE = 18;

	// when dirty is set
	public static int DIRTY_VALIDATION = 19;

	//
	// Events on controller life cycle
	// -------------------------------
	//
	public static int AFTER_INITIALIZE = 20;

	public static int BEFORE_CREATE_CONTROL = 21;

	public static int AFTER_CREATE_CONTROL = 22;

	public static int BEFORE_SYNCHRONIZE = 23;

	public static int AFTER_SYNCHRONIZE = 24;

	public static int BEFORE_END_LIFE_CYCLE = 25;

	public static int AFTER_END_LIFE_CYCLE = 26;

	public static int BEFORE_DISPOSE = 27;

	public static int AFTER_DISPOSE = 28;

	public static int POST_CREATE_PART = 29;

	public static int AFTER_INSTANTIATE_CONTROLLER = 30;

	public static int BEFORE_FIELD_CHANGE = 31;

	public static int AFTER_FIELD_CHANGE = 32;

	//
	// Events combining action, controller, databinding
	// ------------------------------------------------
	//

	// Before setting managed entity {@link org.adichatz.engine.validation.EntityInjection.injectEntity(IEntity<?>,
	// ACollectionController))}.
	public static int BEFORE_ENTITY_INJECTION = 35;

	// After setting managed entity org.adichatz.engine.validation.EntityInjection.initialize(IEntity<?>).
	public static int AFTER_ENTITY_INJECTION = 36;

	//
	// Events on ActionController or AdiRunnable
	// -----------------------------------------
	//

	/** Before execute of runnable */
	public static int PRE_RUN = 40;

	/** After execute of runnable */
	public static int POST_RUN = 41;

	//
	// Events on Control or controller
	// -------------------------------
	//
	public static int MODIFY_TEXT = 50; // onModifyText

	public static int SELECTION_CHANGED = 51; // onSelectionChanged

	public static int WIDGET_SELECTED = 52; // onWidgetSelected

	public static int DOUBLE_CLICK = 53; // onDoubleClick

	public static int BEFORE_REFRESH = 54; // before having refreshed input

	public static int AFTER_REFRESH = 55; // After having refreshed input

	//
	// Events on JPA query
	// -------------------
	//
	public static int AFTER_PREFERENCE_CHANGE = 61;

	public static int PRE_QUERY = 62;

	public static int POST_QUERY = 63;

	/**
	 * Unspecialized event type. Use this one for add programmatically behavior following this steps.
	 * <ul>
	 * <li>add code <code>AListener.fireListener(listeners, IEventType.UNSPECIALIZED);</code> in controller where you want the code
	 * to be exececuted</li>
	 * <li>add code</li>
	 * </ul>
	 */
	public static int UNSPECIALIZED = 99;

}
