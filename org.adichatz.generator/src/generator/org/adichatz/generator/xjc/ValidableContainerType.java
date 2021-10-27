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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour validableContainerType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="validableContainerType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}fieldContainerType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="validators" type="{}validatorsType"/&amp;gt;
 *         &amp;lt;element name="dynamicClause" type="{}dynamicClauseType"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validableContainerType", propOrder = {
    "validators",
    "dynamicClause"
})
@XmlSeeAlso({
    FormPageType.class,
    ScrolledFormType.class,
    CompositeType.class,
    SashFormType.class,
    DirtyContainerType.class
})
public class ValidableContainerType
    extends FieldContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ValidatorsType validators;
    protected DynamicClauseType dynamicClause;

    /**
     * Obtient la valeur de la propriété validators.
     * 
     * @return
     *     possible object is
     *     {@link ValidatorsType }
     *     
     */
    public ValidatorsType getValidators() {
        return validators;
    }

    /**
     * Définit la valeur de la propriété validators.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidatorsType }
     *     
     */
    public void setValidators(ValidatorsType value) {
        this.validators = value;
    }

    /**
     * Obtient la valeur de la propriété dynamicClause.
     * 
     * @return
     *     possible object is
     *     {@link DynamicClauseType }
     *     
     */
    public DynamicClauseType getDynamicClause() {
        return dynamicClause;
    }

    /**
     * Définit la valeur de la propriété dynamicClause.
     * 
     * @param value
     *     allowed object is
     *     {@link DynamicClauseType }
     *     
     */
    public void setDynamicClause(DynamicClauseType value) {
        this.dynamicClause = value;
    }

}
