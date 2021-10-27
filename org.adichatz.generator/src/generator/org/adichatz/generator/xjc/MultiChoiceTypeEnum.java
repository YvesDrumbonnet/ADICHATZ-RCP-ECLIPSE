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
 * &lt;p&gt;Classe Java pour multiChoiceTypeEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="multiChoiceTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="Array"/&amp;gt;
 *     &amp;lt;enumeration value="String"/&amp;gt;
 *     &amp;lt;enumeration value="QUERY"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "multiChoiceTypeEnum")
@XmlEnum
public enum MultiChoiceTypeEnum {

    @XmlEnumValue("Array")
    ARRAY("Array"),
    @XmlEnumValue("String")
    STRING("String"),
    QUERY("QUERY");
    private final String value;

    MultiChoiceTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MultiChoiceTypeEnum fromValue(String v) {
        for (MultiChoiceTypeEnum c: MultiChoiceTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
