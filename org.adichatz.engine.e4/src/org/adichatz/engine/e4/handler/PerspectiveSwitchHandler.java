package org.adichatz.engine.e4.handler;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import javax.inject.Inject;
import javax.inject.Named;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

public class PerspectiveSwitchHandler {

	private MPerspective currentPerspective;

	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	protected Shell shell;

	@Execute
	public void execute(final EPartService partService) {
		new TrayDialog(shell) {
			@Override
			public void create() {
				setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
				super.create();
				EngineTools.centerWindow(getShell());
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected Control createDialogArea(Composite parent) {

				MPerspectiveStack perspectiveStack = AdichatzApplication.getInstance().getContextValue(MPerspectiveStack.class);
				currentPerspective = perspectiveStack.getSelectedElement();
				getShell().setImage(
						AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_E4_BUNDLE, "IMG_PERSPECTIVE.png"));
				getShell().setText(getFromEngineE4Bundle("perspective.switcher"));
				initializeBounds();
				AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
				if (null == toolkit)
					toolkit = new AdiFormToolkit(Display.getCurrent());
				parent.setBackground(toolkit.getColors().getBackground());
				ScrolledForm scrolledForm = toolkit.createScrolledForm(parent);
				scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));
				Composite body = scrolledForm.getBody();
				body.setLayout(new MigLayout("wrap 1, ins 10", "[grow,fill]"));

				for (final MPerspective perspective : perspectiveStack.getChildren()) {
					ImageHyperlink hyperlink = toolkit.createImageHyperlink(body, SWT.NONE);
					hyperlink.setText(perspective.getLabel());
					hyperlink.setImage(EngineTools.getImage(perspective.getIconURI()));
					hyperlink.setLayoutData("wmin 300");
					if (perspective.equals(currentPerspective)) {
						hyperlink.setEnabled(false);
					} else {
						hyperlink.addHyperlinkListener(new HyperlinkAdapter() {
							@Override
							public void linkActivated(HyperlinkEvent e) {
								switchPerspective(partService, perspective);
								close();
							}
						});
					}
				}

				scrolledForm.reflow(true);
				applyDialogFont(scrolledForm.getBody());
				parent.layout();
				return scrolledForm;
			}

			protected Control createButtonBar(Composite parent) {
				Control control = super.createButtonBar(parent);
				getButton(IDialogConstants.OK_ID).setVisible(false);
				getDialogArea().getParent().layout();
				return control;
			};

		}.open();
	}

	public void switchPerspective(EPartService partService, MPerspective newPerspective) {
		MPerspectiveStack perspectiveStack = AdichatzApplication.getInstance().getContextValue(MPerspectiveStack.class);
		for (MPerspective perspective : perspectiveStack.getChildren()) {
			if (perspective.getElementId().equals(newPerspective.getElementId())) {
				partService.switchPerspective(perspective);
				AdichatzApplication.fireListener(new ApplicationEvent(ApplicationEvent.EVENT_TYPE.PERSPECTIVE_CHANGE));
				break;
			}
		}
	}
}
