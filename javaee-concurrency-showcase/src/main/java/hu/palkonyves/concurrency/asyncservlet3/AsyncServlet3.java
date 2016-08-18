package hu.palkonyves.concurrency.asyncservlet3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
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
@WebServlet("elapsedAsyncServlet3")
public class AsyncServlet3 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static Logger LOG = Logger.getLogger(AsyncServlet3.class.getName());
	
	@Resource
	ManagedExecutorService managedExecutorService;
	
	@Inject
	ConfigurationService configurationService;
	
    public AsyncServlet3() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	    
		response.setContentType(Constants.CONTENT_TYPE_PLAIN_HTML);
		
		final PrintWriter writer = response.getWriter();
		
		CallElapsedTimeService3 callElapsedTimeService = new CallElapsedTimeService3(configurationService);
		RunnableFuture<Long> future = new FutureTask<>(callElapsedTimeService);
		managedExecutorService.submit(future);

		try {
			writer.append("Elapsed time: " + future.get());
		} catch (InterruptedException | ExecutionException e) {
			
		}

	}

}
