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
package org.adichatz.permission;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * The Class Session.
 *
 * @author Yves Drumbonnet
 */
public class Session implements Serializable {
	private static final long serialVersionUID = -7844353789635909570L;

	private static PermissionDomain permissionDomain = PermissionDomain.getInstance();

	private String username;

	/** The uuid. */
	private UUID uuid;

	private Set<String> roles;

	private Map<String, Set<String>> permissionsMap;

	private Map<String, Set<String>> denialsMap;

	/**
	 * Instantiates a new session.
	 * 
	 * @param username the username
	 * @param roles    the roles
	 */
	public Session(String username) {
		uuid = UUID.randomUUID();
		this.username = username;
		roles = permissionDomain.getUsers().get(username);
		if (null == roles)
			roles = Collections.<String> emptySet();
		permissionsMap = new HashMap<>();
		denialsMap = new HashMap<>();
		for (String role : roles) {
			Map<String, Set<String>> permissions = permissionDomain.getRoles().get(role);
			if (null != permissions)
				permissionsMap.putAll(permissions);
			Map<String, Set<String>> denials = permissionDomain.getDenials().get(role);
			if (null != denials)
				denialsMap.putAll(denials);
		}
	}

	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the uuid.
	 * 
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Session)
			return uuid.equals(((Session) obj).uuid);
		return false;
	}

	public boolean hasRole(String roleParam) {
		for (String role : roles)
			if (role.equals(roleParam))
				return true;
		return false;
	}

	public boolean isPermitted(String permission) {
		if (null == permission || 0 == permission.length() || null != permissionsMap.get("*"))
			return true;
		int index = permission.indexOf(PermissionTools.PART_DIVIDER_TOKEN);
		String item;
		String subPerm;
		if (-1 == index) {
			item = permission;
			subPerm = PermissionTools.WILDCARD_TOKEN;
		} else {
			item = permission.substring(0, index).trim();
			subPerm = permission.substring(index + 1).trim();
		}
		Set<String> userSubPerms = permissionsMap.get(item);
		if (null == userSubPerms) // Item does not exists in '.ini' file
			return false;
		for (String userSubPerm : userSubPerms) {
			if (PermissionTools.WILDCARD_TOKEN.equals(userSubPerm) || subPerm.equals(userSubPerm))
				return true;
		}
		return false;
	}

	public boolean isDenied(String denial) {
		if (null == denial || 0 == denial.length() || null != denialsMap.get("*"))
			return true;
		int index = denial.indexOf(PermissionTools.PART_DIVIDER_TOKEN);
		String item;
		String subPerm;
		if (-1 == index) {
			item = denial.substring(0, index - 1).trim();
			subPerm = PermissionTools.WILDCARD_TOKEN;
		} else {
			item = denial.substring(0, index - 1).trim();
			subPerm = denial.substring(index + 1).trim();
		}
		Set<String> userSubDenials = denialsMap.get(item);
		for (String userSubDenial : userSubDenials)
			if (PermissionTools.WILDCARD_TOKEN.equals(userSubDenial) || subPerm.equals(userSubDenial))
				return true;
		return false;
	}
}
