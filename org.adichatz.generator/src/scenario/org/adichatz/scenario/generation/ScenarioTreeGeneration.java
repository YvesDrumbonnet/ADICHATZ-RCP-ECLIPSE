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
package org.adichatz.scenario.generation;

import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.action.ButtonController;
import org.adichatz.engine.controller.action.HelpButtonController;
import org.adichatz.engine.controller.action.MenuActionController;
import org.adichatz.engine.controller.action.MenuItemController;
import org.adichatz.engine.controller.action.SeparatorController;
import org.adichatz.engine.controller.action.ToolItemController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ArgTabFolderController;
import org.adichatz.engine.controller.collection.ButtonBarController;
import org.adichatz.engine.controller.collection.CTabFolderController;
import org.adichatz.engine.controller.collection.CTabItemController;
import org.adichatz.engine.controller.collection.CompositeBagController;
import org.adichatz.engine.controller.collection.CompositeController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.collection.FormPageController;
import org.adichatz.engine.controller.collection.GroupController;
import org.adichatz.engine.controller.collection.IncludeController;
import org.adichatz.engine.controller.collection.ManagedToolBarController;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.controller.collection.SashFormController;
import org.adichatz.engine.controller.collection.ScrolledCompositeController;
import org.adichatz.engine.controller.collection.ScrolledFormController;
import org.adichatz.engine.controller.collection.SectionController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.collection.ToolBarController;
import org.adichatz.engine.controller.collection.ATreeController;
import org.adichatz.engine.controller.field.CComboController;
import org.adichatz.engine.controller.field.CheckBoxController;
import org.adichatz.engine.controller.field.ComboController;
import org.adichatz.engine.controller.field.CompositeSeparatorController;
import org.adichatz.engine.controller.field.DateTextController;
import org.adichatz.engine.controller.field.EditableFormTextController;
import org.adichatz.engine.controller.field.ExtraTextController;
import org.adichatz.engine.controller.field.FileTextController;
import org.adichatz.engine.controller.field.FontTextController;
import org.adichatz.engine.controller.field.GMapController;
import org.adichatz.engine.controller.field.HyperlinkController;
import org.adichatz.engine.controller.field.ImageViewerController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.field.MultiChoiceController;
import org.adichatz.engine.controller.field.NumericTextController;
import org.adichatz.engine.controller.field.RGBTextController;
import org.adichatz.engine.controller.field.RadioGroupController;
import org.adichatz.engine.controller.field.StarRatingController;
import org.adichatz.engine.controller.field.TableColumnController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.controller.nebula.ArgPShelfController;
import org.adichatz.engine.controller.nebula.FormattedTextController;
import org.adichatz.engine.controller.nebula.GridColumnController;
import org.adichatz.engine.controller.nebula.GridColumnGroupController;
import org.adichatz.engine.controller.nebula.GridController;
import org.adichatz.engine.controller.nebula.PGroupController;
import org.adichatz.engine.controller.nebula.PGroupMenuController;
import org.adichatz.engine.controller.nebula.PGroupToolItemController;
import org.adichatz.engine.controller.nebula.PShelfController;
import org.adichatz.engine.controller.nebula.PShelfItemController;
import org.adichatz.engine.controller.nebula.RichTextController;
import org.adichatz.generator.ContainerTreeGenerator;
import org.adichatz.generator.EntityMetaModelGenerator;
import org.adichatz.generator.ExtendTreeGenerator;
import org.adichatz.generator.GridGenerator;
import org.adichatz.generator.IncludeTreeGenerator;
import org.adichatz.generator.MenuGenerator;
import org.adichatz.generator.PartTreeGenerator;
import org.adichatz.generator.QueryGenerator;
import org.adichatz.generator.TableGenerator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.ActionWrapper;
import org.adichatz.generator.wrapper.ArgPShelfWrapper;
import org.adichatz.generator.wrapper.ArgTabFolderWrapper;
import org.adichatz.generator.wrapper.ButtonBarWrapper;
import org.adichatz.generator.wrapper.ButtonWrapper;
import org.adichatz.generator.wrapper.CComboWrapper;
import org.adichatz.generator.wrapper.CTabFolderWrapper;
import org.adichatz.generator.wrapper.CTabItemWrapper;
import org.adichatz.generator.wrapper.CheckBoxWrapper;
import org.adichatz.generator.wrapper.ComboWrapper;
import org.adichatz.generator.wrapper.CompositeBagWrapper;
import org.adichatz.generator.wrapper.CompositeSeparatorWrapper;
import org.adichatz.generator.wrapper.CompositeWrapper;
import org.adichatz.generator.wrapper.ContainerTreeWrapper;
import org.adichatz.generator.wrapper.DateTextWrapper;
import org.adichatz.generator.wrapper.EditableFormTextWrapper;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.ExtendTreeWrapper;
import org.adichatz.generator.wrapper.ExtraTextWrapper;
import org.adichatz.generator.wrapper.FileTextWrapper;
import org.adichatz.generator.wrapper.FontTextWrapper;
import org.adichatz.generator.wrapper.FormPageWrapper;
import org.adichatz.generator.wrapper.FormattedTextWrapper;
import org.adichatz.generator.wrapper.GMapWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.GridColumnGroupWrapper;
import org.adichatz.generator.wrapper.GridColumnWrapper;
import org.adichatz.generator.wrapper.GridWrapper;
import org.adichatz.generator.wrapper.GroupWrapper;
import org.adichatz.generator.wrapper.HelpButtonWrapper;
import org.adichatz.generator.wrapper.HyperlinkWrapper;
import org.adichatz.generator.wrapper.ImageViewerWrapper;
import org.adichatz.generator.wrapper.IncludeTreeWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.LabelWrapper;
import org.adichatz.generator.wrapper.ManagedToolBarWrapper;
import org.adichatz.generator.wrapper.MenuActionWrapper;
import org.adichatz.generator.wrapper.MenuItemWrapper;
import org.adichatz.generator.wrapper.MultiChoiceWrapper;
import org.adichatz.generator.wrapper.NavigatorTreeWrapper;
import org.adichatz.generator.wrapper.NumericTextWrapper;
import org.adichatz.generator.wrapper.PGroupMenuWrapper;
import org.adichatz.generator.wrapper.PGroupToolItemWrapper;
import org.adichatz.generator.wrapper.PGroupWrapper;
import org.adichatz.generator.wrapper.PShelfItemWrapper;
import org.adichatz.generator.wrapper.PShelfWrapper;
import org.adichatz.generator.wrapper.PartTreeWrapper;
import org.adichatz.generator.wrapper.QueryTreeWrapper;
import org.adichatz.generator.wrapper.RadioGroupWrapper;
import org.adichatz.generator.wrapper.RefTextWrapper;
import org.adichatz.generator.wrapper.RgbTextWrapper;
import org.adichatz.generator.wrapper.RichTextWrapper;
import org.adichatz.generator.wrapper.SashFormWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.wrapper.ScrolledCompositeWrapper;
import org.adichatz.generator.wrapper.ScrolledFormWrapper;
import org.adichatz.generator.wrapper.SectionWrapper;
import org.adichatz.generator.wrapper.SeparatorWrapper;
import org.adichatz.generator.wrapper.StarRatingWrapper;
import org.adichatz.generator.wrapper.TableColumnWrapper;
import org.adichatz.generator.wrapper.TableWrapper;
import org.adichatz.generator.wrapper.TabularWrapper;
import org.adichatz.generator.wrapper.TextWrapper;
import org.adichatz.generator.wrapper.ToolBarWrapper;
import org.adichatz.generator.wrapper.ToolItemWrapper;
import org.adichatz.generator.wrapper.TreeWrapper;
import org.adichatz.generator.xjc.ControllerType;
import org.adichatz.generator.xjc.ControllersType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GeneratorType;
import org.adichatz.generator.xjc.GeneratorsType;
import org.adichatz.generator.xjc.MenuManagerType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.generator.xjc.PathElementEnum;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.PathElementsType;
import org.adichatz.generator.xjc.ScenarioType;
import org.adichatz.generator.xjc.ScenariosType;
import org.adichatz.jpa.controller.RefTextController;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.impl.EditorScenario;
import org.adichatz.scenario.impl.EntityScenario;
import org.adichatz.scenario.impl.GroupNavigatorScenario;
import org.adichatz.scenario.impl.MessageBundleScenario;
import org.adichatz.scenario.impl.PluginEntityScenario;
import org.adichatz.scenario.impl.QueryScenario;
import org.adichatz.scenario.impl.SectionDetailScenario;
import org.adichatz.scenario.impl.TableScenario;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;

