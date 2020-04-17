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

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.math.BigDecimal;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.widgets.StarRating;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;

// TODO: Auto-generated Javadoc
/**
 * The Class StarRatingController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class StarRatingController extends AFieldController {

	/** The control. */
	protected StarRating starRating;

	private String numericPattern;

	/**
	 * Instantiates a new star rating controller.
	 * 
	 * @param parentController
	 *            the parent controller
	 * @param id
	 *            the id
	 * @param genCode
	 *            the generated code
	 */
	public StarRatingController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public StarRating getControl() {
		return starRating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#getValue()
	 */
	@Override
	public Object getValue() {
		Object convertedValue = convertTargetToModel(starRating.getRating());
		if (null == fieldBindManager)
			return convertedValue;
		else {
			if (null == convertedValue)
				return null;
			else {
				BigDecimal number;
				if (convertedValue instanceof Integer)
					number = new BigDecimal((Integer) convertedValue);
				else if (convertedValue instanceof Short)
					number = new BigDecimal((Short) convertedValue);
				else if (convertedValue instanceof Long)
					number = new BigDecimal((Long) convertedValue);
				else if (convertedValue instanceof Float)
					number = new BigDecimal((Float) convertedValue);
				else if (convertedValue instanceof Double)
					number = new BigDecimal((Double) convertedValue);
				else if (convertedValue instanceof BigDecimal)
					number = (BigDecimal) convertedValue;
				else {
					EngineTools.openDialog(starRating.getDisplay(), MessageDialog.ERROR,
							getFromEngineBundle("starRating.error.value"),
							getFromEngineBundle("starRating.invalid.cast", convertedValue.getClass().getName()));
					number = new BigDecimal(0);
				}

				Class<?> type = fieldBindManager.getField().getGetMethod().getReturnType();
				if (type.equals(Integer.class) || type.equals(int.class))
					return number.intValue();
				else if (type.equals(Short.class) || type.equals(short.class))
					return number.shortValue();
				else if (type.equals(Long.class) || type.equals(long.class))
					return number.longValue();
				else if (type.equals(Float.class) || type.equals(float.class))
					return number.floatValue();
				else if (type.equals(Double.class) || type.equals(double.class))
					return number.doubleValue();
				else if (type.equals(BigDecimal.class))
					return number;
				EngineTools.openDialog(starRating.getDisplay(), MessageDialog.ERROR, getFromEngineBundle("starRating.error.value"),
						getFromEngineBundle("starRating.invalid.cast", convertedValue.getClass().getName()));
				return 0;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#setValue(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		Object convertedValue = convertModelToTarget(value);
		if (null == convertedValue)
			starRating.setRating(0f);
		else {
			BigDecimal number;
			if (convertedValue instanceof Integer)
				number = new BigDecimal((Integer) convertedValue);
			else if (convertedValue instanceof Short)
				number = new BigDecimal((Short) convertedValue);
			else if (convertedValue instanceof Long)
				number = new BigDecimal((Long) convertedValue);
			else if (convertedValue instanceof Float)
				number = new BigDecimal((Float) convertedValue);
			else if (convertedValue instanceof Double)
				number = new BigDecimal((Double) convertedValue);
			else if (convertedValue instanceof BigDecimal)
				number = (BigDecimal) convertedValue;
			else {
				EngineTools.openDialog(starRating.getDisplay(), MessageDialog.ERROR, getFromEngineBundle("starRating.error.value"),
						getFromEngineBundle("starRating.invalid.cast", convertedValue.getClass().getName()));
				number = new BigDecimal(0);
			}
			float floatValue = number.floatValue();
			int maxNumberOfStars = starRating.getMaxNumberOfStars();
			if (floatValue < 0 || floatValue > maxNumberOfStars) {
				int assumedValue = floatValue < 0 ? 0 : maxNumberOfStars;
				EngineTools.openDialog(starRating.getDisplay(), MessageDialog.ERROR, getFromEngineBundle("starRating.error.value"),
						getFromEngineBundle("starRating.invalid.value", floatValue, maxNumberOfStars, assumedValue));
				floatValue = Float.valueOf(assumedValue);
			}
			starRating.setRating(floatValue);
		}

		// Redraw stars
		Event event = new Event();
		event.gc = new GC(starRating);
		event.type = SWT.Paint;
		event.widget = starRating;
		starRating.notifyListeners(SWT.Paint, event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		starRating = AdichatzApplication.getInstance().getFormToolkit().createStarRating(parentController.getComposite(), style,
				numericPattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#addListeners(boolean, org.adichatz.engine.validation.AValidation)
	 */
	@Override
	public void addListeners() {
		starRating.addModifyListener((e) -> {
			broadcastChange();
		});
		starRating.addDisposeListener((e) -> {
			if (null != fieldBindManager) {
				fieldBindManager.unbind();
				fieldBindManager = null;
			}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#notifyBindListener()
	 */
	public void notifyBindListener() {
		starRating.notifyListeners(SWT.Modify, null);
	}

	public String getNumericPattern() {
		return numericPattern;
	}

	public void setNumericPattern(String numericPattern) {
		this.numericPattern = numericPattern;
	}

}
