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
package org.adichatz.jpa.controller;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.contentProvider.QueryContentProvider;
import org.adichatz.engine.controller.ARefController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.data.ADataAccess;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.widgets.supplement.ButtonItem;
import org.adichatz.jpa.data.JPAEntity;
import org.adichatz.jpa.extra.JPAUtil;
import org.adichatz.jpa.extra.SelectRefValueDialog;
import org.adichatz.jpa.tabular.JPAQueryContentProvider;
import org.adichatz.jpa.tabular.PooledContentProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class RefTextController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class RefTextController extends ARefController {

	/** The delete tool tip. */
	private String deleteToolTip = EngineTools.getFromEngineBundle("field.clearValueToolTip");

	/** The editor tool tip. */
	private String editorToolTip = JPAUtil.getFromJpaBundle("refText.reference.editor");

	/** The find tool tip. */
	private String findToolTip = EngineTools.getFromEngineBundle("field.occurences.findToolTipText");

	/** The refText. */
	protected Text text;

	/** The plugin resources. */
	protected AdiPluginResources pluginResources;

	/** The param map. */
	protected ParamMap paramMap;

	/** The delete button. */
	protected ButtonItem deleteItem;

	/** The editor button. */
	protected ButtonItem editorItem;

	/** The find button. */
	protected ButtonItem findItem;

	/** The value. */
	private Object value;

	protected Composite containerComposite;

	/**
	 * Instantiates a new ref text controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public RefTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.BORDER | AdiSWT.FIND_BUTTON | AdiSWT.DELETE_BUTTON;
		if (null != parentController)
			pluginResources = parentController.getPluginResources();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#getValue()
	 */
	@Override
	public Object getValue() {
		return convertTargetToModel(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#setValue(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (null != getEntity() && (!ABindingService.isSynchronizing() || IEntityConstants.PERSIST == getEntity().getStatus())
				&& !Utilities.equals(this.value, value))
			value = dataReferenceManager.replace(value);
		String textValue = (String) convertModelToTarget(value);
		this.value = value;
		text.setText(null == textValue ? "" : textValue);
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public Text getText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public Composite getControl() {
		return containerComposite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createControl() {
		super.createControl();
		containerComposite = new Composite(parentController.getComposite(), style) {
			@Override
			public void setEnabled(boolean enabled) {
				checkWidget();
				if (null != findItem)
					findItem.setEnabled(enabled);
				if (null != deleteItem)
					deleteItem.setEnabled(enabled);
				super.setEnabled(enabled);
			}
		};
		toolkit.adapt(containerComposite);
		containerComposite.setLayoutData("w 65:n:n, h 16:n:n");

		text = new Text(containerComposite, SWT.READ_ONLY);
		text.setForeground(toolkit.getColors().getForeground());
		text.setBackground(toolkit.getColors().getBackground());
		childControl = text;
		containerComposite.setLayoutData("w 10:10:n");

		int nbButton = 0;
		String columnConstraint = "[fill,grow]";

		if (0 == (style & AdiSWT.FIND_BUTTON)) {
			findItem = null;
		} else {
			nbButton++;
			columnConstraint = columnConstraint.concat("1[]");
			findItem = toolkit.createButtonItem(containerComposite, SWT.ICON);
			// findItem.setLayoutData("w 18!, h 20!, pad 0 -3 0 0");
			findItem.setToolTipText(findToolTip);
			findItem.setImage(toolkit.getRegisteredImage("IMG_FIND"));
			findItem.addSelectionListener(() -> {
				QueryContentProvider<?> contentProvider;
				if (poolQueryResult) {
					contentProvider = new PooledContentProvider<>(getQueryManager(), adiQueryURI, preferenceURI);
				} else {
					contentProvider = new JPAQueryContentProvider(getQueryManager(), adiQueryURI, preferenceURI);
				}
				SelectRefValueDialog selectRefValueDialog = new SelectRefValueDialog(queryManager.getEntityMM(), contentProvider,
						getParamMap(), findItem.toDisplay(0, 0));
				if (Dialog.OK == selectRefValueDialog.openSelectValue(pluginResources, value)) {
					Object selectedValue = selectRefValueDialog.getValue();
					if (!Utilities.equalsBean(selectRefValueDialog.getEntityMM(), value, selectedValue))
						setValue(selectedValue);
				}
			});
		}

		if (0 == (style & AdiSWT.EDITOR_BUTTON) || null == ((IContainerController) parentController).getContext()) {
			editorItem = null;
		} else {
			nbButton++;
			columnConstraint = columnConstraint.concat("1[]");
			editorItem = toolkit.createButtonItem(containerComposite, SWT.ICON);
			text.addModifyListener((e) -> {
				editorItem.setEnabled(null != getValue());
			});

			editorItem.setToolTipText(editorToolTip);
			editorItem.setImage(toolkit.getRegisteredImage("IMG_EDITOR"));

			editorItem.addSelectionListener(() -> {
				IContainerController containerController = (IContainerController) parentController;
				ADataAccess dataAccess = getEntity().getEntityMM().getDataAccess();
				JPAEntity<?> entity = (JPAEntity<?>) dataAccess.getDataCache().fetchEntity(getValue());
				JPAUtil.openForm(containerController.getContext(), containerController.getPluginResources(), entity);
			});
		}

		if (0 == (style & AdiSWT.DELETE_BUTTON)) {
			deleteItem = null;
		} else {
			nbButton++;
			columnConstraint = columnConstraint.concat("1[]");
			deleteItem = toolkit.createButtonItem(containerComposite, SWT.ICON);
			// deleteItem.setLayoutData("w 18!, h 20!, pad 0 -3 0 0");
			deleteItem.setToolTipText(deleteToolTip);
			deleteItem.setImage(toolkit.getRegisteredImage("IMG_DELETE"));

			deleteItem.addSelectionListener(() -> {
				if (null != dataReferenceManager)
					dataReferenceManager.replace(null);
				setValue(null);
			});
		}
		containerComposite.setLayout(new MigLayout("wrap " + (nbButton + 1) + ", ins 0", columnConstraint));
		// Null paint listener to avoid fading of the border!
		containerComposite.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
			}
		});
		text.setLayoutData("wmax 100% - " + (nbButton * 20));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#addListener(boolean, boolean,
	 * org.adichatz.engine.validation.AValidation)
	 */
	@Override
	public void addListeners() {
		text.addModifyListener((e) -> {
			broadcastChange();
		});
		text.addDisposeListener((e) -> {
			if (null != fieldBindManager)
				fieldBindManager.unbind();
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#broadcastChange()
	 */
	@Override
	public void broadcastChange() {
		if (null != fieldBindManager)
			fieldBindManager.bindTargetToModel();
		else
			getValidation().validate();
		reflow();
	}

	/**
	 * Gets the param map.
	 * 
	 * @return the param map
	 */
	public ParamMap getParamMap() {
		if (null == paramMap)
			paramMap = new ParamMap();
		return paramMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#notifyBindListener()
	 */
	/**
	 * Notify bind listener.
	 */
	public void notifyBindListener() {
		text.notifyListeners(SWT.Modify, null);
	}

	/**
	 * Gets the pluginresources.
	 * 
	 * @return the pluginresources
	 */
	public AdiPluginResources getPluginResources() {
		return pluginResources;
	}

	/**
	 * Sets the plugin resources.
	 * 
	 * @param pluginResources
	 *            the new plugin resources
	 */
	public void setPluginResources(AdiPluginResources pluginResources) {
		this.pluginResources = pluginResources;
	}
}
