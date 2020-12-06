//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour jointureTypeEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="jointureTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="LEFT JOIN FETCH"/>
 *     &lt;enumeration value="JOIN FETCH"/>
 *     &lt;enumeration value="JOIN"/>
 *     &lt;enumeration value="LEFT JOIN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
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
