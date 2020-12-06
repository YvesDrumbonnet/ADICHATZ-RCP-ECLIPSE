/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Tue Jul 07 12:30:02 CEST 2020
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.jpa.gencode.common.contextMenu;

import java.util.function.BooleanSupplier;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.action.ActionController;
import org.adichatz.engine.controller.action.SeparatorController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.MenuManagerController;
import org.adichatz.engine.controller.utils.ElementaryAccessibility;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.tabular.CopyTableToClipboardAction;
import org.adichatz.engine.tabular.FilterByValueActionController;
import org.adichatz.engine.tabular.HideColumnActionController;
import org.adichatz.engine.tabular.RefreshColumnSizeActionController;
import org.adichatz.engine.tabular.RemoveColumnFilterActionController;
import org.adichatz.engine.tabular.ShowColumnMenuManagerController;
import org.adichatz.jpa.action.AddColumnFilterActionController;
import org.adichatz.jpa.extra.ATabularContextMenuCore;
import org.adichatz.jpa.tabular.DisableAllFiltersActionController;
import org.adichatz.jpa.tabular.EnableAllFiltersActionController;
import org.adichatz.jpa.tabular.RemoveAllFiltersActionController;

@AdiResourceURI(URI="adi://org.adichatz.jpa/common.contextMenu/RefTextDialogCM")
public class RefTextDialogCM extends ATabularContextMenuCore {
	// the MenuManagerController headertMenuHMM.
	protected MenuManagerController headertMenuHMM;
	// the HideColumnActionController hideColumnActionACT.
	private HideColumnActionController hideColumnActionACT;
	// the ShowColumnMenuManagerController showColumnsMM.
	protected ShowColumnMenuManagerController showColumnsMM;
	// the RefreshColumnSizeActionController refreshColumnSizeACT.
	private RefreshColumnSizeActionController refreshColumnSizeACT;
	// the SeparatorController separatorWrapper$$1SPR.
	private SeparatorController separatorWrapper$$1SPR;
	// the AddColumnFilterActionController addColumnFilterActionACT.
	private AddColumnFilterActionController addColumnFilterActionACT;
	// the RemoveColumnFilterActionController removeColumnFilterActionACT.
	private RemoveColumnFilterActionController removeColumnFilterActionACT;
	// the MenuManagerController contextMenuMM.
	protected MenuManagerController contextMenuMM;
	// the FilterByValueActionController filterByValueActionACT.
	private FilterByValueActionController filterByValueActionACT;
	// the DisableAllFiltersActionController disableAllFiltersActionACT.
	private DisableAllFiltersActionController disableAllFiltersActionACT;
	// the EnableAllFiltersActionController enableAllFiltersActionACT.
	private EnableAllFiltersActionController enableAllFiltersActionACT;
	// the RemoveAllFiltersActionController removeAllFiltersActionACT.
	private RemoveAllFiltersActionController removeAllFiltersActionACT;
	// the SeparatorController separatorWrapper$$2SPR.
	private SeparatorController separatorWrapper$$2SPR;
	// the ActionController copyTableToClipboardActionACT.
	private ActionController copyTableToClipboardActionACT;
	/**
	 * Instantiates a new RefTextDialogCM.
	 * 
	 * This constructor could be used by Column parameter. (see org.adichatz.jpa)
	 * 
	 * @param parentContext
	 *            the parent context
	 * @param parentController
	 *            the parent controller
	 */
	public RefTextDialogCM(AdiContext parentContext, IContainerController parentController) {
		coreController = parentController;
	}

	/**
	 * Creates the include RefTextDialogCM.
	 *
	 * @param context
	 *            The context of the root controller
	 * @param parent controller
	 *            The collection parentController
	 * @param id
	 *            The include id
	 * @param parentContext
	 *            The parent context
	 */
	public RefTextDialogCM(ParamMap paramMap, IContainerController parentController, String id, AdiContext parentContext) {
		super(paramMap, parentController, id, parentContext);
		coreController = parentController;
	}

