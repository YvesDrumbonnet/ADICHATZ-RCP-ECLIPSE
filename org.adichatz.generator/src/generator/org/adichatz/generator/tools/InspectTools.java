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
package org.adichatz.generator.tools;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.RegisterEntry;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.ContainerTreeWrapper;
import org.adichatz.generator.wrapper.ExtendTreeWrapper;
import org.adichatz.generator.wrapper.IncludeTreeWrapper;
import org.adichatz.generator.wrapper.PartTreeWrapper;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.wrapper.internal.IRootWrapper;
import org.adichatz.generator.wrapper.internal.IWidgetWrapper;
import org.adichatz.generator.xjc.ConfigType;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class InspectTools.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class InspectTools {

	/** The scenario input. */
	private ScenarioInput scenarioInput;

	private ISearch search;

	private Set<String> resourceBundleURIs = new HashSet<String>();

	public static enum SearchCaseEnum {
		VARIABLE, BUNDLE;
	}

	/**
	 * Instantiates a new inspect tools.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	private InspectTools(ScenarioInput scenarioInput, ITreeWrapper treeWrapper, SearchCaseEnum searchCase) {
		this.treeWrapper = treeWrapper;
		this.scenarioInput = scenarioInput;
		switch (searchCase) {
		case VARIABLE:
			search = new SearchVariable();
			break;
		case BUNDLE:
			search = new SearchBundle();
			break;
		}
	}

	/** The tree wrapper. */
	private ITreeWrapper treeWrapper;

	public static Set<String> inspectBundle(ScenarioInput scenarioInput) {
		ITreeWrapper treeWrapper = new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);
		InspectTools inspectTools = new InspectTools(scenarioInput, treeWrapper, SearchCaseEnum.BUNDLE);
		inspectTools.inspectAttribute(treeWrapper);
		return inspectTools.resourceBundleURIs;
	}

	/**
	 * Inspect form editor tree.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param partTree
	 *            the form part tree
	 */
	public static void inspectPartTree(ScenarioInput scenarioInput, PartTreeWrapper partTree) {
		InspectTools inspectTools = new InspectTools(scenarioInput, partTree, SearchCaseEnum.VARIABLE);
		new TransformTreeTools(scenarioInput, partTree);
		inspectTools.inspectConfig(partTree.getConfig());
		for (ElementType element : partTree.getElements()) {
			inspectTools.inspectAttribute(element);
		}
	}

	/**
	 * Inspect form editor tree.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param includeTree
	 *            the include tree
	 */
	public static void inspectIncludeTree(ScenarioInput scenarioInput, IncludeTreeWrapper includeTree) {
		InspectTools inspectTools = new InspectTools(scenarioInput, includeTree, SearchCaseEnum.VARIABLE);

		new TransformTreeTools(scenarioInput, includeTree);
		inspectTools.inspectConfig(includeTree.getConfig());
		inspectTools.inspectAttribute(includeTree);
	}

	/**
	 * Inspect extend tree.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param extendTree
	 *            the extend tree
	 */
	public static void inspectExtendTree(ScenarioInput scenarioInput, ExtendTreeWrapper extendTree) {
		InspectTools inspectTools = new InspectTools(scenarioInput, extendTree, SearchCaseEnum.VARIABLE);

		new TransformTreeTools(scenarioInput, extendTree);
		inspectTools.inspectConfig(extendTree.getConfig());
		inspectTools.inspectAttribute(extendTree);
	}

	/**
	 * Inspect container tree.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 * @param containerTree
	 *            the container tree
	 */
	public static void inspectContainerTree(ScenarioInput scenarioInput, ContainerTreeWrapper containerTree) {
		InspectTools inspectTools = new InspectTools(scenarioInput, containerTree, SearchCaseEnum.VARIABLE);

		new TransformTreeTools(scenarioInput, containerTree);
		inspectTools.inspectConfig(containerTree.getConfig());
		for (ElementType element : containerTree.getElements()) {
			inspectTools.inspectAttribute(element);
		}
	}

	/**
	 * Inspect config.
	 * 
	 * @param config
	 *            the config
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void inspectConfig(ConfigType config) {
		if (null != config) {
			inspectAttribute(config);
		}
	}

	/**
	 * Inspect attribute.
	 * 
	 * @param attribute
	 *            the attribute
	 * 
	 */
	private void inspectAttribute(Object attribute) {
		try {
			if (attribute instanceof String) {
				search.search((String) attribute);
			} else {
				Class<?> attributeClass;
				if (attribute instanceof IElementWrapper) {
					IElementWrapper element = (IElementWrapper) attribute;
					attributeClass = EngineTools.getChildXjcClass(element);
				} else
					attributeClass = attribute.getClass();
				while (EngineTools.isXmlType(attributeClass)) {
					inspectField(attributeClass, attribute);
					attributeClass = attributeClass.getSuperclass();
				}
			}
		} catch (IllegalArgumentException e) {
			logError(e);
		}
	}

	/**
	 * Inspect field.
	 * 
	 * @param attributeClass
	 *            the attribute class
	 * @param attribute
	 *            the attribute
	 */
	private void inspectField(Class<?> attributeClass, Object attribute) {
		for (Field field : attributeClass.getDeclaredFields())
			if (!Modifier.isStatic(field.getModifiers())) {
				boolean accessible = field.canAccess(attribute);
				field.setAccessible(true);
				Object fieldValue;
				try {
					fieldValue = field.get(attribute);
					if (null != fieldValue) {
						if (fieldValue instanceof List) {
							for (Object element : (List<?>) fieldValue) {
								inspectAttribute(element);
							}
						} else if (fieldValue instanceof IElementWrapper || EngineTools.isXmlType(fieldValue.getClass())) {
							inspectAttribute(fieldValue);
						} else if (fieldValue instanceof String) {
							search.search((String) fieldValue);
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logError(e);
				}
				field.setAccessible(accessible);
			}
	}

	interface ISearch {
		void search(String expression);
	}

	class SearchVariable implements ISearch {
		public void search(String expression) {
			try {
				int index = 0;
				while (index != -1) {
					int[] ind = new int[9];
					ind[0] = expression.indexOf("#CONTROLLER(", index);
					ind[1] = expression.indexOf("#C(", index);
					ind[2] = expression.indexOf("#CONTROL_CONTROLLER(", index);
					ind[3] = expression.indexOf("#CC(", index);
					ind[4] = expression.indexOf("#CONTROL(", index);
					ind[5] = expression.indexOf("#FV(", index);
					ind[6] = expression.indexOf("#FIELD_VALUE(", index);
					ind[7] = expression.indexOf("#BEAN(", index);
					ind[8] = expression.indexOf("#B(", index);
					int minValue = -1;
					for (int i = 0; i < ind.length; i++)
						if (ind[i] != -1) {
							if (-1 == minValue || ind[i] < minValue)
								minValue = ind[i];
						}
					index = minValue;
					int endIndex = expression.indexOf(")", index);
					if (index != -1) {
						String variable = expression.substring(expression.indexOf("(", index) + 1, endIndex);
						int commaPos = variable.indexOf(',');
						if (commaPos > 0) {
							variable = variable.substring(0, commaPos).trim();
						}
						writePutEntryRegister(expression, variable);
						index = endIndex + 1;
					}
				}
			} catch (Exception e) {
				logError("Expression " + expression + " is malformed!", e);
			}
		}

		/**
		 * Write "putEntryinRegister(...)" if necessary.
		 * 
		 * @param expression
		 *            the expression
		 * @param variable
		 *            the variable
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void writePutEntryRegister(String expression, String variable) {
			IElementWrapper element = EvalTools.getElementWrapper((IRootWrapper) treeWrapper, variable);
			if (null != element) {
				String className;
				if (element instanceof IWidgetWrapper && !EngineTools.isEmpty(((IWidgetWrapper) element).getControllerClassName()))
					className = ((IWidgetWrapper) element).getControllerClassName();
				else
					className = scenarioInput.getScenarioResources().getController(element);
				if (null == className)
					throw new RuntimeException(
							"No controller class found for wrapper class '" + element.getClass().getName() + "'!");
				Class<?> elementClass = scenarioInput.getScenarioResources().getGencodePath().getClazz(className, false);
				scenarioInput.getRegister().put(variable, new RegisterEntry(elementClass));
			}
		}
	}

	class SearchBundle implements ISearch {
		@Override
		public void search(String expression) {
			try {
				int index = expression.indexOf("#MSG(", 0);
				if (-1 == index)
					index = expression.indexOf("#MESSAGE(", 0);
				if (-1 == index)
					index = expression.indexOf("#M(", 0);
				if (-1 != index) {
					int endIndex = expression.indexOf(")", index);
					String messageBundleURI = expression.substring(expression.indexOf("(", index) + 1, endIndex);
					int commaPos = messageBundleURI.indexOf(',');
					if (commaPos > 0)
						messageBundleURI = messageBundleURI.substring(0, commaPos).trim();
					resourceBundleURIs.add(messageBundleURI);
				}
			} catch (Exception e) {
				logError("Expression " + expression + " is malformed!", e);
			}
		}
	}
}
