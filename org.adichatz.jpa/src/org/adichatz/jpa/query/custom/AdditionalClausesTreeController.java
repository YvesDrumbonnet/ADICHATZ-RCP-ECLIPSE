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
package org.adichatz.jpa.query.custom;

import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.controller.collection.ATreeController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.viewer.ATreeManager;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.adichatz.jpa.wrapper.RecentPreferenceWrapper;
import org.adichatz.jpa.xjc.QueryOpenClauseType;
import org.adichatz.jpa.xjc.QueryParameterType;
import org.adichatz.jpa.xjc.QueryPreferenceType;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
public class AdditionalClausesTreeController extends ATreeController {
	private static Image parameterImage = AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE,
			"IMG_PARAMETER.png");

	private static Image openClauseImage = AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE,
			"IMG_OPEN_CLAUSE.png");

	private static Image fullTextImage = AdichatzApplication.getInstance().getImage(EngineConstants.JPA_BUNDLE,
			"IMG_FULL_TEXT.png");

	/**
	 * Instantiates a new xjc tree controller.
	 * 
	 * @param id               the id
	 * @param parentController the parent controller
	 * @param genCode          the gen code
	 * @param pluginResources  the plugin resources
	 */
	public AdditionalClausesTreeController(String id, IContainerController parentController, ControllerCore genCode) {
		super(id, parentController, genCode);
	}

	@Override
	public void createControl() {
		super.createControl();
		viewer = new TreeViewer(tree);
		treeManager = new ATreeManager(getPluginResources()) {
			@Override
			public Object[] getChildren(Object parentElement) {
				List<Object> children = new ArrayList<Object>();
				if (parentElement instanceof RecentPreferenceWrapper) {
					RecentPreferenceWrapper<?> recentPreference = (RecentPreferenceWrapper<?>) parentElement;
					QueryPreferenceType queryPreference = recentPreference.getPreferenceTree().getQueryPreference();
					for (QueryParameterType columnParameter : queryPreference.getParameter()) {
						Boolean valid = ((QueryParameterWrapper) columnParameter).isMergeValid();
						if (columnParameter.isVisible() && null != valid && valid) {
							children.add(columnParameter);
						}
					}
					for (QueryOpenClauseType openClause : queryPreference.getOpenClause()) {
						if (openClause.isValid()) {
							children.add(openClause);
						}
					}
					if (!EngineTools.isEmpty(queryPreference.getFullTextClause()))
						children.add(queryPreference.getFullTextClause());
					for (QueryOpenClauseType openClause : queryPreference.getOpenClause()) {
						if (EngineConstants.PARENT_BEAN_PARAM.equals(openClause.getTitle()))
							return new String[] { openClause.getClause() };
						else
							for (QueryParameterType openParameter : openClause.getParameter())
								children.add(openParameter);
					}
				}
				return children.toArray();
			}

			@Override
			public String getText(Object element) {
				if (element instanceof QueryParameterType) {
					QueryParameterType parameter = (QueryParameterType) element;
					StringBuffer textSB = new StringBuffer();
					if (null != parameter.getPrompt())
						textSB.append(EngineTools.getMessage(parameter.getPrompt())).append(" ");
					if (null != parameter.getOperator())
						textSB.append(parameter.getOperator());
					if (null != parameter.getColumnText())
						textSB.append(" ").append(parameter.getColumnText());
					if ("between".equals(parameter.getOperator())) {
						textSB.append(" and ").append(parameter.getSecondColumnText());
					}
					return textSB.toString();
				} else if (element instanceof QueryOpenClauseType) {
					String title = ((QueryOpenClauseType) element).getTitle();
					if (EngineConstants.PARENT_BEAN_PARAM.equals(title))
						return getFromJpaBundle("query.parent.child.relationship");
					return title;
				} else if (element instanceof String) {
					return (String) element;
				}
				return null;
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof QueryParameterType) {
					return parameterImage;
				} else if (element instanceof QueryOpenClauseType)
					return openClauseImage;
				else if (element instanceof String)
					return fullTextImage;
				return null;
			}
		};
		viewer.setLabelProvider(treeManager.getTreeLabelProvider());
		viewer.setContentProvider(treeManager.getTreeContentProvider());
	}

	@Override
	public void synchronize() {
		setRootElement(getEntity().getBean());
		super.synchronize();
		refresh();
	}
}
