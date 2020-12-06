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
package org.adichatz.engine.e4.part;

import javax.annotation.PostConstruct;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.menu.ANodeController;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.controller.menu.NavigatorPath;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ExpandEvent;
import org.eclipse.swt.events.ExpandListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class GroupNavigator.
 */
public class GroupNavigator extends ANavigator {

	/** The content. */
	private Composite content;

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	private Color defaulBackground;

	private Color defaulForeground;

	protected AdiFormToolkit toolkit;

	/**
	 * Creates the control.
	 *
	 * @param parent
	 *            the parent
	 * @param application
	 *            the application
	 */
	@PostConstruct
	public void createControl(Composite parent, MApplication application) {
		toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		scrolledForm = toolkit.createScrolledForm(parent);
		scrolledForm.getBody().setLayout(new MigLayout("wrap 1, ins 0, hidemode 2", "grow,fill", "grow,fill"));

		defaulBackground = toolkit.getColors().getBackground();
		defaulForeground = toolkit.getColors().getColor(JFacePreferences.HYPERLINK_COLOR);
		if (null != AReskinManager.getInstance())
			AReskinManager.getInstance().addReskinListener(() -> {
				AdiFormToolkit toolkit2 = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class); // toolkit from caller could be disposed (see AdiReskinManager)
				defaulForeground = toolkit2.getColors().getColor(JFacePreferences.HYPERLINK_COLOR);
				defaulBackground = toolkit2.getColors().getBackground();
			}, scrolledForm);
		refreshNavigator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.part.ANavigator#refreshInput(org.adichatz.engine.controller.menu.NavigatorPath)
	 */
	@Override
	protected void refreshInput(String navigatorId) {
		NavigatorPath navigatorPath = getNavigatorPath(navigatorId);
		if (null != navigatorPath) {
			if (null != content) {
				content.setVisible(false);
				content.dispose();
				EngineTools.reinitMiglayout(scrolledForm.getBody());
			}

			content = toolkit.createComposite(scrolledForm.getBody());
			content.setLayout(new MigLayout("wrap 1, hidemode 2, ins 0", "grow,fill"));
			content.setLayoutData("h 0:n:n, w 0:n:n");

			createMenuGroup(navigatorPath.createMenu(context, this), content);
			scrolledForm.reflow(true);
		}
	}

