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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour widgetType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="widgetType">
 *   &lt;complexContent>
 *     &lt;extension base="{}validElementType">
 *       &lt;sequence minOccurs="0">
 *         &lt;choice>
 *           &lt;element name="listeners" type="{}listenersType"/>
 *           &lt;element name="accessibilities" type="{}accessibilitiesType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="controllerClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="toolTipText" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "widgetType", propOrder = {
    "listeners",
    "accessibilities"
})
@XmlSeeAlso({
    IncludeType.class,
    MenuActionType.class,
    ToolItemType.class,
    MenuItemType.class,
    ActionType.class,
    ContributionItemType.class,
    SeparatorType.class,
    PGroupToolItemType.class,
    ControlType.class
})
public class WidgetType
    extends ValidElementType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ListenersType listeners;
    protected AccessibilitiesType accessibilities;
    @XmlAttribute(name = "controllerClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String controllerClassName;
    @XmlAttribute(name = "enabled")
    protected String enabled;
    @XmlAttribute(name = "style")
    protected String style;
    @XmlAttribute(name = "toolTipText")
    protected String toolTipText;

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
     * Obtient la valeur de la propri�t� toolTipText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolTipText() {
        return toolTipText;
    }

    /**
     * D�finit la valeur de la propri�t� toolTipText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolTipText(String value) {
        this.toolTipText = value;
    }

}
