//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour customPostActionType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="customPostActionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customGenerationUnit" type="{}customGenerationUnitType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="rewritePojos" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="generateEJB" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="generatePersistenceXml" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="deployEJB" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customPostActionType", propOrder = {
    "customGenerationUnit"
})
public class CustomPostActionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<CustomGenerationUnitType> customGenerationUnit;
    @XmlAttribute(name = "rewritePojos")
    protected Boolean rewritePojos;
    @XmlAttribute(name = "generateEJB")
    protected Boolean generateEJB;
    @XmlAttribute(name = "generatePersistenceXml")
    protected Boolean generatePersistenceXml;
    @XmlAttribute(name = "deployEJB")
    protected Boolean deployEJB;

    /**
     * Gets the value of the customGenerationUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customGenerationUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomGenerationUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomGenerationUnitType }
     * 
     * 
     */
    public List<CustomGenerationUnitType> getCustomGenerationUnit() {
        if (customGenerationUnit == null) {
            customGenerationUnit = new ArrayList<CustomGenerationUnitType>();
        }
        return this.customGenerationUnit;
    }

    /**
     * Obtient la valeur de la propri�t� rewritePojos.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isRewritePojos() {
        if (rewritePojos == null) {
            return true;
        } else {
            return rewritePojos;
        }
    }

    /**
     * D�finit la valeur de la propri�t� rewritePojos.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRewritePojos(Boolean value) {
        this.rewritePojos = value;
    }

    /**
     * Obtient la valeur de la propri�t� generateEJB.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isGenerateEJB() {
        if (generateEJB == null) {
            return true;
        } else {
            return generateEJB;
        }
    }

    /**
     * D�finit la valeur de la propri�t� generateEJB.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGenerateEJB(Boolean value) {
        this.generateEJB = value;
    }

    /**
     * Obtient la valeur de la propri�t� generatePersistenceXml.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isGeneratePersistenceXml() {
        if (generatePersistenceXml == null) {
            return true;
        } else {
            return generatePersistenceXml;
        }
    }

    /**
     * D�finit la valeur de la propri�t� generatePersistenceXml.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGeneratePersistenceXml(Boolean value) {
        this.generatePersistenceXml = value;
    }

    /**
     * Obtient la valeur de la propri�t� deployEJB.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDeployEJB() {
        if (deployEJB == null) {
            return true;
        } else {
            return deployEJB;
        }
    }

    /**
     * D�finit la valeur de la propri�t� deployEJB.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeployEJB(Boolean value) {
        this.deployEJB = value;
    }

}
