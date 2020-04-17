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
package org.adichatz.scenario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.IAdiLogger;
import org.adichatz.engine.common.LogBroker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

// TODO: Auto-generated Javadoc
/**
 * The Class AAdiLogger.
 * 
 * Singleton to manage log in Adichatz
 * 
 * @author Yves Drumbonnet
 *
 */
@SuppressWarnings("restriction")
public abstract class AAdiLogger implements IAdiLogger {

	/** The Excluded classes from stack trace. */
	public static Set<String> EXCLUDED_CLASSES = new HashSet<String>();

	/**
	 * Instantiates a new a adi log.
	 */
	public AAdiLogger() {
		LOG = this;
		EXCLUDED_CLASSES.add(Thread.class.getName());
		EXCLUDED_CLASSES.add(AAdiLogger.class.getName());
		EXCLUDED_CLASSES.add(Logger.class.getName());
		EXCLUDED_CLASSES.add(LogBroker.class.getName());
		Class<?> clazz = this.getClass();
		while (!clazz.equals(AAdiLogger.class)) {
			EXCLUDED_CLASSES.add(clazz.getName());
			clazz = clazz.getSuperclass();
		}
	}

	/** is trace log enabled? */
	protected boolean traceEnabled = false;

	/** is debug log enabled? */
	protected boolean debugEnabled = false;

	/** is info log enabled? */
	protected boolean infoEnabled = true;

	/** is warning log enabled? */
	protected boolean warnEnabled = true;

	/** is warning log enabled? */
	protected boolean errorEnabled = true;

	/** The dialog log error. */
	protected boolean dialogLogError = false;

	/** The status log error. */
	protected boolean statusLogError = false;

	/** The LOG. */
	public static AAdiLogger LOG;

	/**
	 * Gets the single instance of AAdiLog.
	 * 
	 * @return single instance of AAdiLog
	 */
	public static AAdiLogger getInstance() {
		return LOG;
	}

	/**
	 * Log trace.
	 * 
	 * @param message the message
	 */
	public static void logTrace(Object message) {
		LOG.trace(String.valueOf(message));
	}

	/**
	 * Log info.
	 * 
	 * @param message the message
	 */
	public static void logInfo(String message) {
		LOG.info(message);
	}

	public static void logWarning(String message) {
		LOG.warn(message);
	}

	/**
	 * Log error.
	 * 
	 * @param exception the exception
	 */
	public static void logError(Throwable exception) {
		if (null != LOG)
			LOG.error(exception);
		else
			exception.printStackTrace();
	}

	/**
	 * Log error.
	 * 
	 * @param message the message
	 */
	public static void logError(String message) {
		if (null != LOG)
			LOG.error(message);
		else
			System.err.println(message);
	}

	/**
	 * Log error.
	 * 
	 * @param message   the message
	 * @param exception the exception
	 */
	public static void logError(String message, Throwable exception) {
		if (null != LOG)
			LOG.error(exception, message);
		else {
			System.err.println(message);
			exception.printStackTrace();
		}
	}

	/**
	 * Display error.
	 * 
	 * @param title   the title
	 * @param message the message
	 */
	public static void displayError(String title, String message) {
		if (null != Display.getCurrent())
			ErrorDialog.openError(Display.getCurrent().getActiveShell(),
					(null == title) ? EngineTools.getFromEngineBundle("error.encounteredError") : title, "",
					new Status(IStatus.ERROR, "Adichatz", message));
		else
			logError(message);
	}

	public boolean isTraceEnabled() {
		return traceEnabled;
	}

	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	public boolean isInfoEnabled() {
		return infoEnabled;
	}

	public boolean isWarnEnabled() {
		return warnEnabled;
	}

	public boolean isErrorEnabled() {
		return errorEnabled;
	}

	/**
	 * Prefix.
	 * 
	 * @param level the level
	 * 
	 * @return the string buffer
	 */
	protected StringBuffer prefix(String level) {
		StringBuffer stringBuffer = new StringBuffer(level).append(" ")
				.append(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date())).append(" - ");
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String className = "";
		int lineNumber = -1;

		for (StackTraceElement stackTraceElement : stackTraceElements)
			if (!EXCLUDED_CLASSES.contains(stackTraceElement.getClassName())) {
				className = stackTraceElement.getClassName();
				lineNumber = stackTraceElement.getLineNumber();
				break;
			}

		return stringBuffer.append(className.substring(className.lastIndexOf('.') + 1)).append("[").append(lineNumber)
				.append("]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.common.IAdiLogger#initPreferenceManager()
	 */
	public void initPreferenceManager() {
	}

	@Override
	public Set<String> getExcludedCLasses() {
		return null;
	}

	@Override
	public String getJdkLoggerLevel() {
		return "WARNING";
	}

	@Override
	public Level getLevel(String jdkLoggerLevelPref) {
		return Level.WARNING;
	}

	/**
	 * Initialize.
	 * 
	 * @param store the store
	 */
	public abstract void initialize(IPreferenceStore store);
}