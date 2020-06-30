//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour gridColumnGroupType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="gridColumnGroupType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;choice>
 *         &lt;element name="gridColumn" type="{}gridColumnType" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="expanded" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="headerFont" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="headerWordWrap" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getGridColumnGroupType", name = "gridColumnGroupType", propOrder = {
    "gridColumn"
})
public class GridColumnGroupType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<GridColumnType> gridColumn;
    @XmlAttribute(name = "text")
    protected String text;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "expanded")
    protected String expanded;
    @XmlAttribute(name = "headerFont")
    protected String headerFont;
    @XmlAttribute(name = "headerWordWrap")
    protected String headerWordWrap;

    /**
     * Gets the value of the gridColumn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gridColumn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGridColumn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GridColumnType }
     * 
     * 
     */
    public List<GridColumnType> getGridColumn() {
        if (gridColumn == null) {
            gridColumn = new ArrayList<GridColumnType>();
        }
        return this.gridColumn;
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
     * Obtient la valeur de la propri�t� expanded.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpanded() {
        return expanded;
    }

    /**
     * D�finit la valeur de la propri�t� expanded.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpanded(String value) {
        this.expanded = value;
    }

    /**
     * Obtient la valeur de la propri�t� headerFont.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeaderFont() {
        return headerFont;
    }

    /**
     * D�finit la valeur de la propri�t� headerFont.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeaderFont(String value) {
        this.headerFont = value;
    }

    /**
     * Obtient la valeur de la propri�t� headerWordWrap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeaderWordWrap() {
        return headerWordWrap;
    }

    /**
     * D�finit la valeur de la propri�t� headerWordWrap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeaderWordWrap(String value) {
        this.headerWordWrap = value;
    }

}
