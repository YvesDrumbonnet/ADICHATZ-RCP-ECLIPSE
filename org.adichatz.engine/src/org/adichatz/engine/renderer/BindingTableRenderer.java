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
package org.adichatz.engine.renderer;

import java.util.HashSet;
import java.util.Set;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorMessage;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.forms.IFormColors;

// TODO: Auto-generated Javadoc
/**
 * The Class BindingTableRenderer.
 * 
 * @param <T>
 *            the generic type
 */
public class BindingTableRenderer<T> extends StripeTableRenderer<T> {
	/** The currently element for font. */
	private Object fontElement;

	/** The error element. */
	private Object errorElement;

	/** The dirty. */
	private boolean dirty = false;

	/** The error. */
	private boolean error = false;

	/** The identifier of updated beans. */
	private Set<Object> updatedIdentifiers;

	/** The error identifiers. */
	private Set<Object> errorIdentifiers;

	/** The binding service. */
	private ABindingService bindingService;

	/**
	 * Instantiates a new binding table renderer.
	 * 
	 * @param tabularController
	 *            the tabular controller
	 */
	public BindingTableRenderer(ATabularController<T> tabularController, String key) {
		super(tabularController, key);
		this.bindingService = tabularController.getBindingService();
		if (null != AReskinManager.getInstance()) {
			evenBackground = AReskinManager.getInstance().getColor("#adichatz-table-stripe-even", "background-color", null, null);
			oddBackground = AReskinManager.getInstance().getColor("#adichatz-table-stripe-odd", "background-color", null, null);
			evenForeground = AReskinManager.getInstance().getColor("#adichatz-table-stripe-even", "color", null, null);
			oddForeground = AReskinManager.getInstance().getColor("#adichatz-table-stripe-odd", "color", null, null);
		} else {
			evenBackground = AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.H_GRADIENT_END);
			oddBackground = AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(IFormColors.H_GRADIENT_START);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#init(java.util.Set)
	 */
	public void init(Set<IEntity<?>> updatedEntities) {
		if (null == updatedIdentifiers) {
			updatedIdentifiers = new HashSet<Object>();
			errorIdentifiers = new HashSet<Object>();
		} else {
			updatedIdentifiers.clear();
			errorIdentifiers.clear();
		}

		for (IEntity<?> entity : updatedEntities)
			if (entity.getBeanClass().equals(beanClass))
				updatedIdentifiers.add(genCode.getId(entity.getBean()));
		if (null != bindingService)
			for (ErrorMessage errorMessage : bindingService.getErrorMessageMap().getErrorMessages())
				if (errorMessage.getEntity().getBeanClass().equals(beanClass))
					errorIdentifiers.add(genCode.getId(errorMessage.getEntity().getBean()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#getFont(java.lang.Object)
	 */
	public Font getFont(Object element) {
		if (!element.equals(fontElement)) {
			fontElement = element;
			dirty = (null != updatedIdentifiers && updatedIdentifiers.contains(genCode.getId(element)));
		}
		return dirty ? tabularController.getBoldFont() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		if (null == AController.ERROR_COLOR)
			return null;
		if (!element.equals(errorElement)) {
			errorElement = element;
			error = (null != errorIdentifiers && errorIdentifiers.contains(genCode.getId(element)));
		}
		return error ? AController.ERROR_COLOR : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#isRenderedDirty()
	 */
	public boolean isRenderedDirty() {
		return null != tabularController.getBoldFont();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#isRenderedError()
	 */
	public boolean isRenderedError() {
		return null != AController.ERROR_COLOR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#addUpdatedBean(java.lang.Object)
	 */
	public void addUpdatedBean(Object element) {
		updatedIdentifiers.add(genCode.getId(element));
		fontElement = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#removeUpdatedBean(java.lang.Object)
	 */
	public void removeUpdatedBean(Object element) {
		updatedIdentifiers.remove(genCode.getId(element));
		fontElement = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#addErrorBean(java.lang.Object)
	 */
	public void addErrorBean(Object element) {
		errorIdentifiers.add(genCode.getId(element));
		errorElement = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.renderer.BasicTableRenderer#removeErrorBean(java.lang.Object)
	 */
	public void removeErrorBean(Object element) {
		errorIdentifiers.remove(genCode.getId(element));
		errorElement = null;
	}
}
