
package hu.palkonyves.serviceloader;

import javax.persistence.JPAService;

public class EclipseLink implements JPAService {

	@Override
	public void init() {
		System.out.println(EclipseLink.class.getName() + " is loaded");
	}

}
