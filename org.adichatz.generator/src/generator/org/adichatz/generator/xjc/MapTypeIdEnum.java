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
 * &lt;p&gt;Classe Java pour mapTypeIdEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="mapTypeIdEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="roadmap"/&amp;gt;
 *     &amp;lt;enumeration value="satellite"/&amp;gt;
 *     &amp;lt;enumeration value="hybrid"/&amp;gt;
 *     &amp;lt;enumeration value="terrain"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "mapTypeIdEnum")
@XmlEnum
public enum MapTypeIdEnum {

    @XmlEnumValue("roadmap")
    ROADMAP("roadmap"),
    @XmlEnumValue("satellite")
    SATELLITE("satellite"),
    @XmlEnumValue("hybrid")
    HYBRID("hybrid"),
    @XmlEnumValue("terrain")
    TERRAIN("terrain");
    private final String value;

    MapTypeIdEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MapTypeIdEnum fromValue(String v) {
        for (MapTypeIdEnum c: MapTypeIdEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
