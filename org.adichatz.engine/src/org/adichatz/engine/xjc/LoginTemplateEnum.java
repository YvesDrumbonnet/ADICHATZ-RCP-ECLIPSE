//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.01.22 � 11:02:22 AM CET 
//


package org.adichatz.engine.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour loginTemplateEnum.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="loginTemplateEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="dialog"/>
 *     &lt;enumeration value="os"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "loginTemplateEnum")
@XmlEnum
public enum LoginTemplateEnum {

    @XmlEnumValue("dialog")
    DIALOG("dialog"),
    @XmlEnumValue("os")
    OS("os");
    private final String value;

    LoginTemplateEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LoginTemplateEnum fromValue(String v) {
        for (LoginTemplateEnum c: LoginTemplateEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
