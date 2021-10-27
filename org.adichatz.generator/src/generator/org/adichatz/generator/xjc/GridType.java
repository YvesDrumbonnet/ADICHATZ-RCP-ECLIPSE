//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
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
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour gridType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="gridType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}tabularType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;choice maxOccurs="unbounded"&amp;gt;
 *           &amp;lt;element name="gridColumn" type="{}gridColumnType"/&amp;gt;
 *           &amp;lt;element name="gridColumnGroup" type="{}gridColumnGroupType"/&amp;gt;
 *         &amp;lt;/choice&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="rowHeaderVisible" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="columnScrolling" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="selectionEnabled" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="cellSelectionEnabled" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getGridType", name = "gridType", propOrder = {
    "gridColumnOrGridColumnGroup"
})
public class GridType
    extends TabularType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "gridColumn", type = GridColumnType.class),
        @XmlElement(name = "gridColumnGroup", type = GridColumnGroupType.class)
    })
    protected List<ValidElementType> gridColumnOrGridColumnGroup;
    @XmlAttribute(name = "rowHeaderVisible")
    protected String rowHeaderVisible;
    @XmlAttribute(name = "columnScrolling")
    protected String columnScrolling;
    @XmlAttribute(name = "selectionEnabled")
    protected String selectionEnabled;
    @XmlAttribute(name = "cellSelectionEnabled")
    protected String cellSelectionEnabled;

    /**
     * Gets the value of the gridColumnOrGridColumnGroup property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the gridColumnOrGridColumnGroup property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGridColumnOrGridColumnGroup().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link GridColumnType }
     * {@link GridColumnGroupType }
     * 
     * 
     */
    public List<ValidElementType> getGridColumnOrGridColumnGroup() {
        if (gridColumnOrGridColumnGroup == null) {
            gridColumnOrGridColumnGroup = new ArrayList<ValidElementType>();
        }
        return this.gridColumnOrGridColumnGroup;
    }

    /**
     * Obtient la valeur de la propriété rowHeaderVisible.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowHeaderVisible() {
        return rowHeaderVisible;
    }

    /**
     * Définit la valeur de la propriété rowHeaderVisible.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowHeaderVisible(String value) {
        this.rowHeaderVisible = value;
    }

    /**
     * Obtient la valeur de la propriété columnScrolling.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnScrolling() {
        return columnScrolling;
    }

    /**
     * Définit la valeur de la propriété columnScrolling.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnScrolling(String value) {
        this.columnScrolling = value;
    }

    /**
     * Obtient la valeur de la propriété selectionEnabled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelectionEnabled() {
        return selectionEnabled;
    }

    /**
     * Définit la valeur de la propriété selectionEnabled.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelectionEnabled(String value) {
        this.selectionEnabled = value;
    }

    /**
     * Obtient la valeur de la propriété cellSelectionEnabled.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellSelectionEnabled() {
        return cellSelectionEnabled;
    }

    /**
     * Définit la valeur de la propriété cellSelectionEnabled.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellSelectionEnabled(String value) {
        this.cellSelectionEnabled = value;
    }

}
