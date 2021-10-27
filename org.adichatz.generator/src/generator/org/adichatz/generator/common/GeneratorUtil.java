/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 *
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.generator.common;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;

import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.generation.ARewriter;
import org.adichatz.scenario.generation.MPBVisitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneratorUtil.
 */
public class GeneratorUtil {
	private static AdiResourceBundle generatorRBM = null == AdichatzApplication.getInstance() ? null
			: AdichatzApplication.getPluginResources(GeneratorConstants.GENERATOR_BUNDLE).getResourceBundleManager()
					.getResourceBundle("adichatzGenerator");

	/**
	 * The Enum FieldCaseEnum.
	 */
	public static enum FieldCaseEnum {

		/** The NULL. */
		NULL,
		/** Upper case. */
		UPPER_CASE,
		/** Lower case. */
		LOWER_CASE,
		/** Upper case first letter. */
		UPPER_CASE_FIRST_LETTER,
		/** Lower case first letter. */
		LOWER_CASE_FIRST_LETTER;
	}

	/** The special characters. */
	public static Set<Character> SPECIALCHARACTERS = new HashSet<Character>();

	static {
		SPECIALCHARACTERS.add(' ');
		SPECIALCHARACTERS.add('&');
		SPECIALCHARACTERS.add('\"');
		SPECIALCHARACTERS.add('\'');
		SPECIALCHARACTERS.add('{');
		SPECIALCHARACTERS.add('}');
		SPECIALCHARACTERS.add('(');
		SPECIALCHARACTERS.add(')');
		SPECIALCHARACTERS.add('[');
		SPECIALCHARACTERS.add(']');
		SPECIALCHARACTERS.add('-');
		SPECIALCHARACTERS.add('|');
		SPECIALCHARACTERS.add('\\');
		SPECIALCHARACTERS.add('=');
		SPECIALCHARACTERS.add('+');
		SPECIALCHARACTERS.add('*');
		SPECIALCHARACTERS.add('/');
		SPECIALCHARACTERS.add(':');
		SPECIALCHARACTERS.add(';');
		SPECIALCHARACTERS.add('?');
		SPECIALCHARACTERS.add('%');
		SPECIALCHARACTERS.add('<');
		SPECIALCHARACTERS.add('>');
	}

	/**
	 * Gets the from tool bundle.
	 * 
	 * @param key
	 *            the key
	 * @param variables
	 *            the variables
	 * 
	 * @return the from tool bundle
	 */
	public static String getFromGeneratorBundle(String key, Object... variables) {
		return generatorRBM.getValueFromBundle(key, variables);
	}

	/**
	 * Gets the clazz or primitive.
	 * 
	 * @param className
	 *            the class name
	 * 
	 * @return the clazz or primitive
	 */
	public static Class<?> getClazzOrPrimitive(String className) {
		if (className.equals("byte"))
			return byte.class;
		else if (className.equals("char"))
			return char.class;
		else if (className.equals("double"))
			return double.class;
		else if (className.equals("float"))
			return float.class;
		else if (className.equals("int"))
			return int.class;
		else if (className.equals("long"))
			return long.class;
		else if (className.equals("short"))
			return short.class;
		else if (className.equals("boolean"))
			return boolean.class;
		if (null != AdichatzApplication.getInstance().getApplicationPluginResources())
			return ReflectionTools.getClazz(className);
		else {
			try {
				return GeneratorUtil.class.getClassLoader().loadClass(className);
			} catch (ClassNotFoundException e) {
				logError(e);
				return null;
			}
		}
	}

	/**
	 * compute and get a correct java Name from a bundle name.
	 *
	 * @param javaValue
	 *            the java value
	 * @param fieldCase
	 *            the field case
	 * @param isPackage
	 *            the is package
	 * @return the java name
	 */
	public static String getJavaName(Object javaValue, FieldCaseEnum fieldCase, boolean isPackage) {
		String bundleName = String.valueOf(javaValue);
		if (0 == bundleName.length())
			return "";
		StringBuffer javaNameSB = new StringBuffer();
		boolean upper = false;
		if (!isPackage)
			SPECIALCHARACTERS.add('.');
		for (char c : bundleName.toCharArray()) {
			if (SPECIALCHARACTERS.contains(c)) {
				if (0 == javaNameSB.length())
					return "";
				upper = true;
			} else if (upper) {
				javaNameSB.append(new Character(c).toString().toUpperCase());
				upper = false;
			} else
				javaNameSB.append(new Character(c));
		}
		if (!isPackage)
			SPECIALCHARACTERS.remove('.');
		switch (fieldCase) {
		case UPPER_CASE:
			return javaNameSB.toString().toUpperCase();
		case LOWER_CASE:
			return javaNameSB.toString().toLowerCase();
		case UPPER_CASE_FIRST_LETTER:
			javaNameSB.replace(0, 1, javaNameSB.substring(0, 1).toUpperCase());
			break;
		case LOWER_CASE_FIRST_LETTER:
			javaNameSB.replace(0, 1, javaNameSB.substring(0, 1).toLowerCase());
			break;
		default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
			break;
		}
		return javaNameSB.toString();
	}

