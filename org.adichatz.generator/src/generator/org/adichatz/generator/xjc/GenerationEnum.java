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
 * &lt;p&gt;Classe Java pour generationEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="generationEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="MODEL_PART"/&amp;gt;
 *     &amp;lt;enumeration value="EJB"/&amp;gt;
 *     &amp;lt;enumeration value="APPLICATION_PART"/&amp;gt;
 *     &amp;lt;enumeration value="RCP_PART"/&amp;gt;
 *     &amp;lt;enumeration value="PLUGIN_ENTITY"/&amp;gt;
 *     &amp;lt;enumeration value="NAVIGATOR"/&amp;gt;
 *     &amp;lt;enumeration value="ENTITY"/&amp;gt;
 *     &amp;lt;enumeration value="MESSAGE_BUNDLE"/&amp;gt;
 *     &amp;lt;enumeration value="EDITOR"/&amp;gt;
 *     &amp;lt;enumeration value="DETAIL"/&amp;gt;
 *     &amp;lt;enumeration value="TABLE"/&amp;gt;
 *     &amp;lt;enumeration value="QUERY"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "generationEnum")
@XmlEnum
public enum GenerationEnum {

    MODEL_PART,
    EJB,
    APPLICATION_PART,
    RCP_PART,
    PLUGIN_ENTITY,
    NAVIGATOR,
    ENTITY,
    MESSAGE_BUNDLE,
    EDITOR,
    DETAIL,
    TABLE,
    QUERY;

    public String value() {
        return name();
    }

    public static GenerationEnum fromValue(String v) {
        return valueOf(v);
    }

}
