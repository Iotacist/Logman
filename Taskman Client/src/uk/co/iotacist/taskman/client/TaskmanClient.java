package uk.co.iotacist.taskman.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import uk.co.iotacist.logman.Logger;
import uk.co.iotacist.logman.appender.StdOutAppender;
import uk.co.iotacist.taskman.common.packet.Packet;
import uk.co.iotacist.taskman.common.packet.handshake.HandshakePacket;

public class TaskmanClient extends Thread {
	private static PrivateKey privateKey;
    private static PublicKey publicKey;
    
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;
    private static Logger log;
    
    public TaskmanClient() {
    	KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
	        KeyPair pair = keyGen.generateKeyPair();
	        privateKey = pair.getPrivate();
	        publicKey = pair.getPublic();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log = Logger.getLogger(TaskmanClient.class);
        log.setUseParentAppenders(true);
		log.setUseParentFilter(true);
		log.setUseParentFormatter(true);
		log.setUseParentLevel(true);
    }

    @Override
	public void run() {
    	Socket socket = null;
        InetAddress host = null;
        try {
        	host = InetAddress.getLocalHost();
			socket = new Socket(host.getHostName(), 9999);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			while(socket.isConnected()) {
				if (ois.available() > 0) {
            		Packet packet = (Packet) ois.readObject();
            		
            		if (packet instanceof HandshakePacket) {
            			HandshakePacket handshake = (HandshakePacket) packet;
            			System.out.println("Received handshake packet, UUID: " + handshake.getSessionID());
            		}
				}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				log.severe("Failed to close the object input stream.", e);
			}
        	try {
				oos.close();
			} catch (IOException e) {
				log.severe("Faied to close the object output stream.", e);
			}
        	try {
				socket.close();
			} catch (IOException e) {
				log.severe("Failed to close the socket.", e);
			}
        }
	}

	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, InterruptedException {
		TaskmanClient client = new TaskmanClient();
		client.start();
    }
}