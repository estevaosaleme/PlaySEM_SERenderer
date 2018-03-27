package br.ufes.inf.lprm.sensoryeffect.renderer.service.websocket;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import br.ufes.inf.lprm.sensoryeffect.renderer.SEServiceBase;


public class SERendererWebSocketService extends SEServiceBase
{
	ServletContextHandler context = null;
	Server server = null;
	
    public void init() {
    	BasicConfigurator.configure();
	    server = new Server(8080);

		context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		
		ServletHolder wsHolder = new ServletHolder("playsemserenderer", new SERendererWebSocketServlet());
		context.addServlet(wsHolder,"/playsemserenderer");
	    try {
	    	server.start();
	        server.join();
	    }
	    catch (Exception e){
	        e.printStackTrace();
	    }
    }
    
    public URI getAddress() {
    	return server.getURI();
    }
    
    @Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    	if (SERendererWebSocketServletService.remote != null)
			try {
				SERendererWebSocketServletService.remote.sendString("{\""+propertyName+"\":\""+newValue+"\"}");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}