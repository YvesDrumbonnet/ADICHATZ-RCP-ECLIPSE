//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:33 PM CEST 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour anonymous complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence minOccurs="0"&amp;gt;
 *         &amp;lt;choice&amp;gt;
 *           &amp;lt;element name="queryPreference" type="{}queryPreferenceType"/&amp;gt;
 *           &amp;lt;element name="controllerPreference" type="{}controllerPreferenceType"/&amp;gt;
 *         &amp;lt;/choice&amp;gt;
 *         &amp;lt;element name="recentOpenEditorId" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "queryPreference",
    "controllerPreference",
    "recentOpenEditorId"
})
@XmlRootElement(name = "preferenceTree")
public class PreferenceTree
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected QueryPreferenceType queryPreference;
    protected ControllerPreferenceType controllerPreference;
    protected List<String> recentOpenEditorId;
    @XmlAttribute(name = "title")
    protected String title;

    /**
     * Obtient la valeur de la propriété queryPreference.
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
     * Définit la valeur de la propriété queryPreference.
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
     * Obtient la valeur de la propriété controllerPreference.
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
     * Définit la valeur de la propriété controllerPreference.
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
     * Gets the value of the recentOpenEditorId property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the recentOpenEditorId property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRecentOpenEditorId().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRecentOpenEditorId() {
        if (recentOpenEditorId == null) {
            recentOpenEditorId = new ArrayList<String>();
        }
        return this.recentOpenEditorId;
    }

    /**
     * Obtient la valeur de la propriété title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit la valeur de la propriété title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
