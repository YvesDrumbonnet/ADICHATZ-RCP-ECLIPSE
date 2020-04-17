package org.adichatz.generator;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.extra.ColumnViewerSorter;
import org.adichatz.engine.extra.CrossReference;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.widgets.supplement.AdiDecimalFormat;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tools.ControlBufferCode;
import org.adichatz.generator.tools.EvalTools;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.MenuManagerWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.internal.ITableColumnWrapper;
import org.adichatz.generator.wrapper.internal.IWidgetWrapper;
import org.adichatz.generator.xjc.CrossReferenceType;
import org.adichatz.generator.xjc.TabularType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public abstract class ATabularGenerator extends ASetGenerator {
	private String tableColumnName;

	/** The table bean class. */
	protected Class<?> tableBeanClass;

	/** The entity tree. */
	protected EntityTreeWrapper entityTree;

	protected String tableBeanStr;

	public ATabularGenerator(final ScenarioInput scenarioInput, ACodeGenerator parentGenerator) {
		super(scenarioInput, parentGenerator);
	}

	protected void buildClassHeader(final ScenarioInput scenarioInput, StringBuffer extendsSB) {
		tableBeanClass = CodeGenerationUtil.getUIBeanClass(scenarioInput);
		if (null != scenarioInput.getPluginEntity()) {
			extendsSB.append("<").append(getObjectName(tableBeanClass)).append(">");
			PluginEntityWrapper pluginEntityWrapper = scenarioInput.getPluginEntityWrapper();
			if (null == pluginEntityWrapper) {
				StringBuffer messageSB = new StringBuffer("Invalid configuration!");
				if (null != scenarioInput.getPluginEntity())
					messageSB.append(" Cannot find entity '").append(scenarioInput.getPluginEntity().getEntityURI()).append("'.");
				messageSB.append(" Check file 'Scenario.xml' (tag 'generationScenario') and class 'PluginEnityTree'.");
				throw new RuntimeException(messageSB.toString());
			} else {
				entityTree = (EntityTreeWrapper) new GeneratorUnit(
						new ScenarioInput(ScenarioResources.getInstance(pluginEntityWrapper.getEntityKeys()[0],
								scenarioInput.getScenarioResources().getPluginName()), scenarioInput.getPluginEntity()))
										.getTreeWrapperFromXml(true);
			}
		}
	}

	protected abstract void addColumns(TabularType tabular, String parentName) throws IOException;

	protected String getTableColumnClassName(ITableColumnWrapper tableColumnWrapper) {
		if (EngineTools.isEmpty(tableColumnWrapper.getControllerClassName()))
			return getTableColumnClassName();
		else {
			return getObjectName(getControllerClass(tableColumnWrapper));
		}
	}

	protected abstract String getTableColumnClassName();

	protected String getTableColumnName() {
		if (null == tableColumnName)
			tableColumnName = EngineTools.lowerCaseFirstLetter(getTableColumnClassName());
		return tableColumnName;
	}

	protected void addBlocks(TabularType tabular) throws IOException {

		tableBeanStr = tableBeanClass.getSimpleName();
		classBodyBuffer.appendPlus("public " + className + "(final " + getObjectName(AdiContext.class) + " context, "
				+ getObjectName(IContainerController.class) + " parentController) {");
		classBodyBuffer.append("super(context);");

		buildControl(classBodyBuffer, (IWidgetWrapper) tabular, false, "parentController", "tabularController");
		classBodyBuffer.append("tabularController.setPluginResources(" + getObjectName(AdichatzApplication.class)
				+ ".getPluginResources(\"" + scenarioInput.getScenarioResources().getPluginResources().getPluginName() + "\"));");

		classBodyBuffer.append("coreController = tabularController;");

		if (null != tabular.getMenuManager())
			addMenuManager("tabularController", (MenuManagerWrapper) tabular.getMenuManager());
		addIncludes(tabular, "tabularController");

		classBodyBuffer.append("");

		addColumns(tabular, "tabularController");

		if (null != tabular.getCrossReferences())
			for (CrossReferenceType crossReference : tabular.getCrossReferences().getCrossReference()) {
				classBodyBuffer.append("getCrossReferences().add( new " + getObjectName(CrossReference.class) + "("//
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, crossReference.getEntitySetId(), false) + ", " //
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, crossReference.getDescription(), false) + ", " //
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, crossReference.getAxmlDetailURI(), false) + ", " //
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, crossReference.getAxmlTableURI(), false) + ", " //
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, crossReference.getAxmlQueryURI(), false) + ", " //
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, crossReference.getPreferenceURI(), false) + "));");
			}
		classBodyBuffer.appendMinus("}");
		addAditionalCode(tabular);
		Method getIdMethod = GeneratorUtil.getIdMethod(scenarioInput.getPluginEntity().getEntityMetaModel().getBeanClass());

		classBodyBuffer.append("");
		classBodyBuffer.append("@Override");
		classBodyBuffer.appendPlus("public Object getId(Object element) {");
		if (null == getIdMethod)
			classBodyBuffer.append("return null;");
		else {
			classBodyBuffer.append("return ((" + getObjectName(tableBeanClass) + ") element)." + getIdMethod.getName() + "();");
		}
		classBodyBuffer.appendMinus("}");
	}

	public static void addGetStyle(ControlGenerator controlGenerator, String style) throws IOException {
		ControlBufferCode styleBuffer = controlGenerator.addBuffer("getStyle", "public int getStyle()");
		styleBuffer.append("return " + controlGenerator.getKeyWordGenerator().getStyle(controlGenerator, style) + ";");
	}

	public void addColumn(ControlGenerator controlGenerator, ITableColumnWrapper columnWrapper, String parentName)
			throws IOException {
		boolean checkColumn = false;
		Method getMethod = null;
		String element = null;
		Class<?> columnClass = null;
		ControlBufferCode columnImageBuffer;

		String columnFormat = null;

		if (null != columnWrapper.getProperty()) {
			Method method = FieldTools.getGetMethod(tableBeanClass, columnWrapper.getProperty(), true);
			if (null == method)
				throw new RuntimeException(getFromGeneratorBundle("generation.no.column4class", columnWrapper.getProperty(),
						tableBeanClass.getName()));
			columnClass = method.getReturnType();
		} else if (null != columnWrapper.getColumnValueType())
			columnClass = GeneratorUtil.getClazzOrPrimitive(columnWrapper.getColumnValueType());

		if (!EngineTools.isEmpty(columnWrapper.getStyle())) {
			addGetStyle(controlGenerator, columnWrapper.getStyle());
		} else if (null != columnClass) {
			if (FieldTools.isDateType(columnClass))
				addGetStyle(controlGenerator, getObjectName(SWT.class) + ".CENTER");
			else if (FieldTools.isNumericType(columnClass))
				addGetStyle(controlGenerator, getObjectName(SWT.class) + ".RIGHT");
		}
		ControlBufferCode columnTextBuffer = controlGenerator.addBuffer("getColumnText",
				"public String getColumnText(" + tableBeanStr + " row)", "columnText");
		if (null != columnClass && null != columnWrapper.getProperty()) {
			getMethod = FieldTools.getGetMethod(tableBeanClass, columnWrapper.getProperty(), true);
			element = "row." + getMethod.getName() + "()";
		}
		if (null != columnWrapper.getColumnValue()) {
			element = keyWordGenerator.evalExpression(columnTextBuffer, columnWrapper.getColumnValue(), false, false);
		}
		if (null != columnWrapper.getColumnText()) {
			CodeGenerationUtil.addCode(columnTextBuffer, columnWrapper.getColumnText());
		} else if (null != columnWrapper.getProperty()) {
			if (FieldTools.isBooleanType(columnClass) && null != columnWrapper.getPattern()
					&& columnWrapper.getPattern().equals("CHECK_ONLY")) {
				columnTextBuffer.append("return " + null + ";");
			} else if (columnClass.isPrimitive()) {
				columnTextBuffer.append("return String.valueOf(" + element + ");");
			} else if (FieldTools.isNumericType(columnClass)) {
				String prefix = "";
				if (ReflectionTools.hasInterface(columnClass, Comparable.class))
					prefix = "null == " + element + " ? null : ";
				columnTextBuffer.append("return " + prefix + " format.format(row." + getMethod.getName() + "());");
				columnFormat = "new " + getObjectName(AdiDecimalFormat.class) + "("
						+ EvalTools.getNumericFormatter(this, columnWrapper.getPattern(), columnWrapper.getLocale()) + ")";
			} else if (FieldTools.isDateType(columnClass)) {
				String prefix = "format";
				if (EngineTools.isEmpty(columnWrapper.getPattern())) {
					if (null != entityTree) {
						Temporal temporalAnnotation = getMethod.getAnnotation(Temporal.class);
						if (null != temporalAnnotation)
							if (TemporalType.TIMESTAMP.equals(temporalAnnotation.value())
									|| TemporalType.TIME.equals(temporalAnnotation.value()))
								columnFormat = getObjectName(DateFormat.class)
										+ ".getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)";
					}
					if (null == columnFormat) {
						columnFormat = getObjectName(DateFormat.class) + ".getDateInstance(DateFormat.SHORT)";
					}
				} else {
					columnFormat = "new " + getObjectName(SimpleDateFormat.class) + "(\"" + columnWrapper.getPattern() + "\")";
				}
				columnTextBuffer.append("return null == " + element + " ? null : " + prefix + ".format(" + element + ");");

			} else if (columnClass.equals(String.class)) {
				columnTextBuffer.append("return " + element + ";");
			} else if (columnClass.isEnum()) {
				String suffix = ".toString();";
				try {
					columnClass.getMethod("value");
					suffix = ".value();";
				} catch (NoSuchMethodException | SecurityException e) {
					try {
						columnClass.getMethod("getValue");
						suffix = ".getValue();";
					} catch (NoSuchMethodException | SecurityException e1) {
						logError(getFromGeneratorBundle("generation.enum.value.error", columnClass.getName()));
					}
				}
				columnTextBuffer.append("return  null == " + element + " ? null : " + element + suffix);
			} else
				columnTextBuffer.append("return  null == " + element + " ? null : String.valueOf(" + element + ");");
		} else {
			columnTextBuffer.append("return \"\";");
		}

		// Check ==> checkBox plus value, Check only only check box
		if (null != columnClass && null == columnWrapper.getColumnText() && FieldTools.isBooleanType(columnClass)
				&& null != columnWrapper.getPattern()
				&& (columnWrapper.getPattern().equals("CHECK_ONLY") || columnWrapper.getPattern().equals("CHECK"))) {
			columnImageBuffer = controlGenerator.addBuffer("columnImageBuffer",
					"public " + getObjectName(Image.class) + " getColumnImage(" + tableBeanStr + " row)");
			checkColumn = true;
			if (null != element && ReflectionTools.hasInterface(columnClass, Comparable.class)) {
				columnImageBuffer.appendPlus("if (null == " + element + ")");
				columnImageBuffer.append("return null;");
			}
			columnImageBuffer.appendMinus("return " + element + " ? " + getObjectName(EngineTools.class) + ".getCheckedImage() : "
					+ getObjectName(EngineTools.class) + ".getUncheckedImage();");
		}

		String returnType = "Object";
		String returnValue = null;
		ControlBufferCode getValueBuffer = controlGenerator.addBuffer("getValue", null, "value");
		if (null == columnWrapper.getColumnValue() || null == columnWrapper.getColumnValueType()) {
			if (!EngineTools.isEmpty(columnWrapper.getColumnText())) {
				returnType = "String";
				returnValue = "getColumnText(row)";
			} else if (null != columnClass) {
				if (getMethod.getReturnType().equals(byte.class)) {
					returnType = "Byte";
					returnValue = "Byte.valueOf(row." + getMethod.getName() + "())";
				} else if (getMethod.getReturnType().equals(short.class)) {
					returnType = "Short";
					returnValue = "Short.valueOf(row." + getMethod.getName() + "())";
				} else if (getMethod.getReturnType().equals(int.class)) {
					returnType = "Integer";
					returnValue = "Integer.valueOf(row." + getMethod.getName() + "())";
				} else if (getMethod.getReturnType().equals(long.class)) {
					returnType = "Long";
					returnValue = "Long.valueOf(row." + getMethod.getName() + "())";
				} else if (getMethod.getReturnType().equals(float.class)) {
					returnType = "Float";
					returnValue = "Float.valueOf(row." + getMethod.getName() + "())";
				} else if (getMethod.getReturnType().equals(double.class)) {
					returnType = "Double";
					returnValue = "Double.valueOf(row." + getMethod.getName() + "())";
				} else if (getMethod.getReturnType().equals(boolean.class)) {
					returnType = "Boolean";
					returnValue = "Boolean.valueOf(row." + getMethod.getName() + "())";
				} else {
					if (getMethod.getReturnType().equals(String.class))
						returnType = "String";
					else if (getMethod.getReturnType().equals(Byte.class))
						returnType = "Byte";
					else if (getMethod.getReturnType().equals(Short.class))
						returnType = "Short";
					else if (getMethod.getReturnType().equals(Integer.class))
						returnType = "Integer";
					else if (getMethod.getReturnType().equals(Long.class))
						returnType = "Long";
					else if (getMethod.getReturnType().equals(Float.class))
						returnType = "Float";
					else if (getMethod.getReturnType().equals(Double.class))
						returnType = "Double";
					else if (getMethod.getReturnType().equals(Boolean.class))
						returnType = "Boolean";
					else if (getMethod.getReturnType().equals(BigDecimal.class))
						returnType = getObjectName(BigDecimal.class);
					else if (getMethod.getReturnType().equals(Date.class))
						returnType = getObjectName(Date.class);
					else
						returnType = "Object";
					returnValue = "row." + getMethod.getName() + "()";
				}
			} else {
				returnType = "Object";
				if (EngineTools.isEmpty(columnWrapper.getColumnText()))
					returnValue = "null";
				else
					returnValue = keyWordGenerator.evalExpression(getValueBuffer, columnWrapper.getColumnText(), false, false);
			}
		}
		if (null != columnWrapper.getColumnValue())
			returnValue = keyWordGenerator.evalExpression(getValueBuffer, columnWrapper.getColumnValue(), false, false);
		if (null != columnWrapper.getColumnValueType())
			returnType = columnClass.getSimpleName();

		getValueBuffer.setMethodInvocation("public " + returnType + " getValue(" + tableBeanStr + " row)");
		getValueBuffer.append("return " + returnValue + ";");

		BufferCode afterCreateControlBuffer = controlGenerator.getAfterCreateControlBuffer();
		if (columnWrapper.isSorted()) {
			afterCreateControlBuffer.append("setColumnViewerSorter(" + getObjectName(ColumnViewerSorter.class) + "."
					+ columnWrapper.getSortDirection().value() + ");");
		}
		if (null != columnFormat)
			afterCreateControlBuffer.append("format = " + columnFormat + ";");
		/* if width is specified Column will have a fixed witdh and will be not packed */
		if (null != columnWrapper.getWidth())
			afterCreateControlBuffer.append("setWidth(" + columnWrapper.getWidth() + ");");

		if (!checkColumn)
			if (null != columnWrapper.getColumnImage()) {
				columnImageBuffer = controlGenerator.addBuffer("columnImageBuffer",
						"public " + getObjectName(Image.class) + " getColumnImage(" + tableBeanStr + " row)", "image");
				CodeGenerationUtil.addCode(columnImageBuffer, columnWrapper.getColumnImage());
			}
		if (null != columnWrapper.getColumnBackground()) {
			ControlBufferCode columnBackgroundBuffer = controlGenerator.addBuffer("columnBackgroundBuffer",
					"public " + getObjectName(Color.class) + " getColumnBackground(" + tableBeanStr + " row)", "background");
			CodeGenerationUtil.addCode(columnBackgroundBuffer, columnWrapper.getColumnBackground());
		}
		if (null != columnWrapper.getColumnForeground()) {
			ControlBufferCode columnForegroundBuffer = controlGenerator.addBuffer("columnForegroundBuffer",
					"public " + getObjectName(Color.class) + " getColumnForeground(" + tableBeanStr + " row)", "foreground");
			CodeGenerationUtil.addCode(columnForegroundBuffer, columnWrapper.getColumnForeground());
		}
		if (null != columnWrapper.getColumnFont()) {
			ControlBufferCode columnFontBuffer = controlGenerator.addBuffer("columnFontBuffer",
					"public " + getObjectName(Font.class) + " getColumnFont(" + tableBeanStr + " row)", "font");
			CodeGenerationUtil.addCode(columnFontBuffer, columnWrapper.getColumnFont());
		}
	}
}
