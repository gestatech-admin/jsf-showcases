package hu.palkonyves.ws.rest;


import hu.palkonyves.ws.rest.dto.ElapsedTime;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@ApplicationScoped
@Path("longRunningService")
@Produces("application/json")
public class LongRunningService {
	
	private static Logger LOG = Logger.getLogger(LongRunningService.class.getName());

    @GET
    @Path("elapsedTime")
    public ElapsedTime getElapsedTime(@QueryParam("maxMillisec") Long maxMillisec) {
        ElapsedTime result = new ElapsedTime();
        
        if (maxMillisec == null) {
            result.getErrors().add("maxMillisec must be provided");
            return result;
        }

        long startTime = System.nanoTime();
        try {
            Thread.sleep(maxMillisec);
        } catch (InterruptedException e) {
            
        }
        long endTime = System.nanoTime() - startTime;
        
        result.setElapsedTime(endTime / 1000);
        result.setTimeUnit(ElapsedTime.TIME_UNIT_MS);
        
        LOG.info("elapsedTime service returns");
        return result;
    }
}
