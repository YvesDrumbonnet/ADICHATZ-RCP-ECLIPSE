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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour refControlType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="refControlType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;sequence>
 *         &lt;element name="labelProvider" type="{}labelProviderType"/>
 *         &lt;element name="initValues" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="queryReference" type="{}queryReferenceType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="poolQueryResult" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="values" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="displayedValues" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="labelProviderClassName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="preferenceURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "refControlType", propOrder = {
    "labelProvider",
    "initValues",
    "queryReference"
})
@XmlSeeAlso({
    CComboType.class,
    ComboType.class,
    MultiChoiceType.class,
    RadioGroupType.class,
    RefTextType.class
})
public class RefControlType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected LabelProviderType labelProvider;
    @XmlElement(required = true)
    protected String initValues;
    @XmlElement(required = true)
    protected QueryReferenceType queryReference;
    @XmlAttribute(name = "poolQueryResult")
    protected String poolQueryResult;
    @XmlAttribute(name = "values")
    protected String values;
    @XmlAttribute(name = "displayedValues")
    protected String displayedValues;
    @XmlAttribute(name = "labelProviderClassName")
    protected String labelProviderClassName;
    @XmlAttribute(name = "preferenceURI")
    protected String preferenceURI;

    /**
     * Obtient la valeur de la propri�t� labelProvider.
     * 
     * @return
     *     possible object is
     *     {@link LabelProviderType }
     *     
     */
    public LabelProviderType getLabelProvider() {
        return labelProvider;
    }

    /**
     * D�finit la valeur de la propri�t� labelProvider.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelProviderType }
     *     
     */
    public void setLabelProvider(LabelProviderType value) {
        this.labelProvider = value;
    }

    /**
     * Obtient la valeur de la propri�t� initValues.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitValues() {
        return initValues;
    }

    /**
     * D�finit la valeur de la propri�t� initValues.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitValues(String value) {
        this.initValues = value;
    }

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
     * Obtient la valeur de la propri�t� poolQueryResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoolQueryResult() {
        return poolQueryResult;
    }

    /**
     * D�finit la valeur de la propri�t� poolQueryResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoolQueryResult(String value) {
        this.poolQueryResult = value;
    }

    /**
     * Obtient la valeur de la propri�t� values.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValues() {
        return values;
    }

    /**
     * D�finit la valeur de la propri�t� values.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValues(String value) {
        this.values = value;
    }

    /**
     * Obtient la valeur de la propri�t� displayedValues.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayedValues() {
        return displayedValues;
    }

    /**
     * D�finit la valeur de la propri�t� displayedValues.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayedValues(String value) {
        this.displayedValues = value;
    }

    /**
     * Obtient la valeur de la propri�t� labelProviderClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelProviderClassName() {
        return labelProviderClassName;
    }

    /**
     * D�finit la valeur de la propri�t� labelProviderClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelProviderClassName(String value) {
        this.labelProviderClassName = value;
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
