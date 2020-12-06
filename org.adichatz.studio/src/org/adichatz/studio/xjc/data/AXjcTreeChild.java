package org.adichatz.studio.xjc.data;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.tree.ATreeChild;
import org.adichatz.generator.tree.AXjcTreeElement;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.studio.xjc.editor.AStudioTreeManager;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

@SuppressWarnings({ "rawtypes" })
public abstract class AXjcTreeChild extends ATreeChild {
	private static Color HYPER_LINK_COLOR;

	private static Color getHyperLinkColor() {
		if (null == HYPER_LINK_COLOR)
			HYPER_LINK_COLOR = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class).getColors()
					.getColor(JFacePreferences.ACTIVE_HYPERLINK_COLOR);
		return HYPER_LINK_COLOR;
	}

	private static Font ITALIC_FONT;

	private static Font getItalicFont(AXjcTreeElement treeElement) {
		if (null == ITALIC_FONT) {
			Font font = ((AStudioTreeManager) treeElement.getTreeManager()).getTreeController().getControl().getFont();
			ITALIC_FONT = EngineTools.getModifiedFont(font, SWT.ITALIC);
		}
		return ITALIC_FONT;
	}

	protected Set<Class> refFieldClasses = new HashSet<Class>();

	public AXjcTreeChild(Class<?> parentClass) {
		super(parentClass);
		for (String refFieldName : refFieldNames) {
			Method getMethod = super.addRef(parentClass, refFieldName);
			refFieldClasses.add(getMethod.getReturnType());
		}
	}

	@Override
	protected void addElement(AXjcTreeElement parentElement, Object child, Method getMethod, boolean refField) {
		AStudioTreeManager treeManager = (AStudioTreeManager) parentElement.getTreeManager();
		if (child instanceof ElementType && null != ((ElementType) child).getId())
			treeManager.updateChildrenMap((ElementType) child, null, ((ElementType) child).getId());
		parentElement.getChildElements().add(new XjcTreeElement(treeManager, parentElement, child, getMethod, refField));
	}

	public Color getForeground(AXjcTreeElement treeElement) {
		if (refFieldClasses.contains(treeElement.getElement().getClass()))
			return getHyperLinkColor();
		return null;
	}

	public Font getFont(AXjcTreeElement treeElement) {
		if (refFieldClasses.contains(treeElement.getElement().getClass()))
			return getItalicFont(treeElement);
		return null;
	}
}
