package org.adichatz.studio.db4o.model;

public class ItemParam implements java.io.Serializable {

	private Item item;

	private String value;

	public ItemParam() {
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
