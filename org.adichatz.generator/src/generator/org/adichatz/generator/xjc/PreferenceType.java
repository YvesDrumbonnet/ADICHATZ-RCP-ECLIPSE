//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour preferenceType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="preferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;choice>
 *           &lt;element name="queryPreference" type="{}queryPreferenceType"/>
 *           &lt;element name="controllerPreference" type="{}controllerPreferenceType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "preferenceType", propOrder = {
    "queryPreference",
    "controllerPreference"
})
public class PreferenceType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected QueryPreferenceType queryPreference;
    protected ControllerPreferenceType controllerPreference;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Obtient la valeur de la propri�t� queryPreference.
     * 
     * @return
     *     possible object is
     *     {@link QueryPreferenceType }
     *     
     */
    public QueryPreferenceType getQueryPreference() {
        return queryPreference;
    }

    /**
     * D�finit la valeur de la propri�t� queryPreference.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryPreferenceType }
     *     
     */
    public void setQueryPreference(QueryPreferenceType value) {
        this.queryPreference = value;
    }

    /**
     * Obtient la valeur de la propri�t� controllerPreference.
     * 
     * @return
     *     possible object is
     *     {@link ControllerPreferenceType }
     *     
     */
    public ControllerPreferenceType getControllerPreference() {
        return controllerPreference;
    }

    /**
     * D�finit la valeur de la propri�t� controllerPreference.
     * 
     * @param value
     *     allowed object is
     *     {@link ControllerPreferenceType }
     *     
     */
    public void setControllerPreference(ControllerPreferenceType value) {
        this.controllerPreference = value;
    }

    /**
     * Obtient la valeur de la propri�t� id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * D�finit la valeur de la propri�t� id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
