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
package org.adichatz.common.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProxyEntity.
 * 
 * a proxy for an entity needed to communicate between Adicahtz engine and persistence layer.
 *
 * @author Yves
 * @param <T>
 *            the generic type
 * @see org.adichatz.engine.cache.Entity
 * @see org.adichatz.engine.common.DataBaseTools
 */
public class ProxyEntity<T> extends RelatedEntity<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6143847840569006114L;

	/** The id bean. */
	private Object beanId;

	/** The proxy key. */
	private int proxyKey;

	/** The bean class. */
	private Class<? extends T> beanClass;

	/** Flag to return null value rather than to throw an EntityNotFoundException. */
	private boolean nullWhenNotFound;

	/** The many to many updates. */
	private List<ManyToManyUpdate> manyToManyUpdates;

	/** The fire unlock. */
	private boolean fireUnlock;

	/** The adi query. */
	private AdiQuery adiQuery;

	/** The composite key strategy. */
	private ICompositeKeyStrategy compositeKeyStrategy;

	/**
	 * Instantiates a new proxy entity.
	 *
	 * @param beanClass
	 *            the bean class
	 * @param beanId
	 *            the bean id
	 * @param rootLazyNode
	 *            the root lazy node
	 */
	public ProxyEntity(Class<? extends T> beanClass, Object beanId, HomeLazyNode rootLazyNode) {
		this(beanClass, beanId, false, rootLazyNode);
	}

	/**
	 * Instantiates a new proxy entity.
	 *
	 * @param beanClass
	 *            the bean class
	 * @param beanId
	 *            the bean id
	 * @param nullwhenNotFound
	 *            if true return null value for bean when entity not found
	 * @param rootLazyNode
	 *            the root lazy node
	 */
	public ProxyEntity(Class<? extends T> beanClass, Object beanId, boolean nullWhenNotFound, HomeLazyNode rootLazyNode) {
		this.beanId = beanId;
		this.beanClass = beanClass;
		this.nullWhenNotFound = nullWhenNotFound;
		setRootLazyNode(rootLazyNode);
	}

	/**
	 * Instantiates a new proxy entity.
	 *
	 * @param bean
	 *            the bean
	 * @param beanClass
	 *            the bean class
	 * @param beanId
	 *            the bean id
	 * @param proxyKey
	 *            the proxy key
	 * @param status
	 *            the status
	 * @param compositeKeyStrategy
	 *            the composite key strategy
	 * @param fireUnLock
	 *            the fire un lock
	 */
	public ProxyEntity(T bean, Class<? extends T> beanClass, Object beanId, int proxyKey, int status,
			ICompositeKeyStrategy compositeKeyStrategy, boolean fireUnLock) {
		super(bean, status);
		this.beanId = beanId;
		this.beanClass = beanClass;
		this.proxyKey = proxyKey;
		this.fireUnlock = fireUnLock;
		this.compositeKeyStrategy = compositeKeyStrategy;
	}

	/**
	 * Gets the bean.
	 * 
	 * @return the bean
	 */
	public Object getBeanId() {
		return beanId;
	}

	/**
	 * Gets the bean class.
	 * 
	 * @return the beanClass
	 */
	public Class<? extends T> getBeanClass() {
		return beanClass;
	}

	/**
	 * Gets the adi query.
	 * 
	 * @return the adi query
	 */
	public AdiQuery getAdiQuery() {
		return adiQuery;
	}

	/**
	 * Gets the cache key.
	 * 
	 * @return the cacheKey
	 */
	public int getProxyKey() {
		return proxyKey;
	}

	/**
	 * Gets the composite key strategy.
	 * 
	 * @return the compositeKeyStrategy
	 */
	public ICompositeKeyStrategy getCompositeKeyStrategy() {
		return compositeKeyStrategy;
	}

	/**
	 * Sets the composite key strategy.
	 * 
	 * @param compositeKeyStrategy
	 *            the new composite key strategy
	 */
	public void setCompositeKeyStrategy(ICompositeKeyStrategy compositeKeyStrategy) {
		this.compositeKeyStrategy = compositeKeyStrategy;
	}

	/**
	 * Gets the many to many updates.
	 * 
	 * @return the manyToManyUpdates
	 */
	public List<ManyToManyUpdate> getManyToManyUpdates() {
		if (null == manyToManyUpdates)
			manyToManyUpdates = new ArrayList<ManyToManyUpdate>();
		return manyToManyUpdates;
	}

	/**
	 * Checks if is fire unlock.
	 * 
	 * @return the fireUnlock
	 */
	public boolean isFireUnlock() {
		return fireUnlock;
	}

	/**
	 * Sets the fire unlock.
	 * 
	 * @param fireUnlock
	 *            the fireUnlock to set
	 */
	public void setFireUnlock(boolean fireUnlock) {
		this.fireUnlock = fireUnlock;
	}

	/**
	 * Checks if null value must be return if entity not found (elsewhere a EntityNoFoundException is thrown).
	 *
	 * @return true, if a null value is returned when entity is not found
	 */
	public boolean isNullwhenNotFound() {
		return nullWhenNotFound;
	}

}