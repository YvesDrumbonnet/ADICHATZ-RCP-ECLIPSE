package org.adichatz.studio.db4o.model;

// Generated 7 juin 2011 14:05:47 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

public class Item implements java.io.Serializable {

	private Property property;

	private Element element;

	private String description;

	private String category;

	private String controllerClassName;

	private String specificCode;

	private Boolean skip;

	private Set<ItemProposal> itemProposals = new HashSet<ItemProposal>(0);

	private Set<ItemParam> itemParams = new HashSet<ItemParam>(0);

	private String elementId;

	private String propertyId;

	public Item() {
	}

	public Property getProperty() {
		return this.property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Element getElement() {
		return this.element;
	}

	public void setElement(Element element) {
		this.element = element;
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
		return this.controllerClassName;
	}

	public void setControllerClassName(String controllerClassName) {
		this.controllerClassName = controllerClassName;
	}

	public String getSpecificCode() {
		return this.specificCode;
	}

	public void setSpecificCode(String specificCode) {
		this.specificCode = specificCode;
	}

	public Boolean getSkip() {
		return this.skip;
	}

	public void setSkip(Boolean skip) {
		this.skip = skip;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public Set<ItemProposal> getItemProposals() {
		return this.itemProposals;
	}

	public void setItemProposals(Set<ItemProposal> itemProposals) {
		this.itemProposals = itemProposals;
	}

	public Set<ItemParam> getItemParams() {
		return this.itemParams;
	}

	public void setItemParams(Set<ItemParam> itemParams) {
		this.itemParams = itemParams;
	}
}
