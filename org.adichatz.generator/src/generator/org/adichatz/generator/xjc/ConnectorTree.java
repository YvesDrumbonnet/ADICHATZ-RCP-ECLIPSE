//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
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
 *         &lt;element name="applicationServers" type="{}applicationServersType"/>
 *         &lt;element name="datasources" type="{}datasourcesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
