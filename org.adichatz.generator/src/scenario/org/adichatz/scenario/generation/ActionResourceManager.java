package org.adichatz.scenario.generation;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logTrace;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import org.adichatz.engine.common.AdichatzErrorException;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.generator.common.FileUtil;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.generator.xjc.CopyResourceType;
import org.adichatz.generator.xjc.RemoveResourceType;
import org.adichatz.generator.xjc.ReplacementType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.scenario.util.ScenarioUtil;
import org.eclipse.core.resources.IResource;

public class ActionResourceManager {
	private static enum RESOURCE_TYPE {
		FILE, JAR
	}

	private static enum RESOURCE_MODE {
		SOURCE, TARGET
	};

	private Map<String, OutputEntry> entryMap;

	private static String FILE_SEPARATOR = (String) System.getProperties().get("file.separator");

	private ScenarioResources scenarioResources;

	private BufferCode buffer;

	private Set<File> changedFiles;

	public ActionResourceManager(ScenarioResources scenarioResources) {
		this.scenarioResources = scenarioResources;
		buffer = scenarioResources.getBuffer();
		changedFiles = new HashSet<>();
	}

	public Set<File> copyResource(CopyResourceType copyResource) {
		try {
			ResourceEntry resourceEntry = new ResourceEntry(copyResource);
			resourceEntry.copyResources();
			resourceEntry.dispose();
			return changedFiles;
		} catch (AdichatzErrorException e) {
			logError(e);
			return new HashSet<File>();
		}
	}

	public void removeResource(RemoveResourceType removeResource) {
		try {
			ResourceEntry resourceEntry = new ResourceEntry(removeResource);
			resourceEntry.removeResources();
			resourceEntry.dispose();
		} catch (AdichatzErrorException e) {
			logError(e);
		}
	}

	private class ResourceEntry {
		private String sourceURI;

		private RESOURCE_TYPE sourceType;

		private String sourceMember;

		private List<File> sourceFiles;

		private int depth;

		private boolean isSourceList;

		private String targetURI;

		private RESOURCE_TYPE targetType;

		private String targetMember;

		private RemoveResourceType removeResource;

		private OutputResources outputResources;

		private ResourceEntry(RemoveResourceType removeResource) {
			this.removeResource = removeResource;
			ResourceAttributes resourceAttributes;
			if (removeResource instanceof CopyResourceType) {
				resourceAttributes = new ResourceAttributes(((CopyResourceType) removeResource).getSourceURI(),
						RESOURCE_MODE.SOURCE);
				sourceURI = resourceAttributes.resultURI;
				sourceType = resourceAttributes.resourceType;
				sourceMember = resourceAttributes.sourceMember;
				depth = resourceAttributes.depth;
			}
			String workURI = removeResource.getTargetURI();
			if (removeResource.isRelative()) {
				StringBuffer targetSB = new StringBuffer();
				if (!workURI.startsWith("#PLUGINHOME"))
					targetSB.append("#PLUGINHOME()");
				if (!workURI.startsWith("/"))
					targetSB.append("/");
				workURI = targetSB.append(workURI).toString();
			}
			resourceAttributes = new ResourceAttributes(workURI, RESOURCE_MODE.TARGET);
			targetURI = resourceAttributes.resultURI;
			targetType = resourceAttributes.resourceType;
			targetMember = resourceAttributes.sourceMember;
		}

		private void removeResources() {
			if (RESOURCE_TYPE.FILE == targetType) {
				File targetFile = new File(targetURI);
				if (!targetFile.exists()) {
					String message = getFromGeneratorBundle("invalid.file.uri.resources", removeResource.getTargetURI());
					if (removeResource.isThrowError())
						logError(message);
					else
						logTrace(message);
				} else if (!targetFile.delete())
					logError(getFromGeneratorBundle("cannot.delete.file.resources", removeResource.getTargetURI()));
			} else {
				try {
					outputResources = new OutputResources(this);
					boolean delete = false;
					for (String entryKey : entryMap.keySet().toArray(new String[entryMap.size()])) {
						if (entryKey.startsWith(targetMember))
							delete = (null != entryMap.remove(entryKey)) || delete;
					}
					logError(getFromGeneratorBundle("invalid.file.uri.resources", removeResource.getTargetURI()));
				} catch (IOException e) {
					logError(e);
				}
			}
		}

