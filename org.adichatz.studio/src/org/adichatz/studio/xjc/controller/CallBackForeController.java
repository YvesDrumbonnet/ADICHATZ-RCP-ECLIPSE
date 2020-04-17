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
package org.adichatz.studio.xjc.controller;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.adichatz.common.ejb.AEntityCallback;
import org.adichatz.common.ejb.AEntityCallfore;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.ImageManager;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.AdiControlDecoration;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.xjc.ControllerType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.StudioUtil;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class PluginKeyTextController.
 *
 * @author Yves Arpheuil
 */
public class CallBackForeController extends TextController {

	/** The store. */
	protected static IPreferenceStore store = StudioRcpPlugin.getDefault().getPreferenceStore();

	/** The image manager. */
	protected static ImageManager imageManager = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE)
			.getImageManager();

	/** The class image. */
	protected static Image classImage = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
			"IMG_CLASS_OBJ.png");

	/** The table viewer. */
	private CheckboxTableViewer tableViewer;

	/** The confirm form dialog. */
	private ConfirmFormDialog confirmFormDialog;

	/** The control decoration. */
	private AdiControlDecoration controlDecoration;

	/** The control listener. */
	private Listener controlListener;

	private String superTypeName;

	private Button upButton;

	private Button downButton;

	private List<Object> input;

	/**
	 * Instantiates a new call back fore controller.
	 *
	 * @param id the id
	 * @param parentController the parent controller
	 * @param genCode the gen code
	 */
	public CallBackForeController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/**
	 * Creates the control.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.TextController#createControl()
	 */
	@Override
	public void createControl() {
		super.createControl();
		controlDecoration = new AdiControlDecoration(control, SWT.TOP | SWT.LEFT,
				AdiControlDecoration.CONTENT_PROPOSSAL_DECORATOR_IMAGE);
		controlDecoration.setShowOnlyOnFocus(true);
		String title;
		if ("callforeClassNames".equals(getProperty()) && !(getEntity().getBean() instanceof ControllerType)) {
			superTypeName = AEntityCallfore.class.getName();
			title = "studio.resource.callfore.list";
		} else {
			superTypeName = AEntityCallback.class.getName();
			title = "studio.resource.callback.list";
		}
		controlListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (' ' == event.keyCode && SWT.CTRL == event.stateMask) {
					event.doit = false;
					final Shell shell = Display.getCurrent().getActiveShell();
					IConfirmContent confirmContent = getConfirmContent(null, null);
					confirmFormDialog = new ConfirmFormDialog(shell, AdichatzApplication.getInstance().getFormToolkit(),
							getFromStudioBundle(title), classImage, confirmContent) {
						protected void buttonPressed(int buttonId) {
							if (IDialogConstants.OK_ID == buttonId) {
								boolean first = true;
								StringBuffer valueSB = new StringBuffer("");
								for (Object element : tableViewer.getCheckedElements()) {
									if (first)
										first = false;
									else
										valueSB.append(", ");
									valueSB.append(element);
								}
								getControl().setText(valueSB.toString());
								close();
							} else if (IDialogConstants.CANCEL_ID == buttonId) {
								cancelPressed();
							}
						}
					};
					confirmFormDialog.open();
				}
			}
		};
		control.addListener(SWT.KeyDown, controlListener);
		control.addListener(SWT.Modify, controlListener);
	}

	/**
	 * Dispose proposal.
	 */
	public void disposeProposal() {
		controlDecoration.dispose();
		getControl().removeListener(SWT.KeyDown, controlListener);
	}

	/**
	 * Gets the confirm content.
	 *
	 * @param sectionText the section text
	 * @param columnText the column text
	 * @return the confirm content
	 */
	private IConfirmContent getConfirmContent(final String sectionText, final String columnText) {
		return new IConfirmContent() {
			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[grow,fill]0[]"));
				Composite composite = toolkit.createComposite(parent);
				composite.setLayout(new MigLayout("wrap 2, ins 0", "[grow,fill]10[]"));

				tableViewer = CheckboxTableViewer.newCheckList(composite, SWT.CENTER | SWT.BORDER | SWT.V_SCROLL);
				tableViewer.getTable().setLayoutData("hmin 200, wmin 400");
				// tableViewer.getTable().setHeaderVisible(true);
				tableViewer.addSelectionChangedListener((event) -> {
					enableButtons();
				});
				Composite panelComposite = toolkit.createComposite(composite);
				panelComposite.setLayout(new MigLayout("wrap 1, ins 0", "[]", "[]20[]"));
				panelComposite.setLayoutData("top");
				upButton = toolkit.createButton(panelComposite, null, SWT.ICON);
				upButton.setImage(imageManager.getImageDescriptor("IMG_UP.png").createImage());
				upButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						int index = tableViewer.getTable().getSelectionIndex();
						swap(index, index - 1);
					}
				});

				downButton = toolkit.createButton(panelComposite, null, SWT.ICON);
				downButton.setImage(imageManager.getImageDescriptor("IMG_DOWN.png").createImage());
				downButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						int index = tableViewer.getTable().getSelectionIndex();
						swap(index, index + 1);
					}
				});

				tableViewer.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						return String.valueOf(element);
					}
				});
				tableViewer.setContentProvider(new ArrayContentProvider());

				ScenarioResources scenarioResources = ((XjcBindingService) getBindingService()).getEditor().getScenarioResources();

				input = new ArrayList<>();
				String[] elements = null;
				StringTokenizer tokenizer = new StringTokenizer(getControl().getText(), ",");
				if (0 < tokenizer.countTokens()) {
					elements = new String[tokenizer.countTokens()];
					int i = 0;
					while (tokenizer.hasMoreTokens()) {
						String token = tokenizer.nextToken().trim();
						elements[i++] = token;
						input.add(token);
					}
				}

				List<?> classList = StudioUtil.getHierarchy(scenarioResources, superTypeName);
				for (Object className : classList)
					if (!input.contains(className))
						input.add(className);
				tableViewer.setInput(input);
				if (null != elements)
					tableViewer.setCheckedElements(elements);
				enableButtons();
			}

			private void swap(int index1, int index2) {
				Object element1 = tableViewer.getTable().getItem(index1).getData();
				Object element2 = tableViewer.getTable().getItem(index2).getData();
				input.set(index2, element1);
				input.set(index1, element2);
				tableViewer.refresh();
				enableButtons();
			}

			private void enableButtons() {
				if (StructuredSelection.EMPTY.equals(tableViewer.getSelection())) {
					upButton.setEnabled(false);
					downButton.setEnabled(false);
				} else {
					int index = tableViewer.getTable().getSelectionIndex();
					upButton.setEnabled(0 < index);
					downButton.setEnabled(index < input.size() - 1);
				}
			}

			/**
			 * Ok pressed.
			 * 
			 * @param complementControls
			 *            the complement controls
			 */
			@Override
			public void okPressed(List<Control> complementControls) {
			}

		};
	}
}
