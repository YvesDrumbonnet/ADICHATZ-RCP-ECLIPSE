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
 * &lt;p&gt;Classe Java pour starRatingType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="starRatingType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}controlFieldType"&amp;gt;
 *       &amp;lt;attribute name="maxNumberOfStars" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="numericPattern" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getStarRatingType", name = "starRatingType")
public class StarRatingType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "maxNumberOfStars")
    protected String maxNumberOfStars;
    @XmlAttribute(name = "numericPattern")
    protected String numericPattern;

    /**
     * Obtient la valeur de la propriété maxNumberOfStars.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxNumberOfStars() {
        return maxNumberOfStars;
    }

    /**
     * Définit la valeur de la propriété maxNumberOfStars.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxNumberOfStars(String value) {
        this.maxNumberOfStars = value;
    }

    /**
     * Obtient la valeur de la propriété numericPattern.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumericPattern() {
        return numericPattern;
    }

    /**
     * Définit la valeur de la propriété numericPattern.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumericPattern(String value) {
        this.numericPattern = value;
    }

}
