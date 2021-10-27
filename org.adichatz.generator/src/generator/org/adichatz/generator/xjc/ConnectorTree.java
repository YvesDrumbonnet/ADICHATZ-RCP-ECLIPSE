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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour anonymous complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="applicationServers" type="{}applicationServersType"/&amp;gt;
 *         &amp;lt;element name="datasources" type="{}datasourcesType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getConnectorTree", name = "", propOrder = {
    "applicationServers",
    "datasources"
})
@XmlRootElement(name = "connectorTree")
public class ConnectorTree
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ApplicationServersType applicationServers;
    @XmlElement(required = true)
    protected DatasourcesType datasources;

    /**
     * Obtient la valeur de la propriété applicationServers.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationServersType }
     *     
     */
    public ApplicationServersType getApplicationServers() {
        return applicationServers;
    }

    /**
     * Définit la valeur de la propriété applicationServers.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationServersType }
     *     
     */
    public void setApplicationServers(ApplicationServersType value) {
        this.applicationServers = value;
    }

    /**
     * Obtient la valeur de la propriété datasources.
     * 
     * @return
     *     possible object is
     *     {@link DatasourcesType }
     *     
     */
    public DatasourcesType getDatasources() {
        return datasources;
    }

    /**
     * Définit la valeur de la propriété datasources.
     * 
     * @param value
     *     allowed object is
     *     {@link DatasourcesType }
     *     
     */
    public void setDatasources(DatasourcesType value) {
        this.datasources = value;
    }

}
