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
 * <p>Classe Java pour fileTextType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� filterExtension.
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
     * D�finit la valeur de la propri�t� filterExtension.
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
     * Obtient la valeur de la propri�t� filterPath.
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
     * D�finit la valeur de la propri�t� filterPath.
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
