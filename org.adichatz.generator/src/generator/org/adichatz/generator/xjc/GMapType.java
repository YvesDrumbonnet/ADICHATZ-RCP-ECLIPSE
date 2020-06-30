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
 * <p>Classe Java pour gMapType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� addMarker.
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
     * D�finit la valeur de la propri�t� addMarker.
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
     * Obtient la valeur de la propri�t� changeCoordinates.
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
     * D�finit la valeur de la propri�t� changeCoordinates.
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
     * Obtient la valeur de la propri�t� delayMillisLoading.
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
     * D�finit la valeur de la propri�t� delayMillisLoading.
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
     * Obtient la valeur de la propri�t� editable.
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
     * D�finit la valeur de la propri�t� editable.
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
     * Obtient la valeur de la propri�t� coordPattern.
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
     * D�finit la valeur de la propri�t� coordPattern.
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
     * Obtient la valeur de la propri�t� locale.
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
     * D�finit la valeur de la propri�t� locale.
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
     * Obtient la valeur de la propri�t� mapTypeControl.
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
     * D�finit la valeur de la propri�t� mapTypeControl.
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
     * Obtient la valeur de la propri�t� mapDataType.
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
     * D�finit la valeur de la propri�t� mapDataType.
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
     * Obtient la valeur de la propri�t� mapTypeId.
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
     * D�finit la valeur de la propri�t� mapTypeId.
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
     * Obtient la valeur de la propri�t� toolBarStyle.
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
     * D�finit la valeur de la propri�t� toolBarStyle.
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
     * Obtient la valeur de la propri�t� zoom.
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
     * D�finit la valeur de la propri�t� zoom.
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
