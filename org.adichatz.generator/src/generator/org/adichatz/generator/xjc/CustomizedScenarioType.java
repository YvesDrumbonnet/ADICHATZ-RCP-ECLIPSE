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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour customizedScenarioType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="customizedScenarioType">
 *   &lt;complexContent>
 *     &lt;extension base="{}basicType">
 *       &lt;attribute name="scenarioFile" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mergeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customizedScenarioType")
public class CustomizedScenarioType
    extends BasicType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "scenarioFile")
    protected String scenarioFile;
    @XmlAttribute(name = "mergeDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar mergeDate;

    /**
     * Obtient la valeur de la propriété scenarioFile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScenarioFile() {
        return scenarioFile;
    }

    /**
     * Définit la valeur de la propriété scenarioFile.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScenarioFile(String value) {
        this.scenarioFile = value;
    }

    /**
     * Obtient la valeur de la propriété mergeDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMergeDate() {
        return mergeDate;
    }

    /**
     * Définit la valeur de la propriété mergeDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMergeDate(XMLGregorianCalendar value) {
        this.mergeDate = value;
    }

}
