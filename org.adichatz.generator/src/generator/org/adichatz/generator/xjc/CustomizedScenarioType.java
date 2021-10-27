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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * &lt;p&gt;Classe Java pour customizedScenarioType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="customizedScenarioType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}basicType"&amp;gt;
 *       &amp;lt;attribute name="scenarioFile" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="mergeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
