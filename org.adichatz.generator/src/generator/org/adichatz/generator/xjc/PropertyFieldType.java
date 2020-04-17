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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour propertyFieldType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� controlField.
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
     * D�finit la valeur de la propri�t� controlField.
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
     * Obtient la valeur de la propri�t� columnField.
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
     * D�finit la valeur de la propri�t� columnField.
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
     * Obtient la valeur de la propri�t� mandatory.
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
     * D�finit la valeur de la propri�t� mandatory.
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
     * Obtient la valeur de la propri�t� pojoType.
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
     * D�finit la valeur de la propri�t� pojoType.
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
