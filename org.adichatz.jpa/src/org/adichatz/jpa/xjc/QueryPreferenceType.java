//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:23 AM CET 
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
 * <p>Classe Java pour queryPreferenceType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="queryPreferenceType">
 *   &lt;complexContent>
 *     &lt;extension base="{}paramType">
 *       &lt;sequence>
 *         &lt;element name="pagination" type="{}queryPaginationType"/>
 *         &lt;element name="parameter" type="{}queryParameterType" maxOccurs="unbounded"/>
 *         &lt;element name="openClause" type="{}queryOpenClauseType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="fullTextClause" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orderByClause" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the openClause property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOpenClause().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
