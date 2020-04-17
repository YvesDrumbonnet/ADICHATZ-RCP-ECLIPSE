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
import static org.adichatz.engine.common.LogBroker.logError;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.PatternSyntaxException;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.extra.AFormDialog;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.tabular.ColumnViewerFilter;
import org.adichatz.engine.widgets.supplement.AdiDecimalFormat;
import org.adichatz.jpa.tabular.JPAControllerPreferenceManager;
import org.adichatz.jpa.xjc.FilterType;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class ColumnFilterFormDialog.
 */
public class ColumnFilterFormDialog<T> extends AFormDialog {

	private String title;

	private Text searchStringTXT;

	private Button searchBooleanBTN;

	private boolean isBoolean;

	private Button exactStringBTN;

	private Button caseInsensitiveBTN;

	private AColumnController<T> columnController;

	private ATabularController<T> tabularController;

	private Class<?> columnType;

	/**
	 * Instantiates a new communication port form dialog.
	 */
	public ColumnFilterFormDialog(AdiFormToolkit toolkit, ATabularController<T> tabularController,
			AColumnController<T> columnController, String title) {
		super(Display.getCurrent().getActiveShell(), toolkit);
		this.tabularController = tabularController;
		this.columnController = columnController;
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.extra.AFormDialog#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent() {
		try {
			columnType = columnController.getClass().getMethod("getValue", tabularController.getBeanClass()).getReturnType();
			isBoolean = FieldTools.isBooleanType(columnType);

			getShell().setText(title);
			getShell().setImage(toolkit.getRegisteredImage("IMG_ADD_FILTER"));
			scrolledForm.setText(title);

			scrolledForm.getBody().setLayout(new MigLayout("wrap 1", "grow,fill", "grow,fill"));

			final Composite parent = toolkit.createComposite(scrolledForm.getBody());
			parent.setLayout(new MigLayout("wrap 3, ins 0", "[][grow,fill]10[]"));

			if (isBoolean) {
				searchBooleanBTN = toolkit.createButton(parent, getFromEngineBundle("query.filter.dialog.boolean"), SWT.CHECK);
				searchBooleanBTN.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						getButton(IDialogConstants.OK_ID).setEnabled(true);
					}
				});
			} else {
				toolkit.createLabel(parent, getFromEngineBundle("query.filter.dialog.filter"));
				searchStringTXT = toolkit.createText(parent, null);
				StringBuffer formatBuffer = new StringBuffer("");
				Format format = columnController.getFormat();
				if (null != format && format instanceof SimpleDateFormat) {
					formatBuffer.append("(");
					if (format instanceof AdiDecimalFormat)
						formatBuffer.append(((AdiDecimalFormat) format).getPattern());
					else if (format instanceof SimpleDateFormat)
						formatBuffer.append(((SimpleDateFormat) format).toLocalizedPattern());
					else
						formatBuffer.append(getFromEngineBundle("query.filter.dialog.unknown.format"));
					formatBuffer.append(")");
				}
				Label formatLabel = toolkit.createLabel(parent, formatBuffer.toString());
				formatLabel.setFont(EngineTools.getModifiedFont(formatLabel.getFont(), SWT.BOLD));

				if (String.class.equals(columnType)) {
					exactStringBTN = toolkit.createButton(parent, getFromEngineBundle("query.filter.dialog.exact"), SWT.CHECK);
					exactStringBTN.setSelection(true);
					exactStringBTN.setLayoutData("skip 1, span 2");

					caseInsensitiveBTN = toolkit.createButton(parent, getFromEngineBundle("query.search.caseInsensitive"),
							SWT.CHECK);
					caseInsensitiveBTN.setSelection(false);
					caseInsensitiveBTN.setLayoutData("skip 1");
				}
				searchStringTXT.addModifyListener((e) -> {
					String text = searchStringTXT.getText();
					boolean enabled = !text.isEmpty();
					scrolledForm.setMessage(null, IMessageProvider.NONE);
					if (enabled && (String.class.equals(columnType) || Object.class.equals(columnType))
							&& (text.startsWith(">") || text.startsWith(">"))) {
						enabled = false;
						scrolledForm.setMessage(getFromEngineBundle("query.search.only.equality"), IMessageProvider.ERROR);
					}
					getButton(IDialogConstants.OK_ID).setEnabled(enabled);
				});
			}
		} catch (SecurityException | NoSuchMethodException e) {
			logError(e);
		}
	}

	@Override
	protected Control createContents(Composite parent) {
		getShell().setSize(600, 250);
		Control control = super.createContents(parent);
		if (isBoolean)
			searchBooleanBTN.setFocus();
		else
			searchStringTXT.setFocus();
		return control;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	public void okPressed() {
		String searchString;
		if (isBoolean)
			searchString = String.valueOf(searchBooleanBTN.getSelection());
		else
			searchString = searchStringTXT.getText();
		try {
			ColumnViewerFilter<T> filter = new ColumnViewerFilter<T>(columnController, searchString);
			filter.computePredicate(null == exactStringBTN ? false : exactStringBTN.getSelection(),
					null == caseInsensitiveBTN ? false : caseInsensitiveBTN.getSelection());
			tabularController.getControllerPreferenceManager().addColumnFilter(filter);
			tabularController.refreshInput();
			super.okPressed();
		} catch (SecurityException | NoSuchMethodException e) {
			logError(e);
		} catch (ParseException | PatternSyntaxException e) {
			LogBroker.displayError(getFromEngineBundle("error.encounteredErrors"),
					getFromEngineBundle("query.filter.invalid.search.string", searchString));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.forms.FormDialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		Control buttonBar = super.createButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		getButton(IDialogConstants.CANCEL_ID).setFocus();

		FilterType filter = ((JPAControllerPreferenceManager<T>) tabularController.getControllerPreferenceManager())
				.getFilter(columnController.getColumnId());

		if (null != filter) {
			if (isBoolean) {
				searchBooleanBTN.setSelection(Boolean.valueOf(filter.getSearchString()));
				getButton(IDialogConstants.OK_ID).setEnabled(true);
			} else {
				searchStringTXT.setText(filter.getSearchString());
				if (null != exactStringBTN) {
					exactStringBTN.setSelection(filter.isExactString());
					caseInsensitiveBTN.setSelection(filter.isCaseInsensitive());
				}
				searchStringTXT.notifyListeners(SWT.Modify, null);
			}
		}
		return buttonBar;
	}

	static class SearchParam {
		int length;

		String operator;

		int compare = 0;

		public SearchParam(int length, String operator, int compare) {
			this.length = length;
			this.operator = operator;
			this.compare = compare;
		}

	}
}
