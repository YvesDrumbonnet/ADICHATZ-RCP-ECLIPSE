//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour dynamicClauseType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="dynamicClauseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="conditionCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="postCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *       &lt;attribute name="listenedContainerId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="listenedFieldId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
