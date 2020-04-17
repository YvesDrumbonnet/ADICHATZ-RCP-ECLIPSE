package org.adichatz.testing.tracking;

import static org.adichatz.testing.TestingTools.getFromTestingBundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.extra.ABindingOutlinePage;
import org.adichatz.engine.extra.IOutlineContainerPart;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.testing.TestingTools;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.testng.ITestContext;
import org.testng.ITestResult;

import net.miginfocom.swt.MigLayout;

public class TestOutlinePage extends ABindingOutlinePage {
	private ScrolledForm scrolledForm;

	private TestingManagerPart testManagerPart;

	private TreeViewer testTV;

	private Composite detailComposite;

	private AdiFormToolkit toolkit;

	private InfoPart infoPart;

	private ResultPart resultPart;

	private ContextPart contextPart;

	private PGroup detailPgroup;

	public TestOutlinePage(TestingManagerPart testManagerPart, TreeViewer testTV) {
		this.testManagerPart = testManagerPart;
		this.testTV = testTV;
	}

	@Override
	public void createControl(Composite parent) {
		toolkit = AdichatzApplication.getInstance().getFormToolkit();
		scrolledForm = toolkit.createScrolledForm(parent);
		scrolledForm.getBody().setLayout(new MigLayout("wrap 1, ins 10", "grow,fill", "grow,fill"));

		detailPgroup = AdichatzApplication.getInstance().getFormToolkit().createPGroup(scrolledForm.getBody(), SWT.BORDER);

		detailPgroup.setLayout(new FillLayout());
		detailPgroup.addExpandListener(new ExpandListener() {
			@Override
			public void itemExpanded(ExpandEvent e) {
				detailPgroup.layout();
			}

			@Override
			public void itemCollapsed(ExpandEvent e) {
			}
		});
		detailComposite = toolkit.createComposite(detailPgroup);
		detailComposite.setLayout(new MigLayout("wrap 1, hidemode 3", "fill,grow", "fill,grow"));
		infoPart = new InfoPart();
		resultPart = new ResultPart();
		contextPart = new ContextPart();
	}

	@Override
	public void refresh() {
		if (testTV.getSelection().isEmpty() || 1 < ((StructuredSelection) testTV.getSelection()).size()) {
			detailComposite.setVisible(false);
		} else {
			OutlinePart.getInstance().showPage(this);
			detailComposite.setVisible(true);
			Object selectedObject = ((StructuredSelection) testTV.getSelection()).getFirstElement();
			detailPgroup.setImage(((ColumnLabelProvider) testTV.getLabelProvider()).getImage(selectedObject));
			if (selectedObject instanceof AdiResultListener) {
				detailPgroup.setText(((ColumnLabelProvider) testTV.getLabelProvider()).getText(selectedObject));
				for (TestEvent testEvent : ((AdiResultListener) selectedObject).getTestEvents()) {
					if (TestEvent.EVENT_TYPE.onStart == testEvent.getEventType()) {
						contextPart.show(testEvent.getTestContext());
						return;
					}
				}
			} else if (selectedObject instanceof TestEvent) {
				TestEvent testEvent = (TestEvent) selectedObject;
				detailPgroup.setText(testEvent.getEventType().name());
				if (null != testEvent.getTestContext()) {
					contextPart.show(testEvent.getTestContext());
				} else if (null != testEvent.getTestResult()) {
					resultPart.show(testEvent);
				} else if (null != testEvent.getLabel()) {
					infoPart.show(testEvent.getLabel());
				} else {
					infoPart.hide();
					resultPart.hide();
					contextPart.hide();
				}
			}
		}
	}

	@Override
	public Composite getComposite() {
		return scrolledForm;
	}

	@Override
	public IOutlineContainerPart getLinkedPart() {
		return testManagerPart;
	}

	private class ResultPart {

		private Composite resultComposite;

		private Composite descComposite;

		private Text instanceNameText;

		private Text methodNameText;

		private Text statusText;

		private Label throwableLabel;

		private Text throwableText;

		private Text failLocationText;

		private Text failMessageText;

		private List<Control> controls = new ArrayList<Control>(8);

