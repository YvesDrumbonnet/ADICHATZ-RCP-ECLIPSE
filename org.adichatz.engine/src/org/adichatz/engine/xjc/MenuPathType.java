//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:21 PM CEST 
//


package org.adichatz.engine.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour menuPathType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="menuPathType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="adiResourceURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="menuPathURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="uniqueInstance" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menuPathType")
public class MenuPathType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String id;
    @XmlAttribute(name = "adiResourceURI")
    protected String adiResourceURI;
    @XmlAttribute(name = "menuPathURI")
    protected String menuPathURI;
    @XmlAttribute(name = "uniqueInstance")
    protected Boolean uniqueInstance;

    /**
     * Obtient la valeur de la propri�t� id.
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
     * D�finit la valeur de la propri�t� id.
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
     * Obtient la valeur de la propri�t� adiResourceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdiResourceURI() {
        return adiResourceURI;
    }

    /**
     * D�finit la valeur de la propri�t� adiResourceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdiResourceURI(String value) {
        this.adiResourceURI = value;
    }

    /**
     * Obtient la valeur de la propri�t� menuPathURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenuPathURI() {
        return menuPathURI;
    }

    /**
     * D�finit la valeur de la propri�t� menuPathURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenuPathURI(String value) {
        this.menuPathURI = value;
    }

    /**
     * Obtient la valeur de la propri�t� uniqueInstance.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isUniqueInstance() {
        if (uniqueInstance == null) {
            return false;
        } else {
            return uniqueInstance;
        }
    }

    /**
     * D�finit la valeur de la propri�t� uniqueInstance.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUniqueInstance(Boolean value) {
        this.uniqueInstance = value;
    }

}
