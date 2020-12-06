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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour layoutType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="layoutType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;attribute name="layoutConstraints" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="columnConstraints" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="rowConstraints" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
