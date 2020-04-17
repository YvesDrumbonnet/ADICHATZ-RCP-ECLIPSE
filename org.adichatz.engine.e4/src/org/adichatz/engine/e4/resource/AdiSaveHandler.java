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

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adichatz.engine.e4.part.BoundedPart;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.eclipse.jface.dialogs.IDialogConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiSaveHandler.
 * 
 * Replace default implementation found in org.eclipse.e4.ui.workbench.renderers.swt.WBWRenderer.
 * 
 * If errors found in BoundedParts, cancel process.
 * 
 * If changes found in BoundedParts propose Save or Change.
 * 
 * Propose default ISaveHandler implementation for other part.
 */
public class AdiSaveHandler implements ISaveHandler {
	// AVISATZ landmark A Supprimer

	/** The eclipse context. */
	private IEclipseContext eclipseContext;

	/** The e4 save handler. */
	private ISaveHandler e4SaveHandler;

	/**
	 * Instantiates a new adi save handler.
	 * 
	 * @param eclipseContext
	 *            the eclipse context
	 */
	public AdiSaveHandler(IEclipseContext eclipseContext) {
		this.eclipseContext = eclipseContext;
		e4SaveHandler = eclipseContext.get(ISaveHandler.class);
	}

	/**
	 * Cancel response.
	 * 
	 * @param size
	 *            the size
	 * @return the save[]
	 */
	private Save[] cancelResponse(int size) {
		Save[] response = new Save[size];
		Arrays.fill(response, Save.CANCEL);
		return response;
	}

	private Save getSave(BoundedPart boundedPart) {
		int saveReturn = new SaveOrAbortChangesDialog((EPartService) eclipseContext.get(EPartService.class), boundedPart).open();
		switch (saveReturn) {
		case IDialogConstants.CANCEL_ID:
			return Save.CANCEL;
		case IDialogConstants.YES_ID:
			boundedPart.getBoundedPartChangeManager().doSave();
			return Save.YES;
		case IDialogConstants.NO_ID:
			boundedPart.getBoundedPartChangeManager().doRefresh(false);
			return Save.NO;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.ui.workbench.modeling.ISaveHandler#save(org.eclipse.e4.ui.model.application .ui.basic.MPart, boolean)
	 */
	public boolean save(MPart dirtyPart, boolean confirm) {
		if (confirm) {
			switch (promptToSave(dirtyPart)) {
			case NO:
				return true;
			case CANCEL:
				return false;
			case YES:
				break;
			}
		}

		Object client = dirtyPart.getObject();
		try {
			ContextInjectionFactory.invoke(client, Persist.class, dirtyPart.getContext());
		} catch (RuntimeException e) {
			logError(EngineE4Util.getFromEngineE4Bundle("fail.persist.contents", dirtyPart.getElementId()));
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.ui.workbench.modeling.ISaveHandler#saveParts(java.util.Collection, boolean)
	 */
	public boolean saveParts(Collection<MPart> dirtyParts, boolean confirm) {
		if (confirm) {
			List<MPart> dirtyPartsList = Collections.unmodifiableList(new ArrayList<MPart>(dirtyParts));
			Save[] decisions = promptToSave(dirtyPartsList);
			for (Save decision : decisions) {
				if (decision == Save.CANCEL) {
					return false;
				}
			}

			for (int i = 0; i < decisions.length; i++) {
				if (decisions[i] == Save.YES) {
					if (!save(dirtyPartsList.get(i), false)) {
						return false;
					}
				}
			}
			return true;
		}

		for (MPart dirtyPart : dirtyParts) {
			if (!save(dirtyPart, false)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.ui.workbench.modeling.ISaveHandler#promptToSave(org.eclipse.e4.ui.model.application.ui.basic.MPart)
	 */
	@Override
	public Save promptToSave(MPart dirtyPart) {
		if (dirtyPart.getObject() instanceof BoundedPart)
			return getSave((BoundedPart) dirtyPart.getObject());
		return e4SaveHandler.promptToSave(dirtyPart);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.e4.ui.workbench.modeling.ISaveHandler#promptToSave(java.util.Collection)
	 */
	@Override
	public Save[] promptToSave(Collection<MPart> dirtyParts) {
		int size = dirtyParts.size();
		List<MPart> e4DirtyParts = new ArrayList<MPart>();
		Save[] saves = new Save[size];
		int[] e4Links = new int[size];
		int i = 0;
		int e4SavesIndex = 0;
		for (MPart dirtyPart : dirtyParts) {
			Object part = dirtyPart.getObject();
			if (part instanceof BoundedPart && ((BoundedPart) part).isDirty()) {
				BoundedPart boundedPart = (BoundedPart) part;
				Save save = getSave(boundedPart);
				if (Save.CANCEL == save)
					return cancelResponse(size);
				saves[i] = save;
			} else {
				e4DirtyParts.add(dirtyPart);
				e4Links[e4SavesIndex++] = i;
			}
			i++;
		}

		if (e4SavesIndex > 0) {
			Save[] e4Saves = e4SaveHandler.promptToSave(e4DirtyParts);
			int k = 0;
			for (Save save : e4Saves) {
				if (Save.CANCEL == save)
					return cancelResponse(size);
				saves[e4Links[k++]] = save;
			}
		}
		return saves;
	}
}