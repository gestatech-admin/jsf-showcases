package hu.palkonyves.concurrency.asyncservlet4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
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
@WebServlet("elapsedAsyncServlet4")
public class AsyncServlet4 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static Logger LOG = Logger.getLogger(AsyncServlet4.class.getName());
	
	@Resource
	ManagedExecutorService managedExecutorService;
	
	@Inject
	ConfigurationService configurationService;
	
    public AsyncServlet4() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	    
	    // http://www.jayway.com/2014/05/16/async-servlets/
		response.setContentType(Constants.CONTENT_TYPE_PLAIN_HTML);
		
		final PrintWriter writer = response.getWriter();
		
		List<CallElapsedTimeService4> tasks = new LinkedList<>();
		
		for (int i = 0; i < 4; i++) {
			CallElapsedTimeService4 callElapsedTimeService = new CallElapsedTimeService4(configurationService);
			tasks.add(callElapsedTimeService);
		}
		
		try {
			List<Future<Long>> invokeAll = managedExecutorService.invokeAll(tasks);
			for (Future<Long> future : invokeAll) {
				writer.append("Elapsed time: " + future.get());			
			}
		} catch (InterruptedException | ExecutionException e1) {
			
		}

	}

}
