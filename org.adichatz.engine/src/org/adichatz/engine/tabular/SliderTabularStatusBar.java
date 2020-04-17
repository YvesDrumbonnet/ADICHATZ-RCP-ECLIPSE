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
package org.adichatz.engine.tabular;

import org.adichatz.engine.controller.collection.ATabularController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Slider;

// TODO: Auto-generated Javadoc
/**
 * The Class SliderTabularStatusBar.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class SliderTabularStatusBar extends ABasicTabularStatusBar {

	/** The slider. */
	private Slider slider;

	public SliderTabularStatusBar(ATabularController<?> tabularController, String key) {
		super(tabularController, key);
	}

	@Override
	protected void createControl() {
		columnConstraint = "[][grow,fill][][][]";

		slider = new Slider(this, SWT.HORIZONTAL | SWT.BORDER);
		slider.setMinimum(1);
		slider.setIncrement(1);
		slider.setThumb(1);

		slider.setBackground(backgroundColor);

		slider.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageLabel();
			}
		});
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (0 != click++) {
					fireWidgetSelection();
					click = 0;
				}
			}

			@Override
			public void mouseUp(MouseEvent e) {
				if (tabularController.getQueryManager().isPaginated()) {
					Integer maxResults = getMaxResults();
					if (null != maxResults) {
						int selection = slider.getSelection();
						if (1 == click && previousPageSelection != selection) {
							doit = false;
							tabularController.refresh((selection - 1) * maxResults, false);
							doit = true;
							fireWidgetSelection();
							click = 0;
							previousPageSelection = selection;
							previousSelectionIndex = -1;
						}
					}
				}
			}
		});
		slider.setLayoutData("hidemode 2");
	}

	@Override
	protected int getPageSelection() {
		return slider.getSelection();
	}

	/**
	 * Fire widget selection.
	 * 
	 * @param selection
	 *            the selection
	 */
	public void fireWidgetSelection() {
		if (doit) {
			if (tabularController.getQueryManager().isPaginated()) {
				int selection = slider.getSelection();
				if (0 == getFirstResult()) // force selection to first step when relaunching query
					selection = 1;
				if (selection != previousPageSelection) {
					slider.setVisible(true);
					if (slider.getMaximum() != pageNumber + 1)
						slider.setMaximum(pageNumber + 1);
					slider.setSelection(selection);
					setPageLabel();
				}
			}
			layout();
		}
	}

	@Override
	public void showNavigation(boolean visible) {
		slider.setVisible(visible);
	}

	public void layout() {
		super.layout();
	};

	@Override
	public void setLayoutData(Object layoutData) {
		super.setLayoutData(layoutData);
	}
}
