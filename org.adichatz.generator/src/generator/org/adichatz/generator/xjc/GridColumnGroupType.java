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
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour gridColumnGroupType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="gridColumnGroupType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}collectionType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="gridColumn" type="{}gridColumnType" maxOccurs="unbounded"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="expanded" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="headerFont" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="headerWordWrap" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the gridColumn property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGridColumn().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
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
     * Obtient la valeur de la propriété image.
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
     * Définit la valeur de la propriété image.
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
     * Obtient la valeur de la propriété expanded.
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
     * Définit la valeur de la propriété expanded.
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
     * Obtient la valeur de la propriété headerFont.
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
     * Définit la valeur de la propriété headerFont.
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
     * Obtient la valeur de la propriété headerWordWrap.
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
     * Définit la valeur de la propriété headerWordWrap.
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
