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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour fieldContainerType complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="fieldContainerType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{}lazyFetchContainerType"&amp;gt;
 *       &amp;lt;choice&amp;gt;
 *         &amp;lt;element name="layout" type="{}layoutType"/&amp;gt;
 *         &amp;lt;choice maxOccurs="unbounded" minOccurs="0"&amp;gt;
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
 *           &amp;lt;element name="tree" type="{}treeType"/&amp;gt;
 *           &amp;lt;element name="cTabFolder" type="{}cTabFolderType"/&amp;gt;
 *           &amp;lt;element name="argTabFolder" type="{}argTabFolderType"/&amp;gt;
 *           &amp;lt;element name="pshelf" type="{}pShelfType"/&amp;gt;
 *           &amp;lt;element name="argPShelf" type="{}argPShelfType"/&amp;gt;
 *           &amp;lt;element name="include" type="{}includeType"/&amp;gt;
 *           &amp;lt;element name="buttonBar" type="{}buttonBarType"/&amp;gt;
 *           &amp;lt;element name="toolBar" type="{}toolBarType"/&amp;gt;
 *           &amp;lt;element name="menuManager" type="{}menuManagerType"/&amp;gt;
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
 *           &amp;lt;element name="helpButton" type="{}helpButtonType"/&amp;gt;
 *           &amp;lt;element name="hyperlink" type="{}hyperlinkType"/&amp;gt;
 *           &amp;lt;element name="imageViewer" type="{}imageViewerType"/&amp;gt;
 *           &amp;lt;element name="multiChoice" type="{}multiChoiceType"/&amp;gt;
 *           &amp;lt;element name="numericText" type="{}numericTextType"/&amp;gt;
 *           &amp;lt;element name="radioGroup" type="{}radioGroupType"/&amp;gt;
 *           &amp;lt;element name="refText" type="{}refTextType"/&amp;gt;
 *           &amp;lt;element name="rgbText" type="{}rgbTextType"/&amp;gt;
 *           &amp;lt;element name="richText" type="{}richTextType"/&amp;gt;
 *           &amp;lt;element name="starRating" type="{}starRatingType"/&amp;gt;
 *           &amp;lt;element name="text" type="{}textType"/&amp;gt;
 *         &amp;lt;/choice&amp;gt;
 *       &amp;lt;/choice&amp;gt;
 *       &amp;lt;attribute name="entity" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="entityURI" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="backgroundImage" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="bounds" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="capture" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="focus" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="font" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="location" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="menu" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="redraw" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fieldContainerType", propOrder = {
    "layout",
    "scrolledFormOrSectionOrComposite"
})
@XmlSeeAlso({
    ArgTabFolderType.class,
    ArgPShelfType.class,
    ValidableContainerType.class,
    CTabItemType.class,
    PShelfItemType.class
})
public class FieldContainerType
    extends LazyFetchContainerType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected LayoutType layout;
    @XmlElements({
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
        @XmlElement(name = "tree", type = TreeType.class),
        @XmlElement(name = "cTabFolder", type = CTabFolderType.class),
        @XmlElement(name = "argTabFolder", type = ArgTabFolderType.class),
        @XmlElement(name = "pshelf", type = PShelfType.class),
        @XmlElement(name = "argPShelf", type = ArgPShelfType.class),
        @XmlElement(name = "include", type = IncludeType.class),
        @XmlElement(name = "buttonBar", type = ButtonBarType.class),
        @XmlElement(name = "toolBar", type = ToolBarType.class),
        @XmlElement(name = "menuManager", type = MenuManagerType.class),
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
        @XmlElement(name = "helpButton", type = HelpButtonType.class),
        @XmlElement(name = "hyperlink", type = HyperlinkType.class),
        @XmlElement(name = "imageViewer", type = ImageViewerType.class),
        @XmlElement(name = "multiChoice", type = MultiChoiceType.class),
        @XmlElement(name = "numericText", type = NumericTextType.class),
        @XmlElement(name = "radioGroup", type = RadioGroupType.class),
        @XmlElement(name = "refText", type = RefTextType.class),
        @XmlElement(name = "rgbText", type = RgbTextType.class),
        @XmlElement(name = "richText", type = RichTextType.class),
        @XmlElement(name = "starRating", type = StarRatingType.class),
        @XmlElement(name = "text", type = TextType.class)
    })
    protected List<ValidElementType> scrolledFormOrSectionOrComposite;
    @XmlAttribute(name = "entity")
    protected String entity;
    @XmlAttribute(name = "entityURI")
    protected String entityURI;
    @XmlAttribute(name = "backgroundImage")
    protected String backgroundImage;
    @XmlAttribute(name = "bounds")
    protected String bounds;
    @XmlAttribute(name = "capture")
    protected String capture;
    @XmlAttribute(name = "focus")
    protected String focus;
    @XmlAttribute(name = "font")
    protected String font;
    @XmlAttribute(name = "location")
    protected String location;
    @XmlAttribute(name = "menu")
    protected String menu;
    @XmlAttribute(name = "redraw")
    protected String redraw;
    @XmlAttribute(name = "size")
    protected String size;

    /**
     * Obtient la valeur de la propriété layout.
     * 
     * @return
     *     possible object is
     *     {@link LayoutType }
     *     
     */
    public LayoutType getLayout() {
        return layout;
    }

    /**
     * Définit la valeur de la propriété layout.
     * 
     * @param value
     *     allowed object is
     *     {@link LayoutType }
     *     
     */
    public void setLayout(LayoutType value) {
        this.layout = value;
    }

    /**
     * Gets the value of the scrolledFormOrSectionOrComposite property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the scrolledFormOrSectionOrComposite property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getScrolledFormOrSectionOrComposite().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
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
     * {@link TreeType }
     * {@link CTabFolderType }
     * {@link ArgTabFolderType }
     * {@link PShelfType }
     * {@link ArgPShelfType }
     * {@link IncludeType }
     * {@link ButtonBarType }
     * {@link ToolBarType }
     * {@link MenuManagerType }
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
     * {@link HelpButtonType }
     * {@link HyperlinkType }
     * {@link ImageViewerType }
     * {@link MultiChoiceType }
     * {@link NumericTextType }
     * {@link RadioGroupType }
     * {@link RefTextType }
     * {@link RgbTextType }
     * {@link RichTextType }
     * {@link StarRatingType }
     * {@link TextType }
     * 
     * 
     */
    public List<ValidElementType> getScrolledFormOrSectionOrComposite() {
        if (scrolledFormOrSectionOrComposite == null) {
            scrolledFormOrSectionOrComposite = new ArrayList<ValidElementType>();
        }
        return this.scrolledFormOrSectionOrComposite;
    }

    /**
     * Obtient la valeur de la propriété entity.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntity() {
        return entity;
    }

    /**
     * Définit la valeur de la propriété entity.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntity(String value) {
        this.entity = value;
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
     * Obtient la valeur de la propriété backgroundImage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Définit la valeur de la propriété backgroundImage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackgroundImage(String value) {
        this.backgroundImage = value;
    }

    /**
     * Obtient la valeur de la propriété bounds.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBounds() {
        return bounds;
    }

    /**
     * Définit la valeur de la propriété bounds.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBounds(String value) {
        this.bounds = value;
    }

    /**
     * Obtient la valeur de la propriété capture.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapture() {
        return capture;
    }

    /**
     * Définit la valeur de la propriété capture.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapture(String value) {
        this.capture = value;
    }

    /**
     * Obtient la valeur de la propriété focus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFocus() {
        return focus;
    }

    /**
     * Définit la valeur de la propriété focus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFocus(String value) {
        this.focus = value;
    }

    /**
     * Obtient la valeur de la propriété font.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFont() {
        return font;
    }

    /**
     * Définit la valeur de la propriété font.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFont(String value) {
        this.font = value;
    }

    /**
     * Obtient la valeur de la propriété location.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Définit la valeur de la propriété location.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Obtient la valeur de la propriété menu.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenu() {
        return menu;
    }

    /**
     * Définit la valeur de la propriété menu.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenu(String value) {
        this.menu = value;
    }

    /**
     * Obtient la valeur de la propriété redraw.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRedraw() {
        return redraw;
    }

    /**
     * Définit la valeur de la propriété redraw.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedraw(String value) {
        this.redraw = value;
    }

    /**
     * Obtient la valeur de la propriété size.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSize() {
        return size;
    }

    /**
     * Définit la valeur de la propriété size.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSize(String value) {
        this.size = value;
    }

}
