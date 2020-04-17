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
package org.adichatz.tool.cacheManager;

import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorViewerSorter.
 * 
 * @author Yves Drumbonnet
 *
 */
public class EditorViewerSorter extends ViewerSorter {

	/** The Constant ASCENDING. */
	private static final int ASCENDING = 0;

	/** The Constant DESCENDING. */
	private static final int DESCENDING = 1;

	/** The column. */
	private int column;

	/** The direction. */
	private int direction;

	/** The column label providers. */
	private List<ColumnLabelProvider> columnLabelProviders;

	/**
	 * Instantiates a new editor viewer sorter.
	 * 
	 * @param columnLabelProviders
	 *            the column label providers
	 */
	public EditorViewerSorter(List<ColumnLabelProvider> columnLabelProviders) {
		this.columnLabelProviders = columnLabelProviders;
	}

	/**
	 * Does the sort. If it's a different column from the previous sort, do an ascending sort. If it's the same column as the last
	 * sort, toggle the sort direction.
	 * 
	 * @param column
	 *            the column
	 */
	public void doSort(int column) {
		if (column == this.column) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.column = column;
			direction = ASCENDING;
		}
	}

	/**
	 * Compares the object for sorting.
	 * 
	 * @param viewer
	 *            the viewer
	 * @param e1
	 *            the e1
	 * @param e2
	 *            the e2
	 * 
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	public int compare(Viewer viewer, Object e1, Object e2) {
		String text1 = columnLabelProviders.get(column).getText(e1);
		String text2 = columnLabelProviders.get(column).getText(e2);
		int rc = 0;
		if (null == text1)
			rc = -1;
		else if (null == text2)
			rc = 1;
		else {
			try {
				rc = getComparator().compare(text1, text2);

				// If descending order, flip the direction
			} catch (IllegalArgumentException e) {
			}
		}
		if (direction == DESCENDING)
			rc = -rc;
		return rc;
	}
}
