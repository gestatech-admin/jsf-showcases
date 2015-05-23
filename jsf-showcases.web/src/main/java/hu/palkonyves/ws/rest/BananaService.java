package hu.palkonyves.ws.rest;
import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@ApplicationScoped
@Produces("application/json")
@Path("bananaService")
public class BananaService {
    
    @Inject
    BananaStore bananaStore;

    @GET
    @Path("allBananas")
    public List<Banana> getAllBananas() {
        List<Banana> allBanana = bananaStore.getAllBanana();
        return allBanana;
    }
}
