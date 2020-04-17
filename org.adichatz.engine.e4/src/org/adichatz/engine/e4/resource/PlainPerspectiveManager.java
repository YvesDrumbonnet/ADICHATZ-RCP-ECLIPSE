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
package org.adichatz.engine.e4.resource;

import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.e4.part.IntroPart;
import org.adichatz.engine.e4.part.OutlinePart;
import org.adichatz.engine.xjc.NavigatorType;
import org.adichatz.engine.xjc.RcpConfigurationType;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MAdvancedFactory;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

// TODO: Auto-generated Javadoc
/**
 * The Class PlainPerspectiveManager.
 */
public class PlainPerspectiveManager implements IPerspectiveManager {

	/** The editor part stack. */
	protected static MPartStack editorPartStack;

	/** The outline part stack. */
	protected static MPartStack outlinePartStack;

	protected static MArea editorArea;

	protected static MArea outlineArea;

	/** The editor place area. */
	protected static String EDITOR_PLACE_HOLDER = "adichatz.editor.holder";

	/** The outline place area. */
	protected static String OUTLINE_PLACE_HOLDER = "adichatz.outline.holder";

	/** The contribution uri prefix. */
	protected static String CONTRIBUTION_URI_PREFIX = "bundleclass://".concat(EngineConstants.ENGINE_E4_BUNDLE).concat("/");

	/** The application. */
	protected MApplication application;

	/** The perspective. */
	protected MPerspective perspective;

	protected MPartSashContainer centerPSC;

	protected String perspectiveId;

	/**
	 * Instantiates a new plain perspective manager.<br>
	 * Persperctive allready exists and was created from persistent state sored in workbench.xmi file.
	 *
	 * @param application
	 *            the application
	 * @param window
	 *            the window
	 * @param perspectiveStack
	 *            the perspective stack
	 * @param perspectiveId
	 *            the perspective id
	 */
	public PlainPerspectiveManager(MApplication application, MPerspectiveStack perspectiveStack, String perspectiveId) {
		this.application = application;
		this.perspectiveId = perspectiveId;

		for (MPerspective perspective : perspectiveStack.getChildren())
			if (perspective.getElementId().equals(perspectiveId)) {
				this.perspective = perspective;
				break;
			}
	}

	/**
	 * Instantiates a new plain perspective manager.<br>
	 * Perspective is created from PerspectiveProcessor (clear perstistent state protocol).
	 *
	 * @param application
	 *            the application
	 * @param window
	 *            the window
	 */
	public PlainPerspectiveManager(MApplication application, MWindow window, String perspectiveId) {
		this.application = application;
		this.perspectiveId = perspectiveId;

		createContents(window);
		createParts();

	}

	/**
	 * Creates the contents.
	 */
	protected void createContents(MWindow window) {
		MAdvancedFactory advancedFactory = MAdvancedFactory.INSTANCE;
		perspective = advancedFactory.createPerspective();
		perspective.setElementId(perspectiveId);
		postCreatePerspective();

		MBasicFactory basicFactory = MBasicFactory.INSTANCE;
		MPartSashContainer globalPSC = basicFactory.createPartSashContainer();
		perspective.getChildren().add(globalPSC);
		globalPSC.setHorizontal(true);

		MPartStack navigatorPartStack = basicFactory.createPartStack();
		navigatorPartStack.setElementId(PerspectiveProcessor.NAVIGATOR_STACK);
		globalPSC.getChildren().add(navigatorPartStack);
		navigatorPartStack.setContainerData("25");
		// navigatorPartStack.getTags().add("NoAutoCollapse ");

		AdichatzApplication adichatzApplication = AdichatzApplication.getInstance();
		RcpConfigurationType rcpConfiguration = adichatzApplication.getConfigTree().getRcpConfiguration();
		if (null != rcpConfiguration)
			if (null != rcpConfiguration.getNavigators())
				for (NavigatorType navigator : rcpConfiguration.getNavigators().getNavigator()) {
					MPart navigatorPart = basicFactory.createPart();
					navigatorPartStack.getChildren().add(navigatorPart);
					navigatorPart.setElementId(navigator.getId());
					if (EngineTools.isEmpty(navigator.getMessageBundleURI()))
						navigatorPart.setLabel(navigator.getLabel());
					else {
						navigatorPart.setLabel(
								adichatzApplication.getMessageBundle(navigator.getMessageBundleURI(), navigator.getLabel()));
					}
					navigatorPart.setIconURI(navigator.getIconURI());
					navigatorPart.setContributionURI(navigator.getContributionURI());
				}

		MPartSashContainer leftPSC = basicFactory.createPartSashContainer();
		globalPSC.getChildren().add(leftPSC);
		leftPSC.setHorizontal(true);
		leftPSC.setContainerData("75");

		centerPSC = basicFactory.createPartSashContainer();
		leftPSC.getChildren().add(centerPSC);
		centerPSC.setContainerData("70");

		MPlaceholder editorPH = advancedFactory.createPlaceholder();
		centerPSC.getChildren().add(editorPH);
		editorPH.setContainerData("75");
		editorPH.setElementId(EDITOR_PLACE_HOLDER);

		if (null == editorArea) {
			editorArea = advancedFactory.createArea();
			window.getSharedElements().add(editorArea);
			editorPartStack = basicFactory.createPartStack();
			editorPartStack.setElementId(PerspectiveProcessor.EDITOR_PARTSTACK);
			// editorPartStack.getTags().add("NoAutoCollapse ");
			editorArea.getChildren().add(editorPartStack);
			editorArea.setElementId(EDITOR_PLACE_HOLDER);
		}
		editorPH.setRef(editorArea);

		MPlaceholder outlinePH = advancedFactory.createPlaceholder();
		leftPSC.getChildren().add(outlinePH);
		outlinePH.setContainerData("30");
		outlinePH.setElementId(OUTLINE_PLACE_HOLDER);

		if (null == outlineArea) {
			outlineArea = advancedFactory.createArea();
			window.getSharedElements().add(outlineArea);
			outlineArea.setElementId(OUTLINE_PLACE_HOLDER);
			outlinePartStack = basicFactory.createPartStack();
			outlinePartStack.setElementId(PerspectiveProcessor.OUTLINE_PARTSTACK);
			outlineArea.getChildren().add(outlinePartStack);
		}
		outlinePH.setRef(outlineArea);

	}

