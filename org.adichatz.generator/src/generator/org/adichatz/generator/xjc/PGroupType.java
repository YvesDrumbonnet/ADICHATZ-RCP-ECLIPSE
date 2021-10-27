//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
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
 * &lt;p&gt;Classe Java pour pGroupType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="pGroupType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}clientCanvasType"&amp;gt;
 *       &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *         &amp;lt;element name="pgroupToolItem" type="{}pGroupToolItemType"/&amp;gt;
 *         &amp;lt;element name="pgroupMenu" type="{}pGroupMenuType"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="toggleRenderer" type="{}toggleRendererEnum" /&amp;gt;
 *       &amp;lt;attribute name="toolItemRenderer" type="{}toolItemRendererEnum" /&amp;gt;
 *       &amp;lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="togglePosition" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="imagePosition" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="linePosition" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the pgroupToolItemOrPgroupMenu property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPgroupToolItemOrPgroupMenu().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
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
