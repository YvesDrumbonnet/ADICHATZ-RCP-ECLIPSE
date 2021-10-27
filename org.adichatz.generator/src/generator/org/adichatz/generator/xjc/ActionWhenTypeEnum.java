//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour actionWhenTypeEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="actionWhenTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="WHEN_BUILDING_EJB_JAR"/&amp;gt;
 *     &amp;lt;enumeration value="WHEN_BUILDING_MODEL"/&amp;gt;
 *     &amp;lt;enumeration value="WHEN_BUILDING_UI"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "actionWhenTypeEnum")
@XmlEnum
public enum ActionWhenTypeEnum {

    WHEN_BUILDING_EJB_JAR,
    WHEN_BUILDING_MODEL,
    WHEN_BUILDING_UI;

    public String value() {
        return name();
    }

    public static ActionWhenTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
