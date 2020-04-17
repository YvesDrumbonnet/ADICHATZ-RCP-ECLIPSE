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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour richTextType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="richTextType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;attribute name="editable" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tabs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="textLimit" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="containerBackground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerBackgroundImage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerBounds" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerCapture" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerFocus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerFont" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerForeground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerLayoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerLocation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerMenu" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerRedraw" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerSize" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="containerStyle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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

    /**
     * Obtient la valeur de la propri�t� containerBackground.
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
     * D�finit la valeur de la propri�t� containerBackground.
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
     * Obtient la valeur de la propri�t� containerBackgroundImage.
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
     * D�finit la valeur de la propri�t� containerBackgroundImage.
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
     * Obtient la valeur de la propri�t� containerBounds.
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
     * D�finit la valeur de la propri�t� containerBounds.
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
     * Obtient la valeur de la propri�t� containerCapture.
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
     * D�finit la valeur de la propri�t� containerCapture.
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
     * Obtient la valeur de la propri�t� containerFocus.
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
     * D�finit la valeur de la propri�t� containerFocus.
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
     * Obtient la valeur de la propri�t� containerFont.
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
     * D�finit la valeur de la propri�t� containerFont.
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
     * Obtient la valeur de la propri�t� containerForeground.
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
     * D�finit la valeur de la propri�t� containerForeground.
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
     * Obtient la valeur de la propri�t� containerLayoutData.
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
     * D�finit la valeur de la propri�t� containerLayoutData.
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
     * Obtient la valeur de la propri�t� containerLocation.
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
     * D�finit la valeur de la propri�t� containerLocation.
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
     * Obtient la valeur de la propri�t� containerMenu.
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
     * D�finit la valeur de la propri�t� containerMenu.
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
     * Obtient la valeur de la propri�t� containerRedraw.
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
     * D�finit la valeur de la propri�t� containerRedraw.
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
     * Obtient la valeur de la propri�t� containerSize.
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
     * D�finit la valeur de la propri�t� containerSize.
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
     * Obtient la valeur de la propri�t� containerStyle.
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
     * D�finit la valeur de la propri�t� containerStyle.
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
