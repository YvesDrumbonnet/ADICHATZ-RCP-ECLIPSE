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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour formPageType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="formPageType">
 *   &lt;complexContent>
 *     &lt;extension base="{}validableContainerType">
 *       &lt;choice>
 *         &lt;element name="managedToolBar" type="{}managedToolBarType"/>
 *       &lt;/choice>
 *       &lt;attribute name="bindingServiceClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="formMessageManager" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="formText" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="title" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="validStatus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getFormPageType", name = "formPageType", propOrder = {
    "managedToolBar"
})
public class FormPageType
    extends ValidableContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ManagedToolBarType managedToolBar;
    @XmlAttribute(name = "bindingServiceClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String bindingServiceClassName;
    @XmlAttribute(name = "formMessageManager")
    protected Boolean formMessageManager;
    @XmlAttribute(name = "formText")
    protected String formText;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "title", required = true)
    protected String title;
    @XmlAttribute(name = "validStatus")
    protected String validStatus;

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
     * Obtient la valeur de la propri�t� formText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormText() {
        return formText;
    }

    /**
     * D�finit la valeur de la propri�t� formText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormText(String value) {
        this.formText = value;
    }

    /**
     * Obtient la valeur de la propri�t� image.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * D�finit la valeur de la propri�t� image.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Obtient la valeur de la propri�t� title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * D�finit la valeur de la propri�t� title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Obtient la valeur de la propri�t� validStatus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidStatus() {
        return validStatus;
    }

    /**
     * D�finit la valeur de la propri�t� validStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidStatus(String value) {
        this.validStatus = value;
    }

}
