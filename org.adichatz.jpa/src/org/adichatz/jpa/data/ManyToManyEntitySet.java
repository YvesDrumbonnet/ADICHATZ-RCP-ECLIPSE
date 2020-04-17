package org.adichatz.jpa.data;

import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;
import org.adichatz.engine.plugin.ParamMap;

public class ManyToManyEntitySet<T> extends EntitySet<T> {
	protected ParamMap paramMap;

	public ManyToManyEntitySet(AEntityMetaModel<T> parentEntityMM, String fieldName, String mappedBy, String parentClause) {
		super(parentEntityMM, fieldName, mappedBy);
		setParentClause(parentClause);
	}

	public ParamMap getParamMap() {
		if (null == paramMap)
			paramMap = new ParamMap();
		return paramMap;
	}
}
