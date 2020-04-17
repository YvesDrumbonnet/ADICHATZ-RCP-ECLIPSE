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
package org.adichatz.engine.data;

/**
 * @see http://weblogs.java.net/blog/fabriziogiudici/archive/2008/01/beansbinding_no<br/>
 */

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.model.RefField;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.viewer.IBoundContentProvider;

import net.sf.cglib.proxy.MethodProxy;

/**
 * ABeanInterceptor
 * 
 * Abstract class extended by BeanInterceptor.<br>
 * BeanInterceptor must me defined in the top level plugin close to the model
 * classes to avoid strange exceptions<br>
 * 
 * @author Yves Drumbonnet
 * 
 * 
 *         Based on article of Fabrizio Giudici:<br>
 *         http://weblogs.java.net/blog/fabriziogiudici/archive/2008/01/beansbinding_no
 * 
 */
public abstract class ABeanInterceptor<T> implements IBeanInterceptor {
	protected static final List<Method> propertyChangeListenerSupportMethods = Arrays
			.asList(IBeanInterceptor.class.getMethods());

	protected static final String SET_PREFIX = "set";

	protected static final String GET_PREFIX = "get";

	protected static final String IS_PREFIX = "is";

	protected List<TabularBeanChange> tabularBeanChanges;

	protected List<BeanChange> beanChanges;

	protected final PropertyChangeSupport propertyChangeSupport;

	protected APluginEntityTree pluginEntityTree;

	/**
	 * @return the propertyChangeSupport
	 */
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	protected IEntity<T> entity;

	public ABeanInterceptor(IEntity<T> entity) {
		this.entity = entity;
		propertyChangeSupport = new PropertyChangeSupport(entity.getBean());
	}

