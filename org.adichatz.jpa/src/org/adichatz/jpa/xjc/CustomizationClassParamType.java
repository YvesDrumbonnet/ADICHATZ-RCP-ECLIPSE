//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:23 AM CET 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour customizationClassParamType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="customizationClassParamType">
 *   &lt;complexContent>
 *     &lt;extension base="{}paramType">
 *       &lt;attribute name="customizationClassName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="pluginKey" type="{http://www.w3.org/2001/XMLSchema}Name" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getCustomizationClassParamType", name = "customizationClassParamType")
public class CustomizationClassParamType
    extends ParamType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "customizationClassName")
    protected String customizationClassName;
    @XmlAttribute(name = "pluginKey")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "Name")
    protected String pluginKey;

    /**
     * Obtient la valeur de la propri�t� customizationClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomizationClassName() {
        return customizationClassName;
    }

    /**
     * D�finit la valeur de la propri�t� customizationClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomizationClassName(String value) {
        this.customizationClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� pluginKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPluginKey() {
        return pluginKey;
    }

    /**
     * D�finit la valeur de la propri�t� pluginKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPluginKey(String value) {
        this.pluginKey = value;
    }

}
