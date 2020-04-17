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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.supplement.DateChooser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;

// TODO: Auto-generated Javadoc
/**
 * The Class DateText.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class DateText extends Composite {

	/** The delete button. */
	protected Button deleteButton;

	/** The date chooser button. */
	protected Button dateChoosertButton;

	/** The date text. */
	private Text text;

	/** The value. */
	private Date value;

	/** The pattern. */
	private String pattern = null;

	/** The date format. */
	private DateFormat dateFormat;

	/**
	 * Instantiates a new date text.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public DateText(Composite parent, final int style) {
		super(parent, SWT.BORDER | SWT.TRAIL);

		GridLayout layout = new GridLayout();
		layout.marginBottom = layout.marginTop = layout.marginWidth = layout.marginHeight = layout.verticalSpacing = layout.horizontalSpacing = 0;
		setLayout(layout);

		text = new Text(this, SWT.NONE);
		// dateText.setEditable(false);
		text.setLayoutData(new GridData(SWT.RIGHT));
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				dateChoosertButton.setFocus();
			}
		});

		if (0 == (style & SWT.TIME))
			dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		else
			dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		dateChoosertButton = new Button(this, SWT.ICON);
		dateChoosertButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				DateChooser dateChooser = new DateChooser();
				dateChooser.createContents(dateChoosertButton, style, value, new Runnable() {
					@Override
					public void run() {
						setValue(dateChooser.getCalendar().getTime());
						pack();
						notifyListeners(SWT.Modify, null);
						DateText.this.layout();
					}
				});
			}
		});

		if (0 == (style & AdiSWT.DELETE_BUTTON)) {
			deleteButton = null;
			layout.numColumns = 2;
		} else {
			layout.numColumns = 3;
			deleteButton = new Button(this, SWT.IMAGE_GIF);
			deleteButton.setToolTipText(getFromEngineBundle("field.clearValueToolTip"));
			deleteButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					setValue(null);
					notifyListeners(SWT.Modify, null);
				}
			});
		}
		setImages();

		pack();
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

	public Button getDateChoosertButton() {
		return dateChoosertButton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Composite#setFocus()
	 */
	@Override
	public boolean setFocus() {
		return dateChoosertButton.setFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		text.setEnabled(enabled);
		if (null != deleteButton)
			deleteButton.setEnabled(enabled);
		dateChoosertButton.setEnabled(enabled);
		setImages();
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public Date getValue() {
		return value;
	}

	/**
	 * Sets the images.
	 */
	protected void setImages() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		GridData buttonLayout = new GridData();
		if ("win32".equals(SWT.getPlatform())) {
			ImageData id = toolkit.getRegisteredImage("IMG_DELETE").getImageData();
			buttonLayout.widthHint = id.width + 4;
			buttonLayout.heightHint = id.height + 4;
		}
		buttonLayout.grabExcessVerticalSpace = true;
		if (null != deleteButton) {
			deleteButton.setImage(toolkit.getRegisteredImage("IMG_DELETE"));
			deleteButton.setLayoutData(buttonLayout);
		}
		dateChoosertButton.setImage(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_DATECHOOSER"));
		dateChoosertButton.setLayoutData(buttonLayout);
	}

	/**
	 * Sets a new <code>Date</code> value.
	 * 
	 * @param value
	 *            new date value
	 */
	public void setValue(Date value) {
		this.value = value;
		text.setText((null == value) ? "" : dateFormat.format(value));
		layout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Control#pack()
	 */
	@Override
	public void pack() {
		text.pack();
		super.pack();
	}

	/**
	 * Sets the pattern.
	 * 
	 * @param pattern
	 *            the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
		try {
			this.dateFormat = new SimpleDateFormat(pattern);
		} catch (java.lang.IllegalArgumentException e) {
			logError(e);
		}
	}

	/**
	 * Gets the pattern.
	 * 
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
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
}
