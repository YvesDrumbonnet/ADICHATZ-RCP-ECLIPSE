//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.22 à 11:02:17 AM CET 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour partImageTypeEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="partImageTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="EDITOR"/>
 *     &lt;enumeration value="CREATION_ITEM"/>
 *     &lt;enumeration value="QUERY_ITEM"/>
 *     &lt;enumeration value="MENU"/>
 *     &lt;enumeration value="ENTITY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "partImageTypeEnum")
@XmlEnum
public enum PartImageTypeEnum {

    EDITOR,
    CREATION_ITEM,
    QUERY_ITEM,
    MENU,
    ENTITY;

    public String value() {
        return name();
    }

    public static PartImageTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
