//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.07.08 à 04:48:18 PM CEST 
//

package org.adichatz.generator.xjc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Classe Java pour multiChoiceTypeEnum.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="multiChoiceTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Array"/>
 *     &lt;enumeration value="String"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "multiChoiceTypeEnum")
@XmlEnum
public enum MultiChoiceTypeEnum {

	@XmlEnumValue("Array")
	ARRAY("Array"), @XmlEnumValue("String")
	STRING("String"), @XmlEnumValue("Query")
	QUERY("Query");

	private final String value;

	MultiChoiceTypeEnum(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static MultiChoiceTypeEnum fromValue(String v) {
		for (MultiChoiceTypeEnum c : MultiChoiceTypeEnum.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
