package org.adichatz.tool.cacheManager;

import static org.adichatz.tool.ToolUtil.getFromToolBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.tool.ToolUtil;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

public class CacheManagerPart {
	@Inject
	protected Composite parent;

	private AdiPluginResources pluginResources;

	private ScenarioResources scenarioResources;

	private CTabFolder tabFolder;

	@PostConstruct
	public void createControl(IEclipseContext context, MPart part) {
		//		AdiInputPart inputPart = context.get(AdiInputPart.class);
		//		context.remove(AdiInputPart.class);
		//		pluginResources = inputPart.getPluginResources();
		pluginResources = (AdiPluginResources) part.getTransientData().get(ToolUtil.PLUGIN_RESOURCES);
		scenarioResources = ScenarioResources.getInstance(pluginResources.getPluginName(), null);

		tabFolder = new CTabFolder(parent, SWT.BOTTOM | SWT.FLAT);
		AdichatzApplication.getInstance().getFormToolkit().adapt(tabFolder);

		addPage(new EntityFormPage(this), "entityCacheManagerList");
		addPage(new BoundTabularControllerFormPage(this), "boundTable");
		addPage(new RelationshipUpdateFormPage(this), "relationshipUpdate");
		addPage(new CreatedEntitiesFormPage(this), "createdEntities");
		addPage(new TabularByCacheKeyFormPage(this), "tableByCacheKey");
		addPage(new LockManagerFormPage(this), "locks");

		tabFolder.setSelection(0);
	}

	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	public ScenarioResources getScenarioResources() {
		return scenarioResources;
	}

	private void addPage(ICachePage cachePage, String text) {
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		ScrolledForm scrolledForm = AdichatzApplication.getInstance().getFormToolkit().createScrolledForm(tabFolder);
		Composite body = scrolledForm.getBody();
		body.setLayout(new MigLayout("wrap, ins 0", "grow,fill", "grow,fill"));
		tabItem.setControl(scrolledForm);
		tabItem.setText(text);
		String formText = getFromToolBundle(text);
		cachePage.createContent(new ManagedForm(AdichatzApplication.getInstance().getFormToolkit(), scrolledForm), formText);
	}
}
