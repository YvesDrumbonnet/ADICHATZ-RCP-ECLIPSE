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
 * &lt;p&gt;Classe Java pour listenerTypeEnum.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="listenerTypeEnum"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}token"&amp;gt;
 *     &amp;lt;enumeration value="CHANGE_STATUS"/&amp;gt;
 *     &amp;lt;enumeration value="LOCK_ENTITY"/&amp;gt;
 *     &amp;lt;enumeration value="BEFORE_PROPERTY_CHANGE"/&amp;gt;
 *     &amp;lt;enumeration value="WHEN_PROPERTY_CHANGE"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_PROPERTY_CHANGE"/&amp;gt;
 *     &amp;lt;enumeration value="PRE_SAVE"/&amp;gt;
 *     &amp;lt;enumeration value="POST_SAVE"/&amp;gt;
 *     &amp;lt;enumeration value="PRE_REFRESH"/&amp;gt;
 *     &amp;lt;enumeration value="POST_REFRESH"/&amp;gt;
 *     &amp;lt;enumeration value="PRE_SAVE_ENTITIES"/&amp;gt;
 *     &amp;lt;enumeration value="POST_SAVE_ENTITIES"/&amp;gt;
 *     &amp;lt;enumeration value="PRE_REFRESH_ENTITIES"/&amp;gt;
 *     &amp;lt;enumeration value="POST_REFRESH_ENTITIES"/&amp;gt;
 *     &amp;lt;enumeration value="ADD_ENTITY"/&amp;gt;
 *     &amp;lt;enumeration value="REMOVE_ENTITY"/&amp;gt;
 *     &amp;lt;enumeration value="POST_MESSAGE"/&amp;gt;
 *     &amp;lt;enumeration value="BEFORE_ENTITY_INJECTION"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_ENTITY_INJECTION"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_INITIALIZE"/&amp;gt;
 *     &amp;lt;enumeration value="BEFORE_CREATE_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_CREATE_CONTROL"/&amp;gt;
 *     &amp;lt;enumeration value="BEFORE_SYNCHRONIZE"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_SYNCHRONIZE"/&amp;gt;
 *     &amp;lt;enumeration value="BEFORE_END_LIFE_CYCLE"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_END_LIFE_CYCLE"/&amp;gt;
 *     &amp;lt;enumeration value="BEFORE_DISPOSE"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_DISPOSE"/&amp;gt;
 *     &amp;lt;enumeration value="POST_CREATE_PART"/&amp;gt;
 *     &amp;lt;enumeration value="BEFORE_FIELD_CHANGE"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_FIELD_CHANGE"/&amp;gt;
 *     &amp;lt;enumeration value="PRE_RUN"/&amp;gt;
 *     &amp;lt;enumeration value="POST_RUN"/&amp;gt;
 *     &amp;lt;enumeration value="MODIFY_TEXT"/&amp;gt;
 *     &amp;lt;enumeration value="CHECK_STATE"/&amp;gt;
 *     &amp;lt;enumeration value="SELECTION_CHANGED"/&amp;gt;
 *     &amp;lt;enumeration value="POST_SELECTION_CHANGED"/&amp;gt;
 *     &amp;lt;enumeration value="WIDGET_SELECTED"/&amp;gt;
 *     &amp;lt;enumeration value="DOUBLE_CLICK"/&amp;gt;
 *     &amp;lt;enumeration value="BEFORE_REFRESH"/&amp;gt;
 *     &amp;lt;enumeration value="AFTER_REFRESH"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
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
    BEFORE_FIELD_CHANGE,
    AFTER_FIELD_CHANGE,
    PRE_RUN,
    POST_RUN,
    MODIFY_TEXT,
    CHECK_STATE,
    SELECTION_CHANGED,
    POST_SELECTION_CHANGED,
    WIDGET_SELECTED,
    DOUBLE_CLICK,
    BEFORE_REFRESH,
    AFTER_REFRESH;

    public String value() {
        return name();
    }

    public static ListenerTypeEnum fromValue(String v) {
        return valueOf(v);
    }

}
