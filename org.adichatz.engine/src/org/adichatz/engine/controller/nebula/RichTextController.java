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
package org.adichatz.engine.controller.nebula;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.AFormWindow;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.nebula.widgets.richtext.RichTextEditor;
import org.eclipse.nebula.widgets.richtext.RichTextViewer;
import org.eclipse.nebula.widgets.richtext.toolbar.ToolbarConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class DateTextController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class RichTextController extends AFieldController {

	/** The control. */
	protected RichTextViewer richTextViewer;

	/** The tabFolder when controller is editable (AdiSWT.EDITABLE). */
	protected CTabFolder cTabFolder;

	private Composite container;

	private Button expandButton;

	private AdiFormToolkit toolkit;

	/** The container style. */
	protected int containerStyle = SWT.BORDER;

	/**
	 * Instantiates a new date controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public RichTextController(final String id, IContainerController parentController, final ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.BORDER | SWT.WRAP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public RichTextViewer getControl() {
		return richTextViewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#getValue()
	 */
	@Override
	public String getValue() {
		if (null != richTextViewer.getText() && richTextViewer.getText().length() > 0)
			return (String) convertTargetToModel(richTextViewer.getText());
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#setValue(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		richTextViewer.setText((String) convertModelToTarget(value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		toolkit = AdichatzApplication.getInstance().getFormToolkit();
		container = toolkit.createComposite(parentController.getComposite(), containerStyle);

		container.setLayout(new MigLayout("wrap 2, ins 0", "[grow,fill]0[]", "[grow,fill]"));

		richTextViewer = new RichTextViewer(container, style);
		toolkit.adapt(richTextViewer);
		container.setLayoutData("h 0:64:n, w 0:64:n");

		Composite buttonComposite = toolkit.createComposite(container);
		buttonComposite.setLayout(new MigLayout("wrap 1, ins 0", "[]", "[]push[]"));
		expandButton = toolkit.createButton(buttonComposite, "", SWT.ICON);
		expandButton.setLayoutData("wmin 16");
		expandButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AFormWindow expandWindow = new AFormWindow(expandButton.getShell()) {
					private RichTextEditor richTextEditor;

					@Override
					protected void createFormContent() {
						Composite parent = managedForm.getForm().getBody();
						parent.setLayout(new MigLayout("wrap", "grow,fill", "[grow,fill][]"));
						ToolbarConfiguration toolbarConfiguration = richTextViewer.isEnabled() ? null : new ToolbarConfiguration() {
							protected String getToolbarGroupConfiguration() {
								return "CKEDITOR.config.toolbarGroups = [] ;";
							}
						};
						richTextEditor = new RichTextEditor(parent, toolbarConfiguration, style);
						richTextEditor.setText(richTextViewer.getText());
						richTextEditor.setLayoutData("h 0:n:n, w 0:n:n");
						Composite bottomComposite = toolkit.createComposite(parent);

						if (richTextViewer.isEnabled()) {
							bottomComposite.setLayout(new MigLayout("wrap 2, al right"));
							Button okButton = toolkit.createButton(bottomComposite, JFaceResources.getString("ok"), SWT.PUSH);
							okButton.setLayoutData("sg button");
							okButton.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									richTextViewer.setText(richTextEditor.getText());
									close();
									broadcastChange();
								}
							});
							Button cancelButton = toolkit.createButton(bottomComposite, JFaceResources.getString("cancel"),
									SWT.PUSH);
							cancelButton.setLayoutData("sg button");
							cancelButton.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									close();
								}
							});
						} else {
							bottomComposite.setLayout(new MigLayout("wrap 1, al right"));
							richTextEditor.setEnabled(false);
							Button finishButton = toolkit.createButton(bottomComposite, JFaceResources.getString("finish"),
									SWT.PUSH);
							finishButton.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									close();
								}
							});
						}
					}

					@Override
					public int open() {
						scrolledForm.addControlListener(new ControlListener() {
							@Override
							public void controlResized(ControlEvent e) {
								richTextEditor.getParent().layout();
							}

							@Override
							public void controlMoved(ControlEvent e) {
							}
						});
						return super.open();
					}
				};
				expandWindow.create();
				expandWindow.getShell().setSize(800, 600);
				expandWindow.getShell().setImage(expandButton.getImage());
				expandWindow.open();
			}
		});
		// Scroll is not yet supported in RichTextViewer.
		// Add an incomlete image when all the text is not displayed.
		final Label incompleteLabel = toolkit.createLabel(buttonComposite, "", SWT.NONE);
		incompleteLabel.setImage(toolkit.getRegisteredImage("IMG_INCOMPLETE"));
		incompleteLabel.setToolTipText(getFromEngineBundle("richtext.tooltip.incomplete"));
		incompleteLabel.setLayoutData("al right");

		richTextViewer.addPaintListener((e) -> {
			Point richTextViewerSize = richTextViewer.getSize();
			Point painterViewerSize = richTextViewer.getPainter().getPreferredSize();
			incompleteLabel.setVisible(richTextViewerSize.x < painterViewerSize.x || richTextViewerSize.y < painterViewerSize.y);
		});
	}

	@Override
	public void pack() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#addListeners()
	 */
	@Override
	public void addListeners() {
		richTextViewer.addDisposeListener((e) -> {
			if (null != fieldBindManager)
				fieldBindManager.unbind();
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#broadcastChange()
	 */
	@Override
	public void broadcastChange() {
		if (null != fieldBindManager)
			fieldBindManager.bindTargetToModel();
		else
			getValidation().validate();
		reflow();
	}

	public Composite getComposite() {
		return container;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#notifyBindListener()
	 */
	public void notifyBindListener() {
		richTextViewer.notifyListeners(SWT.Modify, null);
	}

	public int getContainerStyle() {
		return containerStyle;
	}

	public void setContainerStyle(int containerStyle) {
		this.containerStyle = containerStyle;
	}

	@Override
	public void lockEntity(boolean locked) {
		super.lockEntity(locked);
		enableEdit();
	}

	@Override
	public void synchronize() {
		super.synchronize();
		enableEdit();
	}

	public void enableEdit() {
		if (richTextViewer.isEnabled()) {
			expandButton.setImage(toolkit.getRegisteredImage("IMG_EDITOR"));
			expandButton.setToolTipText(getFromEngineBundle("control.tooltip.editor"));
		} else {
			expandButton.setImage(toolkit.getRegisteredImage("IMG_EXPAND"));
			expandButton.setToolTipText(getFromEngineBundle("control.tooltip.expand"));
		}
	}
}
