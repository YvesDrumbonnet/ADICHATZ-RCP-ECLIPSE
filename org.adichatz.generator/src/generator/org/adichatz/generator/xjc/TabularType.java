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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour tabularType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="tabularType">
 *   &lt;complexContent>
 *     &lt;extension base="{}setType">
 *       &lt;sequence>
 *         &lt;element name="layout" type="{}layoutType"/>
 *         &lt;element name="contentProvider" type="{}contentProviderType" minOccurs="0"/>
 *         &lt;element name="labelProvider" type="{}labelProviderType" minOccurs="0"/>
 *         &lt;element name="crossReferences" type="{}crossReferencesType"/>
 *         &lt;element name="columnPreferences" type="{}columnPreferencesType"/>
 *         &lt;element name="filters" type="{}filtersType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="sortedColumn" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="columnOrder" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="statusBarKey" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tableRendererKey" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Obtient la valeur de la propri�t� layout.
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
     * D�finit la valeur de la propri�t� layout.
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
     * Obtient la valeur de la propri�t� contentProvider.
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
     * D�finit la valeur de la propri�t� contentProvider.
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
     * Obtient la valeur de la propri�t� labelProvider.
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
     * D�finit la valeur de la propri�t� labelProvider.
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
     * Obtient la valeur de la propri�t� crossReferences.
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
     * D�finit la valeur de la propri�t� crossReferences.
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
     * Obtient la valeur de la propri�t� columnPreferences.
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
     * D�finit la valeur de la propri�t� columnPreferences.
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
     * Obtient la valeur de la propri�t� filters.
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
     * D�finit la valeur de la propri�t� filters.
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
     * Obtient la valeur de la propri�t� sortedColumn.
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
     * D�finit la valeur de la propri�t� sortedColumn.
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
     * Obtient la valeur de la propri�t� columnOrder.
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
     * D�finit la valeur de la propri�t� columnOrder.
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
     * Obtient la valeur de la propri�t� statusBarKey.
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
     * D�finit la valeur de la propri�t� statusBarKey.
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
     * Obtient la valeur de la propri�t� tableRendererKey.
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
     * D�finit la valeur de la propri�t� tableRendererKey.
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
