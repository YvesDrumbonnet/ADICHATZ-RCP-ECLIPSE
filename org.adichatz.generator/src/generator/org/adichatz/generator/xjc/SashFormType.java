//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour sashFormType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="sashFormType">
 *   &lt;complexContent>
 *     &lt;extension base="{}validableContainerType">
 *       &lt;attribute name="maximizedChild" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orientation" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="weights" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Obtient la valeur de la propri�t� maximizedChild.
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
     * D�finit la valeur de la propri�t� maximizedChild.
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
     * Obtient la valeur de la propri�t� orientation.
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
     * D�finit la valeur de la propri�t� orientation.
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
     * Obtient la valeur de la propri�t� weights.
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
     * D�finit la valeur de la propri�t� weights.
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
