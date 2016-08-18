package hu.palkonyves.concurrency.asyncservlet1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.palkonyves.servlet.ConfigurationService;
import hu.palkonyves.servlet.Constants;

/**
 * Servlet implementation class AsyncTranslateServlet
 */
@WebServlet("elapsedAsyncServlet1")
public class AsyncServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static Logger LOG = Logger.getLogger(AsyncServlet.class.getName());
	
	@Resource
	ManagedExecutorService managedExecutorService;
	
	@Inject
	ConfigurationService configurationService;
	
    public AsyncServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	    
		response.setContentType(Constants.CONTENT_TYPE_PLAIN_HTML);
		
		final PrintWriter writer = response.getWriter();
	
		CallElapsedTimeService callElapsedTimeService = 
				new CallElapsedTimeService(configurationService);
		managedExecutorService.execute(callElapsedTimeService);
	
		
		long currentTimeMillis = System.currentTimeMillis();
	
		while (!callElapsedTimeService.isDone() 
				&& System.currentTimeMillis() - currentTimeMillis < 10_000) {
			LOG.info("polling for elapsed time");
		}

		try {
			Long elapsed = callElapsedTimeService.get();
			writer.append("Elapsed time: " + elapsed);
		} catch (InterruptedException | ExecutionException e) {}

	}

}
