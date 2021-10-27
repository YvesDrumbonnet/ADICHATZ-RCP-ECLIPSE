//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour customGenerationUnitType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="customGenerationUnitType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}generationUnitType"&amp;gt;
 *       &amp;lt;attribute name="entityRegex" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="propertyRegex" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
