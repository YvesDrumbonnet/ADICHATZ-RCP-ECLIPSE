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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour pGroupToolItemType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="pGroupToolItemType">
 *   &lt;complexContent>
 *     &lt;extension base="{}widgetType">
 *       &lt;sequence>
 *         &lt;element name="params" type="{}paramsType"/>
 *         &lt;element name="itemCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="actionClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="itemClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="rank" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getPGroupToolItemType", name = "pGroupToolItemType", propOrder = {
    "params",
    "itemCode"
})
public class PGroupToolItemType
    extends WidgetType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ParamsType params;
    @XmlElement(required = true)
    protected String itemCode;
    @XmlAttribute(name = "actionClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String actionClassName;
    @XmlAttribute(name = "itemClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String itemClassName;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "rank")
    protected Integer rank;
    @XmlAttribute(name = "text")
    protected String text;

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
     * Obtient la valeur de la propriété itemCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Définit la valeur de la propriété itemCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCode(String value) {
        this.itemCode = value;
    }

    /**
     * Obtient la valeur de la propriété actionClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionClassName() {
        return actionClassName;
    }

    /**
     * Définit la valeur de la propriété actionClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionClassName(String value) {
        this.actionClassName = value;
    }

    /**
     * Obtient la valeur de la propriété itemClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemClassName() {
        return itemClassName;
    }

    /**
     * Définit la valeur de la propriété itemClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemClassName(String value) {
        this.itemClassName = value;
    }

    /**
     * Obtient la valeur de la propriété image.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Définit la valeur de la propriété image.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Obtient la valeur de la propriété rank.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Définit la valeur de la propriété rank.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRank(Integer value) {
        this.rank = value;
    }

    /**
     * Obtient la valeur de la propriété text.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Définit la valeur de la propriété text.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

}
