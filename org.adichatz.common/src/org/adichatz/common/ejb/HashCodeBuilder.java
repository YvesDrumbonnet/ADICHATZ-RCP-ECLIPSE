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

// TODO: Auto-generated Javadoc
/**
 * The Class HashCodeBuilder.
 * 
 * This class is a copy of org.apache.commons.lang.builder#HashCodeBuilder
 */
public class HashCodeBuilder {

	/**
	 * Constant to use in building the hashCode.
	 */
	private final int iConstant;

	/**
	 * Running total of the hashCode.
	 */
	private int iTotal = 0;

	/**
	 * Hash code.
	 * 
	 * @param objects
	 *            the objects
	 * @return the int
	 */
	public static int hashCode(Object... objects) {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		for (Object object : objects)
			hashCodeBuilder.append(object);
		return hashCodeBuilder.toHashCode();
	}

	/**
	 * <p>
	 * Uses two hard coded choices for the constants needed to build a <code>hashCode</code>.
	 * </p>
	 */
	public HashCodeBuilder() {
		iConstant = 37;
		iTotal = 17;
	}

	/**
	 * <p>
	 * Adds the result of super.hashCode() to this builder.
	 * </p>
	 * 
	 * @param superHashCode
	 *            the result of calling <code>super.hashCode()</code>
	 * @return this HashCodeBuilder, used to chain calls.
	 * @since 2.0
	 */
	public HashCodeBuilder appendSuper(int superHashCode) {
		iTotal = iTotal * iConstant + superHashCode;
		return this;
	}

	// -------------------------------------------------------------------------

	/**
	 * <p>
	 * Append a <code>hashCode</code> for an <code>Object</code>.
	 * </p>
	 * 
	 * @param object
	 *            the Object to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(Object object) {
		if (object == null) {
			iTotal = iTotal * iConstant;

		} else {
			if (object.getClass().isArray() == false) {
				// the simple case, not an array, just the element
				iTotal = iTotal * iConstant + object.hashCode();

			} else {
				// 'Switch' on type of array, to dispatch to the correct handler
				// This handles multi dimensional arrays
				if (object instanceof long[]) {
					append((long[]) object);
				} else if (object instanceof int[]) {
					append((int[]) object);
				} else if (object instanceof short[]) {
					append((short[]) object);
				} else if (object instanceof char[]) {
					append((char[]) object);
				} else if (object instanceof byte[]) {
					append((byte[]) object);
				} else if (object instanceof double[]) {
					append((double[]) object);
				} else if (object instanceof float[]) {
					append((float[]) object);
				} else if (object instanceof boolean[]) {
					append((boolean[]) object);
				} else {
					// Not an array of primitives
					append((Object[]) object);
				}
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>long</code>.
	 * </p>
	 * 
	 * @param value
	 *            the long to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(long value) {
		iTotal = iTotal * iConstant + ((int) (value ^ (value >> 32)));
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for an <code>int</code>.
	 * </p>
	 * 
	 * @param value
	 *            the int to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(int value) {
		iTotal = iTotal * iConstant + value;
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>short</code>.
	 * </p>
	 * 
	 * @param value
	 *            the short to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(short value) {
		iTotal = iTotal * iConstant + value;
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>char</code>.
	 * </p>
	 * 
	 * @param value
	 *            the char to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(char value) {
		iTotal = iTotal * iConstant + value;
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>byte</code>.
	 * </p>
	 * 
	 * @param value
	 *            the byte to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(byte value) {
		iTotal = iTotal * iConstant + value;
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>double</code>.
	 * </p>
	 * 
	 * @param value
	 *            the double to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(double value) {
		return append(Double.doubleToLongBits(value));
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>float</code>.
	 * </p>
	 * 
	 * @param value
	 *            the float to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(float value) {
		iTotal = iTotal * iConstant + Float.floatToIntBits(value);
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>boolean</code>.
	 * </p>
	 * <p>
	 * This adds <code>iConstant * 1</code> to the <code>hashCode</code> and not a <code>1231</code> or <code>1237</code> as done in
	 * java.lang.Boolean. This is in accordance with the <quote>Effective Java</quote> design.
	 * </p>
	 * 
	 * @param value
	 *            the boolean to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(boolean value) {
		iTotal = iTotal * iConstant + (value ? 0 : 1);
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for an <code>Object</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(Object[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>long</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(long[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for an <code>int</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(int[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>short</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(short[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>char</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(char[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>byte</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(byte[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>double</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(double[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>float</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(float[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Append a <code>hashCode</code> for a <code>boolean</code> array.
	 * </p>
	 * 
	 * @param array
	 *            the array to add to the <code>hashCode</code>
	 * @return this
	 */
	public HashCodeBuilder append(boolean[] array) {
		if (array == null) {
			iTotal = iTotal * iConstant;
		} else {
			for (int i = 0; i < array.length; i++) {
				append(array[i]);
			}
		}
		return this;
	}

	/**
	 * <p>
	 * Return the computed <code>hashCode</code>.
	 * </p>
	 * 
	 * @return <code>hashCode</code> based on the fields appended
	 */
	public int toHashCode() {
		return iTotal;
	}

}