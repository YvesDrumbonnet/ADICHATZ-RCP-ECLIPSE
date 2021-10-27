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
 * &lt;p&gt;Classe Java pour entitySetContentProviderType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="entitySetContentProviderType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}listDetailContentProviderType"&amp;gt;
 *       &amp;lt;attribute name="parentEntityURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entitySetContentProviderType")
public class EntitySetContentProviderType
    extends ListDetailContentProviderType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "parentEntityURI")
    protected String parentEntityURI;

    /**
     * Obtient la valeur de la propriété parentEntityURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentEntityURI() {
        return parentEntityURI;
    }

    /**
     * Définit la valeur de la propriété parentEntityURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentEntityURI(String value) {
        this.parentEntityURI = value;
    }

}
