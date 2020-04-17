//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour pShelfType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="pShelfType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;choice>
 *         &lt;choice>
 *           &lt;element name="pshelfItem" type="{}pShelfItemType" maxOccurs="unbounded"/>
 *           &lt;element name="include" type="{}includeType" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/choice>
 *       &lt;attribute name="delayed" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="layoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="selection" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="shelfRenderer" type="{}shelfRendererEnum" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getPShelfType", name = "pShelfType", propOrder = {
    "pshelfItem",
    "include"
})
public class PShelfType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<PShelfItemType> pshelfItem;
    protected List<IncludeType> include;
    @XmlAttribute(name = "delayed")
    protected String delayed;
    @XmlAttribute(name = "layoutData")
    protected String layoutData;
    @XmlAttribute(name = "selection")
    protected String selection;
    @XmlAttribute(name = "shelfRenderer")
    protected ShelfRendererEnum shelfRenderer;

    /**
     * Gets the value of the pshelfItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pshelfItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPshelfItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PShelfItemType }
     * 
     * 
     */
    public List<PShelfItemType> getPshelfItem() {
        if (pshelfItem == null) {
            pshelfItem = new ArrayList<PShelfItemType>();
        }
        return this.pshelfItem;
    }

    /**
     * Gets the value of the include property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the include property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInclude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IncludeType }
     * 
     * 
     */
    public List<IncludeType> getInclude() {
        if (include == null) {
            include = new ArrayList<IncludeType>();
        }
        return this.include;
    }

    /**
     * Obtient la valeur de la propriété delayed.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelayed() {
        return delayed;
    }

    /**
     * Définit la valeur de la propriété delayed.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelayed(String value) {
        this.delayed = value;
    }

    /**
     * Obtient la valeur de la propriété layoutData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLayoutData() {
        return layoutData;
    }

    /**
     * Définit la valeur de la propriété layoutData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLayoutData(String value) {
        this.layoutData = value;
    }

    /**
     * Obtient la valeur de la propriété selection.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelection() {
        return selection;
    }

    /**
     * Définit la valeur de la propriété selection.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelection(String value) {
        this.selection = value;
    }

    /**
     * Obtient la valeur de la propriété shelfRenderer.
     * 
     * @return
     *     possible object is
     *     {@link ShelfRendererEnum }
     *     
     */
    public ShelfRendererEnum getShelfRenderer() {
        return shelfRenderer;
    }

    /**
     * Définit la valeur de la propriété shelfRenderer.
     * 
     * @param value
     *     allowed object is
     *     {@link ShelfRendererEnum }
     *     
     */
    public void setShelfRenderer(ShelfRendererEnum value) {
        this.shelfRenderer = value;
    }

}
