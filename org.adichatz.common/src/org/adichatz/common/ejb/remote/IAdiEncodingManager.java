package org.adichatz.common.ejb.remote;

// TODO: Auto-generated Javadoc
/**
 * The Interface IAdiEncodingManager.
 */
public interface IAdiEncodingManager {
	
	/**
	 * Encode.
	 *
	 * @param dData the d data
	 * @return the byte[]
	 */
	public byte[] encode(byte[] dData);

	/**
	 * Decode.
	 *
	 * @param eData the e data
	 * @return the byte[]
	 */
	public byte[] decode(byte[] eData);

}
