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

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class RadioGroup.
 * 
 * @author Yves Drumbonnet
 *
 */
public class RadioGroup extends Composite {

	/** The radio buttons. */
	private List<Button> radioButtons = new ArrayList<Button>();

	/** The widget selected listeners. */
	private List<SelectionListener> widgetSelectedListeners = new ArrayList<SelectionListener>();

	private IElementComparer comparer;

	private int defaultSelection = -1;

	private boolean doit = true;

	private AdiFormToolkit toolkit;

	/**
	 * Constructs an instance of this widget given an array of Button objects to wrap. The Button objects must have been created
	 * with the SWT.RADIO style bit set, and they must all be in the same Composite.
	 * 
	 * @param parent
	 *            the parent
	 */
	public RadioGroup(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(new MigLayout());
		toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		toolkit.adapt(this);
	}

	/**
	 * Returns the object corresponding to the currently-selected radio button or null if no radio button is selected.
	 * 
	 * @return the object corresponding to the currently-selected radio button or null if no radio button is selected.
	 */
	public Object getSelection() {
		for (Button radioButton : radioButtons) {
			if (true == radioButton.getSelection())
				return radioButton.getData();
		}
		return null;
	}

	/**
	 * Sets the selected radio button to the radio button whose model object equals() the object specified by newSelection. If
	 * !newSelection.equals() any model object managed by this radio group, deselects all radio buttons.
	 * 
	 * @param newSelection
	 *            A model object corresponding to one of the model objects associated with one of the radio buttons.
	 */
	public void setSelection(Object newSelection) {
		deselectAll();
		if (null == comparer) {
			for (Button radioButton : radioButtons)
				if (radioButton.getData().equals(newSelection))
					radioButton.setSelection(true);
		} else {
			for (Button radioButton : radioButtons)
				if (comparer.equals(radioButton.getData(), newSelection))
					radioButton.setSelection(true);
		}
	}

	/**
	 * Fire widget selected event.
	 * 
	 * @param event
	 *            the selected event
	 */
	protected void fireWidgetSelectedEvent(SelectionEvent event) {
		if (doit)
			for (Iterator<SelectionListener> listenersIter = widgetSelectedListeners.iterator(); listenersIter.hasNext();) {
				SelectionListener listener = listenersIter.next();
				listener.widgetSelected(event);
			}
	}

	/**
	 * Adds the selection listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addSelectionListener(SelectionListener listener) {
		widgetSelectedListeners.add(listener);
	}

	/**
	 * Removes the selection listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeSelectionListener(SelectionListener listener) {
		widgetSelectedListeners.remove(listener);
	}

	/**
	 * Deselects all selected items in the receiver.
	 */
	public void deselectAll() {
		for (Button radioButton : radioButtons)
			radioButton.setSelection(false);
		if (-1 != defaultSelection) {
			doit = false;
			radioButtons.get(defaultSelection).setSelection(true);
			doit = true;
		}
	}

	/**
	 * Sets the buttons.
	 * 
	 * @param valueItems
	 *            the new buttons
	 */
	public void setButtons(@SuppressWarnings("rawtypes") Collection values, LabelProvider labelProvider) {
		for (Object value : values) {
			final Button radioButton = new Button(this, SWT.RADIO);
			toolkit.adapt(radioButton, false, false);
			radioButton.setText(labelProvider.getText(value));
			radioButton.setData(value);
			radioButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					if (doit)
						if (event.type == SWT.Selection) {
							SelectionEvent selectionEvent = new SelectionEvent(event);
							selectionEvent.data = radioButton;
							fireWidgetSelectedEvent(selectionEvent);
						}
				}
			});
			radioButtons.add(radioButton);
		}
	}

	/**
	 * @param comparer
	 *            the comparer to set
	 */
	public void setComparer(IElementComparer comparer) {
		this.comparer = comparer;
	}

	public void setDefaultSelection(int defaultSelection) {
		this.defaultSelection = defaultSelection;
		if (null == getSelection())
			try {
				doit = false;
				radioButtons.get(defaultSelection).setSelection(true);
				doit = true;
			} catch (java.lang.IndexOutOfBoundsException e) {
				logError(defaultSelection + " is not a valid range for radio buttons!");
				defaultSelection = -1;
			}
	}
}
