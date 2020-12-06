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
package org.adichatz.jpa.query.custom;

import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.ARefController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.CompositeController;
import org.adichatz.engine.controller.collection.TableController;
import org.adichatz.engine.controller.field.ComboController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.field.NumericTextController;
import org.adichatz.engine.controller.nebula.ScrolledPGroupController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.data.DynamicBindManager;
import org.adichatz.engine.data.IFieldBindManager;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.query.NativeQueryManager;
import org.adichatz.engine.tabular.ATabularContentProvider;
import org.adichatz.engine.viewer.NativeContentProvider;
import org.adichatz.jpa.controller.RefTextController;
import org.adichatz.jpa.data.JPADataAccess;
import org.adichatz.jpa.query.JPAQueryManager;
import org.adichatz.jpa.query.QueryToolInput;
import org.adichatz.jpa.query.action.ValidateParameterItemController;
import org.adichatz.jpa.tabular.ParameterFieldManager;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.adichatz.jpa.wrapper.QueryPreferenceWrapper;
import org.adichatz.jpa.xjc.QueryParameterType;
import org.eclipse.jface.viewers.IStructuredSelection;

// TODO: Auto-generated Javadoc
/**
 * The Class ColumnParameterTableController.
 * 
 * @param <T> the generic type
 */
public class ColumnParameterTableController<T> extends TableController<T> {

	/** The operator Combo controller. */
	private ComboController operatorCMB;

	/** The column parameter group. */
	private ScrolledPGroupController columnParameterGroup;

	/** The column dynamic expressions composite controller. */
	private CompositeController columnDynamicExpressionsCMP;

	/** The query manager. */
	private JPAQueryManager<?> editorQueryManager;

	/** The field controller. */
	private AFieldController valueFC;

	/** The second field controller. */
	private AFieldController secondValueFC;

	/** The validate action. */
	private ValidateParameterItemController validateItemController;

	/** The column parameter. */
	private QueryParameterWrapper columnParameter;

	/** The and label. */
	private LabelController andLBL;

	/** The query data access. */
	private JPADataAccess queryDataAccess;

	/** The jpa data access. */
	private ADataAccess jpaDataAccess;

	/** The query tool input. */
	@SuppressWarnings("rawtypes")
	private QueryToolInput queryToolInput;

	/**
	 * Instantiates a new column parameter table controller.
	 * 
	 * @param id               the id
	 * @param parentController the parent controller
	 * @param genCode          the gen code
	 */
	public ColumnParameterTableController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		jpaDataAccess = this.pluginResources.getPluginEntity("adi://org.adichatz.jpa/query.model/ParameterMM").getEntityMetaModel()
				.getDataAccess();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.controller.collection.TabularController#synchronize()
	 */
	@Override
	public void synchronize() {
		super.synchronize();
		refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.adichatz.engine.controller.collection.TableController#createControl()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void createControl() {
		super.createControl();
		viewer.addSelectionChangedListener((event) -> {
			if (event.getSelection().isEmpty()) {
				columnParameter = null;
				columnParameterGroup.setDirty(false);
				if (null != columnParameterGroup.getControl() && !columnParameterGroup.getControl().isDisposed())
					columnParameterGroup.getControl().setVisible(false);
			} else {
				QueryParameterWrapper newColumnParameter = (QueryParameterWrapper) ((IStructuredSelection) event.getSelection())
						.getFirstElement();
				if (!newColumnParameter.equals(columnParameter)) {
					columnParameter = newColumnParameter;
					if (null != columnParameter.getIdValue() && null == columnParameter.getValue()) {
						Class<?> beanClass;
						if (null != columnParameter.getEntityURI())
							beanClass = queryDataAccess.getPluginResources().getPluginEntity(columnParameter.getEntityURI())
									.getEntityMetaModel().getBeanClass();
						else
							beanClass = editorQueryManager.getJointureMap().get(columnParameter.getSuffix()).getEntityMM()
									.getBeanClass();
						Object value = queryDataAccess.findProxyEntity(beanClass, columnParameter.getIdValue(), true).getBean();
						if (null == value)
							logWarning(
									"No bean found for class:<" + beanClass + "> and id:<" + columnParameter.getIdValue() + ">.");
						columnParameter.setValue((Serializable) value);
					}
					selectNewParameter();
				}
			}
		});
		NativeQueryManager<T> nativeQueryManager = new NativeQueryManager<T>("parameter") {
			@Override
			public QueryResult launchQueryResult() {
				Collection<QueryParameterType> queryResultList = new ArrayList<QueryParameterType>();
				for (QueryParameterType parameter : ((QueryPreferenceWrapper<?>) getEntity().getBean()).getParameter())
					if (parameter.isVisible())
						queryResultList.add(parameter);
				queryResult = new QueryResult(queryResultList, Long.valueOf(queryResultList.size()));
				return queryResult;
			}
		};
		ATabularContentProvider<T> contentProvider = new NativeContentProvider<T>(nativeQueryManager);
		contentProvider.setTabularController(this);

		addListener(new AControlListener("ColumnParameterTableController#Refresh", IEventType.AFTER_REFRESH) {
			@Override
			public void handleEvent(AdiEvent event) {
				if (null != columnParameter) {
					columnParameterGroup.disposeControl();
					columnDynamicExpressionsCMP.getChildControllers().clear();
					columnParameter = null;
				}
			}
		});
		columnParameterGroup = (ScrolledPGroupController) genCode.getFromRegister("columnParameterGroup");
		columnDynamicExpressionsCMP = (CompositeController) genCode.getFromRegister("columnDynamicExpressions");
		queryToolInput = (QueryToolInput) ((AWidgetController) genCode.getFromRegister("queryTool")).getEntity().getBean();
		editorQueryManager = queryToolInput.getQueryManager();
		queryDataAccess = (JPADataAccess) editorQueryManager.getEntityMM().getDataAccess();
		validateItemController = (ValidateParameterItemController) genCode.getFromRegister("validateParameterItem");
	}

