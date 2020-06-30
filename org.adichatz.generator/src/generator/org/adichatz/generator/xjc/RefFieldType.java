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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour refFieldType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="refFieldType">
 *   &lt;complexContent>
 *     &lt;extension base="{}propertyFieldType">
 *       &lt;attribute name="entityURI" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="lazyFetchMember" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="parentMappedBy" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getRefFieldType", name = "refFieldType")
public class RefFieldType
    extends PropertyFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "entityURI", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String entityURI;
    @XmlAttribute(name = "lazyFetchMember")
    protected String lazyFetchMember;
    @XmlAttribute(name = "parentMappedBy")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String parentMappedBy;

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
     * Obtient la valeur de la propriété lazyFetchMember.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLazyFetchMember() {
        return lazyFetchMember;
    }

    /**
     * Définit la valeur de la propriété lazyFetchMember.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLazyFetchMember(String value) {
        this.lazyFetchMember = value;
    }

    /**
     * Obtient la valeur de la propriété parentMappedBy.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentMappedBy() {
        return parentMappedBy;
    }

    /**
     * Définit la valeur de la propriété parentMappedBy.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentMappedBy(String value) {
        this.parentMappedBy = value;
    }

}
