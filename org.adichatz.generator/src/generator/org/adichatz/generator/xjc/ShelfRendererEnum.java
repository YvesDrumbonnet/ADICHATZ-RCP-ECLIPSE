//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.06.26 � 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour shelfRendererEnum.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="shelfRendererEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Palette"/>
 *     &lt;enumeration value="Redmond"/>
 *     &lt;enumeration value="Adichatz"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
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
