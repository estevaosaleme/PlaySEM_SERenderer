package br.ufes.inf.lprm.utils;

import java.io.IOException;
import java.net.ServerSocket;

public class Utils {

	private static ServerSocket serverSocket;
	
    public static boolean checkIfAlreadyRunning() throws IOException {
    	ShutdownHook shutdownHook = new ShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    	
        try {
        	serverSocket = new ServerSocket(65535);
	    	return false;
        }
	    catch (Exception ex){
	    	return true;
	    }
    }

    static class ShutdownHook extends Thread {
        public void run() {
        	try {
        		if (serverSocket != null)
        			serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

}
