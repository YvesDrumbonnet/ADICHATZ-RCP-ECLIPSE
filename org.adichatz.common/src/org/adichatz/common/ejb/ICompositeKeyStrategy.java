package org.adichatz.common.ejb;

public interface ICompositeKeyStrategy {
	public enum INCREMENT_TYPE {
		INTEGER, SHORT, LONG, BYTE
	};

	public String getMaxQueryStr();

	public Object getParentBean();

	public void computeCompositeKey(ProxyEntity<?> proxyEntity, Object nextvalue) throws Exception;

	public INCREMENT_TYPE getIncrementType();
}
