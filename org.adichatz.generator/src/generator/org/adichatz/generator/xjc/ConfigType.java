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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour configType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="configType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;sequence>
 *         &lt;element name="resourceBundles" type="{}resourceBundlesType"/>
 *         &lt;element name="customizations" type="{}customizationsType"/>
 *         &lt;element name="navigationPaths" type="{}navigationPathsType"/>
 *         &lt;element name="params" type="{}paramsType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getConfigType", name = "configType", propOrder = {
    "resourceBundles",
    "customizations",
    "navigationPaths",
    "params"
})
public class ConfigType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ResourceBundlesType resourceBundles;
    @XmlElement(required = true)
    protected CustomizationsType customizations;
    @XmlElement(required = true)
    protected NavigationPathsType navigationPaths;
    @XmlElement(required = true)
    protected ParamsType params;

    /**
     * Obtient la valeur de la propri�t� resourceBundles.
     * 
     * @return
     *     possible object is
     *     {@link ResourceBundlesType }
     *     
     */
    public ResourceBundlesType getResourceBundles() {
        return resourceBundles;
    }

    /**
     * D�finit la valeur de la propri�t� resourceBundles.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceBundlesType }
     *     
     */
    public void setResourceBundles(ResourceBundlesType value) {
        this.resourceBundles = value;
    }

    /**
     * Obtient la valeur de la propri�t� customizations.
     * 
     * @return
     *     possible object is
     *     {@link CustomizationsType }
     *     
     */
    public CustomizationsType getCustomizations() {
        return customizations;
    }

    /**
     * D�finit la valeur de la propri�t� customizations.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomizationsType }
     *     
     */
    public void setCustomizations(CustomizationsType value) {
        this.customizations = value;
    }

    /**
     * Obtient la valeur de la propri�t� navigationPaths.
     * 
     * @return
     *     possible object is
     *     {@link NavigationPathsType }
     *     
     */
    public NavigationPathsType getNavigationPaths() {
        return navigationPaths;
    }

    /**
     * D�finit la valeur de la propri�t� navigationPaths.
     * 
     * @param value
     *     allowed object is
     *     {@link NavigationPathsType }
     *     
     */
    public void setNavigationPaths(NavigationPathsType value) {
        this.navigationPaths = value;
    }

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

}
