//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propriété controllerClassName.
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
     * Définit la valeur de la propriété controllerClassName.
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
     * Obtient la valeur de la propriété minimumWidth.
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
     * Définit la valeur de la propriété minimumWidth.
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

    /**
     * Obtient la valeur de la propriété summary.
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
     * Définit la valeur de la propriété summary.
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
     * Obtient la valeur de la propriété wordWrap.
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
     * Définit la valeur de la propriété wordWrap.
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
