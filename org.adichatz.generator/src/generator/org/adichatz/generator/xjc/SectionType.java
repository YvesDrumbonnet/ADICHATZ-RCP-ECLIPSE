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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour sectionType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="sectionType">
 *   &lt;complexContent>
 *     &lt;extension base="{}clientCanvasType">
 *       &lt;sequence>
 *         &lt;element name="managedToolBar" type="{}managedToolBarType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
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
     * Obtient la valeur de la propri�t� managedToolBar.
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
     * D�finit la valeur de la propri�t� managedToolBar.
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
