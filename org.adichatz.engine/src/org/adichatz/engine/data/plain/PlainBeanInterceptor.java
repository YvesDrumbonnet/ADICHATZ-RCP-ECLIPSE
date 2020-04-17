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
package org.adichatz.engine.data.plain;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.data.ABeanInterceptor;
import org.adichatz.engine.data.IBeanInterceptor;
import org.adichatz.engine.data.IBeanInterceptorFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PlainBeanInterceptor.
 * 
 * @param <T>
 *            the
 */
public class PlainBeanInterceptor<T> extends ABeanInterceptor<T> implements MethodInterceptor {

	/**
	 * Creates the bean interceptor factory.
	 * 
	 * @return the i bean interceptor factory
	 */
	public static IBeanInterceptorFactory createBeanInterceptorFactory() {
		return new IBeanInterceptorFactory() {
			@Override
			public <T> IBeanInterceptor createEnhancedProxy(IEntity<T> entity) {
				PlainBeanInterceptor<T> beanInterceptor = new PlainBeanInterceptor<T>(entity);
				IBeanInterceptor result = (IBeanInterceptor) Enhancer.create(entity.getBeanClass(),
						new Class[] { IBeanInterceptor.class }, beanInterceptor);
				return result;
			}
		};
	}

	/**
	 * Instantiates a new plain bean interceptor.
	 * 
	 * @param entity
	 *            the entity
	 */
	public PlainBeanInterceptor(IEntity<T> entity) {
		super(entity);
	}

	public void adi$FirePropertiesEntity(Object oldBean) {
		initImpactedTabularsChange();
		@SuppressWarnings("rawtypes")
		Class clazz = oldBean.getClass();
		while (!clazz.equals(Object.class)) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getName().startsWith(SET_PREFIX) && 1 == method.getGenericParameterTypes().length) {
					// Get the property name:
					StringBuilder methodSuffix = new StringBuilder(method.getName().substring(SET_PREFIX.length()));
					Method getMethod = getGetter(clazz, methodSuffix);
					methodSuffix.setCharAt(0, Character.toLowerCase(methodSuffix.charAt(0)));
					Object newGetValue = ReflectionTools.invokeMethod(getMethod, entity.getBean());
					Object oldGetValue = ReflectionTools.invokeMethod(getMethod, oldBean);
					if (!Utilities.equals(oldGetValue, newGetValue))
						firePropertyChange(methodSuffix.toString(), getMethod, newGetValue, oldGetValue);
				}
			}
			clazz = clazz.getSuperclass();
		}
	}

}