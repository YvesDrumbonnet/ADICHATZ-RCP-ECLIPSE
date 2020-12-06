//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2020.07.08 � 04:48:18 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour controlFieldType complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="controlFieldType">
 *   &lt;complexContent>
 *     &lt;extension base="{}controlType">
 *       &lt;choice>
 *         &lt;element name="validators" type="{}validatorsType"/>
 *         &lt;element name="initialValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="convertModelToTarget" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="convertTargetToModel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="menuManager" type="{}menuManagerType"/>
 *       &lt;/choice>
 *       &lt;attribute name="property" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="controlValueType" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="forceBinding" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="labelBackground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="labelFont" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="labelForeground" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="labelLayoutData" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="labelText" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lazyFetches" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="linkedControl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mandatory" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="noLabel" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="fieldBindManagerClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getControlFieldType", name = "controlFieldType", propOrder = {
    "validators",
    "initialValue",
    "convertModelToTarget",
    "convertTargetToModel",
    "menuManager"
})
@XmlSeeAlso({
    GenericFieldType.class,
    CheckBoxType.class,
    DateTextType.class,
    DateTimeType.class,
    EditableFormTextType.class,
    ExtraTextType.class,
    FileTextType.class,
    FontTextType.class,
    FormattedTextType.class,
    GMapType.class,
    HyperlinkType.class,
    ImageViewerType.class,
    NumericTextType.class,
    RgbTextType.class,
    RichTextType.class,
    StarRatingType.class,
    TextType.class,
    RefControlType.class
})
public class ControlFieldType
    extends ControlType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ValidatorsType validators;
    protected String initialValue;
    protected String convertModelToTarget;
    protected String convertTargetToModel;
    protected MenuManagerType menuManager;
    @XmlAttribute(name = "property")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String property;
    @XmlAttribute(name = "controlValueType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String controlValueType;
    @XmlAttribute(name = "forceBinding")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String forceBinding;
    @XmlAttribute(name = "labelBackground")
    protected String labelBackground;
    @XmlAttribute(name = "labelFont")
    protected String labelFont;
    @XmlAttribute(name = "labelForeground")
    protected String labelForeground;
    @XmlAttribute(name = "labelLayoutData")
    protected String labelLayoutData;
    @XmlAttribute(name = "labelText")
    protected String labelText;
    @XmlAttribute(name = "lazyFetches")
    protected String lazyFetches;
    @XmlAttribute(name = "linkedControl")
    protected String linkedControl;
    @XmlAttribute(name = "mandatory")
    protected Boolean mandatory;
    @XmlAttribute(name = "noLabel")
    protected Boolean noLabel;
    @XmlAttribute(name = "fieldBindManagerClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String fieldBindManagerClassName;

    /**
     * Obtient la valeur de la propri�t� validators.
     * 
     * @return
     *     possible object is
     *     {@link ValidatorsType }
     *     
     */
    public ValidatorsType getValidators() {
        return validators;
    }

    /**
     * D�finit la valeur de la propri�t� validators.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidatorsType }
     *     
     */
    public void setValidators(ValidatorsType value) {
        this.validators = value;
    }

    /**
     * Obtient la valeur de la propri�t� initialValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitialValue() {
        return initialValue;
    }

    /**
     * D�finit la valeur de la propri�t� initialValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitialValue(String value) {
        this.initialValue = value;
    }

    /**
     * Obtient la valeur de la propri�t� convertModelToTarget.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConvertModelToTarget() {
        return convertModelToTarget;
    }

    /**
     * D�finit la valeur de la propri�t� convertModelToTarget.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConvertModelToTarget(String value) {
        this.convertModelToTarget = value;
    }

    /**
     * Obtient la valeur de la propri�t� convertTargetToModel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConvertTargetToModel() {
        return convertTargetToModel;
    }

    /**
     * D�finit la valeur de la propri�t� convertTargetToModel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConvertTargetToModel(String value) {
        this.convertTargetToModel = value;
    }

    /**
     * Obtient la valeur de la propri�t� menuManager.
     * 
     * @return
     *     possible object is
     *     {@link MenuManagerType }
     *     
     */
    public MenuManagerType getMenuManager() {
        return menuManager;
    }

    /**
     * D�finit la valeur de la propri�t� menuManager.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuManagerType }
     *     
     */
    public void setMenuManager(MenuManagerType value) {
        this.menuManager = value;
    }

    /**
     * Obtient la valeur de la propri�t� property.
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
     * D�finit la valeur de la propri�t� property.
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
     * Obtient la valeur de la propri�t� controlValueType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlValueType() {
        return controlValueType;
    }

    /**
     * D�finit la valeur de la propri�t� controlValueType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlValueType(String value) {
        this.controlValueType = value;
    }

    /**
     * Obtient la valeur de la propri�t� forceBinding.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForceBinding() {
        return forceBinding;
    }

    /**
     * D�finit la valeur de la propri�t� forceBinding.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForceBinding(String value) {
        this.forceBinding = value;
    }

    /**
     * Obtient la valeur de la propri�t� labelBackground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelBackground() {
        return labelBackground;
    }

    /**
     * D�finit la valeur de la propri�t� labelBackground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelBackground(String value) {
        this.labelBackground = value;
    }

    /**
     * Obtient la valeur de la propri�t� labelFont.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelFont() {
        return labelFont;
    }

    /**
     * D�finit la valeur de la propri�t� labelFont.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelFont(String value) {
        this.labelFont = value;
    }

    /**
     * Obtient la valeur de la propri�t� labelForeground.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelForeground() {
        return labelForeground;
    }

    /**
     * D�finit la valeur de la propri�t� labelForeground.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelForeground(String value) {
        this.labelForeground = value;
    }

    /**
     * Obtient la valeur de la propri�t� labelLayoutData.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelLayoutData() {
        return labelLayoutData;
    }

    /**
     * D�finit la valeur de la propri�t� labelLayoutData.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelLayoutData(String value) {
        this.labelLayoutData = value;
    }

    /**
     * Obtient la valeur de la propri�t� labelText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelText() {
        return labelText;
    }

    /**
     * D�finit la valeur de la propri�t� labelText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelText(String value) {
        this.labelText = value;
    }

    /**
     * Obtient la valeur de la propri�t� lazyFetches.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLazyFetches() {
        return lazyFetches;
    }

    /**
     * D�finit la valeur de la propri�t� lazyFetches.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLazyFetches(String value) {
        this.lazyFetches = value;
    }

    /**
     * Obtient la valeur de la propri�t� linkedControl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkedControl() {
        return linkedControl;
    }

    /**
     * D�finit la valeur de la propri�t� linkedControl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkedControl(String value) {
        this.linkedControl = value;
    }

    /**
     * Obtient la valeur de la propri�t� mandatory.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMandatory() {
        return mandatory;
    }

    /**
     * D�finit la valeur de la propri�t� mandatory.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMandatory(Boolean value) {
        this.mandatory = value;
    }

    /**
     * Obtient la valeur de la propri�t� noLabel.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoLabel() {
        return noLabel;
    }

    /**
     * D�finit la valeur de la propri�t� noLabel.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoLabel(Boolean value) {
        this.noLabel = value;
    }

    /**
     * Obtient la valeur de la propri�t� fieldBindManagerClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldBindManagerClassName() {
        return fieldBindManagerClassName;
    }

    /**
     * D�finit la valeur de la propri�t� fieldBindManagerClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldBindManagerClassName(String value) {
        this.fieldBindManagerClassName = value;
    }

}
