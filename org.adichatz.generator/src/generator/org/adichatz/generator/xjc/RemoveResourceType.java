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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour removeResourceType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="removeResourceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="targetURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="actionWhen" type="{}actionWhenTypeEnum" default="WHEN_BUILDING_MODEL" /&amp;gt;
 *       &amp;lt;attribute name="relative" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *       &amp;lt;attribute name="throwError" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeResourceType", propOrder = {
    "condition"
})
@XmlSeeAlso({
    CopyResourceType.class
})
public class RemoveResourceType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String condition;
    @XmlAttribute(name = "targetURI", required = true)
    protected String targetURI;
    @XmlAttribute(name = "actionWhen")
    protected ActionWhenTypeEnum actionWhen;
    @XmlAttribute(name = "relative")
    protected Boolean relative;
    @XmlAttribute(name = "throwError")
    protected Boolean throwError;

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
     * Obtient la valeur de la propriété targetURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetURI() {
        return targetURI;
    }

    /**
     * Définit la valeur de la propriété targetURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetURI(String value) {
        this.targetURI = value;
    }

    /**
     * Obtient la valeur de la propriété actionWhen.
     * 
     * @return
     *     possible object is
     *     {@link ActionWhenTypeEnum }
     *     
     */
    public ActionWhenTypeEnum getActionWhen() {
        if (actionWhen == null) {
            return ActionWhenTypeEnum.WHEN_BUILDING_MODEL;
        } else {
            return actionWhen;
        }
    }

    /**
     * Définit la valeur de la propriété actionWhen.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionWhenTypeEnum }
     *     
     */
    public void setActionWhen(ActionWhenTypeEnum value) {
        this.actionWhen = value;
    }

    /**
     * Obtient la valeur de la propriété relative.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isRelative() {
        if (relative == null) {
            return false;
        } else {
            return relative;
        }
    }

    /**
     * Définit la valeur de la propriété relative.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRelative(Boolean value) {
        this.relative = value;
    }

    /**
     * Obtient la valeur de la propriété throwError.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isThrowError() {
        if (throwError == null) {
            return false;
        } else {
            return throwError;
        }
    }

    /**
     * Définit la valeur de la propriété throwError.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setThrowError(Boolean value) {
        this.throwError = value;
    }

}
