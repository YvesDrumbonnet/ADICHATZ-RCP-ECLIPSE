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
package org.adichatz.studio.xjc.controller;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;

import net.miginfocom.swt.MigLayout;

/**
 * The Class ListenerTypeController.
 */
public abstract class AChooserController extends XjcTextController {

	public static String VALUE = "#VALUE#";

	protected Composite composite;

	protected Label onwardLabel;

	protected Button findButton;

	/**
	 * Instantiates a new listener type combo controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gencode
	 */
	public AChooserController(String id, final IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.BORDER | AdiSWT.FIND_BUTTON | AdiSWT.DELETE_BUTTON;
	}

	@Override
	public void createControl() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		composite = toolkit.createComposite(parentController.getComposite(), SWT.BORDER);
		composite.setLayout(new MigLayout("wrap 4, ins 0", "[grow,fill]0[]0[]0[]"));
		composite.setLayoutData("w 10:10:n");
		control = new Text(composite, SWT.NONE);
		control.setForeground(toolkit.getColors().getForeground());
		control.setBackground(toolkit.getColors().getBackground());
		control.setEditable(false);
		onwardLabel = toolkit.createLabel(composite, "...");
		onwardLabel.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		onwardLabel.setFont(JFaceResources.getBannerFont());
		findButton = new Button(composite, SWT.ICON) {
			@Override
			protected void checkSubclass() {
			}

			@Override
			public Point computeSize(int wHint, int hHint) {
				return new Point(18, 18);
			}
		};
		findButton.setToolTipText(EngineTools.getFromEngineBundle("field.occurences.findToolTipText"));
		findButton.setImage(toolkit.getRegisteredImage("IMG_FIND"));

		Button deleteButton = new Button(composite, SWT.ICON) {
			@Override
			protected void checkSubclass() {
			}

			@Override
			public Point computeSize(int wHint, int hHint) {
				return new Point(18, 18);
			}
		};
		deleteButton.setToolTipText(getFromEngineBundle("field.clearValueToolTip"));
		deleteButton.setImage(toolkit.getRegisteredImage("IMG_DELETE"));
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setValue(null);
			}
		});
		control.setLayoutData("wmax 100%-52px");
		createProposals();
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		composite.setData(VALUE, value);
		displayOnward();
	}

	@Override
	public Object getValue() {
		return convertTargetToModel(composite.getData(VALUE));
	}

	protected void displayOnward() {
		String text = control.getText();
		int index = text.indexOf("\n");
		boolean displayOnwward = false;
		displayOnwward = index > 0;
		if (displayOnwward)
			control.setText(text.substring(0, index - 1));
		else
			displayOnwward = control.computeSize(SWT.DEFAULT, SWT.DEFAULT).x > composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		String previousText = onwardLabel.getText();
		String currentText = displayOnwward ? "..." : "   ";
		if (!previousText.equals(currentText)) {
			onwardLabel.setText(currentText);
		}
	}

	@Override
	public void endLifeCycle() {
		super.endLifeCycle();
		if (null != control) // ontrol could be null on basic item
			displayOnward();
	}

	protected void reflow() {
	}

	public Button getFindButton() {
		return findButton;
	}
}
