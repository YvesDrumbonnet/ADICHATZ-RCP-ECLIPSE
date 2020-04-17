package org.adichatz.studio.db4o.model;

public class ItemProposal implements java.io.Serializable {
	private Item item;

	private String proposal;

	private int row;

	public ItemProposal() {
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getProposal() {
		return proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
