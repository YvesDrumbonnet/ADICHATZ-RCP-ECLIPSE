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
package org.adichatz.engine.e4.preference;

import static org.adichatz.engine.common.LogBroker.logError;

import java.util.ArrayList;
import java.util.List;

import org.adichatz.engine.data.GencodePath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.service.prefs.BackingStoreException;

// TODO: Auto-generated Javadoc
/**
 * The Class AdiPreferenceManager.
 * 
 * @author Yves Drumbonnet
 *
 */
public class AdiPreferenceManager {

	/** The nodes. */
	private static List<AdiPreferenceManager> nodes = new ArrayList<AdiPreferenceManager>();

	/**
	 * Gets the nodes.
	 * 
	 * @return the nodes
	 */
	public static List<AdiPreferenceManager> getNodes() {
		return nodes;
	}

	/**
	 * Inits the preference node.
	 * 
	 * @param preferenceManager
	 *            the preference manager
	 * @return true, if successful
	 */
	public static boolean initPreferenceNode(AdiPreferenceManager preferenceManager) {
		AdiPreferenceManager.getNodes().add(preferenceManager);
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(preferenceManager.getId());
		boolean exist = false;
		try {
			exist = preferences.parent().nodeExists(preferenceManager.getId());
		} catch (BackingStoreException e) {
			logError(e);
		}
		if (exist) {
			preferenceManager.injectValues();
			return true;
		} else {
			preferenceManager.initializeDefault();
			return false;
		}
	}

	/** The label text. */
	private String id, labelText;

	/** The label image descriptor. */
	private ImageDescriptor labelImageDescriptor;

	/** The label image. */
	private Image labelImage;

	/** The init default runnable. */
	private Runnable initDefaultRunnable;

	/** The inject values runnable. */
	private Runnable injectValuesRunnable;

	/** The preference page class name. */
	private String preferencePageClassName;

	/** The children. */
	private List<AdiPreferenceManager> children;

	/** The gencode path. */
	private GencodePath gencodePath;

	private IEclipseContext context;

	/**
	 * Instantiates a new adi preference manager.
	 *
	 * @param id
	 *            the id
	 * @param labelText
	 *            the label text
	 * @param labelImageDescriptor
	 *            the label image descriptor
	 * @param preferencePageClassName
	 *            the preference page class name
	 * @param injectValuesRunnable
	 *            the inject values runnable
	 * @param initDefaultRunnable
	 *            the init default runnable
	 * @param gencodePath
	 *            the gencode path
	 * @param eclipseContext
	 *            the eclipse context
	 */
	public AdiPreferenceManager(String id, String labelText, ImageDescriptor labelImageDescriptor, String preferencePageClassName,
			Runnable injectValuesRunnable, Runnable initDefaultRunnable, GencodePath gencodePath, IEclipseContext context) {
		this.id = id;
		this.labelText = labelText;
		this.labelImageDescriptor = labelImageDescriptor;
		this.preferencePageClassName = preferencePageClassName;
		this.injectValuesRunnable = injectValuesRunnable;
		this.initDefaultRunnable = initDefaultRunnable;
		this.gencodePath = gencodePath;
		this.context = context;
	}

	/**
	 * Initialize default.
	 */
	public void initializeDefault() {
		if (null != initDefaultRunnable)
			initDefaultRunnable.run();
	}

	/**
	 * Inject values.
	 */
	public void injectValues() {
		if (null != injectValuesRunnable)
			injectValuesRunnable.run();
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the label text.
	 * 
	 * @return the label text
	 */
	public String getLabelText() {
		return labelText;
	}

	/**
	 * Gets the label image.
	 * 
	 * @return the label image
	 */
	public Image getLabelImage() {
		if (null == labelImage)
			labelImage = labelImageDescriptor.createImage();
		return labelImage;
	}

	/**
	 * Gets the preference page class name.
	 * 
	 * @return the preference page class name
	 */
	public String getPreferencePageClassName() {
		return preferencePageClassName;
	}

	/**
	 * Gets the eclipse preference.
	 * 
	 * @return the eclipse preference
	 */
	public IEclipsePreferences getEclipsePreference() {
		return InstanceScope.INSTANCE.getNode(id);
	}

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public IEclipseContext getContext() {
		return context;
	}

	/**
	 * Adds the child.
	 *
	 * @param id
	 *            the id
	 * @param labelText
	 *            the label text
	 * @param labelImageDescriptor
	 *            the label image descriptor
	 * @param preferencePageClassName
	 *            the preference page class name
	 * @param injectValuesRunnable
	 *            the inject values runnable
	 * @param initDefaultRunnable
	 *            the init default runnable
	 * @return the adi preference manager
	 */
	public AdiPreferenceManager addChild(String id, String labelText, ImageDescriptor labelImageDescriptor,
			String preferencePageClassName, Runnable injectValuesRunnable, Runnable initDefaultRunnable) {
		return addChild(id, labelText, labelImageDescriptor, preferencePageClassName, injectValuesRunnable, initDefaultRunnable,
				-1);
	}

	/**
	 * Adds the child.
	 * 
	 * @param id
	 *            the id
	 * @param labelText
	 *            the label text
	 * @param labelImageDescriptor
	 *            the label image descriptor
	 * @param preferencePageClassName
	 *            the preference page class name
	 * @param injectValuesRunnable
	 *            the inject values runnable
	 * @param initDefaultRunnable
	 *            the init default runnable
	 * @param rank
	 *            the rank
	 * @return the adi preference manager
	 */
	public AdiPreferenceManager addChild(String id, String labelText, ImageDescriptor labelImageDescriptor,
			String preferencePageClassName, Runnable injectValuesRunnable, Runnable initDefaultRunnable, int rank) {
		AdiPreferenceManager preferenceManager = new AdiPreferenceManager(id, labelText, labelImageDescriptor,
				preferencePageClassName, injectValuesRunnable, initDefaultRunnable, gencodePath, context);
		if (null == children)
			children = new ArrayList<AdiPreferenceManager>();
		if (-1 == rank)
			children.add(preferenceManager);
		else
			children.add(rank, preferenceManager);
		return preferenceManager;
	}

	/**
	 * Gets the gencode path.
	 * 
	 * @return the gencode path
	 */
	public GencodePath getGencodePath() {
		return gencodePath;
	}

	/**
	 * Gets the children.
	 * 
	 * @return the children
	 */
	public List<AdiPreferenceManager> getChildren() {
		return children;
	}
}
