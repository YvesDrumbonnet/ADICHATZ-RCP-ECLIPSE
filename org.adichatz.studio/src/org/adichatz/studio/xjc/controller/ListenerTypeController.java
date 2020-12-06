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
package org.adichatz.studio.xjc.controller;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.util.Map;

import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.ARefController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.HyperlinkController;
import org.adichatz.engine.controller.utils.AdiSWT;
import org.adichatz.engine.controller.utils.IPredicate;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.generator.tools.AListenerTypeManager;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import net.miginfocom.swt.MigLayout;

/**
 * The Class ListenerTypeController.
 */
public class ListenerTypeController extends ARefController {

	/** The refText. */
	private Text text;

	private AdiResourceBundle bundle;

	private ClassTextController listenerClassNameCTC;

	private Object value;

	private Composite composite;

	/**
	 * Instantiates a new listener type combo controller.
	 * 
	 * @param id
	 *            the id
	 * @param parentController
	 *            the parent controller
	 * @param genCode
	 *            the gen code
	 */
	public ListenerTypeController(String id, final IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
		style = SWT.BORDER | AdiSWT.FIND_BUTTON | AdiSWT.DELETE_BUTTON;
		predicate = new IPredicate() {
			@Override
			public boolean apply(Object type) {

				XjcEntity<?> entity = (XjcEntity<?>) parentController.getEntity();
				IElementWrapper element = (IElementWrapper) entity.getTreeElement().getParentElement().getParentElement()
						.getEntity().getBean();

				for (Map.Entry<ListenerTypeEnum, AListenerTypeManager> entry : AListenerTypeManager.LISTENER_MANAGER_MAP
						.entrySet()) {
					switch (entry.getValue().getListenerCategory()) {
					case AListenerTypeManager.CONTROL:
					case AListenerTypeManager.RUNNABLE:
					case AListenerTypeManager.BEFORE_ENTITY_INJECTION:
					case AListenerTypeManager.LIFE_CYCLE:
						if (entry.getKey().equals(type) && entry.getValue().isEligible(element))
							return true;
					}
				}
				return false;
			}
		};
	}

	@Override
	public void createControl() {
		super.createControl();
		bundle = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE).getResourceBundleManager()
				.getResourceBundle("listenerTypeChooser");
		composite = toolkit.createComposite(parentController.getComposite(), SWT.BORDER);
		composite.setLayout(new MigLayout("wrap 3, ins 0", "[grow,fill][][]"));
		text = new Text(composite, SWT.READ_ONLY);
		text.setForeground(toolkit.getColors().getForeground());
		text.setBackground(toolkit.getColors().getBackground());
		text.setEditable(false);
		text.setLayoutData("w 0:n:n");

		Button findButton = new Button(composite, SWT.ICON) {
			@Override
			protected void checkSubclass() {
			}

			@Override
			public Point computeSize(int wHint, int hHint) {
				return new Point(18, 18);
			}
		};
		findButton.setToolTipText(EngineTools.getFromEngineBundle("field.occurences.findToolTipText"));
		findButton.setImage(toolkit.getRegisteredImage("IMG_FIND"));

		Button deleteButton = new Button(composite, SWT.ICON) {
			@Override
			protected void checkSubclass() {
			}

			@Override
			public Point computeSize(int wHint, int hHint) {
				return new Point(18, 18);
			}
		};
		deleteButton.setToolTipText(getFromEngineBundle("field.clearValueToolTip"));
		deleteButton.setImage(toolkit.getRegisteredImage("IMG_DELETE"));

		findButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ListenerTypeDialog(Display.getCurrent().getActiveShell(), bundle.getString("title"),
						AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "xjc/listener.png"), bundle,
						ListenerTypeController.this).open();
			}
		});
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setValue(null);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#getValue()
	 */
	@Override
	public Object getValue() {
		return convertTargetToModel(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.field.AFieldController#setValue(java.lang .Object, java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		this.value = value;
		listenerClassNameCTC.clearProposals();
		if (null == value)
			text.setText("");
		else
			text.setText(((String) value));
	}

	public Text getText() {
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AController#getControl()
	 */
	@Override
	public Composite getControl() {
		return composite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#addListener(boolean, boolean,
	 * org.adichatz.engine.validation.AValidation)
	 */
	@Override
	public void addListeners() {
		final ModifyListener modifyListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				broadcastChange();
			}
		};
		text.addModifyListener(modifyListener);
		text.addDisposeListener((e) -> {
			// refText.getText().removeModifyListener(modifyListener);
			if (null != fieldBindManager)
				fieldBindManager.unbind();
		});
	}

	@Override
	protected void reflow() {
		super.reflow();
		text.getParent().layout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#notifyBindListener()
	 */
	public void notifyBindListener() {
		text.notifyListeners(SWT.Modify, null);
	}

	@Override
	public void endLifeCycle() {
		listenerClassNameCTC = (ClassTextController) genCode.getFromRegister("listenerClassName");
		final HyperlinkController listenerClassNameHL = (HyperlinkController) listenerClassNameCTC.getLinkedController();
		listenerClassNameCTC.getControl().setEnabled(false);
		listenerClassNameHL.getControl().setEnabled(false);
		listenerClassNameCTC.getControl().addModifyListener((e) -> {
			text.setEnabled(null == listenerClassNameCTC.getValue());
		});

		text.addModifyListener((event) -> {
			boolean enabled = !((Text) event.widget).getText().isEmpty();
			listenerClassNameHL.getControl().setEnabled(enabled);
			listenerClassNameCTC.getControl().setEnabled(enabled);
		});
		super.endLifeCycle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.controller.AFieldController#broadcastChange()
	 */
	@Override
	public void broadcastChange() {
		if (null != fieldBindManager)
			fieldBindManager.bindTargetToModel();
		else
			getValidation().validate();
		reflow();
	}
}
