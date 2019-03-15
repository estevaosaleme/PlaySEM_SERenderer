package br.ufes.inf.lprm.sensoryeffect.renderer.service.upnp;

import java.beans.PropertyChangeSupport;
import java.io.IOException;

import org.fourthline.cling.binding.LocalServiceBindingException;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;
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
import org.fourthline.cling.registry.RegistrationException;

import br.ufes.inf.lprm.sensoryeffect.renderer.service.SEServiceBase;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.MainTimeLine;
import br.ufes.inf.lprm.sensoryeffect.renderer.timer.MainTimeLine.Status;

@UpnpService(
    serviceId = @UpnpServiceId("RendererControl"),
    serviceType = @UpnpServiceType(value = "RendererControl", version = 1)
)
public class SERendererUPnPService extends SEServiceBase {
	
	@Override
	public void init() {
		try {
			final org.fourthline.cling.UpnpService upnpService = new org.fourthline.cling.UpnpServiceImpl();
	        Runtime.getRuntime().addShutdownHook(new Thread() {
	            @Override
	            public void run() {
	                upnpService.shutdown();
	            }
	        });
			upnpService.getRegistry().addDevice(SERendererUPnPService.createDevice());
		} catch (RegistrationException | LocalServiceBindingException | ValidationException | IOException e) {
			e.printStackTrace();
		}		
	}
	
private static PropertyChangeSupport propertyChangeSupport = null;
	
	public SERendererUPnPService() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	public static PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}
	
	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		getPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
	}
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String currentTime = ""; 
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private String duration = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private String sensoryEffectMetadata = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String capabilitiesMetadata = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String hexLeft = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String hexCenter = "";
	
	@UpnpStateVariable(defaultValue = "", sendEvents = false)
	private static String hexRight = "";
	
	@UpnpStateVariable(defaultValue = "0", sendEvents = false)
	private static int eventId = 0;
	
	@UpnpStateVariable(defaultValue = "false", sendEvents = false)
	private static Boolean lightAutoExtraction = false;
	
	@UpnpStateVariable(defaultValue = "false", sendEvents = true)
	private static Boolean semPrepared = false;
	
	@UpnpStateVariable(defaultValue = "0", sendEvents = true)
	private static int semEventIdPrepared = 0;

	private void initializeSetSem(String newSensoryEffectMetadata, String newDuration) {
		semPrepared = false;
		semEventIdPrepared = 0;
		sensoryEffectMetadata = newSensoryEffectMetadata;
		duration = newDuration;
		currentTime = "0";
		lightAutoExtraction = false;
	}
	
	@UpnpAction @Override
	public void setSem(
			@UpnpInputArgument(name = "SensoryEffectMetadata") 
			String newSensoryEffectMetadata,
			@UpnpInputArgument(name = "Duration") 
			String newDuration) {
		initializeSetSem(newSensoryEffectMetadata, newDuration);
		super.setSem(newSensoryEffectMetadata, newDuration);
	}
	
	@UpnpAction @Override
	public void setSemEvent(
			@UpnpInputArgument(name = "SensoryEffectMetadata") 
			String newSensoryEffectMetadata,
			@UpnpInputArgument(name = "Duration") 
			String newDuration,
			@UpnpInputArgument(name = "EventId") 
			int eventId) {
		initializeSetSem(newSensoryEffectMetadata, newDuration);
		super.setSemEvent(newSensoryEffectMetadata, newDuration, eventId);
	}
	
	@UpnpAction @Override
	public void setPlay(
			@UpnpInputArgument(name = "CurrentTime") 
			String newCurrentTime) {
		super.setPlay(newCurrentTime);
	}
	
	@UpnpAction @Override
	public void setPlayEvent(
			@UpnpInputArgument(name = "EventId") 
			int eventId) {
		super.setPlayEvent(eventId);
	}
	
	@UpnpAction @Override
	public void setClearEventList() {
		super.setClearEventList();
	}
	
	@UpnpAction @Override
	public void setPause(
			@UpnpInputArgument(name = "CurrentTime") 
			String newCurrentTime) {
		super.setPause(newCurrentTime);
	}
	
	@UpnpAction @Override
	public void setStop() {
		super.setStop();
	}
		    
	@UpnpAction @Override
	public void setCurrentTime(
			@UpnpInputArgument(name = "CurrentTime") 
			String newCurrentTime) {
		currentTime = newCurrentTime;
		super.setCurrentTime(newCurrentTime);
	}
	
	@UpnpAction @Override
	public void setLightColors(
			@UpnpInputArgument(name = "HexLeft") 
			String newHexLeft,
			@UpnpInputArgument(name = "HexCenter") 
			String newHexCenter,
			@UpnpInputArgument(name = "HexRight") 
			String newHexRight) {
		if (lightAutoExtraction && MainTimeLine.getStatus().getId() == Status.PLAYING.getId()){
			hexLeft = newHexLeft;
			hexCenter = newHexCenter;
			hexRight = newHexRight;
		}
		else {
			hexLeft = "";
			hexCenter = "";
			hexRight = "";
		}
		super.setLightColors(newHexLeft, newHexCenter, newHexRight);
	}
	
	@Override
	public void setLocalCurrentTime(long newCurrentTime){
		currentTime = String.valueOf(newCurrentTime);
		super.setLocalCurrentTime(newCurrentTime);
	}
	
	@Override
	public void setCapabilitiesMetadata(String newCapabilitiesMetadata){
		capabilitiesMetadata = newCapabilitiesMetadata;
		super.setCapabilitiesMetadata(newCapabilitiesMetadata);
	}
	
	@Override
	public void setLightAutoExraction(Boolean newLightAutoExtraction){
		lightAutoExtraction = newLightAutoExtraction;
		super.setLightAutoExraction(newLightAutoExtraction);
	}
	
	@Override
	public void setSemPrepared(Boolean value) {
		semPrepared = value;
		super.setSemPrepared(value);
	}
	
	@Override
	public void setSemEventPrepared(int value) {
		semEventIdPrepared = value;
		super.setSemEventPrepared(value);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static LocalDevice createDevice() throws ValidationException, LocalServiceBindingException, IOException {

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

	    LocalService<SERendererUPnPService> seRendererService = new AnnotationLocalServiceBinder().read(SERendererUPnPService.class);
	    seRendererService.setManager(new DefaultServiceManager(seRendererService, SERendererUPnPService.class));

	    try {
	    	return new LocalDevice(identity, type, details, icon, seRendererService);
	    }
	    catch (Exception e) {
	    	System.out.println("An exception has occured: " + e.getMessage());
	    	e.printStackTrace();
	    	return null;
	    }
	}
}


