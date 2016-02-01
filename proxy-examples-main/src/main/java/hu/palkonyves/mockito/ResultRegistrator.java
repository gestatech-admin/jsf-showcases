
package hu.palkonyves.mockito;

/**
 * Offers the {@link #thenReturn(Object)} statement
 * that can be used to register the return value for the mock object
 * {@link MockInvocationHandler}
 */
public class ResultRegistrator {
	
	private final MockInvocationHandler handler;
	private final MethodArgsTuple tuple;
	
	public ResultRegistrator(MockInvocationHandler handler, MethodArgsTuple tuple) {
		this.handler = handler;
		this.tuple = tuple;
	}
	
	public void thenReturn(Object object) {
		handler.registerReturnVal(tuple, object);
	}

}
