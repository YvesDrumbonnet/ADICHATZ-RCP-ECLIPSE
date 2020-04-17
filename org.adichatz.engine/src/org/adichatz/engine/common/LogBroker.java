package org.adichatz.engine.common;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class LogBroker {

	private static IAdiLogger LOGGER;

	public static IAdiLogger getLogger() {
		return LOGGER;
	}

	public LogBroker(IAdiLogger logger) {
		LOGGER = logger;
	}

	/**
	 * Log trace.
	 * 
	 * @param message
	 *            the message
	 */
	public static void logTrace(Object message) {
		LOGGER.trace(String.valueOf(message));
	}

	/**
	 * Log info.
	 * 
	 * @param message
	 *            the message
	 */
	public static void logInfo(String message) {
		LOGGER.info(message);
	}

	public static void logWarning(String message) {
		LOGGER.warn(message);
	}

	/**
	 * Log error.
	 * 
	 * @param exception
	 *            the exception
	 */
	public static void logError(Throwable exception) {
		if (null != LOGGER)
			LOGGER.error(exception);
		else
			exception.printStackTrace();
	}

	/**
	 * Log error.
	 * 
	 * @param message
	 *            the message
	 */
	public static void logError(String message) {
		if (null != LOGGER)
			LOGGER.error(message);
		else
			System.err.println(message);
	}

	/**
	 * Log error.
	 * 
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public static void logError(String message, Throwable exception) {
		if (null != LOGGER)
			LOGGER.error(exception, message);
		else {
			System.err.println(message);
			exception.printStackTrace();
		}
	}

	/**
	 * Display error.
	 * 
	 * @param title
	 *            the title
	 * @param message
	 *            the message
	 */
	public static void displayError(String title, String message) {
		if (null != Display.getCurrent())
			EngineTools.openDialog(MessageDialog.ERROR,
					(null == title) ? EngineTools.getFromEngineBundle("error.encounteredError") : title, message);
		else
			logError(message);
	}

	/**
	 * Display error.
	 * 
	 * @param title
	 *            the title
	 * @param message
	 *            the message
	 * @param exception
	 *            the exception
	 */
	public static void displayError(String title, Throwable exception, String... messages) {
		String finalMessage = "";
		for (String message : messages) {
			if (!finalMessage.equals(message)) {
				if (!finalMessage.isEmpty())
					finalMessage = finalMessage.concat("\n\n");
				finalMessage = finalMessage.concat(message);
			}
		}
		LOGGER.error(finalMessage);
		String message = exception.getLocalizedMessage();
		if (null == message)
			message = exception.toString();
		if (!finalMessage.equals(message))
			finalMessage = finalMessage.concat("\n\n").concat(message);
		if (null != exception)
			LOGGER.error(exception);
		displayError(title, finalMessage);
	}

}
