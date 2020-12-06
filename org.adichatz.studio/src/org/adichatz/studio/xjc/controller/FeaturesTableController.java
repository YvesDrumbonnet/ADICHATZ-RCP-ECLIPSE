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

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.query.NativeQueryManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.engine.viewer.NativeContentProvider;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.PathElementWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ActionWhenTypeEnum;
import org.adichatz.generator.xjc.CopyResourceType;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.RemoveResourceType;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.editor.StudioOutlinePage;
import org.adichatz.studio.xjc.editor.action.ShowOutlineViewAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class FeaturesTableController.
 * 
 * @param <T>
 *            the generic type
 */
public class FeaturesTableController<T> extends TableController<T> {

	/**
	 * Instantiates a new features table controller.
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
	public FeaturesTableController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		super.createControl();
		table.setLayoutData("height 200:200:n, w 300:300:n");
		final MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(table);
		menuManager.setRemoveAllWhenShown(true);
		table.setMenu(menu);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				menuManager.add(new Action(getFromStudioBundle("scenario.features.new"),
						AdichatzApplication.getInstance().getImageDescriptor(EngineConstants.ENGINE_BUNDLE, "IMG_CREATE.png")) {
					private Object bean = null;

					private Button copyButton;

					@Override
					@SuppressWarnings("unchecked")
					public void run() {
						try {
							if (beanClass.getName().equals(RemoveResourceType.class.getName())) {
								IConfirmContent confirmContent = new IConfirmContent() {
									@Override
									public void createConfirmContent(Composite parent, AdiFormToolkit toolkit,
											List<Control> complementControls) {
										parent.setLayout(new MigLayout("wrap 1", "grow, fill"));
										Group group = toolkit.createGroup(parent, SWT.SHADOW_IN);
										group.setLayout(new MigLayout("wrap 2", "[grow, fill]10[grow, fill]"));
										copyButton = toolkit.createButton(group, getFromStudioBundle("action.resource.copy"),
												SWT.RADIO);
										copyButton.setSelection(true);
										toolkit.createButton(group, getFromStudioBundle("action.resource.remove"), SWT.RADIO);
									}

									@Override
									public void okPressed(List<Control> complementControls) {
										ScenarioTreeWrapper scenarioTree = (ScenarioTreeWrapper) parentController.getEntity()
												.getBean();
										String resourceNameExample = getFromStudioBundle("action.resource.name.example");
										if (copyButton.getSelection()) {
											bean = new CopyResourceType();
											((CopyResourceType) bean).setSourceURI(resourceNameExample);
											scenarioTree.getActionResources().getCopyResource().add((CopyResourceType) bean);
										} else {
											bean = new RemoveResourceType();
											scenarioTree.getActionResources().getRemoveResource().add((RemoveResourceType) bean);
										}
										((RemoveResourceType) bean).setActionWhen(ActionWhenTypeEnum.WHEN_BUILDING_MODEL);
										((RemoveResourceType) bean).setTargetURI(resourceNameExample);
									}
								};
								new ConfirmFormDialog(getControl().getShell(), getFromStudioBundle("choose.resource.action.type"),
										AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
												"IMG_ACTION_RESOURCES.png"),
										confirmContent).open();
							} else {
								if (beanClass.getName().equals(PathElementType.class.getName()))
									bean = new PathElementWrapper();
								else
									bean = beanClass.getDeclaredConstructor().newInstance();
								((NativeContentProvider<T>) viewer.getContentProvider()).getQueryManager().getQueryResult()
										.getQueryResultList().add(bean);
							}
							refresh();
							lock();
							if (null != bean)
								viewer.setSelection(new StructuredSelection(bean));
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException e) {
							logError(e);
						}
					}
				});
				menuManager.add(new Action(getFromStudioBundle("scenario.features.delete"),
						toolkit.getRegisteredImageDescriptor("IMG_DELETE")) {

					@Override
					@SuppressWarnings("unchecked")
					public void run() {
						Object bean = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
						if (bean instanceof RemoveResourceType) {
							ScenarioTreeWrapper scenarioTree = (ScenarioTreeWrapper) parentController.getEntity().getBean();
							if (bean instanceof CopyResourceType)
								scenarioTree.getActionResources().getCopyResource().remove(bean);
							else
								scenarioTree.getActionResources().getRemoveResource().remove(bean);
						} else {
							((NativeContentProvider<T>) viewer.getContentProvider()).getQueryManager().getQueryResult()
									.getQueryResultList().remove(bean);
						}
						for (ErrorMessage errorMessage : getBindingService().getErrorMessageMap().getErrorMessages())
							if (errorMessage.getEntity().getBean().equals(bean)) {
								getBindingService().removeMessage(errorMessage);
							}
						refresh();
						lock();
						viewer.setSelection(StructuredSelection.EMPTY);
					}
				});
				if (ShowOutlineViewAction.isHidden()) {
					menuManager.add(new Separator());
					menuManager.add(new ShowOutlineViewAction() {
						@Override
						public void run() {
							runAction();
						}
					});
				}

			}

			private void lock() {
				IEntity<?> entity = parentController.getEntity();
				entity.getEntityMM().getDataAccess().lock(getBindingService(), IEntityConstants.MERGE, entity);
			}

		});
		getViewer().addSelectionChangedListener((event) -> {
			selectOutlinePage();
		});
	}

	public void selectOutlinePage() {
		XjcBindingService bindingService = (XjcBindingService) getBindingService();
		if (null != bindingService.getEditor().getOutlinePage()) {
			StudioOutlinePage studioOutlinePage = bindingService.getEditor().getOutlinePage();
			if (!viewer.getSelection().isEmpty())
				studioOutlinePage.setSelection(FeaturesTableController.this, viewer.getSelection());
			else
				studioOutlinePage.setSelection(FeaturesTableController.this, (XjcEntity<?>) null, true);
			studioOutlinePage.enable(bindingService.getEditor().isEditable());
		}
	}

	@Override
	public void endLifeCycle() {
		super.endLifeCycle();
		((NativeQueryManager<?>) getQueryManager()).initQueryManager(getEntity());
		refresh();
	}
}
