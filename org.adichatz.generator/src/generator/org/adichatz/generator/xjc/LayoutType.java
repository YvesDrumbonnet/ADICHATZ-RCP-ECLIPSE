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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour layoutType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� layoutConstraints.
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
     * D�finit la valeur de la propri�t� layoutConstraints.
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
     * Obtient la valeur de la propri�t� columnConstraints.
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
     * D�finit la valeur de la propri�t� columnConstraints.
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
     * Obtient la valeur de la propri�t� rowConstraints.
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
     * D�finit la valeur de la propri�t� rowConstraints.
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
