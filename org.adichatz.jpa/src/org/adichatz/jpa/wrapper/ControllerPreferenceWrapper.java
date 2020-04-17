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
package org.adichatz.jpa.wrapper;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;

import java.util.StringTokenizer;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.XmlEqualsBuilder;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ATabularController.METHOD_NAME;
import org.adichatz.engine.wrapper.IMarshalledWrapper;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.xjc.ColumnPreferencesType;
import org.adichatz.jpa.xjc.ControllerPreferenceType;
import org.adichatz.jpa.xjc.FiltersType;
import org.eclipse.swt.widgets.Item;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryPreferenceWrapper.
 *
 * @param <T>
 *            the generic type
 */
public class ControllerPreferenceWrapper<T> extends ControllerPreferenceType implements IMarshalledWrapper {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -637390632837546134L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return XmlEqualsBuilder.reflectionEquals(this, obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.IMarshalledWrapper#postUnmarshal()
	 */
	@Override
	public Object postUnmarshal() {
		return clone(null);
	}

	/**
	 * Clone.
	 *
	 * @param userControllerPreference
	 *            the preference passed User preferences
	 * @return the controller preference wrapper clone
	 */
	public ControllerPreferenceWrapper<T> clone(ControllerPreferenceWrapper<T> userControllerPreference) {
		ControllerPreferenceWrapper<T> clone = new ControllerPreferenceWrapper<T>();
		clone.columnPreferences = (ColumnPreferencesType) EngineTools.cloneSerializable(columnPreferences);
		clone.filters = (FiltersType) EngineTools.cloneSerializable(filters);
		clone.id = id;
		if (null != columnPreferences) {
			clone.setColumnPreferences((ColumnPreferencesType) EngineTools.cloneSerializable(columnPreferences));
		}
		clone.columnOrder = columnOrder;
		clone.statusBarKey = statusBarKey;
		clone.tableRendererKey = tableRendererKey;
		return clone;
	}

	/**
	 * Gets the tabular column order.
	 *
	 * @return the tabular column order
	 */
	public int[] getTabularColumnOrder(ATabularController<T> tabularController) {
		int columnCount;
		if (EngineTools.isEmpty(columnOrder))
			return null;
		else
			try {
				columnCount = (Integer) tabularController.invokeControlMethod(METHOD_NAME.getColumnCount);
				int[] itemOrder = new int[columnCount];
				StringTokenizer tokenizer = new StringTokenizer(columnOrder, ",");
				int sorterIndex = 0;
				while (tokenizer.hasMoreElements()) {
					String token = tokenizer.nextToken().trim();
					int value = -1;
					try {
						value = Integer.parseInt(token);
					} catch (NumberFormatException nfe) {
						int columnIndex = 0;
						for (Item item : (Item[]) tabularController.invokeControlMethod(METHOD_NAME.getItems)) {
							if (token.equals(item.getText())) {
								value = columnIndex;
								break;
							}
						}
						if (-1 == value) {
							itemOrder = null;
							logError("Column order <" + columnOrder + "> is not a valid clause!");
							return null;
						}
					}
					itemOrder[sorterIndex++] = value;
				}
				if (sorterIndex == columnCount) {
					return itemOrder;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				columnCount = (Integer) tabularController.invokeControlMethod(METHOD_NAME.getColumnCount);
			}

		StringBuffer assumedSB = new StringBuffer();
		for (int i = 0; i < columnCount; i++)
			assumedSB.append(", ").append(i);
		logWarning(JPAUtil.getFromJpaBundle("preference.invalid.column.order", columnOrder, assumedSB.toString().substring(2)));
		return null;
	}

}
