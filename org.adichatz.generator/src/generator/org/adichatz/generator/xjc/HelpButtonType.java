//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour helpButtonType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="helpButtonType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlType">
 *       &lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="helpLabel" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="helpSpecify" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="helpMessage" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getHelpButtonType", name = "helpButtonType")
public class HelpButtonType
    extends ControlType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "helpLabel", required = true)
    protected String helpLabel;
    @XmlAttribute(name = "helpSpecify", required = true)
    protected String helpSpecify;
    @XmlAttribute(name = "helpMessage", required = true)
    protected String helpMessage;

    /**
     * Obtient la valeur de la propri�t� image.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * D�finit la valeur de la propri�t� image.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Obtient la valeur de la propri�t� helpLabel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHelpLabel() {
        return helpLabel;
    }

    /**
     * D�finit la valeur de la propri�t� helpLabel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHelpLabel(String value) {
        this.helpLabel = value;
    }

    /**
     * Obtient la valeur de la propri�t� helpSpecify.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHelpSpecify() {
        return helpSpecify;
    }

    /**
     * D�finit la valeur de la propri�t� helpSpecify.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHelpSpecify(String value) {
        this.helpSpecify = value;
    }

    /**
     * Obtient la valeur de la propri�t� helpMessage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHelpMessage() {
        return helpMessage;
    }

    /**
     * D�finit la valeur de la propri�t� helpMessage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHelpMessage(String value) {
        this.helpMessage = value;
    }

}
