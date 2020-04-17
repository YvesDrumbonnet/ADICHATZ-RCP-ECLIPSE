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
package org.adichatz.hibernate.ejb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.adichatz.common.ejb.AEntityCallback;
import org.adichatz.common.ejb.AdiPMException;
import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.common.ejb.AdiSecurityException;
import org.adichatz.common.ejb.EntityModel;
import org.adichatz.common.ejb.ManyToManyUpdate;
import org.adichatz.common.ejb.ProxyEntity;
import org.adichatz.common.ejb.ProxyTransaction;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.common.ejb.Session;
import org.adichatz.common.ejb.extra.NextValueGenerator;
import org.adichatz.common.ejb.remote.IAdiPersistenceManager;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.hibernate.SQLQuery;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.proxy.HibernateProxy;

// TODO: Auto-generated Javadoc
/**
 * The Class APersistenceManager.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class APersistenceManager implements IAdiPersistenceManager {

	/** The entity model map. (for privileges) */
	protected Map<String, EntityModel<?>> entityModelMap = new HashMap<>();

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entity manager
	 */
	public abstract EntityManager getEntityManager();

	/** The current proxy entity. */
	protected ProxyEntity<?> currentProxyEntity;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.common.ejb.remote.IAdiPersistenceManager#isConnected()
	 */
	@Override
	public boolean isConnected() {
		return true;
	}

	/**
	 * Exec update query impl.
	 * 
	 * @param adiQuery the adi query
	 * @return the int
	 * @throws AdiPMException the adi pm exception
	 */
	public int execUpdateQueryImpl(AdiQuery adiQuery) throws AdiPMException {
		switch (adiQuery.getQueryType()) {
		case JQL:
			Query query = getEntityManager().createNativeQuery(adiQuery.getQueryString());
			int i = 1;
			if (null != adiQuery.getParameters())
				for (Object parameter : adiQuery.getParameters())
					if (null != parameter)
						query.setParameter(i++, parameter);
			return query.executeUpdate();
		case SQL:
			Query sqlQuery = getEntityManager().createQuery(adiQuery.getQueryString());
			int j = 0;
			if (null != adiQuery.getParameters())
				for (Object parameter : adiQuery.getParameters())
					if (null != parameter)
						sqlQuery.setParameter(j++, parameter);
			return sqlQuery.executeUpdate();
		default:
			throw new AdiPMException(adiQuery.getQueryType() + " is an invalid type for execUpdateQuery", null);
		}
	}

	/**
	 * Gets the internal single result.
	 * 
	 * @param adiQuery the adi query
	 * @return the internal single result
	 * @throws AdiPMException the adi pm exception
	 */
	private QueryResult getInternalSingleResult(AdiQuery adiQuery) throws AdiPMException {
		// getEntityManager().clear();
		Query query = getEntityManager().createQuery(adiQuery.getQueryString());
		int i = 1;
		if (null != adiQuery.getParameters())
			for (Object parameter : adiQuery.getParameters())
				if (null != parameter)
					query.setParameter(i++, parameter);
		return new QueryResult(query.getSingleResult());
	}

	/**
	 * Gets the internal sql single result.
	 * 
	 * @param adiQuery the adi query
	 * @return the internal sql single result
	 * @throws AdiPMException the adi pm exception
	 */
	private QueryResult getInternalSQLSingleResult(AdiQuery adiQuery) throws AdiPMException {
		org.hibernate.Session hibernateSession = (org.hibernate.Session) getEntityManager().getDelegate();
		SQLQuery sqlQuery = hibernateSession.createSQLQuery(adiQuery.getQueryString());
		sqlQuery.setReadOnly(true);
		int i = 0;
		if (null != adiQuery.getParameters())
			for (Object parameter : adiQuery.getParameters())
				if (null != parameter)
					sqlQuery.setParameter(i++, parameter);
		return new QueryResult(sqlQuery.uniqueResult());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.common.ejb.remote.IAdiPersistenceManager#getQueryResult(org.
	 * adichatz.common.ejb.AAdiQuery)
	 */
	@Override
	public QueryResult getQueryResult(AdiQuery adiQuery) throws AdiPMException {
		switch (adiQuery.getQueryType()) {
		case JQL:
			return getJQLQueryResult(adiQuery);
		case SQL:
			return getSQLQueryResult(adiQuery);
		case SingleJQL:
			// getEntityManager().clear();
			return getInternalSingleResult(adiQuery);
		case SingleSQL:
			// getEntityManager().clear();
			return getInternalSQLSingleResult(adiQuery);
		default: // not possible
			return null;
		}
	}

	/**
	 * Gets the jQL query result.
	 * 
	 * @param adiQuery the adi query
	 * @return the jQL query result
	 * @throws AdiPMException the adi pm exception
	 */
	private QueryResult getJQLQueryResult(AdiQuery adiQuery) throws AdiPMException {
		// getEntityManager().clear();
		Query query = getEntityManager().createQuery(adiQuery.getQueryString());
		int i = 1;
		if (null != adiQuery.getParameters())
			for (Object parameter : adiQuery.getParameters())
				query.setParameter(i++, parameter);

		query.setFirstResult(adiQuery.getFirstResult());
		if (null != adiQuery.getMaxResults())
			query.setMaxResults(adiQuery.getMaxResults());

		Long queryCount = null;
		@SuppressWarnings("rawtypes")
		List list = query.getResultList();
		if (null == adiQuery.getMaxResults()
				|| (0 == adiQuery.getFirstResult() && list.size() < adiQuery.getMaxResults()))
			queryCount = Long.valueOf(list.size());
		else if (adiQuery.isPaginated()) {
			String querString = getCountQuery(
					"select count(*) ".concat(adiQuery.getQueryString().replace("JOIN FETCH", "JOIN")));
			AdiQuery countQuery = new AdiQuery(AdiQuery.QUERY_TYPE.SingleJQL, querString, adiQuery.getParameters());
			queryCount = (Long) getInternalSingleResult(countQuery).getSingleResult();
		}

		return new QueryResult(list, queryCount);
	}

	/**
	 * Gets the sQL query result.
	 * 
	 * @param adiQuery the adi query
	 * @return the sQL query result
	 * @throws AdiPMException the adi pm exception
	 */
	private QueryResult getSQLQueryResult(AdiQuery adiQuery) throws AdiPMException {
		org.hibernate.Session hibernateSession = (org.hibernate.Session) getEntityManager().getDelegate();
		SQLQuery sqlQuery = hibernateSession.createSQLQuery(adiQuery.getQueryString());
		sqlQuery.setReadOnly(true);
		sqlQuery.addEntity(adiQuery.getBeanAlias(), adiQuery.getBeanClass());

		if (null != adiQuery.getMaxResults())
			sqlQuery.setMaxResults(adiQuery.getMaxResults());

		int i = 0;
		for (String jointureAlias : adiQuery.getJointureAliases()) {
			sqlQuery.addJoin(jointureAlias, adiQuery.getJointurePaths()[i++]);
		}

		i = 0;
		if (null != adiQuery.getParameters())
			for (Object parameter : adiQuery.getParameters())
				sqlQuery.setParameter(i++, parameter);

		sqlQuery.setFirstResult(adiQuery.getFirstResult());
		if (null != adiQuery.getMaxResults())
			sqlQuery.setMaxResults(adiQuery.getMaxResults());

		@SuppressWarnings("rawtypes")
		List list = sqlQuery.list();
		Long queryCount = null;
		if (null == adiQuery.getMaxResults()
				|| (0 == adiQuery.getFirstResult() && list.size() < adiQuery.getMaxResults()))
			queryCount = Long.valueOf(list.size());
		else if (adiQuery.isPaginated()) {
			String querString = getCountQuery("select count(*) "
					.concat(adiQuery.getQueryString().substring(adiQuery.getQueryString().indexOf("from "))));
			AdiQuery countQuery = new AdiQuery(AdiQuery.QUERY_TYPE.SingleSQL, querString, adiQuery.getParameters());
			queryCount = ((BigInteger) getInternalSingleResult(countQuery).getSingleResult()).longValue();
		}

		return new QueryResult(list, queryCount);
	}

	/**
	 * Gets the count query.
	 * 
	 * @param countQuery the count query
	 * @return the count query
	 */
	private String getCountQuery(String countQuery) {
		int index = countQuery.indexOf(" order by ");
		if (-1 == index)
			return countQuery;
		else
			return countQuery.substring(0, index);
	}

	/**
	 * Save entities impl.
	 * 
	 * @param proxyTransaction the proxy transaction
	 * 
	 * @throws Exception the exception
	 */
	protected void saveEntitiesImpl(ProxyTransaction proxyTransaction) throws Exception {
		List<ProxyEntity<?>> proxyEntitiesMTM = new ArrayList<ProxyEntity<?>>();
		for (ProxyEntity<?> proxyEntity : proxyTransaction.getProxyEntities()) {
			currentProxyEntity = proxyEntity;
			switch (proxyEntity.getStatus()) {
			case IEntityConstants.REMOVE:
				remove(proxyTransaction.getSession(), proxyEntity);
				break;
			case IEntityConstants.PERSIST:
				if (null != proxyEntity.getCompositeKeyStrategy()) {
					Object nextValue = NextValueGenerator.getInstance().getNextValue(getEntityManager(),
							proxyEntity.getBeanClass(), proxyEntity.getCompositeKeyStrategy());
					proxyEntity.getCompositeKeyStrategy().computeCompositeKey(proxyEntity, nextValue);
				}
				persist(proxyTransaction.getSession(), proxyEntity);
				proxyEntity.executeLazy(this);
				break;
			case IEntityConstants.MERGE:
				merge(proxyTransaction.getSession(), proxyEntity);
				proxyEntity.executeLazy(this);
				if (null != proxyEntity.getManyToManyUpdates() && !proxyEntity.getManyToManyUpdates().isEmpty())
					proxyEntitiesMTM.add(proxyEntity);
				break;
			}
		}
		processManyToManyUpdate(proxyEntitiesMTM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.common.ejb.remote.IAdiPersistenceManager#find(org.adichatz.
	 * common.ejb.Session, org.adichatz.common.ejb.ProxyEntity)
	 */
	public <T> ProxyEntity<T> find(Session session, ProxyEntity<T> proxyEntity) throws AdiPMException {
		checkEntityModel(session, proxyEntity.getBeanClass().getName(), IEntityConstants.RETRIEVE, null);
		Object bean;
		if (null == proxyEntity.getAdiQuery())
			bean = getEntityManager().find(proxyEntity.getBeanClass(), proxyEntity.getBeanId());
		else
			bean = getQueryResult(proxyEntity.getAdiQuery()).getSingleResult();
		if (null == bean) {
			if (proxyEntity.isNullwhenNotFound())
				return proxyEntity; // return proxyEntity with no bean.
			throw new EntityNotFoundException("No bean found for class:<" + proxyEntity.getBeanClass() + "> and id:<"
					+ proxyEntity.getBeanId() + ">.");
		}
		if (bean instanceof HibernateProxy)
			bean = ((HibernateProxy) bean).getHibernateLazyInitializer().getImplementation();
		proxyEntity.setBean(bean);
		try {
			proxyEntity.executeLazy(this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdiPMException(e, currentProxyEntity.getBean());
		}
		return proxyEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.common.ejb.remote.IAdiPersistenceManager#batchRequests(org.
	 * adichatz.common.ejb.ProxyTransaction)
	 */
	public ProxyTransaction batchRequests(ProxyTransaction proxyTransaction) throws AdiPMException {
		if (null != proxyTransaction.getProxyEntities())
			refreshEntities(proxyTransaction);
		if (null != proxyTransaction.getProxyQueries())
			for (AdiQuery query : proxyTransaction.getProxyQueries())
				query.setQueryResult(getQueryResult(query));
		return proxyTransaction;
	}

	/**
	 * Refresh entities impl.
	 * 
	 * @param proxyTransaction the proxy transaction
	 * @return the proxy result
	 * @throws AdiPMException the adi pm exception
	 */
	protected void refreshEntitiesImpl(ProxyTransaction proxyTransaction) throws AdiPMException {
		for (ProxyEntity<?> proxyEntity : proxyTransaction.getProxyEntities()) {
			currentProxyEntity = proxyEntity;
			try {
				find(proxyTransaction.getSession(), proxyEntity);
			} catch (EntityNotFoundException e) {
				proxyEntity.setStatus(IEntityConstants.TODELETE);
			}
		}
	}

	/**
	 * Removes the.
	 * 
	 * @param <T>         the
	 * @param session     the session
	 * @param proxyEntity the proxy entity
	 * @throws AdiSecurityException the adi security exception
	 */
	public <T> void remove(Session session, ProxyEntity<T> proxyEntity) throws AdiSecurityException {
		List<AEntityCallback<T>> entityCallBacks = checkEntityModel(session, proxyEntity, IEntityConstants.REMOVE);
		if (null != entityCallBacks)
			for (AEntityCallback<T> entityCallBack : entityCallBacks)
				entityCallBack.preRemove(proxyEntity);
		getEntityManager().remove(getEntityManager().merge(proxyEntity.getBean()));
		if (null != entityCallBacks)
			for (AEntityCallback<T> entityCallBack : entityCallBacks)
				entityCallBack.postRemove(proxyEntity);
	}

	/**
	 * Merge.
	 * 
	 * @param <T>         the
	 * @param session     the session
	 * @param proxyEntity the proxy entity
	 * @throws AdiSecurityException the adi security exception
	 */
	public <T> void merge(Session session, ProxyEntity<T> proxyEntity) throws AdiSecurityException, AdiPMException {
		List<AEntityCallback<T>> entityCallBacks = checkEntityModel(session, proxyEntity, IEntityConstants.MERGE);
		if (null != entityCallBacks)
			for (AEntityCallback<T> entityCallBack : entityCallBacks)
				entityCallBack.preMerge(proxyEntity);
		// Check is proxyEntity exists
		Object currentBean = getEntityManager().find(proxyEntity.getBeanClass(), proxyEntity.getBeanId());
		if (null == currentBean)
			throw new AdiPMException("Bean with key '" + proxyEntity.getBeanId() + "' for class '"
					+ proxyEntity.getBeanClass().getName() + "' no longer exists on database.", proxyEntity.getBean());
		proxyEntity.setBean(getFromProxy(getEntityManager().merge(proxyEntity.getBean())));
		if (null != entityCallBacks)
			for (AEntityCallback<T> entityCallBack : entityCallBacks)
				entityCallBack.postMerge(proxyEntity);
	}

	/**
	 * Persist.
	 * 
	 * @param <T>         the
	 * @param session     the session
	 * @param proxyEntity the proxy entity
	 * @throws AdiSecurityException the adi security exception
	 */
	public <T> void persist(Session session, ProxyEntity<T> proxyEntity) throws AdiSecurityException {
		List<AEntityCallback<T>> entityCallBacks = checkEntityModel(session, proxyEntity, IEntityConstants.PERSIST);
		if (null != entityCallBacks)
			for (AEntityCallback<T> entityCallBack : entityCallBacks)
				entityCallBack.prePersist(proxyEntity);
		getEntityManager().persist(proxyEntity.getBean());
		if (null != entityCallBacks)
			for (AEntityCallback<T> entityCallBack : entityCallBacks)
				entityCallBack.postPersist(proxyEntity);
	}

	/**
	 * Check entity model.
	 * 
	 * If no entity model is retreived, then check privileges is OK and callback is
	 * null.
	 * 
	 * @param <T>         the
	 * @param session     the session
	 * @param proxyEntity the proxy entity
	 * @param privilege   the privilege
	 * @return the a entity call back< t>
	 * @throws AdiSecurityException the adi security exception
	 */
	private <T> List<AEntityCallback<T>> checkEntityModel(Session session, ProxyEntity<T> proxyEntity, int privilege)
			throws AdiSecurityException {
		return checkEntityModel(session, proxyEntity.getBeanClass().getName(), privilege, proxyEntity.getBean());
	}

	/**
	 * Check entity model.
	 * 
	 * If no entity model is retreived, then check privileges is OK and callback is
	 * null.
	 * 
	 * @param <T>       the
	 * @param session   the session
	 * @param hashcode  the hashcode
	 * @param privilege the privilege
	 * @param bean      the bean - Useful only for building AdiSecurityException
	 *                  message
	 * @return the a entity call back< t>
	 * @throws AdiSecurityException the adi security exception
	 */
	@SuppressWarnings("unchecked")
	private <T> List<AEntityCallback<T>> checkEntityModel(Session session, String beanClassName, int privilege, T bean)
			throws AdiSecurityException {
		EntityModel<T> entityModel = (EntityModel<T>) entityModelMap.get(beanClassName);
		if (null != entityModel) {
			entityModel.checkPrivilege(session, privilege, bean);
			return entityModel.getEntityCallbacks();
		}
		return null;

	}

	/**
	 * Process many to many update.
	 * 
	 * @param proxyEntitiesMTM the proxy entities mtm
	 * 
	 * @throws Exception the exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void processManyToManyUpdate(List<ProxyEntity<?>> proxyEntitiesMTM) throws Exception {
		/*
		 * Process for ManyToMany updates if needed
		 */
		for (ProxyEntity<?> proxyEntity : proxyEntitiesMTM) {
			for (ManyToManyUpdate manyToManyUpdate : proxyEntity.getManyToManyUpdates()) {
				/*
				 * Finds the linked proxyEntity
				 */
				for (ProxyEntity<?> linkedProxyEntity : proxyEntitiesMTM) {
					if (linkedProxyEntity.getProxyKey() == manyToManyUpdate.getProxyKeyAssociation()) {
						Set set = (Set) proxyEntity.getBeanClass()
								.getMethod("get" + manyToManyUpdate.getFieldName().substring(0, 1).toUpperCase()
										+ manyToManyUpdate.getFieldName().substring(1))
								.invoke(proxyEntity.getBean());

						switch (manyToManyUpdate.getType()) {
						case ADD_MANY_TO_MANY:
							set.add(linkedProxyEntity.getBean());
							break;
						case REMOVE_MANY_TO_MANY:
							set.remove(linkedProxyEntity.getBean());
							break;
						}
						break;
					} // END if (linkedProxyEntity.getProxyKey() ==
						// manyToManyUpdate.getProxyKeyAssociation())
				} // for (ProxyEntity<?> linkedProxyEntity : proxyEntitiesMTM)
			} // END for (ManyToManyUpdate manyToManyUpdate :
				// proxyEntity.getManyToManyUpdates()
		} // END for (ProxyEntity<?> proxyEntity : proxyEntitiesMTM)
		for (ProxyEntity<?> proxyEntity : proxyEntitiesMTM)
			getEntityManager().merge(proxyEntity.getBean());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.common.ejb.remote.IAdiPersistenceManager#getFromProxy(java.lang.
	 * Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getFromProxy(T bean) {
		if (bean instanceof HibernateProxy) {
			try {
				return (T) ((HibernateProxy) bean).getHibernateLazyInitializer().getImplementation();
			} catch (EntityNotFoundException e) {
				return null;
			}
		} else if (bean instanceof PersistentSet)
			((PersistentSet) bean).forceInitialization();
		return bean;
	}

	public Long[] checkBeforeDeleting(boolean stopAfterFirst, AdiQuery... queries) throws AdiPMException {
		Long[] counts = new Long[queries.length];
		int i = 0;
		for (AdiQuery adiQuery : queries) {
			Long count = (Long) getInternalSingleResult(adiQuery).getSingleResult();
			counts[i++] = count;
			if (count != 0l && stopAfterFirst)
				break;
		}
		return counts;
	}
}