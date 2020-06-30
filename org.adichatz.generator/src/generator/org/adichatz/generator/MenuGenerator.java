/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant 
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.core.AMenuCore;
import org.adichatz.engine.core.ARootMenuCore;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.common.GeneratorUtil.FieldCaseEnum;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tools.TransformTreeTools;
import org.adichatz.generator.wrapper.ItemWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.xjc.AccessibilityType;
import org.adichatz.generator.xjc.ItemType;
import org.adichatz.generator.xjc.MenuType;
import org.adichatz.generator.xjc.NavigatorTree;
import org.adichatz.generator.xjc.NodeType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.scenario.ScenarioInput;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuGenerator.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class MenuGenerator extends AConfigGenerator {
	public Map<String, Integer> controllerSuffixMap = new HashMap<>();

	/** The tree wrapper. */
	protected MenuType menu;

	/** The defaul plugin resources name. */
	protected String defaulPluginResourcesName;

	/** The has item. */
	protected boolean hasItem = false;

	private String createChildrenSignature;

	/**
	 * Instantiates a new menu generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public MenuGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput, null);
		this.menu = (MenuType) scenarioInput.getXmlElement();

		Class<?> parentMenuCoreClass;
		if (menu instanceof NavigatorTree) {
			new TransformTreeTools(scenarioInput, (NavigatorTree) menu);
			createChildrenSignature = "public";
			parentMenuCoreClass = ARootMenuCore.class;
		} else {
			parentMenuCoreClass = AMenuCore.class;
			createChildrenSignature = "protected";
		}
		createClassFile(scenarioInput, " extends " + getObjectName(parentMenuCoreClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.AClassGenerator#addBlocks()
	 */
	@Override
	protected void addBlocks() throws IOException {

		classBodyBuffer.append("");
		classBodyBuffer.appendPlus("public " + scenarioInput.getTreeId() + "(" //
				+ ((menu instanceof NavigatorTree) ? getObjectName(MenuController.class) + " rootMenuController"
						: getObjectName(AMenuCore.class) + " parentMenuCore") //
				+ ") {");
		classBodyBuffer.append("super(\"" + scenarioInput.getScenarioResources().getPluginName() //
				+ "\", " + ((menu instanceof NavigatorTree) ? "rootMenuController" : "parentMenuCore") + ");");
		classBodyBuffer.appendMinus("}");
		classBodyBuffer.append("");

		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Creates children (menu or item) for menu " + menu.getId());
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus(createChildrenSignature + " void createChildren() {");

		addMenu(menu);

		classBodyBuffer.appendMinus("}");
		if (menu instanceof NavigatorTree)
			CodeGenerationUtil.addAdditionalCode(classBodyBuffer, ((NavigatorTree) menu).getAdditionalCode());
	}

	private void addChildren(MenuType menu, String controllerName) throws IOException {
		List<String> evaluatorNames = new ArrayList<String>();
		for (NodeType node : menu.getMenuOrItem()) {
			if (node instanceof MenuType) {
				String menuId = EngineTools.upperCaseFirstLetter(node.getId().concat("MENU"));
				classBodyBuffer.append("");
				classBodyBuffer.append("// Create menu " + menuId);
				String menuName = addMenu((MenuType) node);
				classBodyBuffer.append(controllerName + ".getChildren().add(" + menuName + ");");
				// ScenarioInput menuScenarioInput = new ScenarioInput(scenarioInput, node,
				// EngineTools.getAdiResourceURI(scenarioInput.getSubPackage(), menuId));
				// menuScenarioInput.setAdiResourceURI(scenarioInput.getAdiResourceURI());
				// new MenuGenerator(menuScenarioInput);
			} else if (node instanceof ItemType) {
				addItemDeclaration();
				ItemWrapper item = (ItemWrapper) node;

				classBodyBuffer.append("");
				classBodyBuffer.append("// Create item " + item.getId());
				classBodyBuffer.appendPlus("item = new " + getObjectName(getNodeClass(node)) + "(pluginResources, this, "
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, item.getId(), false) //
						+ ", " + keyWordGenerator.evalExpr2Str(classBodyBuffer, item.getLabel(), false) //
						+ ", " + keyWordGenerator.evalExpr2Str(classBodyBuffer, item.getToolTipText(), false) //
						+ ") {");

				classBodyBuffer.append("@Override");
				classBodyBuffer.appendPlus("public void handleActivate() {");
				String context = getObjectName(IEclipseContext.class);
				classBodyBuffer
						.append(context + " context = (" + context + ") getTransientData().get(" + context + ".class.getName());");
				classBodyBuffer.append(getObjectName(E4AdichatzApplication.class) + ".openPart(context, getParamMap());");
				classBodyBuffer.appendMinus("}");

				classBodyBuffer.append("@Override");
				classBodyBuffer.appendPlus("public " + getObjectName(ParamMap.class) + " getParamMap() {");
				classBodyBuffer.append("paramMap = new ParamMap();");
				CodeGenerationUtil.addParams(classBodyBuffer, "paramMap.", item.getParams(), false);

				boolean hasPluginResourcesParam = false;
				if (null != item.getParams().getParam())
					for (ParamType param : item.getParams().getParam()) {
						if (ParamMap.PLUGIN_RESOURCES.equals(param.getId())) {
							hasPluginResourcesParam = true;
							break;
						}
					}
				if (!hasPluginResourcesParam)
					classBodyBuffer
							.append("paramMap.put(" + getObjectName(ParamMap.class) + ".PLUGIN_RESOURCES, pluginResources);");

				if (null != item.getCustomizations()) {
					String customizationName = EngineTools.upperCaseFirstLetter(scenarioInput.getTreeId()).concat("$Customization")
							.concat(String.valueOf(getControllerSequence()));
					new CustomizationGenerator(scenarioInput, customizationName, item.getCustomizations());
					classBodyBuffer.append("paramMap.put(ParamMap.CUSTOMIZATION, new " + customizationName + "());");
				}
				classBodyBuffer.append("return paramMap;");
				classBodyBuffer.appendMinus("}");
				if (null != item.getImage()) {
					classBodyBuffer.appendPlus("public " + getObjectName(Image.class) + " getImage() {");
					classBodyBuffer.append("return " + keyWordGenerator.getImage(classBodyBuffer, item.getImage()) + ";");
					classBodyBuffer.appendMinus("}");
				}
				classBodyBuffer.appendMinus("};");
				classBodyBuffer.append(controllerName + ".getChildren().add(item);");
				if (null != item.getAccessibilities()) {
					for (AccessibilityType filter : item.getAccessibilities().getAccessibility()) {
						if (!evaluatorNames.contains(filter.getType().name())) {
							evaluatorNames.add(filter.getType().name());
							addPropertyField(classBodyBuffer, "evaluator" + filter.getType().name(), BooleanSupplier.class, false);
						}
					}
					CodeGenerationUtil.addAccessibilities(classBodyBuffer, item, "item", true, false);
				}
			}
		}
	}

	private String addMenu(MenuType menu) throws IOException {
		String controllerName;
		String firstMember;
		if (menu instanceof NavigatorTree) {
			firstMember = "";
			controllerName = "coreController";
		} else {
			firstMember = getObjectName(getNodeClass(menu)) + " ";
			controllerName = getControllerName(
					GeneratorUtil.getJavaName(menu.getId(), FieldCaseEnum.LOWER_CASE_FIRST_LETTER, false));
		}
		classBodyBuffer.append(firstMember + controllerName + " = new " + getObjectName(getNodeClass(menu))
				+ "(pluginResources, this, " + keyWordGenerator.evalExpr2Str(classBodyBuffer, menu.getId(), false) + ", " //
				+ keyWordGenerator.evalExpr2Str(classBodyBuffer, menu.getLabel(), false) + ", " //
				+ keyWordGenerator.evalExpr2Str(classBodyBuffer, menu.getToolTipText(), false) + ")"
				+ (null != menu.getImage() ? " {" : ";"));
		if (null != menu.getImage()) {
			classBodyBuffer.addIdent(1);
			classBodyBuffer.appendPlus("public " + getObjectName(Image.class) + " getImage() {");
			classBodyBuffer.append("return " + keyWordGenerator.getImage(classBodyBuffer, menu.getImage()) + ";");
			classBodyBuffer.appendMinus("}");
			classBodyBuffer.appendMinus("};");
		}
		if (menu.isExpanded())
			classBodyBuffer.append(controllerName + ".setExpanded(true);");
		CodeGenerationUtil.addAccessibilities(classBodyBuffer, (IElementWrapper) menu, controllerName, true, false);
		addChildren(menu, controllerName);
		return controllerName;
	}

	protected Class<?> getNodeClass(NodeType node) {
		if (!EngineTools.isEmpty(node.getControllerClassName())) {
			return scenarioInput.getScenarioResources().getGencodePath().getClazz(node.getControllerClassName(), false);
		} else
			return (node instanceof MenuType) ? MenuController.class : ItemController.class;
	}

	/**
	 * Adds the item declaration.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void addItemDeclaration() throws IOException {
		if (!hasItem) {
			hasItem = true;
			classBodyBuffer.append(getObjectName(ItemController.class) + " item;");
		}
	}

	private String getControllerName(String controllerId) {
		String controllerName;
		Integer index = controllerSuffixMap.get(controllerId);
		if (null == index) {
			index = 1;
			controllerName = controllerId;
		} else
			controllerName = controllerId + (++index);
		controllerSuffixMap.put(controllerId, index);
		return controllerName;
	}
}
