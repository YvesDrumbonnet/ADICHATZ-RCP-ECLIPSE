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
 * &lt;p&gt;Classe Java pour dynamicClauseType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="dynamicClauseType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="conditionCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="listenedContainerId" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="listenedFieldId" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dynamicClauseType", propOrder = {
    "conditionCode",
    "postCode"
})
public class DynamicClauseType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected String conditionCode;
    protected String postCode;
    @XmlAttribute(name = "listenedContainerId")
    protected String listenedContainerId;
    @XmlAttribute(name = "listenedFieldId")
    protected String listenedFieldId;

    /**
     * Obtient la valeur de la propriété conditionCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionCode() {
        return conditionCode;
    }

    /**
     * Définit la valeur de la propriété conditionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionCode(String value) {
        this.conditionCode = value;
    }

    /**
     * Obtient la valeur de la propriété postCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Définit la valeur de la propriété postCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostCode(String value) {
        this.postCode = value;
    }

    /**
     * Obtient la valeur de la propriété listenedContainerId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListenedContainerId() {
        return listenedContainerId;
    }

    /**
     * Définit la valeur de la propriété listenedContainerId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListenedContainerId(String value) {
        this.listenedContainerId = value;
    }

    /**
     * Obtient la valeur de la propriété listenedFieldId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListenedFieldId() {
        return listenedFieldId;
    }

    /**
     * Définit la valeur de la propriété listenedFieldId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListenedFieldId(String value) {
        this.listenedFieldId = value;
    }

}
