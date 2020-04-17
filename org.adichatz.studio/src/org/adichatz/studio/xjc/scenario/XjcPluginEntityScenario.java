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
package org.adichatz.studio.xjc.scenario;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlType;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.GenerationUnitWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.wrapper.internal.ElementWrapper;
import org.adichatz.generator.xjc.ApplicationServerType;
import org.adichatz.generator.xjc.ApplicationServersType;
import org.adichatz.generator.xjc.BasicType;
import org.adichatz.generator.xjc.ConnectorTree;
import org.adichatz.generator.xjc.ControllersType;
import org.adichatz.generator.xjc.DatasourceType;
import org.adichatz.generator.xjc.DatasourcesType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.IconType;
import org.adichatz.generator.xjc.IconsType;
import org.adichatz.generator.xjc.PartImageTypeEnum;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.ScenarioTree;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.impl.EditorScenario;
import org.adichatz.scenario.impl.EntityScenario;
import org.adichatz.scenario.impl.PluginEntityScenario;
import org.adichatz.scenario.impl.QueryScenario;
import org.adichatz.scenario.impl.TableScenario;
import org.adichatz.scenario.impl.TreeNavigatorScenario;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.util.StudioUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcPluginEntityScenario.
 * 
 * @author Yves Drumbonnet
 *
 */
/**
 * @author Adichatz
 * 
 */
public class XjcPluginEntityScenario extends PluginEntityScenario {

	/** The Excluded classes. */
	public static Set<String> EXCLUDED_CLASSES = new HashSet<String>();
	static {
		EXCLUDED_CLASSES.add(Enum.class.getName());
		EXCLUDED_CLASSES.add(Object.class.getName());
		EXCLUDED_CLASSES.add(ElementWrapper.class.getName());
		EXCLUDED_CLASSES.add(BasicType.class.getName());
	}

	public static Set<String> EXCLUDED_PART = new HashSet<String>();
	static {
		EXCLUDED_PART.add(ConnectorTree.class.getName());
		EXCLUDED_PART.add(ScenarioTree.class.getName());
		EXCLUDED_PART.add(ApplicationServersType.class.getName());
		EXCLUDED_PART.add(ApplicationServerType.class.getName());
		EXCLUDED_PART.add(DatasourcesType.class.getName());
		EXCLUDED_PART.add(DatasourceType.class.getName());
		EXCLUDED_PART.add(ScenarioTree.class.getName());
		EXCLUDED_PART.add(ControllersType.class.getName());
		EXCLUDED_PART.add(IconType.class.getName());
		EXCLUDED_PART.add(BasicType.class.getName());
	}

	/** The icons files. */
	private List<String> iconsFiles = Arrays
			.asList(new File(FileUtil.getPluginHome(GeneratorConstants.STUDIO_BUNDLE) + "/resources/icons/xjc").list());

