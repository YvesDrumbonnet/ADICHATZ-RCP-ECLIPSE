//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour imageViewerType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="imageViewerType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}controlFieldType"&amp;gt;
 *       &amp;lt;attribute name="imageStyle" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="toolBarStyle" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="fitCanvas" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="imageType" use="required" type="{}imageTypeEnum" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * Obtient la valeur de la propriété imageStyle.
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
     * Définit la valeur de la propriété imageStyle.
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
     * Obtient la valeur de la propriété fitCanvas.
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
     * Définit la valeur de la propriété fitCanvas.
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
     * Obtient la valeur de la propriété imageType.
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
     * Définit la valeur de la propriété imageType.
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
