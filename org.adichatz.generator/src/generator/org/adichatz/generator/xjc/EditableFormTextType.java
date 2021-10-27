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
 * &lt;p&gt;Classe Java pour editableFormTextType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="editableFormTextType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}controlFieldType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="textFont" type="{}textFontType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="textColor" type="{}textColorType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="textImage" type="{}textImageType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="parseTags" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="expandURLs" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="paragraphsSeparated" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="whitespaceNormalized" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getEditableFormTextType", name = "editableFormTextType", propOrder = {
    "textFont",
    "textColor",
    "textImage"
})
public class EditableFormTextType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<TextFontType> textFont;
    protected List<TextColorType> textColor;
    protected List<TextImageType> textImage;
    @XmlAttribute(name = "parseTags")
    protected String parseTags;
    @XmlAttribute(name = "expandURLs")
    protected String expandURLs;
    @XmlAttribute(name = "paragraphsSeparated")
    protected String paragraphsSeparated;
    @XmlAttribute(name = "whitespaceNormalized")
    protected String whitespaceNormalized;
    @XmlAttribute(name = "text")
    protected String text;

    /**
     * Gets the value of the textFont property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the textFont property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTextFont().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TextFontType }
     * 
     * 
     */
    public List<TextFontType> getTextFont() {
        if (textFont == null) {
            textFont = new ArrayList<TextFontType>();
        }
        return this.textFont;
    }

    /**
     * Gets the value of the textColor property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the textColor property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTextColor().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TextColorType }
     * 
     * 
     */
    public List<TextColorType> getTextColor() {
        if (textColor == null) {
            textColor = new ArrayList<TextColorType>();
        }
        return this.textColor;
    }

    /**
     * Gets the value of the textImage property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the textImage property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTextImage().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TextImageType }
     * 
     * 
     */
    public List<TextImageType> getTextImage() {
        if (textImage == null) {
            textImage = new ArrayList<TextImageType>();
        }
        return this.textImage;
    }

    /**
     * Obtient la valeur de la propriété parseTags.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParseTags() {
        return parseTags;
    }

    /**
     * Définit la valeur de la propriété parseTags.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParseTags(String value) {
        this.parseTags = value;
    }

    /**
     * Obtient la valeur de la propriété expandURLs.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpandURLs() {
        return expandURLs;
    }

    /**
     * Définit la valeur de la propriété expandURLs.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpandURLs(String value) {
        this.expandURLs = value;
    }

    /**
     * Obtient la valeur de la propriété paragraphsSeparated.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParagraphsSeparated() {
        return paragraphsSeparated;
    }

    /**
     * Définit la valeur de la propriété paragraphsSeparated.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParagraphsSeparated(String value) {
        this.paragraphsSeparated = value;
    }

    /**
     * Obtient la valeur de la propriété whitespaceNormalized.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhitespaceNormalized() {
        return whitespaceNormalized;
    }

    /**
     * Définit la valeur de la propriété whitespaceNormalized.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhitespaceNormalized(String value) {
        this.whitespaceNormalized = value;
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

}
