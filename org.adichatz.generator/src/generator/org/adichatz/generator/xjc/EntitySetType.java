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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour entitySetType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� queryReference.
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
     * D�finit la valeur de la propri�t� queryReference.
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
     * Obtient la valeur de la propri�t� entityElements.
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
     * D�finit la valeur de la propri�t� entityElements.
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

    /**
     * Obtient la valeur de la propri�t� mappedBy.
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
     * D�finit la valeur de la propri�t� mappedBy.
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
     * Obtient la valeur de la propri�t� parentClause.
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
     * D�finit la valeur de la propri�t� parentClause.
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
