//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour pluginEntityType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="pluginEntityType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;sequence>
 *         &lt;element name="icons" type="{}iconsType"/>
 *         &lt;element name="messages" type="{}messagesType"/>
 *         &lt;element name="params" type="{}paramsType"/>
 *         &lt;element name="generationUnit" type="{}generationUnitType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="propertyField" type="{}propertyFieldType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pojoRewriters" type="{}pojoRewritersType"/>
 *         &lt;element name="queryBuilder" type="{}queryBuilderType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="beanClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="uiBeanClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="entityURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="protectPojo" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="retrieveRoles" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mergeRoles" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="persistRoles" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="removeRoles" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getPluginEntityType", name = "pluginEntityType", propOrder = {
    "icons",
    "messages",
    "params",
    "generationUnit",
    "propertyField",
    "pojoRewriters",
    "queryBuilder"
})
public class PluginEntityType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected IconsType icons;
    @XmlElement(required = true)
    protected MessagesType messages;
    @XmlElement(required = true)
    protected ParamsType params;
    protected List<GenerationUnitType> generationUnit;
    protected List<PropertyFieldType> propertyField;
    @XmlElement(required = true)
    protected PojoRewritersType pojoRewriters;
    @XmlElement(required = true)
    protected QueryBuilderType queryBuilder;
    @XmlAttribute(name = "beanClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String beanClassName;
    @XmlAttribute(name = "uiBeanClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String uiBeanClassName;
    @XmlAttribute(name = "entityURI", required = true)
    protected String entityURI;
    @XmlAttribute(name = "protectPojo")
    protected Boolean protectPojo;
    @XmlAttribute(name = "retrieveRoles")
    protected String retrieveRoles;
    @XmlAttribute(name = "mergeRoles")
    protected String mergeRoles;
    @XmlAttribute(name = "persistRoles")
    protected String persistRoles;
    @XmlAttribute(name = "removeRoles")
    protected String removeRoles;

    /**
     * Obtient la valeur de la propri�t� icons.
     * 
     * @return
     *     possible object is
     *     {@link IconsType }
     *     
     */
    public IconsType getIcons() {
        return icons;
    }

    /**
     * D�finit la valeur de la propri�t� icons.
     * 
     * @param value
     *     allowed object is
     *     {@link IconsType }
     *     
     */
    public void setIcons(IconsType value) {
        this.icons = value;
    }

    /**
     * Obtient la valeur de la propri�t� messages.
     * 
     * @return
     *     possible object is
     *     {@link MessagesType }
     *     
     */
    public MessagesType getMessages() {
        return messages;
    }

    /**
     * D�finit la valeur de la propri�t� messages.
     * 
     * @param value
     *     allowed object is
     *     {@link MessagesType }
     *     
     */
    public void setMessages(MessagesType value) {
        this.messages = value;
    }

    /**
     * Obtient la valeur de la propri�t� params.
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
     * D�finit la valeur de la propri�t� params.
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
     * Gets the value of the generationUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generationUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenerationUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenerationUnitType }
     * 
     * 
     */
    public List<GenerationUnitType> getGenerationUnit() {
        if (generationUnit == null) {
            generationUnit = new ArrayList<GenerationUnitType>();
        }
        return this.generationUnit;
    }

    /**
     * Gets the value of the propertyField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyFieldType }
     * 
     * 
     */
    public List<PropertyFieldType> getPropertyField() {
        if (propertyField == null) {
            propertyField = new ArrayList<PropertyFieldType>();
        }
        return this.propertyField;
    }

    /**
     * Obtient la valeur de la propri�t� pojoRewriters.
     * 
     * @return
     *     possible object is
     *     {@link PojoRewritersType }
     *     
     */
    public PojoRewritersType getPojoRewriters() {
        return pojoRewriters;
    }

    /**
     * D�finit la valeur de la propri�t� pojoRewriters.
     * 
     * @param value
     *     allowed object is
     *     {@link PojoRewritersType }
     *     
     */
    public void setPojoRewriters(PojoRewritersType value) {
        this.pojoRewriters = value;
    }

    /**
     * Obtient la valeur de la propri�t� queryBuilder.
     * 
     * @return
     *     possible object is
     *     {@link QueryBuilderType }
     *     
     */
    public QueryBuilderType getQueryBuilder() {
        return queryBuilder;
    }

    /**
     * D�finit la valeur de la propri�t� queryBuilder.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryBuilderType }
     *     
     */
    public void setQueryBuilder(QueryBuilderType value) {
        this.queryBuilder = value;
    }

    /**
     * Obtient la valeur de la propri�t� beanClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeanClassName() {
        return beanClassName;
    }

    /**
     * D�finit la valeur de la propri�t� beanClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeanClassName(String value) {
        this.beanClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� uiBeanClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUiBeanClassName() {
        return uiBeanClassName;
    }

    /**
     * D�finit la valeur de la propri�t� uiBeanClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUiBeanClassName(String value) {
        this.uiBeanClassName = value;
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
     * Obtient la valeur de la propri�t� protectPojo.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isProtectPojo() {
        if (protectPojo == null) {
            return false;
        } else {
            return protectPojo;
        }
    }

    /**
     * D�finit la valeur de la propri�t� protectPojo.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setProtectPojo(Boolean value) {
        this.protectPojo = value;
    }

    /**
     * Obtient la valeur de la propri�t� retrieveRoles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetrieveRoles() {
        return retrieveRoles;
    }

    /**
     * D�finit la valeur de la propri�t� retrieveRoles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetrieveRoles(String value) {
        this.retrieveRoles = value;
    }

    /**
     * Obtient la valeur de la propri�t� mergeRoles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMergeRoles() {
        return mergeRoles;
    }

    /**
     * D�finit la valeur de la propri�t� mergeRoles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMergeRoles(String value) {
        this.mergeRoles = value;
    }

    /**
     * Obtient la valeur de la propri�t� persistRoles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersistRoles() {
        return persistRoles;
    }

    /**
     * D�finit la valeur de la propri�t� persistRoles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersistRoles(String value) {
        this.persistRoles = value;
    }

    /**
     * Obtient la valeur de la propri�t� removeRoles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoveRoles() {
        return removeRoles;
    }

    /**
     * D�finit la valeur de la propri�t� removeRoles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoveRoles(String value) {
        this.removeRoles = value;
    }

}
