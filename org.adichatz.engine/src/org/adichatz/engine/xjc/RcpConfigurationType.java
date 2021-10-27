//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:33 PM CEST 
//


package org.adichatz.engine.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour rcpConfigurationType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="rcpConfigurationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}paramsType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="navigators" type="{}navigatorsType"/&amp;gt;
 *         &amp;lt;element name="tableRenderers" type="{}tableRenderersType"/&amp;gt;
 *         &amp;lt;element name="tableStatusBars" type="{}tableStatusBarsType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
