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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adichatz.engine.controller.menu.ANodeController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.controller.menu.NavigatorPath;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;

import net.miginfocom.swt.MigLayout;

public class TreeNavigator extends ANavigator {
	private TreeViewer treeViewer;

	private @Inject IEclipseContext context;

	private List<Object> forceExpandELements = new ArrayList<Object>();

	@PostConstruct
	public void createControl(Composite parent, MApplication application) {
		parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
		treeViewer = new TreeViewer(parent, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL) {
			@Override
			protected void associate(final Object element, Item item) {
				super.associate(element, item);
				if (element instanceof MenuController && ((MenuController) element).isExpanded())
					forceExpandELements.add(element);
				((ANodeController) element).getTransientData().put(this, item);
				item.addDisposeListener((e) -> {
					((ANodeController) element).dispose();
				});
			}
		};
		treeViewer.getTree().setLayoutData("h 0:64:n, w 0:64:n");
		treeViewer.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element instanceof ANodeController)
					return ((ANodeController) element).getLabel();
				return "?! error  on menu text!?";
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof ANodeController)
					return ((ANodeController) element).getImage();
				return null;
			}

			@Override
			public Color getForeground(Object element) {
				if (element instanceof ANodeController)
					return ((ANodeController) element).getForeground();
				return null;
			}

			@Override
			public Font getFont(Object element) {
				if (element instanceof ANodeController)
					return ((ANodeController) element).getFont();
				return null;
			}

		});
		treeViewer.setContentProvider(new ITreeContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean hasChildren(Object element) {
				return element instanceof MenuController;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				List<Object> children = new ArrayList<Object>();
				for (Object child : ((MenuController) treeViewer.getInput()).getChildren())
					if (child instanceof ANodeController && ((ANodeController) child).isValid())
						children.add(child);
				return children.toArray();
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof MenuController) {
					List<Object> children = new ArrayList<Object>();
					for (Object child : ((MenuController) parentElement).getChildren())
						if (child instanceof ANodeController && ((ANodeController) child).isValid())
							children.add(child);
					return children.toArray();
				}
				return null;
			}
		});
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				if (!event.getSelection().isEmpty()) {
					ANodeController nodeController = (ANodeController) ((StructuredSelection) event.getSelection())
							.getFirstElement();
					nodeController.getTransientData().put(IEclipseContext.class.getName(), context);
					nodeController.handleActivate();
				}
			}
		});

		treeViewer.getTree().addListener(SWT.Expand, new Listener() {
			public void handleEvent(Event e) {
				MenuController menuController = (MenuController) e.item.getData();
				menuController.setExpanded(true);
				menuController.expand();
			}
		});
		treeViewer.getTree().addListener(SWT.Collapse, new Listener() {
			public void handleEvent(Event e) {
				((MenuController) e.item.getData()).setExpanded(false);
			}
		});
		refreshNavigator();
	}

	@Override
	protected void refreshInput(NavigatorPath navigatorPath) {
		treeViewer.setInput(navigatorPath.createMenu(context, this));
	}

	@Override
	public void refreshMenuController(ANodeController nodeController) {
		forceExpandELements.clear();
		treeViewer.refresh(nodeController);
		for (Object element : forceExpandELements.toArray())
			treeViewer.expandToLevel(element, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.part.ANavigator#removeMenuController(org.adichatz.engine.controller.menu.MenuController,
	 * org.adichatz.engine.controller.menu.ANodeController)
	 */
	@Override
	public void removeMenuController(MenuController menuController, ANodeController nodeController) {
		menuController.getChildren().remove(nodeController);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.part.ANavigator#expand(org.adichatz.engine.controller.menu.ANodeController)
	 */
	@Override
	public void expand(ANodeController nodeController) {
		treeViewer.expandToLevel(nodeController, 1);
	}
}
