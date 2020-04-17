package org.adichatz.engine.intro;

import static org.adichatz.engine.common.LogBroker.logError;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.extra.IIntroPanel;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.AdiFormText;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

public class DefaultIntroPanel implements IIntroPanel {

	private Composite container;

	@Override
	public void displayIntro(AdiFormToolkit toolkit, Composite parent) {
		Display display = parent.getDisplay();

		ScrolledForm form = toolkit.createScrolledForm(parent);
		container = form.getBody();
		container.setLayout(new MigLayout("wrap 1, align center, ins 0", "[]", "[]"));

		FontData fontData = new FontData("H1", 24, SWT.UNDERLINE_DOUBLE);
		Font fontH1 = new Font(display, fontData);
		fontData = new FontData("H2", 16, SWT.BOLD);
		Font fontH2 = new Font(display, fontData);
		fontData = new FontData("H3", 13, SWT.ITALIC);
		Font fontH3 = new Font(display, fontData);
		fontData = new FontData("H3", 10, SWT.BOLD);
		Font textFont = new Font(display, fontData);
		AdiFormText formText = toolkit.createFormText(container, false);
		formText.setHyperlinkSettings(toolkit.getHyperlinkGroup());
		formText.setLayoutData("al center");
		formText.setWhitespaceNormalized(false);
		formText.addToolkitColor("header", IFormColors.TITLE);
		formText.setColor("header", toolkit.getColors().getColor(IFormColors.TITLE));
		formText.setColor("adichatz", new Color(display, 227, 20, 24));
		formText.setFont("H1", fontH1);
		formText.setFont("H2", fontH2);
		formText.setFont("H3", fontH3);
		formText.setFont(textFont);
		formText.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		formText.setImage("adichatzIcon", getImage("adichatzIcon.png"));
		formText.setImage("mailIcon", getImage("mailIcon.png"));
		formText.setImage("forumIcon", getImage("forumIcon.png"));
		formText.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent event) {
				String href = (String) event.getHref();
				if (!EngineTools.isEmpty(href) && Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						if (href.startsWith("mailto://"))
							desktop.mail(new URI(href));
						else
							desktop.browse(new URI(href));
					} catch (IOException | URISyntaxException e) {
						logError(e);
					}
				}
			}
		});
		try {
			InputStream inputStream = this.getClass().getResourceAsStream("DefaultIntro_" + EngineConstants.LANGUAGE + ".txt");
			if (null == inputStream)
				inputStream = this.getClass().getResourceAsStream("DefaultIntro.txt");
			formText.setContents(inputStream, false);
			inputStream.close();
		} catch (IOException e) {
			LogBroker.logError(e);
		}
	}

	private Image getImage(String imageKey) {
		return ImageDescriptor.createFromURL(DefaultIntroPanel.class.getResource(imageKey)).createImage();
	}
}
