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
package org.adichatz.generator;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.common.GeneratorUtil.FieldCaseEnum;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.wrapper.ActionWrapper;
import org.adichatz.generator.wrapper.ArgPShelfWrapper;
import org.adichatz.generator.wrapper.ArgTabFolderWrapper;
import org.adichatz.generator.wrapper.ButtonWrapper;
import org.adichatz.generator.wrapper.CComboWrapper;
import org.adichatz.generator.wrapper.CTabFolderWrapper;
import org.adichatz.generator.wrapper.CTabItemWrapper;
import org.adichatz.generator.wrapper.CheckBoxWrapper;
import org.adichatz.generator.wrapper.ComboWrapper;
import org.adichatz.generator.wrapper.CompositeBagWrapper;
import org.adichatz.generator.wrapper.CompositeSeparatorWrapper;
import org.adichatz.generator.wrapper.CompositeWrapper;
import org.adichatz.generator.wrapper.ControlFieldWrapper;
import org.adichatz.generator.wrapper.DateTextWrapper;
import org.adichatz.generator.wrapper.DateTimeWrapper;
import org.adichatz.generator.wrapper.EditableFormTextWrapper;
import org.adichatz.generator.wrapper.EncryptedTextWrapper;
import org.adichatz.generator.wrapper.ExtraTextWrapper;
import org.adichatz.generator.wrapper.FileTextWrapper;
import org.adichatz.generator.wrapper.FontTextWrapper;
import org.adichatz.generator.wrapper.FormPageWrapper;
import org.adichatz.generator.wrapper.FormattedTextWrapper;
import org.adichatz.generator.wrapper.GMapWrapper;
import org.adichatz.generator.wrapper.GenericFieldWrapper;
import org.adichatz.generator.wrapper.GridColumnGroupWrapper;
import org.adichatz.generator.wrapper.GridColumnWrapper;
import org.adichatz.generator.wrapper.GridWrapper;
import org.adichatz.generator.wrapper.GroupWrapper;
import org.adichatz.generator.wrapper.HeaderMenuManagerWrapper;
import org.adichatz.generator.wrapper.HelpButtonWrapper;
import org.adichatz.generator.wrapper.HyperlinkWrapper;
import org.adichatz.generator.wrapper.ImageViewerWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.LabelWrapper;
import org.adichatz.generator.wrapper.ManagedToolBarWrapper;
import org.adichatz.generator.wrapper.MenuActionWrapper;
import org.adichatz.generator.wrapper.MenuItemWrapper;
import org.adichatz.generator.wrapper.MenuManagerWrapper;
import org.adichatz.generator.wrapper.MultiChoiceWrapper;
import org.adichatz.generator.wrapper.NumericTextWrapper;
import org.adichatz.generator.wrapper.PGroupMenuWrapper;
import org.adichatz.generator.wrapper.PGroupToolItemWrapper;
import org.adichatz.generator.wrapper.PGroupWrapper;
import org.adichatz.generator.wrapper.PShelfItemWrapper;
import org.adichatz.generator.wrapper.PShelfWrapper;
import org.adichatz.generator.wrapper.RadioGroupWrapper;
import org.adichatz.generator.wrapper.RefTextWrapper;
import org.adichatz.generator.wrapper.RgbTextWrapper;
import org.adichatz.generator.wrapper.RichTextWrapper;
import org.adichatz.generator.wrapper.SashFormWrapper;
import org.adichatz.generator.wrapper.ScrolledCompositeWrapper;
import org.adichatz.generator.wrapper.SectionWrapper;
import org.adichatz.generator.wrapper.SeparatorWrapper;
import org.adichatz.generator.wrapper.StarRatingWrapper;
import org.adichatz.generator.wrapper.TableColumnWrapper;
import org.adichatz.generator.wrapper.TextWrapper;
import org.adichatz.generator.wrapper.ToolBarWrapper;
import org.adichatz.generator.wrapper.ToolItemWrapper;
import org.adichatz.generator.wrapper.TreeWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IWidgetWrapper;
import org.adichatz.generator.xjc.CollectionType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;

// TODO: Auto-generated Javadoc
/**
 * The Class ACodeGenerator.
 */
public abstract class ACodeGenerator extends AGenerator {

	/** The suffix map. */
	static Map<Class<?>, String> suffixMap = new HashMap<Class<?>, String>();

