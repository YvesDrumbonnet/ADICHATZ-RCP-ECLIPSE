/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.engine.controller.nebula;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class PGroupController.
 */
public class ScrolledPGroupController extends PGroupController {

	private SharedScrolledComposite scrolledComposite;

	/**
	 * Instantiates a new scrolled pgroup controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 * @param pluginResources
	 *            the plugin resources
	 */
	public ScrolledPGroupController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	public void createControl() {
		super.createControl();
		pgroup = toolkit.createPGroup(parentController.getComposite(), style);
		pgroup.setLayout(new FillLayout());
		pgroup.addExpandListener(new ExpandListener() {

			@Override
			public void itemExpanded(ExpandEvent e) {
				pgroup.layout();
			}

			@Override
			public void itemCollapsed(ExpandEvent e) {
			}
		});
		dirtyContainer = pgroup;
		pgroup.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
		scrolledComposite = new SharedScrolledComposite(pgroup, SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL) {
		};
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		toolkit.adapt(scrolledComposite);
		composite = toolkit.createComposite(scrolledComposite);
		scrolledComposite.setContent(composite);
		pgroup.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				scrolledComposite.setLayoutData("w 0:0:" + (pgroup.getSize().x - 2));
			}
		});

		composite.addListener(SWT.Resize, new Listener() {
			Point size = new Point(-1, -1);

			public void handleEvent(Event e) {
				Point newSize = composite.getSize();
				if (!size.equals(newSize)) {
					size = newSize;
					scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					scrolledComposite.reflow(true);
				}
			}
		});
	}

	public SharedScrolledComposite getScrolledComposite() {
		return scrolledComposite;
	}

	@Override
	public void reflow() {
		scrolledComposite.reflow(true);
		super.reflow();
	}
}
