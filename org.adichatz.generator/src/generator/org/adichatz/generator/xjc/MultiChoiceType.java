//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour multiChoiceType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="multiChoiceType">
 *   &lt;complexContent>
 *     &lt;extension base="{}refControlType">
 *       &lt;attribute name="numberOfColumns" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="popupToolbar" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="popupTitle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="popupMaxWidth" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="popupMaxHeight" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="multiChoiceType" use="required" type="{}multiChoiceTypeEnum" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getMultiChoiceType", name = "multiChoiceType")
public class MultiChoiceType
    extends RefControlType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "numberOfColumns")
    protected String numberOfColumns;
    @XmlAttribute(name = "popupToolbar")
    protected String popupToolbar;
    @XmlAttribute(name = "popupTitle")
    protected String popupTitle;
    @XmlAttribute(name = "popupMaxWidth")
    protected String popupMaxWidth;
    @XmlAttribute(name = "popupMaxHeight")
    protected String popupMaxHeight;
    @XmlAttribute(name = "multiChoiceType", required = true)
    protected MultiChoiceTypeEnum multiChoiceType;

    /**
     * Obtient la valeur de la propriété numberOfColumns.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Définit la valeur de la propriété numberOfColumns.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfColumns(String value) {
        this.numberOfColumns = value;
    }

    /**
     * Obtient la valeur de la propriété popupToolbar.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopupToolbar() {
        return popupToolbar;
    }

    /**
     * Définit la valeur de la propriété popupToolbar.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopupToolbar(String value) {
        this.popupToolbar = value;
    }

    /**
     * Obtient la valeur de la propriété popupTitle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopupTitle() {
        return popupTitle;
    }

    /**
     * Définit la valeur de la propriété popupTitle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopupTitle(String value) {
        this.popupTitle = value;
    }

    /**
     * Obtient la valeur de la propriété popupMaxWidth.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopupMaxWidth() {
        return popupMaxWidth;
    }

    /**
     * Définit la valeur de la propriété popupMaxWidth.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopupMaxWidth(String value) {
        this.popupMaxWidth = value;
    }

    /**
     * Obtient la valeur de la propriété popupMaxHeight.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopupMaxHeight() {
        return popupMaxHeight;
    }

    /**
     * Définit la valeur de la propriété popupMaxHeight.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopupMaxHeight(String value) {
        this.popupMaxHeight = value;
    }

    /**
     * Obtient la valeur de la propriété multiChoiceType.
     * 
     * @return
     *     possible object is
     *     {@link MultiChoiceTypeEnum }
     *     
     */
    public MultiChoiceTypeEnum getMultiChoiceType() {
        return multiChoiceType;
    }

    /**
     * Définit la valeur de la propriété multiChoiceType.
     * 
     * @param value
     *     allowed object is
     *     {@link MultiChoiceTypeEnum }
     *     
     */
    public void setMultiChoiceType(MultiChoiceTypeEnum value) {
        this.multiChoiceType = value;
    }

}
