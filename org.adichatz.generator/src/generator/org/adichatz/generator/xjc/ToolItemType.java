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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour toolItemType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="toolItemType">
 *   &lt;complexContent>
 *     &lt;extension base="{}widgetType">
 *       &lt;choice>
 *         &lt;element name="action" type="{}actionType"/>
 *         &lt;element name="menuAction" type="{}menuActionType"/>
 *       &lt;/choice>
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getToolItemType", name = "toolItemType", propOrder = {
    "action",
    "menuAction"
})
public class ToolItemType
    extends WidgetType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ActionType action;
    protected MenuActionType menuAction;
    @XmlAttribute(name = "text")
    protected String text;

    /**
     * Obtient la valeur de la propriété action.
     * 
     * @return
     *     possible object is
     *     {@link ActionType }
     *     
     */
    public ActionType getAction() {
        return action;
    }

    /**
     * Définit la valeur de la propriété action.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionType }
     *     
     */
    public void setAction(ActionType value) {
        this.action = value;
    }

    /**
     * Obtient la valeur de la propriété menuAction.
     * 
     * @return
     *     possible object is
     *     {@link MenuActionType }
     *     
     */
    public MenuActionType getMenuAction() {
        return menuAction;
    }

    /**
     * Définit la valeur de la propriété menuAction.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuActionType }
     *     
     */
    public void setMenuAction(MenuActionType value) {
        this.menuAction = value;
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

}
