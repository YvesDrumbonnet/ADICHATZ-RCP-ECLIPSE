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
package org.adichatz.studio.broadcast;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.broadcast.BroadcastUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.XjcTreePartOpening;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

// TODO: Auto-generated Javadoc
/**
 * The Class StartupBroadcast.
 */
@SuppressWarnings("restriction")
public class StartupBroadcast extends Thread implements IStartup {

	private static StartupBroadcast THIS;

	private int ideCommPort;

	public static StartupBroadcast getInstance() {
		return THIS;
	}

	/** The server socket. */
	private ServerSocket serverSocket;

	/** The done. */
	private boolean done = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@Override
	public void earlyStartup() {
		THIS = this;
		Bundle bundle = Platform.getBundle(GeneratorConstants.STUDIO_BUNDLE);
		if (Bundle.ACTIVE != bundle.getState() && Bundle.STARTING != bundle.getState())
			try {
				bundle.start();
			} catch (BundleException e) {
				e.printStackTrace();
			}
		if (StudioRcpPlugin.getDefault().getPreferenceStore().getBoolean(BroadcastUtil.PREFS_IDE_COMM_LAUNCH))
			start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		Socket socket;
		try {
			serverSocket = new ServerSocket(0);
			ideCommPort = serverSocket.getLocalPort();
			Properties properties = new Properties();
			properties.setProperty(BroadcastUtil.PREFS_IDE_COMM_PORT, String.valueOf(ideCommPort));
			FileWriter fileWriter = new FileWriter(new File(BroadcastUtil.ADI_PROPERTY_FILE_NAME));
			properties.store(fileWriter, "Written by: org.adichatz.studio.broadcast.StartupBroadcast. Contains only one item.");
			fileWriter.close();
			while (null != (socket = serverSocket.accept()) && !done) {
				final String adiResourceURI = (String) readObject(socket.getInputStream());
				try {
					Display.getDefault().syncExec(() -> {
						new XjcTreePartOpening(adiResourceURI);
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!EngineTools.isEmpty(adiResourceURI)
						&& StudioRcpPlugin.getDefault().getPreferenceStore().getBoolean(BroadcastUtil.PREFS_IDE_FORCE_ACTIVE))
					activeWindow();
			}
			serverSocket.close();
			serverSocket = null;
		} catch (final Exception e) {
			Display.getDefault().syncExec(() -> {
				EngineTools.openDialog(MessageDialog.ERROR, getFromEngineBundle("error.encounteredError"),
						getFromStudioBundle("broadcast.runtimeServerThread.startupFailed"), EngineTools.getErrorString(e));
			});
		}
	}

	/**
	 * Active window .
	 */
	private void activeWindow() {
		final Display display = Display.getDefault();
		display.asyncExec(() -> {
			Shell shell = display.getActiveShell();
			if (shell.getMinimized())
				shell.setMinimized(false);
			shell.forceActive();
		});
	}

	/**
	 * Checks if is done.
	 * 
	 * @return true, if is done
	 */
	public boolean isDone() {
		return done;
	}

	public synchronized void closeSocketServer() {
		done = true;
		THIS = null;
		BroadcastUtil.sendSocket(ideCommPort, null);
	}

	/**
	 * Read object.
	 * 
	 * @param inputStream
	 *            the input stream
	 * @return the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	private Object readObject(InputStream inputStream) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		Object object = ois.readObject();
		ois.close();
		return object;
	}
}
