/*******************************************************************************
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * This software is a computer program whose purpose is to build easily Eclipse RCP applications using JPA in a JEE or JSE context.
 *
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free software. You
 * can use, modify and/ or redistribute the software under the terms of the CeCILL license as circulated by CEA, CNRS and INRIA at
 * the following URL "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license, users are
 * provided only with a limited warranty and the software's author, the holder of the economic rights, and the successive licensors
 * have only limited liability.
 *
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or developing or
 * reproducing the software by the user in light of its specific status of free software, that may mean that it is complicated to
 * manipulate, and that also therefore means that it is reserved for developers and experienced professionals having in-depth
 * computer knowledge. Users are therefore encouraged to load and test the software's suitability as regards their requirements in
 * conditions enabling the security of their systems and/or data to be ensured and, more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL license and that you accept its
 * terms.
 *
 *
 ********************************************************************************
 *
 * Copyright © Adichatz (2007-2020) - www.adichatz.org
 *
 * yves@adichatz.org
 *
 * Ce logiciel est un programme informatique servant à construire rapidement des applications Eclipse RCP en utilisant JPA dans un
 * contexte JSE ou JEE.
 *
 * Ce logiciel est régi par la licence CeCILL-C soumise au droit français et respectant les principes de diffusion des logiciels
 * libres. Vous pouvez utiliser, modifier et/ou redistribuer ce programme sous les conditions de la licence CeCILL telle que
 * diffusée par le CEA, le CNRS et l'INRIA sur le site "http://www.cecill.info".
 *
 * En contrepartie de l'accessibilité au code source et des droits de copie, de modification et de redistribution accordés par cette
 * licence, il n'est offert aux utilisateurs qu'une garantie limitée. Pour les mêmes raisons, seule une responsabilité restreinte
 * pèse sur l'auteur du programme, le titulaire des droits patrimoniaux et les concédants successifs.
 *
 * A cet égard l'attention de l'utilisateur est attirée sur les risques associés au chargement, à l'utilisation, à la modification
 * et/ou au développement et à la reproduction du logiciel par l'utilisateur étant donné sa spécificité de logiciel libre, qui peut
 * le rendre complexe à manipuler et qui le réserve donc à des développeurs et des professionnels avertis possédant des
 * connaissances informatiques approfondies. Les utilisateurs sont donc invités à charger et tester l'adéquation du logiciel à leurs
 * besoins dans des conditions permettant d'assurer la sécurité de leurs systèmes et ou de leurs données et, plus généralement, à
 * l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 *
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez pris connaissance de la licence CeCILL, et que vous en
 * avez accepté les termes.
 *******************************************************************************/
package org.adichatz.studio.xjc.editor.runnable;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.generator.common.GeneratorUtil.getFromGeneratorBundle;
import static org.adichatz.studio.util.StudioUtil.getFromStudioBundle;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.adichatz.common.ejb.AEntityCallback;
import org.adichatz.common.ejb.AEntityCallfore;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.IValidableController;
import org.adichatz.engine.controller.collection.ATabularController;
import org.adichatz.engine.controller.field.AColumnController;
import org.adichatz.engine.controller.field.HyperlinkController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.listener.AControlListener;
import org.adichatz.engine.listener.AEntityListener;
import org.adichatz.engine.listener.IEventType;
import org.adichatz.engine.widgets.supplement.AHyperlinkRunnable;
import org.adichatz.generator.wrapper.EntityTreeWrapper;
import org.adichatz.generator.wrapper.PluginEntityWrapper;
import org.adichatz.generator.xjc.EntityTree;
import org.adichatz.generator.xjc.ListenerTypeEnum;
import org.adichatz.generator.xjc.ModelPartType;
import org.adichatz.generator.xjc.PropertyFieldType;
import org.adichatz.generator.xjc.TabularType;
import org.adichatz.scenario.ScenarioResources;
import org.adichatz.studio.xjc.controller.IClassNameController;
import org.adichatz.studio.xjc.data.XjcBindingService;
import org.adichatz.studio.xjc.data.XjcEntity;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ImportRewrite;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.pde.internal.ui.PDEPlugin;
import org.eclipse.pde.internal.ui.editor.schema.NewClassCreationWizard;
import org.eclipse.pde.internal.ui.util.SWTUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.PartInitException;

// TODO: Auto-generated Javadoc
/**
 * The Class OpenClassEditorRunnable.
 */
@SuppressWarnings("restriction")
public class OpenClassEditorRunnable extends AHyperlinkRunnable {

