//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour generatorType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="generatorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="treeClassName" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="generatorClassName" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "generatorType")
public class GeneratorType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "treeClassName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String treeClassName;
    @XmlAttribute(name = "generatorClassName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String generatorClassName;

    /**
     * Obtient la valeur de la propriété treeClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreeClassName() {
        return treeClassName;
    }

    /**
     * Définit la valeur de la propriété treeClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreeClassName(String value) {
        this.treeClassName = value;
    }

    /**
     * Obtient la valeur de la propriété generatorClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneratorClassName() {
        return generatorClassName;
    }

    /**
     * Définit la valeur de la propriété generatorClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneratorClassName(String value) {
        this.generatorClassName = value;
    }

}
