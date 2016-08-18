package hu.palkonyves.concurrency.externalservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LdapUserService {
	
	public List<String> getUsers() {
		try {Thread.sleep(100);} catch (InterruptedException e) {}
		return new ArrayList<String>(Arrays.asList("Iron Man", "The Hulk", "Thor", "Ant Man"));
	}
	
}