	static {
		// Action
		suffixMap.put(ActionWrapper.class, "ACT");
		suffixMap.put(ButtonWrapper.class, "BTTN");
		suffixMap.put(HelpButtonWrapper.class, "HB");
		suffixMap.put(SeparatorWrapper.class, "SPR");
		suffixMap.put(MenuActionWrapper.class, "MACT");
		suffixMap.put(MenuItemWrapper.class, "MI");
		suffixMap.put(ToolItemWrapper.class, "ITM");

		// Generic
		suffixMap.put(ControlFieldWrapper.class, "CF");
		suffixMap.put(GenericFieldWrapper.class, "GF");

		suffixMap.put(ComboWrapper.class, "CMB");
		suffixMap.put(CheckBoxWrapper.class, "CB");
		suffixMap.put(CComboWrapper.class, "CCMB");
		suffixMap.put(DateTextWrapper.class, "DT");
		suffixMap.put(DateTimeWrapper.class, "DT");
		suffixMap.put(EditableFormTextWrapper.class, "EFT");
		suffixMap.put(EncryptedTextWrapper.class, "ENTXT");
		suffixMap.put(ExtraTextWrapper.class, "ETXT");
		suffixMap.put(FileTextWrapper.class, "FT");
		suffixMap.put(FontTextWrapper.class, "FNT");
		suffixMap.put(FormattedTextWrapper.class, "FRMT");
		suffixMap.put(GMapWrapper.class, "GMAP");
		suffixMap.put(HyperlinkWrapper.class, "HL");
		suffixMap.put(ImageViewerWrapper.class, "IMG");
		suffixMap.put(LabelWrapper.class, "LBL");
		suffixMap.put(MultiChoiceWrapper.class, "MC");
		suffixMap.put(NumericTextWrapper.class, "NTXT");
		suffixMap.put(RadioGroupWrapper.class, "RG");
		suffixMap.put(RefTextWrapper.class, "RTXT");
		suffixMap.put(RgbTextWrapper.class, "RGBT");
		suffixMap.put(RichTextWrapper.class, "RTXT");
		suffixMap.put(StarRatingWrapper.class, "SR");
		suffixMap.put(TextWrapper.class, "TXT");

		suffixMap.put(ToolBarWrapper.class, "TB");
		suffixMap.put(CTabFolderWrapper.class, "CTBF");

		// Collection
		suffixMap.put(IncludeWrapper.class, "INCL");
		suffixMap.put(ArgTabFolderWrapper.class, "ATBF");
		suffixMap.put(TreeWrapper.class, "TREE");
		suffixMap.put(FormPageWrapper.class, "FP");
		suffixMap.put(SectionWrapper.class, "SCT");
		suffixMap.put(CTabItemWrapper.class, "CTI");
		suffixMap.put(TableColumnWrapper.class, "TC");
		suffixMap.put(ManagedToolBarWrapper.class, "TB");
		suffixMap.put(HeaderMenuManagerWrapper.class, "HMM");
		suffixMap.put(MenuManagerWrapper.class, "MM");
		suffixMap.put(CompositeWrapper.class, "CMPS");
		suffixMap.put(CompositeBagWrapper.class, "CMPB");
		suffixMap.put(CompositeSeparatorWrapper.class, "CSPR");
		suffixMap.put(ScrolledCompositeWrapper.class, "SCLC");
		suffixMap.put(GroupWrapper.class, "GRP");
		suffixMap.put(SashFormWrapper.class, "SHSF");

		// Nebula
		suffixMap.put(PGroupWrapper.class, "PGRP");
		suffixMap.put(PGroupToolItemWrapper.class, "PGTI");
		suffixMap.put(PGroupMenuWrapper.class, "PGM");
		suffixMap.put(PShelfWrapper.class, "PS");
		suffixMap.put(ArgPShelfWrapper.class, "APSS");
		suffixMap.put(PShelfItemWrapper.class, "PSI");
		suffixMap.put(GridWrapper.class, "GRD");
		suffixMap.put(GridColumnWrapper.class, "GC");
		suffixMap.put(GridColumnGroupWrapper.class, "GCG");
	}

	/** METHOD CREATE CONTENTS. */
	public static String METHOD_CREATE_CONTENTS = "createContents";

	/** The parent generator. */
	protected ACodeGenerator parentGenerator;

	/** The scenario input. */
	protected ScenarioInput scenarioInput;

