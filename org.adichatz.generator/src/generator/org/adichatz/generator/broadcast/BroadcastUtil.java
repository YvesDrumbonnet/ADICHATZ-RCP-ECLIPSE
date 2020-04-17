package org.adichatz.generator.broadcast;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.displayError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.eclipse.core.runtime.Platform;

public class BroadcastUtil {
	/** Does the runtime listener must be launch automatically */
	public static String PREFS_IDE_COMM_LAUNCH = "prefs.IDE.comm.launch";

	public static String ADI_PROPERTY_FILE_NAME = Platform.getInstallLocation().getURL().getFile()
			.concat("/configuration/adichatzPort.ini");

	/**
	 * The port number use by the runtime listener for communication between a runtime application and the studio.
	 * 
	 * Constant is used by org.adichatz.tool.util.ToolUtil#sendSocket
	 */
	public static String PREFS_IDE_COMM_PORT = "prefs.IDE.comm.port"; // Be careful before changing variable - Port must be
																		// initialized into org.adichats.broadcast plugin and
																		// ToolUtil.sendSocket method with the same value

	public static String PREFS_IDE_FORCE_ACTIVE = "prefs.IDE.force.active"; // Force activation of IDE after receiving socket.

	/**
	 * Send socket.
	 * 
	 * @param adiResourceURI
	 *            the open parts
	 * @param port
	 *            the port
	 * @return true, if successful
	 */
	public static boolean sendSocket(int port, String adiResourceURI) {
		try {
			Socket socket = null;
			socket = new Socket("localhost", port);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(adiResourceURI);
			oos.close();
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			displayError(getFromEngineBundle("error.encounteredError"), getFromGeneratorBundle("broadcast.socket.nohost"));
		} catch (IOException e) {
			displayError(getFromEngineBundle("error.encounteredError"), getFromGeneratorBundle("broadcast.socket.noconnection"));
		}
		return false;
	}

}
