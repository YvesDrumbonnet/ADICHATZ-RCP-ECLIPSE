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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.Utilities;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.data.AEntity;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AbortChangesDialog.
 */
public class AChangesActionDialog extends ATableFormDialog {

	/** The updated entities. */
	protected Set<IEntity<?>> updatedEntities;

	private ScrolledForm scrolledForm;

	/**
	 * Instantiates a new abort changes dialog.
	 * 
	 * @param updatedEntities
	 *            the updated entities
	 */
	public AChangesActionDialog(Set<IEntity<?>> updatedEntities, AdiPluginResources pluginResources, String... messages) {
		super(Display.getCurrent(), null, messages);
		this.updatedEntities = updatedEntities;
		addEntities();
		initializeBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.FormDialog#createFormContent(org.eclipse.ui.forms .IManagedForm)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setBackground(toolkit.getColors().getInactiveBackground());
		scrolledForm = toolkit.createScrolledForm(parent);
		skinnedParent = scrolledForm;
		scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));
		scrolledForm.getBody().setLayout(new MigLayout("wrap 2", "[grow]"));
		Label label = toolkit.createLabel(scrolledForm.getBody(), "");
		label.setImage(AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_BUNDLE, "IMG_BIG_WARNING.png"));

		label = toolkit.createLabel(scrolledForm.getBody(), messages[1]);
		label.setFont(JFaceResources.getBannerFont());
		if (null != AReskinManager.getInstance())
			label.setForeground(
					AReskinManager.getInstance().getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "title-color", null, null));
		else
			label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		final Section tableSection = toolkit.createSection(scrolledForm.getBody(), Section.FOCUS_TITLE);
		Color background = tableSection.getBackground();
		tableSection.setTitleBarBackground(background);
		tableSection.setTitleBarGradientBackground(background);
		tableSection.setTitleBarBorderColor(background);

		toolkit.createCompositeSeparator(tableSection);

		tableSection.setLayoutData("spanx 2");
		tableSection.setText(getFromEngineBundle("modificationListContent"));
		tableSection.marginWidth = 10;
		tableSection.marginHeight = 5;
		Composite client = toolkit.createComposite(tableSection, SWT.WRAP);
		client.setLayout(new MigLayout("wrap", "", "[grow][fill]"));
		toolkit.paintBordersFor(client);
		tableSection.setClient(client);

		addTable(client, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION, "height 0:n:n, w 0:n:n");

		// Entity
		addColumn(getFromEngineBundle("entity"), SWT.FULL_SELECTION, new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((AEntity<?>) element).getEntityMM().getEntityId();
			}
		});

		// Identifier
		addColumn(getFromEngineBundle("identifier"), SWT.FULL_SELECTION, new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return String.valueOf(((IEntity<?>) element).getBeanId());
			}
		});

		// Status
		addColumn(getFromEngineBundle("status"), SWT.FULL_SELECTION, new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				return Utilities.getStatusLabel(element);
			}
		});

		label = toolkit.createLabel(client, messages[2]);
		label.setFont(JFaceResources.getBannerFont());
		label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setLayoutData("gaptop 30");

		client.layout();

		return scrolledForm;
	}

	private void addEntities() {
		Collection<IEntity<?>> elements = new ArrayList<IEntity<?>>();
		for (IEntity<?> entity : updatedEntities)
			elements.add(entity);
		setInput(elements);
		scrolledForm.reflow(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		setReturnCode(buttonId);
		close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse .swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.NO_ID, getFromEngineBundle("abortChanges"), false);
	}

}
