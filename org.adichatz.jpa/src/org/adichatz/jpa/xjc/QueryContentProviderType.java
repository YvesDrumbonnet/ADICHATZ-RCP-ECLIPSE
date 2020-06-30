//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:52 PM CEST 
//


package org.adichatz.jpa.xjc;

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
 * <p>Classe Java pour queryContentProviderType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="queryContentProviderType">
 *   &lt;complexContent>
 *     &lt;extension base="{}contentProviderType">
 *       &lt;attribute name="adiResourceURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="pluginKey" type="{http://www.w3.org/2001/XMLSchema}Name" />
 *       &lt;attribute name="preferenceURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="comparatorClassName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getQueryContentProviderType", name = "queryContentProviderType")
@XmlSeeAlso({
    ListDetailContentProviderType.class
})
public class QueryContentProviderType
    extends ContentProviderType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "adiResourceURI")
    protected String adiResourceURI;
    @XmlAttribute(name = "pluginKey")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    protected String pluginKey;
    @XmlAttribute(name = "preferenceURI")
    protected String preferenceURI;
    @XmlAttribute(name = "comparatorClassName")
    protected String comparatorClassName;

    /**
     * Obtient la valeur de la propriété adiResourceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdiResourceURI() {
        return adiResourceURI;
    }

    /**
     * Définit la valeur de la propriété adiResourceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdiResourceURI(String value) {
        this.adiResourceURI = value;
    }

    /**
     * Obtient la valeur de la propriété pluginKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPluginKey() {
        return pluginKey;
    }

    /**
     * Définit la valeur de la propriété pluginKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPluginKey(String value) {
        this.pluginKey = value;
    }

    /**
     * Obtient la valeur de la propriété preferenceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferenceURI() {
        return preferenceURI;
    }

    /**
     * Définit la valeur de la propriété preferenceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferenceURI(String value) {
        this.preferenceURI = value;
    }

    /**
     * Obtient la valeur de la propriété comparatorClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComparatorClassName() {
        return comparatorClassName;
    }

    /**
     * Définit la valeur de la propriété comparatorClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComparatorClassName(String value) {
        this.comparatorClassName = value;
    }

}
