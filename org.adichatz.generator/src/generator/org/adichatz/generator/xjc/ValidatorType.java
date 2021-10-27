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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Classe Java pour validatorType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="validatorType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}basicType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="errorWhen" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="warningWhen" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="validatorClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="hostingControllerIds" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="warningMessage" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validatorType", propOrder = {
    "errorWhen",
    "warningWhen"
})
public class ValidatorType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String errorWhen;
    @XmlElement(required = true)
    protected String warningWhen;
    @XmlAttribute(name = "key", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String key;
    @XmlAttribute(name = "validatorClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String validatorClassName;
    @XmlAttribute(name = "hostingControllerIds")
    protected String hostingControllerIds;
    @XmlAttribute(name = "errorMessage")
    protected String errorMessage;
    @XmlAttribute(name = "warningMessage")
    protected String warningMessage;

    /**
     * Obtient la valeur de la propriété errorWhen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorWhen() {
        return errorWhen;
    }

    /**
     * Définit la valeur de la propriété errorWhen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorWhen(String value) {
        this.errorWhen = value;
    }

    /**
     * Obtient la valeur de la propriété warningWhen.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarningWhen() {
        return warningWhen;
    }

    /**
     * Définit la valeur de la propriété warningWhen.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarningWhen(String value) {
        this.warningWhen = value;
    }

    /**
     * Obtient la valeur de la propriété key.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Définit la valeur de la propriété key.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Obtient la valeur de la propriété validatorClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidatorClassName() {
        return validatorClassName;
    }

    /**
     * Définit la valeur de la propriété validatorClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidatorClassName(String value) {
        this.validatorClassName = value;
    }

    /**
     * Obtient la valeur de la propriété hostingControllerIds.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostingControllerIds() {
        return hostingControllerIds;
    }

    /**
     * Définit la valeur de la propriété hostingControllerIds.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostingControllerIds(String value) {
        this.hostingControllerIds = value;
    }

    /**
     * Obtient la valeur de la propriété errorMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Définit la valeur de la propriété errorMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Obtient la valeur de la propriété warningMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarningMessage() {
        return warningMessage;
    }

    /**
     * Définit la valeur de la propriété warningMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarningMessage(String value) {
        this.warningMessage = value;
    }

}
