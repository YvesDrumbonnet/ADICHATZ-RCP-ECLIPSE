package org.adichatz.css.theme;

import static org.adichatz.css.theme.ThemeUtil.getFromPublicBundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.e4.preference.APreferencePage;
import org.adichatz.engine.e4.preference.AdiPreferenceManager;
import org.adichatz.engine.e4.preference.CComboFieldEditor;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.e4.resource.EngineE4Util;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.ui.css.swt.theme.ITheme;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import net.miginfocom.swt.MigLayout;

/**
 * The Class ThemePreferencePage.
 *
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public class ThemePreferencePage extends APreferencePage {

	/** The theme group. */
	private Group themeGroup;

	/**
	 * Instantiates a new theme preference page.
	 *
	 * @param preferenceManager
	 *            the preference manager
	 * @param eclipsePreferences
	 *            the eclipse preferences
	 * @param parent
	 *            the parent
	 * @param treeViewer
	 *            the tree viewer
	 */
	public ThemePreferencePage(AdiPreferenceManager preferenceManager, IEclipsePreferences eclipsePreferences, Composite parent,
			TreeViewer treeViewer) {
		super(preferenceManager, eclipsePreferences, parent, treeViewer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.preference.APreferencePage#createPropertiesContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPropertiesContent(Composite parent) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		themeGroup = toolkit.createGroup(parent, getFromPublicBundle("theme.properties"), SWT.NONE);
		themeGroup.setLayout(new MigLayout("wrap 2", "[][grow,fill]"));
		themeGroup.setFont(JFaceResources.getBannerFont());

		IThemeEngine themeEngine = E4AdichatzApplication.getInstance().getContext().get(IThemeEngine.class);
		CComboFieldEditor themeFE = new CComboFieldEditor(EngineE4Util.THEME_ID, null, this, getFromPublicBundle("theme.title"),
				themeGroup) {
			@Override
			protected void setLabelProvider() {

				comboViewer.setLabelProvider(new LabelProvider() {
					@Override
					public String getText(Object element) {
						return ((ITheme) element).getLabel();
					}
				});
			}

			@Override
			public String getValue() {
				if (null == comboViewer.getSelection() || comboViewer.getSelection().isEmpty())
					return null;
				ITheme theme = (ITheme) comboViewer.getStructuredSelection().getFirstElement();
				return theme.getId();
			}

			@Override
			public void setValue(IEclipsePreferences eclipsePreferences) {
				String defaultThemeId = eclipsePreferences.get(id, defaultValue);
				if (null != defaultThemeId)
					for (ITheme theme : themeEngine.getThemes())
						if (defaultThemeId.equals(theme.getId())) {
							comboViewer.setSelection(new StructuredSelection(theme));
							break;
						}
			}

			@Override
			public boolean injectValue(IEclipsePreferences eclipsePreferences, boolean force) {
				boolean result = super.injectValue(eclipsePreferences, force);
				ITheme theme = (ITheme) getComboViewer().getStructuredSelection().getFirstElement();
				if (result) {
					themeEngine.setTheme(theme.getId(), false);
					AReskinManager.getInstance().postReskin();
				}
				return result;
			}
		};
		List<ITheme> themes = new ArrayList<>();
		for (ITheme theme : themeEngine.getThemes())
			themes.add(theme);
		themeFE.getComboViewer().setInput(themes);
		addField(themeFE);
	}
}
