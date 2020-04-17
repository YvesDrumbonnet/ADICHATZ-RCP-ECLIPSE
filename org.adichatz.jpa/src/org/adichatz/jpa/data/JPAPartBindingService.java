package org.adichatz.jpa.data;

import org.adichatz.engine.e4.part.BoundedPart;
import org.adichatz.engine.e4.resource.PartBindingService;

public class JPAPartBindingService extends PartBindingService {

	public JPAPartBindingService(BoundedPart boundedPart) {
		super(boundedPart);
	}

	@Override
	public void close() {
		super.close();
	}

}
