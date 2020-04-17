package org.adichatz.studio.command;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.Iterator;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ComponentGeneration;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import net.miginfocom.swt.MigLayout;

public class GenerateBundleFileHandler extends AGenerateFileHandler {

	@Override
	public void generate(IStructuredSelection fSelection) {
		this.fSelection = fSelection;
		String fileName = ((IFile) fSelection.getFirstElement()).getProject().getName();
		if (ConfirmFormDialog.check(Display.getCurrent(), getFromStudioBundle("studio.menu.generateBundleAction.title", fileName),
				AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_GENERATE_BUNDLE.png"),
				getConfirmContent(getFromStudioBundle("studio.menu.generateBundleAction.sectionText"),
						getFromStudioBundle("studio.menu.generateBundleAction.columnText")))) {
			generateBundle();
		}
	}

	public void generateBundle() {
		Iterator<?> selectionIter = fSelection.iterator();
		ScenarioResources scenarioResources = null;
		while (selectionIter.hasNext()) {
			IFile bundleFile = (IFile) selectionIter.next();
			if (null == scenarioResources)
				scenarioResources = ScenarioResources.getInstance(bundleFile.getProject());
			scenarioResources.getPluginResources().getPluginEntityTree(); // Initializes PluginEntityTree if needed
			ComponentGeneration componentGeneration = scenarioResources.getComponentGeneration();
			String entityId = scenarioResources.getPluginEntityScenario().getEntityIdFromBundleFile(scenarioResources, bundleFile);
			for (PluginEntityType pluginEntity : scenarioResources.getGenerationScenario().getPluginEntity()) {
				if (((PluginEntityWrapper) pluginEntity).getEntityId().equals(entityId)) {
					LogBroker.logInfo(getFromStudioBundle("studio.generateBundle.file", pluginEntity.getEntityURI()));
					componentGeneration.generate(GenerationEnum.MESSAGE_BUNDLE, pluginEntity.getEntityURI(), true, true, true);
				}
			}
			try {
				bundleFile.refreshLocal(IResource.DEPTH_ZERO, componentGeneration.getSubMonitor());
			} catch (CoreException e) {
				logError(e);
			}
		}
	}

	public IConfirmContent getConfirmContent(final String sectionText, final String columnText) {
		return new IConfirmContent() {
			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				parent.setLayout(new MigLayout("wrap", "[grow, fill]"));
				createTreeSection(toolkit, parent, sectionText, columnText, fSelection);

				Composite composite = toolkit.createComposite(parent);
				composite.setLayout(new MigLayout("wrap 3, center", "[]10[]10[]"));
			}

			@Override
			public void okPressed(List<Control> complementControls) {
			}

		};
	}
}
