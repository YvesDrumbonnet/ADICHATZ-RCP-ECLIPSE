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
package org.adichatz.engine.controller.field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;

// TODO: Auto-generated Javadoc
/**
 * The Class DateTextController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class DateTimeController extends AFieldController {

	public static String dateToString(Date date, String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(date);
	}

	public static String calendarTimeToString(Calendar calendar) {
		String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
		String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
		String second = String.format("%02d", calendar.get(Calendar.SECOND));
		return hour.concat(minute).concat(second);
	}

	public static Calendar stringTimeToCalendar(String time) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(2, 4)));
		calendar.set(Calendar.SECOND, Integer.parseInt(time.substring(4)));
		return calendar;
	}

	/** The control. */
	protected DateTime dateTime;

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
	public DateTimeController(final String id, IContainerController parentController, final ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.DROP_DOWN | SWT.BORDER | SWT.DATE | SWT.MEDIUM;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public DateTime getControl() {
		return dateTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#getValue()
	 */
	@Override
	public Object getValue() {
		Calendar calendar = Calendar.getInstance();
		if (0 == (style & SWT.TIME) || 0 != (style & SWT.DATE)) {
			// Date
			calendar.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay());
		} else {
			// Time
			calendar.set(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHours(), dateTime.getMinutes(),
					dateTime.getSeconds());
		}
		return convertTargetToModel(0 != (style & AdiSWT.CALENDAR_VALUE) ? calendar : calendar.getTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#setValue(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (null != value) {
			Object element = convertModelToTarget(value);
			Calendar calendar;
			if (0 != (style & AdiSWT.CALENDAR_VALUE))
				calendar = (Calendar) element;
			else {
				calendar = Calendar.getInstance();
				calendar.setTime((Date) element);
			}
			if (0 == (style & SWT.TIME) || 0 != (style & SWT.DATE)) {
				// Date
				dateTime.setDay(calendar.get(Calendar.DAY_OF_MONTH));
				dateTime.setMonth(calendar.get(Calendar.MONTH));
				dateTime.setYear(calendar.get(Calendar.YEAR));
			} else {
				// Time
				dateTime.setSeconds(calendar.get(Calendar.SECOND));
				dateTime.setMinutes(calendar.get(Calendar.MINUTE));
				dateTime.setHours(calendar.get(Calendar.HOUR_OF_DAY));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		dateTime = AdichatzApplication.getInstance().getFormToolkit().createDateTime(parentController.getComposite(), style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#addListeners(boolean, org.adichatz.engine.validation.AValidation)
	 */
	@Override
	public void addListeners() {
		dateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				broadcastChange();
			}
		});
		dateTime.addDisposeListener((e) -> {
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#notifyBindListener()
	 */
	public void notifyBindListener() {
		dateTime.notifyListeners(SWT.Modify, null);
	}

	@Override
	public String toString(Object bean) {
		DateFormat dateFormat;
		if (0 == (style & SWT.TIME))
			dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		else
			dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		return String.valueOf(dateFormat.format(bean));
	}
}
