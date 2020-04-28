package org.adichatz.studio.xjc.editor;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.extra.ConfirmFormDialog;
import org.adichatz.engine.extra.IConfirmContent;
import org.adichatz.engine.renderer.AdiFormToolkit;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.common.GeneratorConstants;
import org.adichatz.scenario.util.AdiPropertyTester;
import org.adichatz.scenario.util.ScenarioUtil;
import org.adichatz.studio.StudioRcpPlugin;
import org.adichatz.studio.util.IStudioConstants;
import org.adichatz.studio.xjc.controller.XjcTreeController;
import org.adichatz.studio.xjc.editor.action.GenerateAction;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ide.IDE;

import net.miginfocom.swt.MigLayout;

public class AskForSwitching {
	private XjcTreeFormEditor editor;

	private IFile activeFile;

	private int returnValue = -1;

	public AskForSwitching(XjcTreeFormEditor editor, IFile generatedFile, String content) {
		this.editor = editor;
		runAction(generatedFile, content);
	}

	public void runAction(IFile generatedFile, String content) {
		if (StudioRcpPlugin.getDefault().getPreferenceStore().getBoolean(IStudioConstants.ASK_FOR_SWITCHING_FILE)
				&& AdiPropertyTester.isSwitchable(generatedFile)) {
			IPath localPath = new Path(ScenarioUtil.getTreeId(generatedFile).concat(".").concat(generatedFile.getFileExtension()));
			activeFile = generatedFile.getParent().getFile(localPath);

			IConfirmContent confirmContent = new IConfirmContent() {
				@Override
				public void okPressed(List<Control> complementControls) {
				}

				@Override
				public void createConfirmContent(Composite parent, AdiFormToolkit toolkit, List<Control> complementControls) {
					String message = getFromStudioBundle("studio.xjcEditor.switchToActiveFile.message", generatedFile.getName(),
							activeFile.getName());
					parent.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
					Composite composite = toolkit.createComposite(parent);
					composite.setLayout(new MigLayout("wrap 1, ins 0", "grow,fill", "grow,fill"));
					Label title = toolkit.createLabel(composite, message);
					title.setFont(JFaceResources.getBannerFont());
				}
			};

			final Image image = AdichatzApplication.getInstance().getImage(GeneratorConstants.STUDIO_BUNDLE, "IMG_SWITCH.png");
			ConfirmFormDialog confirmFormDialog = new ConfirmFormDialog(editor.getSite().getShell(),
					AdichatzApplication.getInstance().getFormToolkit(), getFromStudioBundle("studio.xjcEditor.switchToActiveFile"),
					image, confirmContent) {
				protected void buttonPressed(int buttonId) {
					returnValue = buttonId;
					if (IDialogConstants.OK_ID == buttonId) {
						ITreeWrapper treeWrapper = editor.getTreeWrapper();
						String fileName = treeWrapper.getXmlFile().getAbsolutePath();
						fileName = fileName.substring(0, fileName.length() - 14).concat(".axml");
						try {
							if (null == content)
								ScenarioUtil.createXmlFile(new File(fileName), treeWrapper);
							else
								Files.write(Paths.get(fileName), content.getBytes());
							activeFile.refreshLocal(IResource.DEPTH_ZERO, null);
							if (StudioRcpPlugin.getDefault().getPreferenceStore()
									.getBoolean(IStudioConstants.GENERATE_CLASS_AFTER_SAVING)) {
								// Generate automatically java class
								XjcTreeController treeController = (XjcTreeController) editor.getGenCode().getRegister()
										.get("xjcTree").getController();
								new GenerateAction(activeFile, treeController.getControl().getDisplay()).runAction();
							}
							IDE.openEditor(editor.getSite().getPage(), activeFile, true);
							editor.close(false);
						} catch (CoreException | IOException e) {
							LogBroker.logError(e);
						}
					}
					close();
				}

				@Override
				protected Control createButtonBar(Composite parent) {
					Composite composite = new Composite(parent, SWT.NONE);
					GridLayout layout = new GridLayout();
					layout.numColumns = 3; // this is incremented by createButton
					layout.makeColumnsEqualWidth = true;
					layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
					layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
					layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
					layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
					composite.setLayout(layout);
					GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.VERTICAL_ALIGN_CENTER);
					composite.setLayoutData(data);
					composite.setFont(parent.getFont());
					Button okButton = createButton(composite, IDialogConstants.OK_ID,
							getFromStudioBundle("studio.xjcEditor.save.active.file"), true);
					okButton.setToolTipText(getFromStudioBundle("studio.xjcEditor.save.active.file.tooltip", activeFile.getName(),
							generatedFile.getName()));
					Button saveGenerated = createButton(composite, 2, getFromStudioBundle("studio.xjcEditor.save.generated.file"),
							false);
					saveGenerated.setToolTipText(
							getFromStudioBundle("studio.xjcEditor.save.generated.file.tooltip", generatedFile.getName()));
					createButton(composite, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
					return composite;
				}
			};
			confirmFormDialog.open();
		} else
			returnValue = 2;
	}

	public IFile getActiveFile() {
		return activeFile;
	}

	public int getReturnValue() {
		return returnValue;
	}
}