	/** The java project. */
	private IJavaProject javaProject;

	/** The tree controller. */
	private ASetController treeController;

	@SuppressWarnings("rawtypes")
	private Class superClass;

	private AFieldController linkedController;

	private ScenarioResources scenarioResources;

	/**
	 * Instantiates a new open class editor runnable.
	 * 
	 * @param hyperlinkController
	 *            the hyperlink controller
	 */
	public OpenClassEditorRunnable(HyperlinkController hyperlinkController) {
		super(hyperlinkController);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		if (null == treeController)
			treeController = ((XjcEntity<?>) controller.getEntity()).getSetController();
		/**
		 * When linked controller is a ClassTextController<br>
		 * if element exists<br>
		 * - open the editor for class java<br>
		 * else<br>
		 * - propose to create new java class<br>
		 * 
		 */
		try {
			String value = ((IClassNameController) getLinkedController()).getCurrentClassName();
			if (null != value) {
				IJavaElement element = getJavaProject().findType(value.replace('$', '.'));
				if (null != element)
					JavaUI.openInEditor(element);
				else
					LogBroker.displayError(getFromStudioBundle("studio.search.class.error"),
							getFromStudioBundle("studio.search.class.error.message", value));
			} else {
				linkedController = getLinkedController();
				String superTypeName = ((IClassNameController) linkedController).getSuperTypeName();
				scenarioResources = ((IClassNameController) linkedController).getScenarioResources();

				final NewClassCreationWizard wizard = new NewClassCreationWizard(getJavaProject().getProject(), false, "");
				WizardDialog dialog = new WizardDialog(PDEPlugin.getActiveWorkbenchShell(), wizard);
				dialog.create();
				NewClassWizardPage page = ((NewClassWizardPage) wizard.getPages()[0]);

				if (null != superTypeName) {
					superClass = ReflectionTools.getClazz(superTypeName);
					if (superClass.isInterface()) {
						page.addSuperInterface(superTypeName);
					} else
						page.setSuperClass(superTypeName, true);

					String packageName;
					if ("callbackClassNames".equals(linkedController.getProperty())
							|| "callforeClassNames".equals(linkedController.getProperty())) {
						ModelPartType modelPart = scenarioResources.getGenerationScenario().getModelPart();
						if (null == modelPart)
							throw new RuntimeException(getFromGeneratorBundle("scenario.missing.modelPart"));
						packageName = modelPart.getModelPackageName();
						IPackageFragmentRoot srcFolder = getJavaProject()
								.getPackageFragmentRoot("/".concat(javaProject.getElementName()).concat("/src"));
						page.setPackageFragment(srcFolder.getPackageFragment(packageName.concat(".callback")), true);
						EntityTreeWrapper entity = (EntityTreeWrapper) linkedController.getEntity().getBean();
						String entityId = EngineTools.getEntityId(EngineTools.getInstanceKeys(entity.getEntityURI())[2]);
						page.setTypeName(
								EngineTools.upperCaseFirstLetter(entityId).concat(
										"callbackClassNames".equals(linkedController.getProperty()) ? "Callback" : "Callfore"),
								true);
						page.setPackageFragmentRoot(srcFolder, true);
					} else {
						IPackageFragmentRoot gencodeSrcFolder = getJavaProject()
								.getPackageFragmentRoot("/".concat(javaProject.getElementName()).concat("/resources/gencode/src"));
						String gencodePackage = scenarioResources.getGencodePath().getGencodePackage();
						packageName = gencodePackage.concat(".custom");
						page.setPackageFragment(gencodeSrcFolder.getPackageFragment(packageName), true);
						page.setPackageFragmentRoot(gencodeSrcFolder, true);
					}
					SWTUtil.setDialogSize(dialog, 400, 500);

					if (dialog.open() == Window.OK) {
						value = wizard.getQualifiedName();
						((IClassNameController) linkedController).modifyValues(value);

						addContructor2CombinedClass(wizard.getQualifiedName().replace('$', '.'));
						// open Java editor in a separated thread to force UI reflow in current editor
						Display.getCurrent().asyncExec(() -> {
							try {
								JavaUI.openInEditor(getJavaProject().findType(wizard.getQualifiedName()));
							} catch (PartInitException | JavaModelException e) {
								logError(e);
							}
						});
					}
				}
			}
		} catch (CoreException e) {
			logError(e);
		}
	}

