package org.adichatz.engine.controller;

import java.util.Map;

import org.adichatz.engine.core.ControllerCore;
import org.adichatz.engine.core.RootCore;
import org.adichatz.engine.validation.ABindingService;
import org.adichatz.engine.widgets.supplement.AAdiRunnable;

// TODO: Auto-generated Javadoc
/**
 * The Interface IActionController.
 *
 * @author Yves Drumbonnet
 */
public interface IActionController extends IController, IRankedController {

	/**
	 * Gets the runnable.
	 *
	 * @return the runnable
	 */
	public AAdiRunnable getRunnable();

	/**
	 * Gets the param map.
	 *
	 * @return the param map
	 */
	public Map<String, Object> getParamMap();

	/**
	 * Gets the parent controller.
	 *
	 * @return the parent controller
	 */
	public ICollectionController getParentController();

	/**
	 * Gets the gen code.
	 *
	 * @return the gen code
	 */
	public ControllerCore getGenCode();

	/**
	 * Gets the root core.
	 *
	 * @return the root core
	 */
	public RootCore getRootCore();

	/**
	 * Gets the binding service.
	 *
	 * @return the binding service
	 */
	public ABindingService getBindingService();

	public String getRegisterId();
}
