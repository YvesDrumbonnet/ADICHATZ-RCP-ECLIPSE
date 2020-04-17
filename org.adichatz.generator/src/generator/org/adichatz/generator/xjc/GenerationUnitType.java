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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour generationUnitType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="generationUnitType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;sequence>
 *         &lt;element name="generators" type="{}generatorsType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="scenarioClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="adiResourceURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{}generationEnum" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getGenerationUnitType", name = "generationUnitType", propOrder = {
    "generators"
})
@XmlSeeAlso({
    CustomGenerationUnitType.class
})
public class GenerationUnitType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected GeneratorsType generators;
    @XmlAttribute(name = "scenarioClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String scenarioClassName;
    @XmlAttribute(name = "adiResourceURI")
    protected String adiResourceURI;
    @XmlAttribute(name = "type", required = true)
    protected GenerationEnum type;

    /**
     * Obtient la valeur de la propri�t� generators.
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
     * D�finit la valeur de la propri�t� generators.
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
     * Obtient la valeur de la propri�t� scenarioClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScenarioClassName() {
        return scenarioClassName;
    }

    /**
     * D�finit la valeur de la propri�t� scenarioClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScenarioClassName(String value) {
        this.scenarioClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� adiResourceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdiResourceURI() {
        return adiResourceURI;
    }

    /**
     * D�finit la valeur de la propri�t� adiResourceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdiResourceURI(String value) {
        this.adiResourceURI = value;
    }

    /**
     * Obtient la valeur de la propri�t� type.
     * 
     * @return
     *     possible object is
     *     {@link GenerationEnum }
     *     
     */
    public GenerationEnum getType() {
        return type;
    }

    /**
     * D�finit la valeur de la propri�t� type.
     * 
     * @param value
     *     allowed object is
     *     {@link GenerationEnum }
     *     
     */
    public void setType(GenerationEnum value) {
        this.type = value;
    }

}
