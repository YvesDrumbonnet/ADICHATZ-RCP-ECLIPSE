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
 * &lt;p&gt;Classe Java pour sashFormType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="sashFormType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}validableContainerType"&amp;gt;
 *       &amp;lt;attribute name="maximizedChild" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="weights" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getSashFormType", name = "sashFormType")
public class SashFormType
    extends ValidableContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "maximizedChild")
    protected String maximizedChild;
    @XmlAttribute(name = "orientation")
    protected String orientation;
    @XmlAttribute(name = "weights")
    protected String weights;

    /**
     * Obtient la valeur de la propriété maximizedChild.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaximizedChild() {
        return maximizedChild;
    }

    /**
     * Définit la valeur de la propriété maximizedChild.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaximizedChild(String value) {
        this.maximizedChild = value;
    }

    /**
     * Obtient la valeur de la propriété orientation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     * Définit la valeur de la propriété orientation.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrientation(String value) {
        this.orientation = value;
    }

    /**
     * Obtient la valeur de la propriété weights.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeights() {
        return weights;
    }

    /**
     * Définit la valeur de la propriété weights.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeights(String value) {
        this.weights = value;
    }

}
