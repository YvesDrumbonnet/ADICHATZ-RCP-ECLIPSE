/*******************************************************************************
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.studio.xjc.scenario;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.scenario.IMessageBundleScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.impl.ABundleScenario;
import org.adichatz.studio.util.IStudioConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class XjcMessageBundleScenario extends ABundleScenario implements IMessageBundleScenario {

	/**
	 * Instantiates a new xjc message bundle scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public XjcMessageBundleScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IMessageBundleScenario#createEntityBundle(org.adichatz.scenario.ScenarioInput)
	 */
	@Override
	public void createEntityBundle(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
		scenarioInput.setInput(scenarioInput.getTreeId(), scenarioInput.getSubPackage());
		EntityTreeWrapper entityTree = (EntityTreeWrapper) new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);
		try {
			bundleWriter = getFileWriter(scenarioInput.getPluginEntity().getEntityId());

			writeTitle("Bundle for entity " + entityTree.getEntityURI());

			bundleWriter.append('\n');
			writeTitle("fields");

			Set<PropertyFieldType> fields = new HashSet<PropertyFieldType>();
			fields.addAll(entityTree.getPropertyField());

			for (PropertyFieldType property : entityTree.getPropertyField())
				bundleWriter.append(property.getId() + " = " + property.getId() + "\n");

			bundleWriter.flush();
			bundleWriter.close();

		} catch (IOException e) {
			logError(e);
		}
	}

	/**
	 * Creates the tree bundle.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public void createTreeBundle(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
		try {
			bundleWriter = getFileWriter(IStudioConstants.XJC_TREE_ELEMENTS);
			writeTitle("Elements of the XJC tree");
			for (PluginEntityType pluginEntity : scenarioInput.getScenarioResources().getGenerationScenario().getPluginEntity()) {
				String entityId = ((PluginEntityWrapper) pluginEntity).getEntityKeys()[2];
				String value = EngineTools.lowerCaseFirstLetter(entityId);
				value = value.substring(0, value.length() - 2);
				bundleWriter.write(entityId + " = " + value + "\n");
			}
			bundleWriter.close();
		} catch (IOException e) {
			logError(e);
		}
	}
}
