package hu.palkonyves.concurrency;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.palkonyves.servlet.Constants;

/**
 * Servlet implementation class AsyncTranslateServlet
 */
@WebServlet("unsafeServlet3")
public class UnsafeServlet3 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private int calledTimes = 0;
    private String parity = "even";
	
    public UnsafeServlet3() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	    
		response.setContentType(Constants.CONTENT_TYPE_PLAIN_HTML);
		
		calcParity();
		try {Thread.sleep(1);} catch (InterruptedException e) {}
		final PrintWriter writer = response.getWriter();
		
		writer.format("calledTimes: (%5s,%5s) Servlet instance: #%X, threadName: %20s", 
				calledTimes, 
				parity, 
				this.hashCode(),
				Thread.currentThread().getName());
		
		synchronized(this) {
			calledTimes++;
		}
		
	}
	
	private synchronized void calcParity() {
		boolean even = calledTimes % 2 == 0;
		
		if (even) {
			parity = "even";
		}
		else {
			parity = "odd";
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
