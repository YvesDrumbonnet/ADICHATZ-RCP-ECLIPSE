//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour extraTextType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="extraTextType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;attribute name="addRefreshItem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="editable" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tabs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="textLimit" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getExtraTextType", name = "extraTextType")
public class ExtraTextType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "addRefreshItem")
    protected String addRefreshItem;
    @XmlAttribute(name = "editable")
    protected String editable;
    @XmlAttribute(name = "orientation")
    protected String orientation;
    @XmlAttribute(name = "tabs")
    protected Integer tabs;
    @XmlAttribute(name = "text")
    protected String text;
    @XmlAttribute(name = "textLimit")
    protected Integer textLimit;

    /**
     * Obtient la valeur de la propri�t� addRefreshItem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddRefreshItem() {
        return addRefreshItem;
    }

    /**
     * D�finit la valeur de la propri�t� addRefreshItem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddRefreshItem(String value) {
        this.addRefreshItem = value;
    }

    /**
     * Obtient la valeur de la propri�t� editable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditable() {
        return editable;
    }

    /**
     * D�finit la valeur de la propri�t� editable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditable(String value) {
        this.editable = value;
    }

    /**
     * Obtient la valeur de la propri�t� orientation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     * D�finit la valeur de la propri�t� orientation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrientation(String value) {
        this.orientation = value;
    }

    /**
     * Obtient la valeur de la propri�t� tabs.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTabs() {
        return tabs;
    }

    /**
     * D�finit la valeur de la propri�t� tabs.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTabs(Integer value) {
        this.tabs = value;
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

    /**
     * Obtient la valeur de la propri�t� textLimit.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTextLimit() {
        return textLimit;
    }

    /**
     * D�finit la valeur de la propri�t� textLimit.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTextLimit(Integer value) {
        this.textLimit = value;
    }

}
