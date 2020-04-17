/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 *
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.studio.wizard;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ComponentGeneration;
import org.adichatz.scenario.generation.ScenarioTreeGeneration;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.util.StudioLog;
import org.adichatz.studio.util.StudioUtil;
import org.adichatz.studio.xjc.editor.ScenarioFormEditor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

// TODO: Auto-generated Javadoc
/**
 * The Class EmptyProjectWizard.
 */
public class EmptyProjectWizard extends Wizard implements INewWizard {

	/** The wizard page. */
	protected EmptyProjectPage wizardPage;

	/** The project. */
	protected IProject project;

	/** The component generation. */
	protected ComponentGeneration componentGeneration;

	/** The customization scenario tree. */
	protected ScenarioTreeWrapper customizationScenarioTree;

	protected String customizationScenarioFileName;

	/** The scenario resources. */
	protected ScenarioResources scenarioResources;

	/**
	 * The Constructor.
	 */
	public EmptyProjectWizard() {
		wizardPage = new EmptyProjectPage(AdichatzApplication.getInstance().getMessage(GeneratorConstants.STUDIO_BUNDLE, "%plugin",
				"addEmptyAdichatzProject.name"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		getContainer().getShell().setEnabled(false);
		ProgressMonitorDialog generateDialog = new ProgressMonitorDialog(getShell());
		try {
			generateDialog.run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(final IProgressMonitor monitor) {
					getContainer().getShell().getDisplay().syncExec(() -> {
						try {
							project = wizardPage.getProjectHandle();
							String message = getFromStudioBundle("studio.generation.launching", project.getName());
							logInfo(message);
							((StudioLog) LogBroker.getLogger()).log(IStatus.INFO, IStatus.OK, message, null);
							createProject(project, monitor);
						} catch (CoreException e) {
							logError(e);
						} finally {
							monitor.done();
						}
					});
				}
			});
			createAdichatzProject();
			return true;
		} catch (InvocationTargetException | InterruptedException e) {
			logError(e);
		} finally {
			getContainer().getShell().setEnabled(true);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(wizardPage);
	}

	/**
	 * Creates the project.
	 *
	 * @param project
	 *            the project
	 * @param monitor
	 *            the monitor
	 * @throws CoreException
	 *             the core exception
	 */
	protected void createProject(IProject project, IProgressMonitor monitor) throws CoreException {
		final IProjectDescription projectDesc = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

		@SuppressWarnings("restriction")
		final URI projectURI = (!wizardPage.getLocationArea().isDefault()) ? wizardPage.getLocationArea().getProjectLocationURI()
				: null;

		// Create project
		try {
			ScenarioTreeGeneration scenarioTreeGeneration = null;
			String scenarioTreeGenerationURI = StudioRcpPlugin.getDefault().getPreferenceStore()
					.getString(IStudioConstants.SCENARIO_TREE_GENERATION_URI);
			if (EngineTools.isEmpty(scenarioTreeGenerationURI)) {
				scenarioTreeGeneration = new ScenarioTreeGeneration(project, wizardPage.getPackageNameField().getText());
			} else {
				try {
					Class<?> scenarioTreeGenerationClass = StudioUtil.getScenarioTreeGenerationClass(scenarioTreeGenerationURI);
					if (null != scenarioTreeGenerationClass)
						scenarioTreeGeneration = (ScenarioTreeGeneration) scenarioTreeGenerationClass
								.getDeclaredConstructor(new Class[] { IProject.class, String.class })
								.newInstance(project, wizardPage.getPackageNameField().getText());
					else {
						logError(getFromStudioBundle("studio.scenarioTree.generation.uri.not.valid", scenarioTreeGenerationURI));
						return;
					}
				} catch (Exception e) {
					logError(getFromStudioBundle("studio.scenarioTree.generation.uri.not.valid", scenarioTreeGenerationURI), e);
					return;
				}
			}
			projectDesc.setLocationURI(projectURI);
			project.create(projectDesc, monitor);
			project.open(monitor);
			scenarioTreeGeneration.buildScenarioFile();

			scenarioResources = StudioUtil.createScenarioResources(project);//
			scenarioResources.ACTIVATOR_FIRST_PASSAGE = true;
			createScenarioTreeSqueleton();
			if (null != wizardPage.getScenarioCustomizationField()) {
				customizationScenarioFileName = wizardPage.getScenarioCustomizationField().getValue();
				if (null != customizationScenarioFileName) {
					customizationScenarioTree = ScenarioUtil.getCustomScenarioTree(scenarioResources,
							customizationScenarioFileName);
					scenarioTreeGeneration.createXmlFile();
					if (null != customizationScenarioTree && null != customizationScenarioTree.getParams())
						scenarioResources.loadParams(true);
				}
			}
			logInfo(getFromStudioBundle("studio.generation.scenarioTreeGeneration.buildingFile"));
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		} catch (CoreException e) {
			logError(e);
		}
	}

	protected void createScenarioTreeSqueleton() {
	}

	/**
	 * Creates the adichatz project.
	 */
	protected void createAdichatzProject() {
		ScenarioResources scenarioResources = StudioUtil.createScenarioResources(project);
		componentGeneration = scenarioResources.getComponentGeneration();
		componentGeneration.getJavaProject();
		componentGeneration.buildActivatorFile();
		componentGeneration.buildManifestFile();
		String message = getFromStudioBundle("studio.generation.completed", project.getName());
		logInfo(message);
		((StudioLog) LogBroker.getLogger()).log(IStatus.INFO, IStatus.OK, message, null);
		openScenarioFile();
	}

	/**
	 * Open scenario file.
	 */
	protected void openScenarioFile() {
		try {
			FileEditorInput fileEditorInput = new FileEditorInput(
					project.getFile(EngineConstants.XML_FILES_PATH + "/" + GeneratorConstants.SCENARIO_FILE));
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(fileEditorInput, ScenarioFormEditor.ID);
		} catch (PartInitException e) {
			logError(e);
		}
	}
}
