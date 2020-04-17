//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:22 AM CET 
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
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� navigators.
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
     * D�finit la valeur de la propri�t� navigators.
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
     * Obtient la valeur de la propri�t� tableRenderers.
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
     * D�finit la valeur de la propri�t� tableRenderers.
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
     * Obtient la valeur de la propri�t� tableStatusBars.
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
     * D�finit la valeur de la propri�t� tableStatusBars.
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
