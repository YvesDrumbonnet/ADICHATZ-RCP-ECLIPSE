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
package org.adichatz.studio.xjc.custom;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.adichatz.common.encoding.Base64;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.extra.AFormInputDialog;
import org.adichatz.engine.extra.AdiFormInput;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.ABindingListener;
import org.adichatz.engine.validation.FormBindingService;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.xjc.DatasourceType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.generation.ConnectionUtil;
import org.adichatz.scenario.generation.ISqlConnection;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class DatasourceWindow.
 */
public class DatasourceWindow extends AFormInputDialog {
	private static Image addDataSourceImage = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
			"IMG_DATASOURCE_ADD.png");

	private static Image updateDataSourceImage = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
			"IMG_DATASOURCE_UPDATE.png");

	private static String addTitle = getFromStudioBundle("scenario.datasource.add");

	private static String updateTitle = getFromStudioBundle("scenario.datasource.update");

	private static String CONNECTOR_ID_FIELD = "id";

	private static String CONNECTOR_NAME_FIELD = "name";

	private static String CONNECTOR_DRIVER_FIELD = "driver";

	private static String CONNECTOR_DESCRIPTION_FIELD = "description";

	private static String CUSTOMIZATION_FILE_FIELD = "customizationFile";

	private static String DIALECT_FIELD = "dialect";

	private static String DRIVER_CLASS_FIELD = "connectionDriverClass";

	private static String CONNECTION_URL_FIELD = "connectionUrl";

	private static String CONNECTION_USERNAME_FIELD = "connectionUsername";

	private static String CONNECTION_PASSWORD_FIELD = "connectionPassword";

	private static String CONNECTION_SCHEMA_FIELD = "connectionSchema";

	private static String JDBC_DRIVER_JAR_FIELD = "jdbcDriverJar";

	private static String REVENG_FILE_FIELD = "revengFile";

	private static String XA_DATASOURCE_CLASS = "xaDataSourceClass";

	/** The binding service. */
	private FormBindingService bindingService;

	private Button testButton;

	private Button okButton;

	private ContainerController container;

	private String connectorDataSource;

	private ConnectorTreeWrapper connectorTree;

	private DatasourceWrapper dataSource;

	private boolean saveNew = true;

	private String connectorURI;

	/**
	 * Instantiates a new default ref text dialog.
	 * 
	 * @param parentShell
	 *            the parent shell
	 */
	public DatasourceWindow(AdiFormToolkit toolkit, String connectorDataSource, String connectorURI) {
		super(Display.getCurrent().getActiveShell(), toolkit, "", addDataSourceImage, null);
		this.connectorDataSource = connectorDataSource;
		this.connectorURI = connectorURI;
	}

	@Override
	protected void postCreateContent() {
		scrolledForm.getBody().setLayout(new MigLayout("wrap", "grow,fill", "grow,fill"));
		ParamMap paramMap = new ParamMap();
		paramMap.put("CONNECTOR", connectorDataSource);
		formInput = new AdiFormInput(null, "adi://org.adichatz.studio/editor/DatasourceManagerPart", paramMap);
		container = new ContainerController(formInput.getPluginResources(), managedForm, scrolledForm.getBody(), null, null);
		bindingService = new FormBindingService(container);
		container.setBindingService(bindingService);
		formInput.createContents(container);
		if (null != connectorDataSource) {
			connectorTree = ScenarioUtil.getConnectorTree(connectorURI);
			dataSource = connectorTree.getDatasource(connectorDataSource);
			copyValue(CONNECTOR_ID_FIELD, connectorDataSource);
			copyValue(CONNECTOR_NAME_FIELD, dataSource.getDatasourceName());
			if (null != dataSource) {
				copyValue(CONNECTOR_DRIVER_FIELD, dataSource.getDriver());
				copyValue(CONNECTOR_DESCRIPTION_FIELD, dataSource.getDescription());
				copyValue(CUSTOMIZATION_FILE_FIELD, new KeyWordGenerator().evalExpression(dataSource.getCustomizationFile()));
				if (null == dataSource.getParams())
					dataSource.setParams(new ParamsType());
				for (ParamType param : dataSource.getParams().getParam()) {
					if (ISqlConnection.DIALECT.equals(param.getId()))
						copyValue(DIALECT_FIELD, param.getValue());
					else if (ISqlConnection.DRIVER_CLASS.equals(param.getId()))
						copyValue(DRIVER_CLASS_FIELD, param.getValue());
					else if (ISqlConnection.CONNECTION_URL.equals(param.getId()))
						copyValue(CONNECTION_URL_FIELD, param.getValue());
					else if (ISqlConnection.CONNECTION_USERNAME.equals(param.getId()))
						copyValue(CONNECTION_USERNAME_FIELD, param.getValue());
					else if (ISqlConnection.CONNECTION_PASSWORD.equals(param.getId()))
						copyValue(CONNECTION_PASSWORD_FIELD, param.getValue());
					else if (ISqlConnection.CONNECTION_SCHEMA.equals(param.getId()))
						copyValue(CONNECTION_SCHEMA_FIELD, param.getValue());
					else if (ISqlConnection.JDBC_DRIVER_JAR.equals(param.getId()))
						copyValue(JDBC_DRIVER_JAR_FIELD,
								new KeyWordGenerator().evalExpression(
										new BufferCode(ScenarioResources.getInstance(GeneratorConstants.STUDIO_BUNDLE, null)),
										param.getValue(), false, false));
					else if (ISqlConnection.REVENG_FILE.equals(param.getId()))
						copyValue(REVENG_FILE_FIELD,
								new KeyWordGenerator().evalExpression(
										new BufferCode(ScenarioResources.getInstance(GeneratorConstants.STUDIO_BUNDLE, null)),
										param.getValue(), false, false));
					else if (ISqlConnection.XA_DATASOURCE_CLASS.equals(param.getId()))
						copyValue(XA_DATASOURCE_CLASS, param.getValue());
				}
			}
			((AEntityManagerController) formInput.getPartCore().getController()).validateFields();
		}
		final TextController idFLD = (TextController) formInput.getPartCore().getFromRegister(CONNECTOR_ID_FIELD);
		idFLD.getControl().addModifyListener((e) -> {
			if (connectorDataSource.isEmpty() || !connectorDataSource.equals(idFLD.getControl().getText().trim())) {
				if (!saveNew) {
					saveNew = true;
					getShell().setText(addTitle);
					getShell().setImage(addDataSourceImage);
				}
			} else {
				if (saveNew) {
					saveNew = false;
					getShell().setText(updateTitle);
					getShell().setImage(updateDataSourceImage);
				}
			}
		});
		idFLD.getControl().notifyListeners(SWT.Modify, null);
		bindingService.addBindingListener(new ABindingListener(IEventType.POST_MESSAGE) {
			@Override
			public void handleEvent(AdiEvent event) {
				okButton.setEnabled(bindingService.getErrorMessageMap().isEmpty());
				testButton.setEnabled(bindingService.getErrorMessageMap().isEmpty());
			}
		});
	}

	private void copyValue(String fieldName, String value) {
		if (null != value)
			((AFieldController) formInput.getPartCore().getFromRegister(fieldName)).setValue(value);
	}

	private String getFieldValue(String fieldName) {
		AFieldController fieldController = (AFieldController) formInput.getPartCore().getFromRegister(fieldName);
		return (String) fieldController.getValue();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		testButton = createButton(parent, IDialogConstants.OPEN_ID, getFromStudioBundle("studio.testConnection"), true);
		testButton.setEnabled(false);
		okButton = createButton(parent, IDialogConstants.OK_ID, getFromStudioBundle("scenario.datasource.save.connector"), true);
		okButton.setEnabled(false);

		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		bindingService.fireListener(IEventType.POST_MESSAGE);
	}

	private void addProperty(Properties properties, String key, String fieldName) {
		Object object = formInput.getPartCore().getFromRegister(fieldName);
		if (object instanceof AFieldController) {
			Object value = ((AFieldController) object).getValue();
			if (ISqlConnection.CONNECTION_PASSWORD.equals(key))
				try {
					value = Base64.decrypt((String) value);
				} catch (Exception e) {
					logError(e);
				}
			if (null != value)
				properties.put(key, value);
		}

	}

	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			List<DatasourceType> datasources = connectorTree.getDatasources().getDatasource();
			int index = datasources.indexOf(dataSource);
			dataSource = new DatasourceWrapper();
			dataSource.setId(getFieldValue(CONNECTOR_ID_FIELD));
			dataSource.setDatasourceName(getFieldValue(CONNECTOR_NAME_FIELD));
			dataSource.setDriver(getFieldValue(CONNECTOR_DRIVER_FIELD));
			dataSource.setDescription(getFieldValue(CONNECTOR_DESCRIPTION_FIELD));
			dataSource.setCustomizationFile(getFieldValue(CUSTOMIZATION_FILE_FIELD));
			if (null == dataSource.getParams())
				dataSource.setParams(new ParamsType());
			dataSource.getParams().getParam().clear();
			addParam(dataSource, ISqlConnection.DIALECT, DIALECT_FIELD);
			addParam(dataSource, ISqlConnection.DRIVER_CLASS, DRIVER_CLASS_FIELD);
			addParam(dataSource, ISqlConnection.CONNECTION_URL, CONNECTION_URL_FIELD);
			addParam(dataSource, ISqlConnection.CONNECTION_USERNAME, CONNECTION_USERNAME_FIELD);
			addParam(dataSource, ISqlConnection.CONNECTION_PASSWORD, CONNECTION_PASSWORD_FIELD);
			addParam(dataSource, ISqlConnection.CONNECTION_SCHEMA, CONNECTION_SCHEMA_FIELD);
			addParam(dataSource, ISqlConnection.XA_DATASOURCE_CLASS, XA_DATASOURCE_CLASS);
			ParamType param = new ParamType();
			param.setId(ISqlConnection.JDBC_DRIVER_JAR);
			String jarFileName = getFieldValue(JDBC_DRIVER_JAR_FIELD);
			param.setValue(jarFileName);
			dataSource.getParams().getParam().add(param);
			String revengFileName = getFieldValue(REVENG_FILE_FIELD);
			if (!EngineTools.isEmpty(revengFileName)) {
				param = new ParamType();
				param.setId(ISqlConnection.REVENG_FILE);
				param.setValue(revengFileName);
				dataSource.getParams().getParam().add(param);
			}

			if (saveNew)
				datasources.add(dataSource);
			else {
				datasources.remove(index);
				datasources.add(index, dataSource);
			}

			if (null == connectorURI)
				connectorURI = GeneratorConstants.DEFAULT_CONNECTORS_URI;
			String[] keys = EngineTools.getContributionURIToStrings(connectorURI);
			URL url = Platform.getBundle(keys[0]).getEntry(keys[1]);
			try {
				ScenarioUtil.createXmlFile(new File(FileLocator.toFileURL(url).getFile()), connectorTree);
				okPressed();
			} catch (IOException e) {
				logError(e);
			}
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			cancelPressed();
		} else if (IDialogConstants.OPEN_ID == buttonId) {
			Properties properties = new Properties();
			addProperty(properties, ISqlConnection.DIALECT, DIALECT_FIELD);
			addProperty(properties, ISqlConnection.DRIVER_CLASS, DRIVER_CLASS_FIELD);
			addProperty(properties, ISqlConnection.CONNECTION_URL, CONNECTION_URL_FIELD);
			addProperty(properties, ISqlConnection.CONNECTION_USERNAME, CONNECTION_USERNAME_FIELD);
			addProperty(properties, ISqlConnection.CONNECTION_PASSWORD, CONNECTION_PASSWORD_FIELD);
			addProperty(properties, ISqlConnection.CONNECTION_SCHEMA, CONNECTION_SCHEMA_FIELD);
			addProperty(properties, ISqlConnection.XA_DATASOURCE_CLASS, XA_DATASOURCE_CLASS);
			addProperty(properties, ISqlConnection.JDBC_DRIVER_JAR, JDBC_DRIVER_JAR_FIELD);
			ConnectionUtil.testConnection(properties, true);
		}
	}

	private void addParam(DatasourceType dataSource, String propertyName, String fieldName) {
		ParamType param = new ParamType();
		param.setId(propertyName);
		param.setValue(getFieldValue(fieldName));
		dataSource.getParams().getParam().add(param);
	}

	public DatasourceType getDataSource() {
		return dataSource;
	}
}
