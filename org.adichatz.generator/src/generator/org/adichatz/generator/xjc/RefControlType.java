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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour refControlType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="refControlType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}controlFieldType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="labelProvider" type="{}labelProviderType"/&amp;gt;
 *         &amp;lt;element name="initValues" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="queryReference" type="{}queryReferenceType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="poolQueryResult" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="values" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="displayedValues" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="labelProviderClassName" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="preferenceURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * Obtient la valeur de la propriété labelProvider.
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
     * Définit la valeur de la propriété labelProvider.
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
     * Obtient la valeur de la propriété initValues.
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
     * Définit la valeur de la propriété initValues.
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
     * Obtient la valeur de la propriété poolQueryResult.
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
     * Définit la valeur de la propriété poolQueryResult.
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
     * Obtient la valeur de la propriété values.
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
     * Définit la valeur de la propriété values.
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
     * Obtient la valeur de la propriété displayedValues.
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
     * Définit la valeur de la propriété displayedValues.
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
     * Obtient la valeur de la propriété labelProviderClassName.
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
     * Définit la valeur de la propriété labelProviderClassName.
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
     * Obtient la valeur de la propriété preferenceURI.
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
     * Définit la valeur de la propriété preferenceURI.
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
