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
package org.adichatz.engine.cache;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.adichatz.common.ejb.HomeLazyNode;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.model.OneToOneField;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.validation.ABindingService;

// TODO: Auto-generated Javadoc
/**
 * The Class LocalLazyNode.
 * 
 * A node of the lazy node (Node of the decision tree of lazy fetches) server
 * side.
 * 
 * @see org.adichatz.engine.cache.LazyFetchManager
 * @author Yves
 * 
 */
public class LocalLazyNode {

	/** The parent bean. */
	private Object bean;

	/** The children. */
	private Map<String, LocalLazyNode> childrenMap = new HashMap<String, LocalLazyNode>();

	/** The linked lazy field (RefField or EntitySet). */
	protected PropertyField lazyField;

	/** The to fetch. */
	private boolean toFetch = false;

	/** The parent node. */
	private LocalLazyNode parentLazyNode = null;

	/** The binding services. */
	private Set<ABindingService> bindingServices = new HashSet<ABindingService>();

	/** The lazy fetch member. */
	private String lazyFetchMember;

	/** The entity MetaModel. */
	private AEntityMetaModel<?> entityMM;

	/** The initialize OneToOne. */
	private boolean initializeOTO;

	/**
	 * Instantiates a new root local lazy node.
	 * 
	 * @param entityMM the entity MetaModel
	 * @param bean     the bean
	 */
	public LocalLazyNode(AEntityMetaModel<?> entityMM, Object bean) {
		this.bean = bean;
		this.entityMM = entityMM;
	}

	/**
	 * Instantiates a new local lazy node.
	 * 
	 * @param lazyFetchManager the lazy fetch manager
	 * @param parentLazyNode   the parent lazy node
	 * @param parentEntityMM   the parent entity MetaModel
	 * @param lazyFetchMember  the lazy fetch member
	 * @param fieldName        the field name
	 */
	public LocalLazyNode(LazyFetchManager<?> lazyFetchManager, LocalLazyNode parentLazyNode,
			AEntityMetaModel<?> parentEntityMM, String lazyFetchMember, String fieldName) {
		this.parentLazyNode = parentLazyNode;
		this.lazyFetchMember = lazyFetchMember;
		PropertyField propertyField = parentEntityMM.getFieldMap().get(fieldName);
		if (null != propertyField) {
			lazyField = propertyField;
			entityMM = (propertyField instanceof RefField) ? ((RefField<?>) propertyField).getParentEntityMM()
					: propertyField.getEntityMM();
		} else {
			lazyField = parentEntityMM.getEntitySet(fieldName);
		}
		if (null != lazyField) {
			parentLazyNode.getChildrenMap().put(lazyFetchMember, this);
			lazyFetchManager.getLazyFetchMemberMap().put(lazyFetchMember, this);
		} else
			logError(getFromEngineBundle("error.no.field", lazyFetchMember, parentEntityMM.getEntityId()));
	}

	/**
	 * Gets the bean.
	 * 
	 * @return the bean
	 */
	public Object getBean() {
		return bean;
	}

	/**
	 * Gets the entity MetaModel.
	 * 
	 * @return the entity MetaModel
	 */
	public AEntityMetaModel<?> getEntityMM() {
		return entityMM;
	}

	/**
	 * Sets the bean.
	 * 
	 * @param bean the new bean
	 */
	public void setBean(Object bean) {
		this.bean = bean;
	}

	/**
	 * Gets the children nodes.
	 * 
	 * @return the children
	 */
	public Map<String, LocalLazyNode> getChildrenMap() {
		return childrenMap;
	}

	/**
	 * Gets the lazy field.
	 * 
	 * @return the lazy field
	 */
	public PropertyField getLazyField() {
		return lazyField;
	}

	/**
	 * Gets the lazy fetch member.
	 * 
	 * @return the lazy fetch member
	 */
	public String getLazyFetchMember() {
		return lazyFetchMember;
	}

	/**
	 * Gets the to fetch.
	 * 
	 * @return the to fetch
	 */
	public boolean isToFetch() {
		return toFetch;
	}

	/**
	 * Gets the parent lazy node.
	 * 
	 * @return the parent lazy node
	 */
	public LocalLazyNode getParentLazyNode() {
		return parentLazyNode;
	}

	/**
	 * Gets the editors.
	 * 
	 * @return the binding services
	 */
	public Set<ABindingService> getBindingServices() {
		return bindingServices;
	}

