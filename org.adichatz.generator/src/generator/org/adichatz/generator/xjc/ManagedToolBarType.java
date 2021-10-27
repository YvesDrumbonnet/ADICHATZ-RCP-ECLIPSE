//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour managedToolBarType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="managedToolBarType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}collectionType"&amp;gt;
 *       &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *         &amp;lt;element name="action" type="{}actionType"/&amp;gt;
 *         &amp;lt;element name="menuAction" type="{}menuActionType"/&amp;gt;
 *         &amp;lt;element name="separator" type="{}separatorType"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getManagedToolBarType", name = "managedToolBarType", propOrder = {
    "actionOrMenuActionOrSeparator"
})
public class ManagedToolBarType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "action", type = ActionType.class),
        @XmlElement(name = "menuAction", type = MenuActionType.class),
        @XmlElement(name = "separator", type = SeparatorType.class)
    })
    protected List<WidgetType> actionOrMenuActionOrSeparator;

    /**
     * Gets the value of the actionOrMenuActionOrSeparator property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the actionOrMenuActionOrSeparator property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getActionOrMenuActionOrSeparator().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ActionType }
     * {@link MenuActionType }
     * {@link SeparatorType }
     * 
     * 
     */
    public List<WidgetType> getActionOrMenuActionOrSeparator() {
        if (actionOrMenuActionOrSeparator == null) {
            actionOrMenuActionOrSeparator = new ArrayList<WidgetType>();
        }
        return this.actionOrMenuActionOrSeparator;
    }

}
