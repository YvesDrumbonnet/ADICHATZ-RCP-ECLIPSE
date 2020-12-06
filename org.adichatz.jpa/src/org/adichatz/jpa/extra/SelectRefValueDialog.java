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
package org.adichatz.jpa.extra;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import org.adichatz.common.ejb.QueryResult;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.contentProvider.QueryContentProvider;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ATabularController.METHOD_NAME;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.extra.AFormInputDialog;
import org.adichatz.engine.extra.AdiFormInput;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectRefValueDialog.
 *
 * @author Yves Drumbonnet
 */
public class SelectRefValueDialog {

	/** The entity MetaModel. */
	private AEntityMetaModel<?> entityMM;

	/** The param map. */
	private ParamMap paramMap;

	/** The selected value. */
	private Object currentSelectedValue;

	/** The tabular controller. */
	private ATabularController<?> tabularController;

	/** The tabular viewer. */
	private ColumnViewer tabularViewer;

	/** The original location. */
	private Point originalLocation;

	/**
	 * Instantiates a new select ref value dialog.
	 *
	 * @param paramMap
	 *            the param map
	 */
	public SelectRefValueDialog(AEntityMetaModel<?> entityMM, QueryContentProvider<?> contentProvider, ParamMap paramMap,
			Point originalLocation) {
		this.paramMap = paramMap;
		this.originalLocation = originalLocation;
		this.entityMM = entityMM;
		paramMap.put(ParamMap.CONTENT_PROVIDER, contentProvider);
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return currentSelectedValue;
	}

	/**
	 * Open select value.
	 *
	 * @param selectedValueParam
	 *            the selected value param
	 * @return the int
	 */
	public int openSelectValue(AdiPluginResources pluginResource, Object selectedValue) {
		currentSelectedValue = selectedValue;
		PluginEntity<?> queryPluginEntity = entityMM.getPluginEntity();

		String tableResourcesURI = paramMap.getString(ParamMap.TABLE_RESOURCE_URI);
		if (null == tableResourcesURI) {
			tableResourcesURI = queryPluginEntity.getAdiResourceUriMap().get("TABLE");
			if (null == tableResourcesURI)
				throw new RuntimeException(
						"No defined table resources URI for pluginEntity '" + queryPluginEntity.getEntityId() + "'!'");
		}
		String[] instanceKeys = EngineTools.getInstanceKeys(tableResourcesURI);
		if (null == instanceKeys[0])
			tableResourcesURI = EngineTools.getAdiResourceURI(pluginResource.getPluginName(), instanceKeys[1], instanceKeys[2]);
		paramMap.put(ParamMap.TABLE_RESOURCE_URI, tableResourcesURI);
		addParam(ParamMap.CONTEXT_MENU, "adi://org.adichatz.jpa/common.contextMenu/RefTextDialogCM");

		String popupURI = getParamNvl(ParamMap.POPUP_URI,
				(String) AdichatzApplication.getInstance().getContextValue(EngineConstants.DEFAULT_REF_TEXT_POPUP_URI));
		String popupFormText = getParamNvl(ParamMap.POPUP_FORM_TEXT,
				getFromJpaBundle("selection.dialog.formText", entityMM.getEntityId()));
		final AdiFormInput formInput = new AdiFormInput(null, popupURI, paramMap);

		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		AFormInputDialog formDialog = new AFormInputDialog(Display.getCurrent().getActiveShell(), //
				popupFormText, //
				toolkit.getRegisteredImage("IMG_FIND"), formInput) {
			@Override
			protected void postCreateContent() {
				scrolledForm.getBody().setLayout(new MigLayout("wrap", "grow,fill", "grow,fill"));
				ContainerController container = new ContainerController(formInput.getPluginResources(), managedForm,
						scrolledForm.getBody(), null, null);
				formInput.createContents(container);

				tabularController = (ATabularController<?>) formInput.getPartCore().getFromRegister("table:table");
				tabularViewer = tabularController.getViewer();

				tabularViewer.addDoubleClickListener(new IDoubleClickListener() {
					public void doubleClick(DoubleClickEvent event) {
						okPressed();
					}
				});
				tabularViewer.addSelectionChangedListener((event) -> {
					Object selectedValue = ((IStructuredSelection) event.getSelection()).getFirstElement();
					Button okButton = getButton(OK);
					if (null != okButton) {
						okButton.setEnabled(null == selectedValue ? false : !isCurrentValue(currentSelectedValue, selectedValue));
					}
				});
				tabularController.addListener(
						new AControlListener("BasicTabularStatusBar#Refresh", IEventType.AFTER_REFRESH, tabularController) {
							@Override
							public void handleEvent(AdiEvent event) {
								if (null != currentSelectedValue) {
									selectCurrentValue();
								}
							}
						});
				if (null != tabularViewer.getInput()) // Could be null when tabularController is not refreshed at start
					selectCurrentValue();
				AListener.fireListener(container.getListenerMap(), IEventType.POST_CREATE_PART);
			}

			private void selectCurrentValue() {
				/**
				 * If bean with same Id is met in the collection
				 */
				for (Object element : ((QueryResult) tabularViewer.getInput()).getQueryResultList())
					if (isCurrentValue(currentSelectedValue, element)) {
						tabularViewer.setSelection(new StructuredSelection(element));
						tabularController.invokeControlMethod(METHOD_NAME.setTopIndex);
						break;
					}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				super.createButtonsForButtonBar(parent);
				// parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				getButton(IDialogConstants.OK_ID).setEnabled(false);
				parent.layout();
			}

			@Override
			protected Control createContents(Composite parent) {
				Control control = super.createContents(parent);
				Point popupSize = FieldTools.getSize(paramMap.getString(ParamMap.POPUP_SIZE));
				getShell().setSize(null == popupSize ? new Point(400, 500) : popupSize);
				if (null != originalLocation) {
					Point size = getShell().getSize();
					Point newPoint = new Point(Math.max(originalLocation.x - size.x, 0), Math.max(originalLocation.y - size.y, 0));
					getShell().setLocation(newPoint);
				}
				return control;
			}

			@Override
			public void okPressed() {
				currentSelectedValue = tabularController.getSelectedObject();
				super.okPressed();
			}
		};
		return formDialog.open();
	}

	/**
	 * Adds the param.
	 *
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 */
	private void addParam(String key, Object defaultValue) {
		if (!paramMap.containsKey(key))
			paramMap.put(key, defaultValue);
	}

	/**
	 * Gets the param nvl.
	 *
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the param nvl
	 */
	private String getParamNvl(String key, String defaultValue) {
		String value = paramMap.getString(key);
		return null == value ? defaultValue : value;
	}

	/**
	 * Checks if is current value has same Id has old value.
	 * 
	 * @param selectedValue
	 *            the selected value
	 * @return true, if is current value
	 */
	private boolean isCurrentValue(Object currentValue, Object selectedValue) {
		if (null == currentValue || null == selectedValue)
			return false;
		return entityMM.getId(selectedValue).equals(entityMM.getId(currentValue));
	}

	/**
	 * Gets the entity mm.
	 *
	 * @return the entity mm
	 */
	public AEntityMetaModel<?> getEntityMM() {
		return entityMM;
	}
}
