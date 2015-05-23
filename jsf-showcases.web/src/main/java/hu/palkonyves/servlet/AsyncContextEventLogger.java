package hu.palkonyves.servlet;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

class AsyncContextEventLogger implements AsyncListener {
    
    final String contextName;
    
    public AsyncContextEventLogger(String contextName) {
        this.contextName = contextName;
    }
    
    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        System.out.println(contextName + ": timed out");
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        System.out.println(contextName + ": started");
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        System.out.println(contextName + ": error");
    }

    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        System.out.println(contextName + ": completed");
    }
}