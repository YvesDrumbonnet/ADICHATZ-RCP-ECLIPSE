package org.adichatz.generator.tree;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

@SuppressWarnings({ "rawtypes" })
public abstract class ATreeChild {

	protected Class<?> parentClass;

	protected String[] refFieldNames;

	protected String[] entitySetNames;

	public ATreeChild(Class<?> parentClass) {
		this.parentClass = parentClass;
		init();
	}

	public String[] getRefFieldNames() {
		return refFieldNames;
	}

	public List<AXjcTreeElement> getRefFields(AXjcTreeElement parentElement) {
		List<AXjcTreeElement> children = new ArrayList<AXjcTreeElement>();
		if (null != refFieldNames)
			for (String fieldName : refFieldNames) {
				Method getMethod = FieldTools
						.getGetMethod(null == parentElement ? parentClass : parentElement.getElement().getClass(), fieldName, true);
				Object child = ReflectionTools.invokeMethod(getMethod, parentElement.getElement());
				if (null != child)
					addElement(parentElement, child, getMethod, true);
			}
		return children;
	};

	public List<AXjcTreeElement> getEntitySets(AXjcTreeElement parentElement) {
		List<AXjcTreeElement> children = new ArrayList<AXjcTreeElement>();
		if (null != entitySetNames)
			for (String entitySetName : entitySetNames) {
				Method getMethod = FieldTools.getGetMethod(
						null == parentElement ? parentClass : parentElement.getElement().getClass(), entitySetName, true);
				Collection result = (Collection) ReflectionTools.invokeMethod(getMethod, parentElement.getElement());
				if (null != result)
					for (Object child : result)
						addElement(parentElement, child, getMethod, false);
			}
		return children;
	}

	public Font getFont(AXjcTreeElement treeElement) {
		return null;
	}

	public Image getImage(AXjcTreeElement treeElement) {
		return null;
	}

	public Color getForeground(AXjcTreeElement treeElement) {
		return null;
	}

	public Color getBackground(AXjcTreeElement treeElement) {
		return null;
	}

	protected Method addRef(Class<?> clazz, String fieldName) {
		Method getMethod = FieldTools.getGetMethod(clazz, fieldName, true);
		return getMethod;
	}

	protected Method addEntitySet(Class<?> clazz, String fieldName) {
		return FieldTools.getGetMethod(clazz, fieldName, true);
	}

	protected void addElement(AXjcTreeElement parentElement, Object child, Method getMethod, boolean refField) {
		parentElement.getChildElements().add(new AXjcTreeElement(parentElement.getTreeManager(), parentElement, child));
	}

	public abstract void init();

	public abstract String getText(AXjcTreeElement treeElement);

}
