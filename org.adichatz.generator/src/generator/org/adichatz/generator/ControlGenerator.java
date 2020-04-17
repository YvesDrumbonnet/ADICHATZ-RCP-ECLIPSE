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

import static org.adichatz.engine.common.EngineTools.isEmpty;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IDirtyableForm;
import org.adichatz.engine.controller.IItemController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.nebula.ActionPGroupToolItemController;
import org.adichatz.engine.controller.utils.DataReferenceManager;
import org.adichatz.engine.core.EntityManagerCore;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.engine.validation.EntityInjection;
import org.adichatz.engine.widgets.supplement.AHyperlinkRunnable;
import org.adichatz.engine.widgets.supplement.TextControllerProposal;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.tools.AListenerTypeManager;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tools.ControlBufferCode;
import org.adichatz.generator.wrapper.ActionWrapper;
import org.adichatz.generator.wrapper.DateTextWrapper;
import org.adichatz.generator.wrapper.EditableFormTextWrapper;
import org.adichatz.generator.wrapper.FileTextWrapper;
import org.adichatz.generator.wrapper.FormattedTextWrapper;
import org.adichatz.generator.wrapper.HeaderMenuManagerWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.LabelWrapper;
import org.adichatz.generator.wrapper.MenuActionWrapper;
import org.adichatz.generator.wrapper.MenuManagerWrapper;
import org.adichatz.generator.wrapper.MultiChoiceWrapper;
import org.adichatz.generator.wrapper.NumericTextWrapper;
import org.adichatz.generator.wrapper.QueryTreeWrapper;
import org.adichatz.generator.wrapper.RefTextWrapper;
import org.adichatz.generator.wrapper.ScrolledFormWrapper;
import org.adichatz.generator.wrapper.TextWrapper;
import org.adichatz.generator.wrapper.ToolItemWrapper;
import org.adichatz.generator.wrapper.TreeWrapper;
import org.adichatz.generator.wrapper.internal.IActionContainer;
import org.adichatz.generator.wrapper.internal.IArgCollectionWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.IColumnWrapper;
import org.adichatz.generator.wrapper.internal.IControlWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IItemCollectionWrapper;
import org.adichatz.generator.wrapper.internal.IItemCompositeWrapper;
import org.adichatz.generator.wrapper.internal.ILazyFetchesContainer;
import org.adichatz.generator.wrapper.internal.IParamsContainer;
import org.adichatz.generator.wrapper.internal.ITableColumnWrapper;
import org.adichatz.generator.wrapper.internal.IValidableWrapper;
import org.adichatz.generator.wrapper.internal.IValueListWrapper;
import org.adichatz.generator.wrapper.internal.IWidgetWrapper;
import org.adichatz.generator.xjc.ActionType;
import org.adichatz.generator.xjc.CheckBoxType;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.adichatz.generator.xjc.ColumnPreferenceType;
import org.adichatz.generator.xjc.ContributionItemType;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.ControlType;
import org.adichatz.generator.xjc.DateTextType;
import org.adichatz.generator.xjc.DynamicClauseType;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.generator.xjc.ExtraTextType;
import org.adichatz.generator.xjc.FieldContainerType;
import org.adichatz.generator.xjc.FilterType;
import org.adichatz.generator.xjc.FormPageType;
import org.adichatz.generator.xjc.FormattedTextType;
import org.adichatz.generator.xjc.GMapType;
import org.adichatz.generator.xjc.HyperlinkType;
import org.adichatz.generator.xjc.ImageViewerType;
import org.adichatz.generator.xjc.LabelProviderType;
import org.adichatz.generator.xjc.MenuManagerType;
import org.adichatz.generator.xjc.NumericTextType;
import org.adichatz.generator.xjc.PGroupToolItemType;
import org.adichatz.generator.xjc.QueryReferenceType;
import org.adichatz.generator.xjc.RefControlType;
import org.adichatz.generator.xjc.RefTextType;
import org.adichatz.generator.xjc.TabularType;
import org.adichatz.generator.xjc.TextColorType;
import org.adichatz.generator.xjc.TextFontType;
import org.adichatz.generator.xjc.TextImageType;
import org.adichatz.generator.xjc.ValidableContainerType;
import org.adichatz.generator.xjc.ValidatorType;
import org.adichatz.scenario.ScenarioInput;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlGenerator.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class ControlGenerator extends ACodeGenerator {

	/** The control field type. */
	private Class<?> controlFieldType;

	/**
	 * Gets the element wrapper.
	 * 
	 * @return the element wrapper
	 */
	public IElementWrapper getElementWrapper() {
		return controlWrapper;
	}

	/** ********************************* D Y N A M I C - E L E M E N T S ****************************************. */

	/** The control wrapper. */
	protected IElementWrapper controlWrapper;

	/** The parent collection name. */
	private String parentCollectionName;

	/** The controller name. */
	private String controllerName;

	/** The controller class. */
	protected Class<?> controllerClass;

	/** Buffer code for put params in createControl method (insert). */
	protected BufferCode paramCreateControlBuffer;

	/** Buffer code for the init buffer (insert). */
	protected BufferCode beforeInitializeBuffer;

	/** Buffer code for the init buffer (append). */
	protected BufferCode afterInitializeBuffer;

	/** Buffer code for the createControl method (insert). */
	protected BufferCode beforeCreateControlBuffer;

	/** Buffer code for the createControl method (append). */
	protected BufferCode afterCreateControlBuffer;

	/** The after instantiate controller buffer. */
	protected BufferCode afterInstantiateControllerBuffer;

	/** Buffer code for the before entity injection method (insert). */
	protected BufferCode beforeEntityInjectBuffer;

	/** Buffer code for the after entity injection method (insert). */
	protected BufferCode afterEntityInjectBuffer;

	/** Buffer code for the synchronize method (insert). */
	protected BufferCode beforeSynchronizeBuffer;

	/** Buffer code for the synchronize method (append). */
	protected BufferCode afterSynchronizeBuffer;

	/** Buffer code for the afterStartLifeCycleBuffer method (append). */
	protected BufferCode afterStartLifeCycleBuffer;

	/** Buffer code for the endLifeCycle method (insert). */
	protected BufferCode beforeEndLifeCycleBuffer;

	/** Buffer code for the endLifeCycle method (append). */
	protected BufferCode afterEndLifeCycleBuffer;

	protected BufferCode afterEndLifeCycleValidBuffer;

	protected BufferCode parentBodyBuffer;

	protected BufferCode afterInstantiationBuffer;

	/** The control impl sb. */
	private StringBuffer controlImplSB;

	/** The key word generator. */
	private KeyWordGenerator keyWordGenerator;

	/** The add declaration. */
	boolean addDeclaration;

	/** The parent collection. */
	protected ICollectionWrapper<?> parentCollection;

	protected PluginEntity pluginEntity;

	// widgetWrammer#getRef() is not empty
	protected boolean isReferenced = false;

	public ControlGenerator(IElementWrapper controlWrapper) {
		this.controlWrapper = controlWrapper;
	}

	/**
	 * Instantiates a new control generator.
	 * 
	 * @param parentGenerator
	 *            the parent generator
	 * @param controlWrapper
	 *            the control wrapper
	 * @param addDeclaration
	 *            the add declaration
	 * @param parentCollectionName
	 *            the parent collection
	 */
	public ControlGenerator(ACodeGenerator parentGenerator, IElementWrapper controlWrapper, boolean addDeclaration,
			String parentCollectionName) {
		this.scenarioInput = parentGenerator.getScenarioInput();
		pluginEntity = scenarioInput.getPluginEntity();
		this.parentGenerator = parentGenerator;
		this.controlWrapper = controlWrapper;
		this.addDeclaration = addDeclaration;
		controllerClass = getControllerClass(controlWrapper);
		if (null == controllerClass)
			throw new RuntimeException(
					getFromGeneratorBundle("generation.controller.class.error", controlWrapper.getClass().getName()));

		this.parentCollectionName = parentCollectionName;
		controllerName = getControllerName(controlWrapper.getId(), controlWrapper.getClass());
	}

	/**
	 * Sets the parent collection.
	 * 
	 * @param parentCollection
	 *            the new parent collection
	 */
	public void setParentCollection(ICollectionWrapper<?> parentCollection) {
		this.parentCollection = parentCollection;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	/**
	 * Creates the control.
	 * 
	 * @param parentGenerator
	 *            the parent generator
	 * @param controlWrapper
	 *            the control wrapper
	 * @param parentCollectionName
	 *            the parent collection name
	 * @return the control generator
	 */
	public ControlGenerator createControl(ACodeGenerator parentGenerator, IWidgetWrapper controlWrapper,
			String parentCollectionName) {
		this.parentGenerator = parentGenerator;
		this.controlWrapper = controlWrapper;
		this.parentCollectionName = parentCollectionName;

		controllerName = getControllerName(controlWrapper.getId(), controlWrapper.getClass());
		return this;
	}

	/**
	 * Creates the collection.
	 * 
	 * @param parentGenerator
	 *            the parent generator
	 * @param controlWrapper
	 *            the control wrapper
	 * @param parentCollection
	 *            the parent collection
	 * @param controllerName
	 *            the controller name
	 * 
	 * @return the control generator
	 */
	public ControlGenerator createCollection(ACodeGenerator parentGenerator, IWidgetWrapper controlWrapper, String parentCollection,
			String controllerName) {
		createControl(parentGenerator, controlWrapper, parentCollection);
		this.controllerName = controllerName;
		return this;
	}

	/**
	 * Builds the control.
	 * 
	 * @param bufferCode
	 *            the buffer code
	 * 
	 * @return the string
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String buildControl(BufferCode bufferCode) throws IOException {
		return buildControl(bufferCode, 0);
	}

	/**
	 * Builds the control.
	 * 
	 * @param bufferCode
	 *            the buffer code
	 * @param ident
	 *            the ident
	 * 
	 * @return the string
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String buildControl(BufferCode bufferCode, int ident) throws IOException {
		if (controlWrapper instanceof IWidgetWrapper) {
			((IWidgetWrapper) controlWrapper).preCreate();
		}
		keyWordGenerator = scenarioInput.getScenarioResources().getKeyWordGenerator();
		classBodyBuffer = new BufferCode(this, bufferCode.getIdent() + ident, "classBodyBuffer");
		afterInstantiateControllerBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 2, "afterInstantiateControllerBuffer");
		beforeEntityInjectBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 2, "beforeEntityInjectBuffer");
		afterEntityInjectBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 2, "afterEntityInjectBuffer");
		paramCreateControlBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 2, "paramCreateControlBuffer");
		beforeInitializeBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 2, "beforeInitializeBuffer");
		afterInitializeBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 2, "afterInitializeBuffer");
		beforeCreateControlBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 3, "beforeCreateControlBuffer");
		afterCreateControlBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 3, "afterCreateControlBuffer");
		beforeSynchronizeBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 3, "beforeBindEntityBuffer");
		afterSynchronizeBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 3, "afterBindEntityBuffer");
		afterStartLifeCycleBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 3, "afterStartLifeCycleBuffer");
		beforeEndLifeCycleBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 3, "beforeEndLifeCycleBuffer");
		afterEndLifeCycleBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 3, "afterEndLifeCycleBuffer");
		afterEndLifeCycleValidBuffer = new BufferCode(this, classBodyBuffer.getIdent() + 2, "afterEndLifeCycleValidBuffer");
		parentBodyBuffer = new BufferCode(this, classBodyBuffer.getIdent(), "parentBodyBuffer");
		afterInstantiationBuffer = new BufferCode(this, classBodyBuffer.getIdent(), "afterInstantiationBuffer");
		if (controlWrapper instanceof IColumnWrapper)
			scenarioInput.setColumnWrapper((IColumnWrapper) controlWrapper);
		else
			scenarioInput.setColumnWrapper(null);

		if (controlWrapper instanceof IParamsContainer && !(controlWrapper instanceof IncludeWrapper)) {
			CodeGenerationUtil.addParams(paramCreateControlBuffer, "getParamMap()",
					((IParamsContainer) controlWrapper).getParams());
		}

		// adds a line in declaration buffer for the control. LazyFetchContainerType is assumed by AxxxGenCode, IncludeWrapper must
		// not change corecontroller
		if (addDeclaration) {
			if (controlWrapper instanceof IWidgetWrapper && !isEmpty(((IWidgetWrapper) controlWrapper).getRef())) {
				classBodyBuffer.append("// control is referenced by content of widgetWrapper.getRef " + controllerName);
				addRef((IWidgetWrapper) controlWrapper, controllerName, keyWordGenerator);
				isReferenced = true;
			} else {
				addDeclarationControl(controlWrapper, controllerClass, controllerName);
				classBodyBuffer.append("// Creates control for " + controllerClass.getSimpleName() + " " + controllerName);
				if (controlWrapper instanceof ElementType && null != ((ElementType) controlWrapper).getParentIndex())
					classBodyBuffer.append(getObjectName(AdiContext.class) + ".CHILD_INDEX = "
							+ ((ElementType) controlWrapper).getParentIndex() + ";");
			}
		}
		String generic = "";
		if (controlWrapper instanceof TabularType || controlWrapper instanceof ColumnFieldType) {
			Class<?> tableBeanClass = null;
			tableBeanClass = CodeGenerationUtil.getUIBeanClass(scenarioInput);
			if (0 == controllerClass.getTypeParameters().length)
				generic = "";
			else
				generic = "<" + getObjectName(tableBeanClass) + ">";
			if (controlWrapper instanceof TabularType) {
				beforeCreateControlBuffer.append("beanClass = " + getObjectName(tableBeanClass) + ".class;");
				TabularType table = (TabularType) controlWrapper;
				if (null != table.getContentProvider()) {
					afterCreateControlBuffer
							.append(CodeGenerationUtil.addContentProvider(afterCreateControlBuffer, table.getContentProvider())
									+ ".setTabularController(tabularController);");
				}
				if (null != table.getLabelProvider()) {
					LabelProviderType labelProvider = table.getLabelProvider();
					beforeEndLifeCycleBuffer
							.appendPlus("getViewer().setLabelProvider(new " + getObjectName(ColumnLabelProvider.class) + "() {");
					if (!isEmpty(labelProvider.getImageCode())) {
						beforeEndLifeCycleBuffer.append("@Override");
						beforeEndLifeCycleBuffer.appendPlus("public " + getObjectName(Image.class) + " getImage(Object element) {");
						CodeGenerationUtil.addCode(beforeCreateControlBuffer, labelProvider.getImageCode());
						beforeEndLifeCycleBuffer.appendMinus("}");
					}
					if (!isEmpty(labelProvider.getBackgroundCode())) {
						beforeEndLifeCycleBuffer.append("@Override");
						beforeEndLifeCycleBuffer
								.appendPlus("public " + getObjectName(Color.class) + " getBackground(Object element) {");
						CodeGenerationUtil.addCode(beforeEndLifeCycleBuffer, labelProvider.getBackgroundCode());
						beforeEndLifeCycleBuffer.appendMinus("}");
					}
					if (!isEmpty(labelProvider.getForegroundCode())) {
						beforeEndLifeCycleBuffer.append("@Override");
						beforeEndLifeCycleBuffer
								.appendPlus("public " + getObjectName(Color.class) + " getForeground(Object element) {");
						CodeGenerationUtil.addCode(beforeEndLifeCycleBuffer, labelProvider.getForegroundCode());
						beforeEndLifeCycleBuffer.appendMinus("}");
					}
					beforeEndLifeCycleBuffer.appendMinus("});");
				}
				if (null != table.getFilters() && !table.getFilters().getFilter().isEmpty()) {
					String filterClassName = getObjectName("org.adichatz.jpa.xjc.FilterType");
					beforeEndLifeCycleBuffer.append(getObjectName(List.class) + "<" + filterClassName + "> filters = new "
							+ getObjectName(ArrayList.class) + "<>();");
					beforeEndLifeCycleBuffer.append(filterClassName + " filter;");
					for (FilterType filter : table.getFilters().getFilter()) {
						beforeEndLifeCycleBuffer.append(filterClassName + " filter = new " + filterClassName + "();");
						beforeEndLifeCycleBuffer.append("filter.setEnabled()");
						CodeGenerationUtil.addStringProperty(beforeEndLifeCycleBuffer, "filter.setColumn", filter.getColumn());
						CodeGenerationUtil.addStringProperty(beforeEndLifeCycleBuffer, "filter.setSearchString",
								filter.getSearchString());
						CodeGenerationUtil.addStringProperty(beforeEndLifeCycleBuffer, "filter.setText", filter.getText());

						CodeGenerationUtil.addProperty(beforeEndLifeCycleBuffer, "filter.setCaseInsensitive",
								filter.isCaseInsensitive());
						CodeGenerationUtil.addProperty(beforeEndLifeCycleBuffer, "filter.setEnabled", filter.isEnabled());
						CodeGenerationUtil.addProperty(beforeEndLifeCycleBuffer, "filter.setExactString", filter.isExactString());
						beforeEndLifeCycleBuffer.append("filters.add(filter);");
						beforeEndLifeCycleBuffer.append("addFilters(filters);");
					}
				}
				if (null != table.getColumnPreferences() && !table.getColumnPreferences().getColumnPreference().isEmpty()) {
					String columnPreferenceCN = getObjectName("org.adichatz.jpa.xjc.ColumnPreferenceType");
					beforeEndLifeCycleBuffer.append(getObjectName(List.class) + "<" + columnPreferenceCN
							+ "> columnPreferences = new " + getObjectName(ArrayList.class) + "<>();");
					beforeEndLifeCycleBuffer.append(columnPreferenceCN + " columnPreference;");
					for (ColumnPreferenceType columnPreference : table.getColumnPreferences().getColumnPreference()) {
						beforeEndLifeCycleBuffer
								.append(columnPreferenceCN + " columnPreference = new " + columnPreferenceCN + "();");
						beforeEndLifeCycleBuffer.append("columnPreference.setEnabled()");
						CodeGenerationUtil.addStringProperty(beforeEndLifeCycleBuffer, "columnPreference.setId",
								columnPreference.getId());
						CodeGenerationUtil.addProperty(beforeEndLifeCycleBuffer, "filter.setWidth", columnPreference.getWidth());
						CodeGenerationUtil.addProperty(beforeEndLifeCycleBuffer, "filter.setVisible", columnPreference.isVisible());
						beforeEndLifeCycleBuffer.append("columnPreferences.add(columnPreference);");
						beforeEndLifeCycleBuffer.append("addColumnPreferences(columnPreferences);");
					}

				}
			}
		}

		String includeKeysId = null;
		String thisStr;
		if (controlWrapper instanceof IncludeWrapper) {
			thisStr = "new " + getObjectName(EntityManagerCore.class) + "(getContext())";
			includeKeysId = controlWrapper.getId() + "Keys";
			String instanceKeysName = controlWrapper.getId() + "IK";
			String adiResourceURI = keyWordGenerator.evalExpr2Str(classBodyBuffer,
					((IncludeWrapper) controlWrapper).getAdiResourceURI(), true, false);
			classBodyBuffer.append("String " + instanceKeysName + " = " + adiResourceURI + ";");
			classBodyBuffer.appendPlus("if (!" + getObjectName(EngineTools.class) + ".isEmpty(" + instanceKeysName + ")) {");
			classBodyBuffer.append("final String[] " + includeKeysId + " = " + getObjectName(EngineTools.class)
					+ ".getInstanceKeys(" + instanceKeysName + ");");
		} else if (parentGenerator instanceof ControlGenerator)
			thisStr = getGeneratedClassName() + ".this";
		else if (controlWrapper instanceof ControlFieldType)
			thisStr = "genCode";
		else
			thisStr = "this";

		if (null == controllerName) {
			controlImplSB = new StringBuffer("/* */");
		} else {
			controlImplSB = new StringBuffer();
			controlImplSB.append(controllerName).append(" = ");
			controlImplSB.append("new ").append(getObjectName(controllerClass)).append(generic).append("(")
					.append(CodeGenerationUtil.betweenQuotes(controlWrapper.getId()));
			if (null != parentCollectionName) {
				if (controlWrapper instanceof ControlFieldType) {
					controlImplSB.append(", getParentController(").append(parentCollectionName).append("), " + thisStr);
				} else if ((controlWrapper instanceof ColumnFieldType))
					controlImplSB.append(", ").append(parentCollectionName);
				else
					controlImplSB.append(", ").append(parentCollectionName).append(", " + thisStr);
			}

			if (controlWrapper instanceof ImageViewerType) {
				ImageViewerType imageViewer = (ImageViewerType) controlWrapper;
				controlImplSB.append(", ").append(null == imageViewer.getImageStyle() ? "null"
						: keyWordGenerator.getStyle(this, imageViewer.getImageStyle()));
				controlImplSB.append(", ").append(null == imageViewer.getToolBarStyle() ? getObjectName(SWT.class) + ".NONE"
						: keyWordGenerator.getStyle(this, imageViewer.getToolBarStyle()));
			}
			if (controlWrapper instanceof GMapType) {
				GMapType gmap = (GMapType) controlWrapper;
				controlImplSB.append(", ").append(null == gmap.getToolBarStyle() ? getObjectName(SWT.class) + ".NONE"
						: keyWordGenerator.getStyle(this, gmap.getToolBarStyle()));
			}
			if (controlWrapper instanceof MenuManagerType && parentGenerator instanceof ControlGenerator) {
				IElementWrapper parentWrapper = ((ControlGenerator) parentGenerator).getControlWrapper();
				if (parentWrapper instanceof FileTextWrapper || parentWrapper instanceof DateTextWrapper) {
					controlImplSB.append(", ").append(parentCollectionName).append(".getControl()");
					controlImplSB.append(", ").append(parentCollectionName).append(".getControl().getText()");
				} else if (parentWrapper instanceof RefTextWrapper) {
					controlImplSB.append(", ").append(parentCollectionName).append(".getControl()");
					controlImplSB.append(", ").append(parentCollectionName).append(".getText()");
				} else if (parentWrapper instanceof EditableFormTextWrapper) {
					controlImplSB.append(", ").append(parentCollectionName).append(".getControl()");
					controlImplSB.append(", ").append(parentCollectionName).append(".getFormText()");
				} else if (parentWrapper instanceof MultiChoiceWrapper) {
					controlImplSB.append(", ").append(parentCollectionName).append(".getControl()");
					controlImplSB.append(", ").append(parentCollectionName).append(".getLabel()");
				}
			}
		}
		if (controlWrapper instanceof FieldContainerType && !isEmpty(((FieldContainerType) controlWrapper).getEntity())) {
			if (controlWrapper instanceof FormPageType) {
				BufferCode entityBC = addBuffer("getInitialEntity",
						"public " + getObjectName(IEntity.class) + " getInitialEntity()");
				entityBC.append("return "
						+ keyWordGenerator.evalExpression(entityBC, ((FieldContainerType) controlWrapper).getEntity(), true, false)
						+ ";");
			} else {
				BufferCode entityBC = addBuffer("getEntityInjection",
						"public " + getObjectName(EntityInjection.class) + " getEntityInjection()");
				entityBC.appendPlus("if (null == entityInjection)");
				entityBC.append("entityInjection = new " + getObjectName(EntityInjection.class) + "(this, "
						+ keyWordGenerator.evalExpression(entityBC, ((FieldContainerType) controlWrapper).getEntity(), true, false)
						+ ");");
				entityBC.addIdent(-1);
				entityBC.append("return entityInjection;");
				afterInstantiateControllerBuffer.append("breakInjection = true;");
			}
		}
		if (controlWrapper instanceof ValidableContainerType
				&& null != ((ValidableContainerType) controlWrapper).getDynamicClause()) {
			DynamicClauseType dynamicClause = ((ValidableContainerType) controlWrapper).getDynamicClause();
			if (isEmpty(dynamicClause.getConditionCode())) {
				logWarning(getFromGeneratorBundle("generation.invalid.dynamicClause"));
			} else {
				String functionName = controlWrapper.getId().concat("DynamicClause");
				String condition = keyWordGenerator.evalExpr2Str(afterEndLifeCycleBuffer, dynamicClause.getConditionCode(), false);
				BufferCode dynamicBuffer = bufferCode.getGenerator().addExtraBuffer("dynamicBuffer");
				dynamicBuffer.appendPlus("private void " + functionName + "(boolean showDynamic) {");
				boolean emptyPostCode = isEmpty(dynamicClause.getPostCode());
				StringBuffer dynamicSwitch = new StringBuffer();
				String callDynamic = functionName + "(" + condition + ");";
				if (!emptyPostCode)
					dynamicSwitch.append("if (");
				dynamicSwitch.append(controllerName).append(".dynamicSwitchController(showDynamic, false)");
				if (emptyPostCode)
					dynamicBuffer.append(dynamicSwitch.append(';').toString());
				else {
					dynamicBuffer.appendPlus(dynamicSwitch.append(") {").toString());
					CodeGenerationUtil.addCode(dynamicBuffer, dynamicClause.getPostCode());
					dynamicBuffer.appendMinus("}");
				}
				dynamicBuffer.appendMinus("}");

				if (isEmpty(((ValidableContainerType) controlWrapper).getEntity())) {
					// Invokes a new EntityInjection
					BufferCode entityInjectionBuffer = addBuffer("entityInjectionBuffer",
							"public " + getObjectName(EntityInjection.class) + " getEntityInjection()");
					entityInjectionBuffer.appendPlus("if (null == entityInjection)");
					entityInjectionBuffer.append("entityInjection = new " + getObjectName(EntityInjection.class)
							+ "(this, parentController.getEntity());");
					entityInjectionBuffer.addIdent(-1);
					entityInjectionBuffer.append("return entityInjection;");
					afterInstantiateControllerBuffer.append("breakInjection = true;");
				}

				beforeEndLifeCycleBuffer.append(callDynamic);
				afterEndLifeCycleBuffer.appendPlus("final " + getObjectName(AEntityListener.class)
						+ " propertyChangeListener = new " + getObjectName(AEntityListener.class) + "(parentController, "
						+ getObjectName(IEventType.class) + ".AFTER_PROPERTY_CHANGE) {");
				afterEndLifeCycleBuffer.append("@Override");
				afterEndLifeCycleBuffer
						.appendPlus("public void handleEntityEvent(" + getObjectName(AdiEntityEvent.class) + " event)  {");
				if (!isEmpty(dynamicClause.getListenedFieldId()))
					afterEndLifeCycleBuffer.appendPlus("if ("
							+ keyWordGenerator.evalExpr2Str(afterEndLifeCycleBuffer, dynamicClause.getListenedFieldId(), false)
							+ ".equals(event.getPropertyName())) {");
				if (!isEmpty(dynamicClause.getListenedFieldId()))
					afterEndLifeCycleBuffer.append(callDynamic);
				afterEndLifeCycleBuffer.appendMinus("}");
				afterEndLifeCycleBuffer.appendMinus("}");
				afterEndLifeCycleBuffer.appendMinus("};");

				afterEndLifeCycleBuffer.appendPlus("final " + getObjectName(AEntityListener.class) + " refreshChangeListener = new "
						+ getObjectName(AEntityListener.class) + "(parentController, " + getObjectName(IEventType.class)
						+ ".POST_REFRESH) {");
				afterEndLifeCycleBuffer.append("@Override");
				afterEndLifeCycleBuffer
						.appendPlus("public void handleEntityEvent(" + getObjectName(AdiEntityEvent.class) + " event)  {");
				afterEndLifeCycleBuffer
						.appendPlus("if (null != getEntity()) // Entity could be null i.e. when selection is redrawn");
				afterEndLifeCycleBuffer.append(callDynamic);
				afterEndLifeCycleBuffer.addIdent(-1);
				afterEndLifeCycleBuffer.appendMinus("}");
				afterEndLifeCycleBuffer.appendMinus("};");

				afterEndLifeCycleBuffer.append("propertyChangeListener.setForceHandle(true);");
				afterEndLifeCycleBuffer.append("refreshChangeListener.setForceHandle(true);");
				if (isEmpty(dynamicClause.getListenedContainerId()))
					afterEndLifeCycleBuffer.append("getEntity().addEntityListener(propertyChangeListener);");
				else {
					String containerName = controllerName + "DynamicContainer";
					afterEndLifeCycleBuffer
							.append(getObjectName(AEntityManagerController.class) + " " + containerName + " = ("
									+ getObjectName(AEntityManagerController.class) + ") getFromRegister(" + keyWordGenerator
											.evalExpr2Str(afterEndLifeCycleBuffer, dynamicClause.getListenedContainerId(), false)
									+ ");");
					afterEndLifeCycleBuffer.append(containerName + ".getEntity().addEntityListener(propertyChangeListener);");
				}
				afterEndLifeCycleBuffer.append("getEntity().addEntityListener(refreshChangeListener);");
			}
		}
		if (controlWrapper instanceof IItemCompositeWrapper) {
			if (null != ((IItemCompositeWrapper) controlWrapper).getParentIndex())
				afterCreateControlBuffer
						.append("((" + getObjectName(IItemController.class) + ") coreController).getItem().setData(\"index\", "
								+ ((IItemCompositeWrapper) controlWrapper).getParentIndex() + ");");
		}

		/*
		 * Include Wrapper
		 */
		if (controlWrapper instanceof IncludeWrapper) {
			IncludeWrapper includeWrapper = (IncludeWrapper) controlWrapper;
			BufferCode includeBC = addBuffer("include", "public void " + METHOD_CREATE_CONTENTS + "()");
			CodeGenerationUtil.addParams(includeBC, "getParamMap()", includeWrapper.getParams());
			includeBC.append("// loads and instantiates the class " + controllerName + ".");
			boolean canBeDirectlyLaunched = -1 == includeWrapper.getAdiResourceURI().indexOf('#');
			if (canBeDirectlyLaunched) {
				includeBC.appendPlus("if (null != " + getObjectName(EngineConstants.class) + ".GENERATOR) {");
			}
			includeBC.append("pluginResources = null == " + includeKeysId + "[0] ? getPluginResources() : "
					+ getObjectName(AdichatzApplication.class) + ".getPluginResources(" + includeKeysId + "[0]);");
			includeBC.append("instantiateIncludeClass(pluginResources, " + includeKeysId + "[2], " + includeKeysId
					+ "[1], new Class[] {" + getObjectName(ParamMap.class) + ".class, " + getObjectName(IContainerController.class)
					+ ".class, String.class, " + getObjectName(AdiContext.class) + ".class}, new Object[] {getParamMap(), this, "
					+ CodeGenerationUtil.betweenQuotes(includeWrapper.getId()) + ", context});");
			if (canBeDirectlyLaunched) {
				String[] includeKeys = EngineTools.getInstanceKeys(includeWrapper.getAdiResourceURI());
				includeBC.appendMinusPlus("} else");
				GencodePath gencodePath = scenarioInput.getScenarioResources().getGencodePath();
				if (null != includeKeys[0])
					gencodePath = AdichatzApplication.getPluginResources(includeKeys[0]).getGencodePath();
				if (!includeKeys[1].equals(scenarioInput.getSubPackage())) {// Import clause only when necessary
					imports.add(gencodePath.getGenCodePackage(includeKeys[1]).concat(".").concat(includeKeys[2]));
				}
				includeBC.append("new " + includeKeys[2] + "(getParamMap(), this, "
						+ CodeGenerationUtil.betweenQuotes(includeWrapper.getId()) + ", context).createContents();");
				includeBC.addIdent(-1);
			}
		}

		if (controlWrapper instanceof ActionType) {
			ActionType action = (ActionType) controlWrapper;
			if (null != parentCollection && parentCollection instanceof MenuManagerType)
				CodeGenerationUtil.addAccessibilities(afterInitializeBuffer, controlWrapper, controllerName, false, false);
			if (!isEmpty(action.getActionClassName())) {
				Class<?> actionClass = getClazz(action.getActionClassName());
				StringBuffer actionIntantiationSB = new StringBuffer("action = new ");
				actionIntantiationSB.append(getObjectName(actionClass));
				boolean addInit = !EngineTools.isEmpty(action.getImageDescriptor()) || !EngineTools.isEmpty(action.getText())
						|| !EngineTools.isEmpty(action.getToolTipText());
				if (addInit) {
					beforeCreateControlBuffer.appendPlus(actionIntantiationSB.append("() {").toString());
					addActionInit(action);
					beforeCreateControlBuffer.appendMinus("};");
				} else
					beforeCreateControlBuffer.append(actionIntantiationSB.append("();").toString());
				beforeCreateControlBuffer.append("action.setActionController(this);");
			} else if (!isEmpty(action.getActionCode())) {
				beforeCreateControlBuffer.appendPlus("action =  new " + getObjectName(AAction.class) + "() {");
				beforeCreateControlBuffer.append("@Override");
				beforeCreateControlBuffer.appendPlus("public void runAction() {");
				CodeGenerationUtil.addCode(beforeCreateControlBuffer, action.getActionCode());
				beforeCreateControlBuffer.appendMinus("}");
				if (!EngineTools.isEmpty(action.getImageDescriptor()) || !EngineTools.isEmpty(action.getText())
						|| !EngineTools.isEmpty(action.getToolTipText()))
					addActionInit(action);
				beforeCreateControlBuffer.appendMinus("};");
				beforeCreateControlBuffer.append("action.setActionController(this);");
			}
		}
		controlImplSB.append(")");

		if (controlWrapper instanceof PGroupToolItemType) {
			PGroupToolItemType pgroupToolItem = (PGroupToolItemType) controlWrapper;
			if (!isEmpty(pgroupToolItem.getActionClassName())
					&& ReflectionTools.hasSuperClass(controllerClass, ActionPGroupToolItemController.class)) {
				Class<?> actionClass = getClazz(pgroupToolItem.getActionClassName());
				afterCreateControlBuffer.append("setAction(new " + getObjectName(actionClass) + "());");
			} else if (!isEmpty(pgroupToolItem.getItemClassName())) {
				Class<?> itemClass = getClazz(pgroupToolItem.getItemClassName());
				String itemClassName = getObjectName(itemClass);
				StringBuffer itemIntantiationSB = new StringBuffer("pgroupToolItem = new ");
				itemIntantiationSB.append(itemClassName);
				String style;
				if (isEmpty(pgroupToolItem.getStyle()))
					style = getObjectName(SWT.class) + ".None";
				else
					style = keyWordGenerator.evalExpression(beforeCreateControlBuffer, pgroupToolItem.getStyle(), false, false);
				beforeCreateControlBuffer.append(
						itemIntantiationSB.append("(" + parentCollectionName + ".getControl(), " + style + ");").toString());
				beforeCreateControlBuffer.append("((" + itemClassName + ") pgroupToolItem).setActionController(this);");
			} else if (!isEmpty(pgroupToolItem.getItemCode())) {
				beforeCreateControlBuffer
						.appendPlus("pgroupToolItem.addSelectionListener(new " + getObjectName(SelectionAdapter.class) + "() {");
				beforeCreateControlBuffer.append("@Override");
				beforeCreateControlBuffer.appendPlus("public void widgetSelected(" + getObjectName(SelectionEvent.class) + " e) {");
				CodeGenerationUtil.addCode(beforeCreateControlBuffer, pgroupToolItem.getItemCode());
				beforeCreateControlBuffer.appendMinus("}");
				beforeCreateControlBuffer.appendMinus("});");
			}
		}
		/*
		 * Control type is Data field type
		 */
		controlFieldType = null;
		if (controlWrapper instanceof ControlFieldType) {
			ControlFieldType controlField = (ControlFieldType) controlWrapper;
			if (null != controlField.getLinkedControl()) {
				String linkedControlId = controlField.getLinkedControl();
				for (Object element : parentCollection.getElements()) {
					if (element instanceof ControlType && linkedControlId.equals(((ControlType) element).getId())) {
						beforeInitializeBuffer.append("setLinkedController("//
								+ EngineTools.lowerCaseFirstLetter(linkedControlId).replace('#', '$') // # is used by embedded id
								+ getSuffix(element.getClass()) + ");");
						break;
					}
				}
			}
			if (null != scenarioInput.getPluginEntity() && null != controlField.getProperty()) {
				Class<?> beanClass = CodeGenerationUtil.getUIBeanClass(scenarioInput);
				controlFieldType = FieldTools.getGetMethod(beanClass, controlField.getProperty(), true).getReturnType();
			} else {
				String controlValueClassName = ((ControlFieldType) controlWrapper).getControlValueType();
				if (null == controlValueClassName) {
					if (ReflectionTools.hasSuperClass(controlWrapper.getClass(), FormattedTextType.class))
						controlFieldType = int.class;
					else if (ReflectionTools.hasSuperClass(controlWrapper.getClass(), NumericTextType.class))
						controlFieldType = int.class;
					else if (ReflectionTools.hasSuperClass(controlWrapper.getClass(), CheckBoxType.class))
						controlFieldType = boolean.class;
					else if (ReflectionTools.hasSuperClass(controlWrapper.getClass(), DateTextType.class))
						controlFieldType = Date.class;
					else if (ReflectionTools.hasSuperClass(controlWrapper.getClass(), RefControlType.class)) {
						RefControlType refControl = (RefControlType) controlWrapper;
						if (null == refControl.getQueryReference() || isEmpty(refControl.getQueryReference().getAdiQueryURI())) {
							logError(getFromGeneratorBundle("generation.reftext.model.error",
									controlWrapper.getClass().getSimpleName(), controlWrapper.getId()));
						} else {
							ScenarioInput scenarioInput = new ScenarioInput(refControl.getQueryReference().getAdiQueryURI());
							GeneratorUnit generatorUnit = new GeneratorUnit(scenarioInput);
							ITreeWrapper treeWrapper = generatorUnit.getTreeWrapperFromXml(true);
							if (treeWrapper instanceof QueryTreeWrapper) {
								String entityURI = ((QueryTreeWrapper) treeWrapper).getEntityURI();
								AEntityMetaModel<?> entityMM = scenarioInput.getPluginEntityTree().getEntityMM(entityURI);
								if (null == entityMM)
									logError(getFromGeneratorBundle("generation.invalid.entity.URI", entityURI));
								else {
									controlFieldType = entityMM.getBeanClass();
									if (null == pluginEntity)
										pluginEntity = scenarioInput.getScenarioResources().getPluginResources()
												.getPluginEntity(entityURI);
								}
							} else
								logError(getFromGeneratorBundle("generation.invalid.type.resource.URI",
										QueryTreeWrapper.class.getSimpleName(), refControl.getQueryReference().getAdiQueryURI()));
						}
					} else
						controlFieldType = String.class;
				} else {
					if (controlValueClassName.equals("boolean"))
						controlFieldType = boolean.class;
					else if (controlValueClassName.equals("byte"))
						controlFieldType = byte.class;
					else if (controlValueClassName.equals("char"))
						controlFieldType = char.class;
					else if (controlValueClassName.equals("double"))
						controlFieldType = double.class;
					else if (controlValueClassName.equals("float"))
						controlFieldType = float.class;
					else if (controlValueClassName.equals("int"))
						controlFieldType = int.class;
					else if (controlValueClassName.equals("long"))
						controlFieldType = long.class;
					else if (controlValueClassName.equals("short"))
						controlFieldType = short.class;
					else
						controlFieldType = getClazz(controlValueClassName);
				}
			}

			if (controlWrapper instanceof ExtraTextType) {
				if (!isEmpty(((ExtraTextType) controlWrapper).getAddRefreshItem())) {
					ExtraTextType extraText = (ExtraTextType) controlWrapper;
					if (!"true".equals(extraText.getAddRefreshItem()))
						afterCreateControlBuffer.appendPlus("if (" + keyWordGenerator.evalExpression(afterCreateControlBuffer,
								extraText.getAddRefreshItem(), false, false) + ")");
					afterCreateControlBuffer.append("addRefreshItem();");
					if (!"true".equals(extraText.getAddRefreshItem()))
						afterCreateControlBuffer.addIdent(-1);
				}
			}
			if (controlWrapper instanceof RefTextType && null != controlFieldType) {
				RefTextType refText = (RefTextType) controlWrapper;
				if (null == refText.getConvertModelToTarget() && null != refText.getProperty()) {
					addLazyFetch((ILazyFetchesContainer) refText, refText.getProperty());
					String prefix = "";
					if (null != refText.isMandatory())
						prefix = "null==value ? \"\" : ";
					Method referingMethod = FieldTools.getReferingMethod(controlFieldType);
					if (null != referingMethod)
						refText.setConvertModelToTarget("return " + prefix + "#FV()." + referingMethod.getName() + "();");
					else {
						AEntityMetaModel<?> refEntityMM = scenarioInput.getPluginEntityTree().getEntityMM(controlFieldType);
						Method getIdMethod = refEntityMM.getFieldMap().get(refEntityMM.getIdFieldName()).getGetMethod();
						if (String.class == getIdMethod.getReturnType())
							refText.setConvertModelToTarget("return " + prefix + "#FV()." + getIdMethod.getName() + "();");
						else
							refText.setConvertModelToTarget(
									"return " + prefix + "String.valueOf(#FV()." + getIdMethod.getName() + "());");
					}
				}
			}
			/*
			 * initalValue
			 * 
			 * Proccess ModelToTarget element or convertModelToTarget attribute or nothing
			 */
			if (null != controlField.getInitialValue()) {
				addCode("initialValue", "private Object getInitialValue()", controlField.getInitialValue());
				beforeSynchronizeBuffer.append("setValue(getInitialValue());");
			}
			/*
			 * Proccess ModelToTarget element or nothing
			 */
			if (null != controlField.getConvertModelToTarget()) {
				addCode("convertModelToTarget", "public Object convertModelToTarget(Object value)",
						controlField.getConvertModelToTarget());
			}
			/*
			 * Proccess TargetToModel element or specific treatment if NumericText
			 */
			if (null != controlField.getConvertTargetToModel())
				addCode("convertTargetToModel", "public Object convertTargetToModel(Object value)",
						controlField.getConvertTargetToModel());
			/*
			 * compute convertTargetToModel if targetToModel is not null
			 */
			else if (controlWrapper instanceof FormattedTextWrapper) {
				if (controlFieldType.isPrimitive()) {
					addCode("getNull", "public Object getNull()", "return (" + controlFieldType.getSimpleName() + ") 0;");
				}
			} else if (controlWrapper instanceof NumericTextWrapper) {
				String convertMethod = null;
				if (controlFieldType.equals(Byte.class) || controlFieldType.equals(byte.class))
					convertMethod = "byteValue";
				else if (controlFieldType.equals(Double.class) || controlFieldType.equals(double.class))
					convertMethod = "doubleValue";
				else if (controlFieldType.equals(Float.class) || controlFieldType.equals(float.class))
					convertMethod = "floatValue";
				else if (controlFieldType.equals(Integer.class) || controlFieldType.equals(int.class))
					convertMethod = "intValue";
				else if (controlFieldType.equals(Long.class) || controlFieldType.equals(long.class))
					convertMethod = "longValue";
				else if (controlFieldType.equals(Short.class) || controlFieldType.equals(short.class))
					convertMethod = "shortValue";
				else if (controlFieldType.equals(BigInteger.class))
					convertMethod = "toBigInteger";
				if (null != convertMethod) {
					ControlBufferCode convertBuffer = addBuffer("convertTargetToModel",
							"public Object convertTargetToModel(Object fromObject)");
					convertBuffer.appendPlus("if (fromObject instanceof Number)");
					convertBuffer.append("return ((" + getObjectName(BigDecimal.class) + ") fromObject)." + convertMethod + "();");
					convertBuffer.appendMinus("return " + (controlFieldType.isPrimitive() ? "0" : "null") + ";");
					if (controlFieldType.isPrimitive())
						afterSynchronizeBuffer.append("getNumericText().setNullOk(false);");
				}
			}
		}

		if (controlWrapper instanceof HyperlinkType) {
			HyperlinkType hyperlink = ((HyperlinkType) controlWrapper);
			if (null != hyperlink.getRunnableClassName()) {
				Class<?> runnableClass = getClazz(hyperlink.getRunnableClassName());
				afterCreateControlBuffer.append("runnable = new " + getObjectName(runnableClass) + "(this);");
			} else if (null != hyperlink.getRunnableCode()) {
				afterCreateControlBuffer.appendPlus("runnable = new " + getObjectName(AHyperlinkRunnable.class) + "(this){");
				afterCreateControlBuffer.append("@Override");
				afterCreateControlBuffer.appendPlus("public void run() {");
				CodeGenerationUtil.addCode(afterCreateControlBuffer, hyperlink.getRunnableCode());
				afterCreateControlBuffer.appendMinus("}");
				afterCreateControlBuffer.appendMinus("};");
			}
		}

		if (controlWrapper instanceof LabelWrapper && !isEmpty(((LabelWrapper) controlWrapper).getLinkedElement())) {
			if (null != scenarioInput.getRootWrapper()) {
				IElementWrapper element = scenarioInput.getRootWrapper().getElementMap()
						.get(((LabelWrapper) controlWrapper).getLinkedElement());
				if (element instanceof IColumnWrapper)
					scenarioInput.setColumnWrapper((IColumnWrapper) element);

			}
		}

		if (null == parentCollection || !(parentCollection instanceof MenuManagerType) || !(controlWrapper instanceof ActionType))
			CodeGenerationUtil.addAccessibilities(beforeInitializeBuffer, controlWrapper, controllerName, false, false);

		if (controlWrapper instanceof IWidgetWrapper) {
			IWidgetWrapper widgetWrapper = ((IWidgetWrapper) controlWrapper);
			widgetWrapper.postCreate(this, controllerName);
			// Creates code for listeners
			CodeGenerationUtil.addListenersCode(beforeEntityInjectBuffer, widgetWrapper, "",
					AListenerTypeManager.BEFORE_ENTITY_INJECTION);
			CodeGenerationUtil.addListenersCode(afterEntityInjectBuffer, widgetWrapper, "",
					AListenerTypeManager.AFTER_ENTITY_INJECTION);
			CodeGenerationUtil.addListenersCode(afterCreateControlBuffer, widgetWrapper, "",
					AListenerTypeManager.AFTER_CREATE_CONTROL_GROUP);
			CodeGenerationUtil.addListenersCode(beforeInitializeBuffer, widgetWrapper, "", AListenerTypeManager.LIFE_CYCLE);
			// Add CONTROL listener after synchronize to let AWidgetController#addListener be invoked before.
			// That is needed when using controller with dynamicCreateControl after a disposeControl
			CodeGenerationUtil.addListenersCode(afterSynchronizeBuffer, widgetWrapper, "",
					AListenerTypeManager.AFTER_END_LIFE_CYCLE_GROUP);
		}

		// Adds properties from XML file at initialization stage
		keyWordGenerator.processElement(afterInstantiateControllerBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.AFTER_INSTANTIATE_CONTROLLER, "");
		keyWordGenerator.processElement(beforeEntityInjectBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.BEFORE_ENTITY_INJECTION, "");
		keyWordGenerator.processElement(afterEntityInjectBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.AFTER_ENTITY_INJECTION, "");
		keyWordGenerator.processElement(paramCreateControlBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.PARAM_CREATE_CONTROL, "");
		keyWordGenerator.processElement(beforeInitializeBuffer, controlWrapper, controllerClass, LIFECYCLE_STAGE.BEFORE_INITIALIZE,
				"");
		keyWordGenerator.processElement(afterInitializeBuffer, controlWrapper, controllerClass, LIFECYCLE_STAGE.AFTER_INITIALIZE,
				"");
		keyWordGenerator.processElement(beforeCreateControlBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL, "");
		keyWordGenerator.processElement(afterCreateControlBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.AFTER_CREATE_CONTROL, "");
		keyWordGenerator.processElement(afterStartLifeCycleBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.AFTER_START_LIFE_CYCLE, "");
		keyWordGenerator.processElement(beforeEndLifeCycleBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.BEFORE_END_LIFE_CYCLE, "");
		keyWordGenerator.processElement(beforeSynchronizeBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.BEFORE_SYNCHRONIZE, "");
		keyWordGenerator.processElement(afterSynchronizeBuffer, controlWrapper, controllerClass, LIFECYCLE_STAGE.AFTER_SYNCHRONIZE,
				"");
		keyWordGenerator.processElement(afterEndLifeCycleBuffer, controlWrapper, controllerClass,
				LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE, "");

		if (controlWrapper instanceof IControlWrapper) {
			if (!isEmpty(((IControlWrapper) controlWrapper).getProperty())) {
				beforeInitializeBuffer.append(
						"setProperty(" + CodeGenerationUtil.betweenQuotes(((IControlWrapper) controlWrapper).getProperty()) + ");");
			}
		}

		if (controlWrapper instanceof IValidableWrapper) {
			processValidators((IValidableWrapper) controlWrapper, controllerName);
		}
		if (controlWrapper instanceof EditableFormTextWrapper) {
			EditableFormTextWrapper formText = (EditableFormTextWrapper) controlWrapper;
			for (TextFontType font : formText.getTextFont()) {
				String fontValue = font.getFont().trim();
				if (fontValue.startsWith("#CSSFONT")) {
					String variables = fontValue.substring(9);
					variables = variables.substring(0, variables.indexOf(')'));
					afterCreateControlBuffer
							.append("formText.addCSSFont(\"" + font.getKey() + "\", \"" + variables.trim() + "\");");
				} else {
					String value = keyWordGenerator.getTextFont(afterCreateControlBuffer, font.getFont());
					afterCreateControlBuffer.append("formText.addFont(\"" + font.getKey() + "\", " + value + ");");
				}
			}
			for (TextColorType color : formText.getTextColor()) {
				String colorValue = color.getColor().trim();
				if (colorValue.startsWith("#CSSCOLOR")) {
					String variables = colorValue.substring(10);
					variables = variables.substring(0, variables.indexOf(')'));
					int index = variables.indexOf(',');
					if (-1 != index) {
						afterCreateControlBuffer.append("formText.addCSSColor(\"" + color.getKey() + "\", \""
								+ variables.substring(0, index).trim() + "\", \"" + variables.substring(index + 1).trim() + "\");");
					} else
						logError(getFromGeneratorBundle("generation.invalid.CSS.color", variables));
				} else {
					String value = keyWordGenerator.evalExpression(afterCreateControlBuffer, colorValue, false, false);
					afterCreateControlBuffer.append("formText.addColor(\"" + color.getKey() + "\", " + keyWordGenerator
							.getColor(new Statement(null, null, null, afterCreateControlBuffer, null, value.trim())) + ");");
				}
			}
			for (TextImageType image : formText.getTextImage()) {
				String value = keyWordGenerator.evalExpression(afterCreateControlBuffer, image.getImage(), false, false);
				afterCreateControlBuffer.append("formText.addImage(\"" + image.getKey() + "\", "
						+ keyWordGenerator.getImage(afterCreateControlBuffer, value) + ");");
			}
		}

		if (controlWrapper instanceof IValueListWrapper) {
			IValueListWrapper valueListWrapper = (IValueListWrapper) controlWrapper;
			AEntityMetaModel<?> refEntityMM;
			Method getIdMethod;
			if (controlWrapper instanceof IArgCollectionWrapper) {
				refEntityMM = scenarioInput.getPluginEntity().getEntityMetaModel();
				controlFieldType = refEntityMM.getBeanClass();
				getIdMethod = null;
			} else
				try {
					//
					refEntityMM = scenarioInput.getPluginEntityTree().getEntityMM(controlFieldType);
					getIdMethod = refEntityMM.getFieldMap().get(refEntityMM.getIdFieldName()).getGetMethod();
				} catch (RuntimeException e) {
					// Initializes refEntityMM to null and so nothing else. control field type could be a primitive or String...
					refEntityMM = null;
					getIdMethod = null;
				}
			if (controlWrapper instanceof RefControlType && !isEmpty(((RefControlType) controlWrapper).getProperty())) {
				afterCreateControlBuffer
						.append("dataReferenceManager = new " + getObjectName(DataReferenceManager.class) + "(this);");
			}
			boolean displayedValues = false; // There is no displayed values
			if (null != valueListWrapper.getValues()) {
				String componentType = "String";
				if (valueListWrapper instanceof ControlFieldType && null != ((ControlFieldType) valueListWrapper).getProperty()) {
					Class<?> fieldClass = FieldTools.getGetMethod(scenarioInput.getPluginEntityWrapper().getBeanClass(),
							((ControlFieldType) valueListWrapper).getProperty(), true).getReturnType();
					if (fieldClass.isArray()) {
						componentType = fieldClass.getComponentType().getSimpleName();
					}
				}
				addValuesBuffer("getValues", valueListWrapper.getValues(), componentType);
				if (!isEmpty(valueListWrapper.getDisplayedValues())) {
					displayedValues = true;
					addValuesBuffer("getDisplayedValues", valueListWrapper.getDisplayedValues(), componentType);
				}
			} else if (null != valueListWrapper.getInitValues()) {
				BufferCode valuesBC = addBuffer("getValues", "public " + getObjectName(List.class) + " getValues()");
				CodeGenerationUtil.addCode(valuesBC, valueListWrapper.getInitValues());
			} else {
				if (valueListWrapper instanceof RefControlType) {
					RefControlType refControl = (RefControlType) controlWrapper;
					if (isEmpty(refControl.getProperty()) && null == refControl.getQueryReference()) {
						logWarning("Don't know how to build value list for " + controlWrapper.getClass().getSimpleName() + " "
								+ controlWrapper.getId() + "!");
					} else {
						QueryReferenceType queryReference = refControl.getQueryReference();
						if (null != refEntityMM && null == queryReference) {
							queryReference = new QueryReferenceType();
							StringBuffer queryResourceURISB = new StringBuffer("adi://");
							String pluginKey = scenarioInput.getPluginEntityWrapper(refEntityMM.getPluginEntity().getEntityURI())
									.getEntityKeys()[0];
							if (isEmpty(pluginKey))
								pluginKey = scenarioInput.getScenarioResources().getPluginName();
							queryResourceURISB.append(pluginKey).append("/");
							queryResourceURISB.append(refEntityMM.getPluginEntity().getEntityKeys()[1]).append("/");
							queryResourceURISB.append(refEntityMM.getPluginEntity().getEntityId()).append("QUERY");
							queryReference.setAdiQueryURI(queryResourceURISB.toString());
						}
						if (null != queryReference) {
							beforeCreateControlBuffer.append("adiQueryURI = " + keyWordGenerator
									.evalExpr2Str(beforeCreateControlBuffer, queryReference.getAdiQueryURI(), false) + ";");
							if (!EngineTools.isEmpty(queryReference.getPreferenceURI()))
								beforeCreateControlBuffer.append("preferenceURI = " + keyWordGenerator
										.evalExpr2Str(beforeCreateControlBuffer, queryReference.getPreferenceURI(), false) + ";");
						}
						if (null != controlWrapper.getControllerClassName()) {
							Class<?> clazz = getClazz(controlWrapper.getControllerClassName());
							try {
								clazz.getMethod("getDisplayedValues");
								displayedValues = true;
							} catch (SecurityException e) {
								logError(e);
							} catch (NoSuchMethodException e) {
								// Nothing to do
							}
						}
					}
				} else
					logError("values or initValue must not be empty for " + controlWrapper.getClass().getSimpleName() + " "
							+ controlWrapper.getId() + "!");

				if (null != getIdMethod) { // redefines compare when input is made of managed beans
					ControlBufferCode compareBuffer = addBuffer("compare", "public boolean compare(Object a, Object b)");
					getObjectName(controlFieldType);
					if (getIdMethod.getReturnType().isPrimitive())
						compareBuffer.append("return ((" + controlFieldType.getSimpleName() + ") a)." + getIdMethod.getName()
								+ "() == ((" + controlFieldType.getSimpleName() + ") b)." + getIdMethod.getName() + "();");
					else
						compareBuffer.append("return ((" + controlFieldType.getSimpleName() + ") a)." + getIdMethod.getName()
								+ "().equals(((" + controlFieldType.getSimpleName() + ") b)." + getIdMethod.getName() + "());");
				}
			}
			if (controlWrapper instanceof IArgCollectionWrapper
					&& !isEmpty(((IArgCollectionWrapper) controlWrapper).getSelection())) {
				afterSynchronizeBuffer.append("getControl().setSelection(" + keyWordGenerator.evalExpression(afterSynchronizeBuffer,
						((IArgCollectionWrapper) controlWrapper).getSelection(), false, false) + ");");
				afterSynchronizeBuffer.append("getControl().notifyListeners(" + getObjectName(SWT.class) + ".Selection, null);");
			}

			if (isEmpty(valueListWrapper.getLabelProviderClassName()) && null == valueListWrapper.getLabelProvider()) {
				if (null == refEntityMM) {
					if (null == controlWrapper.getControllerClassName()) {
						beforeCreateControlBuffer.appendPlus("labelProvider = new " + getObjectName(LabelProvider.class) + "() {");
						beforeCreateControlBuffer.appendPlus("public String getText(Object element) {");
						if (!displayedValues)
							beforeCreateControlBuffer.append("return null == element ? \"\" : String.valueOf(element);");
						else
							beforeCreateControlBuffer
									.append("return (String) getDisplayedValues().get(getValues().indexOf(element));");

						beforeCreateControlBuffer.appendMinus("}");
						beforeCreateControlBuffer.appendMinus("};");
					}

				} else {
					// Add XML code which is processed just below.
					LabelProviderType labelProvider = new LabelProviderType();
					valueListWrapper.setLabelProvider(labelProvider);
					Method referingMethod = FieldTools.getReferingMethod(controlFieldType);
					if (null != referingMethod)
						labelProvider.setTextCode("return #ELEMENT()." + referingMethod.getName() + "();");
					else {
						if (String.class == getIdMethod.getReturnType())
							labelProvider.setTextCode("return #ELEMENT()." + getIdMethod.getName() + "();");
						else
							labelProvider.setTextCode("return String.valueOf(#ELEMENT()." + getIdMethod.getName() + "());");
					}
				}
			}
			if (!isEmpty(valueListWrapper.getLabelProviderClassName())) {
				Class<?> labelProviderClass = getClazz(valueListWrapper.getLabelProviderClassName());
				beforeCreateControlBuffer.append("labelProvider = new " + getObjectName(labelProviderClass) + "();");
			} else if (null != valueListWrapper.getLabelProvider()) {
				beforeCreateControlBuffer.appendPlus("labelProvider = new " + getObjectName(LabelProvider.class) + "() {");
				beforeCreateControlBuffer.appendPlus("public String getText(Object element) {");
				CodeGenerationUtil.addCode(beforeCreateControlBuffer, valueListWrapper.getLabelProvider().getTextCode());
				beforeCreateControlBuffer.appendMinus("}");
				if (!isEmpty(valueListWrapper.getLabelProvider().getImageCode())) {
					beforeCreateControlBuffer.appendPlus("public " + getObjectName(Image.class) + " getImage(Object element) {");
					CodeGenerationUtil.addCode(beforeCreateControlBuffer, valueListWrapper.getLabelProvider().getImageCode());
					beforeCreateControlBuffer.appendMinus("}");
				}
				beforeCreateControlBuffer.appendMinus("};");
			}
		}
		if (controlWrapper instanceof TextWrapper && null != ((TextWrapper) controlWrapper).getProposals()) {
			afterCreateControlBuffer.append("new " + getObjectName(TextControllerProposal.class) + "(this, null, "
					+ getObjectName(Arrays.class) + ".asList(new Object[] {"
					+ CodeGenerationUtil.addValueList(((TextWrapper) controlWrapper).getProposals(), true) + "}));");
		}

		if (controlWrapper instanceof IActionContainer && null != ((IActionContainer) controlWrapper).getAction()) {
			ActionWrapper action = (ActionWrapper) ((IActionContainer) controlWrapper).getAction();
			classBodyBuffer.addIdent(ident);

			String actionName = buildControl(afterCreateControlBuffer, action, true, "this");
			classBodyBuffer.addIdent(-1 * ident);
			afterCreateControlBuffer.append("setActionController(" + actionName + ");");
		}
		if (controlWrapper instanceof ToolItemWrapper && null != ((ToolItemWrapper) controlWrapper).getMenuAction()) {
			MenuActionWrapper menuAction = (MenuActionWrapper) ((ToolItemWrapper) controlWrapper).getMenuAction();
			classBodyBuffer.addIdent(ident);
			buildControl(afterCreateControlBuffer, menuAction, true, "this");
		}
		if (controlWrapper instanceof ControlFieldType) {
			ControlFieldType controlField = (ControlFieldType) controlWrapper;
			if (null != controlField.getMenuManager()) {
				MenuManagerWrapper menuManager = (MenuManagerWrapper) controlField.getMenuManager();
				String menuName = buildControl(afterCreateControlBuffer, menuManager, true, controllerName);
				afterCreateControlBuffer.append(menuName + ".createControl();");
			}
			if (!EngineTools.isEmpty(controlField.getFieldBindManagerClassName())) {
				afterCreateControlBuffer.appendPlus("if (null != fieldBindManager)");
				Class<?> fieldBindManagerClass = getClazz(controlField.getFieldBindManagerClassName());
				afterCreateControlBuffer.append("fieldBindManager = new " + getObjectName(fieldBindManagerClass) + "(this);");
				afterCreateControlBuffer.addIdent(-1);
			}
		}
		if (controlWrapper instanceof MenuManagerType) {
			MenuManagerType menuManager = (MenuManagerType) controlWrapper;
			BufferCode menuAboutToShowBuffer;
			if (parentGenerator instanceof ExtendTreeGenerator) {
				menuAboutToShowBuffer = classBodyBuffer;
			} else {
				menuAboutToShowBuffer = afterStartLifeCycleBuffer;
			}

			boolean first = true;
			for (ElementType element : menuManager.getActionOrMenuOrSeparator()) {
				if (element instanceof ContributionItemType) {
					ContributionItemType contributionItem = (ContributionItemType) element;
					if (!isEmpty(contributionItem.getControllerClassName()))
						menuAboutToShowBuffer.append("new " + getObjectName(getClazz(contributionItem.getControllerClassName()))
								+ "(" + controllerName + ");");
					else {
						if (first) {
							first = false;
							menuAboutToShowBuffer.append(getObjectName(Action.class) + " action;");
						}
						if (!EngineTools.isEmpty(contributionItem.getValid())) {
							menuAboutToShowBuffer.appendPlus("if (" + keyWordGenerator.evalExpression(menuAboutToShowBuffer,
									contributionItem.getValid(), false, false) + ") {");
						}
						menuAboutToShowBuffer.appendPlus("action = new " + getObjectName(Action.class) + "("
								+ keyWordGenerator.evalExpr2Str(menuAboutToShowBuffer, contributionItem.getText(), false) + ","
								+ keyWordGenerator.evalExpression(menuAboutToShowBuffer, contributionItem.getImageDescriptor(),
										false, false)
								+ ") {");
						menuAboutToShowBuffer.appendPlus("public void run() {");
						CodeGenerationUtil.addCode(menuAboutToShowBuffer, contributionItem.getItemCode());
						menuAboutToShowBuffer.appendMinus("}");
						menuAboutToShowBuffer.appendMinus("};");
						menuAboutToShowBuffer.append(controllerName + ".getControl().add(action);");
						if (!EngineTools.isEmpty(contributionItem.getValid())) {
							menuAboutToShowBuffer.appendMinus("}");
						}
					}
					//				} else if (element instanceof SeparatorType) {
					//					menuAboutToShowBuffer
					//							.append(controllerName + ".getControl().add(new " + getObjectName(Separator.class) + "());");
				} else {
					ControlGenerator childGenerator = new ControlGenerator(this, (IWidgetWrapper) element, true, controllerName);
					childGenerator.setParentCollection((ICollectionWrapper<?>) menuManager);
					childGenerator.buildControl(menuAboutToShowBuffer);
				}
			}
			if (menuManager instanceof HeaderMenuManagerWrapper && !(parentGenerator instanceof ExtendTreeGenerator))
				afterCreateControlBuffer.append("((" + getObjectName(ATabularController.class)
						+ "<?>) getMenuContainer()).setHeaderMenu(getControl().getMenu());");
		}
		if (controlWrapper instanceof ScrolledFormWrapper && ((ScrolledFormWrapper) controlWrapper).isFormMessageManager()) {
			afterInitializeBuffer.append("setFormMessageManager(true);");
		}
		if (controlWrapper instanceof FormPageType && !((FormPageType) controlWrapper).isFormMessageManager()) {
			if (ReflectionTools.hasInterface(controllerClass, IDirtyableForm.class))
				afterInitializeBuffer.append("setFormMessageManager(false);");
		}
		if (controlWrapper instanceof ColumnFieldType) {
			((ATabularGenerator) parentGenerator).addColumn(this, (ITableColumnWrapper) controlWrapper, parentCollectionName);
		}
		if (controlWrapper instanceof TreeWrapper) {
			String expand = keyWordGenerator.evalExpression(beforeCreateControlBuffer, ((TreeWrapper) controlWrapper).getExpand(),
					false, false);
			if (!isEmpty(expand) && !"null".equals(expand)) {
				if ("all".equals(expand))
					beforeCreateControlBuffer.append("levelToExpand = -1;");
				else
					beforeCreateControlBuffer.append("levelToExpand = " + Integer.valueOf(expand) + ";");
			}
		}
		if (controlWrapper instanceof IItemCollectionWrapper
				&& !isEmpty(((IItemCollectionWrapper<?>) controlWrapper).getSelection())) {
			afterSynchronizeBuffer.append("setSelection(" + keyWordGenerator.evalExpression(afterSynchronizeBuffer,
					((IItemCollectionWrapper<?>) controlWrapper).getSelection(), false, false) + ");");
		}
		if (controlWrapper instanceof GMapType)
			afterCreateControlBuffer.append("gmap.createContents();");

		/*
		 * Flush all buffers
		 */
		flush(bufferCode);
		return controllerName;
	}

	protected void addActionInit(ActionType action) throws IOException {
		beforeCreateControlBuffer.append("@Override");
		beforeCreateControlBuffer.appendPlus("public void init() {");
		beforeCreateControlBuffer.append("super.init();");
		if (!EngineTools.isEmpty(action.getImageDescriptor()))
			beforeCreateControlBuffer.append("setImageDescriptor(" + //
					keyWordGenerator.evalExpression(beforeCreateControlBuffer, action.getImageDescriptor(), false, false) + ");");
		if (!EngineTools.isEmpty(action.getText()))
			beforeCreateControlBuffer.append("setText(" + //
					keyWordGenerator.evalExpr2Str(beforeCreateControlBuffer, action.getText(), false) + ");");
		if (!EngineTools.isEmpty(action.getToolTipText()))
			beforeCreateControlBuffer.append("setToolTipText(" + //
					keyWordGenerator.evalExpr2Str(beforeCreateControlBuffer, action.getToolTipText(), false) + ");");
		beforeCreateControlBuffer.appendMinus("}");
	}

	/**
	 * Adds the values buffer.
	 * 
	 * @param methodName
	 *            the method name
	 * @param valueList
	 *            the value list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void addValuesBuffer(String methodName, String valueList, String componentType) throws IOException {
		BufferCode valuesBC = addBuffer(methodName, "public " + getObjectName(List.class) + " " + methodName + "()");
		String values = keyWordGenerator.evalExpression(valuesBC, valueList, false, false);
		if (values.equals(valueList)) {
			// No computed value so create a list of string split by commas
			StringBuffer valuesSB = new StringBuffer("return ").append(getObjectName(Arrays.class))
					.append(".asList(new " + componentType + "[] {");
			valuesSB.append(CodeGenerationUtil.addValueList(valueList,
					String.class.equals(controlFieldType) || String.class.equals(controlFieldType.getComponentType())));
			valuesBC.append(valuesSB.append("});").toString());
		} else
			valuesBC.append("return " + values + ";");
	}

	private void addValidatorCode(String validatorType, String when, String key) throws IOException {
		afterCreateControlBuffer.appendPlus("private boolean has" + (validatorType.equals("ERROR") ? "Error" : "Warning") + "() {");
		CodeGenerationUtil.addCode(afterCreateControlBuffer, when);
		afterCreateControlBuffer.appendMinus("}");
	}

	/**
	 * Adds the code.
	 * 
	 * @param bufferName
	 *            the buffer name
	 * @param declaration
	 *            the declaration
	 * @param expressions
	 *            the expressions
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void addCode(String bufferName, String declaration, String code) throws IOException {
		BufferCode bufferCode = addBuffer(bufferName, declaration);
		CodeGenerationUtil.addCode(bufferCode, code);
	}

	/**
	 * Checks for override.
	 * 
	 * @return true, if successful
	 */
	private boolean hasOverride() {
		return (afterInstantiateControllerBuffer.length() > 0 || paramCreateControlBuffer.length() > 0
				|| beforeEntityInjectBuffer.length() > 0 || afterEntityInjectBuffer.length() > 0
				|| beforeInitializeBuffer.length() > 0 || afterInitializeBuffer.length() > 0
				|| afterCreateControlBuffer.length() > 0 || beforeCreateControlBuffer.length() > 0
				|| beforeSynchronizeBuffer.length() > 0 || afterSynchronizeBuffer.length() > 0
				|| afterStartLifeCycleBuffer.length() > 0 || beforeEndLifeCycleBuffer.length() > 0
				|| afterEndLifeCycleBuffer.length() > 0 || afterEndLifeCycleValidBuffer.length() > 0 || !extraBufferMap.isEmpty())
				&& null != controllerName;
	}

	/**
	 * Flush.
	 * 
	 * @param bufferCode
	 *            the buffer code
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void flush(BufferCode bufferCode) throws IOException {
		if (!isReferenced)
			if (hasOverride())
				classBodyBuffer.appendPlus(controlImplSB.append(" {").toString());
			else
				classBodyBuffer.append(controlImplSB.append(";").toString());
		if (afterInstantiateControllerBuffer.length() > 0) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void afterInstantiateController() {");
			if (afterInstantiateControllerBuffer.length() > 0)
				classBodyBuffer.append(afterInstantiateControllerBuffer.getStringBuffer());
			classBodyBuffer.append("super.afterInstantiateController();");
			classBodyBuffer.appendMinus("}");
		}
		if (beforeEntityInjectBuffer.length() > 0) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void beforeEntityInjection() {");
			if (beforeEntityInjectBuffer.length() > 0)
				classBodyBuffer.append(beforeEntityInjectBuffer.getStringBuffer());
			classBodyBuffer.append("super.beforeEntityInjection();");
			classBodyBuffer.appendMinus("}");
		}
		if (afterEntityInjectBuffer.length() > 0) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void afterEntityInjection() {");
			if (beforeEntityInjectBuffer.length() > 0)
				classBodyBuffer.append(afterEntityInjectBuffer.getStringBuffer());
			classBodyBuffer.append("super.afterEntityInjection();");
			classBodyBuffer.appendMinus("}");
		}
		if (beforeInitializeBuffer.length() > 0 || afterInitializeBuffer.length() > 0 || paramCreateControlBuffer.length() > 0) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void initialize() {");
			if (paramCreateControlBuffer.length() > 0)
				classBodyBuffer.append(paramCreateControlBuffer.getStringBuffer());
			if (beforeInitializeBuffer.length() > 0)
				classBodyBuffer.append(beforeInitializeBuffer.getStringBuffer());
			classBodyBuffer.append("super.initialize();");
			if (afterInitializeBuffer.length() > 0)
				classBodyBuffer.append(afterInitializeBuffer.getStringBuffer());
			classBodyBuffer.appendMinus("}");
		}
		if (beforeCreateControlBuffer.length() > 0 || afterCreateControlBuffer.length() > 0) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void createControl() {");
			if (beforeCreateControlBuffer.length() > 0)
				appendBufferIfvalid(beforeCreateControlBuffer);
			classBodyBuffer.append("super.createControl();");
			if (afterCreateControlBuffer.length() > 0)
				appendBufferIfvalid(afterCreateControlBuffer);
			classBodyBuffer.appendMinus("}");
		}
		if (beforeSynchronizeBuffer.length() > 0 || afterSynchronizeBuffer.length() > 0) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void synchronize() {");
			if (beforeSynchronizeBuffer.length() > 0)
				appendBufferIfvalid(beforeSynchronizeBuffer);
			classBodyBuffer.append("super.synchronize();");
			if (afterSynchronizeBuffer.length() > 0)
				appendBufferIfvalid(afterSynchronizeBuffer);
			classBodyBuffer.appendMinus("}");
		}
		if (afterStartLifeCycleBuffer.length() > 0) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void startLifeCycle() {");
			classBodyBuffer.append("super.startLifeCycle();");
			classBodyBuffer.append(afterStartLifeCycleBuffer.getStringBuffer());
			classBodyBuffer.appendMinus("}");
		}
		if (beforeEndLifeCycleBuffer.length() > 0 || afterEndLifeCycleBuffer.length() > 0
				|| afterEndLifeCycleValidBuffer.length() > 0) {
			classBodyBuffer.append("@Override");
			classBodyBuffer.appendPlus("public void endLifeCycle() {");
			appendBufferIfvalid(beforeEndLifeCycleBuffer);
			classBodyBuffer.append("delegateAfterEndLifeCycleListener = true;");
			classBodyBuffer.append("super.endLifeCycle();");
			// classBodyBuffer.append("syncLocking(getBindingService());");
			appendBufferIfvalid(afterEndLifeCycleBuffer);
			classBodyBuffer.append(afterEndLifeCycleValidBuffer.getStringBuffer());
			classBodyBuffer.appendMinus("}");
		}
		for (BufferCode buffer : extraBufferMap.values()) {
			if (buffer instanceof ControlBufferCode) {
				ControlBufferCode controlBuffer = (ControlBufferCode) buffer;
				if (!controlBuffer.getMethodInvocation().startsWith("private"))
					classBodyBuffer.append("@Override");
				classBodyBuffer.appendPlus(controlBuffer.getMethodInvocation() + " {");
				classBodyBuffer.append(buffer.getStringBuffer());
				classBodyBuffer.appendMinus("}");
			} else {
				classBodyBuffer.append(buffer.getStringBuffer());
			}
		}
		if (hasOverride())
			classBodyBuffer.appendMinus("};");
		if (0 < parentBodyBuffer.length())
			classBodyBuffer.append(parentBodyBuffer.getStringBuffer());

		if (controlWrapper instanceof IncludeWrapper)
			classBodyBuffer.appendMinus("}");
		bufferCode.append(classBodyBuffer.getStringBuffer());
		if (0 < afterInstantiationBuffer.length())
			bufferCode.append(afterInstantiationBuffer.getStringBuffer());

		parentGenerator.getImportStatics().addAll(importStatics);
		parentGenerator.getImports().addAll(imports);
		parentGenerator.getDeclarationBuffer().append(declarationBuffer.getStringBuffer());
	}

	private void appendBufferIfvalid(BufferCode buffer) throws IOException {
		if (!buffer.isEmpty()) {
			classBodyBuffer.appendPlus("if (isValid()) {");
			classBodyBuffer.append(buffer.getStringBuffer());
			classBodyBuffer.appendMinus("}");
		}
	}

	/**
	 * Adds the buffer.
	 * 
	 * @param key
	 *            the key
	 * @param invocation
	 *            the invocation
	 * @return the control buffer code
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ControlBufferCode addBuffer(String key, String invocation) throws IOException {
		return addBuffer(key, invocation, classBodyBuffer.getIdent() + 2);
	}

	public ControlBufferCode addBuffer(String key, String invocation, String property) throws IOException {
		ControlBufferCode buffer = addBuffer(key, invocation, classBodyBuffer.getIdent() + 2);
		buffer.setProperty(property);
		return buffer;
	}

	/**
	 * Adds the buffer.
	 * 
	 * @param key
	 *            the key
	 * @param invocation
	 *            the invocation
	 * @param ident
	 *            the ident
	 * @return the control buffer code
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private ControlBufferCode addBuffer(String key, String invocation, int ident) throws IOException {
		ControlBufferCode buffer = new ControlBufferCode(this, ident, invocation, key);
		extraBufferMap.put(key, buffer);
		return buffer;
	}

	/**
	 * Process validators.
	 * 
	 * @param controlWrapper
	 *            the control wrapper
	 * @param controllerName
	 *            the controller name
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void processValidators(IValidableWrapper controlWrapper, String controllerName) throws IOException {
		if (null != controlWrapper.getValidators()) {
			for (ValidatorType validator : controlWrapper.getValidators().getValidator()) {
				if (null == validator.getValidatorClassName()) {
					if (isEmpty(validator.getErrorWhen()) && isEmpty(validator.getWarningWhen())) {
						logError("A validator of element '" + controllerName
								+ "' has no class and no code. Don't know what to do! validator is skipped.");
					} else if (isEmpty(validator.getKey())) {
						logError("A validator of element '" + controllerName
								+ "' has no key. Don't know what to do! validator is skipped.");
					} else {
						StringBuffer addValidatorSB = new StringBuffer("addValidator(new ").append(getObjectName(AValidator.class))
								.append("(this, ");
						addValidatorSB.append(CodeGenerationUtil.betweenQuotes(validator.getKey()));
						if (null != validator.getHostingControllerIds()) {
							StringTokenizer tokenizer = new StringTokenizer(validator.getHostingControllerIds(), ",");
							while (tokenizer.hasMoreTokens()) {
								String token = tokenizer.nextToken().trim();
								addValidatorSB.append(", (")
										.append(getObjectName(IValidableController.class) + ") getFromRegister(")
										.append(CodeGenerationUtil.betweenQuotes(token)).append(")");
							}
						}
						addValidatorSB.append(") {");
						afterCreateControlBuffer.appendPlus(addValidatorSB.toString());
						if (!isEmpty(validator.getErrorWhen()))
							addValidatorCode("ERROR", validator.getErrorWhen(), validator.getKey());
						if (!isEmpty(validator.getWarningWhen()))
							addValidatorCode("WARNING", validator.getWarningWhen(), validator.getKey());

						afterCreateControlBuffer.appendPlus("public int validate() {");

						if (!isEmpty(validator.getErrorWhen())) {
							afterCreateControlBuffer.appendPlus("if (hasError()) {");
							afterCreateControlBuffer.append("return setLevel(IMessageProvider.ERROR, "
									+ keyWordGenerator.evalExpr2Str(afterCreateControlBuffer, validator.getErrorMessage(), false)
									+ ");");
						}
						if (!isEmpty(validator.getWarningWhen())) {
							if (!isEmpty(validator.getErrorWhen()))
								afterCreateControlBuffer.appendMinusPlus("} else if (hasWarning()) {");
							else
								afterCreateControlBuffer.appendPlus("if (hasWarning()) {");
							afterCreateControlBuffer.append("return setLevel(IMessageProvider.WARNING, "
									+ keyWordGenerator.evalExpr2Str(afterCreateControlBuffer, validator.getWarningMessage(), false)
									+ ");");

						}
						afterCreateControlBuffer.appendMinusPlus("} else {");
						afterCreateControlBuffer.append(
								"return setLevel(" + parentGenerator.getObjectName(IMessageProvider.class) + ".NONE" + ", null);");
						afterCreateControlBuffer.appendMinus("}");
						afterCreateControlBuffer.appendMinus("};");
						afterCreateControlBuffer.appendMinus("});");
					}
				} else {
					afterCreateControlBuffer.append("addValidator(new "
							+ parentGenerator.getObjectName(getClazz(validator.getValidatorClassName())) + "(this, "
							+ keyWordGenerator.evalExpr2Str(afterCreateControlBuffer, validator.getKey(), false) + "));");
				} // END if (null == validator.getValidatorClassName())
			} // END for (ValidatorType validator :
		}
	}

	/**
	 * Gets the before create control buffer.
	 * 
	 * @return the before create control buffer
	 */
	public BufferCode getBeforeCreateControlBuffer() {
		return beforeCreateControlBuffer;
	}

	/**
	 * Gets the param create control buffer.
	 *
	 * @return the param create control buffer
	 */
	public BufferCode getParamCreateControlBuffer() {
		return paramCreateControlBuffer;
	}

	/**
	 * Gets the after create control buffer.
	 * 
	 * @return the after create control buffer
	 */
	public BufferCode getAfterCreateControlBuffer() {
		return afterCreateControlBuffer;
	}

	/**
	 * Gets the controller class.
	 * 
	 * @return the controller class
	 */
	public Class<?> getControllerClass() {
		return controllerClass;
	}

	/**
	 * Gets the generated class name.
	 * 
	 * @return the generated class name
	 */
	private String getGeneratedClassName() {
		ACodeGenerator classGenerator = this;
		while (!(classGenerator instanceof AClassGenerator))
			classGenerator = ((ControlGenerator) classGenerator).getParentGenerator();
		return ((AClassGenerator) classGenerator).getClassName();
	}

	/**
	 * Gets the control field type.
	 * 
	 * @return the control field type
	 */
	public Class<?> getControlFieldType() {
		return controlFieldType;
	}

	/**
	 * Adds the lazy fetch.
	 * 
	 * @param lazyFetchesContainer
	 *            the lazy fetches container
	 * @param column
	 *            the column
	 */
	private void addLazyFetch(ILazyFetchesContainer lazyFetchesContainer, String column) {
		String lazyFetches = lazyFetchesContainer.getLazyFetches();
		if (isEmpty(lazyFetches))
			lazyFetches = column;
		else
			lazyFetches = lazyFetches + "," + column;
		lazyFetchesContainer.setLazyFetches(lazyFetches);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.ACodeGenerator#getControllerSequence()
	 */
	@Override
	public int getControllerSequence() {
		return parentGenerator.getControllerSequence();
	}

	/**
	 * Gets the controller name.
	 * 
	 * @return the controller name
	 */
	public String getControllerName() {
		return controllerName;
	}

	public KeyWordGenerator getKeyWordGenerator() {
		return keyWordGenerator;
	}

	public IElementWrapper getControlWrapper() {
		return controlWrapper;
	}

	public String getThis() {
		return controllerName;
	}

	public PluginEntity getPluginEntity() {
		return pluginEntity;
	}
}
