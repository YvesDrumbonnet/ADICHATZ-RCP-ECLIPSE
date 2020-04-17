/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant 
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.studio.xjc.custom;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.extra.AFormInputDialog;
import org.adichatz.engine.extra.AdiFormInput;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.validation.ABindingListener;
import org.adichatz.engine.validation.FormBindingService;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.wrapper.ApplicationServerWrapper;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.xjc.ApplicationServerType;
import org.adichatz.generator.xjc.ParamType;
import org.adichatz.generator.xjc.ParamsType;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationServerWindow.
 */
public class ApplicationServerWindow extends AFormInputDialog {
	private static String title = getFromStudioBundle("scenario.applicationServer.update");

	private static Image image = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_SERVERS.gif");

	private static String CONNECTOR_NAME_FIELD = "name";

	private static String CONNECTOR_DESCRIPTION_FIELD = "description";

	private static String NAMING_URL_PKGS_FIELD = "java.naming.factory.url.pkgs";

	private static String NAMING_INITIAL_FIELD = "java.naming.factory.initial";

	private static String NAMING_PROVIDER_URL_FIELD = "java.naming.provider.url";

	private static String REMOTE_HOST_FIELD = "remote.connection.default.host";

	private static String REMOTE_PORT_FIELD = "remote.connection.default.port";

	/** The binding service. */
	private FormBindingService bindingService;

	private Button okButton;

	private ContainerController container;

	private String connectorURI;

	private String connectorApplicationServer;

	private ConnectorTreeWrapper connectorTree;

	private ApplicationServerType applicationServer;

	/**
	 * Instantiates a new default ref text dialog.
	 * 
	 * @param parentShell
	 *            the parent shell
	 */
	public ApplicationServerWindow(String connectorApplicationServer, String connectorURI) {
		super(Display.getCurrent().getActiveShell(), AdichatzApplication.getInstance().getFormToolkit(), title, image, null);
		this.connectorApplicationServer = connectorApplicationServer;
		this.connectorURI = connectorURI;
	}

	@Override
	protected void postCreateContent() {
		scrolledForm.getBody().setLayout(new MigLayout("wrap", "grow,fill", "grow,fill"));
		ParamMap paramMap = new ParamMap();
		paramMap.put("CONNECTOR", connectorApplicationServer);
		formInput = new AdiFormInput(null, "adi://org.adichatz.studio/editor/ApplicationServerManagerPart", paramMap);
		container = new ContainerController(formInput.getPluginResources(), managedForm, scrolledForm.getBody(), null, null);
		bindingService = new FormBindingService(container);
		container.setBindingService(bindingService);
		formInput.createContents(container);
		if (null != connectorApplicationServer) {
			connectorTree = ScenarioUtil.getConnectorTree(connectorURI);
			applicationServer = connectorTree.getApplicationServer(connectorApplicationServer);
			copyValue(CONNECTOR_NAME_FIELD, connectorApplicationServer);
			if (null != applicationServer) {
				copyValue(CONNECTOR_DESCRIPTION_FIELD, applicationServer.getDescription());
				if (null == applicationServer.getParams())
					applicationServer.setParams(new ParamsType());
				for (ParamType param : applicationServer.getParams().getParam()) {
					if (IScenarioConstants.AS_HOME_FIELD.equals(param.getId()))
						copyValue(IScenarioConstants.AS_HOME_FIELD, param.getValue());
					else if (IScenarioConstants.AS_DEPLOYMENT_DIR_FIELD.equals(param.getId()))
						copyValue(IScenarioConstants.AS_DEPLOYMENT_DIR_FIELD, param.getValue());
					else if (ApplicationServerWrapper.AS_MODULES_DIRECTORY.equals(param.getId()))
						copyValue(ApplicationServerWrapper.AS_MODULES_DIRECTORY, param.getValue());
					else if (NAMING_INITIAL_FIELD.equals(param.getId()))
						copyValue(NAMING_INITIAL_FIELD, param.getValue());
					else if (NAMING_PROVIDER_URL_FIELD.equals(param.getId()))
						copyValue(NAMING_PROVIDER_URL_FIELD, param.getValue());
					else if (NAMING_URL_PKGS_FIELD.equals(param.getId()))
						copyValue(NAMING_URL_PKGS_FIELD, param.getValue());
					else if (REMOTE_HOST_FIELD.equals(param.getId()))
						copyValue(REMOTE_HOST_FIELD, param.getValue());
					else if (REMOTE_PORT_FIELD.equals(param.getId()))
						copyValue(REMOTE_PORT_FIELD, param.getValue());
				}
			}
			((AEntityManagerController) formInput.getPartCore().getController()).validateFields();
		}
		bindingService.addBindingListener(new ABindingListener(IEventType.POST_MESSAGE) {
			@Override
			public void handleEvent(AdiEvent event) {
				okButton.setEnabled(bindingService.getErrorMessageMap().isEmpty());
			}
		});
		scrolledForm.setText("");
	}

