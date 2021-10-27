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
package org.adichatz.engine.e4.resource;

import org.adichatz.engine.e4.part.IntroPart;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.extra.AOutlineListener;
import org.adichatz.engine.extra.ARecentOutlineItem;
import org.adichatz.engine.extra.ARecentOutlinePage;
import org.adichatz.engine.extra.OutlineEvent;
import org.adichatz.engine.extra.OutlineEvent.EVENT_TYPE;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class RecentOutlinePage.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class RecentOutlinePage extends ARecentOutlinePage {

	/** The scrolled form. */
	private ScrolledForm scrolledForm;

	private PShelf pshelf;

	/**
	 * Instantiates a new recent outline page.
	 */
	public RecentOutlinePage() {
		THIS = this;
		getOutlinePageListeners().add(new AOutlineListener(EVENT_TYPE.REFRESH_OUTLINE) {
			@Override
			public void handleEvent(OutlineEvent event) {
				refresh();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.IOutlinePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		OutlinePart.getInstance().setDefaultPage(this);

		scrolledForm = getToolkit().createScrolledForm(parent);
		scrolledForm.getBody().setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

		pshelf = getToolkit().createPShelf(scrolledForm.getBody(), SWT.NONE);
		initializeItems();
		if (0 < pshelf.getItems().length) {
			PShelfItem item = pshelf.getItems()[0];
			pshelf.setSelection(item);
			Event event = new Event();
			event.item = item;
			pshelf.notifyListeners(SWT.Selection, event);
		}
		scrolledForm.reflow(true);
	}

	public void addPShelfItem(ARecentOutlineItem recentOutlineItem) {
		PShelfItem pshelfItem = getToolkit().createPShelfItem(pshelf, SWT.NONE);
		pshelfItem.setData("#RECENT_ITEM#", recentOutlineItem);
		pshelfItem.setText(recentOutlineItem.getText());
		pshelfItem.setImage(recentOutlineItem.getImage());
		pshelfItem.getBody().setLayout(recentOutlineItem.getLayout());
		pshelf.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.item.equals(pshelfItem))
					recentOutlineItem.selectItem(e);
			}
		});
		recentOutlineItem.setPshelfItem(pshelfItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.IOutlinePage#getComposite()
	 */
	@Override
	public Composite getComposite() {
		return scrolledForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.IOutlinePage#getLinkedPart()
	 */
	@Override
	public IntroPart getLinkedPart() {
		return IntroPart.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.IOutlinePage#refresh()
	 */
	@Override
	public void refresh() {
		for (PShelfItem pshelItem : pshelf.getItems()) {
			((ARecentOutlineItem) pshelItem.getData("#RECENT_ITEM#")).refresh();
		}
	}
}
