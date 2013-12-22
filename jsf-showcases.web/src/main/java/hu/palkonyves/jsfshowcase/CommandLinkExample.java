package hu.palkonyves.jsfshowcase;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

// snippet=1
@Named
@RequestScoped
public class CommandLinkExample {

    /**
     * Mimic a post request that does something permanent in a backend database,
     * then returns to the index.jsf
     * 
     * @return
     */
    public String doSomethingPermanent() {
        return "/index";
    }
}
// endsnippet
