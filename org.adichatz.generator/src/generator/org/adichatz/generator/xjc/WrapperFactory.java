/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 * 
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 * 
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 * 
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.generator.xjc;

import org.adichatz.generator.wrapper.ActionWrapper;
import org.adichatz.generator.wrapper.ApplicationServerWrapper;
import org.adichatz.generator.wrapper.ArgPShelfWrapper;
import org.adichatz.generator.wrapper.ArgTabFolderWrapper;
import org.adichatz.generator.wrapper.ButtonBarWrapper;
import org.adichatz.generator.wrapper.ButtonWrapper;
import org.adichatz.generator.wrapper.CComboWrapper;
import org.adichatz.generator.wrapper.CTabFolderWrapper;
import org.adichatz.generator.wrapper.CTabItemWrapper;
import org.adichatz.generator.wrapper.CheckBoxWrapper;
import org.adichatz.generator.wrapper.ClientCanvasWrapper;
import org.adichatz.generator.wrapper.ColumnFieldWrapper;
import org.adichatz.generator.wrapper.ComboWrapper;
import org.adichatz.generator.wrapper.CompositeBagWrapper;
import org.adichatz.generator.wrapper.CompositeSeparatorWrapper;
import org.adichatz.generator.wrapper.CompositeWrapper;
import org.adichatz.generator.wrapper.ConfigWrapper;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.ContainerTreeWrapper;
import org.adichatz.generator.wrapper.ContributionItemWrapper;
import org.adichatz.generator.wrapper.ControlFieldWrapper;
import org.adichatz.generator.wrapper.CustomizationsWrapper;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.wrapper.DateTextWrapper;
import org.adichatz.generator.wrapper.DateTimeWrapper;
import org.adichatz.generator.wrapper.EditableFormTextWrapper;
import org.adichatz.generator.wrapper.EncryptedTextWrapper;
import org.adichatz.generator.wrapper.EntitySetWrapper;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.ExtendTreeWrapper;
import org.adichatz.generator.wrapper.ExtraTextWrapper;
import org.adichatz.generator.wrapper.FileTextWrapper;
import org.adichatz.generator.wrapper.FontTextWrapper;
import org.adichatz.generator.wrapper.FormPageWrapper;
import org.adichatz.generator.wrapper.FormattedTextWrapper;
import org.adichatz.generator.wrapper.GMapWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.GenerationUnitWrapper;
import org.adichatz.generator.wrapper.GenericFieldWrapper;
import org.adichatz.generator.wrapper.GridColumnGroupWrapper;
import org.adichatz.generator.wrapper.GridColumnWrapper;
import org.adichatz.generator.wrapper.GridWrapper;
import org.adichatz.generator.wrapper.GroupWrapper;
import org.adichatz.generator.wrapper.HeaderMenuManagerWrapper;
import org.adichatz.generator.wrapper.HelpButtonWrapper;
import org.adichatz.generator.wrapper.HyperlinkWrapper;
import org.adichatz.generator.wrapper.ImageViewerWrapper;
import org.adichatz.generator.wrapper.IncludeTreeWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.ItemWrapper;
import org.adichatz.generator.wrapper.JointureWrapper;
import org.adichatz.generator.wrapper.LabelWrapper;
import org.adichatz.generator.wrapper.ManagedToolBarWrapper;
import org.adichatz.generator.wrapper.MenuActionWrapper;
import org.adichatz.generator.wrapper.MenuItemWrapper;
import org.adichatz.generator.wrapper.MenuManagerWrapper;
import org.adichatz.generator.wrapper.MenuWrapper;
import org.adichatz.generator.wrapper.ModelPartWrapper;
import org.adichatz.generator.wrapper.MultiChoiceWrapper;
import org.adichatz.generator.wrapper.NavigatorTreeWrapper;
import org.adichatz.generator.wrapper.NumericTextWrapper;
import org.adichatz.generator.wrapper.PGroupMenuWrapper;
import org.adichatz.generator.wrapper.PGroupToolItemWrapper;
import org.adichatz.generator.wrapper.PGroupWrapper;
import org.adichatz.generator.wrapper.PShelfItemWrapper;
import org.adichatz.generator.wrapper.PShelfWrapper;
import org.adichatz.generator.wrapper.PartTreeWrapper;
import org.adichatz.generator.wrapper.PathElementWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.QueryPreferenceWrapper;
import org.adichatz.generator.wrapper.QueryTreeWrapper;
import org.adichatz.generator.wrapper.RadioGroupWrapper;
import org.adichatz.generator.wrapper.RefFieldWrapper;
import org.adichatz.generator.wrapper.RefTextWrapper;
import org.adichatz.generator.wrapper.RgbTextWrapper;
import org.adichatz.generator.wrapper.RichTextWrapper;
import org.adichatz.generator.wrapper.SashFormWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.wrapper.ScrolledCompositeWrapper;
import org.adichatz.generator.wrapper.ScrolledFormWrapper;
import org.adichatz.generator.wrapper.SectionWrapper;
import org.adichatz.generator.wrapper.SeparatorWrapper;
import org.adichatz.generator.wrapper.StarRatingWrapper;
import org.adichatz.generator.wrapper.TableColumnWrapper;
import org.adichatz.generator.wrapper.TableWrapper;
import org.adichatz.generator.wrapper.TabularWrapper;
import org.adichatz.generator.wrapper.TextWrapper;
import org.adichatz.generator.wrapper.ToolBarWrapper;
import org.adichatz.generator.wrapper.ToolItemWrapper;
import org.adichatz.generator.wrapper.TreeManagerTreeWrapper;
import org.adichatz.generator.wrapper.TreeWrapper;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Wrapper objects.
 */
