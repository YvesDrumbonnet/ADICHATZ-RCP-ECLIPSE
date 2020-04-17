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

// *********************
// *** C A U T I O N ***
// *********************
//
// this class is dynamically loaded in the application No reference will be found in the application
//

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.xjc.GenerationUnitType;
import org.adichatz.generator.xjc.MessageType;
import org.adichatz.generator.xjc.MessagesType;
import org.adichatz.generator.xjc.OneToOneType;
import org.adichatz.generator.xjc.OneToOneTypeEnum;
import org.adichatz.scenario.IMessageBundleScenario;
import org.adichatz.scenario.ScenarioInput;
import org.eclipse.jface.internal.InternalPolicy;

// TODO: Auto-generated Javadoc
/**
 * The Class EditScenario.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class MessageBundleScenario extends ABundleScenario implements IMessageBundleScenario {

	private Map<String, String> initialMessages = new HashMap<>();

	/**
	 * Instantiates a new message bundle scenario.
	 * 
	 * @param generationUnit
	 *            the generation unit
	 */
	public MessageBundleScenario(GenerationUnitType generationUnit) {
		super(generationUnit);
	}

	/**
	 * Creates the entity bundle.
	 * 
	 * @param id
	 *            the id
	 * @param entityClass
	 *            the entity class
	 */
	@SuppressWarnings("restriction")
	@Override
	public void createEntityBundle(ScenarioInput scenarioInput) {
		LogBroker.logInfo(getFromGeneratorBundle("generation.generating.message.bundle", scenarioInput.getTreeId()));
		this.scenarioInput = scenarioInput;
		MessagesType messages = scenarioInput.getPluginEntityWrapper().getMessages();
		if (null != messages)
			for (MessageType message : messages.getMessage()) {
				if (null == message.getLanguage())
					initialMessages.put(message.getKey(), message.getValue());
			}
		try {
			bundleWriter = getFileWriter(scenarioInput.getPluginEntity().getEntityId());

			writeTitle("Bundle for entity " + scenarioInput.getPluginEntity().getEntityURI());

			bundleWriter.append('\n');
			writeTitle("fields");
			writeFields(scenarioInput.getPluginEntityWrapper().getBeanClass(), "");

			bundleWriter.append('\n');
			writeTitle("others");
			writeKeyValuePair("detailContainerText", scenarioInput.getPluginEntity().getEntityId() + " detail");
			writeKeyValuePair("tableTitle", scenarioInput.getPluginEntity().getEntityId() + " table");
			writeKeyValuePair("tableGroupText", scenarioInput.getPluginEntity().getEntityId() + " table\n");
			writeKeyValuePair("editFormText", "Edit " + scenarioInput.getPluginEntity().getEntityId());
			writeKeyValuePair("editFormTitle", "Edit");
			writeKeyValuePair("queryFormText", "Query for " + scenarioInput.getPluginEntity().getEntityId());
			writeKeyValuePair("querySectionText", "Criteria for " + scenarioInput.getPluginEntity().getEntityId());
			writeKeyValuePair("listDetailFormText", "List/Detail for " + scenarioInput.getPluginEntity().getEntityId());
			EntityTreeWrapper entityTree = scenarioInput.getPluginEntityWrapper().getEntityTree();
			for (OneToOneType oneToOne : entityTree.getOneToOne())
				if (OneToOneTypeEnum.MASTER == oneToOne.getOneToOneType()) {
					String id = oneToOne.getId();
					writeKeyValuePair(id, id);
					writeKeyValuePair(id + "OTOFormText", id + " for " + scenarioInput.getPluginEntity().getEntityId() + ": {0}");
				}

			/*
			 * a property for JoinColumns (jointure made on several columns) must bee added
			 */
			boolean first = true;
			for (Method getJoinColumns : scenarioInput.getPluginEntityWrapper().getBeanClass().getDeclaredMethods())
				if (null != getJoinColumns.getAnnotation(JoinColumns.class)) {
					if (first) {
						first = false;
						bundleWriter.append('\n');
						writeTitle("joinColumns");
					}
					String propertyName = EngineTools.lowerCaseFirstLetter(getJoinColumns.getName().substring(3));
					writeKeyValuePair(propertyName, propertyName);
				}

			/*
			 * Cross reference
			 */
			first = true;
			for (Method persistentSetMethod : scenarioInput.getPluginEntityWrapper().getBeanClass().getDeclaredMethods())
				if (null != persistentSetMethod.getAnnotation(ManyToMany.class)
						|| null != persistentSetMethod.getAnnotation(OneToMany.class)) {
					if (first) {
						first = false;
						bundleWriter.append('\n');
						writeTitle("cross-references");
					}
					String propertyName = EngineTools.lowerCaseFirstLetter(persistentSetMethod.getName().substring(3));
					writeKeyValuePair("crossReference." + propertyName, "Cross-reference for property '" + propertyName + "' of "
							+ scenarioInput.getPluginEntity().getEntityId() + " '{0}'.");
				}
			first = true;
			for (Map.Entry<String, String> entry : initialMessages.entrySet()) {
				if (first) {
					first = false;
					bundleWriter.append('\n');
					writeTitle("Extra");
				}
				writeKeyValuePair(entry.getKey(), entry.getValue());
			}

			bundleWriter.flush();
			bundleWriter.close();
			if (InternalPolicy.OSGI_AVAILABLE)
				scenarioInput.getScenarioResources().getAffectedFiles().add(getFile(scenarioInput.getPluginEntity().getEntityId()));

		} catch (IOException e) {
			logError(e);
		}
	}

	/**
	 * Write fields.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param bundleWriter
	 *            the bundle writer
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void writeFields(Class<?> entityClass, String prefix) throws IOException {
		for (Field field : entityClass.getDeclaredFields())
			if (!Modifier.isStatic(field.getModifiers())) {
				Method getMethod = FieldTools.getGetMethod(entityClass, field.getName(), false);
				if (null != getMethod)
					if (null != getMethod.getAnnotation(Column.class) || null != getMethod.getAnnotation(JoinColumn.class))
						writeKeyValuePair(prefix + field.getName(), field.getName());
					else if (null != getMethod.getAnnotation(OneToMany.class) || null != getMethod.getAnnotation(ManyToMany.class)
							|| null != getMethod.getAnnotation(OneToOne.class))
						writeKeyValuePair(prefix + field.getName(), EngineTools.upperCaseFirstLetter(field.getName()));
					else if (null != getMethod.getAnnotation(EmbeddedId.class)) {
						AttributeOverride[] attributeOverrides = getMethod.getAnnotation(AttributeOverrides.class).value();
						for (int i = 0; i < attributeOverrides.length; i++) {
							writeKeyValuePair("id." + attributeOverrides[i].name(), attributeOverrides[i].name());
						}
					}
			}
	}

	protected void writeKeyValuePair(String key, String paramValue) throws IOException {
		String value = initialMessages.get(key);
		if (null != value)
			initialMessages.remove(key);
		bundleWriter.append(key + " = " + (null == value ? paramValue : value) + "\n");
	}
}
