package hu.palkonyves.concurrency.asyncservlet2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import hu.palkonyves.servlet.ConfigurationService;
import hu.palkonyves.servlet.Constants;
import hu.palkonyves.ws.rest.dto.ElapsedTime;

public class CallElapsedTimeService2 implements RunnableFuture<Long> {
	private static Logger LOG = Logger.getLogger(CallElapsedTimeService2.class.getName());
	
    private final ConfigurationService configurationService;
    private Long elapsedTime = null;

    CallElapsedTimeService2(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Override
    public void run() {
    
        final String restBaseUrl = configurationService.getProperty(ConfigurationService.REST_BASE_URL, null);
        final String restElapsedTimeUrl = restBaseUrl + configurationService.getProperty(ConfigurationService.REST_ELAPSEDTIME_URL, null);

        // https://docs.oracle.com/javaee/7/api/javax/ws/rs/client/package-summary.html
        Client client = ClientBuilder.newClient();
        ElapsedTime res = client
                .target(restElapsedTimeUrl)
                .queryParam("maxMillisec", 1000)
                .request(Constants.CONTENT_TYPE_JSON).get(ElapsedTime.class);
        
        synchronized(this) { // 'this' is the mutex
        	elapsedTime = res.getElapsedTime();
        	this.notifyAll();
        }
        
    }

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long get() throws InterruptedException, ExecutionException {
		return elapsedTime;
	}

	@Override
	public Long get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCancelled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDone() {
		return elapsedTime != null;
	}
}