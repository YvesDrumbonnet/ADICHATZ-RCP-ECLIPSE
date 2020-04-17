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
package org.adichatz.engine.controller.utils;

import org.eclipse.swt.SWT;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiSWT.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class AdiSWT {

	/** The Delete Button. */
	public static int DELETE_BUTTON = 1 << 31; // = SWT.FLIP_TEXT_DIRECTION

	/** The Find Button. */
	public static int FIND_BUTTON = 1 << 30; // = SWT.TRANSPARENT

	/** The Find Button. */
	public static int EDITOR_BUTTON = 1 << 29; // = SWT.DOUBLE_BUFFERED

	/** The DIRECTORY. */
	public static int DIRECTORY = SWT.DBCS; // 1 << 1

	/** The Field Assist. */
	public static int FIELD_ASSIST = 1 << 22; // = SWT.NO_RADIO_GROUP

	/** Style constant for expandable field (see EditableFormText). */
	public static int EXPANDABLE = 1 << 30; // = SWT.TRANSPARENT

	/** Style constant for editable field (see EditableFormText). */
	public static int EDITABLE = 1 << 29; // = SWT.DOUBLE_BUFFERED

	/** The No rotate. */
	public static int NO_TOOL_BAR = 1 << 1;

	/** The No rotate. */
	public static int NO_ROTATE = 1 << 2;

	/** The No anti rotate. */
	public static int NO_ANTI_ROTATE = 1 << 3;

	/** The No fit canvas. */
	public static int NO_FIT_CANVAS = 1 << 4;

	/** The No 100. */
	public static int NO_100 = 1 << 5;

	/** The No zoom out. */
	public static int NO_ZOOM_OUT = 1 << 6; // 1 << 25 is used by SWT.LEFT_TO_RIGHT

	/** The No zoom. */
	public static int NO_ZOOM = 1 << 7;

	/** The No zoom in. */
	public static int NO_ZOOM_IN = 1 << 8;

	/** The No copy/paste buffer (only when image type = DATA). */
	public static int NO_COPY_PASTE_BUFFER = 1 << 9;

	public static int CALENDAR_VALUE = 1 << 10;
}
