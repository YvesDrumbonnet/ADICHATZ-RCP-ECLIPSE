//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
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
 * &lt;p&gt;Classe Java pour pluginEntityType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="pluginEntityType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}basicType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="icons" type="{}iconsType"/&amp;gt;
 *         &amp;lt;element name="messages" type="{}messagesType"/&amp;gt;
 *         &amp;lt;element name="params" type="{}paramsType"/&amp;gt;
 *         &amp;lt;element name="generationUnit" type="{}generationUnitType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="propertyField" type="{}propertyFieldType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="pojoRewriters" type="{}pojoRewritersType"/&amp;gt;
 *         &amp;lt;element name="queryBuilder" type="{}queryBuilderType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="beanClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="uiBeanClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="entityURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="protectPojo" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *       &amp;lt;attribute name="retrieveRoles" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="mergeRoles" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="persistRoles" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="removeRoles" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * Obtient la valeur de la propriété icons.
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
     * Définit la valeur de la propriété icons.
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
     * Obtient la valeur de la propriété messages.
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
     * Définit la valeur de la propriété messages.
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
     * Obtient la valeur de la propriété params.
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
     * Définit la valeur de la propriété params.
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
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the generationUnit property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGenerationUnit().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
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
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the propertyField property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPropertyField().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
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
     * Obtient la valeur de la propriété pojoRewriters.
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
     * Définit la valeur de la propriété pojoRewriters.
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
     * Obtient la valeur de la propriété queryBuilder.
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
     * Définit la valeur de la propriété queryBuilder.
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
     * Obtient la valeur de la propriété beanClassName.
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
     * Définit la valeur de la propriété beanClassName.
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
     * Obtient la valeur de la propriété uiBeanClassName.
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
     * Définit la valeur de la propriété uiBeanClassName.
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
     * Obtient la valeur de la propriété protectPojo.
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
     * Définit la valeur de la propriété protectPojo.
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
     * Obtient la valeur de la propriété retrieveRoles.
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
     * Définit la valeur de la propriété retrieveRoles.
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
     * Obtient la valeur de la propriété mergeRoles.
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
     * Définit la valeur de la propriété mergeRoles.
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
     * Obtient la valeur de la propriété persistRoles.
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
     * Définit la valeur de la propriété persistRoles.
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
     * Obtient la valeur de la propriété removeRoles.
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
     * Définit la valeur de la propriété removeRoles.
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
