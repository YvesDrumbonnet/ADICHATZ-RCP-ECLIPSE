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
package org.adichatz.jpa.data;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.lang.reflect.Method;
import java.util.Map;

import org.adichatz.common.ejb.ICompositeKeyStrategy;
import org.adichatz.common.ejb.RelatedEntity;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.BeanUtils;
import org.adichatz.engine.cache.LocalLazyNode;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.data.AEntity;
import org.adichatz.engine.model.OneToOneField;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.tabular.IMarshalledElement;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.wrapper.EntityParamWrapper;
import org.eclipse.jface.dialogs.MessageDialog;

// TODO: Auto-generated Javadoc
/**
 * The Class Entity.
 * 
 * An entity wraps a bean to provide special features
 * <ul>
 * <li></li>interception of all setter (databinding)</li>
 * <li>lazy fetch manager</li>
 * <li>status and locking editor</li>
 * <li>consistency</li>
 * <li>set of tableControllers</li>
 * <li>relationship update</li>
 * </ul>
 * 
 * @param <T>
 *            the
 * @author Yves
 */
public class JPAEntity<T> extends AEntity<T> implements IMarshalledElement {

	/** The bean id meter for new entities. */
	private static int beanIdForNew = 1;

	/** The composite key strategy. */
	private ICompositeKeyStrategy compositeKeyStrategy;

	/** The bean before refresh. */
	private T beforeBean;

	private boolean initialized;

	private EntityParamWrapper entityParam;

	/**
	 * Instantiates a new jPA entity.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 * @param bean
	 *            the bean
	 * @param status
	 *            the status
	 */
	public JPAEntity(JPADataAccess dataAccess, T bean, int status) {
		super(dataAccess, bean, status);
		if (IEntityConstants.PERSIST == status) {
			beanId = "new#bean#" + beanIdForNew++;
			dataAccess.getDataCache().getCreatedEntities().put(this.bean, this);
		} else
			beanId = entityMM.getId(bean);

		cacheKey = dataAccess.getCacheKey(entityMM.getEntityId(), beanId);

		if (dataAccess.getDeletedEntityCachedKeys().contains(cacheKey)) {
			String message = getFromJpaBundle("entity.hasBeanPreviouslyDeleted", entityMM.getEntityId(), beanId);
			EngineTools.openDialog(MessageDialog.ERROR, EngineTools.getFromEngineBundle("error.encounteredError"), message);
			// Entity has been previously deleted in the application
			throw new RuntimeException(message);
		}

		beanInterceptor = dataAccess.getBeanInterceptorFactory().createEnhancedProxy(this);
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
	 *            the compositeKeyStrategy to set
	 */
	public void setCompositeKeyStrategy(ICompositeKeyStrategy compositeKeyStrategy) {
		this.compositeKeyStrategy = compositeKeyStrategy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#refreshEntityIfNeeded(org.adichatz.engine.validation.ABindingService,
	 * java.lang.String[])
	 */
	@Override
	public void refreshEntityIfNeeded(ABindingService bindingService, String... lazyFetchMembers) {
		if (needRefresh(bindingService, lazyFetchMembers))
			((JPADataAccess) entityMM.getDataAccess()).refreshEntities(bindingService, false, this);
	}

	@Override
	public boolean needRefresh(ABindingService bindingService, String... lazyFetchMembers) {
		for (String lazyFetchMember : lazyFetchMembers)
			addLazyFetch(bindingService, lazyFetchMember);
		return (!lazyFetchManager.getRootLazyNode().executeLazy(bindingService));
	}

	/**
	 * Pre refresh.
	 */
	public void cloneBeforeBean() {
		if (null != bean)
			beforeBean = BeanUtils.clone(bean);

	}

	/**
	 * Post refresh.
	 * 
	 * @param proxyEntity
	 *            the proxy entity
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void postRefresh(ABindingService bindingService, RelatedEntity<?> proxyEntity) {
		T oldBean = bean;
		/**
		 * Applies refresh (refresh data fields from server) when
		 * <ul>
		 * <li>entity was PERSIST</li>
		 * <li><b>OR</b></li>
		 * <li>a refresh was asked by the user in one editor and the entity is not locked (updated) in another editor</li>
		 * </ul>
		 */
		boolean lazyInstance = entityMM.getDataAccess().getGatewayConnector().isLazyInstance(beforeBean);
		if (lazyInstance)
			beforeBean = BeanUtils.clone((T) proxyEntity.getBean());
		boolean applyRefresh = IEntityConstants.RETRIEVE == proxyEntity.getStatus()
				|| (ABindingService.isForceRefreshing() && (null == lockingBindingService || (lockingBindingService).isClosing()
						|| lockingBindingService.equals(bindingService)))
				|| lazyInstance;

		if (applyRefresh) {
			if (null == bean) {
				bean = (T) proxyEntity.getBean();
				if (null != lazyFetchManager)
					lazyFetchManager.getRootLazyNode().setBean(bean);
			} else {
				bean = (T) proxyEntity.getBean();
				for (LocalLazyNode fieldNode : lazyFetchManager.getRootLazyNode().getChildrenMap().values()) {
					if (!fieldNode.isToFetch() && fieldNode.getLazyField() instanceof RefField && null != fieldNode.getBean()) {
						/*
						 * This node was initialized in a previous refresh process and its result must be incorporated in the bean.
						 */
						ReflectionTools.invokeMethod(
								entityMM.getFieldMap().get(((RefField) fieldNode.getLazyField()).getFieldName()).getSetMethod(),
								bean, new Object[] { fieldNode.getBean() });
					}
				}
			}

			if (IEntityConstants.NULL == proxyEntity.getStatus()) {
				status = IEntityConstants.RETRIEVE;
				beanInterceptor = entityMM.getDataAccess().getBeanInterceptorFactory().createEnhancedProxy(this);
			}
		} else if (IEntityConstants.MERGE == proxyEntity.getStatus()) {
			// When entity is locally updated, accept to change only fields with locally a lazy instance value and a real value in
			// the server side
			for (LocalLazyNode fieldNode : lazyFetchManager.getRootLazyNode().getChildrenMap().values()) {
				if (fieldNode.getLazyField() instanceof RefField || fieldNode.getLazyField() instanceof OneToOneField) {
					Method getMethod = entityMM.getFieldMap().get(((PropertyField) fieldNode.getLazyField()).getFieldName())
							.getGetMethod();
					Object fieldValue = ReflectionTools.invokeMethod(getMethod, bean);
					if (entityMM.getDataAccess().getGatewayConnector().isLazyInstance(fieldValue)) {
						Object newFieldValue = ReflectionTools.invokeMethod(entityMM.getFieldMap()
								.get(((PropertyField) fieldNode.getLazyField()).getFieldName()).getGetMethod(),
								proxyEntity.getBean());

						if (!entityMM.getDataAccess().getGatewayConnector().isLazyInstance(newFieldValue))
							ReflectionTools.invokeMethod(entityMM.getFieldMap()
									.get(((PropertyField) fieldNode.getLazyField()).getFieldName()).getSetMethod(), bean,
									new Object[] { newFieldValue });
					}
				}
			}
		}
		/*
		 * Populates lazy node tree from data coming from the server
		 */
		try {
			if (null != proxyEntity.getLazyNode()) {
				lazyFetchManager.getRootLazyNode().setBean(bean);
				lazyFetchManager.getRootLazyNode().populateLazy(lazyFetchManager, proxyEntity.getLazyNode());
			}
		} catch (Exception e) {
			LogBroker.logError(e);
		}
		/**
		 * fires properties entity changes exept when needed<br>
		 * beforeBean is null when refreshing (cancelling) a hust created bean which is unknown on the server
		 */
		if (applyRefresh && !ABindingService.isSynchronizing() && null != beforeBean)
			beanInterceptor.adi$FirePropertiesEntity(beforeBean);

		/*
		 * Needed because beforeBean could exist in Lists outside or inside the editor and must be updated following to the
		 * refreshed value.
		 */
		if (null != oldBean)
			BeanUtils.copyFields(bean, oldBean);
	}

