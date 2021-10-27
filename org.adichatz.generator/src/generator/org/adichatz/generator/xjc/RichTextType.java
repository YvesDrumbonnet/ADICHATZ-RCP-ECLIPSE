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
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour richTextType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="richTextType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}controlFieldType"&amp;gt;
 *       &amp;lt;attribute name="editable" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="tabs" type="{http://www.w3.org/2001/XMLSchema}int" /&amp;gt;
 *       &amp;lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="textLimit" type="{http://www.w3.org/2001/XMLSchema}int" /&amp;gt;
 *       &amp;lt;attribute name="containerBackground" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerBackgroundImage" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerBounds" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerCapture" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerFocus" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerFont" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerForeground" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerLayoutData" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerLocation" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerMenu" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerRedraw" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerSize" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="containerStyle" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getRichTextType", name = "richTextType")
public class RichTextType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
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
    @XmlAttribute(name = "containerBackground")
    protected String containerBackground;
    @XmlAttribute(name = "containerBackgroundImage")
    protected String containerBackgroundImage;
    @XmlAttribute(name = "containerBounds")
    protected String containerBounds;
    @XmlAttribute(name = "containerCapture")
    protected String containerCapture;
    @XmlAttribute(name = "containerFocus")
    protected String containerFocus;
    @XmlAttribute(name = "containerFont")
    protected String containerFont;
    @XmlAttribute(name = "containerForeground")
    protected String containerForeground;
    @XmlAttribute(name = "containerLayoutData")
    protected String containerLayoutData;
    @XmlAttribute(name = "containerLocation")
    protected String containerLocation;
    @XmlAttribute(name = "containerMenu")
    protected String containerMenu;
    @XmlAttribute(name = "containerRedraw")
    protected String containerRedraw;
    @XmlAttribute(name = "containerSize")
    protected String containerSize;
    @XmlAttribute(name = "containerStyle")
    protected String containerStyle;

    /**
     * Obtient la valeur de la propriété editable.
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
     * Définit la valeur de la propriété editable.
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
     * Obtient la valeur de la propriété orientation.
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
     * Définit la valeur de la propriété orientation.
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
     * Obtient la valeur de la propriété tabs.
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
     * Définit la valeur de la propriété tabs.
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

    /**
     * Obtient la valeur de la propriété textLimit.
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
     * Définit la valeur de la propriété textLimit.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTextLimit(Integer value) {
        this.textLimit = value;
    }

    /**
     * Obtient la valeur de la propriété containerBackground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerBackground() {
        return containerBackground;
    }

    /**
     * Définit la valeur de la propriété containerBackground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerBackground(String value) {
        this.containerBackground = value;
    }

    /**
     * Obtient la valeur de la propriété containerBackgroundImage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerBackgroundImage() {
        return containerBackgroundImage;
    }

    /**
     * Définit la valeur de la propriété containerBackgroundImage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerBackgroundImage(String value) {
        this.containerBackgroundImage = value;
    }

    /**
     * Obtient la valeur de la propriété containerBounds.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerBounds() {
        return containerBounds;
    }

    /**
     * Définit la valeur de la propriété containerBounds.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerBounds(String value) {
        this.containerBounds = value;
    }

    /**
     * Obtient la valeur de la propriété containerCapture.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerCapture() {
        return containerCapture;
    }

    /**
     * Définit la valeur de la propriété containerCapture.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerCapture(String value) {
        this.containerCapture = value;
    }

    /**
     * Obtient la valeur de la propriété containerFocus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerFocus() {
        return containerFocus;
    }

    /**
     * Définit la valeur de la propriété containerFocus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerFocus(String value) {
        this.containerFocus = value;
    }

    /**
     * Obtient la valeur de la propriété containerFont.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerFont() {
        return containerFont;
    }

    /**
     * Définit la valeur de la propriété containerFont.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerFont(String value) {
        this.containerFont = value;
    }

    /**
     * Obtient la valeur de la propriété containerForeground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerForeground() {
        return containerForeground;
    }

    /**
     * Définit la valeur de la propriété containerForeground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerForeground(String value) {
        this.containerForeground = value;
    }

    /**
     * Obtient la valeur de la propriété containerLayoutData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerLayoutData() {
        return containerLayoutData;
    }

    /**
     * Définit la valeur de la propriété containerLayoutData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerLayoutData(String value) {
        this.containerLayoutData = value;
    }

    /**
     * Obtient la valeur de la propriété containerLocation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerLocation() {
        return containerLocation;
    }

    /**
     * Définit la valeur de la propriété containerLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerLocation(String value) {
        this.containerLocation = value;
    }

    /**
     * Obtient la valeur de la propriété containerMenu.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerMenu() {
        return containerMenu;
    }

    /**
     * Définit la valeur de la propriété containerMenu.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerMenu(String value) {
        this.containerMenu = value;
    }

    /**
     * Obtient la valeur de la propriété containerRedraw.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerRedraw() {
        return containerRedraw;
    }

    /**
     * Définit la valeur de la propriété containerRedraw.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerRedraw(String value) {
        this.containerRedraw = value;
    }

    /**
     * Obtient la valeur de la propriété containerSize.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerSize() {
        return containerSize;
    }

    /**
     * Définit la valeur de la propriété containerSize.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerSize(String value) {
        this.containerSize = value;
    }

    /**
     * Obtient la valeur de la propriété containerStyle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerStyle() {
        return containerStyle;
    }

    /**
     * Définit la valeur de la propriété containerStyle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerStyle(String value) {
        this.containerStyle = value;
    }

}
