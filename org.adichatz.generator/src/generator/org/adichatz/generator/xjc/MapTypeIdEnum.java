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
 * <p>Classe Java pour mapTypeIdEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="mapTypeIdEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="roadmap"/>
 *     &lt;enumeration value="satellite"/>
 *     &lt;enumeration value="hybrid"/>
 *     &lt;enumeration value="terrain"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
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