	protected Map<String, String> injectMap = new HashMap<>();

	/** The imports. */
	protected SortedSet<String> imports = new TreeSet<String>();

	/** The static importStatics. */
	protected SortedSet<String> importStatics = new TreeSet<String>();

	/** Buffer code for main class body. */
	protected BufferCode classBodyBuffer = new BufferCode(this, 1, "classBodyBuffer");

	/** Buffer code for declaration main class. */
	protected BufferCode declarationBuffer = new BufferCode(this, 1, "declarationBuffer");

	/** The extra buffer map. */
	protected Map<String, BufferCode> extraBufferMap = new HashMap<String, BufferCode>();

	protected Set<String> propertyFields = new HashSet<String>();

	protected int variableIndex; // index for having unique variable name see method getVariableName

	/**
	 * Gets the class body buffer.
	 * 
	 * @return the class body buffer
	 */
	public BufferCode getClassBodyBuffer() {
		return classBodyBuffer;
	}

	/**
	 * Gets the declaration buffer.
	 * 
	 * @return the declaration buffer
	 */
	public BufferCode getDeclarationBuffer() {
		return declarationBuffer;
	}

	/**
	 * Gets the object name.
	 * 
	 * @param clazz
	 *            the clazz
	 * 
	 * @return the object name
	 */
	public String getObjectName(Class<?> clazz) {
		boolean isArray = clazz.isArray();
		if (isArray)
			clazz = clazz.getComponentType();
		if (!clazz.isPrimitive() && !clazz.getCanonicalName().equals("java.lang." + clazz.getSimpleName())) {
			imports.add(clazz.getName());
		}
		return clazz.getSimpleName() + (isArray ? "[]" : "");
	}

	public String getObjectName(String className) {
		imports.add(className);
		int index = className.lastIndexOf('.');
		return className.substring(index + 1);
	}

	/**
	 * Gets the import statics.
	 * 
	 * @return the importStatics
	 */
	public SortedSet<String> getImportStatics() {
		return importStatics;
	}

	/**
	 * Gets the imports.
	 * 
	 * @return the imports
	 */
	public SortedSet<String> getImports() {
		return imports;
	}

	public String addInjects(String fieldName, String type) {
		String actualType = injectMap.get(fieldName);
		if (null != actualType) {
			if (!type.equals(actualType))
				logError(getFromGeneratorBundle("bad.inject.type", type, actualType, fieldName));
		} else
			injectMap.put(fieldName, type);
		return getObjectName(type);
	}

	/**
	 * Gets the scenario input.
	 * 
	 * @return the scenario input
	 */
	public ScenarioInput getScenarioInput() {
		return scenarioInput;
	}

	/**
	 * Gets the clazz.
	 * 
	 * @param className
	 *            the class name
	 * @return the clazz
	 */
	protected Class<?> getClazz(String className) {
		return scenarioInput.getScenarioResources().getGencodePath().getClazz(className);
	}

	/**
	 * New control generator.
	 * 
	 * @param controlWrapper
	 *            the control wrapper
	 * @param stringParams
	 *            the string params
	 * @return the control generator
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ControlGenerator newControlGenerator(IElementWrapper controlWrapper, boolean addDeclaration, String... stringParams)
			throws IOException {
		ScenarioResources scenarioResources = getScenarioInput().getScenarioResources();
		Class<?> controlGeneratorClass = scenarioResources.getGenerator("#CONTROLGENERATOR()");
		if (null == controlGeneratorClass)
			controlGeneratorClass = ControlGenerator.class;
		if (1 == stringParams.length || 2 == stringParams.length) {
			ControlGenerator controlGenerator = (ControlGenerator) scenarioResources.getGencodePath().instantiateClass(
					controlGeneratorClass, new Class[] { ACodeGenerator.class, IElementWrapper.class, boolean.class, String.class },
					new Object[] { this, controlWrapper, addDeclaration, stringParams[0] });
			if (2 == stringParams.length)
				controlGenerator.setControllerName(stringParams[1]);
			return controlGenerator;
		} else
			throw new RuntimeException("Inconsistent call to ControlGenerator#buildControl !!!");
	}

	/**
	 * Builds the control.
	 * 
	 * @param bufferCode
	 *            the buffer code
	 * @param controlWrapper
	 *            the control wrapper
	 * @param stringParams
	 *            the string params
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String buildControl(BufferCode bufferCode, IElementWrapper controlWrapper, boolean addDeclaration,
			String... stringParams) throws IOException {
		return newControlGenerator(controlWrapper, addDeclaration, stringParams).buildControl(bufferCode);
	}

	/**
	 * Gets the controller class.
	 * 
	 * @param controlWrapper
	 *            the control wrapper
	 * @return the controller class
	 */
	protected Class<?> getControllerClass(IElementWrapper controlWrapper) {
		String controllerClassName = controlWrapper.getControllerClassName();
		if (EngineTools.isEmpty(controllerClassName)) {
			controllerClassName = scenarioInput.getScenarioResources().getController(controlWrapper);
		}
		if (null == controllerClassName) {
			LogBroker.logError("No controller for wrapper " + controlWrapper.getClass().getName() + "! Add it to file "
					+ GeneratorConstants.SCENARIO_FILE + ".");
			return null;
		}
		return scenarioInput.getScenarioResources().getComponentClass(controllerClassName);
	}

