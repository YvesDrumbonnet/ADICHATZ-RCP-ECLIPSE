//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
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
 * <p>Classe Java pour compositeBagType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="compositeBagType">
 *   &lt;complexContent>
 *     &lt;extension base="{}collectionType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="scrolledForm" type="{}scrolledFormType"/>
 *         &lt;element name="section" type="{}sectionType"/>
 *         &lt;element name="composite" type="{}compositeType"/>
 *         &lt;element name="group" type="{}groupType"/>
 *         &lt;element name="pgroup" type="{}pGroupType"/>
 *         &lt;element name="sashForm" type="{}sashFormType"/>
 *         &lt;element name="scrolledComposite" type="{}scrolledCompositeType"/>
 *         &lt;element name="cTabFolder" type="{}cTabFolderType"/>
 *         &lt;element name="argTabFolder" type="{}argTabFolderType"/>
 *         &lt;element name="pshelf" type="{}pShelfType"/>
 *         &lt;element name="argPShelf" type="{}argPShelfType"/>
 *         &lt;element name="include" type="{}includeType"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scrolledFormOrSectionOrComposite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScrolledFormOrSectionOrComposite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
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
