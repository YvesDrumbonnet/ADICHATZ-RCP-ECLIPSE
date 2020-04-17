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
package org.adichatz.studio.xjc.data;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.adichatz.common.ejb.AdiPMException;
import org.adichatz.common.ejb.MultiKey;
import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.BeanUtils;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.AEntity;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.BasicType;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.adichatz.studio.xjc.editor.ScenarioFormEditor;
import org.adichatz.studio.xjc.editor.XjcTreeFormEditor;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcEntity.
 * 
 * @param <T>
 *            the
 */
public class XjcEntity<T> extends AEntity<T> {

	/** The tree element. */
	private XjcTreeElement treeElement;

	/** The set controller. */
	private ASetController setController;

	/** The bean before modification. */
	private T beforeBean;

	/**
	 * Instantiates a new plain entity.
	 * 
	 * @param dataAccess
	 *            the data access
	 * @param bean
	 *            the bean
	 * @param status
	 *            the status
	 * @param cacheKey
	 *            the cache key
	 */
	public XjcEntity(ADataAccess dataAccess, T bean, int status, MultiKey cacheKey) {
		super(dataAccess, bean, status);
		this.cacheKey = cacheKey;
		beanId = dataAccess.getBeanId(bean);
		beanInterceptor = BeanInterceptor.createBeanInterceptorFactory().createEnhancedProxy(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#refreshEntityIfNeeded(org.adichatz.engine.validation.ABindingService,
	 * java.lang.String[])
	 */
	@Override
	public void refreshEntityIfNeeded(ABindingService bindingService, String... lazyFetchMembers) throws AdiPMException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.cache.IEntity#firePropertyChanges()
	 */
	public void firePropertyChanges() {
		Class<?> superClass = beanClass;
		while (BasicType.class != superClass && Object.class != superClass) {
			firePropertyChanges(superClass);
			superClass = superClass.getSuperclass();
		}

	}

	/**
	 * Gets the tree element.
	 * 
	 * @return the tree element
	 */
	public XjcTreeElement getTreeElement() {
		return treeElement;
	}

	/**
	 * Sets the tree element.
	 * 
	 * @param treeElement
	 *            the new tree element
	 */
	public void setTreeElement(XjcTreeElement treeElement) {
		this.treeElement = treeElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.AEntity#putStatus(int, boolean)
	 */
	@Override
	public void putStatus(int status, boolean forced) {
		super.putStatus(status, forced);
		if (status > IEntityConstants.RETRIEVE)
			for (ABindingService bindingService : bindingServices) {
				XjcBindingService xjcBindingService = (XjcBindingService) bindingService;
				if ((xjcBindingService.getEditor() instanceof ScenarioFormEditor)
						|| (xjcBindingService.getEditor() instanceof XjcTreeFormEditor && null != treeElement))
					((XjcBindingService) bindingService).enableActions();
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.AEntity#removeFromCache()
	 */
	@Override
	public void removeFromCache() {
		super.removeFromCache();
		if (null != lockingBindingService) {
			XjcBindingService bindingService = ((XjcTreeController) setController).getBindingService();
			bindingService.getErrorBeans().remove(bean);
			bindingService.getTreeErrorMessageMap().remove(this);
			bindingService.enableActions();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.AEntity#lock(boolean, org.adichatz.engine.validation.ABindingService)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void lock(boolean locked, ABindingService bindingService) {
		if (locked) {
			lockingBindingService = bindingService;
		} else
			lockingBindingService = null;
		if (null == beforeBean) {
			// use bean.getClass() rather than beanClass due to wrappers
			beforeBean = (T) ReflectionTools.instantiateClass(bean.getClass());
			ScenarioUtil.copyExtendedFields(bean, beforeBean);
		}
	}

	/**
	 * Refresh.
	 */
	@SuppressWarnings("unchecked")
	public void refresh() {
		if (IEntityConstants.PERSIST == status) {
			XjcTreeElement parentElement = treeElement.getParentElement();
			parentElement.getChildElements().remove(treeElement);
			Object result = ReflectionTools.invokeMethod(treeElement.getGetMethod(), parentElement.getElement());
			if (result instanceof Collection)
				((Collection<?>) result).remove(bean);
			else {
				String setMethodName = "s".concat(treeElement.getGetMethod().getName().substring(1));
				Method setMethod = ReflectionTools.getMethod(parentElement.getElement().getClass(), setMethodName,
						treeElement.getGetMethod().getReturnType());
				ReflectionTools.invokeMethod(setMethod, parentElement.getElement(), new Object[] { null });
			}
			removeFromCache();
		} else {
			if (null != beforeBean) {
				BeanUtils.copyExtendedFields(beforeBean, bean);
			}
			// use bean.getClass() rather than beanClass due to wrappers
			beforeBean = (T) ReflectionTools.instantiateClass(bean.getClass());
			if (bean instanceof PluginEntityWrapper) {
				((PluginEntityWrapper) beforeBean).setScenarioResources(((PluginEntityWrapper) bean).getScenarioResources());
				((PluginEntityWrapper) beforeBean).setEntityURI(((PluginEntityWrapper) bean).getEntityURI());
			}
			beanInterceptor.adi$FirePropertiesEntity(beforeBean);
			BeanUtils.copyExtendedFields(bean, beforeBean);
		}
	}

	/**
	 * Sets the bean.
	 * 
	 * @param bean
	 *            the new bean
	 */
	@SuppressWarnings("unchecked")
	public void setBean(Object bean) {
		if (null == bean)
			try {
				bean = beanClass.getDeclaredConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				logError(e);
			}
		this.bean = (T) bean;
		if (null != beforeBean)
			ScenarioUtil.copyExtendedFields(bean, beforeBean);
	}

	/**
	 * Gets the sets the controller.
	 * 
	 * @return the sets the controller
	 */
	public ASetController getSetController() {
		return setController;
	}

	/**
	 * Sets the sets the controller.
	 * 
	 * @param setController
	 *            the new sets the controller
	 */
	public void setSetController(ASetController setController) {
		this.setController = setController;
	}

}
