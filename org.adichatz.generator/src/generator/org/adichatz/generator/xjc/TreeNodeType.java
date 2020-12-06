//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour treeNodeType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="treeNodeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="propertyId" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="nodeType" use="required" type="{}nodeTypeEnum" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "treeNodeType")
public class TreeNodeType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "propertyId", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String propertyId;
    @XmlAttribute(name = "nodeType", required = true)
    protected NodeTypeEnum nodeType;

    /**
     * Obtient la valeur de la propriété propertyId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
     * Définit la valeur de la propriété propertyId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyId(String value) {
        this.propertyId = value;
    }

    /**
     * Obtient la valeur de la propriété nodeType.
     * 
     * @return
     *     possible object is
     *     {@link NodeTypeEnum }
     *     
     */
    public NodeTypeEnum getNodeType() {
        return nodeType;
    }

    /**
     * Définit la valeur de la propriété nodeType.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeTypeEnum }
     *     
     */
    public void setNodeType(NodeTypeEnum value) {
        this.nodeType = value;
    }

}
