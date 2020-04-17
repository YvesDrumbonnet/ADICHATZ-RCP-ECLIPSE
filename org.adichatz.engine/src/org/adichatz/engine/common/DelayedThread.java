package org.adichatz.engine.common;

import static org.adichatz.engine.common.LogBroker.logTrace;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.adichatz.common.ejb.MultiKey;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class DelayedThread extends Thread {
	private static HashMap<MultiKey, DelayedThread> delayedThreadMap = new HashMap<>();

	private static LoopThread loopThread;

	public static void newDelayedThread(MultiKey multiKey, int delay, Runnable runnable) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			if (null == loopThread) {
				loopThread = new LoopThread(((Control) multiKey.getKey(0)).getDisplay());
				loopThread.start();
			}
			loopThread.add(multiKey, delay, runnable);
			// if (!loopThread.isAlive())
			// loopThread.start();
		} finally {
			lock.unlock();
		}
	}

	private boolean wait;

	private boolean removed;

	private MultiKey multiKey;

	private Runnable runnable;

	private int millis;

	private DelayedThread(MultiKey multiKey, int millis, Runnable runnable) {
		this.multiKey = multiKey;
		this.runnable = runnable;
		this.millis = millis;
		((Control) multiKey.getKey(0)).addDisposeListener((e) -> {
			removed = true;
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public synchronized void run() {
		while (!removed) {
			try {
				logTrace("" + "start wait: " + multiKey.getString(1));
				sleep(millis);
			} catch (InterruptedException e) {
			}
			if (wait) {
				logTrace("wait:" + multiKey.getString(1));
				wait = false;
			} else {
				logTrace("Launch: " + multiKey.getString(1));
				Control control = (Control) multiKey.getKey(0);
				if (!control.isDisposed())
					control.getDisplay().syncExec(new Runnable() {
						@Override
						public void run() {
							runnable.run();
						}
					});
				delayedThreadMap.remove(multiKey);
				removed = true;
			}
		}
	}

	static class LoopThread extends Thread {
		private boolean disposed;

		private Display display;

		private LoopThread(Display display) {
			this.display = display;
		}

		private synchronized void add(MultiKey multiKey, int delay, Runnable runnable) {
			DelayedThread delayedThread = delayedThreadMap.get(multiKey);
			if (!delayedThreadMap.containsKey(multiKey)) {
				delayedThread = new DelayedThread(multiKey, delay, runnable);
				delayedThreadMap.put(multiKey, delayedThread);
				delayedThread.start();
			} else {
				delayedThread.wait = true;
			}
			loopThread.notify();
		}

		@Override
		public synchronized void run() {
			while (!disposed && !display.isDisposed()) {
				try {
					display.asyncExec(new Runnable() {
						public synchronized void run() {
							if (delayedThreadMap.isEmpty())
								disposed = true;
						} // END run()
					}); // END new Runnable
					if (!disposed)
						wait();
				} catch (InterruptedException e) {
					LogBroker.logError(e);
				} // END try
			} // END while (!disposed)
		}

	}
}
