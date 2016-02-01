
package hu.palkonyves.mockito;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for finding the <i>return value</i>
 * registered for a method invocation for specific arguments.
 * If no return value was registered, then just returns null.
 */
public class MockInvocationHandler implements InvocationHandler {
	
	/**
	 * Update the lastCall in every invocation, so we can reuse
	 * the referenced method and arguments in the <i>thenReturn</i> call
	 */
	private MethodArgsTuple lastCall;
	
	/**
	 * Map of return values for each method call with specific arguments
	 */
	private final Map<Object, Object> returns = new HashMap<Object, Object>();
	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		lastCall = new MethodArgsTuple(method, args);
		Mock.lastMockCalled.set(this);
		
		Object result = returns.get(lastCall);
		if (result != null) {
			return result;
		}
		
		return null;
	}
	
	public void registerReturnVal(MethodArgsTuple methodCall, Object result) {
		Mock.lastMockUsed.set(true);
		returns.put(methodCall, result);
	}
	
	public MethodArgsTuple getLastCall() {
		return lastCall;
	}

}
