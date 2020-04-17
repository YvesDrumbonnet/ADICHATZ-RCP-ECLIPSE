package org.adichatz.jpa.recent;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.util.function.BooleanSupplier;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.e4.preference.APreferencePage;
import org.adichatz.engine.e4.preference.AdiPreferenceManager;
import org.adichatz.engine.e4.preference.BooleanFieldEditor;
import org.adichatz.engine.e4.preference.IntegerFieldEditor;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.jpa.extra.JPAUtil;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import net.miginfocom.swt.MigLayout;

public class RecentPreferencePage extends APreferencePage {

	public static String RECENTLY_OPEN_EDITORS_NUMBER = "RECENTLY_OPEN_EDITORS_NUMBER";

	public RecentPreferencePage(AdiPreferenceManager preferenceManager, IEclipsePreferences eclipsePreferences, Composite parent,
			TreeViewer treeViewer) {
		super(preferenceManager, eclipsePreferences, parent, treeViewer);
	}

	@Override
	public void createPropertiesContent(Composite parent) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		Group recentGroup = toolkit.createGroup(parent, JPAUtil.getFromJpaBundle("recent.preference.group"), SWT.NONE);
		recentGroup.setLayout(new MigLayout("wrap 2", "[fill,al right][grow,fill]", "[][]"));
		recentGroup.setFont(JFaceResources.getBannerFont());

		final IntegerFieldEditor recentlyOpenEditorsNumberIFE = new IntegerFieldEditor(
				RecentPreferencePage.RECENTLY_OPEN_EDITORS_NUMBER, 10, this, getFromJpaBundle("recent.editors.size"), recentGroup,
				"######");
		addField(recentlyOpenEditorsNumberIFE);
		evaluators.add(new BooleanSupplier() {
			@Override
			public boolean getAsBoolean() {
				if (recentlyOpenEditorsNumberIFE.getValue() < 0 || recentlyOpenEditorsNumberIFE.getValue() > 99) {
					addMessage(getFromEngineBundle("invalid.range", 1, 99));
					return false;
				}
				return true;
			}
		});

		BooleanFieldEditor saveOnComputerFE = new BooleanFieldEditor(RecentUtil.SAVE_RECENTLY_OPEN_ON_COMPUTER, true, this,
				getFromJpaBundle("recent.save.open.editor"), recentGroup);
		((Control) saveOnComputerFE.getControl()).setLayoutData("span 2");
		addField(saveOnComputerFE);
	}
}