	public boolean isInitializeOTO() {
		return initializeOTO;
	}

	/**
	 * Sets the to fetch parent.
	 * 
	 * @param toFetch the new to fetch parent
	 */
	public void setToFetchParent(boolean toFetch) {
		this.toFetch = toFetch;
		if (null != parentLazyNode)
			parentLazyNode.setToFetchParent(toFetch);
	}

	/**
	 * Sets the to fetch children.
	 * 
	 * @param toFetch the new to fetch children
	 */
	public void setToFetchChildren(boolean toFetch) {
		this.toFetch = toFetch;
		for (LocalLazyNode lazyNode : childrenMap.values())
			lazyNode.setToFetchChildren(toFetch);
	}

	/**
	 * Copy refreshed node.
	 * 
	 * @param refreshedNode the refreshed node
	 */
	public void copyRefreshedNode(LocalLazyNode refreshedNode) {
		bean = refreshedNode.getBean();
		for (Map.Entry<String, LocalLazyNode> entry : refreshedNode.getChildrenMap().entrySet()) {
			LocalLazyNode childNode = childrenMap.get(entry.getKey());
			if (null != childNode)
				childNode.copyRefreshedNode(entry.getValue());
		}
	}

	/**
	 * Populate lazy.
	 * 
	 * @param lazyFetchManager the lazy fetch manager
	 * @param homeParentNode   the home parent node
	 * @throws Exception the exception
	 */
	public void populateLazy(LazyFetchManager<?> lazyFetchManager, HomeLazyNode homeParentNode) throws Exception {
		if (null != homeParentNode.getChildren())
			for (HomeLazyNode homeChildNode : homeParentNode.getChildren()) {
				LocalLazyNode localChildNode = childrenMap.get(homeChildNode.getLazyFetchMember());
				if (null != localChildNode) {
					localChildNode.setBean(homeChildNode.getBean());
				} else {
					/*
					 * Create a new local node because it does not exist
					 */
					AEntityMetaModel<?> parentEntityMM;
					if (null == parentLazyNode)
						parentEntityMM = lazyFetchManager.getEntity().getEntityMM();
					else
						parentEntityMM = getEntityMM();
					String member = homeChildNode.getLazyFetchMember()
							.substring(homeChildNode.getLazyFetchMember().lastIndexOf('.') + 1);
					localChildNode = new LocalLazyNode(lazyFetchManager, this, parentEntityMM,
							homeChildNode.getLazyFetchMember(), member);
					localChildNode.setBean(homeChildNode.getBean());
				}
				localChildNode.populateLazy(lazyFetchManager, homeChildNode);
			}
	}

	/**
	 * Execute lazy.
	 * 
	 * @param bindingService the binding service (null means fetch for all lazy
	 *                       nodes regardless binding service)
	 * @return true, if successful
	 */
	public boolean executeLazy(ABindingService bindingService) {
		boolean lazyOk = !entityMM.getDataAccess().getGatewayConnector().isLazyInstance(bean);
		for (LocalLazyNode lazyNode : childrenMap.values()) {
			// bindingService is null means fetch for all lazy nodes regardless binding
			// service
			if (null == bindingService || lazyNode.getBindingServices().contains(bindingService)) {
				if (lazyNode.getLazyField() instanceof RefField<?>
						|| lazyNode.getLazyField() instanceof OneToOneField) {
					if (null != bean) {
						Object localBean = ReflectionTools.invokeMethod(
								entityMM.getFieldMap().get(lazyNode.getLazyField().getFieldName()).getGetMethod(),
								bean);
						boolean toBeFetched;
						if (null == localBean && lazyNode.getLazyField() instanceof OneToOneField) {
							toBeFetched = true;
							lazyNode.initializeOTO = true;
						} else
							toBeFetched = entityMM.getDataAccess().getGatewayConnector().isLazyInstance(localBean);
						if (toBeFetched) {
							lazyNode.setToFetchParent(true);
							lazyNode.setToFetchChildren(true);
							lazyOk = false;
						} else {
							lazyNode.setBean(localBean);
							if (!lazyNode.executeLazy(bindingService))
								lazyOk = false;
						}
					}
				} else if (null == ((EntitySet<?>) lazyNode.getLazyField()).getQueryManager().getQueryResult()) {
					lazyNode.setToFetchParent(true);
					lazyOk = false;
				}
			}
		}
		return lazyOk;
	}
}
