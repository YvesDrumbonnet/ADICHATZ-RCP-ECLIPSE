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
package org.adichatz.tool;

import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.e4.part.ANavigator;
import org.adichatz.engine.e4.part.AdiInputPart;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.E4SimulationTools;
import org.adichatz.engine.e4.resource.EngineE4Util;
import org.adichatz.engine.xjc.MenuPathType;
import org.adichatz.engine.xjc.NavigatorType;
import org.adichatz.engine.xjc.NavigatorsType;
import org.adichatz.generator.common.GeneratorConstants;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuHandler.
 */
@SuppressWarnings("restriction")
public class MenuHandler {
	/**
	 * Execute.
	 * 
	 * @param application
	 *            the application
	 * @param modelService
	 *            the model service
	 * @param partService
	 *            the part service
	 * @param item
	 *            the item
	 */
	@Execute
	public void execute(final MApplication application, final EModelService modelService, final EPartService partService,
			MItem item) {

		ToolBar toolbar = ((ToolItem) item.getWidget()).getParent();
		final Menu menu = new Menu(toolbar.getShell(), SWT.POP_UP);
		menu.setVisible(true);
		final MenuResources menuResources = new MenuResources();
		menuResources.computeResourcesBundles();

		boolean hideExternalResources = InstanceScope.INSTANCE.getNode(GeneratorConstants.TOOL_BUNDLE)
				.getBoolean(ToolUtil.HIDE_EXTERNAL_RESOURCES, true);

		NavigatorsType navigators = AdichatzApplication.getInstance().getContextValue(NavigatorsType.class);
		if (null != navigators) {
			// Menu navigator
			Menu navigatorMenu = new Menu(menu);
			MenuItem navigatorMenuPlugin = new MenuItem(menu, SWT.CASCADE);
			navigatorMenuPlugin.setText(getFromToolBundle("tool.navigator"));
			navigatorMenuPlugin
					.setImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_MENU.png"));
			navigatorMenuPlugin.setMenu(navigatorMenu);

			// Item refresh navigator
			MenuItem refreshMenuItem = new MenuItem(navigatorMenu, SWT.PUSH);
			refreshMenuItem.setText(getFromToolBundle("tool.navigator.refresh")); //$NON-NLS-1$
			refreshMenuItem
					.setImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_MENU_REFRESH.png"));
			refreshMenuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ANavigator navigator = (ANavigator) ((MPart) E4SimulationTools.getSelectedNavigator()).getObject();
					ToolNavigatorContent toolNavigatorContent = ToolNavigatorContent.getInstance();
					if (null != toolNavigatorContent) {
						BoundedPart actualPart = toolNavigatorContent.getCurrentPart();
						toolNavigatorContent.removeOpenMenuController(navigator);
						toolNavigatorContent.removeNavigatorController(navigator);
						navigator.refreshNavigator();
						toolNavigatorContent.createNavigatorController(navigator);
						if (null != actualPart) {
							toolNavigatorContent.updateOpenMenuController(actualPart.getInputPart());
						}
						navigator.refreshMenuController(toolNavigatorContent.getToolMenuController());
					}
				}
			});
			ANavigator navigator = (ANavigator) ((MPart) E4SimulationTools.getSelectedNavigator()).getObject();
			String navigatorId = ((MPart) navigator.getContext().get(MPartSashContainerElement.class)).getElementId();
			for (NavigatorType navigatorType : navigators.getNavigator()) {
				if (navigatorId.equals(navigatorType.getId())) {
					for (MenuPathType menuPath : navigatorType.getMenuPath()) {
						if (menuPath.getAdiResourceURI().startsWith("adi://")) {
							final String adiResourceURI = menuPath.getAdiResourceURI();
							boolean updateable = ToolUtil.isProject(EngineTools.getInstanceKeys(menuPath.getAdiResourceURI())[0]);
							if (updateable || !hideExternalResources) {
								MenuItem menuURIItem = new MenuItem(navigatorMenu, SWT.PUSH);
								menuURIItem.setText(menuPath.getAdiResourceURI()); // $NON-NLS-1$
								Image image = AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE,
										"IMG_MENU_EDIT.png");
								menuURIItem.setImage(updateable ? image : new Image(image.getDevice(), image, SWT.IMAGE_DISABLE));
								menuURIItem.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										ToolUtil.openNavigatorIDE(adiResourceURI);
									}
								});
							}
						}
					}
				}
			}
		}
		for (final ResourcesBundle resourcesBundle : menuResources.getResourcesBundles()) {
			boolean addSeparator = false;
			Menu pluginMenu;
			if (1 == menuResources.getResourcesBundles().size()) {
				pluginMenu = menu;
			} else {
				pluginMenu = new Menu(menu);
				MenuItem applMenuPlugin = new MenuItem(menu, SWT.CASCADE);
				applMenuPlugin.setText(getFromToolBundle("tool.navigator.plugin", resourcesBundle.pluginResources.getPluginName()));
				applMenuPlugin
						.setImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_PLUGIN.gif"));
				applMenuPlugin.setMenu(pluginMenu);
			}
			if (resourcesBundle.hasBundle) {
				// Only resource and images of adichatz builts plugins are accesibles.
				// org.adichatz.engine, org.adichatz.jap are not accessible (not updateable)
				if (addSeparator)
					new MenuItem(pluginMenu, SWT.SEPARATOR);
				addSeparator = true;
				MenuItem resourceBundlesRefresh = new MenuItem(pluginMenu, SWT.PUSH);
				resourceBundlesRefresh.setText(getFromToolBundle("tool.bundle.refresh")); //$NON-NLS-1$
				resourceBundlesRefresh.setImage(
						AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_BUNDLE_REFRESH.png"));
				resourceBundlesRefresh.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						resourcesBundle.pluginResources.getResourceBundleManager().getResourceBundleMap().clear();
						EngineTools.openDialog(MessageDialog.INFORMATION, getFromToolBundle("tool.bundle.refresh"),
								getFromToolBundle("tool.bundle.refreshed"));
					}
				});
			}

			if (resourcesBundle.hasImage) {
				if (addSeparator)
					new MenuItem(pluginMenu, SWT.SEPARATOR);
				addSeparator = true;
				MenuItem resourceBundlesRefresh = new MenuItem(pluginMenu, SWT.PUSH);
				resourceBundlesRefresh.setText(getFromToolBundle("tool.image.refresh")); //$NON-NLS-1$
				resourceBundlesRefresh.setImage(
						AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_IMAGE_REFRESH.png"));
				resourceBundlesRefresh.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						resourcesBundle.pluginResources.getImageManager().getImageDescriptorMap().clear();
						EngineTools.openDialog(MessageDialog.INFORMATION, getFromToolBundle("tool.image.refresh"),
								getFromToolBundle("tool.image.refreshed"));
					}
				});
			}

			if (resourcesBundle.hasCache) {
				if (addSeparator)
					new MenuItem(pluginMenu, SWT.SEPARATOR);
				addSeparator = true;
				MenuItem cacheManager = new MenuItem(pluginMenu, SWT.PUSH);
				cacheManager.setText(getFromToolBundle("tool.cacheManager")); //$NON-NLS-1$
				cacheManager.setImage(
						AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_LOCK_MANAGER.png"));
				cacheManager.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						ToolUtil.openCacheManagerEditor(resourcesBundle.pluginResources, application, modelService, partService);
					}
				});
			}
			new MenuItem(menu, SWT.SEPARATOR);
			MenuItem applicationParamManager = new MenuItem(pluginMenu, SWT.PUSH);
			applicationParamManager.setText(getFromToolBundle("tool.applicationParamManager")); //$NON-NLS-1$
			applicationParamManager
					.setImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_LOCK_MANAGER.png"));
			applicationParamManager.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ToolUtil.openApplicationParamManagerEditor(resourcesBundle.pluginResources, application, modelService,
							partService);
				}
			});
		}

		MPartStack editorPartStack = (MPartStack) AdichatzApplication.getInstance().getContextValue(EngineE4Util.EDITOR_PARTSTACK);
		final MStackElement stackElement = editorPartStack.getSelectedElement();
		if (stackElement instanceof AdiInputPart) {
			BoundedPart currentPart = ((AdiInputPart) stackElement).getObject();
			Menu openPartMenu = new Menu(menu);
			MenuItem openPartMenuItem = new MenuItem(menu, SWT.CASCADE);
			openPartMenuItem.setText(getFromToolBundle("resourceBroadcast.title", currentPart.getTitle()));
			openPartMenuItem
					.setImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_BROADCAST.gif"));
			openPartMenuItem.setMenu(openPartMenu);

			if (null != currentPart.getGenCode())
				for (final String adiResourceURI : currentPart.getGenCode().getContext().getOpenParts()) {
					String bundleName = EngineTools.getInstanceKeys(adiResourceURI)[0];
					if (null == bundleName)
						bundleName = currentPart.getInputPart().getPluginResources().getPluginName();
					boolean updateable = ToolUtil.isProject(bundleName);
					if (updateable || !hideExternalResources) {
						MenuItem openPartItem = new MenuItem(openPartMenu, SWT.PUSH);
						openPartItem.setText(adiResourceURI);
						Image image = AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE,
								"IMG_MENU_EDIT.png");
						openPartItem.setImage(updateable ? image : new Image(image.getDevice(), image, SWT.IMAGE_DISABLE));
						openPartItem.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								ToolUtil.openNavigatorIDE(adiResourceURI);
							}
						});
					}
				}
		}
	}
}