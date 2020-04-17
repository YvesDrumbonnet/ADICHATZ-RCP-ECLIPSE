/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * This software is a computer program whose purpose is to build easily
 * Eclipse RCP applications using JPA in a JEE or JSE context.
 * 
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 * 
 ********************************************************************************
 * 
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant à construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 * 
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant 
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à 
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement, 
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité. 
 * 
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 *******************************************************************************/
package org.adichatz.generator;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.common.LogBroker.logInfo;
import static org.adichatz.engine.common.LogBroker.logTrace;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.FileUtility;
import org.adichatz.engine.data.GencodePath;
import org.adichatz.engine.extra.AdiResourceURI;
import org.adichatz.generator.common.ParentContext;
import org.adichatz.generator.tools.BufferCode;
import org.adichatz.scenario.IScenarioConstants;
import org.adichatz.scenario.ScenarioInput;
import org.adichatz.scenario.ScenarioResources;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.internal.InternalPolicy;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

// TODO: Auto-generated Javadoc
/**
 * The Class AClassGenerator.
 * 
 * @author Yves Drumbonnet
 */
@SuppressWarnings("restriction")
public abstract class AClassGenerator extends ACodeGenerator {

	/** The Buffer Code Map. */
	protected Map<String, BufferCode> bufferCodeMap = new HashMap<String, BufferCode>();

	/** The java class file. */
	protected FileWriter javaClassFile;

	/** The class name generated by the current generator. */
	protected String className;

	/** The root file name generated by the parent instance. */
	protected String rootFileName;

	/** The package name. */
	protected String classPackage;

	protected GencodePath gencodePath;

	private int sequence = 1;

	protected KeyWordGenerator keyWordGenerator;

	/**
	 * Instantiates a new a class generator.
	 * 
	 * @param scenarioInput
	 *            the scenario input
	 */
	protected AClassGenerator(ScenarioInput scenarioInput, ACodeGenerator parentGenerator) {
		this.scenarioInput = scenarioInput;
		this.parentGenerator = parentGenerator;
		gencodePath = scenarioInput.getScenarioResources().getGencodePath();
		classPackage = getClassPackage(scenarioInput);
		keyWordGenerator = scenarioInput.getScenarioResources().getKeyWordGenerator();
	}

	protected void createClassFile(ScenarioInput scenarioInput, String extendClause) {
		preCreateClassFile();
		createClassFile(scenarioInput, scenarioInput.getTreeId(), extendClause);
	}

	protected void preCreateClassFile() {
	}

	protected String getClassPackage(ScenarioInput scenarioInput) {
		return gencodePath.getGencodePackage()
				+ (EngineTools.isEmpty(scenarioInput.getSubPackage()) || scenarioInput.getSubPackage().equals(".") ? ""
						: "." + scenarioInput.getSubPackage());
	}

	/**
	 * Creates the class file.
	 *
	 * @param scenarioInput
	 *            the scenario input
	 * @param className
	 *            the class name
	 * @param extendClause
	 *            the extend clause
	 */
	protected void createClassFile(ScenarioInput scenarioInput, String className, String extendClause) {
		this.className = className;

		String subPackage = scenarioInput.getSubPackage();
		if (null == subPackage || ".".equals(subPackage))
			subPackage = "";
		String dirName = getJavaFileDir(subPackage);
		File file = new File(dirName);
		if (!file.exists()) {
			logTrace(getFromGeneratorBundle("createDirectory", dirName));
			file.mkdirs();
		}
		rootFileName = dirName + "/" + className;
		/*
		 * Create the JavaClassFile in the child class
		 */
		try {
			javaClassFile = new FileWriter(rootFileName.concat(".java"));
			/* Write package clause */
			javaClassFile
					.write("/**********************************************************************************************\n");
			javaClassFile.write(" * This class was automatically generated by Adichatz tools on " + new Date() + "\n");
			javaClassFile.write(" *\n");
			javaClassFile.write(" * \t\t\t\t+----------------------------------------+\n");
			javaClassFile.write(" * \t\t\t\t| BE CAREFUL BEFORE UPDATING THIS CLASS! |\n");
			javaClassFile.write(" * \t\t\t\t+----------------------------------------+\n");

			javaClassFile.write(" *\n");
			javaClassFile.write(" * Changes will be transient up to next generation process.\n");
			javaClassFile.write(" *");
			javaClassFile
					.write(" **********************************************************************************************/\n");
			javaClassFile.write("package " + classPackage + ";\n\n");

			addBlocks();

			addDeclareURIAnnotation();
			writeImportClause();

			javaClassFile.write("\n");

			addURIAnnotation();

			javaClassFile.write("public class " + className + extendClause + " {\n");

			javaClassFile.write(declarationBuffer.toString());

			javaClassFile.write(classBodyBuffer.toString());

			for (BufferCode bufferCode : extraBufferMap.values())
				javaClassFile.write(bufferCode.toString());
			extraBufferMap.clear();

			javaClassFile.write("}");

			javaClassFile.close();

			if (scenarioInput.isCompile()) {
				IProject project = scenarioInput.getScenarioResources().getProject();
				ScenarioResources scenarioResources = scenarioInput.getScenarioResources();
				if (InternalPolicy.OSGI_AVAILABLE && null != project) {
					IFile classFile = scenarioResources.getJavaFolder(scenarioInput.getSubPackage())
							.getFile(className.concat(".java"));
					scenarioResources.getAffectedFiles().add(classFile);
				} else {
					String javaFileNames = rootFileName + ".java";
					if ("true".equals(scenarioResources.getParam(IScenarioConstants.FORMAT_GENERATED_CLASS)))
						formatGeneratedClass(javaFileNames);
					logInfo(getFromGeneratorBundle("compilingGeneratedClass",
							javaFileNames.substring(gencodePath.getPluginHome().length())));
					String commandLine = "-classpath " + scenarioResources.getClassPath() + " -1.8 -nowarn -d "
							+ gencodePath.getGenCodeBinLocation() + " " + javaFileNames;
					logTrace(commandLine);

					BatchCompiler.compile(commandLine, new PrintWriter(System.out), new PrintWriter(System.err), null);
				}
			}
		} catch (IOException e) {
			logError(e);
		}

	}

