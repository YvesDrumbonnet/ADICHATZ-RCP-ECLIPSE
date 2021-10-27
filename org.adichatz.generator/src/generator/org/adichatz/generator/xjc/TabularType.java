//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour tabularType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="tabularType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}setType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="layout" type="{}layoutType"/&amp;gt;
 *         &amp;lt;element name="contentProvider" type="{}contentProviderType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="labelProvider" type="{}labelProviderType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="crossReferences" type="{}crossReferencesType"/&amp;gt;
 *         &amp;lt;element name="columnPreferences" type="{}columnPreferencesType"/&amp;gt;
 *         &amp;lt;element name="filters" type="{}filtersType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="sortedColumn" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
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
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getTabularType", name = "tabularType", propOrder = {
    "layout",
    "contentProvider",
    "labelProvider",
    "crossReferences",
    "columnPreferences",
    "filters"
})
@XmlSeeAlso({
    TableType.class,
    GridType.class
})
public class TabularType
    extends SetType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected LayoutType layout;
    protected ContentProviderType contentProvider;
    protected LabelProviderType labelProvider;
    @XmlElement(required = true)
    protected CrossReferencesType crossReferences;
    @XmlElement(required = true)
    protected ColumnPreferencesType columnPreferences;
    @XmlElement(required = true)
    protected FiltersType filters;
    @XmlAttribute(name = "sortedColumn")
    protected String sortedColumn;
    @XmlAttribute(name = "columnOrder")
    protected String columnOrder;
    @XmlAttribute(name = "statusBarKey")
    protected String statusBarKey;
    @XmlAttribute(name = "tableRendererKey")
    protected String tableRendererKey;

    /**
     * Obtient la valeur de la propriété layout.
     * 
     * @return
     *     possible object is
     *     {@link LayoutType }
     *     
     */
    public LayoutType getLayout() {
        return layout;
    }

    /**
     * Définit la valeur de la propriété layout.
     * 
     * @param value
     *     allowed object is
     *     {@link LayoutType }
     *     
     */
    public void setLayout(LayoutType value) {
        this.layout = value;
    }

    /**
     * Obtient la valeur de la propriété contentProvider.
     * 
     * @return
     *     possible object is
     *     {@link ContentProviderType }
     *     
     */
    public ContentProviderType getContentProvider() {
        return contentProvider;
    }

    /**
     * Définit la valeur de la propriété contentProvider.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentProviderType }
     *     
     */
    public void setContentProvider(ContentProviderType value) {
        this.contentProvider = value;
    }

    /**
     * Obtient la valeur de la propriété labelProvider.
     * 
     * @return
     *     possible object is
     *     {@link LabelProviderType }
     *     
     */
    public LabelProviderType getLabelProvider() {
        return labelProvider;
    }

    /**
     * Définit la valeur de la propriété labelProvider.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelProviderType }
     *     
     */
    public void setLabelProvider(LabelProviderType value) {
        this.labelProvider = value;
    }

    /**
     * Obtient la valeur de la propriété crossReferences.
     * 
     * @return
     *     possible object is
     *     {@link CrossReferencesType }
     *     
     */
    public CrossReferencesType getCrossReferences() {
        return crossReferences;
    }

    /**
     * Définit la valeur de la propriété crossReferences.
     * 
     * @param value
     *     allowed object is
     *     {@link CrossReferencesType }
     *     
     */
    public void setCrossReferences(CrossReferencesType value) {
        this.crossReferences = value;
    }

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
     * Obtient la valeur de la propriété sortedColumn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortedColumn() {
        return sortedColumn;
    }

    /**
     * Définit la valeur de la propriété sortedColumn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortedColumn(String value) {
        this.sortedColumn = value;
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
