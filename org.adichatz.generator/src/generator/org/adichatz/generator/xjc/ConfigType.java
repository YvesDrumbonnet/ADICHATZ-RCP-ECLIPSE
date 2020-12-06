//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
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
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propriété resourceBundles.
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
     * Définit la valeur de la propriété resourceBundles.
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
     * Obtient la valeur de la propriété customizations.
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
     * Définit la valeur de la propriété customizations.
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
     * Obtient la valeur de la propriété navigationPaths.
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
     * Définit la valeur de la propriété navigationPaths.
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

}
