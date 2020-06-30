//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour validableContainerType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="validableContainerType">
 *   &lt;complexContent>
 *     &lt;extension base="{}fieldContainerType">
 *       &lt;choice>
 *         &lt;element name="validators" type="{}validatorsType"/>
 *         &lt;element name="dynamicClause" type="{}dynamicClauseType"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Obtient la valeur de la propri�t� validators.
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
     * D�finit la valeur de la propri�t� validators.
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
     * Obtient la valeur de la propri�t� dynamicClause.
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
     * D�finit la valeur de la propri�t� dynamicClause.
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
