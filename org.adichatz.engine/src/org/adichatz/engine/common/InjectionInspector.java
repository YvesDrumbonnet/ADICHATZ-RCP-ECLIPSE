package org.adichatz.engine.common;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.Field;

import javax.inject.Inject;

public class InjectionInspector {
	public static void inject(Object reference) {
		Class<?> clazz = reference.getClass();
		while (clazz != Object.class) {
			for (Field field : clazz.getDeclaredFields()) {
				if (null != field.getAnnotation(Inject.class)) {
					boolean canAccess = field.canAccess(reference);
					if (!canAccess)
						field.setAccessible(true);
					Object value = AdichatzApplication.getInstance().getContextValue(field.getType().getName());
					try {
						field.set(reference, value);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						logError(e);
					}
					if (!canAccess)
						field.setAccessible(false);
				}
			}
			clazz = clazz.getSuperclass();
		}

	}

}
