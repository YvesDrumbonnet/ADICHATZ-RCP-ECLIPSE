//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.10.15 à 11:55:00 AM CEST 
//


package org.adichatz.testing.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="suite" type="{}suiteType"/>
 *           &lt;element name="testFile" type="{}testFileType"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="classLoaders" type="{}classLoadersType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="applicationTestRunerURI" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="expanded" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "suiteOrTestFile",
    "classLoaders"
})
@XmlRootElement(name = "adichatzTestingTree")
public class AdichatzTestingTree
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "suite", type = SuiteType.class),
        @XmlElement(name = "testFile", type = TestFileType.class)
    })
    protected List<CollectionTestType> suiteOrTestFile;
    protected ClassLoadersType classLoaders;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String id;
    @XmlAttribute(name = "applicationTestRunerURI")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String applicationTestRunerURI;
    @XmlAttribute(name = "expanded")
    protected Boolean expanded;

    /**
     * Gets the value of the suiteOrTestFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the suiteOrTestFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSuiteOrTestFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SuiteType }
     * {@link TestFileType }
     * 
     * 
     */
    public List<CollectionTestType> getSuiteOrTestFile() {
        if (suiteOrTestFile == null) {
            suiteOrTestFile = new ArrayList<CollectionTestType>();
        }
        return this.suiteOrTestFile;
    }

    /**
     * Obtient la valeur de la propriété classLoaders.
     * 
     * @return
     *     possible object is
     *     {@link ClassLoadersType }
     *     
     */
    public ClassLoadersType getClassLoaders() {
        return classLoaders;
    }

    /**
     * Définit la valeur de la propriété classLoaders.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassLoadersType }
     *     
     */
    public void setClassLoaders(ClassLoadersType value) {
        this.classLoaders = value;
    }

    /**
     * Obtient la valeur de la propriété id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété applicationTestRunerURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationTestRunerURI() {
        return applicationTestRunerURI;
    }

    /**
     * Définit la valeur de la propriété applicationTestRunerURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationTestRunerURI(String value) {
        this.applicationTestRunerURI = value;
    }

    /**
     * Obtient la valeur de la propriété expanded.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isExpanded() {
        if (expanded == null) {
            return true;
        } else {
            return expanded;
        }
    }

    /**
     * Définit la valeur de la propriété expanded.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExpanded(Boolean value) {
        this.expanded = value;
    }

}