// TODO: Auto-generated Javadoc
/**
 * The Class ScenarioTreeGeneration.
 */
public class ScenarioTreeGeneration {

	/**
	 * Instantiates a new scenario tree generation.
	 * 
	 * @param project
	 *            the project
	 * @param pluginPackage
	 *            the plugin package
	 */
	public ScenarioTreeGeneration(IProject project, String pluginPackage) {
		this.project = project;
		this.pluginPackage = pluginPackage;
	}

	/** The project. */
	protected IProject project;

	/** The plugin package. */
	protected String pluginPackage;

	/** The scenario tree. */
	protected ScenarioTreeWrapper scenarioTree;

	protected ParamsType params;

	protected GeneratorsType generators;

	protected ScenariosType scenarios;

	protected PathElementsType pathElements;

	protected ControllersType controllers;

	/**
	 * Builds the scenario file.
	 * 
	 */
	public void buildScenarioFile() {
		/*
		 * Initialize new scenarioTree
		 */
		scenarioTree = new ScenarioTreeWrapper();

		params = new ParamsType();
		scenarioTree.setParams(params);

		generators = new GeneratorsType();
		scenarioTree.setGenerators(generators);

		pathElements = new PathElementsType();
		scenarioTree.setPathElements(pathElements);

		controllers = new ControllersType();
		scenarioTree.setControllers(controllers);

		scenarios = new ScenariosType();
		scenarioTree.setScenarios(scenarios);

		addParams();
		addGenerators();
		addPathElements();
		addControllers();
		addScenarios();

		GenerationScenarioWrapper generationScenario = new GenerationScenarioWrapper();
		scenarioTree.setGenerationScenario(generationScenario);
		createXmlFile();
	}

