package br.ufes.inf.lprm.sensoryeffect.renderer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

import javax.xml.bind.JAXBContext;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.iso.mpeg.mpegv._2010.sedl.SEM;

import br.ufes.inf.lprm.sensoryeffect.renderer.parser.SEMParser;
import br.ufes.inf.lprm.sensoryeffect.renderer.serial.SerialCom;
import br.ufes.inf.lprm.sensoryeffect.renderer.service.coap.SERendererCoapService;
import br.ufes.inf.lprm.sensoryeffect.renderer.service.mqtt.SERendererMqttService;
import br.ufes.inf.lprm.sensoryeffect.renderer.service.upnp.SERendererUPnPService;
import br.ufes.inf.lprm.sensoryeffect.renderer.service.websocket.SERendererWebSocketService;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLine;
import br.ufes.inf.lprm.utils.Utils;

public class SERendererBroker implements Runnable {

	public static TimeLine timeLine = new TimeLine();
	private static final File configFile = new File("config.properties");
	static Properties configProps;
	public static boolean debugMode = false;
	private static String capabilitiesMetadata = "";
	private static CommunicationMode communicationMode = CommunicationMode.UNDEFINED;
	
	public static SEServiceBase service = null;
	
	public static void main(String[] args) throws Exception {
		if (Utils.checkIfAlreadyRunning()) {
			System.err.println("There is an instance of the PlaySEM Sensory Effects Renderer (SER) running. It is not possible to run two instances at the same time on the same machine. Please, check it out and try to run it again.");
			System.exit(1);
		}
		
		SEMParser.jaxbContext = JAXBContext.newInstance(SEM.class);
		String capabilitiesXmlResource = "br/ufes/inf/lprm/sensoryeffect/renderer/capabilities.xml";
		capabilitiesMetadata = fileToBuffer(Thread.currentThread().getContextClassLoader().getResourceAsStream(capabilitiesXmlResource));
		
		Properties defaultProps = new Properties();
		if (configFile.exists()){
			InputStream inputStream = new FileInputStream(configFile);
			configProps = new Properties(defaultProps);
			configProps.load(inputStream);
			SerialCom.port = configProps.getProperty("serial_port");
			communicationMode = CommunicationMode.valueOf(configProps.getProperty("communicationMode").trim());
			if ("1".equalsIgnoreCase(configProps.getProperty("emulateDevices")) || "yes".equalsIgnoreCase(configProps.getProperty("emulateDevices")) || 
                    "true".equalsIgnoreCase(configProps.getProperty("emulateDevices")) || "on".equalsIgnoreCase(configProps.getProperty("emulateDevices")))
				SerialCom.emulateDevices = true;
			else
				SerialCom.emulateDevices = false;
			if ("1".equalsIgnoreCase(configProps.getProperty("debugMode")) || "yes".equalsIgnoreCase(configProps.getProperty("debugMode")) || 
                    "true".equalsIgnoreCase(configProps.getProperty("debugMode")) || "on".equalsIgnoreCase(configProps.getProperty("debugMode")))
				debugMode = true;
			else
				debugMode = false;
			inputStream.close();
		}
		else {
			configProps = new Properties(defaultProps);
			configProps.setProperty("serial_port", "COM3");
			configProps.setProperty("emulateDevices", "true");
			configProps.setProperty("debugMode", "true");
			configProps.setProperty("communicationMode", "UPNP");
			SerialCom.port = configProps.getProperty("serial_port");
			SerialCom.emulateDevices = Boolean.parseBoolean(configProps.getProperty("emulateDevices"));
			debugMode = Boolean.parseBoolean(configProps.getProperty("debugMode"));
			communicationMode = CommunicationMode.valueOf(configProps.getProperty("communicationMode").trim());
			OutputStream outputStream = new FileOutputStream(configFile);
			configProps.store(outputStream, "PlaySEM SE Renderer - Settings");
			outputStream.close();
		}
		Thread serverThread = new Thread(new SERendererBroker());
        serverThread.setDaemon(false);
        serverThread.start();
        
        int interval = 1;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timeLine, new Date(), interval);
    }
	
	private static String fileToBuffer(InputStream is) throws IOException {
	    StringBuilder sb = new StringBuilder("");
	    try (BufferedReader rdr = new BufferedReader(new InputStreamReader(is))) { 
	        for (int c; (c = rdr.read()) != -1;) {
	            sb.append((char) c);
	        }
	    }
	    return sb.toString();
	}

    public void run() {
    	switch (communicationMode) {
    	case UPNP : {
	    	service = new SERendererUPnPService();
	    	service.setCapabilitiesMetadata(capabilitiesMetadata);
	    	try {
	            final UpnpService upnpService = new UpnpServiceImpl();
	            Runtime.getRuntime().addShutdownHook(new Thread() {
	                @Override
	                public void run() {
	                    upnpService.shutdown();
	                }
	            });
	            upnpService.getRegistry().addDevice(SERendererUPnPService.createDevice());
	        } catch (Exception ex) {
	            System.err.println("An exception has occured: " + ex);
	            ex.printStackTrace(System.err);
	            System.exit(1);
	        }
    	}
    	break;
    	case WEBSOCKET : {
	    	try {
	    		service = new SERendererWebSocketService();
		    	service.setCapabilitiesMetadata(capabilitiesMetadata);
		    	((SERendererWebSocketService)service).init();
	        } catch (Exception ex) {
	            System.err.println("An exception has occured: " + ex);
	            ex.printStackTrace(System.err);
	            System.exit(1);
	        }
    	}
    	break;
    	case COAP : {
	    	try {
	    		service = new SERendererCoapService();
		    	service.setCapabilitiesMetadata(capabilitiesMetadata);
		    	((SERendererCoapService)service).init();
	        } catch (Exception ex) {
	            System.err.println("An exception has occured: " + ex);
	            ex.printStackTrace(System.err);
	            System.exit(1);
	        }
    	}
    	break;
    	case MQTT : {
	    	try {
	    		service = new SERendererMqttService();
		    	service.setCapabilitiesMetadata(capabilitiesMetadata);
		    	((SERendererMqttService)service).init();
	        } catch (Exception ex) {
	            System.err.println("An exception has occured: " + ex);
	            ex.printStackTrace(System.err);
	            System.exit(1);
	        }
    	}
    	break;
    	default : {
    		try {
				throw new Exception("The communication mode is not set up properly. Please check it out.");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	}
    }
}