public class WrapperFactory {

	/*
	 * actions
	 */

	/**
	 * Gets the menu manager type.
	 * 
	 * @return the menu manager type
	 */
	public static MenuManagerType getMenuManagerType() {
		return new MenuManagerWrapper();
	}

	/**
	 * Gets the header menu manager type.
	 * 
	 * @return the header menu manager type
	 */
	public static HeaderMenuManagerType getHeaderMenuManagerType() {
		return new HeaderMenuManagerWrapper();
	}

	/**
	 * Gets the tool bar type.
	 * 
	 * @return the tool bar type
	 */
	public static ToolBarType getToolBarType() {
		return new ToolBarWrapper();
	}

	/**
	 * Gets the managed tool bar type.
	 * 
	 * @return the managed tool bar type
	 */
	public static ManagedToolBarType getManagedToolBarType() {
		return new ManagedToolBarWrapper();
	}

	/**
	 * Gets the button bar type.
	 * 
	 * @return the button bar type
	 */
	public static ButtonBarType getButtonBarType() {
		return new ButtonBarWrapper();
	}

	/**
	 * Gets the separator type.
	 * 
	 * @return the separator type
	 */
	public static SeparatorType getSeparatorType() {
		return new SeparatorWrapper();
	}

	/**
	 * Gets the contribution item type.
	 * 
	 * @return the contribution item type
	 */
	public static ContributionItemType getContributionItemType() {
		return new ContributionItemWrapper();
	}

	/**
	 * Gets the generation scenario type.
	 * 
	 * @return the generation scenario type
	 */
	public static GenerationScenarioType getGenerationScenarioType() {
		return new GenerationScenarioWrapper();
	}

	/**
	 * Gets the generation unit type.
	 * 
	 * @return the generation unit type
	 */
	public static GenerationUnitType getGenerationUnitType() {
		return new GenerationUnitWrapper();
	}

	/**
	 * Gets the path element type.
	 * 
	 * @return the path element type
	 */
	public static PathElementType getPathElementType() {
		return new PathElementWrapper();
	}

	/*
	 * Trees
	 */

	/**
	 * Gets the scenario tree.
	 * 
	 * @return the scenario tree
	 */
	public static ScenarioTree getScenarioTree() {
		return new ScenarioTreeWrapper();
	}

	/**
	 * Gets the part tree.
	 * 
	 * @return the part tree
	 */
	public static PartTree getPartTree() {
		return new PartTreeWrapper();
	}

	/**
	 * Gets the include tree.
	 * 
	 * @return the include tree
	 */
	public static IncludeTree getIncludeTree() {
		return new IncludeTreeWrapper();
	}

	public static ExtendTree getExtendTree() {
		return new ExtendTreeWrapper();
	}

	/**
	 * Gets the container tree.
	 * 
	 * @return the container tree
	 */
	public static ContainerTree getContainerTree() {
		return new ContainerTreeWrapper();
	}

	/**
	 * Gets the connector tree.
	 * 
	 * @return the connector tree
	 */
	public static ConnectorTree getConnectorTree() {
		return new ConnectorTreeWrapper();
	}

	/**
	 * Gets the navigator tree.
	 * 
	 * @return the navigator tree
	 */
	public static NavigatorTree getNavigatorTree() {
		return new NavigatorTreeWrapper();
	}

