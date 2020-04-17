/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant 
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe � 
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, 
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�. 
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.engine.common;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.extra.AMessageDialog;
import org.adichatz.engine.extra.AdiMessageDialog;
import org.adichatz.engine.validation.ABindingService;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import net.miginfocom.swt.MigLayout;

public class MessageUtility {

	public static void displayCannotRefreshEntity(final List<IEntity<?>> entities) throws InterruptedException {
		AdiMessageDialog messageDialog = new AdiMessageDialog(Display.getCurrent(), MessageDialog.NONE,
				AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_BUNDLE, "IMG_BIG_ERROR.png"),
				getFromEngineBundle("detail.entityLockedInOtherEnvironment"),
				getFromEngineBundle("detail.entityLockedInOtherEnvironment"),
				getFromEngineBundle("detail.entityCannotBeRefreshedInEnvironment"),
				getFromEngineBundle("detail.refreshingOtherEntities")) {
			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				Button button = createButton(parent, IDialogConstants.CANCEL_ID, getFromEngineBundle("continue"), true);
				button.setFocus();
			}

			@Override
			public void createMessage(Composite parent) {
				Composite composite = toolkit.createComposite(parent);
				composite.setLayout(new MigLayout("wrap 2"));
				CLabel cLabel;
				for (IEntity<?> entity : entities) {
					toolkit.createLabel(composite, getFromEngineBundle("entityType") + ": " + entity.getEntityMM().getEntityId()
							+ "      " + getFromEngineBundle("identifier") + ": " + entity.getBeanId().toString());
					cLabel = new CLabel(composite, SWT.LEFT);
					cLabel.setText(entity.getLockingBindingService().getMessage());
					cLabel.setImage(entity.getLockingBindingService().getImage());
					toolkit.adapt(cLabel, false, false);
				}
			}
		};
		if (IDialogConstants.CANCEL_ID != messageDialog.open())
			// Cancel the refresh process
			throw new InterruptedException();
	}

	public static void displayEntityToDelete(final IEntity<?> entity, final ABindingService bindingService) {
		AdiMessageDialog messageDialog = new AdiMessageDialog(Display.getCurrent(), MessageDialog.NONE,
				AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_BUNDLE, "IMG_BIG_ERROR.png"),
				getFromEngineBundle("entityDoesNoLongerExist"), getFromEngineBundle("entityDoesNoLongerExist"),
				getFromEngineBundle("missingEntityElements"), getFromEngineBundle("entityWillbeRemoved")) {

			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				Button button = createButton(parent, IDialogConstants.CANCEL_ID, getFromEngineBundle("continue"), true);
				button.setFocus();
			}

			@Override
			public void createMessage(Composite parent) {
				Composite composite = toolkit.createComposite(parent);
				composite.setLayout(new MigLayout("wrap 2"));
				toolkit.createLabel(composite, getFromEngineBundle("entityType") + ":");
				toolkit.createLabel(composite, entity.getEntityMM().getEntityId());
				toolkit.createLabel(composite, getFromEngineBundle("identifier") + ":");
				toolkit.createLabel(composite, entity.getBeanId().toString());
				toolkit.createLabel(composite, getFromEngineBundle("editor") + ":");

				if (null != bindingService) {
					CLabel cLabel = new CLabel(composite, SWT.LEFT);
					cLabel.setText(bindingService.getMessage());
					cLabel.setImage(bindingService.getImage());
					toolkit.adapt(cLabel, false, false);
				}
			}

		};
		messageDialog.open();
	}

	public static void displayEntityMessage(final IEntity<?> entity, String title, String sectionMessage, final boolean locking) {
		AMessageDialog messageDialog = new AdiMessageDialog(Display.getCurrent(), MessageDialog.ERROR,
				AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_BUNDLE, "IMG_BIG_ERROR.png"), title, title,
				sectionMessage, "") {
			@Override
			protected void createButtonsForButtonBar(Composite parent) {
				Button button = createButton(parent, IDialogConstants.CANCEL_ID, getFromEngineBundle("continue"), false);
				button.setFocus();
			}

			@Override
			public void createMessage(Composite parent) {
				Composite composite = toolkit.createComposite(parent);
				composite.setLayout(new MigLayout("wrap 2"));
				toolkit.createLabel(composite, getFromEngineBundle("entityType") + ":");
				toolkit.createLabel(composite, entity.getEntityMM().getEntityId());
				toolkit.createLabel(composite, getFromEngineBundle("identifier") + ":");
				toolkit.createLabel(composite, entity.getBeanId().toString());

				if (locking && null != entity.getLockingBindingService()) {
					toolkit.createLabel(composite, getFromEngineBundle("editor") + ":");
					CLabel cLabel = new CLabel(composite, SWT.LEFT);
					cLabel.setText(entity.getLockingBindingService().getMessage());
					cLabel.setImage(entity.getLockingBindingService().getImage());
					toolkit.adapt(cLabel, false, false);
				}
			}
		};
		messageDialog.open();
	}
}
