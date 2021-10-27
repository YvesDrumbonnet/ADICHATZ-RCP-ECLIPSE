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
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour argPShelfType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="argPShelfType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}fieldContainerType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="labelProvider" type="{}labelProviderType"/&amp;gt;
 *         &amp;lt;element name="initValues" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="selection" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="displayedValues" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="values" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="labelProviderClassName" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="shelfRenderer" type="{}shelfRendererEnum" default="Redmond" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getArgPShelfType", name = "argPShelfType", propOrder = {
    "labelProvider",
    "initValues"
})
public class ArgPShelfType
    extends FieldContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected LabelProviderType labelProvider;
    @XmlElement(required = true)
    protected String initValues;
    @XmlAttribute(name = "selection")
    protected String selection;
    @XmlAttribute(name = "displayedValues")
    protected String displayedValues;
    @XmlAttribute(name = "values", required = true)
    protected String values;
    @XmlAttribute(name = "labelProviderClassName")
    protected String labelProviderClassName;
    @XmlAttribute(name = "shelfRenderer")
    protected ShelfRendererEnum shelfRenderer;

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
     * Obtient la valeur de la propriété selection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelection() {
        return selection;
    }

    /**
     * Définit la valeur de la propriété selection.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelection(String value) {
        this.selection = value;
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
     * Obtient la valeur de la propriété shelfRenderer.
     * 
     * @return
     *     possible object is
     *     {@link ShelfRendererEnum }
     *     
     */
    public ShelfRendererEnum getShelfRenderer() {
        if (shelfRenderer == null) {
            return ShelfRendererEnum.REDMOND;
        } else {
            return shelfRenderer;
        }
    }

    /**
     * Définit la valeur de la propriété shelfRenderer.
     * 
     * @param value
     *     allowed object is
     *     {@link ShelfRendererEnum }
     *     
     */
    public void setShelfRenderer(ShelfRendererEnum value) {
        this.shelfRenderer = value;
    }

}
