/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE.
 *
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 *
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe �
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement,
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.
 *
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.studio.xjc.editor.action;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.Collection;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.validation.ErrorMessage;
import org.adichatz.engine.validation.ErrorPath;
import org.adichatz.generator.tree.AXjcTreeElement;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.data.XjcTreeElement;
import org.adichatz.studio.xjc.editor.StudioOutlinePage;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.forms.IMessageManager;

// TODO: Auto-generated Javadoc
/**
 * The Class CutXjcElementAction.
 */
public class CutXjcElementAction extends AOneParentXjcAction {

	/**
	 * Instantiates a new cut xjc element action.
	 */
	public CutXjcElementAction() {
		String text = getFromStudioBundle("studio.editor.cut");
		setText(text);
		setToolTipText(text);
		setImageDescriptor(toolkit.getRegisteredImageDescriptor("IMG_CUT"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.action.AAction#runAction()
	 */
	@Override
	public void runAction() {
		cutTreeElement(treeController);
	}

	@SuppressWarnings("rawtypes")
	public void cutTreeElement(XjcTreeController treeController) {
		boolean setSelection = true;
		if (null == selection) { // Key SWT.DEL is handled
			selection = (ITreeSelection) treeController.getViewer().getSelection();
			setSelection = false;
		}
		if (!selection.isEmpty()) {
			Clipboard clipboard = new Clipboard(treeController.getControl().getDisplay());
			Object[] o = new Object[] { selection.getFirstElement() };
			XjcSelectionTransfer transfer = XjcSelectionTransfer.getTransfer(selection);
			clipboard.setContents(o, new Transfer[] { transfer });
			clipboard.dispose();
			transfer.setCutSelectionIsCopied(false);

			XjcTreeElement parentElement = null;
			for (Object selectedObject : selection.toArray()) {
				XjcTreeElement cutElement = (XjcTreeElement) selectedObject;
				XjcBindingService bindingService = treeController.getBindingService();
				Object element = cutElement.getEntity().getBean();
				if (element instanceof ElementType)
					treeController.getTreeManager().updateChildrenMap((ElementType) element, ((ElementType) element).getId(), null);

				cutEntity(cutElement, bindingService);

				if (null == parentElement) {
					parentElement = cutElement.getParentElement();
					parentElement.getEntity().putStatus(IEntityConstants.MERGE, false);
				}
				Object result = ReflectionTools.invokeMethod(cutElement.getGetMethod(), parentElement.getElement());
				if (result instanceof Collection)
					((Collection) result).remove(cutElement.getEntity().getBean());
				else
					ReflectionTools.invokeMethod("s".concat(cutElement.getGetMethod().getName().substring(1)),
							parentElement.getElement(), new Class<?>[] { cutElement.getGetMethod().getReturnType() },
							new Object[] { null });
				parentElement.getChildElements().remove(cutElement);
			}
			((TreeViewer) treeController.getViewer()).remove(selection.toArray());
			// setDirty();
			treeController.getViewer().refresh(parentElement);
			if (setSelection)
				treeController.getViewer().setSelection(new StructuredSelection(selection.getPaths()[0].getParentPath()));
		}
	}

	private void cutEntity(XjcTreeElement cutElement, XjcBindingService bindingService) {
		for (AXjcTreeElement childElement : cutElement.getChildren())
			cutEntity((XjcTreeElement) childElement, bindingService);
		XjcEntity<?> cutEntity = cutElement.getEntity();
		cutElement.getEntity().putStatus(IEntityConstants.REMOVE, false);
		Set<ErrorMessage> errorMessages = bindingService.getErrorMessageMap().get(cutEntity);
		if (null != errorMessages) {
			StudioOutlinePage outlinePage = bindingService.getEditor().getOutlinePage();
			for (ErrorMessage errorMessage : errorMessages.toArray(new ErrorMessage[0])) {
				bindingService.removeMessage(errorMessage);
				if (null != outlinePage) {
					IMessageManager messageManager = errorMessage.getValidator().getFormMessageManager().getForm()
							.getMessageManager();
					for (IValidableController controller : errorMessage.getValidator().getHostingControllers())
						messageManager.removeMessage(errorMessage.getValidator().getKey(), controller.getControl());
				}
			}
		}
		for (ErrorPath errorPath : bindingService.getErrorPaths())
			if (errorPath.getErrorMessage().getEntity().equals(cutEntity)) {
				bindingService.removeMessage(errorPath.getErrorMessage());
			}
	}
}
