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
package org.adichatz.jpa.query;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.BeanUtils;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.collection.ContainerController;
import org.adichatz.engine.validation.FormBindingService;
import org.adichatz.jpa.wrapper.QueryPaginationWrapper;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.adichatz.jpa.wrapper.QueryPreferenceWrapper;
import org.adichatz.jpa.xjc.QueryOpenClauseType;
import org.adichatz.jpa.xjc.QueryParameterType;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryToolBindingService.
 */
public class QueryToolBindingService extends FormBindingService {

	/** The query tool input. */
	QueryToolInput<?> queryToolInput;

	/**
	 * Instantiates a new query part binding service.
	 * 
	 * @param boundedController
	 *            the bounded controller
	 * @param queryToolInput
	 *            the query tool input
	 */
	public QueryToolBindingService(ContainerController containerController, QueryToolInput<?> queryToolInput) {
		super(containerController);
		this.queryToolInput = queryToolInput;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.validation.ABindingService#refreshEntities(org.adichatz.engine.cache.IEntity<?>[])
	 */
	public boolean refreshEntities(IEntity<?>... entities) {
		for (IEntity<?> entity : entities) {
			if (entity.getStatus() == IEntityConstants.PERSIST)
				entity.removeFromCache();
			else {
				QueryPreferenceWrapper<?> initialQueryPreference = queryToolInput.getQueryManager().getQueryPreferenceManager()
						.getInitialQueryPreference();
				if (entity.getBean() instanceof QueryParameterWrapper) {
					QueryParameterWrapper columnParameter = (QueryParameterWrapper) entity.getBean();
					for (QueryParameterType originalParameter : initialQueryPreference.getParameter()) {
						if (originalParameter.getId().equals(columnParameter.getId())) {
							BeanUtils.copyExtendedFields(originalParameter, columnParameter);
							entity.getBeanInterceptor().adi$FirePropertiesEntity(columnParameter);
							break;
						} // END if
					} // END for (QueryColumnParameterType originParameter :...
				} // END if (entity.getBean() instanceof ColumnParameter)
				else if (entity.getBean() instanceof QueryOpenClauseType) {
					QueryOpenClauseType openClause = (QueryOpenClauseType) entity.getBean();
					if (!openClause.getParameter().isEmpty()) {
						openClause.getParameter().clear();
						for (QueryOpenClauseType original : initialQueryPreference.getOpenClause()) {
							if (original.getTitle().equals(openClause.getTitle())) {
								openClause.setClause(original.getClause());
								for (QueryParameterType originalParameter : original.getParameter())
									openClause.getParameter()
											.add((QueryParameterType) EngineTools.cloneSerializable(originalParameter));
								break;
							}
						}

					}
				} else if (entity.getBean() instanceof QueryPaginationWrapper) {
					entity.getBeanInterceptor().adi$FirePropertiesEntity(initialQueryPreference.getPagination());
				}
				entity.putStatus(IEntityConstants.RETRIEVE, true);
				entity.lock(false, this);
			} // END else
		} // END for (IEntity<?> entity : entities)
		return false;
	};

	/**
	 * Gets the query tool input.
	 * 
	 * @return the query tool input
	 */
	public QueryToolInput<?> getQueryToolInput() {
		return queryToolInput;
	}
}
