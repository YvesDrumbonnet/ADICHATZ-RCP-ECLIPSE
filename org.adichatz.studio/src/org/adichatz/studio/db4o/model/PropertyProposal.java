package org.adichatz.studio.db4o.model;

public class PropertyProposal implements java.io.Serializable {

	private Property property;

	private String proposal;

	private int row;

	public PropertyProposal() {
	}

	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
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
