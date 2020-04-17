//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:22 AM CET 
//


package org.adichatz.engine.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour navigatorType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="navigatorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="menuPath" type="{}menuPathType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="iconURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="contributionURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="label" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="messageBundleURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "navigatorType", propOrder = {
    "menuPath"
})
public class NavigatorType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<MenuPathType> menuPath;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String id;
    @XmlAttribute(name = "iconURI")
    protected String iconURI;
    @XmlAttribute(name = "contributionURI")
    protected String contributionURI;
    @XmlAttribute(name = "label", required = true)
    protected String label;
    @XmlAttribute(name = "messageBundleURI")
    protected String messageBundleURI;

    /**
     * Gets the value of the menuPath property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuPath property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuPath().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenuPathType }
     * 
     * 
     */
    public List<MenuPathType> getMenuPath() {
        if (menuPath == null) {
            menuPath = new ArrayList<MenuPathType>();
        }
        return this.menuPath;
    }

    /**
     * Obtient la valeur de la propriété id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété iconURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIconURI() {
        return iconURI;
    }

    /**
     * Définit la valeur de la propriété iconURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIconURI(String value) {
        this.iconURI = value;
    }

    /**
     * Obtient la valeur de la propriété contributionURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContributionURI() {
        return contributionURI;
    }

    /**
     * Définit la valeur de la propriété contributionURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContributionURI(String value) {
        this.contributionURI = value;
    }

    /**
     * Obtient la valeur de la propriété label.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Définit la valeur de la propriété label.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Obtient la valeur de la propriété messageBundleURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageBundleURI() {
        return messageBundleURI;
    }

    /**
     * Définit la valeur de la propriété messageBundleURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageBundleURI(String value) {
        this.messageBundleURI = value;
    }

}
