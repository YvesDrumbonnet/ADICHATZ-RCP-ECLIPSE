package org.adichatz.engine.data;

import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.model.PropertyField;

public interface IFieldBindManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.AEntityBindingManager#addPropertyChangeListener(org.adichatz.engine.cache.IEntity)
	 */
	/**
	 * Adds the property change listener.
	 * 
	 * @param entity
	 *            the entity
	 */
	void addPropertyChangeListener(IEntity<?> entity);

	/**
	 * Bind target to model.
	 */
	void bindTargetToModel();

	/**
	 * Gets the field controller.
	 * 
	 * @return the field controller
	 */
	AFieldController getFieldController();

	/**
	 * Gets the listeners.
	 * 
	 * @return the listeners
	 */
	List<Runnable> getListeners();

	/**
	 * Unbind.
	 */
	void unbind();

	/**
	 * Sets the column.
	 * 
	 * @param column
	 *            the new column
	 */
	void setColumn(String column);

	/**
	 * Checks if is synchro target to model.
	 * 
	 * @return true, if is synchro target to model
	 */
	boolean isSynchroTargetToModel();

	PropertyField getField();

}