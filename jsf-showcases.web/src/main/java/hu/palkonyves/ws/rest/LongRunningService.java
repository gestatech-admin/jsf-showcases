package hu.palkonyves.ws.rest;

import hu.palkonyves.ws.rest.dto.ElapsedTime;

import java.util.logging.Logger;

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

	private static Logger LOG = Logger.getLogger(LongRunningService.class.getName());

	@GET
	@Path("elapsedTime")
	@ApiOperation(value = "Find purchase order by ID", notes = "For valid response try integer IDs with value <= 5 or > 10. Other values will generated exceptions")
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

		}
		long endTime = System.nanoTime() - startTime;

		result.setElapsedTime(endTime / 1000);
		result.setTimeUnit(ElapsedTime.TIME_UNIT_MS);

		LOG.info("elapsedTime service returns");
		return result;
	}
}
