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

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.FileText;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.common.GeneratorUtil.FieldCaseEnum;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.xjc.ApplicationServerType;
import org.adichatz.generator.xjc.DatasourceType;
import org.adichatz.generator.xjc.DatasourcesType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.util.StudioUtil;
import org.adichatz.studio.xjc.custom.ApplicationServerWindow;
import org.adichatz.studio.xjc.custom.ClassNameValidator;
import org.adichatz.studio.xjc.custom.DatasourceWindow;
import org.adichatz.studio.xjc.editor.ExternalResourcesFormEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class FullProjectPage.
 */
public class FullProjectPage extends EmptyProjectPage {

	/** The class pattern. */
	private Pattern classPattern;

	/** The title. */
	private String title;

	/** The datasource Id. */
	private String datasourceId;

	/** The application server name. */
	private String applicationServerName;

	/** The model part rb. */
	private ResourceBundle modelPartRB = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE)
			.getResourceBundleManager().getResourceBundle("modelPart");

	/** The connector tree. */
	private ConnectorTreeWrapper connectorTree;

	/** The ds unit name txt. */
	private Text dsUnitNameTxt;

	/** The ejb composite. */
	private Composite ejbComposite;

	/** The deploy composite. */
	private Composite deployComposite;

	/** The generate ej bjar. */
	private Button generateEJBjar;

	/** The deploy ej bjar. */
	private Button deployEJBjar;

	/** The pm bean class name txt. */
	private Text pmBeanClassNameTxt;

	/** The lm bean class name txt. */
	private Text lmBeanClassNameTxt;

	/** The ejb jar name txt. */
	private Text ejbJarNameTxt;

	/** The application server combo. */
	private Combo applicationServerCombo;

	/** The datasource combo. */
	private ComboViewer datasourceComboCV;

	/** The deployment error. */
	private boolean deploymentError = false;

	/**
	 * Instantiates a new a project generation page.
	 * 
	 * @param pageName
	 *            the page name
	 */
	public FullProjectPage(String pageName) {
		super(pageName);
		classPattern = Pattern.compile("^[a-zA-Z_\\$][\\w\\$]*(?:\\[a-zA-Z_\\$][\\w\\$]*)*$");
		connectorTree = ScenarioUtil.getConnectorTree((String) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.wizard.EmptyProjectPage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createContents(final Composite parent) {
		super.createContents(parent);
		dataComposite.setLayout(new MigLayout("wrap 4, ins 5", "[][fill,grow][][][]"));

		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();

		title = modelPartRB.getString("connectorDataSource");

		Hyperlink datasourceHL = new Hyperlink(dataComposite, SWT.WRAP);
		datasourceHL.setText(title);
		datasourceHL.setUnderlined(true);
		datasourceHL.setForeground(JFaceColors.getHyperlinkText(datasourceHL.getDisplay()));
		datasourceHL.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				openDataSourceWindow(toolkit);
			}
		});
		datasourceHL.setLayoutData("newline 20, right");
		DatasourcesType datasources = connectorTree.getDatasources();

		datasourceComboCV = new ComboViewer(dataComposite, SWT.READ_ONLY | SWT.BORDER | SWT.DROP_DOWN);
		datasourceComboCV.setContentProvider(new ArrayContentProvider());
		datasourceComboCV.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((DatasourceWrapper) element).getDescription();
			}
		});

		if (null != datasources) {
			datasourceComboCV.setInput(connectorTree.getDatasources().getDatasource());
			datasourceComboCV.addSelectionChangedListener((e) -> {
				IStructuredSelection selection = (IStructuredSelection) e.getSelection();
				if (!selection.isEmpty())
					setDataSourcesFieldText((DatasourceWrapper) selection.getFirstElement());
			});
		}
		WizardUtil.createHelp(dataComposite, "studio.newProject.datasourceToolTip", "studio.newProject.specifyDatasource",
				"studio.newProject.datasourceHelp");

		Button button = toolkit.createButton(dataComposite, getFromStudioBundle("studio.testConnection"), SWT.PUSH);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				connectorTree.testConnection(parent.getShell(), datasourceId, true);
			}
		});

		Label customizationLabel = new Label(dataComposite, SWT.NONE);
		customizationLabel.setText(getFromStudioBundle("scenario.custom.label"));
		customizationLabel.setLayoutData("newline");
		scenarioCustomizationField = new FileText(dataComposite, AdiSWT.DELETE_BUTTON);
		scenarioCustomizationField.setFilterExtension("*.xml");

		scenarioCustomizationField.addModifyListener((e) -> {
			setPageComplete(validatePage());
		});
		WizardUtil.createHelp(dataComposite, "scenario.custom.toolTip", "scenario.custom.specify", "scenario.custom.help");
		MenuManager menuManager = new MenuManager("Custom FIleText menu");
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				if (!EngineTools.isEmpty(scenarioCustomizationField.getValue())) {
					Action openFileAction = new Action(getFromStudioBundle("scenario.custom.openFile"),
							toolkit.getRegisteredImageDescriptor("IMG_OPEN_FILE")) {
						@Override
						public void run() {
							ExternalResourcesFormEditor.openExternalFile(scenarioCustomizationField.getValue());
						}
					};
					mgr.add(openFileAction);
					Action copyFileNameAction = new Action(getFromStudioBundle("scenario.custom.copyFileName"),
							toolkit.getRegisteredImageDescriptor("IMG_COPY")) {
						@Override
						public void run() {
							EngineTools.copyToBuffer(scenarioCustomizationField.getValue());
						}
					};
					mgr.add(copyFileNameAction);
				}
			}
		});
		scenarioCustomizationField.setMenu(menuManager.createContextMenu(scenarioCustomizationField));
		scenarioCustomizationField.getText().setMenu(menuManager.createContextMenu(scenarioCustomizationField.getText()));
		new Label(dataComposite, SWT.NONE);

		datasourceComboCV.setSelection(new StructuredSelection(connectorTree.getDatasources().getDatasource().get(0)));
		datasourceComboCV.getCombo().notifyListeners(SWT.Selection, null);

		Group jpaGroup = new Group(dataComposite, SWT.NONE);
		jpaGroup.setLayout(new MigLayout("wrap", "[grow,fill]", "grow"));
		jpaGroup.setLayoutData("span 4, grow");
		jpaGroup.setFont(JFaceResources.getBannerFont());

		jpaGroup.setText(getFromStudioBundle("scenario.EJB.group"));

		final Composite connectorComposite = new Composite(jpaGroup, SWT.NONE);
		connectorComposite.setLayout(new MigLayout("wrap 6, ins 0 0 0 0", "[][][]15[][grow,fill][]", "grow"));
		connectorComposite.setLayoutData("grow");

		title = modelPartRB.getString("connectorASVersion");
		Hyperlink applicationServerHL = new Hyperlink(connectorComposite, SWT.WRAP);
		applicationServerHL.setUnderlined(true);
		applicationServerHL.setForeground(JFaceColors.getHyperlinkText(datasourceHL.getDisplay()));
		applicationServerHL.setText(title);
		applicationServerHL.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				openApplicationServerWindow();
				checkASInstallation();
				setPageComplete(validatePage());
			}
		});

		applicationServerCombo = new Combo(connectorComposite, SWT.READ_ONLY | SWT.BORDER | SWT.DROP_DOWN);
		if (null != connectorTree.getApplicationServers().getApplicationServer()) {
			List<String> applicationServerNames = new ArrayList<String>();
			for (ApplicationServerType applicationServer : connectorTree.getApplicationServers().getApplicationServer())
				applicationServerNames.add(applicationServer.getName());
			applicationServerCombo.setItems(applicationServerNames.toArray(new String[applicationServerNames.size()]));
			applicationServerCombo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					validDeployEJBjar();
					applicationServerName = getApplicationServerName();
					checkASInstallation();
					deployEJBjar.setSelection(true);
					setPageComplete(validatePage());
				}
			});
			applicationServerCombo.select(0);
		}
		WizardUtil.createHelp(connectorComposite, "studio.newProject.connectorVersionToolTip",
				"studio.newProject.specifyconnectorVersion", "studio.newProject.connectorVersionHelp");

		dsUnitNameTxt = createField(connectorComposite, "studio.jpa.unitNameDS", FieldCaseEnum.NULL, "Unit",
				"studio.jpa.unitNameDSToolTip", "studio.jpa.unitNameDSSpecify", "studio.jpa.unitNameDSHelp", false, false);

		ejbComposite = new Composite(jpaGroup, SWT.NONE);
		ejbComposite.setLayout(new MigLayout("wrap 1, ins 5 0 0 0", "[grow,fill]", "grow"));
		ejbComposite.setLayoutData("grow");
		ejbComposite.setVisible(false);

		deployComposite = new Composite(ejbComposite, SWT.NONE);
		deployComposite.setLayout(new MigLayout("wrap 2, ins 5 0 0 0, hidemode 3", "[grow,fill]15[grow,fill]", "grow"));
		deployComposite.setLayoutData("grow");

		generateEJBjar = new Button(deployComposite, SWT.CHECK);
		generateEJBjar.setText(getFromStudioBundle("studio.jpa.generateEJBjar"));
		generateEJBjar.setSelection(true);

		deployEJBjar = new Button(deployComposite, SWT.CHECK);
		deployEJBjar.setText(getFromStudioBundle("studio.jpa.deployEJBjar"));
		deployEJBjar.setSelection(true);

		Composite deployDirComposite = new Composite(deployComposite, SWT.NONE);
		deployDirComposite.setLayout(new MigLayout("wrap 3, ins 0", "[][grow,fill][]"));
		deployDirComposite.setLayoutData("wmax 600, span 2");

		Label deployDirLBL = new Label(deployDirComposite, SWT.None);
		deployDirLBL.setText(getFromStudioBundle("studio.jpa.deploymentDirectory"));

		final Group beanGroup = new Group(deployComposite, SWT.NONE);
		beanGroup.setText(getFromStudioBundle("studio.newProject.beanClassName"));
		beanGroup.setLayoutData("span 2, grow");
		beanGroup.setLayout(new MigLayout("wrap 6", "[align right][fill,grow][]15[align right][fill,grow][]", "grow"));
		beanGroup.setFont(JFaceResources.getBannerFont());

		pmBeanClassNameTxt = createField(beanGroup, "studio.newProject.pmBeanClassName", FieldCaseEnum.UPPER_CASE_FIRST_LETTER,
				"PMBean", "studio.newProject.pmBeanClassNameToolTip", "studio.newProject.pmBeanClassNameSpecify",
				"studio.newProject.pmBeanClassNameHelp", true, false);
		pmBeanClassNameTxt.setLayoutData("wmin 200");
		lmBeanClassNameTxt = createField(beanGroup, "studio.newProject.lmBeanClassName", FieldCaseEnum.UPPER_CASE_FIRST_LETTER,
				"LMBean", "studio.newProject.lmBeanClassNameToolTip", "studio.newProject.lmBeanClassNameSpecify",
				"studio.newProject.lmBeanClassNameHelp", true, false);
		lmBeanClassNameTxt.setLayoutData("wmin 200");
		ejbJarNameTxt = createField(beanGroup, "studio.newProject.ejbJarName", FieldCaseEnum.NULL, "EJB.jar",
				"studio.newProject.ejbJarNameToolTip", "studio.newProject.ejbJarNameSpecify", "studio.newProject.ejbJarNameHelp",
				true, false);

		if (null != connectorTree.getApplicationServers().getApplicationServer())
			applicationServerCombo.notifyListeners(SWT.Selection, null);
	}

	/**
	 * Check as installation.
	 */
	private void checkASInstallation() {
		ApplicationServerType applicationServer = connectorTree.getApplicationServer(getApplicationServerName());
		if (IScenarioConstants.JSE.equals(applicationServerName))
			deploymentError = false;
		else {
			if (null == applicationServer.getParams())
				deploymentError = true;
			String applicationServerHome = null;
			String deploymentDir = null;
			for (ParamType param : applicationServer.getParams().getParam()) {
				if (IScenarioConstants.AS_HOME_FIELD.equals(param.getId()))
					applicationServerHome = param.getValue().trim();
				else if (IScenarioConstants.AS_DEPLOYMENT_DIR_FIELD.equals(param.getId()))
					deploymentDir = param.getValue().trim();
			}
			if (null == deploymentDir || null == applicationServerHome)
				deploymentError = true;
			else {
				deploymentError = !new File(applicationServerHome).exists()
						|| !new File(applicationServerHome.concat("/").concat(deploymentDir)).exists();
			}
		}

	}

	/**
	 * Valid deploy EJB jar.
	 */
	private void validDeployEJBjar() {
		if (IScenarioConstants.JSE.equals(getApplicationServerName())) {
			ejbComposite.setVisible(false);
		} else {
			ejbComposite.setVisible(true);
			if (!generateEJBjar.getSelection()) {
				deployComposite.setVisible(false);
			} else {
				deployComposite.setVisible(true);
				deployEJBjar.setEnabled(true);
				deployComposite.layout();
			}
		}
	}

	/**
	 * Gets the data source Id.
	 * 
	 * @return the data source Id
	 */
	public String getDataSourceId() {
		return datasourceId;
	}

	/**
	 * Gets the application server name.
	 * 
	 * @return the application server name
	 */
	public String getApplicationServerName() {
		return applicationServerCombo.getItem(applicationServerCombo.getSelectionIndex());
	}

	/**
	 * Gets the ds unit name txt.
	 * 
	 * @return the ds unit name txt
	 */
	public Text getDsUnitNameTxt() {
		return dsUnitNameTxt;
	}

	/**
	 * Gets the pm bean class name txt.
	 * 
	 * @return the pm bean class name txt
	 */
	public Text getPmBeanClassNameTxt() {
		return pmBeanClassNameTxt;
	}

	/**
	 * Gets the lm bean class name txt.
	 * 
	 * @return the lm bean class name txt
	 */
	public Text getLmBeanClassNameTxt() {
		return lmBeanClassNameTxt;
	}

	/**
	 * Gets the ejb jar name txt.
	 * 
	 * @return the ejb jar name txt
	 */
	public Text getEjbJarNameTxt() {
		return ejbJarNameTxt;
	}

	/**
	 * Gets the deploy ej bjar.
	 * 
	 * @return the deploy ej bjar
	 */
	public Button getDeployEJBjar() {
		return deployEJBjar;
	}

	/**
	 * Open data source window.
	 */
	private void openDataSourceWindow(AdiFormToolkit toolkit) {
		DatasourceWindow datasourceWindow = new DatasourceWindow(toolkit, datasourceId, null);
		if (Dialog.OK == datasourceWindow.open()) {
			connectorTree = ScenarioUtil.getConnectorTree((String) null); // Reload connector tree
			datasourceComboCV.setInput(connectorTree.getDatasources().getDatasource());
			initDataSourceValues();
			datasourceComboCV.setSelection(new StructuredSelection(connectorTree.getDatasources().getDatasource().get(0)));
			setDataSourcesFieldText(datasourceWindow.getDataSource());
		}
	}

	private void setDataSourcesFieldText(DatasourceType dataSource) {
		datasourceId = dataSource.getId();
		scenarioCustomizationField.setValue(new KeyWordGenerator().evalExpression(dataSource.getCustomizationFile()));
	}

	/**
	 * Open data source window.
	 */
	private void openApplicationServerWindow() {
		if (IScenarioConstants.JSE.equals(applicationServerName))
			EngineTools.openDialog(MessageDialog.INFORMATION, getFromStudioBundle("scenario.applicationServer"),
					getFromStudioBundle("scenario.as.jse.no.parameter"));
		else {
			ApplicationServerWindow applicationServerWindow = new ApplicationServerWindow(applicationServerName, null);
			if (Dialog.OK == applicationServerWindow.open()) {
				connectorTree = ScenarioUtil.getConnectorTree((String) null); // Reload connector tree
			}

		}
	}

	/**
	 * Inits the data source values.
	 */
	private void initDataSourceValues() {
		datasourceComboCV.setInput(connectorTree.getDatasources().getDatasource());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.wizard.EmptyProjectPage#validatePage()
	 */
	@Override
	protected boolean validatePage() {
		if (!super.validatePage())
			return false;
		if (null == datasourceId) {
			setErrorMessage(getFromStudioBundle("studio.fullProject.noDatasource"));
			return false;
		}

		if (applicationServerCombo.getSelectionIndex() == -1) {
			setErrorMessage(getFromStudioBundle("studio.fullProject.selectApplicationSever"));
			return false;
		}
		String applicationServerName = applicationServerCombo.getItem(applicationServerCombo.getSelectionIndex());
		if (!IScenarioConstants.JSE.equals(applicationServerName)) {
			if (!classPattern.matcher(pmBeanClassNameTxt.getText()).matches()
					|| ClassNameValidator.JAVA_KEYWORDS.contains(pmBeanClassNameTxt.getText())) {
				setErrorMessage(getFromStudioBundle("studio.fullProject.invalidPmBeanClassName"));
				return false;
			}
			if (!classPattern.matcher(lmBeanClassNameTxt.getText()).matches()
					|| ClassNameValidator.JAVA_KEYWORDS.contains(lmBeanClassNameTxt.getText())) {
				setErrorMessage(getFromStudioBundle("studio.fullProject.invalidLmBeanClassName"));
				return false;
			}
			if (deploymentError) {
				setErrorMessage(getFromStudioBundle("studio.fullProject.applicationServer"));
				return false;
			}
		}
		Object validation = StudioUtil.validateCustomizationFile(scenarioCustomizationField.getValue(), null);
		if (null != validation && validation instanceof String) {
			setErrorMessage((String) validation);
			return false;
		}
		return true;
	}

	/**
	 * Checks if is connection ok.
	 *
	 * @param displayMessage
	 *            the display message
	 * @return true, if checks if is connection ok
	 */
	public boolean isConnectionOk(boolean displayMessage) {
		return connectorTree.testConnection(dataComposite.getShell(), datasourceId, displayMessage);
	}
}