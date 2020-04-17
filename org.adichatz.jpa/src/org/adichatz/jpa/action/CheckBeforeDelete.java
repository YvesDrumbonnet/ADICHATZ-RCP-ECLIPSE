package org.adichatz.jpa.action;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.adichatz.common.ejb.AdiPMException;
import org.adichatz.common.ejb.AdiQuery;
import org.adichatz.common.ejb.AdiQuery.QUERY_TYPE;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.extra.AdiMessageDialog;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.jpa.data.AdiOneToOne;
import org.adichatz.jpa.data.AdiOneToOnes;
import org.adichatz.jpa.data.JPADataAccess;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import net.miginfocom.swt.MigLayout;

public class CheckBeforeDelete {

	private AdiResourceBundle resourceBundle;

	public boolean test(Display display, IEntity<?> entity, Object stopAfterFirstStr) {
		boolean stopAfterFirst = true;

		if (Boolean.TRUE.equals(stopAfterFirstStr))
			stopAfterFirst = true;
		else if (stopAfterFirstStr instanceof String)
			stopAfterFirst = !"FALSE".equals(((String) stopAfterFirstStr).trim().toUpperCase());
		AEntityMetaModel<?> entityMM = entity.getEntityMM();
		JPADataAccess dataAccess = (JPADataAccess) entityMM.getDataAccess();
		AdiOneToOnes oneToOnes = entityMM.getBeanClass().getAnnotation(AdiOneToOnes.class);
		List<AdiQuery> queries = new ArrayList<>();
		List<String> fieldNames = new ArrayList<>();
		if (null != oneToOnes) {
			for (AdiOneToOne oneToOne : oneToOnes.oneToOnes()) {
				if (!isCascadeRemoveable(oneToOne.cascade())) {
					AEntityMetaModel<?> emm = entityMM.getPluginEntity().getPluginEntityTree().getEntityMM(oneToOne.entityURI());
					queries.add(getQueryCount(dataAccess, oneToOne.mappedBy(), emm.getEntityId(), entity.getBean()));
					fieldNames.add(oneToOne.fieldName());
				}
			}
		}
		for (Method method : entityMM.getBeanClass().getDeclaredMethods()) {
			OneToMany oneToMany = method.getAnnotation(OneToMany.class);
			if (null != oneToMany && !isCascadeRemoveable(oneToMany.cascade())) {
				Class<?> genericClass = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
				AEntityMetaModel<?> emm = entityMM.getPluginEntity().getPluginEntityTree().getEntityMM(genericClass);
				queries.add(getQueryCount(dataAccess, oneToMany.mappedBy(), emm.getEntityId(), entity.getBean()));
				fieldNames.add(EngineTools.lowerCaseFirstLetter(method.getName().substring(3)));
			}
		}
		try {
			Long[] counts = dataAccess.getPersistenceManager().checkBeforeDeleting(stopAfterFirst,
					queries.toArray(new AdiQuery[queries.size()]));
			List<String> errorMessages = new ArrayList<>();
			for (int i = 0; i < counts.length; i++) {
				if (0l != counts[i]) {
					if (null == resourceBundle)
						resourceBundle = dataAccess.getPluginResources().getResourceBundleManager()
								.getResourceBundle(entityMM.getEntityId());
					String fieldName = resourceBundle.getValueFromBundle(fieldNames.get(i));
					errorMessages.add(
							getFromJpaBundle(1l == counts[i] ? "entity.child.count" : "entity.child.counts", counts[i], fieldName));
					if (stopAfterFirst)
						break;
				}
			}
			if (!errorMessages.isEmpty()) {
				String title = getFromJpaBundle("cannot.delete.entity");
				new AdiMessageDialog(display, MessageDialog.INFORMATION,
						AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_ENTITY_DELETE"), title, title,
						getFromJpaBundle("cannot.delete.entity.dependencies")) {
					@Override
					public void createMessage(Composite parent) {
						Composite composite = toolkit.createComposite(parent);
						composite.setLayout(new MigLayout("wrap 1"));
						for (String message : errorMessages) {
							toolkit.createLabel(composite, message);
						}
					}
				}.open();
				return false;
			}
		} catch (AdiPMException e) {
			logError(e);
		}
		return true;
	}

	private boolean isCascadeRemoveable(CascadeType[] cascades) {
		for (CascadeType cascade : cascades) {
			if (CascadeType.ALL == cascade || CascadeType.REMOVE == cascade)
				return true;
		}
		return false;
	}

	private AdiQuery getQueryCount(JPADataAccess dataAccess, String fieldName, String entityId, Object beanId) {
		StringBuffer querySB = new StringBuffer("select count(*) from ").append(entityId).append(" where ").append(fieldName)
				.append(" = ?1");
		return new AdiQuery(QUERY_TYPE.SingleJQL, querySB.toString(), (Serializable) beanId);
	}

}
