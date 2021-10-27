//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour configType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="configType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}basicType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="resourceBundles" type="{}resourceBundlesType"/&amp;gt;
 *         &amp;lt;element name="customizations" type="{}customizationsType"/&amp;gt;
 *         &amp;lt;element name="navigationPaths" type="{}navigationPathsType"/&amp;gt;
 *         &amp;lt;element name="params" type="{}paramsType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
