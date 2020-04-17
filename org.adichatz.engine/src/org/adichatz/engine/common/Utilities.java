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
package org.adichatz.engine.common;

import static org.adichatz.engine.common.EngineTools.getFromEngineBundle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adichatz.common.ejb.util.IEntityConstants;
import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.cache.LazyFetchManager;
import org.adichatz.engine.cache.LocalLazyNode;
import org.adichatz.engine.controller.ACollectionController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.ICollectionController;
import org.adichatz.engine.model.AEntityMetaModel;
import org.adichatz.engine.validation.ABindingService;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionUtility.
 * 
 * Contains utilities (static methods) used by Adichatz Actions
 * 
 * @author Yves Drumbonnet
 * 
 */
public class Utilities {

	/** The Excluded classes from stack trace. */
	private static Set<String> EXCLUDED_CLASSES = new HashSet<String>();
	static {
		EXCLUDED_CLASSES.add(Thread.class.getName());
		EXCLUDED_CLASSES.add(Utilities.class.getName());
	}

	/**
	 * Gets the first child controller.
	 * 
	 * @param parentController
	 *            the parent controller
	 * @param childClass
	 *            the child class
	 * 
	 * @return the first child controller
	 */
	public static AWidgetController getFirstChildController(ICollectionController parentController, Class<?> childClass) {
		for (AWidgetController controller : parentController.getChildControllers()) {
			if (childClass.isInterface()) {
				if (ReflectionTools.hasInterface(controller.getClass(), childClass))
					return controller;
			} else if (ReflectionTools.hasSuperClass(controller.getClass(), childClass))
				return controller;

			if (controller instanceof ICollectionController) {
				AWidgetController returnController = getFirstChildController((ICollectionController) controller, childClass);
				if (null != returnController)
					return returnController;
			}
		}
		return null;
	}

	public static List<AWidgetController> getChildControllers(ICollectionController parentController, Class<?> childClass) {
		List<AWidgetController> controllers = new ArrayList<>();
		addChildControllers(controllers, parentController, childClass);
		return controllers;
	}

	public static void addChildControllers(List<AWidgetController> controllers, ICollectionController parentController,
			Class<?> childClass) {
		for (AWidgetController controller : parentController.getChildControllers()) {
			if (childClass.isInterface()) {
				if (ReflectionTools.hasInterface(controller.getClass(), childClass))
					controllers.add(controller);
			} else if (ReflectionTools.hasSuperClass(controller.getClass(), childClass))
				controllers.add(controller);

			if (controller instanceof ICollectionController) {
				addChildControllers(controllers, (ICollectionController) controller, childClass);
			}
		}
	}

	/**
	 * Equals bean.
	 *
	 * @param entityMM the entity MM
	 * @param bean1 the bean 1
	 * @param bean2 the bean 2
	 * @return true, if successful
	 */
	public static final boolean equalsBean(AEntityMetaModel<?> entityMM, final Object bean1, final Object bean2) {
		if (null == bean1)
			return null == bean2;
		else if (null == bean2)
			return false;
		return entityMM.getId(bean1).equals(entityMM.getId(bean2));
	}

	/**
	 * Checks whether the two objects are <code>null</code> -- allowing for <code>null</code>.
	 * 
	 * @param left
	 *            The left object to compare; may be <code>null</code>.
	 * @param right
	 *            The right object to compare; may be <code>null</code>.
	 * 
	 * @return <code>true</code> if the two objects are equivalent; <code>false</code> otherwise.
	 */
	public static final boolean equals(final Object left, final Object right) {
		if (null == left)
			return null == right;
		else if (null == right)
			return false;
		if (right instanceof Collection && left instanceof Collection)
			return true;
		if (left instanceof byte[]) {
			if (!(right instanceof byte[]))
				return false;
			byte[] letfBytes = (byte[]) left;
			byte[] rightBytes = (byte[]) right;
			if (letfBytes.length != rightBytes.length)
				return false;
			for (int i = 0; i < letfBytes.length; i++)
				if (letfBytes[i] != rightBytes[i])
					return false;
			return true;
		}
		if (left instanceof BigDecimal) {
			if (!(right instanceof BigDecimal))
				return false;
			int scale = Math.max(((BigDecimal) left).scale(), ((BigDecimal) right).scale());
			BigDecimal leftRG = ((BigDecimal) left).setScale(scale);
			BigDecimal rightRG = ((BigDecimal) right).setScale(scale);
			return leftRG.equals(rightRG);
		}
		if (left.getClass().isArray()) {
			if (!right.getClass().isArray())
				return false;
			return Arrays.equals((Object[]) left, (Object[]) right);
		}
		return left.equals(right);
	}

	/** The previous time. */
	private static long previousTime = 0;

	/** The initial time. */
	private static long initialTime = 0;

	/** The format. */
	private static NumberFormat format = NumberFormat.getInstance();

	/**
	 * Prints the time.
	 * 
	 * @param message
	 *            the message
	 * @param reinit
	 *            the reinit
	 */
	public static void printTime(String message, boolean reinit) {
		printTime(message, 100L, reinit);
	}

