package org.adichatz.jpa.editor;

import java.util.List;

import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorPath;
import org.eclipse.swt.graphics.Image;

public class EditorOutlineBindingService extends ABindingService {

	public EditorOutlineBindingService(IBoundedController boundedController) {
		super(boundedController);
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public List<ErrorPath> getErrorPaths() {
		return null;
	}

	@Override
	public void activateControllers(ErrorPath errorPath) {
	}

	@Override
	public String getTitle() {
		return null;
	}
}