	protected void addScenarios() {
		/*
		 * Add default scenario
		 */
		addScenario(GenerationEnum.PLUGIN_ENTITY, PluginEntityScenario.class.getName());
		addScenario(GenerationEnum.ENTITY, EntityScenario.class.getName());
		addScenario(GenerationEnum.NAVIGATOR, GroupNavigatorScenario.class.getName());
		addScenario(GenerationEnum.MESSAGE_BUNDLE, MessageBundleScenario.class.getName());
		addScenario(GenerationEnum.EDITOR, EditorScenario.class.getName());
		addScenario(GenerationEnum.DETAIL, SectionDetailScenario.class.getName());
		addScenario(GenerationEnum.TABLE, TableScenario.class.getName());
		addScenario(GenerationEnum.QUERY, QueryScenario.class.getName());
	}

	protected void addControllers() {
		/*
		 * Add common controllers
		 */
		addController(ActionWrapper.class.getName(), ActionController.class.getName());
		addController(ArgTabFolderWrapper.class.getName(), ArgTabFolderController.class.getName());
		addController(ButtonBarWrapper.class.getName(), ButtonBarController.class.getName());
		addController(ButtonWrapper.class.getName(), ButtonController.class.getName());
		addController(CComboWrapper.class.getName(), CComboController.class.getName());
		addController(CheckBoxWrapper.class.getName(), CheckBoxController.class.getName());
		addController(ComboWrapper.class.getName(), ComboController.class.getName());
		addController(CompositeWrapper.class.getName(), CompositeController.class.getName());
		addController(CompositeBagWrapper.class.getName(), CompositeBagController.class.getName());
		addController(ContainerTreeWrapper.class.getName(), ContainerController.class.getName());
		addController(CTabFolderWrapper.class.getName(), CTabFolderController.class.getName());
		addController(CTabItemWrapper.class.getName(), CTabItemController.class.getName());
		addController(DateTextWrapper.class.getName(), DateTextController.class.getName());
		addController(EditableFormTextWrapper.class.getName(), EditableFormTextController.class.getName());
		addController(ExtraTextWrapper.class.getName(), ExtraTextController.class.getName());
		addController(FileTextWrapper.class.getName(), FileTextController.class.getName());
		addController(FontTextWrapper.class.getName(), FontTextController.class.getName());
		addController(FormPageWrapper.class.getName(), FormPageController.class.getName());
		addController(FormattedTextWrapper.class.getName(), FormattedTextController.class.getName());
		addController(GMapWrapper.class.getName(), GMapController.class.getName());
		addController(GroupWrapper.class.getName(), GroupController.class.getName());
		addController(HelpButtonWrapper.class.getName(), HelpButtonController.class.getName());
		addController(HyperlinkWrapper.class.getName(), HyperlinkController.class.getName());
		addController(ImageViewerWrapper.class.getName(), ImageViewerController.class.getName());
		addController(IncludeWrapper.class.getName(), IncludeController.class.getName());
		addController(LabelWrapper.class.getName(), LabelController.class.getName());
		addController(CompositeSeparatorWrapper.class.getName(), CompositeSeparatorController.class.getName());
		addController(ManagedToolBarWrapper.class.getName(), ManagedToolBarController.class.getName());
		addController(MenuActionWrapper.class.getName(), MenuActionController.class.getName());
		addController(MenuManagerType.class.getName(), MenuManagerController.class.getName());
		addController(MultiChoiceWrapper.class.getName(), MultiChoiceController.class.getName());
		addController(NumericTextWrapper.class.getName(), NumericTextController.class.getName());
		addController(RadioGroupWrapper.class.getName(), RadioGroupController.class.getName());
		addController(RefTextWrapper.class.getName(), RefTextController.class.getName());
		addController(RgbTextWrapper.class.getName(), RGBTextController.class.getName());
		addController(RichTextWrapper.class.getName(), RichTextController.class.getName());
		addController(SashFormWrapper.class.getName(), SashFormController.class.getName());
		addController(ScrolledCompositeWrapper.class.getName(), ScrolledCompositeController.class.getName());
		addController(ScrolledFormWrapper.class.getName(), ScrolledFormController.class.getName());
		addController(SectionWrapper.class.getName(), SectionController.class.getName());
		addController(SeparatorWrapper.class.getName(), SeparatorController.class.getName());
		addController(StarRatingWrapper.class.getName(), StarRatingController.class.getName());
		addController(TableColumnWrapper.class.getName(), TableColumnController.class.getName());
		addController(TableWrapper.class.getName(), TableController.class.getName());
		addController(TabularWrapper.class.getName(), ATabularController.class.getName());
		addController(TextWrapper.class.getName(), TextController.class.getName());
		addController(ToolBarWrapper.class.getName(), ToolBarController.class.getName());
		addController(ToolItemWrapper.class.getName(), ToolItemController.class.getName());
		addController(TreeWrapper.class.getName(), ATreeController.class.getName());
		addController(MenuItemWrapper.class.getName(), MenuItemController.class.getName());
		// Nebula
		addController(PGroupWrapper.class.getName(), PGroupController.class.getName());
		addController(PGroupToolItemWrapper.class.getName(), PGroupToolItemController.class.getName());
		addController(PGroupMenuWrapper.class.getName(), PGroupMenuController.class.getName());
		addController(PShelfWrapper.class.getName(), PShelfController.class.getName());
		addController(ArgPShelfWrapper.class.getName(), ArgPShelfController.class.getName());
		addController(PShelfItemWrapper.class.getName(), PShelfItemController.class.getName());
		addController(GridColumnGroupWrapper.class.getName(), GridColumnGroupController.class.getName());
		addController(GridColumnWrapper.class.getName(), GridColumnController.class.getName());
		addController(GridWrapper.class.getName(), GridController.class.getName());
	}

