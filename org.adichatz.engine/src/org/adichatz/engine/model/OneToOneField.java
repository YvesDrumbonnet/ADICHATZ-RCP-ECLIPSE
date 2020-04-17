package org.adichatz.engine.model;

public class OneToOneField extends PropertyField {

	public static enum TYPE {
		MASTER, SLAVE
	};

	private TYPE oneToOneType;

	public OneToOneField(AEntityMetaModel<?> entityMM, String fieldName, TYPE oneToOneType) {
		super(entityMM, fieldName);
		this.oneToOneType = oneToOneType;
	}

	public TYPE getOneToOneType() {
		return oneToOneType;
	}
}
