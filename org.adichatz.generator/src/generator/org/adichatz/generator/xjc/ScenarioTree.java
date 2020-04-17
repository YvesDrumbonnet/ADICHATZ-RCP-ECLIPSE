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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="params" type="{}paramsType"/>
 *         &lt;element name="actionResources" type="{}actionResourcesType"/>
 *         &lt;element name="generators" type="{}generatorsType"/>
 *         &lt;element name="pathElements" type="{}pathElementsType"/>
 *         &lt;element name="scenarios" type="{}scenariosType"/>
 *         &lt;element name="controllers" type="{}controllersType"/>
 *         &lt;element name="generationScenario" type="{}generationScenarioType"/>
 *         &lt;element name="customizedScenarios" type="{}customizedScenariosType"/>
 *         &lt;element name="customPostAction" type="{}customPostActionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getScenarioTree", name = "", propOrder = {
    "params",
    "actionResources",
    "generators",
    "pathElements",
    "scenarios",
    "controllers",
    "generationScenario",
    "customizedScenarios",
    "customPostAction"
})
@XmlRootElement(name = "scenarioTree")
public class ScenarioTree
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ParamsType params;
    @XmlElement(required = true)
    protected ActionResourcesType actionResources;
    @XmlElement(required = true)
    protected GeneratorsType generators;
    @XmlElement(required = true)
    protected PathElementsType pathElements;
    @XmlElement(required = true)
    protected ScenariosType scenarios;
    @XmlElement(required = true)
    protected ControllersType controllers;
    @XmlElement(required = true)
    protected GenerationScenarioType generationScenario;
    @XmlElement(required = true)
    protected CustomizedScenariosType customizedScenarios;
    @XmlElement(required = true)
    protected CustomPostActionType customPostAction;

    /**
     * Obtient la valeur de la propriété params.
     * 
     * @return
     *     possible object is
     *     {@link ParamsType }
     *     
     */
    public ParamsType getParams() {
        return params;
    }

    /**
     * Définit la valeur de la propriété params.
     * 
     * @param value
     *     allowed object is
     *     {@link ParamsType }
     *     
     */
    public void setParams(ParamsType value) {
        this.params = value;
    }

    /**
     * Obtient la valeur de la propriété actionResources.
     * 
     * @return
     *     possible object is
     *     {@link ActionResourcesType }
     *     
     */
    public ActionResourcesType getActionResources() {
        return actionResources;
    }

    /**
     * Définit la valeur de la propriété actionResources.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionResourcesType }
     *     
     */
    public void setActionResources(ActionResourcesType value) {
        this.actionResources = value;
    }

    /**
     * Obtient la valeur de la propriété generators.
     * 
     * @return
     *     possible object is
     *     {@link GeneratorsType }
     *     
     */
    public GeneratorsType getGenerators() {
        return generators;
    }

    /**
     * Définit la valeur de la propriété generators.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneratorsType }
     *     
     */
    public void setGenerators(GeneratorsType value) {
        this.generators = value;
    }

    /**
     * Obtient la valeur de la propriété pathElements.
     * 
     * @return
     *     possible object is
     *     {@link PathElementsType }
     *     
     */
    public PathElementsType getPathElements() {
        return pathElements;
    }

    /**
     * Définit la valeur de la propriété pathElements.
     * 
     * @param value
     *     allowed object is
     *     {@link PathElementsType }
     *     
     */
    public void setPathElements(PathElementsType value) {
        this.pathElements = value;
    }

    /**
     * Obtient la valeur de la propriété scenarios.
     * 
     * @return
     *     possible object is
     *     {@link ScenariosType }
     *     
     */
    public ScenariosType getScenarios() {
        return scenarios;
    }

    /**
     * Définit la valeur de la propriété scenarios.
     * 
     * @param value
     *     allowed object is
     *     {@link ScenariosType }
     *     
     */
    public void setScenarios(ScenariosType value) {
        this.scenarios = value;
    }

    /**
     * Obtient la valeur de la propriété controllers.
     * 
     * @return
     *     possible object is
     *     {@link ControllersType }
     *     
     */
    public ControllersType getControllers() {
        return controllers;
    }

    /**
     * Définit la valeur de la propriété controllers.
     * 
     * @param value
     *     allowed object is
     *     {@link ControllersType }
     *     
     */
    public void setControllers(ControllersType value) {
        this.controllers = value;
    }

    /**
     * Obtient la valeur de la propriété generationScenario.
     * 
     * @return
     *     possible object is
     *     {@link GenerationScenarioType }
     *     
     */
    public GenerationScenarioType getGenerationScenario() {
        return generationScenario;
    }

    /**
     * Définit la valeur de la propriété generationScenario.
     * 
     * @param value
     *     allowed object is
     *     {@link GenerationScenarioType }
     *     
     */
    public void setGenerationScenario(GenerationScenarioType value) {
        this.generationScenario = value;
    }

    /**
     * Obtient la valeur de la propriété customizedScenarios.
     * 
     * @return
     *     possible object is
     *     {@link CustomizedScenariosType }
     *     
     */
    public CustomizedScenariosType getCustomizedScenarios() {
        return customizedScenarios;
    }

    /**
     * Définit la valeur de la propriété customizedScenarios.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomizedScenariosType }
     *     
     */
    public void setCustomizedScenarios(CustomizedScenariosType value) {
        this.customizedScenarios = value;
    }

    /**
     * Obtient la valeur de la propriété customPostAction.
     * 
     * @return
     *     possible object is
     *     {@link CustomPostActionType }
     *     
     */
    public CustomPostActionType getCustomPostAction() {
        return customPostAction;
    }

    /**
     * Définit la valeur de la propriété customPostAction.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomPostActionType }
     *     
     */
    public void setCustomPostAction(CustomPostActionType value) {
        this.customPostAction = value;
    }

}
