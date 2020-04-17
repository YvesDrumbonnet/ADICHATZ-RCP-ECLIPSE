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
package org.adichatz.jpa.tabular;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.jpa.extra.JPAUtil.getFromJpaBundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.IContainerController;
import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.plugin.AdiContext;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.jpa.wrapper.QueryParameterWrapper;
import org.eclipse.swt.widgets.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class ParameterFieldManager.
 * 
 * @author Yves Drumbonnet
 * 
 */
public class ParameterFieldManager {
	/** The value fld ctlr. */
	private AFieldController valueFldCtlr;

	/** The detail class. */
	private Class<?> detailClass;

	/** The detail core. */
	private ControllerCore detailCore;

	/** The create method. */
	protected Method createMethod;

	/** The plugin resources. */
	protected AdiPluginResources pluginResources;

	/** The parent controller. */
	private IContainerController parentController;

	/** The query parameter. */
	private QueryParameterWrapper queryParameter;

	/** The plugin entity. */
	private PluginEntity<?> pluginEntity;

	/**
	 * Instantiates a new parameter field manager.
	 * 
	 * @param queryParameter
	 *            the query parameter
	 * @param pluginResources
	 *            the plugin resources
	 * @param pluginEntity
	 *            the plugin entity
	 * @param parentController
	 *            the parent controller
	 */
	public ParameterFieldManager(QueryParameterWrapper queryParameter, AdiPluginResources pluginResources,
			PluginEntity<?> pluginEntity, IContainerController parentController) {
		this.queryParameter = queryParameter;
		this.parentController = parentController;
		this.pluginResources = pluginResources;
		this.pluginEntity = pluginEntity;
	}

	/**
	 * New field controller.
	 * 
	 * @return the a field controller
	 */
	public AFieldController newFieldController() {
		if (null == detailCore || null == detailCore.getController()
				|| ((Widget) detailCore.getController().getControl()).isDisposed())
			createMethod = null;
		if (null == createMethod) {
			getCreateMethod();
			detailCore = (ControllerCore) pluginResources.getGencodePath().instantiateClass(detailClass,
					new Class[] { AdiContext.class, IContainerController.class }, new Object[] { null, parentController });
		}

		if (null != createMethod) {
			try {
				valueFldCtlr = (AFieldController) createMethod.invoke(detailCore,
						null == parentController ? null : parentController.getGenCode());
				return valueFldCtlr;
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				logError(e);
			}
		}
		return null;
	}

	/**
	 * Gets the creates the method.
	 * 
	 * @return the creates the method
	 */
	public Method getCreateMethod() {
		String methodName;
		AdiPluginResources parameterPR;
		String className;
		if (null == queryParameter.getExpressionMethodURI()) {
			methodName = "create".concat(EngineTools.upperCaseFirstLetter(queryParameter.getProperty()));
			String[] instanceKey = EngineTools.getInstanceKeys(pluginEntity.getAdiResourceUriMap().get("DETAIL"));
			className = pluginResources.getGencodePath().getClassName(instanceKey[2], instanceKey[1]);
			parameterPR = pluginResources;
		} else {
			String[] methodKeys = EngineTools.getInstanceKeys(queryParameter.getExpressionMethodURI());
			methodName = methodKeys[2];
			className = methodKeys[1];
			parameterPR = AdichatzApplication.getPluginResources(methodKeys[0]);
		}
		detailClass = parameterPR.getGencodePath().getClazz(className);

		Class<?> clazz = parameterPR.getGencodePath().getClazz(className);
		try {
			createMethod = clazz.getMethod(methodName, new Class[] { ControllerCore.class });
		} catch (NoSuchMethodException | SecurityException e) {
			logError(getFromJpaBundle("parameter.method.missing", queryParameter.getProperty(), className, methodName));
		}
		return createMethod;
	}

	/**
	 * Gets the value Field Controller
	 * 
	 * @return the value Field Controller
	 */
	public AFieldController getValueFldCtlr() {
		return valueFldCtlr;
	}

	/**
	 * Gets the parent controller.
	 * 
	 * @return the parent controller
	 */
	public IContainerController getParentController() {
		return parentController;
	}
}
