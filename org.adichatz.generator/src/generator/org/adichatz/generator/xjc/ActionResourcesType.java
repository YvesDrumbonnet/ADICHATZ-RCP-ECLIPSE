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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour actionResourcesType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="actionResourcesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="copyResource" type="{}copyResourceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="removeResource" type="{}removeResourceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actionResourcesType", propOrder = {
    "copyResource",
    "removeResource"
})
public class ActionResourcesType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<CopyResourceType> copyResource;
    protected List<RemoveResourceType> removeResource;

    /**
     * Gets the value of the copyResource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the copyResource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCopyResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CopyResourceType }
     * 
     * 
     */
    public List<CopyResourceType> getCopyResource() {
        if (copyResource == null) {
            copyResource = new ArrayList<CopyResourceType>();
        }
        return this.copyResource;
    }

    /**
     * Gets the value of the removeResource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the removeResource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemoveResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RemoveResourceType }
     * 
     * 
     */
    public List<RemoveResourceType> getRemoveResource() {
        if (removeResource == null) {
            removeResource = new ArrayList<RemoveResourceType>();
        }
        return this.removeResource;
    }

}
