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
package org.adichatz.engine.extra;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.List;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.validation.ErrorPath;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ErrorsFoundDialog.
 * 
 * @author Yves Drumbonnet
 *
 */
public class ErrorsFoundDialog extends AdiMessageDialog {

	/**
	 * Convenience method to open a simple confirm (OK/Cancel) dialog.
	 * 
	 * @param parent
	 *            the parent shell of the dialog, or <code>null</code> if none
	 * @param editor
	 *            the validation container
	 * 
	 * @return <code>true</code> if the user presses the OK button, <code>false</code> otherwise
	 */
	public static boolean openConfirm(Shell parent, List<ABindingService> bindingServices) {
		ErrorsFoundDialog dialog = new ErrorsFoundDialog(bindingServices);
		return 0 == dialog.open();
	}

	/** The scrolled form. */
	protected ScrolledForm scrolledForm;

	/** The editor. */
	private List<ABindingService> bindingServices;

	private Section tableSection;

	private Composite client;

	public ErrorsFoundDialog(List<ABindingService> bindingServices) {
		super(Display.getCurrent(), SWT.NONE, null, getFromEngineBundle("error.encounteredErrors"));

		this.bindingServices = bindingServices;
		addErrors();
		initializeBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.FormDialog#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		scrolledForm = toolkit.createScrolledForm(parent);
		skinnedParent = scrolledForm;
		addPostReskinListener(() -> {
			parent.setBackground(toolkit.getColors().getBackground());
			scrolledForm.setBackground(toolkit.getColors().getBackground());
		});
		scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		scrolledForm.getBody().setLayout(new MigLayout("wrap 2", "[grow]"));

		Label label = toolkit.createLabel(scrolledForm.getBody(), "");
		label.setImage(AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_BUNDLE, "IMG_BIG_ERROR.png"));
		label = toolkit.createLabel(scrolledForm.getBody(), getFromEngineBundle("error.dialog.errorsWereFound"));
		label.setFont(JFaceResources.getBannerFont());
		label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		tableSection = toolkit.createSection(scrolledForm.getBody(), Section.EXPANDED);

		toolkit.createCompositeSeparator(tableSection);

		tableSection.marginWidth = 10;
		tableSection.marginHeight = 5;
		client = toolkit.createComposite(tableSection, SWT.WRAP);
		client.setLayout(new MigLayout("wrap", "", "[grow][fill]"));
		toolkit.paintBordersFor(client);
		tableSection.setClient(client);
		tableSection.setLayoutData("spanx 2, w n:300:n");

		tableSection.setText(getFromEngineBundle("error.encounteredErrors.list"));
		return scrolledForm;
	}

	private void addErrors() {
		for (final ABindingService bindingService : bindingServices)
			for (final ErrorPath errorPath : bindingService.getErrorPaths()) {
				StringBuffer messageSB = new StringBuffer();
				messageSB.append(getFromEngineBundle("error.dialog.in.page", errorPath.getPage()));
				IEntity<?> entity = errorPath.getErrorMessage().getEntity();
				if (null != entity.getBean()) {
					messageSB.append(getFromEngineBundle("error.dialog.for.entity", errorPath.getEntityId()));
					String beanId = errorPath.getEntityBeanId();
					if (null != beanId)
						messageSB.append(", ").append(getFromEngineBundle("identifier")).append(": '").append(beanId).append("'");
					messageSB.append(">,");
				}
				if (null != errorPath.getField())
					messageSB.append("\t").append(getFromEngineBundle("error.dialog.for.field", errorPath.getField()));
				messageSB.append("\t")
						.append(getFromEngineBundle("error.dialog.error", errorPath.getErrorMessage().getMessageStr()));

				Hyperlink link = toolkit.createHyperlink(client, messageSB.toString(), SWT.WRAP);
				link.setForeground(AController.ERROR_COLOR);
				addPostReskinListener(() -> {
					link.setBackground(AController.ERROR_COLOR);
				});
				link.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(HyperlinkEvent e) {
						bindingService.activateControllers(errorPath);
						close();
					}
				});
			}
		client.layout();
		tableSection.pack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, getFromEngineBundle("continue"), true);
	}
}
