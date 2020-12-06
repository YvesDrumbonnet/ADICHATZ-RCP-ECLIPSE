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
package org.adichatz.studio.xjc.editor;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import org.adichatz.common.ejb.MultiKey;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.indigo.controller.LegacyFormPageController;
import org.adichatz.engine.indigo.editor.AdiFormPage;
import org.adichatz.engine.indigo.editor.AdiParListener;
import org.adichatz.engine.indigo.editor.FormEditorCore;
import org.adichatz.engine.indigo.editor.FormPageHeader;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AdiEvent;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.Generator;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.InternalScenarioResources;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;

// TODO: Auto-generated Javadoc
/**
 * The Class InternalTreeFormEditor.
 */
public class InternalTreeFormEditor extends AStudioFormEditor {

	/** The Constant ID. */
	static final public String ID = InternalTreeFormEditor.class.getName();

	private InternalTreePartInput internalTreePartInput;

	private String[] instanceKeys;

	/** The xml text editor. */
	protected XmlTextEditor xmlTextEditor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.AFormEditor#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(final IEditorSite site, IEditorInput input) throws PartInitException {
		editable = false;
		setSite(site);
		if (input instanceof InternalTreePartInput) {
			internalTreePartInput = (InternalTreePartInput) input;
		} else
			throw new RuntimeException("Don't know how to open " + this.getClass().getSimpleName() + " with input " + input + "?!");
		setInput(internalTreePartInput);
		instanceKeys = EngineTools.getInstanceKeys(internalTreePartInput.getName());

		pluginResources = StudioRcpPlugin.getDefault().getPluginResources();

		scenarioResources = InternalScenarioResources.getInstance(instanceKeys[0]);
		setPartName(instanceKeys[2]);

		setTitleImage(AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_XJC_EDITOR.png"));

		AdiParListener partListener = new AdiParListener() {
			@Override
			public void partActivated(IWorkbenchPart part) {
			}

			@Override
			public void partDeactivated(IWorkbenchPart part) {
				if (part.equals(InternalTreeFormEditor.this)) {
					if (null != XjcFieldViewPart.getInstance())
						XjcFieldViewPart.getInstance().showEmptyPage();
				}
			}

			@Override
			public void partClosed(IWorkbenchPart part) {
				if (part.equals(InternalTreeFormEditor.this))
					site.getPage().removePartListener(this);
			}
		};
		site.getPage().addPartListener(partListener);
	}

	/**
	 * Adds the xml editor.
	 *
	 * @param pageIndex
	 *            the page index
	 */
	private void addXMLEditor(int pageIndex) {
		try {
			xmlTextEditor = new XmlTextEditor(this, pageIndex);
			xmlTextEditor.setXmlEditable(false);
			String[] instanceKeys = EngineTools.getInstanceKeys(internalTreePartInput.getName());
			String radixURI = "platform:/plugin/".concat(instanceKeys[0]).concat("/").concat(EngineConstants.XML_FILES_PATH)
					.concat("/").concat(instanceKeys[1].replace('.', '/')).concat("/");
			URL url = FileLocator.find(new URL(radixURI.concat(instanceKeys[2]).concat(".axml")));
			if (null == url)
				url = FileLocator.find(new URL(radixURI.concat(instanceKeys[2]).concat("GENERATED.axml")));
			if (null != url) {
				URL resolvedFileURL = FileLocator.toFileURL(url);
				URI resolvedURI = new URI(resolvedFileURL.getProtocol(), resolvedFileURL.getPath(), null);
				File file = new File(resolvedURI);
				IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(file.getAbsolutePath()));
				FileStoreEditorInput fileStoreEditorInput = new FileStoreEditorInput(fileStore);
				int fSourcePageIndex = addPage(xmlTextEditor, fileStoreEditorInput);
				setPageText(fSourcePageIndex, "XML");
				setPageImage(fSourcePageIndex,
						AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_XML.png"));
				firePropertyChange(PROP_TITLE);
			} else
				logError(getFromStudioBundle("studio.no.resource", radixURI.concat(instanceKeys[2]).concat(".axml")));
		} catch (PartInitException | URISyntaxException | IOException e) {
			logError(e);
		}
	}

