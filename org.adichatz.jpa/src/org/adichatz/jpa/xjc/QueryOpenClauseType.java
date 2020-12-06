//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:22 PM CEST 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour queryOpenClauseType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="queryOpenClauseType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;choice>
 *         &lt;element name="parameter" type="{}queryParameterType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="clause" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="permanent" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="visible" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getQueryOpenClauseType", name = "queryOpenClauseType", propOrder = {
    "parameter"
})
public class QueryOpenClauseType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<QueryParameterType> parameter;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "clause")
    protected String clause;
    @XmlAttribute(name = "valid")
    protected Boolean valid;
    @XmlAttribute(name = "permanent")
    protected Boolean permanent;
    @XmlAttribute(name = "visible")
    protected Boolean visible;

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
     * Obtient la valeur de la propriété title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit la valeur de la propriété title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Obtient la valeur de la propriété clause.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClause() {
        return clause;
    }

    /**
     * Définit la valeur de la propriété clause.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClause(String value) {
        this.clause = value;
    }

    /**
     * Obtient la valeur de la propriété valid.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isValid() {
        if (valid == null) {
            return true;
        } else {
            return valid;
        }
    }

    /**
     * Définit la valeur de la propriété valid.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValid(Boolean value) {
        this.valid = value;
    }

    /**
     * Obtient la valeur de la propriété permanent.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPermanent() {
        if (permanent == null) {
            return false;
        } else {
            return permanent;
        }
    }

    /**
     * Définit la valeur de la propriété permanent.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPermanent(Boolean value) {
        this.permanent = value;
    }

    /**
     * Obtient la valeur de la propriété visible.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isVisible() {
        if (visible == null) {
            return true;
        } else {
            return visible;
        }
    }

    /**
     * Définit la valeur de la propriété visible.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVisible(Boolean value) {
        this.visible = value;
    }

}