	/**
	 * Adds the contructor2 combined class.
	 * 
	 * @param sourceName
	 *            the source name
	 * @param combinedClass
	 *            the combined class
	 * @throws JavaModelException
	 *             the java model exception
	 * @throws CoreException
	 *             the core exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addContructor2CombinedClass(String sourceName) throws JavaModelException, CoreException {
		final SourceType sourceType = (SourceType) getJavaProject().findType(sourceName);
		if (null != sourceType) {
			/* Instantiates a document for the PersistenceManager bean. */
			ICompilationUnit compilationUnit = sourceType.getCompilationUnit();
			final Document document = new Document(compilationUnit.getSource());

			ASTParser parser = ASTParser.newParser(AST.JLS9);
			parser.setSource(document.get().toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);

			/* Creates the AST for the document and 'visits' id to determine */
			final CompilationUnit astRoot = (CompilationUnit) parser.createAST(null);

			final AST ast = astRoot.getAST();
			List types = astRoot.types();
			TypeDeclaration classType = (TypeDeclaration) types.get(0);

			ImportRewrite importRewrite = ImportRewrite.create(compilationUnit, true);
			ASTRewrite rewriter = ASTRewrite.create(ast);
			MethodDeclaration methodConstructor = ast.newMethodDeclaration();
			methodConstructor.setConstructor(true);
			methodConstructor.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
			methodConstructor.setName(ast.newSimpleName(sourceType.getElementName()));

			Block block = ast.newBlock();
			methodConstructor.setBody(block);
			FieldDeclaration fieldDeclaration = null;
			boolean insert = true;

