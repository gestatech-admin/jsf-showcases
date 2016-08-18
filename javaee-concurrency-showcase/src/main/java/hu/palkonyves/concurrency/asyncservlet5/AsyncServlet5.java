package hu.palkonyves.concurrency.asyncservlet5;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
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
@WebServlet("elapsedAsyncServlet5")
public class AsyncServlet5 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static Logger LOG = Logger.getLogger(AsyncServlet5.class.getName());
	
	@Resource
	ManagedExecutorService managedExecutorService;
	
	@Inject
	ConfigurationService configurationService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AsyncServlet5() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	    
	    // http://www.jayway.com/2014/05/16/async-servlets/
		response.setContentType(Constants.CONTENT_TYPE_PLAIN_HTML);
		
		final PrintWriter writer = response.getWriter();
		
		List<CallElapsedTimeService5> tasks = new LinkedList<>();
		ArrayBlockingQueue<Long> elapsedTimes = new ArrayBlockingQueue<>(10);
		
		for (int i = 0; i < 4; i++) {
			CallElapsedTimeService5 callElapsedTimeService = new CallElapsedTimeService5(configurationService);
			tasks.add(callElapsedTimeService);
		}
		
		try {
			elapsedTimes.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Future<Long>> invokeAll;
//		try {
//			invokeAll = managedExecutorService.invokeAll(tasks);
//			for (Future<Long> future : invokeAll) {
//			}
//		} catch (InterruptedException | ExecutionException e1) {
//			
//		}

	}

}