	public static String getJNDI(String name) {
		return getJavaName(name, FieldCaseEnum.NULL, false).concat("DS");
	}

	/**
	 * Adds the line comment.
	 * 
	 * @param javadoc
	 *            the javadoc
	 * @param comment
	 *            the comment
	 */
	@SuppressWarnings("unchecked")
	public static void addLineComment(Javadoc javadoc, String comment) {
		TagElement tagElement = javadoc.getAST().newTagElement();
		TextElement textElement = javadoc.getAST().newTextElement();
		tagElement.fragments().add(textElement);
		textElement.setText(comment);
		javadoc.tags().add(tagElement);
	}

	/**
	 * Gets the qualified name.
	 * 
	 * @param ast
	 *            the ast
	 * @param className
	 *            the class name
	 * 
	 * @return the qualified name
	 */
	public static QualifiedName getQualifiedName(AST ast, String className) {
		StringTokenizer tokenizer = new StringTokenizer(className, ".");
		QualifiedName name = ast.newQualifiedName(ast.newSimpleName(tokenizer.nextToken()),
				ast.newSimpleName(tokenizer.nextToken()));
		while (tokenizer.hasMoreElements()) {
			name = ast.newQualifiedName(name, ast.newSimpleName(tokenizer.nextToken()));
		}
		return name;
	}

	/**
	 * Gets the param.
	 * 
	 * @param params
	 *            the params
	 * @param key
	 *            the key
	 * @return the param
	 */
	public static ParamType getParam(ParamsType params, String key) {
		if (null != params)
			for (ParamType param : params.getParam())
				if (key.equals(param.getId())) {
					return param;
				}
		return null;
	}

	public static String getParamValue(ParamsType params, String key) {
		ParamType param = getParam(params, key);
		if (null != param)
			return param.getValue();
		return null;
	}

	/**
	 * Checks for annotation.
	 * 
	 * @param compilationUnit
	 *            the compilation unit
	 * @param annotation
	 *            the annotation
	 * @return the boolean
	 */
	public static Boolean hasAnnotation(ICompilationUnit compilationUnit, String annotation) {
		return !getAnnotations(compilationUnit, annotation).isEmpty();
	}

	public static List<Annotation> getAnnotations(ICompilationUnit compilationUnit, String annotation) {
		try {
			CompilationUnit astRoot = ARewriter.getAstRoot(compilationUnit);
			List<Annotation> annotations = getAnnotations(astRoot, annotation);
			compilationUnit.close();
			return annotations;
		} catch (Exception e) {
			LogBroker.logError(e);
			return null;
		}
	}

	public static List<Annotation> getAnnotations(CompilationUnit astRoot, String annotation) {
		// AST ast = astRoot.getAST();
		MPBVisitor visitor = new MPBVisitor(annotation);
		astRoot.accept(visitor);
		return visitor.getSearchedAnnotations();
	}

	public static Annotation getAnnotation(MethodDeclaration methodDeclaration, String annotation) {
		for (Object modifier : methodDeclaration.modifiers()) {
			if (modifier instanceof Annotation && annotation.equals(((Annotation) modifier).getTypeName().getFullyQualifiedName()))
				return (Annotation) modifier;
		}
		return null;
	}

	public static MethodDeclaration getMethodDeclaration(CompilationUnit astRoot, String methodName) {
		MPBVisitor visitor = new MPBVisitor(methodName);
		astRoot.accept(visitor);
		return visitor.getSearchedMethod();
	}

	/**
	 * Gets the id method.
	 * 
	 * @param beanClass
	 *            the bean class
	 * @return the id method
	 */
	@SuppressWarnings("rawtypes")
	public static Method getIdMethod(Class beanClass) {
		Method getIdMethod = null;
		for (Method method : beanClass.getDeclaredMethods()) {
			if (null != method.getAnnotation(Id.class) || null != method.getAnnotation(EmbeddedId.class)) {
				getIdMethod = method;
				break;
			}
		}
		return getIdMethod;
	}

	/**
	 * Instantiate the generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param treeClassName
	 *            the tree class name
	 * @param classes
	 *            the classes
	 * @param params
	 *            the params
	 */
	public static void instantiateGenerator(ScenarioInput scenarioInput, Object element,
			@SuppressWarnings("rawtypes") Class[] classes, Object[] params) {
		Class<?> generatorClass = scenarioInput.getScenarioResources()
				.getGenerator(element instanceof String ? element : element.getClass().getName());
		if (null == generatorClass)
			throw new RuntimeException("Don't know how to find the right generator for " + element);
		ReflectionTools.instantiateClass(generatorClass, classes, params);
	}

	public static boolean isNullable(Class<?> beanClass, String property) {
		Method getMethod = FieldTools.getGetMethod(beanClass, property, false);
		if (null != getMethod) {
			Column columnAnnotation = getMethod.getAnnotation(Column.class);
			if (null != columnAnnotation)
				return columnAnnotation.nullable();
		}
		return true;
	}
}
