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
package org.adichatz.studio.xjc.controller;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.ComboController;
import org.adichatz.engine.controller.field.FileTextController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.generator.KeyWordGenerator;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.ConnectorTreeWrapper;
import org.adichatz.generator.wrapper.DatasourceWrapper;
import org.adichatz.generator.xjc.DatasourceType;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.custom.DatasourceWindow;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;

// TODO: Auto-generated Javadoc
/**
 * The Class DatasourceComboController.
 */
@SuppressWarnings({ "rawtypes" })
public class DatasourceComboController extends ComboController {
	private List values;

	private List displayedValues;

	private ConnectorTreeWrapper connectorTree;

	/**
	 * Instantiates a new column text controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public DatasourceComboController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.TextController#createControl()
	 */
	@Override
	public void createControl() {
		labelProvider = new LabelProvider() {
			@Override
			public String getText(Object element) {
				DatasourceWrapper datasource = connectorTree.getDatasource((String) element);
				StringBuffer dataSourceSB = new StringBuffer(datasource.getDatasourceName());
				if (!EngineTools.isEmpty(datasource.getDescription()))
					dataSourceSB.append(" - ").append(datasource.getDescription());
				return dataSourceSB.toString();
			}
		};
		super.createControl();
		comboViewer.addSelectionChangedListener((e) -> {
			getControl().setEnabled(enabled);
			IStructuredSelection selection = (IStructuredSelection) e.getSelection();
			if (!selection.isEmpty()) {
				for (DatasourceType datasource : connectorTree.getDatasources().getDatasource()) {
					String datasourceId = (String) selection.getFirstElement();
					if (datasourceId.equals(datasource.getId())) {
						FileTextController customFileFT = (FileTextController) genCode.getFromRegister("customFileName");
						customFileFT.setValue(new KeyWordGenerator().evalExpression(datasource.getCustomizationFile()));
						break;
					}
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.ComboController#broadcastChange()
	 */
	public void broadcastChange() {
		String datasourceName = (String) getValue();
		if (null != datasourceName) {
			TextController jndiTC = (TextController) genCode.getFromRegister("jndi");
			if (null != jndiTC && null != jndiTC.getControl() && !jndiTC.getControl().isDisposed())
				jndiTC.setValue(GeneratorUtil.getJNDI(datasourceName));
		}
	}

	public void openDataSourceWindow() {
		String title = (String) getValue();
		if (null != title) {
			ScenarioResources scenarioResources = ((XjcBindingService) getBindingService()).getEditor().getScenarioResources();
			DatasourceWindow datasourceWindow = new DatasourceWindow(title,
					scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI));
			if (Dialog.OK == datasourceWindow.open()) {
				String datasourceId = datasourceWindow.getDataSource().getId();
				comboViewer.setInput(getInput());
				comboViewer.setSelection(new StructuredSelection(datasourceId));
				for (DatasourceType datasource : connectorTree.getDatasources().getDatasource())
					if (datasource.getId().equals(datasourceId)) {
						comboViewer.setSelection(new StructuredSelection(datasource));
					}
				EngineTools.openDialog(MessageDialog.INFORMATION, title,
						getFromStudioBundle("scenario.connector.save.message", datasourceId));
			}
		}
	}

	public void testConnection() {
		connectorTree.testConnection(null, (String) getValue(), true);
	}

	@SuppressWarnings("unchecked")
	private void initValues() {
		ScenarioResources scenarioResources = ((XjcBindingService) getBindingService()).getEditor().getScenarioResources();
		connectorTree = ScenarioUtil.getConnectorTree(scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI));
		values = new ArrayList();
		displayedValues = new ArrayList();
		if (null != connectorTree.getDatasources())
			for (DatasourceType datasource : connectorTree.getDatasources().getDatasource()) {
				values.add(datasource.getId());
				displayedValues.add(EngineTools.isEmpty(datasource.getDescription()) ? datasource.getDatasourceName()
						: datasource.getDatasourceName().concat(" - ").concat(datasource.getDescription()));
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ARefController#getValues()
	 */
	@Override
	public List getValues() {
		if (null == values)
			initValues();
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.ARefController#getDisplayedValues()
	 */
	@Override
	public List getDisplayedValues() {
		if (null == displayedValues)
			initValues();
		return displayedValues;
	}
}
