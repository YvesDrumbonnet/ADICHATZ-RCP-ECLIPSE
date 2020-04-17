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

import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.common.ejb.ICompositeKeyStrategy;
import org.adichatz.engine.action.AAction;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.IFieldBindManager;
import org.adichatz.engine.extra.IOutlinePage;
import org.adichatz.engine.indigo.editor.AdiFormPage;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.ARunnableListener;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.engine.widgets.supplement.AHyperlinkRunnable;
import org.adichatz.generator.AClassGenerator;
import org.adichatz.generator.tools.AListenerTypeManager;
import org.adichatz.generator.wrapper.internal.IGeneratorEntry;
import org.adichatz.generator.xjc.ControllerType;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.generator.xjc.GenerationScenarioType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ListenerType;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.generator.xjc.RcpPartType;
import org.adichatz.scenario.IDetailScenario;
import org.adichatz.scenario.IEditorScenario;
import org.adichatz.scenario.IEntityScenario;
import org.adichatz.scenario.IMessageBundleScenario;
import org.adichatz.scenario.INavigatorScenario;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.IPojoRewriter;
import org.adichatz.scenario.IQueryScenario;
import org.adichatz.scenario.ITableScenario;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.impl.AScenario;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.util.StudioUtil;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.editor.InternalTreeFormEditor;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;

@SuppressWarnings({ "rawtypes" })
public class ClassTextController extends ATextController implements IClassNameController {
	private static Map<String, String> superTypeNameMap = new HashMap<String, String>();

	static {
		superTypeNameMap.put("actionClassName", AAction.class.getName());
		superTypeNameMap.put("bindingServiceClassName", ABindingService.class.getName());
		superTypeNameMap.put("classNameFC", ControllerCore.class.getName());
		superTypeNameMap.put("compositeKeyStrategyFactoryClassName", ICompositeKeyStrategy.class.getName());
		superTypeNameMap.put("controllerClassName", AController.class.getName());
		superTypeNameMap.put("fieldBindManagerClassName", IFieldBindManager.class.getName());
		superTypeNameMap.put("comparatorClassName", Comparator.class.getName());
		superTypeNameMap.put("formPageClassName", AdiFormPage.class.getName());
		superTypeNameMap.put("generatorClassName", AClassGenerator.class.getName());
		superTypeNameMap.put("labelProviderClass", LabelProvider.class.getName());
		superTypeNameMap.put("outlinePageClassName", IOutlinePage.class.getName());
		superTypeNameMap.put("runnableClassName", AHyperlinkRunnable.class.getName());
		superTypeNameMap.put("scenarioClassName", AScenario.class.getName());
		superTypeNameMap.put("rewriterClassName", IPojoRewriter.class.getName());
		superTypeNameMap.put("treeClassName", IGeneratorEntry.class.getName());
		superTypeNameMap.put("validatorClassName", AValidator.class.getName());
		superTypeNameMap.put("wrapperClassName", ElementType.class.getName());
	}

	private ScenarioResources scenarioResources;

