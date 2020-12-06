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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour buttonBarType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="buttonBarType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="button" type="{}buttonType"/>
 *         &lt;element name="separator" type="{}separatorType"/>
 *       &lt;/choice>
 *       &lt;attribute name="separator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getButtonBarType", name = "buttonBarType", propOrder = {
    "buttonOrSeparator"
})
public class ButtonBarType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "button", type = ButtonType.class),
        @XmlElement(name = "separator", type = SeparatorType.class)
    })
    protected List<WidgetType> buttonOrSeparator;
    @XmlAttribute(name = "separator")
    protected Boolean separator;

    /**
     * Gets the value of the buttonOrSeparator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the buttonOrSeparator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getButtonOrSeparator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ButtonType }
     * {@link SeparatorType }
     * 
     * 
     */
    public List<WidgetType> getButtonOrSeparator() {
        if (buttonOrSeparator == null) {
            buttonOrSeparator = new ArrayList<WidgetType>();
        }
        return this.buttonOrSeparator;
    }

    /**
     * Obtient la valeur de la propri�t� separator.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSeparator() {
        return separator;
    }

    /**
     * D�finit la valeur de la propri�t� separator.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSeparator(Boolean value) {
        this.separator = value;
    }

}
