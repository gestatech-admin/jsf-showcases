package hu.palkonyves.jsfshowcase;

import java.util.List;

import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class JstlFunctionsController {
	
    @Inject
    BananaStore store;
	
	public String[] getBananaNames() {
	    List<Banana> allBanana = store.getAllBanana();
	    String[] result = new String[allBanana.size()];
	    
	    int i = 0;
	    for (Banana banana : allBanana) {
	        result[i] = banana.getName();
	        i++;
	    }
	    
	    return result;
	}

}
