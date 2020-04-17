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

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.AIncludeCore;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tools.InspectTools;
import org.adichatz.generator.wrapper.IncludeTreeWrapper;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class IncludeTreeGenerator.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class IncludeTreeGenerator extends AConfigGenerator {

	/** The form page wrapper. */
	private final IncludeTreeWrapper includeTreeWrapper;

	/**
	 * Instantiates a new include tree generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public IncludeTreeGenerator(final ScenarioInput scenarioInput) {
		super(scenarioInput, null);
		this.includeTreeWrapper = (IncludeTreeWrapper) scenarioInput.getXmlElement();

		scenarioInput.getRegister().clear();
		scenarioInput.getBundleTranslation().clear();
		scenarioInput.setRootWrapper(includeTreeWrapper);

		buildClassFile(scenarioInput, includeTreeWrapper.getEntityURI(), () -> {
			createClassFile(scenarioInput,
					" extends " + getParentCoreName(includeTreeWrapper.getCoreClassName(), AIncludeCore.class));
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.AClassGenerator#addBlocks()
	 */
	@Override
	protected void addBlocks() throws IOException {
		try {
			if (null != includeTreeWrapper.getConfig()) {
				addConfigCustomizations(includeTreeWrapper.getConfig());
			}
		} catch (IllegalArgumentException e) {
			logError(e);
		}
		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Instantiates a new " + className + ".");
		classBodyBuffer.append(" * ");
		classBodyBuffer.append(" * This constructor could be used by Column parameter. (see " + EngineConstants.JPA_BUNDLE + ")");
		classBodyBuffer.append(" * ");
		classBodyBuffer.append(" * @param parentContext");
		classBodyBuffer.append(" *            the parent context");
		classBodyBuffer.append(" * @param parentController");
		classBodyBuffer.append(" *            the parent controller");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(" + getObjectName(AdiContext.class) + " parentContext, "
				+ getObjectName(IContainerController.class) + " parentController) {");
		classBodyBuffer.append("coreController = parentController;");
		classBodyBuffer.appendMinus("}\n");

		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Creates the include " + className + ".");
		classBodyBuffer.append(" *");
		classBodyBuffer.append(" * @param context");
		classBodyBuffer.append(" *            The context of the root controller");
		classBodyBuffer.append(" * @param parent controller");
		classBodyBuffer.append(" *            The collection parentController");
		classBodyBuffer.append(" * @param id");
		classBodyBuffer.append(" *            The include id");
		classBodyBuffer.append(" * @param parentContext");
		classBodyBuffer.append(" *            The parent context");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(" + getObjectName(ParamMap.class) + " paramMap, "
				+ getObjectName(IContainerController.class) + " parentController, String id, " + getObjectName(AdiContext.class)
				+ " parentContext) {");
		classBodyBuffer.append("super(paramMap, parentController, id, parentContext);");

		/* Inspects and transforms include tree */
		InspectTools.inspectIncludeTree(scenarioInput, includeTreeWrapper);
		addResourceBundleTranslation(includeTreeWrapper.getConfig());

		classBodyBuffer.append("coreController = parentController;");

		classBodyBuffer.appendMinus("}\n");

		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * creates contents");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public void " + METHOD_CREATE_CONTENTS + "(){");

		addCollection(scenarioInput, includeTreeWrapper, "coreController");

		classBodyBuffer.appendMinus("}");
		CodeGenerationUtil.addAdditionalCode(classBodyBuffer, includeTreeWrapper.getAdditionalCode());
	}
}
