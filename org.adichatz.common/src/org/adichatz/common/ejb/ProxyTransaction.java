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
 * The Class ProxyTransaction.
 * 
 * Wrapping of all proxy entities passed on between Adichatzz engine and persistence layer<br>
 * Used when saving or refreshing entities.
 * 
 * @see org.adichatz.engine.common.DataBaseTools
 * @see org.adichatz.hibernate.common.AAdiPersistenceManager
 * @see org.adichatz.hibernate.ejb.AdiPersistenceManagerBean
 * @author Yves Drumbonnet
 * 
 */
public class ProxyTransaction implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1088072350554855500L;

	/** The proxy entities. */
	List<ProxyEntity<?>> proxyEntities;

	/** The proxy queries. */
	List<AdiQuery> proxyQueries;

	/** The related entities. */
	List<RelatedEntity<?>> relatedEntities = new ArrayList<RelatedEntity<?>>();

	/** The session of the transaction. */
	private Session session;

	private String pluginName;

	/** Does the refresh transaction unlock entities. */
	private boolean unlock;

	/**
	 * Instantiates a new proxy transaction.
	 * 
	 * @param session
	 *            the session
	 * @param proxyEntities
	 *            the proxy entities
	 */
	public ProxyTransaction(Session session, String pluginName) {
		this.session = session;
		this.pluginName = pluginName;
	}

	/**
	 * Instantiates a new proxy transaction.
	 * 
	 * @param session
	 *            the session
	 * @param proxyEntities
	 *            the proxy entities
	 * @param unlock
	 *            the unlock
	 */
	public ProxyTransaction(Session session, String pluginName, boolean unlock) {
		this(session, pluginName);
		this.unlock = unlock;
	}

	/**
	 * Gets the session.
	 * 
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Gets the plugin name.
	 * 
	 * @return the plugin name
	 */
	public String getPluginName() {
		return pluginName;
	}

	/**
	 * Gets the proxy entities.
	 * 
	 * @return the proxyEntities
	 */
	public List<ProxyEntity<?>> getProxyEntities() {
		return proxyEntities;
	}

	public void setProxyEntities(List<ProxyEntity<?>> proxyEntities) {
		this.proxyEntities = proxyEntities;
	}

	/**
	 * Gets the related entities.
	 * 
	 * @return the touchedObjects
	 */
	public List<RelatedEntity<?>> getRelatedEntities() {
		return relatedEntities;
	}

	public List<AdiQuery> getProxyQueries() {
		return proxyQueries;
	}

	public void setProxyQueries(List<AdiQuery> proxyQueries) {
		this.proxyQueries = proxyQueries;
	}

	/**
	 * @return the unlock
	 */
	public boolean isUnlock() {
		return unlock;
	}
}
