//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour copyResourceType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="copyResourceType">
 *   &lt;complexContent>
 *     &lt;extension base="{}removeResourceType">
 *       &lt;sequence>
 *         &lt;element name="replacement" type="{}replacementType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="sourceURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="force" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "copyResourceType", propOrder = {
    "replacement"
})
public class CopyResourceType
    extends RemoveResourceType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<ReplacementType> replacement;
    @XmlAttribute(name = "sourceURI", required = true)
    protected String sourceURI;
    @XmlAttribute(name = "force")
    protected Boolean force;

    /**
     * Gets the value of the replacement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the replacement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReplacement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReplacementType }
     * 
     * 
     */
    public List<ReplacementType> getReplacement() {
        if (replacement == null) {
            replacement = new ArrayList<ReplacementType>();
        }
        return this.replacement;
    }

    /**
     * Obtient la valeur de la propri�t� sourceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceURI() {
        return sourceURI;
    }

    /**
     * D�finit la valeur de la propri�t� sourceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceURI(String value) {
        this.sourceURI = value;
    }

    /**
     * Obtient la valeur de la propri�t� force.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isForce() {
        if (force == null) {
            return false;
        } else {
            return force;
        }
    }

    /**
     * D�finit la valeur de la propri�t� force.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setForce(Boolean value) {
        this.force = value;
    }

}
