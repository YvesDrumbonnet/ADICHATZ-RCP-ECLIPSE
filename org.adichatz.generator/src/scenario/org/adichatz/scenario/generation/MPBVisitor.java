/*******************************************************************************
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
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
 * Copyright � Adichatz (2007-2020) - www.adichatz.org
 * 
 * yves@adichatz.org
 * 
 * Ce logiciel est un programme informatique servant � construire rapidement des
 * applications Eclipse RCP en utilisant JPA dans un contexte JSE ou JEE. 
 * 
 * Ce logiciel est r�gi par la licence CeCILL-C soumise au droit fran�ais et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffus�e par le CEA, le CNRS et l'INRIA 
 * sur le site "http://www.cecill.info".
 * 
 * En contrepartie de l'accessibilit� au code source et des droits de copie,
 * de modification et de redistribution accord�s par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
 * seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les conc�dants successifs.
 * 
 * A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
 * associ�s au chargement,  � l'utilisation,  � la modification et/ou au
 * d�veloppement et � la reproduction du logiciel par l'utilisateur �tant 
 * donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe � 
 * manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
 * avertis poss�dant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
 * logiciel � leurs besoins dans des conditions permettant d'assurer la
 * s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement, 
 * � l'utiliser et l'exploiter dans les m�mes conditions de s�curit�. 
 * 
 * Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez 
 * pris connaissance de la licence CeCILL, et que vous en avez accept� les
 * termes.
 *******************************************************************************/
package org.adichatz.scenario.generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

// TODO: Auto-generated Javadoc
/**
 * The Class MPBVisitor.
 * 
 * Visitor for an AST representing Persistence Manager bean class or JPA Connector class
 * 
 * The visitor is used to add or remove Inject annotation and initialize method.
 * 
 * It must determine 3 value
 * <ul>
 * <li>the TypeDeclaration of the PersistenceManager class</li>
 * <li>the ImportDeclaration for javax.inject.Inject if exists</li>
 * <li>the MethodDeclaration for initialize method if exists</li>
 * </ul>
 * 
 * @author Yves Drumbonnet
 *
 */
public class MPBVisitor extends ASTVisitor {

	/** The searched method. */
	private MethodDeclaration searchedMethod = null;

	/** The searched annotation. */
	private Annotation searchedAnnotation = null;

	/** The searched annotations. */
	private List<Annotation> searchedAnnotations = new ArrayList<Annotation>();

	/** The searched field declaration. */
	private FieldDeclaration searchedFieldDeclaration = null;

	/** The class body declaration. */
	private TypeDeclaration classBodyDeclaration = null;

	/** The imports. */
	private Set<ImportDeclaration> imports = new HashSet<ImportDeclaration>();

	/** The searched name. */
	private String searchedName;

	/**
	 * Instantiates a new mPB visitor.
	 *
	 * @param searchedName
	 *            the searched name
	 */
	public MPBVisitor(String searchedName) {
		super();
		this.searchedName = searchedName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	public boolean visit(MethodDeclaration node) {
		if (node.getName().getFullyQualifiedName().equals(searchedName)) {
			searchedMethod = node;
		}
		return super.visit(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		classBodyDeclaration = node;
		return super.visit(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ImportDeclaration)
	 */
	@Override
	public boolean visit(ImportDeclaration node) {
		imports.add(node);
		return super.visit(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.FieldDeclaration)
	 */
	@Override
	public boolean visit(FieldDeclaration node) {
		if (!node.fragments().isEmpty()) {
			Object fragment = node.fragments().get(0);
			if (fragment instanceof VariableDeclarationFragment
					&& ((VariableDeclarationFragment) fragment).getName().getFullyQualifiedName().equals(searchedName))
				searchedFieldDeclaration = node;
		}
		return super.visit(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MarkerAnnotation)
	 */
	@Override
	public boolean visit(MarkerAnnotation node) {
		if (node.getTypeName().getFullyQualifiedName().equals(searchedName))
			searchedAnnotations.add(node);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.NormalAnnotation)
	 */
	@Override
	public boolean visit(NormalAnnotation node) {
		if (node.getTypeName().getFullyQualifiedName().equals(searchedName))
			searchedAnnotations.add(node);
		return true;
	}

	/**
	 * Gets the searched method.
	 * 
	 * @return the searchedMethod
	 */
	public MethodDeclaration getSearchedMethod() {
		return searchedMethod;
	}

	/**
	 * Gets the searched annotations.
	 *
	 * @return the searched annotations
	 */
	public List<Annotation> getSearchedAnnotations() {
		return searchedAnnotations;
	}

	/**
	 * Gets the searched annotation.
	 * 
	 * @return the searched annotation
	 */
	public Annotation getSearchedAnnotation() {
		return searchedAnnotation;
	}

	/**
	 * Gets the persistence manager declaration.
	 * 
	 * @return the persistence manager declaration
	 */
	public TypeDeclaration getClassBodyDeclaration() {
		return classBodyDeclaration;
	}

	/**
	 * Gets the searched field declaration.
	 * 
	 * @return the searched field declaration
	 */
	public FieldDeclaration getSearchedFieldDeclaration() {
		return searchedFieldDeclaration;
	}

	/**
	 * Gets the imports.
	 *
	 * @param newImportDeclaration
	 *            the new import declaration
	 * @return the imports
	 */
	public boolean hasImports(ImportDeclaration newImportDeclaration) {
		for (ImportDeclaration importDeclaration : imports) {
			if (importDeclaration.getName().getFullyQualifiedName().equals(newImportDeclaration.getName().getFullyQualifiedName()))
				return true;
		}
		return false;
	}
}
