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
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour treeType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="treeType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}setType"&amp;gt;
 *       &amp;lt;attribute name="showRoot" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="treeManager" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="expand" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="rootElement" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
