package org.adichatz.studio.xjc.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.wst.sse.ui.internal.reconcile.validator.ISourceValidator;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;

public class AdiXmlFileValidator implements IValidator, ISourceValidator {

	@Override
	public void cleanup(IReporter arg0) {
		System.out.println("AVISATZ: org.adichatz.studio.xjc.editor.AdiXmlFileValidator.cleanup(IReporter)");
	}

	@Override
	public void validate(IValidationContext arg0, IReporter arg1) throws ValidationException {
		System.out.println("AVISATZ: org.adichatz.studio.xjc.editor.AdiXmlFileValidator.validate");
	}

	@Override
	public void connect(IDocument arg0) {
		System.out.println("AVISATZ: org.adichatz.studio.xjc.editor.AdiXmlFileValidator.connect");
	}

	@Override
	public void disconnect(IDocument arg0) {
		System.out.println("AVISATZ: org.adichatz.studio.xjc.editor.AdiXmlFileValidator.disconnect");
	}

	@Override
	public void validate(IRegion arg0, IValidationContext arg1, IReporter arg2) {
		System.out.println("AVISATZ: org.adichatz.studio.xjc.editor.AdiXmlFileValidator.validate 2");
	}
}
