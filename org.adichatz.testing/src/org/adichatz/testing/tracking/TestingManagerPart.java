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
package org.adichatz.testing.tracking;

import static org.adichatz.testing.TestingTools.getFromTestingBundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.extra.IOutlineContainerPart;
import org.adichatz.engine.extra.IOutlinePage;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.viewer.ATreeContentProvider;
import org.adichatz.testing.TestingTools;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

/**
 * The Class TestingManagerPart.
 *
 * @author Yves Drumbonnet
 */
public class TestingManagerPart implements IOutlineContainerPart {
	private static long LAST_REFRESH = -1l;

	public static long LAST_TEST = -1l;

	@Inject
	private Composite parent;

	private TreeViewer testTV;

	private TestOutlinePage testOutlinePage;

	private ATreeContentProvider treeContentProvider;

	private Action deleteAction;

	private Composite container;

	@PostConstruct
	public void createControl() {
		final AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		TestingTools.init();
		ScrolledForm scrolledForm = toolkit.createScrolledForm(parent);
		new ManagedForm(toolkit, scrolledForm);
		IToolBarManager toolBarManager = scrolledForm.getToolBarManager();

		toolBarManager.add(new Action(getFromTestingBundle("testing.delete.all.runs"),
				TestingTools.imageManager.getImageDescriptor("IMG_DELETE_ALL.png")) {
			@Override
			public void run() {
				AdiResultListener.RESULT_LISTENERs.clear();
				refresh();
			}
		});

		deleteAction = new Action(getFromTestingBundle("testing.delete.selected.runs"),
				TestingTools.imageManager.getImageDescriptor("IMG_DELETE.gif")) {
			@Override
			public void run() {
				for (Object element : ((StructuredSelection) testTV.getSelection()).toList())
					AdiResultListener.RESULT_LISTENERs.remove((AdiResultListener) element);
				refresh();
			}
		};
		toolBarManager.add(deleteAction);
		deleteAction.setEnabled(false);

		toolBarManager.add(new Separator());
		toolBarManager.add(new Action(getFromTestingBundle("testing.action.collapseTree"),
				TestingTools.imageManager.getImageDescriptor("IMG_COLLAPSE.gif")) {
			@Override
			public void run() {
				testTV.collapseAll();
			}
		});

		toolBarManager.add(
				new Action(getFromTestingBundle("testing.action.expandTree"), toolkit.getRegisteredImageDescriptor("IMG_DEVELOP")) {
					@Override
					public void run() {
						testTV.expandAll();
					}
				});
		toolBarManager.add(new Separator());
		toolBarManager.add(
				new Action(getFromTestingBundle("testing.action.refresh"), toolkit.getRegisteredImageDescriptor("IMG_REFRESH")) {
					@Override
					public void run() {
						refresh();
					}
				});
		scrolledForm.getToolBarManager().update(true);
		container = scrolledForm.getBody();
		container.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

		testTV = new TreeViewer(container) {
			protected void handleTreeExpand(TreeEvent event) {
				// When click for expand an element, expand the entire branch
				createChildren(event.item);
				if (event.item.getData() != null) {
					fireTreeExpanded(new TreeExpansionEvent(this, event.item.getData()));
					testTV.expandToLevel(event.item.getData(), ALL_LEVELS);
				}
			}
		};
		testTV.getTree().setLayoutData("w 64:64:n, h 64:64:n");
		treeContentProvider = new ATreeContentProvider() {
			@Override
			public Object[] getChildren(Object parentElement) {
				if (TestingManagerPart.this.equals(parentElement)) {
					List<AdiResultListener> list = new ArrayList<AdiResultListener>(AdiResultListener.RESULT_LISTENERs);
					Collections.reverse(list);
					return list.toArray();
				}
				if (parentElement instanceof AdiResultListener)
					return ((AdiResultListener) parentElement).getTestEvents().toArray();
				if (parentElement instanceof TestEvent && null != ((TestEvent) parentElement).getChildren())
					return ((TestEvent) parentElement).getChildren().toArray();
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				if (TestingManagerPart.this.equals(element))
					return !AdiResultListener.RESULT_LISTENERs.isEmpty();
				if (element instanceof AdiResultListener)
					return !((AdiResultListener) element).getTestEvents().isEmpty();
				return null != ((TestEvent) element).getChildren() && !((TestEvent) element).getChildren().isEmpty();
			}
		};
		testTV.setContentProvider(treeContentProvider);
		testTV.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof AdiResultListener)
					return ((AdiResultListener) element).getLabel();
				return ((TestEvent) element).getText();
			}

			@Override
			public Color getForeground(Object element) {
				if (element instanceof AdiResultListener) {
					for (TestEvent event : ((AdiResultListener) element).getTestEvents())
						if (hasFailure(event))
							return AController.ERROR_COLOR;
					return null;
				} else if (element instanceof TestEvent)
					return ((TestEvent) element).getForeGround();
				return null;
			}

			private boolean hasFailure(TestEvent parentEvent) {
				if (null == parentEvent.getChildren())
					switch (parentEvent.getEventType()) {
					case onConfigurationFailure:
					case onTestFailure:
						return true;
					default:
						return false;
					}
				for (TestEvent event : parentEvent.getChildren()) {
					if (hasFailure(event))
						return true;
				}
				return false;
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof AdiResultListener)
					return ((AdiResultListener) element).getImage();
				else
					return ((TestEvent) element).getImage();
			}
		});
		refresh();
		testOutlinePage = new TestOutlinePage(this, testTV);
		testTV.addSelectionChangedListener((event) -> {
			if (testTV.getSelection().isEmpty())
				partActivation();
			else {
				testOutlinePage.refresh();
				boolean enabled = false;
				enabled = true;
				for (Object element : ((StructuredSelection) testTV.getSelection()).toList())
					if (!(element instanceof AdiResultListener)) {
						enabled = false;
						break;
					}
				deleteAction.setEnabled(enabled);
			}
		});
	}

	public void refresh() {
		testTV.setSelection(StructuredSelection.EMPTY);
		testTV.setInput(this);
		testTV.refresh();
		if (!AdiResultListener.RESULT_LISTENERs.isEmpty())
			testTV.expandToLevel(treeContentProvider.getChildren(this)[0], TreeViewer.ALL_LEVELS);
		deleteAction.setEnabled(false);
		LAST_REFRESH = LAST_TEST;
	}

	@Override
	public void partActivation() {
		if (LAST_REFRESH != LAST_TEST) {
			LAST_REFRESH = LAST_TEST;
			refresh();
		}
		OutlinePart outlinePart = OutlinePart.getInstance();
		IOutlinePage outlinePage = testTV.getSelection().isEmpty() ? outlinePart.getDefaultPage() : testOutlinePage;
		if (!outlinePage.equals(outlinePart.getCurrentPage()))
			outlinePart.showPage(outlinePage);
	}

	@Override
	public Composite getContainer() {
		return null;
	}
}
