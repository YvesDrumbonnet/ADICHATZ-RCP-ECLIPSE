//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour queryReferenceType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="queryReferenceType">
 *   &lt;complexContent>
 *     &lt;extension base="{}elementType">
 *       &lt;attribute name="adiQueryURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="preferenceURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryReferenceType")
public class QueryReferenceType
    extends ElementType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "adiQueryURI", required = true)
    protected String adiQueryURI;
    @XmlAttribute(name = "preferenceURI")
    protected String preferenceURI;

    /**
     * Obtient la valeur de la propri�t� adiQueryURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdiQueryURI() {
        return adiQueryURI;
    }

    /**
     * D�finit la valeur de la propri�t� adiQueryURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdiQueryURI(String value) {
        this.adiQueryURI = value;
    }

    /**
     * Obtient la valeur de la propri�t� preferenceURI.
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
     * D�finit la valeur de la propri�t� preferenceURI.
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
