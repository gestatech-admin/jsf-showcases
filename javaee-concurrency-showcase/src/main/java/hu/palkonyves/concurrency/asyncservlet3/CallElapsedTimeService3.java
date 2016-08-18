package hu.palkonyves.concurrency.asyncservlet3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import hu.palkonyves.servlet.ConfigurationService;
import hu.palkonyves.servlet.Constants;
import hu.palkonyves.ws.rest.dto.ElapsedTime;

public class CallElapsedTimeService3 implements Callable<Long> {
	private static Logger LOG = Logger.getLogger(CallElapsedTimeService3.class.getName());
	
    private final ConfigurationService configurationService;
    

    CallElapsedTimeService3(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Override
    public Long call() throws Exception {

    
        final String restBaseUrl = configurationService.getProperty(ConfigurationService.REST_BASE_URL, null);
        final String restElapsedTimeUrl = restBaseUrl + configurationService.getProperty(ConfigurationService.REST_ELAPSEDTIME_URL, null);

        // https://docs.oracle.com/javaee/7/api/javax/ws/rs/client/package-summary.html
        Client client = ClientBuilder.newClient();
        ElapsedTime res = client
                .target(restElapsedTimeUrl)
                .queryParam("maxMillisec", 1000)
                .request(Constants.CONTENT_TYPE_JSON).get(ElapsedTime.class);
        
        
        return res.getElapsedTime();
        
    }

}