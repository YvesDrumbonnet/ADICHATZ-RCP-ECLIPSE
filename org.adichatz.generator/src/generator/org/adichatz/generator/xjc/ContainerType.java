//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.06.26 à 05:05:47 PM CEST 
//


package org.adichatz.generator.xjc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour containerType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="containerType">
 *   &lt;complexContent>
 *     &lt;extension base="{}elementType">
 *       &lt;choice>
 *         &lt;element name="config" type="{}configType"/>
 *         &lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="formPage" type="{}formPageType"/>
 *           &lt;element name="scrolledForm" type="{}scrolledFormType"/>
 *           &lt;element name="section" type="{}sectionType"/>
 *           &lt;element name="composite" type="{}compositeType"/>
 *           &lt;element name="compositeBag" type="{}compositeBagType"/>
 *           &lt;element name="group" type="{}groupType"/>
 *           &lt;element name="pgroup" type="{}pGroupType"/>
 *           &lt;element name="sashForm" type="{}sashFormType"/>
 *           &lt;element name="scrolledComposite" type="{}scrolledCompositeType"/>
 *           &lt;element name="table" type="{}tableType"/>
 *           &lt;element name="grid" type="{}gridType"/>
 *           &lt;element name="cTabFolder" type="{}cTabFolderType"/>
 *           &lt;element name="argTabFolder" type="{}argTabFolderType"/>
 *           &lt;element name="pshelf" type="{}pShelfType"/>
 *           &lt;element name="argPShelf" type="{}argPShelfType"/>
 *           &lt;element name="include" type="{}includeType"/>
 *           &lt;element name="headerMenuManager" type="{}headerMenuManagerType"/>
 *           &lt;element name="menuManager" type="{}menuManagerType"/>
 *           &lt;element name="managedToolBar" type="{}managedToolBarType"/>
 *           &lt;element name="controlField" type="{}controlFieldType"/>
 *           &lt;element name="genericField" type="{}genericFieldType"/>
 *           &lt;element name="label" type="{}labelType"/>
 *           &lt;element name="button" type="{}buttonType"/>
 *           &lt;element name="ccombo" type="{}cComboType"/>
 *           &lt;element name="checkBox" type="{}checkBoxType"/>
 *           &lt;element name="combo" type="{}comboType"/>
 *           &lt;element name="compositeSeparator" type="{}compositeSeparatorType"/>
 *           &lt;element name="dateText" type="{}dateTextType"/>
 *           &lt;element name="dateTime" type="{}dateTimeType"/>
 *           &lt;element name="encryptedText" type="{}encryptedTextType"/>
 *           &lt;element name="editableFormText" type="{}editableFormTextType"/>
 *           &lt;element name="extraText" type="{}extraTextType"/>
 *           &lt;element name="fileText" type="{}fileTextType"/>
 *           &lt;element name="fontText" type="{}fontTextType"/>
 *           &lt;element name="formattedText" type="{}formattedTextType"/>
 *           &lt;element name="gMap" type="{}gMapType"/>
 *           &lt;element name="hyperlink" type="{}hyperlinkType"/>
 *           &lt;element name="helpButton" type="{}helpButtonType"/>
 *           &lt;element name="imageViewer" type="{}imageViewerType"/>
 *           &lt;element name="multiChoice" type="{}multiChoiceType"/>
 *           &lt;element name="numericText" type="{}numericTextType"/>
 *           &lt;element name="radioGroup" type="{}radioGroupType"/>
 *           &lt;element name="refText" type="{}refTextType"/>
 *           &lt;element name="rgbText" type="{}rgbTextType"/>
 *           &lt;element name="richText" type="{}richTextType"/>
 *           &lt;element name="starRating" type="{}starRatingType"/>
 *           &lt;element name="tableColumn" type="{}tableColumnType"/>
 *           &lt;element name="text" type="{}textType"/>
 *         &lt;/choice>
 *       &lt;/choice>
 *       &lt;attribute name="coreClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "containerType", propOrder = {
    "config",
    "additionalCode",
    "formPageOrScrolledFormOrSection"
})
@XmlSeeAlso({
    ContainerTree.class,
    IncludeTree.class
})
public class ContainerType
    extends ElementType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected ConfigType config;
    protected String additionalCode;
    @XmlElements({
        @XmlElement(name = "formPage", type = FormPageType.class),
        @XmlElement(name = "scrolledForm", type = ScrolledFormType.class),
        @XmlElement(name = "section", type = SectionType.class),
        @XmlElement(name = "composite", type = CompositeType.class),
        @XmlElement(name = "compositeBag", type = CompositeBagType.class),
        @XmlElement(name = "group", type = GroupType.class),
        @XmlElement(name = "pgroup", type = PGroupType.class),
        @XmlElement(name = "sashForm", type = SashFormType.class),
        @XmlElement(name = "scrolledComposite", type = ScrolledCompositeType.class),
        @XmlElement(name = "table", type = TableType.class),
        @XmlElement(name = "grid", type = GridType.class),
        @XmlElement(name = "cTabFolder", type = CTabFolderType.class),
        @XmlElement(name = "argTabFolder", type = ArgTabFolderType.class),
        @XmlElement(name = "pshelf", type = PShelfType.class),
        @XmlElement(name = "argPShelf", type = ArgPShelfType.class),
        @XmlElement(name = "include", type = IncludeType.class),
        @XmlElement(name = "headerMenuManager", type = HeaderMenuManagerType.class),
        @XmlElement(name = "menuManager", type = MenuManagerType.class),
        @XmlElement(name = "managedToolBar", type = ManagedToolBarType.class),
        @XmlElement(name = "controlField", type = ControlFieldType.class),
        @XmlElement(name = "genericField", type = GenericFieldType.class),
        @XmlElement(name = "label", type = LabelType.class),
        @XmlElement(name = "button", type = ButtonType.class),
        @XmlElement(name = "ccombo", type = CComboType.class),
        @XmlElement(name = "checkBox", type = CheckBoxType.class),
        @XmlElement(name = "combo", type = ComboType.class),
        @XmlElement(name = "compositeSeparator", type = CompositeSeparatorType.class),
        @XmlElement(name = "dateText", type = DateTextType.class),
        @XmlElement(name = "dateTime", type = DateTimeType.class),
        @XmlElement(name = "encryptedText", type = EncryptedTextType.class),
        @XmlElement(name = "editableFormText", type = EditableFormTextType.class),
        @XmlElement(name = "extraText", type = ExtraTextType.class),
        @XmlElement(name = "fileText", type = FileTextType.class),
        @XmlElement(name = "fontText", type = FontTextType.class),
        @XmlElement(name = "formattedText", type = FormattedTextType.class),
        @XmlElement(name = "gMap", type = GMapType.class),
        @XmlElement(name = "hyperlink", type = HyperlinkType.class),
        @XmlElement(name = "helpButton", type = HelpButtonType.class),
        @XmlElement(name = "imageViewer", type = ImageViewerType.class),
        @XmlElement(name = "multiChoice", type = MultiChoiceType.class),
        @XmlElement(name = "numericText", type = NumericTextType.class),
        @XmlElement(name = "radioGroup", type = RadioGroupType.class),
        @XmlElement(name = "refText", type = RefTextType.class),
        @XmlElement(name = "rgbText", type = RgbTextType.class),
        @XmlElement(name = "richText", type = RichTextType.class),
        @XmlElement(name = "starRating", type = StarRatingType.class),
        @XmlElement(name = "tableColumn", type = TableColumnType.class),
        @XmlElement(name = "text", type = TextType.class)
    })
    protected List<ValidElementType> formPageOrScrolledFormOrSection;
    @XmlAttribute(name = "coreClassName")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String coreClassName;
    @XmlAttribute(name = "entityURI")
    protected String entityURI;

    /**
     * Obtient la valeur de la propriété config.
     * 
     * @return
     *     possible object is
     *     {@link ConfigType }
     *     
     */
    public ConfigType getConfig() {
        return config;
    }

    /**
     * Définit la valeur de la propriété config.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigType }
     *     
     */
    public void setConfig(ConfigType value) {
        this.config = value;
    }

    /**
     * Obtient la valeur de la propriété additionalCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalCode() {
        return additionalCode;
    }

    /**
     * Définit la valeur de la propriété additionalCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalCode(String value) {
        this.additionalCode = value;
    }

    /**
     * Gets the value of the formPageOrScrolledFormOrSection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the formPageOrScrolledFormOrSection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormPageOrScrolledFormOrSection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FormPageType }
     * {@link ScrolledFormType }
     * {@link SectionType }
     * {@link CompositeType }
     * {@link CompositeBagType }
     * {@link GroupType }
     * {@link PGroupType }
     * {@link SashFormType }
     * {@link ScrolledCompositeType }
     * {@link TableType }
     * {@link GridType }
     * {@link CTabFolderType }
     * {@link ArgTabFolderType }
     * {@link PShelfType }
     * {@link ArgPShelfType }
     * {@link IncludeType }
     * {@link HeaderMenuManagerType }
     * {@link MenuManagerType }
     * {@link ManagedToolBarType }
     * {@link ControlFieldType }
     * {@link GenericFieldType }
     * {@link LabelType }
     * {@link ButtonType }
     * {@link CComboType }
     * {@link CheckBoxType }
     * {@link ComboType }
     * {@link CompositeSeparatorType }
     * {@link DateTextType }
     * {@link DateTimeType }
     * {@link EncryptedTextType }
     * {@link EditableFormTextType }
     * {@link ExtraTextType }
     * {@link FileTextType }
     * {@link FontTextType }
     * {@link FormattedTextType }
     * {@link GMapType }
     * {@link HyperlinkType }
     * {@link HelpButtonType }
     * {@link ImageViewerType }
     * {@link MultiChoiceType }
     * {@link NumericTextType }
     * {@link RadioGroupType }
     * {@link RefTextType }
     * {@link RgbTextType }
     * {@link RichTextType }
     * {@link StarRatingType }
     * {@link TableColumnType }
     * {@link TextType }
     * 
     * 
     */
    public List<ValidElementType> getFormPageOrScrolledFormOrSection() {
        if (formPageOrScrolledFormOrSection == null) {
            formPageOrScrolledFormOrSection = new ArrayList<ValidElementType>();
        }
        return this.formPageOrScrolledFormOrSection;
    }

    /**
     * Obtient la valeur de la propriété coreClassName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoreClassName() {
        return coreClassName;
    }

    /**
     * Définit la valeur de la propriété coreClassName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoreClassName(String value) {
        this.coreClassName = value;
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

}
