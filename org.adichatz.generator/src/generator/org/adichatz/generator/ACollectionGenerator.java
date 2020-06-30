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

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IToolBarContainerController;
import org.adichatz.engine.core.ATabularCore;
import org.adichatz.engine.core.ATreeCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.PageManager;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.tools.AdditionalBufferCode;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.wrapper.GridWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.ManagedToolBarWrapper;
import org.adichatz.generator.wrapper.PGroupWrapper;
import org.adichatz.generator.wrapper.TableWrapper;
import org.adichatz.generator.wrapper.TreeWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.ICompositeWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IItemCollectionWrapper;
import org.adichatz.generator.wrapper.internal.IItemCompositeWrapper;
import org.adichatz.generator.wrapper.internal.IToolBarContainerWrapper;
import org.adichatz.generator.wrapper.internal.IWidgetWrapper;
import org.adichatz.generator.xjc.CollectionType;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.FieldContainerType;
import org.adichatz.generator.xjc.FormPageType;
import org.adichatz.generator.xjc.MenuManagerType;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class ACollectionGenerator.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class ACollectionGenerator extends AClassGenerator {

	private static Class<?> compositeGeneratorClass;

	boolean legacyVersion; // Version 3.8 or 3.7 of eclipse

	/**
	 * Instantiates a new a collection generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	protected ACollectionGenerator(ScenarioInput scenarioInput, ACodeGenerator parentGenerator) {
		super(scenarioInput, parentGenerator);
	}

	/**
	 * Adds the block.
	 * 
	 * @param parentCollection
	 *            the parent collection
	 * @param blockClassName
	 *            the block class name
	 * @param genCodeClass
	 *            the gen code class
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void addBlock(String blockClassName, String parentCollection, Class<?> genCodeClass) throws IOException {

		classBodyBuffer.append("");

		String blockInstanceName = EngineTools.lowerCaseFirstLetter(blockClassName);

		declarationBuffer.append("protected " + getObjectName(genCodeClass) + " " + blockInstanceName + ";");

		classBodyBuffer.append("// loads and instantiates the class " + blockClassName + ".");
		String subPackage = scenarioInput.getScenarioResources().getGencodePath().getGenCodePackage(scenarioInput.getSubPackage());
		String canonicalClassName = subPackage + "." + blockClassName;
		if (!subPackage.equals(classPackage))
			imports.add(canonicalClassName);

		classBodyBuffer.append(blockInstanceName + "  = new " + blockClassName + "(context, " + parentCollection + ");");
	}

	/**
	 * Adds the collection.
	 * 
	 * @param parentCollectionName
	 *            the parent collection
	 * @param parentCollection
	 *            the parent collection
	 * @param compile
	 *            the compile
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("rawtypes")
	protected void addCollection(ScenarioInput scenarioInput, ICollectionWrapper parentCollection, String parentCollectionName)
			throws IOException {
		boolean first = true;
		for (Object element : parentCollection.getElements()) {
			if (element instanceof FormPageType) {
				FormPageType formPage = (FormPageType) element;
				((IWidgetWrapper) formPage).preCreate();
				String pageClassName = classPackage.concat(".").concat(scenarioInput.getTreeId())
						.concat(EngineTools.upperCaseFirstLetter(formPage.getId()));
				if (legacyVersion) {
					StringBuffer addFormPageHeaderSB = new StringBuffer("formPageHeaderMap.put(\"").append(formPage.getId())
							.append("\", new ");
					addFormPageHeaderSB.append(getObjectName(getClazz(GeneratorConstants.FORM_PAGE_HEADER)));
					addFormPageHeaderSB.append("(context, \"");
					addFormPageHeaderSB.append(pageClassName);
					addFormPageHeaderSB.append("\", ");
					if (null == formPage.getTitle()) {
						addFormPageHeaderSB.append("\"");
						addFormPageHeaderSB.append(formPage.getId());
						addFormPageHeaderSB.append("\"");
					} else {
						addFormPageHeaderSB.append(keyWordGenerator.evalExpr2Str(classBodyBuffer, formPage.getTitle(), false));
					}
					addFormPageHeaderSB.append(",");
					addFormPageHeaderSB.append(CodeGenerationUtil.betweenQuotes("org.adichatz.engine.indigo.editor.AdiFormPage"));
					if (!EngineTools.isEmpty(formPage.getValidStatus())) {
						if (-1 < formPage.getValidStatus().indexOf("IEntityConstants."))
							getObjectName(IEntityConstants.class);
						addFormPageHeaderSB.append(", ");
						addFormPageHeaderSB.append(formPage.getValidStatus().trim());
					}
					if (null != formPage.getValid())
						addValidClause(formPage, addFormPageHeaderSB);
					else {
						addFormPageHeaderSB.append("));");
						classBodyBuffer.append(addFormPageHeaderSB.toString());
					}
					classBodyBuffer.append("pageIds.add(\"" + formPage.getId() + "\");");
				} else {
					if (first) {
						first = false;
						classBodyBuffer.append(getObjectName(BoundedPart.class) + " boundedPart = ("
								+ getObjectName(BoundedPart.class) + ") coreController;");
					}

					StringBuffer addFormPageSB = new StringBuffer("boundedPart.addPageManager(" + "new ")
							.append(getObjectName(PageManager.class)).append("(\"");
					addFormPageSB.append(pageClassName).append("\", \"").append(formPage.getId()).append("\", ")
							.append(keyWordGenerator.evalExpr2Str(classBodyBuffer, formPage.getTitle(), false));
					if (!EngineTools.isEmpty(formPage.getImage()))
						addFormPageSB.append(", ").append(keyWordGenerator.getImage(classBodyBuffer, formPage.getImage()));

					if (!EngineTools.isEmpty(formPage.getValidStatus())) {
						if (-1 < formPage.getValidStatus().indexOf("IEntityConstants."))
							getObjectName(IEntityConstants.class);
						addFormPageSB.append(", ");
						addFormPageSB.append(formPage.getValidStatus().trim());
					}
					if (null != formPage.getValid())
						addValidClause(formPage, addFormPageSB);
					else {
						addFormPageSB.append("));");
						classBodyBuffer.append(addFormPageSB.toString());
					}
				}

				new FormPageGenerator(new ScenarioInput(scenarioInput, formPage), this);
			} else if (element instanceof FieldContainerType) {
				ICompositeWrapper compositeWrapper = (ICompositeWrapper) element;
				if (null != ((FieldContainerType) element).getEntityURI() || null != ((FieldContainerType) element).getEntity()) {
					instantiateCompositeGenerator(new ScenarioInput(scenarioInput, compositeWrapper));
					addBlock(scenarioInput.getTreeId() + EngineTools.upperCaseFirstLetter(compositeWrapper.getId()),
							parentCollectionName, compositeWrapper.getGencodeClass());
				} else {
					String controlName = buildControl(classBodyBuffer, compositeWrapper, true, parentCollectionName);
					addCollection(scenarioInput, ((ICollectionWrapper) element), controlName);
					if (compositeWrapper instanceof IToolBarContainerWrapper
							&& null != ((IToolBarContainerWrapper) compositeWrapper).getManagedToolBar()
							&& ((IToolBarContainerWrapper) compositeWrapper).getManagedToolBar().getActionOrMenuActionOrSeparator()
									.size() > 0) {
						ManagedToolBarWrapper managedToolBar = (ManagedToolBarWrapper) ((IToolBarContainerWrapper) compositeWrapper)
								.getManagedToolBar();
						addCollection(scenarioInput, managedToolBar, "((" + getObjectName(IToolBarContainerController.class) + ") "
								+ controlName + ").getMToolBarCtler()");
					} else if (element instanceof PGroupWrapper)
						CodeGenerationUtil.addPGroupItems(this, (PGroupWrapper) element, controlName);
				}
			} else if (element instanceof IItemCollectionWrapper) {
				IItemCollectionWrapper itemContainer = (IItemCollectionWrapper) element;
				String controlName = buildControl(classBodyBuffer, itemContainer, true, parentCollectionName);

				if (!EngineTools.isEmpty(itemContainer.getDelayed()))
					classBodyBuffer.append(controlName + ".setDelayed("
							+ keyWordGenerator.evalExpression(classBodyBuffer, itemContainer.getDelayed(), false, false) + ");");
				for (Object object : itemContainer.getElements()) {
					IItemCompositeWrapper item = (IItemCompositeWrapper) object;
					instantiateCompositeGenerator(new ScenarioInput(scenarioInput, item));
					addBlock(scenarioInput.getTreeId() + EngineTools.upperCaseFirstLetter(item.getId()), controlName,
							ControllerCore.class);
				}
			} else if (element instanceof TableWrapper) {
				GeneratorUtil.instantiateGenerator(scenarioInput, TableWrapper.class.getName(),
						new Class[] { ScenarioInput.class, ACodeGenerator.class },
						new Object[] { new ScenarioInput(scenarioInput, element), this });
				addBlock(scenarioInput.getTreeId() + EngineTools.upperCaseFirstLetter(((IElementWrapper) element).getId()),
						parentCollectionName, ATabularCore.class);
			} else if (element instanceof GridWrapper) {
				GeneratorUtil.instantiateGenerator(scenarioInput, GridWrapper.class.getName(),
						new Class[] { ScenarioInput.class, ACodeGenerator.class },
						new Object[] { new ScenarioInput(scenarioInput, element), this });
				addBlock(scenarioInput.getTreeId() + EngineTools.upperCaseFirstLetter(((IElementWrapper) element).getId()),
						parentCollectionName, ATabularCore.class);
			} else if (element instanceof TreeWrapper) {
				new TreeGenerator(new ScenarioInput(scenarioInput, element), this);
				addBlock(scenarioInput.getTreeId() + EngineTools.upperCaseFirstLetter(((IElementWrapper) element).getId()),
						parentCollectionName, ATreeCore.class);
			} else if (element instanceof IncludeWrapper) {
				buildControl(classBodyBuffer, (IncludeWrapper) element, true, parentCollectionName);
			} else if (element instanceof MenuManagerType) {
				buildControl(classBodyBuffer, (IElementWrapper) element, true, parentCollectionName);
			} else if (element instanceof ManagedToolBarWrapper) {
				String controlName = buildControl(classBodyBuffer, (ManagedToolBarWrapper) element, true, parentCollectionName);
				addCollection(scenarioInput, ((ICollectionWrapper) element), controlName);
			} else if (element instanceof ICollectionWrapper && element instanceof IWidgetWrapper) {
				// See MenuActionWrapper, ...
				IWidgetWrapper controlWrapper = (IWidgetWrapper) element;
				String controlName = buildControl(classBodyBuffer, controlWrapper, true, parentCollectionName);
				addCollection(scenarioInput, ((ICollectionWrapper) element), controlName);
			} else if (element instanceof IWidgetWrapper) {
				ControlGenerator controlGenerator = newControlGenerator((IWidgetWrapper) element, true, parentCollectionName);
				controlGenerator.setParentCollection(parentCollection);
				String collectionName;
				if (parentCollection instanceof FieldContainerType && element instanceof ControlFieldType) {
					// Use create Method for control field
					// The method could be invoked from other way (e.g. query column parameter).
					String createMethod = getCreateMethodName(((IWidgetWrapper) element).getId());
					classBodyBuffer.append(createMethod.concat("(this);"));
					BufferCode createMethodBuffer = addExtraBuffer("createMethodBuffer");
					createMethodBuffer.appendPlus("public " + getObjectName(controlGenerator.getControllerClass()) + " "
							+ createMethod + "(" + getObjectName(ControllerCore.class) + " genCode) {");
					collectionName = controlGenerator.buildControl(createMethodBuffer);
					createMethodBuffer.append("return " + collectionName + ";");
					createMethodBuffer.appendMinus("}");
				} else {
					collectionName = controlGenerator.buildControl(classBodyBuffer);
				}
			}
		}
		if (parentCollection instanceof CollectionType && null != ((CollectionType) parentCollection).getAdditionalCode()) {
			AdditionalBufferCode additionalBuffer = new AdditionalBufferCode(this, 1, parentCollectionName);
			extraBufferMap.put(additionalBuffer.getKeyBuffer(), additionalBuffer);
			CodeGenerationUtil.addAdditionalCode(additionalBuffer, ((CollectionType) parentCollection).getAdditionalCode());
		}
	}

	private void addValidClause(FormPageType formPage, StringBuffer addFormPageSB) throws IOException {
		classBodyBuffer.append(addFormPageSB.append(") {").toString());
		classBodyBuffer.addIdent(1);
		classBodyBuffer.append("@Override");
		classBodyBuffer.appendPlus("public boolean isValid() {");
		classBodyBuffer.append("return super.isValid() && ("
				+ keyWordGenerator.evalExpression(classBodyBuffer, formPage.getValid(), false, false) + ");");
		classBodyBuffer.appendMinus("}");
		classBodyBuffer.appendMinus("});");
	}

	private Class<?> getCompositeGeneratorClass() {
		if (null == compositeGeneratorClass) {
			compositeGeneratorClass = scenarioInput.getScenarioResources().getGenerator("#COMPOSITEGENERATOR()");
			if (null == compositeGeneratorClass)
				compositeGeneratorClass = CompositeGenerator.class;
		}
		return compositeGeneratorClass;
	}

	private CompositeGenerator instantiateCompositeGenerator(ScenarioInput scenarioInput) {
		return (CompositeGenerator) scenarioInput.getScenarioResources().getGencodePath().instantiateClass(
				getCompositeGeneratorClass(), new Class[] { ScenarioInput.class, ACodeGenerator.class },
				new Object[] { scenarioInput, this });
	}

	protected String getParentCoreName(String coreClassName, Class<?> defaultCoreClass) {
		Class<?> coreClass;
		if (EngineTools.isEmpty(coreClassName))
			coreClass = defaultCoreClass;
		else
			coreClass = getClazz(coreClassName);
		return getObjectName(coreClass);
	}
}
