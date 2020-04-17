//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:22 AM CET 
//


package org.adichatz.engine.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour rcpConfigurationType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="rcpConfigurationType">
 *   &lt;complexContent>
 *     &lt;extension base="{}paramsType">
 *       &lt;sequence>
 *         &lt;element name="navigators" type="{}navigatorsType"/>
 *         &lt;element name="tableRenderers" type="{}tableRenderersType"/>
 *         &lt;element name="tableStatusBars" type="{}tableStatusBarsType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rcpConfigurationType", propOrder = {
    "navigators",
    "tableRenderers",
    "tableStatusBars"
})
public class RcpConfigurationType
    extends ParamsType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected NavigatorsType navigators;
    @XmlElement(required = true)
    protected TableRenderersType tableRenderers;
    @XmlElement(required = true)
    protected TableStatusBarsType tableStatusBars;

    /**
     * Obtient la valeur de la propriété navigators.
     * 
     * @return
     *     possible object is
     *     {@link NavigatorsType }
     *     
     */
    public NavigatorsType getNavigators() {
        return navigators;
    }

    /**
     * Définit la valeur de la propriété navigators.
     * 
     * @param value
     *     allowed object is
     *     {@link NavigatorsType }
     *     
     */
    public void setNavigators(NavigatorsType value) {
        this.navigators = value;
    }

    /**
     * Obtient la valeur de la propriété tableRenderers.
     * 
     * @return
     *     possible object is
     *     {@link TableRenderersType }
     *     
     */
    public TableRenderersType getTableRenderers() {
        return tableRenderers;
    }

    /**
     * Définit la valeur de la propriété tableRenderers.
     * 
     * @param value
     *     allowed object is
     *     {@link TableRenderersType }
     *     
     */
    public void setTableRenderers(TableRenderersType value) {
        this.tableRenderers = value;
    }

    /**
     * Obtient la valeur de la propriété tableStatusBars.
     * 
     * @return
     *     possible object is
     *     {@link TableStatusBarsType }
     *     
     */
    public TableStatusBarsType getTableStatusBars() {
        return tableStatusBars;
    }

    /**
     * Définit la valeur de la propriété tableStatusBars.
     * 
     * @param value
     *     allowed object is
     *     {@link TableStatusBarsType }
     *     
     */
    public void setTableStatusBars(TableStatusBarsType value) {
        this.tableStatusBars = value;
    }

}
