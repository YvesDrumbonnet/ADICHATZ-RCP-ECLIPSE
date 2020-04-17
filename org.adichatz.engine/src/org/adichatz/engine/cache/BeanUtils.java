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
package org.adichatz.engine.cache;

import static org.adichatz.engine.common.LogBroker.logError;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.adichatz.engine.common.ReflectionTools;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanUtils.
 * 
 * Utilities aroung bean
 * 
 * @author Yves Drumbonnet
 * 
 */
public class BeanUtils {

	@SuppressWarnings("unchecked")
	public static <T> T clone(T source) {
		T target = (T) ReflectionTools.instantiateClass(source.getClass());
		copyFields(source, target);
		return target;
	}

	public static <T> void copyField(Field field, T source, T target) {
		boolean accessible = field.canAccess(source);
		field.setAccessible(true);
		try {
			field.set(target, field.get(source));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logError(e);
		}
		field.setAccessible(accessible);
	}

	public static <T> void copyFields(T source, T target) {
		for (Field field : source.getClass().getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()))
				copyField(field, source, target);
		}
	}

	/**
	 * Copy fields.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 */
	public static <T> void copyExtendedFields(T source, T target) {
		Class<?> superClass = source.getClass();
		while (!superClass.equals(Object.class)) {
			for (Field field : superClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(XmlAttribute.class)
						|| ("java.lang.String".equals(field.getType().getName()) && field.isAnnotationPresent(XmlElement.class)))
					BeanUtils.copyField(field, source, target);
			}
			superClass = superClass.getSuperclass();
		}
	}

}
