/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.engine.tabular;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.AControlController;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.controller.IRootController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.collection.ATabularController.METHOD_NAME;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.extra.OutlineEvent;
import org.adichatz.engine.extra.OutlineEvent.EVENT_TYPE;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.query.IQueryPreferenceManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class AControlTabularStatusBar.
 * 
 * @author Yves Drumbonnet
 * 
 */
public abstract class ABasicTabularStatusBar extends ATabularStatusBar {

	protected static String PAGE_MESSAGE = getFromEngineBundle("query.page");

	protected static String ROW_MESSAGE = getFromEngineBundle("query.row");

	protected static String INCOMPLETE = getFromEngineBundle("query.incomplete");

	/** The previous selection. */
	protected int previousPageSelection = -1;

	protected ATabularController<?> tabularController;

	protected Long totalRows;

	protected Integer fetchedRows;

	protected Integer displayedRows;

	protected Integer pageNumber;

	protected boolean filtered;

	protected boolean doit = true;

	protected int previousSelectionIndex;

	private AControlListener refreshListener;

	private ISelectionChangedListener selectionChangedListener;

	protected String columnConstraint; // column constraints of the status bar layout

	protected Label pageLabel;

	protected Composite filterComposite;

	protected Label filterLabel;

	protected Button filterButton;

	/** The click. */
	protected int click;

	protected Color backgroundColor;

	protected IQueryPreferenceManager<?> queryPreferenceManager;

	/**
	 * Instantiates a new basic status bar. Only selected record, number of records, filters are managed. (no pagination process)
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public ABasicTabularStatusBar(final ATabularController<?> tabularController, String key) {
		super(tabularController.getComposite(), SWT.NONE, key);
		this.tabularController = tabularController;
		createToolBar();
		if (null == getLayoutData())
			setLayoutData("h ".concat(String.valueOf(getHeight())).concat("!"));
		refreshListener = new AControlListener("BasicTabularStatusBar#Refresh", IEventType.REFRESH, tabularController) {
			@Override
			public void handleEvent(AdiEvent event) {
				afterRefreshTabularController();
			}

		};
		tabularController.addListener(refreshListener);
		selectionChangedListener = new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				int selectionIndex = (Integer) tabularController.invokeControlMethod(METHOD_NAME.getSelectionIndex) + 1;
				if (selectionIndex != previousSelectionIndex) {
					previousSelectionIndex = selectionIndex;
					if (isVisible() && tabularController.getQueryManager().isPaginated())
						displayPosition();
				}
			}
		};
		tabularController.getViewer().addSelectionChangedListener(selectionChangedListener);
		queryPreferenceManager = tabularController.getQueryManager().getQueryPreferenceManager();
	}

	/**
	 * Creates the tool bar.
	 */
	protected void createToolBar() {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		if (null != AReskinManager.getInstance()) {
			backgroundColor = AReskinManager.getInstance().getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "title-background",
					AControlController.ADI_CSS_BACKGROUND, this);
		} else {
			backgroundColor = toolkit.getColors().getColor(IFormColors.TB_TOGGLE_HOVER);
		}
		setBackground(backgroundColor);
		pageLabel = new Label(this, SWT.NONE);
		pageLabel.setFont(tabularController.getBoldFont());
		pageLabel.setBackground(backgroundColor);

		createControl();

