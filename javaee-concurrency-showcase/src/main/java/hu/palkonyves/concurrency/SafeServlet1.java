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
@WebServlet("safeServlet1")
public class SafeServlet1 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private int calledTimes = 0;
    private String parity = "even";
	
    public SafeServlet1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	    
	    // http://www.jayway.com/2014/05/16/async-servlets/
		response.setContentType(Constants.CONTENT_TYPE_PLAIN_HTML);
		
		Tuple<Integer,String> calcParity = calcParity();
		
		final PrintWriter writer = response.getWriter();
		writer.format("calledTimes: (%5s,%5s) objId: #%X, threadName: %20s", 
				calcParity.a, 
				calcParity.b, 
				this.hashCode(),
				Thread.currentThread().getName());
	}
	
	private synchronized Tuple<Integer, String> calcParity() {
		boolean even = calledTimes % 2 == 0;
		try {Thread.sleep(1);} catch (InterruptedException e) {}
		if (even) {
			parity = "even";
		}
		else {
			parity = "odd";
		}
		calledTimes++;
		return new Tuple<>(calledTimes, String.valueOf(parity));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	static class Tuple<A,B> {
		A a;
		B b;
		
		Tuple(A a, B b) {
			this.a = a;
			this.b = b;
		}
	}

}
