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
package org.adichatz.studio.xjc.controller;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.APageController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.SectionController;
import org.adichatz.engine.controller.field.CheckBoxController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.generator.common.GeneratorUtil;
import org.adichatz.generator.wrapper.GenerationScenarioWrapper;
import org.adichatz.generator.wrapper.ModelPartWrapper;
import org.adichatz.generator.wrapper.ScenarioTreeWrapper;
import org.adichatz.generator.xjc.ConnectorTree;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.ModelProcurementEnum;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelCheckBoxController.
 */
public class ModelCheckBoxController extends CheckBoxController {

	/** The selection listener. */
	private SelectionAdapter selectionListener;

	private GenerationScenarioWrapper generationScenario;

	/**
	 * Instantiates a new model check box controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public ModelCheckBoxController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#synchronize()
	 */
	@Override
	public void synchronize() {
		super.synchronize();
		generationScenario = (GenerationScenarioWrapper) parentController.getEntity().getBean();
		ScenarioResources scenarioResources = ((XjcBindingService) getBindingService()).getEditor().getScenarioResources();
		if (null != selectionListener)
			getControl().removeSelectionListener(selectionListener);
		selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				scenarioResources.setImpactModel(true);
				dynamicCreation(true);
				IEntity<?> entity = parentController.getEntity();
				entity.getEntityMM().getDataAccess().lock(getBindingService(), IEntityConstants.MERGE, entity);
			}
		};
		getControl().addSelectionListener(selectionListener);
		setValue(null != ((GenerationScenarioWrapper) getEntity().getBean()).getModelPart());
		dynamicCreation(false); // model part is true only if refreshing editor. In this cas endLifeCycle is assumed by parent
								// controller
	}

	private void dynamicCreation(boolean endLifeCycle) {
		// needed to avoid error on miglayout computation on disposed control
		APageController pageController = (APageController) genCode.getFromRegister("modelPage");
		EngineTools.reinitMiglayout(pageController.getComposite());
		SectionController modelSectionController = (SectionController) genCode.getFromRegister("modelSection");
		if (getValue()) {
			ModelPartType modelPart = generationScenario.getModelPart();
			if (null == modelPart) {
				ScenarioResources scenarioResources = ((XjcBindingService) getBindingService()).getEditor().getScenarioResources();
				ConnectorTree connectorTree = ScenarioUtil
						.getConnectorTree(scenarioResources.getParam(IScenarioConstants.CONNECTORS_URI));
				modelPart = new ModelPartWrapper();
				if (null != connectorTree.getDatasources())
					modelPart.setConnectorDataSource(connectorTree.getDatasources().getDatasource().get(0).getId());
				if (null != connectorTree.getApplicationServers())
					modelPart.setConnectorASVersion(connectorTree.getApplicationServers().getApplicationServer().get(0).getName());
				ScenarioTreeWrapper scenarioTree = (ScenarioTreeWrapper) pageController.getEntity().getBean();
				String pluginPackageName = GeneratorUtil.getParamValue(scenarioTree.getParams(), IScenarioConstants.PLUGIN_PACKAGE);
				modelPart.setModelPackageName(pluginPackageName + ".model");
				modelPart.setModelProcurement(ModelProcurementEnum.HIBERNATE);
				generationScenario.setModelPart(modelPart);
				generationScenario.initModelPart(scenarioResources);
			}
			modelSectionController.getEntityInjection()
					.initialize(modelSectionController.getPluginResources().getDataAccess().getDataCache().fetchEntity(modelPart));
			modelSectionController.dynamicCreateControl(endLifeCycle, true);
			modelSectionController.reflowControllers();
		} else if (null != modelSectionController.getControl()) {
			modelSectionController.disposeControl();
			modelSectionController.reflowControllers();
		}
	}

}