	/**
	 * Creates the menu group.
	 *
	 * @param parentController
	 *            the parent controller
	 * @param parent
	 *            the parent
	 */
	private void createMenuGroup(MenuController parentController, final Composite parent) {
		for (final ANodeController nodeController : parentController.getChildren()) {
			if (!nodeController.isValid())
				continue;
			Control control = (Control) nodeController.getTransientData().get(this);
			if (nodeController instanceof MenuController) {
				final MenuController menuController = (MenuController) nodeController;
				if (null == control || control.isDisposed()) {
					final PGroup pgroup = toolkit.createPGroup(parent, SWT.SMOOTH);
					nodeController.getTransientData().put(this, pgroup);
					pgroup.addDisposeListener((e) -> {
						menuController.dispose();
					});
					pgroup.addExpandListener(new ExpandListener() {

						@Override
						public void itemExpanded(ExpandEvent e) {
							menuController.expand();
						}

						@Override
						public void itemCollapsed(ExpandEvent e) {
						}
					});
					pgroup.setText(menuController.getLabel());
					pgroup.setImage(menuController.getImage());
					pgroup.setLayoutData("w 0:n:n, h 0:n:n");
					pgroup.setLayout(new MigLayout("wrap 1, ins 5 0 0 0, hidemode 2", "grow,fill", "grow,fill"));
					pgroup.setData("#MENU_CONTROLLER#", menuController);
					pgroup.addExpandListener(new ExpandListener() {
						@Override
						public void itemExpanded(ExpandEvent e) {
							Composite composite = (Composite) pgroup.getData("#COMPOSITE#");
							if (null == composite) {
								composite = toolkit.createComposite(pgroup, SWT.NONE);
								composite.setLayoutData("h 0:n:n, w 0:n:n");
								composite.setLayout(new MigLayout("wrap 1, hidemode 2, ins 0 10 0 0", "grow,fill"));
								createMenuGroup((MenuController) pgroup.getData("#MENU_CONTROLLER#"), composite);
								pgroup.setData("#COMPOSITE#", composite);
							}
							menuController.setExpanded(true);
							internalReflow(pgroup);
						}

						@Override
						public void itemCollapsed(ExpandEvent e) {
							internalReflow(pgroup);
							menuController.setExpanded(false);
						}
					});
					if (menuController.isExpanded()) {
						pgroup.notifyListeners(SWT.Expand, null);
					} else
						pgroup.setExpanded(false);
				}
			} else if (nodeController instanceof ItemController) {
				if (null == control || control.isDisposed()) {
					Composite composite = toolkit.createComposite(parent);
					composite.setLayout(new MigLayout("wrap 1, ins 0", "[grow,fill]"));

					final ImageHyperlink hyperlink = new ImageHyperlink(composite, SWT.NONE);
					hyperlink.setImage(nodeController.getImage());
					hyperlink.setText(nodeController.getLabel());
					hyperlink.setBackground(
							null == nodeController.getBackground() ? defaulBackground : nodeController.getBackground());
					hyperlink.setForeground(
							null == nodeController.getForeground() ? defaulForeground : nodeController.getForeground());
					if (null != AReskinManager.getInstance())
						AReskinManager.getInstance().addReskinListener(() -> {
							hyperlink.setForeground(
									null == nodeController.getForeground() ? defaulForeground : nodeController.getForeground());
							hyperlink.setBackground(
									null == nodeController.getBackground() ? defaulBackground : nodeController.getBackground());
						}, hyperlink);
					hyperlink
							.setFont(null == nodeController.getFont() ? hyperlink.getParent().getFont() : nodeController.getFont());
					if (null != nodeController.getToolTipText())
						hyperlink.setToolTipText(nodeController.getToolTipText());
					hyperlink.addHyperlinkListener(new HyperlinkAdapter() {

						@Override
						public void linkActivated(HyperlinkEvent e) {
							String tooltipText = hyperlink.getToolTipText();
							if (!EngineTools.isEmpty(tooltipText))
								hyperlink.setToolTipText(""); // close ToolTipText window (useful in debug mode)
							nodeController.getTransientData().put(IEclipseContext.class.getName(), context);
							((ItemController) nodeController).handleActivate();
							if (!EngineTools.isEmpty(tooltipText) && !hyperlink.isDisposed()) // previous action could change navigator state
								hyperlink.setToolTipText(tooltipText);
						}
					});
					hyperlink.addDisposeListener((e) -> {
						nodeController.dispose();
					});
					nodeController.getTransientData().put(this, hyperlink);
					if (!nodeController.isEnabled())
						hyperlink.setEnabled(false);
				}

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.part.ANavigator#refreshMenuController(org.adichatz.engine.controller.menu.MenuController)
	 */
	@Override
	public void refreshMenuController(ANodeController nodeController) {
		Object control = nodeController.getTransientData().get(this);
		if (control instanceof PGroup) {
			PGroup pgroup = (PGroup) control;
			if (!pgroup.isDisposed()) {
				if (pgroup.getExpanded()) {
					createMenuGroup((MenuController) nodeController, (Composite) pgroup.getData("#COMPOSITE#"));
				}
				Font font = nodeController.getFont();
				if (null != font)
					pgroup.setFont(font);
				Color foreground = nodeController.getForeground();
				if (null != foreground)
					pgroup.setForeground(foreground);
				pgroup.layout();
				internalReflow(pgroup);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.part.ANavigator#removeMenuController(org.adichatz.engine.controller.menu.MenuController,
	 * org.adichatz.engine.controller.menu.ANodeController)
	 */
	@Override
	public void removeMenuController(MenuController menuController, ANodeController nodeController) {
		if (nodeController instanceof MenuController) {
			PGroup pgroup = (PGroup) nodeController.getTransientData().get(this);
			if (null != pgroup && !pgroup.isDisposed()) {
				Composite parent = pgroup.getParent();
				pgroup.dispose();
				EngineTools.reinitMiglayout(parent);
			}
		} else if (nodeController instanceof ItemController) {
			Hyperlink hyperlink = (Hyperlink) nodeController.getTransientData().get(this);
			if (!hyperlink.isDisposed()) {
				Composite grandParent = hyperlink.getParent().getParent();
				hyperlink.getParent().dispose();
				EngineTools.reinitMiglayout(grandParent);
			}
		}
		menuController.getChildren().remove(nodeController);
	}

	/**
	 * Internal reflow.
	 *
	 * @param pgroup
	 *            the pgroup
	 */
	private void internalReflow(PGroup pgroup) {
		Composite c = pgroup;
		while (c != null && !c.isDisposed()) {
			c.setRedraw(false);
			c = c.getParent();
		}
		c = pgroup;
		while (c != null && !c.isDisposed()) {
			c.layout(true);
			c = c.getParent();
			if (c instanceof SharedScrolledComposite) {
				((SharedScrolledComposite) c).reflow(true);
			}
		}
		c = pgroup;
		while (c != null && !c.isDisposed()) {
			c.setRedraw(true);
			c = c.getParent();
		}
		scrolledForm.reflow(true);
	}

	@Override
	public void expand(ANodeController nodeController) {
		PGroup pgroup = (PGroup) nodeController.getTransientData().get(this);
		pgroup.setExpanded(true);
		pgroup.notifyListeners(SWT.Expand, null);
	}
}
