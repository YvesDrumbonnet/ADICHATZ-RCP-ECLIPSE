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
package org.adichatz.jpa.data;

import java.net.MalformedURLException;
import java.util.Properties;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.adichatz.common.ejb.remote.IAdiLockManager;
import org.adichatz.common.ejb.remote.IAdiPersistenceManager;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;

//TODO: Auto-generated Javadoc
/**
 * The Class AJPACommonConnector.
 * 
 * Provides connection between EJB and Engine
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class AJPACommonConnector extends ACommonConnector {

	/** The persistence manager. */
	protected IAdiPersistenceManager persistenceManager;

	/** The lock manager. */
	private IAdiLockManager lockManager;

	/** The pm lookup. */
	protected String pmLookup;

	/** The lm lookup. */
	protected String lmLookup;

	/** The initial context. */
	protected InitialContext initialContext;

	protected String pluginName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IGatewayConnector#getPersistenceManager()
	 */
	@Override
	public IAdiPersistenceManager getPersistenceManager() throws MalformedURLException, NamingException, InterruptedException {
		if (null == persistenceManager) {
			try {
				InitialContext initialContext = getInitialContext();
				persistenceManager = (IAdiPersistenceManager) initialContext.lookup(pmLookup);
				persistenceManager.isConnected();
			} catch (IllegalStateException | CommunicationException e) {
				persistenceManager = null;
				initialContext = null;
				throw e;
			}
		}
		return persistenceManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IGatewayConnector#getLockManager()
	 */
	@Override
	public IAdiLockManager getLockManager() throws MalformedURLException, NamingException, InterruptedException {
		if (null == lockManager)
			try {
				lockManager = (IAdiLockManager) getInitialContext().lookup(lmLookup);
			} catch (CommunicationException e) {
				initialContext = null;
				throw e;
			}
		return lockManager;
	}

	/**
	 * Gets the initial context.
	 * 
	 * @return the initial context
	 * 
	 * @throws NamingException
	 *             the naming exception
	 * @throws MalformedURLException
	 *             the malformed url exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	protected InitialContext getInitialContext() throws NamingException, MalformedURLException, InterruptedException {
		// JPAConnector could be instantiated later (when called from another plugin), so connector context is not initialized
		if (null == initialContext) {
			AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(pluginName);
			final Properties environment = getProperties(pluginResources, "CONTEXT");

			ClassLoader contextClassLoader = getClass().getClassLoader();
			initialContext = getInitialContext(environment, contextClassLoader);
		}
		return initialContext;
	}

	protected InitialContext getInitialContext(final Properties environment, ClassLoader contextClassLoader)
			throws InterruptedException {
		class InitialContextGetter implements Runnable {
			protected InitialContext result;

			public void run() {
				try {
					result = new InitialContext(environment);
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}

		final InitialContextGetter getter = new InitialContextGetter();
		final Thread getterThread = new Thread(getter);
		getterThread.setContextClassLoader(contextClassLoader);
		getterThread.start();
		getterThread.join();
		return getter.result;
	}
}
