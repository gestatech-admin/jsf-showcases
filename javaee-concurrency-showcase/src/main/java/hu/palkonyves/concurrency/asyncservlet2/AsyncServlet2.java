package hu.palkonyves.concurrency.asyncservlet2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
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
@WebServlet("elapsedAsyncServlet2")
public class AsyncServlet2 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static Logger LOG = Logger.getLogger(AsyncServlet2.class.getName());
	
	@Resource
	ManagedExecutorService managedExecutorService;
	
	@Inject
	ConfigurationService configurationService;
	
    public AsyncServlet2() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	    
		response.setContentType(Constants.CONTENT_TYPE_PLAIN_HTML);
		
		final PrintWriter writer = response.getWriter();
		
		CallElapsedTimeService2 callElapsedTimeService = 
				new CallElapsedTimeService2(configurationService);

		managedExecutorService.execute(callElapsedTimeService);

		Long elapsed = waitForFuture(callElapsedTimeService);

		writer.append("Elapsed time: " + elapsed);

	}

	private Long waitForFuture(CallElapsedTimeService2 callElapsedTimeService) {
		Long elapsed = null;
		synchronized (callElapsedTimeService) {
			while (!callElapsedTimeService.isDone()) {
				try {
					// Object.wait() - low level thread API
					callElapsedTimeService.wait();
				} catch (InterruptedException e) {

				}
			}
			
			try {
				elapsed = callElapsedTimeService.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		return elapsed;
	}


}
