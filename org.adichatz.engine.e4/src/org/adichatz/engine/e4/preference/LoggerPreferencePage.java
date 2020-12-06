package org.adichatz.engine.e4.preference;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.logging.Logger;

import org.adichatz.engine.common.LogBroker;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import net.miginfocom.swt.MigLayout;

public class LoggerPreferencePage extends APreferencePage {
	private Group logGroup;

	public LoggerPreferencePage(AdiPreferenceManager preferenceManager, IEclipsePreferences eclipsePreferences, Composite parent,
			TreeViewer treeViewer) {
		super(preferenceManager, eclipsePreferences, parent, treeViewer);
	}

	@Override
	public void createPropertiesContent(Composite parent) {
		logGroup = toolkit.createGroup(parent, getFromEngineE4Bundle("logger.properties"), SWT.NONE);
		logGroup.setLayout(new MigLayout("wrap 2", "[grow,fill]25[grow,fill]"));
		logGroup.setFont(JFaceResources.getBannerFont());

		/*
		 * Trace
		 */

		addField("traceEnabled", false, getFromEngineE4Bundle("logger.trace"), "traceConsolePerspective", true, false);
		addField("debugEnabled", false, getFromEngineE4Bundle("logger.debug"), "debugConsolePerspective", true, false);
		addField("warnEnabled", true, getFromEngineE4Bundle("logger.warn"), "warnConsolePerspective", true, false);
		addField("infoEnabled", true, getFromEngineE4Bundle("logger.info"), "infoConsolePerspective", true, false);
		addField("errorEnabled", true, getFromEngineE4Bundle("logger.error"), "errorConsolePerspective", true, true);
		addField("dialogLogError", false, getFromEngineE4Bundle("logger.dialog"), null, false, false);
		addField("statusLogError", false, getFromEngineE4Bundle("logger.statusLog"), "null", false, false);

		CComboFieldEditor levelFE = new CComboFieldEditor("jdkLoggerLevel", "WARNING", this,
				getFromEngineE4Bundle("jdkLogger.level"), logGroup) {
			@Override
			protected void setLabelProvider() {
				comboViewer.setLabelProvider(new LabelProvider() {
					@Override
					public String getText(Object element) {
						String jdkLoggerLevelKey;
						switch ((String) element) {
						case "OFF":
							jdkLoggerLevelKey = "OFF";
							break;
						case "SEVERE":
							jdkLoggerLevelKey = "SEVERE";
							break;
						case "WARNING":
							jdkLoggerLevelKey = "WARNING";
							break;
						case "INFO":
							jdkLoggerLevelKey = "INFO";
							break;
						case "CONFIG":
							jdkLoggerLevelKey = "CONFIG";
							break;
						case "FINE":
							jdkLoggerLevelKey = "FINE";
							break;
						case "FINER":
							jdkLoggerLevelKey = "FINER";
							break;
						case "FINEST":
							jdkLoggerLevelKey = "FINEST";
							break;
						case "ALL":
							jdkLoggerLevelKey = "ALL";
							break;
						default:
							jdkLoggerLevelKey = "WARNING";
						}
						return getFromEngineE4Bundle("jdkLogger.".concat(jdkLoggerLevelKey));
					}
				});
			}

			@Override
			public String getValue() {
				return (String) ((StructuredSelection) comboViewer.getSelection()).getFirstElement();
			}

			@Override
			public boolean injectValue(IEclipsePreferences eclipsePreferences, boolean force) {
				boolean result = super.injectValue(eclipsePreferences, force);
				Logger.getGlobal().getParent().setLevel(
						LogBroker.getLogger().getLevel((String) getComboViewer().getStructuredSelection().getFirstElement()));
				return result;
			}
		};
		ComboViewer comboViewer = levelFE.getComboViewer();
		comboViewer.setInput(new String[] { "OFF", "SEVERE", "WARNING", "INFO", "CONFIG", "FINE", "FINER", "FINEST", "ALL" });
		comboViewer.getCCombo().setToolTipText(getFromEngineE4Bundle("jdkLogger.level.tooltip"));
		comboViewer.setSelection(new StructuredSelection(LogBroker.getLogger().getJdkLoggerLevel()));
		addField(levelFE);

	}

	private BooleanFieldEditor addField(String id, boolean defaultValue, String labelText, String consolePerspId,
			boolean addConsolePersp, boolean defaultForceConsolePersp) {
		BooleanFieldEditor booleanFieldEditor = new BooleanFieldEditor(id, defaultValue, this, labelText, logGroup);
		addField(booleanFieldEditor);
		if (addConsolePersp) {
			addField(new BooleanFieldEditor(consolePerspId, defaultForceConsolePersp, this,
					getFromEngineE4Bundle("logger.active.console.perspective"), logGroup));

		} else
			((Button) booleanFieldEditor.getControl()).setLayoutData("span 2");

		return booleanFieldEditor;
	}
}
