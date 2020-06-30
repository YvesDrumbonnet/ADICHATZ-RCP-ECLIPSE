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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour formPageType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propriété managedToolBar.
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
     * Définit la valeur de la propriété managedToolBar.
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
     * Obtient la valeur de la propriété bindingServiceClassName.
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
     * Définit la valeur de la propriété bindingServiceClassName.
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
     * Obtient la valeur de la propriété formMessageManager.
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
     * Définit la valeur de la propriété formMessageManager.
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
     * Obtient la valeur de la propriété formText.
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
     * Définit la valeur de la propriété formText.
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
     * Obtient la valeur de la propriété image.
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
     * Définit la valeur de la propriété image.
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
     * Obtient la valeur de la propriété title.
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
     * Définit la valeur de la propriété title.
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
     * Obtient la valeur de la propriété validStatus.
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
     * Définit la valeur de la propriété validStatus.
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
