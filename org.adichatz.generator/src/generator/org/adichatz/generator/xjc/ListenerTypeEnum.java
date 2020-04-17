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
 * <p>Classe Java pour listenerTypeEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="listenerTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="CHANGE_STATUS"/>
 *     &lt;enumeration value="LOCK_ENTITY"/>
 *     &lt;enumeration value="BEFORE_PROPERTY_CHANGE"/>
 *     &lt;enumeration value="WHEN_PROPERTY_CHANGE"/>
 *     &lt;enumeration value="AFTER_PROPERTY_CHANGE"/>
 *     &lt;enumeration value="PRE_SAVE"/>
 *     &lt;enumeration value="POST_SAVE"/>
 *     &lt;enumeration value="PRE_REFRESH"/>
 *     &lt;enumeration value="POST_REFRESH"/>
 *     &lt;enumeration value="PRE_SAVE_ENTITIES"/>
 *     &lt;enumeration value="POST_SAVE_ENTITIES"/>
 *     &lt;enumeration value="PRE_REFRESH_ENTITIES"/>
 *     &lt;enumeration value="POST_REFRESH_ENTITIES"/>
 *     &lt;enumeration value="ADD_ENTITY"/>
 *     &lt;enumeration value="REMOVE_ENTITY"/>
 *     &lt;enumeration value="POST_MESSAGE"/>
 *     &lt;enumeration value="BEFORE_ENTITY_INJECTION"/>
 *     &lt;enumeration value="AFTER_ENTITY_INJECTION"/>
 *     &lt;enumeration value="AFTER_INITIALIZE"/>
 *     &lt;enumeration value="BEFORE_CREATE_CONTROL"/>
 *     &lt;enumeration value="AFTER_CREATE_CONTROL"/>
 *     &lt;enumeration value="BEFORE_SYNCHRONIZE"/>
 *     &lt;enumeration value="AFTER_SYNCHRONIZE"/>
 *     &lt;enumeration value="BEFORE_END_LIFE_CYCLE"/>
 *     &lt;enumeration value="AFTER_END_LIFE_CYCLE"/>
 *     &lt;enumeration value="BEFORE_DISPOSE"/>
 *     &lt;enumeration value="AFTER_DISPOSE"/>
 *     &lt;enumeration value="POST_CREATE_PART"/>
 *     &lt;enumeration value="PRE_RUN"/>
 *     &lt;enumeration value="POST_RUN"/>
 *     &lt;enumeration value="MODIFY_TEXT"/>
 *     &lt;enumeration value="CHECK_STATE"/>
 *     &lt;enumeration value="SELECTION_CHANGED"/>
 *     &lt;enumeration value="POST_SELECTION_CHANGED"/>
 *     &lt;enumeration value="WIDGET_SELECTED"/>
 *     &lt;enumeration value="DOUBLE_CLICK"/>
 *     &lt;enumeration value="REFRESH"/>
 *     &lt;enumeration value="BEFORE_FIELD_CHANGE"/>
 *     &lt;enumeration value="AFTER_FIELD_CHANGE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "listenerTypeEnum")
@XmlEnum
public enum ListenerTypeEnum {

    CHANGE_STATUS,
    LOCK_ENTITY,
    BEFORE_PROPERTY_CHANGE,
    WHEN_PROPERTY_CHANGE,
    AFTER_PROPERTY_CHANGE,
    PRE_SAVE,
    POST_SAVE,
    PRE_REFRESH,
    POST_REFRESH,
    PRE_SAVE_ENTITIES,
    POST_SAVE_ENTITIES,
    PRE_REFRESH_ENTITIES,
    POST_REFRESH_ENTITIES,
    ADD_ENTITY,
    REMOVE_ENTITY,
    POST_MESSAGE,
    BEFORE_ENTITY_INJECTION,
    AFTER_ENTITY_INJECTION,
    AFTER_INITIALIZE,
    BEFORE_CREATE_CONTROL,
    AFTER_CREATE_CONTROL,
    BEFORE_SYNCHRONIZE,
    AFTER_SYNCHRONIZE,
    BEFORE_END_LIFE_CYCLE,
    AFTER_END_LIFE_CYCLE,
    BEFORE_DISPOSE,
    AFTER_DISPOSE,
    POST_CREATE_PART,
    PRE_RUN,
    POST_RUN,
    MODIFY_TEXT,
    CHECK_STATE,
    SELECTION_CHANGED,
    POST_SELECTION_CHANGED,
    WIDGET_SELECTED,
    DOUBLE_CLICK,
    REFRESH,
    BEFORE_FIELD_CHANGE,
    AFTER_FIELD_CHANGE;

    public String value() {
        return name();
    }

    public static ListenerTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
