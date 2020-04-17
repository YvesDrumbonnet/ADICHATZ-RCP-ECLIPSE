package org.adichatz.engine.validation;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.field.EditableFormTextController;
import org.adichatz.engine.widgets.EditableFormText;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.ui.internal.forms.widgets.FormTextModel;

@SuppressWarnings("restriction")
public class FormTextParseValidator extends AValidator {
	FormTextModel formTextModel = new FormTextModel();

	public FormTextParseValidator(EditableFormTextController formTextController) {
		super(formTextController, "error.parsing.text");
	}

	@Override
	public int validate() {
		EditableFormText formText = ((EditableFormTextController) triggeringController).getControl();

		if (!EngineTools.isEmpty(formText.getText())) {
			try {
				if (formText.isParseTags())
					formTextModel.parseTaggedText(formText.getText(), formText.isExpandURLs());
				else
					formTextModel.parseRegularText(formText.getText(), formText.isExpandURLs());
				return setLevel(IMessageProvider.NONE, null);
			} catch (IllegalArgumentException e) {
				return setLevel(IMessageProvider.ERROR, EngineTools.getFromEngineBundle("error.parsing.text"));
			}
		} else
			return setLevel(IMessageProvider.NONE, null);
	}

	public FormTextModel getFormTextModel() {
		return formTextModel;
	};
}
