//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour menuType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="menuType">
 *   &lt;complexContent>
 *     &lt;extension base="{}nodeType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="menu" type="{}menuType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="item" type="{}itemType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="expanded" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getMenuType", name = "menuType", propOrder = {
    "menuOrItem"
})
@XmlSeeAlso({
    NavigatorTree.class
})
public class MenuType
    extends NodeType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "menu", type = MenuType.class),
        @XmlElement(name = "item", type = ItemType.class)
    })
    protected List<NodeType> menuOrItem;
    @XmlAttribute(name = "expanded")
    protected Boolean expanded;

    /**
     * Gets the value of the menuOrItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuOrItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuOrItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenuType }
     * {@link ItemType }
     * 
     * 
     */
    public List<NodeType> getMenuOrItem() {
        if (menuOrItem == null) {
            menuOrItem = new ArrayList<NodeType>();
        }
        return this.menuOrItem;
    }

    /**
     * Obtient la valeur de la propriété expanded.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isExpanded() {
        if (expanded == null) {
            return false;
        } else {
            return expanded;
        }
    }

    /**
     * Définit la valeur de la propriété expanded.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExpanded(Boolean value) {
        this.expanded = value;
    }

}
