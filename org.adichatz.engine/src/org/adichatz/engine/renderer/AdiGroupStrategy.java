package org.adichatz.engine.renderer;

import org.adichatz.engine.controller.utils.AReskinManager;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.nebula.widgets.pgroup.RectangleGroupStrategy;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

public class AdiGroupStrategy extends RectangleGroupStrategy {
	public void initialize(PGroup pgroup) {
		group = pgroup;
		update();
		setColors(pgroup);
	}

	public void setColors(PGroup pgroup) {
		AReskinManager reskineEngine = AReskinManager.getInstance();
		if (null != reskineEngine) {
			pgroup.setForeground(reskineEngine.getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "title-color", null, pgroup));
			Color g1 = reskineEngine.getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "button_background", null, pgroup);
			Color g2 = reskineEngine.getColor(AdiFormToolkit.CSS_ADICHATZ_COMMON_SELECTOR, "button_background", null, pgroup);
			setBackground(new Color[] { g1, g2 }, new int[] { 100 }, true);
			setBorderColor(g2);
		}
	}

	@Override
	public void paint(GC gc) {
		super.paint(gc);
		Color backgroundColor = getGroup().getBackground();
		if (backgroundColor != null)
			gc.setBackground(backgroundColor); // Set background color to squared figure rather than round figure.
	}

	@Override
	public void dispose() {
	}
}
