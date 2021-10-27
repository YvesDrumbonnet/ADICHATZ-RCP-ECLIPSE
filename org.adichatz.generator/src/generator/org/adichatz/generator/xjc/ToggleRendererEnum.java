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
 * &lt;p&gt;Classe Java pour toggleRendererEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="toggleRendererEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="Chevrons"/&amp;gt;
 *     &amp;lt;enumeration value="MinMax"/&amp;gt;
 *     &amp;lt;enumeration value="None"/&amp;gt;
 *     &amp;lt;enumeration value="Tree"/&amp;gt;
 *     &amp;lt;enumeration value="Twiste"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "toggleRendererEnum")
@XmlEnum
public enum ToggleRendererEnum {

    @XmlEnumValue("Chevrons")
    CHEVRONS("Chevrons"),
    @XmlEnumValue("MinMax")
    MIN_MAX("MinMax"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Tree")
    TREE("Tree"),
    @XmlEnumValue("Twiste")
    TWISTE("Twiste");
    private final String value;

    ToggleRendererEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ToggleRendererEnum fromValue(String v) {
        for (ToggleRendererEnum c: ToggleRendererEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
