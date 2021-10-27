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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour customPostActionType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="customPostActionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="customGenerationUnit" type="{}customGenerationUnitType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="rewritePojos" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&amp;gt;
 *       &amp;lt;attribute name="generateEJB" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&amp;gt;
 *       &amp;lt;attribute name="generatePersistenceXml" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&amp;gt;
 *       &amp;lt;attribute name="deployEJB" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the customGenerationUnit property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCustomGenerationUnit().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
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
     * Obtient la valeur de la propriété rewritePojos.
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
     * Définit la valeur de la propriété rewritePojos.
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
     * Obtient la valeur de la propriété generateEJB.
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
     * Définit la valeur de la propriété generateEJB.
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
     * Obtient la valeur de la propriété generatePersistenceXml.
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
     * Définit la valeur de la propriété generatePersistenceXml.
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
     * Obtient la valeur de la propriété deployEJB.
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
     * Définit la valeur de la propriété deployEJB.
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
