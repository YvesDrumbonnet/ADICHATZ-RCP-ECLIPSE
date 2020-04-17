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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour entityParamType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="entityParamType">
 *   &lt;complexContent>
 *     &lt;extension base="{}paramType">
 *       &lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="idvalue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="binaryIdvalue" type="{http://www.w3.org/2001/XMLSchema}base64Binary" />
 *       &lt;attribute name="lazyFetches" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entityParamType")
public class EntityParamType
    extends ParamType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "entityURI")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String entityURI;
    @XmlAttribute(name = "idvalue")
    protected String idvalue;
    @XmlAttribute(name = "binaryIdvalue")
    protected byte[] binaryIdvalue;
    @XmlAttribute(name = "lazyFetches")
    protected String lazyFetches;

    /**
     * Obtient la valeur de la propri�t� entityURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityURI() {
        return entityURI;
    }

    /**
     * D�finit la valeur de la propri�t� entityURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityURI(String value) {
        this.entityURI = value;
    }

    /**
     * Obtient la valeur de la propri�t� idvalue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdvalue() {
        return idvalue;
    }

    /**
     * D�finit la valeur de la propri�t� idvalue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdvalue(String value) {
        this.idvalue = value;
    }

    /**
     * Obtient la valeur de la propri�t� binaryIdvalue.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBinaryIdvalue() {
        return binaryIdvalue;
    }

    /**
     * D�finit la valeur de la propri�t� binaryIdvalue.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBinaryIdvalue(byte[] value) {
        this.binaryIdvalue = value;
    }

    /**
     * Obtient la valeur de la propri�t� lazyFetches.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLazyFetches() {
        return lazyFetches;
    }

    /**
     * D�finit la valeur de la propri�t� lazyFetches.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLazyFetches(String value) {
        this.lazyFetches = value;
    }

}
