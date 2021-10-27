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
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour formattedTextType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="formattedTextType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}controlFieldType"&amp;gt;
 *       &amp;lt;attribute name="editable" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="locale" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="displayPattern" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="editPattern" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="format" type="{}formatEnum" default="Integer" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
