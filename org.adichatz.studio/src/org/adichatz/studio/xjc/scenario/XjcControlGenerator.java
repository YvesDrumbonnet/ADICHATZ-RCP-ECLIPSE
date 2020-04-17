package org.adichatz.studio.xjc.scenario;

import java.io.IOException;

import org.adichatz.generator.ACodeGenerator;
import org.adichatz.generator.ControlGenerator;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.wrapper.internal.IElementWrapper;
import org.adichatz.generator.xjc.ControlFieldType;
import org.adichatz.studio.xjc.controller.XjcFocusListener;

public class XjcControlGenerator extends ControlGenerator {
	public XjcControlGenerator(ACodeGenerator parentGenerator, IElementWrapper controlWrapper, boolean addDeclaration,
			String parentCollection) {
		super(parentGenerator, controlWrapper, addDeclaration, parentCollection);
	}

	public XjcControlGenerator(ACodeGenerator parentGenerator, IElementWrapper controlWrapper, boolean addDeclaration,
			String parentCollection, String controllerName) {
		super(parentGenerator, controlWrapper, addDeclaration, parentCollection);
		setControllerName(controllerName);
	}

	@Override
	public void flush(BufferCode bufferCode) throws IOException {
		if ("detail".equals(scenarioInput.getSubPackage()) && controlWrapper instanceof ControlFieldType) {
			afterCreateControlBuffer
					.append("getControl().addFocusListener(new " + getObjectName(XjcFocusListener.class) + "(this));");
		}
		super.flush(bufferCode);
	}
}
