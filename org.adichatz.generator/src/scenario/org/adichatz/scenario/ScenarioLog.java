package org.adichatz.scenario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.eclipse.jface.preference.IPreferenceStore;

public class ScenarioLog extends AAdiLogger {
	private static BufferedWriter errorLog;

	public static void write(String message) {
		try {
			if (null == errorLog)
				errorLog = new BufferedWriter(new FileWriter("scenarioErrorLog.txt"));
			errorLog.write(message.concat("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		if (null != errorLog)
			try {
				errorLog.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public ScenarioLog() {
		new LogBroker(this);
	}

	@Override
	public void debug(Throwable throwable) {
		if (debugEnabled)
			throwable.printStackTrace();
	}

	@Override
	public void initialize(IPreferenceStore store) {
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
	public void error(Throwable throwable, String message) {
		String errorString = null == throwable ? "" : "\n" + EngineTools.getErrorString(throwable);
		message = null == message ? errorString : message + errorString;
		System.err.println(prefix("ERROR").append(message));
		write(message);
		if (null != throwable)
			throwable.printStackTrace();
	}

	@Override
	public void warn(String message) {
		System.out.println(prefix("WARNING").append(message));
	}

	@Override
	public void info(String message) {
		System.out.println(prefix("INFO").append(message));
	}

	@Override
	public void trace(String message) {
		System.out.println(prefix("TRACE").append(message));
	}

	@Override
	public void flush() {
	}
}
