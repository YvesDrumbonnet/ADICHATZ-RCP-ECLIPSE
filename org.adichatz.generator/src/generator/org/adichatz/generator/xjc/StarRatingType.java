//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour starRatingType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="starRatingType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;attribute name="maxNumberOfStars" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="numericPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Obtient la valeur de la propri�t� maxNumberOfStars.
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
     * D�finit la valeur de la propri�t� maxNumberOfStars.
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
     * Obtient la valeur de la propri�t� numericPattern.
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
     * D�finit la valeur de la propri�t� numericPattern.
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
