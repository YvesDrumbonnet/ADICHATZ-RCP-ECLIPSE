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
package org.adichatz.engine.e4.resource;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.e4.part.AdiConsole;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.extra.IOutlineContainerPart;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.query.AQueryManager;
import org.adichatz.engine.widgets.GMap;
import org.adichatz.engine.xjc.AdichatzRcpConfigTree;
import org.adichatz.engine.xjc.LoginType;
import org.adichatz.engine.xjc.NavigatorsType;
import org.adichatz.engine.xjc.ParamType;
import org.adichatz.engine.xjc.RcpConfigurationType;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.menu.impl.HandledToolItemImpl;
import org.eclipse.e4.ui.model.application.ui.menu.impl.ToolBarImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.internal.InternalPolicy;
import org.eclipse.swt.widgets.Control;

// TODO: Auto-generated Javadoc
/**
 * The Class E4AdichatzApplication.
 */
@SuppressWarnings("restriction")
public class E4AdichatzApplication extends AdichatzApplication {
	/*
	 * S T A T I C
	 */
	/**
	 * Open part.
	 *
	 * @param eclipseContext
	 *            the eclipse context
	 * @param paramMap
	 *            the param map
	 * @return the bounded part
	 */
	public static BoundedPart openPart(final Object eclipseContext, final ParamMap paramMap) {
		IEclipseContext context = (IEclipseContext) eclipseContext;
		// commandService and handlerService are specific by context
		// Do not use a commandService defined during application initialization
		ECommandService commandService = context.get(ECommandService.class);
		EHandlerService handlerService = context.get(EHandlerService.class);

		String command = paramMap.getString(ParamMap.COMMAND);
		if (null == command)
			command = "adichatz.open.editor.command";
		Command cmd = commandService.getCommand(command);
		ParameterizedCommand pCmd = ParameterizedCommand.generateCommand(cmd, null);
		context.set(ParamMap.class, paramMap);
		return (BoundedPart) handlerService.executeHandler(pCmd, context);
	}

	/**
	 * Gets the single instance of AdichatzApplication.
	 * 
	 * @return single instance of AdichatzApplication
	 */
	public static E4AdichatzApplication getInstance() {
		return (E4AdichatzApplication) THIS;
	}

	/*
	 * end S T A T I C
	 */

	/** The save tool item. */
	private HandledToolItemImpl saveToolItem;

	/** The refresh tool item. */
	private HandledToolItemImpl refreshToolItem;

	/** The error tool item. */
	private HandledToolItemImpl errorToolItem;

	/** The toolbar. */
	private ToolBarImpl toolbar;

	/** The active part. */
	private Object activePart;

	protected Map<BoundedPart, Object> openPartMap = new HashMap<>();

	public E4AdichatzApplication(AdichatzApplication oldAA, AdiPluginResources pluginResources) {
		super();
		if (null != oldAA) {
			getPluginMap().putAll(oldAA.getPluginMap());
			applicationListeners.addAll(oldAA.getApplicationListeners());
			earlyStartupHooks.addAll(oldAA.getEarlyStartupHooks());
		}
		setApplicationPluginResources(pluginResources);
		// Force plugin resources to be initialized if not already loaded.
		AdichatzApplication.getPluginResources(EngineConstants.ENGINE_BUNDLE);
		String gencodePackage = popContextValue(EngineConstants.GENCODE_PACKAGE);
		new AdiPluginResources(EngineConstants.ENGINE_E4_BUNDLE, EngineConstants.ENGINE_E4_BUNDLE, gencodePackage,
				getClass().getClassLoader());
		loadRcpConfigTree();
	}

	/**
	 * Inits the toolbar.
	 *
	 * @param application
	 *            the application
	 * @param context
	 *            the context
	 */
	public void initToolbar(MApplication application, IEclipseContext context) {
		applContext.put(IEclipseContext.class.getName(), context.getActiveLeaf());
		EModelService modelService = context.get(EModelService.class);
		applContext.put(EngineE4Util.EDITOR_PARTSTACK, modelService.find(EngineE4Util.EDITOR_PARTSTACK, application));
		applContext.put(MPerspectiveStack.class.getName(), modelService.find(EngineE4Util.PERSPECTIVE_STACK, application));
		toolbar = (ToolBarImpl) modelService.find("adichatz.editor.toolbar", application);
		saveToolItem = (HandledToolItemImpl) modelService.find("adichatz.editor.save.toolItem", application);
		saveToolItem.setTooltip(getFromEngineE4Bundle("adichatz.save.editor"));
		refreshToolItem = (HandledToolItemImpl) modelService.find("adichatz.editor.refresh.toolItem", application);
		refreshToolItem.setTooltip(getFromEngineE4Bundle("adichatz.refresh.editor"));
		errorToolItem = (HandledToolItemImpl) modelService.find("adichatz.editor.error.toolItem", application);
		errorToolItem.setTooltip(getFromEngineE4Bundle("adichatz.error.editor"));

		saveToolItem.setVisible(false);
		refreshToolItem.setVisible(false);
		errorToolItem.setVisible(false);
	}

	/**
	 * Enable tool bar.
	 *
	 * @param part
	 *            the part
	 */
	public void enableToolBar(Object part) {
		if (part.equals(activePart) && part instanceof BoundedPart && ((BoundedPart) part).hasEditorToolBar()) {
			BoundedPart boundedPart = (BoundedPart) part;
			refreshToolItem
					.setIconURI(boundedPart.isDirty() ? "platform:/plugin/org.adichatz.jpa/resources/icons/IMG_REFRESH_DIRTY.png"
							: "platform:/plugin/org.adichatz.engine/resources/icons/IMG_REFRESH_DB.png");
			// Enable RefreshItem when no entity for BoundedPArt (e.g.
			// ListDetailQueryForm) or entity status != PERSIST
			boolean refreshEnabled = null == boundedPart.getEntity();
			if (!refreshEnabled && IEntityConstants.PERSIST != boundedPart.getEntity().getStatus())
				refreshEnabled = true;
			refreshToolItem.setEnabled(refreshEnabled);

			saveToolItem.setEnabled(boundedPart.getBoundedPartChangeManager().isSaveable());
			errorToolItem.setEnabled(boundedPart.getBoundedPartChangeManager().hasError());
		}
	}

