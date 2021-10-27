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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour generationScenarioType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="generationScenarioType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="pluginEntity" type="{}pluginEntityType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="modelPart" type="{}modelPartType"/&amp;gt;
 *         &amp;lt;element name="rcpPart" type="{}rcpPartType"/&amp;gt;
 *         &amp;lt;element name="generationUnit" type="{}generationUnitType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="propertyField" type="{}propertyFieldType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getGenerationScenarioType", name = "generationScenarioType", propOrder = {
    "pluginEntity",
    "modelPart",
    "rcpPart",
    "generationUnit",
    "propertyField"
})
public class GenerationScenarioType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected List<PluginEntityType> pluginEntity;
    @XmlElement(required = true)
    protected ModelPartType modelPart;
    @XmlElement(required = true)
    protected RcpPartType rcpPart;
    protected List<GenerationUnitType> generationUnit;
    protected List<PropertyFieldType> propertyField;

    /**
     * Gets the value of the pluginEntity property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the pluginEntity property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPluginEntity().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PluginEntityType }
     * 
     * 
     */
    public List<PluginEntityType> getPluginEntity() {
        if (pluginEntity == null) {
            pluginEntity = new ArrayList<PluginEntityType>();
        }
        return this.pluginEntity;
    }

    /**
     * Obtient la valeur de la propriété modelPart.
     * 
     * @return
     *     possible object is
     *     {@link ModelPartType }
     *     
     */
    public ModelPartType getModelPart() {
        return modelPart;
    }

    /**
     * Définit la valeur de la propriété modelPart.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelPartType }
     *     
     */
    public void setModelPart(ModelPartType value) {
        this.modelPart = value;
    }

    /**
     * Obtient la valeur de la propriété rcpPart.
     * 
     * @return
     *     possible object is
     *     {@link RcpPartType }
     *     
     */
    public RcpPartType getRcpPart() {
        return rcpPart;
    }

    /**
     * Définit la valeur de la propriété rcpPart.
     * 
     * @param value
     *     allowed object is
     *     {@link RcpPartType }
     *     
     */
    public void setRcpPart(RcpPartType value) {
        this.rcpPart = value;
    }

    /**
     * Gets the value of the generationUnit property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the generationUnit property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getGenerationUnit().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link GenerationUnitType }
     * 
     * 
     */
    public List<GenerationUnitType> getGenerationUnit() {
        if (generationUnit == null) {
            generationUnit = new ArrayList<GenerationUnitType>();
        }
        return this.generationUnit;
    }

    /**
     * Gets the value of the propertyField property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the propertyField property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPropertyField().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyFieldType }
     * 
     * 
     */
    public List<PropertyFieldType> getPropertyField() {
        if (propertyField == null) {
            propertyField = new ArrayList<PropertyFieldType>();
        }
        return this.propertyField;
    }

}