	// @Override
	// protected Control createContents(Composite parent) {
	// Control control = super.createContents(parent);
	// getShell().setSize(600, 500);
	// return control;
	// }
	//
	private void copyValue(String fieldName, String value) {
		((AFieldController) formInput.getPartCore().getFromRegister(fieldName)).setValue(value);
	}

	private String getFieldValue(String fieldName) {
		AFieldController fieldController = (AFieldController) formInput.getPartCore().getFromRegister(fieldName);
		return (String) fieldController.getValue();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, getFromStudioBundle("scenario.as.save.connector"), true);
		okButton.setEnabled(false);

		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		bindingService.fireListener(IEventType.POST_MESSAGE);
		scrolledForm.reflow(true);
	}

	private void addProperty(Properties properties, String key, String fieldName) {
		Object object = formInput.getPartCore().getFromRegister(fieldName);
		if (object instanceof AFieldController) {
			Object value = ((AFieldController) object).getValue();
			if (null != value)
				properties.put(key, value);
		}

	}

	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			applicationServer.setName(getFieldValue(CONNECTOR_NAME_FIELD));
			applicationServer.setDescription(getFieldValue(CONNECTOR_DESCRIPTION_FIELD));
			if (null == applicationServer.getParams())
				applicationServer.setParams(new ParamsType());

			updateParam(applicationServer, IScenarioConstants.AS_HOME_FIELD, IScenarioConstants.AS_HOME_FIELD);
			updateParam(applicationServer, IScenarioConstants.AS_DEPLOYMENT_DIR_FIELD, IScenarioConstants.AS_DEPLOYMENT_DIR_FIELD);
			updateParam(applicationServer, ApplicationServerWrapper.AS_MODULES_DIRECTORY,
					ApplicationServerWrapper.AS_MODULES_DIRECTORY);

			updateParam(applicationServer, NAMING_INITIAL_FIELD, NAMING_INITIAL_FIELD);
			updateParam(applicationServer, NAMING_PROVIDER_URL_FIELD, NAMING_PROVIDER_URL_FIELD);
			updateParam(applicationServer, NAMING_URL_PKGS_FIELD, NAMING_URL_PKGS_FIELD);

			updateParam(applicationServer, REMOTE_HOST_FIELD, REMOTE_HOST_FIELD);
			updateParam(applicationServer, REMOTE_PORT_FIELD, REMOTE_PORT_FIELD);

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
			addProperty(properties, IScenarioConstants.AS_HOME_FIELD, IScenarioConstants.AS_HOME_FIELD);
			addProperty(properties, IScenarioConstants.AS_DEPLOYMENT_DIR_FIELD, IScenarioConstants.AS_DEPLOYMENT_DIR_FIELD);

			addProperty(properties, NAMING_INITIAL_FIELD, NAMING_INITIAL_FIELD);
			addProperty(properties, NAMING_PROVIDER_URL_FIELD, NAMING_PROVIDER_URL_FIELD);
			addProperty(properties, NAMING_URL_PKGS_FIELD, NAMING_URL_PKGS_FIELD);

			addProperty(properties, REMOTE_HOST_FIELD, REMOTE_HOST_FIELD);
			addProperty(properties, REMOTE_PORT_FIELD, REMOTE_PORT_FIELD);
		}
	}

	private void updateParam(ApplicationServerType applicationServer, String propertyName, String fieldName) {
		boolean done = false;

		for (ParamType param : applicationServer.getParams().getParam())
			if (propertyName.equals(param.getId())) {
				param.setValue(getFieldValue(fieldName));
				done = true;
				break;
			}
		if (!done) {
			ParamType param = new ParamType();
			param.setId(propertyName);
			param.setValue(getFieldValue(fieldName));
			applicationServer.getParams().getParam().add(param);
		}
	}

	public ApplicationServerType getApplicationServer() {
		return applicationServer;
	}
}
