package org.adichatz.scenario;

import static org.adichatz.engine.common.LogBroker.logError;

import java.io.IOException;
import java.net.URL;

import org.adichatz.scenario.generation.ManifestChanger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.Bundle;

public class AdiResourceContainer {
	private Bundle bundle;

	private IProject project;

	private String gencodePackage;

	private boolean currentProject;

	private IFolder xmlFolder;

	public AdiResourceContainer(Bundle bundle, IProject project, String gencodePackage, boolean currentProject) {
		this.bundle = bundle;
		this.project = project;
		this.gencodePackage = gencodePackage;
		this.currentProject = currentProject;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public IProject getProject() {
		return project;
	}

	public String getGencodePackage() {
		return gencodePackage;
	}

	public Object getFolder(String subPackage) {
		String path = subPackage.replace('.', '/');
		IFolder folder = getXmlFolder().getFolder(path);
		return folder.exists() ? folder : null;
	}

	public IFolder getXmlFolder() {
		if (null == xmlFolder)
			xmlFolder = ScenarioResources.getInstance(project).getXmlFolder();
		return xmlFolder;
	}

	public String getBinFolder() {
		String path;
		try {
			path = new ManifestChanger(bundle.getEntry("META-INF/MANIFEST.MF")).getValue(
					IScenarioConstants.ADICHATZ_GENCODE_PACKAGE).replace('.', '/');
			URL folderPath = bundle.getEntry(path);
			if (null == folderPath) {
				folderPath = bundle.getEntry("resources/gencode/bin/".concat(path));
				if (null == folderPath)
					folderPath = bundle.getEntry("bin/".concat(path));
			}
			return folderPath.getPath();
		} catch (CoreException | IOException e) {
			logError(e);
			return null;
		}
	}

	public boolean isCurrentProject() {
		return currentProject;
	}
}
