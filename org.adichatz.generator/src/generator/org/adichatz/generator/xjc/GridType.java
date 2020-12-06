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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour gridType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="gridType">
 *   &lt;complexContent>
 *     &lt;extension base="{}tabularType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="gridColumn" type="{}gridColumnType"/>
 *           &lt;element name="gridColumnGroup" type="{}gridColumnGroupType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="rowHeaderVisible" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="columnScrolling" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="selectionEnabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="cellSelectionEnabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gridColumnOrGridColumnGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGridColumnOrGridColumnGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * Obtient la valeur de la propri�t� rowHeaderVisible.
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
     * D�finit la valeur de la propri�t� rowHeaderVisible.
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
     * Obtient la valeur de la propri�t� columnScrolling.
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
     * D�finit la valeur de la propri�t� columnScrolling.
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
     * Obtient la valeur de la propri�t� selectionEnabled.
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
     * D�finit la valeur de la propri�t� selectionEnabled.
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
     * Obtient la valeur de la propri�t� cellSelectionEnabled.
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
     * D�finit la valeur de la propri�t� cellSelectionEnabled.
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
