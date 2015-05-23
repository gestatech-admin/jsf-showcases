package hu.palkonyves.ws.rest;

import hu.palkonyves.ws.rest.dto.ElapsedTime;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@ApplicationScoped
@Path("longRunningService")
@Produces("application/json")
public class LongRunningService {

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime = System.nanoTime() - startTime;
        
        result.setElapsedTime(endTime);
        result.setTimeUnit(ElapsedTime.TIME_UNIT_NS);
        
        return result;
    }
}
