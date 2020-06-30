//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour toggleRendererEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="toggleRendererEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Chevrons"/>
 *     &lt;enumeration value="MinMax"/>
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Tree"/>
 *     &lt;enumeration value="Twiste"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
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
