package org.adichatz.common.ejb;

public class ProxyFindRequest {
	Class<?> beanClass;

	Object beanId;

	String[] lazyFetchMembers;

	public ProxyFindRequest(Class<?> beanClass, Object beanId, String... lazyFetchMembers) {
		this.beanClass = beanClass;
		this.beanId = beanId;
		this.lazyFetchMembers = lazyFetchMembers;
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public Object getBeanId() {
		return beanId;
	}

	public String[] getLazyFetchMembers() {
		return lazyFetchMembers;
	}

}
