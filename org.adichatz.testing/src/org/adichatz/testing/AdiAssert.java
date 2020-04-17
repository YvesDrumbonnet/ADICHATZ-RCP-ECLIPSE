package org.adichatz.testing;

import static org.adichatz.testing.TestingTools.getFromTestingBundle;

import org.adichatz.engine.cache.IEntity;
import org.adichatz.engine.controller.ACollectionController;
import org.adichatz.engine.controller.AEntityManagerController;
import org.adichatz.engine.controller.AFieldController;
import org.adichatz.engine.controller.ASetController;
import org.adichatz.engine.controller.AWidgetController;
import org.adichatz.engine.controller.IBoundedController;
import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.simulation.SimulationTools;
import org.adichatz.testing.tracking.AdiResultListener;
import org.testng.Assert;

public class AdiAssert {
	/**
	 * Asserts that a condition is true. If it isn't it throws an {@link AssertionError} with the given message.
	 *
	 * @param message
	 *            the identifying message for the {@link AssertionError} (<code>null</code> okay)
	 * @param condition
	 *            condition to be checked
	 */
	static public void assertTrue(String message, boolean condition) {
		if (!condition) {
			fail(message);
		}
	}

	public static void isPartActive(BoundedPart boundedPart) {
		isPartActive(boundedPart, null);
	}

	@SuppressWarnings("restriction")
	public static void isPartActive(BoundedPart boundedPart, String message) {
		SimulationTools.checkNullArguments(boundedPart);
		if (!boundedPart.getPartService().isPartVisible(boundedPart.getInputPart())) {
			if (null == message)
				message = getFromTestingBundle("testing.fail.isActive.part", boundedPart.getInputPart().getLabel());
			AdiResultListener.TEST_REFERENCE = AdiResultListener.CURRENT_LISTENER.storeTestReference();
			fail(message);
		}
	}

	public static void hasSelection(ASetController setController) {
		hasSelection(setController, null);
	}

	public static void hasSelection(ASetController setController, String message) {
		SimulationTools.checkNullArguments(setController);
		if (null == setController.getSelectedObject()) {
			if (null == message)
				message = getFromTestingBundle("testing.fail.hasSelection.setController", setController.getRegisterId());
			fail(message);
		}
	}

	private static boolean _isLocked(IEntity<?> entity, boolean lockedParam) {
		if (lockedParam == entity.isLocked())
			return true;
		try {
			return lockedParam == entity.getEntityMM().getDataAccess().getGatewayConnector().getLockManager()
					.isLocked(entity.getBeanClass().getName(), entity.getBeanId());
		} catch (Exception e) {
			Assert.fail(getFromTestingBundle("testing.error.lockManager", e));
			return false;
		}
	}

	public static void isLocked(IEntity<?> entity, boolean lockedParam) {
		isLocked(entity, null, lockedParam);
	}

	public static void isLocked(IEntity<?> entity, String message, boolean lockedParam) {
		if (!_isLocked(entity, lockedParam)) {
			if (null == message)
				message = getFromTestingBundle(lockedParam ? "testing.fail.entity.not.locked" : "testing.fail.entity.locked",
						entity.getEntityMM().getEntityId(), entity.getBeanId());
			fail(message);
		}
	}

	public static void isLocked(AEntityManagerController entityManagerController, boolean lockedParam) {
		isLocked(entityManagerController, null, lockedParam);
	}

	public static void isLocked(AEntityManagerController entityManagerController, String message, boolean lockedParam) {
		SimulationTools.checkNullArguments(entityManagerController);
		if (lockedParam != entityManagerController.isLocked()) {
			if (null == message)
				message = getFromTestingBundle(
						lockedParam ? "testing.fail.controller.not.locked" : "testing.fail.controller.locked",
						entityManagerController.getRegisterId());
			fail(message);
		}
		if (lockedParam) { // Check lock on children fields only when disabled is asked - A field could disabled independently from its parent
			String childControllerId = internalIsLocked(entityManagerController, entityManagerController.getEntity(), lockedParam);
			if (null != childControllerId) {
				if (null == message)
					message = getFromTestingBundle("testing.fail.child.controller.not.locked", childControllerId,
							entityManagerController.getRegisterId());
				fail(message);
			}
		}
	}

	private static String internalIsLocked(ACollectionController collectionController, IEntity<?> entity, boolean lockedParam) {
		SimulationTools.checkNullArguments(collectionController);
		boolean locked = !(collectionController instanceof AEntityManagerController) || collectionController.isLocked();
		if (lockedParam != locked)
			return collectionController.getRegisterId();
		for (AWidgetController controller : collectionController.getChildControllers()) {
			if (controller instanceof ACollectionController && entity.equals(controller.getEntity())) {
				String unlockedController = internalIsLocked((ACollectionController) controller, entity, lockedParam);
				if (null != unlockedController)
					return unlockedController;
			} else if (controller instanceof AFieldController && !controller.isLocked())
				return controller.getRegisterId();
		}
		return null;
	}

	public static void isDirty(IBoundedController dirtyController, boolean dirtyParam) {
		isDirty(dirtyController, null, dirtyParam);
	}

	public static void isDirty(IBoundedController dirtyController, String message, boolean dirtyParam) {
		SimulationTools.checkNullArguments(dirtyController);
		if (dirtyParam != dirtyController.isDirty()) {
			if (null == message) {
				message = getFromTestingBundle(dirtyParam ? "testing.fail.controller.not.dirty" : "testing.fail.controller.dirty",
						dirtyController.getTitle());
			}
			fail(message);
		}
	}

	public static void fail(String message) {
		AdiResultListener.TEST_REFERENCE = AdiResultListener.CURRENT_LISTENER.storeTestReference();
		Assert.fail(message);
	}
}
