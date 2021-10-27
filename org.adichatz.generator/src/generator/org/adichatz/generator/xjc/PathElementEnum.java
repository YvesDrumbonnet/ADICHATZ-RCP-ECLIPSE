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
 * &lt;p&gt;Classe Java pour pathElementEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="pathElementEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="PLUGIN"/&amp;gt;
 *     &amp;lt;enumeration value="LIBRARY"/&amp;gt;
 *     &amp;lt;enumeration value="PROJECT"/&amp;gt;
 *     &amp;lt;enumeration value="DIRECTORY"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "pathElementEnum")
@XmlEnum
public enum PathElementEnum {

    PLUGIN,
    LIBRARY,
    PROJECT,
    DIRECTORY;

    public String value() {
        return name();
    }

    public static PathElementEnum fromValue(String v) {
        return valueOf(v);
    }

}
