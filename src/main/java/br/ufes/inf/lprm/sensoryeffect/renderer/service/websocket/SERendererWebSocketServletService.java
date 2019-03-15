package br.ufes.inf.lprm.sensoryeffect.renderer.service.websocket;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;

@WebSocket
public class SERendererWebSocketServletService {

	private static Session session;
    public static RemoteEndpoint remote;
    
    @OnWebSocketClose
    public void onWebSocketClose(int statusCode, String reason){
        session = null;
        remote = null;
        System.out.println("Websocket connection closed. Reason:" + reason);
    }

    @OnWebSocketConnect
    public void onWebSocketConnect(Session session){
        SERendererWebSocketServletService.session = session;
        remote = SERendererWebSocketServletService.session.getRemote();
        try {
			remote.sendString("You are now connected to " + this.getClass().getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @OnWebSocketError
    public void onWebSocketError(Throwable cause){
    	System.out.println("Error:" + cause.getMessage());
    }
    
    @OnWebSocketMessage
    public void onWebSocketBinary(byte[] payload, int offset, int length) {	
    	onWebSocketText(new String(payload));
    }
    
    @OnWebSocketMessage
    public void onWebSocketText(String message){
        if (session != null && session.isOpen() && remote != null && !message.isEmpty()){ 
        	if (SERendererBroker.debugMode)
        		System.out.println("Received message:" + message);
            try {
	            JSONObject jsonObject = (JSONObject)JSONValue.parseWithException(message);
	            if ((JSONArray)jsonObject.get("setPlayEvent") != null) {
	            	JSONArray setPlayEventParams = (JSONArray)jsonObject.get("setPlayEvent");
		            Object eventId = ((JSONObject)setPlayEventParams.get(0)).get("eventId");
		            SERendererBroker.service.setPlayEvent(Integer.valueOf((String)eventId));
	            }
	            else if ((JSONArray)jsonObject.get("setPlay") != null) {
	            	JSONArray setPlayEventParams = (JSONArray)jsonObject.get("setPlay");
		            Object currentTime = ((JSONObject)setPlayEventParams.get(0)).get("currentTime");
		            SERendererBroker.service.setPlay((String)currentTime);
	            }
	            else if ((JSONArray)jsonObject.get("setCurrentTime") != null) {
	            	JSONArray setPlayEventParams = (JSONArray)jsonObject.get("setCurrentTime");
		            Object currentTime = ((JSONObject)setPlayEventParams.get(0)).get("currentTime");
		            SERendererBroker.service.setCurrentTime((String)currentTime);
	            }
	            else if ((JSONArray)jsonObject.get("setPause") != null) {
	            	JSONArray setPlayEventParams = (JSONArray)jsonObject.get("setPause");
		            Object currentTime = ((JSONObject)setPlayEventParams.get(0)).get("currentTime");
		            SERendererBroker.service.setPause((String)currentTime);
	            }
	            else if ((JSONArray)jsonObject.get("setStop") != null) {
		            SERendererBroker.service.setStop();
	            }
	            else if ((JSONArray)jsonObject.get("setCleanEventList") != null) {
		            SERendererBroker.service.setClearEventList();
	            }
	            else if ((JSONArray)jsonObject.get("setSemEvent") != null) {
	            	JSONArray setSemEventParams = (JSONArray)jsonObject.get("setSemEvent");
		            Object sensoryEffectMetadata = ((JSONObject)setSemEventParams.get(0)).get("sensoryEffectMetadata");
		            Object duration = ((JSONObject)setSemEventParams.get(0)).get("duration");
		            Object eventId = ((JSONObject)setSemEventParams.get(0)).get("eventId");
		            SERendererBroker.service.setSemEvent((String)sensoryEffectMetadata, (String)duration, Integer.valueOf((String)eventId));
	            }
	            else if ((JSONArray)jsonObject.get("setSem") != null) {
	            	JSONArray setSemEventParams = (JSONArray)jsonObject.get("setSem");
		            Object sensoryEffectMetadata = ((JSONObject)setSemEventParams.get(0)).get("sensoryEffectMetadata");
		            Object duration = ((JSONObject)setSemEventParams.get(0)).get("duration");
		            SERendererBroker.service.setSem((String)sensoryEffectMetadata, (String)duration);
	            }
	            
	            // to do: implement the service getCapabilitiesMetadata()
            }
            catch(Exception e) {
            	System.out.println("An exception has occured: " + e.getMessage());
            	try {
					remote.sendString("An exception has occured: " + e.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        }
    }
}
