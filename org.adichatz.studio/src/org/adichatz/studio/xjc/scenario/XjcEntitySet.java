package org.adichatz.studio.xjc.scenario;

import java.util.HashSet;
import java.util.Set;

import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.model.EntitySet;;

public class XjcEntitySet<T> extends EntitySet<T> {

	/** The element ids. */
	private Set<String> elementIds;

	public XjcEntitySet(AEntityMetaModel<T> entityMM, String fieldName, String mappedBy) {
		super(entityMM, fieldName, mappedBy);
	}

	/**
	 * Adds the element entity.
	 * 
	 * @param entityId
	 *            the entity id
	 */
	public void addElementEntity(String entityId) {
		if (null == elementIds)
			elementIds = new HashSet<String>();
		elementIds.add(entityId);
	}

	/**
	 * Gets the element ids.
	 * 
	 * @return the element ids
	 */
	public Set<String> getElementIds() {
		return elementIds;
	}

}
