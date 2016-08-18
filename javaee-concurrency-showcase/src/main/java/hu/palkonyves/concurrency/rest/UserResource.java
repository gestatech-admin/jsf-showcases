package hu.palkonyves.concurrency.rest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import hu.palkonyves.concurrency.bean.UserCacheBean;

@Produces("application/json")
@Path("users")
public class UserResource {
    
    @Inject
    UserCacheBean users;
    
    
    
    
    
    
    
    
    
    
    
    
    @GET()
    @Path("simple")
    public List<String> getAllUsersA() {
        List<String> usersList = users.getUsers();
        String idHashCode = String.format("#%05X", 
        		System.identityHashCode(usersList));
        
        List<String> res = new ArrayList<String>(usersList);
        res.add(idHashCode);
        return res;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GET()
    @Path("sort")
    public List<String> getAllUsersB() {
        List<String> res = users.getUsersSync();
        
        UserPreferenceComparator userPreferenceComparator = 
        		new UserPreferenceComparator();
		Collections.sort(res, userPreferenceComparator);
		
        return res;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    @GET()
    @Path("syncedsort")
    public List<String> getAllUsersC() {
        List<String> res = users.getUsersSync();
        
        UserPreferenceComparator userPreferenceComparator = 
        		new UserPreferenceComparator();		
		 
		synchronized(res) {
			Collections.sort(res, userPreferenceComparator);			
			res = new ArrayList<String>(res);
		}
		
		res.add(userPreferenceComparator.increasing ? "increasing" : "decreasing");
		
        return res;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GET()
    @Path("immutablesort")
    public List<String> getAllUsersD() {
        List<String> res = users.getUsersSyncImmutable();
        res = new ArrayList<String>(res);
        
        UserPreferenceComparator userPreferenceComparator = 
        		new UserPreferenceComparator();
        
		Collections.sort(res, userPreferenceComparator);
		res.add(userPreferenceComparator.increasing ? "increasing" : "decreasing");
		
        return res;
    }
    
}