	/**
	 * creates contents
	 */
	public void createContents(){
		// Creates control for MenuManagerController headertMenuHMM
		headertMenuHMM = new MenuManagerController("headertMenu", coreController, this) {
			@Override
			public void createControl() {
				super.createControl();
				if (isValid()) {
					((ATabularController<?>) getMenuContainer()).setHeaderMenu(getControl().getMenu());
				}
			}
			@Override
			public void startLifeCycle() {
				super.startLifeCycle();
					// Creates control for HideColumnActionController hideColumnActionACT
					hideColumnActionACT = new HideColumnActionController("hideColumnAction", headertMenuHMM, RefTextDialogCM.this);
					// Creates control for ShowColumnMenuManagerController showColumnsMM
					showColumnsMM = new ShowColumnMenuManagerController("showColumns", headertMenuHMM, RefTextDialogCM.this);
					// Creates control for RefreshColumnSizeActionController refreshColumnSizeACT
					refreshColumnSizeACT = new RefreshColumnSizeActionController("refreshColumnSize", headertMenuHMM, RefTextDialogCM.this);
					// Creates control for SeparatorController separatorWrapper$$1SPR
					separatorWrapper$$1SPR = new SeparatorController("separatorWrapper$$1", headertMenuHMM, RefTextDialogCM.this);
					// Creates control for AddColumnFilterActionController addColumnFilterActionACT
					addColumnFilterActionACT = new AddColumnFilterActionController("addColumnFilterAction", headertMenuHMM, RefTextDialogCM.this) {
						@Override
						public void initialize() {
							super.initialize();
							BooleanSupplier evaluatorVALID;
							evaluatorVALID = new BooleanSupplier() {
								public boolean getAsBoolean() {
									return isColumnFilterable();
								}
							};
							addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID, evaluatorVALID));
						}
					};
					// Creates control for RemoveColumnFilterActionController removeColumnFilterActionACT
					removeColumnFilterActionACT = new RemoveColumnFilterActionController("RemoveColumnFilterAction", headertMenuHMM, RefTextDialogCM.this) {
						@Override
						public void initialize() {
							super.initialize();
							BooleanSupplier evaluatorVALID;
							evaluatorVALID = new BooleanSupplier() {
								public boolean getAsBoolean() {
									return hasFilterOnColumn();
								}
							};
							addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID, evaluatorVALID));
						}
					};
			}
		};
		// Creates control for MenuManagerController contextMenuMM
		contextMenuMM = new MenuManagerController("contextMenu", coreController, this) {
			@Override
			public void startLifeCycle() {
				super.startLifeCycle();
					// Creates control for FilterByValueActionController filterByValueActionACT
					filterByValueActionACT = new FilterByValueActionController("filterByValueAction", contextMenuMM, RefTextDialogCM.this) {
						@Override
						public void initialize() {
							super.initialize();
							BooleanSupplier evaluatorVALID;
							evaluatorVALID = new BooleanSupplier() {
								public boolean getAsBoolean() {
									return isFilterable();
								}
							};
							addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID, evaluatorVALID));
						}
					};
					// Creates control for DisableAllFiltersActionController disableAllFiltersActionACT
					disableAllFiltersActionACT = new DisableAllFiltersActionController("disableAllFiltersAction", contextMenuMM, RefTextDialogCM.this) {
						@Override
						public void initialize() {
							super.initialize();
							BooleanSupplier evaluatorVALID;
							evaluatorVALID = new BooleanSupplier() {
								public boolean getAsBoolean() {
									return hasEnabledFilter();
								}
							};
							addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID, evaluatorVALID));
						}
					};
					// Creates control for EnableAllFiltersActionController enableAllFiltersActionACT
					enableAllFiltersActionACT = new EnableAllFiltersActionController("enableAllFiltersAction", contextMenuMM, RefTextDialogCM.this) {
						@Override
						public void initialize() {
							super.initialize();
							BooleanSupplier evaluatorVALID;
							evaluatorVALID = new BooleanSupplier() {
								public boolean getAsBoolean() {
									return hasDisabledFilter();
								}
							};
							addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID, evaluatorVALID));
						}
					};
					// Creates control for RemoveAllFiltersActionController removeAllFiltersActionACT
					removeAllFiltersActionACT = new RemoveAllFiltersActionController("removeAllFiltersAction", contextMenuMM, RefTextDialogCM.this) {
						@Override
						public void initialize() {
							super.initialize();
							BooleanSupplier evaluatorVALID;
							evaluatorVALID = new BooleanSupplier() {
								public boolean getAsBoolean() {
									return hasFilter();
								}
							};
							addAccessibility(new ElementaryAccessibility(ElementaryAccessibility.ACCESS_ATTRIBUTE.VALID, evaluatorVALID));
						}
					};
					// Creates control for SeparatorController separatorWrapper$$2SPR
					separatorWrapper$$2SPR = new SeparatorController("separatorWrapper$$2", contextMenuMM, RefTextDialogCM.this);
					// Creates control for ActionController copyTableToClipboardActionACT
					copyTableToClipboardActionACT = new ActionController("copyTableToClipboardAction", contextMenuMM, RefTextDialogCM.this) {
						@Override
						public void initialize() {
							getParamMap().put("TABULAR_CONTROLLER", ((MenuManagerController) getFromRegister("contextMenu")).getMenuContainer());
							super.initialize();
						}
						@Override
						public void createControl() {
							if (isValid()) {
								action = new CopyTableToClipboardAction();
								action.setActionController(this);
							}
							super.createControl();
						}
					};
			}
		};
	}
}