package org.adichatz.engine.action;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

public class CopyClipboardAction extends Action {
	private Display display;

	private String message;

	public CopyClipboardAction(Display display, String message) {
		this.display = display;
		this.message = message;
		String text = getFromEngineBundle("copyTextToClipboard");
		this.setText(text);
		this.setToolTipText(text);
		this.setImageDescriptor(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_COPY"));
	}

	@Override
	public void run() {
		Clipboard clipboard = new Clipboard(display);
		Transfer[] t = new Transfer[] { TextTransfer.getInstance() };
		clipboard.setContents(new Object[] { message }, t);
		clipboard.dispose();
	}

}
