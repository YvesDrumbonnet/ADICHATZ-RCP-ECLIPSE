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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour compositeBagType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="compositeBagType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}collectionType"&amp;gt;
 *       &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *         &amp;lt;element name="scrolledForm" type="{}scrolledFormType"/&amp;gt;
 *         &amp;lt;element name="section" type="{}sectionType"/&amp;gt;
 *         &amp;lt;element name="composite" type="{}compositeType"/&amp;gt;
 *         &amp;lt;element name="group" type="{}groupType"/&amp;gt;
 *         &amp;lt;element name="pgroup" type="{}pGroupType"/&amp;gt;
 *         &amp;lt;element name="sashForm" type="{}sashFormType"/&amp;gt;
 *         &amp;lt;element name="scrolledComposite" type="{}scrolledCompositeType"/&amp;gt;
 *         &amp;lt;element name="cTabFolder" type="{}cTabFolderType"/&amp;gt;
 *         &amp;lt;element name="argTabFolder" type="{}argTabFolderType"/&amp;gt;
 *         &amp;lt;element name="pshelf" type="{}pShelfType"/&amp;gt;
 *         &amp;lt;element name="argPShelf" type="{}argPShelfType"/&amp;gt;
 *         &amp;lt;element name="include" type="{}includeType"/&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getCompositeBagType", name = "compositeBagType", propOrder = {
    "scrolledFormOrSectionOrComposite"
})
public class CompositeBagType
    extends CollectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElements({
        @XmlElement(name = "scrolledForm", type = ScrolledFormType.class),
        @XmlElement(name = "section", type = SectionType.class),
        @XmlElement(name = "composite", type = CompositeType.class),
        @XmlElement(name = "group", type = GroupType.class),
        @XmlElement(name = "pgroup", type = PGroupType.class),
        @XmlElement(name = "sashForm", type = SashFormType.class),
        @XmlElement(name = "scrolledComposite", type = ScrolledCompositeType.class),
        @XmlElement(name = "cTabFolder", type = CTabFolderType.class),
        @XmlElement(name = "argTabFolder", type = ArgTabFolderType.class),
        @XmlElement(name = "pshelf", type = PShelfType.class),
        @XmlElement(name = "argPShelf", type = ArgPShelfType.class),
        @XmlElement(name = "include", type = IncludeType.class)
    })
    protected List<ValidElementType> scrolledFormOrSectionOrComposite;

    /**
     * Gets the value of the scrolledFormOrSectionOrComposite property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the scrolledFormOrSectionOrComposite property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getScrolledFormOrSectionOrComposite().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ScrolledFormType }
     * {@link SectionType }
     * {@link CompositeType }
     * {@link GroupType }
     * {@link PGroupType }
     * {@link SashFormType }
     * {@link ScrolledCompositeType }
     * {@link CTabFolderType }
     * {@link ArgTabFolderType }
     * {@link PShelfType }
     * {@link ArgPShelfType }
     * {@link IncludeType }
     * 
     * 
     */
    public List<ValidElementType> getScrolledFormOrSectionOrComposite() {
        if (scrolledFormOrSectionOrComposite == null) {
            scrolledFormOrSectionOrComposite = new ArrayList<ValidElementType>();
        }
        return this.scrolledFormOrSectionOrComposite;
    }

}
