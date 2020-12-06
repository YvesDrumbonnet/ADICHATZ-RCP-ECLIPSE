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
 * <p>Classe Java pour multiChoiceType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� numberOfColumns.
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
     * D�finit la valeur de la propri�t� numberOfColumns.
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
     * Obtient la valeur de la propri�t� popupToolbar.
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
     * D�finit la valeur de la propri�t� popupToolbar.
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
     * Obtient la valeur de la propri�t� popupTitle.
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
     * D�finit la valeur de la propri�t� popupTitle.
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
     * Obtient la valeur de la propri�t� popupMaxWidth.
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
     * D�finit la valeur de la propri�t� popupMaxWidth.
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
     * Obtient la valeur de la propri�t� popupMaxHeight.
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
     * D�finit la valeur de la propri�t� popupMaxHeight.
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
     * Obtient la valeur de la propri�t� multiChoiceType.
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
     * D�finit la valeur de la propri�t� multiChoiceType.
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
