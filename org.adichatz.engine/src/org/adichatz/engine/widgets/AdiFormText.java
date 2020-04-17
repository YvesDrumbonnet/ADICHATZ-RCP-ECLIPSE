package org.adichatz.engine.widgets;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.controller.utils.AReskinManager;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormText;

public class AdiFormText extends FormText {

	private List<Runnable> runnables;

	public AdiFormText(Composite parent, int style) {
		super(parent, style);
	}

	public void addCSSColor(String key, String selector, String property) {
		setColor(key, AReskinManager.getInstance().getColor(selector, property, null, null));
		getRunnables().add(() -> {
			setColor(key, AReskinManager.getInstance().getColor(selector, property, null, null));
		});
	}

	public void addToolkitColor(String key, String registryKey) {
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();
		setColor(key, toolkit.getColors().getColor(registryKey));
		if (null != AReskinManager.getInstance()) {
			getRunnables().add(() -> {
				setColor(key, AdichatzApplication.getInstance().getFormToolkit().getColors().getColor(registryKey));
			});
		}
	}

	public void addCSSFont(String key, String selector) {
		setFont(key, AReskinManager.getInstance().getFont(selector, null));
		getRunnables().add(() -> {
			setFont(key, AReskinManager.getInstance().getFont(selector, null));
		});
	}

	public void addJFaceFont(String key, String registryKey) {
		setFont(key, JFaceResources.getFont(registryKey));
		if (null != AReskinManager.getInstance()) {
			getRunnables().add(() -> {
				setFont(key, JFaceResources.getFont(registryKey));
			});
		}
	}

	public List<Runnable> getRunnables() {
		if (null == runnables) {
			runnables = new ArrayList<>();
			AReskinManager.getInstance().addReskinListener(() -> {
				runnables.forEach(Runnable::run);
			}, this);
		}
		return runnables;
	}
}
