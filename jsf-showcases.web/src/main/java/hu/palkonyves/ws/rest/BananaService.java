package hu.palkonyves.ws.rest;
import hu.palkonyves.jsfshowcase.business.Banana;
import hu.palkonyves.jsfshowcase.business.BananaStore;
import hu.palkonyves.ws.rest.dto.ElapsedTime;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api()
@ApplicationScoped
@Produces("application/json")
@Path("bananaService")
public class BananaService {
    
    @Inject
    BananaStore bananaStore;

    /**
     * Return all bananas from the database
     * @return Return all bananas from the database
     */
    @GET
    @Path("allBananas")
    @ApiOperation(value = "Return all bananas")
    public List<Banana> getAllBananas() {
        List<Banana> allBanana = bananaStore.getAllBanana();
        return allBanana;
    }
}
