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
package org.adichatz.testing.tracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.Utilities;
import org.adichatz.testing.AdiAssert;
import org.adichatz.testing.TestingTools;
import org.adichatz.testing.tracking.TestEvent.EVENT_TYPE;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener2;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving adiResult events.
 * The class that is interested in processing a adiResult
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAdiResultListener<code> method. When
 * the adiResult event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AdiResultEvent
 */
public class AdiResultListener implements IResultListener2 {
	/*
	 * S T A T I C
	 */
	/** List of all run listeners for the session. */
	public static List<AdiResultListener> RESULT_LISTENERs = new ArrayList<AdiResultListener>();

	/** The current listener. */
	public static AdiResultListener CURRENT_LISTENER;

	public static String TEST_REFERENCE;

	/** The Excluded classes from stack trace. */
	private static Set<String> EXCLUDED_CLASSES = new HashSet<String>();
	static {
		EXCLUDED_CLASSES.add(Thread.class.getName());
		EXCLUDED_CLASSES.add(AdiResultListener.class.getName());
		EXCLUDED_CLASSES.add(AdiAssert.class.getName());
		EXCLUDED_CLASSES.add(Utilities.class.getName());
	}

	/*
	 * end S T A T I C
	 */

	/** The label. */
	private String label;

	/** The image. */
	private Image image;

	/** The test events. */
	private List<TestEvent> testEvents = new ArrayList<TestEvent>();

	/** The current on start. */
	private TestEvent currentOnStart;

	/** The current on test start. */
	private TestEvent currentOnTestStart;

	/** The context. */
	private ITestContext context;

	private long currentTimeMillis;

	/**
	 * Instantiates a new adi result listener.
	 *
	 * @param label the label
	 * @param image the image
	 */
	public AdiResultListener(String label, Image image) {
		this.label = label;
		this.image = image;
		RESULT_LISTENERs.add(this);
		CURRENT_LISTENER = this;
	}

	/**
	 * On test start.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestStart(ITestResult result) {
		TestEvent testEvent = new TestEvent(EVENT_TYPE.onTestStart, result);
		currentOnTestStart = testEvent;
		currentOnStart.getChildren().add(testEvent);
		currentTimeMillis = System.currentTimeMillis();
	}

	/**
	 * On test success.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		currentOnTestStart.getChildren().add(new TestEvent(EVENT_TYPE.onTestSuccess, result));
	}

	/**
	 * On test failure.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		currentOnTestStart.getChildren().add(new TestEvent(EVENT_TYPE.onTestFailure, result));
	}

	/**
	 * On test skipped.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		currentOnTestStart.getChildren().add(new TestEvent(EVENT_TYPE.onTestSkipped, result));
	}

	/**
	 * On test failed but within success percentage.
	 *
	 * @param result the result
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		currentOnTestStart.getChildren().add(new TestEvent(EVENT_TYPE.onTestFailedButWithinSuccessPercentage, result));
	}

	/**
	 * On start.
	 *
	 * @param context the context
	 */
	@Override
	public void onStart(ITestContext context) {
		Display.getCurrent().setData(EngineConstants.DIALOG_AUTOMATIC_RESPONSE, null);
		this.context = context;
		TestEvent testEvent = new TestEvent(EVENT_TYPE.onStart, context);
		currentOnStart = testEvent;
		testEvents.add(testEvent);
	}

	/**
	 * On finish.
	 *
	 * @param context the context
	 */
	@Override
	public void onFinish(ITestContext context) {
		testEvents.add(new TestEvent(EVENT_TYPE.onFinish, context));
	}

	/**
	 * On configuration success.
	 *
	 * @param result the result
	 */
	@Override
	public void onConfigurationSuccess(ITestResult result) {
	}

	/**
	 * On configuration failure.
	 *
	 * @param result the result
	 */
	@Override
	public void onConfigurationFailure(ITestResult result) {
		testEvents.add(new TestEvent(EVENT_TYPE.onConfigurationFailure, result));
	}

	/**
	 * On configuration skip.
	 *
	 * @param result the result
	 */
	@Override
	public void onConfigurationSkip(ITestResult result) {
	}

	/**
	 * Before configuration.
	 *
	 * @param result the result
	 */
	@Override
	public void beforeConfiguration(ITestResult result) {
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Gets the test events.
	 *
	 * @return the test events
	 */
	public List<TestEvent> getTestEvents() {
		return testEvents;
	}

	/**
	 * Adds the event.
	 *
	 * @param testEvent the test event
	 */
	public void addEvent(TestEvent testEvent) {
		currentOnTestStart.getChildren().add(testEvent);
	}

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public ITestContext getContext() {
		return context;
	}

	public long getStepDuration() {
		long current = System.currentTimeMillis();
		long duration = current - currentTimeMillis;
		currentTimeMillis = current;
		return duration;

	}

	public String storeTestReference() {
		EXCLUDED_CLASSES = new HashSet<String>();
		EXCLUDED_CLASSES.add(Thread.class.getName());
		EXCLUDED_CLASSES.add(TestingTools.class.getName());
		EXCLUDED_CLASSES.add(AdiResultListener.class.getName());
		EXCLUDED_CLASSES.add(AdiAssert.class.getName());
		EXCLUDED_CLASSES.add(Utilities.class.getName());

		StackTraceElement stackTraceElement = Utilities.getCurrentStackTraceElement(EXCLUDED_CLASSES);

		String className = stackTraceElement.getClassName();
		className = className.substring(className.lastIndexOf('.') + 1);
		StringBuffer refrenceSB = new StringBuffer(className).append('.').append(stackTraceElement.getMethodName()).append(':')
				.append(stackTraceElement.getLineNumber());
		return refrenceSB.toString();
	}
}
