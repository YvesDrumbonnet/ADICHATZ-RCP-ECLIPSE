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
package org.adichatz.engine.indigo.rcp;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.IPreferenceConstant;
import org.adichatz.engine.indigo.IndigoUIPlugin;
import org.adichatz.scenario.AAdiLogger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

// TODO: Auto-generated Javadoc
/**
 * The Class RcpLog.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class RcpLog extends AAdiLogger {

	/**
	 * Instantiates a new rcp log.
	 */
	public RcpLog() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.common.AAdiLog#initialize(org.eclipse.jface.
	 * preference.IPreferenceStore)
	 */
	public void initialize(IPreferenceStore store) {
		traceEnabled = store.getBoolean(IPreferenceConstant.logTraceEnabled);
		debugEnabled = store.getBoolean(IPreferenceConstant.logDebugEnabled);
		infoEnabled = store.getBoolean(IPreferenceConstant.logInfoEnabled);
		warnEnabled = store.getBoolean(IPreferenceConstant.logWarningEnabled);
		errorEnabled = store.getBoolean(IPreferenceConstant.logErrorEnabled);
		dialogLogError = store.getBoolean(IPreferenceConstant.dialogLogError);
		statusLogError = store.getBoolean(IPreferenceConstant.statusLogError);
	}

	@Override
	public void debug(Throwable throwable) {
		if (debugEnabled)
			throwable.printStackTrace();
	}

	@Override
	public void error(Throwable throwable) {
		error(throwable, null);
	}

	@Override
	public void error(String message) {
		error((Throwable) null, message);
	}

	@Override
	public void warn(String message) {
		if (warnEnabled) {
			System.out.println(prefix("WARNING").append(message));
		}
	}

	@Override
	public void info(String message) {
		if (infoEnabled) {
			System.out.println(prefix("INFO").append(message));
		}
	}

	@Override
	public void trace(String message) {
		if (traceEnabled) {
			System.out.println(prefix("TRACE").append(message));
		}
	}

	/**
	 * Log the specified error.
	 * 
	 * @param message the message
	 */
	@Override
	public void error(Throwable throwable, String message) {
		if (errorEnabled) {
			String errorMessage = null != message ? message : EngineTools.getErrorString(throwable);
			System.err.println(prefix("ERROR").append(errorMessage));
			if (null != throwable && null != message)
				System.err.println(EngineTools.getErrorString(throwable));
			if (dialogLogError && null != Display.getCurrent())
				EngineTools.openDialog(MessageDialog.ERROR, prefix("ERROR").toString(), errorMessage);
			if (statusLogError)
				log(IStatus.ERROR, IStatus.OK, errorMessage, throwable);
		}
	}

	/**
	 * Log the specified information. Create a status object representing the
	 * specified information.
	 * 
	 * @param severity  2 * the severity; one of <code>IStatus.OK</code>,
	 *                  <code>IStatus.ERROR</code>,<code>IStatus.INFO</code>, or
	 *                  <code>IStatus.WARNING</code>
	 * @param code      the plug-in-specific status code, or <code>OK</code>
	 * @param message   a human-readable message, localized to the current locale
	 * @param exception a low-level exception, or <code>null</code> if not
	 *                  applicable
	 */
	public void log(int severity, int code, String message, Throwable exception) {
		IndigoUIPlugin.ADICHATZ_APPLICATION_PLUGIN.getLog()
				.log(new Status(severity, "AdichatzRcp", code, message, exception));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.common.IAdiLogger#flush()
	 */
	@Override
	public void flush() {
	}
}