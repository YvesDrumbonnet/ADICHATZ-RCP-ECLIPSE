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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour propertyFieldType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="propertyFieldType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}fieldType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="controlField" type="{}controlFieldType"/&amp;gt;
 *         &amp;lt;element name="columnField" type="{}columnFieldType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="mandatory" type="{http://www.w3.org/2001/XMLSchema}boolean" /&amp;gt;
 *       &amp;lt;attribute name="pojoType" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "propertyFieldType", propOrder = {
    "controlField",
    "columnField"
})
@XmlSeeAlso({
    RefFieldType.class,
    EmbeddedFieldType.class,
    EmbeddedIdFieldType.class
})
public class PropertyFieldType
    extends FieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ControlFieldType controlField;
    @XmlElement(required = true)
    protected ColumnFieldType columnField;
    @XmlAttribute(name = "mandatory")
    protected Boolean mandatory;
    @XmlAttribute(name = "pojoType")
    protected String pojoType;

    /**
     * Obtient la valeur de la propriété controlField.
     * 
     * @return
     *     possible object is
     *     {@link ControlFieldType }
     *     
     */
    public ControlFieldType getControlField() {
        return controlField;
    }

    /**
     * Définit la valeur de la propriété controlField.
     * 
     * @param value
     *     allowed object is
     *     {@link ControlFieldType }
     *     
     */
    public void setControlField(ControlFieldType value) {
        this.controlField = value;
    }

    /**
     * Obtient la valeur de la propriété columnField.
     * 
     * @return
     *     possible object is
     *     {@link ColumnFieldType }
     *     
     */
    public ColumnFieldType getColumnField() {
        return columnField;
    }

    /**
     * Définit la valeur de la propriété columnField.
     * 
     * @param value
     *     allowed object is
     *     {@link ColumnFieldType }
     *     
     */
    public void setColumnField(ColumnFieldType value) {
        this.columnField = value;
    }

    /**
     * Obtient la valeur de la propriété mandatory.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMandatory() {
        return mandatory;
    }

    /**
     * Définit la valeur de la propriété mandatory.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMandatory(Boolean value) {
        this.mandatory = value;
    }

    /**
     * Obtient la valeur de la propriété pojoType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPojoType() {
        return pojoType;
    }

    /**
     * Définit la valeur de la propriété pojoType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPojoType(String value) {
        this.pojoType = value;
    }

}
