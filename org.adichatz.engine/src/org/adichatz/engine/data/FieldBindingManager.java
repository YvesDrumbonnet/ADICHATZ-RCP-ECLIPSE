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
package org.adichatz.engine.data;

import static org.adichatz.engine.common.LogBroker.logError;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.controller.nebula.FormattedTextController;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.PropertyField;
import org.adichatz.engine.validation.ABindingService;

// TODO: Auto-generated Javadoc
/**
 * The Class BindingManager.
 */
public class FieldBindingManager implements IFieldBindManager {

	/** The field controller. */
	protected AFieldController fieldController;

	/** the plugin resources. */
	protected AdiPluginResources pluginResources;

	/** The is synchro target to model. */
	private boolean isSynchroTargetToModel;

	/** The set method. */
	protected Method setMethod;

	/** The get method. */
	protected Method getMethod;

	/** The locking. */
	private boolean locking = false;

	/** The property change listener. */
	private AdiPropertyChangeListener propertyChangeListener;

	/** The previous entity. */
	private IEntity<?> previousEntity;

	/** The column. */
	protected String column;

	/** The listeners. */
	private List<Runnable> listeners;

	private PropertyField field;

	/**
	 * Instantiates a new binding manager.
	 * 
	 * @param fieldController
	 *            the field controller
	 */
	public FieldBindingManager(final AFieldController fieldController) {
		this.fieldController = fieldController;
		this.pluginResources = fieldController.getParentController().getPluginResources();
		column = fieldController.getProperty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.AEntityBindingManager#addPropertyChangeListener(org.adichatz.engine.cache.IEntity)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBindingManager#addPropertyChangeListener(org.adichatz.engine.cache.IEntity)
	 */
	@Override
	public void addPropertyChangeListener(final IEntity<?> entity) {
		if (null == propertyChangeListener)
			propertyChangeListener = new AdiPropertyChangeListener(this);

		if (!entity.equals(previousEntity)) {
			unbind();
			entity.getBeanInterceptor().getPropertyChangeSupport().addPropertyChangeListener(column, propertyChangeListener);
			fieldController.getControl().addDisposeListener((e) -> {
				PropertyChangeSupport propertyChangeSupport = entity.getBeanInterceptor().getPropertyChangeSupport();
				propertyChangeSupport.removePropertyChangeListener(column, propertyChangeListener);
			});
		}
		previousEntity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBindingManager#bindTargetToModel()
	 */
	@Override
	public void bindTargetToModel() {
		if (!ABindingService.isSynchronizing() && !locking) {
			try {
				isSynchroTargetToModel = true;
				IEntity<?> entity = fieldController.getEntity();
				if (null != entity && IEntityConstants.RETRIEVE == entity.getStatus()) {
					locking = true;
					if (!entity.getEntityMM().getPluginEntity().checkPrivilege(IEntityConstants.MERGE) || !entity.getEntityMM()
							.getDataAccess().lock(fieldController.getBindingService(), IEntityConstants.MERGE, entity)) {
						/* Change or Lock is not possible, so controller receives value of the field */
						fieldController.setValue(
								ReflectionTools.invokeMethod(getField().getGetMethod(), fieldController.getEntity().getBean()));
						entity = null; // Do not propagate changes.
					}
				}

				if (null != entity && null != column) {
					ABindingService.setCurrentBinding(fieldController.getBindingService());
					AListener.fireListener(entity.getListeners(),
							new AdiEntityEvent(IEventType.BEFORE_PROPERTY_CHANGE, entity, column));
					AListener.fireListener(fieldController.getListenerMap(), new AdiEvent(IEventType.BEFORE_FIELD_CHANGE));
					try {
						getSetMethod().invoke(entity.getBeanInterceptor(), fieldController.getValue());
					} catch (IllegalArgumentException e) {
						// Null is not a correct value for primitive (short, int, ...) replace null by 0.
						if (fieldController instanceof FormattedTextController
								&& getField().getGetMethod().getReturnType().isPrimitive() && null == fieldController.getValue())
							getField().getSetMethod().invoke(entity.getBeanInterceptor(),
									((FormattedTextController) fieldController).getNull());
						else
							throw e;
					}
					// Validate field controller for same column in same Binding Service.
					// must be executed after the set method which propagates change to other field controllers.
					for (PropertyChangeListener listener : entity.getBeanInterceptor().getPropertyChangeSupport()
							.getPropertyChangeListeners(column)) {
						if (listener instanceof AdiPropertyChangeListener) {
							AdiPropertyChangeListener apcl = (AdiPropertyChangeListener) listener;
							if (!apcl.equals(propertyChangeListener)
									&& apcl.fieldController.getBindingService().equals(fieldController.getBindingService()))
								apcl.getFieldController().getValidation().validate();
						}
					}
					AListener.fireListener(fieldController.getListenerMap(), new AdiEvent(IEventType.AFTER_FIELD_CHANGE));
				} // end if (null != entity)
					// validation after set method so that #FV() keyword point to the up-to-date value
				fieldController.getValidation().validate();
				if (null != entity && null != column)
					// Launch AFTER_PROPERTY_CHANGE after validation process (e.g bindingService#errorMessageMap is up-to-date).
					AListener.fireListener(entity.getListeners(),
							new AdiEntityEvent(IEventType.AFTER_PROPERTY_CHANGE, entity, column));
				ICollectionController parent = fieldController.getParentController();
				while (null != parent) {
					if (parent instanceof IValidableController && null != ((IValidableController) parent).getValidation())
						((IValidableController) parent).getValidation().validate();
					parent = parent.getParentController();
				}
			} catch (Exception e) {
				logError(e);
			} finally {
				isSynchroTargetToModel = false;
				locking = false;
				ABindingService.setCurrentBinding(null);
			}
		}
	}

	protected Method getSetMethod() {
		return getField().getSetMethod();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBindingManager#getFieldController()
	 */
	@Override
	public AFieldController getFieldController() {
		return fieldController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBindingManager#getListeners()
	 */
	@Override
	public List<Runnable> getListeners() {
		if (null == listeners)
			listeners = new ArrayList<Runnable>();
		return listeners;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBindingManager#unbind()
	 */
	@Override
	public void unbind() {
		if (null != previousEntity) {
			previousEntity.getBeanInterceptor().getPropertyChangeSupport().removePropertyChangeListener(column,
					propertyChangeListener);
			previousEntity = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBindingManager#setColumn(java.lang.String)
	 */
	@Override
	public void setColumn(String column) {
		this.column = column;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBindingManager#isSynchroTargetToModel()
	 */
	@Override
	public boolean isSynchroTargetToModel() {
		return isSynchroTargetToModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.data.IBindingManager#getField()
	 */
	@Override
	public PropertyField getField() {
		if (null == field)
			field = fieldController.getEntity().getEntityMM().getFieldMap().get(fieldController.getProperty());
		return field;
	}
}
