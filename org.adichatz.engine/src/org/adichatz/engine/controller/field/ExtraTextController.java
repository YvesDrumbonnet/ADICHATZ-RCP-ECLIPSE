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
package org.adichatz.engine.controller.field;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.engine.widgets.extratext.ExtraText;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtraTextController.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class ExtraTextController extends AFieldController {

	/** The control. */
	protected ExtraText extraText;

	/** The tabFolder when controller is editable (AdiSWT.EDITABLE). */
	protected CTabFolder cTabFolder;

	protected ToolItem refreshItem;

	private String initialValue;

	/** Flag indicating if process is internal (avoid loop) */
	private boolean internal;

	private boolean hasParseError;

	/**
	 * Instantiates a new date controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the generated code
	 */
	public ExtraTextController(final String id, IContainerController parentController, final ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.BORDER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#createControl()
	 */
	@Override
	public void createControl() {
		super.createControl();
		extraText = toolkit.createExtraText(parentController.getComposite(), style);
		// Check if layoutData is correctly set. Nothing could be displayed if layoutData do not set height and width.
		extraText.getStyledText().setLayoutData("h 0:n:n, w 0:n:n");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public ExtraText getControl() {
		return extraText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#getValue()
	 */
	@Override
	public String getValue() {
		String text = extraText.getFormattedText();
		if (null != text && text.length() > 0) {
			return (String) convertTargetToModel(text);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#setValue(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (null == value)
			extraText.getStyledText().setText("");
		else
			setInternalValue((String) convertModelToTarget(value));
	}

	/**
	 * Sets the internal value.
	 *
	 * @param value
	 *            the new internal value
	 */
	public void setInternalValue(String value) {
		if (!internal) {
			internal = true;
			try {
				if ("".equals(value))
					extraText.getStyledText().setText("");
				else {
					extraText.setFormattedText(value);
				}
				if (hasParseError) {
					ABindingService bindingService = getBindingService();
					getValidation().getDirtyableForm().getFormMessageManager().getForm().getMessageManager()
							.removeMessage("error.parsing.text", extraText);
					if (null != bindingService) {
						for (ErrorMessage errorMessage : bindingService.getErrorMessageMap().get(this)
								.toArray(new ErrorMessage[bindingService.getErrorMessageMap().size()])) {
							if (errorMessage.getValidator() instanceof ParseValidator) {
								bindingService.getErrorMessageMap().remove(errorMessage);
							}
						}
						bindingService.fireListener(IEventType.POST_MESSAGE);
						if (bindingService.getErrorMessageMap().isEmpty())
							bindingService.fireListener(IEventType.DIRTY_VALIDATION);
						hasParseError = false;
					}
				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				new ParseValidator().validate();
				logError(e);
			} finally {
				internal = false;
			}
		}
	}

	public void addRefreshItem() {
		new ToolItem(extraText.getToolBar(), SWT.SEPARATOR);
		refreshItem = new ToolItem(extraText.getToolBar(), SWT.CHECK);
		refreshItem.setImage(toolkit.getRegisteredImage("IMG_REFRESH"));
		refreshItem.setToolTipText(EngineTools.getFromEngineBundle("extraText.refresh"));
		refreshItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IEntity<?> entity = getEntity();
				if (null != entity) {
					setValue(initialValue);
					refreshItem.setEnabled(false);
				}
			}
		});
		enableRefreshItem(false);
	}

	@Override
	public void synchronize() {
		super.synchronize();
		if (null != getEntity() && null != fieldBindManager)
			initialValue = (String) ReflectionTools.invokeMethod(fieldBindManager.getField().getGetMethod(), getEntity().getBean());
		else
			initialValue = null;
	}

	private void enableRefreshItem(boolean enabled) {
		refreshItem.setEnabled(enabled);
		extraText.getToolBar().update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#addListeners()
	 */
	@Override
	public void addListeners() {
		extraText.getStyledText().addModifyListener((e) -> {
			broadcastChange();
		});
		AControlListener afterSynchronizeLSNR = new AControlListener(null, IEventType.AFTER_SYNCHRONIZE) {
			@Override
			public void handleEvent(AdiEvent event) {
				// Compute enabled for Refresh item after synchronize otherwise getValue() do not return right value
				enableRefreshItem(!Utilities.equals(initialValue, getValue()));
			}
		};
		parentController.addListener(afterSynchronizeLSNR);
		extraText.addDisposeListener((e) -> {
			if (null != fieldBindManager)
				fieldBindManager.unbind();
			((AWidgetController) parentController).removeListener(afterSynchronizeLSNR);
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
		if (null != refreshItem && !refreshItem.isDisposed() && !refreshItem.isEnabled()) {
			String value = getValue();
			enableRefreshItem(!Utilities.equals(initialValue, value));
		}
		reflow();
	}

	@Override
	public void setEnabled(boolean enabled) {
		ToolBar toolBar = extraText.getToolBar();
		if (toolBar.isVisible()) {
			toolBar.setVisible(false);
			toolBar.setLayoutData(new GridData(0, 0));
			extraText.getStyledText().setEditable(false);
		} else {
			toolBar.setVisible(true);
			toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			extraText.getStyledText().setEditable(true);
		}
		extraText.layout();
		super.setEnabled(enabled);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#notifyBindListener()
	 */
	public void notifyBindListener() {
		extraText.notifyListeners(SWT.Modify, null);
	}

	@Override
	public void pack() {
		extraText.pack();
	}

	@Override
	protected void reflow() {
	}

	private class ParseValidator extends AValidator {
		public ParseValidator() {
			super(ExtraTextController.this, "error.parsing.text");
		}

		@Override
		public int validate() {
			hasParseError = true;
			return setLevel(IMessageProvider.ERROR, EngineTools.getFromEngineBundle("error.parsing.text"));
		}
	}
}
