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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour resourceBundleType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="resourceBundleType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;attribute name="basename" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="var" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resourceBundleType")
public class ResourceBundleType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "basename", required = true)
    protected String basename;
    @XmlAttribute(name = "var")
    protected String var;

    /**
     * Obtient la valeur de la propriété basename.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBasename() {
        return basename;
    }

    /**
     * Définit la valeur de la propriété basename.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBasename(String value) {
        this.basename = value;
    }

    /**
     * Obtient la valeur de la propriété var.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVar() {
        return var;
    }

    /**
     * Définit la valeur de la propriété var.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVar(String value) {
        this.var = value;
    }

}
