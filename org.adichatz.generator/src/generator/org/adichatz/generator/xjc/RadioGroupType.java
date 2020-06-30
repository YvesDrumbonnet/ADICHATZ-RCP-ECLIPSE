//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour radioGroupType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="radioGroupType">
 *   &lt;complexContent>
 *     &lt;extension base="{}refControlType">
 *       &lt;sequence minOccurs="0">
 *         &lt;choice>
 *           &lt;element name="layout" type="{}layoutType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="defaultSelection" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getRadioGroupType", name = "radioGroupType", propOrder = {
    "layout"
})
public class RadioGroupType
    extends RefControlType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected LayoutType layout;
    @XmlAttribute(name = "defaultSelection")
    protected String defaultSelection;

    /**
     * Obtient la valeur de la propriété layout.
     * 
     * @return
     *     possible object is
     *     {@link LayoutType }
     *     
     */
    public LayoutType getLayout() {
        return layout;
    }

    /**
     * Définit la valeur de la propriété layout.
     * 
     * @param value
     *     allowed object is
     *     {@link LayoutType }
     *     
     */
    public void setLayout(LayoutType value) {
        this.layout = value;
    }

    /**
     * Obtient la valeur de la propriété defaultSelection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultSelection() {
        return defaultSelection;
    }

    /**
     * Définit la valeur de la propriété defaultSelection.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultSelection(String value) {
        this.defaultSelection = value;
    }

}
