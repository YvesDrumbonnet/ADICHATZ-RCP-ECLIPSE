//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour mapDataTypeEnum.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="mapDataTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="COORDINATES"/>
 *     &lt;enumeration value="ADDRESS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "mapDataTypeEnum")
@XmlEnum
public enum MapDataTypeEnum {

    COORDINATES,
    ADDRESS;

    public String value() {
        return name();
    }

    public static MapDataTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
