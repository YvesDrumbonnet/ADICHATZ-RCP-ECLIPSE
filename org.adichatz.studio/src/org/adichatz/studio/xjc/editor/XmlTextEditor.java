package org.adichatz.studio.xjc.editor;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import org.adichatz.engine.common.AdiResourceBundle;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.internal.resources.ResourceException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

import net.miginfocom.swt.MigLayout;

public class XmlTextEditor extends StructuredTextEditor implements IFormPage {
	private boolean xmlEditable;

	private AStudioFormEditor editor;

	private int pageIndex;

	private Action saveAction;

	public XmlTextEditor(AStudioFormEditor editor, int pageIndex) {
		this.editor = editor;
		this.pageIndex = pageIndex;
	}

	/*
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor.createPartControl(Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		final ManagedForm managedForm = new ManagedForm(parent);
		ScrolledForm scrolledForm = managedForm.getForm();
		scrolledForm.setText(editor.getPageTitle());
		scrolledForm.getBody().setLayout(new MigLayout("wrap 1", "grow,fill"));
		scrolledForm.getBody().setLayout(new FillLayout());
		if (xmlEditable) {
			AdiResourceBundle resourceBundle = AdichatzApplication.getPluginResources(GeneratorConstants.STUDIO_BUNDLE)
					.getResourceBundleManager().getResourceBundle("adichatzStudio");
			Action refreshAction = new Action() {
				@Override
				public void run() {
					doRevertToSaved();
					saveAction.setEnabled(false);
				}
			};
			AdiFormToolkit toolkit = AdichatzApplication.getInstance().getContextValue(AdiFormToolkit.class);
			refreshAction.setImageDescriptor(toolkit.getRegisteredImageDescriptor("IMG_REFRESH"));
			refreshAction.setToolTipText(resourceBundle.getValueFromBundle("studio.editor.refreshFormPage"));
			scrolledForm.getToolBarManager().add(refreshAction);

			saveAction = new Action() {
				@Override
				public void run() {
					doSave(null);
					saveAction.setEnabled(false);
				}
			};
			saveAction.setImageDescriptor(toolkit.getRegisteredImageDescriptor("IMG_SAVE"));
			saveAction.setToolTipText(resourceBundle.getValueFromBundle("studio.editor.save"));
			saveAction.setEnabled(false);
			scrolledForm.getToolBarManager().add(saveAction);
		}

		scrolledForm.getToolBarManager().update(true);
		super.createPartControl(scrolledForm.getBody());

	}

	/*
	 * @see WorkbenchPart#firePropertyChange(int)
	 */
	@Override
	protected void firePropertyChange(int property) {
		super.firePropertyChange(property);
		updatePropertyDependentActions();
		if (null != saveAction)
			saveAction.setEnabled(isDirty() && xmlEditable);
	}

	public boolean isXmlEditable() {
		return xmlEditable;
	}

	public void setXmlEditable(boolean xmlEditable) {
		this.xmlEditable = xmlEditable;
		if (null != getSourceViewer())
			getSourceViewer().setEditable(isEditable());
	}

	@Override
	public boolean isEditable() {
		return super.isEditable() && xmlEditable;
	}

	@SuppressWarnings("restriction")
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		if (editor instanceof XjcTreeFormEditor) {
			IFile file = ((FileEditorInput) getEditorInput()).getFile();
			int returnValue = new AskForSwitching((XjcTreeFormEditor) editor, file, getXmlViewer().getDocument().get())
					.getReturnValue();
			/*
			 return Value:
			# -1 (escape) or 1 (cancel) do nothing do nothing
				# 0 current file is Generated, copy changed content in active file
				# 2 save changes in current file
			 */
			if (2 == returnValue) {
				// If save in active
				for (IResource resource : ScenarioUtil.getJavaFiles(file))
					try {
						resource.delete(true, null);
					} catch (ResourceException e) {
						logError(getFromStudioBundle("studio.resource.exception", resource.getName()), e);
					} catch (CoreException e) {
						logError(e);
					}
				super.doSave(progressMonitor);
				editor.setEditable(true);
			}
		} else
			super.doSave(progressMonitor);
	}

	@Override
	public void doRevertToSaved() {
		super.doRevertToSaved();
		editor.setEditable(true);
	}

	public ISourceViewer getXmlViewer() {
		return super.getSourceViewer();
	}

	@Override
	public void initialize(FormEditor editor) {
	}

	@Override
	public FormEditor getEditor() {
		return editor;
	}

	@Override
	public IManagedForm getManagedForm() {
		return null;
	}

	@Override
	public void setActive(boolean active) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canLeaveThePage() {
		return true;
	}

	@Override
	public Control getPartControl() {
		return getSourceViewer().getTextWidget();
	}

	@Override
	public String getId() {
		return "XML";
	}

	@Override
	public int getIndex() {
		return pageIndex;
	}

	@Override
	public void setIndex(int index) {
	}

	@Override
	public boolean isEditor() {
		return true;
	}

	@Override
	public boolean selectReveal(Object object) {
		return false;
	}
}
