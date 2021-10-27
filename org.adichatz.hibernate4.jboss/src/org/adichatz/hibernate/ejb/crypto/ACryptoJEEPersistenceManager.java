package org.adichatz.hibernate.ejb.crypto;

import org.adichatz.common.ejb.AdiPMException;
import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.common.ejb.ProxyEntity;
import org.adichatz.common.ejb.ProxyTransaction;
import org.adichatz.common.ejb.QueryResult;
import org.adichatz.common.ejb.Session;
import org.adichatz.common.encoding.AEncoding;
import org.adichatz.hibernate.ejb.AJEEPersistenceManager;

import jakarta.persistence.EntityManager;

public class ACryptoJEEPersistenceManager extends AJEEPersistenceManager {

	private AEncoding encoding;

	@Override
	public EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getEncryptedQueryResult(byte[] cryptedAdiQuery) throws Exception {
		AdiQuery adiQuery = (AdiQuery) encoding.decrypt(cryptedAdiQuery);
		QueryResult queryResult = getQueryResult(adiQuery);
		return encoding.encrypt(queryResult);
	}

	@Override
	public int execUpdateQuery(AdiQuery adiQuery) throws AdiPMException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> ProxyEntity<T> find(Session session, ProxyEntity<T> proxyEntity) throws AdiPMException {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] cryptodSaveEntities(byte[] cryptedProxyTransaction) throws Exception {
		ProxyTransaction proxyTransaction = (ProxyTransaction) encoding.decrypt(cryptedProxyTransaction);
		ProxyTransaction transactionResult = saveEntities(proxyTransaction);
		return encoding.encrypt(transactionResult);
	}

	@Override
	public ProxyTransaction refreshEntities(ProxyTransaction proxyTransaction) throws AdiPMException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> byte[] cryptoGetFromProxy(byte[] cryptedBean) throws Exception {
		@SuppressWarnings("unchecked")
		T bean = (T) encoding.decrypt(cryptedBean);
		T resultBean = getFromProxy(bean);
		return encoding.encrypt(resultBean);
	}

	public byte[] cryptodBatchRequests(byte[] cryptedProxyTransaction) throws Exception {
		ProxyTransaction proxyTransaction = (ProxyTransaction) encoding.decrypt(cryptedProxyTransaction);
		ProxyTransaction transactionResult = batchRequests(proxyTransaction);
		return encoding.encrypt(transactionResult);
	}
}
