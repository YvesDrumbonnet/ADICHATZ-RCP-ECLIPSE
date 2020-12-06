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

import static org.adichatz.engine.common.LogBroker.displayError;
import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.common.GeneratorUtil.FieldCaseEnum;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.ModelPartWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ActionWhenTypeEnum;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ModelProcurementEnum;
import org.adichatz.generator.xjc.PathElementType;
import org.adichatz.generator.xjc.PluginEntityType;
import org.adichatz.generator.xjc.RcpPartType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.generation.ModelComponentGeneration;
import org.adichatz.scenario.generation.UIComponentGeneration;
import org.adichatz.scenario.util.JBossStandaloneReader;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.util.ErroneousFilesFormDialog;
import org.adichatz.studio.util.StudioLog;
import org.adichatz.studio.xjc.custom.StandaloneFileChangeDialog;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IFormColors;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class FullProjectWizard.
 */
public class FullProjectWizard extends EmptyProjectWizard {

	/** The model part. */
	private ModelPartWrapper modelPart;

	/** The shell. */
	private Shell shell;

	/**
	 * The Constructor.
	 */
	public FullProjectWizard() {
		wizardPage = new FullProjectPage(AdichatzApplication.getInstance().getMessage(GeneratorConstants.STUDIO_BUNDLE, "%plugin",
				"addFullAdichatzProject.name"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.wizard.EmptyProjectWizard#createAdichatzProject()
	 */
	@Override
	protected void createAdichatzProject() {
		try {
			shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			final FullProjectPage fullProjectPage = (FullProjectPage) wizardPage;
			componentGeneration = scenarioResources.getComponentGeneration();
			componentGeneration.getJavaProject();
			componentGeneration.buildActivatorFile();
			scenarioResources.ACTIVATOR_FIRST_PASSAGE = false;

			final ScenarioTreeWrapper scenarioTree = componentGeneration.getScenarioResources().getScenarioTree();
			final GenerationScenarioType generationScenario = scenarioTree.getGenerationScenario();

			modelPart.setConnectorASVersion(fullProjectPage.getApplicationServerName());
			modelPart.setConnectorDataSource(fullProjectPage.getDataSourceId());
			modelPart.setDataSourceUnit(fullProjectPage.getDsUnitNameTxt().getText());
			final boolean deploy;
			if (IScenarioConstants.JSE.equals(fullProjectPage.getApplicationServerName())) {
				modelPart.setPersistenceManagerClassName("JSEPersistenceManager");
				modelPart.setLockManagerClassName("JSELockManager");
				deploy = false;
			} else {
				deploy = !fullProjectPage.getDeployEJBjar().isDisposed() && fullProjectPage.getDeployEJBjar().getSelection();
				modelPart.setLockManagerClassName(fullProjectPage.getLmBeanClassNameTxt().getText());
				modelPart.setPersistenceManagerClassName(fullProjectPage.getPmBeanClassNameTxt().getText());
				modelPart.setJndi(
						GeneratorUtil.getJavaName(modelPart.getConnectorDataSource(), FieldCaseEnum.NULL, false).concat("DS"));
				modelPart.setEjbFileName(fullProjectPage.getEjbJarNameTxt().getText());
			}
			modelPart.setModelPackageName(scenarioResources.getPluginPackage() + ".model");
			modelPart.setModelProcurement(ModelProcurementEnum.HIBERNATE);
			scenarioTree.addModelResources(scenarioResources);

			scenarioResources.marshalScenarioFile();
			componentGeneration.setGenerationScenario((GenerationScenarioWrapper) generationScenario);
			String generating = getFromStudioBundle("studio.generation.launching", scenarioResources.getPluginName());

			componentGeneration.buildManifestFile();
			final String jndi = modelPart.getJndi();
			final String datasourceId = modelPart.getConnectorDataSource();
			final String applicationServerName = fullProjectPage.getApplicationServerName();
			ModelComponentGeneration modelComponentGeneration = componentGeneration.getModelComponentGeneration();

			Job job = new Job(generating) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						GenerationScenarioWrapper generationScenario = (GenerationScenarioWrapper) scenarioTree
								.getGenerationScenario();
						modelComponentGeneration.generatePojo(generationScenario.getModelPart().getConnectorDataSource());
						if (null != customizationScenarioTree && null != customizationScenarioTree.getPathElements()) {
							List<PathElementType> pathElements = new ArrayList<>(
									customizationScenarioTree.getPathElements().getPathElement());
							if (!pathElements.isEmpty())
								scenarioResources.addPathElements(ScenarioUtil.getPathElements(scenarioResources, pathElements));
						}

						// Use new list because modelComponentGeneration.workPluginEntityMap is cleared
						// by generateMetaModel.
						List<PluginEntityWrapper> workPluginEntities = new ArrayList<>(
								modelComponentGeneration.getWorkPluginEntityMap(generationScenario).values());
						scenarioTree.getGenerationScenario().getPluginEntity().addAll(workPluginEntities);
						modelComponentGeneration.generateModelResources(workPluginEntities);
						if (null != customizationScenarioTree) {
							scenarioTree.mergeCustomization(scenarioResources, customizationScenarioTree,
									IPluginEntityScenario.MODEL_PART);
							((GenerationScenarioWrapper) scenarioTree.getGenerationScenario()).mergeCustomization(
									customizationScenarioTree.getGenerationScenario(), scenarioResources.getPluginName(),
									IPluginEntityScenario.ENTITY);
							scenarioResources.loadParams(true);
						}
						scenarioResources.rewritePojos(workPluginEntities, true, true);
						generationScenario.addGenerationUnit(componentGeneration.getScenarioResources(), GenerationEnum.ENTITY);
						generationScenario.addGenerationUnit(componentGeneration.getScenarioResources(), GenerationEnum.QUERY);
						modelComponentGeneration.generateMetaModel(scenarioTree, customizationScenarioTree, false);
						componentGeneration.buildActivatorFile();

						workPluginEntities.clear();
						componentGeneration.getScenarioResources().refreshPluginEntityTree();
						modelComponentGeneration.createPersistencesXmlFile();
						if (null != scenarioResources.getGenerationScenario().getModelPart() && !IScenarioConstants.JSE
								.equals(scenarioResources.getGenerationScenario().getModelPart().getConnectorASVersion()))
							modelComponentGeneration.createEJBJar(deploy);

						UIComponentGeneration uiComponentGeneration = componentGeneration.getUIComponentGeneration();
						uiComponentGeneration.initializeRcp(true);
						scenarioResources.getScenarioTree().actionResource(scenarioResources, ActionWhenTypeEnum.WHEN_BUILDING_UI);
						uiComponentGeneration.buildApplicationFiles(true);

						if (null != customizationScenarioTree) {
							scenarioTree.mergeCustomization(scenarioResources, customizationScenarioTree,
									IPluginEntityScenario.ENTITY | IPluginEntityScenario.RCP_PART);
						}

						for (GenerationUnitType generationUnit : generationScenario.getNavigatorGenerationUnit())
							uiComponentGeneration.buildNavigator(generationUnit);

						for (PluginEntityType pluginEntity : generationScenario.getPluginEntity())
							((PluginEntityWrapper) pluginEntity).clearUnitMap();
						componentGeneration.getScenarioResources().refreshPluginEntityTree();

						componentGeneration.generate(GenerationEnum.DETAIL, true, true, true);
						componentGeneration.generate(GenerationEnum.TABLE, true, true, true);
						componentGeneration.generate(GenerationEnum.EDITOR, true, true, true);
						componentGeneration.generate(GenerationEnum.MESSAGE_BUNDLE, true, true, true);

						if (applicationServerName.startsWith(IScenarioConstants.JBOSS_7_1)
								|| applicationServerName.startsWith(IScenarioConstants.WILDFLY)
								|| applicationServerName.startsWith(IScenarioConstants.JBOSS_EAP_6)) {
							ConnectorTreeWrapper connectorTree = ScenarioUtil.getConnectorTree((String) null);
							DatasourceWrapper datasource = connectorTree.getDatasource(datasourceId);
							int check71Installation = connectorTree.checkJboss71Iinstallation(applicationServerName, datasource,
									jndi, scenarioResources);
							if (0 != check71Installation) {
								final IConfirmContent confirmContent = getConfirmContent(check71Installation, applicationServerName,
										datasource);
								shell.getDisplay().syncExec(() -> {
									ConfirmFormDialog confirmFormDialog = new ConfirmFormDialog(shell,
											getFromStudioBundle("scenario.jboss.incomplete.installation", applicationServerName,
													project.getName()),
											AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
													"IMG_NEW_FULL_PLUGIN.png"),
											confirmContent) {
										@Override
										protected void buttonPressed(int buttonId) {
											if (IDialogConstants.OK_ID == buttonId) {
												confirmContent.okPressed(complementControls);
												okPressed();
											} else if (IDialogConstants.CANCEL_ID == buttonId) {
												cancelPressed();
											}
										}
									};
									confirmFormDialog.open();
								});
							}
						}

						componentGeneration.buildBuildPropertiesFile();

						scenarioResources.marshalScenarioFile();
						if (scenarioResources.isDirtyScenarioFile())
							scenarioResources.marshalScenarioFile();
						new ErroneousFilesFormDialog(null, shell.getDisplay(), componentGeneration.getScenarioResources());
						String message = getFromStudioBundle("studio.generation.completed", project.getName());
						logInfo(message);
						((StudioLog) LogBroker.getLogger()).log(IStatus.INFO, IStatus.OK, message, null);
					} catch (IOException | CoreException e) {
						logError(e);
					}
					return Status.OK_STATUS;
				}
			};
			job.setName(generating);
			job.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					if (!event.getResult().isOK())
						logError(EngineTools.getErrorString(event.getResult().getException()));
				}
			});
			job.schedule(); // start as soon as possible
		} catch (Exception e) {
			displayError(getFromGeneratorBundle("generation.error"), e.getLocalizedMessage());
			logError(e);
			((WizardDialog) getContainer()).close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.wizard.EmptyProjectWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		if (((FullProjectPage) wizardPage).isConnectionOk(false))
			return super.performFinish();
		return false;
	}

	/**
	 * Gets the confirm content.
	 *
	 * @param check71Installation   the check71 installation
	 * @param applicationServerName the application server name
	 * @param datasource            the datasource
	 * @return the confirm content
	 */
	private IConfirmContent getConfirmContent(final int check71Installation, final String applicationServerName,
			final DatasourceWrapper datasource) {
		return new IConfirmContent() {
			private Button addDriverModule;

			private Button addDriverInStandalone;

			private Button addDatasourceInStandalone;

			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				parent.setLayout(new MigLayout("wrap", "grow, fill", "grow, fill"));
				Group initJbossGroup = new Group(parent, SWT.None);
				initJbossGroup.setText(getFromStudioBundle("scenario.JBossinitialization", applicationServerName));
				toolkit.adapt(initJbossGroup);
				initJbossGroup.setFont(JFaceResources.getBannerFont());
				initJbossGroup.setBackground(toolkit.getColors().getColor(IFormColors.H_GRADIENT_START));
				initJbossGroup.setLayout(new MigLayout("wrap 1, ins 20"));
				parent.setBackground(initJbossGroup.getBackground());
				parent.getParent().setBackground(initJbossGroup.getBackground());
				if (0 != (check71Installation & IScenarioConstants.MISSING_DRIVER_MODULE)) {
					String module = datasource.getModule();
					addDriverModule = toolkit.createButton(initJbossGroup,
							getFromStudioBundle("scenario.add.driver.module", module), SWT.CHECK);
					addDriverModule.setBackground(initJbossGroup.getBackground());
					addDriverModule.setSelection(true);
				}
				if (0 != (check71Installation & IScenarioConstants.MISSING_DRIVER_STANDALONE)) {
					addDriverInStandalone = toolkit.createButton(initJbossGroup,
							getFromStudioBundle("scenario.add.driver.standalone", datasource.getDriver()), SWT.CHECK);
					addDriverInStandalone.setBackground(initJbossGroup.getBackground());
					addDriverInStandalone.setSelection(true);
				}
				if (0 != (check71Installation & IScenarioConstants.MISSING_DATASOURCE_STANDALONE)) {
					addDatasourceInStandalone = toolkit.createButton(initJbossGroup,
							getFromStudioBundle("scenario.add.datasource.standalone", modelPart.getJndi()), SWT.CHECK);
					addDatasourceInStandalone.setBackground(initJbossGroup.getBackground());
					addDatasourceInStandalone.setSelection(true);
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.adichatz.engine.extra.IConfirmContent#okPressed(java.util.List)
			 */
			@Override
			public void okPressed(List<Control> complementControls) {
				JBossStandaloneReader jbossStandaloneReader = null;
				if ((null != addDriverModule && addDriverModule.getSelection())
						|| ((null != addDriverInStandalone && addDriverInStandalone.getSelection())
								|| (null != addDatasourceInStandalone && addDatasourceInStandalone.getSelection()))) {
					jbossStandaloneReader = ScenarioUtil.getJBossStandaloneReader(modelPart, scenarioResources);
					new StandaloneFileChangeDialog(shell, jbossStandaloneReader).open();
				}
				if (null != addDriverModule && addDriverModule.getSelection())
					componentGeneration.getModelComponentGeneration().addDriverModule();
				if ((null != addDriverInStandalone && addDriverInStandalone.getSelection())
						|| (null != addDatasourceInStandalone && addDatasourceInStandalone.getSelection())) {
					if (null != jbossStandaloneReader) {
						if (null != addDriverInStandalone && addDriverInStandalone.getSelection())
							jbossStandaloneReader.addDriver();
						if (null != addDatasourceInStandalone && addDatasourceInStandalone.getSelection())
							jbossStandaloneReader.addDatasource();
					}
					jbossStandaloneReader.writeDoc();
				}
			}

		};
	}

	protected void createScenarioTreeSqueleton() {
		// Create model part and RCP part before merging param from customization tree
		GenerationScenarioWrapper generationScenario = scenarioResources.getGenerationScenario();
		modelPart = new ModelPartWrapper();
		generationScenario.setModelPart(modelPart);
		RcpPartType rcpPart = new RcpPartType();
		rcpPart.setApplication(true);
		rcpPart.setNavigator(true);
		generationScenario.setRcpPart(rcpPart);
	}

}
