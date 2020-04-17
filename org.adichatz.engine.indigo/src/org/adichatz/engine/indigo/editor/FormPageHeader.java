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
package org.adichatz.engine.indigo.editor;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.AFormPageCore;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.indigo.controller.LegacyFormPageController;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.AdiContext;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.editor.IFormPage;

// TODO: Auto-generated Javadoc
/**
 * The Class FormPageHeader.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class FormPageHeader {

	/** The class name. */
	private final String className;

	/** The title. */
	private final String title;

	/** The class name. */
	private final String formPageClassName;

	/** The valid. */
	protected boolean valid = true;

	private int[] validStatusTable;

	private AEntityListener statusChangeListener;

	/** The editor controller. */
	private AFormEditor editorController;

	private GencodePath gencodePath;

	private LegacyFormPageController formPageController;

	private AdiContext context;

	protected Map<MultiKey, AListener> listenerMap;

	/** The page number. */
	private Integer pageIndex;

	/**
	 * Instantiates a new form page header.
	 * 
	 * @param className
	 *            the class name
	 * @param title
	 *            the title
	 * @param formPageClassName
	 *            the form page class name
	 */
	public FormPageHeader(AdiContext context, String className, String title, String formPageClassName, int... validStatusTable) {
		this.className = className;
		this.formPageClassName = formPageClassName;
		this.title = title;
		this.validStatusTable = validStatusTable;
		this.context = context;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the class name.
	 * 
	 * @return the class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Gets the controller class name.
	 * 
	 * @return the controller class name
	 */
	public String getFormPageClassName() {
		return formPageClassName;
	}

	/**
	 * Checks if is valid.
	 * 
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Gets the page index.
	 * 
	 * @return the pageIndex
	 */
	public Integer getPageIndex() {
		return pageIndex;
	}

	public void addPage(AFormEditor editorController, IEntity<?> entity, final int pageIndex) {
		if (isValid()) {
			this.editorController = editorController;
			gencodePath = editorController.getPluginResources().getGencodePath();
			boolean pageMustBeAdded = false;
			if (0 != validStatusTable.length) {
				for (int status : validStatusTable) {
					if (entity.getStatus() == status) {
						pageMustBeAdded = true;
						break;
					}
				}
				if (!pageMustBeAdded) {
					/* add status listener on entity */
					statusChangeListener = new AEntityListener(editorController, IEventType.CHANGE_STATUS) {
						boolean first = true;

						@Override
						public void handleEntityEvent(AdiEntityEvent event) {
							if (first) {
								for (int status : validStatusTable) {
									if (entity.getStatus() == status) {
										addpage(pageIndex);
										/* Adds pages only once */
										first = false;
										break;
									}
								}
							}

						}
					};
					entity.addEntityListener(statusChangeListener);
				}
			} else
				pageMustBeAdded = true;
			if (pageMustBeAdded) {
				addpage(pageIndex);
			}
		}
	}

	private void addpage(int pageIndex) {
		this.pageIndex = pageIndex;
		try {
			editorController.addPage(pageIndex, (IFormPage) gencodePath.instantiateClass(formPageClassName,
					new Class[] { AFormEditor.class, FormPageHeader.class }, new Object[] { editorController, this }));
		} catch (PartInitException e) {
			logError("Error when adding page!", e);
		}
	}

	/**
	 * Launch.
	 * 
	 * @param editorGencode
	 *            the editor gencode
	 * @param managedForm
	 *            the managed form
	 * 
	 * @return the a form page controller
	 */
	public LegacyFormPageController launch(FormEditorCore editorGenCode, ManagedForm managedForm) {
		GencodePath gencodePath = editorGenCode.getController().getPluginResources().getGencodePath();
		AFormPageCore formPageGenCode = (AFormPageCore) gencodePath.instantiateClass(className,
				new Class[] { AdiContext.class, IContainerController.class },
				new Object[] { context, editorGenCode.getController() });
		formPageController = (LegacyFormPageController) formPageGenCode.getController();
		formPageController.init(managedForm);
		AListener.fireListener(listenerMap, IEventType.AFTER_INITIALIZE);

		formPageGenCode.postCreate(formPageController.getInitialEntity());
		if (null != statusChangeListener)
			formPageController.getControl().addDisposeListener((e) -> {
				formPageController.getEntity().disposeListener(statusChangeListener);
			});
		return formPageController;
	}

	public LegacyFormPageController getFormPageController() {
		return formPageController;
	}

	public void addListener(AListener listener) {
		if (null == listenerMap)
			listenerMap = new HashMap<MultiKey, AListener>();
		listenerMap.put(new MultiKey(listener.getId()), listener);
	}
}
