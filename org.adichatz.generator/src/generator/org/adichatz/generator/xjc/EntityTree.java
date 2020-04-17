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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;sequence>
 *         &lt;element name="propertyField" type="{}propertyFieldType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="oneToMany" type="{}oneToManyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="manyToMany" type="{}manyToManyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="oneToOne" type="{}oneToOneType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="superEntityURI" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="idFieldName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="callbackClassNames" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="callforeClassNames" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="compositeKeyStrategyFactoryClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Gets the value of the oneToMany property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oneToMany property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOneToMany().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the manyToMany property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getManyToMany().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the oneToOne property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOneToOne().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * Obtient la valeur de la propri�t� superEntityURI.
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
     * D�finit la valeur de la propri�t� superEntityURI.
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
     * Obtient la valeur de la propri�t� idFieldName.
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
     * D�finit la valeur de la propri�t� idFieldName.
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
     * Obtient la valeur de la propri�t� callbackClassNames.
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
     * D�finit la valeur de la propri�t� callbackClassNames.
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
     * Obtient la valeur de la propri�t� callforeClassNames.
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
     * D�finit la valeur de la propri�t� callforeClassNames.
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
     * Obtient la valeur de la propri�t� compositeKeyStrategyFactoryClassName.
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
     * D�finit la valeur de la propri�t� compositeKeyStrategyFactoryClassName.
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
