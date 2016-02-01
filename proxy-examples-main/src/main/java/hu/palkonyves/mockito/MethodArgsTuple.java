
package hu.palkonyves.mockito;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A tuple identifying a method call with specific arguments
 * {@link #equals(Object)} and {@link #hashCode()} is overridden
 * so this class can be used in a map
 */
public class MethodArgsTuple {

	final Method lastMethodCalled;
	
	final Object[] lastArgsPassed;
	
	public MethodArgsTuple(Method method, Object[] args) {
		this.lastMethodCalled = method;
		this.lastArgsPassed = args;
	}

	public Method getLastMethodCalled() {
		return lastMethodCalled;
	}

	public Object[] getLastArgsPassed() {
		return lastArgsPassed;
	}

	/**
	 * @see java.lang.Object#hashCode() 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(lastArgsPassed);
		result = prime * result + ((lastMethodCalled == null) ? 0 : lastMethodCalled.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object) 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodArgsTuple other = (MethodArgsTuple) obj;
		if (!Arrays.equals(lastArgsPassed, other.lastArgsPassed))
			return false;
		if (lastMethodCalled == null) {
			if (other.lastMethodCalled != null)
				return false;
		}
		else if (!lastMethodCalled.equals(other.lastMethodCalled))
			return false;
		return true;
	}


}
