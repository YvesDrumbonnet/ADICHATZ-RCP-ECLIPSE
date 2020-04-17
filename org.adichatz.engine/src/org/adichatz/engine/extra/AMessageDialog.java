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

import org.adichatz.engine.action.CopyClipboardAction;
import org.adichatz.engine.common.AdichatzApplication;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AAdiFormDialog.
 * 
 * @author Yves Drumbonnet
 *
 */
public abstract class AMessageDialog extends ADialog {

	/** The messages. */
	protected String[] messages;

	private Image labelImage;

	/** The kind. */
	protected int kind;

	protected FormToolkit toolkit;

	protected String[] dialogButtonLabels;

	/**
	 * Instantiates a new a message dialog.
	 *
	 * @param shell
	 *            the shell
	 * @param kind
	 *            the kind
	 * @param dialogButtonLabels
	 *            the dialog button labels
	 * @param image
	 *            the image
	 * @param labelImage
	 *            the label image
	 * @param messages
	 *            the messages
	 */
	public AMessageDialog(Shell shell, int kind, String[] dialogButtonLabels, Image labelImage, String... messages) {
		super(shell);
		this.messages = messages;
		this.labelImage = labelImage;
		this.kind = kind;
		this.dialogButtonLabels = dialogButtonLabels;
		try {
			toolkit = AdichatzApplication.getInstance().getFormToolkit();
		} catch (Exception e) { // toolkit could be null when error occurs at startup
			toolkit = new FormToolkit(Display.getCurrent());
		}
		create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		parent.setBackground(toolkit.getColors().getBackground());
		ScrolledForm scrolledForm = toolkit.createScrolledForm(parent);
		skinnedParent = scrolledForm;
		scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));

		int wrap = 0;
		if (null != labelImage) {
			Label label = toolkit.createLabel(scrolledForm.getBody(), "");
			label.setImage(labelImage);
			wrap++;
		}
		if (messages.length > 1 && null != messages[1]) {
			wrap++;
			final Label label = toolkit.createLabel(scrolledForm.getBody(), messages[1]);
			label.setFont(JFaceResources.getBannerFont());
			label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

			final MenuManager menuMgr = new MenuManager();
			menuMgr.setRemoveAllWhenShown(true);
			final Menu menu = menuMgr.createContextMenu(label);
			menuMgr.addMenuListener(new IMenuListener() {
				public void menuAboutToShow(IMenuManager mgr) {
					menuMgr.add(new CopyClipboardAction(label.getDisplay(), label.getText()));
				}
			});
			label.setMenu(menu);
		}
		scrolledForm.getBody().setLayout(new MigLayout("wrap " + wrap, "[grow]"));

		Section messageSection = toolkit.createSection(scrolledForm.getBody(), Section.FOCUS_TITLE);
		messageSection.setLayoutData("spanx 2, gapbottom 20");

		if (messages.length > 2 && null != messages[2]) {
			messageSection.setText(messages[2]);
			toolkit.createCompositeSeparator(messageSection);
		} else
			messageSection.setText("");

		Composite messageCient = toolkit.createComposite(messageSection, SWT.WRAP);
		messageCient.setLayout(new MigLayout("wrap", "", "[grow][fill]"));
		toolkit.paintBordersFor(messageCient);
		messageSection.setClient(messageCient);
		messageSection.setBackground(toolkit.getColors().getBackground());

		createMessage(messageCient);

		if (messages.length > 3 && null != messages[3]) {
			Label label = toolkit.createLabel(messageCient, messages[3]);
			label.setFont(JFaceResources.getBannerFont());
			label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
			label.setLayoutData("gaptop 30");
		}

		Composite separator = toolkit.createCompositeSeparator(parent);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 2;
		gridData.horizontalIndent = 5;
		separator.setLayoutData(gridData);

		messageCient.layout();
		scrolledForm.reflow(true);
		applyDialogFont(scrolledForm.getBody());
		return scrolledForm;
	}

	/**
	 * Creates the message.
	 * 
	 * @param parent
	 *            the parent
	 * @param toolkit
	 *            the toolkit
	 */
	public void createMessage(Composite parent) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets .Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (messages.length > 0 && null != messages[0])
			newShell.setText(messages[0]);
	}
}
