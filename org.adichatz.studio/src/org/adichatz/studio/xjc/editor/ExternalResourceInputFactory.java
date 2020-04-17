package org.adichatz.studio.xjc.editor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

public class ExternalResourceInputFactory implements IElementFactory {

	public static String PROJECT_NAME = "projectName";

	@Override
	public IAdaptable createElement(IMemento memento) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(memento.getString(PROJECT_NAME));
		return new ExternalResourcesEditorInput(project);
	}
}
