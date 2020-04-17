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
package org.adichatz.studio.xjc.controller;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.ComboController;
import org.adichatz.engine.core.ControllerCore;
import org.eclipse.jface.viewers.LabelProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class DialectComboController.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DialectComboController extends ComboController {

	/** The values. */
	private List values;

	/** The displayed values. */
	private List displayedValues;

	/**
	 * Instantiates a new dialect combo controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public DialectComboController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		super.createControl();
		labelProvider = new LabelProvider() {
			public String getText(Object element) {
				return (String) getDisplayedValues().get(getValues().indexOf(element));
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ARefController#getValues()
	 */
	@Override
	public List getValues() {
		if (null == values) {
			values = new ArrayList();
			values.add("org.hibernate.dialect.DB2Dialect");
			values.add("org.hibernate.dialect.DB2400Dialect");
			values.add("org.hibernate.dialect.DB2390Dialect");
			values.add("org.hibernate.dialect.PostgreSQLDialect");
			values.add("org.hibernate.dialect.MySQLDialect");
			values.add("org.hibernate.dialect.MySQLInnoDBDialect");
			values.add("org.hibernate.dialect.MySQLMyISAMDialect");
			values.add("org.hibernate.dialect.OracleDialect");
			values.add("org.hibernate.dialect.Oracle9Dialect");
			values.add("org.hibernate.dialect.SybaseDialect");
			values.add("org.hibernate.dialect.SybaseAnywhereDialect");
			values.add("org.hibernate.dialect.SQLServerDialect");
			values.add("org.hibernate.dialect.SAPDBDialect");
			values.add("org.hibernate.dialect.InformixDialect");
			values.add("org.hibernate.dialect.HSQLDialect");
			values.add("org.hibernate.dialect.IngresDialect");
			values.add("org.hibernate.dialect.ProgressDialect");
			values.add("org.hibernate.dialect.MckoiDialect");
			values.add("org.hibernate.dialect.InterbaseDialect");
			values.add("org.hibernate.dialect.PointbaseDialect");
			values.add("org.hibernate.dialect.FrontbaseDialect");
			values.add("org.hibernate.dialect.FirebirdDialect");
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ARefController#getDisplayedValues()
	 */
	@Override
	public List getDisplayedValues() {
		if (null == displayedValues) {
			displayedValues = new ArrayList();
			displayedValues.add("DB2 - org.hibernate.dialect.DB2Dialect");
			displayedValues.add("DB2 AS/400 - org.hibernate.dialect.DB2400Dialect");
			displayedValues.add("DB2 OS390 - org.hibernate.dialect.DB2390Dialect");
			displayedValues.add("PostgreSQL - org.hibernate.dialect.PostgreSQLDialect");
			displayedValues.add("MySQL - org.hibernate.dialect.MySQLDialect");
			displayedValues.add("MySQL with InnoDB - org.hibernate.dialect.MySQLInnoDBDialect");
			displayedValues.add("MySQL with MyISAM - org.hibernate.dialect.MySQLMyISAMDialect");
			displayedValues.add("Oracle (any version) - org.hibernate.dialect.OracleDialect");
			displayedValues.add("Oracle 9i/10g - org.hibernate.dialect.Oracle9Dialect");
			displayedValues.add("Sybase - org.hibernate.dialect.SybaseDialect");
			displayedValues.add("Sybase Anywhere - org.hibernate.dialect.SybaseAnywhereDialect");
			displayedValues.add("Microsoft SQL Server - org.hibernate.dialect.SQLServerDialect");
			displayedValues.add("SAP DB - org.hibernate.dialect.SAPDBDialect");
			displayedValues.add("Informix - org.hibernate.dialect.InformixDialect");
			displayedValues.add("HypersonicSQL - org.hibernate.dialect.HSQLDialect");
			displayedValues.add("H2 - org.hibernate.dialect.H2Dialect");
			displayedValues.add("Ingres - org.hibernate.dialect.IngresDialect");
			displayedValues.add("Progress - org.hibernate.dialect.ProgressDialect");
			displayedValues.add("Mckoi SQL - org.hibernate.dialect.MckoiDialect");
			displayedValues.add("Interbase - org.hibernate.dialect.InterbaseDialect");
			displayedValues.add("Pointbase - org.hibernate.dialect.PointbaseDialect");
			displayedValues.add("FrontBase - org.hibernate.dialect.FrontbaseDialect");
			displayedValues.add("Firebird - org.hibernate.dialect.FirebirdDialect");
		}
		return displayedValues;
	}
}