	/**
	 * Gets the plugin entity type.
	 * 
	 * @return the plugin entity type
	 */
	public static PluginEntityType getPluginEntityType() {
		return new PluginEntityWrapper();
	}

	/**
	 * Gets the model part type.
	 *
	 * @return the model part type
	 */
	public static ModelPartType getModelPartType() {
		return new ModelPartWrapper();
	}

	/**
	 * Gets the query manager tree.
	 * 
	 * @return the query manager tree
	 */
	public static QueryTree getQueryTree() {
		return new QueryTreeWrapper();
	}

	/**
	 * Gets the tree manager tree.
	 * 
	 * @return the tree manager tree
	 */
	public static TreeManagerTree getTreeManagerTree() {
		return new TreeManagerTreeWrapper();
	}

	/**
	 * Gets the jointure type.
	 * 
	 * @return the jointure type
	 */
	public static JointureType getJointureType() {
		return new JointureWrapper();
	}

	/*
	 * Collections
	 */

	/**
	 * Gets the include type.
	 * 
	 * @return the include type
	 */
	public static ConfigType getConfigType() {
		return new ConfigWrapper();
	}

	/**
	 * Gets the customizations type.
	 * 
	 * @return the customizations type
	 */
	public static CustomizationsType getCustomizationsType() {
		return new CustomizationsWrapper();
	}

	/**
	 * Gets the include type.
	 * 
	 * @return the include type
	 */
	public static IncludeType getIncludeType() {
		return new IncludeWrapper();
	}

	/**
	 * Gets the form page type.
	 * 
	 * @return the form page type
	 */
	public static FormPageType getFormPageType() {
		return new FormPageWrapper();
	}

	/**
	 * Gets the section type.
	 * 
	 * @return the section type
	 */
	public static SectionType getSectionType() {
		return new SectionWrapper();
	}

	/**
	 * Gets the composite type.
	 * 
	 * @return the composite type
	 */
	public static CompositeType getCompositeType() {
		return new CompositeWrapper();
	}

	/**
	 * Gets the composite bag type.
	 * 
	 * @return the composite bag type
	 */
	public static CompositeBagType getCompositeBagType() {
		return new CompositeBagWrapper();
	}

	/**
	 * Gets the scrolled composite type.
	 * 
	 * @return the scrolled composite type
	 */
	public static ScrolledCompositeType getScrolledCompositeType() {
		return new ScrolledCompositeWrapper();
	}

	/**
	 * Gets the group type.
	 * 
	 * @return the group type
	 */
	public static GroupType getGroupType() {
		return new GroupWrapper();

	}

	/**
	 * Gets the p group type.
	 * 
	 * @return the p group type
	 */
	public static PGroupType getPGroupType() {
		return new PGroupWrapper();

	}

	/**
	 * Gets the p group tool item type.
	 * 
	 * @return the p group tool item type
	 */
	public static PGroupToolItemType getPGroupToolItemType() {
		return new PGroupToolItemWrapper();

	}

	/**
	 * Gets the p group menu type.
	 * 
	 * @return the p group menu type
	 */
	public static PGroupMenuType getPGroupMenuType() {
		return new PGroupMenuWrapper();

	}

	/**
	 * Gets the form type.
	 * 
	 * @return the form type
	 */
	public static ScrolledFormType getScrolledFormType() {
		return new ScrolledFormWrapper();

	}

	/**
	 * Gets the sash form type.
	 * 
	 * @return the sash form type
	 */
	public static SashFormType getSashFormType() {
		return new SashFormWrapper();

	}

	/**
	 * Gets the table type.
	 * 
	 * @return the table type
	 */
	public static TableType getTableType() {
		return new TableWrapper();
	}

	/**
	 * Gets the tabular type.
	 * 
	 * @return the tabular type
	 */
	public static TabularType getTabularType() {
		return new TabularWrapper();
	}

	/**
	 * Gets the grid type.
	 * 
	 * @return the grid type
	 */
	public static GridType getGridType() {
		return new GridWrapper();
	}

	/**
	 * Gets the tree type.
	 * 
	 * @return the tree type
	 */
	public static TreeType getTreeType() {
		return new TreeWrapper();
	}

	/**
	 * Gets the cTabFolder type.
	 * 
	 * @return the cTabFolder type
	 */
	public static CTabFolderType getCTabFolderType() {
		return new CTabFolderWrapper();
	}

