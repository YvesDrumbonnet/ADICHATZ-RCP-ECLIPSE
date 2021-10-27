//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour formatEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="formatEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="Byte"/&amp;gt;
 *     &amp;lt;enumeration value="Double"/&amp;gt;
 *     &amp;lt;enumeration value="Float"/&amp;gt;
 *     &amp;lt;enumeration value="Integer"/&amp;gt;
 *     &amp;lt;enumeration value="Long"/&amp;gt;
 *     &amp;lt;enumeration value="Percent"/&amp;gt;
 *     &amp;lt;enumeration value="Short"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "formatEnum")
@XmlEnum
public enum FormatEnum {

    @XmlEnumValue("Byte")
    BYTE("Byte"),
    @XmlEnumValue("Double")
    DOUBLE("Double"),
    @XmlEnumValue("Float")
    FLOAT("Float"),
    @XmlEnumValue("Integer")
    INTEGER("Integer"),
    @XmlEnumValue("Long")
    LONG("Long"),
    @XmlEnumValue("Percent")
    PERCENT("Percent"),
    @XmlEnumValue("Short")
    SHORT("Short");
    private final String value;

    FormatEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormatEnum fromValue(String v) {
        for (FormatEnum c: FormatEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
