//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:22 AM CET 
//


package org.adichatz.engine.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour loginType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="loginType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="loginClassName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="loginTemplate" use="required" type="{}loginTemplateEnum" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginType")
public class LoginType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "loginClassName", required = true)
    protected String loginClassName;
    @XmlAttribute(name = "loginTemplate", required = true)
    protected LoginTemplateEnum loginTemplate;

    /**
     * Obtient la valeur de la propri�t� loginClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginClassName() {
        return loginClassName;
    }

    /**
     * D�finit la valeur de la propri�t� loginClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginClassName(String value) {
        this.loginClassName = value;
    }

    /**
     * Obtient la valeur de la propri�t� loginTemplate.
     * 
     * @return
     *     possible object is
     *     {@link LoginTemplateEnum }
     *     
     */
    public LoginTemplateEnum getLoginTemplate() {
        return loginTemplate;
    }

    /**
     * D�finit la valeur de la propri�t� loginTemplate.
     * 
     * @param value
     *     allowed object is
     *     {@link LoginTemplateEnum }
     *     
     */
    public void setLoginTemplate(LoginTemplateEnum value) {
        this.loginTemplate = value;
    }

}
