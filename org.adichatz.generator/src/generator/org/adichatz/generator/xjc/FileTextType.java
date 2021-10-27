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
 * &lt;p&gt;Classe Java pour fileTextType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="fileTextType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}controlFieldType"&amp;gt;
 *       &amp;lt;attribute name="filterExtension" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="filterPath" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getFileTextType", name = "fileTextType")
public class FileTextType
    extends ControlFieldType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "filterExtension")
    protected String filterExtension;
    @XmlAttribute(name = "filterPath")
    protected String filterPath;

    /**
     * Obtient la valeur de la propriété filterExtension.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterExtension() {
        return filterExtension;
    }

    /**
     * Définit la valeur de la propriété filterExtension.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterExtension(String value) {
        this.filterExtension = value;
    }

    /**
     * Obtient la valeur de la propriété filterPath.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterPath() {
        return filterPath;
    }

    /**
     * Définit la valeur de la propriété filterPath.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterPath(String value) {
        this.filterPath = value;
    }

}