	protected void addPathElements() {
		/*
		 * Add common pathElements
		 */
		addPathElement(PathElementEnum.DIRECTORY, "#PLUGINHOME()/bin");
		addPathElement(PathElementEnum.DIRECTORY, "#PLUGINHOME()/resources/gencode/bin");

		addPathElement(PathElementEnum.PLUGIN, "org.eclipse.swt.win32.win32.x86_64");
		addPathElement(PathElementEnum.PLUGIN, "org.eclipse.jface");
		addPathElement(PathElementEnum.PLUGIN, "org.eclipse.ui.forms");
		addPathElement(PathElementEnum.PLUGIN, "org.eclipse.core.commands");
		addPathElement(PathElementEnum.PLUGIN, "org.eclipse.ui.workbench");
		addPathElement(PathElementEnum.PLUGIN, "org.eclipse.equinox.common");
		addPathElement(PathElementEnum.PLUGIN, "org.eclipse.equinox.registry");
		addPathElement(PathElementEnum.PLUGIN, "org.adichatz.common");
		addPathElement(PathElementEnum.PLUGIN, "org.adichatz.engine");
		addPathElement(PathElementEnum.PLUGIN, "org.adichatz.jpa");
		addPathElement(PathElementEnum.PLUGIN, "org.adichatz.resources");

		addPathElement(PathElementEnum.LIBRARY, "#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE
				+ ")/template/lib/connector/org.adichatz.hibernate4.jboss.connector.jar");
		addPathElement(PathElementEnum.LIBRARY, "#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE
				+ ")/template/lib/connector/org.adichatz.hibernate4.jboss.connector.source.jar");
		addPathElement(PathElementEnum.LIBRARY, "#PLUGINHOME(" + GeneratorConstants.TEMPLATE_BUNDLE + ")/template/lib/lib-jse//"
				+ GeneratorConstants.HIBERNATE_CORE_JAR);
	}

