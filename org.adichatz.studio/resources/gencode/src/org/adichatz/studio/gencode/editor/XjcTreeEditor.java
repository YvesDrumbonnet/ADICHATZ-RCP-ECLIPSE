/**********************************************************************************************
 * This class was automatically generated by Adichatz tools on Sat Dec 07 15:19:15 CET 2019
 *
 * 				+----------------------------------------+
 * 				| BE CAREFUL BEFORE UPDATING THIS CLASS! |
 * 				+----------------------------------------+
 *
 * Changes will be transient up to next generation process.
 * **********************************************************************************************/
package org.adichatz.studio.gencode.editor;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.engine.indigo.editor.AFormEditor;
import org.adichatz.engine.indigo.editor.FormEditorCore;
import org.adichatz.engine.indigo.editor.FormPageHeader;
import org.adichatz.engine.plugin.ParamMap;
import org.adichatz.studio.xjc.data.XjcBindingService;

@AdiResourceURI(URI="adi://org.adichatz.studio/editor/XjcTreeEditor")
public class XjcTreeEditor extends FormEditorCore {
	/**
	 * Instantiates a new XjcTreeEditor.
	 *
	 * @param pluginResources
	 *            the plugin resources
	 * @param partController
	 *            the part controller
	 * @param paramMap
	 *            the param map
	 */
	public XjcTreeEditor(AdiPluginResources pluginResources, IContainerController partController, ParamMap paramMap) {
		super(pluginResources, "XjcTreeEditor", "editor", paramMap);
		this.coreController = partController;
		
		
		((AFormEditor) partController).setBindingService(new XjcBindingService((AFormEditor) partController));
	}

	/**
	 * Create contents for FormPage
	 */
	public void createContents(){
		// adds formPage headers or includes.
		formPageHeaderMap.put("treeForm", new FormPageHeader(context, "org.adichatz.studio.gencode.editor.XjcTreeEditorTreeForm", AdichatzApplication.getInstance().getMessage("org.adichatz.studio", "adichatzStudio", "studio.editor.treeForm"),"org.adichatz.engine.indigo.editor.AdiFormPage"));
		pageIds.add("treeForm");
		firePostOpenPart();
	}
}