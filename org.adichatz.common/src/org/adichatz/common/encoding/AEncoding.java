package org.adichatz.common.encoding;

public abstract class AEncoding {

	/*
	 * S T A T I C
	 */

	/** The THIS. */
	protected static AEncoding THIS;

	public static AEncoding getInstance() {
		return THIS;
	}

	/*
	 * end S T A T I C
	 */

	public byte[] encrypt(Object object) throws Exception {
		byte[] buffer = Base64.writeObject(object);
		return internalEncrypt(buffer);
	}

	public abstract byte[] internalEncrypt(byte[] buffer) throws Exception;

	public Object decrypt(byte[] buffer) throws Exception {
		byte[] decrypedBuffer = internalDecrypt(buffer);
		return Base64.readObject(decrypedBuffer, getClass().getClassLoader());
	}

	public abstract byte[] internalDecrypt(byte[] buffer) throws Exception;

}