	public BufferCode addExtraBuffer(String bufferKey) {
		BufferCode bufferCode = extraBufferMap.get(bufferKey);
		if (null == bufferCode) {
			bufferCode = new BufferCode(this, 1, bufferKey);
			extraBufferMap.put(bufferKey, bufferCode);
		}
		return bufferCode;
	}

	protected String getCreateMethodName(String fieldId) {
		String createMethod = fieldId.replace("#", "$");
		createMethod = "create".concat(GeneratorUtil.getJavaName(createMethod, FieldCaseEnum.UPPER_CASE_FIRST_LETTER, false));
		return createMethod;
	}

	/**
	 * Gets the parent generator.
	 * 
	 * @return the parentGenerator
	 */
	public ACodeGenerator getParentGenerator() {
		return parentGenerator;
	}

	public String getVariableName(String variableName) {
		return variableName.concat("$").concat(String.valueOf(variableIndex++));
	}

	/**
	 * Gets the suffix.
	 * 
	 * @param controllerClass
	 *            the controller class
	 * 
	 * @return the suffix
	 */
	public static String getSuffix(Class<?> controllerClass) {
		return suffixMap.get(controllerClass);
	}

	/**
	 * Gets the controller name.
	 * 
	 * @param controlId
	 *            the control id
	 * @param controlClass
	 *            the control class
	 * 
	 * @return the controller name
	 */
	public static String getControllerName(String controlId, Class<?> controlClass) {
		String controllerName = GeneratorUtil.getJavaName(controlId.replace("#", "$"), FieldCaseEnum.LOWER_CASE_FIRST_LETTER,
				false);
		return controllerName + getSuffix(controlClass);
	}

	public String getThis() {
		return "this";
	}

	/**
	 * Gets the controller sequence.
	 * 
	 * @return the controller sequence
	 */
	public abstract int getControllerSequence();

	public boolean addPropertyField(BufferCode buffer, String propertyName, Class<?> propertyClass, boolean forceAddField)
			throws IOException {
		if (forceAddField || !propertyFields.contains(propertyName)) {
			propertyFields.add(propertyName);
			buffer.append(getObjectName(propertyClass) + " " + propertyName + ";");
			return true;
		}
		return false;
	}

	public PluginEntity<?> getPluginEntity() {
		return scenarioInput.getPluginEntity();
	}

	protected void addRef(IWidgetWrapper widgetWrapper, String widgetName, KeyWordGenerator keyWordGenerator) throws IOException {
		Class<?> controllerClass = getControllerClass(widgetWrapper);
		String controllerClassName = getObjectName(controllerClass);
		addDeclarationControl(widgetWrapper, controllerClass, widgetName);
		declarationBuffer.append("// the " + controllerClass.getSimpleName() + " " + widgetName + ".");
		classBodyBuffer.append(widgetName + " = (" + controllerClassName + ") "
				+ keyWordGenerator.evalExpression(classBodyBuffer, widgetWrapper.getRef(), false, false) + ";");
	}

	public void addDeclarationControl(IElementWrapper controlWrapper, Class<?> controllerClass, String controllerName)
			throws IOException {
		declarationBuffer.append("// the " + controllerClass.getSimpleName() + " " + controllerName + ".");
		String modifier = (controlWrapper instanceof CollectionType) ? "protected " : "private ";
		declarationBuffer.append(modifier + getObjectName(controllerClass) + " " + controllerName + ";");
	}

}
