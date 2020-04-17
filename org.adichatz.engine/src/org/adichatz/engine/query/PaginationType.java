package org.adichatz.engine.query;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.xjc.AdichatzRcpConfigTree;
import org.adichatz.engine.xjc.TableComponentType;

public class PaginationType {
	private static List<TableComponentType> PAGINATIONS;

	public static TableComponentType DEFAULT;

	public static List<TableComponentType> getPaginations() {
		return PAGINATIONS;
	}

	static {
		/*
		 * Add pagination from all Adichatz Plugin Resources.
		 * 
		 * Proccess Application Plugin Resources to the end to override other configuration. Put a null class name for remove a
		 * pagination defined in other plugin
		 */
		PAGINATIONS = new ArrayList<TableComponentType>();
		AdiPluginResources applicationPR = AdichatzApplication.getInstance().getApplicationPluginResources();
		for (AdiPluginResources pluginResources : AdichatzApplication.getInstance().getPluginMap().values()) {
			if (!pluginResources.equals(applicationPR))
				addPagination(pluginResources);
		}
		if (null != applicationPR)
			addPagination(applicationPR);
	}

	private static void addPagination(AdiPluginResources pluginResources) {
		AdichatzRcpConfigTree configTree = (AdichatzRcpConfigTree) pluginResources.getConfigTree("AdichatzRcpConfig.xml", true);
		if (null != configTree && null != configTree.getRcpConfiguration()
				&& null != configTree.getRcpConfiguration().getTableStatusBars()) {
			for (TableComponentType tableStatusBar : configTree.getRcpConfiguration().getTableStatusBars().getTableStatusBar()) {
				if (EngineTools.isEmpty(tableStatusBar.getClassName()))
					for (TableComponentType pagination : PAGINATIONS.toArray(new TableComponentType[PAGINATIONS.size()]))
						if (tableStatusBar.getId().equals(pagination.getId()))
							PAGINATIONS.remove(pagination);
						else {
							tableStatusBar.setText(pluginResources.getMessage(tableStatusBar.getResourceBundleKey(),
									tableStatusBar.getText()));
							PAGINATIONS.add(tableStatusBar);
							if (tableStatusBar.isDefault())
								DEFAULT = tableStatusBar;
						}
			}
		}
	}

	private String key;

	private String statusBarClassName;

	private String text;

	public PaginationType(String key, String statusBarClassName, String text) {
		this.key = key;
		this.statusBarClassName = statusBarClassName;
		this.text = text;
	}

	public String getKey() {
		return key;
	}

	public String getStatusBarClassName() {
		return statusBarClassName;
	}

	public String getText() {
		return text;
	}

}
