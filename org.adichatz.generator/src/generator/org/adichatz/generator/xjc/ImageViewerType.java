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
 * <p>Classe Java pour imageViewerType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="imageViewerType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;attribute name="imageStyle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="toolBarStyle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fitCanvas" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="imageType" use="required" type="{}imageTypeEnum" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getImageViewerType", name = "imageViewerType")
public class ImageViewerType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "imageStyle")
    protected String imageStyle;
    @XmlAttribute(name = "toolBarStyle")
    protected String toolBarStyle;
    @XmlAttribute(name = "fitCanvas")
    protected String fitCanvas;
    @XmlAttribute(name = "imageType", required = true)
    protected ImageTypeEnum imageType;

    /**
     * Obtient la valeur de la propri�t� imageStyle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageStyle() {
        return imageStyle;
    }

    /**
     * D�finit la valeur de la propri�t� imageStyle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageStyle(String value) {
        this.imageStyle = value;
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
     * Obtient la valeur de la propri�t� fitCanvas.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFitCanvas() {
        return fitCanvas;
    }

    /**
     * D�finit la valeur de la propri�t� fitCanvas.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFitCanvas(String value) {
        this.fitCanvas = value;
    }

    /**
     * Obtient la valeur de la propri�t� imageType.
     * 
     * @return
     *     possible object is
     *     {@link ImageTypeEnum }
     *     
     */
    public ImageTypeEnum getImageType() {
        return imageType;
    }

    /**
     * D�finit la valeur de la propri�t� imageType.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageTypeEnum }
     *     
     */
    public void setImageType(ImageTypeEnum value) {
        this.imageType = value;
    }

}