		private void copyResources() {
			changedFiles = new HashSet<>();
			CopyResourceType copyResource = (CopyResourceType) removeResource;
			if (RESOURCE_TYPE.FILE == sourceType) {
				sourceFiles = new ArrayList<>();
				File sourceFile = new File(sourceURI);
				if (!sourceFile.exists()) {
					logError(getFromGeneratorBundle("invalid.file.uri.resources", sourceURI));
					return;
				}
				isSourceList = sourceFile.isDirectory();
				if (isSourceList)
					getDir(sourceFile);
				else
					addFile(sourceFile);
				if (RESOURCE_TYPE.FILE == targetType) {
					File targetFile = getTargetFile(!isSourceList && -1 != sourceFile.getName().indexOf('.'));
					for (File sourceEntry : sourceFiles) {
						String[] tokens = getTokens(copyResource);
						String[] values = getValues(copyResource);
						File file = targetFile.isDirectory() ? new File(targetFile.getAbsolutePath() + "/" + sourceEntry.getName())
								: targetFile;
						try {
							if (0 == tokens.length)
								FileUtil.copyFile(sourceEntry, file, copyResource.isForce());
							else
								FileUtil.copyFile(sourceEntry, file, tokens, values, copyResource.isForce());
							changedFiles.add(file);
						} catch (IOException e) {
							logError(e);
						}
					}
				} else {
					try {
						outputResources = new OutputResources(this);
						int radixLength = sourceURI.length();
						for (File file : sourceFiles) {
							String fileName = file.getAbsolutePath().substring(radixLength);
							if (fileName.startsWith(FILE_SEPARATOR))
								fileName = fileName.substring(FILE_SEPARATOR.length());
							String path = (targetMember + FILE_SEPARATOR + fileName).replace(FILE_SEPARATOR, "/");
							boolean replace = entryMap.containsKey(path);
							if (replace && copyResource.isForce())
								entryMap.remove(path);
							if (copyResource.isForce() || !replace) {
								InputStream inputStream = new FileInputStream(file);
								FileUtil.addContent2ZipFile(outputResources.jarOutStream, path, inputStream, false);
								inputStream.close();
								changedFiles.add(outputResources.targetFile);
							}
						}
					} catch (IOException e) {
						logError(e);
					}
				}
			} else {
				try {
					int depth = IResource.DEPTH_ZERO;
					if (sourceMember.endsWith("/**")) {
						depth = IResource.DEPTH_INFINITE;
						sourceMember = sourceMember.substring(0, sourceMember.length() - 3);
					}
					File sourceFile = new File(sourceURI);
					JarFile sourceJarFile = new JarFile(sourceFile);
					JarEntry memberEntry = sourceJarFile.getJarEntry(sourceMember);
					if (null == memberEntry || memberEntry.isDirectory())
						depth = (depth == IResource.DEPTH_INFINITE) ? depth : IResource.DEPTH_ONE;
					else if (IResource.DEPTH_INFINITE == depth) {
						sourceJarFile.close();
						throw new AdichatzErrorException(
								getFromGeneratorBundle("source.uri.not.directory", copyResource.getSourceURI()));
					}
					int lastIndex = sourceMember.length() + 1;
					Enumeration<JarEntry> entries = sourceJarFile.entries();
					File targetFile = null;
					if (RESOURCE_TYPE.FILE == targetType) {
						targetFile = getTargetFile(false);
					} else
						outputResources = new OutputResources(this);

					while (entries.hasMoreElements()) {
						JarEntry entry = entries.nextElement();
						String entryName = entry.getName();
						boolean add = false;
						switch (depth) {
						case IResource.DEPTH_ZERO:
							add = entryName.equals(sourceMember);
							break;
						case IResource.DEPTH_ONE:
							add = entryName.startsWith(sourceMember) && entryName.lastIndexOf('/') < lastIndex;
							break;
						case IResource.DEPTH_INFINITE:
							add = entryName.startsWith(sourceMember);
							break;
						default:
							break;
						}
						if (add)
							if (RESOURCE_TYPE.FILE == targetType) {
								String targetName = targetFile.getAbsolutePath();
								if (!targetName.endsWith(FILE_SEPARATOR))
									targetName = targetName.concat(FILE_SEPARATOR);
								targetName = targetName.concat(entryName);
								File file = new File(targetFile.getAbsolutePath() + "/" + entryName);
								if (!file.exists() || copyResource.isForce()) {
									if (file.exists() && file.isDirectory() != entry.isDirectory()) {
										logError(getFromGeneratorBundle("source.and.target.incompatible",
												copyResource.getSourceURI(), removeResource.getTargetURI()));
									} else {
										if (entry.isDirectory())
											file.mkdirs();
										else {
											if (!file.getParentFile().exists())
												file.getParentFile().mkdirs();
											InputStream is = sourceJarFile.getInputStream(entry);
											FileOutputStream fos = new java.io.FileOutputStream(file);
											while (is.available() > 0) {
												fos.write(is.read());
											}
											fos.close();
											is.close();
										}
									}
								}
								changedFiles.add(file);
							} else {
								if (copyResource.isForce() || !entryMap.containsKey(entryName))
									entryMap.put(entryName, new OutputEntry(entry, sourceJarFile));
							}
					}
				} catch (IOException e) {
					logError(e);
				}
			}
		}

