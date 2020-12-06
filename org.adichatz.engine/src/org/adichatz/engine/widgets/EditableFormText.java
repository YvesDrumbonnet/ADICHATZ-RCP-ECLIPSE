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
package org.adichatz.engine.widgets;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.extra.AFormWindow;
import org.adichatz.engine.extra.FormMessageManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.ui.forms.IMessageManager;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.internal.forms.widgets.FormTextModel;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class DateText.
 * 
 * @author Yves Drumbonnet
 * 
 */
@SuppressWarnings("restriction")
public class EditableFormText extends AFormTextScrolledComposite {

	/** The parse tags. */
	boolean parseTags = true;

	/** The expand URLs. */
	boolean expandURLs = true;

	/** The edit button. */
	private boolean editFlag;

	/** The edit button. */
	private Button editButton;

	/** The edit button. */
	private Button viewButton;

	/** The edit button. */
	private Button expandButton;

	/** The text composite. */
	private Composite textComposite;

	/** The button composite. */
	private Composite buttonComposite;

	/** The form text. */
	private AdiFormText formText;

	private List<FormTextResource> formTextResources;

	private int originalStyle;

	/** The edit text. */
	private Text editText;

	/** The value. */
	private String text;

	/**
	 * Instantiates a new date text.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param toolkit
	 *            the toolkit
	 */
	public EditableFormText(Composite parent, final int style) {
		super(parent, style);
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		this.originalStyle = style;
		toolkit.adapt(this);

		textComposite = toolkit.createComposite(this);
		setContent(textComposite);
		toolkit.adapt(textComposite);

		formText = toolkit.createFormText(textComposite, true);
		formText.setLayoutData("h 20:20, w 20:20");
		toolkit.adapt(formText);
		formText.setHyperlinkSettings(toolkit.getHyperlinkGroup());

		if (expandURLs)
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

		if (0 == (style & AdiSWT.EDITABLE) && 0 == (style & AdiSWT.EXPANDABLE)) {
			// hidemode 3 ==> Invisible components will not participate in the layout
			textComposite.setLayout(new MigLayout("ins 0, hidemode 3", "grow,fill", "grow,fill"));
		} else {
			// hidemode 3 ==> Invisible components will not participate in the layout
			textComposite.setLayout(new MigLayout("wrap 2, ins 0, hidemode 3", "[grow,fill]0[]", "grow,fill"));
			if (0 != (style & AdiSWT.EDITABLE)) {
				editText = toolkit.createText(textComposite, "", SWT.MULTI | SWT.WRAP);
				editText.setLayoutData("h 20:20, w 50:50");
				editText.addModifyListener((e) -> {
					text = editText.getText();
					if (EngineTools.isEmpty(text))
						formText.setText("", false, false);
					else
						try {
							formText.setText(EngineTools.isEmpty(text) ? "" : text, parseTags, expandURLs);
						} catch (IllegalArgumentException e1) {
						}
					notifyListeners(SWT.Modify, null);
				});

				createButtonComposite(toolkit);

				editButton = toolkit.createButton(buttonComposite, "", SWT.IMAGE_BMP);
				editButton.setImage(toolkit.getRegisteredImage("IMG_EDITOR"));
				editButton.setLayoutData("wmax 18, hmax 18, pos 0 0");
				editButton.setToolTipText(getFromEngineBundle("control.tooltip.editor"));
				editButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						editFlag = true;
						redraw();
					}
				});

