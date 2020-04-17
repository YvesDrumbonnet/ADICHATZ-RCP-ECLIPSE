//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour formatEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="formatEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Byte"/>
 *     &lt;enumeration value="Double"/>
 *     &lt;enumeration value="Float"/>
 *     &lt;enumeration value="Integer"/>
 *     &lt;enumeration value="Long"/>
 *     &lt;enumeration value="Percent"/>
 *     &lt;enumeration value="Short"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
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
