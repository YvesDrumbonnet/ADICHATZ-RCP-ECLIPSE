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
package org.adichatz.engine.e4.resource;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.collection.FormPageController;
import org.adichatz.engine.core.AFormPageCore;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.AdiEntityEvent;
import org.adichatz.engine.listener.IEventType;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;

// TODO: Auto-generated Javadoc
/**
 * The Class PageManager.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class PageManager {

	/** The class name. */
	private final String className;

	/** The title. */
	private final String title;

	private final Image image;

	/** The valid status table. */
	private int[] validStatusTable;

	/** The form page controller. */
	private FormPageController formPageController;

	/** The page id. */
	private String pageId;

	/** The status change listener. */
	private AEntityListener statusChangeListener;

	private boolean valid;

	private Composite parent;

	private ManagedForm managedForm;

	/**
	 * Instantiates a new form page header.
	 * 
	 * @param className
	 *            the class name
	 * @param pageId
	 *            the pageId
	 * @param title
	 *            the title
	 * @param validStatusTable
	 *            the valid status table
	 */
	public PageManager(String className, String pageId, String title, int... validStatusTable) {
		this(className, pageId, title, null, validStatusTable);
	}

	/**
	 * Instantiates a new page manager.
	 * 
	 * @param className
	 *            the class name
	 * @param pageId
	 *            the page id
	 * @param title
	 *            the title
	 * @param image
	 *            the image
	 * @param validStatusTable
	 *            the valid status table
	 */
	public PageManager(String className, String pageId, String title, Image image, int... validStatusTable) {
		this.className = className;
		this.pageId = pageId;
		this.title = EngineTools.isEmpty(title) ? pageId : title;
		this.validStatusTable = validStatusTable;
		this.image = image;

	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	public Image getImage() {
		return image;
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
	 * Checks if is valid.
	 * 
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Gets the page id.
	 * 
	 * @return the page id
	 */
	public String getPageId() {
		return pageId;
	}

	public Composite getParent() {
		return parent;
	}

	public void setParent(Composite parent) {
		this.parent = parent;
	}

	/**
	 * Gets the valid status table.
	 * 
	 * @return the valid status table
	 */
	public int[] getValidStatusTable() {
		return validStatusTable;
	}

	/**
	 * Adds the page.
	 * 
	 * @param entity
	 *            the entity
	 * @param boundedPart
	 *            the bounded part
	 */
	public void addPage(IEntity<?> entity, final BoundedPart boundedPart) {
		boolean pageMustBeAdded = false;
		if (0 != validStatusTable.length) {
			for (int status : validStatusTable) {
				if (entity.getStatus() == status) {
					pageMustBeAdded = true;
					break;
				}
			}
			if (!pageMustBeAdded) {
				valid = false;
				/* add status listener on entity */
				statusChangeListener = new AEntityListener(null, IEventType.CHANGE_STATUS) {
					@Override
					public void handleEntityEvent(AdiEntityEvent event) {
						for (int status : validStatusTable) {
							if (entity.getStatus() == status) {
								boundedPart.addPage(PageManager.this);
								/* Adds pages only once */
								valid = true;
								break;
							}
						}
						dispose();
					}
				};
				entity.addEntityListener(statusChangeListener);
			}
		} else
			pageMustBeAdded = true;
		if (pageMustBeAdded) {
			boundedPart.addPage(this);
			valid = true;
		}
	}

	public void init(IEntity<?> entity, AFormPageCore formPageGenCode) {
		formPageController = (FormPageController) formPageGenCode.getController();
		formPageController.init(managedForm);

		formPageGenCode.postCreate(entity);
		if (null != statusChangeListener)
			formPageController.getControl().addDisposeListener((e) -> {
				formPageController.getEntity().disposeListener(statusChangeListener);
			});
	}

	/**
	 * Gets the form page controller.
	 * 
	 * @return the form page controller
	 */
	public FormPageController getFormPageController() {
		return formPageController;
	}

	public ManagedForm getManagedForm() {
		return managedForm;
	}

	public void setManagedForm(ManagedForm managedForm) {
		this.managedForm = managedForm;
	}
}
