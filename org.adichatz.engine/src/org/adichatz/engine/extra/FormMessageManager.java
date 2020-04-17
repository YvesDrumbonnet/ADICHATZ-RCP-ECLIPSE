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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.adichatz.engine.common.AdichatzApplication;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.IMessage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;

// TODO: Auto-generated Javadoc
/**
 * The Class FormMessageManager.
 */
public class FormMessageManager {
	public static String FORM_MESSAGE_MANAGER = "FORM_MESSAGE_MANAGER";

	/** The form. */
	protected Form form;

	private Shell messageShell;

	/**
	 * Instantiates a new form message manager.
	 * 
	 * @param managedForm
	 *            the managed form
	 */
	public FormMessageManager(Form form) {
		this.form = form;
		// Useful for avoiding several Form Message Manager creation when content is dynamically created
		form.setData(FORM_MESSAGE_MANAGER, this);
		createContents();
	}

	/**
	 * Creates the contents.
	 */
	private void createContents() {
		// Add an option to process form update in a pool to enhance opening of editor when there are many errors
		// See
		// - org.adichatz.engine.controller.AEntityManagerController.displayValidation()
		// - org.adichatz.engine.data.FieldBindingManager.bindTargetToModel()
		// AdichatzApplication.getInstance().getFormToolkit().decorateFormHeading(form);
		form.addMessageHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				String title = e.getLabel();
				Object href = e.getHref();
				if (href instanceof IMessage[]) {
				}
				Point hl = ((Control) e.widget).toDisplay(0, 0);
				hl.x += 10;
				hl.y += 10;
				messageShell = new Shell(form.getShell(), SWT.ON_TOP | SWT.TOOL);
				messageShell.setImage(getImage(form.getMessageType()));
				messageShell.setText(title);
				messageShell.setLayout(new FillLayout());
				FormText text = AdichatzApplication.getInstance().getFormToolkit().createFormText(messageShell, true);
				configureFormText(form, text);
				if (href instanceof IMessage[])
					text.setText(createFormTextContent((IMessage[]) href), true, false);
				messageShell.setLocation(hl);
				messageShell.pack();
				messageShell.open();
				messageShell.addShellListener(new ShellListener() {

					@Override
					public void shellIconified(ShellEvent e) {
					}

					@Override
					public void shellDeiconified(ShellEvent e) {
					}

					@Override
					public void shellDeactivated(ShellEvent e) {
						messageShell.close();
					}

					@Override
					public void shellClosed(ShellEvent e) {
					}

					@Override
					public void shellActivated(ShellEvent e) {
					}
				});
			}
		});
	}

	/**
	 * Configure form text. Form text is the &quot;banner&quot; form text where messages are displayed
	 * 
	 * @param form
	 *            the form
	 * @param text
	 *            the text
	 */
	private void configureFormText(final Form form, FormText text) {
		text.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent hyperlinkEvent) {
				String is = (String) hyperlinkEvent.getHref();
				try {
					int index = Integer.parseInt(is);
					IMessage[] messages = form.getChildrenMessages();
					IMessage message = messages[index];
					Control c = message.getControl();
					((FormText) hyperlinkEvent.widget).getShell().close();
					if (c != null)
						c.setFocus();
				} catch (NumberFormatException e) {
				} catch (NullPointerException e) {
					if (hyperlinkEvent.widget instanceof FormText) {
						((FormText) hyperlinkEvent.widget).getParent().dispose();
					}
				}
			}
		});
		text.setImage(Dialog.DLG_IMG_MESSAGE_ERROR, getImage(IMessageProvider.ERROR));
		text.setImage(Dialog.DLG_IMG_MESSAGE_WARNING, getImage(IMessageProvider.WARNING));
		text.setImage(Dialog.DLG_IMG_MESSAGE_INFO, getImage(IMessageProvider.INFORMATION));
	}

	/**
	 * Creates the form text content.
	 * 
	 * @param messages
	 *            the messages
	 * 
	 * @return the string
	 */
	private String createFormTextContent(IMessage[] messages) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println("<form>");
		for (int i = 0; i < messages.length; i++) {
			IMessage message = messages[i];
			pw.print("<li vspace=\"false\" style=\"image\" indent=\"16\" value=\"");
			switch (message.getMessageType()) {
			case IMessageProvider.ERROR:
				pw.print(Dialog.DLG_IMG_MESSAGE_ERROR);
				break;
			case IMessageProvider.WARNING:
				pw.print(Dialog.DLG_IMG_MESSAGE_WARNING);
				break;
			case IMessageProvider.INFORMATION:
				pw.print(Dialog.DLG_IMG_MESSAGE_INFO);
				break;
			}
			pw.print("\"> <a href=\"");
			pw.print(i + "");
			pw.print("\">");
			if (message.getPrefix() != null)
				pw.print(message.getPrefix());
			pw.print(message.getMessage());
			pw.println("</a></li>");
		}
		pw.println("</form>");
		pw.flush();
		return sw.toString();
	}

	/**
	 * Gets the image.
	 * 
	 * @param type
	 *            the type
	 * 
	 * @return the image
	 */
	private static Image getImage(int type) {
		switch (type) {
		case IMessageProvider.ERROR:
			return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_ERROR);
		case IMessageProvider.WARNING:
			return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING);
		case IMessageProvider.INFORMATION:
			return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO);
		}
		return null;
	}

	public Form getForm() {
		return form;
	}

}
