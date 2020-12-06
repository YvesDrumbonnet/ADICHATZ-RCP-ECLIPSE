/**********************************************
 * import org.adichatz.engine.common.ReflectionTools; import org.adichatz.engine.controller.IContainerController; import
 * org.adichatz.engine.controller.field.TextController; import org.adichatz.engine.core.ControllerCore; import
 * org.adichatz.engine.data.FieldBindingManager; import org.adichatz.generator.xjc.ElementType; import
 * org.adichatz.studio.xjc.validator.IdValidator; import org.eclipse.swt.events.DisposeEvent; import
 * org.eclipse.swt.events.DisposeListener; import org.eclipse.swt.events.ModifyEvent; import org.eclipse.swt.events.ModifyListener;
 * ill.info".
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
package org.adichatz.studio.xjc.controller;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.HashSet;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ATreeController;
import org.adichatz.engine.controller.field.TextController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.validation.AValidator;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.xjc.ElementType;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.adichatz.studio.xjc.editor.InternalTreeFormEditor;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

// TODO: Auto-generated Javadoc
/**
 * The Class IdTextController.
 */
public class IdTextController extends TextController {
	protected static IPreferenceStore store = StudioRcpPlugin.getDefault().getPreferenceStore();

	/** The tree controller. */
	private XjcTreeController treeController;

	/**
	 * Instantiates a new id text controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public IdTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void initialize() {
		super.initialize();
		ATreeController treeController = (ATreeController) ((XjcEntity<?>) getEntity()).getSetController();
		if (treeController instanceof XjcTreeController
				&& !(((XjcBindingService) getBindingService()).getEditor() instanceof InternalTreeFormEditor)) {
			this.treeController = (XjcTreeController) treeController;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.TextController#createControl()
	 */
	@Override
	public void createControl() {
		super.createControl();
		if (null != treeController) {
			addValidator(new AValidator(this, "studio.identifier.key") {
				@Override
				public int validate() {
					String identifier = (String) getValue();
					Object bean = treeController.getEntity().getBean();
					if (null == identifier) {
						if (!(bean instanceof EntityTreeWrapper))
							return setLevel(Integer.parseInt(store.getString(IStudioConstants.LEVEL_IDENTIFIER_MANDATORY)),
									getFromEngineBundle("mandatoryField"));
						else
							return setLevel(IMessageProvider.NONE, null);
					} else {
						Set<ElementType> set = treeController.getTreeManager().getChildrenMap().get(identifier);
						if (null != set && 1 < set.size()) {
							return setLevel(IMessageProvider.ERROR, getFromStudioBundle("studio.duplicateIdentifier"));
						} else {
							if (bean instanceof ElementType && null == set) {
								set = new HashSet<>();
								set.add((ElementType) treeController.getEntity().getBean());
								treeController.getTreeManager().getChildrenMap().put(identifier, set);
							}
							return setLevel(IMessageProvider.NONE, null);
						}
					}
				}
			});
		}
	}

	@Override
	public void disposeControl() {
		if (IEntityConstants.REMOVE == getEntity().getStatus() && null != treeController)
			treeController.getTreeManager().updateChildrenMap((ElementType) getEntity().getBean(), (String) getValue(), null);
		super.disposeControl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.TextController#addListeners()
	 */
	public void addListeners() {
		if (null != treeController) {
			final ModifyListener modifyListener = new ModifyListener() {
				public void modifyText(ModifyEvent event) {
					String currentValue = (String) ReflectionTools.invokeMethod(fieldBindManager.getField().getGetMethod(),
							getEntity().getBean());
					treeController.getTreeManager().updateChildrenMap((ElementType) getEntity().getBean(), currentValue,
							(String) getValue());
					fieldBindManager.bindTargetToModel();
				}
			};
			control.addModifyListener(modifyListener);
			control.addDisposeListener((e) -> {
				control.removeModifyListener(modifyListener);
				if (null != fieldBindManager) {
					fieldBindManager.unbind();
					fieldBindManager = null;
				}
			});
		}
		;
	}
}
