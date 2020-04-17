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
package org.adichatz.common.encoding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// TODO: Auto-generated Javadoc
/**
 * The Class Base64.
 * 
 * Encodes/decodes byte arrays and Strings into/from a base 64 String.
 * 
 * (see com.sun.syndication.io.impl.Base64 author Alejandro Abdelnur)
 * 
 * @author Yves Drumbonnet
 *
 */
public class Base64 {

	/**
	 * Encodes a String into a base 64 String. The resulting encoding is chunked at 76 bytes.
	 * <p>
	 * 
	 * @param s
	 *            String to encode.
	 * @return encoded string.
	 * 
	 */
	public static String encode(String s) {
		byte[] sBytes = s.getBytes();
		sBytes = encode(sBytes);
		s = new String(sBytes);
		return s;
	}

	/**
	 * Decodes a base 64 String into a String.
	 * <p>
	 * 
	 * @param s
	 *            String to decode.
	 * @return encoded string.
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public static byte[] decode(String s) throws IllegalArgumentException {
		s = s.replaceAll("\n", "");
		s = s.replaceAll("\r", "");
		byte[] sBytes = s.getBytes();
		return decode(sBytes);
	}

	/** The Constant ALPHASET. */
	private static final byte[] ALPHASET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".getBytes();

	/** The Constant I6O2. */
	private static final int I6O2 = 255 - 3;

	/** The Constant O6I2. */
	private static final int O6I2 = 3;

	/** The Constant I4O4. */
	private static final int I4O4 = 255 - 15;

	/** The Constant O4I4. */
	private static final int O4I4 = 15;

	/** The Constant I2O6. */
	private static final int I2O6 = 255 - 63;

	/** The Constant O2I6. */
	private static final int O2I6 = 63;

	/**
	 * Encodes a byte array into a base 64 byte array.
	 * <p>
	 * 
	 * @param dData
	 *            byte array to encode.
	 * @return encoded byte array.
	 * 
	 */
	public static byte[] encode(byte[] dData) {
		if (dData == null) {
			return null;
		}
		byte[] eData = new byte[((dData.length + 2) / 3) * 4];

		int eIndex = 0;
		for (int i = 0; i < dData.length; i += 3) {
			int d1;
			int d2 = 0;
			int d3 = 0;
			int e1;
			int e2;
			int e3;
			int e4;
			int pad = 0;

			d1 = dData[i];
			if ((i + 1) < dData.length) {
				d2 = dData[i + 1];
				if ((i + 2) < dData.length) {
					d3 = dData[i + 2];
				} else {
					pad = 1;
				}
			} else {
				pad = 2;
			}

			e1 = ALPHASET[(d1 & I6O2) >> 2];
			e2 = ALPHASET[(d1 & O6I2) << 4 | (d2 & I4O4) >> 4];
			e3 = ALPHASET[(d2 & O4I4) << 2 | (d3 & I2O6) >> 6];
			e4 = ALPHASET[(d3 & O2I6)];

			eData[eIndex++] = (byte) e1;
			eData[eIndex++] = (byte) e2;
			eData[eIndex++] = (pad < 2) ? (byte) e3 : (byte) '=';
			eData[eIndex++] = (pad < 1) ? (byte) e4 : (byte) '=';

		}
		return eData;
	}

	/** The Constant CODES. */
	private final static int[] CODES = new int[256];

	static {
		for (int i = 0; i < CODES.length; i++) {
			CODES[i] = 64;
		}
		for (int i = 0; i < ALPHASET.length; i++) {
			CODES[ALPHASET[i]] = i;
		}
	}

