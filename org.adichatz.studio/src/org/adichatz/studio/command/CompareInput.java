package org.adichatz.studio.command;

import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.ByteArrayInputStream;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.internal.BufferedResourceNode;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;

@SuppressWarnings("restriction")
public class CompareInput extends CompareEditorInput {

	private BufferedResourceNode left;

	private BufferedResourceNode right;

	private IFile leftFile;

	private IFile rightFile;

	private String title;

	private String toolTipText;

	public CompareInput(IFile leftResource, IFile rightResource) {
		super(new CompareConfiguration());
		this.leftFile = leftResource;
		this.rightFile = rightResource;
		left = new BufferedResourceNode(leftResource);
		right = new BufferedResourceNode(rightResource);
	}

	protected Object prepareInput(IProgressMonitor pm) {
		CompareConfiguration compareConfiguration = getCompareConfiguration();
		compareConfiguration.setLeftLabel(leftFile.getFullPath().makeRelative().toString());
		compareConfiguration.setRightLabel(rightFile.getFullPath().makeRelative().toString());
		return new DiffNode(left, right);
	}

	@Override
	public void saveChanges(IProgressMonitor monitor) throws CoreException {
		super.saveChanges(monitor);
		/*
		 * leftResource.setContents(left.getContents()... give wrong result
		 */
		leftFile.setContents(new ByteArrayInputStream(left.getContent()), IResource.FORCE, monitor);
		rightFile.setContents(new ByteArrayInputStream(right.getContent()), IResource.FORCE, monitor);
	}

	public IFile getLeftFile() {
		return leftFile;
	}

	public IFile getRightFile() {
		return rightFile;
	}

	@Override
	public String getTitle() {
		if (null == title) {
			title = getFromStudioBundle("studio.compare.title", ScenarioUtil.getTreeId(leftFile));
		}
		return title;
	}

	@Override
	public Image getTitleImage() {
		return AdichatzApplication.getInstance().getImage("org.adichatz.studio", "IMG_COMPARE.png");
	}

	@Override
	public String getToolTipText() {
		if (null == toolTipText) {
			toolTipText = getFromStudioBundle("studio.compare.toolTipText", leftFile.getFullPath().makeRelative(), rightFile
					.getFullPath().makeRelative());
		}
		return toolTipText;
	}
}
