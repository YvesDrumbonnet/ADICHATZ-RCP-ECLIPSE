/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.studio.xjc.controller;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.QueryPreferenceWrapper;
import org.adichatz.generator.wrapper.QueryTreeWrapper;
import org.adichatz.generator.wrapper.internal.IEntityContainerWrapper;
import org.adichatz.generator.wrapper.internal.IJointure;
import org.adichatz.generator.xjc.JointureType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.QueryParameterType;
import org.adichatz.generator.xjc.RefFieldType;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.adichatz.studio.xjc.editor.XjcTreeFormEditor;
import org.eclipse.jface.dialogs.IMessageProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyTextController.
 */
public class PropertyTextController extends ATextController {

	/** The entity URI. */
	private String entityURI = null;

	private AValidator propertyValidator;

	private AEntityListener propertyListener;

	/**
	 * Instantiates a new property text controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public PropertyTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.TextController#createControl()
	 */
	@Override
	public void createControl() {
		super.createControl();
		if (((XjcBindingService) getBindingService()).getEditor() instanceof XjcTreeFormEditor) {
			propertyValidator = new AValidator(this, "studio.invalid.property.name.key") {
				@Override
				public int validate() {
					XjcTreeElement element = ((XjcEntity<?>) getEntity()).getTreeElement();
					String property = (String) getValue();
					if (null == property && element.getElement() instanceof QueryParameterType) {
						QueryParameterType parameter = (QueryParameterType) element.getElement();
						if (!getProposals().contains(parameter.getId()))
							return setLevel(IMessageProvider.ERROR,
									getFromStudioBundle("studio.queryParameter.property.not.specified", parameter.getId()));
					} else if (null != property && -1 == property.indexOf('#') && !getProposals().contains(property))
						return setLevel(Integer.parseInt(store.getString(IStudioConstants.LEVEL_PROPERTY_NAME)),
								getFromStudioBundle("studio.invalid.property.name", property));
					return setLevel(IMessageProvider.NONE, null);
				}
			};
			addValidator(propertyValidator);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.controller.ATextController#getProposals()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List getProposals() {
		QueryTreeWrapper queryTreeWrapper = null;
		XjcTreeElement element = ((XjcEntity<?>) getEntity()).getTreeElement();
		if (null == proposals) {
			if (element.getElement() instanceof QueryParameterType) {
				QueryParameterType parameter = (QueryParameterType) element.getElement();
				if (null == parameter.getSuffix()) {
					logError(getFromStudioBundle("studio.queryParameter.suffix.error", parameter.getId()));
					return new ArrayList();
				}
				QueryPreferenceWrapper preference = (QueryPreferenceWrapper) element.getParentElement().getElement();
				queryTreeWrapper = preference.getQueryTreeWrapper(element.getParentElement());
				if (null != queryTreeWrapper) {
					queryTreeWrapper.initJointureEntityURI();
					for (IJointure jointure : queryTreeWrapper.getAllJointures())
						if (parameter.getSuffix().equals(jointure.getSuffix())) {
							entityURI = jointure.getEntityURI();
							break;
						}
				}
			} else if (element.getElement() instanceof JointureType) {
				JointureType jointure = (JointureType) element.getElement();
				if (EngineTools.isEmpty(jointure.getEntityURI())) {
					queryTreeWrapper = null;
					XjcTreeElement parentElement = element.getParentElement();
					while (null != parentElement) {
						if (parentElement.getElement() instanceof QueryTreeWrapper) {
							queryTreeWrapper = (QueryTreeWrapper) parentElement.getElement();
							break;
						}
						parentElement = parentElement.getParentElement();
					}
					queryTreeWrapper.initJointureEntityURI();
				}
				entityURI = ((IJointure) element.getParentElement().getElement()).getEntityURI();
			} else {
				XjcTreeElement parentElement = element.getParentElement();
				/*
				 * Search for close entityId
				 * 
				 * when entitId is found, search for the pluginKey which is the closest value or the plugin name use by the
				 * pluginResources used to open the file.
				 */
				while (null != parentElement) {
					Object bean = parentElement.getEntity().getBean();
					if (bean instanceof IEntityContainerWrapper && null != ((IEntityContainerWrapper) bean).getEntityURI()) {
						entityURI = null != entityURI ? entityURI : ((IEntityContainerWrapper) bean).getEntityURI();
						if (null != entityURI)
							break;
					} else if (bean instanceof EntityTreeWrapper) {
						entityURI = ((EntityTreeWrapper) bean).getEntityURI();
						break;
					} else if (bean instanceof PluginEntityWrapper) {
						entityURI = ((PluginEntityWrapper) bean).getEntityURI();
						break;
					}
					parentElement = parentElement.getParentElement();
				}
			}
			proposals = new ArrayList();
			if (null != entityURI) {
				AdiPluginResources modelPR = AdichatzApplication.getPluginResources(EngineTools.getInstanceKeys(entityURI)[0]);
				modelPR.getPluginEntityTree(); // Initializes PluginEntityTree if needed
				PluginEntity pluginEntity = modelPR.getPluginEntityTree().getEntityMM(entityURI).getPluginEntity();
				String modelPluginName = pluginEntity.getPluginEntityTree().getPluginResources().getPluginName();
				ScenarioResources modelSR = ScenarioResources.getInstance(modelPluginName, null);
				EntityTreeWrapper entityTree = null;
				entityTree = (EntityTreeWrapper) new GeneratorUnit(new ScenarioInput(modelSR, pluginEntity))
						.getTreeWrapperFromXml(true);
				if (null != entityTree)
					for (PropertyFieldType propertyField : entityTree.getPropertyField()) {
						if (propertyField instanceof RefFieldType || !(element.getElement() instanceof JointureType))
							proposals.add(propertyField.getId());
					}
			}
			if (element.getElement() instanceof QueryParameterType) {
				if (null == propertyListener) {
					propertyListener = new AEntityListener(parentController, IEventType.AFTER_PROPERTY_CHANGE) {
						@Override
						public void handleEntityEvent(AdiEntityEvent event) {
							if ("suffix".equals(event.getPropertyName()) || "entityURI".equals(event.getPropertyName())) {
								proposals = null;
								propertyValidator.validate();
							}
						}
					};
				}
			}
		}
		if (null != queryTreeWrapper)
			queryTreeWrapper.clearPluginEntity(queryTreeWrapper);
		return proposals;
	}
}
