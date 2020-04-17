package org.adichatz.jpa.data;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.adichatz.common.ejb.ICompositeKeyStrategy;
import org.adichatz.common.ejb.extra.CompositeKeyStrategy;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.data.ICompositeKeyStrategyFactory;
import org.adichatz.engine.model.EntitySet;

public class CompositeKeyStrategyFactory implements ICompositeKeyStrategyFactory {

	private static final long serialVersionUID = 7928331678518726532L;

	@Override
	public ICompositeKeyStrategy createCompositeKeyStrategy(IEntity<?> parentEntity, IEntity<?> entity, EntitySet<?> entitySet) {
		Object id = ReflectionTools.instantiateClass(
				entity.getEntityMM().getFieldMap().get(entity.getEntityMM().getIdFieldName()).getGetMethod().getReturnType());

		String parentIdFieldName = parentEntity.getEntityMM().getIdFieldName();
		String idFieldName = entity.getEntityMM().getIdFieldName();

		Field[] idFields = id.getClass().getDeclaredFields();
		ArrayList<Field> idFieldList = new ArrayList<>(idFields.length - 1);
		for (Field field : idFields)
			if (!Modifier.isStatic(field.getModifiers()))
				idFieldList.add(field);
		idFields = new Field[idFieldList.size()];
		idFieldList.toArray(idFields);
		Field incrementedField = null;
		try {
			if (2 == idFields.length) {
				int index = 0;
				if (parentIdFieldName.equals(idFields[1])) {
					index = 1;
					incrementedField = idFields[0];
				} else
					incrementedField = idFields[1];
				boolean accessible = idFields[index].canAccess(parentEntity.getBeanId());
				idFields[index].setAccessible(true);
				idFields[index].set(id, parentEntity.getBeanId());
				idFields[index].setAccessible(accessible);
			} else {
				for (Field idField : idFields) {
					boolean isIncrementedField = true;
					for (Field parentIdField : parentEntity.getBeanId().getClass().getDeclaredFields()) {
						if (idField.getName().equals(parentIdField.getName())) {
							boolean idAccess = idField.canAccess(parentEntity.getBeanId());
							boolean parentIdAccess = parentIdField.canAccess(parentEntity.getBeanId());
							idField.setAccessible(true);
							parentIdField.setAccessible(true);
							idField.set(id, parentIdField.get(parentEntity.getBeanId()));
							idField.setAccessible(idAccess);
							parentIdField.setAccessible(parentIdAccess);
							isIncrementedField = false;
							break;
						}
					}
					if (isIncrementedField)
						incrementedField = idField;
				}
			}
			StringBuffer maxQuerySB = new StringBuffer("select max(").append(idFieldName).append('.');
			maxQuerySB.append(incrementedField.getName());
			maxQuerySB.append(") from ").append(entity.getEntityMM().getEntityId()).append(" where ");
			maxQuerySB.append(entitySet.getMappedBy()).append(" = ?1");

			ICompositeKeyStrategy.INCREMENT_TYPE incrementType = null;
			if (incrementedField.getType().equals(short.class) || incrementedField.getType().equals(Short.class))
				incrementType = ICompositeKeyStrategy.INCREMENT_TYPE.SHORT;
			else if (incrementedField.getType().equals(int.class) || incrementedField.getType().equals(Integer.class))
				incrementType = ICompositeKeyStrategy.INCREMENT_TYPE.INTEGER;
			else if (incrementedField.getType().equals(long.class) || incrementedField.getType().equals(Long.class))
				incrementType = ICompositeKeyStrategy.INCREMENT_TYPE.LONG;
			else if (incrementedField.getType().equals(byte.class) || incrementedField.getType().equals(Byte.class))
				incrementType = ICompositeKeyStrategy.INCREMENT_TYPE.BYTE;
			else
				LogBroker.logError("Do not know how to increment new value for " + entity.getEntityMM().getEntityId()
						+ "!!! Use only int, Integer, long, Long, short, Short for next value");

			return new CompositeKeyStrategy(parentEntity.getBean(), id, idFieldName, incrementedField.getName(),
					maxQuerySB.toString(), incrementType);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logError(e);
		}
		return null;
	}
}
