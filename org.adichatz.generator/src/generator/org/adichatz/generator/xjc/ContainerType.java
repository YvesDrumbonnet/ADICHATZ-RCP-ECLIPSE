//
// Ce fichier a été généré par Eclipse Implementation of JAXB, v2.3.3 
// Voir https://eclipse-ee4j.github.io/jaxb-ri 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.25 à 05:19:29 PM CEST 
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
 * &lt;p&gt;Classe Java pour containerType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="containerType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}elementType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="config" type="{}configType"/&amp;gt;
 *         &amp;lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 *         &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *           &amp;lt;element name="formPage" type="{}formPageType"/&amp;gt;
 *           &amp;lt;element name="scrolledForm" type="{}scrolledFormType"/&amp;gt;
 *           &amp;lt;element name="section" type="{}sectionType"/&amp;gt;
 *           &amp;lt;element name="composite" type="{}compositeType"/&amp;gt;
 *           &amp;lt;element name="compositeBag" type="{}compositeBagType"/&amp;gt;
 *           &amp;lt;element name="group" type="{}groupType"/&amp;gt;
 *           &amp;lt;element name="pgroup" type="{}pGroupType"/&amp;gt;
 *           &amp;lt;element name="sashForm" type="{}sashFormType"/&amp;gt;
 *           &amp;lt;element name="scrolledComposite" type="{}scrolledCompositeType"/&amp;gt;
 *           &amp;lt;element name="table" type="{}tableType"/&amp;gt;
 *           &amp;lt;element name="grid" type="{}gridType"/&amp;gt;
 *           &amp;lt;element name="cTabFolder" type="{}cTabFolderType"/&amp;gt;
 *           &amp;lt;element name="argTabFolder" type="{}argTabFolderType"/&amp;gt;
 *           &amp;lt;element name="pshelf" type="{}pShelfType"/&amp;gt;
 *           &amp;lt;element name="argPShelf" type="{}argPShelfType"/&amp;gt;
 *           &amp;lt;element name="include" type="{}includeType"/&amp;gt;
 *           &amp;lt;element name="headerMenuManager" type="{}headerMenuManagerType"/&amp;gt;
 *           &amp;lt;element name="menuManager" type="{}menuManagerType"/&amp;gt;
 *           &amp;lt;element name="managedToolBar" type="{}managedToolBarType"/&amp;gt;
 *           &amp;lt;element name="controlField" type="{}controlFieldType"/&amp;gt;
 *           &amp;lt;element name="genericField" type="{}genericFieldType"/&amp;gt;
 *           &amp;lt;element name="label" type="{}labelType"/&amp;gt;
 *           &amp;lt;element name="button" type="{}buttonType"/&amp;gt;
 *           &amp;lt;element name="ccombo" type="{}cComboType"/&amp;gt;
 *           &amp;lt;element name="checkBox" type="{}checkBoxType"/&amp;gt;
 *           &amp;lt;element name="combo" type="{}comboType"/&amp;gt;
 *           &amp;lt;element name="compositeSeparator" type="{}compositeSeparatorType"/&amp;gt;
 *           &amp;lt;element name="dateText" type="{}dateTextType"/&amp;gt;
 *           &amp;lt;element name="dateTime" type="{}dateTimeType"/&amp;gt;
 *           &amp;lt;element name="encryptedText" type="{}encryptedTextType"/&amp;gt;
 *           &amp;lt;element name="editableFormText" type="{}editableFormTextType"/&amp;gt;
 *           &amp;lt;element name="extraText" type="{}extraTextType"/&amp;gt;
 *           &amp;lt;element name="fileText" type="{}fileTextType"/&amp;gt;
 *           &amp;lt;element name="fontText" type="{}fontTextType"/&amp;gt;
 *           &amp;lt;element name="formattedText" type="{}formattedTextType"/&amp;gt;
 *           &amp;lt;element name="gMap" type="{}gMapType"/&amp;gt;
 *           &amp;lt;element name="hyperlink" type="{}hyperlinkType"/&amp;gt;
 *           &amp;lt;element name="helpButton" type="{}helpButtonType"/&amp;gt;
 *           &amp;lt;element name="imageViewer" type="{}imageViewerType"/&amp;gt;
 *           &amp;lt;element name="multiChoice" type="{}multiChoiceType"/&amp;gt;
 *           &amp;lt;element name="numericText" type="{}numericTextType"/&amp;gt;
 *           &amp;lt;element name="radioGroup" type="{}radioGroupType"/&amp;gt;
 *           &amp;lt;element name="refText" type="{}refTextType"/&amp;gt;
 *           &amp;lt;element name="rgbText" type="{}rgbTextType"/&amp;gt;
 *           &amp;lt;element name="richText" type="{}richTextType"/&amp;gt;
 *           &amp;lt;element name="starRating" type="{}starRatingType"/&amp;gt;
 *           &amp;lt;element name="tableColumn" type="{}tableColumnType"/&amp;gt;
 *           &amp;lt;element name="text" type="{}textType"/&amp;gt;
 *         &amp;lt;/choice&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="coreClassName" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
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
    IncludeTree.class,
    ContainerTree.class
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
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the formPageOrScrolledFormOrSection property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFormPageOrScrolledFormOrSection().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
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
