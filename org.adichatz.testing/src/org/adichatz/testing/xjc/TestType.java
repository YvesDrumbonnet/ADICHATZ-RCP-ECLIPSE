//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2019.10.15 � 11:55:00 AM CEST 
//


package org.adichatz.testing.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour testType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="testType">
 *   &lt;complexContent>
 *     &lt;extension base="{}testNodeType">
 *       &lt;attribute name="testURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testType")
public class TestType
    extends TestNodeType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "testURI", required = true)
    protected String testURI;

    /**
     * Obtient la valeur de la propri�t� testURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestURI() {
        return testURI;
    }

    /**
     * D�finit la valeur de la propri�t� testURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestURI(String value) {
        this.testURI = value;
    }

}
