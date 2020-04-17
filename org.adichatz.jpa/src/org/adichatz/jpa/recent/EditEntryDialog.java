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
package org.adichatz.jpa.recent;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.extra.AFormDialog;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class EditEntryDialog.
 */
public class EditEntryDialog extends AFormDialog {
	private Object recentElement;

	private Text entryLabelTXT;

	/**
	 * Instantiates a new edits the entry dialog.
	 *
	 * @param shell
	 *            the shell
	 * @param recentElement
	 *            the recent element
	 */
	public EditEntryDialog(Shell shell, AdiFormToolkit toolkit, Object recentElement) {
		super(shell, toolkit);
		setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
		this.recentElement = recentElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.AFormDialog#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent() {
		String title = getFromJpaBundle("recent.edit.entry");
		getShell().setText(title);
		getShell().setImage(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_EDITOR"));
		scrolledForm.setText(title);
		IRecentOpenEditor recentOpenEditor = (IRecentOpenEditor) recentElement;
		scrolledForm.setImage(RecentUtil.getImage(recentOpenEditor));

		scrolledForm.getBody().setLayout(new MigLayout("wrap 1", "grow,fill", "grow,fill"));

		Composite parent = toolkit.createComposite(scrolledForm.getBody());
		parent.setLayout(new MigLayout("wrap 2, ins 0", "[][grow,fill]", "[]20[grow,fill]"));

		toolkit.createLabel(parent, getFromJpaBundle("recent.editor.label"));
		entryLabelTXT = toolkit.createText(parent, recentOpenEditor.getLabel());
		entryLabelTXT.addModifyListener((e) -> {
			getButton(IDialogConstants.OK_ID).setEnabled(!entryLabelTXT.getText().isEmpty());
		});
		Label descriptionLBL = toolkit.createLabel(parent, RecentUtil.getRecentToolTipText(recentOpenEditor));
		descriptionLBL.setLayoutData("span 2, gap 20");
		descriptionLBL.setFont(EngineTools.getModifiedFont(descriptionLBL.getFont(), SWT.BOLD));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		getShell().setSize(600, 250);
		return super.createContents(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	public void okPressed() {
		((IRecentOpenEditor) recentElement).setLabel(entryLabelTXT.getText());
		((IRecentOpenEditor) recentElement).getParamMap().put(ParamMap.TITLE, entryLabelTXT.getText());
		super.okPressed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.FormDialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		Control buttonBar = super.createButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		getButton(IDialogConstants.CANCEL_ID).setFocus();
		return buttonBar;
	}
}
