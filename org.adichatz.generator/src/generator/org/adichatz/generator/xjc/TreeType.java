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
 * <p>Classe Java pour treeType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� showRoot.
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
     * D�finit la valeur de la propri�t� showRoot.
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
     * Obtient la valeur de la propri�t� treeManager.
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
     * D�finit la valeur de la propri�t� treeManager.
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
     * Obtient la valeur de la propri�t� expand.
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
     * D�finit la valeur de la propri�t� expand.
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
     * Obtient la valeur de la propri�t� rootElement.
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
     * D�finit la valeur de la propri�t� rootElement.
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
