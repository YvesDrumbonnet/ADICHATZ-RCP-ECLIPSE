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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour refTextType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="refTextType">
 *   &lt;complexContent>
 *     &lt;extension base="{}refControlType">
 *       &lt;choice>
 *         &lt;element name="params" type="{}paramsType"/>
 *       &lt;/choice>
 *       &lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="textLayoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tabs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getRefTextType", name = "refTextType", propOrder = {
    "params"
})
public class RefTextType
    extends RefControlType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ParamsType params;
    @XmlAttribute(name = "orientation")
    protected String orientation;
    @XmlAttribute(name = "textLayoutData")
    protected String textLayoutData;
    @XmlAttribute(name = "tabs")
    protected Integer tabs;

    /**
     * Obtient la valeur de la propri�t� params.
     * 
     * @return
     *     possible object is
     *     {@link ParamsType }
     *     
     */
    public ParamsType getParams() {
        return params;
    }

    /**
     * D�finit la valeur de la propri�t� params.
     * 
     * @param value
     *     allowed object is
     *     {@link ParamsType }
     *     
     */
    public void setParams(ParamsType value) {
        this.params = value;
    }

    /**
     * Obtient la valeur de la propri�t� orientation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     * D�finit la valeur de la propri�t� orientation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrientation(String value) {
        this.orientation = value;
    }

    /**
     * Obtient la valeur de la propri�t� textLayoutData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextLayoutData() {
        return textLayoutData;
    }

    /**
     * D�finit la valeur de la propri�t� textLayoutData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextLayoutData(String value) {
        this.textLayoutData = value;
    }

    /**
     * Obtient la valeur de la propri�t� tabs.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTabs() {
        return tabs;
    }

    /**
     * D�finit la valeur de la propri�t� tabs.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTabs(Integer value) {
        this.tabs = value;
    }

}
