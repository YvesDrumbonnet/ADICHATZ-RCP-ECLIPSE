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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour labelProviderType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="labelProviderType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}basicType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="textCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="imageCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="backgroundCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;element name="foregroundCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * Obtient la valeur de la propriété textCode.
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
     * Définit la valeur de la propriété textCode.
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
     * Obtient la valeur de la propriété imageCode.
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
     * Définit la valeur de la propriété imageCode.
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
     * Obtient la valeur de la propriété backgroundCode.
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
     * Définit la valeur de la propriété backgroundCode.
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
     * Obtient la valeur de la propriété foregroundCode.
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
     * Définit la valeur de la propriété foregroundCode.
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
