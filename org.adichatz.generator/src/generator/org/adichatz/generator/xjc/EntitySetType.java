//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour entitySetType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="entitySetType">
 *   &lt;complexContent>
 *     &lt;extension base="{}fieldType">
 *       &lt;choice>
 *         &lt;element name="queryReference" type="{}queryReferenceType"/>
 *         &lt;element name="entityElements" type="{}entityElementsType"/>
 *       &lt;/choice>
 *       &lt;attribute name="entityURI" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="lazyFetches" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mappedBy" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="parentClause" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getEntitySetType", name = "entitySetType", propOrder = {
    "queryReference",
    "entityElements"
})
@XmlSeeAlso({
    OneToManyType.class,
    ManyToManyType.class
})
public class EntitySetType
    extends FieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected QueryReferenceType queryReference;
    protected EntityElementsType entityElements;
    @XmlAttribute(name = "entityURI", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String entityURI;
    @XmlAttribute(name = "lazyFetches")
    protected String lazyFetches;
    @XmlAttribute(name = "mappedBy", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String mappedBy;
    @XmlAttribute(name = "parentClause", required = true)
    protected String parentClause;

    /**
     * Obtient la valeur de la propriété queryReference.
     * 
     * @return
     *     possible object is
     *     {@link QueryReferenceType }
     *     
     */
    public QueryReferenceType getQueryReference() {
        return queryReference;
    }

    /**
     * Définit la valeur de la propriété queryReference.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryReferenceType }
     *     
     */
    public void setQueryReference(QueryReferenceType value) {
        this.queryReference = value;
    }

    /**
     * Obtient la valeur de la propriété entityElements.
     * 
     * @return
     *     possible object is
     *     {@link EntityElementsType }
     *     
     */
    public EntityElementsType getEntityElements() {
        return entityElements;
    }

    /**
     * Définit la valeur de la propriété entityElements.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityElementsType }
     *     
     */
    public void setEntityElements(EntityElementsType value) {
        this.entityElements = value;
    }

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

    /**
     * Obtient la valeur de la propriété mappedBy.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMappedBy() {
        return mappedBy;
    }

    /**
     * Définit la valeur de la propriété mappedBy.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMappedBy(String value) {
        this.mappedBy = value;
    }

    /**
     * Obtient la valeur de la propriété parentClause.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentClause() {
        return parentClause;
    }

    /**
     * Définit la valeur de la propriété parentClause.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentClause(String value) {
        this.parentClause = value;
    }

}
