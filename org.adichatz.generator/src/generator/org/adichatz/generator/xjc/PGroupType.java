//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour pGroupType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="pGroupType">
 *   &lt;complexContent>
 *     &lt;extension base="{}clientCanvasType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="pgroupToolItem" type="{}pGroupToolItemType"/>
 *         &lt;element name="pgroupMenu" type="{}pGroupMenuType"/>
 *       &lt;/choice>
 *       &lt;attribute name="toggleRenderer" type="{}toggleRendererEnum" />
 *       &lt;attribute name="toolItemRenderer" type="{}toolItemRendererEnum" />
 *       &lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="togglePosition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="imagePosition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="linePosition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getPGroupType", name = "pGroupType", propOrder = {
    "pgroupToolItemOrPgroupMenu"
})
public class PGroupType
    extends ClientCanvasType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "pgroupToolItem", type = PGroupToolItemType.class),
        @XmlElement(name = "pgroupMenu", type = PGroupMenuType.class)
    })
    protected List<ValidElementType> pgroupToolItemOrPgroupMenu;
    @XmlAttribute(name = "toggleRenderer")
    protected ToggleRendererEnum toggleRenderer;
    @XmlAttribute(name = "toolItemRenderer")
    protected ToolItemRendererEnum toolItemRenderer;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "togglePosition")
    protected String togglePosition;
    @XmlAttribute(name = "imagePosition")
    protected String imagePosition;
    @XmlAttribute(name = "linePosition")
    protected String linePosition;

    /**
     * Gets the value of the pgroupToolItemOrPgroupMenu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pgroupToolItemOrPgroupMenu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPgroupToolItemOrPgroupMenu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PGroupToolItemType }
     * {@link PGroupMenuType }
     * 
     * 
     */
    public List<ValidElementType> getPgroupToolItemOrPgroupMenu() {
        if (pgroupToolItemOrPgroupMenu == null) {
            pgroupToolItemOrPgroupMenu = new ArrayList<ValidElementType>();
        }
        return this.pgroupToolItemOrPgroupMenu;
    }

    /**
     * Obtient la valeur de la propriété toggleRenderer.
     * 
     * @return
     *     possible object is
     *     {@link ToggleRendererEnum }
     *     
     */
    public ToggleRendererEnum getToggleRenderer() {
        return toggleRenderer;
    }

    /**
     * Définit la valeur de la propriété toggleRenderer.
     * 
     * @param value
     *     allowed object is
     *     {@link ToggleRendererEnum }
     *     
     */
    public void setToggleRenderer(ToggleRendererEnum value) {
        this.toggleRenderer = value;
    }

    /**
     * Obtient la valeur de la propriété toolItemRenderer.
     * 
     * @return
     *     possible object is
     *     {@link ToolItemRendererEnum }
     *     
     */
    public ToolItemRendererEnum getToolItemRenderer() {
        return toolItemRenderer;
    }

    /**
     * Définit la valeur de la propriété toolItemRenderer.
     * 
     * @param value
     *     allowed object is
     *     {@link ToolItemRendererEnum }
     *     
     */
    public void setToolItemRenderer(ToolItemRendererEnum value) {
        this.toolItemRenderer = value;
    }

    /**
     * Obtient la valeur de la propriété image.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Définit la valeur de la propriété image.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Obtient la valeur de la propriété togglePosition.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTogglePosition() {
        return togglePosition;
    }

    /**
     * Définit la valeur de la propriété togglePosition.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTogglePosition(String value) {
        this.togglePosition = value;
    }

    /**
     * Obtient la valeur de la propriété imagePosition.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagePosition() {
        return imagePosition;
    }

    /**
     * Définit la valeur de la propriété imagePosition.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagePosition(String value) {
        this.imagePosition = value;
    }

    /**
     * Obtient la valeur de la propriété linePosition.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinePosition() {
        return linePosition;
    }

    /**
     * Définit la valeur de la propriété linePosition.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinePosition(String value) {
        this.linePosition = value;
    }

}
