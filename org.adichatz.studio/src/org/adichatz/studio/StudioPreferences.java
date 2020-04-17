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
package org.adichatz.studio;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.util.StudioUtil;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.pde.internal.ui.SWTFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

// TODO: Auto-generated Javadoc
/**
 * The Class StudioPreferences.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class StudioPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Instantiates a new adichatz tool preferences.
	 */
	public StudioPreferences() {
		super(GRID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.implementation.rcp.ALogPreferences#createFieldEditors()
	 */
	@SuppressWarnings("restriction")
	@Override
	protected void createFieldEditors() {
		Composite comp = SWTFactory.createComposite(getFieldEditorParent(), 1, 1, GridData.FILL_HORIZONTAL);

		Group defaulParamGroup = SWTFactory.createGroup(comp, getFromStudioBundle("studio.preference.default.param"), 1, 1,
				GridData.FILL_HORIZONTAL);
		Composite defaulParamSpacer = SWTFactory.createComposite(defaulParamGroup, 1, 1, GridData.FILL_HORIZONTAL);

		BooleanFieldEditor formatGeneratedClass = new BooleanFieldEditor(IScenarioConstants.FORMAT_GENERATED_CLASS,
				getFromStudioBundle("studio.preference.default.format.classes"), SWT.NONE, defaulParamSpacer);
		formatGeneratedClass.fillIntoGrid(defaulParamSpacer, 2);
		addField(formatGeneratedClass);

		BooleanFieldEditor createXmlTrace = new BooleanFieldEditor(IScenarioConstants.CREATE_XML_TRACE,
				getFromStudioBundle("studio.preference.default.xml.trace"), SWT.NONE, defaulParamSpacer);
		createXmlTrace.fillIntoGrid(defaulParamSpacer, 2);
		addField(createXmlTrace);

		BooleanFieldEditor askForSwitchingFile = new BooleanFieldEditor(IStudioConstants.ASK_FOR_SWITCHING_FILE,
				getFromStudioBundle("studio.preference.default.ask.switch.file.when.saving"), SWT.NONE, defaulParamSpacer);
		addField(askForSwitchingFile);

		StringFieldEditor scenarioTreeGenerationClassName = new StringFieldEditor(IStudioConstants.SCENARIO_TREE_GENERATION_URI,
				getFromStudioBundle("studio.preference.default.generation.scenarioTree.uri"), defaulParamSpacer) {
			@Override
			protected boolean checkState() {
				String fieldValue = getStringValue();
				if (!EngineTools.isEmpty(fieldValue))
					try {
						Class<?> scenarioTreeGenerationClass = StudioUtil.getScenarioTreeGenerationClass(fieldValue);
						if (null == scenarioTreeGenerationClass) {
							showErrorMessage(getFromStudioBundle("studio.scenarioTree.generation.uri.not.valid", fieldValue));
							return false;
						}
					} catch (Exception e) {
						showErrorMessage(getFromStudioBundle("studio.scenarioTree.generation.uri.not.valid", fieldValue));
						return false;
					}
				return super.checkState();
			}
		};

		addField(scenarioTreeGenerationClassName);

	}

	/*
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(GeneratorConstants.ADICHATZ_STUDIO_PREFERENCE_STORE);
	}

	@Override
	public boolean performOk() {
		super.performOk();
		return true;
	}
}