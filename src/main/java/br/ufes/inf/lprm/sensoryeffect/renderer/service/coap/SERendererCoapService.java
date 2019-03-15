package br.ufes.inf.lprm.sensoryeffect.renderer.service.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;
import br.ufes.inf.lprm.sensoryeffect.renderer.service.SEServiceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.service.websocket.SERendererWebSocketService;

public class SERendererCoapService extends SEServiceBase {
	
	private static SERendererWebSocketService webSocketService = new SERendererWebSocketService();
	
	@Override
	public void init() {
		
		new Thread(new Runnable() {
		     public void run() {
		    	 webSocketService.init();
		     }
		}).start();
		
        // binds on UDP port 5683
        CoapServer server = new CoapServer();
        server.add(new SERendererWebSocketAddressResource());
        server.add(new SetPlayEventResource());
        server.start();  
    }

    public static class SERendererWebSocketAddressResource extends CoapResource {
        public SERendererWebSocketAddressResource() {
            super("SERendererWebSocketAddress");
            getAttributes().setTitle("SERendererWebSocketAddress Resource");
        }

        @Override
        public void handleGET(CoapExchange exchange) {
        	try {
	        	String address = webSocketService.getAddress().getHost() + ":" + webSocketService.getAddress().getPort();
	            exchange.respond("ws://"+address+"/playsemserenderer");
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }
    }
    
    public static class SetPlayEventResource extends CoapResource {
        public SetPlayEventResource() {
            super("SetPlayEvent");
            getAttributes().setTitle("SetPlayEvent Resource");
        }

        @Override
        public void handleGET(CoapExchange exchange) {
            exchange.respond("Please use POST <eventId> to start an event.");
        }
        
        @Override
        public void handlePOST(CoapExchange exchange) {
        	exchange.accept(); 
            int eventId = 0;
            try {
	            eventId = Integer.parseInt(exchange.getRequestText());
	            SERendererBroker.service.setPlayEvent(eventId);
            }
            catch (Exception e){
    	        e.printStackTrace();
    	    }
        }
    }
    
 // It needs to implement the other services as classes here

	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		webSocketService.firePropertyChange(propertyName, oldValue, newValue);
	}
}
