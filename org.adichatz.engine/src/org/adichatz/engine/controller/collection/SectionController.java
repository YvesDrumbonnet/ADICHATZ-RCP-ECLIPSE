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
package org.adichatz.engine.controller.collection;

import org.adichatz.engine.controller.ADirtyContainerController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IToolBarContainerController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;
import org.eclipse.ui.forms.widgets.Twistie;

// TODO: Auto-generated Javadoc
/**
 * The Class SectionController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class SectionController extends ADirtyContainerController implements IToolBarContainerController {

	/** The section. */
	private Section section;

	/** The toolBar. */
	private ToolBar toolBar = null;

	/** The m tool bar ctler. */
	private ManagedToolBarController mToolBarCtler;

	/** The client style. */
	private int clientStyle = SWT.NONE;

	/**
	 * Instantiates a new section controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public SectionController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	public void createControl() {
		super.createControl();
		section = toolkit.createSection(parentController.getComposite(), style);
		Color titleForeground = null;
		AReskinManager reskinManager = AReskinManager.getInstance();
		if (null != reskinManager) {
			titleForeground = reskinManager.getColor("Section > Label", "color", null, null);
			reskinManager.addSkinnedWidget(section);
		}
		for (Control child : section.getChildren()) {
			if (child instanceof Twistie) {
				section.setData("#TWISTIE#", child);
				if (null != titleForeground)
					((Twistie) child).setDecorationColor(titleForeground);
			} else {
				section.setData("#LABEL#", child);
				if (null != titleForeground)
					((Label) child).setForeground(child.getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
			}
		}

		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				if (null != toolBar) {
					toolBar.setVisible((Boolean) e.data);
				}
			}
		});

		composite = toolkit.createComposite(section, clientStyle);
		composite.setLayoutData("wmin 100, hmin 100");

		section.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				// Reflow is needed when section contains specific controllers as RefText, ExtraText, ImageViewer...
				internalReflow();
			}
		});
		section.setClient(composite);
		composite.setLayoutData("w 64:n:n");
		dirtyContainer = (Control) section.getData("#LABEL#");
	}

	/**
	 * Gets the section.
	 * 
	 * @return the section
	 */
	@Override
	public Section getControl() {
		return section;
	}

	/**
	 * Gets the tool bar controller.
	 * 
	 * @return the tool bar controller
	 */
	public ManagedToolBarController getMToolBarCtler() {
		if (null == mToolBarCtler)
			mToolBarCtler = new ManagedToolBarController("Internal$$FormToolBarController", this, genCode);
		return mToolBarCtler;
	}

	/**
	 * Sets the toolbar.
	 * 
	 * @param toolBar
	 *            the new toolbar
	 */
	public void setToolBar(ToolBar toolBar) {
		this.toolBar = toolBar;
	}

	/**
	 * Reflows this section and all the parents up the hierarchy until a SharedScrolledComposite is reached.
	 */
	public void reflow() {
		section.layout();
		internalReflow();
	}

	/**
	 * Internal reflow.
	 */
	private void internalReflow() {
		Composite c = section;
		while (c != null && !c.isDisposed()) {
			c.setRedraw(false);
			c = c.getParent();
			if (c instanceof SharedScrolledComposite) {
				break;
			}
		}
		c = section;
		while (c != null && !c.isDisposed()) {
			c.layout(true);
			c = c.getParent();
			if (c instanceof SharedScrolledComposite) {
				((SharedScrolledComposite) c).reflow(true);
				break;
			}
		}
		c = section;
		while (c != null && !c.isDisposed()) {
			c.setRedraw(true);
			c = c.getParent();
			if (c instanceof SharedScrolledComposite) {
				break;
			}
		}
	}

	/**
	 * Sets the client style.
	 * 
	 * @param clientStyle
	 *            the new client style
	 */
	public void setClientStyle(int clientStyle) {
		this.clientStyle = clientStyle;
	}
}
