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
package org.adichatz.engine.e4.handler;

import java.util.List;

import javax.inject.Inject;

import org.adichatz.engine.common.EngineTools;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuSeparator;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.workbench.renderers.swt.DirectContributionItem;
import org.eclipse.e4.ui.workbench.renderers.swt.HandledContributionItem;
import org.eclipse.e4.ui.workbench.renderers.swt.MenuManagerRenderer;
import org.eclipse.e4.ui.workbench.swt.factories.IRendererFactory;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class CloneMenuHandler.
 * 
 * Build a menu from items defined in MMenu set to MToolItem.
 */
@SuppressWarnings("restriction")
public class CloneMenuHandler {

	private Menu menu;

	@Inject
	private IEclipseContext context;

	private MenuManagerRenderer renderer;

	/**
	 * Execute.
	 * 
	 * @param toolItem
	 *            the item
	 */
	@SuppressWarnings("unchecked")
	@Execute
	public void execute(MToolItem toolItem) {
		if (null == toolItem.getMenu())
			return;

		IRendererFactory rendererFactory = context.get(IRendererFactory.class);
		renderer = (MenuManagerRenderer) rendererFactory.getRenderer(toolItem.getMenu(), null);
		IContributionItem ci = renderer.getContribution(toolItem.getMenu());
		if (null == ci) { // ci null means that menu has not already bean built. So clone cannot done.
			// Forces menu building.
			Object obj = toolItem.getMenu();
			renderer.createWidget(toolItem.getMenu(), toolItem.getParent().getWidget());
			renderer.processContents((MElementContainer<MUIElement>) obj);
		}

		menu = new Menu(((ToolItem) toolItem.getWidget()).getParent().getShell(), SWT.POP_UP);
		addItems(toolItem.getMenu().getChildren(), menu);
		menu.setVisible(true);
	}

	protected void addItems(List<MMenuElement> children, final Menu menu) {
		int index = 0;
		for (final MMenuElement menuElement : children) {
			if (menuElement instanceof MMenuSeparator)
				new MenuItem(menu, SWT.SEPARATOR);
			else {
				MenuItem menuItem = new MenuItem(menu, menuElement instanceof MHandledMenuItem ? SWT.PUSH : SWT.CASCADE);
				menuItem.setText(menuElement.getLocalizedLabel());
				menuItem.setImage(EngineTools.getImage(menuElement.getIconURI()));
				if (menuElement instanceof MMenuItem) {
					menuItem.setEnabled(((MMenuItem) menuElement).isEnabled());
					menuItem.addSelectionListener(new ItemSelectionAdapter(menuElement, index++));
				} else if (menuElement instanceof MMenu) {
					Menu subMenu = new Menu(menu);
					menuItem.setMenu(subMenu);
					renderer.createWidget(menuElement, menu);
					addItems(((MMenu) menuElement).getChildren(), subMenu);
				}
			}
		}
	}

	private class ItemSelectionAdapter extends SelectionAdapter {
		private int index;

		private MMenuElement menuElement;

		ItemSelectionAdapter(MMenuElement menuElement, int index) {
			this.menuElement = menuElement;
			this.index = index;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			Widget widget = null;
			IContributionItem ci = renderer.getContribution(menuElement);
			if (ci instanceof HandledContributionItem) {
				widget = ((HandledContributionItem) ci).getWidget();
				if (null == widget) {
					ci.fill(menu, index);
					widget = ((HandledContributionItem) ci).getWidget();
				}
			} else if (ci instanceof DirectContributionItem) {
				widget = ((DirectContributionItem) ci).getWidget();
				if (null == widget) {
					ci.fill(menu, index);
					widget = ((DirectContributionItem) ci).getWidget();
				}
			}
			if (null != widget)
				widget.notifyListeners(SWT.Selection, null);
		}
	}
}
