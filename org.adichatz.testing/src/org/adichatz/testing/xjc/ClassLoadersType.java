//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.10.15 à 11:55:00 AM CEST 
//


package org.adichatz.testing.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour classLoadersType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="classLoadersType">
 *   &lt;complexContent>
 *     &lt;extension base="{}testNodeType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="adiPluginName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "classLoadersType", propOrder = {
    "adiPluginName"
})
public class ClassLoadersType
    extends TestNodeType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<String> adiPluginName;

    /**
     * Gets the value of the adiPluginName property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adiPluginName property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdiPluginName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAdiPluginName() {
        if (adiPluginName == null) {
            adiPluginName = new ArrayList<String>();
        }
        return this.adiPluginName;
    }

}
