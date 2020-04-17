package org.adichatz.tool;

import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

public class ApplicationParamManagerPart {
	@Inject
	protected Composite parent;

	private AdiPluginResources pluginResources;

	private ScenarioResources scenarioResources;

	private AdiFormToolkit toolkit;

	private Action saveAction;

	private List<Text> texts = new ArrayList<>();

	@PostConstruct
	public void createControl(IEclipseContext context, MPart part) {
		pluginResources = (AdiPluginResources) part.getTransientData().get(ToolUtil.PLUGIN_RESOURCES);
		scenarioResources = ScenarioResources.getInstance(pluginResources.getPluginName(), null);

		toolkit = AdichatzApplication.getInstance().getFormToolkit();
		ScrolledForm scrolledForm = toolkit.createScrolledForm(parent);
		scrolledForm.setText(getFromToolBundle("tool.applicationParamManager"));
		Composite body = scrolledForm.getBody();
		body.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "grow,fill"));

		saveAction = new Action(getFromToolBundle("tool.save.params"), toolkit.getRegisteredImageDescriptor("IMG_SAVE")) {
			@Override
			public void run() {
				Map<String, Object> params = AdichatzApplication.getInstance().getApplicationParamMap();
				for (Text text : texts)
					if (null != text.getData("#MODIFIED#"))
						params.put((String) text.getData("#KEY#"), text.getText());
				saveAction.setEnabled(false);
			}
		};
		saveAction.setEnabled(false);
		scrolledForm.getToolBarManager().add(saveAction);
		scrolledForm.getToolBarManager().update(true);

		body.setLayout(new MigLayout("wrap 2", "[][fill,grow]"));
		for (Map.Entry<String, Object> entry : AdichatzApplication.getInstance().getApplicationParamMap().entrySet()) {
			createLabel(body, entry.getKey());
			Object value = entry.getValue();
			if (value instanceof String)
				texts.add(createText(body, entry.getKey(), (String) value));
			else
				createLabel(body, (null == entry.getValue() ? " ???" : entry.getValue().getClass().getName()));
		}
	}

	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	public ScenarioResources getScenarioResources() {
		return scenarioResources;
	}

	private Text createText(Composite parent, String key, String label) {
		Text text = toolkit.createText(parent, label);
		text.addModifyListener((e) -> {
			saveAction.setEnabled(true);
			text.setData("#MODIFIED#", true);
		});
		text.setData("#KEY#", key);
		return text;
	}

	private void createLabel(Composite parent, String text) {
		toolkit.createLabel(parent, text);
	}
}
