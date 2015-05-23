package hu.palkonyves.servlet;

import hu.palkonyves.ws.rest.dto.ElapsedTime;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.AsyncContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class CallElapsedTimeService implements Runnable {
    private final AsyncContext asyncContext;
    private final PrintWriter writer;
    private final AtomicInteger threadsSpawned;
    private final ConfigurationService configurationService;

    CallElapsedTimeService(AsyncContext asyncContext, PrintWriter writer, AtomicInteger threadsSpawned, ConfigurationService configurationService) {
        this.asyncContext = asyncContext;
        this.writer = writer;
        this.threadsSpawned = threadsSpawned;
        this.configurationService = configurationService;
    }

    @Override
    public void run() {
        threadsSpawned.addAndGet(1);
    
        final String restBaseUrl = configurationService.getProperty(ConfigurationService.REST_BASE_URL, null);
        final String restElapsedTimeUrl = restBaseUrl + configurationService.getProperty(ConfigurationService.REST_ELAPSEDTIME_URL, null);

        // https://docs.oracle.com/javaee/7/api/javax/ws/rs/client/package-summary.html
        Client client = ClientBuilder.newClient();
        ElapsedTime res = client
                .target(restElapsedTimeUrl)
                .queryParam("maxMillisec", 2000)
                .request(Constants.CONTENT_TYPE_JSON).get(ElapsedTime.class);
        
        Long elapsedTime = res.getElapsedTime();
        int currThreads = threadsSpawned.get();
        writer.append("elapsed time: " + elapsedTime + ", threads: "
                + currThreads);
        threadsSpawned.addAndGet(-1);
        asyncContext.complete();
    }
}