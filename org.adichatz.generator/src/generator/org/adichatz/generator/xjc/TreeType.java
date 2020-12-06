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
 * <p>Classe Java pour treeType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="treeType">
 *   &lt;complexContent>
 *     &lt;extension base="{}setType">
 *       &lt;attribute name="showRoot" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="treeManager" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="expand" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="rootElement" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getTreeType", name = "treeType")
public class TreeType
    extends SetType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "showRoot")
    protected String showRoot;
    @XmlAttribute(name = "treeManager")
    protected String treeManager;
    @XmlAttribute(name = "expand")
    protected String expand;
    @XmlAttribute(name = "rootElement")
    protected String rootElement;

    /**
     * Obtient la valeur de la propriété showRoot.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShowRoot() {
        return showRoot;
    }

    /**
     * Définit la valeur de la propriété showRoot.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShowRoot(String value) {
        this.showRoot = value;
    }

    /**
     * Obtient la valeur de la propriété treeManager.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTreeManager() {
        return treeManager;
    }

    /**
     * Définit la valeur de la propriété treeManager.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTreeManager(String value) {
        this.treeManager = value;
    }

    /**
     * Obtient la valeur de la propriété expand.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpand() {
        return expand;
    }

    /**
     * Définit la valeur de la propriété expand.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpand(String value) {
        this.expand = value;
    }

    /**
     * Obtient la valeur de la propriété rootElement.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootElement() {
        return rootElement;
    }

    /**
     * Définit la valeur de la propriété rootElement.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootElement(String value) {
        this.rootElement = value;
    }

}
