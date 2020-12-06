//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour entityElementsType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="entityElementsType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="entityElement" type="{}entityElementType" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entityElementsType", propOrder = {
    "entityElement"
})
public class EntityElementsType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<EntityElementType> entityElement;

    /**
     * Gets the value of the entityElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entityElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntityElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntityElementType }
     * 
     * 
     */
    public List<EntityElementType> getEntityElement() {
        if (entityElement == null) {
            entityElement = new ArrayList<EntityElementType>();
        }
        return this.entityElement;
    }

}
