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
 * <p>Classe Java pour gridColumnType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="gridColumnType">
 *   &lt;complexContent>
 *     &lt;extension base="{}columnFieldType">
 *       &lt;attribute name="controllerClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="minimumWidth" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="cellSelectionEnabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="summary" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="wordWrap" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getGridColumnType", name = "gridColumnType")
public class GridColumnType
    extends ColumnFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "controllerClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String controllerClassName;
    @XmlAttribute(name = "minimumWidth")
    protected Integer minimumWidth;
    @XmlAttribute(name = "cellSelectionEnabled")
    protected String cellSelectionEnabled;
    @XmlAttribute(name = "summary")
    protected String summary;
    @XmlAttribute(name = "wordWrap")
    protected String wordWrap;
    @XmlAttribute(name = "headerFont")
    protected String headerFont;
    @XmlAttribute(name = "headerWordWrap")
    protected String headerWordWrap;

    /**
     * Obtient la valeur de la propri�t� controllerClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControllerClassName() {
        return controllerClassName;
    }

    /**
     * D�finit la valeur de la propri�t� controllerClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControllerClassName(String value) {
        this.controllerClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� minimumWidth.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinimumWidth() {
        return minimumWidth;
    }

    /**
     * D�finit la valeur de la propri�t� minimumWidth.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinimumWidth(Integer value) {
        this.minimumWidth = value;
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

    /**
     * Obtient la valeur de la propri�t� summary.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummary() {
        return summary;
    }

    /**
     * D�finit la valeur de la propri�t� summary.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummary(String value) {
        this.summary = value;
    }

    /**
     * Obtient la valeur de la propri�t� wordWrap.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWordWrap() {
        return wordWrap;
    }

    /**
     * D�finit la valeur de la propri�t� wordWrap.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWordWrap(String value) {
        this.wordWrap = value;
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
