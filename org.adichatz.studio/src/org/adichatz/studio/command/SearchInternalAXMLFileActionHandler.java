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
package org.adichatz.studio.command;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.ImageManager;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.xjc.editor.InternalTreeFormEditor;
import org.adichatz.studio.xjc.editor.InternalTreePartInput;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class CleanProjectActionDelegate.
 */
public class SearchInternalAXMLFileActionHandler extends AAdiHandler {
	private Bundle selectedBundle;

	private ImageManager imageManager = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE).getImageManager();

	private ConfirmFormDialog confirmFormDialog;

	private TreeViewer treeViewer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		Display.getCurrent().syncExec(() -> {
			final IConfirmContent confirmContent = getConfirmContent(getFromStudioBundle("search.internal.axml.files"),
					getFromStudioBundle("search.internal.axml.files"));
			final Shell shell = Display.getCurrent().getActiveShell();
			confirmFormDialog = new ConfirmFormDialog(shell, getFromStudioBundle("search.internal.axml.files"),
					imageManager.getImageDescriptor("IMG_AXML.png").createImage(), confirmContent) {
				protected void buttonPressed(int buttonId) {
					if (IDialogConstants.OK_ID == buttonId) {
						confirmContent.okPressed(complementControls);
						// okPressed();
					} else if (IDialogConstants.CANCEL_ID == buttonId) {
						cancelPressed();
					}
				}

				@Override
				protected Control createButtonBar(Composite parent) {
					Control control = super.createButtonBar(parent);
					getButton(IDialogConstants.OK_ID).setEnabled(false);
					return control;
				}
			};
			confirmFormDialog.open();
		});
		return null;
	}

	private IConfirmContent getConfirmContent(final String sectionText, final String columnText) {
		return new IConfirmContent() {
			@Override
			public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
				parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[][grow,fill]"));
				Composite composite = toolkit.createComposite(parent);
				composite.setLayout(new MigLayout("wrap 2", "[grow,fill][]", "[grow,fill]"));
				Label title = toolkit.createLabel(composite, getFromStudioBundle("search.internal.axml.files"));
				title.setLayoutData("span 2");
				title.setFont(JFaceResources.getBannerFont());

				Image dirImage = imageManager.getImageDescriptor("xjc/directory.png").createImage();
				Image axmlImage = imageManager.getImageDescriptor("IMG_AXML.png").createImage();
				treeViewer = new TreeViewer(composite, SWT.CENTER | SWT.BORDER | SWT.V_SCROLL);
				treeViewer.getTree().setLayoutData("hmin 200, wmin 500");
				treeViewer.setContentProvider(new ITreeContentProvider() {
					@Override
					public boolean hasChildren(Object element) {
						return hasChildrenUrl((URL) element);
					}

					@Override
					public Object getParent(Object element) {
						return null;
					}

					@Override
					public Object[] getElements(Object inputElement) {
						Enumeration<URL> children = selectedBundle.findEntries(((URL) inputElement).getPath(), "*", false);
						if (null == children)
							return new Object[0];
						Set<URL> urls = new HashSet<>();
						while (children.hasMoreElements()) {
							URL url = children.nextElement();
							if (url.getPath().endsWith(".axml") || hasChildren(url))
								urls.add(url);
						}
						return urls.toArray();
					}

					@Override
					public Object[] getChildren(Object parentElement) {
						return getElements(parentElement);
					}
				});
				treeViewer.setLabelProvider(new LabelProvider() {
					@Override
					public String getText(Object element) {
						if (element instanceof URL)
							return ((URL) element).getFile();
						return "?!";
					}

					@Override
					public Image getImage(Object element) {
						if (hasChildrenUrl((URL) element))
							return dirImage;
						return axmlImage;
					}
				});

				treeViewer.addSelectionChangedListener((e) -> {
					boolean enabled = true;
					StructuredSelection selection = (StructuredSelection) e.getSelection();
					if (selection.isEmpty() || hasChildrenUrl((URL) selection.getFirstElement()))
						enabled = false;
					else {
						confirmFormDialog.getButton(IDialogConstants.OK_ID).setEnabled(enabled);
					}
					confirmFormDialog.getButton(IDialogConstants.OK_ID).setEnabled(enabled);
				});
				treeViewer.addDoubleClickListener((e) -> {
					if (confirmFormDialog.getButton(IDialogConstants.OK_ID).isEnabled())
						okPressed(null);
				});
				Composite buttonComposite = toolkit.createComposite(composite);
				buttonComposite.setLayout(new MigLayout("wrap"));
				buttonComposite.setLayoutData("top");
				toolkit.createLabel(buttonComposite, getFromStudioBundle("scenario.generation.choose.plugin"));

				for (final Bundle bundle : getAXMLPlugins()) {
					Button button = toolkit.createButton(buttonComposite, bundle.getSymbolicName(), SWT.RADIO);
					button.setData(bundle);
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if (((Button) e.widget).getSelection()) {
								selectedBundle = (Bundle) ((Button) e.widget).getData();
								treeViewer.setInput(selectedBundle.getEntry(EngineConstants.XML_FILES_PATH));
							}
						}
					});
				}
			}

			/**
			 * Ok pressed.
			 * 
			 * @param complementControls
			 *            the complement controls
			 */
			@Override
			public void okPressed(List<Control> complementControls) {
				String path = ((URL) ((StructuredSelection) treeViewer.getSelection()).getFirstElement()).getPath();
				int index = path.lastIndexOf('/');
				String subPackage = path.substring(EngineConstants.XML_FILES_PATH.length() + 1, index).replace('/', '.');
				if (subPackage.isEmpty())
					subPackage = ".";
				else
					subPackage = subPackage.substring(1);
				String adiResourceURI = "adi://" + selectedBundle.getSymbolicName() + "/" + subPackage
						+ path.substring(index, path.length() - 5);
				try {
					InternalTreePartInput input = new InternalTreePartInput(adiResourceURI, false);
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input,
							InternalTreeFormEditor.ID, true);
				} catch (PartInitException e) {
					logError(e);
				}

			}
		};
	}

	private Set<Bundle> getAXMLPlugins() {
		Set<Bundle> axmlPlugins = new HashSet<Bundle>();
		for (Bundle bundle : StudioRcpPlugin.getDefault().getBundle().getBundleContext().getBundles()) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(bundle.getSymbolicName());
			if (null == project || !project.exists()) {
				if (null != bundle.getEntry(EngineConstants.XML_FILES_PATH)
						&& null != bundle.findEntries(EngineConstants.XML_FILES_PATH, "*.axml", true)) {
					axmlPlugins.add(bundle);
				}
			}
		}
		return axmlPlugins;
	}

	public boolean hasChildrenUrl(URL url) {
		return null != selectedBundle.findEntries(url.getPath(), "*", false);
	}

}
