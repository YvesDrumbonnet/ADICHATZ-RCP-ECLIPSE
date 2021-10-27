//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:33 PM CEST 
//


package org.adichatz.jpa.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour jointureTypeEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="jointureTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="LEFT JOIN FETCH"/&amp;gt;
 *     &amp;lt;enumeration value="JOIN FETCH"/&amp;gt;
 *     &amp;lt;enumeration value="JOIN"/&amp;gt;
 *     &amp;lt;enumeration value="LEFT JOIN"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "jointureTypeEnum")
@XmlEnum
public enum JointureTypeEnum {

    @XmlEnumValue("LEFT JOIN FETCH")
    LEFT_JOIN_FETCH("LEFT JOIN FETCH"),
    @XmlEnumValue("JOIN FETCH")
    JOIN_FETCH("JOIN FETCH"),
    JOIN("JOIN"),
    @XmlEnumValue("LEFT JOIN")
    LEFT_JOIN("LEFT JOIN");
    private final String value;

    JointureTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static JointureTypeEnum fromValue(String v) {
        for (JointureTypeEnum c: JointureTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