	/**
	 * Part to be rendered.
	 *
	 * @param part
	 *            the part
	 */
	public void partToBeRendered(Object part) {
		if (null != toolbar) {
			if (part instanceof IOutlineContainerPart)
				((IOutlineContainerPart) part).partActivation();
			else if (part instanceof OutlinePart
					&& OutlinePart.getInstance().getCurrentPage().getLinkedPart() instanceof IOutlineContainerPart)
				part = OutlinePart.getInstance().getCurrentPage().getLinkedPart();
			else if (null != OutlinePart.getInstance())
				OutlinePart.getInstance().showPage(null);
			boolean visible = part instanceof BoundedPart && ((BoundedPart) part).hasEditorToolBar();
			saveToolItem.setVisible(visible);
			refreshToolItem.setVisible(visible);
			errorToolItem.setVisible(visible);
			// Does not work (only last item is displayed) see bug
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=400217
			Object renderer = toolbar.getRenderer();
			toolbar.setRenderer(null);
			toolbar.setRenderer(renderer);
			activePart = part;
			enableToolBar(part);
		}
	}

	public Map<BoundedPart, Object> getOpenPartMap() {
		return openPartMap;
	}

	/**
	 * Load config tree (only when called from RCP context).
	 *
	 */
	private void loadRcpConfigTree() {

		AdichatzRcpConfigTree configTree = (AdichatzRcpConfigTree) applicationPluginResources.getConfigTree("AdichatzRcpConfig.xml",
				true);
		if (null == configTree)
			configTree = new AdichatzRcpConfigTree();

		// Put default values
		applContext.put(EngineConstants.DEFAULT_REF_TEXT_POPUP_URI, "adi://org.adichatz.jpa/common/DefaultRefText");
		RcpConfigurationType rcpConfiguration = configTree.getRcpConfiguration();
		if (null == rcpConfiguration) {
			rcpConfiguration = new RcpConfigurationType();
			configTree.setRcpConfiguration(rcpConfiguration);
			ParamType param = new ParamType();
			param.setId(EngineConstants.DEFAULT_QUERY_MAX_RESULT);
			param.setValue("50");
			rcpConfiguration.getParam().add(param);

			param = new ParamType();
			param.setId(EngineConstants.DEFAULT_REF_TEXT_POPUP_URI);
			param.setValue("adi://org.adichatz.jpa/common/DefaultRefText");
			rcpConfiguration.getParam().add(param);

			param = new ParamType();
			param.setId(EngineConstants.INTRO_PART_URI);
			param.setValue("bundleclass://org.adichatz.engine/org.adichatz.engine.intro.DefaultIntroPanel");
			rcpConfiguration.getParam().add(param);

			param = new ParamType();
			param.setId(EngineConstants.INTRO_OUTLINE_URI);
			param.setValue("bundleclass://org.adichatz.engine.e4/org.adichatz.engine.e4.resource.RecentOutlinePage");
			rcpConfiguration.getParam().add(param);
		} else {
			for (ParamType param : rcpConfiguration.getParam())
				if (param.getId().equals(EngineConstants.DEFAULT_QUERY_MAX_RESULT))
					AQueryManager.DEFAULT_MAX_RESULTS = Integer.parseInt(param.getValue());
				else if (param.getId().equals(EngineConstants.ADICHATZ_GMAP_API_KEY)) {
					GMap.GMAP_API_KEY = param.getValue();
				} else if (param.getId().equals(EngineConstants.INTRO_OUTLINE_URI)) {
					try {
						ReflectionTools.instantiateURI(param.getValue(), null, null);
					} catch (Exception e) {
						LogBroker.logError(e);
					}
				} else if (param.getId().equals(EngineConstants.ADICHATZ_AVOIDED_MESSAGES)) {
					StringTokenizer tokenizer = new StringTokenizer(param.getValue(), ",");
					while (tokenizer.hasMoreTokens()) {
						AdiConsole.AVOIDED_MESSAGES.add(tokenizer.nextToken().trim());
					}
				} else
					applContext.put(param.getId(), param.getValue());
			if (null != rcpConfiguration.getNavigators())
				applContext.put(NavigatorsType.class.getName(), rcpConfiguration.getNavigators());
			if (InternalPolicy.OSGI_AVAILABLE)
				applContext.put(LoginType.class.getName(), configTree.getLogin());
		}
	}

	@Override
	public void reapplyStyles(Control control) {
		IEclipseContext context = getContextValue(IEclipseContext.class);
		if (null != context) {
			IThemeEngine themeEngine = context.get(IThemeEngine.class);
			if (null != themeEngine)
				themeEngine.applyStyles(control, true);
		}
	}

	protected Object injectValue(String key) {
		if (MPerspectiveStack.class.getName().equals(key)) {
			String location = Platform.getLocation().toString();
			EngineTools.openDialog(MessageDialog.ERROR, getFromEngineE4Bundle("invalid.run.configuration.title"),
					getFromEngineE4Bundle("invalid.run.configuration.content", location,
							location.concat("/.metadata/.plugins/org.eclipse.e4.workbench/workbench.xmi")));
		}
		return super.injectValue(key);
	}
}
