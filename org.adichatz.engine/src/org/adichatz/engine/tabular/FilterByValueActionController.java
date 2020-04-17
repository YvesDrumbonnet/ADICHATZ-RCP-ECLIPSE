/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.engine.tabular;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.action.AAction;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.controller.utils.IPredicate;
import org.adichatz.engine.core.ControllerCore;

// TODO: Auto-generated Javadoc
/**
 * The Class FilterByValueActionController.
 * 
 */
public class FilterByValueActionController<T> extends ActionController {

	private ATabularController<T> tabularController;

	private String searchString;

	@SuppressWarnings("unchecked")
	public FilterByValueActionController(String id, ICollectionController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		MenuManagerController menuManagerController = (MenuManagerController) getParentController();
		tabularController = (ATabularController<T>) menuManagerController.getMenuContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.action.ActionController#createControl()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void createControl() {
		action = new AAction() {
			@Override
			public void runAction() {
				final AColumnController<T> columnController = tabularController.getRightClickColumnController();
				ColumnViewerFilter<T> filter = new ColumnViewerFilter<T>(columnController, searchString);
				IPredicate predicate;
				Class<?> returnType;
				try {
					returnType = columnController.getClass().getMethod("getValue", tabularController.getBeanClass())
							.getReturnType();
					if (returnType.equals(String.class)) {
						filter.setExactString(true);
						filter.setCaseInsensitive(false);
					}
					T selectedObject = tabularController.getSelectedObject();
					@SuppressWarnings("rawtypes")
					Comparable filterValue = (Comparable<?>) columnController.getValue(selectedObject);
					predicate = new IPredicate() {
						@Override
						public boolean apply(Object element) {
							Object value = filter.getColumnController().getValue((T) element);
							return null != value && 0 == filterValue.compareTo(value);
						}
					};
					String text;
					if (FieldTools.isBooleanType(returnType))
						text = getFromEngineBundle(Boolean.valueOf(searchString) ? "query.search.true" : "query.search.false",
								columnController.getText());
					else
						text = EngineTools.getFromEngineBundle("query.search.equal", columnController.getText(), searchString);
					filter.setText(text.concat("."));
					filter.initFilter(filterValue, predicate);
					tabularController.getControl().setRedraw(false);
					tabularController.getControllerPreferenceManager().addColumnFilter(filter);
					tabularController.refreshInput();
					tabularController.getControl().setRedraw(true);
				} catch (SecurityException | NoSuchMethodException e) {
					logError(e);
				}
			}
		};
		action.setText(getFromEngineBundle("query.filter.value"));
		action.setToolTipText(getFromEngineBundle("query.filter.value"));
		action.setImageDescriptor(
				AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_ADD_FILTER"));
		action.setActionController(this);
	}

	@Override
	public boolean isValid() {
		if (!super.isValid() || null == tabularController.getRightClickColumnController())
			return false;
		final AColumnController<T> columnController = tabularController.getRightClickColumnController();
		if (null == columnController)
			return false;
		T selectedObject = tabularController.getSelectedObject();
		if (null == selectedObject)
			return false;
		// Take the string displayed in column. Could be null (e.g. by default boolean displayed only checed image). in this case
		// take the value
		searchString = columnController.getColumnText(selectedObject);
		if (null == searchString)
			searchString = String.valueOf(columnController.getValue(selectedObject));
		return null != columnController.getValue(selectedObject);
	}
}
