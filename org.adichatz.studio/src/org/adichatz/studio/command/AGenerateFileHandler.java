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
package org.adichatz.studio.command;

import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.util.AdiPropertyTester;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

import net.miginfocom.swt.MigLayout;

public abstract class AGenerateFileHandler extends AAdiHandler {

	protected IStructuredSelection fSelection;

	protected ColumnLabelProvider labelProvider;

	protected ITreeContentProvider contentProvider;

	protected Composite createTreeSection(FormToolkit toolkit, Composite parent, String sectionText, String columnName,
			final IStructuredSelection selection) {
		Section section = toolkit.createSection(parent, Section.TWISTIE | Section.TITLE_BAR | Section.EXPANDED);
		section.setText(sectionText);
		Composite composite = toolkit.createComposite(section, SWT.NONE);
		section.setClient(composite);
		composite.setLayout(new MigLayout("wrap", "grow,fill"));

		TreeViewerColumn column;
		TreeViewer treeViewer = new TreeViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		treeViewer.getTree().setLinesVisible(true);
		treeViewer.getTree().setHeaderVisible(true);

		column = new TreeViewerColumn(treeViewer, SWT.NONE);
		column.getColumn().setText(columnName);
		column.setLabelProvider(getLabelProvider());

		treeViewer.setContentProvider(getContentProvider());

		treeViewer.getTree().setLayoutData("growx, hmax 400");
		treeViewer.setInput(selection.toList());
		treeViewer.refresh();
		treeViewer.expandAll();
		column.getColumn().pack();

		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object selectedObject = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (selectedObject instanceof IFile) {
					try {
						IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), (IFile) selectedObject,
								true);
					} catch (PartInitException e) {
						LogBroker.logError(e);
					}
				}
			}
		});
		return composite;
	}

	public ColumnLabelProvider getLabelProvider() {
		if (null == labelProvider) {
			labelProvider = new ColumnLabelProvider() {
				public String getText(Object element) {
					if (element instanceof IResource)
						return ((IResource) element).getName();
					return null;
				};

				public Image getImage(Object element) {
					if (element instanceof IResource) {
						IResource resource = (IResource) element;
						IPath path = resource.getFullPath();
						if (AdiPropertyTester.isXMLResource(path))
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
									"IMG_XML_FOLDER.png");
						if (AdiPropertyTester.isBundleResource(path))
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
									"IMG_BUNDLE_FOLDER.png");
						if (AdiPropertyTester.isAdiXmlFile(element))
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_XML.png");
						if (AdiPropertyTester.isAdiBundleFile(element))
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_BUNDLE.png");
						if (AdiPropertyTester.isConfigFile(element, null))
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_CONFIG.png");
						if (element instanceof IFile && "java".equals(((IFile) element).getFileExtension()))
							return AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE,
									"IMG_CLASS_OBJ.png");
					}
					return null;
				};
			};
		}
		return labelProvider;
	}

	public void setLabelProvider(ColumnLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public ITreeContentProvider getContentProvider() {
		if (null == contentProvider)
			contentProvider = new ITreeContentProvider() {
				@Override
				public void dispose() {
				}

				@Override
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				}

				@Override
				public Object[] getElements(Object inputElement) {
					return getChildren(inputElement);
				}

				@Override
				public Object[] getChildren(Object parentElement) {
					if (parentElement instanceof List) {
						return ((List<?>) parentElement).toArray();
					} else if (AdiPropertyTester.isAdiXmlFile(parentElement)) {
						IFile file = (IFile) parentElement;
						List<IResource> javaResources = ScenarioUtil.getJavaFiles(file);
						Object[] children = new Object[javaResources.size()];
						int index = 0;
						for (IResource childResource : javaResources)
							children[index++] = childResource;
						return children;
					}
					return null;
				}

				@Override
				public Object getParent(Object element) {
					return null;
				}

				@Override
				public boolean hasChildren(Object element) {
					Object[] children = getChildren(element);
					return null != children && 0 < children.length;
				}
			};
		return contentProvider;
	}

	public void setContentProvider(ITreeContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public abstract IConfirmContent getConfirmContent(String sectionText, String columnText);

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			generate((IStructuredSelection) selection);
		}
		return null;
	}

	/**
	 * Generate.
	 * 
	 * @param currentSelection
	 *            the current selection
	 */
	public void generate(IStructuredSelection currentSelection) {
	}

}
