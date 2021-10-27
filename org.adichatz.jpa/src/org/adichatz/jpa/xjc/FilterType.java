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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Classe Java pour filterType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="filterType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}basicType"&amp;gt;
 *       &amp;lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&amp;gt;
 *       &amp;lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="column" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="searchString" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="columnType" type="{}columnTypeEnum" default="String" /&amp;gt;
 *       &amp;lt;attribute name="exactString" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&amp;gt;
 *       &amp;lt;attribute name="caseInsensitive" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "filterType")
public class FilterType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "text")
    protected String text;
    @XmlAttribute(name = "column")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String column;
    @XmlAttribute(name = "searchString")
    protected String searchString;
    @XmlAttribute(name = "columnType")
    protected ColumnTypeEnum columnType;
    @XmlAttribute(name = "exactString")
    protected Boolean exactString;
    @XmlAttribute(name = "caseInsensitive")
    protected Boolean caseInsensitive;

    /**
     * Obtient la valeur de la propriété enabled.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isEnabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    /**
     * Définit la valeur de la propriété enabled.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
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
     * Obtient la valeur de la propriété column.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumn() {
        return column;
    }

    /**
     * Définit la valeur de la propriété column.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumn(String value) {
        this.column = value;
    }

    /**
     * Obtient la valeur de la propriété searchString.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * Définit la valeur de la propriété searchString.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchString(String value) {
        this.searchString = value;
    }

    /**
     * Obtient la valeur de la propriété columnType.
     * 
     * @return
     *     possible object is
     *     {@link ColumnTypeEnum }
     *     
     */
    public ColumnTypeEnum getColumnType() {
        if (columnType == null) {
            return ColumnTypeEnum.STRING;
        } else {
            return columnType;
        }
    }

    /**
     * Définit la valeur de la propriété columnType.
     * 
     * @param value
     *     allowed object is
     *     {@link ColumnTypeEnum }
     *     
     */
    public void setColumnType(ColumnTypeEnum value) {
        this.columnType = value;
    }

    /**
     * Obtient la valeur de la propriété exactString.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isExactString() {
        if (exactString == null) {
            return true;
        } else {
            return exactString;
        }
    }

    /**
     * Définit la valeur de la propriété exactString.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExactString(Boolean value) {
        this.exactString = value;
    }

    /**
     * Obtient la valeur de la propriété caseInsensitive.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isCaseInsensitive() {
        if (caseInsensitive == null) {
            return false;
        } else {
            return caseInsensitive;
        }
    }

    /**
     * Définit la valeur de la propriété caseInsensitive.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCaseInsensitive(Boolean value) {
        this.caseInsensitive = value;
    }

}
