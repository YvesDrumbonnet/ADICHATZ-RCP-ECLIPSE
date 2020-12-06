package org.adichatz.studio.xjc.editor;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.part.FileEditorInput;

public class ScenarioFormEditorMatchingStrategy implements IEditorMatchingStrategy {

	@Override
	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		if (input instanceof FileEditorInput) {
			String name = editorRef.getName();
			return new ScenarioEditorInput(((FileEditorInput) input).getFile()).getName().equals(name);
		}
		return false;
	}

}
