package org.adichatz.engine.extra;

public class OutlineEvent {

	/**
	 * The Enum EVENT_TYPE.
	 */
	public static enum EVENT_TYPE {
		FILTER_CHANGE, REFRESH_OUTLINE
	};

	private EVENT_TYPE eventType;

	public OutlineEvent(EVENT_TYPE eventType) {
		this.eventType = eventType;
	}

	public EVENT_TYPE getEventType() {
		return eventType;
	}
}
