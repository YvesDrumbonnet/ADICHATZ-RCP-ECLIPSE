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
package org.adichatz.generator.tree;

import java.util.HashMap;
import java.util.Map;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.plugin.PluginEntity;
import org.adichatz.engine.viewer.ATreeManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class AQueryManager.
 */
public abstract class AXjcTreeManager extends ATreeManager {

	/** The tree child map. */
	protected Map<String, ATreeChild> treeChildMap = new HashMap<String, ATreeChild>();

	/**
	 * Instantiates a new a tree manager.
	 * 
	 * @param pluginResources
	 *            the plugin resources
	 */
	public AXjcTreeManager(AdiPluginResources pluginResources) {
		super(pluginResources);
	}

	/**
	 * Gets the children.
	 * 
	 * @param parentElement
	 *            the parent element
	 * @return the children
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		return ((AXjcTreeElement) parentElement).getChildren();
	}

	/**
	 * Gets the text.
	 * 
	 * @param element
	 *            the element
	 * @return the text
	 */
	@Override
	public String getText(Object element) {
		return getTreeChild(((AXjcTreeElement) element).getElement()).getText((AXjcTreeElement) element);
	}

	/**
	 * Gets the image.
	 * 
	 * @param element
	 *            the element
	 * @return the image
	 */
	@Override
	public Image getImage(Object element) {
		return getTreeChild(((AXjcTreeElement) element).getElement()).getImage((AXjcTreeElement) element);
	}

	/**
	 * Gets the font.
	 * 
	 * @param element
	 *            the element
	 * @return the font
	 */
	@Override
	public Font getFont(Object element) {
		return getTreeChild(((AXjcTreeElement) element).getElement()).getFont((AXjcTreeElement) element);
	}

	/**
	 * Gets the foreground.
	 * 
	 * @param element
	 *            the element
	 * @return the foreground
	 */
	@Override
	public Color getForeground(Object element) {
		return getTreeChild(((AXjcTreeElement) element).getElement()).getForeground((AXjcTreeElement) element);
	}

	/**
	 * Gets the background.
	 * 
	 * @param element
	 *            the element
	 * @return the background
	 */
	@Override
	public Color getBackground(Object element) {
		return getTreeChild(((AXjcTreeElement) element).getElement()).getBackground((AXjcTreeElement) element);
	}

	/**
	 * Concat.
	 * 
	 * @param member1
	 *            the member1
	 * @param member2
	 *            the member2
	 * @return the string
	 */
	protected String concat(String member1, String member2) {
		return null == member2 ? member1 : member1.concat(": ").concat(member2);
	}

	protected String concatNvl(String... members) {
		StringBuffer resultSB = new StringBuffer();
		for (int i = 0; i < members.length; i++)
			if (null != members[i]) {
				if (i > 0)
					resultSB.append(" ");
				resultSB.append(members[i]);
			}
		return resultSB.toString();
	}

	/**
	 * Gets the tree child.
	 * 
	 * @param element
	 *            the element
	 * @return the tree child
	 */
	public ATreeChild getTreeChild(Object element) {
		PluginEntity pe = pluginResources.getPluginEntityTree().getPluginEntity(element.getClass());
		return treeChildMap.get(pe.getEntityURI());
	}

	/**
	 * Gets the tree child map.
	 *
	 * @return the tree child map
	 */
	public Map<String, ATreeChild> getTreeChildMap() {
		return treeChildMap;
	}
}
