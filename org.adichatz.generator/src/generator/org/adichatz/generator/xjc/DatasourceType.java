//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour datasourceType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="datasourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="params" type="{}paramsType"/>
 *         &lt;element name="buildParams" type="{}paramsType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="datasourceName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="driver" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="customizationFile" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getDatasourceType", name = "datasourceType", propOrder = {
    "params",
    "buildParams"
})
public class DatasourceType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ParamsType params;
    @XmlElement(required = true)
    protected ParamsType buildParams;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "datasourceName", required = true)
    protected String datasourceName;
    @XmlAttribute(name = "driver", required = true)
    protected String driver;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "customizationFile")
    protected String customizationFile;

    /**
     * Obtient la valeur de la propri�t� params.
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
     * D�finit la valeur de la propri�t� params.
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
     * Obtient la valeur de la propri�t� buildParams.
     * 
     * @return
     *     possible object is
     *     {@link ParamsType }
     *     
     */
    public ParamsType getBuildParams() {
        return buildParams;
    }

    /**
     * D�finit la valeur de la propri�t� buildParams.
     * 
     * @param value
     *     allowed object is
     *     {@link ParamsType }
     *     
     */
    public void setBuildParams(ParamsType value) {
        this.buildParams = value;
    }

    /**
     * Obtient la valeur de la propri�t� id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * D�finit la valeur de la propri�t� id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propri�t� datasourceName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasourceName() {
        return datasourceName;
    }

    /**
     * D�finit la valeur de la propri�t� datasourceName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasourceName(String value) {
        this.datasourceName = value;
    }

    /**
     * Obtient la valeur de la propri�t� driver.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDriver() {
        return driver;
    }

    /**
     * D�finit la valeur de la propri�t� driver.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDriver(String value) {
        this.driver = value;
    }

    /**
     * Obtient la valeur de la propri�t� description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * D�finit la valeur de la propri�t� description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtient la valeur de la propri�t� customizationFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomizationFile() {
        return customizationFile;
    }

    /**
     * D�finit la valeur de la propri�t� customizationFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomizationFile(String value) {
        this.customizationFile = value;
    }

}
