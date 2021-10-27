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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Classe Java pour collectionType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="collectionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}validElementType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="accessibilities" type="{}accessibilitiesType"/&amp;gt;
 *         &amp;lt;element name="listeners" type="{}listenersType"/&amp;gt;
 *         &amp;lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="controllerClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="background" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="visible" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="enableRoles" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="validRoles" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="visibleRoles" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
    PGroupMenuType.class,
    LazyFetchContainerType.class,
    GridColumnGroupType.class
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
     * Obtient la valeur de la propriété accessibilities.
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
     * Définit la valeur de la propriété accessibilities.
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
     * Obtient la valeur de la propriété listeners.
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
     * Définit la valeur de la propriété listeners.
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
     * Obtient la valeur de la propriété additionalCode.
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
     * Définit la valeur de la propriété additionalCode.
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
     * Obtient la valeur de la propriété controllerClassName.
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
     * Définit la valeur de la propriété controllerClassName.
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
     * Obtient la valeur de la propriété background.
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
     * Définit la valeur de la propriété background.
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
     * Obtient la valeur de la propriété enabled.
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
     * Définit la valeur de la propriété enabled.
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
     * Obtient la valeur de la propriété foreground.
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
     * Définit la valeur de la propriété foreground.
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
     * Obtient la valeur de la propriété style.
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
     * Définit la valeur de la propriété style.
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
     * Obtient la valeur de la propriété visible.
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
     * Définit la valeur de la propriété visible.
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
     * Obtient la valeur de la propriété enableRoles.
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
     * Définit la valeur de la propriété enableRoles.
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
     * Obtient la valeur de la propriété validRoles.
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
     * Définit la valeur de la propriété validRoles.
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
     * Obtient la valeur de la propriété visibleRoles.
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
     * Définit la valeur de la propriété visibleRoles.
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
