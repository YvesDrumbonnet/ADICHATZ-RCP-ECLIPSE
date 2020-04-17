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
package org.adichatz.tool;

import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.DelayedThread;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.menu.ItemController;
import org.adichatz.engine.controller.menu.MenuController;
import org.adichatz.engine.controller.utils.INavigator;
import org.adichatz.engine.core.ARootMenuCore;
import org.adichatz.engine.e4.part.ANavigator;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.PartCore;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.e4.resource.E4SimulationTools;
import org.adichatz.engine.xjc.MenuPathType;
import org.adichatz.engine.xjc.NavigatorType;
import org.adichatz.engine.xjc.RcpConfigurationType;
import org.adichatz.generator.common.GeneratorConstants;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainerElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolNavigatorContent.
 */
public class ToolNavigatorContent extends ARootMenuCore {
	/*
	 * S T A T I C
	 */

	/** The THIS. */
	private static ToolNavigatorContent THIS;

	/**
	 * Gets the single instance of ToolNavigatorContent.
	 *
	 * @return single instance of ToolNavigatorContent
	 */
	public static ToolNavigatorContent getInstance() {
		return THIS;
	}

	/** The italic font. */
	public static Font ITALIC_FONT;

	/*
	 * end S T A T I C
	 */

	/** The current part. */
	private BoundedPart currentPart;

	/** The part service. */
	private EPartService partService;

	/** The editor part stack. */
	private MPartStack editorPartStack;

	/** The tool menu controller. */
	private MenuController toolMenuController;

	/** The open part menu controller. */
	private MenuController openPartMenuController;

	/** The navigator controller. */
	private MenuController navigatorController;

	/** The parent. */
	private Composite parent;

	/** The navigator. */
	private ANavigator navigator;

	/** The expanded open part menu. */
	private boolean expandedOpenPartMenu = false;

	/**
	 * Instantiates a new tool navigator content.
	 */
	public ToolNavigatorContent(MenuController rootMenuController) {
		super(GeneratorConstants.TOOL_BUNDLE, rootMenuController);
		THIS = this;
	}

	/**
	 * Creates children (menu or item) for menu GroupNavigatorContent.
	 */
	public void createChildren() {
		if (null != toolMenuController)
			return;
		IEclipseContext context = (IEclipseContext) eclipseContext;
		final EModelService modelService = context.get(EModelService.class);
		final MApplication application = context.get(MApplication.class);
		partService = context.get(EPartService.class);
		parent = context.get(Composite.class);
		String label = getFromToolBundle("tool.menu");
		coreController = new MenuController(pluginResources, this, "toolNavigatorContent", label, null);
		toolMenuController = new MenuController(pluginResources, this, "developmentToolNavigatorMenu", label, null) {
			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_COMMUNICATION.png");
			}
		};
		coreController.getChildren().add(toolMenuController);
		ItemController item;

		final MenuResources menuResources = new MenuResources();
		menuResources.computeResourcesBundles();