	/**
	 * Decodes a com.sun.syndication.io.impl.Base64 byte array.
	 * <p>
	 * 
	 * @param eData
	 *            byte array to decode.
	 * @return decoded byte array.
	 */
	public static byte[] decode(byte[] eData) {
		if (eData == null) {
			return null;
		}
		byte[] cleanEData = eData.clone();
		int cleanELength = 0;
		for (int i = 0; i < eData.length; i++) {
			if (eData[i] < 256 && CODES[eData[i]] < 64) {
				cleanEData[cleanELength++] = eData[i];
			}
		}

		int dLength = (cleanELength / 4) * 3;
		switch (cleanELength % 4) {
		case 3:
			dLength += 2;
			break;
		case 2:
			dLength++;
			break;
		}

		byte[] dData = new byte[dLength];
		int dIndex = 0;
		for (int i = 0; i < eData.length; i += 4) {
			if ((i + 3) > eData.length) {
				throw new IllegalArgumentException("byte array is not a valid com.sun.syndication.io.impl.Base64 encoding");
			}
			int e1 = CODES[cleanEData[i]];
			int e2 = CODES[cleanEData[i + 1]];
			int e3 = CODES[cleanEData[i + 2]];
			int e4 = CODES[cleanEData[i + 3]];
			dData[dIndex++] = (byte) ((e1 << 2) | (e2 >> 4));
			if (dIndex < dData.length) {
				dData[dIndex++] = (byte) ((e2 << 4) | (e3 >> 2));
			}
			if (dIndex < dData.length) {
				dData[dIndex++] = (byte) ((e3 << 6) | (e4));
			}
		}
		return dData;
	}

	/**
	 * ================================================================================================<br>
	 * parseBase64Binary: Read string which represents byte array
	 * ================================================================================================.
	 */

	private static final byte[] decodeMap = initDecodeMap();

	/** The Constant PADDING. */
	private static final byte PADDING = 127;

	/**
	 * Inits the decode map.
	 *
	 * @return the byte[]
	 */
	private static byte[] initDecodeMap() {
		byte[] map = new byte[128];
		int i;
		for (i = 0; i < 128; i++) {
			map[i] = -1;
		}

		for (i = 'A'; i <= 'Z'; i++) {
			map[i] = (byte) (i - 'A');
		}
		for (i = 'a'; i <= 'z'; i++) {
			map[i] = (byte) (i - 'a' + 26);
		}
		for (i = '0'; i <= '9'; i++) {
			map[i] = (byte) (i - '0' + 52);
		}
		map['+'] = 62;
		map['/'] = 63;
		map['='] = PADDING;

		return map;
	}

	/**
	 * Guess length.
	 *
	 * @param text
	 *            the text
	 * @return the int
	 */
	private static int guessLength(String text) {
		final int len = text.length();

		// compute the tail '=' chars
		int j = len - 1;
		for (; j >= 0; j--) {
			byte code = decodeMap[text.charAt(j)];
			if (code == PADDING) {
				continue;
			}
			if (code == -1) // most likely this base64 text is indented. go with the upper bound
			{
				return text.length() / 4 * 3;
			}
			break;
		}

		j++; // text.charAt(j) is now at some base64 char, so +1 to make it the size
		int padSize = len - j;
		if (padSize > 2) // something is wrong with base64. be safe and go with the upper bound
		{
			return text.length() / 4 * 3;
		}

		// so far this base64 looks like it's unindented tightly packed base64.
		// take a chance and create an array with the expected size
		return text.length() / 4 * 3 - padSize;
	}

	/**
	 * Parses the base64 binary.
	 *
	 * @param text
	 *            the text
	 * @return the byte[]
	 */
	public static byte[] parseBase64Binary(String text) {
		final int buflen = guessLength(text);
		final byte[] out = new byte[buflen];
		int o = 0;

		final int len = text.length();
		int i;

		final byte[] quadruplet = new byte[4];
		int q = 0;

		// convert each quadruplet to three bytes.
		for (i = 0; i < len; i++) {
			char ch = text.charAt(i);
			byte v = decodeMap[ch];

			if (v != -1) {
				quadruplet[q++] = v;
			}

			if (q == 4) {
				// quadruplet is now filled.
				out[o++] = (byte) ((quadruplet[0] << 2) | (quadruplet[1] >> 4));
				if (quadruplet[2] != PADDING) {
					out[o++] = (byte) ((quadruplet[1] << 4) | (quadruplet[2] >> 2));
				}
				if (quadruplet[3] != PADDING) {
					out[o++] = (byte) ((quadruplet[2] << 6) | (quadruplet[3]));
				}
				q = 0;
			}
		}

		if (buflen == o) // speculation worked out to be OK
		{
			return out;
		}

		// we overestimated, so need to create a new buffer
		byte[] nb = new byte[o];
		System.arraycopy(out, 0, nb, 0, o);
		return nb;
	}

