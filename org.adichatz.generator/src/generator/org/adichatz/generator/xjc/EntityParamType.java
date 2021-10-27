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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Classe Java pour entityParamType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="entityParamType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}paramType"&amp;gt;
 *       &amp;lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="idvalue" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="binaryIdvalue" type="{http://www.w3.org/2001/XMLSchema}base64Binary" /&amp;gt;
 *       &amp;lt;attribute name="lazyFetches" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * Obtient la valeur de la propriété entityURI.
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
     * Définit la valeur de la propriété entityURI.
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
     * Obtient la valeur de la propriété idvalue.
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
     * Définit la valeur de la propriété idvalue.
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
     * Obtient la valeur de la propriété binaryIdvalue.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBinaryIdvalue() {
        return binaryIdvalue;
    }

    /**
     * Définit la valeur de la propriété binaryIdvalue.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBinaryIdvalue(byte[] value) {
        this.binaryIdvalue = value;
    }

    /**
     * Obtient la valeur de la propriété lazyFetches.
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
     * Définit la valeur de la propriété lazyFetches.
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
