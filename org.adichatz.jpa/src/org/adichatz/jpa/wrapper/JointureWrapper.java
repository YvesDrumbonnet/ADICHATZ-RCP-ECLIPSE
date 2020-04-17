package org.adichatz.jpa.wrapper;

import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.xjc.JointureType;

public class JointureWrapper extends JointureType {
	private static final long serialVersionUID = 6638159057540886230L;

	private JointureWrapper parentJointure;

	private AEntityMetaModel<?> entityMM;

	public JointureWrapper(String suffix, JointureWrapper parentJointure, String entityURI, JPAQueryManager<?> queryManager) {
		this.suffix = suffix;
		this.parentJointure = parentJointure;
		this.entityMM = queryManager.getEntityMM().getPluginEntity().getPluginEntityTree().getEntityMM(entityURI);
		queryManager.getJointureMap().put(suffix, this);
		queryManager.getJointures().add(this);
	}

	public JointureWrapper getParentJointure() {
		if (null == parentJointure)
			parentJointure = this;
		return parentJointure;
	}

	public AEntityMetaModel<?> getEntityMM() {
		return entityMM;
	}

	public void setEntityMM(AEntityMetaModel<?> entityMM) {
		this.entityMM = entityMM;
	}

}
