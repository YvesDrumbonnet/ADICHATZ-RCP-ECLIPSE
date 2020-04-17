package org.adichatz.studio.xjc.custom;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.validation.AValidator;
import org.eclipse.jface.dialogs.IMessageProvider;

public class ClassNameValidator extends AValidator {

	/** The java keyword map. */
	public static List<String> JAVA_KEYWORDS = new ArrayList<String>();
	static {
		JAVA_KEYWORDS.add("abstract");
		JAVA_KEYWORDS.add("continue");
		JAVA_KEYWORDS.add("for");
		JAVA_KEYWORDS.add("new");
		JAVA_KEYWORDS.add("switch");
		JAVA_KEYWORDS.add("assert");
		JAVA_KEYWORDS.add("default");
		JAVA_KEYWORDS.add("goto");
		JAVA_KEYWORDS.add("package");
		JAVA_KEYWORDS.add("synchronized");
		JAVA_KEYWORDS.add("boolean");
		JAVA_KEYWORDS.add("do");
		JAVA_KEYWORDS.add("if");
		JAVA_KEYWORDS.add("private");
		JAVA_KEYWORDS.add("this");
		JAVA_KEYWORDS.add("break");
		JAVA_KEYWORDS.add("double");
		JAVA_KEYWORDS.add("implements");
		JAVA_KEYWORDS.add("protected");
		JAVA_KEYWORDS.add("throw");
		JAVA_KEYWORDS.add("byte");
		JAVA_KEYWORDS.add("else");
		JAVA_KEYWORDS.add("import");
		JAVA_KEYWORDS.add("public");
		JAVA_KEYWORDS.add("throws");
		JAVA_KEYWORDS.add("case");
		JAVA_KEYWORDS.add("enum");
		JAVA_KEYWORDS.add("instanceof");
		JAVA_KEYWORDS.add("return");
		JAVA_KEYWORDS.add("transient");
		JAVA_KEYWORDS.add("catch");
		JAVA_KEYWORDS.add("extends");
		JAVA_KEYWORDS.add("int");
		JAVA_KEYWORDS.add("short");
		JAVA_KEYWORDS.add("try");
		JAVA_KEYWORDS.add("char");
		JAVA_KEYWORDS.add("final");
		JAVA_KEYWORDS.add("interface");
		JAVA_KEYWORDS.add("static");
		JAVA_KEYWORDS.add("void");
		JAVA_KEYWORDS.add("class");
		JAVA_KEYWORDS.add("finally");
		JAVA_KEYWORDS.add("long");
		JAVA_KEYWORDS.add("strictfp");
		JAVA_KEYWORDS.add("volatile");
		JAVA_KEYWORDS.add("const");
		JAVA_KEYWORDS.add("float");
		JAVA_KEYWORDS.add("native");
		JAVA_KEYWORDS.add("super");
		JAVA_KEYWORDS.add("while");
	}

	public static Pattern PACKAGE_PATTERN = Pattern.compile("^[a-zA-Z_\\$][\\w\\$]*(?:\\.[a-zA-Z_\\$][\\w\\$]*)*$");

	public ClassNameValidator(IValidableController triggeringController, String key) {
		super(triggeringController, key);
	}

	@Override
	public int validate() {
		Pattern pattern = Pattern.compile("^[a-zA-Z_\\$][\\w\\$]*(?:\\[a-zA-Z_\\$][\\w\\$]*)*$");
		String text = ((TextController) triggeringController).getControl().getText();
		if (!text.isEmpty() && !pattern.matcher(text).matches() || JAVA_KEYWORDS.contains(text)) {
			return setLevel(IMessageProvider.ERROR, getFromStudioBundle("scenario.datasource.invalid.className"));
		} else
			return setLevel(IMessageProvider.NONE, null);
	};

}
