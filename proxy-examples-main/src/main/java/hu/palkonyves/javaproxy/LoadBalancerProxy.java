
package hu.palkonyves.javaproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class LoadBalancerProxy<T> implements InvocationHandler {

	RoundRobinCache<T> cache;
	
	public LoadBalancerProxy(RoundRobinCache<T> cache) {
		if (cache == null) {
			throw new IllegalArgumentException("Cache must be defined");
		}
		this.cache = cache;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		T object = cache.get();
		return method.invoke(object, args);
	}

	

}