	/**
	 * Supply combo items.
	 * 
	 * @param fieldClass the field class
	 * @return the combo controller
	 */
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	private ComboController supplyComboItems(Class<?> fieldClass) {
		List<String> operators = new ArrayList<String>();
		operators.add("=");
		operators.add("!=");
		if (FieldTools.isNumericType(fieldClass) || FieldTools.isDateType(fieldClass)) {
			operators.add(">");
			operators.add(">=");
			operators.add("<");
			operators.add("<=");
			operators.add(QueryParameterWrapper.BETWEEN);
		} else if (fieldClass.equals(String.class)) {
			operators.add("like");
			operators.add("not like");
			operators.add(QueryParameterWrapper.BETWEEN);
		}
		Boolean mandatory = FieldTools.isMandatory(
				editorQueryManager.getJointureMap().get(columnParameter.getSuffix()).getEntityMM().getBeanClass(),
				columnParameter.getProperty());
		if (!mandatory) {
			operators.add(QueryParameterWrapper.IS_NULL);
			operators.add(QueryParameterWrapper.IS_NOT_NULL);
		}

		operatorCMB.getViewer().setInput(operators);
		operatorCMB.getViewer().getCombo().setItems(operators.toArray(new String[operators.size()]));

		operatorCMB.getViewer().getCombo().setVisibleItemCount(operators.size());

		return operatorCMB;
	}

	/**
	 * Adds the selection listener2 operator cmb.
	 */
	private void addSelectionListener2OperatorCMB() {
		operatorCMB.getViewer().addSelectionChangedListener((event) -> {
			operatorSelected();
		});
	}

	/**
	 * Adds the field controller.
	 * 
	 * @param columnParameter the column parameter
	 * @param columnName      the column name
	 * @return the a field controller
	 */
	private AFieldController addFieldController(QueryParameterWrapper columnParameter, String columnName) {
		AFieldController fieldController = columnParameter.getParameterFieldManager(editorQueryManager, columnDynamicExpressionsCMP)
				.newFieldController();
		if (null == fieldController)
			return null;
		if (fieldController instanceof RefTextController)
			((RefTextController) fieldController).setPluginResources(queryToolInput.getTabularController().getPluginResources());
		fieldController.setProperty(columnParameter.getProperty());
		/*
		 * query manager plugin resource must be temporary set to
		 * columnDynamicExpressionsCMP (e.g. for looking for pooled query manager in
		 * referenced controller).
		 */
		columnDynamicExpressionsCMP
				.setPluginResources(editorQueryManager.getEntityMM().getPluginEntity().getPluginEntityTree().getPluginResources());
		fieldController.startLifeCycle();
		fieldController.setFieldBindManager(new DynamicBindManager(fieldController));
		columnDynamicExpressionsCMP.setPluginResources(getPluginResources());

		((IFieldBindManager) fieldController.getFieldBindManager()).setColumn(columnName);
		fieldController.endLifeCycle();
		fieldController.setLocked(false);
		fieldController.getValidation().getValidators().clear();
		return fieldController;
	}

