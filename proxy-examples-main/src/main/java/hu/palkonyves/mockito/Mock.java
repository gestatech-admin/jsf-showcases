
package hu.palkonyves.mockito;

import java.lang.reflect.Proxy;

public class Mock {
	
	/*
	 * We use thread local variables because these states are held 
	 * in static fields, however JUnit tests may run in multiple threads
	 */
	
	static ThreadLocal<MockInvocationHandler> lastMockCalled = new ThreadLocal<MockInvocationHandler>();
	static ThreadLocal<Boolean> lastMockUsed = new ThreadLocal<Boolean>() {
		
		protected Boolean initialValue() {
			return Boolean.TRUE;
		}
	};
	
	static ThreadLocal<StackTraceElement> mockMethodCalledAt = new ThreadLocal<StackTraceElement>();

	
	public static <T> T mock(Class<T> clazz) {
		
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Class<?>[] ifaces = new Class<?>[] {clazz};
		
		MockInvocationHandler handler = new MockInvocationHandler();
		
		return (T) Proxy.newProxyInstance(contextClassLoader, ifaces, handler);
	}
	
	public static ResultRegistrator when(Object o) {
		if (!lastMockUsed.get()) {
			StackTraceElement stackTraceElement = mockMethodCalledAt.get();
			throw new RuntimeException("Forgot to call thenReturn at " + stackTraceElement);
		}
		
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement mockMethodCalledAt = stackTrace[2];
		Mock.mockMethodCalledAt.set(mockMethodCalledAt);
		
		MockInvocationHandler object = lastMockCalled.get();
		lastMockUsed.set(false);
		
		return new ResultRegistrator(object, object.getLastCall());
	}

}
