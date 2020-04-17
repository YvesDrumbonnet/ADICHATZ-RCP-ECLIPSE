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
package org.adichatz.jpa.action;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.util.List;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.action.ADetailAction;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.MessageUtility;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.validation.ABindingService;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteEntityAction.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class DeleteEntityAction extends ADetailAction {

	public void init() {
		setText(getFromJpaBundle("detail.deleteEntity"));
		setToolTipText(getFromJpaBundle("detail.deleteEntityToolTip"));
		setImageDescriptor(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImageDescriptor("IMG_ENTITY_DELETE"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void runAction() {
		final IEntity<?> entity = entityManagerController.getEntity();
		deleteEntity(entity, entityManagerController.getBindingService(), getImageDescriptor().createImage(),
				getParam("STOP_AFTER_FIRST"), getFromJpaBundle("detail.delete.message"));
	}

	public static void deleteEntity(final IEntity<?> entity, ABindingService bindingService, Image image, Object stopAfterFirst,
			String labelMessage) {
		if (!(new CheckBeforeDelete().test(Display.getCurrent(), entity, stopAfterFirst)))
			return;
		ConfirmFormDialog.check(Display.getCurrent(), getFromJpaBundle("detail.delete.deletingEntity"), image,
				new IConfirmContent() {
					@Override
					public void okPressed(List<Control> complementControls) {
						if (entity.getLockingBindingService() != null
								&& !entity.getLockingBindingService().equals(bindingService)) {

							// Send message <<Entity was locked in another editor>>
							// User can continue skipping the current entity or cancel the refresh process.
							MessageUtility.displayEntityMessage(entity,
									getFromEngineBundle("detail.entityLockedInOtherEnvironment"),
									getFromJpaBundle("detail.entityCannotBeDeletedInEnvironment"), true);
							return;
						}

						if (entity.getEntityMM().getDataAccess().lock(bindingService, IEntityConstants.REMOVE, entity)) {
							/*
							 * Fire change on all manyToOnes relationships.
							 */
							entity.fireManyToOnes(true);
							/*
							 * Locks the entity
							 */
							entity.lock(true, bindingService);

							bindingService.removeMessage(entity);
							bindingService.fireListener(IEventType.DIRTY_VALIDATION);

							// EngineTools.openDialog(MessageDialog.INFORMATION, getFromJpaBundle("detail.deleteEntity"),
							// getFromJpaBundle("detail.deleteEntityMessage"));
						} else {
							String message = getFromJpaBundle("detail.entityCannotBeLockMessage");
							LogBroker.displayError(getFromJpaBundle("detail.entityCannotBeLockTitle"),
									new RuntimeException(message), message);
							return;
						}
					}

					@Override
					public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
						parent.setLayout(new MigLayout("wrap 2", "grow, fill", "[]30[]"));
						Label label = toolkit.createLabel(parent, "");
						label.setImage(
								AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_BUNDLE, "IMG_BIG_WARNING.png"));

						label = toolkit.createLabel(parent, labelMessage);
						label.setFont(JFaceResources.getBannerFont());
						label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

						label = toolkit.createLabel(parent, getFromJpaBundle("detail.delete.confirmDeletingRecord"));
						label.setLayoutData("span 2, center");
					}
				});
	}
}
