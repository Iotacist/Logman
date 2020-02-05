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

public class TaskmanClient {
	private static PrivateKey privateKey;
    private static PublicKey publicKey;

    public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, InterruptedException, NoSuchAlgorithmException{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair pair = keyGen.generateKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
        
        Socket socket = null;
        InetAddress host = InetAddress.getLocalHost();
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
			socket = new Socket(host.getHostName(), 9999);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			while(socket.isConnected()) {
				String message = (String) objectInputStream.readObject();
				System.out.println("Received: " + message);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				objectInputStream.close();
				objectOutputStream.close();
	            socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}