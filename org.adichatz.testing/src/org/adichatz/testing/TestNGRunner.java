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
package org.adichatz.testing;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.testing.TestingTools.getFromTestingBundle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.e4.resource.E4Logger;
import org.adichatz.engine.e4.resource.E4LoggerListener;
import org.adichatz.testing.tracking.AdiResultListener;
import org.adichatz.testing.xjc.AdichatzTestingTree;
import org.adichatz.testing.xjc.SuiteType;
import org.adichatz.testing.xjc.TestFileType;
import org.adichatz.testing.xjc.TestNodeType;
import org.adichatz.testing.xjc.TestType;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.graphics.Image;
import org.testng.ITestContext;
import org.testng.TestNG;
import org.testng.internal.ClassHelper;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class TestNGRunner.
 */
public class TestNGRunner implements ITestRunner {
	/*
	 * S T A T I C
	 */
	static {
		if (null != TestingTools.getTestingTree(false).getClassLoaders())
			for (String pluginName : TestingTools.getTestingTree(false).getClassLoaders().getAdiPluginName()) {
				ClassLoader classLoader = AdichatzApplication.getPluginResources(pluginName).getGencodePath().getClassLoader();
				ClassHelper.addClassLoader(classLoader);
			}
	}

	/** The testing class loaders. */
	private static Map<AdiPluginResources, TestingClassLoader> testingClassLoaders = new HashMap<AdiPluginResources, TestingClassLoader>();

	/**
	 * Gets the class from uri.
	 *
	 * @param testSuiteURI
	 *            the test suite uri
	 * @return the class from uri
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static Class<?> getClassFromURI(String testSuiteURI) throws ClassNotFoundException {
		String[] keys = EngineTools.getContributionURIToStrings(testSuiteURI);
		AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(keys[0]);
		if (null == pluginResources) {
			throw new RuntimeException("Invalid plugin key '" + keys[0] + "' from URI '" + testSuiteURI + "'!");
		}
		TestingClassLoader testingClassLoader = testingClassLoaders.get(pluginResources);
		if (null == testingClassLoader) {
			testingClassLoader = new TestingClassLoader(pluginResources);
			testingClassLoaders.put(pluginResources, testingClassLoader);
		}
		return testingClassLoader.loadClass(keys[1]);
	}

	/*
	 * end S T A T I C
	 */

	/** The logger listener. */
	private E4LoggerListener loggerListener;

