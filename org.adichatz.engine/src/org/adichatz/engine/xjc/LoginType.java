//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:33 PM CEST 
//


package org.adichatz.engine.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour loginType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="loginType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;attribute name="loginClassName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="loginTemplate" use="required" type="{}loginTemplateEnum" /&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
     * Obtient la valeur de la propriété loginClassName.
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
     * Définit la valeur de la propriété loginClassName.
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
     * Obtient la valeur de la propriété loginTemplate.
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
     * Définit la valeur de la propriété loginTemplate.
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