	protected void addGenerators() {
		/*
		 * Add generators
		 */
		// Common Generators
		addGenerator(QueryTreeWrapper.class.getName(), QueryGenerator.class.getName());
		addGenerator(EntityTreeWrapper.class.getName(), EntityMetaModelGenerator.class.getName());
		addGenerator(NavigatorTreeWrapper.class.getName(), MenuGenerator.class.getName());
		addGenerator(PartTreeWrapper.class.getName(), PartTreeGenerator.class.getName());
		addGenerator(ExtendTreeWrapper.class.getName(), ExtendTreeGenerator.class.getName());
		addGenerator(IncludeTreeWrapper.class.getName(), IncludeTreeGenerator.class.getName());
		addGenerator(ContainerTreeWrapper.class.getName(), ContainerTreeGenerator.class.getName());
		addGenerator(TableWrapper.class.getName(), TableGenerator.class.getName());
		addGenerator(GridWrapper.class.getName(), GridGenerator.class.getName());
	}

	protected void addParams() {
		/*
		 * Add common parameters
		 */
		IPreferenceStore store = GeneratorConstants.ADICHATZ_STUDIO_PREFERENCE_STORE;
		addParam(EngineConstants.DEFAULT_QUERY_MAX_RESULT, EngineConstants.RUNTIME, "200");
		addParam(EngineConstants.INTRO_PART_URI, EngineConstants.RUNTIME,
				"bundleclass://org.adichatz.engine/org.adichatz.engine.intro.DefaultIntroPanel");
		addParam(EngineConstants.INTRO_OUTLINE_URI, EngineConstants.RUNTIME,
				"bundleclass://org.adichatz.engine.e4/org.adichatz.engine.e4.resource.RecentOutlinePage");
		addParam(EngineConstants.DEFAULT_REF_TEXT_POPUP_URI, EngineConstants.RUNTIME,
				"bundleclass://org.adichatz.jpa/org.adichatz.jpa.gencode.common.DefaultRefText");
		addParam(IScenarioConstants.DEFAULT_DECIMAL_SCALE, "2");
		addParam(IScenarioConstants.DEFAULT_INTEGER_PATTERN, "######");
		addParam(IScenarioConstants.FORMAT_GENERATED_CLASS, store.getString(IScenarioConstants.FORMAT_GENERATED_CLASS));
		addParam(EngineConstants.ADICHATZ_GMAP_API_KEY, EngineConstants.RUNTIME, "YOUR_GMAP_API_KEY");
		addParam(EngineConstants.ADICHATZ_AVOIDED_MESSAGES, "HHH000412, HHH000206");
		addParam(IScenarioConstants.PLUGIN_PACKAGE, pluginPackage);
	}

