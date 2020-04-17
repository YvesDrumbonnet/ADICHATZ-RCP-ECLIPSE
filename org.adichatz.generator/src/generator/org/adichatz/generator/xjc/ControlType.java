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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour controlType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="controlType">
 *   &lt;complexContent>
 *     &lt;extension base="{}widgetType">
 *       &lt;attribute name="background" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="backgroundImage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="bounds" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="capture" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="focus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="font" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="layoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="location" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="menu" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="redraw" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="visible" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "controlType")
@XmlSeeAlso({
    LabelType.class,
    ButtonType.class,
    CompositeSeparatorType.class,
    HelpButtonType.class,
    ControlFieldType.class
})
public class ControlType
    extends WidgetType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "background")
    protected String background;
    @XmlAttribute(name = "backgroundImage")
    protected String backgroundImage;
    @XmlAttribute(name = "bounds")
    protected String bounds;
    @XmlAttribute(name = "capture")
    protected String capture;
    @XmlAttribute(name = "foreground")
    protected String foreground;
    @XmlAttribute(name = "focus")
    protected String focus;
    @XmlAttribute(name = "font")
    protected String font;
    @XmlAttribute(name = "layoutData")
    protected String layoutData;
    @XmlAttribute(name = "location")
    protected String location;
    @XmlAttribute(name = "menu")
    protected String menu;
    @XmlAttribute(name = "redraw")
    protected String redraw;
    @XmlAttribute(name = "size")
    protected String size;
    @XmlAttribute(name = "visible")
    protected String visible;

    /**
     * Obtient la valeur de la propri�t� background.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackground() {
        return background;
    }

    /**
     * D�finit la valeur de la propri�t� background.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackground(String value) {
        this.background = value;
    }

    /**
     * Obtient la valeur de la propri�t� backgroundImage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * D�finit la valeur de la propri�t� backgroundImage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackgroundImage(String value) {
        this.backgroundImage = value;
    }

    /**
     * Obtient la valeur de la propri�t� bounds.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBounds() {
        return bounds;
    }

    /**
     * D�finit la valeur de la propri�t� bounds.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBounds(String value) {
        this.bounds = value;
    }

    /**
     * Obtient la valeur de la propri�t� capture.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapture() {
        return capture;
    }

    /**
     * D�finit la valeur de la propri�t� capture.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapture(String value) {
        this.capture = value;
    }

    /**
     * Obtient la valeur de la propri�t� foreground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeground() {
        return foreground;
    }

    /**
     * D�finit la valeur de la propri�t� foreground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeground(String value) {
        this.foreground = value;
    }

    /**
     * Obtient la valeur de la propri�t� focus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFocus() {
        return focus;
    }

    /**
     * D�finit la valeur de la propri�t� focus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFocus(String value) {
        this.focus = value;
    }

    /**
     * Obtient la valeur de la propri�t� font.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFont() {
        return font;
    }

    /**
     * D�finit la valeur de la propri�t� font.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFont(String value) {
        this.font = value;
    }

    /**
     * Obtient la valeur de la propri�t� layoutData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLayoutData() {
        return layoutData;
    }

    /**
     * D�finit la valeur de la propri�t� layoutData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLayoutData(String value) {
        this.layoutData = value;
    }

    /**
     * Obtient la valeur de la propri�t� location.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * D�finit la valeur de la propri�t� location.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Obtient la valeur de la propri�t� menu.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenu() {
        return menu;
    }

    /**
     * D�finit la valeur de la propri�t� menu.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenu(String value) {
        this.menu = value;
    }

    /**
     * Obtient la valeur de la propri�t� redraw.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedraw() {
        return redraw;
    }

    /**
     * D�finit la valeur de la propri�t� redraw.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedraw(String value) {
        this.redraw = value;
    }

    /**
     * Obtient la valeur de la propri�t� size.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSize() {
        return size;
    }

    /**
     * D�finit la valeur de la propri�t� size.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSize(String value) {
        this.size = value;
    }

    /**
     * Obtient la valeur de la propri�t� visible.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisible() {
        return visible;
    }

    /**
     * D�finit la valeur de la propri�t� visible.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisible(String value) {
        this.visible = value;
    }

}
