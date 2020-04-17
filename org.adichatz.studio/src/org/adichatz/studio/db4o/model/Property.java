package org.adichatz.studio.db4o.model;

import java.util.HashSet;
import java.util.Set;

public class Property implements java.io.Serializable {

	private String id;

	private String description;

	private String category;

	private Set<PropertyProposal> propertyProposals = new HashSet<PropertyProposal>(0);

	private Set<Item> items = new HashSet<Item>(0);

	private String controllerClassName;

	public Property() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getControllerClassName() {
		return controllerClassName;
	}

	public void setControllerClassName(String controllerClassName) {
		this.controllerClassName = controllerClassName;
	}

	public Set<PropertyProposal> getPropertyProposals() {
		return this.propertyProposals;
	}

	public void setPropertyProposals(Set<PropertyProposal> propertyProposals) {
		this.propertyProposals = propertyProposals;
	}

	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
}
