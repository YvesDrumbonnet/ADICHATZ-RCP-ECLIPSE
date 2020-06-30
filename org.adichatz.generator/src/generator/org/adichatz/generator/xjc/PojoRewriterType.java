//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour pojoRewriterType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="pojoRewriterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="params" type="{}paramsType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="rewriterClassName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="entityRegex" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="propertyRegex" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="priority" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pojoRewriterType", propOrder = {
    "params"
})
public class PojoRewriterType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ParamsType params;
    @XmlAttribute(name = "rewriterClassName")
    protected String rewriterClassName;
    @XmlAttribute(name = "entityRegex")
    protected String entityRegex;
    @XmlAttribute(name = "propertyRegex")
    protected String propertyRegex;
    @XmlAttribute(name = "priority")
    protected Integer priority;

    /**
     * Obtient la valeur de la propriété params.
     * 
     * @return
     *     possible object is
     *     {@link ParamsType }
     *     
     */
    public ParamsType getParams() {
        return params;
    }

    /**
     * Définit la valeur de la propriété params.
     * 
     * @param value
     *     allowed object is
     *     {@link ParamsType }
     *     
     */
    public void setParams(ParamsType value) {
        this.params = value;
    }

    /**
     * Obtient la valeur de la propriété rewriterClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRewriterClassName() {
        return rewriterClassName;
    }

    /**
     * Définit la valeur de la propriété rewriterClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRewriterClassName(String value) {
        this.rewriterClassName = value;
    }

    /**
     * Obtient la valeur de la propriété entityRegex.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityRegex() {
        return entityRegex;
    }

    /**
     * Définit la valeur de la propriété entityRegex.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityRegex(String value) {
        this.entityRegex = value;
    }

    /**
     * Obtient la valeur de la propriété propertyRegex.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyRegex() {
        return propertyRegex;
    }

    /**
     * Définit la valeur de la propriété propertyRegex.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyRegex(String value) {
        this.propertyRegex = value;
    }

    /**
     * Obtient la valeur de la propriété priority.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Définit la valeur de la propriété priority.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriority(Integer value) {
        this.priority = value;
    }

}
