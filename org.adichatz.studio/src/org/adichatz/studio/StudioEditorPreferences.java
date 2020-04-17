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
package org.adichatz.studio;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import org.adichatz.studio.util.IStudioConstants;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.pde.internal.ui.SWTFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
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
@SuppressWarnings("restriction")
public class StudioEditorPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Instantiates a new adichatz tool preferences.
	 */
	public StudioEditorPreferences() {
		super(GRID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.implementation.rcp.ALogPreferences#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		setPreferenceStore(StudioRcpPlugin.getDefault().getPreferenceStore());
		Composite comp = SWTFactory.createComposite(getFieldEditorParent(), 1, 1, GridData.FILL_HORIZONTAL);

		Group treeEditorGroup = SWTFactory.createGroup(comp, getFromStudioBundle("studio.preference.treeEditor"), 1, 1,
				GridData.FILL_HORIZONTAL);
		Composite treeEditorSpacer = SWTFactory.createComposite(treeEditorGroup, 1, 1, GridData.FILL_HORIZONTAL);
		BooleanFieldEditor generateClassAfterSaving = new BooleanFieldEditor(IStudioConstants.GENERATE_CLASS_AFTER_SAVING,
				getFromStudioBundle("studio.preference.treeEditor.automaticGeneration"), SWT.NONE, treeEditorSpacer);
		addField(generateClassAfterSaving);

		Group customizationGroup = SWTFactory.createGroup(comp, getFromStudioBundle("studio.preference.customization"), 1, 1,
				GridData.FILL_HORIZONTAL);
		Composite customizationSpacer = SWTFactory.createComposite(customizationGroup, 1, 1, GridData.FILL_HORIZONTAL);
		FileTextFieldEditor customizationFileModel = new CustomFileTextFieldEditor(IStudioConstants.CUSTOMIZATION_FILE_MODEL,
				getFromStudioBundle("studio.preference.customization.model"), customizationSpacer);
		customizationFileModel.setFilterExtension("*.xml");
		addField(customizationFileModel);
		FileTextFieldEditor customizationFileRcp = new CustomFileTextFieldEditor(IStudioConstants.CUSTOMIZATION_FILE_RCP,
				getFromStudioBundle("studio.preference.customization.rcp"), customizationSpacer);
		customizationFileRcp.setFilterExtension("*.xml");
		addField(customizationFileRcp);

		FontData fontData = treeEditorGroup.getFont().getFontData()[0];
		Font font = new Font(treeEditorGroup.getDisplay(), new FontData(fontData.getName(), fontData.getHeight(), SWT.BOLD));
		treeEditorGroup.setFont(font);
		customizationGroup.setFont(font);

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
		setPreferenceStore(StudioRcpPlugin.getDefault().getPreferenceStore());
	}

	@Override
	public boolean performOk() {
		super.performOk();
		return true;
	}
}