package hu.palkonyves.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AsyncTranslateServlet
 */

public class AsyncTranslateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource
	ManagedExecutorService managedExecutorService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AsyncTranslateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // http://www.jayway.com/2014/05/16/async-servlets/
		response.setContentType("text/plain");
		final PrintWriter writer = response.getWriter();
		
		final AsyncContext asyncContext = request.startAsync();
		asyncContext.addListener(new AsyncListener() {
            
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("timed out");
            }
            
            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("started");
            }
            
            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("error");
            }
            
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("completed");
            }
        });
		
		asyncContext.setTimeout(100L);
		
		
		managedExecutorService.execute(new Runnable() {
            
            @Override
            public void run() {
                writer.append("shit");
                asyncContext.complete();                
            }
        });
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}