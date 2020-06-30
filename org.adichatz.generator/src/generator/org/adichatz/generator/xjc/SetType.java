//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour setType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="setType">
 *   &lt;complexContent>
 *     &lt;extension base="{}lazyFetchContainerType">
 *       &lt;sequence>
 *         &lt;element name="headerMenuManager" type="{}headerMenuManagerType"/>
 *         &lt;element name="menuManager" type="{}menuManagerType"/>
 *         &lt;element name="include" type="{}includeType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="headerVisible" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="linesVisible" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="refreshAtStart" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "setType", propOrder = {
    "headerMenuManager",
    "menuManager",
    "include"
})
@XmlSeeAlso({
    TabularType.class,
    TreeType.class
})
public class SetType
    extends LazyFetchContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected HeaderMenuManagerType headerMenuManager;
    @XmlElement(required = true)
    protected MenuManagerType menuManager;
    protected List<IncludeType> include;
    @XmlAttribute(name = "entityURI")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String entityURI;
    @XmlAttribute(name = "headerVisible")
    protected String headerVisible;
    @XmlAttribute(name = "linesVisible")
    protected String linesVisible;
    @XmlAttribute(name = "refreshAtStart")
    protected String refreshAtStart;
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
     * Obtient la valeur de la propri�t� headerMenuManager.
     * 
     * @return
     *     possible object is
     *     {@link HeaderMenuManagerType }
     *     
     */
    public HeaderMenuManagerType getHeaderMenuManager() {
        return headerMenuManager;
    }

    /**
     * D�finit la valeur de la propri�t� headerMenuManager.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderMenuManagerType }
     *     
     */
    public void setHeaderMenuManager(HeaderMenuManagerType value) {
        this.headerMenuManager = value;
    }

    /**
     * Obtient la valeur de la propri�t� menuManager.
     * 
     * @return
     *     possible object is
     *     {@link MenuManagerType }
     *     
     */
    public MenuManagerType getMenuManager() {
        return menuManager;
    }

    /**
     * D�finit la valeur de la propri�t� menuManager.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuManagerType }
     *     
     */
    public void setMenuManager(MenuManagerType value) {
        this.menuManager = value;
    }

    /**
     * Gets the value of the include property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the include property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInclude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IncludeType }
     * 
     * 
     */
    public List<IncludeType> getInclude() {
        if (include == null) {
            include = new ArrayList<IncludeType>();
        }
        return this.include;
    }

    /**
     * Obtient la valeur de la propri�t� entityURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityURI() {
        return entityURI;
    }

    /**
     * D�finit la valeur de la propri�t� entityURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityURI(String value) {
        this.entityURI = value;
    }

    /**
     * Obtient la valeur de la propri�t� headerVisible.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeaderVisible() {
        return headerVisible;
    }

    /**
     * D�finit la valeur de la propri�t� headerVisible.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeaderVisible(String value) {
        this.headerVisible = value;
    }

    /**
     * Obtient la valeur de la propri�t� linesVisible.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinesVisible() {
        return linesVisible;
    }

    /**
     * D�finit la valeur de la propri�t� linesVisible.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinesVisible(String value) {
        this.linesVisible = value;
    }

    /**
     * Obtient la valeur de la propri�t� refreshAtStart.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefreshAtStart() {
        return refreshAtStart;
    }

    /**
     * D�finit la valeur de la propri�t� refreshAtStart.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefreshAtStart(String value) {
        this.refreshAtStart = value;
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
