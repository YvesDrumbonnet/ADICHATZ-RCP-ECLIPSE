//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour anonymous complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="config" type="{}configType"/&amp;gt;
 *         &amp;lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *           &amp;lt;element name="formPage" type="{}formPageType"/&amp;gt;
 *           &amp;lt;element name="scrolledForm" type="{}scrolledFormType"/&amp;gt;
 *           &amp;lt;element name="section" type="{}sectionType"/&amp;gt;
 *           &amp;lt;element name="composite" type="{}compositeType"/&amp;gt;
 *           &amp;lt;element name="compositeBag" type="{}compositeBagType"/&amp;gt;
 *           &amp;lt;element name="group" type="{}groupType"/&amp;gt;
 *           &amp;lt;element name="pgroup" type="{}pGroupType"/&amp;gt;
 *           &amp;lt;element name="sashForm" type="{}sashFormType"/&amp;gt;
 *           &amp;lt;element name="scrolledComposite" type="{}scrolledCompositeType"/&amp;gt;
 *           &amp;lt;element name="table" type="{}tableType"/&amp;gt;
 *           &amp;lt;element name="grid" type="{}gridType"/&amp;gt;
 *           &amp;lt;element name="cTabFolder" type="{}cTabFolderType"/&amp;gt;
 *           &amp;lt;element name="argTabFolder" type="{}argTabFolderType"/&amp;gt;
 *           &amp;lt;element name="pshelf" type="{}pShelfType"/&amp;gt;
 *           &amp;lt;element name="argPShelf" type="{}argPShelfType"/&amp;gt;
 *           &amp;lt;element name="include" type="{}includeType"/&amp;gt;
 *           &amp;lt;element name="headerMenuManager" type="{}headerMenuManagerType"/&amp;gt;
 *           &amp;lt;element name="menuManager" type="{}menuManagerType"/&amp;gt;
 *           &amp;lt;element name="managedToolBar" type="{}managedToolBarType"/&amp;gt;
 *         &amp;lt;/choice&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="adiResourceURI" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getExtendTree", name = "", propOrder = {
    "config",
    "additionalCode",
    "formPageOrScrolledFormOrSection"
})
@XmlRootElement(name = "extendTree")
public class ExtendTree
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ConfigType config;
    protected String additionalCode;
    @XmlElements({
        @XmlElement(name = "formPage", type = FormPageType.class),
        @XmlElement(name = "scrolledForm", type = ScrolledFormType.class),
        @XmlElement(name = "section", type = SectionType.class),
        @XmlElement(name = "composite", type = CompositeType.class),
        @XmlElement(name = "compositeBag", type = CompositeBagType.class),
        @XmlElement(name = "group", type = GroupType.class),
        @XmlElement(name = "pgroup", type = PGroupType.class),
        @XmlElement(name = "sashForm", type = SashFormType.class),
        @XmlElement(name = "scrolledComposite", type = ScrolledCompositeType.class),
        @XmlElement(name = "table", type = TableType.class),
        @XmlElement(name = "grid", type = GridType.class),
        @XmlElement(name = "cTabFolder", type = CTabFolderType.class),
        @XmlElement(name = "argTabFolder", type = ArgTabFolderType.class),
        @XmlElement(name = "pshelf", type = PShelfType.class),
        @XmlElement(name = "argPShelf", type = ArgPShelfType.class),
        @XmlElement(name = "include", type = IncludeType.class),
        @XmlElement(name = "headerMenuManager", type = HeaderMenuManagerType.class),
        @XmlElement(name = "menuManager", type = MenuManagerType.class),
        @XmlElement(name = "managedToolBar", type = ManagedToolBarType.class)
    })
    protected List<ValidElementType> formPageOrScrolledFormOrSection;
    @XmlAttribute(name = "adiResourceURI", required = true)
    protected String adiResourceURI;

    /**
     * Obtient la valeur de la propriété config.
     * 
     * @return
     *     possible object is
     *     {@link ConfigType }
     *     
     */
    public ConfigType getConfig() {
        return config;
    }

    /**
     * Définit la valeur de la propriété config.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigType }
     *     
     */
    public void setConfig(ConfigType value) {
        this.config = value;
    }

    /**
     * Obtient la valeur de la propriété additionalCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCode() {
        return additionalCode;
    }

    /**
     * Définit la valeur de la propriété additionalCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCode(String value) {
        this.additionalCode = value;
    }

    /**
     * Gets the value of the formPageOrScrolledFormOrSection property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the formPageOrScrolledFormOrSection property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFormPageOrScrolledFormOrSection().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link FormPageType }
     * {@link ScrolledFormType }
     * {@link SectionType }
     * {@link CompositeType }
     * {@link CompositeBagType }
     * {@link GroupType }
     * {@link PGroupType }
     * {@link SashFormType }
     * {@link ScrolledCompositeType }
     * {@link TableType }
     * {@link GridType }
     * {@link CTabFolderType }
     * {@link ArgTabFolderType }
     * {@link PShelfType }
     * {@link ArgPShelfType }
     * {@link IncludeType }
     * {@link HeaderMenuManagerType }
     * {@link MenuManagerType }
     * {@link ManagedToolBarType }
     * 
     * 
     */
    public List<ValidElementType> getFormPageOrScrolledFormOrSection() {
        if (formPageOrScrolledFormOrSection == null) {
            formPageOrScrolledFormOrSection = new ArrayList<ValidElementType>();
        }
        return this.formPageOrScrolledFormOrSection;
    }

    /**
     * Obtient la valeur de la propriété adiResourceURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdiResourceURI() {
        return adiResourceURI;
    }

    /**
     * Définit la valeur de la propriété adiResourceURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdiResourceURI(String value) {
        this.adiResourceURI = value;
    }

}
