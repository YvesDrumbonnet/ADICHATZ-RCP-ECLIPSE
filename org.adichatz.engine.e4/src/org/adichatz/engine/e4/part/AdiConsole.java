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
package org.adichatz.engine.e4.part;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.controller.utils.IPostReskinListener;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiConsole.
 * 
 * @author Yves Drumbonnet
 */
public class AdiConsole {

	public static Set<String> AVOIDED_MESSAGES = new HashSet<>();

	/** The THIS. */
	private static AdiConsole THIS;

	/** The print thread. */
	private Thread printThread;

	/** The system out. */
	private PrintStream systemOut;

	/** The system err. */
	private PrintStream systemErr;

	/** The scrolllock. */
	private boolean scrolllock;

	/** The disposed. */
	private boolean disposed = false;

	/** The message colors. */
	private List<MessageColor> messageColors = new ArrayList<MessageColor>();

	private Color outColor;

	private Color errColor;

	private boolean nullStream;

	/**
	 * Gets the single instance of AdiConsole.
	 * 
	 * @return single instance of AdiConsole
	 */
	public static AdiConsole getInstance() {
		return THIS;
	}

	/** The console styled text. */
	private StyledText consoleStyledText;

	private Display display;

	/**
	 * Creates the control.
	 * 
	 * @param parent
	 *            the parent
	 */
	@PostConstruct
	public void createControl(Composite parent) {
		THIS = this;
		display = parent.getDisplay();
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(EngineTools.getModifiedFont(composite.getFont(), SWT.BOLD));
		composite.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
		consoleStyledText = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		consoleStyledText
				.setBackground(AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class).getColors().getBackground());
		consoleStyledText.setLayoutData("wmin 50, hmin 50");
		consoleStyledText.setText("");
		consoleStyledText.addDisposeListener((e) -> {
			disposed = true;
		});
		consoleStyledText.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if ('a' == e.keyCode && SWT.CTRL == e.stateMask) {
					consoleStyledText.selectAll();
				}
			}
		});
		systemOut = System.out;
		systemErr = System.err;
		initStreams();
		LogBroker.getLogger().flush();
		AReskinManager reskinManager = AReskinManager.getInstance();
		if (null != reskinManager) {
			reskinManager.addReskinListener(new IPostReskinListener() {
				@Override
				public void postReskin() {
					initStreams();
					AController.ERROR_COLOR = reskinManager.getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR,
							"error-foreground-color", null, null);
				}
			}, null);
		}
	}

	/**
	 * Dispose.
	 */
	@PreDestroy
	private void dispose() {
		disposed = true;
	}

	/**
	 * Prints the.
	 * 
	 * @param message
	 *            the message
	 * @param color
	 *            the color
	 * @param addLF
	 *            the add lf
	 */
	public void print(StringBuffer message, Color color, boolean addLF) {
		for (String avoidedMessage : AVOIDED_MESSAGES)
			if (-1 < message.indexOf(avoidedMessage))
				return;
		messageColors.add(new MessageColor(message, color, addLF));
		if (null == printThread) {
			printThread = new PrintThread();
			printThread.start();
		}
	}

	/**
	 * Clear.
	 */
	public void clear() {
		consoleStyledText.setText("");
	}

	public void initStreams() {
		nullStream = false;
		Color oldOutColor = outColor;
		Color oldErrColor = errColor;
		boolean changeColors = null != oldOutColor && null != oldErrColor;
		if (null == AReskinManager.getInstance()) {
			outColor = display.getSystemColor(SWT.COLOR_BLUE);
			errColor = display.getSystemColor(SWT.COLOR_RED);
		} else {
			outColor = AReskinManager.getInstance().getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "info-foreground-color",
					null, null);
			errColor = AReskinManager.getInstance().getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "error-foreground-color",
					null, null);
		}

		/** The out console stream. */
		MessageOutputStream outConsoleStream = new MessageOutputStream(outColor);
		/** The err console stream. */
		MessageOutputStream errConsoleStream = new MessageOutputStream(errColor);

		System.setOut(new PrintStream(outConsoleStream));
		System.setErr(new PrintStream(errConsoleStream));

		consoleStyledText.setForeground(outColor);
		StyleRange[] styleRanges = consoleStyledText.getStyleRanges();
		for (StyleRange styleRange : styleRanges) {
			if (changeColors) {
				if (styleRange.foreground.equals(oldOutColor))
					styleRange.foreground = outColor;
				if (styleRange.foreground.equals(oldErrColor))
					styleRange.foreground = errColor;
			}
			consoleStyledText.setStyleRange(styleRange);
		}
	}

	public void initNullStreams() {
		nullStream = true;
		OutputStream nullOutputStream = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		};
		System.setOut(new PrintStream(nullOutputStream));
		System.setErr(new PrintStream(nullOutputStream));
	}

	public Color getErrColor() {
		return errColor;
	}

	public Color getOutColor() {
		return outColor;
	}

	/*
	 * I N N E R - C L A S S E
	 */

	/**
	 * The Class MessageColor.
	 * 
	 * @author Yves Drumbonnet
	 */
	private class MessageColor {

		/** The message. */
		StringBuffer message;

		/** The color. */
		Color color;

		/** The add lf. */
		private boolean addLF;

		/**
		 * Instantiates a new message color.
		 * 
		 * @param message
		 *            the message
		 * @param color
		 *            the color
		 * @param addLF
		 *            the add lf
		 */
		public MessageColor(StringBuffer message, Color color, boolean addLF) {
			this.message = message;
			this.color = color;
			this.addLF = addLF;
		}
	}

	/**
	 * The Class PrintThread.
	 * 
	 * @author Yves Drumbonnet
	 */
	private class PrintThread extends Thread {

		/** The current length. */
		int currentLength;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			if (!disposed)
				consoleStyledText.getDisplay().asyncExec(() -> {
					while (true) {
						currentLength = messageColors.size();
						synchronized (THIS) {
							printMessage();
							break;
						}
					}
				});
			else { // process message after having disposed console
				System.setOut(systemOut);
				System.setErr(systemErr);
				printMessage();
			}
		}

		/**
		 * Prints the message.
		 */
		private void printMessage() {
			if (!disposed && currentLength == messageColors.size()) {
				for (MessageColor messageColor : messageColors) {
					if (!nullStream) {
						Color color = messageColor.color;
						StringBuffer message = messageColor.message;
						StyleRange styleRange = null;
						if (null != color)
							styleRange = new StyleRange(consoleStyledText.getText().length(), message.length(), color, null);

						if (messageColor.addLF)
							message.append("\n");
						consoleStyledText.append(message.toString());
						if (null != color)
							consoleStyledText.setStyleRange(styleRange);
					}
				}
				messageColors.clear();
				if (!scrolllock)
					consoleStyledText.setTopIndex(consoleStyledText.getLineCount());
				stopIt();
			} else {
				System.setErr(systemErr);
				for (MessageColor messageColor : messageColors.toArray(new MessageColor[messageColors.size()]))
					System.err.println(messageColor.message);
			}
		}

		/**
		 * Stop it.
		 */
		private void stopIt() {
			printThread = null;
		}
	}

	/**
	 * The Class MessageOutputStream.
	 * 
	 * @author Yves Drumbonnet
	 */
	class MessageOutputStream extends OutputStream {

		/** The color. */
		Color color;

		/**
		 * Instantiates a new message output stream.
		 * 
		 * @param color
		 *            the color
		 */
		MessageOutputStream(Color color) {
			this.color = color;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.OutputStream#write(int)
		 */
		@Override
		public void write(final int b) throws IOException {
			print(new StringBuffer((char) b), color, false);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.OutputStream#write(byte[], int, int)
		 */
		public void write(byte[] bytes, int off, int len) throws IOException {
			print(new StringBuffer(new String(Arrays.copyOfRange(bytes, off, len))), color, false);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.OutputStream#write(byte[])
		 */
		@Override
		public void write(byte[] b) throws IOException {
			write(b, 0, b.length);
		}
	}

	/**
	 * Scroll lock.
	 */
	public void scrollLock() {
		scrolllock = !scrolllock;
	}
}
