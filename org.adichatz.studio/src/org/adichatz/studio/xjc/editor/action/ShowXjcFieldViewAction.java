package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.studio.xjc.editor.XjcFieldViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowXjcFieldViewAction.
 */
public class ShowXjcFieldViewAction extends AAction {

	/**
	 * Instantiates a new show xjc field view action.
	 */
	public ShowXjcFieldViewAction() {
		String text = getFromStudioBundle("studio.editor.show.fieldView");
		setText(text);
		setToolTipText(text);
		setImageDescriptor(AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE,
				"IMG_SHOW_XJC_FIELD.png"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.action.AAction#runAction()
	 */
	@Override
	public void runAction() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(XjcFieldViewPart.ID);
		} catch (PartInitException e) {
			logError(e);
		}
	}

	/**
	 * Checks if is hidden.
	 * 
	 * @return true, if is hidden
	 */
	public static boolean isHidden() {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		return !activePage.isPartVisible(activePage.findView(XjcFieldViewPart.ID));
	}
}