		filterComposite = new Composite(this, SWT.NONE);
		filterComposite.setLayout(new MigLayout("ins 0 10 0 0, rtl"));
		filterComposite.setBackground(backgroundColor);
		filterLabel = new Label(filterComposite, SWT.NONE);
		filterButton = new Button(filterComposite, SWT.ICON);
		filterButton.setImage(toolkit.getRegisteredImage("IMG_FILTER"));
		filterButton.setBackground(backgroundColor);
		filterButton.setLayoutData("hmax " + getHeight());
		filterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ICollectionController rootController = tabularController.getRootCore().getController();
				if (rootController instanceof IRootController)
					((IRootController) rootController).fireOutlineListener(new OutlineEvent(EVENT_TYPE.FILTER_CHANGE));
			}
		});

		warningLabel = new Label(this, SWT.ICON);
		warningLabel.setImage(JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING));
		warningLabel.setLayoutData("wmax " + getHeight() + ",hmax " + (getHeight()));
		warningLabel.setVisible(false);
		warningLabel.setBackground(backgroundColor);
		warningLabel.setToolTipText(getFromEngineBundle("query.warning.on.result"));

	}

	@Override
	public void displayPosition() {
		setPageLabel();
		layout();
	}

	protected void setPageLabel() {
		StringBuffer pageSB = new StringBuffer(ROW_MESSAGE);
		StringBuffer toolTipPageSB = new StringBuffer();
		Long queryCount = queryPreferenceManager.getPaginationQueryCount();
		int rowCount = getItemCount();
		if (null == queryCount && !queryPreferenceManager.isCompleted()) {
			toolTipPageSB.append(INCOMPLETE).append(" ");
		}

		toolTipPageSB.append(ROW_MESSAGE).append(": ");
		pageSB.append(": ");
		int selectionIndex = (Integer) tabularController.invokeControlMethod(METHOD_NAME.getSelectionIndex) + 1;
		if (0 < selectionIndex) {
			pageSB.append(selectionIndex).append((" / "));
			toolTipPageSB.append(" ").append(selectionIndex).append(" / ");
		}

		if (null == queryCount) {
			pageSB.append(getItemCount()).append((" / "));
			toolTipPageSB.append(getFromEngineBundle("query.page.toolTip.fetchedRows", getItemCount()));

			setLayout(new MigLayout("ins 1 5 0 5", "[][][][align right, grow,fill]", "al top"));
			if (queryPreferenceManager.isCompleted()) {
				pageLabel.setForeground(filterLabel.getForeground());
				pageSB.append(rowCount);
			} else {
				pageSB.append("__");
				Color foreground;
				if (null != AReskinManager.getInstance())
					foreground = AReskinManager.getInstance().getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR,
							"error-foreground-color", AControlController.ADI_CSS_FOREGROUND, pageLabel);
				else
					foreground = AController.ERROR_COLOR;
				pageLabel.setForeground(foreground);
			}
		} else {
			if (null != getMaxResults()) {
				pageSB.append(fetchedRows).append((" / "));
				toolTipPageSB.append(getFromEngineBundle("query.page.toolTip.rowPerPage", fetchedRows, getMaxResults()));
			}
			pageSB.append(queryCount);
			pageLabel.setForeground(filterLabel.getForeground());
			toolTipPageSB.append(" / ").append(getFromEngineBundle("query.page.toolTip.queryCount", queryCount));
			setLayout(new MigLayout("ins 1 5 0 5", columnConstraint, "al top"));
		}

		if (!Integer.valueOf(1).equals(pageNumber)) {
			pageSB.append("  -  ").append(PAGE_MESSAGE);
			pageSB.append(": ").append(getPageSelection()).append(" / ").append(pageNumber);
			toolTipPageSB.append(" / ").append(getFromEngineBundle("query.page.toolTip.page", getPageSelection(), pageNumber));
		}
		pageLabel.setText(pageSB.toString());
		pageLabel.setToolTipText(toolTipPageSB.toString());

		int filterNumber = tabularController.getViewer().getFilters().length;
		if (0 < filterNumber) {
			filterComposite.setVisible(true);
			filterLabel.setText(getFromEngineBundle("query.filter", rowCount, fetchedRows));
			filterLabel.setToolTipText(getFromEngineBundle("query.filter.toolTipText", rowCount, fetchedRows, filterNumber));
			filterLabel.setFont(tabularController.getBoldFont());
			filterLabel.setBackground(backgroundColor);
			filterComposite.setBackground(backgroundColor);
		} else {
			filterComposite.setVisible(false);
		}
	}

	protected Integer getItemCount() {
		return (Integer) tabularController.invokeControlMethod(METHOD_NAME.getItemCount);
	}

	@Override
	public void dispose() {
		refreshListener.dispose();
		tabularController.getViewer().removeSelectionChangedListener(selectionChangedListener);
		super.dispose();
	}

	public void afterRefreshTabularController() {
		fetchedRows = queryPreferenceManager.getQueryManager().getQueryResult().getQueryResultList().size();
		displayedRows = (Integer) tabularController.invokeControlMethod(METHOD_NAME.getItemCount);
		filtered = 0 < tabularController.getViewer().getFilters().length;

		totalRows = queryPreferenceManager.getPaginationQueryCount();
		if (null == totalRows || null == getMaxResults())
			pageNumber = 1;
		else {
			Double totalRowsR = Double.valueOf(totalRows);
			Double maxResults = Double.valueOf(getMaxResults());
			pageNumber = Double.valueOf(Math.ceil(totalRowsR / maxResults)).intValue();
			if (0 == pageNumber)
				pageNumber = 1;
		}

		if (doit) {
			previousPageSelection = -1;
			previousSelectionIndex = -1;
			fireWidgetSelection();
		}
	}

	protected int getPageSelection() {
		return 0;
	}

	protected void createControl() {
		columnConstraint = "[]push[][][]";
	}

	/**
	 * Fire widget selection.
	 * 
	 * @param selection
	 *            the selection
	 */
	public void fireWidgetSelection() {
		if (doit) {
			pageNumber = 1;
			if (tabularController.getQueryManager().isPaginated()) {
				setPageLabel();
				layout();
			}
		}
	}

	@Override
	public int getHeight() {
		return 18;
	}

	protected int getFirstResult() {
		Integer firstResult = queryPreferenceManager.getFirstResult();
		return null == firstResult ? 0 : firstResult;
	}

	protected Integer getMaxResults() {
		return queryPreferenceManager.getCurrentMaxResults();
	}

	public void setReskinBackground() {
		backgroundColor = AReskinManager.getInstance().getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "title-background",
				AControlController.ADI_CSS_BACKGROUND, this);
	}

}