	/**
	 * Gets the arg tab folder type.
	 * 
	 * @return the arg tab folder type
	 */
	public static ArgTabFolderType getArgTabFolderType() {
		return new ArgTabFolderWrapper();
	}

	/**
	 * Gets the cTabItem type.
	 * 
	 * @return the cTabItem type
	 */
	public static CTabItemType getCTabItemType() {
		return new CTabItemWrapper();
	}

	/**
	 * Gets the pshelf type.
	 * 
	 * @return the pshelf type
	 */
	public static PShelfType getPShelfType() {
		return new PShelfWrapper();
	}

	/**
	 * Gets the arg pshelf type.
	 * 
	 * @return the arg pshelf type
	 */
	public static ArgPShelfType getArgPShelfType() {
		return new ArgPShelfWrapper();
	}

	/**
	 * Gets the pshelf item type.
	 * 
	 * @return the pshelf item type
	 */
	public static PShelfItemType getPShelfItemType() {
		return new PShelfItemWrapper();
	}

	/**
	 * Gets the client canvas type.
	 *
	 * @return the client canvas type
	 */
	public static ClientCanvasType getClientCanvasType() {
		return new ClientCanvasWrapper();
	}

	/*
	 * Controls
	 */

	/**
	 * Gets the control field type.
	 * 
	 * @return the control field type
	 */
	public static ControlFieldType getControlFieldType() {
		return new ControlFieldWrapper();
	}

	/**
	 * Gets the generic field type.
	 * 
	 * @return the generic field type
	 */
	public static GenericFieldType getGenericFieldType() {
		return new GenericFieldWrapper();
	}

	/**
	 * Gets the column field type.
	 * 
	 * @return the column field type
	 */
	public static ColumnFieldType getColumnFieldType() {
		return new ColumnFieldWrapper();
	}

	/**
	 * Gets the radioGroup type.
	 * 
	 * @return the radioGroup type
	 */
	public static RadioGroupType getRadioGroupType() {
		return new RadioGroupWrapper();
	}

	/**
	 * Gets the ccombo type.
	 * 
	 * @return the ccombo type
	 */
	public static CComboType getCComboType() {
		return new CComboWrapper();
	}

	/**
	 * Gets the combo type.
	 * 
	 * @return the combo type
	 */
	public static ComboType getComboType() {
		return new ComboWrapper();
	}

	public static RichTextType getRichTextType() {
		return new RichTextWrapper();
	}

	/**
	 * Gets the ref text type.
	 * 
	 * @return the ref text type
	 */
	public static RefTextType getRefTextType() {
		return new RefTextWrapper();
	}

	/**
	 * Gets the rich text type.
	 * 
	 * @return the rich text type
	 */
	public static ExtraTextType getExtraTextType() {
		return new ExtraTextWrapper();
	}

	/**
	 * Gets the tool item type.
	 * 
	 * @return the tool item type
	 */
	public static ToolItemType getToolItemType() {
		return new ToolItemWrapper();
	}

	/**
	 * Gets the button type.
	 * 
	 * @return the button type
	 */
	public static ButtonType getButtonType() {
		return new ButtonWrapper();
	}

	/**
	 * Gets the help button type.
	 * 
	 * @return the help button type
	 */
	public static HelpButtonType getHelpButtonType() {
		return new HelpButtonWrapper();
	}

	/**
	 * Gets the hyperlink type.
	 * 
	 * @return the hyperlink type
	 */
	public static HyperlinkType getHyperlinkType() {
		return new HyperlinkWrapper();
	}

	/**
	 * Gets the check box type.
	 * 
	 * @return the check box type
	 */
	public static CheckBoxType getCheckBoxType() {
		return new CheckBoxWrapper();
	}

	/**
	 * Gets the date type.
	 * 
	 * @return the date type
	 */
	public static DateTextType getDateTextType() {
		return new DateTextWrapper();
	}

	/**
	 * Gets the date time type.
	 *
	 * @return the date time type
	 */
	public static DateTimeType getDateTimeType() {
		return new DateTimeWrapper();
	}

	/**
	 * Gets the file text type.
	 * 
	 * @return the file text type
	 */
	public static FileTextType getFileTextType() {
		return new FileTextWrapper();
	}

	/**
	 * Gets the editable form text type.
	 *
	 * @return the editable form text type
	 */
	public static EditableFormTextType getEditableFormTextType() {
		return new EditableFormTextWrapper();
	}

	/**
	 * Gets the numeric text type.
	 * 
	 * @return the numeric text type
	 */
	public static NumericTextType getNumericTextType() {
		return new NumericTextWrapper();
	}

