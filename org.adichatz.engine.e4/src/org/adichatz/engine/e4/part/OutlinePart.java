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
package org.adichatz.engine.e4.part;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.extra.ABindingOutlinePage;
import org.adichatz.engine.extra.IOutlinePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

// TODO: Auto-generated Javadoc
/**
 * The Class OutlinePart.
 * 
 * @author Yves Drumbonnet
 */
public class OutlinePart extends AAdiBasicPart {

	/** The THIS. */
	private static OutlinePart THIS;

	/** The parent. */
	@Inject
	private Composite parent;

	/**
	 * Gets the single instance of OutlinePart.
	 * 
	 * @return single instance of OutlinePart
	 */
	public static OutlinePart getInstance() {
		return THIS;
	}

	/** The default page. */
	private IOutlinePage defaultPage;

	/** The current page. */
	private IOutlinePage currentPage;

	/**
	 * Instantiates a new outline part.
	 */
	public OutlinePart() {
		THIS = this;
	}

	/**
	 * Creates the control.
	 */
	@PostConstruct
	public void createControl() {
		parent.setLayout(new PageBookLayout());
		currentPage = defaultPage;
		IntroPart.getInstance().partActivation();
	}

	public IOutlinePage getDefaultPage() {
		if (null == defaultPage)
			defaultPage = new DefaultPage();
		return defaultPage;
	}

	public void setDefaultPage(IOutlinePage defaultPage) {
		this.defaultPage = defaultPage;
	}

	/**
	 * Gets the parent.
	 * 
	 * @return the parent
	 */
	public Composite getParent() {
		return parent;
	}

	/**
	 * Gets the current page.
	 * 
	 * @return the current page
	 */
	public IOutlinePage getCurrentPage() {
		return currentPage;
	}

	/**
	 * Shows the given outline page.
	 * 
	 * @param outlinePage
	 *            the outline page
	 */
	public void showPage(IOutlinePage outlinePage) {
		if (null != outlinePage)
			currentPage = outlinePage;
		else
			currentPage = getDefaultPage();
		currentPage.showPage(parent);
	}

	/**
	 * The Class DefaultPage.
	 * 
	 * @author Yves Drumbonnet
	 */
	class DefaultPage extends ABindingOutlinePage {

		/** The composite. */
		private Composite composite;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.engine.extra.IOutlinePage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public void createControl(Composite parent) {
			composite = AdichatzApplication.getInstance().getFormToolkit().createComposite(parent);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.engine.extra.IOutlinePage#getComposite()
		 */
		@Override
		public Composite getComposite() {
			return composite;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.engine.extra.IOutlinePage#getLinkedPart()
		 */
		@Override
		public BoundedPart getLinkedPart() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.adichatz.engine.extra.IOutlinePage#refresh()
		 */
		@Override
		public void refresh() {
		}
	}

	/**
	 * Special layout for OutlinePart.
	 * 
	 * @author Yves Drumbonnet
	 */
	private class PageBookLayout extends Layout {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite, int, int, boolean)
		 */
		protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
			if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
				return new Point(wHint, hHint);
			}

			Point result = null;
			if (currentPage != null) {
				result = currentPage.getComposite().computeSize(wHint, hHint, flushCache);
			} else {
				// Rectangle rect= composite.getClientArea();
				// result= new Point(rect.width, rect.height);
				result = new Point(0, 0);
			}
			if (wHint != SWT.DEFAULT) {
				result.x = wHint;
			}
			if (hHint != SWT.DEFAULT) {
				result.y = hHint;
			}
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite, boolean)
		 */
		protected void layout(Composite composite, boolean flushCache) {
			if (null != currentPage && null != currentPage.getComposite() && !currentPage.getComposite().isDisposed()) {
				currentPage.getComposite().setBounds(composite.getClientArea());
			}
		}
	}
}
