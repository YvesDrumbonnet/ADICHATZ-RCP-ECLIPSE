//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour pathElementType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="pathElementType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="type" use="required" type="{}pathElementEnum" /&amp;gt;
 *       &amp;lt;attribute name="location" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="addWhen" type="{}addWhenEnum" default="ALL" /&amp;gt;
 *       &amp;lt;attribute name="additionalResourcePath" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *       &amp;lt;attribute name="addToManifestFile" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *       &amp;lt;attribute name="addToClassPath" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getPathElementType", name = "pathElementType", propOrder = {
    "condition"
})
public class PathElementType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String condition;
    @XmlAttribute(name = "type", required = true)
    protected PathElementEnum type;
    @XmlAttribute(name = "location")
    protected String location;
    @XmlAttribute(name = "addWhen")
    protected AddWhenEnum addWhen;
    @XmlAttribute(name = "additionalResourcePath")
    protected Boolean additionalResourcePath;
    @XmlAttribute(name = "addToManifestFile")
    protected Boolean addToManifestFile;
    @XmlAttribute(name = "addToClassPath")
    protected Boolean addToClassPath;

    /**
     * Obtient la valeur de la propriété condition.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Définit la valeur de la propriété condition.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondition(String value) {
        this.condition = value;
    }

    /**
     * Obtient la valeur de la propriété type.
     * 
     * @return
     *     possible object is
     *     {@link PathElementEnum }
     *     
     */
    public PathElementEnum getType() {
        return type;
    }

    /**
     * Définit la valeur de la propriété type.
     * 
     * @param value
     *     allowed object is
     *     {@link PathElementEnum }
     *     
     */
    public void setType(PathElementEnum value) {
        this.type = value;
    }

    /**
     * Obtient la valeur de la propriété location.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Définit la valeur de la propriété location.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Obtient la valeur de la propriété addWhen.
     * 
     * @return
     *     possible object is
     *     {@link AddWhenEnum }
     *     
     */
    public AddWhenEnum getAddWhen() {
        if (addWhen == null) {
            return AddWhenEnum.ALL;
        } else {
            return addWhen;
        }
    }

    /**
     * Définit la valeur de la propriété addWhen.
     * 
     * @param value
     *     allowed object is
     *     {@link AddWhenEnum }
     *     
     */
    public void setAddWhen(AddWhenEnum value) {
        this.addWhen = value;
    }

    /**
     * Obtient la valeur de la propriété additionalResourcePath.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isAdditionalResourcePath() {
        if (additionalResourcePath == null) {
            return false;
        } else {
            return additionalResourcePath;
        }
    }

    /**
     * Définit la valeur de la propriété additionalResourcePath.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAdditionalResourcePath(Boolean value) {
        this.additionalResourcePath = value;
    }

    /**
     * Obtient la valeur de la propriété addToManifestFile.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isAddToManifestFile() {
        if (addToManifestFile == null) {
            return false;
        } else {
            return addToManifestFile;
        }
    }

    /**
     * Définit la valeur de la propriété addToManifestFile.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddToManifestFile(Boolean value) {
        this.addToManifestFile = value;
    }

    /**
     * Obtient la valeur de la propriété addToClassPath.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isAddToClassPath() {
        if (addToClassPath == null) {
            return false;
        } else {
            return addToClassPath;
        }
    }

    /**
     * Définit la valeur de la propriété addToClassPath.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddToClassPath(Boolean value) {
        this.addToClassPath = value;
    }

}
