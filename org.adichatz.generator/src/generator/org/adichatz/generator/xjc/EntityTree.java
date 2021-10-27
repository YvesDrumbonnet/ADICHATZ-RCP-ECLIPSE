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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Classe Java pour anonymous complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}basicType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="propertyField" type="{}propertyFieldType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="oneToMany" type="{}oneToManyType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="manyToMany" type="{}manyToManyType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="oneToOne" type="{}oneToOneType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="superEntityURI" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="idFieldName" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="callbackClassNames" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="callforeClassNames" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="compositeKeyStrategyFactoryClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getEntityTree", name = "", propOrder = {
    "propertyField",
    "oneToMany",
    "manyToMany",
    "oneToOne"
})
@XmlRootElement(name = "entityTree")
public class EntityTree
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<PropertyFieldType> propertyField;
    protected List<OneToManyType> oneToMany;
    protected List<ManyToManyType> manyToMany;
    protected List<OneToOneType> oneToOne;
    @XmlAttribute(name = "entityURI")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String entityURI;
    @XmlAttribute(name = "superEntityURI")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String superEntityURI;
    @XmlAttribute(name = "idFieldName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String idFieldName;
    @XmlAttribute(name = "callbackClassNames")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String callbackClassNames;
    @XmlAttribute(name = "callforeClassNames")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String callforeClassNames;
    @XmlAttribute(name = "compositeKeyStrategyFactoryClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String compositeKeyStrategyFactoryClassName;

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
     * Gets the value of the oneToMany property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the oneToMany property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOneToMany().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link OneToManyType }
     * 
     * 
     */
    public List<OneToManyType> getOneToMany() {
        if (oneToMany == null) {
            oneToMany = new ArrayList<OneToManyType>();
        }
        return this.oneToMany;
    }

    /**
     * Gets the value of the manyToMany property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the manyToMany property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getManyToMany().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ManyToManyType }
     * 
     * 
     */
    public List<ManyToManyType> getManyToMany() {
        if (manyToMany == null) {
            manyToMany = new ArrayList<ManyToManyType>();
        }
        return this.manyToMany;
    }

    /**
     * Gets the value of the oneToOne property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the oneToOne property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOneToOne().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link OneToOneType }
     * 
     * 
     */
    public List<OneToOneType> getOneToOne() {
        if (oneToOne == null) {
            oneToOne = new ArrayList<OneToOneType>();
        }
        return this.oneToOne;
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
     * Obtient la valeur de la propriété superEntityURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuperEntityURI() {
        return superEntityURI;
    }

    /**
     * Définit la valeur de la propriété superEntityURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuperEntityURI(String value) {
        this.superEntityURI = value;
    }

    /**
     * Obtient la valeur de la propriété idFieldName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdFieldName() {
        return idFieldName;
    }

    /**
     * Définit la valeur de la propriété idFieldName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdFieldName(String value) {
        this.idFieldName = value;
    }

    /**
     * Obtient la valeur de la propriété callbackClassNames.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallbackClassNames() {
        return callbackClassNames;
    }

    /**
     * Définit la valeur de la propriété callbackClassNames.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallbackClassNames(String value) {
        this.callbackClassNames = value;
    }

    /**
     * Obtient la valeur de la propriété callforeClassNames.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallforeClassNames() {
        return callforeClassNames;
    }

    /**
     * Définit la valeur de la propriété callforeClassNames.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallforeClassNames(String value) {
        this.callforeClassNames = value;
    }

    /**
     * Obtient la valeur de la propriété compositeKeyStrategyFactoryClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompositeKeyStrategyFactoryClassName() {
        return compositeKeyStrategyFactoryClassName;
    }

    /**
     * Définit la valeur de la propriété compositeKeyStrategyFactoryClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompositeKeyStrategyFactoryClassName(String value) {
        this.compositeKeyStrategyFactoryClassName = value;
    }

}
