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
package org.adichatz.common.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class was inspired fromorg.apache.common.collection
 * 
 * A <code>MultiKey</code> allows multiple map keys to be merged together.
 * <p>
 * The purpose of this class is to avoid the need to write code to handle maps of maps. An example might be the need to look up a
 * file name by key and locale. The typical solution might be nested maps. This class can be used instead by creating an instance
 * passing in the key and locale.
 * <p>
 * Example usage:
 * 
 * <pre>
 * populate map with data mapping key+locale to localizedText
 *  Map map = new HashMap();
 *  - MultiKey multiKey = new MultiKey(key, locale);
 *  - map.put(multiKey, localizedText);
 * 
 * later retrieve the localized text
 *  - MultiKey multiKey = new MultiKey(key, locale);
 *  - String localizedText = (String) map.get(multiKey);
 * </pre>
 * 
 * @author Yves Drumbonnet
 */
public class MultiKey implements Serializable {
	/** Serialisation version. */
	private static final long serialVersionUID = 4465448607415788805L;

	/**
	 * Instances a new MultiKey removing null param keys.
	 *
	 * @param keys
	 *            the keys
	 * @return the multi key
	 */
	public static MultiKey newMultiKey(Object... keys) {
		List<Object> keyList = new ArrayList<>();
		for (Object key : keys)
			if (null != key)
				keyList.add(key);
		return new MultiKey(keyList.toArray());
	}

	/** The individual keys. */
	private final Object[] keys;

	/** The cached hashCode. */
	private int hashCode;

	/**
	 * Constructor taking one to several keys.
	 * <p>
	 * The keys should be immutable If they are not then they must not be changed after adding to the MultiKey.
	 * 
	 * @param keys
	 *            the keys
	 */
	public MultiKey(Object... keys) {
		this.keys = keys;
		hashCode = HashCodeBuilder.hashCode(keys);
	}

	/**
	 * Gets the key at the specified index.
	 * <p>
	 * The key should be immutable. If it is not then it must not be changed.
	 * 
	 * @param index
	 *            the index to retrieve
	 * 
	 * @return the key at the index
	 * 
	 */
	public Object getKey(int index) {
		return keys[index];
	}

	/**
	 * Gets the string.
	 * 
	 * @param index
	 *            the index
	 * @return the string
	 */
	public String getString(int index) {
		return (String) getKey(index);
	}

	/**
	 * Sets the key.
	 * 
	 * @param index
	 *            the index
	 * @param key
	 *            the key
	 */
	public void setKey(int index, Object key) {
		keys[index] = key;
		hashCode = HashCodeBuilder.hashCode(keys);
	}

	/**
	 * Gets the size of the list of keys.
	 * 
	 * @return the size of the list of keys
	 * 
	 * @since Commons Collections 3.1
	 */
	public int size() {
		return keys.length;
	}

	// -----------------------------------------------------------------------
	/**
	 * Compares this object to another.
	 * <p>
	 * To be equal, the other object must be a <code>MultiKey</code> with the same number of keys which are also equal.
	 * 
	 * @param other
	 *            the other object to compare to
	 * 
	 * @return true if equal
	 */
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other instanceof MultiKey)
			return Arrays.equals(keys, ((MultiKey) other).keys);
		return false;
	}

	/**
	 * Gets the combined hash code that is computed from all the keys.
	 * <p>
	 * This value is computed once and then cached, so elements should not change their hash codes once created (note that this is
	 * the same constraint that would be used if the individual keys elements were themselves {@link java.util.Map Map} keys.
	 * 
	 * @return the hash code
	 */
	public int hashCode() {
		return hashCode;
	}

	/**
	 * Gets a debugging string version of the key.
	 * 
	 * @return a debugging string
	 */
	public String toString() {
		try {
			return "MultiKey" + Arrays.asList(keys).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Multi Key Error";
	}
}
