package org.adichatz.generator.tools;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.generator.wrapper.internal.IControlWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.xjc.ActionType;
import org.adichatz.generator.xjc.ArgPShelfType;
import org.adichatz.generator.xjc.ArgTabFolderType;
import org.adichatz.generator.xjc.ButtonType;
import org.adichatz.generator.xjc.CTabFolderType;
import org.adichatz.generator.xjc.CheckBoxType;
import org.adichatz.generator.xjc.CollectionType;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.adichatz.generator.xjc.ComboType;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.DateTextType;
import org.adichatz.generator.xjc.EditableFormTextType;
import org.adichatz.generator.xjc.ExtraTextType;
import org.adichatz.generator.xjc.FieldContainerType;
import org.adichatz.generator.xjc.GMapType;
import org.adichatz.generator.xjc.HyperlinkType;
import org.adichatz.generator.xjc.ImageViewerType;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.generator.xjc.MenuItemType;
import org.adichatz.generator.xjc.NumericTextType;
import org.adichatz.generator.xjc.PGroupToolItemType;
import org.adichatz.generator.xjc.PShelfType;
import org.adichatz.generator.xjc.RadioGroupType;
import org.adichatz.generator.xjc.RefTextType;
import org.adichatz.generator.xjc.SetType;
import org.adichatz.generator.xjc.TextType;
import org.adichatz.generator.xjc.WidgetType;

public abstract class AListenerTypeManager {
	public final static int CONTROL = 1 << 0;

	public final static int LIFE_CYCLE = 1 << 1;

	public final static int ENTITY = 1 << 2;

	public final static int DATABINDING = 1 << 3;

	public final static int BEFORE_ENTITY_INJECTION = 1 << 4;

	public final static int AFTER_ENTITY_INJECTION = 1 << 5;

	public final static int RUNNABLE = 1 << 6;

	public static int AFTER_CREATE_CONTROL_GROUP = AListenerTypeManager.RUNNABLE | AListenerTypeManager.DATABINDING;

	public static int AFTER_END_LIFE_CYCLE_GROUP = AListenerTypeManager.CONTROL | AListenerTypeManager.ENTITY;

	public static Map<ListenerTypeEnum, AListenerTypeManager> LISTENER_MANAGER_MAP = new HashMap<ListenerTypeEnum, AListenerTypeManager>();

