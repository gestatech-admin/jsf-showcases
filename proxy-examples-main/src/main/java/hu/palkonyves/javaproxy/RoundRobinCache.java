
package hu.palkonyves.javaproxy;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinCache<T> {

	List<T> elements = new ArrayList<T>();
	int currIndex = 0;
	
	public void add(T element) {
		elements.add(element);
	}
	
	public T get() {
		if (elements.isEmpty()) {
			throw new IllegalStateException("There are no elements in cache");
		}
		
		int size = elements.size();
		T result = elements.get(currIndex);
		currIndex = (currIndex + 1) % size;
		
		return result;
	}
	

}
