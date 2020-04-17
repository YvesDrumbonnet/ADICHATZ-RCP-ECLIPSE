package org.adichatz.common.ejb.util;

public abstract class AAdiEncoding {

	/*
	 * S T A T I C
	 */

	/** The THIS. */
	protected static AAdiEncoding THIS;

	public AAdiEncoding() {
		THIS = this;
	}

	public static AAdiEncoding getInstance() {
		return THIS;
	}

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
	 * ================================================================================================
	 */

	private static final byte[] decodeMap = initDecodeMap();

	private static final byte PADDING = 127;

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

		if (buflen == o) { // speculation worked out to be OK
			return out;
		}

		// we overestimated, so need to create a new buffer
		byte[] nb = new byte[o];
		System.arraycopy(out, 0, nb, 0, o);
		return nb;
	}

	/*
	 * end S T A T I C
	 */
}
