//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour generationEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="generationEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="MODEL_PART"/>
 *     &lt;enumeration value="EJB"/>
 *     &lt;enumeration value="APPLICATION_PART"/>
 *     &lt;enumeration value="RCP_PART"/>
 *     &lt;enumeration value="PLUGIN_ENTITY"/>
 *     &lt;enumeration value="NAVIGATOR"/>
 *     &lt;enumeration value="ENTITY"/>
 *     &lt;enumeration value="MESSAGE_BUNDLE"/>
 *     &lt;enumeration value="EDITOR"/>
 *     &lt;enumeration value="DETAIL"/>
 *     &lt;enumeration value="TABLE"/>
 *     &lt;enumeration value="QUERY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
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
