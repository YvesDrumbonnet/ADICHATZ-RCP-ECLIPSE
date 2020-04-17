package org.adichatz.engine.e4.handler;

import static org.adichatz.engine.common.LogBroker.logError;

import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.core.RootCore;
import org.adichatz.engine.e4.part.AdiInputPart;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.part.PartCore;
import org.adichatz.engine.e4.resource.E4AdichatzApplication;
import org.adichatz.engine.e4.resource.EngineE4Util;
import org.adichatz.engine.plugin.ParamMap;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

@SuppressWarnings("restriction")
public class OpenAdiEditor {

	@Execute
	protected BoundedPart execute(MApplication application, IEclipseContext context, EModelService modelService,
			EPartService partService) {
		ParamMap paramMap = context.get(ParamMap.class);
		MPartStack stack = E4AdichatzApplication.getInstance().getEditorPartStack();
		AdiInputPart inputPart = new AdiInputPart(paramMap, stack);
		inputPart.initializeLabel(paramMap.getString(ParamMap.TITLE));
		inputPart.setDuplicateEditor(paramMap.containsKey(ParamMap.DUPLICATE_EDITOR));
		if (!inputPart.isDuplicateEditor())
			for (MStackElement element : stack.getChildren()) {
				if (element instanceof AdiInputPart && element.equals(inputPart)) {
					inputPart = (AdiInputPart) element;
					break;
				}
			}
		if (null == inputPart.getElementId()) {
			String contributionURI = paramMap.getString(ParamMap.CONTRIBUTION_URI);
			if (null == contributionURI)
				contributionURI = EngineE4Util.DEFAULT_CONTRIBUTION_URI;
			inputPart.setContributionURI(contributionURI);
			inputPart.setElementId(AdiInputPart.getNewPartId());
			inputPart.setTooltip(paramMap.getString(ParamMap.TOOL_TIP_TEXT));
			inputPart.setIconURI(paramMap.getString(ParamMap.ICON_URI));
			inputPart.setCloseable(true);
			inputPart.getPersistedState().put(IWorkbench.PERSIST_STATE, "false");
			inputPart.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
			inputPart.getTags().add(IPresentationEngine.NO_MOVE);
			stack.getChildren().add(inputPart);
			inputPart.getTransientData().put(EngineE4Util.NO_SAVE, true);
			application.getContext().set(AdiInputPart.class, inputPart);
		}

		partService.showPart(inputPart, PartState.ACTIVATE);
		if (null == inputPart.getParamMap().get(ParamMap.BOUNDED_PART_ERROR)) {
			String postOpenEditorURI = inputPart.getParamMap().getString(ParamMap.POST_OPEN_EDITOR_URI);
			if (null != postOpenEditorURI) {
				PartCore genCode = ((BoundedPart) inputPart.getObject()).getGenCode();
				try {
					ReflectionTools.instantiateURI(postOpenEditorURI, new Class[] { RootCore.class }, new Object[] { genCode });
				} catch (Exception e) {
					logError(e);
				}
			}
			return (BoundedPart) inputPart.getObject();
		} else {
			((BoundedPart) inputPart.getObject()).close();
			return null;
		}
	}
}