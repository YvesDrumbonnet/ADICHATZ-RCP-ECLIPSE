//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:33 PM CEST 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour controllerPreferenceType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="controllerPreferenceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}paramType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="columnPreferences" type="{}columnPreferencesType"/&amp;gt;
 *         &amp;lt;element name="filters" type="{}filtersType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="columnOrder" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="statusBarKey" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="tableRendererKey" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getControllerPreferenceType", name = "controllerPreferenceType", propOrder = {
    "columnPreferences",
    "filters"
})
public class ControllerPreferenceType
    extends ParamType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ColumnPreferencesType columnPreferences;
    @XmlElement(required = true)
    protected FiltersType filters;
    @XmlAttribute(name = "columnOrder")
    protected String columnOrder;
    @XmlAttribute(name = "statusBarKey")
    protected String statusBarKey;
    @XmlAttribute(name = "tableRendererKey")
    protected String tableRendererKey;

    /**
     * Obtient la valeur de la propriété columnPreferences.
     * 
     * @return
     *     possible object is
     *     {@link ColumnPreferencesType }
     *     
     */
    public ColumnPreferencesType getColumnPreferences() {
        return columnPreferences;
    }

    /**
     * Définit la valeur de la propriété columnPreferences.
     * 
     * @param value
     *     allowed object is
     *     {@link ColumnPreferencesType }
     *     
     */
    public void setColumnPreferences(ColumnPreferencesType value) {
        this.columnPreferences = value;
    }

    /**
     * Obtient la valeur de la propriété filters.
     * 
     * @return
     *     possible object is
     *     {@link FiltersType }
     *     
     */
    public FiltersType getFilters() {
        return filters;
    }

    /**
     * Définit la valeur de la propriété filters.
     * 
     * @param value
     *     allowed object is
     *     {@link FiltersType }
     *     
     */
    public void setFilters(FiltersType value) {
        this.filters = value;
    }

    /**
     * Obtient la valeur de la propriété columnOrder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnOrder() {
        return columnOrder;
    }

    /**
     * Définit la valeur de la propriété columnOrder.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnOrder(String value) {
        this.columnOrder = value;
    }

    /**
     * Obtient la valeur de la propriété statusBarKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusBarKey() {
        return statusBarKey;
    }

    /**
     * Définit la valeur de la propriété statusBarKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusBarKey(String value) {
        this.statusBarKey = value;
    }

    /**
     * Obtient la valeur de la propriété tableRendererKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTableRendererKey() {
        return tableRendererKey;
    }

    /**
     * Définit la valeur de la propriété tableRendererKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTableRendererKey(String value) {
        this.tableRendererKey = value;
    }

}
