//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
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
 * <p>Classe Java pour cTabFolderType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="cTabFolderType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;choice>
 *         &lt;choice>
 *           &lt;element name="cTabItem" type="{}cTabItemType" maxOccurs="unbounded"/>
 *           &lt;element name="include" type="{}includeType" maxOccurs="unbounded"/>
 *         &lt;/choice>
 *       &lt;/choice>
 *       &lt;attribute name="delayed" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="layoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="selection" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getCTabFolderType", name = "cTabFolderType", propOrder = {
    "cTabItem",
    "include"
})
public class CTabFolderType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<CTabItemType> cTabItem;
    protected List<IncludeType> include;
    @XmlAttribute(name = "delayed")
    protected String delayed;
    @XmlAttribute(name = "layoutData")
    protected String layoutData;
    @XmlAttribute(name = "selection")
    protected String selection;

    /**
     * Gets the value of the cTabItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cTabItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCTabItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CTabItemType }
     * 
     * 
     */
    public List<CTabItemType> getCTabItem() {
        if (cTabItem == null) {
            cTabItem = new ArrayList<CTabItemType>();
        }
        return this.cTabItem;
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
     * Obtient la valeur de la propri�t� delayed.
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
     * D�finit la valeur de la propri�t� delayed.
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
     * Obtient la valeur de la propri�t� layoutData.
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
     * D�finit la valeur de la propri�t� layoutData.
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
     * Obtient la valeur de la propri�t� selection.
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
     * D�finit la valeur de la propri�t� selection.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelection(String value) {
        this.selection = value;
    }

}
