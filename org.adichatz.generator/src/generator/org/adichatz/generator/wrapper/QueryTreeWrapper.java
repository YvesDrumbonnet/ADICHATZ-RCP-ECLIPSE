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
package org.adichatz.generator.wrapper;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.FieldTools;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.wrapper.ITreeWrapper;
import org.adichatz.generator.wrapper.internal.IEntityContainerWrapper;
import org.adichatz.generator.wrapper.internal.IJointure;
import org.adichatz.generator.xjc.JointureType;
import org.adichatz.generator.xjc.QueryTree;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryManagerTreeWrapper.
 * 
 * @author Yves Drumbonnet
 *
 */
public class QueryTreeWrapper extends QueryTree implements ITreeWrapper, IJointure, IEntityContainerWrapper {

	/** The plugin name. */
	private String pluginName;

	/** The tree id. */
	private String treeId;

	/** The sub package. */
	private String subPackage;

	/** The xml file. */
	private File xmlFile;

	private List<IJointure> allJointures;

	/** The plugin entity. */
	private PluginEntity pluginEntity;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.ITreeWrapper#getPluginName()
	 */
	@Override
	public String getPluginName() {
		return pluginName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.ITreeWrapper#setPluginName(java.lang.String)
	 */
	@Override
	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.ITreeWrapper#getTreeId()
	 */
	@Override
	public String getTreeId() {
		return treeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.ITreeWrapper#setTreeId(java.lang.String)
	 */
	@Override
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.ITreeWrapper#getSubPackage()
	 */
	@Override
	public String getSubPackage() {
		return subPackage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.engine.wrapper.ITreeWrapper#setSubPackage(java.lang.String)
	 */
	@Override
	public void setSubPackage(String subPackage) {
		this.subPackage = subPackage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.wrapper.tree.ITreeWrapper#getXmlFile()
	 */
	public File getXmlFile() {
		return xmlFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.adichatz.generator.wrapper.tree.ITreeWrapper#setXmlFile(java.io.File)
	 */
	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	@Override
	public String getJointureName() {
		return "query";
	}

	public void initJointureEntityURI() {
		if (null == getPluginEntity()) {
			AdiPluginResources pluginResources = AdichatzApplication.getPluginResources(pluginName);
			if (pluginResources.getPluginEntityTree().getPluginEntityURIMap().isEmpty())
				pluginResources.loadPluginEntities();
			setPluginEntity(pluginResources.getPluginEntity(entityURI));
			initJointureEntityURI(pluginResources, this);
		}
	}

	private void initJointureEntityURI(AdiPluginResources pluginResources, IJointure parentJointure) {
		for (JointureType jointure : parentJointure.getJointure()) {
			if (null != jointure.getFieldName() && null != parentJointure.getPluginEntity()) {
				Method getGetMethod = FieldTools.getGetMethod(parentJointure.getPluginEntity().getEntityMetaModel().getBeanClass(),
						jointure.getFieldName(), true);
				jointure.setPluginEntity(pluginResources.getPluginEntityTree().getPluginEntity(getGetMethod.getReturnType()));
				jointure.setEntityURI(jointure.getPluginEntity().getEntityURI());
				initJointureEntityURI(pluginResources, (IJointure) jointure);
			}
		}
	}

	public void clearPluginEntity(IJointure parentJointure) {
		parentJointure.setPluginEntity(null);
		for (JointureType jointure : parentJointure.getJointure()) {
			clearPluginEntity((IJointure) jointure);
		}
	}

	public List<IJointure> getAllJointures() {
		if (allJointures == null) {
			allJointures = new ArrayList<IJointure>();
			allJointures.add(this);
			addJointures(this);
		}
		return this.allJointures;
	}

	private void addJointures(IJointure parentJointures) {
		for (JointureType jointure : parentJointures.getJointure()) {
			allJointures.add((IJointure) jointure);
			addJointures((IJointure) jointure);
		}
	}

	/**
	 * Gets the plugin entity.
	 * 
	 * @return the plugin entity
	 */
	public PluginEntity getPluginEntity() {
		return pluginEntity;
	}

	/**
	 * Sets the plugin entity.
	 * 
	 * @param pluginEntity
	 *            the new plugin entity
	 */
	public void setPluginEntity(PluginEntity pluginEntity) {
		this.pluginEntity = pluginEntity;
	}
}
