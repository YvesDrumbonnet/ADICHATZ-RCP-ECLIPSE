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
import static org.adichatz.engine.common.LogBroker.logWarning;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.ImageManager;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.e4.resource.EngineE4Util;
import org.adichatz.testing.tracking.AdiResultListener;
import org.adichatz.testing.tracking.TestEvent;
import org.adichatz.testing.tracking.TestingManagerPart;
import org.adichatz.testing.xjc.AdichatzTestingTree;
import org.adichatz.testing.xjc.ClassLoadersType;
import org.adichatz.testing.xjc.ObjectFactory;
import org.adichatz.testing.xjc.TestFileType;
import org.adichatz.testing.xjc.TestNodeType;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class TestingTools {
	public static String TESTING_MANAGER_PART_ID = "adichatz.testing.manager.part";

	private static AdichatzTestingTree testingTree;

	/** The Constant TESTING_BUNDLE. */
	public static final String TESTING_BUNDLE = "org.adichatz.testing";

	private static final String TESTING_SELECTOR = "#adichatz-testing";

	public static ImageManager imageManager;

	private static Map<String, Image> registry = new HashMap<String, Image>(10);

	public static Color COLOR_INFO;

	public static Color COLOR_PARTIAL;

	public static Color COLOR_SUCCESS;

	public static DateFormat DATE_FORMAT;

	public static boolean FORCE_OPEN_MANAGER_PART;

	public static String TEST_MANAGER_CONTRIBUTION_URI = "bundleclass://".concat(TESTING_BUNDLE).concat("/")
			.concat(org.adichatz.testing.tracking.TestingManagerPart.class.getName());

	public static String OPEN_MANAGER_PART = "prefs.open.manager.part";

	public static String OPEN_MANAGER_PART_ON_FAIL = "prefs.open.manager.part.on.fail";

	public static String DATE_PATTERN = "prefs.date.pattern";

	public static String NAVIGATOR_FILTERS = "prefs.date.navigator_filters";

	private static AdiResourceBundle testingRBM = AdichatzApplication.getPluginResources(TESTING_BUNDLE).getResourceBundleManager()
			.getResourceBundle("adichatzTesting");

	public static ITestRunner APPLICATION_TEST_RUNNER;

	public static void init() {
		if (null == imageManager) {
			imageManager = AdichatzApplication.getPluginResources(TESTING_BUNDLE).getImageManager();
			registry.put("suiteImage", imageManager.getImageDescriptor("IMG_SUITE.png").createImage());
			registry.put("runFileImage", imageManager.getImageDescriptor("IMG_RUN_FILE.png").createImage());
			registry.put("runSuiteImage", imageManager.getImageDescriptor("IMG_RUN_SUITE.png").createImage());
			registry.put("runTestImage", imageManager.getImageDescriptor("IMG_RUN_TEST.png").createImage());
			registry.put("onStartImage", imageManager.getImageDescriptor("IMG_ON_START.png").createImage());
			registry.put("onFinishImage", imageManager.getImageDescriptor("IMG_ON_FINISH.png").createImage());
			registry.put("onTestStartImage", imageManager.getImageDescriptor("IMG_START.png").createImage());
			registry.put("onTestSuccessImage", imageManager.getImageDescriptor("IMG_SUCCESS.png").createImage());
			registry.put("failureImage", imageManager.getImageDescriptor("IMG_FAILURE.png").createImage());
			registry.put("infoImage", imageManager.getImageDescriptor("IMG_INFO.png").createImage());
			registry.put("onTestSkipedImage", imageManager.getImageDescriptor("IMG_SKIP.png").createImage());
			registry.put("onTestFailSuccessImage", imageManager.getImageDescriptor("IMG_SUCC_FAIL.png").createImage());
			Display display = Display.getCurrent();
			if (null != AReskinManager.getInstance()) {
				COLOR_INFO = AReskinManager.getInstance().getColor(TESTING_SELECTOR, "testing-info-color", null, null);
				COLOR_PARTIAL = AReskinManager.getInstance().getColor(TESTING_SELECTOR, "testing-partial-color", null, null);
				COLOR_SUCCESS = AReskinManager.getInstance().getColor(TESTING_SELECTOR, "testing-success-color", null, null);
			} else {
				COLOR_INFO = display.getSystemColor(SWT.COLOR_BLUE);
				COLOR_PARTIAL = display.getSystemColor(SWT.COLOR_CYAN);
				COLOR_SUCCESS = display.getSystemColor(SWT.COLOR_DARK_BLUE);
			}
			String pattern = new SimpleDateFormat().toPattern();
			if (pattern.endsWith("mm"))
				pattern = pattern.concat(":ss");
			if (pattern.endsWith("ss"))
				pattern = pattern.concat(".SSS");
			DATE_FORMAT = new SimpleDateFormat(pattern);
		}
	}

	public static Image getRegistry(String imageKey) {
		return registry.get(imageKey);
	}

	/**
	 * Gets the value from JPA bundle.
	 * 
	 * @param key
	 *            the key
	 * @param variables
	 *            the variables
	 * @return the from bundle
	 */
	public static String getFromTestingBundle(String key, Object... variables) {
		return testingRBM.getValueFromBundle(key, variables);
	}

	public static void activeTestManagerPart() {
		MPartStack editorPartStack = E4AdichatzApplication.getInstance().getEditorPartStack();
		MPart inputPart = null;
		for (MStackElement element : editorPartStack.getChildren()) {
			if (element instanceof MPart
					&& TestingTools.TEST_MANAGER_CONTRIBUTION_URI.equals(((MPart) element).getContributionURI())) {
				inputPart = (MPart) element;
				break;
			}
		}
		if (null == inputPart) {
			inputPart = MBasicFactory.INSTANCE.createPart();
			inputPart.setContributionURI(TestingTools.TEST_MANAGER_CONTRIBUTION_URI);
			inputPart.setElementId(TestingTools.TESTING_MANAGER_PART_ID);
			inputPart.setLabel(getFromTestingBundle("testing.result.manager"));
			inputPart.setTooltip(getFromTestingBundle("testing.result.manager.tooltip"));
			inputPart.setIconURI("platform:/plugin/".concat(TESTING_BUNDLE).concat("/resources/icons/IMG_ADI_TEST.png"));
			inputPart.getPersistedState().put(IWorkbench.PERSIST_STATE, "false");
			inputPart.setCloseable(true);
			inputPart.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
			inputPart.getTags().add(IPresentationEngine.NO_MOVE);
			inputPart.getTags().add(IPresentationEngine.NO_RESTORE);
			editorPartStack.getChildren().add(inputPart);
		}
		try {
			E4AdichatzApplication.getInstance().getContext().get(EPartService.class).showPart(inputPart, PartState.ACTIVATE);
			((TestingManagerPart) inputPart.getObject()).refresh();
			inputPart.getTransientData().put(EngineE4Util.NO_SAVE, true);
		} catch (IllegalStateException e) {
			logWarning(e.getLocalizedMessage());
		}
	}

	public static void testMessage(String messageParam, TestEvent.EVENT_TYPE type) {
		AdiResultListener resultListener = AdiResultListener.CURRENT_LISTENER;
		StringBuffer messageSB = new StringBuffer("[").append(resultListener.storeTestReference()).append("] ");
		messageSB.append(getFromTestingBundle("testing.context.gap", messageParam, resultListener.getStepDuration()));
		resultListener.addEvent(new TestEvent(type, messageSB.toString()));

	}

	public static void testInfo(String messageParam) {
		testMessage(messageParam, TestEvent.EVENT_TYPE.onInfo);
	}

	public static String getLabel(TestNodeType testNode) {
		return null == testNode.getLabel() ? testNode.getId() : testNode.getLabel();
	}

	public static String getLabel(TestFileType testFile) {
		final String testFileName = testFile.getFileName();
		int index = Math.max(testFileName.lastIndexOf('/'), testFileName.lastIndexOf('\\')) + 1;
		return null == testFile.getLabel() ? testFileName.substring(index) : testFile.getLabel();
	}

	public static AdichatzTestingTree getTestingTree(boolean force) {
		if (force || null == testingTree) {
			URL testingUrl = Platform.getBundle(AdichatzApplication.getInstance().getApplicationPluginResources().getPluginName())
					.getEntry(EngineConstants.XML_FILES_PATH.concat("/").concat("AdichatzTesting.xml"));
			if (null != testingUrl) {
				try {
					InputStream inputStream = testingUrl.openStream();
					JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
					Unmarshaller unmarshaller = jc.createUnmarshaller();
					testingTree = (AdichatzTestingTree) unmarshaller.unmarshal(inputStream);
					inputStream.close();
				} catch (IOException | JAXBException e) {
					logError(e);
				}
			}
			if (null == testingTree)
				testingTree = new AdichatzTestingTree();
		}
		if (null == testingTree.getClassLoaders())
			testingTree.setClassLoaders(new ClassLoadersType());
		return testingTree;

	}
}
