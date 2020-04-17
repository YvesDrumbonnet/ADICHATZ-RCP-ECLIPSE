/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 * 
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 * 
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 * 
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.engine.controller.field;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.ARefController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.supplement.ButtonItem;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AComboController.
 */
public abstract class AComboController extends ARefController {

	/** The adichatz combo viewer. */
	protected ComboViewer comboViewer;

	private ButtonItem deleteItem;

	/**
	 * Instantiates a new referenced combo controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public AComboController(String id, IContainerController parentController, final ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.BORDER;
	}

	public abstract Composite getInternalControl();

	protected abstract ComboViewer createComboViewer();

	protected Composite containerComposite;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		boolean readOnly = 0 != (style & SWT.READ_ONLY);
		containerComposite = new Composite(parentController.getComposite(), style) {
			@Override
			public void setEnabled(boolean enabled) {
				checkWidget();
				if (null != deleteItem)
					deleteItem.setEnabled(enabled);
				super.setEnabled(enabled);
			}

			@Override
			public void setBackground(Color color) {
				super.setBackground(color);
			}
		};
		containerComposite.setLayout(new MigLayout("wrap 2, ins 0", "[grow,fill]0[]"));

		comboViewer = createComboViewer();
		childControl = comboViewer.getControl();
		getInternalControl().setForeground(toolkit.getColors().getForeground());
		getInternalControl().setBackground(toolkit.getColors().getBackground());
		if (!readOnly) {
			deleteItem = toolkit.createButtonItem(containerComposite, SWT.ICON);
			deleteItem.setToolTipText(getFromEngineBundle("field.clearValueToolTip"));
			deleteItem.setImage(toolkit.getRegisteredImage("IMG_DELETE"));
			deleteItem.addSelectionListener(() -> {
				getViewer().setSelection(StructuredSelection.EMPTY);
				getControl().notifyListeners(SWT.Selection, null);
			});
			getControl().setLayoutData("grow");
		}
		comboViewer.setContentProvider(new ArrayContentProvider());

		comboViewer.setLabelProvider(labelProvider);
		comboViewer.setInput(getInput());

		comboViewer.setComparer(new IElementComparer() {
			@Override
			public boolean equals(Object a, Object b) {
				if (null == a || null == b)
					return null == a && null == b;
				return compare(a, b);
			}

			@Override
			public int hashCode(Object element) {
				return 0;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AControlController#endLifeCycle()
	 */
	@Override
	public void endLifeCycle() {
		super.endLifeCycle();
		if (null != comboViewer) // comboViewer is null when controller is not valid
			comboViewer.addSelectionChangedListener((e) -> {
				broadcastChange();
			});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#broadcastChange()
	 */
	public void broadcastChange() {
		if (null != dataReferenceManager) {
			StructuredSelection selection = (StructuredSelection) comboViewer.getSelection();
			if (!selection.isEmpty())
				dataReferenceManager.replace(selection.getFirstElement());
			else
				dataReferenceManager.replace(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#getValue()
	 */

	@Override
	public Object getValue() {
		if (null == comboViewer.getSelection() || comboViewer.getSelection().isEmpty())
			return null;
		return convertTargetToModel(((StructuredSelection) comboViewer.getSelection()).getFirstElement());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#setValue(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (!Utilities.equals(value, getValue())) {
			comboViewer.setSelection(null == value ? StructuredSelection.EMPTY : new StructuredSelection(value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public Composite getControl() {
		return containerComposite;
	}

	/**
	 * Gets the combo viewer.
	 * 
	 * @return the combo viewer
	 */
	public ComboViewer getViewer() {
		return comboViewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#addListener(boolean, boolean,
	 * org.adichatz.engine.validation.AValidation)
	 */
	@Override
	public void addListeners() {
		final ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent e) {
				if (null != fieldBindManager)
					fieldBindManager.bindTargetToModel();
				else
					getValidation().validate();
			}
		};
		comboViewer.addSelectionChangedListener(selectionListener);
		getControl().addDisposeListener((e) -> {
			comboViewer.removeSelectionChangedListener(selectionListener);
			if (null != fieldBindManager)
				fieldBindManager.unbind();
		});
	}

	/**
	 * Accept proposal.
	 * 
	 * @param proposal
	 *            the proposal
	 */
	public void acceptProposal(Object proposal) {
		comboViewer.setSelection(new StructuredSelection(new Object[] { proposal }));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#notifyBindListener()
	 */
	public void notifyBindListener() {
		getControl().notifyListeners(SWT.Selection, null);
	}
}
