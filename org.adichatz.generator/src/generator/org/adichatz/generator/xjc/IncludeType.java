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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour includeType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="includeType">
 *   &lt;complexContent>
 *     &lt;extension base="{}widgetType">
 *       &lt;choice>
 *         &lt;element name="params" type="{}paramsType"/>
 *         &lt;element name="customizations" type="{}customizationsType"/>
 *       &lt;/choice>
 *       &lt;attribute name="adiResourceURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getIncludeType", name = "includeType", propOrder = {
    "params",
    "customizations"
})
public class IncludeType
    extends WidgetType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ParamsType params;
    protected CustomizationsType customizations;
    @XmlAttribute(name = "adiResourceURI")
    protected String adiResourceURI;

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
     * Obtient la valeur de la propri�t� customizations.
     * 
     * @return
     *     possible object is
     *     {@link CustomizationsType }
     *     
     */
    public CustomizationsType getCustomizations() {
        return customizations;
    }

    /**
     * D�finit la valeur de la propri�t� customizations.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomizationsType }
     *     
     */
    public void setCustomizations(CustomizationsType value) {
        this.customizations = value;
    }

    /**
     * Obtient la valeur de la propri�t� adiResourceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdiResourceURI() {
        return adiResourceURI;
    }

    /**
     * D�finit la valeur de la propri�t� adiResourceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdiResourceURI(String value) {
        this.adiResourceURI = value;
    }

}
