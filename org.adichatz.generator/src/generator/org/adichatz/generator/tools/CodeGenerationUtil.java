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
package org.adichatz.generator.tools;

import static org.adichatz.engine.common.EngineTools.isEmpty;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.common.encoding.Base64;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.ARunnableListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.validation.ABindingListener;
import org.adichatz.engine.viewer.NativeContentProvider;
import org.adichatz.generator.ACodeGenerator;
import org.adichatz.generator.ACollectionGenerator;
import org.adichatz.generator.ControlGenerator;
import org.adichatz.generator.CustomizationGenerator;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.PartTreeGenerator;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.PGroupMenuWrapper;
import org.adichatz.generator.wrapper.PGroupWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IWidgetWrapper;
import org.adichatz.generator.xjc.AccessibilityType;
import org.adichatz.generator.xjc.ContentProviderType;
import org.adichatz.generator.xjc.CustomizationsType;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.generator.xjc.EntityParamType;
import org.adichatz.generator.xjc.EntitySetContentProviderType;
import org.adichatz.generator.xjc.ListDetailContentProviderType;
import org.adichatz.generator.xjc.ListenerType;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.generator.xjc.MenuItemType;
import org.adichatz.generator.xjc.NativeContentProviderType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.QueryContentProviderType;
import org.adichatz.generator.xjc.QueryOpenClauseType;
import org.adichatz.generator.xjc.QueryParameterType;
import org.adichatz.generator.xjc.QueryPreferenceType;
import org.adichatz.generator.xjc.RefTextType;
import org.adichatz.jpa.tabular.JPAQueryContentProvider;
import org.adichatz.jpa.tabular.ListDetailContentProvider;
import org.adichatz.jpa.wrapper.EntityParamWrapper;
import org.adichatz.scenario.ScenarioInput;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckable;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class CodeGenerationUtil.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class CodeGenerationUtil {

	/**
	 * Adds the code of all listeners.
	 * 
	 * @param buffer
	 *            the buffer
	 * @param wrapper
	 *            the wrapper
	 * @param prefix
	 *            the prefix
	 * @param listenerType
	 *            the listener type
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void addListenersCode(BufferCode buffer, IWidgetWrapper wrapper, String prefix, int listenerType)
			throws IOException {
		if (null == wrapper.getListeners() || wrapper.getListeners().getListener().isEmpty())
			return;
		for (ListenerType listener : wrapper.getListeners().getListener()) {
			ListenerTypeEnum listenerTypeEnum = AListenerTypeManager.LISTENER_TYPE_MAP.get(listener.getListenerTypes());
			if (null == listenerTypeEnum) {
				logError("Listener '" + listener.getId() + "' has no valid type ('" + listener.getListenerTypes() + "')!?");
				continue;
			}
			AListenerTypeManager listenerTypeManager = AListenerTypeManager.LISTENER_MANAGER_MAP.get(listenerTypeEnum);
			if (null == listenerTypeManager) {
				logError("Incompatible listener on element '" + wrapper.getId() + "'!! listener type is invalid or null.");
				continue;
			}
			if (0 == (listenerTypeManager.getListenerCategory() & listenerType))
				continue;
			if (!listenerTypeManager.isEligible(wrapper)) {
				logError("Incompatible listener on element '" + wrapper.getId() + "'!! element class is '"
						+ wrapper.getClass().getName() + "', event type is '" + listener.getListenerTypes() + "'.");
				continue;
			}
			addListenerCode(buffer, wrapper, listener, prefix, listenerTypeManager, listenerTypeEnum);
		}
	}

	/**
	 * Adds the listener code.
	 *
	 * listener.getListenerTypes() contains only when one type (see org.adichatz.generator.tools.TransformTreeTools#reprocessField)
	 *  
	 * @param buffer the buffer
	 * @param wrapper the wrapper
	 * @param listener the listener
	 * @param prefix the prefix
	 * @param listenerTypeManager the listener type manager
	 * @param listenerTypeEnum the listener type enum
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void addListenerCode(BufferCode buffer, IWidgetWrapper wrapper, ListenerType listener, String prefix,
			AListenerTypeManager listenerTypeManager, ListenerTypeEnum listenerTypeEnum) throws IOException {
		KeyWordGenerator keyWordGenerator = buffer.getGenerator().getScenarioInput().getScenarioResources().getKeyWordGenerator();
		String id = listener.getId();
		if (isEmpty(id))
			id = listener.getListenerTypes();
		String listenerId = keyWordGenerator.evalExpr2Str(buffer, id, false);
		ACodeGenerator generator = buffer.getGenerator();
		StringBuffer listenerDeclaration = new StringBuffer();
		String listenerName = generator.getVariableName(wrapper.getId().replace(":", "_") + "LSTN");
		if (!isEmpty(listener.getListenerClassName())) {
			String head = prefix + "addListener";
			String foot = "(this)";
			if (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.LIFE_CYCLE)
					|| 0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.RUNNABLE)) {
				foot = "(" + listenerId + ", " + generator.getObjectName(IEventType.class) + "." + listener.getListenerTypes();
				if (generator instanceof ControlGenerator)
					foot = foot + ", this)";
				else if (generator instanceof CustomizationGenerator || generator instanceof PartTreeGenerator)
					foot = foot + ", controller)";
			}
			if (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.ENTITY))
				head = prefix + "getEntity().addEntityListener";
			else if (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.DATABINDING))
				head = prefix + "getBindingService().addBindingListener";
			else if (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.CONTROL))
				switch (listenerTypeEnum) {
				case DOUBLE_CLICK:
					head = prefix + "getViewer().addDoubleClickListener";
					break;
				case MODIFY_TEXT:
					if (wrapper instanceof RefTextType)
						head = prefix + "getText().addModifyListener";
					else
						head = prefix + "getControl().addModifyListener";
					break;
				case WIDGET_SELECTED:
					head = prefix + "getControl().addSelectionListener";
					break;
				case CHECK_STATE:
					head = prefix + "((" + generator.getObjectName(ICheckable.class) + ") getViewer()).addCheckStateListener";
					break;
				case SELECTION_CHANGED:
					head = prefix + "getViewer().addSelectionChangedListener";
					break;
				case POST_SELECTION_CHANGED:
					head = prefix + "getViewer().addPostSelectionChangedListener";
					break;
				default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
					break;
				}

			Class<?> listenerClass = generator.getScenarioInput().getScenarioResources().getGencodePath()
					.getClazz(listener.getListenerClassName());
			listenerDeclaration.append(head).append("(new ").append(generator.getObjectName(listenerClass)).append(foot)
					.append(");");
			buffer.append(listenerDeclaration.toString());
			// buffer.append(head + "(" + listenerName).append(");");
		} else {
			String listenerClassName = null;
			if (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.ENTITY)) {
				listenerClassName = generator.getObjectName(AEntityListener.class);
				String collectionControllerName = null;
				if (generator instanceof ControlGenerator) {
					ControlGenerator controlGenerator = (ControlGenerator) generator;
					if (controlGenerator.getControlWrapper() instanceof ICollectionWrapper)
						collectionControllerName = controlGenerator.getControllerName();
					else
						logError(getFromGeneratorBundle("generation.not.collection.entity.listener", wrapper.getId(),
								listener.getListenerTypes()));
				} else if (generator instanceof CustomizationGenerator || generator instanceof PartTreeGenerator) {
					collectionControllerName = "(" + generator.getObjectName(ICollectionController.class) + ") controller";
				} else
					logError(getFromGeneratorBundle("generation.not.collection.entity.listener", wrapper.getId(),
							listener.getListenerTypes()));

				buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "(" + listenerId + ", "
						+ collectionControllerName + ", " + generator.getObjectName(IEventType.class) + "."
						+ listener.getListenerTypes() + ") {");
				buffer.append("@Override");
				buffer.appendPlus("public void handleEntityEvent(" + generator.getObjectName(AdiEntityEvent.class) + " event) {");
				addCode(buffer, listener.getCode());
				buffer.appendMinus("}");
				buffer.appendMinus("};");
			} else if (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.DATABINDING)) {
				listenerClassName = generator.getObjectName(ABindingListener.class);
				buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "(" + listenerId + ", "
						+ generator.getObjectName(IEventType.class) + "." + listener.getListenerTypes() + ") {");
				buffer.append("@Override");
				buffer.appendPlus("public void handleEvent(" + generator.getObjectName(AdiEvent.class) + " event) {");
				addCode(buffer, listener.getCode());
				buffer.appendMinus("}");
				buffer.appendMinus("};");
				buffer.append(prefix + "getBindingService().addBindingListener(" + listenerName + ");");
			} else if ((0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.LIFE_CYCLE)) //
					|| (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.BEFORE_ENTITY_INJECTION)) //
					|| (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.AFTER_ENTITY_INJECTION))) {
				listenerClassName = generator.getObjectName(AControlListener.class);
				buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "(" + listenerId + ", "
						+ generator.getObjectName(IEventType.class) + "." + listener.getListenerTypes() + ") {");
				buffer.append("@Override");
				buffer.appendPlus("public void handleEvent(" + generator.getObjectName(AdiEvent.class) + " event) {");
				addCode(buffer, listener.getCode());
				buffer.appendMinus("}");
				buffer.appendMinus("};");
				buffer.append(prefix + "addListener(" + listenerName + ");");
			} else if (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.RUNNABLE)) {
				listenerClassName = generator.getObjectName(ARunnableListener.class);
				buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "(" + listenerId + ", "
						+ generator.getObjectName(IEventType.class) + "." + listener.getListenerTypes() + ") {");
				buffer.append("@Override");
				buffer.appendPlus("public void handleEvent(" + generator.getObjectName(AdiEvent.class) + " event) {");
				addCode(buffer, listener.getCode());
				buffer.appendMinus("}");
				buffer.appendMinus("};");
				buffer.append(prefix + "addListener(" + listenerName + ");");
			} else if (0 != (listenerTypeManager.getListenerCategory() & AListenerTypeManager.CONTROL)) {
				switch (listenerTypeEnum) {
				case DOUBLE_CLICK:
					listenerClassName = generator.getObjectName(IDoubleClickListener.class);
					buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "() {");
					buffer.append("@Override");
					buffer.appendPlus("public void doubleClick(" + generator.getObjectName(DoubleClickEvent.class) + " event) {");
					addCode(buffer, listener.getCode());
					buffer.appendMinus("}");
					buffer.appendMinus("};");
					buffer.append(prefix + "getViewer().addDoubleClickListener(" + listenerName + ");");
					break;
				case MODIFY_TEXT:
					listenerClassName = generator.getObjectName(ModifyListener.class);
					buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "() {");
					buffer.append("@Override");
					buffer.appendPlus("public void modifyText(" + generator.getObjectName(ModifyEvent.class) + " event) {");
					addCode(buffer, listener.getCode());
					buffer.appendMinus("}");
					buffer.appendMinus("};");
					if (wrapper instanceof RefTextType)
						buffer.append(prefix + "getText().addModifyListener(" + listenerName + ");");
					else
						buffer.append(prefix + "getControl().addModifyListener(" + listenerName + ");");
					break;
				case WIDGET_SELECTED:
					listenerClassName = generator.getObjectName(SelectionAdapter.class);
					buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "() {");
					buffer.append("@Override");
					buffer.appendPlus("public void widgetSelected(" + generator.getObjectName(SelectionEvent.class) + " event) {");
					addCode(buffer, listener.getCode());
					buffer.appendMinus("}");
					buffer.appendMinus("};");
					buffer.append(prefix + "getControl().addSelectionListener(" + listenerName + ");");
					break;
				case CHECK_STATE:
					listenerClassName = generator.getObjectName(ICheckStateListener.class);
					buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "() {");
					buffer.append("@Override");
					buffer.appendPlus(
							"public void checkStateChanged(" + generator.getObjectName(CheckStateChangedEvent.class) + " event) {");
					addCode(buffer, listener.getCode());
					buffer.appendMinus("}");
					buffer.appendMinus("};");
					buffer.append(prefix + "getViewer().addCheckStateListener(" + listenerName + ");");
					break;
				case SELECTION_CHANGED:
				case POST_SELECTION_CHANGED:
					listenerClassName = generator.getObjectName(ISelectionChangedListener.class);
					buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "() {");
					buffer.append("@Override");
					buffer.appendPlus(
							"public void selectionChanged(" + generator.getObjectName(SelectionChangedEvent.class) + " event) {");
					addCode(buffer, listener.getCode());
					buffer.appendMinus("}");
					buffer.appendMinus("};");
					buffer.append(
							prefix + "getViewer().add" + (listenerTypeEnum == ListenerTypeEnum.SELECTION_CHANGED ? "" : "Post")
									+ "SelectionChangedListener(" + listenerName + ");");
					break;
				case AFTER_REFRESH:
				case BEFORE_REFRESH:
					listenerClassName = generator.getObjectName(AControlListener.class);
					buffer.appendPlus(listenerClassName + " " + listenerName + " = new " + listenerClassName + "(" + listenerId
							+ ", " + generator.getObjectName(IEventType.class) + "." + listener.getListenerTypes() + ") {");
					buffer.append("@Override");
					buffer.appendPlus("public void handleEvent(" + generator.getObjectName(AdiEvent.class) + " event) {");
					addCode(buffer, listener.getCode());
					buffer.appendMinus("}");
					buffer.appendMinus("};");
					buffer.append(prefix + "addListener(" + listenerName + ");");
					break;
				default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
					break;
				}
			}
			// buffer.appendMinus("}");
		}
	}

	public static void addAdditionalCode(BufferCode buffer, String code) throws IOException {
		if (!isEmpty(code)) {
			buffer.append("");
			buffer.append("//* *****************");
			buffer.append("//* Additional code *");
			buffer.append("//*******************");
			CodeGenerationUtil.addCode(buffer, code);
		}
	}

	public static void addCode(BufferCode buffer, String code) throws IOException {
		if (!isEmpty(code)) {
			KeyWordGenerator keyWordGenerator = buffer.getGenerator().getScenarioInput().getScenarioResources()
					.getKeyWordGenerator();
			StringTokenizer codeTokeniser = new StringTokenizer(code, "\r\n");
			while (codeTokeniser.hasMoreTokens()) {
				String line = codeTokeniser.nextToken().replace(' ', ' '); // replace NO-BREAK SPACE by space (when copied from
																			// web site).
				if (line.trim().startsWith("import ")) {
					line = keyWordGenerator.evalExpression(buffer, line, false, false);
					if (line.endsWith(";"))
						line = line.substring(7, line.length() - 1);
					else
						line = line.substring(7);
					buffer.getGenerator().getImports().add(line);
				} else if (!"\r".equals(line) && !"\n".equals(line))
					buffer.append(keyWordGenerator.evalExpression(buffer, line, true, false).toString());
			}
		}
	}

	public static String addValueList(String valueList, boolean addQuote) {
		StringBuffer valuesSB = new StringBuffer();
		StringTokenizer tokenizer = new StringTokenizer(valueList, ",");
		boolean first = true;
		while (tokenizer.hasMoreElements()) {
			if (first)
				first = false;
			else
				valuesSB.append(", ");
			if (addQuote)
				valuesSB.append("\"");
			valuesSB.append(tokenizer.nextToken().trim());
			if (addQuote)
				valuesSB.append("\"");
		}
		return valuesSB.toString();
	}

	/**
	 * Adds the params.
	 * 
	 * @param buffer
	 *            the buffer code
	 * @param paramMap
	 *            the parent map
	 * @param params
	 *            the params
	 * 
	 * @return true, if successful
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void addParams(BufferCode buffer, String paramMap, ParamsType params, boolean include) throws IOException {
		if (null != params) {
			KeyWordGenerator keyWordGenerator = buffer.getGenerator().getScenarioInput().getScenarioResources()
					.getKeyWordGenerator();
			ACodeGenerator generator = buffer.getGenerator();
			for (ParamType param : params.getParam()) {
				String valueExpr = null;
				if (param instanceof ContentProviderType) {
					ContentProviderType contentProvider = (ContentProviderType) param;
					valueExpr = addContentProvider(buffer, contentProvider);
				} else if (param instanceof CustomizationsType) {
					valueExpr = addCustomizations(buffer, (CustomizationsType) param);
				} else if (param instanceof EntityParamType) {
					EntityParamType entityParam = (EntityParamType) param;
					String EntityParamWrapperName = generator.getObjectName(EntityParamWrapper.class);
					buffer.append(EntityParamWrapperName + " entityParamWrapper = new " + EntityParamWrapperName + "(null);");
					buffer.append("entityParamWrapper.setEntityURI("
							+ keyWordGenerator.evalExpr2Str(buffer, entityParam.getEntityURI(), false) + ");");
					if (!isEmpty(entityParam.getLazyFetches()))
						buffer.append("entityParamWrapper.setLazyFetches("
								+ keyWordGenerator.evalExpr2Str(buffer, entityParam.getLazyFetches(), false) + ");");
					if (!isEmpty(entityParam.getIdvalue()))
						buffer.append("entityParamWrapper.setIdvalue("
								+ keyWordGenerator.evalExpr2Str(buffer, entityParam.getIdvalue(), false) + ");");
					if (null != entityParam.getBinaryIdvalue()) {
						String binIdString = new String(Base64.encode(entityParam.getBinaryIdvalue()), StandardCharsets.ISO_8859_1);
						buffer.append("Object bindIdValue = " + generator.getObjectName(EngineTools.class) + ".readObject("
								+ generator.getObjectName(Base64.class) + ".parseBase64Binary(\"" + binIdString
								+ "\"),pluginResources.getGencodePath().getClassLoader());");
						buffer.append("entityParamWrapper.setBinaryIdvalue(" + generator.getObjectName(EngineTools.class)
								+ ".writeObject(bindIdValue));");
					}
					valueExpr = "entityParamWrapper.postUnmarshal()";
				} else {
					if (null == param.getValue()) {
						valueExpr = "null";
						logWarning(getFromGeneratorBundle("generation.param.value.is.null", param.getId()));
					} else {
						if (null == param.getType() || "String".equals(param.getType()))
							valueExpr = keyWordGenerator.evalExpr2Str(buffer, param.getValue(),
									-1 != param.getValue().indexOf(")."));
						else if ("PROPERTIES".equals(param.getType())) {
							buffer.append(generator.getObjectName(Map.class) + "<String,Object> paramProperties = new "
									+ generator.getObjectName(HashMap.class) + "<>();");
							valueExpr = "paramProperties";
							String value = param.getValue();
							int start = value.indexOf("{");
							int end = value.indexOf("}", start);
							while (start < end && -1 != start) {
								String entry = value.substring(start + 1, end);
								int index = entry.indexOf(',');
								String key = entry.substring(0, index).trim();
								String propertyValue = entry.substring(index + 1).trim();
								buffer.append("paramProperties.put(\"" + key + "\", "
										+ keyWordGenerator.evalExpr2Str(buffer, propertyValue, -1 != propertyValue.indexOf(")."))
										+ ");");
								start = param.getValue().indexOf("{", end);
								end = param.getValue().indexOf("}", start);
							}
						} else
							valueExpr = "(" + generator.getObjectName(GeneratorUtil.getClazzOrPrimitive(param.getType())) + ") "
									+ keyWordGenerator.evalExpression(buffer, param.getValue(), false, false);
					}
				}
				String putString = Boolean.TRUE.equals(param.isOptional()) ? "putOptional" : "put";
				buffer.append(paramMap + putString + "(" + keyWordGenerator.evalExpr2Str(buffer, param.getId(), false) + ", "
						+ valueExpr + ");");
			}
		}
	}

	public static String addCustomizations(BufferCode buffer, CustomizationsType customizations) throws IOException {
		ScenarioInput scenarioInput = buffer.getGenerator().getScenarioInput();
		String customizationName = EngineTools.upperCaseFirstLetter(scenarioInput.getTreeId()).concat("$Customization")
				.concat(String.valueOf(buffer.getGenerator().getControllerSequence()));
		new CustomizationGenerator(scenarioInput, customizationName, customizations);
		return " new " + customizationName + "()";
	}

	public static void addQueryPreference(BufferCode buffer, QueryPreferenceType queryPreference, String queryPreferenceId)
			throws IOException {
		KeyWordGenerator keyWordGenerator = buffer.getGenerator().getScenarioInput().getScenarioResources().getKeyWordGenerator();
		if (!isEmpty(queryPreference.getOrderByClause()))
			buffer.append(queryPreferenceId + ".setOrderByClause("
					+ keyWordGenerator.evalExpr2Str(buffer, queryPreference.getOrderByClause(), false) + ");");
		addPagination(buffer, queryPreference, queryPreferenceId, getControllerName(buffer.getGenerator(), null, "pagination"));
		addParameters(buffer, queryPreference, queryPreferenceId);
	}

	public static void addParameters(BufferCode buffer, QueryPreferenceType queryPreference, String preferenceId)
			throws IOException {
		boolean firstParameter = true;
		for (QueryParameterType parameter : queryPreference.getParameter()) {
			if (firstParameter) {
				firstParameter = false;
				buffer.append(buffer.getGenerator().getObjectName("org.adichatz.jpa.xjc.QueryParameterType") + " parameter;");
			}
			addParameter(buffer, parameter);
			buffer.append(preferenceId + ".getParameter().add(parameter);");
		}
		boolean firstOpenClause = true;
		for (QueryOpenClauseType queryOpenClause : queryPreference.getOpenClause()) {
			String openClauseCN = buffer.getGenerator().getObjectName("org.adichatz.jpa.wrapper.QueryOpenClauseWrapper");
			if (firstOpenClause) {
				buffer.append(openClauseCN + " openClause;");
				firstOpenClause = false;
			}

			buffer.append("openClause = new " + openClauseCN + "();");
			if (queryOpenClause.isPermanent())
				buffer.append("openClause.setPermanent(" + queryOpenClause.isPermanent() + ");");
			if (!queryOpenClause.isVisible())
				buffer.append("openClause.setVisible(" + queryOpenClause.isVisible() + ");");
			addStringProperty(buffer, "openClause.setClause", queryOpenClause.getClause());
			addStringProperty(buffer, "openClause.setTitle", queryOpenClause.getTitle());
			buffer.append("openClause.setValid(" + queryOpenClause.isValid() + ");");
			buffer.append(preferenceId + ".getOpenClause().add(openClause);");
			for (QueryParameterType parameter : queryOpenClause.getParameter()) {
				if (firstParameter) {
					String queryParameterCN = buffer.getGenerator().getObjectName("org.adichatz.jpa.wrapper.QueryParameterWrapper");
					buffer.append(queryParameterCN + " parameter;");
					firstParameter = false;
				}
				addParameter(buffer, parameter);
				buffer.append("openClause.getParameter().add(parameter);");
			}
		}

	}

	private static void addParameter(BufferCode buffer, QueryParameterType parameter) throws IOException {
		String queryParameterCN = buffer.getGenerator().getObjectName("org.adichatz.jpa.wrapper.QueryParameterWrapper");
		buffer.append("parameter = new " + queryParameterCN + "();");
		// query parameter
		addStringProperty(buffer, "parameter.setId", parameter.getId());
		addStringProperty(buffer, "parameter.setPrompt", parameter.getPrompt());
		addStringProperty(buffer, "parameter.setColumnText", parameter.getColumnText());
		addStringProperty(buffer, "parameter.setEntityURI", parameter.getEntityURI());
		addStringProperty(buffer, "parameter.setExpression", parameter.getExpression());
		addStringProperty(buffer, "parameter.setExpressionClassName", parameter.getExpressionClassName());
		if (null != parameter.getBinaryExpression()) {
			logWarning("binary expression in parameters of query preference is not yet implemented");
			/*
			 * PluginEntity pluginEntity = buffer.getScenarioResources().getPluginResources()
			 * .getPluginEntity(parameter.getEntityURI()); Object object =
			 * EngineTools.readObject(Base64.decode(parameter.getBinaryExpression()), pluginEntity.getBeanClass()
			 * .getClassLoader()); ... Import of the class of the object Instantiates the class
			 * 
			 * ...
			 * 
			 * other way ???
			 */

			// buffer.append("parameter.setBinarySecondExpression(" + parameter.getBinarySecondExpression() + ");");

		}
		addStringProperty(buffer, "parameter.setExpressionMethodURI", parameter.getExpressionMethodURI());
		// Column parameter
		addStringProperty(buffer, "parameter.setProperty", parameter.getProperty());
		if (!parameter.isVisible())
			buffer.append("parameter.setVisible(" + parameter.isVisible() + ");");
		if (parameter.isPermanent())
			buffer.append("parameter.setPermanent(" + parameter.isPermanent() + ");");
		addStringProperty(buffer, "parameter.setOperator", parameter.getOperator());
		addStringProperty(buffer, "parameter.setSecondColumnText", parameter.getSecondColumnText());
		addStringProperty(buffer, "parameter.setSecondExpression", parameter.getSecondExpression());
		addStringProperty(buffer, "parameter.setSuffix", parameter.getSuffix());
		buffer.append("parameter.setValid(" + parameter.isValid() + ");");
	}

	public static void addStringProperty(BufferCode buffer, String member, String property) throws IOException {
		if (!isEmpty(property))
			buffer.append(member + "(" + buffer.getGenerator().getScenarioInput().getScenarioResources().getKeyWordGenerator()
					.evalExpr2Str(buffer, property, false) + ");");
	}

	public static void addProperty(BufferCode buffer, String member, Object property) throws IOException {
		if (null != property)
			buffer.append(member + "(" + buffer.getGenerator().getScenarioInput().getScenarioResources().getKeyWordGenerator()
					.evalExpression(buffer, String.valueOf(property), false, false) + ");");
	}

	public static void addPagination(BufferCode buffer, QueryPreferenceType queryPreference, String preferenceId,
			String paginationId) throws IOException {
		if (null != queryPreference.getPagination()) {
			org.adichatz.generator.xjc.QueryPaginationType pagination = queryPreference.getPagination();
			String queryPaginationCN = buffer.getGenerator().getObjectName("org.adichatz.jpa.wrapper.QueryPaginationWrapper");
			buffer.append(queryPaginationCN + " " + paginationId + " = new " + queryPaginationCN + "();");
			buffer.append(preferenceId + ".setPagination(" + paginationId + ");");
			if (null != pagination.getFirstResult())
				buffer.append(paginationId + ".setFirstResult(" + pagination.getFirstResult() + ");");
			if (null != pagination.getMaxResults())
				buffer.append(paginationId + ".setMaxResults(" + pagination.getMaxResults() + ");");
		}
	}

	public static String addContentProvider(BufferCode buffer, ContentProviderType contentProvider) throws IOException {
		ACodeGenerator generator = buffer.getGenerator();
		String valueExpr = "null";
		Class<?> contentProviderClass = ATabularContentProvider.class;
		String contentProviderId = getControllerName(generator, null, "contentProvider");
		QueryContentProviderType queryContentProvider = null;
		KeyWordGenerator keyWordGenerator = generator.getScenarioInput().getScenarioResources().getKeyWordGenerator();

		if (contentProvider instanceof NativeContentProviderType) {
			valueExpr = "new " + generator.getObjectName(NativeContentProvider.class) + "(" + keyWordGenerator.evalExpr2Str(buffer,
					((NativeContentProviderType) contentProvider).getFieldName().trim(), false) + ")";
		} else {
			contentProviderClass = JPAQueryContentProvider.class;
			queryContentProvider = (QueryContentProviderType) contentProvider;
			if (null == queryContentProvider.getAdiResourceURI()) {
				logError("Resource URI must no be null form content provider <contentProvider>!");
				return null;
			}
			if (contentProvider instanceof EntitySetContentProviderType) {
				EntitySetContentProviderType entitySetCP = (EntitySetContentProviderType) queryContentProvider;
				String entityURI = entitySetCP.getParentEntityURI();
				if (isEmpty(entityURI)) {
					entityURI = generator.getScenarioInput().getRootWrapper().getEntityURI();
				}
				String contentProvideCN = generator.getObjectName("org.adichatz.jpa.tabular.EntitySetContentProvider");
				valueExpr = "new " + contentProvideCN + "(getPluginResources().getPluginEntity(\"" //
						+ entityURI + "\").getEntityMetaModel().getEntitySet(\"" + entitySetCP.getFieldName().trim() + "\"), "
						+ keyWordGenerator.evalExpr2Str(buffer, queryContentProvider.getAdiResourceURI(), false) + ", "
						+ keyWordGenerator.evalExpr2Str(buffer, queryContentProvider.getPreferenceURI(), false) + ")";
			} else {
				if (contentProvider instanceof ListDetailContentProviderType)
					contentProviderClass = ListDetailContentProvider.class;
				else
					contentProviderClass = JPAQueryContentProvider.class;
				valueExpr = "new " + generator.getObjectName(contentProviderClass) + "(" + "pluginResources, "
						+ keyWordGenerator.evalExpr2Str(buffer, queryContentProvider.getAdiResourceURI(), false) + ", "
						+ keyWordGenerator.evalExpr2Str(buffer, queryContentProvider.getPreferenceURI(), false) + ")";
			}
		}
		buffer.append(generator.getObjectName(contentProviderClass) + " " + contentProviderId + " = " + valueExpr + ";");
		if (contentProvider instanceof QueryContentProviderType) {
			String comparatorClassName = ((QueryContentProviderType) contentProvider).getComparatorClassName();
			if (!isEmpty(comparatorClassName)) {
				Class<?> comparatorClass = buffer.getGenerator().getScenarioInput().getScenarioResources().getGencodePath()
						.getClazz(comparatorClassName);
				buffer.append(
						contentProviderId + ".setComparator(new " + buffer.getGenerator().getObjectName(comparatorClass) + "());");
			}
		}
		return contentProviderId;
	}

	private static String getControllerName(ACodeGenerator generator, String id, String prefix) {
		if (!isEmpty(id))
			return id;
		return prefix + "$" + generator.getControllerSequence();
	}

	/**
	 * Between quotes.
	 * 
	 * @param string
	 *            the string
	 * 
	 * @return the string wrapped with quotes
	 */
	public static String betweenQuotes(String string) {
		return betweenQuotes(string, "null");
	}

	/**
	 * Between quotes.
	 * 
	 * @param string
	 *            the string
	 * @param valueIfNull
	 *            the value if null
	 * 
	 * @return the string wrapped with quotes
	 */
	private static String betweenQuotes(String string, String valueIfNull) {
		return isEmpty(string) ? valueIfNull : "\"" + string + "\"";
	}

	/**
	 * Gets the values from list.
	 * 
	 * @param valuesList
	 *            the values list
	 * 
	 * @return the values from list
	 */
	public static String getValuesFromList(String valuesList) {
		if (!isEmpty(valuesList)) {
			StringBuffer valuesSB = new StringBuffer();
			StringTokenizer tokenizer = new StringTokenizer(valuesList, ",");
			boolean first = true;
			while (tokenizer.hasMoreElements()) {
				if (first)
					first = false;
				else
					valuesSB.append(", ");
				valuesSB.append("\"").append(tokenizer.nextToken().trim()).append("\"");
			}
			return valuesSB.toString();
		}

		return null;
	}

	public static void addAccessibilities(BufferCode buffer, IElementWrapper controlWrapper, String controllerName,
			boolean addController, boolean forceAddField) throws IOException {
		if (null != controlWrapper.getAccessibilities()) {
			KeyWordGenerator keyWordGenerator = buffer.getGenerator().getScenarioInput().getScenarioResources()
					.getKeyWordGenerator();
			/*
			 * Adds filters
			 */
			for (AccessibilityType filter : controlWrapper.getAccessibilities().getAccessibility()) {
				String prefix = addController ? controllerName + "." : "";
				String evaluatorName = "evaluator" + filter.getType().name();
				buffer.getGenerator().addPropertyField(buffer, evaluatorName, BooleanSupplier.class, forceAddField);
				buffer.appendPlus(evaluatorName + " = new " + buffer.getGenerator().getObjectName(BooleanSupplier.class) + "() {");
				buffer.appendPlus("public boolean getAsBoolean() {");
				if (filter.getAccept().contains("IEntityConstants."))
					buffer.getGenerator().getObjectName(IEntityConstants.class);
				buffer.append(keyWordGenerator.evalExpression(buffer, filter.getAccept(), true, false));
				buffer.appendMinus("}");
				buffer.appendMinus("};");
				buffer.append(prefix + "addAccessibility(new " + buffer.getGenerator().getObjectName(ElementaryAccessibility.class)
						+ "(" + buffer.getGenerator().getObjectName(ElementaryAccessibility.class) + ".ACCESS_ATTRIBUTE."
						+ filter.getType() + ", " + evaluatorName + "));");
			}
		}
	}

	public static void addPGroupItems(ACollectionGenerator collectionGenerator, PGroupWrapper pgroup, String parentCollectionName)
			throws IOException {
		BufferCode bufferCode = collectionGenerator.getClassBodyBuffer();
		for (ElementType element : pgroup.getPgroupToolItemOrPgroupMenu()) {
			String itemName = collectionGenerator.newControlGenerator((IElementWrapper) element, true, parentCollectionName)
					.buildControl(bufferCode);
			if (element instanceof PGroupMenuWrapper)
				for (MenuItemType pgroupMenu : ((PGroupMenuWrapper) element).getMenuItem()) {
					collectionGenerator.newControlGenerator((IElementWrapper) pgroupMenu, true, itemName).buildControl(bufferCode);
				}
		}
	}

	/**
	 * Gets the field.
	 * 
	 * @param beanClass
	 *            the bean class
	 * @param fieldName
	 *            the field name
	 * @return the field
	 */
	public static Field getField(Class<?> beanClass, String fieldName) {
		try {
			return beanClass.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			logError(e);
		} catch (NoSuchFieldException e) {
			Class<?> superClass = beanClass.getSuperclass();
			if (Object.class != superClass)
				return getField(superClass, fieldName);
			logError(e);
		}
		return null;
	}

	public static Class<?> getUIBeanClass(ScenarioInput scenarioInput) {
		Class<?> beanClass;
		if (null != scenarioInput.getPluginEntityWrapper())
			beanClass = scenarioInput.getPluginEntityWrapper().getUIBeanClass();
		else
			beanClass = scenarioInput.getPluginEntity().getUIBeanClass();
		if (null == beanClass) {
			PluginEntity<?> pluginEntity = scenarioInput.getPluginEntity();
			beanClass = pluginEntity.getEntityMetaModel().getUIBeanClass();
		}
		return beanClass;
	}

	public static String getNormalizedId(String id) {
		if (EngineTools.isEmpty(id) || id.trim().isEmpty())
			return "_id_";
		StringBuffer normalizedSB = new StringBuffer();
		char[] chars = id.trim().toCharArray();
		boolean upper = false;
		for (char car : chars) {
			if (Character.isLetterOrDigit(car))
				if (upper) {
					normalizedSB.append(Character.toUpperCase(car));
					upper = false;
				} else
					normalizedSB.append(car);
			else
				upper = true;
		}
		String result = normalizedSB.toString();
		if (EngineTools.isEmpty(result))
			return "_id_";
		if (Character.isDigit(result.charAt(0))) {
			normalizedSB.insert(0, '_');
			result = normalizedSB.toString();
		}
		String strTemp = Normalizer.normalize(result, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return EngineTools.lowerCaseFirstLetter(pattern.matcher(strTemp).replaceAll(""));
	}
}
