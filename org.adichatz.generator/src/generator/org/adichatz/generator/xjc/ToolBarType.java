//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:17 AM CET 
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
 * <p>Classe Java pour toolBarType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="toolBarType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;sequence>
 *         &lt;element name="layout" type="{}layoutType"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="toolItem" type="{}toolItemType"/>
 *           &lt;element name="separator" type="{}separatorType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="layoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getToolBarType", name = "toolBarType", propOrder = {
    "layout",
    "toolItemOrSeparator"
})
public class ToolBarType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected LayoutType layout;
    @XmlElements({
        @XmlElement(name = "toolItem", type = ToolItemType.class),
        @XmlElement(name = "separator", type = SeparatorType.class)
    })
    protected List<WidgetType> toolItemOrSeparator;
    @XmlAttribute(name = "layoutData")
    protected String layoutData;

    /**
     * Obtient la valeur de la propri�t� layout.
     * 
     * @return
     *     possible object is
     *     {@link LayoutType }
     *     
     */
    public LayoutType getLayout() {
        return layout;
    }

    /**
     * D�finit la valeur de la propri�t� layout.
     * 
     * @param value
     *     allowed object is
     *     {@link LayoutType }
     *     
     */
    public void setLayout(LayoutType value) {
        this.layout = value;
    }

    /**
     * Gets the value of the toolItemOrSeparator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the toolItemOrSeparator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getToolItemOrSeparator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ToolItemType }
     * {@link SeparatorType }
     * 
     * 
     */
    public List<WidgetType> getToolItemOrSeparator() {
        if (toolItemOrSeparator == null) {
            toolItemOrSeparator = new ArrayList<WidgetType>();
        }
        return this.toolItemOrSeparator;
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

}
