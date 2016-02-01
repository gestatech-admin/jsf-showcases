
package hu.palkonyves.javaproxy.test;

import hu.palkonyves.javaproxy.ServiceLocator;
import hu.palkonyves.rest.api.BookDto;
import hu.palkonyves.rest.api.BookResource;
import hu.palkonyves.serviceloader.ClassInspector;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

import org.junit.Test;

public class RestServiceLoadBalancerTest {

	@Test
	public void instantiating_with_new() {
		BookResource bookResource = new ServiceLocator().getBookResource(true);

		List<BookDto> result = bookResource.findBook("author*", "*");

		ClassInspector.inspectClass(bookResource.getClass());
	}

	@Test
	public void creating_load_balancer_proxy() {

		BookResource balancedBookResource = new ServiceLocator().getBookResourceProxy(true);

		for (int i = 0; i < 4; i++) {
			List<BookDto> result2 = balancedBookResource.findBook("author*", "*");
		}

		// INSPECT PROXY
		ClassInspector.inspectClass(balancedBookResource.getClass());

		if (Proxy.isProxyClass(balancedBookResource.getClass())) {
			InvocationHandler invocationHandler = Proxy.getInvocationHandler(balancedBookResource);
			ClassInspector.inspectClass(invocationHandler.getClass());
		}
	}

	/**
	 * Simple performance test to see how long it takes
	 * to create the proxy objects compared to instantiating a POJO
	 */
	@Test
	public void create_proxy_performance_test() {

		final int ROUNDS = 1000_000;
		final double nanosecInMilisec = 1000_000D;
		ServiceLocator serviceLocator = new ServiceLocator();

		// warmup
		for (int i = 0; i < ROUNDS; i++) {
			serviceLocator.getBookResource(false);
			serviceLocator.getBookResourceProxySimple(false);
		}

		// Creating original 
		long nanoStart = System.nanoTime();
		for (int i = 0; i < ROUNDS; i++) {
			serviceLocator.getBookResource(false);
		}
		System.out.println("elapsed original: " + (System.nanoTime() - nanoStart)
				/ nanosecInMilisec + " ms");

		// Creating proxy
		nanoStart = System.nanoTime();
		for (int i = 0; i < ROUNDS; i++) {
			serviceLocator.getBookResourceProxySimple(false);
		}
		System.out.println("elapsed proxy: " + (System.nanoTime() - nanoStart) / nanosecInMilisec
				+ " ms");
	}

	/**
	 * Test how long it takes to call method on a proxy object
	 * compared to calling method on POJO
	 */
	@Test
	public void call_proxy_performance_test() {

		final double nanosecInMilisec = 1000_000D;
		final int ROUNDS = 1000_000;

		BookResource bookResource = new ServiceLocator().getBookResource(false);
		BookResource balancedBookResource = new ServiceLocator().getBookResourceProxySimple(false);

		// warmup
		for (int i = 0; i < ROUNDS; i++) {
			bookResource.findBook("author*", "*");
			balancedBookResource.findBook("author*", "*");
		}

		// Calling original
		long nanoStart = System.nanoTime();
		for (int i = 0; i < ROUNDS; i++) {

			bookResource.findBook("author*", "*");
		}
		System.out.println("elapsed: " + (System.nanoTime() - nanoStart) / nanosecInMilisec + " ms");

		// Calling Proxy
		nanoStart = System.nanoTime();
		for (int i = 0; i < ROUNDS; i++) {

			balancedBookResource.findBook("author*", "*");
		}

		System.out.println("elapsed: " + (System.nanoTime() - nanoStart) / nanosecInMilisec + " ms");
	}

}
