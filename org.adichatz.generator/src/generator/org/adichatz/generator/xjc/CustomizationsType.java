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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour customizationsType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="customizationsType">
 *   &lt;complexContent>
 *     &lt;extension base="{}paramType">
 *       &lt;choice>
 *         &lt;element name="additionalCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="params" type="{}paramsType"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="scrolledForm" type="{}scrolledFormType"/>
 *           &lt;element name="clientCanvas" type="{}clientCanvasType"/>
 *           &lt;element name="section" type="{}sectionType"/>
 *           &lt;element name="composite" type="{}compositeType"/>
 *           &lt;element name="compositeBag" type="{}compositeBagType"/>
 *           &lt;element name="group" type="{}groupType"/>
 *           &lt;element name="pgroup" type="{}pGroupType"/>
 *           &lt;element name="sashForm" type="{}sashFormType"/>
 *           &lt;element name="scrolledComposite" type="{}scrolledCompositeType"/>
 *           &lt;element name="table" type="{}tableType"/>
 *           &lt;element name="tabular" type="{}tabularType"/>
 *           &lt;element name="grid" type="{}gridType"/>
 *           &lt;element name="tree" type="{}treeType"/>
 *           &lt;element name="cTabFolder" type="{}cTabFolderType"/>
 *           &lt;element name="argTabFolder" type="{}argTabFolderType"/>
 *           &lt;element name="pshelf" type="{}pShelfType"/>
 *           &lt;element name="argPShelf" type="{}argPShelfType"/>
 *           &lt;element name="include" type="{}includeType"/>
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
 *           &lt;element name="editableFormText" type="{}editableFormTextType"/>
 *           &lt;element name="encryptedText" type="{}encryptedTextType"/>
 *           &lt;element name="extraText" type="{}extraTextType"/>
 *           &lt;element name="fileText" type="{}fileTextType"/>
 *           &lt;element name="fontText" type="{}fontTextType"/>
 *           &lt;element name="formattedText" type="{}formattedTextType"/>
 *           &lt;element name="gMap" type="{}gMapType"/>
 *           &lt;element name="helpButton" type="{}helpButtonType"/>
 *           &lt;element name="hyperlink" type="{}hyperlinkType"/>
 *           &lt;element name="imageViewer" type="{}imageViewerType"/>
 *           &lt;element name="multiChoice" type="{}multiChoiceType"/>
 *           &lt;element name="numericText" type="{}numericTextType"/>
 *           &lt;element name="radioGroup" type="{}radioGroupType"/>
 *           &lt;element name="refText" type="{}refTextType"/>
 *           &lt;element name="rgbText" type="{}rgbTextType"/>
 *           &lt;element name="richText" type="{}richTextType"/>
 *           &lt;element name="starRating" type="{}starRatingType"/>
 *           &lt;element name="text" type="{}textType"/>
 *           &lt;element name="columnField" type="{}columnFieldType"/>
 *           &lt;element name="gridColumn" type="{}gridColumnType"/>
 *           &lt;element name="tableColumn" type="{}tableColumnType"/>
 *           &lt;element name="managedToolBar" type="{}managedToolBarType"/>
 *           &lt;element name="action" type="{}actionType"/>
 *           &lt;element name="buttonBar" type="{}buttonBarType"/>
 *           &lt;element name="toolBarBar" type="{}toolBarType"/>
 *           &lt;element name="toolItem" type="{}toolItemType"/>
 *           &lt;element name="pgroupToolItem" type="{}pGroupToolItemType"/>
 *         &lt;/choice>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(factoryClass=WrapperFactory.class, factoryMethod="getCustomizationsType", name = "customizationsType", propOrder = {
    "additionalCode",
    "params",
    "scrolledFormOrClientCanvasOrSection"
})
public class CustomizationsType
    extends ParamType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected String additionalCode;
    protected ParamsType params;
    @XmlElements({
        @XmlElement(name = "scrolledForm", type = ScrolledFormType.class),
        @XmlElement(name = "clientCanvas", type = ClientCanvasType.class),
        @XmlElement(name = "section", type = SectionType.class),
        @XmlElement(name = "composite", type = CompositeType.class),
        @XmlElement(name = "compositeBag", type = CompositeBagType.class),
        @XmlElement(name = "group", type = GroupType.class),
        @XmlElement(name = "pgroup", type = PGroupType.class),
        @XmlElement(name = "sashForm", type = SashFormType.class),
        @XmlElement(name = "scrolledComposite", type = ScrolledCompositeType.class),
        @XmlElement(name = "table", type = TableType.class),
        @XmlElement(name = "tabular", type = TabularType.class),
        @XmlElement(name = "grid", type = GridType.class),
        @XmlElement(name = "tree", type = TreeType.class),
        @XmlElement(name = "cTabFolder", type = CTabFolderType.class),
        @XmlElement(name = "argTabFolder", type = ArgTabFolderType.class),
        @XmlElement(name = "pshelf", type = PShelfType.class),
        @XmlElement(name = "argPShelf", type = ArgPShelfType.class),
        @XmlElement(name = "include", type = IncludeType.class),
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
        @XmlElement(name = "editableFormText", type = EditableFormTextType.class),
        @XmlElement(name = "encryptedText", type = EncryptedTextType.class),
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
        @XmlElement(name = "text", type = TextType.class),
        @XmlElement(name = "columnField", type = ColumnFieldType.class),
        @XmlElement(name = "gridColumn", type = GridColumnType.class),
        @XmlElement(name = "tableColumn", type = TableColumnType.class),
        @XmlElement(name = "managedToolBar", type = ManagedToolBarType.class),
        @XmlElement(name = "action", type = ActionType.class),
        @XmlElement(name = "buttonBar", type = ButtonBarType.class),
        @XmlElement(name = "toolBarBar", type = ToolBarType.class),
        @XmlElement(name = "toolItem", type = ToolItemType.class),
        @XmlElement(name = "pgroupToolItem", type = PGroupToolItemType.class)
    })
    protected List<ValidElementType> scrolledFormOrClientCanvasOrSection;

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
     * Obtient la valeur de la propriété params.
     * 
     * @return
     *     possible object is
     *     {@link ParamsType }
     *     
     */
    public ParamsType getParams() {
        return params;
    }

    /**
     * Définit la valeur de la propriété params.
     * 
     * @param value
     *     allowed object is
     *     {@link ParamsType }
     *     
     */
    public void setParams(ParamsType value) {
        this.params = value;
    }

    /**
     * Gets the value of the scrolledFormOrClientCanvasOrSection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scrolledFormOrClientCanvasOrSection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScrolledFormOrClientCanvasOrSection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScrolledFormType }
     * {@link ClientCanvasType }
     * {@link SectionType }
     * {@link CompositeType }
     * {@link CompositeBagType }
     * {@link GroupType }
     * {@link PGroupType }
     * {@link SashFormType }
     * {@link ScrolledCompositeType }
     * {@link TableType }
     * {@link TabularType }
     * {@link GridType }
     * {@link TreeType }
     * {@link CTabFolderType }
     * {@link ArgTabFolderType }
     * {@link PShelfType }
     * {@link ArgPShelfType }
     * {@link IncludeType }
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
     * {@link EditableFormTextType }
     * {@link EncryptedTextType }
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
     * {@link ColumnFieldType }
     * {@link GridColumnType }
     * {@link TableColumnType }
     * {@link ManagedToolBarType }
     * {@link ActionType }
     * {@link ButtonBarType }
     * {@link ToolBarType }
     * {@link ToolItemType }
     * {@link PGroupToolItemType }
     * 
     * 
     */
    public List<ValidElementType> getScrolledFormOrClientCanvasOrSection() {
        if (scrolledFormOrClientCanvasOrSection == null) {
            scrolledFormOrClientCanvasOrSection = new ArrayList<ValidElementType>();
        }
        return this.scrolledFormOrClientCanvasOrSection;
    }

}
