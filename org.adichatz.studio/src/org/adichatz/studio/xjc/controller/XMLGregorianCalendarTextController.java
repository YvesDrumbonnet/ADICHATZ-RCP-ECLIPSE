package org.adichatz.studio.xjc.controller;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.field.DateTextController;
import org.adichatz.engine.core.ControllerCore;

public class XMLGregorianCalendarTextController extends DateTextController {

	public XMLGregorianCalendarTextController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public Object convertModelToTarget(Object fromObject) {
		if (null == fromObject)
			return null;
		return ((XMLGregorianCalendar) fromObject).toGregorianCalendar().getTime();
	}
	
	@Override
	public Object convertTargetToModel(Object fromObject) {
		if (null == fromObject) return null;
		return EngineTools.getXMLGregorianCalendar((Date) fromObject);
	}
}
