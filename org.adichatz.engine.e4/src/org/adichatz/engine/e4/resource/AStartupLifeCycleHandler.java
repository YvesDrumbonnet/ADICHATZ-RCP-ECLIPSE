package org.adichatz.engine.e4.resource;

import static org.adichatz.engine.common.LogBroker.logError;
import static org.adichatz.engine.e4.resource.EngineE4Util.getFromEngineE4Bundle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.adichatz.engine.common.AdiPluginResources;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.common.EngineConstants;
import org.adichatz.engine.common.EngineTools;
import org.adichatz.engine.common.IAdiLogger;
import org.adichatz.engine.common.LogBroker;
import org.adichatz.engine.common.ReflectionTools;
import org.adichatz.engine.e4.part.IntroPart;
import org.adichatz.engine.extra.IAdiLogin;
import org.adichatz.engine.xjc.LoginType;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public abstract class AStartupLifeCycleHandler {
	/** The plugin resources for RCP application. */
	protected AdiPluginResources pluginResources;

	@Inject
	protected IEclipseContext context;

	@Inject
	protected EModelService modelService;

	protected Display display;

	protected E4AdichatzApplication adichatzApplication;

	protected void init(final IEclipseContext context) {
		adichatzApplication = E4AdichatzApplication.getInstance();

		// Force plugin resources to be initialized if not already loaded.
		AdichatzApplication.getPluginResources(EngineConstants.ENGINE_BUNDLE);
		String gencodePackage = (String) adichatzApplication.popParam(EngineConstants.GENCODE_PACKAGE);
		new AdiPluginResources(EngineConstants.ENGINE_E4_BUNDLE, EngineConstants.ENGINE_E4_BUNDLE, gencodePackage,
				getClass().getClassLoader());

		adichatzApplication.setApplicationPluginResources(pluginResources);

		try {
			display = Display.getDefault();
			context.set(Display.class.getName(), display);
			EngineConstants.SYNCHRONIZE_REFLOW_MODE = false;
			context.set(Logger.class.getName(), new E4Logger(context));
			LogBroker.getLogger().initPreferenceManager();

			adichatzApplication.loadRcpConfigTree(pluginResources, display);

			String loggerClassName = (String) adichatzApplication.popParam("adiLoggerClassName");
			if (!EngineTools.isEmpty(loggerClassName)) {
				try {
					Logger logger = (Logger) ReflectionTools.instantiateClass(loggerClassName,
							new Class[] { IEclipseContext.class, EModelService.class }, new Object[] { context, modelService });
					context.set(Logger.class.getName(), logger);
				} catch (Exception e) {
					logError(e);
				}
			}

			/*
			 * Login must succeed to continue
			 */
			String loginMessage = login();
			if (null != loginMessage) {
				EngineTools.openDialog(MessageDialog.ERROR, getFromEngineE4Bundle("cannot.initialize.application"),
						getFromEngineE4Bundle("loginFailedInterrupApplication"), "", "\n\t" + loginMessage);
				System.exit(0);
			}
		} catch (Exception e) {
			EngineTools.openDialog(MessageDialog.ERROR, getFromEngineE4Bundle("cannot.initialize.application"),
					EngineTools.getErrorString(e), "", "\n\t" + getFromEngineE4Bundle("loginFailedInterrupApplication"));
			System.exit(0);
		}

		adichatzApplication.getEarlyStartupHooks().add(() -> {
			String applicationPostOpenURI = (String) adichatzApplication.getApplicationParamMap()
					.get(EngineConstants.APPLICATION_POST_OPEN_URI);
			if (!EngineTools.isEmpty(applicationPostOpenURI)) {
				try {
					Runnable applicationPostOpen = ((Runnable) ReflectionTools.instantiateURI(applicationPostOpenURI,
							new Class[] {}, new Object[] {}));
					if (null != applicationPostOpen)
						applicationPostOpen.run();
				} catch (Exception e) {
					logError(e);
				}
			}
		});
		context.set(PartRenderingEngine.EARLY_STARTUP_HOOK, new Runnable() {
			@Override
			public void run() {
				IAdiLogger loggerBroker = LogBroker.getLogger();
				loggerBroker.getExcludedCLasses().add(getClass().getName());
				loggerBroker.flush();
				loggerBroker.getExcludedCLasses().remove(getClass().getName());
				List<Runnable> earlyStartupHookRunables = adichatzApplication.getEarlyStartupHooks();
				E4AdichatzApplication.getInstance().initToolbar(context.get(MApplication.class), context);
				while (!earlyStartupHookRunables.isEmpty()) { // Entry in earlyStartupHookRunables must be created
																// during process
					List<Runnable> runables = new ArrayList<>(earlyStartupHookRunables);
					for (Runnable runnable : runables) {
						runnable.run();
						earlyStartupHookRunables.remove(runnable);
					}
				}
				IntroPart introPart = IntroPart.getInstance();
				if (null != introPart)
					introPart.show();
			}
		});
		context.get(IEventBroker.class).subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, (e) -> {
			MApplication application = (MApplication) e.getProperty("org.eclipse.e4.data");
			MWindow window = application.getChildren().get(0);
			// Instantiate a new Save processus when closing windows.
			window.getContext().set(ISaveHandler.class, new AdiSaveHandler(window.getContext()));
		});

		// Disable warning
		// Code: 'defineClass.setAccessible(true);' in net.sf.cglib.core.ReflectUtils class generate warning:
		// 'An illegalreflective access operation has occurred'.
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			Unsafe u = (Unsafe) theUnsafe.get(null);

			Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
			Field logger = cls.getDeclaredField("logger");
			u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * Login.
	 * 
	 * This is the default login process provided by Adichatz<br>
	 * You can change this process according to your needs. You only have to set a
	 * session in the current Adichatz application
	 * 
	 * @param adichatzApplication the adichatz application
	 * @return true, if successful
	 */
	protected String login() {
		LoginType login = adichatzApplication.getLogin();
		if (null == login)
			return getFromEngineE4Bundle("no.login.specified");
		else {
			if (null != login.getLoginTemplate()
					&& login.getLoginTemplate().value().equals(EngineConstants.LOGIN_TEMPLATE_DIALOG)) {
				IAdiLogin loginDialog = (IAdiLogin) ReflectionTools.instantiateClass(login.getLoginClassName());
				while (true) {
					int result = loginDialog.open();
					switch (result) {
					case Window.OK: {
						adichatzApplication.setSession(loginDialog.getSession());
						return null;
					}
					case Window.CANCEL:
						return getFromEngineE4Bundle("login.process.cancelled");
					default:
						return getFromEngineE4Bundle("invalid.login.parameters");
					}
				}
			} else {
				return getFromEngineE4Bundle("invalid.login.template");
			}
		}
	}

	@PreSave
	private void presave() {
		adichatzApplication.getEditorPartStack().setSelectedElement(null); // Avoid reference to a non persistable BoundedPart
	}
}
