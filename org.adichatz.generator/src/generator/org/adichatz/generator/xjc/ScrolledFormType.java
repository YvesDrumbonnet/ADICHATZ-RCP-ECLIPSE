//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:17 AM CET 
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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour scrolledFormType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="scrolledFormType">
 *   &lt;complexContent>
 *     &lt;extension base="{}validableContainerType">
 *       &lt;sequence>
 *         &lt;element name="action" type="{}actionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="managedToolBar" type="{}managedToolBarType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="bindingServiceClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="formMessageManager" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getScrolledFormType", name = "scrolledFormType", propOrder = {
    "action",
    "managedToolBar"
})
public class ScrolledFormType
    extends ValidableContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<ActionType> action;
    @XmlElement(required = true)
    protected ManagedToolBarType managedToolBar;
    @XmlAttribute(name = "bindingServiceClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String bindingServiceClassName;
    @XmlAttribute(name = "formMessageManager")
    protected Boolean formMessageManager;
    @XmlAttribute(name = "text")
    protected String text;

    /**
     * Gets the value of the action property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the action property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActionType }
     * 
     * 
     */
    public List<ActionType> getAction() {
        if (action == null) {
            action = new ArrayList<ActionType>();
        }
        return this.action;
    }

    /**
     * Obtient la valeur de la propri�t� managedToolBar.
     * 
     * @return
     *     possible object is
     *     {@link ManagedToolBarType }
     *     
     */
    public ManagedToolBarType getManagedToolBar() {
        return managedToolBar;
    }

    /**
     * D�finit la valeur de la propri�t� managedToolBar.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedToolBarType }
     *     
     */
    public void setManagedToolBar(ManagedToolBarType value) {
        this.managedToolBar = value;
    }

    /**
     * Obtient la valeur de la propri�t� bindingServiceClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindingServiceClassName() {
        return bindingServiceClassName;
    }

    /**
     * D�finit la valeur de la propri�t� bindingServiceClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindingServiceClassName(String value) {
        this.bindingServiceClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� formMessageManager.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFormMessageManager() {
        return formMessageManager;
    }

    /**
     * D�finit la valeur de la propri�t� formMessageManager.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFormMessageManager(Boolean value) {
        this.formMessageManager = value;
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

}
