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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour encryptedTextType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="encryptedTextType">
 *   &lt;complexContent>
 *     &lt;extension base="{}textType">
 *       &lt;attribute name="encryptedKey" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getEncryptedTextType", name = "encryptedTextType")
public class EncryptedTextType
    extends TextType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "encryptedKey")
    @XmlSchemaType(name = "anySimpleType")
    protected String encryptedKey;

    /**
     * Obtient la valeur de la propri�t� encryptedKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptedKey() {
        return encryptedKey;
    }

    /**
     * D�finit la valeur de la propri�t� encryptedKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptedKey(String value) {
        this.encryptedKey = value;
    }

}
