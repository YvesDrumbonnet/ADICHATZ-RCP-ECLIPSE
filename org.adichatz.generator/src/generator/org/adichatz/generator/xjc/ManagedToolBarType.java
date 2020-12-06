//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
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
 * <p>Classe Java pour managedToolBarType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="managedToolBarType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="action" type="{}actionType"/>
 *         &lt;element name="menuAction" type="{}menuActionType"/>
 *         &lt;element name="separator" type="{}separatorType"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actionOrMenuActionOrSeparator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActionOrMenuActionOrSeparator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
