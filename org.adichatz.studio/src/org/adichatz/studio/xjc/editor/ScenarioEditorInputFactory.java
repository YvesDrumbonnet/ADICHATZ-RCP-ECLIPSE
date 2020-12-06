package org.adichatz.studio.xjc.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.internal.IWorkbenchConstants;

@SuppressWarnings("restriction")
public class ScenarioEditorInputFactory implements IElementFactory {

	@Override
	public IAdaptable createElement(IMemento memento) {
		// Get the file name.
		String fileName = memento.getString(IWorkbenchConstants.TAG_PATH);
		if (fileName == null) {
			return null;
		}

		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
		if (file != null) {
			return new ScenarioEditorInput(file);
		}
		return null;
	}
}
