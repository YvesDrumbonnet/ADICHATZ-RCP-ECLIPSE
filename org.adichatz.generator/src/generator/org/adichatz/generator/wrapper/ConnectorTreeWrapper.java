/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.generator.wrapper;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.xjc.ApplicationServerType;
import org.adichatz.generator.xjc.ConnectorTree;
import org.adichatz.generator.xjc.DatasourceType;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.JBossStandaloneReader;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectorTreeWrapper.
 */
public class ConnectorTreeWrapper extends ConnectorTree {

	/** The unit map. */
	private Map<String, DatasourceType> datasourceMap;

	public DatasourceWrapper getDatasource(String datasourceId) {
		if (null == datasourceMap) {
			datasourceMap = new HashMap<String, DatasourceType>();
			if (null != getDatasources().getDatasource())
				for (DatasourceType datasourceType : getDatasources().getDatasource())
					datasourceMap.put(datasourceType.getId(), datasourceType);
		}
		return (DatasourceWrapper) datasourceMap.get(datasourceId);
	}

	/**
	 * Gets the data source.
	 * 
	 * @param datasourceName
	 *            the data source name
	 * @return the data source
	 */
	public DatasourceWrapper getDatasourceByName(String datasourceName) {
		if (null != datasources)
			for (DatasourceType dataSource : datasources.getDatasource())
				if (datasourceName.equals(dataSource.getDatasourceName()))
					return (DatasourceWrapper) dataSource;
		return null;
	}

	/**
	 * Gets the application server.
	 * 
	 * @param applicationServerName
	 *            the application server name
	 * @return the application server
	 */
	public ApplicationServerType getApplicationServer(String applicationServerName) {
		if (null != applicationServers)
			for (ApplicationServerType applicationServer : applicationServers.getApplicationServer())
				if (applicationServerName.equals(applicationServer.getName()))
					return applicationServer;
		return null;
	}

	public String getAdichatzConnector(String applicationServerName) {
		ApplicationServerType applicationServer = getApplicationServer(applicationServerName);
		if (null != applicationServer && null != applicationServer.getParams()) {
			return GeneratorUtil.getParamValue(applicationServer.getParams(), IScenarioConstants.ADICHATZ_CONNECTOR);
		}
		return null;
	}

	public String getApplicationServerHome(String applicationServerName) {
		ApplicationServerType applicationServer = getApplicationServer(applicationServerName);
		if (null != applicationServer && null != applicationServer.getParams()) {
			return GeneratorUtil.getParamValue(applicationServer.getParams(), IScenarioConstants.APPLICATION_SERVER_HOME);
		}
		return null;
	}

	/**
	 * Gets the ejb deploy dir.
	 * 
	 * @param applicationServerName
	 *            the application server name
	 * @return the ejb deploy dir
	 */
	public String getEjbDeployDir(String applicationServerName) {
		ApplicationServerType applicationServer = getApplicationServer(applicationServerName);
		if (null != applicationServer && null != applicationServer.getParams()) {
			String ejbDeployDir = GeneratorUtil.getParamValue(applicationServer.getParams(), IScenarioConstants.EJB_DEPLOYMENT_DIR);
			return getApplicationServerHome(applicationServerName).concat("/").concat(ejbDeployDir);
		}
		return null;
	}

	/**
	 * Test connection.
	 */
	public boolean testConnection(Shell shell, String datasourceId, boolean displayMessage) {
		DatasourceWrapper dataSource = getDatasource(datasourceId);
		if (null == dataSource) {
			String message = getFromGeneratorBundle("no.datasource.error", datasourceId);
			if (null != shell)
				EngineTools.openDialog(MessageDialog.ERROR, getFromGeneratorBundle("jdbc.connection.error"), message);
			else
				logError(message);
			return false;
		}
		return dataSource.testConnection(displayMessage);
	}

	public int checkJboss71Iinstallation(String applicationServerName, DatasourceWrapper datasource, String jndi,
			ScenarioResources scenarioResources) throws AdichatzErrorException {
		int check = 0;
		String jbossInstallation = getApplicationServerHome(applicationServerName);
		if (null == jbossInstallation)
			throw new RuntimeException(getFromGeneratorBundle("generation.error.wrong.application.server", applicationServerName));
		if (!new File(jbossInstallation).exists()) {
			String message = getFromGeneratorBundle("generation.error.wrong.jboss.home", jbossInstallation);
			LogBroker.displayError(getFromGeneratorBundle("generation.error"), message);
			throw new AdichatzErrorException(message);
		} else {
			String module = datasource.getDriverClassName();
			module = datasource.getModule();
			File moduleFile = new File(jbossInstallation + "/modules/" + module.replace('.', '/'));
			if (!moduleFile.exists())
				check = IScenarioConstants.MISSING_DRIVER_MODULE;
			JBossStandaloneReader jBossStandaloneReader = datasource.getJBossStandaloneReader(jbossInstallation, jndi,
					scenarioResources);
			if (null != jBossStandaloneReader) {
				if (!jBossStandaloneReader.hasDriver(datasource.getDriver()))
					check = check | IScenarioConstants.MISSING_DRIVER_STANDALONE;
				if (!jBossStandaloneReader.hasDatasource(jndi))
					check = check | IScenarioConstants.MISSING_DATASOURCE_STANDALONE;
			}
		}
		return check;
	}
}
