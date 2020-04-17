package org.adichatz.engine.data;

import java.io.Serializable;

import org.adichatz.common.ejb.ICompositeKeyStrategy;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.model.EntitySet;

public interface ICompositeKeyStrategyFactory extends Serializable {
	public ICompositeKeyStrategy createCompositeKeyStrategy(IEntity<?> parentEntity, IEntity<?> entity, EntitySet<?> persistentSet);
}