	static {

		/** ENTITY */
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.CHANGE_STATUS, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.LOCK_ENTITY, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.BEFORE_PROPERTY_CHANGE, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.WHEN_PROPERTY_CHANGE, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_PROPERTY_CHANGE, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.PRE_SAVE, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.POST_SAVE, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.PRE_REFRESH, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.POST_REFRESH, new AListenerTypeManager(ENTITY) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});

		/** DATABINDING */
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.PRE_SAVE_ENTITIES, new AListenerTypeManager(DATABINDING) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType || element instanceof ControlFieldType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.POST_SAVE_ENTITIES, new AListenerTypeManager(DATABINDING) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType || element instanceof ControlFieldType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.PRE_REFRESH_ENTITIES, new AListenerTypeManager(DATABINDING) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType || element instanceof ControlFieldType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.POST_REFRESH_ENTITIES, new AListenerTypeManager(DATABINDING) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType || element instanceof ControlFieldType;
			}
		});

		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.ADD_ENTITY, new AListenerTypeManager(DATABINDING) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType || element instanceof ControlFieldType;
			}
		});

		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.REMOVE_ENTITY, new AListenerTypeManager(DATABINDING) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType || element instanceof ControlFieldType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.POST_MESSAGE, new AListenerTypeManager(DATABINDING) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});

		/** LIFE CYCLE */
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_INITIALIZE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CollectionType || element instanceof ColumnFieldType || element instanceof WidgetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.BEFORE_CREATE_CONTROL, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CollectionType || element instanceof ColumnFieldType || element instanceof WidgetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_CREATE_CONTROL, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CollectionType || element instanceof ColumnFieldType || element instanceof WidgetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.BEFORE_SYNCHRONIZE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType || element instanceof IControlWrapper;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_SYNCHRONIZE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType || element instanceof IControlWrapper;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.BEFORE_END_LIFE_CYCLE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CollectionType || element instanceof ColumnFieldType || element instanceof WidgetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_END_LIFE_CYCLE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CollectionType || element instanceof ColumnFieldType || element instanceof WidgetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.BEFORE_DISPOSE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CollectionType || element instanceof ColumnFieldType || element instanceof WidgetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_DISPOSE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CollectionType || element instanceof ColumnFieldType || element instanceof WidgetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.POST_CREATE_PART, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CollectionType || element instanceof ColumnFieldType || element instanceof WidgetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.BEFORE_FIELD_CHANGE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof IControlWrapper;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_FIELD_CHANGE, new AListenerTypeManager(LIFE_CYCLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof IControlWrapper;
			}
		});

		/** ENTITY_INJECTION */
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.BEFORE_ENTITY_INJECTION, new AListenerTypeManager(BEFORE_ENTITY_INJECTION) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_ENTITY_INJECTION, new AListenerTypeManager(AFTER_ENTITY_INJECTION) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof FieldContainerType;
			}
		});

		/** RUNNABLE */
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.PRE_RUN, new AListenerTypeManager(RUNNABLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof ActionType || element instanceof HyperlinkType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.POST_RUN, new AListenerTypeManager(RUNNABLE) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof ActionType || element instanceof HyperlinkType;
			}
		});

		/** CONTROL */
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.MODIFY_TEXT, new AListenerTypeManager(CONTROL) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof DateTextType || element instanceof EditableFormTextType
						|| element instanceof ImageViewerType || element instanceof NumericTextType
						|| element instanceof RefTextType || element instanceof TextType || element instanceof ExtraTextType
						|| element instanceof GMapType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.CHECK_STATE, new AListenerTypeManager(CONTROL) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof SetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.SELECTION_CHANGED, new AListenerTypeManager(CONTROL) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof SetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.POST_SELECTION_CHANGED, new AListenerTypeManager(CONTROL) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof SetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.WIDGET_SELECTED, new AListenerTypeManager(CONTROL) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof CTabFolderType || element instanceof ArgTabFolderType || element instanceof ComboType
						|| element instanceof CheckBoxType || element instanceof ButtonType || element instanceof RadioGroupType
						|| element instanceof PShelfType || element instanceof ArgPShelfType || element instanceof MenuItemType
						|| element instanceof PGroupToolItemType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.DOUBLE_CLICK, new AListenerTypeManager(CONTROL) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof SetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.BEFORE_REFRESH, new AListenerTypeManager(CONTROL) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof SetType;
			}
		});
		LISTENER_MANAGER_MAP.put(ListenerTypeEnum.AFTER_REFRESH, new AListenerTypeManager(CONTROL) {
			public boolean isEligible(IElementWrapper element) {
				return element instanceof SetType;
			}
		});

	}

	public static Map<String, ListenerTypeEnum> LISTENER_TYPE_MAP = new HashMap<String, ListenerTypeEnum>();

	static {
		/** ENTITY */
		LISTENER_TYPE_MAP.put("CHANGE_STATUS", ListenerTypeEnum.CHANGE_STATUS);
		LISTENER_TYPE_MAP.put("LOCK_ENTITY", ListenerTypeEnum.LOCK_ENTITY);
		LISTENER_TYPE_MAP.put("BEFORE_PROPERTY_CHANGE", ListenerTypeEnum.BEFORE_PROPERTY_CHANGE);
		LISTENER_TYPE_MAP.put("WHEN_PROPERTY_CHANGE", ListenerTypeEnum.WHEN_PROPERTY_CHANGE);
		LISTENER_TYPE_MAP.put("AFTER_PROPERTY_CHANGE", ListenerTypeEnum.AFTER_PROPERTY_CHANGE);
		LISTENER_TYPE_MAP.put("PRE_SAVE", ListenerTypeEnum.PRE_SAVE);
		LISTENER_TYPE_MAP.put("POST_SAVE", ListenerTypeEnum.POST_SAVE);
		LISTENER_TYPE_MAP.put("PRE_REFRESH", ListenerTypeEnum.PRE_REFRESH);
		LISTENER_TYPE_MAP.put("POST_REFRESH", ListenerTypeEnum.POST_REFRESH);

		/** DATABINDING */
		LISTENER_TYPE_MAP.put("PRE_SAVE_ENTITIES", ListenerTypeEnum.PRE_SAVE_ENTITIES);
		LISTENER_TYPE_MAP.put("POST_SAVE_ENTITIES", ListenerTypeEnum.POST_SAVE_ENTITIES);
		LISTENER_TYPE_MAP.put("PRE_REFRESH_ENTITIES", ListenerTypeEnum.PRE_REFRESH_ENTITIES);
		LISTENER_TYPE_MAP.put("POST_REFRESH_ENTITIES", ListenerTypeEnum.POST_REFRESH_ENTITIES);
		LISTENER_TYPE_MAP.put("ADD_ENTITY", ListenerTypeEnum.ADD_ENTITY);
		LISTENER_TYPE_MAP.put("REMOVE_ENTITY", ListenerTypeEnum.REMOVE_ENTITY);
		LISTENER_TYPE_MAP.put("POST_MESSAGE", ListenerTypeEnum.POST_MESSAGE);

		/** LIFE CYCLE */
		LISTENER_TYPE_MAP.put("AFTER_INITIALIZE", ListenerTypeEnum.AFTER_INITIALIZE);
		LISTENER_TYPE_MAP.put("BEFORE_CREATE_CONTROL", ListenerTypeEnum.BEFORE_CREATE_CONTROL);
		LISTENER_TYPE_MAP.put("AFTER_CREATE_CONTROL", ListenerTypeEnum.AFTER_CREATE_CONTROL);
		LISTENER_TYPE_MAP.put("BEFORE_SYNCHRONIZE", ListenerTypeEnum.BEFORE_SYNCHRONIZE);
		LISTENER_TYPE_MAP.put("AFTER_SYNCHRONIZE", ListenerTypeEnum.AFTER_SYNCHRONIZE);
		LISTENER_TYPE_MAP.put("BEFORE_END_LIFE_CYCLE", ListenerTypeEnum.BEFORE_END_LIFE_CYCLE);
		LISTENER_TYPE_MAP.put("AFTER_END_LIFE_CYCLE", ListenerTypeEnum.AFTER_END_LIFE_CYCLE);
		LISTENER_TYPE_MAP.put("BEFORE_DISPOSE", ListenerTypeEnum.BEFORE_DISPOSE);
		LISTENER_TYPE_MAP.put("AFTER_DISPOSE", ListenerTypeEnum.AFTER_DISPOSE);
		LISTENER_TYPE_MAP.put("POST_CREATE_PART", ListenerTypeEnum.POST_CREATE_PART);
		LISTENER_TYPE_MAP.put("BEFORE_FIELD_CHANGE", ListenerTypeEnum.BEFORE_FIELD_CHANGE);
		LISTENER_TYPE_MAP.put("AFTER_FIELD_CHANGE", ListenerTypeEnum.AFTER_FIELD_CHANGE);

		/** ENTITY_INJECTION */
		LISTENER_TYPE_MAP.put("BEFORE_ENTITY_INJECTION", ListenerTypeEnum.BEFORE_ENTITY_INJECTION);
		LISTENER_TYPE_MAP.put("AFTER_ENTITY_INJECTION", ListenerTypeEnum.AFTER_ENTITY_INJECTION);

		/** RUNNABLE */
		LISTENER_TYPE_MAP.put("PRE_RUN", ListenerTypeEnum.PRE_RUN);
		LISTENER_TYPE_MAP.put("POST_RUN", ListenerTypeEnum.POST_RUN);

		/** CONTROL */
		LISTENER_TYPE_MAP.put("MODIFY_TEXT", ListenerTypeEnum.MODIFY_TEXT);
		LISTENER_TYPE_MAP.put("CHECK_STATE", ListenerTypeEnum.CHECK_STATE);
		LISTENER_TYPE_MAP.put("SELECTION_CHANGED", ListenerTypeEnum.SELECTION_CHANGED);
		LISTENER_TYPE_MAP.put("POST_SELECTION_CHANGED", ListenerTypeEnum.POST_SELECTION_CHANGED);
		LISTENER_TYPE_MAP.put("WIDGET_SELECTED", ListenerTypeEnum.WIDGET_SELECTED);
		LISTENER_TYPE_MAP.put("DOUBLE_CLICK", ListenerTypeEnum.DOUBLE_CLICK);
		LISTENER_TYPE_MAP.put("REFRESH", ListenerTypeEnum.AFTER_REFRESH);
	}

	public static boolean isEligible(IElementWrapper element, int category) {
		for (AListenerTypeManager listenerTypeManager : LISTENER_MANAGER_MAP.values())
			if (category == listenerTypeManager.listenerCategory && listenerTypeManager.isEligible(element))
				return true;
		return false;
	}

	private int listenerCategory;

	private AListenerTypeManager(int listenerCategory) {
		this.listenerCategory = listenerCategory;
	}

	public int getListenerCategory() {
		return listenerCategory;
	}

	public abstract boolean isEligible(IElementWrapper element);
}
