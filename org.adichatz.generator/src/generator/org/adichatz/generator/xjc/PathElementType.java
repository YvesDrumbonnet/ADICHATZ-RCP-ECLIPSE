//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour pathElementType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="pathElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{}pathElementEnum" />
 *       &lt;attribute name="location" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="addWhen" type="{}addWhenEnum" default="ALL" />
 *       &lt;attribute name="additionalResourcePath" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="addToManifestFile" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="addToClassPath" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Obtient la valeur de la propri�t� condition.
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
     * D�finit la valeur de la propri�t� condition.
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
     * Obtient la valeur de la propri�t� type.
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
     * D�finit la valeur de la propri�t� type.
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
     * Obtient la valeur de la propri�t� location.
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
     * D�finit la valeur de la propri�t� location.
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
     * Obtient la valeur de la propri�t� addWhen.
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
     * D�finit la valeur de la propri�t� addWhen.
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
     * Obtient la valeur de la propri�t� additionalResourcePath.
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
     * D�finit la valeur de la propri�t� additionalResourcePath.
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
     * Obtient la valeur de la propri�t� addToManifestFile.
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
     * D�finit la valeur de la propri�t� addToManifestFile.
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
     * Obtient la valeur de la propri�t� addToClassPath.
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
     * D�finit la valeur de la propri�t� addToClassPath.
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
