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
package org.adichatz.scenario.impl;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.IncludeTreeWrapper;
import org.adichatz.generator.wrapper.IncludeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.wrapper.internal.ICollectionWrapper;
import org.adichatz.generator.xjc.ColumnFieldType;
import org.adichatz.generator.xjc.CrossReferenceType;
import org.adichatz.generator.xjc.CrossReferencesType;
import org.adichatz.generator.xjc.EmbeddedFieldType;
import org.adichatz.generator.xjc.EmbeddedIdFieldType;
import org.adichatz.generator.xjc.EntitySetType;
import org.adichatz.generator.xjc.GenerationEnum;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.RefFieldType;
import org.adichatz.generator.xjc.TabularType;
import org.adichatz.scenario.IPluginEntityScenario;
import org.adichatz.scenario.ITableScenario;
import org.adichatz.scenario.ScenarioInput;

// TODO: Auto-generated Javadoc
/**
 * The Class TableScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class ATabularScenario extends AScenario implements ITableScenario {

	/** The include tree. */
	protected IncludeTreeWrapper includeTree;

	/** The entity class. */
	protected Class<?> entityClass;

	/** The tabular. */
	protected TabularType tabular;

	/**
	 * Instantiates a new a tabular scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	protected ATabularScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.ITableScenario#createEntityTable(org.adichatz.scenario.ScenarioInput)
	 */
	@Override
	public void createEntityTable(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
		if (scenarioInput.isGenerateXml()) {
			includeTree = new IncludeTreeWrapper();

			entityClass = scenarioInput.getPluginEntityWrapper().getBeanClass();

			tabular = newTabular();
			tabular.setId("table");
			tabular.setEntityURI(scenarioInput.getPluginEntity().getEntityURI());
			includeTree.getElements().add(tabular);

			IncludeWrapper contextMenu = new IncludeWrapper();
			contextMenu.setAdiResourceURI("#PARAM(" + ParamMap.CONTEXT_MENU + ")");
			contextMenu.setId("tableContextMenu");
			tabular.getInclude().add(contextMenu);

			EntityTreeWrapper entityTree = getEntityTree();
			addColumns(entityTree);
			// CrossReference
			addCrossReferences(entityTree);

		} else
			includeTree = (IncludeTreeWrapper) new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);
		createXmlAndPrepare(scenarioInput.setXmlElement(includeTree));
	}

	/**
	 * Adds the columns.
	 * 
	 * @param entityTree
	 *            the entity tree
	 */
	protected void addColumns(EntityTreeWrapper entityTree) {
		for (PropertyFieldType propertyField : entityTree.getPropertyField()) {
			if ((propertyField instanceof RefFieldType) // Skip field when reference do not belongs to pluginResources scope
					&& null == scenarioInput.getScenarioResources().getPluginResources()
							.getPluginEntity(((RefFieldType) propertyField).getEntityURI()))
				continue;
			ColumnFieldType columnField = null;
			if (null != propertyField.getColumnField()) {
				columnField = addColumn(propertyField);
			} else if (propertyField instanceof EmbeddedIdFieldType) {
				for (EmbeddedFieldType embeddedField : ((EmbeddedIdFieldType) propertyField).getEmbeddedField()) {
					columnField = addColumn(embeddedField);
					columnField.setText(embeddedField.getControlField().getLabelText());
				}
			}
		}
	}

	/**
	 * Adds the column.
	 * 
	 * @param propertyField
	 *            the property field
	 * @return the column field type
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected ColumnFieldType addColumn(PropertyFieldType propertyField) {
		ColumnFieldType columnField = newColumnField();
		columnField.setId(propertyField.getId() + "TC");

		ColumnFieldType entityColumnField = propertyField.getColumnField();
		columnField.setProperty(entityColumnField.getProperty());
		columnField.setColumnText(entityColumnField.getColumnText());
		columnField.setColumnImage(entityColumnField.getColumnImage());
		columnField.setColumnValueType(entityColumnField.getColumnValueType());
		columnField.setColumnValue(entityColumnField.getColumnValue());
		columnField.setColumnImage(entityColumnField.getColumnImage());
		columnField.setPattern(entityColumnField.getPattern());
		columnField.setColumnBackground(entityColumnField.getColumnBackground());
		columnField.setColumnForeground(entityColumnField.getColumnForeground());
		columnField.setColumnFont(entityColumnField.getColumnFont());
		columnField.setSorted(entityColumnField.isSorted());

		((ICollectionWrapper) tabular).getElements().add(columnField);
		if (null == tabular.getSortedColumn())
			tabular.setSortedColumn(columnField.getId());

		return columnField;
	}

	/**
	 * Adds the cross references.
	 * 
	 * @param entityTree
	 *            the entity tree
	 */
	protected void addCrossReferences(EntityTreeWrapper entityTree) {
		IPluginEntityScenario pluginEntityScenario = scenarioInput.getScenarioResources().getPluginEntityScenario();
		CrossReferencesType crossReferences = new CrossReferencesType();
		PluginEntityWrapper pluginEntity = scenarioInput.getPluginEntityWrapper(entityTree.getEntityURI());
		String bundleResource = EngineTools.lowerCaseFirstLetter(pluginEntity.getEntityId());
		for (EntitySetType entitySet : entityTree.getEntitySet()) {
			PluginEntityWrapper childPluginEntity = scenarioInput.getPluginEntityWrapper(entitySet.getEntityURI());
			if (null != childPluginEntity // Could be null is pluginEntity is not selected from model
					&& null != childPluginEntity.getGenerationUnit(GenerationEnum.TABLE)) {
				CrossReferenceType crossReference = new CrossReferenceType();
				crossReference.setEntitySetId(entitySet.getId());
				String pluginKey = scenarioInput.getScenarioResources().getPluginName();
				crossReference.setAxmlDetailURI(EngineTools.getAdiResourceURI(pluginKey,
						pluginEntityScenario.getSubPackage(childPluginEntity, GenerationEnum.DETAIL),
						pluginEntityScenario.getTreeId(childPluginEntity, GenerationEnum.DETAIL)));
				crossReference.setAxmlTableURI(EngineTools.getAdiResourceURI(pluginKey,
						pluginEntityScenario.getSubPackage(childPluginEntity, GenerationEnum.TABLE),
						pluginEntityScenario.getTreeId(childPluginEntity, GenerationEnum.TABLE)));
				crossReference.setAxmlQueryURI(EngineTools.getAdiResourceURI(childPluginEntity.getEntityKeys()[0],
						pluginEntityScenario.getSubPackage(childPluginEntity, GenerationEnum.QUERY),
						pluginEntityScenario.getTreeId(childPluginEntity, GenerationEnum.QUERY)));

				crossReference.setDescription("#MSG(" + bundleResource + ", " + entitySet.getId() + ")");

				crossReferences.getCrossReference().add(crossReference);
			}
		}
		if (!crossReferences.getCrossReference().isEmpty())
			tabular.setCrossReferences(crossReferences);
	}

	/**
	 * New tabular.
	 * 
	 * @return the tabular type
	 */
	protected abstract TabularType newTabular();

	/**
	 * New column field.
	 * 
	 * @return the column field type
	 */
	protected abstract ColumnFieldType newColumnField();

}
