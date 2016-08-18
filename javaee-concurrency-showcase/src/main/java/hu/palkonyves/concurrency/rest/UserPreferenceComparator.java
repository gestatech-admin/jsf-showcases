package hu.palkonyves.concurrency.rest;

import java.util.Comparator;

public class UserPreferenceComparator implements Comparator<String> {
	
	boolean increasing = Double.valueOf(Math.random() * 10).intValue() % 2 == 0; 

	@Override
	public int compare(String o1, String o2) {
		try {Thread.sleep(1);} catch (InterruptedException e) {}
		if (increasing) {
			return o1.compareTo(o2);
		}
		else {
			return o2.compareTo(o1);
		}
	}
}
