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
package org.adichatz.jpa.wrapper;

import static org.adichatz.engine.common.EngineTools.getValueFromXml;

import java.io.Serializable;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.collection.CompositeController;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.APluginEntityTree;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.tabular.ParameterFieldManager;
import org.adichatz.jpa.xjc.QueryParameterType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryParameterWrapper.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class QueryParameterWrapper extends QueryParameterType {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3961088534402284833L;

	/** The id null. */
	public static String IS_NULL = "is null";

	/** The is not null. */
	public static String IS_NOT_NULL = "is not null";

	/** The between. */
	public static String BETWEEN = "between";

	/** The id field name. */
	private String idFieldName;

	/** The value of the identifier for expression. */
	private Object idValue;

	/** The value of the identifier for second expression. */
	private Object secondIdValue;

	/** The parameter field manager. */
	private ParameterFieldManager parameterFieldManager;

	/** The value of the first expression of the parameter. */
	private Serializable value;

	/** The value of the second expression of the parameter. */
	private Serializable secondValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.jpa.xjc.QueryParameterType#getColumn()
	 */
	@Override
	public String getProperty() {
		if (null == property)
			return getId();
		return super.getProperty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.jpa.xjc.QueryParameterType#getPrompt()
	 */
	@Override
	public String getPrompt() {
		if (null == prompt)
			return getId();
		return super.getPrompt();
	}

	/**
	 * Gets the id value.
	 * 
	 * @return the id value
	 */
	public Object getIdValue() {
		return idValue;
	}

	/**
	 * Sets the id value.
	 * 
	 * @param idValue
	 *            the new id value
	 */
	public void setIdValue(Object idValue) {
		this.idValue = idValue;
	}

	/**
	 * Gets the second id value.
	 * 
	 * @return the second id value
	 */
	public Object getSecondIdValue() {
		return secondIdValue;
	}

	/**
	 * Sets the second id value.
	 * 
	 * @param secondIdValue
	 *            the new second id value
	 */
	public void setSecondIdValue(Object secondIdValue) {
		this.secondIdValue = secondIdValue;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public Serializable getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(Serializable value) {
		this.value = value;
	}

	/**
	 * Gets the second value.
	 * 
	 * @return the second value
	 */
	public Serializable getSecondValue() {
		return secondValue;
	}

	/**
	 * Sets the second value.
	 * 
	 * @param secondValue
	 *            the new second value
	 */
	public void setSecondValue(Serializable secondValue) {
		this.secondValue = secondValue;
	}

	/**
	 * Gets the id field name.
	 * 
	 * Only identifier value and field name are needed to build the query
	 * 
	 * @param metaModel
	 *            the meta model
	 * @return the id field name
	 */
	public String getIdFieldName(APluginEntityTree pluginEnitityTree) {
		if (null == idFieldName) {
			idFieldName = pluginEnitityTree.getEntityMM(entityURI).getIdFieldName();
		}
		return idFieldName;
	}

	/**
	 * No expression.
	 * 
	 * @return true, if successful
	 */
	public boolean noExpression() {
		return IS_NULL.equals(operator) || IS_NOT_NULL.equals(operator);
	}

	/**
	 * One expression.
	 * 
	 * @return true, if successful
	 */
	public boolean oneExpression() {
		return !IS_NULL.equals(operator) && !IS_NOT_NULL.equals(operator) && !BETWEEN.equals(operator);
	}

	/**
	 * =========================================================================================.
	 * 
	 * @return the valid image
	 */
	public Image getValidImage() {
		Image image;
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		if (isPermanent())
			image = isValid() ? AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE, "IMG_PERMANENT.png")
					: new Image(toolkit.getRegisteredImage("IMG_CANCEL").getDevice(), toolkit.getRegisteredImage("IMG_CANCEL"),
							SWT.IMAGE_GRAY);
		else
			image = isValid() ? toolkit.getRegisteredImage("IMG_ACCEPT") : toolkit.getRegisteredImage("IMG_CANCEL");
		return image;
	}

	/**
	 * Checks if is computed valid.
	 * 
	 * @return true, if is computed valid
	 */
	public boolean isComputedValid() {
		return null != operator && isValidValue() && isValidSecondValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.jpa.xjc.QueryParameterType#isValid()
	 */
	@Override
	public Boolean isValid() {
		return null != valid && valid && isComputedValid();
	}

	/**
	 * Checks if is internal valid.
	 * 
	 * @return the boolean
	 */
	public Boolean isMergeValid() {
		return valid;
	}

	/**
	 * Checks if is valid second expression.
	 * 
	 * @return true, if is valid second value
	 */
	private boolean isValidSecondValue() {
		return !BETWEEN.equals(operator) || (null != secondValue || null != secondIdValue);
	}

	/**
	 * Checks if is valid expression.
	 * 
	 * @return true, if is valid value
	 */
	private boolean isValidValue() {
		return noExpression() || (oneExpression() && (null != value || null != idValue));
	}

	/**
	 * Gets the parameter field manager.
	 * 
	 * @param queryManager
	 *            the query manager
	 * @param parentController
	 *            the parent controller
	 * @return the parameter field manager
	 */
	public ParameterFieldManager getParameterFieldManager(JPAQueryManager<?> queryManager, CompositeController parentController) {
		if (null == parameterFieldManager) {
			PluginEntity<?> pluginEntity = null;
			JointureWrapper jointure = queryManager.getJointureMap().get(suffix);
			if (null != jointure)
				pluginEntity = jointure.getEntityMM().getPluginEntity();

			parameterFieldManager = new ParameterFieldManager(this, queryManager.getQueryPreferenceManager().getUiPluginResources(),
					pluginEntity, parentController);
			parentController.getControl().addDisposeListener((e) -> {
				parameterFieldManager = null;
			});
		}
		return parameterFieldManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public QueryParameterWrapper clone() {
		QueryParameterWrapper cloneParameter = new QueryParameterWrapper();
		cloneParameter.setVisible(visible);
		cloneParameter.setPermanent(permanent);
		cloneParameter.setBinaryExpression(null == binaryExpression ? null : binaryExpression.clone());
		cloneParameter.setBinarySecondExpression(null == binarySecondExpression ? null : binarySecondExpression.clone());
		cloneParameter.setExpressionMethodURI(expressionMethodURI);
		cloneParameter.setProperty(property);
		cloneParameter.setEntityURI(entityURI);
		cloneParameter.setExpressionClassName(expressionClassName);
		cloneParameter.setExpression(expression);
		cloneParameter.setIdValue(idValue);
		cloneParameter.setColumnText(columnText);
		cloneParameter.setValue(value);
		cloneParameter.setId(id);
		cloneParameter.setOperator(operator);
		cloneParameter.setPrompt(prompt);
		cloneParameter.setSecondColumnText(secondColumnText);
		cloneParameter.setSecondExpression(secondExpression);
		cloneParameter.setSecondValue(secondValue);
		cloneParameter.setSecondIdValue(secondIdValue);
		cloneParameter.setStyle(style);
		cloneParameter.setSuffix(suffix);
		cloneParameter.setValid(valid);
		return cloneParameter;
	}

	/**
	 * Sets the expressions.
	 */
	public void setExpressions() {
		if (null != entityURI) {
			setExpressions(idValue, secondIdValue);
		} else {
			setExpressions(value, secondValue);
		}
	}

	/**
	 * Sets the expressions.
	 *
	 * @param value
	 *            the value
	 * @param secondValue
	 *            the second value
	 */
	private void setExpressions(Object value, Object secondValue) {
		if (null != value) {
			Object xmlValue = EngineTools.getXmlValue(value);
			if (xmlValue instanceof String)
				expression = (String) xmlValue;
			else
				binaryExpression = (byte[]) xmlValue;
		}
		if (null != secondValue) {
			Object secondXmlValue = EngineTools.getXmlValue(secondValue);
			if (secondXmlValue instanceof String)
				secondExpression = (String) secondXmlValue;
			else
				binarySecondExpression = (byte[]) secondXmlValue;
		}
	}

	/**
	 * Initialize values.
	 *
	 * @param entityMM
	 *            the entity mm
	 */
	public void initializeValues(AEntityMetaModel<?> entityMM) {
		if (null != expressionClassName) {
			if (null == entityURI) {
				Class<?> fieldType = ReflectionTools.getClazz(expressionClassName);
				value = getValueFromXml(fieldType, expression, binaryExpression);
				secondValue = getValueFromXml(fieldType, secondExpression, binarySecondExpression);
				return;
			}
			if (null == entityMM)
				entityMM = AdichatzApplication.getInstance().getPluginEntity(entityURI).getEntityMetaModel();
		}
		if (null == entityMM && !EngineTools.isEmpty(expressionMethodURI)) {
			Class<?> fieldType = new ParameterFieldManager(this, null, null, null).getCreateMethod().getReturnType();
			value = getValueFromXml(fieldType, expression, binaryExpression);
			secondValue = getValueFromXml(fieldType, secondExpression, binarySecondExpression);
		} else if (EngineTools.isEmpty(entityURI)) {
			Class<?> fieldType = FieldTools.getGetMethod(entityMM.getBeanClass(), getProperty(), false).getReturnType();
			value = getValueFromXml(fieldType, expression, binaryExpression);
			secondValue = getValueFromXml(fieldType, secondExpression, binarySecondExpression);
		} else {
			Class<?> fieldType = FieldTools.getGetMethod(entityMM.getBeanClass(), entityMM.getIdFieldName(), true).getReturnType();
			idValue = getValueFromXml(fieldType, expression, binaryExpression);
			secondIdValue = getValueFromXml(fieldType, secondExpression, binarySecondExpression);
		}
	}

	/**
	 * Gets the cloneable.
	 *
	 * @param userQueryPreference
	 *            the preference passed User preferences
	 * @return the cloneable
	 */
	public QueryParameterWrapper getCloneable(QueryPreferenceWrapper<?> userQueryPreference) {
		if (null == userQueryPreference)
			return this;
		if ((null == operator && null == expression && null == secondExpression))
			return null;
		if (isPermanent()) {
			for (QueryParameterType parameter : userQueryPreference.getParameter())
				if (parameter.getId().equals(id))
					return (null == operator && null == expression && null == secondExpression) ? null
							: (QueryParameterWrapper) parameter;
		} else
			return this;
		return null;
	}
}
