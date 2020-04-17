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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour propertyFieldType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="propertyFieldType">
 *   &lt;complexContent>
 *     &lt;extension base="{}fieldType">
 *       &lt;sequence>
 *         &lt;element name="controlField" type="{}controlFieldType"/>
 *         &lt;element name="columnField" type="{}columnFieldType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="mandatory" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="pojoType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "propertyFieldType", propOrder = {
    "controlField",
    "columnField"
})
@XmlSeeAlso({
    EmbeddedFieldType.class,
    RefFieldType.class,
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