	/**
	 * Launch editor.
	 */
	protected void launchEditor() {
		ParamMap paramMap = new ParamMap();
		StringBuffer toolTipSB = new StringBuffer(instanceKeys[0]).append(": ").append(instanceKeys[1].replace('.', '/'))
				.append("/").append(instanceKeys[2]);

		paramMap.put(ParamMap.TITLE, toolTipSB.toString());
		// launch the form Editor
		genCode = (FormEditorCore) pluginResources.getNewInstance(IStudioConstants.XJC_TREE_EDITOR, "editor", this, paramMap);
		genCode.createContents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.AFormEditor#addPages()
	 */
	@Override
	public void addPages() {
		boolean first = true;
		for (final FormPageHeader formPageHeader : genCode.getFormPageHeaders()) {
			formPageHeader.addPage(this, entity, 0);
			if (first) {
				first = false;
				/**
				 * Instantiates a AFTER_INITIALIZE listener on formPageHeader (formPageController is not yet instantiated) that<br>
				 * + instantiates a AFTER_CREATE_CONTROL listener on formPageController that<br>
				 * + + instantiates a AFTER_END_LIFE_CYCLE listener on 'xjcTree' treeController for init controller. + instantiates
				 * a AFTER_END_LIFE_CYCLE listener on formPageController for enabling actions.
				 */
				formPageHeader.addListener(new AControlListener("addPage#AI", IEventType.AFTER_INITIALIZE) {
					@Override
					public void handleEvent(AdiEvent event) {
						formPageHeader.getFormPageController()
								.addListener(new AControlListener("addPage#ACC", IEventType.AFTER_CREATE_CONTROL) {
									@Override
									public void handleEvent(AdiEvent event) {
										final XjcTreeController treeController = ((XjcTreeController) genCode
												.getFromRegister("xjcTree"));
										treeController.addListener(
												new AControlListener("addPage#AELC#1", IEventType.AFTER_END_LIFE_CYCLE) {
													@Override
													public void handleEvent(AdiEvent event) {
														getTreeWrapper(true, true);
														treeController.refresh();
													}
												});
									}
								});
						formPageHeader.getFormPageController()
								.addListener(new AControlListener("addPage#AELC#2", IEventType.AFTER_END_LIFE_CYCLE) {
									@Override
									public void handleEvent(AdiEvent event) {
										((XjcBindingService) formPageHeader.getFormPageController().getBindingService())
												.enableActions();
									}
								});
					}

					@Override
					public void dispose() {
					}
				});
			}
		}
		addXMLEditor(1);
	}

	@Override
	public ITreeWrapper getTreeWrapper(boolean reloadFile, boolean reinit) {
		if (null == treeWrapper || reloadFile) {
			URL fileURL = internalTreePartInput.getFileUrl();
			if (null == fileURL) {
				String message = getFromStudioBundle("studio.bundle.project.not.exists", instanceKeys[0]);
				LogBroker.displayError(getFromStudioBundle("studio.logError"), message);
				getSite().getPage().closeEditor(this, false);
				throw new RuntimeException(message);
			} else
				try {
					ITreeWrapper treeWrapper = (ITreeWrapper) FileUtility.getTreeFromXmlFile(Generator.getUnmarshaller(),
							fileURL.openStream());
					this.treeWrapper = treeWrapper;
					treeWrapper.setPluginName(instanceKeys[0]);
					treeWrapper.setTreeId(instanceKeys[2]);
					treeWrapper.setSubPackage(instanceKeys[1]);
					if (reloadFile)
						((XjcTreeController) genCode.getFromRegister("xjcTree")).initTreeManager(treeWrapper,
								new MultiKey(instanceKeys[0], instanceKeys[2], instanceKeys[1]));
				} catch (IOException | JAXBException e) {
					logError(e);
				}
		}
		return (ITreeWrapper) treeWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.studio.xjc.editor.AStudioFormEditor#enableAction(boolean, boolean)
	 */
	@Override
	public void enableAction(boolean enabled) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.editor.ADirtyFormEditor#doSave()
	 */
	@Override
	public void doSave() {
	}

	@Override
	public void postRefresh() {
		LegacyFormPageController page = ((AdiFormPage) getSelectedPage()).getFormPageController();
		page.getControl().setRedraw(false);
		page.getEntityInjection().initialize(entity);
		page.endLifeCycleAndSync(); // refresh is included AFTER_END_LIFE_CYCLE listener (see addPages)
		page.getControl().setRedraw(true);
	}
}
