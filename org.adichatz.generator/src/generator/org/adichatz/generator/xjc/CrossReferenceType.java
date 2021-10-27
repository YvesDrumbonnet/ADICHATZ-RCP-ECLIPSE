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
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour crossReferenceType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="crossReferenceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="entitySetId" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="axmlDetailURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="axmlTableURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="axmlQueryURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="preferenceURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "crossReferenceType")
public class CrossReferenceType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "entitySetId")
    protected String entitySetId;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "axmlDetailURI")
    protected String axmlDetailURI;
    @XmlAttribute(name = "axmlTableURI")
    protected String axmlTableURI;
    @XmlAttribute(name = "axmlQueryURI")
    protected String axmlQueryURI;
    @XmlAttribute(name = "preferenceURI")
    protected String preferenceURI;

    /**
     * Obtient la valeur de la propriété entitySetId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntitySetId() {
        return entitySetId;
    }

    /**
     * Définit la valeur de la propriété entitySetId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntitySetId(String value) {
        this.entitySetId = value;
    }

    /**
     * Obtient la valeur de la propriété description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la valeur de la propriété description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtient la valeur de la propriété axmlDetailURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAxmlDetailURI() {
        return axmlDetailURI;
    }

    /**
     * Définit la valeur de la propriété axmlDetailURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAxmlDetailURI(String value) {
        this.axmlDetailURI = value;
    }

    /**
     * Obtient la valeur de la propriété axmlTableURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAxmlTableURI() {
        return axmlTableURI;
    }

    /**
     * Définit la valeur de la propriété axmlTableURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAxmlTableURI(String value) {
        this.axmlTableURI = value;
    }

    /**
     * Obtient la valeur de la propriété axmlQueryURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAxmlQueryURI() {
        return axmlQueryURI;
    }

    /**
     * Définit la valeur de la propriété axmlQueryURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAxmlQueryURI(String value) {
        this.axmlQueryURI = value;
    }

    /**
     * Obtient la valeur de la propriété preferenceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferenceURI() {
        return preferenceURI;
    }

    /**
     * Définit la valeur de la propriété preferenceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferenceURI(String value) {
        this.preferenceURI = value;
    }

}
