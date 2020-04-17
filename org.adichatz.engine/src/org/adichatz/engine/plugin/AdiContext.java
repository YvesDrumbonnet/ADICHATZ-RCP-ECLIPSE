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
package org.adichatz.engine.plugin;

import java.util.LinkedHashSet;
import java.util.Set;

import org.adichatz.engine.core.AXmlTreeCore;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.RootCore;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiContext.
 * 
 * The Adichatz Context containing a map of parameters for the environment
 */
public class AdiContext {

	// When adding child controller, rank can be specified using CHILD_INDEX (see ACollectionController#addChildController)
	public static Integer CHILD_INDEX;

	protected Set<String> openParts = new LinkedHashSet<String>();

	/** The parameters forMap<String, Object> params the form. */
	protected ParamMap paramMap;

	/** The parent context. */
	private AdiContext parentContext;

	/** The tree gencode corresponding to a xml Tree as an IncludeTree or PartTree. */
	private AXmlTreeCore xmlTreeGenCode;

	/** The root gencode corresponding to a databinding service container. */
	private RootCore rootGenCode;

	/**
	 * Instantiates a new adi context.
	 * 
	 * @param genCode
	 *            the gen code
	 * @param parentContext
	 *            the parent context
	 * @param paramMap
	 *            the param map
	 */
	private AdiContext(ControllerCore genCode, AdiContext parentContext, ParamMap paramMap) {
		this.parentContext = parentContext;
		this.rootGenCode = parentContext.getRootCore();
		this.xmlTreeGenCode = genCode instanceof AXmlTreeCore ? (AXmlTreeCore) genCode : parentContext.getXmlTreeGenCode();
		this.paramMap = null == paramMap ? parentContext.paramMap : paramMap;
	}

	/**
	 * Instantiates a new adi context.
	 * 
	 * @param genCode
	 *            the gen code
	 * @param paramMap
	 *            the param map
	 */
	private AdiContext(ControllerCore genCode, ParamMap paramMap) {
		this.rootGenCode = (RootCore) genCode;
		this.xmlTreeGenCode = (RootCore) genCode;
		this.paramMap = null == paramMap ? new ParamMap() : paramMap;
	}

	/**
	 * Adds the context.
	 * 
	 * @param genCode
	 *            the gen code
	 * @param parentContext
	 *            the parent context
	 * @param paramMap
	 *            the param map
	 * @return the adi context
	 */
	public static AdiContext addContext(ControllerCore genCode, AdiContext parentContext, ParamMap paramMap) {
		return new AdiContext(genCode, parentContext, paramMap);
	}

	/**
	 * Adds the context.
	 * 
	 * @param genCode
	 *            the gen code
	 * @param paramMap
	 *            the param map
	 * @return the adi context
	 */
	public static AdiContext addContext(ControllerCore genCode, ParamMap paramMap) {
		return new AdiContext(genCode, paramMap);
	}

	/**
	 * Gets the xml tree generated code.
	 * 
	 * @return the xml tree generated code
	 */
	public AXmlTreeCore getXmlTreeGenCode() {
		return xmlTreeGenCode;
	}

	/**
	 * Sets the xml tree generated code.
	 * 
	 * @param xmlTreeGenCode
	 *            the new xml tree generated code
	 */
	public void setXmlTreeGenCode(AXmlTreeCore xmlTreeGenCode) {
		this.xmlTreeGenCode = xmlTreeGenCode;
	}

	/**
	 * Gets the root core.
	 * 
	 * @return the root core
	 */
	public RootCore getRootCore() {
		return rootGenCode;
	}

	/**
	 * Gets the param map.
	 * 
	 * @return the param map
	 */
	public ParamMap getParamMap() {
		return paramMap;
	}

	/**
	 * Gets the param.
	 * 
	 * @param key
	 *            the key
	 * 
	 * @return the param
	 */
	public Object getParam(String key) {
		return paramMap.get(key);
	}

	/**
	 * Gets the parent context.
	 * 
	 * @return the parent context
	 */
	public AdiContext getParentContext() {
		return parentContext;
	}

	public Set<String> getOpenParts() {
		return openParts;
	}
}