	/**
	 * Instantiates a new test ng runner.
	 */
	public TestNGRunner() {
		TestingTools.init();
		loggerListener = new E4LoggerListener() {
			public void beforeError(org.adichatz.engine.e4.resource.E4LoggerEvent event) {
				String message = event.message;
				if (null == event.throwable) {
					message = event.className.concat("[").concat(String.valueOf(event.lineNumber)).concat("] ").concat(message);
				}
				throw new AssertionError(message, event.throwable);
			};
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.ITestRunner#runTesting(org.adichatz.engine.xjc.TestingType, boolean)
	 */
	@Override
	public void runTesting(AdichatzTestingTree testingTree, boolean force) {
		((E4Logger) LogBroker.getLogger()).addListener(loggerListener);
		try {
			for (TestNodeType node : testingTree.getSuiteOrTestFile()) {
				if (node instanceof SuiteType && (force || ((SuiteType) node).isLaunchOnStartup())) {
					SuiteType suite = (SuiteType) node;
					runSuite(suite);
				} else if (node instanceof TestFileType && (force || ((TestFileType) node).isLaunchOnStartup())) {
					TestFileType testFile = (TestFileType) node;
					runFile(getFromTestingBundle("testing.file", TestingTools.getLabel(testFile)), testFile.getFileName(),
							testFile.isForceOpenManagerPart());
				}
			}
		} finally {
			((E4Logger) LogBroker.getLogger()).removeListener(loggerListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.ITestRunner#runSuite(org.adichatz.engine.xjc.SuiteType)
	 */
	@Override
	public void runSuite(SuiteType suite) {
		if (!suite.getTest().isEmpty()) {
			String[] testSuiteURIs = new String[suite.getTest().size()];
			int i = 0;
			for (TestType test : suite.getTest())
				if (!EngineTools.isEmpty(test.getTestURI()))
					testSuiteURIs[i++] = test.getTestURI();
				else
					logError(getFromTestingBundle("testing.error.null.testURI", test.getId()));
			runClasses(getFromTestingBundle("testing.suite", TestingTools.getLabel(suite)),
					TestingTools.getRegistry("runSuiteImage"), suite.isForceOpenManagerPart(), testSuiteURIs);
			doOpenManagerPart(suite.isForceOpenManagerPart());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.ITestRunner#runFile(java.lang.String, java.lang.String)
	 */
	@Override
	public void runFile(String label, String fileName, boolean forceOpenManagerPart) {
		((E4Logger) LogBroker.getLogger()).addListener(loggerListener);
		try {
			String pluginName = AdichatzApplication.getInstance().getApplicationPluginResources().getPluginName();
			ClassHelper.addClassLoader(AdichatzApplication.getPluginResources(pluginName).getGencodePath().getClassLoader());
			TestNG testng = new TestNG(false);
			Parser fileParser = new Parser(fileName);
			final List<XmlSuite> suites = fileParser.parseToList();
			testng.setXmlSuites(suites);
			testng.setVerbose(0);
			testng.addListener(new AdiResultListener(label, TestingTools.getRegistry("runFileImage")));
			testng.setDefaultTestName(label);
			testng.run();
			doOpenManagerPart(forceOpenManagerPart);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logError(e);
		} finally {
			((E4Logger) LogBroker.getLogger()).removeListener(loggerListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.ITestRunner#runClasses(java.lang.String, org.eclipse.swt.graphics.Image, java.lang.String[])
	 */
	@Override
	public void runClasses(String label, Image image, boolean forceOpenManagerPart, String... testSuiteURIs) {
		((E4Logger) LogBroker.getLogger()).addListener(loggerListener);
		try {
			TestNG testng = new TestNG(false);
			Class<?>[] annotedTestClasses = new Class<?>[testSuiteURIs.length];
			int index = 0;
			testingClassLoaders.clear();
			for (String testSuiteURI : testSuiteURIs) {
				annotedTestClasses[index++] = getClassFromURI(testSuiteURI);
			}
			testng.setTestClasses(annotedTestClasses);
			testng.setVerbose(0);
			AdiResultListener resultListener = new AdiResultListener(label, image);
			testng.addListener(resultListener);
			testng.setDefaultTestName(label);
			testng.setPreserveOrder(true);

			testng.run();
		} catch (ClassNotFoundException e) {
			logError(e);
		} finally {
			((E4Logger) LogBroker.getLogger()).removeListener(loggerListener);
			doOpenManagerPart(forceOpenManagerPart);
		}
	}

	/**
	 * Do open manager part.
	 *
	 * @return true, if successful
	 */
	private void doOpenManagerPart(boolean force) {
		boolean openManagerPart = force;
		if (!force)
			if (InstanceScope.INSTANCE.getNode(TestingTools.TESTING_BUNDLE).getBoolean(TestingTools.OPEN_MANAGER_PART, false))
				openManagerPart = true;
			else {
				if (null != AdiResultListener.CURRENT_LISTENER) {// No Error encountered
					ITestContext context = AdiResultListener.CURRENT_LISTENER.getContext();
					if (InstanceScope.INSTANCE.getNode(TestingTools.TESTING_BUNDLE)
							.getBoolean(TestingTools.OPEN_MANAGER_PART_ON_FAIL, true)
							&& 0 < (context.getFailedButWithinSuccessPercentageTests().size() + context.getFailedTests().size()))
						openManagerPart = true;
				}
			}
		if (openManagerPart)
			TestingTools.activeTestManagerPart();
	}
}
