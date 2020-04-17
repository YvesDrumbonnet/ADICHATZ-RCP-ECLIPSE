//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:17 AM CET 
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
 * <p>Classe Java pour removeResourceType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="removeResourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="condition" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="targetURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="actionWhen" type="{}actionWhenTypeEnum" default="WHEN_BUILDING_MODEL" />
 *       &lt;attribute name="relative" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="throwError" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Obtient la valeur de la propri�t� targetURI.
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
     * D�finit la valeur de la propri�t� targetURI.
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
     * Obtient la valeur de la propri�t� actionWhen.
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
     * D�finit la valeur de la propri�t� actionWhen.
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
     * Obtient la valeur de la propri�t� relative.
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
     * D�finit la valeur de la propri�t� relative.
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
     * Obtient la valeur de la propri�t� throwError.
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
     * D�finit la valeur de la propri�t� throwError.
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
