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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour modelPartType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="modelPartType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="params" type="{}paramsType"/>
 *         &lt;element name="pojoRewriters" type="{}pojoRewritersType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="connectorDataSource" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="connectorASVersion" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="modelPackageName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="modelProcurement" type="{}modelProcurementEnum" />
 *       &lt;attribute name="modelProcurementFolder" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dataSourceUnit" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="persistenceManagerClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="lockManagerClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="ejbFileName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="dataSourceFileName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="jndi" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getModelPartType", name = "modelPartType", propOrder = {
    "params",
    "pojoRewriters"
})
public class ModelPartType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ParamsType params;
    @XmlElement(required = true)
    protected PojoRewritersType pojoRewriters;
    @XmlAttribute(name = "connectorDataSource")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String connectorDataSource;
    @XmlAttribute(name = "connectorASVersion")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String connectorASVersion;
    @XmlAttribute(name = "modelPackageName", required = true)
    protected String modelPackageName;
    @XmlAttribute(name = "modelProcurement")
    protected ModelProcurementEnum modelProcurement;
    @XmlAttribute(name = "modelProcurementFolder")
    protected String modelProcurementFolder;
    @XmlAttribute(name = "dataSourceUnit")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String dataSourceUnit;
    @XmlAttribute(name = "persistenceManagerClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String persistenceManagerClassName;
    @XmlAttribute(name = "lockManagerClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String lockManagerClassName;
    @XmlAttribute(name = "ejbFileName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String ejbFileName;
    @XmlAttribute(name = "dataSourceFileName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String dataSourceFileName;
    @XmlAttribute(name = "jndi")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String jndi;

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
     * Obtient la valeur de la propri�t� pojoRewriters.
     * 
     * @return
     *     possible object is
     *     {@link PojoRewritersType }
     *     
     */
    public PojoRewritersType getPojoRewriters() {
        return pojoRewriters;
    }

    /**
     * D�finit la valeur de la propri�t� pojoRewriters.
     * 
     * @param value
     *     allowed object is
     *     {@link PojoRewritersType }
     *     
     */
    public void setPojoRewriters(PojoRewritersType value) {
        this.pojoRewriters = value;
    }

    /**
     * Obtient la valeur de la propri�t� connectorDataSource.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectorDataSource() {
        return connectorDataSource;
    }

    /**
     * D�finit la valeur de la propri�t� connectorDataSource.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectorDataSource(String value) {
        this.connectorDataSource = value;
    }

    /**
     * Obtient la valeur de la propri�t� connectorASVersion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectorASVersion() {
        return connectorASVersion;
    }

    /**
     * D�finit la valeur de la propri�t� connectorASVersion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectorASVersion(String value) {
        this.connectorASVersion = value;
    }

    /**
     * Obtient la valeur de la propri�t� modelPackageName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelPackageName() {
        return modelPackageName;
    }

    /**
     * D�finit la valeur de la propri�t� modelPackageName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelPackageName(String value) {
        this.modelPackageName = value;
    }

    /**
     * Obtient la valeur de la propri�t� modelProcurement.
     * 
     * @return
     *     possible object is
     *     {@link ModelProcurementEnum }
     *     
     */
    public ModelProcurementEnum getModelProcurement() {
        return modelProcurement;
    }

    /**
     * D�finit la valeur de la propri�t� modelProcurement.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelProcurementEnum }
     *     
     */
    public void setModelProcurement(ModelProcurementEnum value) {
        this.modelProcurement = value;
    }

    /**
     * Obtient la valeur de la propri�t� modelProcurementFolder.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelProcurementFolder() {
        return modelProcurementFolder;
    }

    /**
     * D�finit la valeur de la propri�t� modelProcurementFolder.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelProcurementFolder(String value) {
        this.modelProcurementFolder = value;
    }

    /**
     * Obtient la valeur de la propri�t� dataSourceUnit.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourceUnit() {
        return dataSourceUnit;
    }

    /**
     * D�finit la valeur de la propri�t� dataSourceUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourceUnit(String value) {
        this.dataSourceUnit = value;
    }

    /**
     * Obtient la valeur de la propri�t� persistenceManagerClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersistenceManagerClassName() {
        return persistenceManagerClassName;
    }

    /**
     * D�finit la valeur de la propri�t� persistenceManagerClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersistenceManagerClassName(String value) {
        this.persistenceManagerClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� lockManagerClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockManagerClassName() {
        return lockManagerClassName;
    }

    /**
     * D�finit la valeur de la propri�t� lockManagerClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockManagerClassName(String value) {
        this.lockManagerClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� ejbFileName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEjbFileName() {
        return ejbFileName;
    }

    /**
     * D�finit la valeur de la propri�t� ejbFileName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEjbFileName(String value) {
        this.ejbFileName = value;
    }

    /**
     * Obtient la valeur de la propri�t� dataSourceFileName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourceFileName() {
        return dataSourceFileName;
    }

    /**
     * D�finit la valeur de la propri�t� dataSourceFileName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourceFileName(String value) {
        this.dataSourceFileName = value;
    }

    /**
     * Obtient la valeur de la propri�t� jndi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndi() {
        return jndi;
    }

    /**
     * D�finit la valeur de la propri�t� jndi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndi(String value) {
        this.jndi = value;
    }

}
