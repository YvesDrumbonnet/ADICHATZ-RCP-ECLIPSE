package org.adichatz.engine.e4.resource;

import org.adichatz.engine.e4.resource.E4Logger.MESSAGE_TYPE;

public class E4LoggerEvent {
	public E4Logger.MESSAGE_TYPE messageType;

	public Throwable throwable;

	public String message;

	public String className;

	public int lineNumber;

	public E4LoggerEvent(MESSAGE_TYPE messageType, Throwable throwable, String message) {
		this.messageType = messageType;
		this.throwable = throwable;
		this.message = message;
	}

}
