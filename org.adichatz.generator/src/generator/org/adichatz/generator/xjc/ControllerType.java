//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour controllerType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="controllerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="wrapperClassName" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="controllerClassName" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "controllerType")
public class ControllerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "wrapperClassName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String wrapperClassName;
    @XmlAttribute(name = "controllerClassName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String controllerClassName;

    /**
     * Obtient la valeur de la propri�t� wrapperClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWrapperClassName() {
        return wrapperClassName;
    }

    /**
     * D�finit la valeur de la propri�t� wrapperClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWrapperClassName(String value) {
        this.wrapperClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� controllerClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControllerClassName() {
        return controllerClassName;
    }

    /**
     * D�finit la valeur de la propri�t� controllerClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControllerClassName(String value) {
        this.controllerClassName = value;
    }

}
