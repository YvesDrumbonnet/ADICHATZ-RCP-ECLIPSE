package org.adichatz.generator.common;

import java.io.IOException;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.AGenerator.LIFECYCLE_STAGE;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.wrapper.internal.IElementWrapper;

public class PropertyField {
	LIFECYCLE_STAGE lifeCycleStage;

	/** The type. */
	String propertyType;

	/**
	 * The control getter For IFieldWrapper, property <em>enabled</em> and <em>visible</em> are treated on the controller and not on
	 * the controls so that if the controller manages several controls (e.g. text + button) the properties can be
	 */
	protected String controlGetter;

	/** The setter. */
	String setter;

	/** value if null. */
	Object nvlValue;

	public PropertyField(LIFECYCLE_STAGE lifeCycleStage) {
		this.lifeCycleStage = lifeCycleStage;
	}

	public PropertyField(String propertyType, String controlGetter, String setter, LIFECYCLE_STAGE lifeCycleStage) {
		this.propertyType = propertyType;
		this.controlGetter = controlGetter;
		this.setter = setter;
		this.lifeCycleStage = lifeCycleStage;
	}

	/**
	 * Instantiates a new property field.
	 * 
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @param controlGetter
	 *            the control getter
	 * @param setter
	 *            the setter
	 */
	public PropertyField(String propertyType, String controlGetter, String setter) {
		this(propertyType, controlGetter, setter, LIFECYCLE_STAGE.AFTER_CREATE_CONTROL);
	}

	/**
	 * Instantiates a new property field.
	 * 
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @param controlGetter
	 *            the control getter
	 */
	public PropertyField(String type, String controlGetter) {
		this(type, controlGetter, null);
	}

	/**
	 * Gets the control getter.
	 * 
	 * @param elementClass
	 *            the element class
	 * 
	 * @return the controlGetter
	 */
	public String getControlGetter(IElementWrapper element) {
		return EngineTools.isEmpty(controlGetter) ? "" : controlGetter + ".";
	}

	public boolean hasSpecialCode(BufferCode bufferCode, Object fieldValue) throws IOException {
		return false;
	}

	public LIFECYCLE_STAGE getLifeCycleStage(IElementWrapper element) {
		return lifeCycleStage;
	}

	public String getSetter(IElementWrapper element) {
		return setter;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public Object getNvlValue() {
		return nvlValue;
	}
}
