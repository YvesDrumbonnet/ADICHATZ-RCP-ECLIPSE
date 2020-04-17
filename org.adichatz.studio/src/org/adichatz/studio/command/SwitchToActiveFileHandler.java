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

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.Iterator;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.xjc.editor.XjcTreeFormEditor;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

import net.miginfocom.swt.MigLayout;

// TODO: Auto-generated Javadoc
/**
 * The Class SwitchToActiveFileHandler.
 */
public class SwitchToActiveFileHandler extends AAdiHandler {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection instanceof IStructuredSelection) {
			switchFile((IStructuredSelection) selection);
		}
		return null;
	}

	public void switchFile(final IStructuredSelection selection) {
		final Display display = Display.getCurrent();
		if (EngineTools.openConfirm(getFromStudioBundle("studio.menu.switchToCustomizeFile.confirm"))) {
			display.syncExec(() -> {
				final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				final Image image = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_SWITCH.png");
				Iterator<?> selectionIter = selection.iterator();
				while (selectionIter.hasNext()) {
					final IFile file = (IFile) selectionIter.next();
					try {
						file.refreshLocal(IResource.DEPTH_ZERO, null);
					} catch (CoreException e) {
						logError(e);
					}
					IPath localPath = new Path(ScenarioUtil.getTreeId(file).concat(".").concat(file.getFileExtension()));
					try {
						file.copy(file.getParent().getFullPath().append(localPath), true, null);
					} catch (CoreException e) {
						logError(e);
					}
					final IFile newFile = file.getParent().getFile(localPath);
					LogBroker.logInfo(getFromStudioBundle("studio.menu.switchToCustomizeFile.customizedVersionCreated",
							newFile.getFullPath().toPortableString()));
					for (IEditorReference editorReference : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.getEditorReferences()) {
						final IEditorPart editor = editorReference.getEditor(false);
						if (null != editor) {
							IEditorInput editorInput = editor.getEditorInput();

							if (editorInput instanceof FileEditorInput && ((FileEditorInput) editorInput).getFile().equals(file))
								if (ConfirmFormDialog.check(display,
										getFromStudioBundle("studio.menu.switchToCustomizeFile.switchEditor"), image,
										new IConfirmContent() {
											@Override
											public void createConfirmContent(Composite parent, AdiFormToolkit toolkit,
													List<Control> complementControls) {
												parent.setLayout(new MigLayout("wrap", "[grow, fill]"));

												Label messageLBL = toolkit.createLabel(parent, getFromStudioBundle(
														"studio.menu.switchToCustomizeFile.switchEditor.message"));
												messageLBL.setFont(
														JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));

												final Button openCustomizedFile = toolkit.createButton(parent,
														getFromStudioBundle(
																"studio.menu.switchToCustomizeFile.openEditor.customizedFile",
																newFile.getName()),
														SWT.CHECK);
												openCustomizedFile.setSelection(true);
												complementControls.add(openCustomizedFile);

												final Button closeGeneratedFile = toolkit.createButton(parent,
														getFromStudioBundle(
																"studio.menu.switchToCustomizeFile.closeEditor.closeFile",
																file.getName()),
														SWT.CHECK);
												closeGeneratedFile.setSelection(true);
												complementControls.add(closeGeneratedFile);
											}

											@Override
											public void okPressed(List<Control> complementControls) {
												if (((Button) complementControls.get(0)).getSelection())
													try {
														IDE.openEditor(activePage, newFile, true);
													} catch (PartInitException e) {
														LogBroker.logError(e);
													}
												if (((Button) complementControls.get(1)).getSelection())
													activePage.closeEditor(editor, true);
												else if (editor instanceof XjcTreeFormEditor)
													((XjcTreeFormEditor) editor).enablePageActions();
											}
										})) {
								}
						}
					}
				}
			});
		}
	}
}
