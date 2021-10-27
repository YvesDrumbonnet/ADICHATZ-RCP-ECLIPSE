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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour layoutType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="layoutType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}basicType"&amp;gt;
 *       &amp;lt;attribute name="layoutConstraints" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="columnConstraints" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="rowConstraints" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "layoutType")
public class LayoutType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "layoutConstraints")
    protected String layoutConstraints;
    @XmlAttribute(name = "columnConstraints")
    protected String columnConstraints;
    @XmlAttribute(name = "rowConstraints")
    protected String rowConstraints;

    /**
     * Obtient la valeur de la propriété layoutConstraints.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLayoutConstraints() {
        return layoutConstraints;
    }

    /**
     * Définit la valeur de la propriété layoutConstraints.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLayoutConstraints(String value) {
        this.layoutConstraints = value;
    }

    /**
     * Obtient la valeur de la propriété columnConstraints.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnConstraints() {
        return columnConstraints;
    }

    /**
     * Définit la valeur de la propriété columnConstraints.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnConstraints(String value) {
        this.columnConstraints = value;
    }

    /**
     * Obtient la valeur de la propriété rowConstraints.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRowConstraints() {
        return rowConstraints;
    }

    /**
     * Définit la valeur de la propriété rowConstraints.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRowConstraints(String value) {
        this.rowConstraints = value;
    }

}