				viewButton = toolkit.createButton(buttonComposite, "", SWT.ICON);
				viewButton.setImage(toolkit.getRegisteredImage("IMG_VIEW"));
				viewButton.setVisible(false);
				viewButton.setLayoutData("wmax 18, hmax 18, pos 0 0");
				viewButton.setToolTipText(getFromEngineBundle("formText.tooltip.view"));
				viewButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						editFlag = false;
						redraw();
					}
				});
			}
			if (0 != (style & AdiSWT.EXPANDABLE)) {
				createButtonComposite(toolkit);
				expandButton = toolkit.createButton(buttonComposite, "", SWT.ICON);
				expandButton.setImage(toolkit.getRegisteredImage("IMG_EXPAND"));
				expandButton.setLayoutData("wmax 18, hmax 18, pos 0 " + (null == editText ? "0" : "18"));
				expandButton.setToolTipText(getFromEngineBundle("control.tooltip.expand"));
				expandButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						AFormWindow expandWindow = new AFormWindow(expandButton.getShell()) {
							private Button okButton = null;

							@Override
							public void create() {
								super.create();
								managedForm.reflow(true);
								managedForm.getForm().getForm().getMessageManager().update();
							}

							@Override
							protected void createFormContent() {
								managedForm.getForm().setText(EngineTools.getFromEngineBundle("formText.tooltip.view"));
								new FormMessageManager(managedForm.getForm().getForm());
								boolean editable = false;
								Composite parent = managedForm.getForm().getBody();
								parent.setLayout(new MigLayout("wrap", "grow,fill", "[grow,fill][]"));
								int style = SWT.BORDER;
								if (0 != (originalStyle & AdiSWT.EDITABLE)) {
									style = AdiSWT.EDITABLE | SWT.BORDER;
									editable = true;
								}
								final EditableFormText dFormText = new EditableFormText(parent, style);
								dFormText.setEnabled(editable);
								if (null != dFormText.viewButton)
									dFormText.viewButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											managedForm.getForm().setText(EngineTools.getFromEngineBundle("formText.tooltip.view"));
										}
									});
								if (null != dFormText.editButton)
									dFormText.editButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											managedForm.getForm()
													.setText(EngineTools.getFromEngineBundle("control.tooltip.editor"));
										}
									});
								copyResources(dFormText);
								dFormText.addModifyListener((e) -> {
									validate(dFormText);
								});

								// dFormText.setLayoutData("w 680!, h 480!");

								dFormText.setParseTags(parseTags);
								dFormText.setExpandURLs(expandURLs);
								FormText internalFormText = dFormText.getFormText();
								internalFormText.setWhitespaceNormalized(formText.isWhitespaceNormalized());
								internalFormText.setParagraphsSeparated(formText.getParagraphsSeparated());
								internalFormText.setBackground(formText.getBackground());
								internalFormText.setBackgroundImage(formText.getBackgroundImage());
								internalFormText.setBackgroundMode(formText.getBackgroundMode());
								internalFormText.setBounds(formText.getBounds());
								internalFormText.setCursor(formText.getCursor());
								internalFormText.setFont(formText.getFont());

								dFormText.setText(EditableFormText.this.getText());

								Composite bottomComposite = toolkit.createComposite(parent);
								bottomComposite.setLayout(new MigLayout("wrap 2, al right"));

								Button finishButton = null;
								Button cancelButton = null;
								if (editable) {
									okButton = toolkit.createButton(bottomComposite, JFaceResources.getString("ok"), SWT.PUSH);
									okButton.setEnabled(false);
									okButton.setLayoutData("sg button");
									okButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											setText(dFormText.getText());
											close();
										}
									});
									cancelButton = toolkit.createButton(bottomComposite, JFaceResources.getString("cancel"),
											SWT.PUSH);
									cancelButton.setLayoutData("sg button");
									cancelButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											close();
										}
									});
								} else {
									finishButton = toolkit.createButton(bottomComposite, JFaceResources.getString("finish"),
											SWT.PUSH);
									finishButton.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											close();
										}
									});
								}
								dFormText.addModifyListener(new ModifyListener() {

									@Override
									public void modifyText(ModifyEvent e) {
										okButton.setEnabled(true);
									}
								});
								parent.layout();
							}

							private void validate(EditableFormText dFormText) {
								final IMessageManager mmng = managedForm.getMessageManager();
								if (!EngineTools.isEmpty(dFormText.getText())) {
									try {
										FormTextModel formTextModel = new FormTextModel();
										if (dFormText.isParseTags())
											formTextModel.parseTaggedText(dFormText.getText(), dFormText.isExpandURLs());
										else
											formTextModel.parseRegularText(dFormText.getText(), dFormText.isExpandURLs());
										mmng.removeMessage("parseError");
									} catch (IllegalArgumentException e) {
										mmng.addMessage("parseError", EngineTools.getFromEngineBundle("error.parsing.text"), null,
												IMessageProvider.ERROR);
									}
								} else
									mmng.removeMessage("parseError");
								mmng.update();
							}
						};
						expandWindow.create();
						expandWindow.getShell().setSize(800, 600);
						expandWindow.open();
					}
				});

			}
		}
		addControlListener(new ControlListener() {
			@Override
			public void controlResized(ControlEvent e) {
				redraw();
			}

			@Override
			public void controlMoved(ControlEvent e) {
			}
		});
	}

	private void createButtonComposite(AdiFormToolkit toolkit) {
		if (null == buttonComposite) {
			buttonComposite = toolkit.createComposite(textComposite);
			buttonComposite.setLayout(new MigLayout("ins 0, wrap 1"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		if (null != editButton)
			editButton.setEnabled(enabled);
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 * 
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
		try {
			formText.setText(text, parseTags, expandURLs);
		} catch (IllegalArgumentException e) {
			// Exception is assumed by FormTextParseValidator @see FormTextController.addListeners() when property is linked to
			// controller
			logError(EngineTools.getFromEngineBundle("error.parsing.text"));
		}
		if (null != editText)
			editText.setText(EngineTools.isEmpty(text) ? "" : text);
		if (null == text || text.isEmpty())
			contentMinHeight = -1;
		else {
			super.redraw();
			contentMinHeight = formText.computeSize(-1, -1).y;
		}
		reflow(true);
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified when the receiver's text is modified, by sending it one
	 * of the messages defined in the <code>ModifyListener</code> interface.
	 * 
	 * @param listener
	 *            the listener
	 * 
	 * @see ModifyListener
	 * @see #removeModifyListener
	 */
	public void addModifyListener(ModifyListener listener) {
		checkWidget();
		if (listener == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		TypedListener typedListener = new TypedListener(listener);
		addListener(SWT.Modify, typedListener);
	}

	/**
	 * Removes the modify listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeModifyListener(ModifyListener listener) {
		checkWidget();
		if (listener == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		removeListener(SWT.Modify, listener);
	}

	public void redraw() {
		if (null != editText) {
			if (editFlag) {
				editButton.setVisible(false);
				viewButton.setVisible(true);
				formText.setVisible(false);
				editText.setVisible(true);
				editText.setBounds(textComposite.getClientArea());
			} else {
				editButton.setVisible(true);
				viewButton.setVisible(false);
				editText.setVisible(false);
				formText.setVisible(true);
			}
			buttonComposite.layout();
		}
		super.redraw();
		textComposite.layout();
	}

	public FormText getFormText() {
		return formText;
	}

	public boolean isParseTags() {
		return parseTags;
	}

	public void setParseTags(boolean parseTags) {
		this.parseTags = parseTags;
	}

	public boolean isExpandURLs() {
		return expandURLs;
	}

	public void setExpandURLs(boolean expandURLs) {
		this.expandURLs = expandURLs;
	}

	public void addColor(String key, Color color) {
		addFormTextResource(new FormTextResource(key, color));
		formText.setColor(key, color);
	}

	public void addCSSColor(String key, String selector, String property) {
		Color color = AReskinManager.getInstance().getColor(selector, property, null, null);
		addFormTextResource(new FormTextResource(key, color));
		formText.setColor(key, color);
		if (null != AReskinManager.getInstance()) {
			formText.getRunnables().add(() -> {
				formText.setColor(key, AReskinManager.getInstance().getColor(selector, property, null, null));
			});
		}
	}

	public void addToolkitColor(String key, String registryKey) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		Color color = toolkit.getColors().getColor(registryKey);
		addFormTextResource(new FormTextResource(key, color));
		if (null != AReskinManager.getInstance()) {
			formText.getRunnables().add(() -> {
				formText.setColor(key, toolkit.getColors().getColor(registryKey)); // Do not use parent toolkit because it could be disposed
			});
		}
	}

	public void addFont(String key, Font font) {
		addFormTextResource(new FormTextResource(key, font));
		formText.setFont(key, font);
	}

	public void addCSSFont(String key, String selector) {
		Font font = AReskinManager.getInstance().getFont(selector, null);
		addFormTextResource(new FormTextResource(key, font));
		formText.setFont(key, font);
		formText.getRunnables().add(() -> {
			formText.setFont(key, AReskinManager.getInstance().getFont(selector, this));
		});
	}

	public void addJFaceFont(String key, String registryKey) {
		Font font = JFaceResources.getFont(registryKey);
		addFormTextResource(new FormTextResource(key, font));
		formText.setFont(key, font);
		if (null != AReskinManager.getInstance()) {
			formText.getRunnables().add(() -> {
				formText.setFont(key, JFaceResources.getFont(registryKey));
			});
		}
	}

	public void addImage(String key, Image image) {
		formText.setImage(key, image);
		addFormTextResource(new FormTextResource(key, image));
	}

	private void addFormTextResource(FormTextResource formTextResource) {
		if (null == formTextResources)
			formTextResources = new ArrayList<FormTextResource>();
		formTextResources.add(formTextResource);
	}

	public void copyResources(EditableFormText editableFormText) {
		if (null != formTextResources)
			for (FormTextResource formTextResource : formTextResources) {
				if (formTextResource.resource instanceof Color)
					editableFormText.getFormText().setColor(formTextResource.key, (Color) formTextResource.resource);
				else if (formTextResource.resource instanceof Font)
					editableFormText.getFormText().setFont(formTextResource.key, (Font) formTextResource.resource);
				else if (formTextResource.resource instanceof Image)
					editableFormText.getFormText().setImage(formTextResource.key, (Image) formTextResource.resource);
			}
	}

	private class FormTextResource {

		private String key;

		private Object resource;

		public FormTextResource(String key, Object resource) {
			this.key = key;
			this.resource = resource;
		}
	}
}
