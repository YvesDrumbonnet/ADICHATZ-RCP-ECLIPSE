package org.adichatz.scenario.util;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BooleanSupplier;

import org.adichatz.scenario.ScenarioResources;
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;

public class InFlyClassBuilder {

	private ScenarioResources scenarioResources;

	private String className;

	public InFlyClassBuilder(ScenarioResources scenarioResources, String predicate) {
		this.scenarioResources = scenarioResources;
		buildClass(scenarioResources, predicate);
	}

	@SuppressWarnings("restriction")
	private void buildClass(ScenarioResources scenarioResources, String predicate) {
		try {
			// Class name must be unique otherwise even when changing predicate, Class Loader takes old class.
			className = "Harmless".concat(String.valueOf(System.currentTimeMillis()).substring(5));
			logInfo("Compiling new predicate class " + className + ".'");
			StringBuffer sourceSB = new StringBuffer("import org.adichatz.scenario.ScenarioResources;");
			sourceSB.append("public class ").append(className).append(" implements java.util.function.BooleanSupplier {");
			sourceSB.append("private ScenarioResources scenarioResources;");
			sourceSB.append("public ").append(className)
					.append("(ScenarioResources scenarioResources){this.scenarioResources=scenarioResources;}");
			sourceSB.append("public boolean getAsBoolean() {").append(predicate);
			if (!predicate.endsWith(";"))
				sourceSB.append(";");
			sourceSB.append("}}");
			EclipseCompiler compiler = new EclipseCompiler() {
				@Override
				public int run(InputStream in, OutputStream out, OutputStream err, String... arguments) {
					boolean succeed = new org.eclipse.jdt.internal.compiler.batch.Main(
							new PrintWriter(new OutputStreamWriter(System.out)),
							new PrintWriter(new OutputStreamWriter(System.err)), false/* systemExit */, null/* options */,
							null/* progress */).compile(arguments);
					return succeed ? 0 : -1;
				}
			};

			String binLocation = scenarioResources.getPluginHome().concat("/resources/build/predicate");
			File bindDirectory = new File(binLocation);
			bindDirectory.mkdirs();
			Path sourcePath = Paths.get(binLocation, className.concat(".java"));
			Files.write(sourcePath, sourceSB.toString().getBytes(StandardCharsets.UTF_8));
			if (null == scenarioResources.getClassPath())
				scenarioResources.loadScenarioParameters();

			String[] commandLine = { "-classpath", scenarioResources.getClassPath(), "-1.8", "-nowarn", "-d", binLocation,
					sourcePath.toFile().getAbsolutePath() };
			compiler.run(null, null, null, commandLine);
			scenarioResources.getGencodePath().getClassPaths().add(bindDirectory);
		} catch (IOException e) {
			className = null;
			logError(e);
		}
	}

	public BooleanSupplier evaluateSupplier() {
		if (null != className)
			try {
				Class<?> clazz = Class.forName(className, true, scenarioResources.getGencodePath().getClassLoader());
				return (BooleanSupplier) clazz.getConstructor(ScenarioResources.class).newInstance(scenarioResources);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				logError(e);
			}
		return new BooleanSupplier() {

			@Override
			public boolean getAsBoolean() {
				return false;
			}
		};
	}

}
