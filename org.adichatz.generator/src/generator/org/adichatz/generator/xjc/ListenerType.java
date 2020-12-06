//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour listenerType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="listenerType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="listenerTypes" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="listenerClassName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listenerType", propOrder = {
    "code"
})
public class ListenerType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String code;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "listenerTypes")
    protected String listenerTypes;
    @XmlAttribute(name = "listenerClassName")
    protected String listenerClassName;

    /**
     * Obtient la valeur de la propriété code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Définit la valeur de la propriété code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Obtient la valeur de la propriété id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété listenerTypes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListenerTypes() {
        return listenerTypes;
    }

    /**
     * Définit la valeur de la propriété listenerTypes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListenerTypes(String value) {
        this.listenerTypes = value;
    }

    /**
     * Obtient la valeur de la propriété listenerClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListenerClassName() {
        return listenerClassName;
    }

    /**
     * Définit la valeur de la propriété listenerClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListenerClassName(String value) {
        this.listenerClassName = value;
    }

}
