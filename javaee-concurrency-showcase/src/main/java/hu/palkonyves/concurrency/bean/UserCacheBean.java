package hu.palkonyves.concurrency.bean;

import java.util.Collections;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import hu.palkonyves.concurrency.externalservice.LdapUserService;

@SessionScoped
public class UserCacheBean implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	LdapUserService ldapUserService;
	
	private volatile List<String> users;
	
	public List<String> getUsers() {
		if (users == null) {
			users = ldapUserService.getUsers();
		}
		
		
		return users;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<String> getUsersDCL() {
		if (users == null) { // visibility issue, half-created object
			synchronized(UserCacheBean.class) {
				if (users == null) {
					users = ldapUserService.getUsers();
				}
			}
		}
		
		return users;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public synchronized List<String> getUsersSync() {
		if (users == null) {
			users = ldapUserService.getUsers();
		}
		
		return users;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public synchronized List<String> getUsersSyncImmutable() {
		if (users == null) {
			users = Collections.unmodifiableList(ldapUserService.getUsers());
		}
		
		return users;
	}
}
