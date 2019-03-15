package br.ufes.inf.lprm.sensoryeffect.renderer;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.Vector;

import javax.xml.bind.JAXBContext;

import org.iso.mpeg.mpegv._2010.sedl.SEM;

import br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.ConnectivityBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.device.SensoryEffectDeviceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.metadata.parser.mpegv.MPEGVSEMParser;
import br.ufes.inf.lprm.sensoryeffect.renderer.service.SEServiceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.MainTimeLine;
import br.ufes.inf.lprm.utils.Utils;

public class SERendererBroker implements Runnable {

	private static final File xmlConfigurationFile = new File("SERenderer.xml");
	private static String communicationServiceBroker = "";
	private static String communicationServiceClass = "";
	public static String metadataParserClass = "";
	private static Vector<String> capabilityDevices = new Vector<String>();
	
	public static boolean debugMode = false;
	public static MainTimeLine timeLine = new MainTimeLine();
	public static SEServiceBase service = null;
	
	public static HashMap<String, SensoryEffectDeviceBase> sensoryEffectDevices = new HashMap<String, SensoryEffectDeviceBase>();
	public static SensoryEffectDeviceBase lightDevice = null;
	public static SensoryEffectDeviceBase windDevice = null;
	public static SensoryEffectDeviceBase vibrationDevice = null;
	public static SensoryEffectDeviceBase scentDevice = null;
	
	public static HashMap<String, ConnectivityBase> deviceConnectivities = new HashMap<String, ConnectivityBase>();

