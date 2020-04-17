//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:23 AM CET 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour recentOpenEditorType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="recentOpenEditorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="params" type="{}paramsType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="recentId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lastOpen" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="lastClose" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getRecentOpenEditorType", name = "recentOpenEditorType", propOrder = {
    "params"
})
@XmlSeeAlso({
    RecentOpenQueryEditorType.class
})
public class RecentOpenEditorType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ParamsType params;
    @XmlAttribute(name = "recentId", required = true)
    protected String recentId;
    @XmlAttribute(name = "label")
    protected String label;
    @XmlAttribute(name = "lastOpen")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastOpen;
    @XmlAttribute(name = "lastClose")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastClose;

    /**
     * Obtient la valeur de la propriété params.
     * 
     * @return
     *     possible object is
     *     {@link ParamsType }
     *     
     */
    public ParamsType getParams() {
        return params;
    }

    /**
     * Définit la valeur de la propriété params.
     * 
     * @param value
     *     allowed object is
     *     {@link ParamsType }
     *     
     */
    public void setParams(ParamsType value) {
        this.params = value;
    }

    /**
     * Obtient la valeur de la propriété recentId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecentId() {
        return recentId;
    }

    /**
     * Définit la valeur de la propriété recentId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecentId(String value) {
        this.recentId = value;
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
     * Obtient la valeur de la propriété lastOpen.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastOpen() {
        return lastOpen;
    }

    /**
     * Définit la valeur de la propriété lastOpen.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastOpen(XMLGregorianCalendar value) {
        this.lastOpen = value;
    }

    /**
     * Obtient la valeur de la propriété lastClose.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastClose() {
        return lastClose;
    }

    /**
     * Définit la valeur de la propriété lastClose.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastClose(XMLGregorianCalendar value) {
        this.lastClose = value;
    }

}
