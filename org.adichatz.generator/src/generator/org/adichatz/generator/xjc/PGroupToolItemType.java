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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour pGroupToolItemType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� itemCode.
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
     * D�finit la valeur de la propri�t� itemCode.
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
     * Obtient la valeur de la propri�t� actionClassName.
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
     * D�finit la valeur de la propri�t� actionClassName.
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
     * Obtient la valeur de la propri�t� itemClassName.
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
     * D�finit la valeur de la propri�t� itemClassName.
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
     * Obtient la valeur de la propri�t� image.
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
     * D�finit la valeur de la propri�t� image.
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
     * Obtient la valeur de la propri�t� rank.
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
     * D�finit la valeur de la propri�t� rank.
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
     * Obtient la valeur de la propri�t� text.
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
     * D�finit la valeur de la propri�t� text.
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
