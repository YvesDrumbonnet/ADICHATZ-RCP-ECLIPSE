//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
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
 * &lt;p&gt;Classe Java pour jointureType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="jointureType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}queryPartType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="jointure" type="{}jointureType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="fieldName" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="jointureType" use="required" type="{}jointureTypeEnum" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the jointure property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getJointure().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
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
