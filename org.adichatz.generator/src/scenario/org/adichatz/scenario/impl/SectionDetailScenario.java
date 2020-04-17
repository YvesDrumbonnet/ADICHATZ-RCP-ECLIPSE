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
package org.adichatz.scenario.impl;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.IncludeTreeWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.SectionWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.generator.xjc.DirtyContainerType;
import org.adichatz.generator.xjc.EmbeddedFieldType;
import org.adichatz.generator.xjc.EmbeddedIdFieldType;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.ListenerType;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.generator.xjc.ListenersType;
import org.adichatz.generator.xjc.OneToOneType;
import org.adichatz.generator.xjc.OneToOneTypeEnum;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.RefFieldType;
import org.adichatz.scenario.IDetailScenario;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class SectionDetailScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class SectionDetailScenario extends AScenario implements IDetailScenario {

	/**
	 * Instantiates a new a Section detail scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public SectionDetailScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IDetailScenario#createEntityDetailFields(org.adichatz.scenario.ScenarioInput)
	 */
	@Override
	public void createEntityDetailFields(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
		IncludeTreeWrapper includeTree;
		if (scenarioInput.isGenerateXml()) {
			includeTree = new IncludeTreeWrapper();
			includeTree.setEntityURI(scenarioInput.getPluginEntity().getEntityURI());

			DirtyContainerType detailContainer = newDetailContainer(includeTree);
			detailContainer.setId("detailContainer");
			detailContainer
					.setLayout(addLayout(scenarioMap.get(DETAIL_LC), scenarioMap.get(DETAIL_CC), scenarioMap.get(DETAIL_RC)));

			includeTree.getElements().add(detailContainer);

			IncludeWrapper toolBar = new IncludeWrapper();
			toolBar.setAdiResourceURI("getToolBarURI((String) #PARAM(" + ParamMap.TOOL_BAR_TYPE + "))");

			toolBar.setId("detailToolbarMenu");
			((ICollectionWrapper) detailContainer).getElements().add(toolBar);
			addParam(toolBar, ParamMap.CONTROLLER, "#CONTROLLER(detailContainer)");
			addParam(toolBar, ParamMap.TOOL_BAR_TYPE, "#PARAM(" + ParamMap.TOOL_BAR_TYPE + ")", true);
			for (PropertyFieldType propertyField : getEntityTree().getPropertyField()) {
				if ((propertyField instanceof RefFieldType) // Skip field when reference do not belongs to pluginResources scope
						&& null == scenarioInput.getScenarioResources().getPluginResources()
								.getPluginEntity(((RefFieldType) propertyField).getEntityURI()))
					continue;

				if (propertyField instanceof EmbeddedIdFieldType) {
					Class<?> idClass = FieldTools
							.getGetMethod(scenarioInput.getPluginEntityWrapper().getBeanClass(), propertyField.getId(), true)
							.getReturnType();
					for (EmbeddedFieldType embeddedField : ((EmbeddedIdFieldType) propertyField).getEmbeddedField()) {
						ControlFieldType controlField = addControlField(idClass, detailContainer, embeddedField,
								propertyField.getId());
						controlField.setLabelText(embeddedField.getControlField().getLabelText());
						controlField.setConvertModelToTarget(embeddedField.getControlField().getConvertModelToTarget());
					}
				} else {
					addControlField(scenarioInput.getPluginEntityWrapper().getBeanClass(), detailContainer, propertyField,
							propertyField.getId());
				}
			}
			for (OneToOneType oneToOne : getEntityTree().getOneToOne())
				if (OneToOneTypeEnum.SLAVE == oneToOne.getOneToOneType()) {
					ListenersType listeners = null;
					listeners = new ListenersType();
					detailContainer.setListeners(listeners);
					ListenerType listener = new ListenerType();
					listener.setId(oneToOne.getId() + "IELsnr");
					listener.setListenerTypes(ListenerTypeEnum.BEFORE_ENTITY_INJECTION.name());
					listener.setCode("getComposite().setEnabled(null != getEntity());");
					listeners.getListener().add(listener);

					listener = new ListenerType();
					listener.setId(oneToOne.getId() + "AELCLsnr");
					listener.setListenerTypes(ListenerTypeEnum.AFTER_END_LIFE_CYCLE.name());
					StringBuffer codeSB = new StringBuffer("getComposite().setEnabled(null != getEntity());\n");
					codeSB.append("getControl().setData(\"ONE_TO_ONE\", \""
							+ EngineTools.lowerCaseFirstLetter(scenarioInput.getPluginEntity().getEntityId()) + "\");");
					listener.setCode(codeSB.toString());
					listeners.getListener().add(listener);
				}
		} else
			includeTree = (IncludeTreeWrapper) new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);
		createXmlAndPrepare(scenarioInput.setXmlElement(includeTree));
	}

	public DirtyContainerType newDetailContainer(IncludeTreeWrapper includeTree) {
		includeTree.setCoreClassName("org.adichatz.engine.core.ASectionCore");
		SectionWrapper detailContainer = new SectionWrapper();
		detailContainer.setText("#MSG(" + EngineTools.lowerCaseFirstLetter(scenarioInput.getPluginEntity().getEntityId())
				+ ", detailContainerText)");
		return detailContainer;
	}

	/**
	 * Adds the data field.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param detailContainer
	 *            the section
	 * @param field
	 *            the field
	 * @param column
	 *            the column
	 * 
	 * @return the data field type
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected ControlFieldType addControlField(Class<?> entityClass, DirtyContainerType detailContainer, PropertyFieldType field,
			String column) {
		ControlFieldType controlField = field.getControlField();
		controlField.setProperty(column);
		controlField.setId(field.getId());
		((ICollectionWrapper) detailContainer).getElements().add(controlField);
		return controlField;
	}

}
