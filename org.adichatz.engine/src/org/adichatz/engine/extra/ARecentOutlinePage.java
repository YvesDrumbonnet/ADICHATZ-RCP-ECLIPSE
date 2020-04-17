package org.adichatz.engine.extra;

import java.util.ArrayList;
import java.util.List;

public abstract class ARecentOutlinePage extends ABindingOutlinePage {
	/*
	 * S T A T I C
	 */

	/** The THIS. */
	protected static ARecentOutlinePage THIS;

	public static List<ARecentOutlineItem> delayedOutlineItems = new ArrayList<>();

	public static void addRecentOutlineItem(ARecentOutlineItem recentOutlineItem) {
		if (null == THIS)
			delayedOutlineItems.add(recentOutlineItem);
		else {
			recentOutlineItem.init();
			THIS.addPShelfItem(recentOutlineItem);
		}

	}

	/**
	 * Gets the single instance of RecentOutlinePage.
	 * 
	 * @return single instance of RecentOutlinePage
	 */
	public static ARecentOutlinePage getInstance() {
		return THIS;
	}

	/*
	 * end S T A T I C
	 */

	protected void initializeItems() {
		for (ARecentOutlineItem recentOutlineItem : delayedOutlineItems) {
			recentOutlineItem.init();
			addPShelfItem(recentOutlineItem);
		}
	}

	public abstract void addPShelfItem(ARecentOutlineItem recentOutlineItem);
}