	public Object intercept(Object object, Method method, Object[] methodParameters, MethodProxy proxy)
			throws Throwable {
		// If property change listener method is called, pass it to 'this':
		if (propertyChangeListenerSupportMethods.contains(method)) {
			return method.invoke(this, methodParameters);
		}
		if (!method.getName().startsWith(SET_PREFIX)) {
			// No, pass the call to bean.
			return method.invoke(entity.getBean(), methodParameters);
		}
		//
		// Setter called.
		//
		// Try to get the old value, call the setter, and finally fire a
		// property change event:
		//

		// Get the property name:
		StringBuilder methodSuffix = new StringBuilder(method.getName().substring(SET_PREFIX.length()));

		// Get the old value, or use null if bean does'nt have a getter.
		Method getMethod = getGetter(entity.getBean().getClass(), methodSuffix);
		Object oldValue = (getMethod != null) ? getMethod.invoke(object) : null;

		// Call the setter:
		Object returnValue = method.invoke(entity.getBean(), methodParameters);

		// If the setter returns the bean itself, return the wrapped object
		// instead. This
		// provides support for "setter chaining".
		if (returnValue == entity.getBean())
			returnValue = object;

		// Change the first letter to lower case and fire property change:
		methodSuffix.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));

		String fieldName = methodSuffix.toString();
		AListener.fireListener(entity.getListeners(),
				new AdiEntityEvent(IEventType.WHEN_PROPERTY_CHANGE, entity, fieldName));

		initImpactedTabularsChange();
		firePropertyChange(fieldName, getMethod, methodParameters[0], oldValue);
		fireImpactedTabularsChange();

		return returnValue;
	}

	/**
	 * Get the getter for given property, or <code>null</code> if bean does not
	 * support getter for given property.
	 * 
	 * @param beanClass    <code>Class</code> of the bean
	 * @param propertyName Property name
	 * @return Getter method, or <code>null</code> if getter is not supported.
	 */

	protected Method getGetter(Class<?> beanClass, StringBuilder propertyName) {

		// Try the 'get' getter first:
		try {
			return beanClass.getMethod(new StringBuilder().append(GET_PREFIX).append(propertyName).toString());
		} catch (NoSuchMethodException e) {
			// No 'get' getter, proceed...
		}

		// Try 'is' getter:
		try {
			return beanClass.getMethod(new StringBuilder().append(IS_PREFIX).append(propertyName).toString());
		} catch (NoSuchMethodException e1) {
			// No 'is' getter either, proceed...
		}

		// Return null indicating that this bean does not have a getter for this
		// property.
		return null;
	}

	protected void firePropertyChange(String fieldName, Method getMethod, Object newGetValue, Object oldGetValue) {
		if (!(oldGetValue instanceof Set)) {
			propertyChangeSupport.firePropertyChange(fieldName, oldGetValue, newGetValue);
			if (!ABindingService.isSynchronizing()) {
				PropertyField joinColumn = entity.getEntityMM().getFieldMap().get(fieldName);
				if (null != joinColumn && joinColumn instanceof RefField) {
					try {
						if (null == oldGetValue ? null != newGetValue
								: null == newGetValue
										|| !((RefField<?>) joinColumn).getParentEntityMM().getId(oldGetValue).equals(
												((RefField<?>) joinColumn).getParentEntityMM().getId(newGetValue))) {
							entity.getEntityMM().getDataAccess().getDataCache().doRelationshipUpdate(entity,
									((RefField<?>) joinColumn).getParentMappedBy(), oldGetValue, newGetValue,
									IEntityConstants.relationshipUpdateType.UPDATE_MANY_TO_ONE, fieldName);
						}
					} catch (NullPointerException e) {
						// Do nothing, means that values are proxy values and are not be initialized
					}
				}
				if (!tabularBeanChanges.isEmpty()) {
					Class<?> returnType;
					// If return type match up to bean and interface (e.g. IOrgBean) is used, look
					// for UI bean class because setter
					// is using interface.
					PluginEntity<?> pluginEntity = getPluginEntityTree(entity)
							.getPluginEntity(getMethod.getReturnType());
					if (null != pluginEntity)
						returnType = pluginEntity.getUIBeanClass();
					else
						returnType = getMethod.getReturnType();
					Method setMethod = FieldTools.getSetMethod(entity.getBeanClass(), fieldName, returnType, true);
					beanChanges.add(new BeanChange(newGetValue, setMethod));
				}
			}
		}
	}

	/**
	 * Inits the impacted tabulars change.
	 */
	@SuppressWarnings("unchecked")
	protected void initImpactedTabularsChange() {
		Set<ATabularController<?>> tabularControllers = entity.getEntityMM().getDataAccess().getDataCache()
				.getTabularControllerByCKey(entity);
		tabularBeanChanges = new ArrayList<TabularBeanChange>();
		if (null != tabularControllers) {
			for (ATabularController<?> tabularController : tabularControllers) {
				T bean = ((IBoundContentProvider<T>) tabularController.getViewer().getContentProvider()).getBeanMap()
						.get(entity.getCacheKey());
				tabularBeanChanges.add(new TabularBeanChange((ATabularController<T>) tabularController, bean));
			}
		}
		beanChanges = new ArrayList<BeanChange>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBeanInterceptor#fireImpactedTabularsChange()
	 */
	@Override
	public void fireImpactedTabularsChange() {
		if (null != tabularBeanChanges)
			for (TabularBeanChange tabularBeanChange : tabularBeanChanges) {
				for (BeanChange beanChange : beanChanges) {
					ReflectionTools.invokeMethod(beanChange.getSetMethod(), tabularBeanChange.getBean(),
							new Object[] { beanChange.getNewValue() });
				}
				tabularBeanChange.getTabularController().update(entity, tabularBeanChange.getBean());
			}
	}

	public void adi$FireNullEntity() {
		initImpactedTabularsChange();
		for (Method method : entity.getBeanClass().getDeclaredMethods()) {
			if (method.getName().startsWith(SET_PREFIX) && 1 == method.getGenericParameterTypes().length) {
				// Get the property name:
				StringBuilder methodSuffix = new StringBuilder(method.getName().substring(SET_PREFIX.length()));
				Method getMethod = getGetter(entity.getBean().getClass(), methodSuffix);
				methodSuffix.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));
				Object currentValue = ReflectionTools.invokeMethod(getMethod, entity.getBean());

				if (!Utilities.equals(null, currentValue))
					firePropertyChange(methodSuffix.toString(), getMethod, null, currentValue);
			}
		}
	}

	public void adi$FirePropertiesEntity(Object oldBean) {
		initImpactedTabularsChange();
		for (Method method : oldBean.getClass().getDeclaredMethods()) {
			if (method.getName().startsWith(SET_PREFIX) && 1 == method.getGenericParameterTypes().length) {
				// Get the property name:
				StringBuilder methodSuffix = new StringBuilder(method.getName().substring(SET_PREFIX.length()));
				Method getMethod = getGetter(entity.getBean().getClass(), methodSuffix);
				if (null != getMethod) {
					methodSuffix.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));
					Object newGetValue = ReflectionTools.invokeMethod(getMethod, entity.getBean());
					Object oldGetValue = ReflectionTools.invokeMethod(getMethod, oldBean);
					if (!Utilities.equals(oldGetValue, newGetValue))
						firePropertyChange(methodSuffix.toString(), getMethod, newGetValue, oldGetValue);
				}
			}
		}
	}

	@Override
	public IEntity<?> adi$getEntity() {
		return entity;
	}

	/**********************************************
	 * INNERS CLASSES
	 **********************************************/

	protected class TabularBeanChange {
		protected ATabularController<T> tabularController;

		protected T bean;

		public TabularBeanChange(ATabularController<T> tabularController, T bean) {
			this.tabularController = tabularController;
			this.bean = bean;
		}

		public ATabularController<T> getTabularController() {
			return tabularController;
		}

		public T getBean() {
			return bean;
		}

	}

	protected class BeanChange {
		Method setMethod;

		Object newValue;

		public BeanChange(Object newValue, Method setMethod) {
			super();
			this.newValue = newValue;
			this.setMethod = setMethod;
		}

		public Method getSetMethod() {
			return setMethod;
		}

		public Object getNewValue() {
			return newValue;
		}

	}

	public APluginEntityTree getPluginEntityTree(IEntity<T> entity) {
		if (null == pluginEntityTree)
			pluginEntityTree = entity.getEntityMM().getPluginEntity().getPluginEntityTree();
		return pluginEntityTree;
	}

}
