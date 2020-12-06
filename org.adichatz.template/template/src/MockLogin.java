package #{adichatz.package.name};

import java.util.HashSet;

import org.adichatz.common.ejb.Session;
import org.adichatz.engine.common.AdichatzApplication;
import org.adichatz.engine.extra.IAdiLogin;
import org.eclipse.jface.window.Window;

public class MockLogin implements IAdiLogin {

	@Override
	public int open() {
		return Window.OK;
	}

	@Override
	public Session getSession() {
		return new Session(AdichatzApplication.getInstance().getApplicationPluginResources().getPluginName(),
				System.getProperty("user.name"), new HashSet<String>(0));
	}

}
