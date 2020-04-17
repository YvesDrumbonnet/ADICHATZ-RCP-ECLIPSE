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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.IAdiLogger;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.e4.part.AdiConsole;
import org.adichatz.engine.e4.preference.AdiPreferenceManager;
import org.adichatz.engine.e4.preference.LoggerPreferencePage;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.core.services.statusreporter.StatusReporter;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.prefs.BackingStoreException;

// TODO: Auto-generated Javadoc
/**
 * The Class E4Logger.
 */
@SuppressWarnings("restriction")
public class E4Logger extends Logger implements IAdiLogger {

	/** The Excluded classes from stack trace. */
	private static Set<String> EXCLUDED_CLASSES = new HashSet<String>();

	public static enum MESSAGE_TYPE {
		TRACE, INFO, DEBUG, WARN, ERROR, AFTER_TRACE, AFTER_INFO, AFTER_DEBUG, AFTER_WARN, AFTER_ERROR
	};

	/** is trace log enabled? */
	protected boolean traceEnabled;

	/** is debug log enabled? */
	protected boolean debugEnabled;

	/** is info log enabled? */
	protected boolean infoEnabled;

	/** is warning log enabled? */
	protected boolean warnEnabled;

	/** is warning log enabled? */
	protected boolean errorEnabled = true;

	/** The dialog log error. */
	protected boolean dialogLogError;

	/** The status log error. */
	protected boolean statusLogError;

	// Force console perspective for trace
	/** The trace console perspective. */
	protected boolean traceConsolePerspective;

	// Force console perspective for trace
	/** The debug console perspective. */
	protected boolean debugConsolePerspective;

	// Force console perspective for trace
	/** The info console perspective. */
	protected boolean infoConsolePerspective;

	// Force console perspective for trace
	/** The warn console perspective. */
	protected boolean warnConsolePerspective;

	// Force console perspective for trace
	/** The error console perspective. */
	protected boolean errorConsolePerspective = true;

	protected String jdkLoggerLevel;

	/** The console part stack. */
	protected MPartStack consolePartStack;

	protected List<DelayedMessage> delayedMessages;

	protected AdiConsole console;

	/** The context. */
	protected IEclipseContext context;

	protected List<E4LoggerListener> listeners = new ArrayList<E4LoggerListener>();

	protected boolean doit = true;

