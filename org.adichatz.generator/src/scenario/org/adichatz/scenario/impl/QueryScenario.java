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
package org.adichatz.scenario.impl;

// *********************
// *** C A U T I O N ***
// *********************
//
// this class is dynamically loaded in the application No reference will be found in the application
//

import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.generator.common.GeneratorUnit;
import org.adichatz.generator.wrapper.QueryTreeWrapper;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.JointureType;
import org.adichatz.generator.xjc.JointureTypeEnum;
import org.adichatz.generator.xjc.QueryParameterType;
import org.adichatz.generator.xjc.QueryPreferenceType;
import org.adichatz.generator.xjc.QueryTypeEnum;
import org.adichatz.scenario.IQueryScenario;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.util.ScenarioUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryScenario.
 * 
 * @author Yves Drumbonnet
 */
public class QueryScenario extends AScenario implements IQueryScenario {

	protected QueryTreeWrapper queryTree;

	/**
	 * Instantiates a new query scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public QueryScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.scenario.IQueryScenario#createQueryManager(org.adichatz.scenario.ScenarioInput)
	 */
	@Override
	public void createQueryManager(ScenarioInput scenarioInput) {
		this.scenarioInput = scenarioInput;
		if (scenarioInput.isGenerateXml()) {
			queryTree = generateXmlFile(scenarioInput);
		} else
			queryTree = (QueryTreeWrapper) new GeneratorUnit(scenarioInput).getTreeWrapperFromXml(true);
		createXmlAndPrepare(scenarioInput.setXmlElement(queryTree));
	}

	protected QueryTreeWrapper generateXmlFile(ScenarioInput scenarioInput) {
		QueryTreeWrapper queryTree;
		queryTree = new QueryTreeWrapper();
		queryTree.setQueryType(QueryTypeEnum.JQL);

		Map<String, Integer> suffixMap = new HashMap<String, Integer>();
		queryTree.setEntityURI(scenarioInput.getPluginEntity().getEntityURI());
		String suffix = EngineTools.getSuffix(scenarioInput.getPluginEntity().getEntityKeys()[2], suffixMap);
		queryTree.setSuffix(suffix);
		QueryPreferenceType queryPreference = new QueryPreferenceType();
		queryTree.setQueryPreference(queryPreference);
		if (null != scenarioInput.getPluginEntityWrapper().getQueryBuilder())
			queryTree.setQueryBuilder(scenarioInput.getPluginEntityWrapper().getQueryBuilder());
		List<Method> methods = Arrays.asList(scenarioInput.getPluginEntityWrapper().getBeanClass().getDeclaredMethods());
		Collections.sort(methods, new Comparator<Method>() {
			@Override
			public int compare(Method o1, Method o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		for (Method method : methods) {
			Column columnAnnotation = method.getAnnotation(Column.class);
			ManyToOne manyToOneAnnotation = method.getAnnotation(ManyToOne.class);
			Id idAnnotation = method.getAnnotation(Id.class);
			/*
			 * Add parameter for ManyToOne and Column when column length < 255
			 * 
			 * Add jointure for ManyToOne field
			 */
			if ((null != columnAnnotation || null != manyToOneAnnotation) && !Clob.class.equals(method.getReturnType())
					&& !Blob.class.equals(method.getReturnType()) && !method.getReturnType().isArray()) {
				String child = getFieldName(method);
				boolean addQueryParameter = false;
				QueryParameterType queryColumnParameter = new QueryParameterType();
				if (null != manyToOneAnnotation) {
					addQueryParameter = true;
					JointureType join = new JointureType();
					join.setFieldName(child);
					join.setSuffix(EngineTools.getSuffix(child, suffixMap));
					boolean nullable = ScenarioUtil.isNullableJoinColumn(method);
					join.setJointureType(nullable ? JointureTypeEnum.LEFT_JOIN_FETCH : JointureTypeEnum.JOIN_FETCH);
					queryTree.getJointure().add(join);
				} else {
					if (FieldTools.isNumericType(method.getReturnType()))
						queryColumnParameter.setStyle("SWT.RIGHT");
					else if (FieldTools.isDateType(method.getReturnType()))
						queryColumnParameter.setStyle("SWT.CENTER");
					addQueryParameter = !String.class.equals(method.getReturnType()) || 256 > columnAnnotation.length();
				}
				if (addQueryParameter) {
					queryColumnParameter.setSuffix(suffix);
					queryColumnParameter.setId(child);
					queryColumnParameter.setProperty(child);
					queryPreference.getParameter().add(queryColumnParameter);
				}
				if (null != idAnnotation)
					queryPreference.setOrderByClause(suffix + "." + child);
			}
		}
		return queryTree;
	}
}
