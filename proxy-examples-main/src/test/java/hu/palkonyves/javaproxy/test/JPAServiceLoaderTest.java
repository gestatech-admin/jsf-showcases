
package hu.palkonyves.javaproxy.test;

import org.junit.Assert;

import javax.persistence.JPAService;

import org.junit.Test;

import com.sun.glassfish.JPAServiceLoader;

public class JPAServiceLoaderTest {

	/**
	 * How to load service implementation.
	 * The actual class to be loaded will be defined by the file residing in
	 * <i>/META-INF/services/your.service.Interface</i>
	 * 
	 * <p>This is same mechanism how SLF4J will find the backend implementation on runtime
	 */
	@Test
	public void load_jpa_provider() {
		JPAService loadJPAService = new JPAServiceLoader().loadJPAService();
		
		Assert.assertNotNull("Found JPAService implementation", loadJPAService);
		System.out.println(loadJPAService.getClass());
	}

}
