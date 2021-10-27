//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * &lt;p&gt;Classe Java pour queryParameterType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="queryParameterType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}elementType"&amp;gt;
 *       &amp;lt;attribute name="prompt" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="suffix" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="valid" type="{http://www.w3.org/2001/XMLSchema}boolean" /&amp;gt;
 *       &amp;lt;attribute name="style" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="permanent" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *       &amp;lt;attribute name="visible" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&amp;gt;
 *       &amp;lt;attribute name="columnText" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="binaryExpression" type="{http://www.w3.org/2001/XMLSchema}base64Binary" /&amp;gt;
 *       &amp;lt;attribute name="expression" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="operator" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="secondColumnText" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="binarySecondExpression" type="{http://www.w3.org/2001/XMLSchema}base64Binary" /&amp;gt;
 *       &amp;lt;attribute name="secondExpression" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="expressionMethodURI" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" /&amp;gt;
 *       &amp;lt;attribute name="expressionClassName" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryParameterType")
public class QueryParameterType
    extends ElementType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(name = "prompt")
    protected String prompt;
    @XmlAttribute(name = "property")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String property;
    @XmlAttribute(name = "entityURI")
    protected String entityURI;
    @XmlAttribute(name = "suffix", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String suffix;
    @XmlAttribute(name = "valid")
    protected Boolean valid;
    @XmlAttribute(name = "style")
    protected String style;
    @XmlAttribute(name = "permanent")
    protected Boolean permanent;
    @XmlAttribute(name = "visible")
    protected Boolean visible;
    @XmlAttribute(name = "columnText")
    protected String columnText;
    @XmlAttribute(name = "binaryExpression")
    protected byte[] binaryExpression;
    @XmlAttribute(name = "expression")
    protected String expression;
    @XmlAttribute(name = "operator")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String operator;
    @XmlAttribute(name = "secondColumnText")
    protected String secondColumnText;
    @XmlAttribute(name = "binarySecondExpression")
    protected byte[] binarySecondExpression;
    @XmlAttribute(name = "secondExpression")
    protected String secondExpression;
    @XmlAttribute(name = "expressionMethodURI")
    @XmlSchemaType(name = "anySimpleType")
    protected String expressionMethodURI;
    @XmlAttribute(name = "expressionClassName")
    protected String expressionClassName;

    /**
     * Obtient la valeur de la propriété prompt.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Définit la valeur de la propriété prompt.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrompt(String value) {
        this.prompt = value;
    }

    /**
     * Obtient la valeur de la propriété property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProperty() {
        return property;
    }

    /**
     * Définit la valeur de la propriété property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProperty(String value) {
        this.property = value;
    }

    /**
     * Obtient la valeur de la propriété entityURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityURI() {
        return entityURI;
    }

    /**
     * Définit la valeur de la propriété entityURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityURI(String value) {
        this.entityURI = value;
    }

    /**
     * Obtient la valeur de la propriété suffix.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Définit la valeur de la propriété suffix.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
    }

    /**
     * Obtient la valeur de la propriété valid.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isValid() {
        return valid;
    }

    /**
     * Définit la valeur de la propriété valid.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setValid(Boolean value) {
        this.valid = value;
    }

    /**
     * Obtient la valeur de la propriété style.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyle() {
        return style;
    }

    /**
     * Définit la valeur de la propriété style.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyle(String value) {
        this.style = value;
    }

    /**
     * Obtient la valeur de la propriété permanent.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isPermanent() {
        if (permanent == null) {
            return false;
        } else {
            return permanent;
        }
    }

    /**
     * Définit la valeur de la propriété permanent.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPermanent(Boolean value) {
        this.permanent = value;
    }

    /**
     * Obtient la valeur de la propriété visible.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isVisible() {
        if (visible == null) {
            return true;
        } else {
            return visible;
        }
    }

    /**
     * Définit la valeur de la propriété visible.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVisible(Boolean value) {
        this.visible = value;
    }

    /**
     * Obtient la valeur de la propriété columnText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColumnText() {
        return columnText;
    }

    /**
     * Définit la valeur de la propriété columnText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColumnText(String value) {
        this.columnText = value;
    }

    /**
     * Obtient la valeur de la propriété binaryExpression.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBinaryExpression() {
        return binaryExpression;
    }

    /**
     * Définit la valeur de la propriété binaryExpression.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBinaryExpression(byte[] value) {
        this.binaryExpression = value;
    }

    /**
     * Obtient la valeur de la propriété expression.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Définit la valeur de la propriété expression.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpression(String value) {
        this.expression = value;
    }

    /**
     * Obtient la valeur de la propriété operator.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Définit la valeur de la propriété operator.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    /**
     * Obtient la valeur de la propriété secondColumnText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondColumnText() {
        return secondColumnText;
    }

    /**
     * Définit la valeur de la propriété secondColumnText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondColumnText(String value) {
        this.secondColumnText = value;
    }

    /**
     * Obtient la valeur de la propriété binarySecondExpression.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBinarySecondExpression() {
        return binarySecondExpression;
    }

    /**
     * Définit la valeur de la propriété binarySecondExpression.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBinarySecondExpression(byte[] value) {
        this.binarySecondExpression = value;
    }

    /**
     * Obtient la valeur de la propriété secondExpression.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondExpression() {
        return secondExpression;
    }

    /**
     * Définit la valeur de la propriété secondExpression.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondExpression(String value) {
        this.secondExpression = value;
    }

    /**
     * Obtient la valeur de la propriété expressionMethodURI.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpressionMethodURI() {
        return expressionMethodURI;
    }

    /**
     * Définit la valeur de la propriété expressionMethodURI.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpressionMethodURI(String value) {
        this.expressionMethodURI = value;
    }

    /**
     * Obtient la valeur de la propriété expressionClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpressionClassName() {
        return expressionClassName;
    }

    /**
     * Définit la valeur de la propriété expressionClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpressionClassName(String value) {
        this.expressionClassName = value;
    }

}
