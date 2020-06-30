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
 * <p>Classe Java pour gMapType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="gMapType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;attribute name="addMarker" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="changeCoordinates" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="delayMillisLoading" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="editable" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="coordPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="locale" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mapTypeControl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mapDataType" use="required" type="{}mapDataTypeEnum" />
 *       &lt;attribute name="mapTypeId" type="{}mapTypeIdEnum" />
 *       &lt;attribute name="toolBarStyle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="zoom" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getgMapType", name = "gMapType")
public class GMapType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "addMarker")
    protected String addMarker;
    @XmlAttribute(name = "changeCoordinates")
    protected String changeCoordinates;
    @XmlAttribute(name = "delayMillisLoading")
    protected String delayMillisLoading;
    @XmlAttribute(name = "editable")
    protected String editable;
    @XmlAttribute(name = "coordPattern")
    protected String coordPattern;
    @XmlAttribute(name = "locale")
    protected String locale;
    @XmlAttribute(name = "mapTypeControl")
    protected String mapTypeControl;
    @XmlAttribute(name = "mapDataType", required = true)
    protected MapDataTypeEnum mapDataType;
    @XmlAttribute(name = "mapTypeId")
    protected MapTypeIdEnum mapTypeId;
    @XmlAttribute(name = "toolBarStyle")
    protected String toolBarStyle;
    @XmlAttribute(name = "zoom")
    protected String zoom;

    /**
     * Obtient la valeur de la propriété addMarker.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddMarker() {
        return addMarker;
    }

    /**
     * Définit la valeur de la propriété addMarker.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddMarker(String value) {
        this.addMarker = value;
    }

    /**
     * Obtient la valeur de la propriété changeCoordinates.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeCoordinates() {
        return changeCoordinates;
    }

    /**
     * Définit la valeur de la propriété changeCoordinates.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeCoordinates(String value) {
        this.changeCoordinates = value;
    }

    /**
     * Obtient la valeur de la propriété delayMillisLoading.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelayMillisLoading() {
        return delayMillisLoading;
    }

    /**
     * Définit la valeur de la propriété delayMillisLoading.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelayMillisLoading(String value) {
        this.delayMillisLoading = value;
    }

    /**
     * Obtient la valeur de la propriété editable.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditable() {
        return editable;
    }

    /**
     * Définit la valeur de la propriété editable.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditable(String value) {
        this.editable = value;
    }

    /**
     * Obtient la valeur de la propriété coordPattern.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoordPattern() {
        return coordPattern;
    }

    /**
     * Définit la valeur de la propriété coordPattern.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoordPattern(String value) {
        this.coordPattern = value;
    }

    /**
     * Obtient la valeur de la propriété locale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Définit la valeur de la propriété locale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Obtient la valeur de la propriété mapTypeControl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapTypeControl() {
        return mapTypeControl;
    }

    /**
     * Définit la valeur de la propriété mapTypeControl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapTypeControl(String value) {
        this.mapTypeControl = value;
    }

    /**
     * Obtient la valeur de la propriété mapDataType.
     * 
     * @return
     *     possible object is
     *     {@link MapDataTypeEnum }
     *     
     */
    public MapDataTypeEnum getMapDataType() {
        return mapDataType;
    }

    /**
     * Définit la valeur de la propriété mapDataType.
     * 
     * @param value
     *     allowed object is
     *     {@link MapDataTypeEnum }
     *     
     */
    public void setMapDataType(MapDataTypeEnum value) {
        this.mapDataType = value;
    }

    /**
     * Obtient la valeur de la propriété mapTypeId.
     * 
     * @return
     *     possible object is
     *     {@link MapTypeIdEnum }
     *     
     */
    public MapTypeIdEnum getMapTypeId() {
        return mapTypeId;
    }

    /**
     * Définit la valeur de la propriété mapTypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link MapTypeIdEnum }
     *     
     */
    public void setMapTypeId(MapTypeIdEnum value) {
        this.mapTypeId = value;
    }

    /**
     * Obtient la valeur de la propriété toolBarStyle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolBarStyle() {
        return toolBarStyle;
    }

    /**
     * Définit la valeur de la propriété toolBarStyle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolBarStyle(String value) {
        this.toolBarStyle = value;
    }

    /**
     * Obtient la valeur de la propriété zoom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoom() {
        return zoom;
    }

    /**
     * Définit la valeur de la propriété zoom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoom(String value) {
        this.zoom = value;
    }

}
