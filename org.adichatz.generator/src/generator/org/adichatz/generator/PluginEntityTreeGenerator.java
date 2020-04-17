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

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.IconType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;

// TODO: Auto-generated Javadoc
/**
 * The Class PluginEntityTreeGenerator.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class PluginEntityTreeGenerator extends AClassGenerator {

	/**
	 * Instantiates a new plugin entity tree generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public PluginEntityTreeGenerator(ScenarioInput scenarioInput) {
		super(scenarioInput, null);
		createClassFile(scenarioInput, " extends " + getObjectName(APluginEntityTree.class));
	}

	protected void addStaticGetEntityURI(String entityURI) {
		try {
			classBodyBuffer.append("/*");
			classBodyBuffer.append(" * Add a way to compute entity URI from bean class.");
			classBodyBuffer.append(" */");
			classBodyBuffer.appendPlus("public static String getEntityURI(Class<?> beanClass) {");
			classBodyBuffer.append(keyWordGenerator.evalExpression(classBodyBuffer, entityURI, false, false));
			classBodyBuffer.appendMinus("}");
		} catch (IOException e) {
			logError(e);
		}
	}

	@Override
	protected void addDeclareURIAnnotation() throws IOException {
	}

	@Override
	protected void addURIAnnotation() throws IOException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.AClassGenerator#addBlocks()
	 */
	@Override
	protected void addBlocks() throws IOException {
		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Creates the launcher " + className + ".");
		classBodyBuffer.append(" *");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(" + getObjectName(AdiPluginResources.class) + " pluginResources) {");

		String modelPluginKey = "???";
		List<PluginEntityType> pluginEntities = scenarioInput.getScenarioResources().getGenerationScenario().getPluginEntity();
		Collections.sort(pluginEntities, new Comparator<PluginEntityType>() {

			@Override
			public int compare(PluginEntityType o1, PluginEntityType o2) {
				return o1.getEntityURI().compareTo(o2.getEntityURI());
			}
		});

		ScenarioResources modelSR = null;
		classBodyBuffer.append("this.pluginResources = pluginResources;");
		classBodyBuffer.append(getObjectName(Map.class) + "<String, String> adiResourceUriMap;");
		classBodyBuffer.append(getObjectName(AdiPluginResources.class) + " modelPluginResources;");
		for (PluginEntityType pluginEntityType : pluginEntities) {
			PluginEntityWrapper pluginEntity = (PluginEntityWrapper) pluginEntityType;
			String pluginKey = pluginEntity.getEntityKeys()[0];
			if (!Utilities.equals(modelPluginKey, pluginKey)) {
				modelPluginKey = pluginKey;
				classBodyBuffer.append("modelPluginResources = " + getObjectName(AdichatzApplication.class)
						+ ".getPluginResources(\"" + modelPluginKey + "\");");
				modelSR = ScenarioResources.getInstance(modelPluginKey, scenarioInput.getScenarioResources().getPluginName());
			}
			/*
			 * addPluginEntity invocation
			 */
			modelPluginKey = pluginKey;
			String entityName = getEntityName(pluginEntity.getEntityKeys()[2]);
			classBodyBuffer.append("");
			classBodyBuffer.append("// add plugin entity for '" + pluginEntity.getEntityURI() + "'");
			StringBuffer addPluginEntitySB = new StringBuffer();

			addPluginEntitySB.append(getObjectName(PluginEntity.class)).append(" ").append(entityName).append(" = ");
			addPluginEntitySB.append("addPluginEntity(modelPluginResources, \"").append(pluginEntity.getEntityURI()).append("\"");
			addPluginEntitySB.append(");");
			classBodyBuffer.append(addPluginEntitySB.toString());

			addPrivilege(entityName, "IEntityConstants.RETRIEVE", pluginEntity.getRetrieveRoles());
			addPrivilege(entityName, "IEntityConstants.MERGE", pluginEntity.getMergeRoles());
			addPrivilege(entityName, "IEntityConstants.PERSIST", pluginEntity.getPersistRoles());
			addPrivilege(entityName, "IEntityConstants.REMOVE", pluginEntity.getRemoveRoles());

			/*
			 * Adds icons if needed
			 */
			if (null != pluginEntity.getIcons())
				for (IconType partIcon : pluginEntity.getIcons().getIcon()) {
					classBodyBuffer.append(
							entityName + ".getImageKeyMap().put(" + CodeGenerationUtil.betweenQuotes(partIcon.getType().value())
									+ ", " + CodeGenerationUtil.betweenQuotes(partIcon.getImage()) + ");");
				}
			boolean firstGU = true;
			for (GenerationUnitType generationUnit : pluginEntity.getGenerationUnit()) {
				switch (generationUnit.getType()) {
				case MESSAGE_BUNDLE:
				case ENTITY:
					break;
				default:
					if (firstGU) {
						firstGU = false;
						classBodyBuffer.append("adiResourceUriMap = " + entityName + ".getAdiResourceUriMap();");
					}
					String[] instanceKeys = EngineTools.getInstanceKeys(generationUnit.getAdiResourceURI());
					if (null == instanceKeys[0])
						instanceKeys[0] = scenarioInput.getScenarioResources().getPluginName();
					classBodyBuffer.append("adiResourceUriMap.put(\"" + generationUnit.getType().value() + "\", \""
							+ EngineTools.getAdiResourceURI(instanceKeys) + "\");");
					break;
				}
			}
			if (!EngineTools.isEmpty(pluginEntityType.getUiBeanClassName())) {
				classBodyBuffer.append("pluginEntityClassMap.put("
						+ keyWordGenerator.evalExpr2Str(classBodyBuffer, pluginEntityType.getUiBeanClassName(), false) + ", "
						+ entityName + ");");
			}
			addon(modelSR, pluginEntity, entityName);
		}
		classBodyBuffer.appendMinus("}");
		addon();
	}

	/**
	 * Addon (Default usage for UI or JPA bundle. Could be overriden
	 *
	 * @param modelSR
	 *            the model SR
	 * @param pluginEntity
	 *            the plugin entity
	 * @param entityName
	 *            the entity name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void addon(ScenarioResources modelSR, PluginEntityWrapper pluginEntity, String entityName) throws IOException {
		String beanClassName;
		if (!EngineTools.isEmpty(pluginEntity.getBeanClassName()))
			beanClassName = pluginEntity.getBeanClassName();
		else
			beanClassName = modelSR.getGenerationScenario().getModelPart().getModelPackageName() + "." + pluginEntity.getEntityId();
		classBodyBuffer.append("pluginEntityClassMap.put(\"" + beanClassName + "\", " + entityName + ");");
	}

	protected void addon() throws IOException {
	}

	public String getEntityName(String entityId) {
		String entityName = EngineTools.lowerCaseFirstLetter(entityId) + "PE";
		return entityName;
	}

	/**
	 * Adds the privilege.
	 * 
	 * @param privilege
	 *            the privilege
	 * @param roles
	 *            the roles
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void addPrivilege(String entityName, String privilege, String roles) throws IOException {
		if (!EngineTools.isEmpty(roles)) {
			getObjectName(IEntityConstants.class);
			classBodyBuffer
					.append(entityName + ".addPrivileges(" + privilege + ", " + CodeGenerationUtil.getValuesFromList(roles) + ");");
		}
	}
}
