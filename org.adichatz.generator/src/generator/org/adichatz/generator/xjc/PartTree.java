//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;sequence>
 *         &lt;element name="config" type="{}configType"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="formPage" type="{}formPageType"/>
 *           &lt;element name="include" type="{}includeType"/>
 *         &lt;/choice>
 *         &lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="bindingServiceClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="coreClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="outlinePageClassName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="toolTipText" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getPartTree", name = "", propOrder = {
    "config",
    "formPageOrInclude",
    "additionalCode"
})
@XmlRootElement(name = "partTree")
public class PartTree
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ConfigType config;
    @XmlElements({
        @XmlElement(name = "formPage", type = FormPageType.class),
        @XmlElement(name = "include", type = IncludeType.class)
    })
    protected List<ValidElementType> formPageOrInclude;
    @XmlElement(required = true)
    protected String additionalCode;
    @XmlAttribute(name = "bindingServiceClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String bindingServiceClassName;
    @XmlAttribute(name = "coreClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String coreClassName;
    @XmlAttribute(name = "entityURI")
    protected String entityURI;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "outlinePageClassName")
    protected String outlinePageClassName;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "toolTipText")
    protected String toolTipText;

    /**
     * Obtient la valeur de la propri�t� config.
     * 
     * @return
     *     possible object is
     *     {@link ConfigType }
     *     
     */
    public ConfigType getConfig() {
        return config;
    }

    /**
     * D�finit la valeur de la propri�t� config.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigType }
     *     
     */
    public void setConfig(ConfigType value) {
        this.config = value;
    }

    /**
     * Gets the value of the formPageOrInclude property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formPageOrInclude property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormPageOrInclude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormPageType }
     * {@link IncludeType }
     * 
     * 
     */
    public List<ValidElementType> getFormPageOrInclude() {
        if (formPageOrInclude == null) {
            formPageOrInclude = new ArrayList<ValidElementType>();
        }
        return this.formPageOrInclude;
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
     * Obtient la valeur de la propri�t� coreClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoreClassName() {
        return coreClassName;
    }

    /**
     * D�finit la valeur de la propri�t� coreClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoreClassName(String value) {
        this.coreClassName = value;
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
     * Obtient la valeur de la propri�t� outlinePageClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutlinePageClassName() {
        return outlinePageClassName;
    }

    /**
     * D�finit la valeur de la propri�t� outlinePageClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutlinePageClassName(String value) {
        this.outlinePageClassName = value;
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