	/**
	 * Instantiates a new e4 logger.
	 * 
	 * @param context the context
	 */
	public E4Logger(IEclipseContext context) {
		this.context = context;
		EXCLUDED_CLASSES.add(Thread.class.getName());
		EXCLUDED_CLASSES.add(LogBroker.class.getName());
		EXCLUDED_CLASSES.add("org.eclipse.e4.ui.internal.workbench.swt.WorkbenchStatusReporter");
		EXCLUDED_CLASSES.add(StatusReporter.class.getName());
		EXCLUDED_CLASSES.add(DelayedMessage.class.getName());
		EXCLUDED_CLASSES.add(AdiConsole.class.getName());
		EXCLUDED_CLASSES.add(Utilities.class.getName());
		Class<?> clazz = this.getClass();
		while (!clazz.equals(Object.class)) {
			EXCLUDED_CLASSES.add(clazz.getName());
			clazz = clazz.getSuperclass();
		}

		new LogBroker(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.common.IAdiLogger#initPreferenceManager()
	 */
	public void initPreferenceManager() {
		AdiPreferenceManager preferenceManager = new AdiPreferenceManager("org.adichatz.logger",
				EngineE4Util.getFromEngineE4Bundle("adichatz.logger"),
				AdichatzApplication.getInstance().getImageDescriptor(EngineConstants.ENGINE_E4_BUNDLE, "IMG_LOGGER.png"),
				LoggerPreferencePage.class.getName(), injectValuesRunnable, initDefaultRunnable, null, context);
		AdiPreferenceManager.initPreferenceNode(preferenceManager);
	}

	/** The inject values runnable. */
	private Runnable injectValuesRunnable = () -> {
		IEclipsePreferences loggerNode = InstanceScope.INSTANCE.getNode("org.adichatz.logger");
		traceEnabled = loggerNode.getBoolean("traceEnabled", false);
		traceConsolePerspective = loggerNode.getBoolean("traceConsolePerspective", false);

		debugEnabled = loggerNode.getBoolean("debugEnabled", false);
		debugConsolePerspective = loggerNode.getBoolean("debugConsolePerspective", false);

		warnEnabled = loggerNode.getBoolean("warnEnabled", true);
		warnConsolePerspective = loggerNode.getBoolean("warnConsolePerspective", false);

		infoEnabled = loggerNode.getBoolean("infoEnabled", true);
		infoConsolePerspective = loggerNode.getBoolean("infoConsolePerspective", false);

		errorEnabled = loggerNode.getBoolean("errorEnabled", true);
		errorConsolePerspective = loggerNode.getBoolean("errorConsolePerspective", true);

		dialogLogError = loggerNode.getBoolean("dialogLogError", false);
		statusLogError = loggerNode.getBoolean("statusLogError", false);
		jdkLoggerLevel = loggerNode.get("jdkLoggerLevel", "WARNING");
	};

	/** The init default runnable. */
	private Runnable initDefaultRunnable = () -> {
		IEclipsePreferences loggerNode = InstanceScope.INSTANCE.getNode("org.adichatz.logger");
		loggerNode.putBoolean("traceEnabled", false);
		loggerNode.putBoolean("traceConsolePerspective", false);

		loggerNode.putBoolean("debugEnabled", false);
		loggerNode.putBoolean("debugConsolePerspective", false);

		loggerNode.putBoolean("warnEnabled", true);
		loggerNode.putBoolean("warnConsolePerspective", false);

		loggerNode.putBoolean("infoEnabled", true);
		loggerNode.putBoolean("infoConsolePerspective", false);

		loggerNode.putBoolean("errorEnabled", true);
		loggerNode.putBoolean("errorConsolePerspective", true);

		loggerNode.putBoolean("dialogLogError", false);
		loggerNode.putBoolean("statusLogError", false);

		loggerNode.put("jdkLoggerLevel", "WARNING");
		jdkLoggerLevel = "WARNING";
		try {
			loggerNode.flush();
		} catch (BackingStoreException e) {
			LogBroker.logError(e);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#trace(java.lang.Throwable,
	 * java.lang.String)
	 */
	@Override
	public void trace(Throwable throwable, String message) {
		if (traceEnabled) {
			activeConsolePerspective(traceConsolePerspective, MESSAGE_TYPE.TRACE, message, throwable);
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.TRACE, throwable, message));
			log(message, MESSAGE_TYPE.TRACE);
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.AFTER_TRACE, throwable, message));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#debug(java.lang.Throwable,
	 * java.lang.String)
	 */
	@Override
	public void debug(Throwable throwable, String message) {
		if (debugEnabled) {
			activeConsolePerspective(debugConsolePerspective, MESSAGE_TYPE.DEBUG, message, throwable);
			debug(throwable);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#debug(java.lang.Throwable)
	 */
	@Override
	public void debug(Throwable throwable) {
		if (debugEnabled) {
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.DEBUG, throwable, null));
			activeConsolePerspective(debugConsolePerspective, MESSAGE_TYPE.DEBUG, null, throwable);
			getConsole().print(new StringBuffer(throwable.getLocalizedMessage()), null, true);
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.AFTER_DEBUG, throwable, null));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#info(java.lang.Throwable,
	 * java.lang.String)
	 */
	@Override
	public void info(Throwable throwable, String message) {
		if (infoEnabled) {
			activeConsolePerspective(infoConsolePerspective, MESSAGE_TYPE.INFO, message, throwable);
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.INFO, throwable, message));
			log(message, MESSAGE_TYPE.INFO);
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.AFTER_INFO, throwable, message));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#warn(java.lang.String)
	 */
	@Override
	public void warn(Throwable throwable, String message) {
		if (warnEnabled) {
			activeConsolePerspective(warnConsolePerspective, MESSAGE_TYPE.WARN, message, throwable);
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.WARN, throwable, message));
			log(message, MESSAGE_TYPE.WARN);
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.AFTER_WARN, throwable, message));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#isTraceEnabled()
	 */
	public boolean isTraceEnabled() {
		return traceEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#isDebugEnabled()
	 */
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#isInfoEnabled()
	 */
	public boolean isInfoEnabled() {
		return infoEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#isWarnEnabled()
	 */
	public boolean isWarnEnabled() {
		return warnEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#isErrorEnabled()
	 */
	public boolean isErrorEnabled() {
		return errorEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#error(java.lang.String)
	 */
	@Override
	public void error(String message) {
		if (errorEnabled) {
			activeConsolePerspective(errorConsolePerspective, MESSAGE_TYPE.ERROR, message, null);
			if (doit)
				handleListeners(new E4LoggerEvent(MESSAGE_TYPE.ERROR, null, message));
			log(message, MESSAGE_TYPE.ERROR);
			if (dialogLogError && null != Display.getCurrent())
				EngineTools.openDialog(MessageDialog.INFORMATION, prefix(MESSAGE_TYPE.ERROR).toString(), message);
			if (statusLogError)
				EngineTools.openDialog(MessageDialog.INFORMATION, "CAUTION: status log not implemented!!! ", message);
			if (doit)
				handleListeners(new E4LoggerEvent(MESSAGE_TYPE.AFTER_ERROR, null, message));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.core.services.log.Logger#error(java.lang.Throwable,
	 * java.lang.String)
	 */
	@Override
	public void error(Throwable throwable, String message) {
		if (errorEnabled) {
			String exceptionMessage = throwable.getMessage();
			if (null == exceptionMessage)
				if (null != throwable.getCause()) {
					exceptionMessage = throwable.getCause().getLocalizedMessage();
				}
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.ERROR, throwable, exceptionMessage));
			activeConsolePerspective(errorConsolePerspective, MESSAGE_TYPE.ERROR, message, throwable);
			doit = false;
			if (null == getConsole())
				EngineTools.openDialog(MessageDialog.ERROR, "An error was encountered", EngineTools.getErrorString(throwable));
			else {
				if (null != message)
					error(message);
				if (null != exceptionMessage)
					log(exceptionMessage, MESSAGE_TYPE.ERROR);
				log(getString(throwable), MESSAGE_TYPE.ERROR);
			}
			doit = true;
			handleListeners(new E4LoggerEvent(MESSAGE_TYPE.AFTER_ERROR, throwable, exceptionMessage));
		}
	}

	private String getString(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		return sw.toString();
	}

	private void log(String message, MESSAGE_TYPE messageType) {
		if (null != getConsole()) {
			Color color = getColor(messageType);
			getConsole().print(prefix(messageType).append(message), color, true);
		}
	}

	private Color getColor(MESSAGE_TYPE messageType) {
		Color color = null;
		switch (messageType) {
		case ERROR:
			color = getConsole().getErrColor();
			break;
		default:
			color = getConsole().getOutColor();
			break;
		}
		return color;
	}

	/**
	 * Prefix.
	 * 
	 * @param level the level
	 * 
	 * @return the string buffer
	 */
	private StringBuffer prefix(MESSAGE_TYPE messageType) {
		StackTraceElement stackTraceElement = Utilities.getCurrentStackTraceElement(EXCLUDED_CLASSES);
		return prefix(messageType, stackTraceElement.getFileName(), stackTraceElement.getLineNumber());
	}

	private StringBuffer prefix(MESSAGE_TYPE messageType, String fileName, int lineNumber) {
		StringBuffer stringBuffer = new StringBuffer(messageType.toString()).append(" ")
				.append(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date())).append(" - ");
		return stringBuffer.append(fileName.substring(0, fileName.indexOf('.'))).append("[").append(lineNumber).append("]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.common.IAdiLogger#flush()
	 */
	@Override
	public void flush() {
		if (doit && null != delayedMessages) {
			try {
				doit = false;
				boolean force = false;
				for (DelayedMessage delayedMessage : delayedMessages) {
					if (delayedMessage.activeConsole()) {
						force = true;
						break;
					}
				}
				if (force || null != getConsole()) {
					if (!force)
						activateConsolePartStack();
					for (DelayedMessage delayedMessage : delayedMessages) {
						delayedMessage.flush();
					}
					delayedMessages = null;
				}
			} finally {
				doit = true;
			}
		}
	}

	/**
	 * Active console perspective.
	 * 
	 * @param active the active
	 */
	private void activeConsolePerspective(boolean active, MESSAGE_TYPE messageType, String message, Throwable throwable) {
		if (null == getConsole())
			new DelayedMessage(messageType, message, throwable);
		if (active)
			activateConsolePartStack();
	}

	private void activateConsolePartStack() {
		if (AdichatzApplication.getInstance() instanceof E4AdichatzApplication)
			try {
				E4AdichatzApplication.getInstance().switchToConsolePerspective();
			} catch (IllegalStateException e) { // No active window
			}
		if (null != getConsole()) {
			if (null != getConsolePartStack()) {
				for (String tag : consolePartStack.getTags()) {
					if (IPresentationEngine.MINIMIZED.equals(tag)) {
						consolePartStack.getTags().remove(tag);
						break;
					}
				}
			}
		}
	}

	private MPartStack getConsolePartStack() {
		if (null == consolePartStack) {
			EModelService modelService = context.get(EModelService.class);
			MApplication application = context.get(MApplication.class);
			if (null != application)
				consolePartStack = (MPartStack) modelService.find("org.adichatz.engine.e4.consolePartStack", application);
		}
		return consolePartStack;
	}

	private class DelayedMessage {
		private MESSAGE_TYPE messageType;

		private String message;

		private Throwable throwable;

		private String fileName;

		private int lineNumber;

		protected DelayedMessage(MESSAGE_TYPE messageType, String message, Throwable throwable) {
			this.messageType = messageType;
			this.message = message;
			this.throwable = throwable;
			if (null == delayedMessages)
				delayedMessages = new ArrayList<DelayedMessage>();
			StackTraceElement stackTraceElement = Utilities.getCurrentStackTraceElement(EXCLUDED_CLASSES);
			fileName = stackTraceElement.getFileName();
			lineNumber = stackTraceElement.getLineNumber();
			delayedMessages.add(this);
		}

		public boolean activeConsole() {
			switch (messageType) {
			case DEBUG:
				return debugConsolePerspective;
			case ERROR:
				return errorConsolePerspective;
			case INFO:
				return infoConsolePerspective;
			case TRACE:
				return traceConsolePerspective;
			case WARN:
				return warnConsolePerspective;
			default:
				return false;
			}
		}

		private void flush() {
			Color color = getColor(messageType);
			getConsole().print(prefix(messageType, fileName, lineNumber).append(message), color, true);
			if (null != throwable) {
				getConsole().print(prefix(messageType, fileName, lineNumber).append(getString(throwable)), color, true);
			}
		}
	}

	private void handleListeners(E4LoggerEvent event) {
		StackTraceElement stackTraceElement = Utilities.getCurrentStackTraceElement(EXCLUDED_CLASSES);
		event.className = stackTraceElement.getClassName();
		event.lineNumber = stackTraceElement.getLineNumber();
		for (E4LoggerListener listener : listeners) {
			switch (event.messageType) {
			case DEBUG:
				listener.beforeDebug(event);
				break;
			case ERROR:
				listener.beforeError(event);
				break;
			case INFO:
				listener.beforeInfo(event);
				break;
			case TRACE:
				listener.beforeTrace(event);
				break;
			case WARN:
				listener.afterWarn(event);
				break;
			case AFTER_DEBUG:
				listener.afterDebug(event);
				break;
			case AFTER_ERROR:
				listener.afterError(event);
				break;
			case AFTER_INFO:
				listener.afterInfo(event);
				break;
			case AFTER_TRACE:
				listener.afterTrace(event);
				break;
			case AFTER_WARN:
				listener.afterWarn(event);
				break;
			}
		}
	}

	@Override
	public Set<String> getExcludedCLasses() {
		return EXCLUDED_CLASSES;
	}

	private AdiConsole getConsole() {
		if (null == console)
			console = AdiConsole.getInstance();
		return console;
	}

	public void addListener(E4LoggerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(E4LoggerListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Level getLevel(String jdkLoggerLevelPref) {
		switch (jdkLoggerLevelPref) {
		case "OFF":
			return Level.OFF;
		case "SEVERE":
			return Level.SEVERE;
		case "WARNING":
			return Level.WARNING;
		case "INFO":
			return Level.INFO;
		case "CONFIG":
			return Level.CONFIG;
		case "FINE":
			return Level.FINE;
		case "FINER":
			return Level.FINER;
		case "FINEST":
			return Level.FINEST;
		case "ALL":
			return Level.ALL;
		default:
			return Level.WARNING;
		}
	}

	@Override
	public String getJdkLoggerLevel() {
		return jdkLoggerLevel;
	}
}
