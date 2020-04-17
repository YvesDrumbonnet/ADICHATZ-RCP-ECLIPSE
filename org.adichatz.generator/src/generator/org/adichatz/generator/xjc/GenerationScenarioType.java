//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
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
 * <p>Classe Java pour generationScenarioType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="generationScenarioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pluginEntity" type="{}pluginEntityType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modelPart" type="{}modelPartType"/>
 *         &lt;element name="rcpPart" type="{}rcpPartType"/>
 *         &lt;element name="generationUnit" type="{}generationUnitType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="propertyField" type="{}propertyFieldType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pluginEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPluginEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the generationUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenerationUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
