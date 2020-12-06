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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour hyperlinkType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="hyperlinkType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;sequence>
 *         &lt;element name="runnableCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="text" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="runnableClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getHyperlinkType", name = "hyperlinkType", propOrder = {
    "runnableCode"
})
public class HyperlinkType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String runnableCode;
    @XmlAttribute(name = "text")
    protected String text;
    @XmlAttribute(name = "runnableClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String runnableClassName;

    /**
     * Obtient la valeur de la propri�t� runnableCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunnableCode() {
        return runnableCode;
    }

    /**
     * D�finit la valeur de la propri�t� runnableCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunnableCode(String value) {
        this.runnableCode = value;
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
     * Obtient la valeur de la propri�t� runnableClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRunnableClassName() {
        return runnableClassName;
    }

    /**
     * D�finit la valeur de la propri�t� runnableClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRunnableClassName(String value) {
        this.runnableClassName = value;
    }

}
