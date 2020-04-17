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
package org.adichatz.studio.xjc.editor.action;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Policy;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorTreeSelectionTransfer.
 * 
 * Directly inspired from org.eclipse.jface.util.LocalSelectionTransfer
 */
public class XjcSelectionTransfer extends ByteArrayTransfer {
	// First attempt to create a UUID for the type name to make sure that
	// different Eclipse applications use different "types" of
	// <code>EditorTreeSelectionTransfer</code>
	/** The Constant TYPE_NAME. */
	private static final String TYPE_NAME = "editor-tree-selection-transfer-format" + (new Long(System.currentTimeMillis())).toString(); //$NON-NLS-1$;

	/** The Constant TYPEID. */
	private static final int TYPEID = registerType(TYPE_NAME);

	/** The Constant INSTANCE. */
	private static final XjcSelectionTransfer INSTANCE = new XjcSelectionTransfer();

	/** The selection. */
	private ITreeSelection selection;

	/** The cut selection is copied. */
	private boolean cutSelectionIsCopied = true;

	/**
	 * Only the singleton instance of this class may be used.
	 */
	protected XjcSelectionTransfer() {
	}

	/**
	 * Returns the singleton.
	 * 
	 * @return the singleton
	 */
	public static XjcSelectionTransfer getTransfer() {
		return INSTANCE;
	}

	/**
	 * Gets the transfer.
	 * 
	 * @param selection
	 *            the selection
	 * @return the transfer
	 */
	public static XjcSelectionTransfer getTransfer(ITreeSelection selection) {
		INSTANCE.selection = selection;
		return INSTANCE;
	}

	/**
	 * Gets the selection.
	 * 
	 * @return the selection
	 */
	public ITreeSelection getSelection() {
		return selection;
	}

	/**
	 * Sets the selection.
	 * 
	 * @param selection
	 *            the new selection
	 */
	public void setSelection(ITreeSelection selection) {
		this.selection = selection;
	}

	/**
	 * Tests whether native drop data matches this transfer type.
	 * 
	 * @param result
	 *            result of converting the native drop data to Java
	 * @return true if the native drop data does not match this transfer type. false otherwise.
	 */
	private boolean isInvalidNativeType(Object result) {
		return !(result instanceof byte[]) || !TYPE_NAME.equals(new String((byte[]) result));
	}

	/**
	 * Returns the type id used to identify this transfer.
	 * 
	 * @return the type id used to identify this transfer.
	 */
	protected int[] getTypeIds() {
		return new int[] { TYPEID };
	}

	/**
	 * Returns the type name used to identify this transfer.
	 * 
	 * @return the type name used to identify this transfer.
	 */
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}

	/**
	 * Overrides org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(Object, TransferData). Only encode the transfer type name since
	 * the selection is read and written in the same process.
	 * 
	 * @param object
	 *            the object
	 * @param transferData
	 *            the transfer data
	 * @see org.eclipse.swt.dnd.ByteArrayTransfer#javaToNative(java.lang.Object, org.eclipse.swt.dnd.TransferData)
	 */
	public void javaToNative(Object object, TransferData transferData) {
		byte[] check = TYPE_NAME.getBytes();
		super.javaToNative(check, transferData);
	}

	/**
	 * Overrides org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(TransferData). Test if the native drop data matches this
	 * transfer type.
	 * 
	 * @param transferData
	 *            the transfer data
	 * @return the object
	 * @see org.eclipse.swt.dnd.ByteArrayTransfer#nativeToJava(TransferData)
	 */
	public Object nativeToJava(TransferData transferData) {
		Object result = super.nativeToJava(transferData);
		if (isInvalidNativeType(result)) {
			Policy.getLog().log(
					new Status(IStatus.ERROR, Policy.JFACE, IStatus.ERROR, JFaceResources
							.getString("LocalSelectionTransfer.errorMessage"), null)); //$NON-NLS-1$
		}
		return selection;
	}

	/**
	 * Checks if is cut selection is copied.
	 * 
	 * @return true, if is cut selection is copied
	 */
	public boolean isCutSelectionIsCopied() {
		return cutSelectionIsCopied;
	}

	/**
	 * Sets the cut selection is copied.
	 * 
	 * @param cutSelectionIsCopied
	 *            the new cut selection is copied
	 */
	public void setCutSelectionIsCopied(boolean cutSelectionIsCopied) {
		this.cutSelectionIsCopied = cutSelectionIsCopied;
	}

}
