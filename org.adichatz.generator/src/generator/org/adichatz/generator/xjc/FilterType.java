//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour filterType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="filterType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="column" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="searchString" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="columnType" type="{}columnTypeEnum" default="String" />
 *       &lt;attribute name="exactString" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="caseInsensitive" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Obtient la valeur de la propri�t� enabled.
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
     * D�finit la valeur de la propri�t� enabled.
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
     * Obtient la valeur de la propri�t� column.
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
     * D�finit la valeur de la propri�t� column.
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
     * Obtient la valeur de la propri�t� searchString.
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
     * D�finit la valeur de la propri�t� searchString.
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
     * Obtient la valeur de la propri�t� columnType.
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
     * D�finit la valeur de la propri�t� columnType.
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
     * Obtient la valeur de la propri�t� exactString.
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
     * D�finit la valeur de la propri�t� exactString.
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
     * Obtient la valeur de la propri�t� caseInsensitive.
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
     * D�finit la valeur de la propri�t� caseInsensitive.
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
