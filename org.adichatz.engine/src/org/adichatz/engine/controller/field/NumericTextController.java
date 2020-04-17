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

import static org.adichatz.engine.common.LogBroker.logError;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.widgets.NumericText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

// TODO: Auto-generated Javadoc
/**
 * The Class NumericTextController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class NumericTextController extends AFieldController {

	/** The child composite. */
	protected Composite childComposite;

	/** The control. */
	protected NumericText numericText;

	/**
	 * Instantiates a new numeric text controller.
	 * 
	 * @param parentController
	 *            the parent controller
	 * @param id
	 *            the id
	 * @param genCode
	 *            the generated code
	 */
	public NumericTextController(String id, IContainerController parentController, final ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.RIGHT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public Text getControl() {
		if (null == numericText)
			return null;
		return numericText.getControl();
	}

	/**
	 * Gets the numeric text.
	 * 
	 * @return the numeric text
	 */
	public NumericText getNumericText() {
		return numericText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#getValue()
	 * 
	 * Return value type is and Object instance (could be Integer, int... following to data model).
	 * 
	 * Use numericText.getValue() to get the original BigDecimal value managed by target model.
	 */
	@Override
	public Object getValue() {
		return convertTargetToModel(numericText.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#setValue(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		try {
			numericText.setValue((Number) convertModelToTarget(value));
		} catch (ParseException e) {
			logError(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		numericText = AdichatzApplication.getInstance().getFormToolkit().createNumericText(parentController.getComposite(), style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#addListeners(boolean, org.adichatz.engine.validation.AValidation)
	 */
	@Override
	public void addListeners() {
		getControl().addModifyListener((e) -> {
			broadcastChange();
		});
		getControl().addDisposeListener((e) -> {
			if (null != fieldBindManager) {
				fieldBindManager.unbind();
			}
		});

		// Add standard listeners after other for a good working
		numericText.addStandardListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#broadcastChange()
	 */
	@Override
	public void broadcastChange() {
		if (numericText.isDoit()) {
			numericText.modifyText();
			BigDecimal newValue;
			if (null == numericText.getValue())
				newValue = null;
			else
				newValue = numericText.getValue().setScale(numericText.getMaximumFractionDigits(), RoundingMode.HALF_UP);
			if (!Utilities.equals(newValue, numericText.getOldValue()))
				if (null != fieldBindManager)
					fieldBindManager.bindTargetToModel();
				else
					getValidation().validate();
			reflow();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#notifyBindListener()
	 */
	public void notifyBindListener() {
		getControl().notifyListeners(SWT.Modify, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.manager.AFieldControlManager#convertModelToTarget(java.lang.Object)
	 */
	@Override
	public Object convertModelToTarget(Object fromObject) {
		if (null == fromObject)
			return null;
		if (fromObject instanceof BigDecimal)
			return fromObject;
		else if (fromObject instanceof Number)
			return new BigDecimal(fromObject.toString());
		else if (fromObject instanceof String)
			return new BigDecimal((String) fromObject);
		// Class is not compatible with NumericText
		throw new RuntimeException(fromObject.getClass().getCanonicalName() + " class is not compatible with numericText");
	}

	@Override
	public void pack() {
	}
}
