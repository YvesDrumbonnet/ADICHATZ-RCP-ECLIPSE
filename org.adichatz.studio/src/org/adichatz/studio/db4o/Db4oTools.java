package org.adichatz.studio.db4o;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.studio.db4o.model.Element;
import org.adichatz.studio.db4o.model.Item;
import org.adichatz.studio.db4o.model.ItemProposal;
import org.adichatz.studio.db4o.model.Property;
import org.adichatz.studio.db4o.model.PropertyProposal;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Db4oTools {
	public static String DB4O_FILE_NAME = "xjcDB4O.base";

	private static Db4oTools THIS;

	private static ObjectContainer container;

	public static Db4oTools getInstance() {
		if (null == THIS)
			THIS = new Db4oTools();
		return THIS;
	}

	public static void closeDB4oBase() {
		if (null != container)
			container.close();
	}

	private ObjectContainer getContainer(String dataBaseFileName) {
		if (null == container) {
			if (null == dataBaseFileName)
				dataBaseFileName = getDatabaseFileName();
			container = Db4oEmbedded.openFile(dataBaseFileName);
		}
		return container;
	}

	private String getDatabaseFileName() {
		return FileUtil.getPluginHome(GeneratorConstants.TEMPLATE_BUNDLE).concat("/database/").concat(Db4oTools.DB4O_FILE_NAME);
	}

	public Property getProperty(String dataBaseFileName, String propertyId) {
		Property property = new Property();
		property.setId(propertyId);
		ObjectSet<Property> properties = getContainer(dataBaseFileName).queryByExample(property);
		if (properties.isEmpty()) {
			logError("No property found for property id '" + propertyId + "'!?");
			return null;
		}
		return properties.next();
	}

	public Element getElement(String dataBaseFileName, String elementId, boolean erroWhenNoElement) {
		Element element = new Element();
		element.setId(elementId);
		ObjectSet<Element> elements = getContainer(dataBaseFileName).queryByExample(element);
		if (elements.isEmpty()) {
			if (erroWhenNoElement)
				logError("No element found for element id '" + elementId + "'!?");
			return null;
		}
		return elements.next();
	}

	public Item getItem(String dataBaseFileName, String elementId, String propertyId) {
		Item item = new Item();
		item.setElementId(elementId);
		item.setPropertyId(propertyId);
		ObjectSet<Item> elements = getContainer(dataBaseFileName).queryByExample(item);
		if (elements.isEmpty()) {
			return null;
		}
		return elements.next();
	}

	public List<String> getProposals(String dataBaseFileName, String elementId, String propertyId) {
		Item item = new Item();
		item.setElementId(elementId);
		item.setPropertyId(propertyId);
		ObjectSet<Item> elements = getContainer(dataBaseFileName).queryByExample(item);
		if (elements.isEmpty()) {
			logError("No item found for element id '" + elementId + "' and property id '" + propertyId + "'!?");
			return null;
		}
		item = elements.next();
		List<String> proposals = new ArrayList<String>();
		if (item.getItemProposals().isEmpty()) {
			List<PropertyProposal> propertyProposals = new ArrayList<PropertyProposal>(item.getProperty().getPropertyProposals());
			Collections.sort(propertyProposals, new Comparator<PropertyProposal>() {
				@Override
				public int compare(PropertyProposal arg0, PropertyProposal arg1) {
					return arg0.getRow() - arg1.getRow();
				}
			});
			for (PropertyProposal proposal : propertyProposals)
				proposals.add(proposal.getProposal());
		} else {
			List<ItemProposal> itemProposals = new ArrayList<ItemProposal>(item.getItemProposals());
			Collections.sort(itemProposals, new Comparator<ItemProposal>() {
				@Override
				public int compare(ItemProposal arg0, ItemProposal arg1) {
					return arg0.getRow() - arg1.getRow();
				}
			});
			for (ItemProposal proposal : itemProposals)
				proposals.add(proposal.getProposal());
		}
		return proposals;
	}

	public void store(String dataBaseFileName, Object object) {
		getContainer(dataBaseFileName).store(object);
	}

	public String getControllerClassName(String elementId, String propertyId) {
		Item item = new Item();
		item.setElementId(elementId);
		item.setPropertyId(propertyId);
		ObjectSet<Item> items = getContainer(null).queryByExample(item);
		if (!items.isEmpty()) {
			String controllerClassName = items.next().getControllerClassName();
			if (null != controllerClassName)
				return controllerClassName;
		}

		Property property = new Property();
		property.setId(propertyId);
		ObjectSet<Property> properties = getContainer(null).queryByExample(property);
		if (!properties.isEmpty())
			return properties.next().getControllerClassName();
		return null;
	}
}