	/**
	 * Select new parameter.
	 */
	private void selectNewParameter() {
		IEntity<QueryParameterWrapper> parameterEntity = jpaDataAccess.getDataCache().fetchEntity(columnParameter);
		if (null == columnParameterGroup.getControl() || columnParameterGroup.getControl().isDisposed()) {
			columnParameterGroup.getEntityInjection().initialize(parameterEntity);
			columnParameterGroup.setImmutableValid(true);
			columnParameterGroup.initialize();
			columnParameterGroup.startLifeCycle();
			operatorCMB = (ComboController) genCode.getFromRegister("operator");
			andLBL = (LabelController) genCode.getFromRegister("and");
			addSelectionListener2OperatorCMB();
			// redefine a new Layout to avoid MigLayout bug
			EngineTools.reinitMiglayout(columnDynamicExpressionsCMP.getComposite());
		} else {
			valueFC.disposeControl();
			secondValueFC.disposeControl();
			columnDynamicExpressionsCMP.getChildControllers().clear();
			// redefine a new Layout to avoid MigLayout bug
			EngineTools.reinitMiglayout(columnDynamicExpressionsCMP.getComposite());
		}
		AEntityMetaModel<?> entityMM = editorQueryManager.getJointureMap().get(columnParameter.getSuffix()).getEntityMM();
		Class<?> fieldClass = FieldTools.getGetMethod(entityMM.getBeanClass(), columnParameter.getProperty(), true).getReturnType();
		String prompt = EngineTools.getMessage(columnParameter.getPrompt());
		columnParameterGroup.getControl()
				.setText(columnParameter.isPermanent() ? prompt + " " + getFromJpaBundle("query.parameter.permanent") : prompt);

		operatorCMB = supplyComboItems(fieldClass);

		valueFC = addFieldController(columnParameter, "value");
		if (null == valueFC)
			return;
		secondValueFC = addFieldController(columnParameter, "secondValue");

		// When pointing on numeric primitive field and value is null, initialize value
		// to zero.
		if (valueFC instanceof NumericTextController && fieldClass.isPrimitive()) {
			QueryParameterWrapper parameter = parameterEntity.getBean();
			if (null == parameter.getValue()) {
				parameter.setValue((Serializable) valueFC.convertTargetToModel(new BigDecimal(0)));
			}
			if (null == parameter.getSecondValue()) {
				parameter.setSecondValue((Serializable) valueFC.convertTargetToModel(new BigDecimal(0)));
			}
		}

		columnParameterGroup.getEntityInjection().initialize(parameterEntity);

		if (!columnParameter.isPermanent()) {
			columnParameterGroup.setEnabled(true);
			if (parameterEntity.getListeners().isEmpty()) {
				new AEntityListener("whenPropertyChange", columnParameterGroup, IEventType.WHEN_PROPERTY_CHANGE) {
					@Override
					public void handleEntityEvent(AdiEntityEvent event) {
						ParameterFieldManager parameterFieldManager = columnParameter.getParameterFieldManager(null, null);
						// Expression received from program must be set to null
						columnParameter.setExpression(null);
						columnParameter.setBinaryExpression(null);

						if ("value".equals(event.getPropertyName())) {
							columnParameter.setColumnText(
									String.valueOf(parameterFieldManager.getValueFldCtlr().toString(columnParameter.getValue())));
							if (parameterFieldManager.getValueFldCtlr() instanceof ARefController) {
								try {
									AEntityMetaModel<?> refMM = queryDataAccess.getPluginResources().getPluginEntityTree()
											.getEntityMM(columnParameter.getValue().getClass());
									columnParameter.setEntityURI(refMM.getPluginEntity().getEntityURI());
									columnParameter.setIdValue(
											null == columnParameter.getValue() ? null : refMM.getId(columnParameter.getValue()));
								} catch (RuntimeException e) {
								}
							}
						} else if ("secondValue".equals(event.getPropertyName()))
							columnParameter.setSecondColumnText(String
									.valueOf(parameterFieldManager.getValueFldCtlr().toString(columnParameter.getSecondValue())));
					}
				};
				new AEntityListener("afterPropertyChange", columnParameterGroup, IEventType.AFTER_PROPERTY_CHANGE) {
					@Override
					public void handleEntityEvent(AdiEntityEvent event) {
						validateItemController.setImage();
					}
				};
			}
		} else
			columnParameterGroup.setEnabled(false);

		columnParameterGroup.endLifeCycleAndSync();

		operatorSelected();

		columnParameterGroup.getControl().setVisible(true);
		columnParameterGroup.getScrolledComposite().reflow(true);
		columnParameterGroup.forceDecorationDisplay();
		validateItemController.setImage();

		EngineTools.reinitMiglayout(((CompositeController) genCode.getFromRegister("columnParameterCmp")).getControl());
		columnParameterGroup.getControl().getParent().layout();
	}

	/**
	 * Operator selected.
	 */
	private void operatorSelected() {
		columnDynamicExpressionsCMP.getControl().setRedraw(false);
		String selectedOperator = (String) operatorCMB.getValue();
		if (null == selectedOperator || QueryParameterWrapper.IS_NULL.equals(selectedOperator)
				|| QueryParameterWrapper.IS_NOT_NULL.equals(selectedOperator)) {
			valueFC.getControl().setVisible(false);
			andLBL.getControl().setVisible(false);
			secondValueFC.getControl().setVisible(false);
		} else if (selectedOperator.equals(QueryParameterWrapper.BETWEEN)) {
			valueFC.getControl().setVisible(true);
			andLBL.getControl().setVisible(true);
			secondValueFC.getControl().setVisible(true);
		} else {
			valueFC.getControl().setVisible(true);
			andLBL.getControl().setVisible(false);
			secondValueFC.getControl().setVisible(false);
		}
		columnDynamicExpressionsCMP.getControl().setRedraw(true);
		columnDynamicExpressionsCMP.getControl().layout();
	}

	public AFieldController getValueFC() {
		return valueFC;
	}

	public AFieldController getSecondValueFC() {
		return secondValueFC;
	}
}
