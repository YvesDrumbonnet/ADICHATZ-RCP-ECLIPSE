package org.adichatz.engine.e4.preference;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.widgets.AdiControlDecoration;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.pgroup.PGroup;
import org.eclipse.nebula.widgets.pgroup.PGroupToolItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;
import org.osgi.service.prefs.BackingStoreException;

import net.miginfocom.swt.MigLayout;

public abstract class APreferencePage {
	protected IEclipsePreferences eclipsePreferences;

	private boolean dirty;

	private PGroup pgroup;

	private Button applyButton;

	/** The dirty decoration. */
	private AdiControlDecoration dirtyDecoration;

	private List<AFieldEditor> fields = new ArrayList<>();

	private TreeViewer treeViewer;

	private AdiPreferenceManager preferenceManager;

	protected List<BooleanSupplier> evaluators = new ArrayList<BooleanSupplier>();

	protected boolean hasMessage = true;

	public APreferencePage(AdiPreferenceManager preferenceManager, IEclipsePreferences eclipsePreferences,
			Composite parent, TreeViewer treeViewer) {
		this.preferenceManager = preferenceManager;
		this.eclipsePreferences = eclipsePreferences;
		this.treeViewer = treeViewer;
		AdiFormToolkit toolkit = AdichatzApplication.getInstance().getFormToolkit();

		pgroup = toolkit.createPGroup(parent, SWT.SMOOTH);
		removeMessage();
		pgroup.setFont(new Font(Display.getCurrent(), StringConverter.asFontData("Segoe UI-bold-14")));
		pgroup.setForeground(toolkit.getColors().getColor(IFormColors.TB_TOGGLE));

		pgroup.setLayoutData("w 0:n:n, h 0:n:n");
		pgroup.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
		pgroup.setToggleRenderer(null); // PGroup.toggle becomes hidden

		PGroupToolItem toolItem = new PGroupToolItem(pgroup, SWT.PUSH);
		toolItem.setImage(AdichatzApplication.getInstance().getImage(EngineConstants.ENGINE_BUNDLE, "IMG_REFRESH.png"));
		toolItem.setToolTipText(getFromEngineE4Bundle("cancel.change"));
		toolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				initValues();
				applyButton.setEnabled(false);
			}
		});
		ScrolledComposite scrolledComposite = new SharedScrolledComposite(pgroup,
				SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL) {
			@Override
			public Point computeSize(int wHint, int hHint, boolean changed) {
				return new Point(SWT.DEFAULT, SWT.DEFAULT);
			}
		};
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		toolkit.adapt(scrolledComposite);

		Composite client = toolkit.createComposite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(client);

		client.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "[grow,fill][]"));

		createPropertiesContent(client);
		Composite bottomComposite = toolkit.createComposite(client);
		bottomComposite.setLayout(new MigLayout("wrap 2, al right"));

		Button restoreButton = toolkit.createButton(bottomComposite, getFromEngineE4Bundle("restore.default"),
				SWT.PUSH);
		restoreButton.setLayoutData("sg button, gapx 20");
		restoreButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				APreferencePage.this.preferenceManager.initializeDefault();
				initValues();
				injectValues(true);
			}
		});
		applyButton = toolkit.createButton(bottomComposite, getFromEngineE4Bundle("apply"), SWT.PUSH);
		applyButton.setLayoutData("sg button");
		applyButton.setEnabled(false);
		applyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				injectValues(false);
			}
		});
		initValues();
	}

	public IEclipsePreferences getEclipsePreferences() {
		return eclipsePreferences;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		if (dirty) {
			if (null == dirtyDecoration)
				dirtyDecoration = new AdiControlDecoration(pgroup, SWT.TOP | SWT.LEFT,
						AdiControlDecoration.REQUIRED_DECORATOR_IMAGE);
			dirtyDecoration.show();
			boolean enabled = true;
			for (BooleanSupplier evaluator : evaluators)
				if (!evaluator.getAsBoolean()) {
					enabled = false;
					break;
				}
			applyButton.setEnabled(enabled);
			if (enabled)
				removeMessage();
		} else if (null != dirtyDecoration) {
			dirtyDecoration.hide();
			applyButton.setEnabled(false);
			removeMessage();
		}
		this.dirty = dirty;
		treeViewer.refresh(preferenceManager);
	}

	public abstract void createPropertiesContent(Composite parent);

	public Composite getPropertiesPage() {
		return pgroup;
	}

	public void addField(AFieldEditor fieldEditor) {
		fields.add(fieldEditor);
	}

	protected void initValues() {
		for (AFieldEditor fieldEditor : fields) {
			fieldEditor.setValue(eclipsePreferences);
		}
		setDirty(false);
	}

	public boolean injectValues(boolean force) {
		boolean flush = force;
		for (AFieldEditor fieldEditor : fields) {
			if (fieldEditor.injectValue(eclipsePreferences, force))
				flush = true;
		}
		setDirty(false);
		if (flush)
			try {
				eclipsePreferences.flush();
				preferenceManager.injectValues();
			} catch (BackingStoreException e) {
				logError(e);
			}
		return flush;
	}

	protected void addMessage(String message) {
		hasMessage = true;
		pgroup.setText(message);
		pgroup.setImage(AdichatzApplication.getInstance().getFormToolkit().getRegisteredImage("IMG_CANCEL"));
	}

	protected void removeMessage() {
		if (hasMessage) {
			hasMessage = false;
			TreeItem treeItem = treeViewer.getTree().getSelection()[0];
			pgroup.setText(treeItem.getText());
			pgroup.setImage(treeItem.getImage());
		}
	}
}