		for (final ResourcesBundle resourcesBundle : menuResources.getResourcesBundles()) {
			MenuController pluginMenuController;
			if (1 == menuResources.getResourcesBundles().size()) {
				pluginMenuController = toolMenuController;
			} else {
				pluginMenuController = new MenuController(pluginResources, this, "developmentPluginsNavigatorMenu",
						getFromToolBundle("tool.navigator.plugin", resourcesBundle.pluginResources.getPluginName()), null);
				toolMenuController.getChildren().add(pluginMenuController);
			}

			if (resourcesBundle.hasBundle) {
				// Refresh Bundles
				item = new ItemController(pluginResources, this, "#tool.bundle.refresh#", getFromToolBundle("tool.bundle.refresh"),
						null) {
					@Override
					public void handleActivate() {
						resourcesBundle.pluginResources.getResourceBundleManager().getResourceBundleMap().clear();
						EngineTools.openDialog(MessageDialog.INFORMATION, getFromToolBundle("tool.bundle.refresh"),
								getFromToolBundle("tool.bundle.refreshed"));
					}

					@Override
					public Image getImage() {
						return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_BUNDLE_REFRESH.png");
					}
				};
				pluginMenuController.getChildren().add(item);
			}
			if (resourcesBundle.hasImage) {
				// Refresh Images
				item = new ItemController(pluginResources, this, "#tool.image.refresh#", getFromToolBundle("tool.image.refresh"),
						null) {
					@Override
					public void handleActivate() {
						resourcesBundle.pluginResources.getImageManager().getImageDescriptorMap().clear();
						EngineTools.openDialog(MessageDialog.INFORMATION, getFromToolBundle("tool.image.refresh"),
								getFromToolBundle("tool.image.refreshed"));
					}

					@Override
					public Image getImage() {
						return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_IMAGE_REFRESH.png");
					}
				};
				pluginMenuController.getChildren().add(item);
			}
			if (resourcesBundle.hasCache) {
				item = new ItemController(pluginResources, this, "#tool.cache.manager#", getFromToolBundle("tool.cacheManager"),
						null) {
					@Override
					public void handleActivate() {
						ToolUtil.openCacheManagerEditor(resourcesBundle.pluginResources, application, modelService, partService);
					}

					@Override
					public Image getImage() {
						return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_LOCK_MANAGER.png");
					}
				};
				pluginMenuController.getChildren().add(item);
				ITALIC_FONT = EngineTools.getModifiedFont(parent.getFont(), SWT.ITALIC);
			}
			item = new ItemController(pluginResources, this, "#tool.application#param#managerr#",
					getFromToolBundle("tool.applicationParamManager"), null) {
				@Override
				public void handleActivate() {
					ToolUtil.openApplicationParamManagerEditor(resourcesBundle.pluginResources, application, modelService,
							partService);
				}

				@Override
				public Image getImage() {
					return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_PARAM.png");
				}
			};
			pluginMenuController.getChildren().add(item);
			ITALIC_FONT = EngineTools.getModifiedFont(parent.getFont(), SWT.ITALIC);
		}
	}

	/**
	 * Refresh open parts.
	 *
	 * @param event
	 *            the event
	 */
	public void refreshOpenParts(ApplicationEvent event) {
		expandedOpenPartMenu = null == openPartMenuController ? expandedOpenPartMenu : openPartMenuController.isExpanded();
		if (null == event.part && null != event.context) {
			if (null == navigator) // Navigator is null when Navigator stack was minimized
				navigator = (ANavigator) ((MPart) E4SimulationTools.getSelectedNavigator()).getObject();
			// Instantiate a new axml master class (PartCore, QueryContentProvider...)
			removeOpenMenuController(navigator);
			event.part = ((BoundedPart) event.context.getRootCore().getController()).getInputPart();
		}
		if (event.part instanceof MPart) {
			if (null != currentPart && null == currentPart.getInputPart().getObject()) { // Close all...
				removeOpenMenuController(navigator);
				currentPart = null;
			}
			MPart part = (MPart) event.part;
			if (part.getObject() instanceof ANavigator) {
				BoundedPart currentPart = this.currentPart;
				if (!part.getObject().equals(navigator)) { // Change navigator
					MenuController currentOpenPartMenuController = openPartMenuController;
					removeOpenMenuController(navigator); // previous navigator
					removeNavigatorController(navigator); // previous navigator
					navigator = (ANavigator) part.getObject();
					openPartMenuController = currentOpenPartMenuController;
					removeOpenMenuController(navigator); // current navigator
					removeNavigatorController(navigator); // current navigator
					createNavigatorController(navigator);
					if (null != currentPart) {
						part = currentPart.getInputPart();
						updateOpenMenuController(part);
					}
					navigator.refreshMenuController(toolMenuController);
				}
			} else {
				if (getEditorPartStack().equals(part.getParent())) {
					if (part.getObject() instanceof BoundedPart) {
						if (!part.getObject().equals(currentPart))
							updateOpenMenuController(part);
					} else if (null != openPartMenuController) {
						if (null != navigator) // When navigator part is collapsed
							navigator.removeMenuController(toolMenuController, openPartMenuController);
						openPartMenuController = null;
						currentPart = null;
					}
					if (null != navigator) // When navigator part is collapsed
						navigator.refreshMenuController(toolMenuController);
				}
			}
		} else {
			removeOpenMenuController(navigator);
			currentPart = null;
			if (null != navigator) // When navigator part is collapsed
				navigator.refreshMenuController(toolMenuController);
		}
	}

	/**
	 * Refresh open parts in new thread.
	 *
	 * @param event
	 *            the event
	 */
	public void refreshOpenPartsInNewThread(final ApplicationEvent event) {
		DelayedThread.newDelayedThread(new MultiKey(parent, "#refreshOpenParts#"), 50, () -> {
			ToolNavigatorContent.getInstance().refreshOpenParts(event);
		});
	}

	/**
	 * Creates the navigator controller.
	 *
	 * @param navigator
	 *            the navigator
	 */
	public void createNavigatorController(ANavigator navigator) {
		RcpConfigurationType rcpConfiguration = AdichatzApplication.getInstance().getConfigTree().getRcpConfiguration();
		if (null != rcpConfiguration && null != rcpConfiguration.getNavigators()) {
			String navigatorNodeId = "#navigatorController#";
			if (null == navigatorController) {
				navigatorController = new MenuController(pluginResources, this, navigatorNodeId,
						getFromToolBundle("tool.navigator"), getFromToolBundle("tool.navigator")) {
					@Override
					public Image getImage() {
						return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_MENU.png");
					}
				};
				toolMenuController.getChildren().add(navigatorController);
				ItemController item = new ItemController(pluginResources, this, "#tool.navigator.refresh#",
						getFromToolBundle("tool.navigator.refresh"), null) {
					@Override
					public void handleActivate() {
						ANavigator navigator = (ANavigator) ((MPart) E4SimulationTools.getSelectedNavigator()).getObject();
						BoundedPart actualPart = currentPart;
						removeOpenMenuController(navigator);
						removeNavigatorController(navigator);
						navigator.refreshNavigator();
						createNavigatorController(navigator);
						if (null != actualPart) {
							updateOpenMenuController(actualPart.getInputPart());
						}
						navigator.refreshMenuController(toolMenuController);
					}

					@Override
					public Image getImage() {
						return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_MENU_REFRESH.png");
					}
				};
				navigatorController.getChildren().add(item);
				String navigatorId = ((MPart) navigator.getContext().get(MPartSashContainerElement.class)).getElementId();
				boolean hideExternalResources = InstanceScope.INSTANCE.getNode(GeneratorConstants.TOOL_BUNDLE)
						.getBoolean(ToolUtil.HIDE_EXTERNAL_RESOURCES, true);
				for (NavigatorType navigatorType : rcpConfiguration.getNavigators().getNavigator()) {
					if (navigatorId.equals(navigatorType.getId())) {
						for (MenuPathType menuPath : navigatorType.getMenuPath()) {
							if (menuPath.getAdiResourceURI().startsWith("adi://")) {
								final String adiResourceURI = menuPath.getAdiResourceURI();
								final boolean updateable = ToolUtil
										.isProject(EngineTools.getInstanceKeys(menuPath.getAdiResourceURI())[0]);
								if (updateable || !hideExternalResources) {
									ItemController itemController = new ToolItemController(pluginResources, this, adiResourceURI,
											adiResourceURI, adiResourceURI, updateable) {
										@Override
										public void handleActivate() {
											ToolUtil.openNavigatorIDE(adiResourceURI);
										}

										@Override
										public Font getFont() {
											return updateable ? null : ITALIC_FONT;
										}

										@Override
										public Image getImage() {
											Image image = AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE,
													"IMG_MENU_EDIT.png");
											if (updateable)
												return image;
											else
												return new Image(image.getDevice(), image, SWT.IMAGE_DISABLE);
										}
									};
									navigatorController.getChildren().add(itemController);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Removes the open menu controller.
	 *
	 * @param navigator
	 *            the navigator
	 */
	public void removeOpenMenuController(INavigator navigator) {
		if (null != openPartMenuController) {
			navigator.removeMenuController(toolMenuController, openPartMenuController);
			openPartMenuController = null;
			currentPart = null;
			navigator.refreshMenuController(toolMenuController);
		}
	}

	/**
	 * Removes the navigator controller.
	 *
	 * @param navigator
	 *            the navigator
	 */
	public void removeNavigatorController(INavigator navigator) {
		if (null != navigatorController) {
			navigator.removeMenuController(toolMenuController, navigatorController);
			navigatorController = null;
		}
	}

	/**
	 * Gets the current part.
	 *
	 * @return the current part
	 */
	public BoundedPart getCurrentPart() {
		return currentPart;
	}

	/**
	 * Gets the tool menu controller.
	 *
	 * @return the tool menu controller
	 */
	public MenuController getToolMenuController() {
		return toolMenuController;
	}

	/**
	 * Update open menu controller.
	 *
	 * @param part
	 *            the part
	 */
	public void updateOpenMenuController(MPart part) {
		currentPart = (BoundedPart) part.getObject();
		if (null != openPartMenuController) {
			navigator.removeMenuController(toolMenuController, openPartMenuController);
		}
		openPartMenuController = new MenuController(pluginResources, this, "openPartMenuController",
				getFromToolBundle("resourceBroadcast.title", currentPart.getTitle()),
				getFromToolBundle("resourceBroadcast.title.tooltip", currentPart.getTitle())) {
			@Override
			public Image getImage() {
				return AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE, "IMG_BROADCAST.gif");
			}
		};
		if (expandedOpenPartMenu)
			openPartMenuController.setExpanded(true);
		toolMenuController.getChildren().add(openPartMenuController);
		boolean hideExternalResources = InstanceScope.INSTANCE.getNode(GeneratorConstants.TOOL_BUNDLE)
				.getBoolean(ToolUtil.HIDE_EXTERNAL_RESOURCES, true);

		PartCore gencode = currentPart.getGenCode();
		if (null != gencode)
			for (final String adiResourceURI : gencode.getContext().getOpenParts()) {
				String bundleName = EngineTools.getInstanceKeys(adiResourceURI)[0];
				if (null == bundleName)
					bundleName = currentPart.getInputPart().getPluginResources().getPluginName();
				final boolean updateable = ToolUtil.isProject(bundleName);
				if (updateable || !hideExternalResources) {
					ItemController itemController = new ToolItemController(pluginResources, this, adiResourceURI, adiResourceURI,
							adiResourceURI, updateable) {
						@Override
						public void handleActivate() {
							ToolUtil.openNavigatorIDE(adiResourceURI);
						}

						@Override
						public Font getFont() {
							return updateable ? null : ITALIC_FONT;
						}

						@Override
						public Image getImage() {
							Image image = AdichatzApplication.getInstance().getImage(GeneratorConstants.TOOL_BUNDLE,
									"IMG_MENU_EDIT.png");
							if (updateable)
								return image;
							else
								return new Image(image.getDevice(), image, SWT.IMAGE_DISABLE);
						}
					};
					openPartMenuController.getChildren().add(itemController);
				}
			}
	}

	public MPartStack getEditorPartStack() {
		if (null == editorPartStack)
			editorPartStack = E4AdichatzApplication.getInstance().getEditorPartStack();
		return editorPartStack;
	}
}
