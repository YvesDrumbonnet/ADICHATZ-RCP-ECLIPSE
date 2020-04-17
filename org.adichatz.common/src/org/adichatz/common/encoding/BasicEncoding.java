package org.adichatz.common.encoding;

public class BasicEncoding extends AEncoding {

	private String getEncryptedKey() {
		return "0123456789AbCdEf";
	}

	@Override
	public byte[] internalEncrypt(byte[] buffer) throws Exception {
		return Base64.encrypt(buffer, getEncryptedKey());
	}

	@Override
	public byte[] internalDecrypt(byte[] buffer) throws Exception {
		return Base64.decrypt(buffer, getEncryptedKey());
	}

}
