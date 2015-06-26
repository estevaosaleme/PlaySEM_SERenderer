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
import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.Icon;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.ManufacturerDetails;
import org.fourthline.cling.model.meta.ModelDetails;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import org.iso.mpeg.mpegv._2010.sedl.SEM;

import br.ufes.inf.lprm.sensoryeffect.renderer.parser.SEMParser;
import br.ufes.inf.lprm.sensoryeffect.renderer.serial.SerialCom;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.TimeLine;

public class SERendererDevice implements Runnable {

	public static TimeLine timeLine = new TimeLine();
	private static final File configFile = new File("config.properties");
	static Properties configProps;
	public static boolean debugMode = false;
	
	public static void main(String[] args) throws Exception {
		SEMParser.jaxbContext = JAXBContext.newInstance(SEM.class);
		String capabilitiesXmlResource = "br/ufes/inf/lprm/sensoryeffect/renderer/capabilities.xml";
		String capabilitiesMetadata = fileToBuffer(Thread.currentThread().getContextClassLoader().getResourceAsStream(capabilitiesXmlResource));
		SERendererService.setCapabilitiesMetadata(capabilitiesMetadata);
		
        Thread serverThread = new Thread(new SERendererDevice());
        serverThread.setDaemon(false);
        serverThread.start();

        int interval = 1;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timeLine, new Date(), interval);
        
		Properties defaultProps = new Properties();
		if (configFile.exists()){
			InputStream inputStream = new FileInputStream(configFile);
			configProps = new Properties(defaultProps);
			configProps.load(inputStream);
			SerialCom.port = configProps.getProperty("serial_port");
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
			SerialCom.port = configProps.getProperty("serial_port");
			SerialCom.emulateDevices = Boolean.parseBoolean(configProps.getProperty("emulateDevices"));
			debugMode = Boolean.parseBoolean(configProps.getProperty("debugMode"));
			OutputStream outputStream = new FileOutputStream(configFile);
			configProps.store(outputStream, "PlaySEM SE Renderer - Settings");
			outputStream.close();
		}
//		new SerialCom();
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
        try {
            final UpnpService upnpService = new UpnpServiceImpl();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });
            upnpService.getRegistry().addDevice(createDevice());

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	LocalDevice createDevice() throws ValidationException, LocalServiceBindingException, IOException {

	    DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("SensoryEffectRendererDevice"));
	    DeviceType type = new UDADeviceType("SensoryEffectRenderer", 1);
	    DeviceDetails details =
	            new DeviceDetails(
	                    "LPRM - PlaySEM - Sensory Effect Renderer Device",
	                    new ManufacturerDetails("LPRM"),
	                    new ModelDetails(
	                            "PlaySEM.SERendererDevice.ModelA",
	                            "A remote Sensory Effect Renderer Device. Model A supports Light, Fan and Vibration.",
	                            "v1"
	                    )
	            );

	    String iconResource = "br/ufes/inf/lprm/sensoryeffect/renderer/icon.png";
	    Icon icon = new Icon("image/png", 48, 48, 8, "icon.png", Thread.currentThread().getContextClassLoader().getResourceAsStream(iconResource));

	    LocalService<SERendererService> seRendererService = new AnnotationLocalServiceBinder().read(SERendererService.class);
	    seRendererService.setManager(new DefaultServiceManager(seRendererService, SERendererService.class));

	    try {
	    	return new LocalDevice(identity, type, details, icon, seRendererService);
	    }
	    catch (Exception e) {
	    	System.out.print("Exception occured: " + e.getMessage());
	    	e.printStackTrace();
	    	return null;
	    }
	}

}
