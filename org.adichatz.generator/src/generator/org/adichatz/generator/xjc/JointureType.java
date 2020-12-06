//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour jointureType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="jointureType">
 *   &lt;complexContent>
 *     &lt;extension base="{}queryPartType">
 *       &lt;choice>
 *         &lt;element name="jointure" type="{}jointureType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="fieldName" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="jointureType" use="required" type="{}jointureTypeEnum" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getJointureType", name = "jointureType", propOrder = {
    "jointure"
})
public class JointureType
    extends QueryPartType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<JointureType> jointure;
    @XmlAttribute(name = "fieldName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String fieldName;
    @XmlAttribute(name = "jointureType", required = true)
    protected JointureTypeEnum jointureType;

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
     * Obtient la valeur de la propriété fieldName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Définit la valeur de la propriété fieldName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Obtient la valeur de la propriété jointureType.
     * 
     * @return
     *     possible object is
     *     {@link JointureTypeEnum }
     *     
     */
    public JointureTypeEnum getJointureType() {
        return jointureType;
    }

    /**
     * Définit la valeur de la propriété jointureType.
     * 
     * @param value
     *     allowed object is
     *     {@link JointureTypeEnum }
     *     
     */
    public void setJointureType(JointureTypeEnum value) {
        this.jointureType = value;
    }

}
