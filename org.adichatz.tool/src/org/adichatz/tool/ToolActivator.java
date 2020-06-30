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

import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import java.net.URL;

import org.adichatz.engine.common.AApplicationListener;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ApplicationEvent;
import org.adichatz.engine.common.ApplicationEvent.EVENT_TYPE;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.FiltersMatcher;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.preference.AdiPreferenceManager;
import org.adichatz.engine.extra.IGenerator;
import org.adichatz.engine.xjc.AdichatzRcpConfigTree;
import org.adichatz.engine.xjc.MenuPathType;
import org.adichatz.engine.xjc.NavigatorType;
import org.adichatz.generator.common.GeneratorConstants;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

// TODO: Auto-generated Javadoc
/**
 * The activator class controls the plug-in life cycle.
 */
public class ToolActivator implements BundleActivator {

	private AdichatzRcpConfigTree configTree;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		new AdiPluginResources(GeneratorConstants.TOOL_BUNDLE, GeneratorConstants.TOOL_BUNDLE, "gencode",
				getClass().getClassLoader());

		Runnable initDefaultRunnable = () -> {
			IEclipsePreferences toolNode = InstanceScope.INSTANCE.getNode(GeneratorConstants.TOOL_BUNDLE);
			toolNode.putBoolean(ToolUtil.ADD_TOOL_NAVIGATOR, true);
			toolNode.putBoolean(ToolUtil.HIDE_EXTERNAL_RESOURCES, true);
		};
		AdiPreferenceManager preferenceManager = new AdiPreferenceManager(GeneratorConstants.TOOL_BUNDLE,
				getFromToolBundle("tool.development.tools"),
				AdichatzApplication.getInstance().getImageDescriptor(GeneratorConstants.TOOL_BUNDLE, "IMG_BROADCAST.gif"),
				ToolPreferencePage.class.getName(), null, initDefaultRunnable, null, null);
		AdiPreferenceManager.initPreferenceNode(preferenceManager);

		boolean addToolNavigator = InstanceScope.INSTANCE.getNode(GeneratorConstants.TOOL_BUNDLE)
				.getBoolean(ToolUtil.ADD_TOOL_NAVIGATOR, true);
		if (addToolNavigator) {
			configTree = AdichatzApplication.getInstance().getConfigTree();
			addToolNavigator = null != configTree && null != configTree.getRcpConfiguration()
					&& null != configTree.getRcpConfiguration().getNavigators();
		}

		if (addToolNavigator) {
			// Add a Application listener for adding menu to existing navigator following to preerence filters
			AdichatzApplication.getInstance().getApplicationListeners().add(new AApplicationListener(EVENT_TYPE.POST_START_UP) {
				@Override
				public void handleEvent(ApplicationEvent event) {
					MenuPathType menuPath = new MenuPathType();
					menuPath.setId("toolNavigatorContentId");
					menuPath.setAdiResourceURI("bundleclass://org.adichatz.tool/org.adichatz.tool.ToolNavigatorContent");
					menuPath.setUniqueInstance(true);
					String filters = InstanceScope.INSTANCE.getNode(GeneratorConstants.TOOL_BUNDLE).get(ToolUtil.NAVIGATOR_FILTERS,
							"");
					FiltersMatcher filterMatchers = new FiltersMatcher(filters);
					for (NavigatorType navigator : configTree.getRcpConfiguration().getNavigators().getNavigator()) {
						if (filterMatchers.evaluate(navigator.getId()))
							navigator.getMenuPath().add(menuPath);
					}
				}
			});
			AdichatzApplication.getInstance().getApplicationListeners().add(new AApplicationListener(EVENT_TYPE.BRINGTOTOP) {
				@Override
				public void handleEvent(ApplicationEvent event) {
					if (null != ToolNavigatorContent.getInstance())
						ToolNavigatorContent.getInstance().refreshOpenPartsInNewThread(event);
				}
			});
		}
		AdichatzApplication.getInstance().getApplicationListeners()
				.add(new AApplicationListener(EVENT_TYPE.POST_INSTANTIATE_XMLCORE) {
					@Override
					public void handleEvent(ApplicationEvent event) {
						if (event.context.getRootCore().getController() instanceof BoundedPart) {
							event.context.getOpenParts().add(event.adiResourceURI);
							// ToolNavigatorContent instance could be null when navigator is minimized
							if (null != ToolNavigatorContent.getInstance())
								ToolNavigatorContent.getInstance().refreshOpenPartsInNewThread(event);
						}
					}
				});

		Class<?> generatorClass = AdichatzApplication.getInstance().getApplicationPluginResources().getGencodePath()
				.getClazz(EngineConstants.GENERATOR_CLASS_NAME, true);
		if (null != generatorClass)
			EngineConstants.GENERATOR = (IGenerator) generatorClass.getDeclaredConstructor().newInstance();
		else
			LogBroker.logWarning("Cannot load class '" + EngineConstants.GENERATOR_CLASS_NAME + "'! Development mode is disabled");

		//force org.adichatz.testing bundle if needed 
		URL entry = Platform.getBundle(AdichatzApplication.getInstance().getApplicationPluginResources().getPluginName())
				.getEntry("resources/xml/AdichatzTesting.xml");
		if (null != entry) {
			Bundle testingBundle = Platform.getBundle("org.adichatz.testing");
			if (null != testingBundle)
				testingBundle.start();
			else
				logWarning(getFromToolBundle("cannot.activate.testing"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}
}
