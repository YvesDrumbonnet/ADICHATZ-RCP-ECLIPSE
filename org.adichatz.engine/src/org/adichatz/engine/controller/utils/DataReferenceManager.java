/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe �
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement,
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.engine.controller.utils;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.LocalLazyNode;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.ARefController;
import org.adichatz.engine.controller.AWidgetController;

// TODO: Auto-generated Javadoc
/**
 * The Class DataReferenceManager.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class DataReferenceManager {

	/** The controller. */
	private AWidgetController controller;

	private String columnName;

	private AdiPluginResources pluginResources;

	/**
	 * Instantiates a new data reference manager.
	 * 
	 * @param refController
	 *            the ref controller
	 */
	public DataReferenceManager(ARefController refController) {
		this(refController, refController.getParentController().getPluginResources(), refController.getProperty());
	}

	/**
	 * Instantiates a new data reference manager.
	 * 
	 * @param refController
	 *            the ref controller
	 * @param parentEntity
	 *            the parent entity
	 */
	public DataReferenceManager(AWidgetController controller, AdiPluginResources pluginResources, String columnName) {
		this.controller = controller;
		this.pluginResources = pluginResources;
		this.columnName = columnName;
	}

	/**
	 * Replace.
	 * 
	 * @param bean
	 *            the bean
	 * 
	 * @return the object
	 */
	public Object replace(Object bean) {
		return replace(controller.getEntity(), bean);
	}

	public Object replace(IEntity<?> parentEntity, Object bean) {
		if (null == parentEntity.getLazyFetchManager())
			return bean;
		LocalLazyNode lazyNode = parentEntity.getLazyFetchManager().getLazyFetchMemberMap().get(columnName);
		if (null == lazyNode) // Generally when plugin resources does not manage Lazy loading as for org.adichatz.jpa plugin.
			return bean;
		if (null == bean) {
			lazyNode.setBean(null);
			return null;
		}

		// Check that bean class is managed by current plugin resources
		try {
			pluginResources.getPluginEntityTree().getEntityMM(bean.getClass());
		} catch (RuntimeException e) {
			// Bean is not managed be current PluginResources (like in Query tool part)
			return bean;
		}

		List<String> lazyFetchMembers = new ArrayList<String>();
		String prefix = columnName + ".";

		for (String lazyMember : parentEntity.getLazyFetchManager().getLazyFetchMemberMap().keySet())
			if (lazyMember.startsWith(prefix))
				lazyFetchMembers.add(lazyMember.substring(prefix.length()));
		IEntity<?> fieldEntity;
		try {
			fieldEntity = parentEntity.getEntityMM().getDataAccess().getDataCache().fetchTransientEntity(bean,
					controller.getBindingService(), lazyFetchMembers.toArray(new String[lazyFetchMembers.size()]));
			// refController.getBindingService().addEntity(fieldEntity, (IBoundedController) refController.getParentController());
		} catch (Exception e) {
			LogBroker.logError(e);
			return null;
		}
		/*
		 * Copy lazyNode result in the parentEntity lazy fetch manager
		 */
		try {
			lazyNode.copyRefreshedNode(fieldEntity.getLazyFetchManager().getRootLazyNode());
		} catch (NullPointerException e) {
			/*
			 * NPE could occur when closing Editor during field assist process.
			 * 
			 * DO NOTHING because bean is not yet impacted
			 */
		}
		fieldEntity.disposeFromCache(); // Dispose from cache if no databinding service is attached to field entity
		return fieldEntity.getBean();
	}
}