	public ClassTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	public String getSuperTypeName() {
		scenarioResources = ((XjcBindingService) getBindingService()).getEditor().getScenarioResources();
		if ("controllerClassName".equals(getProperty()) && !(getEntity().getBean() instanceof ControllerType)) {
			if (!scenarioResources.isParametersLoaded())
				scenarioResources.loadScenarioParameters();
			// getBeanClass() ==> TextType (incorrect) << --- >> getBean().getClass() ==> TextWrapper.class (correct)
			return scenarioResources.getController(getEntity().getBean());
		} else if ("scenarioClassName".equals(getProperty())) {
			if (getEntity().getBean() instanceof GenerationUnitType)
				switch (((GenerationUnitType) getEntity().getBean()).getType()) {
				case DETAIL:
					return IDetailScenario.class.getName();
				case EDITOR:
					return IEditorScenario.class.getName();
				case ENTITY:
					return IEntityScenario.class.getName();
				case MESSAGE_BUNDLE:
					return IMessageBundleScenario.class.getName();
				case TABLE:
					return ITableScenario.class.getName();
				case QUERY:
					return IQueryScenario.class.getName();
				case NAVIGATOR:
					return INavigatorScenario.class.getName();
				default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
					break;
				}
			else if (getEntity().getBean() instanceof RcpPartType) {
				return INavigatorScenario.class.getName();
			} else if (getEntity().getBean() instanceof GenerationScenarioType) {
				return IPluginEntityScenario.class.getName();
			}
			return AScenario.class.getName();
		} else if ("listenerClassName".equals(getProperty())) {
			ListenerTypeEnum listenerType = AListenerTypeManager.LISTENER_TYPE_MAP
					.get(((ListenerType) getEntity().getBean()).getListenerTypes());
			if (null != listenerType) {
				switch (listenerType) {
				case MODIFY_TEXT:
					return ModifyListener.class.getName();
				case WIDGET_SELECTED:
					return SelectionListener.class.getName();
				case CHECK_STATE:
					return ICheckStateListener.class.getName();
				case SELECTION_CHANGED:
				case POST_SELECTION_CHANGED:
					return ISelectionChangedListener.class.getName();
				case DOUBLE_CLICK:
					return IDoubleClickListener.class.getName();
				case PRE_RUN:
				case POST_RUN:
					return ARunnableListener.class.getName();
				case AFTER_INITIALIZE:
				case AFTER_CREATE_CONTROL:
				case AFTER_DISPOSE:
				case AFTER_SYNCHRONIZE:
				case BEFORE_CREATE_CONTROL:
				case BEFORE_DISPOSE:
				case REFRESH:
				case BEFORE_SYNCHRONIZE:
				case AFTER_FIELD_CHANGE:
				case BEFORE_END_LIFE_CYCLE:
				case AFTER_END_LIFE_CYCLE:
				case BEFORE_FIELD_CHANGE:
				case BEFORE_ENTITY_INJECTION:
				case POST_CREATE_PART:
					return AControlListener.class.getName();
				case CHANGE_STATUS:
				case WHEN_PROPERTY_CHANGE:
				case PRE_SAVE:
				case POST_SAVE:
				case PRE_REFRESH:
				case POST_REFRESH:
				case LOCK_ENTITY:
				case BEFORE_PROPERTY_CHANGE:
				case AFTER_PROPERTY_CHANGE:
					return AEntityListener.class.getName();
				default: // to prevent WARNING (https://bugs.eclipse.org/bugs/show_bug.cgi?id=374605)
					break;
				}
				LogBroker.displayError(getFromStudioBundle("studio.search.class.error"),
						getFromStudioBundle("studio.no.class.for.listener.type", listenerType));
			}
			return null;
		} else {
			String superClassName = superTypeNameMap.get(getProperty());
			if (null == superClassName)
				LogBroker.displayError(getFromStudioBundle("studio.search.class.error"),
						getFromGeneratorBundle("generation.controller.class.error", getProperty()));
			return superClassName;
		}
	}

	@Override
	public void createControl() {
		super.createControl();
		if (!(((XjcBindingService) getBindingService()).getEditor() instanceof InternalTreeFormEditor)) {
			addValidator(new AValidator(this, "studio.controller.class.key") {
				@Override
				public int validate() {
					String className = (String) getValue();
					if (null != className && className.startsWith("bundleclass://")) {
						String[] instanceKeys = EngineTools.getInstanceKeys(className);
						className = instanceKeys[2];
					}
					if (control.isEnabled() && null != className && !getProposals().contains(className))
						return setLevel(Integer.parseInt(store.getString(IStudioConstants.LEVEL_CONTROLLER_CLASS)),
								getFromStudioBundle("studio.controller.class", className));
					else
						return setLevel(IMessageProvider.NONE, null);
				}
			});
		}
	}

	public List getProposals() {
		if (null == proposals) {
			String superTypeName = getSuperTypeName(); // scenarioResources is initialized in the called method
			proposals = StudioUtil.getHierarchy(scenarioResources, superTypeName);
			Collections.sort(proposals);
		}
		return proposals;
	}

	public void clearProposals() {
		proposals = null;
	}

	/**
	 * Gets the scenario resources.
	 * 
	 * @return the scenario resources
	 */
	public ScenarioResources getScenarioResources() {
		return scenarioResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void modifyValues(String value) {
		getProposals().add(value);
		broadCastedSetValue(value);
	}

	@Override
	public String getCurrentClassName() {
		return (String) getValue();
	}
}