			if (superClass.isInterface()) {
				VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
				vdf.setName(ast.newSimpleName("controller"));
				fieldDeclaration = ast.newFieldDeclaration(vdf);
				fieldDeclaration.setType(ast.newSimpleType(ast.newSimpleName("AWidgetController")));
				fieldDeclaration.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PRIVATE_KEYWORD));
				classType.bodyDeclarations().add(fieldDeclaration);

				SingleVariableDeclaration parentControllerVariable = ast.newSingleVariableDeclaration();
				parentControllerVariable.setType(ast.newSimpleType(ast.newSimpleName("AWidgetController")));
				parentControllerVariable.setName(ast.newSimpleName("controller"));
				methodConstructor.parameters().add(parentControllerVariable);
				importRewrite.addImport(AWidgetController.class.getName());

				// this.controller = controller;
				Assignment assignment = ast.newAssignment();
				assignment.setOperator(Assignment.Operator.ASSIGN);

				FieldAccess fa = ast.newFieldAccess();
				fa.setExpression(ast.newThisExpression());
				fa.setName(ast.newSimpleName("controller"));
				assignment.setLeftHandSide(fa);
				assignment.setRightHandSide(ast.newSimpleName("controller"));

				block.statements().add(ast.newExpressionStatement(assignment));
			} else {
				if ("controllerClassName".equals(linkedController.getProperty())) {
					SuperConstructorInvocation superConstructorInvocation = ast.newSuperConstructorInvocation();

					SingleVariableDeclaration idVariable = ast.newSingleVariableDeclaration();
					idVariable.setType(ast.newSimpleType(ast.newSimpleName("String")));
					idVariable.setName(ast.newSimpleName("id"));
					methodConstructor.parameters().add(idVariable);
					superConstructorInvocation.arguments().add(ast.newSimpleName("id"));
					superConstructorInvocation.arguments().add(ast.newSimpleName("parentController"));

					if (ReflectionTools.hasSuperClass(superClass, AColumnController.class)) {
						XjcEntity<?> xjcEntity = ((XjcEntity<?>) linkedController.getEntity()).getTreeElement().getParentElement()
								.getEntity();
						Object element = xjcEntity.getBean();
						PluginEntityWrapper pluginEntity;
						if (element instanceof TabularType) {
							TabularType tabular = (TabularType) ((XjcEntity<?>) linkedController.getEntity()).getTreeElement()
									.getParentElement().getEntity().getBean();
							pluginEntity = scenarioResources.getPluginEntityWrapper(tabular.getEntityURI());
						} else if (element instanceof PropertyFieldType) {
							pluginEntity = scenarioResources.getPluginEntityWrapper(
									((EntityTree) xjcEntity.getTreeElement().getParentElement().getEntity().getBean())
											.getEntityURI());
						} else
							throw new RuntimeException("");
						Class<?> entityClass = pluginEntity.getBeanClass();
						importRewrite.addImport(entityClass.getCanonicalName());
						ParameterizedType parameterizedType = ast
								.newParameterizedType(ast.newSimpleType(ast.newSimpleName("TabularController")));
						parameterizedType.typeArguments().add(ast.newSimpleType(ast.newSimpleName(entityClass.getSimpleName())));

						SingleVariableDeclaration parentControllerVariable = ast.newSingleVariableDeclaration();
						parentControllerVariable.setType(parameterizedType);
						parentControllerVariable.setName(ast.newSimpleName("parentController"));
						methodConstructor.parameters().add(parentControllerVariable);
						importRewrite.addImport(ATabularController.class.getName());
					} else {
						SingleVariableDeclaration parentControllerVariable = ast.newSingleVariableDeclaration();
						parentControllerVariable.setType(ast.newSimpleType(ast.newSimpleName("IContainerController")));
						parentControllerVariable.setName(ast.newSimpleName("parentController"));
						methodConstructor.parameters().add(parentControllerVariable);
						importRewrite.addImport(IContainerController.class.getName());

						superConstructorInvocation.arguments().add(ast.newSimpleName("genCode"));

						SingleVariableDeclaration gencodeVariable = ast.newSingleVariableDeclaration();
						gencodeVariable.setType(ast.newSimpleType(ast.newSimpleName("ControllerCore")));
						gencodeVariable.setName(ast.newSimpleName("genCode"));
						methodConstructor.parameters().add(gencodeVariable);
					}

					importRewrite.addImport(ControllerCore.class.getName());
					block.statements().add(superConstructorInvocation);
				} else if ("validatorClassName".equals(linkedController.getProperty())) {
					SuperConstructorInvocation superConstructorInvocation = ast.newSuperConstructorInvocation();

					SingleVariableDeclaration parentControllerVariable = ast.newSingleVariableDeclaration();
					parentControllerVariable.setType(ast.newSimpleType(ast.newSimpleName("IValidableController")));
					parentControllerVariable.setName(ast.newSimpleName("triggeringController"));
					methodConstructor.parameters().add(parentControllerVariable);
					importRewrite.addImport(IValidableController.class.getName());

					SingleVariableDeclaration keyVariable = ast.newSingleVariableDeclaration();
					keyVariable.setType(ast.newSimpleType(ast.newSimpleName("String")));
					keyVariable.setName(ast.newSimpleName("key"));
					methodConstructor.parameters().add(keyVariable);

					superConstructorInvocation.arguments().add(ast.newSimpleName("triggeringController"));
					superConstructorInvocation.arguments().add(ast.newSimpleName("key"));

					importRewrite.addImport(ControllerCore.class.getName());
					block.statements().add(superConstructorInvocation);
				} else if (AHyperlinkRunnable.class.getName().equals(superClass.getName())) {
					SingleVariableDeclaration parentControllerVariable = ast.newSingleVariableDeclaration();
					parentControllerVariable.setType(ast.newSimpleType(ast.newSimpleName("HyperlinkController")));
					parentControllerVariable.setName(ast.newSimpleName("hyperlinkController"));
					methodConstructor.parameters().add(parentControllerVariable);
					importRewrite.addImport(HyperlinkController.class.getName());

					SuperConstructorInvocation superConstructorInvocation = ast.newSuperConstructorInvocation();
					superConstructorInvocation.arguments().add(ast.newSimpleName("hyperlinkController"));

					block.statements().add(superConstructorInvocation);
				} else if (AControlListener.class.getName().equals(superClass.getName())) {
					SingleVariableDeclaration parentControllerVariable = ast.newSingleVariableDeclaration();
					parentControllerVariable.setType(ast.newSimpleType(ast.newSimpleName("AWidgetController")));
					parentControllerVariable.setName(ast.newSimpleName("controller"));
					methodConstructor.parameters().add(parentControllerVariable);
					importRewrite.addImport(AWidgetController.class.getName());

					ListenerTypeEnum listenerType = (ListenerTypeEnum) ((AFieldController) linkedController.getGenCode()
							.getFromRegister("listenerType")).getValue();

					SuperConstructorInvocation superConstructorInvocation = ast.newSuperConstructorInvocation();
					superConstructorInvocation.arguments().add(ast.newName(new String[] { "IEventType", listenerType.name() }));
					superConstructorInvocation.arguments().add(ast.newSimpleName("controller"));
					importRewrite.addImport(IEventType.class.getName());

					block.statements().add(superConstructorInvocation);
				} else if (AEntityListener.class.getName().equals(superClass.getName())) {
					SingleVariableDeclaration parentControllerVariable = ast.newSingleVariableDeclaration();
					parentControllerVariable.setType(ast.newSimpleType(ast.newSimpleName("AEntityManagerController")));
					parentControllerVariable.setName(ast.newSimpleName("controller"));
					methodConstructor.parameters().add(parentControllerVariable);
					importRewrite.addImport(AEntityManagerController.class.getName());

					ListenerTypeEnum listenerType = (ListenerTypeEnum) ((AFieldController) linkedController.getGenCode()
							.getFromRegister("listenerType")).getValue();

					SuperConstructorInvocation superConstructorInvocation = ast.newSuperConstructorInvocation();
					superConstructorInvocation.arguments().add(ast.newName(new String[] { "IEventType", listenerType.name() }));
					MethodInvocation methodInvocation = ast.newMethodInvocation();
					methodInvocation.setExpression(ast.newSimpleName("controller"));
					methodInvocation.setName(ast.newSimpleName("getBindingService"));
					superConstructorInvocation.arguments().add(methodInvocation);
					block.statements().add(superConstructorInvocation);

					importRewrite.addImport(IEventType.class.getName());

				} else if (AEntityCallback.class.getName().equals(superClass.getName())
						|| AEntityCallfore.class.getName().equals(superClass.getName())) {
					boolean isCallback = AEntityCallback.class.getName().equals(superClass.getName());
					TypeDeclaration newClassType = ast.newTypeDeclaration();
					newClassType.setInterface(false);
					newClassType.modifiers().add(ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD));
					newClassType.setName(ast.newSimpleName(classType.getName().getFullyQualifiedName()));

					EntityTreeWrapper entity = (EntityTreeWrapper) linkedController.getEntity().getBean();
					PluginEntityWrapper pluginEntity = scenarioResources.getPluginEntityWrapper(entity.getEntityURI());
					Class<?> entityClass = pluginEntity.getBeanClass();
					importRewrite.addImport(entityClass.getCanonicalName());

					String simpleName = isCallback ? AEntityCallback.class.getSimpleName() : AEntityCallfore.class.getSimpleName();
					ParameterizedType superClassType = ast.newParameterizedType(ast.newSimpleType(ast.newSimpleName(simpleName)));
					superClassType.typeArguments().add(ast.newSimpleType(ast.newSimpleName(entityClass.getSimpleName())));
					newClassType.setSuperclassType(superClassType);

					if (isCallback) {
						SingleVariableDeclaration parentControllerVariable = ast.newSingleVariableDeclaration();
						parentControllerVariable.setType(ast.newSimpleType(ast.newSimpleName("EntityManager")));
						parentControllerVariable.setName(ast.newSimpleName("entityManager"));
						methodConstructor.parameters().add(parentControllerVariable);
						importRewrite.addImport(EntityManager.class.getName());

						SuperConstructorInvocation superConstructorInvocation = ast.newSuperConstructorInvocation();
						superConstructorInvocation.arguments().add(ast.newSimpleName("entityManager"));
						block.statements().add(superConstructorInvocation);
					}

					newClassType.bodyDeclarations().add(methodConstructor);

					/* Rewrites method */
					rewriter.getListRewrite(astRoot, CompilationUnit.TYPES_PROPERTY).replace(classType, newClassType, null);
					insert = false;
				}
			}
			if (insert) {
				rewriter.getListRewrite(classType, TypeDeclaration.BODY_DECLARATIONS_PROPERTY).insertFirst(methodConstructor, null);
				if (null != fieldDeclaration)
					rewriter.getListRewrite(classType, TypeDeclaration.BODY_DECLARATIONS_PROPERTY).insertFirst(fieldDeclaration,
							null);
			}

			compilationUnit.reconcile(AST.JLS4, true, null, null);

			TextEdit textEdit = rewriter.rewriteAST(document, null);
			textEdit.addChild(importRewrite.rewriteImports(null));

			try {
				textEdit.apply(document);
				compilationUnit.getBuffer().setContents(document.get());
				if (compilationUnit.isWorkingCopy()) {
					compilationUnit.commitWorkingCopy(true, null);
				} else
					compilationUnit.save(new NullProgressMonitor(), true);
			} catch (MalformedTreeException | BadLocationException e) {
				logError(e);
			}

		}
	}

	/**
	 * Gets the java project.
	 * 
	 * @return the java project
	 */
	protected IJavaProject getJavaProject() {
		if (null == javaProject) {
			XjcBindingService bindingService = (XjcBindingService) controller.getBindingService();
			IProject project = bindingService.getEditor().getScenarioResources().getProject();
			javaProject = JavaCore.create(project);
		}
		return javaProject;
	}

}
