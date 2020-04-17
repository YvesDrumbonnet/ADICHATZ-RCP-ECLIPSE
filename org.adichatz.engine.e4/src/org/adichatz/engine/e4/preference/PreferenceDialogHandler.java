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
package org.adichatz.engine.e4.preference;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.AController;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.CompositeBag;
import org.adichatz.engine.widgets.LimitedComposite;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class PreferenceDialogHandler.
 */
public class PreferenceDialogHandler {

	/** The previous selection. */
	private static ISelection previousSelection;

	/** The item data. */
	public static String ITEM_DATA = "#preferencePage#";

	/** The shell. */
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	protected Shell shell;

	@Inject
	protected IEclipseContext context;

	/**
	 * Execute.
	 */
	@Execute
	public void execute() {
		new TrayDialog(shell) {

			private TreeViewer treeViewer;

			private CompositeBag propertiesPanel;

			private Font updateFont;

			private Map<AdiPreferenceManager, APreferencePage> preferencePageMap = new HashMap<AdiPreferenceManager, APreferencePage>();

			@Override
			public void create() {
				setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
				super.create();
				EngineTools.centerWindow(getShell());
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.
			 * Composite)
			 */
			@Override
			protected Control createDialogArea(Composite parent) {
				setShellStyle(SWT.APPLICATION_MODAL | SWT.MAX | SWT.RESIZE | getDefaultOrientation());
				getShell().setSize(800, 400);
				getShell().setText(getFromEngineE4Bundle("adichatz.preferences"));
				initializeBounds();
				AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
				if (null == toolkit)
					toolkit = new AdiFormToolkit(Display.getCurrent());
				getShell().setImage(toolkit.getRegisteredImage("IMG_PREFERENCE"));
				parent.setBackground(toolkit.getColors().getBackground());
				ScrolledForm scrolledForm = toolkit.createScrolledForm(parent);
				scrolledForm.setLayoutData(new GridData(GridData.FILL_BOTH));
				Composite body = scrolledForm.getBody();
				body.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

				SashForm sashForm = new SashForm(scrolledForm.getBody(), SWT.HORIZONTAL);
				sashForm.setBackground(toolkit.getColors().getColor(IFormColors.H_GRADIENT_START));
				sashForm.setSashWidth(5);
				Composite treeComposite = toolkit.createComposite(sashForm, SWT.BORDER);
				treeComposite.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));

				LimitedComposite nodePanel = new LimitedComposite(treeComposite, SWT.NONE);
				toolkit.adapt(nodePanel);
				nodePanel.setLayout(new MigLayout("wrap 1", "grow,fill", "grow,fill"));
				createTreeViewer(nodePanel);

				updateFont = EngineTools.getModifiedFont(treeViewer.getTree().getFont(), SWT.BOLD);

				propertiesPanel = toolkit.createCompositeBag(sashForm, SWT.BORDER);
				propertiesPanel.getContent().setLayout(new MigLayout("wrap, ins 0 7 0 0, hidemode 3", "grow, fill", "grow,fill"));

				sashForm.setWeights(new int[] { 1, 2 });
				if (null != previousSelection) {
					treeViewer.setSelection(previousSelection);
					treeViewer.getTree().notifyListeners(SWT.Selection, null);
				}
				scrolledForm.reflow(true);
				applyDialogFont(scrolledForm.getBody());
				parent.layout();
				return scrolledForm;
			}

			@Override
			protected void okPressed() {
				for (APreferencePage preferencePage : preferencePageMap.values()) {
					if (preferencePage.isDirty() && !preferencePage.hasMessage)
						preferencePage.injectValues(false);
				}
				preferencePageMap.clear();
				preferencePageMap = null;
				super.okPressed();
			}

			@Override
			protected void cancelPressed() {
				preferencePageMap.clear();
				preferencePageMap = null;
				super.cancelPressed();
			}

			@Override
			protected Control createButtonBar(Composite parent) {
				Control control = super.createButtonBar(parent);
				parent.layout();
				return control;
			}

			/**
			 * Create a new <code>TreeViewer</code>.
			 * 
			 * @param parent the parent <code>Composite</code>.
			 * @return the <code>TreeViewer</code>.
			 * @since 3.0
			 */
			protected void createTreeViewer(Composite parent) {
				treeViewer = new TreeViewer(parent, SWT.NONE);
				treeViewer.getControl().setFont(JFaceResources.getDialogFont());
				treeViewer.getControl().setLayoutData("h 0:n:n, w 0:n:n");

				treeViewer.addSelectionChangedListener((event) -> {
					if (event.getSelection().isEmpty())
						propertiesPanel.showComposite(null);
					else {
						previousSelection = event.getSelection();
						AdiPreferenceManager preferenceManager = (AdiPreferenceManager) ((ITreeSelection) event.getSelection())
								.getFirstElement();
						TreeItem treeItem = treeViewer.getTree().getSelection()[0];

						APreferencePage preferencePage = (APreferencePage) treeItem.getData(ITEM_DATA);
						if (null == preferencePage) {
							IEclipsePreferences eclipsePreferences = InstanceScope.INSTANCE.getNode(preferenceManager.getId());
							Class<?> preferencePageClass;
							if (null == preferenceManager.getGencodePath())
								preferencePageClass = ReflectionTools.getClazz(preferenceManager.getPreferencePageClassName());
							else
								preferencePageClass = preferenceManager.getGencodePath()
										.getClazz(preferenceManager.getPreferencePageClassName());
							preferencePage = (APreferencePage) ReflectionTools.instantiateClass(preferencePageClass,
									new Class[] { AdiPreferenceManager.class, IEclipsePreferences.class, Composite.class,
											TreeViewer.class },
									new Object[] { preferenceManager, eclipsePreferences, propertiesPanel.getContent(),
											treeViewer });
							preferencePageMap.put(preferenceManager, preferencePage);
							treeItem.setData(ITEM_DATA, preferencePage);
						}
						propertiesPanel.showComposite(preferencePage.getPropertiesPage());
						propertiesPanel.getContent().layout();
					}
				});
				treeViewer.setLabelProvider(new ColumnLabelProvider() {
					public String getText(Object element) {
						return ((AdiPreferenceManager) element).getLabelText();
					}

					public Image getImage(Object element) {
						return ((AdiPreferenceManager) element).getLabelImage();
					}

					@Override
					public Font getFont(Object element) {
						APreferencePage preferencePage = preferencePageMap.get(element);
						if (null != preferencePage && preferencePage.isDirty())
							return updateFont;
						return null;
					}

					@Override
					public Color getForeground(Object element) {
						APreferencePage preferencePage = preferencePageMap.get(element);
						if (null != preferencePage && preferencePage.hasMessage)
							return AController.ERROR_COLOR;
						return null;
					}
				});
				treeViewer.setContentProvider(new ITreeContentProvider() {

					@Override
					public void dispose() {
					}

					@Override
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					}

					@Override
					public Object[] getElements(Object inputElement) {
						return AdiPreferenceManager.getNodes().toArray();
					}

					@Override
					public Object[] getChildren(Object parentElement) {
						return ((AdiPreferenceManager) parentElement).getChildren().toArray();
					}

					@Override
					public Object getParent(Object element) {
						return null;
					}

					@Override
					public boolean hasChildren(Object element) {
						List<AdiPreferenceManager> children = ((AdiPreferenceManager) element).getChildren();
						return null != children && !children.isEmpty();
					}
				});
				treeViewer.setInput(AdiPreferenceManager.getNodes());
			}
		}.open();
	}
}
