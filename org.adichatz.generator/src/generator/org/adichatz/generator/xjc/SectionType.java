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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour sectionType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="sectionType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}clientCanvasType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="managedToolBar" type="{}managedToolBarType"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getSectionType", name = "sectionType", propOrder = {
    "managedToolBar"
})
public class SectionType
    extends ClientCanvasType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected ManagedToolBarType managedToolBar;

    /**
     * Obtient la valeur de la propriété managedToolBar.
     * 
     * @return
     *     possible object is
     *     {@link ManagedToolBarType }
     *     
     */
    public ManagedToolBarType getManagedToolBar() {
        return managedToolBar;
    }

    /**
     * Définit la valeur de la propriété managedToolBar.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagedToolBarType }
     *     
     */
    public void setManagedToolBar(ManagedToolBarType value) {
        this.managedToolBar = value;
    }

}
