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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour dirtyContainerType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="dirtyContainerType">
 *   &lt;complexContent>
 *     &lt;extension base="{}validableContainerType">
 *       &lt;attribute name="dirtyManagement" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dirtyContainerType")
@XmlSeeAlso({
    GroupType.class,
    ClientCanvasType.class
})
public class DirtyContainerType
    extends ValidableContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "dirtyManagement")
    protected String dirtyManagement;

    /**
     * Obtient la valeur de la propri�t� dirtyManagement.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirtyManagement() {
        return dirtyManagement;
    }

    /**
     * D�finit la valeur de la propri�t� dirtyManagement.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirtyManagement(String value) {
        this.dirtyManagement = value;
    }

}
