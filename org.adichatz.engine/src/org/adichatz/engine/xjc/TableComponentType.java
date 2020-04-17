//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:22 AM CET 
//


package org.adichatz.engine.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour tableComponentType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="tableComponentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="text" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="resourceBundleKey" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="className" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tabularClassName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="requireBindingService" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="default" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="rank" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tableComponentType")
public class TableComponentType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String id;
    @XmlAttribute(name = "text", required = true)
    protected String text;
    @XmlAttribute(name = "resourceBundleKey", required = true)
    protected String resourceBundleKey;
    @XmlAttribute(name = "className", required = true)
    protected String className;
    @XmlAttribute(name = "tabularClassName", required = true)
    protected String tabularClassName;
    @XmlAttribute(name = "requireBindingService")
    protected Boolean requireBindingService;
    @XmlAttribute(name = "default")
    protected Boolean _default;
    @XmlAttribute(name = "rank")
    protected Integer rank;

    /**
     * Obtient la valeur de la propri�t� id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * D�finit la valeur de la propri�t� id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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

    /**
     * Obtient la valeur de la propri�t� resourceBundleKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceBundleKey() {
        return resourceBundleKey;
    }

    /**
     * D�finit la valeur de la propri�t� resourceBundleKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceBundleKey(String value) {
        this.resourceBundleKey = value;
    }

    /**
     * Obtient la valeur de la propri�t� className.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * D�finit la valeur de la propri�t� className.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
    }

    /**
     * Obtient la valeur de la propri�t� tabularClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTabularClassName() {
        return tabularClassName;
    }

    /**
     * D�finit la valeur de la propri�t� tabularClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTabularClassName(String value) {
        this.tabularClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� requireBindingService.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isRequireBindingService() {
        if (requireBindingService == null) {
            return false;
        } else {
            return requireBindingService;
        }
    }

    /**
     * D�finit la valeur de la propri�t� requireBindingService.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequireBindingService(Boolean value) {
        this.requireBindingService = value;
    }

    /**
     * Obtient la valeur de la propri�t� default.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDefault() {
        if (_default == null) {
            return false;
        } else {
            return _default;
        }
    }

    /**
     * D�finit la valeur de la propri�t� default.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDefault(Boolean value) {
        this._default = value;
    }

    /**
     * Obtient la valeur de la propri�t� rank.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * D�finit la valeur de la propri�t� rank.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRank(Integer value) {
        this.rank = value;
    }

}
