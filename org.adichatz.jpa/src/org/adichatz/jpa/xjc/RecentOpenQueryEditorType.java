//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:23 AM CET 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour recentOpenQueryEditorType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="recentOpenQueryEditorType">
 *   &lt;complexContent>
 *     &lt;extension base="{}recentOpenEditorType">
 *       &lt;sequence>
 *         &lt;element name="recentPreferences" type="{}recentPreferencesType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getRecentOpenQueryEditorType", name = "recentOpenQueryEditorType", propOrder = {
    "recentPreferences"
})
public class RecentOpenQueryEditorType
    extends RecentOpenEditorType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected RecentPreferencesType recentPreferences;

    /**
     * Obtient la valeur de la propri�t� recentPreferences.
     * 
     * @return
     *     possible object is
     *     {@link RecentPreferencesType }
     *     
     */
    public RecentPreferencesType getRecentPreferences() {
        return recentPreferences;
    }

    /**
     * D�finit la valeur de la propri�t� recentPreferences.
     * 
     * @param value
     *     allowed object is
     *     {@link RecentPreferencesType }
     *     
     */
    public void setRecentPreferences(RecentPreferencesType value) {
        this.recentPreferences = value;
    }

}
