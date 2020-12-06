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
package org.adichatz.studio.xjc.controller;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.InjectionInspector;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ATreeController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.widgets.LimitedComposite;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.studio.gencode.editor.XjcTreeManager;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.adichatz.studio.xjc.editor.AStudioTreeManager;
import org.adichatz.studio.xjc.editor.StudioOutlinePage;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcTreeController.
 */
public class ScenarioTreeController extends ATreeController {

	protected boolean editableOutlinePage;

	/**
	 * Instantiates a new xjc tree controller.
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
	public ScenarioTreeController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		InjectionInspector.inject(this);
		composite = new LimitedComposite(parentController.getComposite(), SWT.NONE);
		composite.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "[grow,fill]"));
		composite.setLayoutData("height 200:200:n, w 300:300:n");
		viewer = createViewer();
		tree = ((TreeViewer) viewer).getTree();
		tree.setHeaderVisible(true);
		tree.setLayoutData("height 200:200:n, w 300:300:n");

		treeManager = new XjcTreeManager(getPluginResources());
		((XjcTreeManager) treeManager).setTreeController(this);
		if (null == AStudioTreeManager.BOLD) {
			AStudioTreeManager.BOLD = EngineTools.getModifiedFont(tree.getFont(), SWT.BOLD);
			AStudioTreeManager.ITALIC = EngineTools.getModifiedFont(tree.getFont(), SWT.ITALIC);
		}
		getTreeManager().setBindingService(getBindingService());
		viewer.setLabelProvider(getTreeManager().getTreeLabelProvider());
		viewer.setContentProvider(getTreeManager().getTreeContentProvider());
		getViewer().addSelectionChangedListener((event) -> {
			selectOutlinePage();
		});
		editableOutlinePage = ((XjcBindingService) getBindingService()).getEditor().isEditable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.collection.TreeController#getTreeManager()
	 */
	@Override
	public AStudioTreeManager getTreeManager() {
		return (AStudioTreeManager) treeManager;
	}

	@Override
	public void synchronize() {
		super.synchronize();
		refresh();
	}

	@Override
	public void refresh() {
		setRootELement();
		super.refresh();
	}

	protected void setRootELement() {
		XjcEntity<?> entity = (XjcEntity<?>) parentController.getEntity();
		if (null != entity) {
			entity.setTreeElement(new XjcTreeElement(((XjcTreeManager) treeManager), null, entity.getBean(), null, false));
			setRootElement(entity.getTreeElement());
		} else
			setRootElement(new XjcTreeElement(((XjcTreeManager) treeManager), null, new ScenarioTreeWrapper(), null, false));
	};

	protected ColumnViewer createViewer() {
		setShowRoot(false);
		return new TreeViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
	}

	public void selectOutlinePage() {
		StudioOutlinePage outlinePage = ((XjcBindingService) getBindingService()).getEditor().getOutlinePage();
		if (null != outlinePage) {
			XjcTreeElement treeElement = (XjcTreeElement) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			outlinePage.setSelection(ScenarioTreeController.this, viewer.getSelection().isEmpty() ? null : treeElement.getEntity(),
					!editableOutlinePage);
			if (!viewer.getSelection().isEmpty())
				outlinePage.disableColumnControllers(false);
		}

	}
}
