//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour labelProviderType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="labelProviderType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;sequence>
 *         &lt;element name="textCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="imageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="backgroundCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="foregroundCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "labelProviderType", propOrder = {
    "textCode",
    "imageCode",
    "backgroundCode",
    "foregroundCode"
})
public class LabelProviderType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String textCode;
    @XmlElement(required = true)
    protected String imageCode;
    @XmlElement(required = true)
    protected String backgroundCode;
    @XmlElement(required = true)
    protected String foregroundCode;

    /**
     * Obtient la valeur de la propri�t� textCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextCode() {
        return textCode;
    }

    /**
     * D�finit la valeur de la propri�t� textCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextCode(String value) {
        this.textCode = value;
    }

    /**
     * Obtient la valeur de la propri�t� imageCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageCode() {
        return imageCode;
    }

    /**
     * D�finit la valeur de la propri�t� imageCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageCode(String value) {
        this.imageCode = value;
    }

    /**
     * Obtient la valeur de la propri�t� backgroundCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackgroundCode() {
        return backgroundCode;
    }

    /**
     * D�finit la valeur de la propri�t� backgroundCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackgroundCode(String value) {
        this.backgroundCode = value;
    }

    /**
     * Obtient la valeur de la propri�t� foregroundCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForegroundCode() {
        return foregroundCode;
    }

    /**
     * D�finit la valeur de la propri�t� foregroundCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForegroundCode(String value) {
        this.foregroundCode = value;
    }

}
