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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour lazyFetchContainerType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="lazyFetchContainerType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;attribute name="layoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lazyFetches" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lazyFetchContainerType")
@XmlSeeAlso({
    SetType.class,
    FieldContainerType.class
})
public class LazyFetchContainerType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "layoutData")
    protected String layoutData;
    @XmlAttribute(name = "lazyFetches")
    protected String lazyFetches;

    /**
     * Obtient la valeur de la propriété layoutData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLayoutData() {
        return layoutData;
    }

    /**
     * Définit la valeur de la propriété layoutData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLayoutData(String value) {
        this.layoutData = value;
    }

    /**
     * Obtient la valeur de la propriété lazyFetches.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLazyFetches() {
        return lazyFetches;
    }

    /**
     * Définit la valeur de la propriété lazyFetches.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLazyFetches(String value) {
        this.lazyFetches = value;
    }

}
