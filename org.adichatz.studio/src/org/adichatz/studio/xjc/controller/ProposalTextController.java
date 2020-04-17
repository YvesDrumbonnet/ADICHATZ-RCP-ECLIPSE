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
package org.adichatz.studio.xjc.controller;

import java.util.List;

import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.widgets.AdiControlDecoration;
import org.adichatz.engine.widgets.supplement.AdiContentProposalAdapter;
import org.adichatz.engine.widgets.supplement.ITextControllerProposal;
import org.adichatz.engine.widgets.supplement.TextViewerContentProposalProvider;
import org.adichatz.engine.widgets.supplement.ViewerContentProposal;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class IdTextController.
 */
public abstract class ProposalTextController extends TextController {

	private TextProposal textProposal;

	private LabelProvider labelProvider;

	private AdiControlDecoration controlDecoration;

	private KeyAdapter keyAdapter;

	/**
	 * Instantiates a new id text controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public ProposalTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.TextController#createControl()
	 */
	@Override
	public void createControl() {
		super.createControl();
		createProposals();
	}

	protected void createProposals() {
		textProposal = new TextProposal(this, labelProvider, null) {
			@Override
			public List<?> getInitialProposals() {
				return getProposals();
			}
		};
		controlDecoration = new AdiControlDecoration(getControl(), SWT.TOP | SWT.LEFT,
				AdiControlDecoration.CONTENT_PROPOSSAL_DECORATOR_IMAGE);
		controlDecoration.setShowOnlyOnFocus(true);
		keyAdapter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Object currentValue = textProposal.getCurrentValue();
				if (SWT.ESC == e.character && !currentValue.equals(getControl().getText()))
					setValue(currentValue);
			}
		};
		getControl().addKeyListener(keyAdapter);
	}

	public void disposeTextProposal() {
		getControl().removeKeyListener(keyAdapter);
		controlDecoration.dispose();
	}

	protected abstract List<?> getProposals();

	class TextProposal implements ITextControllerProposal {

		/** The current value. */
		protected Object currentValue;

		/** The viewer content proposal provider. */
		protected TextViewerContentProposalProvider viewerContentProposalProvider;

		/** The proposal adapter. */
		protected AdiContentProposalAdapter proposalAdapter;

		/** The initial proposals. */
		@SuppressWarnings("rawtypes")
		protected List initialProposals;

		/** The label provider. */
		protected LabelProvider labelProvider;

		/**
		 * Instantiates a new text controller proposal.
		 * 
		 * @param textController
		 *            the text controller
		 * @param labelProvider
		 *            the label provider
		 * @param initialProposals
		 *            the initial proposals
		 */
		public TextProposal(final TextController textController, LabelProvider labelProvider,
				@SuppressWarnings("rawtypes") List initialProposals) {
			this.initialProposals = initialProposals;
			if (null == labelProvider)
				this.labelProvider = new LabelProvider() {
					@Override
					public String getText(Object element) {
						return (String) element;
					}
				};
			else
				this.labelProvider = labelProvider;

			currentValue = textController.getControl().getText();

			viewerContentProposalProvider = new TextViewerContentProposalProvider(this, false);
			proposalAdapter = new AdiContentProposalAdapter(textController.getControl(), new TextContentAdapter(),
					viewerContentProposalProvider, KeyStroke.getInstance(SWT.CTRL, ' '), null);
			proposalAdapter.setProposalAcceptanceStyle(AdiContentProposalAdapter.PROPOSAL_REPLACE);
			addContentProposalListener(textController);
		}

		public Object getCurrentValue() {
			return currentValue;
		}

		/**
		 * Sets the current value.
		 * 
		 * @param currentValue
		 *            the currentValue to set
		 */
		public void setCurrentValue(Object currentValue) {
			this.currentValue = currentValue;
		}

		/**
		 * Adds the content proposal listener.
		 * 
		 * This method could be overriden to specify other process whan accpeting value
		 * 
		 * @param textController
		 *            the text controller
		 */
		protected void addContentProposalListener(final TextController textController) {
			proposalAdapter.addContentProposalListener(new IContentProposalListener() {
				@Override
				public void proposalAccepted(IContentProposal proposal) {
					textController.setValue(((ViewerContentProposal) proposal).getElement());
					proposalAdapter.closeProposalPopup();
					textController.getControl().notifyListeners(SWT.Modify, null);
				}
			});
		}

		/**
		 * Gets the proposal adapter.
		 * 
		 * @return the proposalAdapter
		 */
		public AdiContentProposalAdapter getProposalAdapter() {
			return proposalAdapter;
		}

		/**
		 * Gets the initial proposals.
		 * 
		 * @return the initial proposals
		 */
		public List<?> getInitialProposals() {
			return initialProposals;
		}

		/**
		 * Gets the label provider.
		 * 
		 * @return the label provider
		 */
		public LabelProvider getLabelProvider() {
			return labelProvider;
		}
	}
}
