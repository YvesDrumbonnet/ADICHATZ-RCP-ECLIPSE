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

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IToolBarContainerController;
import org.adichatz.engine.controller.collection.CTabFolderController;
import org.adichatz.engine.controller.nebula.PGroupController;
import org.adichatz.engine.controller.nebula.PShelfController;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.ManagedToolBarWrapper;
import org.adichatz.generator.wrapper.PGroupWrapper;
import org.adichatz.generator.wrapper.internal.ICompositeWrapper;
import org.adichatz.generator.wrapper.internal.IItemCompositeWrapper;
import org.adichatz.generator.wrapper.internal.IToolBarContainerWrapper;
import org.adichatz.generator.xjc.ArgPShelfType;
import org.adichatz.generator.xjc.ArgTabFolderType;
import org.adichatz.generator.xjc.CTabItemType;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class CompositeGenerator.
 * 
 * Composite generated code is contained in its own class
 * 
 * @author Yves Drumbonnet
 * 
 */
public class CompositeGenerator extends ACollectionGenerator {

	/** The composite wrapper. */
	protected final ICompositeWrapper compositeWrapper;

	public CompositeGenerator(final ScenarioInput scenarioInput, ACodeGenerator parentGenerator) {
		super(scenarioInput, parentGenerator);
		this.compositeWrapper = (ICompositeWrapper) scenarioInput.getXmlElement();

		buildClassFile(scenarioInput, compositeWrapper.getEntityURI(), () -> {
			createClassFile(scenarioInput, scenarioInput.getTreeId() + EngineTools.upperCaseFirstLetter(compositeWrapper.getId()),
					" extends " + getObjectName(compositeWrapper.getGencodeClass()));
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
		classBodyBuffer.append(" * Creates " + className + ".");
		classBodyBuffer.append(" *");
		classBodyBuffer.append(" * @param context");
		classBodyBuffer.append(" *            The context of the root controller");
		classBodyBuffer.append(" * @param core controller");
		classBodyBuffer.append(" *            The collection controller linked to the class");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public " + className + "(final " + getObjectName(AdiContext.class) + " context, "
				+ getObjectName(IContainerController.class) + " parentController) {");
		classBodyBuffer.append("super(context);");

		classBodyBuffer.append(
				"// do not create contents when context is null because that means that class is invoked from dynamic part");
		classBodyBuffer.appendPlus("if (null != context) {");
		String collectionName = buildControl(classBodyBuffer, compositeWrapper, false, "parentController", "coreController");

		if (compositeWrapper instanceof IToolBarContainerWrapper
				&& null != ((IToolBarContainerWrapper) compositeWrapper).getManagedToolBar()
				&& ((IToolBarContainerWrapper) compositeWrapper).getManagedToolBar().getActionOrMenuActionOrSeparator()
						.size() > 0) {
			addCollection(scenarioInput, (ManagedToolBarWrapper) ((IToolBarContainerWrapper) compositeWrapper).getManagedToolBar(),
					"((" + getObjectName(IToolBarContainerController.class) + ") " + collectionName + ").getMToolBarCtler()");
		}
		if (compositeWrapper instanceof IItemCompositeWrapper) {
			Class<?> parentClass = (compositeWrapper instanceof CTabItemType) ? CTabFolderController.class : PShelfController.class;
			classBodyBuffer.appendPlus("if (!((" + getObjectName(parentClass) + ") parentController).isDelayed())");
			classBodyBuffer.append(METHOD_CREATE_CONTENTS + "();");
			classBodyBuffer.addIdent(-1);
		} else if (!(compositeWrapper instanceof ArgTabFolderType) && !(compositeWrapper instanceof ArgPShelfType)) {
			classBodyBuffer.append(METHOD_CREATE_CONTENTS + "();");
		}

		classBodyBuffer.appendMinusPlus("} else");
		classBodyBuffer.append("coreController = parentController;");
		classBodyBuffer.addIdent(-1);
		classBodyBuffer.appendMinus("}\n");

		classBodyBuffer.append("/**");
		classBodyBuffer.append(" * creates contents for controller");
		classBodyBuffer.append(" */");
		classBodyBuffer.appendPlus("public void " + METHOD_CREATE_CONTENTS + "(){");

		if (compositeWrapper instanceof PGroupWrapper)
			CodeGenerationUtil.addPGroupItems(this, (PGroupWrapper) compositeWrapper,
					"(" + getObjectName(PGroupController.class) + ") coreController");

		/*
		 * If compositeWrapper is instance of IncludeWrapper, elements were added by Transform process. they must no be treated at
		 * this moment
		 */
		if (!(compositeWrapper instanceof IncludeWrapper))
			addCollection(scenarioInput, compositeWrapper, "coreController");

		classBodyBuffer.appendMinus("}");

	}
}
