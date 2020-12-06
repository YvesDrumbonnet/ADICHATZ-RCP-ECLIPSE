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
 * <p>Classe Java pour fileTextType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="fileTextType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlFieldType">
 *       &lt;attribute name="filterExtension" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="filterPath" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
