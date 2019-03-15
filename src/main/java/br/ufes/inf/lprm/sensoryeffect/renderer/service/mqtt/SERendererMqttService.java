package br.ufes.inf.lprm.sensoryeffect.renderer.service.mqtt;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import br.ufes.inf.lprm.sensoryeffect.renderer.SERendererBroker;
import br.ufes.inf.lprm.sensoryeffect.renderer.service.SEServiceBase;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.interception.messages.InterceptSubscribeMessage;
import io.moquette.server.Server;
import io.moquette.server.config.ClasspathResourceLoader;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.IResourceLoader;
import io.moquette.server.config.ResourceLoaderConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;
import io.netty.handler.codec.mqtt.MqttQoS;


public class SERendererMqttService extends SEServiceBase {

	static Server mqttBroker = null;
	static String clientId = "";
	
	class PublisherListener extends AbstractInterceptHandler {
		@Override
		public void onPublish(InterceptPublishMessage msg) {
			String content = new String(byteBufToString(msg.getPayload()));
			clientId = msg.getClientID();
			if (SERendererBroker.debugMode)
				System.out.println("Received on topic: " + msg.getTopicName() + " content: " + content );	

			JSONObject jsonObject;
			try {
				if (msg.getTopicName().equals("setPlayEvent")) {
		            Object eventId = content;
		            SERendererBroker.service.setPlayEvent(Integer.valueOf((String)eventId));
				} 
				else if (msg.getTopicName().equals("setSemEvent")) {
					jsonObject = (JSONObject)JSONValue.parseWithException(content);
					JSONArray setSemEventParams = (JSONArray)jsonObject.get("setSemEvent");
		            Object sensoryEffectMetadata = ((JSONObject)setSemEventParams.get(0)).get("sensoryEffectMetadata");
		            Object duration = ((JSONObject)setSemEventParams.get(0)).get("duration");
		            Object eventId = ((JSONObject)setSemEventParams.get(0)).get("eventId");
		            SERendererBroker.service.setSemEvent((String)sensoryEffectMetadata, (String)duration, Integer.valueOf((String)eventId));
				}
				
				// It needs to implement the other services here
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		@Override
	    public void onSubscribe(InterceptSubscribeMessage msg) {
			System.out.println("Client '"+msg.getClientID()+"' has subscribed to "+msg.getTopicFilter());
		}
		@Override
		public String getID() {
			return "EmbeddedSERendererPublishListener";
		}
	}
	
	private byte[] byteBufToString(ByteBuf buf) {
		byte[] bytes;
		int offset;
		int length = buf.readableBytes();
		if (buf.hasArray()) {
		    bytes = buf.array();
		    offset = buf.arrayOffset();
		} else {
		    bytes = new byte[length];
		    buf.getBytes(buf.readerIndex(), bytes);
		    offset = 0;
		}
		return bytes;
	}

	@Override
	public void init() {
		IResourceLoader classpathLoader = new ClasspathResourceLoader();
        final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
        mqttBroker = new Server();
        List<? extends InterceptHandler> userHandlers = Collections.singletonList(new PublisherListener());
        try {
			mqttBroker.startServer(classPathConfig, userHandlers);
		
			System.out.println("Moquette mqtt SERendererBroker started.");
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					System.out.println("Stopping broker...");
					mqttBroker.stopServer();
					System.out.println("Broker stopped");
				}
			});
			Thread.sleep(4000);
        } catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (SERendererBroker.debugMode)
			System.out.println("firePropertyChange() fired.");
		try {
			String message = "{\""+propertyName+"\":\""+newValue+"\"}";
			MqttQoS qos = MqttQoS.valueOf(2);
	        MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH, false, qos, false, 0);
	        MqttPublishVariableHeader varHeader = new MqttPublishVariableHeader("SemEventIdPrepared", 0);
	        ByteBuf  payload = Unpooled.wrappedBuffer(message.getBytes());
			MqttPublishMessage publishMessage = new MqttPublishMessage(fixedHeader, varHeader, payload);
			mqttBroker.internalPublish(publishMessage,clientId);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