		protected void show(TestEvent testEvent) {
			ITestResult testResult = testEvent.getTestResult();
			boolean displayThrowable = (null != testResult.getThrowable()
					&& (testEvent.getEventType() == TestEvent.EVENT_TYPE.onTestFailure
							|| testEvent.getEventType() == TestEvent.EVENT_TYPE.onTestFailedButWithinSuccessPercentage));
			// boolean displayThrowableMessage = displayThrowable && !(testResult.getThrowable() instanceof AssertionError);
			boolean displayThrowableMessage = displayThrowable;
			if (displayThrowableMessage) {
				if (testResult.getThrowable() instanceof AssertionError) {
					displayThrowableMessage = false;
					for (StackTraceElement stackElement : testResult.getThrowable().getStackTrace())
						if (stackElement.getClassName().equals(LogBroker.class.getName())) {
							displayThrowableMessage = true;
							break;
						}
				}
			}

			if (null == resultComposite) {
				resultComposite = toolkit.createComposite(detailComposite);
				resultComposite.setLayout(new MigLayout("wrap 1, hidemode 3, ins 0", "[fill,grow]", "[][][grow,fill]"));
				descComposite = toolkit.createComposite(resultComposite);
				descComposite.setLayout(new MigLayout("wrap 2, hidemode 3, ins 0", "[fill,align right]10[fill,grow]"));

				instanceNameText = createField(getFromTestingBundle("testing.instance.name"), SWT.SINGLE);
				methodNameText = createField(getFromTestingBundle("testing.method.name"), SWT.SINGLE);
				statusText = createField(getFromTestingBundle("testing.status"), SWT.SINGLE);
				failLocationText = createField(getFromTestingBundle("testing.failure.location"), SWT.SINGLE);
				failMessageText = createField(getFromTestingBundle("testing.failure.Message"), SWT.SINGLE);
				failMessageText.setForeground(AController.ERROR_COLOR);

				throwableLabel = toolkit.createLabel(resultComposite, getFromTestingBundle("testing.throwable.stack.trace"));
				throwableText = toolkit.createText(resultComposite, "", SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
				throwableText.setLayoutData("push, grow, h 0:64:n, w 0:64:n");
				throwableText.setEditable(false);
				throwableText.setForeground(AController.ERROR_COLOR);
			} else if (!resultComposite.isVisible())
				resultComposite.setVisible(true);
			instanceNameText.setText(testResult.getInstanceName());
			methodNameText.setText(testResult.getMethod().getMethodName());
			statusText.setText(getFromTestingBundle("testing.status.".concat(String.valueOf(testResult.getStatus()))));
			if (displayThrowable) {
				StackTraceElement[] stackTraceElements = testResult.getThrowable().getStackTrace();
				int line = -1;
				for (StackTraceElement stackTraceElement : stackTraceElements)
					if (stackTraceElement.getClassName().equals(testResult.getInstanceName())
							&& stackTraceElement.getMethodName().equals(testResult.getMethod().getMethodName())) {
						line = stackTraceElement.getLineNumber();
						break;
					}
				controls.get(6).setVisible(-1 != line);
				failLocationText.setVisible(-1 != line);
				failLocationText.setText(testResult.getInstance().getClass().getSimpleName().concat("#")
						.concat(testResult.getMethod().getMethodName()).concat(": ").concat(String.valueOf(line)));
				if (displayThrowableMessage) {
					controls.get(8).setVisible(false);
					failMessageText.setVisible(false);
					throwableLabel.setVisible(true);
					throwableText.setVisible(true);
					throwableText.setText(EngineTools.getErrorString(testResult.getThrowable()));
				} else {
					controls.get(8).setVisible(true);
					failMessageText.setVisible(true);
					failMessageText.setText(testResult.getThrowable().getMessage());
					throwableLabel.setVisible(false);
					throwableText.setVisible(false);
				}
			} else if (null != throwableLabel) {
				controls.get(8).setVisible(false);
				failMessageText.setVisible(false);
				controls.get(6).setVisible(false);
				failLocationText.setVisible(false);
				throwableLabel.setVisible(false);
				throwableText.setVisible(false);
			}
			String layoutData = displayThrowableMessage ? null : "gaptop 10";
			for (Control control : controls)
				control.setLayoutData(layoutData);
			if (displayThrowable)
				statusText.setForeground(AController.ERROR_COLOR);
			else if (TestEvent.EVENT_TYPE.onTestSuccess == testEvent.getEventType())
				statusText.setForeground(TestingTools.COLOR_SUCCESS);
			else
				statusText.setForeground(instanceNameText.getForeground());

			infoPart.hide();
			contextPart.hide();
			resultComposite.layout();
			descComposite.layout();
			detailComposite.layout();
			detailPgroup.layout();
			scrolledForm.reflow(true);
		}

		private Text createField(String textLabel, int style) {
			Label label = toolkit.createLabel(descComposite, textLabel);
			label.setLayoutData("gaptop 10");
			controls.add(label);
			Text text = toolkit.createText(descComposite, "", style);
			text.setEditable(false);
			controls.add(text);
			return text;

		}

		private void hide() {
			if (null != resultComposite)
				resultComposite.setVisible(false);
		}
	}

