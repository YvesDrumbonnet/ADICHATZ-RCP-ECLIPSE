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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour menuManagerType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="menuManagerType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="action" type="{}actionType"/>
 *         &lt;element name="menu" type="{}menuManagerType"/>
 *         &lt;element name="separator" type="{}separatorType"/>
 *         &lt;element name="contributionItem" type="{}contributionItemType"/>
 *       &lt;/choice>
 *       &lt;attribute name="rank" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="imageDescriptor" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getMenuManagerType", name = "menuManagerType", propOrder = {
    "actionOrMenuOrSeparator"
})
@XmlSeeAlso({
    HeaderMenuManagerType.class
})
public class MenuManagerType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "action", type = ActionType.class),
        @XmlElement(name = "menu", type = MenuManagerType.class),
        @XmlElement(name = "separator", type = SeparatorType.class),
        @XmlElement(name = "contributionItem", type = ContributionItemType.class)
    })
    protected List<ValidElementType> actionOrMenuOrSeparator;
    @XmlAttribute(name = "rank")
    protected Integer rank;
    @XmlAttribute(name = "imageDescriptor")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String imageDescriptor;
    @XmlAttribute(name = "text")
    protected String text;

    /**
     * Gets the value of the actionOrMenuOrSeparator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actionOrMenuOrSeparator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActionOrMenuOrSeparator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionType }
     * {@link MenuManagerType }
     * {@link SeparatorType }
     * {@link ContributionItemType }
     * 
     * 
     */
    public List<ValidElementType> getActionOrMenuOrSeparator() {
        if (actionOrMenuOrSeparator == null) {
            actionOrMenuOrSeparator = new ArrayList<ValidElementType>();
        }
        return this.actionOrMenuOrSeparator;
    }

    /**
     * Obtient la valeur de la propriété rank.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Définit la valeur de la propriété rank.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRank(Integer value) {
        this.rank = value;
    }

    /**
     * Obtient la valeur de la propriété imageDescriptor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageDescriptor() {
        return imageDescriptor;
    }

    /**
     * Définit la valeur de la propriété imageDescriptor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageDescriptor(String value) {
        this.imageDescriptor = value;
    }

    /**
     * Obtient la valeur de la propriété text.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Définit la valeur de la propriété text.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

}
