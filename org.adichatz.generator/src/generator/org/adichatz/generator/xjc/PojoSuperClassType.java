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
 * &lt;p&gt;Classe Java pour pojoSuperClassType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="pojoSuperClassType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="superClassURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="sourceURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pojoSuperClassType")
public class PojoSuperClassType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "superClassURI")
    protected String superClassURI;
    @XmlAttribute(name = "sourceURI")
    protected String sourceURI;

    /**
     * Obtient la valeur de la propriété superClassURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuperClassURI() {
        return superClassURI;
    }

    /**
     * Définit la valeur de la propriété superClassURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuperClassURI(String value) {
        this.superClassURI = value;
    }

    /**
     * Obtient la valeur de la propriété sourceURI.
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
     * Définit la valeur de la propriété sourceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceURI(String value) {
        this.sourceURI = value;
    }

}
