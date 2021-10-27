//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:33 PM CEST 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour recentOpenQueryEditorType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="recentOpenQueryEditorType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}recentOpenEditorType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="recentPreferences" type="{}recentPreferencesType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
