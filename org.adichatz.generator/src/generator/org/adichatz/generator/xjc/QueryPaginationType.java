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
 * <p>Classe Java pour queryPaginationType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� firstResult.
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
     * D�finit la valeur de la propri�t� firstResult.
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
     * Obtient la valeur de la propri�t� maxResults.
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
     * D�finit la valeur de la propri�t� maxResults.
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
