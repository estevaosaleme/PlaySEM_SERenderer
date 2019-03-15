package br.ufes.inf.lprm.sensoryeffect.renderer.connectivity;

public interface Connectivity {
	public void openConnection();
	public void closeConnection();
	public void sendMessage(byte[] message);
}
