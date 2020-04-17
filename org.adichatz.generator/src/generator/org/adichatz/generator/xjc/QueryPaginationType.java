//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour queryPaginationType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="queryPaginationType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;attribute name="firstResult" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="maxResults" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryPaginationType")
public class QueryPaginationType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "firstResult")
    protected Integer firstResult;
    @XmlAttribute(name = "maxResults")
    protected Integer maxResults;

    /**
     * Obtient la valeur de la propriété firstResult.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFirstResult() {
        return firstResult;
    }

    /**
     * Définit la valeur de la propriété firstResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFirstResult(Integer value) {
        this.firstResult = value;
    }

    /**
     * Obtient la valeur de la propriété maxResults.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * Définit la valeur de la propriété maxResults.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxResults(Integer value) {
        this.maxResults = value;
    }

}
