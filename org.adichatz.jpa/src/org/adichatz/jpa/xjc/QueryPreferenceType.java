//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:33 PM CEST 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour queryPreferenceType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="queryPreferenceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}paramType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="pagination" type="{}queryPaginationType"/&amp;gt;
 *         &amp;lt;element name="parameter" type="{}queryParameterType" maxOccurs="unbounded"/&amp;gt;
 *         &amp;lt;element name="openClause" type="{}queryOpenClauseType" maxOccurs="unbounded"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="fullTextClause" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="orderByClause" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getQueryPreferenceType", name = "queryPreferenceType", propOrder = {
    "pagination",
    "parameter",
    "openClause"
})
public class QueryPreferenceType
    extends ParamType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected QueryPaginationType pagination;
    @XmlElement(required = true)
    protected List<QueryParameterType> parameter;
    @XmlElement(required = true)
    protected List<QueryOpenClauseType> openClause;
    @XmlAttribute(name = "fullTextClause")
    protected String fullTextClause;
    @XmlAttribute(name = "orderByClause")
    protected String orderByClause;

    /**
     * Obtient la valeur de la propriété pagination.
     * 
     * @return
     *     possible object is
     *     {@link QueryPaginationType }
     *     
     */
    public QueryPaginationType getPagination() {
        return pagination;
    }

    /**
     * Définit la valeur de la propriété pagination.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryPaginationType }
     *     
     */
    public void setPagination(QueryPaginationType value) {
        this.pagination = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the parameter property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getParameter().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link QueryParameterType }
     * 
     * 
     */
    public List<QueryParameterType> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<QueryParameterType>();
        }
        return this.parameter;
    }

    /**
     * Gets the value of the openClause property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the openClause property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getOpenClause().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link QueryOpenClauseType }
     * 
     * 
     */
    public List<QueryOpenClauseType> getOpenClause() {
        if (openClause == null) {
            openClause = new ArrayList<QueryOpenClauseType>();
        }
        return this.openClause;
    }

    /**
     * Obtient la valeur de la propriété fullTextClause.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullTextClause() {
        return fullTextClause;
    }

    /**
     * Définit la valeur de la propriété fullTextClause.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullTextClause(String value) {
        this.fullTextClause = value;
    }

    /**
     * Obtient la valeur de la propriété orderByClause.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * Définit la valeur de la propriété orderByClause.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderByClause(String value) {
        this.orderByClause = value;
    }

}