	/**
	 * Gets the formatted text type.
	 * 
	 * @return the formatted text type
	 */
	public static FormattedTextType getFormattedTextType() {
		return new FormattedTextWrapper();
	}

	public static EncryptedTextType getEncryptedTextType() {
		return new EncryptedTextWrapper();
	}

	/**
	 * Gets the Font type.
	 * 
	 * @return the Font type
	 */
	public static FontTextType getFontTextType() {
		return new FontTextWrapper();
	}

	/**
	 * Gets the RGB type.
	 * 
	 * @return the RGB type
	 */
	public static RgbTextType getRgbTextType() {
		return new RgbTextWrapper();
	}

	/**
	 * Gets the text type.
	 * 
	 * @return the text type
	 */
	public static TextType getTextType() {
		return new TextWrapper();
	}

	/**
	 * Gets the gmap type.
	 * 
	 * @return the gmap type
	 */
	public static GMapType getgMapType() {
		return new GMapWrapper();
	}

	/**
	 * Gets the multiChoice type.
	 * 
	 * @return the multiChoice type
	 */
	public static MultiChoiceType getMultiChoiceType() {
		return new MultiChoiceWrapper();
	}

	/**
	 * Gets the starRating type.
	 * 
	 * @return the starRating type
	 */
	public static StarRatingType getStarRatingType() {
		return new StarRatingWrapper();
	}

	/**
	 * Gets the image viewer type.
	 * 
	 * @return the image viewer type
	 */
	public static ImageViewerType getImageViewerType() {
		return new ImageViewerWrapper();
	}

	/**
	 * Gets the composite separator type.
	 * 
	 * @return the composite separator type
	 */
	public static CompositeSeparatorType getCompositeSeparatorType() {
		return new CompositeSeparatorWrapper();
	}

	/**
	 * Gets the label type.
	 * 
	 * @return the label type
	 */
	public static LabelType getLabelType() {
		return new LabelWrapper();
	}

	/**
	 * Gets the table column type.
	 * 
	 * @return the table column type
	 */
	public static TableColumnType getTableColumnType() {
		return new TableColumnWrapper();
	}

	/**
	 * Gets the grid column type.
	 * 
	 * @return the grid column type
	 */
	public static GridColumnType getGridColumnType() {
		return new GridColumnWrapper();
	}

	/**
	 * Gets the grid column group type.
	 * 
	 * @return the grid column group type
	 */
	public static GridColumnGroupType getGridColumnGroupType() {
		return new GridColumnGroupWrapper();
	}

	/**
	 * Gets the action type.
	 * 
	 * @return the action type
	 */
	public static ActionType getActionType() {
		return new ActionWrapper();
	}

	/**
	 * Gets the menu action type.
	 * 
	 * @return the menu action type
	 */
	public static MenuActionType getMenuActionType() {
		return new MenuActionWrapper();
	}

	/**
	 * Gets the menu item type.
	 * 
	 * @return the menu item type
	 */
	public static MenuItemType getMenuItemType() {
		return new MenuItemWrapper();
	}

	/**
	 * Gets the item type.
	 * 
	 * @return the item type
	 */
	public static ItemType getItemType() {
		return new ItemWrapper();
	}

	/**
	 * Gets the menu type.
	 * 
	 * @return the menu type
	 */
	public static MenuType getMenuType() {
		return new MenuWrapper();
	}

	/**
	 * Gets the entity tree.
	 * 
	 * @return the entity tree
	 */
	public static EntityTree getEntityTree() {
		return new EntityTreeWrapper();
	}

	/**
	 * Gets the referenced field type.
	 * 
	 * @return the referenced field type
	 */
	public static RefFieldType getRefFieldType() {
		return new RefFieldWrapper();
	}

	/**
	 * Gets the entity set type.
	 * 
	 * @return the entity set type
	 */
	public static EntitySetType getEntitySetType() {
		return new EntitySetWrapper();
	}

	public static QueryPreferenceType getQueryPreferenceType() {
		return new QueryPreferenceWrapper();
	}

	/**
	 * Gets the datasource wrapper.
	 * 
	 * @return the datasource wrapper
	 */
	public static DatasourceType getDatasourceType() {
		return new DatasourceWrapper();
	}

	/**
	 * Gets the application server type.
	 * 
	 * @return the application server type
	 */
	public static ApplicationServerType getApplicationServerType() {
		return new ApplicationServerWrapper();
	}

}