	/**
	 * Instantiates a new xjc plugin entity scenario.
	 * 
	 * @param scenarioResources
	 *            the scenario resources
	 */
	public XjcPluginEntityScenario(GenerationUnitType generationUnit, ScenarioResources scenarioResources) {
		super(generationUnit, scenarioResources);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IPluginEntityScenario#generatePluginEntitiesTree(org.adichatz.scenario.ScenarioInput)
	 */
	public void generatePluginEntitiesTree() {
		this.scenarioInput = new XjcScenarioInput(scenarioResources, EngineConstants.PLUGIN_ENTITY_TREE, "");
		ScenarioTreeWrapper scenarioTree = scenarioResources.getScenarioTree();
		GenerationScenarioWrapper generationScenario = getInitialGenerationScenario(scenarioTree.getGenerationScenario());
		scenarioTree.setGenerationScenario(generationScenario);
		ScenarioUtil.createXmlFile(scenarioTree.getXmlFile(), scenarioTree);
		GeneratorUnit generatorUnit = new GeneratorUnit(scenarioInput);
		generatorUnit.initialize(false);
		new XjcPluginEntityTreeGenerator(scenarioInput);
	}

	private GenerationScenarioWrapper getInitialGenerationScenario(GenerationScenarioType oldGenerationScenario) {
		GenerationScenarioWrapper generationScenario = new GenerationScenarioWrapper();
		generationScenario.setModelPart(oldGenerationScenario.getModelPart());
		generationScenario.setRcpPart(oldGenerationScenario.getRcpPart());
		generationScenario.getGenerationUnit().addAll(oldGenerationScenario.getGenerationUnit());

		for (Class<?> clazz : getXjcClasses(StudioUtil.getXjcDirFile())) {
			PluginEntityWrapper pluginEntity = new PluginEntityWrapper(scenarioResources);
			String entityId = EngineTools.upperCaseFirstLetter(clazz.getSimpleName());
			if (entityId.endsWith("Type"))
				entityId = entityId.substring(0, entityId.length() - 4);
			pluginEntity.setEntityURI("adi://org.adichatz.studio/model/".concat(entityId).concat("MM"));
			pluginEntity.setBeanClassName(clazz.getName());

			generationScenario.getPluginEntity().add(pluginEntity);
			/*
			 * Add entity image if file exists
			 */
			String image = null;
			if (entityId.equals("CopyResource"))
				image = "IMG_COPY.png";
			else if (entityId.equals("RemoveResource"))
				image = "IMG_DELETE.gif";
			else {
				String fileName = EngineTools.lowerCaseFirstLetter(entityId);
				if (fileName.endsWith("Tree"))
					fileName = fileName.substring(0, fileName.length() - 4);
				fileName = fileName.concat(".png");
				if (iconsFiles.contains(fileName)) {
					image = "xjc/".concat(fileName);
				}
			}
			if (null != image) {
				IconType icon = new IconType();
				icon.setType(PartImageTypeEnum.ENTITY);
				icon.setImage(image);
				if (null == pluginEntity.getIcons())
					pluginEntity.setIcons(new IconsType());
				pluginEntity.getIcons().getIcon().add(icon);
			}
			List<GenerationUnitType> generationUnits = pluginEntity.getGenerationUnit();
			generationUnits.add(newGenerationUnit(entityId.concat("MM"), "model", GenerationEnum.ENTITY));
			generationUnits.add(newGenerationUnit(EngineTools.lowerCaseFirstLetter(entityId), ".", GenerationEnum.MESSAGE_BUNDLE));
			if (!XjcPluginEntityScenario.EXCLUDED_PART.contains(clazz.getName())) {
				generationUnits.add(newGenerationUnit(entityId + "PART", "detail", GenerationEnum.DETAIL));
			}
		} // END for (Class<?> clazz : getXjcClasses(xjcDir))
		return generationScenario;
	}

	private GenerationUnitWrapper newGenerationUnit(String treeId, String subPackage, GenerationEnum generationEnum) {
		GenerationUnitWrapper gu = new GenerationUnitWrapper(generationEnum);
		gu.setAdiResourceURI(EngineTools.getAdiResourceURI(subPackage, treeId));
		return gu;
	}

	/**
	 * Gets the xjc classes.
	 * 
	 * @param dir
	 *            the dir
	 * @return the xjc classes
	 */
	private List<Class<?>> getXjcClasses(File dir) {
		final List<Class<?>> xjcClasses = new ArrayList<Class<?>>();
		dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(".java")) {
					String entityId = name.substring(0, name.length() - 5);
					String entityClassName = "org.adichatz.generator.xjc." + entityId;
					Class<?> xjcClass = scenarioInput.getScenarioResources().getPluginResources().getGencodePath()
							.getClazz(entityClassName);
					if (null != xjcClass //
							&& null != xjcClass.getAnnotation(XmlType.class) //
							&& !xjcClass.getSuperclass().equals(Enum.class) //
							&& !EXCLUDED_CLASSES.contains(xjcClass.getName())) {
						xjcClasses.add(xjcClass);
						return true;
					}
				}
				return false;
			}
		});
		return xjcClasses;
	}

	@Override
	public String getTreeId(PluginEntityType pluginEntity, GenerationEnum generationEnum) {
		String entityId = ((PluginEntityWrapper) pluginEntity).getPluginEntityId();
		switch (generationEnum) {
		case ENTITY:
			return entityId.concat("MM");
		case DETAIL:
			return entityId.concat("Part");
		default:
			return null;
		}
	}

	@Override
	public String getSubPackage(PluginEntityType pluginEntity, GenerationEnum generationEnum) {
		switch (generationEnum) {
		case ENTITY:
			return "model";
		case DETAIL:
			return "detail";
		default:
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.impl.PluginEntityScenario#getDefaultGenerationUnit(org.adichatz.generator.xjc.GenerationEnum)
	 */
	@Override
	public GenerationUnitWrapper getDefaultGenerationUnit(GenerationEnum generationEnum) {
		GenerationUnitWrapper generationUnit = new GenerationUnitWrapper();
		generationUnit.setType(generationEnum);
		String scenarioClassName;
		switch (generationEnum) {
		case PLUGIN_ENTITY:
			scenarioClassName = XjcPluginEntityScenario.class.getName();
			break;
		case ENTITY:
			scenarioClassName = EntityScenario.class.getName();
			break;
		case NAVIGATOR:
			generationUnit.setAdiResourceURI(EngineTools.getAdiResourceURI("treeNavigator", "TreeNavigatorContent"));
			scenarioClassName = TreeNavigatorScenario.class.getName();
			break;
		case MESSAGE_BUNDLE:
			scenarioClassName = XjcMessageBundleScenario.class.getName();
			break;
		case EDITOR:
			scenarioClassName = EditorScenario.class.getName();
			break;
		case DETAIL:
			scenarioClassName = XjcDetailPartScenario.class.getName();
			break;
		case TABLE:
			scenarioClassName = TableScenario.class.getName();
			break;
		case QUERY:
			scenarioClassName = QueryScenario.class.getName();
			break;
		default:
			scenarioClassName = null;
		}
		generationUnit.setScenarioClassName(scenarioClassName);
		return generationUnit;
	}

	@Override
	public String getEntityURI(ScenarioResources scenarioResources, String fileName) {
		String entityId = fileName.substring(0, fileName.length() - (fileName.endsWith(".class") ? 6 : 5));
		String subPackage = "model";
		return "adi://".concat(scenarioResources.getPluginName()).concat("/").concat(subPackage).concat("/").concat(entityId);
	}

}
