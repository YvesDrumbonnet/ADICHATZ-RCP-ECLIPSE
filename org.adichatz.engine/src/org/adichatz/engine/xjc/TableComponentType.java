//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:22 AM CET 
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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propriété id.
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
     * Définit la valeur de la propriété id.
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

    /**
     * Obtient la valeur de la propriété resourceBundleKey.
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
     * Définit la valeur de la propriété resourceBundleKey.
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
     * Obtient la valeur de la propriété className.
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
     * Définit la valeur de la propriété className.
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
     * Obtient la valeur de la propriété tabularClassName.
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
     * Définit la valeur de la propriété tabularClassName.
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
     * Obtient la valeur de la propriété requireBindingService.
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
     * Définit la valeur de la propriété requireBindingService.
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
     * Obtient la valeur de la propriété default.
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
     * Définit la valeur de la propriété default.
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
     * Obtient la valeur de la propriété rank.
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
     * Définit la valeur de la propriété rank.
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
