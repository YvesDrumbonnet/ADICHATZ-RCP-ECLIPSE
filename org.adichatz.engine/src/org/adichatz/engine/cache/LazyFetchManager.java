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
package org.adichatz.engine.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.validation.ABindingService;

/**
 * The Class LazyFetchManager.
 * 
 * The lazy fetch manager is linked to an entity<br>
 * All needed lazy fetches are stored in a "decision tree". The requirement of a lazy fetch is managed editor by editor.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class LazyFetchManager<T> {

	/** The root tree node. */
	private LocalLazyNode rootLocalNode;

	/** The entity. */
	private IEntity<T> entity;

	/** The lazy fetch member map. */
	private Map<String, LocalLazyNode> lazyFetchMemberMap = new TreeMap<String, LocalLazyNode>();

	/** the binding service map. */
	private Map<ABindingService, Set<LocalLazyNode>> bindingServiceMap = new HashMap<ABindingService, Set<LocalLazyNode>>();

	/**
	 * Instantiates a new lazy fetch manager.
	 * 
	 * @param entity
	 *            the entity
	 */
	public LazyFetchManager(IEntity<T> entity) {
		this.entity = entity;
		rootLocalNode = new LocalLazyNode(entity.getEntityMM(), entity.getBean());
	}

	public IEntity<T> getEntity() {
		return entity;
	}

	/**
	 * Gets the lazy fetch member map.
	 * 
	 * @return the lazyFetchMemberMap
	 */
	public Map<String, LocalLazyNode> getLazyFetchMemberMap() {
		return lazyFetchMemberMap;
	}

	/**
	 * Gets the root tree node.
	 * 
	 * @return the rootTreeNode
	 */
	public LocalLazyNode getRootLazyNode() {
		return rootLocalNode;
	}

	/**
	 * Gets the local lazy nodes by binding service.
	 * 
	 * @return the local lazy nodes for a binding service
	 */
	public Map<ABindingService, Set<LocalLazyNode>> getBindingServiceMap() {
		return bindingServiceMap;
	}

	/**
	 * Adds the lazy fetch.
	 * 
	 * If lazyFetchMember does not exist, traverse all the children members creating needed local lazy nodes
	 * 
	 * Add editor to the final local lazy node. Add the final local lazy node to the editor.
	 * 
	 * @param bindingService
	 *            the binding service
	 * @param lazyFetchMember
	 *            the lazy fetch member
	 */
	public void addLazyFetch(ABindingService bindingService, String lazyFetchMember) {
		StringTokenizer tokenizer = new StringTokenizer(lazyFetchMember, ".");
		LocalLazyNode parentNode = rootLocalNode;
		String currentLazyFetchMember = "";
		boolean first = true;
		while (tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken().trim();
			if (first) {
				first = false;
				currentLazyFetchMember = token;
			} else
				currentLazyFetchMember = currentLazyFetchMember + "." + token;

			LocalLazyNode lazyNode = parentNode.getChildrenMap().get(currentLazyFetchMember);
			if (null != lazyNode)
				parentNode = lazyNode;
			else {
				/*
				 * Token must correspond to a RefField or a entitySet of the EntityMM.
				 */
				AEntityMetaModel<?> parentEntityMM = parentNode.getEntityMM();
				parentNode = new LocalLazyNode(this, parentNode, parentEntityMM, currentLazyFetchMember, token);
			}
			if (null != bindingService) {
				getLazyNode(bindingService).add(parentNode);
				parentNode.getBindingServices().add(bindingService);
			}
		}
	}

	/**
	 * Gets the set of local lazy nodes for the binding service.
	 * 
	 * @param bindingService
	 *            the binding service
	 * 
	 * @return the set of local lazy nodes
	 */
	public Set<LocalLazyNode> getLazyNode(ABindingService bindingService) {
		Set<LocalLazyNode> lazyNodes = bindingServiceMap.get(bindingService);
		if (null == lazyNodes) {
			lazyNodes = new HashSet<LocalLazyNode>();
			bindingServiceMap.put(bindingService, lazyNodes);
		}
		return lazyNodes;
	}

	/**
	 * Removes the binding service.
	 * 
	 * @param bindingService
	 *            the binding service
	 */
	public void removeBindingService(ABindingService bindingService) {
		Set<LocalLazyNode> lazyNodes = bindingServiceMap.get(bindingService);
		if (null != lazyNodes)
			for (LocalLazyNode lazyNode : lazyNodes) {
				lazyNode.getBindingServices().remove(bindingService);
				// Check deleting Lazy Node because it not used in any editor.
				checkDeletingLazyFetch(lazyNode);
			}
		bindingServiceMap.remove(bindingService);
	}

	/**
	 * Check deleting lazy fetch.
	 * 
	 * @param lazyNode
	 *            the lazy node
	 */
	private void checkDeletingLazyFetch(LocalLazyNode lazyNode) {
		if (lazyNode.getBindingServices().isEmpty()) {
			LocalLazyNode parentLazyNode = lazyNode.getParentLazyNode();
			// lazyNode.getLazyField() could be null for transient entity
			if (null != lazyNode.getLazyField() && null != lazyNode.getLazyField().getFieldName()) {
				// equivalent to lazyNode != root lazyNode
				String member = lazyNode.getLazyField().getFieldName();
				lazyFetchMemberMap.remove(member);
				lazyNode.getParentLazyNode().getChildrenMap().remove(member);
				if (null != parentLazyNode.getParentLazyNode())
					checkDeletingLazyFetch(parentLazyNode);
			}
		}
	}
}