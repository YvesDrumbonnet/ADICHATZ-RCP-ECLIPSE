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
package org.adichatz.scenario;

// TODO: Auto-generated Javadoc
/**
 * The Interface IScenarioConstants.
 */
public interface IScenarioConstants {
	/**
	 * DEVELOPMENT<br>
	 * -----------.
	 */
	/** The Constant CREATE_XML_TRACE. */
	public static final String CREATE_XML_TRACE = "devl.create.xml.trace";

	/** The Constant WORKSPACE_DIRECTORY. */
	public static final String WORKSPACE_DIRECTORY = "devl.workspace.directory";

	public static final String MODEL_PACKAGE_NAME = "devl.model.package.name";

	/** The Constant GENCODE_DIRECTORY. */
	public static final String GENCODE_DIRECTORY = "devl.gencode.directory";

	/** The Constant GENCODE_SUB_PACKAGE. */
	public static final String GENCODE_PACKAGE = "devl.gencode.package";

	/** The Constant DEFAULT_INTEGER_PATTERN. */
	public static final String DEFAULT_INTEGER_PATTERN = "devl.default.integer.pattern";

	/** The Constant DEFAULT_DECIMAL_SCALE. */
	public static final String DEFAULT_DECIMAL_SCALE = "devl.default.integer.scale";

	/** The Constant DEFAULT_INTEGER_PATTERN. */
	public static final String DEFAULT_HIBERNATE_INTERCEPTOR = "devl.default.hibernate.interceptor.class.name";

	/** The Constant FORMAT_GENERATED_CLASS. */
	public static final String FORMAT_GENERATED_CLASS = "devl.format.generated.classes";

	public static final String CONNECTORS_URI = "devl.connectors.uri";

	public static final String COMPONENT_GENERATION_URI = "devl.component_generation_uri";

	/** The Constant LEVEL_NONE. */
	public static String LEVEL_NONE = "0";

	/** The Constant LEVEL_INFO. */
	public static String LEVEL_INFO = "1";

	/** The Constant LEVEL_WARNING. */
	public static String LEVEL_WARNING = "2";

	/** The Constant LEVEL_ERROR. */
	public static String LEVEL_ERROR = "3";

	/** The Constant JBOSS_7_1. */
	public static final String JBOSS_7_1 = "jboss-7.1";

	/** The Constant WILDFLY. */
	public static final String WILDFLY = "wildfly";

	/** The Constant JBOSS_EAP_6. */
	public static final String JBOSS_EAP_6 = "jboss-eap-6";

	/** The Constant JSE. */
	public static final String JSE = "jse";

	/**
	 * MISCELLANEOUS<br>
	 * -------------.
	 */

	/** The Constant PLUGIN_PACKAGE. */
	public static final String PLUGIN_PACKAGE = "plugin.package";

	public static String LIB_DIRECTORY = "resources/lib";

	/**
	 * DATASOURCE<br>
	 * -----------.
	 */
	/** The Constant DATASOURCE_UNIT. */
	public static final String DATASOURCE_UNIT = "datasource.unit";

	/** The Constant DATASOURCE_FILE. */
	public static final String DATASOURCE_FILE = "datasource.file";

	/** The Constant DATASOURCE_JNDI. */
	public static final String DATASOURCE_JNDI = "datasource.jndi";

	/**
	 * JPA<br>
	 * ---.
	 */
	/** The Constant JPA_CONNECTOR_VERSION. */
	public static final String JPA_CONNECTOR_VERSION = "jpa.connector.version";

	/** The Constant JPA_CONNECTOR_JAR. */
	public static final String JPA_CONNECTOR_JAR = "jpa.connector.jar";

	/** The Constant PERSITENCE_CLASS_NAME. */
	public static final String PERSITENCE_CLASS_NAME = "jpa.persistenceClassName";

	/** The Constant LOCK_CLASS_NAME. */
	public static final String LOCK_CLASS_NAME = "jpa.lockClassName";

	/** The Constant EJB_JAR_NAME. */
	public static final String EJB_JAR_FILE_NAME = "ejb.jar.file.name";

	/** The Constant APPLICATION_SERVER_HOME. */
	public static final String APPLICATION_SERVER_HOME = "application.server.home";

	/** The Constant ADICHATZ_CONNECTOR. */
	public static final String ADICHATZ_CONNECTOR = "adichatz.connector";

	/** The Constant EJB_DEPLOYMENT_DIR. */
	public static final String EJB_DEPLOYMENT_DIR = "ejb.deployment.dir";

	/** The Constant DEPLOY_EJB. */
	public static final String DEPLOY_EJB = "deployment.ejb";

	/** The Constant MODEL_PLUGIN_KEY. */
	public static final String MODEL_PLUGIN_KEY = "model.plugin.key";

	/** The Constant MISSING_DRIVER_MODULE. */
	public static final int MISSING_DRIVER_MODULE = 1 << 0;

	/** The Constant MISSING_DRIVER_STANDALONE. */
	public static final int MISSING_DRIVER_STANDALONE = 1 << 1;

	/** The Constant MISSING_DATASOURCE_STANDALONE. */
	public static final int MISSING_DATASOURCE_STANDALONE = 1 << 2;

	public static String AS_HOME_FIELD = "application.server.home";

	public static String AS_DEPLOYMENT_DIR_FIELD = "ejb.deployment.dir";

	/** The constant string for Project. */
	public static final String PROJECT = "PROJECT";

	/**
	 * MANIFEST Attributes<br>
	 * -------------------.
	 */
	public static final String ADICHATZ_GENCODE_PACKAGE = "Adichatz-gencode-package"; // for any Adichatz projects

	/** The Constant ADICHATZ_CONNECTOR_VERSION. */
	public static final String ADICHATZ_CONNECTOR_VERSION = "Adichatz-connector-version"; // for Adichatz model projects

	public static final String ADICHATZ_DO_NOT_EXPORT = "Adichatz-do-not-export"; // Adichatz plugin which do not be exported when exporting product
}