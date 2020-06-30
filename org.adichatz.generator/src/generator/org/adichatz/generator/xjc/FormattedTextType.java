//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour formattedTextType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="formattedTextType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;attribute name="editable" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="locale" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="displayPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="editPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="format" type="{}formatEnum" default="Integer" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getFormattedTextType", name = "formattedTextType")
public class FormattedTextType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "editable")
    protected String editable;
    @XmlAttribute(name = "locale")
    protected String locale;
    @XmlAttribute(name = "displayPattern")
    protected String displayPattern;
    @XmlAttribute(name = "editPattern")
    protected String editPattern;
    @XmlAttribute(name = "format")
    protected FormatEnum format;

    /**
     * Obtient la valeur de la propriété editable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditable() {
        return editable;
    }

    /**
     * Définit la valeur de la propriété editable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditable(String value) {
        this.editable = value;
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
     * Obtient la valeur de la propriété displayPattern.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayPattern() {
        return displayPattern;
    }

    /**
     * Définit la valeur de la propriété displayPattern.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayPattern(String value) {
        this.displayPattern = value;
    }

    /**
     * Obtient la valeur de la propriété editPattern.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditPattern() {
        return editPattern;
    }

    /**
     * Définit la valeur de la propriété editPattern.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditPattern(String value) {
        this.editPattern = value;
    }

    /**
     * Obtient la valeur de la propriété format.
     * 
     * @return
     *     possible object is
     *     {@link FormatEnum }
     *     
     */
    public FormatEnum getFormat() {
        if (format == null) {
            return FormatEnum.INTEGER;
        } else {
            return format;
        }
    }

    /**
     * Définit la valeur de la propriété format.
     * 
     * @param value
     *     allowed object is
     *     {@link FormatEnum }
     *     
     */
    public void setFormat(FormatEnum value) {
        this.format = value;
    }

}
