package org.adichatz.engine.extra;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.simulation.AutomaticResponseManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ADialog extends Dialog {

	private List<Runnable> runnables = new ArrayList<>();

	protected int skipControl4AR;

	/** The scrolled form. */
	protected Composite skinnedParent;

	protected ADialog(Shell shell) {
		super(shell);
	}

	public void addPostReskinListener(Runnable runnable) {
		runnables.add(runnable);
	}

	@Override
	public void create() {
		super.create();
		skinnedParent.reskin(SWT.ALL);
		for (Runnable runnable : runnables)
			runnable.run();
	}

	@Override
	public int open() {
		Shell parentShell = getParentShell();
		Display display = null == parentShell ? getShell().getDisplay() : parentShell.getDisplay();
		AutomaticResponseManager automaticResponseManager = (AutomaticResponseManager) display
				.getData(EngineConstants.DIALOG_AUTOMATIC_RESPONSE);
		if (null == automaticResponseManager)
			return super.open();
		display.setData(EngineConstants.DIALOG_AUTOMATIC_RESPONSE, null);
		Shell shell = getShell();
		if (shell == null || shell.isDisposed()) {
			create();
		}

		constrainShellSize();

		// open the window
		getShell().open();
		if (0 < automaticResponseManager.getWaiting())
			try {
				Thread.sleep(automaticResponseManager.getWaiting());
			} catch (InterruptedException e) {
				logError(e);
			}
		return automaticResponseManager.computeResult(getShell(), skipControl4AR);
	}
}
