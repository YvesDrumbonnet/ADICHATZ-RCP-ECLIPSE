package org.adichatz.testing.tracking;

import static org.adichatz.testing.TestingTools.getFromTestingBundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.controller.AController;
import org.adichatz.testing.TestingTools;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class TestEvent {

	public enum EVENT_TYPE {
		onTestStart, onTestSuccess, onTestFailure, onTestSkipped, onTestFailedButWithinSuccessPercentage, onStart, onFinish, onConfigurationSuccess, onConfigurationFailure, onConfigurationSkip, beforeConfiguration, onInfo
	};

	private ITestResult testResult;

	private ITestContext testContext;

	private EVENT_TYPE eventType;

	private String label;

	private List<TestEvent> children;

	private String testReference = null;

	private TestEvent(EVENT_TYPE eventType) {
		this.eventType = eventType;
		testReference = AdiResultListener.TEST_REFERENCE;
		AdiResultListener.TEST_REFERENCE = null;
	}

	public TestEvent(EVENT_TYPE eventType, String label) {
		this(eventType);
		this.label = label;
	}

	public TestEvent(EVENT_TYPE eventType, ITestResult testResult) {
		this(eventType);
		this.testResult = testResult;
		if (eventType == EVENT_TYPE.onTestStart)
			children = new ArrayList<TestEvent>();
	}

	public TestEvent(EVENT_TYPE eventType, ITestContext testContext) {
		this(eventType);
		this.testContext = testContext;
		if (eventType == EVENT_TYPE.onStart)
			children = new ArrayList<TestEvent>();
	}

	public EVENT_TYPE getEventType() {
		return eventType;
	}

	public ITestResult getTestResult() {
		return testResult;
	}

	public ITestContext getTestContext() {
		return testContext;
	}

	public String getLabel() {
		return label;
	}

	public List<TestEvent> getChildren() {
		return children;
	}

	public Image getImage() {
		switch (eventType) {
		case onTestStart:
			return TestingTools.getRegistry("onTestStartImage");
		case onTestSuccess:
			return TestingTools.getRegistry("onTestSuccessImage");
		case onTestFailure:
		case onConfigurationFailure:
			return TestingTools.getRegistry("failureImage");
		case onTestSkipped:
			return TestingTools.getRegistry("onTestSkipedImage");
		case onTestFailedButWithinSuccessPercentage:
			return TestingTools.getRegistry("onTestFailSuccessImage");
		case onStart:
			return TestingTools.getRegistry("onStartImage");
		case onFinish:
			return TestingTools.getRegistry("onFinishImage");
		case onInfo:
			return TestingTools.getRegistry("infoImage");
		default:
			return null;
		}
	}

	public Color getForeGround() {
		switch (eventType) {
		case onTestSuccess:
			return TestingTools.COLOR_SUCCESS;
		case onConfigurationFailure:
		case onTestFailure:
			return AController.ERROR_COLOR;
		case onTestFailedButWithinSuccessPercentage:
			return TestingTools.COLOR_PARTIAL;
		case onInfo:
			return TestingTools.COLOR_INFO;
		default:
			return null;
		}
	}

	public String getText() {
		switch (eventType) {
		case onTestStart:
		case onTestSuccess:
		case onTestSkipped:
			return getResultLabel().toString();
		case onTestFailure:
		case onTestFailedButWithinSuccessPercentage:
			String resultText = getResultLabel().toString();
			Throwable throwable = testResult.getThrowable();
			return null == throwable ? resultText : resultText.concat(" - ").concat(throwable.toString());
		case onStart:
		case onFinish:
			return eventType.name().concat(": ").concat(getContextLabel(EVENT_TYPE.onStart == eventType));
		case onInfo:
			return label;
		default:
			return eventType.name();
		}
	}

	private String getResultLabel() {
		StringBuffer textSB = new StringBuffer(eventType.name()).append(" [");
		if (null == testReference)
			textSB.append(testResult.getInstance().getClass().getSimpleName()).append(".")
					.append(testResult.getMethod().getMethodName());
		else
			textSB.append(testReference);
		String status = getFromTestingBundle("testing.status.".concat(String.valueOf(testResult.getStatus())));
		textSB.append("] ").append(getFromTestingBundle("testing.status.label", status));
		return textSB.toString();
	}

	private String getContextLabel(boolean start) {
		String suffix;
		String date;
		if (start) {
			date = TestingTools.DATE_FORMAT.format(testContext.getStartDate());
			suffix = "start";
		} else {
			date = TestingTools.DATE_FORMAT.format(testContext.getEndDate());
			suffix = "end";
		}
		long duration = testContext.getEndDate().getTime() - testContext.getStartDate().getTime();
		int testNumber = testContext.getPassedTests().size();
		int failNumber = testContext.getFailedTests().size();
		int skipNumber = testContext.getSkippedTests().size();
		return getFromTestingBundle("testing.context.label.".concat(suffix), date, duration, testNumber, failNumber, skipNumber);
	}
}