	public static void main(String[] args) throws Exception {
		// It checks if an instance is already running
		if (Utils.checkIfAlreadyRunning()) {
			System.err.println("There is an instance of the PlaySEM Sensory Effects Renderer (SER) running. It is not possible to run two instances at the same time on the same machine. Please, check it out and try to run it again.");
			System.exit(1);
		}
		
		// When shutting it down do the following
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutting down...");
				for (ConnectivityBase connectivity : deviceConnectivities.values()) {
					connectivity.closeConnection();
				}
				System.out.println("Application terminated.");
			}
		});
		
		MPEGVSEMParser.jaxbContext = JAXBContext.newInstance(SEM.class);
		
		// It reads the configuration file SERenderer.xml
		if (xmlConfigurationFile.exists()){
			debugMode = Boolean.valueOf(Utils.getConfigurationValue("debugMode", "", "/configuration/debugMode", xmlConfigurationFile));
			
			communicationServiceBroker = Utils.getConfigurationValue("communicationServiceBroker", "", "/configuration/communicationServiceBroker", xmlConfigurationFile);
			communicationServiceClass = Utils.getConfigurationValue("communicationServiceClass", communicationServiceBroker, "/configuration/communicationServices/communicationService/id", xmlConfigurationFile);
			
			String metadataParser = Utils.getConfigurationValue("metadataParser", "", "/configuration/metadataParser", xmlConfigurationFile);
			metadataParserClass = Utils.getConfigurationValue("metadataParserClass", metadataParser, "/configuration/metadataParsers/metadataParser/id", xmlConfigurationFile);
			
			// It reads devices' configurations and their connectivities
			lightDevice = setupDevice("lightDevice");
			windDevice = setupDevice("windDevice");
			vibrationDevice = setupDevice("vibrationDevice");
			scentDevice = setupDevice("scentDevice");

			// It establishes connectivities
			for (ConnectivityBase connectivity : deviceConnectivities.values()) {
				connectivity.openConnection();
			}
			
			lightDevice.resetDevice();
			windDevice.resetDevice();
			vibrationDevice.resetDevice();
			scentDevice.resetDevice();
		}
		else {
			System.err.println("Please certify that the file 'SERenderer.xml' exists and it is properly configured.");
			System.exit(1);
		}
		
		// It starts a thread for the broker
		Thread serverThread = new Thread(new SERendererBroker());
        serverThread.setDaemon(false);
        serverThread.start();
        
        // It sets a timer
        int interval = 1;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timeLine, new Date(), interval);
    }
	
	private static SensoryEffectDeviceBase setupDevice(String deviceType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		String device = Utils.getConfigurationValue(deviceType, "", "/configuration/"+deviceType, xmlConfigurationFile);
		String deviceClass = Utils.getConfigurationValue("deviceClass", device, "/configuration/devices/device/id", xmlConfigurationFile);
		Class<?> clazzSensoryEffectDevice = Class.forName(deviceClass);
		SensoryEffectDeviceBase sensoryEffectDeviceBase = (SensoryEffectDeviceBase)clazzSensoryEffectDevice.newInstance();
		sensoryEffectDeviceBase.setId(device);
		HashMap<String, String> deviceProperties = Utils.getConfigurationListValues("properties", device, "/configuration/devices/device/id", xmlConfigurationFile);
		sensoryEffectDeviceBase.setProperties(deviceProperties);
		
		String deviceConnectivityInterface = Utils.getConfigurationValue("connectivityInterface", device, "/configuration/devices/device/id", xmlConfigurationFile);
		String sensoryEffectDeviceConnectivityInterfaceClass = Utils.getConfigurationValue("connectivityInterfaceClass", deviceConnectivityInterface, "/configuration/connectivityInterfaces/connectivityInterface/id", xmlConfigurationFile);
		Class<?> clazzSensoryEffectDeviceConnectivityInterface = Class.forName(sensoryEffectDeviceConnectivityInterfaceClass);
		if (deviceConnectivities.get(deviceConnectivityInterface) == null) {
			ConnectivityBase clazzDeviceConnectivityBase = (ConnectivityBase)clazzSensoryEffectDeviceConnectivityInterface.newInstance();
			HashMap<String, String> connectivityProperties = Utils.getConfigurationListValues("properties", deviceConnectivityInterface, "/configuration/connectivityInterfaces/connectivityInterface/id", xmlConfigurationFile);
			clazzDeviceConnectivityBase.setProperties(connectivityProperties);
			deviceConnectivities.put(deviceConnectivityInterface, clazzDeviceConnectivityBase);
		}
		
		String capabilityXmlResource = deviceClass.replaceAll("\\.", "/");
		capabilityXmlResource = capabilityXmlResource.substring(0, capabilityXmlResource.lastIndexOf("/")).substring(0, capabilityXmlResource.lastIndexOf("/"));
		if (!capabilityDevices.contains(capabilityXmlResource))
			capabilityDevices.addElement(capabilityXmlResource);
		
		sensoryEffectDeviceBase.setDeviceConnectivity(deviceConnectivities.get(deviceConnectivityInterface));
		sensoryEffectDevices.put(device, sensoryEffectDeviceBase);
		return sensoryEffectDeviceBase;
	}
	
	private static String getCapatilities() throws IOException {
		String capabilities = "";
		String capabilitiesHeadXmlResource = "br/ufes/inf/lprm/sensoryeffect/renderer/device/capabilitiesHead.xml";
		capabilities = Utils.fileToBuffer(Thread.currentThread().getContextClassLoader().getResourceAsStream(capabilitiesHeadXmlResource));
		for (String capabilityXmlResourceItem : capabilityDevices) {
			String capabilityXmlResourceBody = capabilityXmlResourceItem + "/capabilityBody.xml";
			capabilities += Utils.fileToBuffer(Thread.currentThread().getContextClassLoader().getResourceAsStream(capabilityXmlResourceBody));
		}
		String capabilitiesTailXmlResource = "br/ufes/inf/lprm/sensoryeffect/renderer/device/capabilitiesTail.xml";
		capabilities += Utils.fileToBuffer(Thread.currentThread().getContextClassLoader().getResourceAsStream(capabilitiesTailXmlResource));
		return capabilities;
	}
	
	private void calculateDelayChain() {
		int delayService = 0;
		if (service.getProperties() !=null && service.getProperties().containsKey("delay"))
			delayService = Integer.parseInt(service.getProperties().get("delay"));

		for (SensoryEffectDeviceBase device : sensoryEffectDevices.values()) {	
			int delayDevice = 0;
			int delayDeviceConnectivity = 0;
			if (device.getProperties() !=null && device.getProperties().containsKey("delay"))
				delayDevice = Integer.parseInt(device.getProperties().get("delay"));
			
			if (device.getDeviceConnectivity() != null && device.getDeviceConnectivity().getProperties() !=null 
					&& device.getDeviceConnectivity().getProperties().containsKey("delay")) 
				delayDeviceConnectivity = Integer.parseInt(device.getDeviceConnectivity().getProperties().get("delay"));
		
			device.setDelayChainToBeCompensated(delayService + delayDevice + delayDeviceConnectivity);
			
			System.out.println("Delay to be compensated for '"+service.getClass().getSimpleName() + "', '"+
					device.getDeviceConnectivity().getClass().getSimpleName() + "', '"+
					device.getClass().getSimpleName()+ "' : " +
					delayService + " + "+
					delayDeviceConnectivity + " + "+
					delayDevice + " = " + device.getDelayChainToBeCompensated() + "ms");
		}	
	}

    public void run() {
    	try {
    		Class<?> clazz = Class.forName(communicationServiceClass);
        	service = (SEServiceBase)clazz.newInstance();
        	// It gets service properties
        	HashMap<String, String> serviceProperties = Utils.getConfigurationListValues("properties", communicationServiceBroker, "/configuration/communicationServices/communicationService/id", xmlConfigurationFile);
        	service.setProperties(serviceProperties);
        	// It provides capabilities through the broker
        	service.setCapabilitiesMetadata(getCapatilities());
        	// It initialises the service
        	service.init();
        	// It calculates the delay chain to be compensated
        	calculateDelayChain();
        } catch (Exception ex) {
			try {
				throw new Exception("The communication mode is not set up properly. Please check it out. ");
			} catch (Exception e) {
				e.printStackTrace();
			}
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
}
