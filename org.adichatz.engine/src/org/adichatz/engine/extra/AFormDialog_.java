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
package org.adichatz.engine.extra;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

// TODO: Auto-generated Javadoc
/**
 * The Class AFormDialog.
 *
 * @author Yves Drumbonnet
 */
public abstract class AFormDialog_ extends ADialog {

	protected ScrolledForm scrolledForm;

	/** The managed form. */
	protected ManagedForm managedForm;

	/** The toolkit. */
	protected AdiFormToolkit toolkit;

	/**
	 * Instantiates a new a form dialog.
	 *
	 * @param shell
	 *            the shell
	 * @param toolkit
	 *            the toolkit
	 */
	public AFormDialog_(Shell shell, AdiFormToolkit toolkit) {
		super(shell);
		this.toolkit = toolkit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		toolkit = AdichatzApplication.getInstance().getFormToolkit();

		scrolledForm = toolkit.createScrolledForm(parent);
		skinnedParent = scrolledForm;

		// scrolledForm = new ScrolledForm(parent, SWT.V_SCROLL | SWT.H_SCROLL);
		// scrolledForm.setExpandHorizontal(true);
		// scrolledForm.setExpandVertical(true);
		// scrolledForm.setFont(JFaceResources.getHeaderFont());

		scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		managedForm = new ManagedForm(toolkit, scrolledForm);

		createFormContent();
		applyDialogFont(scrolledForm.getBody());
		new FormMessageManager(managedForm.getForm().getForm());
		getShell().setBackground(toolkit.getColors().getBackground());
		return scrolledForm;
	}

	/**
	 * Creates the form content.
	 */
	protected abstract void createFormContent();

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		scrolledForm.getBody().pack();
		addPostReskinListener(() -> {
			parent.setBackground(toolkit.getColors().getBackground());
		});
		return control;
	}

	/**
	 * Creates the help button.
	 * 
	 * @param parent
	 *            the parent
	 * @param label
	 *            the label
	 * @param specifyHelp
	 *            the specify help
	 * @param messageHelp
	 *            the message help
	 */
	public void createHelp(final Composite parent, String label, final String specifyHelp, final String messageHelp) {
		createBruteHelp(parent, getFromEngineBundle(label), getFromEngineBundle(specifyHelp), getFromEngineBundle(messageHelp));
	}

	/**
	 * Creates the help button.
	 * 
	 * @param parent
	 *            the parent
	 * @param label
	 *            the label
	 * @param specifyHelp
	 *            the specify help
	 * @param messageHelp
	 *            the message help
	 */
	public void createBruteHelp(final Composite parent, String label, final String specifyHelp, final String messageHelp) {
		Button helpBtn;
		helpBtn = new Button(parent, SWT.ICON);
		helpBtn.setImage(toolkit.getRegisteredImage("IMG_HELP"));
		helpBtn.setToolTipText(label);
		helpBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EngineTools.openDialog(MessageDialog.INFORMATION, getFromEngineBundle("helpFor", specifyHelp), messageHelp);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	public void okPressed() {
		// super.okPressed() modifier method is protected changed to public
		super.okPressed();
	}
}
