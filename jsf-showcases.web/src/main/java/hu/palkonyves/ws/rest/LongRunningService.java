package hu.palkonyves.ws.rest;

import hu.palkonyves.ws.rest.dto.ElapsedTime;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

@ApplicationScoped
@Path("longRunningService")
@Api()
@Produces("application/json")
public class LongRunningService {

    @GET
    @Path("elapsedTime")
    @ApiOperation(value = "Find purchase order by ID",
    notes = "For valid response try integer IDs with value <= 5 or > 10. Other values will generated exceptions")
  @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied"),
      @ApiResponse(code = 404, message = "Order not found") })
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
