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

import org.adichatz.engine.common.IPreferenceConstant;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.studio.util.StudioLog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

// TODO: Auto-generated Javadoc
/**
 * The Class StudioLogPreferences.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class StudioLogPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/** The property layout. */
	protected GridLayout propertyLayout = new GridLayout();

	/** The log group. */
	private Group logGroup;

	/** The field composite. */
	protected Composite fieldComposite;

	/**
	 * Instantiates a new adichatz tool preferences.
	 */
	public StudioLogPreferences() {
		super(GRID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		getFieldEditorParent().setLayout(new GridLayout());
		fieldComposite = new Composite(getFieldEditorParent(), SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		fieldComposite.setLayout(gridLayout);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		fieldComposite.setLayoutData(gridData);

		logGroup = new Group(fieldComposite, SWT.NONE);
		logGroup.setText(getFromStudioBundle("studio.logProperties"));

		/*
		 * Trace
		 */
		addField(new BooleanFieldEditor(IPreferenceConstant.logTraceEnabled, getFromStudioBundle("studio.logTrace"), logGroup));

		/*
		 * Debug
		 */
		addField(new BooleanFieldEditor(IPreferenceConstant.logDebugEnabled, getFromStudioBundle("studio.logDebug"), logGroup));

		/*
		 * Info
		 */
		addField(new BooleanFieldEditor(IPreferenceConstant.logInfoEnabled, getFromStudioBundle("studio.logInfo"), logGroup));

		/*
		 * Warning
		 */
		addField(new BooleanFieldEditor(IPreferenceConstant.logWarningEnabled, getFromStudioBundle("studio.logWarning"), logGroup));

		/*
		 * Error
		 */
		addField(new BooleanFieldEditor(IPreferenceConstant.logErrorEnabled, getFromStudioBundle("studio.logError"), logGroup));

		addField(new BooleanFieldEditor(IPreferenceConstant.logErrorEnabled, getFromStudioBundle("studio.consoleDisplay"),
				logGroup));
		addField(new BooleanFieldEditor(IPreferenceConstant.dialogLogError, getFromStudioBundle("studio.dialogDisplay"), logGroup));
		addField(new BooleanFieldEditor(IPreferenceConstant.statusLogError, getFromStudioBundle("studio.statusDisplay"), logGroup));
		FontData fontData = logGroup.getFont().getFontData()[0];
		Font font = new Font(logGroup.getDisplay(), new FontData(fontData.getName(), fontData.getHeight(), SWT.BOLD));
		logGroup.setFont(font);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		super.createContents(parent);
		propertyLayout.numColumns = 1;
		propertyLayout.marginTop = 10;
		propertyLayout.marginBottom = 10;
		propertyLayout.marginLeft = 10;
		propertyLayout.marginRight = 10;
		propertyLayout.horizontalSpacing = 20;
		propertyLayout.verticalSpacing = 10;
		logGroup.setLayout(propertyLayout);
		return parent;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		boolean result = super.performOk();
		if (result)
			StudioLog.getInstance().initialize(getPreferenceStore());
		return result;

	}
}