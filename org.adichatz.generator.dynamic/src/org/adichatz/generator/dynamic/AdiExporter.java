/*******************************************************************************
 * Copyright © Adichatz (2007-2009) - www.adichatz.org
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
 * Copyright © Adichatz (2007-2009) - www.adichatz.org
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
package org.adichatz.generator.dynamic;

/*
 * <h1><center> ************************* *** C A U T I O N *** ************************* </center></h1>
 * 
 * this class is dynamically loaded in the application No reference will be found in the application
 */

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.adichatz.scenario.generation.ISqlConnection;
import org.hibernate.cfg.JDBCMetaDataConfiguration;
import org.hibernate.cfg.reveng.DefaultReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.OverrideRepository;
import org.hibernate.cfg.reveng.ReverseEngineeringSettings;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.tool.hbm2x.POJOExporter;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiExporter.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class AdiExporter {

	/**
	 * Export.
	 * 
	 * @param sqlProperties
	 *            the sql properties
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public void export(Properties sqlProperties) throws InstantiationException, IllegalAccessException, ClassNotFoundException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Properties hibernateProperties = new Properties();
		addProperty(sqlProperties, ISqlConnection.DIALECT, hibernateProperties, "hibernate.dialect");
		addProperty(sqlProperties, ISqlConnection.DRIVER_CLASS, hibernateProperties, "hibernate.connection.driver_class");
		addProperty(sqlProperties, ISqlConnection.CONNECTION_URL, hibernateProperties, "hibernate.connection.url");
		addProperty(sqlProperties, ISqlConnection.CONNECTION_USERNAME, hibernateProperties, "hibernate.connection.username");
		addProperty(sqlProperties, ISqlConnection.CONNECTION_PASSWORD, hibernateProperties, "hibernate.connection.password");
		addProperty(sqlProperties, ISqlConnection.CONNECTION_SCHEMA, hibernateProperties, "hibernate.connection.schema");
		hibernateProperties.put("jdbc.driver.jar", sqlProperties.getProperty(ISqlConnection.JDBC_DRIVER_JAR));

		JDBCMetaDataConfiguration cfg = new JDBCMetaDataConfiguration();
		cfg.addProperties(hibernateProperties);

		String revengStrategyClassName = (String) sqlProperties.get(ISqlConnection.REVENG_STRATEGY);
		ReverseEngineeringStrategy strategy;
		if (null == revengStrategyClassName)
			strategy = new DefaultReverseEngineeringStrategy();
		else
			strategy = (ReverseEngineeringStrategy) Class.forName(revengStrategyClassName).getDeclaredConstructor().newInstance();

		String revengFileName = (String) sqlProperties.get(ISqlConnection.REVENG_FILE);
		if (null != revengFileName && !revengFileName.equals("")) {
			File revengFile = new File(revengFileName);
			if (!revengFile.exists())
				throw new RuntimeException("Reverse engineering file '" + revengFile.getAbsolutePath() + "' was not found!");
			OverrideRepository or = new OverrideRepository();
			or.addFile(revengFile);
			strategy = or.getReverseEngineeringStrategy(strategy);
		}

		ReverseEngineeringSettings settings = new ReverseEngineeringSettings(strategy);
		settings.setDefaultPackageName(sqlProperties.getProperty(ISqlConnection.REVENG_BEAN_PACKAGE_NAME));
		settings.setDetectManyToMany(true);

		strategy.setSettings(settings);

		cfg.setReverseEngineeringStrategy(strategy);
		cfg.readFromJDBC();

		File outputDir = new File(sqlProperties.getProperty(ISqlConnection.REVENG_BEAN_SRC_PATH));
		POJOExporter exporter = new POJOExporter(cfg, outputDir);
		exporter.getProperties().setProperty("jdk5", "true");
		exporter.getProperties().setProperty("ejb3", "true");
		exporter.setFilePattern("{package-name}/{class-name}.java");
		exporter.setTemplatePath(new String[] { sqlProperties.getProperty(ISqlConnection.REVENG_TEMPLATE_PATH) });
		exporter.setTemplateName(sqlProperties.getProperty(ISqlConnection.REVENG_TEMPLATE_NAME));
		exporter.start();
	}

	private void addProperty(Properties sqlProperties, String key, Properties hibernateProperties, String hibKey) {
		String value = sqlProperties.getProperty(key);
		if (null != value)
			hibernateProperties.put(hibKey, value);
	}
}