		private String[] getValues(CopyResourceType copyResource) {
			int i = 0;
			String[] values = new String[copyResource.getReplacement().size()];
			for (ReplacementType replacement : copyResource.getReplacement()) {
				values[i] = ScenarioUtil.evalLocation(buffer, replacement.getValue());
				i++;
			}
			return values;
		}

		private void addFile(File sourceFile) {
			sourceFiles.add(sourceFile);
			String fileName = sourceFile.getName();
			int index = fileName.indexOf('.');
			if (-1 != index && "class".equals(fileName.substring(index + 1))) {
				String radixName = fileName.substring(0, index).concat("$");
				File[] files = sourceFile.getParentFile().listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						String fileName = pathname.getName();
						return fileName.startsWith(radixName) && fileName.endsWith(".class");
					}
				});
				for (File file : files) {
					isSourceList = true;
					sourceFiles.add(file);
				}
			}
		}

		private void getDir(File sourceFile) {
			for (File file : sourceFile.listFiles()) {
				if (file.isDirectory() && IResource.DEPTH_INFINITE != depth)
					getDir(file);
				else
					addFile(file);
			}
		}

		private String[] getTokens(CopyResourceType copyResource) {
			String[] tokens = new String[copyResource.getReplacement().size()];
			int i = 0;
			for (ReplacementType replacement : copyResource.getReplacement()) {
				tokens[i] = ScenarioUtil.evalLocation(buffer, replacement.getToken());
				i++;
			}
			return tokens;
		}

		private File getTargetFile(boolean sourceWithExtension) {
			File targetFile = new File(targetURI);
			if (!targetFile.exists()) {
				if (isSourceList)
					targetFile.mkdirs();
				else {
					if (!targetFile.getParentFile().exists())
						targetFile.getParentFile().mkdirs();
					if (sourceWithExtension) {
						if (-1 == targetFile.getName().indexOf('.')) {
							targetFile.mkdirs();
						}
					}
				}
			}
			if (!targetFile.isDirectory() && isSourceList)
				throw new AdichatzErrorException(getFromGeneratorBundle("target.must.be.directory", targetURI));
			return targetFile;
		}

		private void dispose() {
			if (null != sourceFiles) {
				sourceFiles.clear();
				sourceFiles = null;
			}
			if (null != outputResources) {
				try {
					outputResources.close();
				} catch (IOException e) {
					logError(e);
				}
				entryMap.clear();
				entryMap = null;
			}
		}
	}

	private class ResourceAttributes {

		private String resultURI;

		private RESOURCE_TYPE resourceType;

		private int depth;

		private String sourceMember;

		ResourceAttributes(String resourceURI, RESOURCE_MODE resourceMode) {
			depth = IResource.DEPTH_ZERO;
			resultURI = ScenarioUtil.evalLocation(buffer, resourceURI);
			int index = resultURI.indexOf('!');
			if (-1 != index || (RESOURCE_MODE.TARGET == resourceMode && resultURI.endsWith(".jar"))) {
				sourceMember = resultURI.substring(index + 1);
				resultURI = resultURI.substring(0, index);
				if (sourceMember.endsWith("/**")) {
					depth = IResource.DEPTH_INFINITE;
					sourceMember = sourceMember.substring(0, sourceMember.length() - 3);
				}
				if (sourceMember.startsWith("/"))
					sourceMember = sourceMember.substring(1);
				resourceType = RESOURCE_TYPE.JAR;
			} else
				try {
					EngineTools.getContributionURIToStrings(resultURI);
					resourceType = RESOURCE_TYPE.JAR;
				} catch (AdichatzErrorException e) {
					resourceType = RESOURCE_TYPE.FILE;
				}
		}
	}

	class OutputResources {
		File tmpFile = null;

		FileOutputStream fileOutputStream = null;

		JarOutputStream jarOutStream = null;

		File targetFile = null;

		JarFile targetJarFile = null;

		String targetURI;

		OutputResources(ResourceEntry resourceEntry) throws IOException {
			this.targetURI = resourceEntry.targetURI;
			entryMap = new HashMap<>();
			open();
		}

		private void open() throws IOException {
			tmpFile = new File(scenarioResources.getPluginHome() + "/resources/build/tmpJarFile.jar");
			fileOutputStream = new FileOutputStream(tmpFile);
			jarOutStream = new JarOutputStream(fileOutputStream);
			targetFile = new File(targetURI);
			targetJarFile = new JarFile(targetFile);
			if (targetFile.exists()) {// Add curent content is target file already exists
				Enumeration<JarEntry> entries = targetJarFile.entries();
				while (entries.hasMoreElements()) {
					JarEntry jarEntry = entries.nextElement();
					entryMap.put(jarEntry.getName(), new OutputEntry(jarEntry, targetJarFile));
				}
			}
		}

		private void close() throws IOException {
			byte[] buf = new byte[FileUtil.BYTE];
			Set<JarFile> jarFiles = new HashSet<>();
			for (OutputEntry outputEntry : entryMap.values()) {
				jarFiles.add(outputEntry.jarFile);
				outputEntry.write(this, buf);
			}
			jarOutStream.close();
			fileOutputStream.close();
			targetJarFile.close();
			for (JarFile jarFile : jarFiles)
				jarFile.close();
			buf = null;
			Files.move(tmpFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			changedFiles.add(targetFile);
		}
	}

	class OutputEntry {

		JarEntry entry;

		JarFile jarFile;

		public OutputEntry(JarEntry entry, JarFile jarFile) {
			this.entry = entry;
			this.jarFile = jarFile;
		}

		private void write(OutputResources outputResources, byte[] buf) throws IOException {
			outputResources.jarOutStream.putNextEntry(new ZipEntry(entry.getName()));
			InputStream in = jarFile.getInputStream(entry);
			int len;
			while ((len = in.read(buf)) > 0) {
				outputResources.jarOutStream.write(buf, 0, len);
			}

			// Complete the entry
			outputResources.jarOutStream.closeEntry();
			in.close();
		}

	}
}
