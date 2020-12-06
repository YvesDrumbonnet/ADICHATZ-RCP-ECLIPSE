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
package org.adichatz.studio.xjc.editor;

import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.controller.field.HyperlinkController;
import org.adichatz.engine.controller.field.LabelController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.EditableFormText;
import org.adichatz.studio.db4o.Db4oTools;
import org.adichatz.studio.db4o.model.Element;
import org.adichatz.studio.db4o.model.Item;
import org.adichatz.studio.xjc.controller.XjcFocusListener;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.part.ViewPart;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class XjcFieldViewPart.
 * 
 * @author Yves Drumbonnet
 *
 */
public class XjcFieldViewPart extends ViewPart {

	/** The ID. */
	public static String ID = XjcFieldViewPart.class.getName();

	/**
	 * Gets the single instance of XjcFieldViewPart.
	 * 
	 * @return single instance of XjcFieldViewPart
	 */
	public static XjcFieldViewPart getInstance() {
		return THIS;
	}

	/** The THIS. */
	private static XjcFieldViewPart THIS;

	/** The current controller. */
	private AWidgetController currentController;

	/** The toolkit. */
	private AdiFormToolkit toolkit;

	/** The page book. */
	private ScrolledPageBook pageBook;

	/** The display part thread. */
	public DisplayPartThread displayPartThread;

	/**
	 * Instantiates a new xjc field view part.
	 */
	public XjcFieldViewPart() {
		THIS = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
		pageBook = toolkit.createPageBook(parent, SWT.TOP | SWT.FLAT | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		pageBook.addDisposeListener((e) -> {
			THIS = null;
		});
		IWorkbenchPage activePage = getSite().getWorkbenchWindow().getActivePage();
		if (null == activePage)
			showEmptyPage();
		else {
			IWorkbenchPart activePart = getSite().getWorkbenchWindow().getActivePage().getActivePart();
			if (activePart instanceof StudioOutlinePage && null != XjcFocusListener.getFocusedController())
				XjcFocusListener.getFocusedController().getControl().notifyListeners(SWT.FocusIn, null);
			else
				showEmptyPage();
		}
	}

	public void showEmptyPage() {
		pageBook.showEmptyPage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	/**
	 * Display field.
	 * 
	 * @param controller
	 *            the controller
	 * @param isField
	 *            the is field
	 */
	public synchronized void displayHelp(AWidgetController controller, boolean isField) {
		if (null != displayPartThread)
			displayPartThread.interrupt();
		displayPartThread = new DisplayPartThread(controller, isField);
		displayPartThread.run();
	}

	/**
	 * The Class DisplayPartThread.
	 * 
	 * @author Yves Drumbonnet
	 *
	 */
	class DisplayPartThread extends Thread {

		/** The is field. */
		private boolean isField;

		/** The controller. */
		private AWidgetController controller;

		/**
		 * Instantiates a new display part thread.
		 * 
		 * @param controller
		 *            the controller
		 * @param methodName
		 *            the method name
		 */
		private DisplayPartThread(AWidgetController controller, boolean isField) {
			this.controller = controller;
			this.isField = isField;
		}

		/** The description. */
		private String description;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			if (!controller.equals(currentController)) {
				if (isField) {
					if (null != ((AFieldController) controller).getProperty()) {
						Item item = Db4oTools.getInstance().getItem(null, getEntityId(controller.getParentController()),
								((AFieldController) controller).getProperty());
						if (null != item) {
							description = item.getDescription();
							if (null == description)
								description = item.getProperty().getDescription();
						}
					}
				} else {
					Element element = Db4oTools.getInstance().getElement(null, getEntityId((ICollectionController) controller),
							true);
					if (null != element)
						description = element.getDescription();
				}
				pageBook.getDisplay().asyncExec(() -> {
					try {
						if (null != currentController && null != pageBook.getCurrentPage()) {
							pageBook.removePage(currentController);
							currentController = null;
						}
						final Composite page = pageBook.createPage(controller);

						currentController = controller;

						page.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

						String scrolledFormText;
						ScrolledForm parentScrolledForm = null;
						ScrolledForm scrolledForm = toolkit.createScrolledForm(page);
						if (isField && null == ((AFieldController) currentController).getProperty()) {
							scrolledFormText = "";
						} else {
							if (isField) {
								ContainerController rootController = (ContainerController) controller.getRootCore().getController();
								parentScrolledForm = rootController.getScrolledForm();

								AWidgetController linkedController = ((AFieldController) controller).getLinkedController();
								String propertyText;
								if (linkedController instanceof HyperlinkController)
									propertyText = ((HyperlinkController) linkedController).getControl().getText();
								else
									propertyText = ((LabelController) linkedController).getControl().getText();
								scrolledFormText = parentScrolledForm.getText().concat(" - ").concat(propertyText);
							} else {
								parentScrolledForm = ((ContainerController) controller).getScrolledForm();
								scrolledFormText = parentScrolledForm.getText();
							}
							scrolledForm.getBody().setLayout(new MigLayout("wrap 1, ins 0", "[grow,fill]", "[grow,fill]"));

							EditableFormText formText = new EditableFormText(scrolledForm.getBody(),
									SWT.BORDER | AdiSWT.EXPANDABLE | SWT.V_SCROLL);
							formText.setLayoutData("span 2, push, grow");
							formText.setEnabled(false);
							formText.addJFaceFont("header", JFaceResources.HEADER_FONT);
							formText.addJFaceFont("banner", JFaceResources.BANNER_FONT);
							formText.addFont("bold", EngineTools.getModifiedFont(formText.getFont(), SWT.BOLD));
							formText.addFont("italic", EngineTools.getModifiedFont(formText.getFont(), SWT.ITALIC));
							formText.addToolkitColor("header", IFormColors.TB_BG);
							formText.addColor("blue", Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
							formText.addColor("darkBlue", Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
							formText.addToolkitColor("jpql", IFormColors.TB_TOGGLE_HOVER);
							formText.addToolkitColor("suffix", IFormColors.TB_TOGGLE);
							formText.addToolkitColor("name", IFormColors.H_BOTTOM_KEYLINE2);
							formText.setText(null == description ? "<form></form>" : description);
						}

						scrolledForm.setText(scrolledFormText);
						scrolledForm.setImage(null == parentScrolledForm ? null : parentScrolledForm.getImage());
						pageBook.showPage(controller);
						displayPartThread = null;
						pageBook.layout();
					} catch (SWTException e) {
						// Widget could be disposed due to thread aspect
						if (SWT.ERROR_WIDGET_DISPOSED != e.code)
							logError(e);
					}
				}); // END new Runnable
			}
		}

		private String getEntityId(ICollectionController collectioncontroller) {
			return collectioncontroller.getEntity().getEntityMM().getEntityId();
		}
	}
}
