package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;

import javax.xml.bind.JAXBException;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.controller.collection.FormPageController;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.studio.xjc.editor.AStudioFormEditor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public abstract class AFileAction extends AAction {
	protected String pluginName;

	protected String pagePartName;

	protected String treeId;

	protected String subPackage;

	protected FormPageController formPageController;

	protected IProject project;

	@Override
	public void runAction() {
		StringBuffer separatorSB = new StringBuffer("\t\t");
		for (int i = 0; i < getText().length() + 8; i++)
			separatorSB.append('*');

		System.out.println("\n\n");
		logInfo(separatorSB.toString());
		logInfo("\t\t*   " + getText() + "   *");
		logInfo(separatorSB.toString());
	}

	@Override
	public void postCreate() {
		try {
			ITreeWrapper treeWrapper = ((AStudioFormEditor) actionController.getRootCore().getController()).getTreeWrapper(false,
					false);
			pluginName = treeWrapper.getPluginName();
			pagePartName = treeWrapper.getTreeId();
			treeId = pagePartName.endsWith("GENERATED") ? pagePartName.substring(0, pagePartName.length() - 9) : pagePartName;
			subPackage = treeWrapper.getSubPackage();

			setEnabled(ResourcesPlugin.getWorkspace().getRoot().getProject(pluginName).exists());
		} catch (CoreException | JAXBException e) {
			logError(e);
		}
	}
}
