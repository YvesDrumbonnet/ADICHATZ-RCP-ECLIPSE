//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour menuActionType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="menuActionType">
 *   &lt;complexContent>
 *     &lt;extension base="{}widgetType">
 *       &lt;sequence>
 *         &lt;element name="params" type="{}paramsType"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="action" type="{}actionType"/>
 *           &lt;element name="menuAction" type="{}menuActionType"/>
 *           &lt;element name="separator" type="{}separatorType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="imageDescriptor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="reflow" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getMenuActionType", name = "menuActionType", propOrder = {
    "params",
    "actionOrMenuActionOrSeparator"
})
public class MenuActionType
    extends WidgetType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ParamsType params;
    @XmlElements({
        @XmlElement(name = "action", type = ActionType.class),
        @XmlElement(name = "menuAction", type = MenuActionType.class),
        @XmlElement(name = "separator", type = SeparatorType.class)
    })
    protected List<WidgetType> actionOrMenuActionOrSeparator;
    @XmlAttribute(name = "imageDescriptor")
    protected String imageDescriptor;
    @XmlAttribute(name = "reflow")
    protected Boolean reflow;
    @XmlAttribute(name = "text")
    protected String text;

    /**
     * Obtient la valeur de la propriété params.
     * 
     * @return
     *     possible object is
     *     {@link ParamsType }
     *     
     */
    public ParamsType getParams() {
        return params;
    }

    /**
     * Définit la valeur de la propriété params.
     * 
     * @param value
     *     allowed object is
     *     {@link ParamsType }
     *     
     */
    public void setParams(ParamsType value) {
        this.params = value;
    }

    /**
     * Gets the value of the actionOrMenuActionOrSeparator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actionOrMenuActionOrSeparator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActionOrMenuActionOrSeparator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionType }
     * {@link MenuActionType }
     * {@link SeparatorType }
     * 
     * 
     */
    public List<WidgetType> getActionOrMenuActionOrSeparator() {
        if (actionOrMenuActionOrSeparator == null) {
            actionOrMenuActionOrSeparator = new ArrayList<WidgetType>();
        }
        return this.actionOrMenuActionOrSeparator;
    }

    /**
     * Obtient la valeur de la propriété imageDescriptor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageDescriptor() {
        return imageDescriptor;
    }

    /**
     * Définit la valeur de la propriété imageDescriptor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageDescriptor(String value) {
        this.imageDescriptor = value;
    }

    /**
     * Obtient la valeur de la propriété reflow.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReflow() {
        return reflow;
    }

    /**
     * Définit la valeur de la propriété reflow.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReflow(Boolean value) {
        this.reflow = value;
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
