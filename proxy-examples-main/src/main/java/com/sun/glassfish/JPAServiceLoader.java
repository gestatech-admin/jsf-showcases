
package com.sun.glassfish;

import java.io.IOException;
import java.util.ServiceLoader;

import javax.persistence.JPAService;

/*
 * This is also how SLF4J works
 */
public class JPAServiceLoader {

	public JPAService loadJPAService() {
		ServiceLoader<JPAService> codecSetLoader = ServiceLoader.load(JPAService.class);

		for (JPAService jpaService : codecSetLoader) {

			try {
				jpaService.init();
				return jpaService;
			}
			catch (ClassNotFoundException | IOException e) {
				throw new RuntimeException(e);
			}

		}
		
		throw new IllegalStateException("No JPAService providers found");
	}
}
