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
 * <p>Classe Java pour columnTypeEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="columnTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Byte"/>
 *     &lt;enumeration value="Short"/>
 *     &lt;enumeration value="Integer"/>
 *     &lt;enumeration value="Long"/>
 *     &lt;enumeration value="Float"/>
 *     &lt;enumeration value="Double"/>
 *     &lt;enumeration value="Boolean"/>
 *     &lt;enumeration value="BigDecimal"/>
 *     &lt;enumeration value="Date"/>
 *     &lt;enumeration value="String"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "columnTypeEnum")
@XmlEnum
public enum ColumnTypeEnum {

    @XmlEnumValue("Byte")
    BYTE("Byte"),
    @XmlEnumValue("Short")
    SHORT("Short"),
    @XmlEnumValue("Integer")
    INTEGER("Integer"),
    @XmlEnumValue("Long")
    LONG("Long"),
    @XmlEnumValue("Float")
    FLOAT("Float"),
    @XmlEnumValue("Double")
    DOUBLE("Double"),
    @XmlEnumValue("Boolean")
    BOOLEAN("Boolean"),
    @XmlEnumValue("BigDecimal")
    BIG_DECIMAL("BigDecimal"),
    @XmlEnumValue("Date")
    DATE("Date"),
    @XmlEnumValue("String")
    STRING("String");
    private final String value;

    ColumnTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ColumnTypeEnum fromValue(String v) {
        for (ColumnTypeEnum c: ColumnTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
