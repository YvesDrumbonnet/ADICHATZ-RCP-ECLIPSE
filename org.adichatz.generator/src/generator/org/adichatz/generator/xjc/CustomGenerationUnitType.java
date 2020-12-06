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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour customGenerationUnitType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="customGenerationUnitType">
 *   &lt;complexContent>
 *     &lt;extension base="{}generationUnitType">
 *       &lt;attribute name="entityRegex" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="propertyRegex" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customGenerationUnitType")
public class CustomGenerationUnitType
    extends GenerationUnitType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "entityRegex")
    protected String entityRegex;
    @XmlAttribute(name = "propertyRegex")
    protected String propertyRegex;

    /**
     * Obtient la valeur de la propriété entityRegex.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityRegex() {
        return entityRegex;
    }

    /**
     * Définit la valeur de la propriété entityRegex.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityRegex(String value) {
        this.entityRegex = value;
    }

    /**
     * Obtient la valeur de la propriété propertyRegex.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyRegex() {
        return propertyRegex;
    }

    /**
     * Définit la valeur de la propriété propertyRegex.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyRegex(String value) {
        this.propertyRegex = value;
    }

}
