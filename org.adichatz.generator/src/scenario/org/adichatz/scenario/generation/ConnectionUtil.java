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
package org.adichatz.scenario.generation;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adichatz.common.encoding.Base64;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.extra.AdiMessageDialog;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.internal.InternalPolicy;
import org.eclipse.swt.widgets.Display;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionUtil.
 */
@SuppressWarnings("restriction")
public class ConnectionUtil {

	/**
	 * Gets the class loader connection.
	 *
	 * @param urls
	 *            the urls
	 * @return the class loader connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static URLClassLoader getClassLoaderConnection(List<URL> urls) throws IOException {

		URL url;
		if (InternalPolicy.OSGI_AVAILABLE) {
			url = Platform.getBundle(GeneratorConstants.TEMPLATE_BUNDLE)
					.getEntry("/template/lib/tool/org.adichatz.generator.dynamic.jar");
		} else {
			String dynamicJarName = GeneratorConstants.SECONDARY_WORKSPACE_DIRECTORY + "/" + GeneratorConstants.TEMPLATE_BUNDLE
					+ "/template/lib/tool/org.adichatz.generator.dynamic.jar";
			try {
				url = new File(dynamicJarName).toURI().toURL();
			} catch (MalformedURLException e) {
				logError(getFromEngineBundle("malformedURLException.message", dynamicJarName), e);
				url = null;
			}
		}
		urls.add(url);

		URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]),
				ScenarioResources.class.getClassLoader());
		return classLoader;
	}

	/**
	 * Display exception.
	 *
	 * @param message
	 *            the message
	 * @param exception
	 *            the e
	 */
	private static void displayException(String message, Exception exception) {
		String errorString = EngineTools.getErrorString(exception);
		message = null == message ? errorString : message.concat("\n\n").concat(errorString);
		if (null == Display.getCurrent())
			throw new RuntimeException(message, exception);
		else {
			AdiMessageDialog dialog = new AdiMessageDialog(Display.getCurrent(), MessageDialog.ERROR, null,
					getFromGeneratorBundle("jdbc.connection.error"), message);
			dialog.open();
		}
	}

	/**
	 * Test connection.
	 *
	 * @param properties
	 *            the properties
	 * @param displayMessage
	 *            the display message
	 * @return true, if successful
	 */
	public static boolean testConnection(Properties properties, boolean displayMessage) {
		boolean connectionOk = false;
		String message = getFromGeneratorBundle("jdbc.connection.succedeed");
		String labels[] = new String[1];
		labels[0] = "OK";

		try {
			ISqlConnection connectionTest = getConnection(properties.getProperty(ISqlConnection.JDBC_DRIVER_JAR));
			Connection sqlConnection = connectionTest.getConnection(properties);
			sqlConnection.close();
			connectionOk = true;
			if (displayMessage)
				new AdiMessageDialog(Display.getCurrent().getActiveShell(), MessageDialog.INFORMATION, new String[] { "OK" }, null,
						null, getFromGeneratorBundle("jdbc.connection"), message).open();
		} catch (ClassNotFoundException e) {
			displayException(getFromGeneratorBundle("jdbc.connection.driverNotFound",
					properties.getProperty(ISqlConnection.JDBC_DRIVER_JAR)), e);
		} catch (Exception e) {
			displayException(null, e);
		}
		return connectionOk;
	}

	public static ISqlConnection getConnection(String jdbcDriverJar)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		List<URL> urls = new ArrayList<URL>(1);
		urls.add(new File(jdbcDriverJar).toURI().toURL());
		ClassLoader classLoader = getClassLoaderConnection(urls);
		return (ISqlConnection) classLoader.loadClass("org.adichatz.generator.dynamic.AdiSqlConnection").getDeclaredConstructor()
				.newInstance();
	}

	public static Properties getProperties(DatasourceWrapper dataSource, boolean decrypt) {
		Properties properties = new Properties();
		addParam(dataSource.getParams(), decrypt, properties);
		addParam(dataSource.getBuildParams(), decrypt, properties);
		return properties;
	}

	public static void addParam(ParamsType params, boolean decrypt, Properties properties) {
		if (null != params)
			for (ParamType param : params.getParam()) {
				String key = param.getId();
				if (null != param.getValue())
					if (decrypt && ISqlConnection.CONNECTION_PASSWORD.equals(key))
						try {
							properties.put(key, Base64.decrypt(param.getValue()));
						} catch (Exception e) {
							logError(e);
						}
					else if (ISqlConnection.JDBC_DRIVER_JAR.equals(key) || ISqlConnection.REVENG_FILE.equals(key))
						properties.put(key, new KeyWordGenerator().evalExpression(param.getValue()));
					else
						properties.put(key, param.getValue());
			}
	}
}
