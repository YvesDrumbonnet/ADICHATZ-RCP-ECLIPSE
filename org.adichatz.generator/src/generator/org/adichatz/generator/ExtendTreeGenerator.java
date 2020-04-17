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

import java.io.IOException;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.generator.tools.AListenerTypeManager;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.tools.InspectTools;
import org.adichatz.generator.wrapper.ExtendTreeWrapper;
import org.adichatz.generator.wrapper.PGroupWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IWidgetWrapper;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class IncludeTreeGenerator.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class ExtendTreeGenerator extends AConfigGenerator {

	/** The form page wrapper. */
	private final ExtendTreeWrapper extendTreeWrapper;

	private String superClassName;

	/**
	 * Instantiates a new include tree generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	public ExtendTreeGenerator(final ScenarioInput scenarioInput) {
		super(scenarioInput, null);
		this.extendTreeWrapper = (ExtendTreeWrapper) scenarioInput.getXmlElement();

		scenarioInput.getRegister().clear();
		scenarioInput.getBundleTranslation().clear();
		scenarioInput.setRootWrapper(extendTreeWrapper);

		buildClassFile(scenarioInput, extendTreeWrapper.getEntityURI(), () -> {
			String[] instanceKeys = EngineTools.getInstanceKeys(extendTreeWrapper.getAdiResourceURI());
			AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(instanceKeys[0]);
			superClassName = pluginResources.getClassName(instanceKeys[2], instanceKeys[1]);
			final Class<?> superClass = pluginResources.getGencodePath().getClazz(superClassName);
			createClassFile(scenarioInput, " extends " + getObjectName(superClass));
		});

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
		classBodyBuffer.append("super(parentContext,parentController);");
		classBodyBuffer.appendMinus("}\n");

		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * Creates class " + className + " extension of " + superClassName + ".");
		classBodyBuffer.append(" *");
		classBodyBuffer.append(" * @param context");
		classBodyBuffer.append(" *            The context of the root controller");
		classBodyBuffer.append(" * @param parent controller");
		classBodyBuffer.append(" *            The collection parentController");
		classBodyBuffer.append(" * @param id");
		classBodyBuffer.append(" *            The part id");
		classBodyBuffer.append(" * @param parentContext");
		classBodyBuffer.append(" *            The parent context");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(" + getObjectName(ParamMap.class) + " paramMap, "
				+ getObjectName(IContainerController.class) + " parentController, String id, " + getObjectName(AdiContext.class)
				+ " parentContext) {");
		classBodyBuffer.append("super(paramMap, parentController, id, parentContext);");

		/* Inspects and transforms include tree */
		InspectTools.inspectExtendTree(scenarioInput, extendTreeWrapper);
		addResourceBundleTranslation(extendTreeWrapper.getConfig());

		classBodyBuffer.append("coreController = parentController;");

		classBodyBuffer.appendMinus("}\n");

		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * creates contents");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public void " + METHOD_CREATE_CONTENTS + "(){");
		classBodyBuffer.append("super.createContents();");

		for (ElementType element : extendTreeWrapper.getElements()) {
			ICollectionWrapper<?> parentCollection = (ICollectionWrapper<?>) element;

			String parentCollectionName = ControlGenerator.getControllerName(parentCollection.getId(), parentCollection.getClass());
			if (element instanceof IWidgetWrapper && !EngineTools.isEmpty(((IWidgetWrapper) element).getRef())) {
				// addRef((IWidgetWrapper) element, parentCollectionName, keyWordGenerator);
				buildControl(classBodyBuffer, (IElementWrapper) element, true, parentCollectionName);
			} else {
				addCollection(scenarioInput, parentCollection, parentCollectionName);
				if (element instanceof PGroupWrapper)
					CodeGenerationUtil.addPGroupItems(this, (PGroupWrapper) element, parentCollectionName);
			}
			CodeGenerationUtil.addListenersCode(classBodyBuffer, (IWidgetWrapper) parentCollection, parentCollectionName + ".",
					AListenerTypeManager.LIFE_CYCLE);
		}

		classBodyBuffer.appendMinus("}");
		CodeGenerationUtil.addAdditionalCode(classBodyBuffer, extendTreeWrapper.getAdditionalCode());
	}
}
