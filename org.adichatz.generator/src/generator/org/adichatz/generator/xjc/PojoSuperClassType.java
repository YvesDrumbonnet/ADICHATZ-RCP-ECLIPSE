//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour pojoSuperClassType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="pojoSuperClassType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="superClassURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sourceURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pojoSuperClassType")
public class PojoSuperClassType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "superClassURI")
    protected String superClassURI;
    @XmlAttribute(name = "sourceURI")
    protected String sourceURI;

    /**
     * Obtient la valeur de la propri�t� superClassURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuperClassURI() {
        return superClassURI;
    }

    /**
     * D�finit la valeur de la propri�t� superClassURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuperClassURI(String value) {
        this.superClassURI = value;
    }

    /**
     * Obtient la valeur de la propri�t� sourceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceURI() {
        return sourceURI;
    }

    /**
     * D�finit la valeur de la propri�t� sourceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceURI(String value) {
        this.sourceURI = value;
    }

}
