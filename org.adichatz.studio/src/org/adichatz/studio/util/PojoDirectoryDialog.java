package org.adichatz.studio.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

public class PojoDirectoryDialog extends DirectoryDialog {

	public PojoDirectoryDialog(Shell parent) {
		super(parent, SWT.OPEN);
	}

	@Override
	public String open() {
		String result = super.open();
		return result;
	}

}
