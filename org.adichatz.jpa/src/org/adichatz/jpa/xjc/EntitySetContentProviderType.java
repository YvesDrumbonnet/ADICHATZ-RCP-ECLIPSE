//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:22 PM CEST 
//


package org.adichatz.jpa.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour entitySetContentProviderType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="entitySetContentProviderType">
 *   &lt;complexContent>
 *     &lt;extension base="{}listDetailContentProviderType">
 *       &lt;attribute name="parentEntityURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getEntitySetContentProviderType", name = "entitySetContentProviderType")
public class EntitySetContentProviderType
    extends ListDetailContentProviderType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "parentEntityURI")
    protected String parentEntityURI;

    /**
     * Obtient la valeur de la propri�t� parentEntityURI.
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
     * D�finit la valeur de la propri�t� parentEntityURI.
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