	protected void postCreatePerspective() {
		perspective.setIconURI(EngineE4Util.ICON_URI_PREFIX.concat("IMG_PERSPECTIVE_PLAIN.png"));
		perspective.setLabel(getFromEngineE4Bundle("adichatz.perspective.plain"));
		perspective.getPersistedState().put(PerspectiveProcessor.PERSPECTIVE_MANAGER,
				"bundleclass://org.adichatz.engine.e4/".concat(getClass().getName()));
	}

	/**
	 * Creates the parts.
	 */
	protected void createParts() {
		MBasicFactory basicFactory = MBasicFactory.INSTANCE;
		if (editorPartStack.getChildren().isEmpty()) {
			// Create intro part
			MPart introPart = basicFactory.createPart();
			editorPartStack.getChildren().add(introPart);
			introPart.setElementId(IntroPart.ID);
			introPart.setIconURI(EngineE4Util.ICON_URI_PREFIX.concat("IMG_INTRO9.png"));
			introPart.setContributionURI(CONTRIBUTION_URI_PREFIX.concat(IntroPart.class.getName()));
			introPart.setTooltip(getFromEngineE4Bundle("adichatz.introduction"));
			introPart.getTags().add(IPresentationEngine.NO_MOVE);
		}
		if (outlinePartStack.getChildren().isEmpty()) {
			// create Outline Part
			MPart outlinePart = basicFactory.createPart();
			outlinePartStack.getChildren().add(outlinePart);
			outlinePart.setElementId("adichatz.outline");
			outlinePart.setLabel(getFromEngineE4Bundle("adichatz.outline"));
			outlinePart.setIconURI(EngineE4Util.ICON_URI_PREFIX.concat("IMG_OUTLINE.png"));
			outlinePart.setContributionURI(CONTRIBUTION_URI_PREFIX.concat(OutlinePart.class.getName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.e4.resource.IPerspectiveManager#reset()
	 */
	@Override
	public void reset() {
		EModelService modelService = (EModelService) application.getContext().get(EModelService.class);
		preReset(modelService);

		MPerspective oldPerspective = perspective;
		createContents(application.getChildren().get(0));
		MPerspectiveStack perspectiveStack = (MPerspectiveStack) modelService.find(PerspectiveProcessor.PERSPECTIVE_STACK,
				application);
		perspectiveStack.getChildren().add(perspective);
		application.getContext().get(EPartService.class).switchPerspective(perspective);
		perspectiveStack.getChildren().remove(oldPerspective);
	}

	protected void preReset(EModelService modelService) {
		MPlaceholder editorPlaceHolder = (MPlaceholder) modelService.find(EDITOR_PLACE_HOLDER, application);
		if (null != editorPlaceHolder)
			editorArea = (MArea) editorPlaceHolder.getRef();
		MPlaceholder outlinePlaceHolder = (MPlaceholder) modelService.find(OUTLINE_PLACE_HOLDER, application);
		outlineArea = (MArea) outlinePlaceHolder.getRef();

	}

	/**
	 * Gets the perspective.
	 *
	 * @return the perspective
	 */
	public MPerspective getPerspective() {
		return perspective;
	}

}