	/**
	 * Prints the time.
	 *
	 * @param message the message
	 * @param interval the interval
	 * @param reinit the reinit
	 */
	public static void printTime(String message, long interval, boolean reinit) {
		long actualTime = System.currentTimeMillis();
		if (reinit || (actualTime - previousTime) > interval) {
			if (0 == previousTime || reinit) {
				format.setMinimumIntegerDigits(2);
				format.setMinimumFractionDigits(4);
				format.setMaximumFractionDigits(4);
				initialTime = previousTime = System.currentTimeMillis();
			}
			System.out.println(new StringBuffer(DateFormat.getTimeInstance().format(actualTime))//
					.append(" - Diff (second):\t")//
					.append(format.format(Float.valueOf((actualTime - previousTime)) / 1000)) //
					.append(" - \t")//
					.append(format.format(Float.valueOf((actualTime - initialTime)) / 1000))//
					.append(" - \t").append(message));
			previousTime = actualTime;
		}
	}

	/**
	 * appends the message.
	 * 
	 * Useful for having a printed and flushed trace
	 * 
	 * @param fileName
	 *            the file name
	 * @param message
	 *            the message
	 */
	public static void appendMessage(String fileName, String message) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
			out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS").format(System.currentTimeMillis()).concat(" - ")
					.concat(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the status label.
	 *
	 * @param element the element
	 * @return the status label
	 */
	public static String getStatusLabel(final Object element) {
		switch (((IEntity<?>) element).getStatus()) {
		case IEntityConstants.PERSIST:
			return getFromEngineBundle("status.created");
		case IEntityConstants.MERGE:
			return getFromEngineBundle("status.updated");
		case IEntityConstants.REMOVE:
			return getFromEngineBundle("status.deleted");
		case IEntityConstants.RETRIEVE:
			return getFromEngineBundle("status.read");
		case IEntityConstants.TODELETE:
			return getFromEngineBundle("status.todelete");
		default:
			return getFromEngineBundle("unknown");
		}
	}

	/**
	 * Pick element.
	 *
	 * @param collection the collection
	 * @param keys the keys
	 * @return the a widget controller
	 */
	public static AWidgetController pickElement(ICollectionController collection, String... keys) {
		for (AWidgetController element : collection.getChildControllers()) {
			if (keys[0].equals(element.getRegisterId())) {
				if (1 == keys.length)
					return element;
				if (element instanceof ACollectionController) {
					return pickElement((ACollectionController) element, Arrays.copyOfRange(keys, 1, keys.length));
				}
			}
		}
		return null;
	}

	/**
	 * Gets the current stack trace element.
	 *
	 * @param excludedClasses the excluded classes
	 * @return the current stack trace element
	 */
	public static StackTraceElement getCurrentStackTraceElement(Set<String> excludedClasses) {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		for (StackTraceElement stackTraceElement : stackTraceElements)
			if (!excludedClasses.contains(stackTraceElement.getClassName()))
				return stackTraceElement;
		return null;
	}

	/**
	 * Adi print trace.
	 *
	 * @param message the message
	 * @param variables the variables
	 */
	public static void adiPrintTrace(String message, Object... variables) {
		StringBuffer stringBuffer = new StringBuffer("\t - AdiTrace:")
				.append(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date())).append(" - ");
		StackTraceElement stackTraceElement = getCurrentStackTraceElement(EXCLUDED_CLASSES);
		String fileName = stackTraceElement.getFileName();
		if (null != fileName)
			stringBuffer.append(fileName.substring(0, fileName.indexOf('.'))).append("[").append(stackTraceElement.getLineNumber())
					.append("] ");
		else {
			fileName = stackTraceElement.getClassName();
			stringBuffer.append(fileName.substring(0, fileName.indexOf('.'))).append("[").append(stackTraceElement.getMethodName())
					.append("] ");
		}
		stringBuffer.append(message);
		if (null != variables) {
			for (Object variable : variables) {
				stringBuffer.append("\n\t\t\t- ");
				stringBuffer.append(variable);

			}
		}
		System.out.println(stringBuffer);
	}

	public static void printLazyTree(LazyFetchManager<?> lazyFetchManager) {
		IEntity<?> entity = lazyFetchManager.getEntity();
		String entityTitle = entity.getEntityMM().getEntityId() + ":" + entity.getBeanId();
		if (lazyFetchManager.getBindingServiceMap().isEmpty()) {
			adiPrintTrace(entityTitle);
			printLazyNode(lazyFetchManager.getRootLazyNode(), 1);
		} else
			for (ABindingService bindingService : lazyFetchManager.getBindingServiceMap().keySet()) {
				if (null != bindingService.getBoundedController())
					entityTitle = entityTitle + " - " + bindingService.getBoundedController().getTitle();
				adiPrintTrace(entityTitle);
				for (LocalLazyNode lazyNode : lazyFetchManager.getBindingServiceMap().get(bindingService))
					printLazyNode(lazyNode, 1);
			}
	}

	public static void printLazyNode(LocalLazyNode parentLazyNode, int level) {
		for (int i = 0; i < level; i++)
			System.out.print("\t");
		System.out.println("\t- " + level + parentLazyNode.getLazyFetchMember());
		for (LocalLazyNode lazyNode : parentLazyNode.getChildrenMap().values())
			printLazyNode(lazyNode, ++level);
	}
}
