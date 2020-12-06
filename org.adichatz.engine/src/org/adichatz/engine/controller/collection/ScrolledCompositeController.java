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
package org.adichatz.engine.controller.collection;

import org.adichatz.engine.controller.ADirtyContainerController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IControlController;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class CompositeController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class ScrolledCompositeController extends ADirtyContainerController implements IControlController {

	private SharedScrolledComposite scrolledComposite;

	/**
	 * Instantiates a new composite controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 * @param pluginResources
	 *            the plugin resources
	 */
	public ScrolledCompositeController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL;
	}

	/**
	 * Creates the control.
	 * 
	 * @param style
	 *            the style
	 */
	public void createControl() {
		super.createControl();
		/**
		 * In order to manage Scroll bar appearance, follow these steps<br>
		 * - Create a parent composite<br>
		 * - Create a ShareScrolledComposite which is the unique component inside the parent composite<br>
		 * - Create a composite to be the content of the scrolled composite<br>
		 * - Create a ControlListener on parent composite to limit size of SharedScrolledComposite.<br>
		 * - Create a listener to compute min size (required size managed by scrolling)
		 */
		final Composite parent = toolkit.createComposite(parentController.getComposite());
		parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
		scrolledComposite = new SharedScrolledComposite(parent, style) {
		};
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		toolkit.adapt(scrolledComposite);
		composite = toolkit.createComposite(scrolledComposite);
		scrolledComposite.setContent(composite);
		composite.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "grow,fill"));
		dirtyContainer = composite;

		parent.addControlListener(new ControlAdapter() {
			Point size = new Point(-1, -1);

			@Override
			public void controlResized(ControlEvent e) {
				Point newSize = parent.getSize();
				if (!size.equals(newSize)) {
					scrolledComposite.setLayoutData("w 0:0:" + (newSize.x - 0) + ", h 0:0:" + (newSize.y - 0));
					size = newSize;
				}
			}
		});

		composite.addListener(SWT.Resize, new Listener() {
			Point size = new Point(-1, -1);

			public void handleEvent(Event e) {
				Point newSize = composite.getSize();
				if (!size.equals(newSize)) {
					scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					scrolledComposite.reflow(true);
					size = newSize;
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ACollectionController#startLifeCycle()
	 */
	@Override
	public void startLifeCycle() {
		super.startLifeCycle();
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public SharedScrolledComposite getControl() {
		return scrolledComposite;
	}
}
