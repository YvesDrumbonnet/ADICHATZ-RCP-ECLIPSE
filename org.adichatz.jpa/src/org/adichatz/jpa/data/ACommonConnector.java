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

import java.util.Properties;

import org.adichatz.common.ejb.remote.IAdiLockManager;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.data.IGatewayConnector;
import org.adichatz.engine.xjc.AdichatzConnectorConfigTree;
import org.adichatz.engine.xjc.ParamType;

//TODO: Auto-generated Javadoc
/**
 * The Class AJPACommonConnector.
 * 
 * Provides connection between EJB and Engine
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class ACommonConnector implements IGatewayConnector {

	/** The proxy interface specifying that Pojo is a proxy value . */
	private String proxyInterface = "org.hibernate.proxy.HibernateProxy";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IGatewayConnector#extractException(java.lang.Exception)
	 */
	@Override
	public Throwable extractException(Exception runtimeException) {
		if (runtimeException instanceof RuntimeException) {
			Throwable currentThrowable = runtimeException.getCause();
			if (null != currentThrowable)
				while (true) {
					Throwable parentThrowable = currentThrowable.getCause();
					if (null == parentThrowable || currentThrowable.equals(parentThrowable))
						break;
					if (currentThrowable.getClass().getName().startsWith("org.hibernate"))
						return currentThrowable;
					currentThrowable = parentThrowable;
				}
		}
		return runtimeException;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IGatewayConnector#isLazyInstance(java.lang.Object)
	 */
	@Override
	public boolean isLazyInstance(Object value) {
		if (null == value)
			return false;
		for (Class<?> clazz : value.getClass().getInterfaces())
			if (clazz.getName().equals(proxyInterface))
				return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IGatewayConnector#getLockManager()
	 */
	public abstract IAdiLockManager getLockManager() throws Exception;

	/**
	 * Gets the properties.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param contextType
	 *            the context type
	 * @return the properties
	 */
	protected static Properties getProperties(AdiPluginResources pluginResources, String contextType) {
		final Properties properties = new Properties();
		AdichatzConnectorConfigTree connectorConfigTree = (AdichatzConnectorConfigTree) pluginResources
				.getConfigTree("AdichatzConnectorConfig.xml", true);
		if (null != connectorConfigTree && null != connectorConfigTree.getParams())
			for (ParamType param : connectorConfigTree.getParams().getParam()) {
				if (contextType.equals(param.getType()))
					properties.put(param.getId(), getValue(param.getValue()));
			}
		return properties;
	}

	/**
	 * Gets the value.
	 *
	 * @param value
	 *            the value
	 * @return the value
	 */
	private static Object getValue(String value) {
		if ("true".equals(value.toLowerCase()))
			return true;
		if ("false".equals(value.toLowerCase()))
			return false;
		return value;
	}
}
