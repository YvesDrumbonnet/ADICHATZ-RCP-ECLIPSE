//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour collectionType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="collectionType">
 *   &lt;complexContent>
 *     &lt;extension base="{}validElementType">
 *       &lt;choice>
 *         &lt;element name="accessibilities" type="{}accessibilitiesType"/>
 *         &lt;element name="listeners" type="{}listenersType"/>
 *         &lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *       &lt;attribute name="controllerClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="background" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="visible" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="enableRoles" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="validRoles" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="visibleRoles" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "collectionType", propOrder = {
    "accessibilities",
    "listeners",
    "additionalCode"
})
@XmlSeeAlso({
    CompositeBagType.class,
    CTabFolderType.class,
    PShelfType.class,
    MenuManagerType.class,
    ManagedToolBarType.class,
    ToolBarType.class,
    ButtonBarType.class,
    GridColumnGroupType.class,
    PGroupMenuType.class,
    LazyFetchContainerType.class
})
public class CollectionType
    extends ValidElementType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected AccessibilitiesType accessibilities;
    protected ListenersType listeners;
    protected String additionalCode;
    @XmlAttribute(name = "controllerClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String controllerClassName;
    @XmlAttribute(name = "background")
    protected String background;
    @XmlAttribute(name = "enabled")
    protected String enabled;
    @XmlAttribute(name = "foreground")
    protected String foreground;
    @XmlAttribute(name = "style")
    protected String style;
    @XmlAttribute(name = "visible")
    protected String visible;
    @XmlAttribute(name = "enableRoles")
    protected String enableRoles;
    @XmlAttribute(name = "validRoles")
    protected String validRoles;
    @XmlAttribute(name = "visibleRoles")
    protected String visibleRoles;

    /**
     * Obtient la valeur de la propri�t� accessibilities.
     * 
     * @return
     *     possible object is
     *     {@link AccessibilitiesType }
     *     
     */
    public AccessibilitiesType getAccessibilities() {
        return accessibilities;
    }

    /**
     * D�finit la valeur de la propri�t� accessibilities.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessibilitiesType }
     *     
     */
    public void setAccessibilities(AccessibilitiesType value) {
        this.accessibilities = value;
    }

    /**
     * Obtient la valeur de la propri�t� listeners.
     * 
     * @return
     *     possible object is
     *     {@link ListenersType }
     *     
     */
    public ListenersType getListeners() {
        return listeners;
    }

    /**
     * D�finit la valeur de la propri�t� listeners.
     * 
     * @param value
     *     allowed object is
     *     {@link ListenersType }
     *     
     */
    public void setListeners(ListenersType value) {
        this.listeners = value;
    }

    /**
     * Obtient la valeur de la propri�t� additionalCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCode() {
        return additionalCode;
    }

    /**
     * D�finit la valeur de la propri�t� additionalCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCode(String value) {
        this.additionalCode = value;
    }

    /**
     * Obtient la valeur de la propri�t� controllerClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControllerClassName() {
        return controllerClassName;
    }

    /**
     * D�finit la valeur de la propri�t� controllerClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControllerClassName(String value) {
        this.controllerClassName = value;
    }

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
     * Obtient la valeur de la propri�t� enabled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * D�finit la valeur de la propri�t� enabled.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnabled(String value) {
        this.enabled = value;
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
     * Obtient la valeur de la propri�t� style.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyle() {
        return style;
    }

    /**
     * D�finit la valeur de la propri�t� style.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyle(String value) {
        this.style = value;
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

    /**
     * Obtient la valeur de la propri�t� enableRoles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnableRoles() {
        return enableRoles;
    }

    /**
     * D�finit la valeur de la propri�t� enableRoles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnableRoles(String value) {
        this.enableRoles = value;
    }

    /**
     * Obtient la valeur de la propri�t� validRoles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidRoles() {
        return validRoles;
    }

    /**
     * D�finit la valeur de la propri�t� validRoles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidRoles(String value) {
        this.validRoles = value;
    }

    /**
     * Obtient la valeur de la propri�t� visibleRoles.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisibleRoles() {
        return visibleRoles;
    }

    /**
     * D�finit la valeur de la propri�t� visibleRoles.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisibleRoles(String value) {
        this.visibleRoles = value;
    }

}
