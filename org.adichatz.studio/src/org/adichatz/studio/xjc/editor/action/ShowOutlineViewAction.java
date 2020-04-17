package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.generator.common.GeneratorConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowOutlineViewAction.
 */
public class ShowOutlineViewAction extends AAction {

	/**
	 * Instantiates a new show outline view action.
	 */
	public ShowOutlineViewAction() {
		String text = getFromStudioBundle("studio.editor.show.outLine");
		setText(text);
		setToolTipText(text);
		setImageDescriptor(
				AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.STUDIO_BUNDLE, "IMG_SHOW_OUTLINE.png"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.action.AAction#runAction()
	 */
	@Override
	public void runAction() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(EngineConstants.CONTENT_OUT_LINE);
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
		return !activePage.isPartVisible(activePage.findView(EngineConstants.CONTENT_OUT_LINE));
	}
}
