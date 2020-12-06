//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour treeElementType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="treeElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="treeNode" type="{}treeNodeType" maxOccurs="unbounded"/>
 *         &lt;element name="backgroundCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="foregroundlCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fontCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="labelCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="imageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *       &lt;attribute name="entityURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="background" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="foreground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="font" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="image" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="treeChildClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "treeElementType", propOrder = {
    "treeNode",
    "backgroundCode",
    "foregroundlCode",
    "fontCode",
    "labelCode",
    "imageCode"
})
public class TreeElementType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<TreeNodeType> treeNode;
    protected String backgroundCode;
    protected String foregroundlCode;
    protected String fontCode;
    protected String labelCode;
    protected String imageCode;
    @XmlAttribute(name = "entityURI", required = true)
    protected String entityURI;
    @XmlAttribute(name = "background")
    protected String background;
    @XmlAttribute(name = "foreground")
    protected String foreground;
    @XmlAttribute(name = "font")
    protected String font;
    @XmlAttribute(name = "image")
    protected String image;
    @XmlAttribute(name = "label")
    protected String label;
    @XmlAttribute(name = "treeChildClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String treeChildClassName;

    /**
     * Gets the value of the treeNode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the treeNode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTreeNode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TreeNodeType }
     * 
     * 
     */
    public List<TreeNodeType> getTreeNode() {
        if (treeNode == null) {
            treeNode = new ArrayList<TreeNodeType>();
        }
        return this.treeNode;
    }

    /**
     * Obtient la valeur de la propriété backgroundCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackgroundCode() {
        return backgroundCode;
    }

    /**
     * Définit la valeur de la propriété backgroundCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackgroundCode(String value) {
        this.backgroundCode = value;
    }

    /**
     * Obtient la valeur de la propriété foregroundlCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForegroundlCode() {
        return foregroundlCode;
    }

    /**
     * Définit la valeur de la propriété foregroundlCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForegroundlCode(String value) {
        this.foregroundlCode = value;
    }

    /**
     * Obtient la valeur de la propriété fontCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontCode() {
        return fontCode;
    }

    /**
     * Définit la valeur de la propriété fontCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontCode(String value) {
        this.fontCode = value;
    }

    /**
     * Obtient la valeur de la propriété labelCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelCode() {
        return labelCode;
    }

    /**
     * Définit la valeur de la propriété labelCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelCode(String value) {
        this.labelCode = value;
    }

    /**
     * Obtient la valeur de la propriété imageCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageCode() {
        return imageCode;
    }

    /**
     * Définit la valeur de la propriété imageCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageCode(String value) {
        this.imageCode = value;
    }

    /**
     * Obtient la valeur de la propriété entityURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityURI() {
        return entityURI;
    }

    /**
     * Définit la valeur de la propriété entityURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityURI(String value) {
        this.entityURI = value;
    }

    /**
     * Obtient la valeur de la propriété background.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackground() {
        return background;
    }

    /**
     * Définit la valeur de la propriété background.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackground(String value) {
        this.background = value;
    }

    /**
     * Obtient la valeur de la propriété foreground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeground() {
        return foreground;
    }

    /**
     * Définit la valeur de la propriété foreground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeground(String value) {
        this.foreground = value;
    }

    /**
     * Obtient la valeur de la propriété font.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFont() {
        return font;
    }

    /**
     * Définit la valeur de la propriété font.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFont(String value) {
        this.font = value;
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
     * Obtient la valeur de la propriété label.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Définit la valeur de la propriété label.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Obtient la valeur de la propriété treeChildClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreeChildClassName() {
        return treeChildClassName;
    }

    /**
     * Définit la valeur de la propriété treeChildClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreeChildClassName(String value) {
        this.treeChildClassName = value;
    }

}
