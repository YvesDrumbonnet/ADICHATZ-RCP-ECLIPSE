/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffus�e par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie, de modification et de redistribution accord�s par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limit�e. Pour les m�mes raisons, seule une responsabilit� restreinte
 * p�se sur l'auteur du programme, le titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard l'attention de l'utilisateur est attir�e sur les risques associ�s au chargement, � l'utilisation, � la modification
 * et/ou au d�veloppement et � la reproduction du logiciel par l'utilisateur �tant donn� sa sp�cificit� de logiciel libre, qui peut
 * le rendre complexe � manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels avertis poss�dant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invit�s � charger et tester l'ad�quation du logiciel � leurs
 * besoins dans des conditions permettant d'assurer la s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, �
 * l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accept� les termes.
 *******************************************************************************/
package org.adichatz.engine.widgets;

import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;

// TODO: Auto-generated Javadoc
/**
 * The Class MultiChoiceViewer.
 */
public class MultiChoiceViewer extends AbstractListViewer {

	/** The multi choice. */
	private MultiChoice multiChoice;

	/**
	 * The Constructor.
	 *
	 * @param multiChoice
	 *            the multi choice
	 */
	public MultiChoiceViewer(MultiChoice multiChoice) {
		this.multiChoice = multiChoice;
		hookControl(multiChoice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listAdd(java.lang.String, int)
	 */
	@Override
	protected void listAdd(String string, int index) {
		multiChoice.add(string, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listSetItem(int, java.lang.String)
	 */
	@Override
	protected void listSetItem(int index, String string) {
		multiChoice.add(string, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listGetSelectionIndices()
	 */
	@Override
	protected int[] listGetSelectionIndices() {
		return multiChoice.getSelectedIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listGetItemCount()
	 */
	@Override
	protected int listGetItemCount() {
		return multiChoice.getItemCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listSetItems(java.lang.String[])
	 */
	@Override
	protected void listSetItems(String[] labels) {
		multiChoice.setElements(labels);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listRemoveAll()
	 */
	@Override
	protected void listRemoveAll() {
		multiChoice.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listRemove(int)
	 */
	@Override
	protected void listRemove(int index) {
		multiChoice.removeAt(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	@Override
	public MultiChoice getControl() {
		return multiChoice;
	}

	/**
	 * Returns this list viewer's list control. If the viewer was not created on a multiChoice control, some kind of unchecked
	 * exception is thrown.
	 *
	 * @return the list control
	 * @since 3.3
	 */
	public MultiChoice getMultiCHoice() {
		Assert.isNotNull(multiChoice);
		return multiChoice;
	}

	/*
	 * Do nothing.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.StructuredViewer#reveal(java.lang.Object)
	 */
	@Override
	public void reveal(Object element) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listSetSelection(int[])
	 */
	@Override
	protected void listSetSelection(int[] ixs) {
		listDeselectAll();
		for (int ix : ixs) {
			multiChoice.selectAt(ix);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listDeselectAll()
	 */
	@Override
	protected void listDeselectAll() {
		multiChoice.deselectAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.AbstractListViewer#listShowSelection()
	 */
	@Override
	protected void listShowSelection() {
	}

	public void setText() {
		StringBuffer sb = new StringBuffer();
		Iterator<?> iterator = ((StructuredSelection) getSelection()).iterator();
		while (iterator.hasNext()) {
			String label = ((LabelProvider) getLabelProvider()).getText(iterator.next());
			sb.append(label);
			if (iterator.hasNext()) {
				sb.append(",");
			}
		}
		multiChoice.getLabel().setText(sb.toString());
	}
}