	/**
	 * Gets the scenario tree.
	 * 
	 * @return the scenario tree
	 */
	public ScenarioTreeWrapper getScenarioTree() {
		return scenarioTree;
	}

	/**
	 * Creates the xml file.
	 */
	public void createXmlFile() {
		try {
			IFolder resourceFolder = project.getFolder("resources");
			if (!resourceFolder.exists())
				resourceFolder.create(true, true, null);
			IFolder xmlFolder = resourceFolder.getFolder("xml");
			if (!xmlFolder.exists())
				xmlFolder.create(true, true, null);
			IFile scenarioFile = xmlFolder.getFile(GeneratorConstants.SCENARIO_FILE);
			ScenarioUtil.createXmlFile(scenarioFile.getLocation().toFile(), scenarioTree);
			scenarioFile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			logError(e);
		}
	}

	/**
	 * Adds the controller.
	 * 
	 * @param controllers
	 *            the controllers S * @param wrapperClassName the wrapper class name
	 * @param wrapperClassName
	 *            the wrapper class name
	 * @param controllerClassName
	 *            the controller class name
	 */
	protected void addController(String wrapperClassName, String controllerClassName) {
		ControllerType controller = new ControllerType();
		controller.setWrapperClassName(wrapperClassName);
		controller.setControllerClassName(controllerClassName);
		controllers.getController().add(controller);
	}

	protected void addScenario(GenerationEnum type, String scenarioClassName) {
		ScenarioType scenario = new ScenarioType();
		scenario.setType(type);
		scenario.setScenarioClassName(scenarioClassName);
		scenarios.getScenario().add(scenario);
	}

	/**
	 * Adds the path element.
	 * 
	 * @param pathElements
	 *            the path elements
	 * @param type
	 *            the type
	 * @param location
	 *            the location
	 */
	protected PathElementType addPathElement(PathElementEnum type, String location) {
		PathElementType pathElement = new PathElementType();
		pathElement.setType(type);
		pathElement.setLocation(location);
		pathElements.getPathElement().add(pathElement);
		return pathElement;
	}

	/**
	 * Adds the generator.
	 * 
	 * @param generators
	 *            the generators
	 * @param treeClassName
	 *            the tree class name
	 * @param generatorClassName
	 *            the generator class name
	 */
	protected void addGenerator(String treeClassName, String generatorClassName) {
		GeneratorType generator = new GeneratorType();
		generator.setTreeClassName(treeClassName);
		generator.setGeneratorClassName(generatorClassName);
		generators.getGenerator().add(generator);
	}

	/**
	 * Adds the param.
	 * 
	 * @param params
	 *            the params
	 * @param id
	 *            the id
	 * @param value
	 *            the value
	 */
	protected void addParam(String id, String value) {
		addParam(id, null, value);
	}

	protected void addParam(String id, String type, String value) {
		ParamType param = new ParamType();
		param.setId(id);
		param.setType(type);
		param.setValue(value);
		params.getParam().add(param);
	}
}