	@Override
	public void putStatus(int status, boolean forced) {
		super.putStatus(status, forced);
		if (null != cacheKey) {// Entity is just beeing instantiated
			if (IEntityConstants.MERGE == status || IEntityConstants.REMOVE == status)
				((JPADataAccess) entityMM.getDataAccess()).getDirtyEntities().add(this);
			else if (IEntityConstants.RETRIEVE == status)
				((JPADataAccess) entityMM.getDataAccess()).getDirtyEntities().remove(this);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JPAEntity && cacheKey.equals(((JPAEntity<?>) obj).getCacheKey()))
			return true;
		return super.equals(obj);
	}

	@Override
	public void disposeFromCache() {
		super.disposeFromCache();
		((JPADataAccess) entityMM.getDataAccess()).getDirtyEntities().remove(this);
	}

	@Override
	public void addLazyFetch(ABindingService bindingService, String lazyFetchMember) {
		super.addLazyFetch(bindingService, lazyFetchMember);
		if (null == entityParam)
			preMarshal(false);
		else {
			entityParam.addLazyFetchMember(lazyFetchMember);
		}

	}

	@Override
	public IMarshalledWrapper preMarshal(boolean closingPart) {
		if (null == entityParam) {
			entityParam = new EntityParamWrapper(this);
			entityParam.setEntityURI(entityMM.getPluginEntity().getEntityURI());
			if (!lazyFetchManager.getLazyFetchMemberMap().isEmpty()) {
				for (Map.Entry<String, LocalLazyNode> entry : lazyFetchManager.getLazyFetchMemberMap().entrySet()) {
					LocalLazyNode localLazyNode = entry.getValue();
					if (localLazyNode.getChildrenMap().isEmpty()) // Copy uniquely final lazyMember 'e.g field1.field2.field3)
						entityParam.addLazyFetchMember(entry.getKey());
				}
			}
			Object xmlValue = EngineTools.getXmlValue(beanId);
			if (xmlValue instanceof String)
				entityParam.setIdvalue((String) xmlValue);
			else
				entityParam.setBinaryIdvalue((byte[]) xmlValue);
		}
		if (closingPart)
			entityParam.marshallLazyFetches();
		return entityParam;
	}

	/**
	 * Sets the initialized.
	 *
	 * @param initialized the new initialized
	 */
	@Override
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public void initialize() {
		if (!initialized) {
			entityMM.getDataAccess().refreshEntities(null, false, this);
			initialized = true;
		}
	}

}
