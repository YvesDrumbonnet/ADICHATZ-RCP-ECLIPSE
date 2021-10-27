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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Classe Java pour columnFieldType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="columnFieldType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}validElementType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="columnImage" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="columnText" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="columnValue" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="columnBackground" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="columnForeground" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="columnFont" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="accessibilities" type="{}accessibilitiesType"/&amp;gt;
 *         &amp;lt;element name="listeners" type="{}listenersType"/&amp;gt;
 *         &amp;lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="lazyFetches" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="locale" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="pack" type="{http://www.w3.org/2001/XMLSchema}boolean" /&amp;gt;
 *       &amp;lt;attribute name="pattern" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="sorted" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *       &amp;lt;attribute name="columnValueType" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="alignment" type="{http://www.w3.org/2001/XMLSchema}int" /&amp;gt;
 *       &amp;lt;attribute name="moveable" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="resizeable" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="sortDirection" type="{}sortEnum" default="ASC" /&amp;gt;
 *       &amp;lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="toolTipText" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}int" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getColumnFieldType", name = "columnFieldType", propOrder = {
    "columnImage",
    "columnText",
    "columnValue",
    "columnBackground",
    "columnForeground",
    "columnFont",
    "accessibilities",
    "listeners",
    "additionalCode"
})
@XmlSeeAlso({
    TableColumnType.class,
    GridColumnType.class
})
public class ColumnFieldType
    extends ValidElementType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected String columnImage;
    protected String columnText;
    protected String columnValue;
    protected String columnBackground;
    protected String columnForeground;
    protected String columnFont;
    protected AccessibilitiesType accessibilities;
    protected ListenersType listeners;
    protected String additionalCode;
    @XmlAttribute(name = "property")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String property;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "lazyFetches")
    protected String lazyFetches;
    @XmlAttribute(name = "locale")
    protected String locale;
    @XmlAttribute(name = "pack")
    protected Boolean pack;
    @XmlAttribute(name = "pattern")
    protected String pattern;
    @XmlAttribute(name = "sorted")
    protected Boolean sorted;
    @XmlAttribute(name = "columnValueType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String columnValueType;
    @XmlAttribute(name = "alignment")
    protected Integer alignment;
    @XmlAttribute(name = "moveable")
    protected String moveable;
    @XmlAttribute(name = "resizeable")
    protected String resizeable;
    @XmlAttribute(name = "sortDirection")
    protected SortEnum sortDirection;
    @XmlAttribute(name = "style")
    protected String style;
    @XmlAttribute(name = "text")
    protected String text;
    @XmlAttribute(name = "toolTipText")
    protected String toolTipText;
    @XmlAttribute(name = "width")
    protected Integer width;

    /**
     * Obtient la valeur de la propriété columnImage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnImage() {
        return columnImage;
    }

    /**
     * Définit la valeur de la propriété columnImage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnImage(String value) {
        this.columnImage = value;
    }

    /**
     * Obtient la valeur de la propriété columnText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnText() {
        return columnText;
    }

    /**
     * Définit la valeur de la propriété columnText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnText(String value) {
        this.columnText = value;
    }

    /**
     * Obtient la valeur de la propriété columnValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnValue() {
        return columnValue;
    }

    /**
     * Définit la valeur de la propriété columnValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnValue(String value) {
        this.columnValue = value;
    }

    /**
     * Obtient la valeur de la propriété columnBackground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnBackground() {
        return columnBackground;
    }

    /**
     * Définit la valeur de la propriété columnBackground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnBackground(String value) {
        this.columnBackground = value;
    }

    /**
     * Obtient la valeur de la propriété columnForeground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnForeground() {
        return columnForeground;
    }

    /**
     * Définit la valeur de la propriété columnForeground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnForeground(String value) {
        this.columnForeground = value;
    }

    /**
     * Obtient la valeur de la propriété columnFont.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnFont() {
        return columnFont;
    }

    /**
     * Définit la valeur de la propriété columnFont.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnFont(String value) {
        this.columnFont = value;
    }

    /**
     * Obtient la valeur de la propriété accessibilities.
     * 
     * @return
     *     possible object is
     *     {@link AccessibilitiesType }
     *     
     */
    public AccessibilitiesType getAccessibilities() {
        return accessibilities;
    }

    /**
     * Définit la valeur de la propriété accessibilities.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessibilitiesType }
     *     
     */
    public void setAccessibilities(AccessibilitiesType value) {
        this.accessibilities = value;
    }

    /**
     * Obtient la valeur de la propriété listeners.
     * 
     * @return
     *     possible object is
     *     {@link ListenersType }
     *     
     */
    public ListenersType getListeners() {
        return listeners;
    }

    /**
     * Définit la valeur de la propriété listeners.
     * 
     * @param value
     *     allowed object is
     *     {@link ListenersType }
     *     
     */
    public void setListeners(ListenersType value) {
        this.listeners = value;
    }

    /**
     * Obtient la valeur de la propriété additionalCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCode() {
        return additionalCode;
    }

    /**
     * Définit la valeur de la propriété additionalCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCode(String value) {
        this.additionalCode = value;
    }

    /**
     * Obtient la valeur de la propriété property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProperty() {
        return property;
    }

    /**
     * Définit la valeur de la propriété property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProperty(String value) {
        this.property = value;
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
     * Obtient la valeur de la propriété lazyFetches.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLazyFetches() {
        return lazyFetches;
    }

    /**
     * Définit la valeur de la propriété lazyFetches.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLazyFetches(String value) {
        this.lazyFetches = value;
    }

    /**
     * Obtient la valeur de la propriété locale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Définit la valeur de la propriété locale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Obtient la valeur de la propriété pack.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPack() {
        return pack;
    }

    /**
     * Définit la valeur de la propriété pack.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPack(Boolean value) {
        this.pack = value;
    }

    /**
     * Obtient la valeur de la propriété pattern.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Définit la valeur de la propriété pattern.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPattern(String value) {
        this.pattern = value;
    }

    /**
     * Obtient la valeur de la propriété sorted.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isSorted() {
        if (sorted == null) {
            return false;
        } else {
            return sorted;
        }
    }

    /**
     * Définit la valeur de la propriété sorted.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSorted(Boolean value) {
        this.sorted = value;
    }

    /**
     * Obtient la valeur de la propriété columnValueType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnValueType() {
        return columnValueType;
    }

    /**
     * Définit la valeur de la propriété columnValueType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnValueType(String value) {
        this.columnValueType = value;
    }

    /**
     * Obtient la valeur de la propriété alignment.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAlignment() {
        return alignment;
    }

    /**
     * Définit la valeur de la propriété alignment.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAlignment(Integer value) {
        this.alignment = value;
    }

    /**
     * Obtient la valeur de la propriété moveable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoveable() {
        return moveable;
    }

    /**
     * Définit la valeur de la propriété moveable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoveable(String value) {
        this.moveable = value;
    }

    /**
     * Obtient la valeur de la propriété resizeable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResizeable() {
        return resizeable;
    }

    /**
     * Définit la valeur de la propriété resizeable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResizeable(String value) {
        this.resizeable = value;
    }

    /**
     * Obtient la valeur de la propriété sortDirection.
     * 
     * @return
     *     possible object is
     *     {@link SortEnum }
     *     
     */
    public SortEnum getSortDirection() {
        if (sortDirection == null) {
            return SortEnum.ASC;
        } else {
            return sortDirection;
        }
    }

    /**
     * Définit la valeur de la propriété sortDirection.
     * 
     * @param value
     *     allowed object is
     *     {@link SortEnum }
     *     
     */
    public void setSortDirection(SortEnum value) {
        this.sortDirection = value;
    }

    /**
     * Obtient la valeur de la propriété style.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyle() {
        return style;
    }

    /**
     * Définit la valeur de la propriété style.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyle(String value) {
        this.style = value;
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
     * Obtient la valeur de la propriété toolTipText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolTipText() {
        return toolTipText;
    }

    /**
     * Définit la valeur de la propriété toolTipText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolTipText(String value) {
        this.toolTipText = value;
    }

    /**
     * Obtient la valeur de la propriété width.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Définit la valeur de la propriété width.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth(Integer value) {
        this.width = value;
    }

}
