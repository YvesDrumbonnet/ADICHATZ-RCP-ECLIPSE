package org.adichatz.jpa;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.extra.ARecentOutlinePage;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.recent.IRecentOpenEditor;
import org.adichatz.jpa.recent.RecentEditorOutlineItem;
import org.adichatz.jpa.recent.RecentUtil;
import org.adichatz.jpa.wrapper.RecentOpenEditorTreeWrapper;
import org.adichatz.jpa.xjc.RecentOpenEditorType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JPAActivator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		// Instantiates a new Plugin resources with parameters values
		JPAPluginResources pluginResources = new JPAPluginResources(getClass().getClassLoader());
		AdichatzApplication.getInstance().getPluginMap().put(EngineConstants.JPA_BUNDLE, pluginResources);
		ARecentOutlinePage.addRecentOutlineItem(new RecentEditorOutlineItem());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		if (null != ARecentOutlinePage.getInstance()) {
			RecentOpenEditorTreeWrapper recentOpenEditorTree = RecentOpenEditorTreeWrapper.getInstance();

			List<String> errorMessages = new ArrayList<String>();
			recentOpenEditorTree.getRecentOpenEditor().clear();
			for (IRecentOpenEditor recentOpenEditor : RecentEditorOutlineItem.getInstance().getRecentOpenEditorMap().values()) {
				if (null != recentOpenEditor) {
					errorMessages.addAll(recentOpenEditorTree.setParamTypes(recentOpenEditor, recentOpenEditor.getLabel(),
							ParamMap.POST_OPEN_EDITOR_INSTANCE));
					recentOpenEditorTree.getRecentOpenEditor().add((RecentOpenEditorType) recentOpenEditor);
				}
			}
			if (!errorMessages.isEmpty()) {
				String[] messages = new String[errorMessages.size() + 2];
				messages[0] = JPAUtil.getFromJpaBundle("recent.invalid.param.marshalling");
				messages[1] = JPAUtil.getFromJpaBundle("recent.error.when.marshalling");
				int i = 2;
				for (String message : errorMessages)
					messages[i++] = message;
				Display display = new Shell().getDisplay();
				AdichatzApplication.getInstance().getApplContext().put(AdiFormToolkit.class.getName(), new AdiFormToolkit(display));
				EngineTools.openDialog(display, MessageDialog.ERROR, messages);
			}
			File recentOpenEditorFile = RecentUtil.getRecentOpenEditorFile();
			if (recentOpenEditorTree.getRecentOpenEditor().isEmpty()) {
				if (recentOpenEditorFile.exists())
					recentOpenEditorFile.delete();
			} else
				EngineTools.createXmlFile(recentOpenEditorFile, recentOpenEditorTree, "jpa/recentOpenEditorTree.xsd");
		}
	}
}
