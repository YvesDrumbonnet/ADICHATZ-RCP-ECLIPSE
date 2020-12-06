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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="treeElement" type="{}treeElementType" maxOccurs="unbounded"/>
 *         &lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *       &lt;attribute name="superTreeManagerClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getTreeManagerTree", name = "", propOrder = {
    "treeElement",
    "additionalCode"
})
@XmlRootElement(name = "treeManagerTree")
public class TreeManagerTree
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<TreeElementType> treeElement;
    protected String additionalCode;
    @XmlAttribute(name = "superTreeManagerClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String superTreeManagerClassName;

    /**
     * Gets the value of the treeElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treeElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreeElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TreeElementType }
     * 
     * 
     */
    public List<TreeElementType> getTreeElement() {
        if (treeElement == null) {
            treeElement = new ArrayList<TreeElementType>();
        }
        return this.treeElement;
    }

    /**
     * Obtient la valeur de la propriété additionalCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCode() {
        return additionalCode;
    }

    /**
     * Définit la valeur de la propriété additionalCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCode(String value) {
        this.additionalCode = value;
    }

    /**
     * Obtient la valeur de la propriété superTreeManagerClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuperTreeManagerClassName() {
        return superTreeManagerClassName;
    }

    /**
     * Définit la valeur de la propriété superTreeManagerClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuperTreeManagerClassName(String value) {
        this.superTreeManagerClassName = value;
    }

}
