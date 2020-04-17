//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:17 AM CET 
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
 * <p>Classe Java pour editableFormTextType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="editableFormTextType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;choice>
 *         &lt;element name="textFont" type="{}textFontType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="textColor" type="{}textColorType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="textImage" type="{}textImageType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="parseTags" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="expandURLs" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="paragraphsSeparated" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="whitespaceNormalized" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textFont property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextFont().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textColor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextColor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textImage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextImage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * Obtient la valeur de la propri�t� parseTags.
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
     * D�finit la valeur de la propri�t� parseTags.
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
     * Obtient la valeur de la propri�t� expandURLs.
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
     * D�finit la valeur de la propri�t� expandURLs.
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
     * Obtient la valeur de la propri�t� paragraphsSeparated.
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
     * D�finit la valeur de la propri�t� paragraphsSeparated.
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
     * Obtient la valeur de la propri�t� whitespaceNormalized.
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
     * D�finit la valeur de la propri�t� whitespaceNormalized.
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

}
