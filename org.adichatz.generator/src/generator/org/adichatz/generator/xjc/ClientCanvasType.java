//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour clientCanvasType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="clientCanvasType">
 *   &lt;complexContent>
 *     &lt;extension base="{}dirtyContainerType">
 *       &lt;attribute name="expanded" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="text" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientBackground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientBackgroundImage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientBounds" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientCapture" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientFocus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientFont" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientForeground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientLayoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientLocation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientMenu" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientRedraw" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientSize" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clientStyle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getClientCanvasType", name = "clientCanvasType")
@XmlSeeAlso({
    SectionType.class,
    PGroupType.class,
    ScrolledCompositeType.class
})
public class ClientCanvasType
    extends DirtyContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "expanded")
    protected String expanded;
    @XmlAttribute(name = "text", required = true)
    protected String text;
    @XmlAttribute(name = "clientBackground")
    protected String clientBackground;
    @XmlAttribute(name = "clientBackgroundImage")
    protected String clientBackgroundImage;
    @XmlAttribute(name = "clientBounds")
    protected String clientBounds;
    @XmlAttribute(name = "clientCapture")
    protected String clientCapture;
    @XmlAttribute(name = "clientFocus")
    protected String clientFocus;
    @XmlAttribute(name = "clientFont")
    protected String clientFont;
    @XmlAttribute(name = "clientForeground")
    protected String clientForeground;
    @XmlAttribute(name = "clientLayoutData")
    protected String clientLayoutData;
    @XmlAttribute(name = "clientLocation")
    protected String clientLocation;
    @XmlAttribute(name = "clientMenu")
    protected String clientMenu;
    @XmlAttribute(name = "clientRedraw")
    protected String clientRedraw;
    @XmlAttribute(name = "clientSize")
    protected String clientSize;
    @XmlAttribute(name = "clientStyle")
    protected String clientStyle;

    /**
     * Obtient la valeur de la propriété expanded.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpanded() {
        return expanded;
    }

    /**
     * Définit la valeur de la propriété expanded.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpanded(String value) {
        this.expanded = value;
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
     * Obtient la valeur de la propriété clientBackground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientBackground() {
        return clientBackground;
    }

    /**
     * Définit la valeur de la propriété clientBackground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientBackground(String value) {
        this.clientBackground = value;
    }

    /**
     * Obtient la valeur de la propriété clientBackgroundImage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientBackgroundImage() {
        return clientBackgroundImage;
    }

    /**
     * Définit la valeur de la propriété clientBackgroundImage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientBackgroundImage(String value) {
        this.clientBackgroundImage = value;
    }

    /**
     * Obtient la valeur de la propriété clientBounds.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientBounds() {
        return clientBounds;
    }

    /**
     * Définit la valeur de la propriété clientBounds.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientBounds(String value) {
        this.clientBounds = value;
    }

    /**
     * Obtient la valeur de la propriété clientCapture.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientCapture() {
        return clientCapture;
    }

    /**
     * Définit la valeur de la propriété clientCapture.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientCapture(String value) {
        this.clientCapture = value;
    }

    /**
     * Obtient la valeur de la propriété clientFocus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientFocus() {
        return clientFocus;
    }

    /**
     * Définit la valeur de la propriété clientFocus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientFocus(String value) {
        this.clientFocus = value;
    }

    /**
     * Obtient la valeur de la propriété clientFont.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientFont() {
        return clientFont;
    }

    /**
     * Définit la valeur de la propriété clientFont.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientFont(String value) {
        this.clientFont = value;
    }

    /**
     * Obtient la valeur de la propriété clientForeground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientForeground() {
        return clientForeground;
    }

    /**
     * Définit la valeur de la propriété clientForeground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientForeground(String value) {
        this.clientForeground = value;
    }

    /**
     * Obtient la valeur de la propriété clientLayoutData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientLayoutData() {
        return clientLayoutData;
    }

    /**
     * Définit la valeur de la propriété clientLayoutData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientLayoutData(String value) {
        this.clientLayoutData = value;
    }

    /**
     * Obtient la valeur de la propriété clientLocation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientLocation() {
        return clientLocation;
    }

    /**
     * Définit la valeur de la propriété clientLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientLocation(String value) {
        this.clientLocation = value;
    }

    /**
     * Obtient la valeur de la propriété clientMenu.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientMenu() {
        return clientMenu;
    }

    /**
     * Définit la valeur de la propriété clientMenu.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientMenu(String value) {
        this.clientMenu = value;
    }

    /**
     * Obtient la valeur de la propriété clientRedraw.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientRedraw() {
        return clientRedraw;
    }

    /**
     * Définit la valeur de la propriété clientRedraw.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientRedraw(String value) {
        this.clientRedraw = value;
    }

    /**
     * Obtient la valeur de la propriété clientSize.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSize() {
        return clientSize;
    }

    /**
     * Définit la valeur de la propriété clientSize.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSize(String value) {
        this.clientSize = value;
    }

    /**
     * Obtient la valeur de la propriété clientStyle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientStyle() {
        return clientStyle;
    }

    /**
     * Définit la valeur de la propriété clientStyle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientStyle(String value) {
        this.clientStyle = value;
    }

}
