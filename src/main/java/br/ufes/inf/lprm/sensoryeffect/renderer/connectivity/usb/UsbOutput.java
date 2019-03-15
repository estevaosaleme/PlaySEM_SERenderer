package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.usb;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Calendar;

import org.usb4java.BufferUtils;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

import br.ufes.inf.lprm.sensoryeffect.renderer.connectivity.ConnectivityBase;
import br.ufes.inf.lprm.utils.Utils;

public class UsbOutput extends ConnectivityBase {

	private int result = 0;
	
	private short vendorId;
	private short productId;
	private byte usbInterface;
	private byte inEndPoint;
	private byte outEndPoint;
	private int timeout;
	
	DeviceHandle handle;
	
	@Override
	public void openConnection() {
		if (!isConnected())
			try {
				vendorId = Short.decode(getProperties().get("vendorId"));
				productId = Short.decode(getProperties().get("productId"));
				usbInterface = Byte.decode(getProperties().get("usbInterface"));
				inEndPoint = Byte.decode(getProperties().get("inEndPoint"));
				outEndPoint = Byte.decode(getProperties().get("outEndPoint"));
				timeout = Integer.valueOf(getProperties().get("timeout"));
				
				result = LibUsb.init(null);
		        if (result != LibUsb.SUCCESS)
		        {
		        	setConnected(false);
		            throw new LibUsbException("Unable to initialize libusb", result);
		        }

		        handle = LibUsb.openDeviceWithVidPid(null, vendorId, productId);
		        if (handle == null)
		        {
		        	setConnected(false);
		            System.err.println("Device not found.");
		        }
		        result = LibUsb.claimInterface(handle, usbInterface);
		        if (result != LibUsb.SUCCESS)
		        {
		        	setConnected(false);
		            throw new LibUsbException("Unable to claim interface", result);
		        }
		        
				setConnected(true);
				System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": USB connection has been opened.");

			} catch (Exception e){
				e.printStackTrace();
			}
	}

	@Override
	public void closeConnection() {
		if (isConnected()) {
			result = LibUsb.releaseInterface(handle, usbInterface);
	        if (result != LibUsb.SUCCESS)
	        {
	            throw new LibUsbException("Unable to release interface", result);
	        }
	        LibUsb.close(handle);
	        LibUsb.exit(null);
	
			setConnected(false);
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": USB connection has been closed.");
		}
		else
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": USB connection is already closed.");
	}

	@Override
	public void sendMessage(byte[] message) {
		if (!isConnected())
			openConnection();
			
		try {
			ByteBuffer buffer = BufferUtils.allocateByteBuffer(message.length);
	        buffer.put(message);
	        IntBuffer transferred = BufferUtils.allocateIntBuffer();
	        int result = LibUsb.bulkTransfer(handle, outEndPoint, buffer,
	            transferred, timeout);
	        if (result != LibUsb.SUCCESS)
	        {
	            throw new LibUsbException("Unable to send data", result);
	        }	
			System.out.println(Utils.dateFormat.format(Calendar.getInstance().getTime()) + ": USB message has been sent.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