	private class ContextPart {
		private Composite contextComposite;

		private Text startDateText;

		private Text endDateText;

		private Text durationText;

		private Text passedText;

		private Text failedText;

		private Text skippedText;

		protected void show(ITestContext testContext) {
			if (null == contextComposite) {
				contextComposite = toolkit.createComposite(detailComposite);
				contextComposite.setLayout(new MigLayout("wrap 2, hidemode 3, ins 0", "[align right]10[fill,grow]"));

				startDateText = createField(getFromTestingBundle("testing.startDate"), SWT.SINGLE);
				endDateText = createField(getFromTestingBundle("testing.endDate"), SWT.SINGLE);
				durationText = createField(getFromTestingBundle("testing.duration"), SWT.RIGHT);
				passedText = createField(getFromTestingBundle("testing.passedTests"), SWT.RIGHT);
				failedText = createField(getFromTestingBundle("testing.failedTests"), SWT.RIGHT);
				skippedText = createField(getFromTestingBundle("testing.skippedTests"), SWT.RIGHT);
			} else if (!contextComposite.isVisible())
				contextComposite.setVisible(true);

			startDateText.setText(TestingTools.DATE_FORMAT.format(testContext.getStartDate()));
			endDateText.setText(TestingTools.DATE_FORMAT.format(testContext.getEndDate()));
			durationText.setText(String.valueOf(testContext.getEndDate().getTime() - testContext.getStartDate().getTime()));

			passedText.setText(String.valueOf(testContext.getPassedTests().size()));
			failedText.setText(String.valueOf(testContext.getFailedTests().size()));
			failedText
					.setForeground(0 == testContext.getFailedTests().size() ? passedText.getForeground() : AController.ERROR_COLOR);
			skippedText.setText(String.valueOf(testContext.getSkippedTests().size()));
			infoPart.hide();
			resultPart.hide();
			contextComposite.layout();
			detailComposite.layout();
			detailPgroup.layout();
			scrolledForm.reflow(true);
		}

		private Text createField(String textLabel, int style) {
			Label label = toolkit.createLabel(contextComposite, textLabel);
			label.setLayoutData("gaptop 10");
			Text text = toolkit.createText(contextComposite, "", style);
			text.setEditable(false);
			text.setLayoutData("wmax 200, gaptop 10");
			return text;

		}

		private void hide() {
			if (null != contextComposite)
				contextComposite.setVisible(false);
		}
	}

	private class InfoPart {
		private Composite infoComposite;

		private Text infoText;

		protected void show(String label) {
			if (null == infoComposite) {
				infoComposite = toolkit.createComposite(detailComposite);
				infoComposite.setLayout(new MigLayout("wrap 1, ins 0", "fill,grow", "fill,grow"));
				infoText = toolkit.createText(infoComposite, "", SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL);
				infoText.setLayoutData("push, grow, h 0:64:n, w 0:64:n");
				infoText.setEditable(false);
				infoText.setForeground(TestingTools.COLOR_INFO);
			} else if (!infoComposite.isVisible())
				infoComposite.setVisible(true);

			infoText.setText(label);
			contextPart.hide();
			resultPart.hide();
			infoComposite.layout();
			detailComposite.layout();
			detailPgroup.layout();
		}

		private void hide() {
			if (null != infoComposite)
				infoComposite.setVisible(false);
		}
	}
}