	/**
	 * Format Java file.
	 * 
	 * @param javaFileName
	 *            the java file name
	 */
	@SuppressWarnings("unchecked")
	private void formatGeneratedClass(String javaFileName) {
		File file = new File(javaFileName);
		Map<String, String> options = DefaultCodeFormatterConstants.getJavaConventionsSettings();
		options.put(DefaultCodeFormatterConstants.FORMATTER_LINE_SPLIT, "132");
		// initialize the compiler settings to be able to format 1.5 code
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);

		// instantiate the default code formatter with the given options
		final CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(options);
		IDocument doc = new Document();
		try {
			String contents = new String(org.eclipse.jdt.internal.compiler.util.Util.getFileCharContent(file, null));
			// format the file
			doc.set(contents);
			TextEdit edit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT | CodeFormatter.F_INCLUDE_COMMENTS, contents, 0,
					contents.length(), 0, null);
			if (edit != null) {
				edit.apply(doc);
			} else {
				logError("Do not know how to format file:" + javaFileName + "!");
				return;
			}

			FileWriter fileWriter = new FileWriter(file);
			// write the file
			final BufferedWriter out = new BufferedWriter(fileWriter);
			try {
				out.write(doc.get());
				out.flush();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					/* ignore */
				}
			}
		} catch (IOException | BadLocationException e) {
			logError(e);
		}
	}

	/**
	 * Write import clause.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void writeImportClause() throws IOException {
		if (0 < importStatics.size()) {
			for (String importMember : importStatics)
				javaClassFile.write("import static " + importMember + ";\n");
			javaClassFile.write("\n");
		}
		for (String importMember : imports) {
			boolean addImport = true;
			if (classPackage.isEmpty() && -1 == className.indexOf('.'))
				addImport = false;
			else
				addImport = !className.startsWith(classPackage.concat("."))
						|| -1 != className.substring(classPackage.length() + 1).indexOf('.');
			if (addImport)
				javaClassFile.write("import " + importMember + ";\n");
		}
	}

	/**
	 * Gets the class name.
	 * 
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	public String getClassPackage() {
		return classPackage;
	}

	/**
	 * Creates the main class body.
	 * 
	 * @param extendClause
	 *            the extend clause
	 */
	protected void createClassBody(String extendClause) {
	}

	/**
	 * Adds blocks for main class.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected abstract void addBlocks() throws IOException;

	/**
	 * Gets the Java files directory for generated class.
	 * 
	 * @param subPackage
	 *            the sub package
	 * 
	 * @return the directory for Java files depending on present sub package (edit,query).
	 */
	protected String getJavaFileDir(String subPackage) {
		if (EngineTools.isEmpty(subPackage)) {
			return gencodePath.getGenCodeSrcLocation() + "/" + gencodePath.getGencodePackage().replace('.', '/');
		} else {
			return FileUtility.mkdirs(gencodePath.getGenCodeSrcLocation() + "/" + gencodePath.getGencodePackage().replace('.', '/')
					+ "/" + subPackage.replace('.', '/'));
		}
	}

	protected void buildClassFile(ScenarioInput scenarioInput, String entityURI, Runnable runnable) {
		ParentContext parentContext = new ParentContext(scenarioInput);
		if (!EngineTools.isEmpty(entityURI))
			scenarioInput.setPluginEntity(entityURI);
		runnable.run();
		parentContext.retrievePreviousContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.ACodeGenerator#getControllerSequence()
	 */
	@Override
	public int getControllerSequence() {
		return sequence++;
	}

	protected void addDeclareURIAnnotation() throws IOException {
		getObjectName(AdiResourceURI.class);

	}

	protected void addURIAnnotation() throws IOException {
		javaClassFile.write("@" + AdiResourceURI.class.getSimpleName() + "(URI=\"" + scenarioInput.getAdiResourceURI() + "\")\n");
	}
}
