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
 * &lt;p&gt;Classe Java pour shelfRendererEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="shelfRendererEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="Palette"/&amp;gt;
 *     &amp;lt;enumeration value="Redmond"/&amp;gt;
 *     &amp;lt;enumeration value="Adichatz"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "shelfRendererEnum")
@XmlEnum
public enum ShelfRendererEnum {

    @XmlEnumValue("Palette")
    PALETTE("Palette"),
    @XmlEnumValue("Redmond")
    REDMOND("Redmond"),
    @XmlEnumValue("Adichatz")
    ADICHATZ("Adichatz");
    private final String value;

    ShelfRendererEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ShelfRendererEnum fromValue(String v) {
        for (ShelfRendererEnum c: ShelfRendererEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
