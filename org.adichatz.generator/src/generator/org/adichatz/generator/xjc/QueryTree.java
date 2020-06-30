//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{}queryPartType">
 *       &lt;choice>
 *         &lt;element name="jointure" type="{}jointureType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="whereClause" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sqlClause" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="jointureAliases" type="{}jointureAliasesType"/>
 *         &lt;element name="queryPreference" type="{}queryPreferenceType"/>
 *         &lt;element name="customizedPreferences" type="{}customizedPreferenceType"/>
 *         &lt;element name="queryBuilder" type="{}queryBuilderType"/>
 *       &lt;/choice>
 *       &lt;attribute name="parentQueryManagerURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lazyFetches" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="queryType" type="{}queryTypeEnum" />
 *       &lt;attribute name="valid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getQueryTree", name = "", propOrder = {
    "jointure",
    "whereClause",
    "sqlClause",
    "jointureAliases",
    "queryPreference",
    "customizedPreferences",
    "queryBuilder"
})
@XmlRootElement(name = "queryTree")
public class QueryTree
    extends QueryPartType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<JointureType> jointure;
    protected String whereClause;
    protected String sqlClause;
    protected JointureAliasesType jointureAliases;
    protected QueryPreferenceType queryPreference;
    protected CustomizedPreferenceType customizedPreferences;
    protected QueryBuilderType queryBuilder;
    @XmlAttribute(name = "parentQueryManagerURI")
    protected String parentQueryManagerURI;
    @XmlAttribute(name = "lazyFetches")
    protected String lazyFetches;
    @XmlAttribute(name = "queryType")
    protected QueryTypeEnum queryType;
    @XmlAttribute(name = "valid")
    protected String valid;

    /**
     * Gets the value of the jointure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jointure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJointure().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JointureType }
     * 
     * 
     */
    public List<JointureType> getJointure() {
        if (jointure == null) {
            jointure = new ArrayList<JointureType>();
        }
        return this.jointure;
    }

    /**
     * Obtient la valeur de la propriété whereClause.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhereClause() {
        return whereClause;
    }

    /**
     * Définit la valeur de la propriété whereClause.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhereClause(String value) {
        this.whereClause = value;
    }

    /**
     * Obtient la valeur de la propriété sqlClause.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSqlClause() {
        return sqlClause;
    }

    /**
     * Définit la valeur de la propriété sqlClause.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSqlClause(String value) {
        this.sqlClause = value;
    }

    /**
     * Obtient la valeur de la propriété jointureAliases.
     * 
     * @return
     *     possible object is
     *     {@link JointureAliasesType }
     *     
     */
    public JointureAliasesType getJointureAliases() {
        return jointureAliases;
    }

    /**
     * Définit la valeur de la propriété jointureAliases.
     * 
     * @param value
     *     allowed object is
     *     {@link JointureAliasesType }
     *     
     */
    public void setJointureAliases(JointureAliasesType value) {
        this.jointureAliases = value;
    }

    /**
     * Obtient la valeur de la propriété queryPreference.
     * 
     * @return
     *     possible object is
     *     {@link QueryPreferenceType }
     *     
     */
    public QueryPreferenceType getQueryPreference() {
        return queryPreference;
    }

    /**
     * Définit la valeur de la propriété queryPreference.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryPreferenceType }
     *     
     */
    public void setQueryPreference(QueryPreferenceType value) {
        this.queryPreference = value;
    }

    /**
     * Obtient la valeur de la propriété customizedPreferences.
     * 
     * @return
     *     possible object is
     *     {@link CustomizedPreferenceType }
     *     
     */
    public CustomizedPreferenceType getCustomizedPreferences() {
        return customizedPreferences;
    }

    /**
     * Définit la valeur de la propriété customizedPreferences.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomizedPreferenceType }
     *     
     */
    public void setCustomizedPreferences(CustomizedPreferenceType value) {
        this.customizedPreferences = value;
    }

    /**
     * Obtient la valeur de la propriété queryBuilder.
     * 
     * @return
     *     possible object is
     *     {@link QueryBuilderType }
     *     
     */
    public QueryBuilderType getQueryBuilder() {
        return queryBuilder;
    }

    /**
     * Définit la valeur de la propriété queryBuilder.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryBuilderType }
     *     
     */
    public void setQueryBuilder(QueryBuilderType value) {
        this.queryBuilder = value;
    }

    /**
     * Obtient la valeur de la propriété parentQueryManagerURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentQueryManagerURI() {
        return parentQueryManagerURI;
    }

    /**
     * Définit la valeur de la propriété parentQueryManagerURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentQueryManagerURI(String value) {
        this.parentQueryManagerURI = value;
    }

    /**
     * Obtient la valeur de la propriété lazyFetches.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLazyFetches() {
        return lazyFetches;
    }

    /**
     * Définit la valeur de la propriété lazyFetches.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLazyFetches(String value) {
        this.lazyFetches = value;
    }

    /**
     * Obtient la valeur de la propriété queryType.
     * 
     * @return
     *     possible object is
     *     {@link QueryTypeEnum }
     *     
     */
    public QueryTypeEnum getQueryType() {
        return queryType;
    }

    /**
     * Définit la valeur de la propriété queryType.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryTypeEnum }
     *     
     */
    public void setQueryType(QueryTypeEnum value) {
        this.queryType = value;
    }

    /**
     * Obtient la valeur de la propriété valid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValid() {
        return valid;
    }

    /**
     * Définit la valeur de la propriété valid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValid(String value) {
        this.valid = value;
    }

}
