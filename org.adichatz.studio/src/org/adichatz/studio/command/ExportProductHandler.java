package org.adichatz.studio.command;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logWarning;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.generation.ManifestChanger;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.pde.internal.core.iproduct.IProductPlugin;
import org.eclipse.pde.internal.core.product.WorkspaceProductModel;
import org.eclipse.pde.internal.ui.PDEPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.xml.sax.SAXException;

@SuppressWarnings("restriction")
public class ExportProductHandler extends AAdiHandler {

	private List<FileRestoration> fileRestorations = new ArrayList<FileRestoration>();

	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		final IProject project;
		ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection instanceof ITreeSelection) {
			Object selectedObject = ((ITreeSelection) selection).getFirstElement();
			if (selectedObject instanceof IJavaProject)
				project = ((IJavaProject) selectedObject).getProject();
			else
				project = (IProject) selectedObject;

			if (checkProject(project))
				Display.getCurrent().syncExec(() -> {
					PDEPlugin.getDefault().getLabelProvider().connect(this);
					IDialogSettings masterSettings = PDEPlugin.getDefault().getDialogSettings();
					IDialogSettings settings = masterSettings.getSection("ProductExportWizard");
					try {
						launchExport(project, settings);
					} catch (CoreException e) {
						LogBroker.logError(e);
					}
				});
		}
		return null;
	}

	private boolean checkProject(IProject project) {
		try {
			int severity;
			severity = project.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			if (IMarker.SEVERITY_WARNING < severity) {
				LogBroker.displayError(getFromStudioBundle("studio.menu.productExportAction.cannotExportProject"),
						getFromStudioBundle("studio.menu.productExportAction.projectHasErrors"));
				return false;
			}
		} catch (CoreException e) {
			LogBroker.logError(e);
			return false;
		}
		IFile configFile = project.getFile(EngineConstants.XML_FILES_PATH + "/AdichatzRcpConfig.xml");
		if (configFile.exists()) {
			InputStream inputStream;
			try {
				inputStream = configFile.getContents();
				EngineTools.getUnmarshaller().unmarshal(inputStream);
				inputStream.close();
			} catch (CoreException | JAXBException | IOException e) {
				logError(e);
			}
		} else {
			if (!EngineTools.openDialog(MessageDialog.WARNING,
					getFromStudioBundle("studio.menu.productExportAction.noConfigFile.title"),
					getFromStudioBundle("studio.menu.productExportAction.noConfigFile.message")))
				return false;
		}
		return true;
	}

	private void launchExport(final IProject project, IDialogSettings settings) throws CoreException {
		try {
			prepareExport(project);
			for (String bundleName : ScenarioUtil.getRequiredBundleNames(new ManifestChanger(project, null))) {
				IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(bundleName);
				if (null != requiredProject && requiredProject.exists())
					prepareExport(requiredProject);
			}
			AdiProductExportWizard exportWizard = new AdiProductExportWizard(project, settings);
			new WizardDialog(Display.getCurrent().getActiveShell(), exportWizard).open();
		} catch (ParserConfigurationException | SAXException | TransformerException | OperationCanceledException | IOException
				| CoreException | TransformerFactoryConfigurationError e) {
			logError(e);
		} finally {
			for (FileRestoration fileRestoration : fileRestorations)
				fileRestoration.restoreFile();
		}
	}

	private void prepareExport(final IProject project)
			throws IOException, CoreException, ParserConfigurationException, SAXException, TransformerException {
		Set<String> notExportedBundles = new HashSet<String>();
		ManifestChanger manifestChanger = new ManifestChanger(project, null);
		try {
			StringTokenizer tokenizer = new StringTokenizer(manifestChanger.getMainAttributes().getValue(Constants.REQUIRE_BUNDLE),
					",");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();
				if (doNotExport(token))
					notExportedBundles.add(token);
			}
		} catch (CoreException | IOException e) {
			logError(e);
		}

		// Delete plugins which must not be exported (tool, generator, ...) from manifest file
		if (!notExportedBundles.isEmpty()) {
			IFile manifestFile = project.getFile("META-INF/MANIFEST.MF");
			new FileRestoration(manifestFile);
			for (String notExportedBundle : notExportedBundles)
				manifestChanger.removeAttributeElement(Constants.REQUIRE_BUNDLE, notExportedBundle);
			manifestChanger.write();
			manifestFile.refreshLocal(IResource.DEPTH_INFINITE, null);
		}

		WorkspaceProductModel productModel = new WorkspaceProductModel(project.getFile(project.getName() + ".product"), true);
		productModel.load();
		List<IProductPlugin> notExportedPlugins = new ArrayList<IProductPlugin>(2);
		for (IProductPlugin plugin : productModel.getProduct().getPlugins()) {
			if (doNotExport(plugin.getId()))
				notExportedPlugins.add(plugin);
		}
		if (!notExportedPlugins.isEmpty()) {
			IFile productFile = project.getFile(project.getName() + ".product");
			new FileRestoration(productFile);
			productModel.getProduct().removePlugins(notExportedPlugins.toArray(new IProductPlugin[notExportedPlugins.size()]));
			productModel.save();
			productFile.refreshLocal(IResource.DEPTH_INFINITE, null);
		}
	}

	private boolean doNotExport(String bundleName) throws CoreException, IOException {
		Bundle bundle = Platform.getBundle(bundleName);
		ManifestChanger manifestChanger;
		if (null == bundle) {
			IProject dependantProject = ResourcesPlugin.getWorkspace().getRoot().getProject(bundleName);
			if (dependantProject.exists()) {
				manifestChanger = new ManifestChanger(dependantProject, null);
			} else {
				logWarning(getFromStudioBundle("studio.bundle.project.not.exists", bundleName));
				return false;
			}
		} else
			manifestChanger = new ManifestChanger(bundle.getEntry("META-INF/MANIFEST.MF"));
		String adichatzDoNotExport = manifestChanger.getValue(IScenarioConstants.ADICHATZ_DO_NOT_EXPORT);
		return null != adichatzDoNotExport && "true".equals(adichatzDoNotExport.toLowerCase());
	}

	class FileRestoration {
		IFile ifile;

		File file;

		byte[] data;

		public FileRestoration(IFile ifile) throws IOException {
			this.ifile = ifile;
			file = ifile.getLocation().toFile();
			if (!ifile.exists())
				throw new RuntimeException(getFromStudioBundle("studio.export.no.file", ifile.getName()));
			this.data = EngineTools.getByteArray(new FileInputStream(file));
			fileRestorations.add(this);
		}

		protected void restoreFile() {
			try {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
				FileOutputStream outputStream = new FileOutputStream(file);
				EngineTools.copyStream(inputStream, outputStream);
				inputStream.close();
				outputStream.close();
				ifile.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (IOException | CoreException e) {
				logError(e);
			}
		}
	}
}
