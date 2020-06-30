/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant 
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe � 
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, 
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�. 
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.generator;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.CTabFolderController;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.MultiBoundedPart;
import org.adichatz.engine.e4.part.PartCore;
import org.adichatz.engine.e4.resource.APartNavigation;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tools.InspectTools;
import org.adichatz.generator.wrapper.CTabFolderWrapper;
import org.adichatz.generator.wrapper.PartTreeWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.xjc.CTabFolderType;
import org.adichatz.generator.xjc.CTabItemType;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.generator.xjc.FormPageType;
import org.adichatz.generator.xjc.NavigationPathType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.util.ScenarioUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class FormEditorGenerator.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class PartTreeGenerator extends AConfigGenerator {

	/** The tree wrapper. */
	private final PartTreeWrapper treeWrapper;

	private boolean severalPages = false;

	/**
	 * Instantiates a new part generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public PartTreeGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput, null);
		this.treeWrapper = (PartTreeWrapper) scenarioInput.getXmlElement();

		scenarioInput.getRegister().clear();
		scenarioInput.getBundleTranslation().clear();
		scenarioInput.setRootWrapper(treeWrapper);

		scenarioInput.setPluginEntity(treeWrapper.getEntityURI());

		legacyVersion = ScenarioUtil.isLegacyVersion(scenarioInput);
		Class<?> extendedClass;
		if (legacyVersion)
			extendedClass = getClazz(GeneratorConstants.FORM_EDITOR_CORE);
		else {
			extendedClass = PartCore.class;
			// If more than 1 page, extendedClass = MultiPageCore
			boolean first = true;
			for (ElementType element : treeWrapper.getFormPageOrInclude()) {
				if (element instanceof FormPageType) {
					if (first)
						first = false;
					else {
						severalPages = true;
						break;
					}
				}
			}
		}
		createClassFile(scenarioInput, " extends " + getParentCoreName(treeWrapper.getCoreClassName(), extendedClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.AClassGenerator#addBlocks()
	 */
	@Override
	protected void addBlocks() throws IOException {
		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Instantiates a new " + className + ".");
		classBodyBuffer.append(" *");
		classBodyBuffer.append(" * @param pluginResources");
		classBodyBuffer.append(" *            the plugin resources");
		classBodyBuffer.append(" * @param partController");
		classBodyBuffer.append(" *            the part controller");
		classBodyBuffer.append(" * @param paramMap");
		classBodyBuffer.append(" *            the param map");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(" + getObjectName(AdiPluginResources.class) + " pluginResources, "
				+ getObjectName(IContainerController.class) + " partController, " + getObjectName(ParamMap.class) + " paramMap) {");
		classBodyBuffer.append("super(pluginResources, \"" + scenarioInput.getTreeId() + "\", \"" + scenarioInput.getSubPackage()
				+ "\", paramMap);");
		classBodyBuffer.append("this.coreController = partController;");

		classBodyBuffer.append("");

		/* Inspects and transforms Form Editor tree */
		InspectTools.inspectPartTree(scenarioInput, treeWrapper);
		classBodyBuffer.append("");
		try {
			if (null != treeWrapper.getConfig()) {
				addConfigCustomizations(treeWrapper.getConfig());
				if (null != treeWrapper.getConfig().getNavigationPaths()) {
					boolean first = true;
					for (NavigationPathType navigationPath : treeWrapper.getConfig().getNavigationPaths().getNavigationPath()) {
						if (first) {
							classBodyBuffer.append("navigations = new " + getObjectName(ArrayList.class) + "<"
									+ getObjectName(APartNavigation.class) + ">();");
							classBodyBuffer.append(getObjectName(APartNavigation.class) + " navigation;");
							first = false;
						}
						classBodyBuffer.appendPlus("navigation = new " + getObjectName(APartNavigation.class) + "(("
								+ getObjectName(MultiBoundedPart.class) + ") partController, "
								+ keyWordGenerator.evalExpr2Str(classBodyBuffer, navigationPath.getPath(), false) + ", "
								+ keyWordGenerator.evalExpr2Str(classBodyBuffer, navigationPath.getName(), false) + ") {");
						classBodyBuffer.appendPlus("public void run() {");
						StringTokenizer tokenizer = new StringTokenizer(navigationPath.getPath(), ",");
						String parentControllerName = null;
						StringBuffer activeSB = new StringBuffer();
						ICollectionWrapper<?> parentWrapper = treeWrapper;
						while (tokenizer.hasMoreTokens()) {
							String controllerId = tokenizer.nextToken().trim();
							ElementType element = getChild(parentWrapper, controllerId);
							if (null == element)
								logError("Element '" + controllerId + "' does not exist for navigation '" + navigationPath.getPath()
										+ "'!?");
							else {
								if (element instanceof FormPageType) {
									classBodyBuffer.append("boundePart.activatePage(\"" + element.getId() + "\");");
									parentWrapper = (ICollectionWrapper<?>) element;
									activeSB.append("boundePart.getPageManagerMap().get(\"" + element.getId() + "\").isValid()");
								} else if (element instanceof CTabFolderType) {
									parentControllerName = getControllerName(element.getId(), CTabFolderWrapper.class);
									parentWrapper = (ICollectionWrapper<?>) element;
									classBodyBuffer.append(getObjectName(CTabFolderController.class) + " " + parentControllerName
											+ " = (" + getObjectName(CTabFolderController.class) + ") getFromRegister(\""
											+ element.getId() + "\");");
								} else if (element instanceof CTabItemType) {
									parentWrapper = (ICollectionWrapper<?>) element;
									classBodyBuffer.append(parentControllerName + ".setSelection(\"" + element.getId() + "\");");
								} else if (element instanceof ICollectionWrapper) {
									parentWrapper = (ICollectionWrapper<?>) element;
								}
							}
						}
						classBodyBuffer.appendMinus("}");
						classBodyBuffer.appendPlus("public boolean isEnabled() {");
						classBodyBuffer.append("return " + activeSB.toString() + ";");
						classBodyBuffer.appendMinus("}");
						classBodyBuffer.appendMinus("};");
						classBodyBuffer.append("navigations.add(navigation);");
					}
				}
			}
		} catch (IllegalArgumentException e) {
			logError(e);
		}

		addResourceBundleTranslation(treeWrapper.getConfig());
		if (null != treeWrapper.getConfig())
			CodeGenerationUtil.addParams(classBodyBuffer, "paramMap.", treeWrapper.getConfig().getParams(), false);

		String partClassName = null;
		if (!legacyVersion) {
			partClassName = getObjectName(BoundedPart.class);
			if (!EngineTools.isEmpty(treeWrapper.getOutlinePageClassName()))
				if (severalPages)
					classBodyBuffer.append("((" + partClassName + ") partController).setSeveralPages(true);");
		}
		if (!EngineTools.isEmpty(treeWrapper.getBindingServiceClassName())) {
			partClassName = getObjectName(legacyVersion ? getClazz(GeneratorConstants.AFORM_EDITOR) : BoundedPart.class);
			Class<?> bindingServiceClass = getClazz(treeWrapper.getBindingServiceClassName());
			classBodyBuffer.append("((" + partClassName + ") partController).setBindingService(new "
					+ getObjectName(bindingServiceClass) + "((" + partClassName + ") partController));");
		}

		classBodyBuffer.appendMinus("}\n");
		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Create contents for FormPage");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public void " + ACollectionGenerator.METHOD_CREATE_CONTENTS + "(){");

		if (!legacyVersion) {
			partClassName = getObjectName(BoundedPart.class);
			if (!EngineTools.isEmpty(treeWrapper.getOutlinePageClassName()))
				classBodyBuffer.append("outlinePage = new " + getObjectName(getClazz(treeWrapper.getOutlinePageClassName())) + "(("
						+ partClassName + ") coreController);");
		}

		classBodyBuffer.append("// adds formPage headers or includes.");
		addCollection(scenarioInput, treeWrapper, "coreController");
		classBodyBuffer.append("firePostOpenPart();");
		classBodyBuffer.appendMinus("}");
		CodeGenerationUtil.addAdditionalCode(classBodyBuffer, treeWrapper.getAdditionalCode());
	}

	private ElementType getChild(ICollectionWrapper<?> parentWrapper, String elementId) {
		List<ICollectionWrapper<?>> collectionWrappers = new ArrayList<ICollectionWrapper<?>>();
		for (ElementType element : parentWrapper.getElements()) {
			if (elementId.equals(element.getId()))
				return element;
			if (element instanceof ICollectionWrapper)
				collectionWrappers.add((ICollectionWrapper<?>) element);
		}
		for (ICollectionWrapper<?> collectionWrapper : collectionWrappers) {
			ElementType element = getChild(collectionWrapper, elementId);
			if (null != element)
				return element;
		}
		return null;
	}
}
