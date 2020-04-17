package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.GeneratorConstants;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;

public class CopyXjcElementAction extends AOneParentXjcAction {

	public CopyXjcElementAction() {
		String text = getFromStudioBundle("studio.editor.copy");
		setText(text);
		setToolTipText(text);
		setImageDescriptor(AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "IMG_COPY.png"));
	}

	@Override
	public void runAction() {
		if (!selection.isEmpty()) {
			if (XjcSelectionTransfer.getTransfer().isCutSelectionIsCopied()
					|| EngineTools.openConfirm(getFromStudioBundle("studio.editor.confirm.delete.elements"))) {
				Clipboard clipboard = new Clipboard(treeController.getControl().getDisplay());
				Object[] o = new Object[] { selection.getFirstElement() };
				XjcSelectionTransfer transfert = XjcSelectionTransfer.getTransfer(selection);
				clipboard.setContents(o, new Transfer[] { transfert });
				clipboard.dispose();
			}
		}
	}

}
