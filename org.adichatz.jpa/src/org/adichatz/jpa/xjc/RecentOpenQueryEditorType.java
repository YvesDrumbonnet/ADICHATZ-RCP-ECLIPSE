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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour recentOpenQueryEditorType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propriété recentPreferences.
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
     * Définit la valeur de la propriété recentPreferences.
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
