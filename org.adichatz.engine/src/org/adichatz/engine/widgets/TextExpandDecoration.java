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
package org.adichatz.engine.widgets;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiControlDecoration.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class TextExpandDecoration extends AdiControlDecoration {

	public TextExpandDecoration(Text text) {
		this(text, SWT.TOP, AdiControlDecoration.WIDGET_WIDGET_OPEN);
	}

	/**
	 * Instantiates a new adi control decoration.
	 * 
	 * @param control
	 *            the control
	 * @param position
	 *            the position
	 * @param image
	 *            the image
	 */
	public TextExpandDecoration(final Text text, int position, int image) {
		super(text, position, image);

		addSelectionListener(new SelectionAdapter() {
			private boolean doit = true;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (doit) {
					doit = false;
					Rectangle clientArea = text.getDisplay().getClientArea();
					final Shell dialog = new Shell(text.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE);
					dialog.setLayout(new MigLayout("wrap 2", "[fill, grow][]"));
					AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
					final Text expandText = toolkit.createText(dialog, text.getText());
					expandText.setEnabled(text.isEnabled());
					expandText.setEditable(text.getEditable());
					expandText.setLayoutData("w 400:400:" + (clientArea.width - 20) + ", h n:" + text.getSize().y + ":n");

					if (text.isEnabled() && text.isEnabled()) {
						Button ok = new Button(dialog, SWT.IMAGE_BMP);
						ok.setImage(toolkit.getRegisteredImage("IMG_ACCEPT"));
						ok.setToolTipText(getFromEngineBundle("field.acceptToolTip"));
						ok.setLayoutData("ax right, ay top");
						ok.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								if (!expandText.getText().equals(text.getText())) {
									text.setText(expandText.getText());
									text.notifyListeners(SWT.Modify, null);
								}
								dialog.close();
							}
						});
						dialog.setDefaultButton(ok);
					}

					dialog.pack();

					/*
					 * Compute position for the Dialog window
					 */
					Point toolbarPoint = text.toDisplay(0, 0);
					Point newPoint = new Point(Math.min(clientArea.width - dialog.getBounds().width - 5, toolbarPoint.x - 25),
							Math.min(clientArea.height - 25, toolbarPoint.y + 25));
					dialog.setLocation(newPoint);

					dialog.open();
					doit = true;
				}
			}
		});

	}

}
