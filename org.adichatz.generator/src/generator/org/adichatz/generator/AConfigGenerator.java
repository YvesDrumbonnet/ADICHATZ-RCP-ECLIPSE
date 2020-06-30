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
package org.adichatz.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.generator.tools.AListenerTypeManager;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.tools.CodeGenerationUtil;
import org.adichatz.generator.wrapper.ClientCanvasWrapper;
import org.adichatz.generator.wrapper.ControlFieldWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.internal.IColumnWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IWidgetWrapper;
import org.adichatz.generator.xjc.ConfigType;
import org.adichatz.generator.xjc.CustomizationsType;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.generator.xjc.IncludeType;
import org.adichatz.generator.xjc.ResourceBundleType;
import org.adichatz.generator.xjc.SetType;
import org.adichatz.generator.xjc.TabularType;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class AConfigGenerator.
 */
public abstract class AConfigGenerator extends ACollectionGenerator {

	private String prefix;

	private int listenerRank = 0;

	/**
	 * Instantiates a new a config generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	protected AConfigGenerator(ScenarioInput scenarioInput, ACodeGenerator parentGenerator) {
		super(scenarioInput, parentGenerator);
	}

	protected void addResourceBundleTranslation(ConfigType config) {
		if (null != config && null != config.getResourceBundles())
			for (ResourceBundleType loadBundle : config.getResourceBundles().getResourceBundle()) {
				scenarioInput.getBundleTranslation().put(loadBundle.getVar(), loadBundle.getBasename());
			}
	}

	/**
	 * Adds the config customizations.
	 * 
	 * @param config
	 *            the config
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void addConfigCustomizations(ConfigType config) throws IOException {
		if (null != config.getCustomizations())
			addCustomizations(config.getCustomizations(), "addCustomization");
	}

	public void addCustomizations(CustomizationsType customizations, String addMethod) throws IOException {
		for (ElementType element : customizations.getScrolledFormOrClientCanvasOrSection()) {
			Class<?> controllerClass;
			if (element instanceof IElementWrapper && !EngineTools.isEmpty(((IElementWrapper) element).getControllerClassName())) {
				controllerClass = scenarioInput.getScenarioResources().getGencodePath()
						.getClazz(((IElementWrapper) element).getControllerClassName());
			}
			if (element instanceof ControlFieldWrapper)
				controllerClass = AFieldController.class;
			else if (element instanceof ClientCanvasWrapper)
				controllerClass = AEntityManagerController.class;
			else
				controllerClass = getControllerClass((IElementWrapper) element);

			prefix = "((" + controllerClass.getSimpleName() + ") controller)";

			Map<LIFECYCLE_STAGE, ListenerBuffer> listenerBufferMap = new HashMap<LIFECYCLE_STAGE, ListenerBuffer>();
			listenerBufferMap.put(LIFECYCLE_STAGE.AFTER_INSTANTIATE_CONTROLLER,
					new ListenerBuffer("beforeEntityInject", LIFECYCLE_STAGE.AFTER_INSTANTIATE_CONTROLLER, 2));
			listenerBufferMap.put(LIFECYCLE_STAGE.BEFORE_ENTITY_INJECTION,
					new ListenerBuffer("beforeEntityInject", LIFECYCLE_STAGE.BEFORE_ENTITY_INJECTION, 2));
			listenerBufferMap.put(LIFECYCLE_STAGE.AFTER_ENTITY_INJECTION,
					new ListenerBuffer("beforeEntityInject", LIFECYCLE_STAGE.AFTER_ENTITY_INJECTION, 2));
			listenerBufferMap.put(LIFECYCLE_STAGE.BEFORE_INITIALIZE,
					new ListenerBuffer("beforeInitialize", LIFECYCLE_STAGE.BEFORE_INITIALIZE, 2));
			listenerBufferMap.put(LIFECYCLE_STAGE.AFTER_INITIALIZE,
					new ListenerBuffer("afterInitialize", LIFECYCLE_STAGE.AFTER_INITIALIZE, 2));
			listenerBufferMap.put(LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL,
					new ListenerBuffer("beforeCreateControl", LIFECYCLE_STAGE.BEFORE_CREATE_CONTROL, 3));
			listenerBufferMap.put(LIFECYCLE_STAGE.AFTER_CREATE_CONTROL,
					new ListenerBuffer("afterCreateControl", LIFECYCLE_STAGE.AFTER_CREATE_CONTROL, 3));
			listenerBufferMap.put(LIFECYCLE_STAGE.AFTER_START_LIFE_CYCLE,
					new ListenerBuffer("afterStartLifeCycle", LIFECYCLE_STAGE.AFTER_START_LIFE_CYCLE, 3));
			listenerBufferMap.put(LIFECYCLE_STAGE.BEFORE_END_LIFE_CYCLE,
					new ListenerBuffer("beforeEndLifeCycle", LIFECYCLE_STAGE.BEFORE_END_LIFE_CYCLE, 3));
			listenerBufferMap.put(LIFECYCLE_STAGE.BEFORE_SYNCHRONIZE,
					new ListenerBuffer("beforeSynchronize", LIFECYCLE_STAGE.BEFORE_SYNCHRONIZE, 3));
			listenerBufferMap.put(LIFECYCLE_STAGE.AFTER_SYNCHRONIZE,
					new ListenerBuffer("afterSynchronize", LIFECYCLE_STAGE.AFTER_SYNCHRONIZE, 3));
			listenerBufferMap.put(LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE,
					new ListenerBuffer("afterEndLifeCycle", LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE, 3));
			boolean addControllerListener = false;

			if (element instanceof IWidgetWrapper && null != ((IWidgetWrapper) element).getListeners()) {
				CodeGenerationUtil.addListenersCode(listenerBufferMap.get(LIFECYCLE_STAGE.BEFORE_ENTITY_INJECTION).buffer,
						((IWidgetWrapper) element), prefix.concat("."), AListenerTypeManager.BEFORE_ENTITY_INJECTION);
				CodeGenerationUtil.addListenersCode(listenerBufferMap.get(LIFECYCLE_STAGE.AFTER_ENTITY_INJECTION).buffer,
						((IWidgetWrapper) element), prefix.concat("."), AListenerTypeManager.AFTER_ENTITY_INJECTION);
				CodeGenerationUtil.addListenersCode(listenerBufferMap.get(LIFECYCLE_STAGE.AFTER_CREATE_CONTROL).buffer,
						((IWidgetWrapper) element), prefix.concat("."), AListenerTypeManager.AFTER_CREATE_CONTROL_GROUP);
				CodeGenerationUtil.addListenersCode(listenerBufferMap.get(LIFECYCLE_STAGE.AFTER_INITIALIZE).buffer,
						((IWidgetWrapper) element), prefix.concat("."), AListenerTypeManager.LIFE_CYCLE);
				CodeGenerationUtil.addListenersCode(listenerBufferMap.get(LIFECYCLE_STAGE.AFTER_END_LIFE_CYCLE).buffer,
						((IWidgetWrapper) element), prefix.concat("."), AListenerTypeManager.AFTER_END_LIFE_CYCLE_GROUP);

			}
			List<ListenerBuffer> listenerBuffers = new ArrayList<ListenerBuffer>(listenerBufferMap.values());
			Collections.sort(listenerBuffers, new Comparator<ListenerBuffer>() {
				@Override
				public int compare(ListenerBuffer o1, ListenerBuffer o2) {
					return o1.rank - o2.rank;
				}
			});
			if (element instanceof IWidgetWrapper || element instanceof IColumnWrapper)
				CodeGenerationUtil.addAccessibilities(listenerBufferMap.get(LIFECYCLE_STAGE.AFTER_INITIALIZE).buffer,
						(IElementWrapper) element, "controller", true, true);
			if (element instanceof SetType) {
				SetType set = (SetType) element;
				if (set instanceof TabularType && null != ((TabularType) set).getContentProvider()) {
					String controllerName = scenarioInput.getScenarioResources().getSimpleControllerName(set, imports);
					BufferCode afterCreateControlBuffer = listenerBufferMap.get(LIFECYCLE_STAGE.AFTER_CREATE_CONTROL).buffer;
					afterCreateControlBuffer.append(CodeGenerationUtil.addContentProvider(afterCreateControlBuffer,
							((TabularType) set).getContentProvider()) + ".setTabularController((" + controllerName
							+ ") controller);");
				}
				if (!set.getInclude().isEmpty()) {
					String controllerClassNameName = scenarioInput.getScenarioResources().getSimpleControllerName(set, imports);
					BufferCode afterCreateControlBuffer = listenerBufferMap.get(LIFECYCLE_STAGE.AFTER_CREATE_CONTROL).buffer;
					String controllerName = EngineTools
							.lowerCaseFirstLetter(controllerClassNameName + "$$" + getControllerSequence());
					afterCreateControlBuffer.append(
							controllerClassNameName + " " + controllerName + " = (" + controllerClassNameName + ") controller;");
					for (IncludeType include : set.getInclude())
						buildControl(afterCreateControlBuffer, (IncludeWrapper) include, true, controllerName);
				}
			}
			int customIndex = 1;
			for (ListenerBuffer listenerBuffer : listenerBuffers) {
				keyWordGenerator.processElement(listenerBuffer.buffer, (IElementWrapper) element, element.getClass(),
						listenerBuffer.lifeCycle, prefix.concat("."));
				if (listenerBuffer.buffer.length() > 0) {
					classBodyBuffer.appendPlus(addMethod + "(\"" + element.getId() + "\", new "
							+ getObjectName(AControlListener.class) + "(\"Customization#" + customIndex++ + "\", "
							+ getObjectName(IEventType.class) + "." + listenerBuffer.lifeCycle + ") {");
					classBodyBuffer.append("@Override");
					classBodyBuffer.appendPlus("public void handleEvent(" + getObjectName(AdiEvent.class) + " event) {");
					switch (listenerBuffer.lifeCycle) {
					case BEFORE_CREATE_CONTROL:
					case AFTER_CREATE_CONTROL:
					case AFTER_START_LIFE_CYCLE:
					case BEFORE_END_LIFE_CYCLE:
					case BEFORE_SYNCHRONIZE:
					case AFTER_SYNCHRONIZE:
					case AFTER_END_LIFE_CYCLE:
						classBodyBuffer.appendPlus("if (controller.isValid()) {");
						break;
					}
					classBodyBuffer.append(listenerBuffer.buffer.getStringBuffer());
					switch (listenerBuffer.lifeCycle) {
					case BEFORE_CREATE_CONTROL:
					case AFTER_CREATE_CONTROL:
					case AFTER_START_LIFE_CYCLE:
					case BEFORE_END_LIFE_CYCLE:
					case BEFORE_SYNCHRONIZE:
					case AFTER_SYNCHRONIZE:
					case AFTER_END_LIFE_CYCLE:
						classBodyBuffer.appendMinus("}");
						break;
					}
					classBodyBuffer.appendMinus("}");
					classBodyBuffer.appendMinus("});");

					addControllerListener = -1 < listenerBuffer.buffer.getStringBuffer()
							.indexOf("(".concat(controllerClass.getSimpleName()).concat(")"));
				}
			}
			if (addControllerListener)
				classBodyBuffer.getGenerator().getObjectName(controllerClass);
		}
	}

	@Override
	public String getThis() {
		return prefix;
	}

	class ListenerBuffer {
		protected BufferCode buffer;

		protected String bufferKey;

		protected LIFECYCLE_STAGE lifeCycle;

		protected int rank;

		public ListenerBuffer(String bufferKey, LIFECYCLE_STAGE lifeCycle, int ident) {
			this.bufferKey = bufferKey;
			this.buffer = new BufferCode(AConfigGenerator.this, classBodyBuffer.getIdent() + ident, bufferKey, true);
			this.lifeCycle = lifeCycle;
			this.rank = listenerRank++;
		}

	}
}