	/** The transformation method. */
	private static String TRANSFORMATION_METHOD = "AES";

	/**
	 * Gets the encrypted key.
	 *
	 * @return the encrypted key
	 */
	public static String getEncryptedKey() {
		return "0123456789AbCdEf";
	}

	//
	//
	// S E R I A L I Z A T I O N : Write / Read Object.
	//
	//

	/**
	 * Write object.
	 * 
	 * @param object
	 *            the object
	 * @return the byte[]
	 */
	public static byte[] writeObject(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		byte[] buffer = baos.toByteArray();
		oos.close();
		baos.close();
		return Base64.encode(buffer);
	}

	/**
	 * Read object.
	 *
	 * @param buffer
	 *            the buffer
	 * @param classLoader
	 *            the class loader
	 * @return the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public static Object readObject(byte[] buffer, ClassLoader classLoader) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(buffer));
		ObjectInputStream ois = new ClassLoaderObjectInputStream(classLoader, bais);
		Object object = ois.readObject();
		ois.close();
		bais.close();
		return object;
	}

	//
	//
	// E N C R Y P T I O N
	//
	//

	/**
	 * Encrypt.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String encrypt(String text) throws Exception {
		return encrypt(text, getEncryptedKey());
	}

	/**
	 * Encrypt.
	 *
	 * @param text
	 *            the text
	 * @param encryptKey
	 *            the encrypt key
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String encrypt(String text, String encryptKey) throws Exception {
		if (null == text || text.isEmpty())
			return text;
		return new String(encrypt(text.getBytes(), encryptKey));
	}

	/**
	 * Encrypt.
	 *
	 * @param buffer
	 *            the buffer
	 * @param encryptKey
	 *            the encrypt key
	 * @return the byte[]
	 * @throws Exception
	 *             the exception
	 */
	public static byte[] encrypt(byte[] buffer, String encryptKey) throws Exception {
		if (null == buffer || 0 == buffer.length)
			return buffer;
		SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey.getBytes(), TRANSFORMATION_METHOD);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_METHOD);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, cipher.getParameters());
		return Base64.encode(cipher.doFinal(buffer));
	}

	/**
	 * Decrypt.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String decrypt(String text) throws Exception {
		return decrypt(text, getEncryptedKey());
	}

	/**
	 * Decrypt.
	 *
	 * @param encryptedText
	 *            the encrypted text
	 * @param encryptKey
	 *            the encrypt key
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String decrypt(String encryptedText, String encryptKey) throws Exception {
		if (null == encryptedText || encryptedText.isEmpty())
			return encryptedText;
		SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey.getBytes(), TRANSFORMATION_METHOD);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_METHOD);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		return new String(cipher.doFinal(Base64.decode(encryptedText)));
	}

	/**
	 * Decrypt.
	 *
	 * @param buffer
	 *            the buffer
	 * @param encryptKey
	 *            the encrypt key
	 * @return the byte[]
	 * @throws Exception
	 *             the exception
	 */
	public static byte[] decrypt(byte[] buffer, String encryptKey) throws Exception {
		if (null == buffer || 0 == buffer.length)
			return buffer;
		SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey.getBytes(), TRANSFORMATION_METHOD);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_METHOD);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		return cipher.doFinal(Base64.decode(buffer));
	}
}