package uk.co.iotacist.taskman.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import uk.co.iotacist.logman.Logger;
import uk.co.iotacist.taskman.common.packet.handshake.HandshakePacket;
import uk.co.iotacist.taskman.common.packet.handshake.HandshakePacketHandler;
import uk.co.iotacist.taskman.common.socket.SocketState;

public class ConcurrentSocketHandler implements Runnable {
	
    protected Socket socket = null;
    protected SocketState socketState;
    
    protected ObjectOutputStream objectOutputStream;
    protected ObjectInputStream objectInputStream;
    
    protected HandshakePacketHandler handshakePacketHandler;
    protected Logger log;

    public ConcurrentSocketHandler(Socket clientSocket) {
        this.socket = clientSocket;
        this.socketState = SocketState.INIT;
        log = Logger.getLogger(this.getClass());
    }

    @Override
    public void run() {
        try {
        	try {
    			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    		} catch (IOException e) {
    			log.severe("An I/O error has occured while writing the stream header.", e);
    		}
        	try {
    			objectInputStream = new ObjectInputStream(socket.getInputStream());
    		} catch (IOException e) {
    			log.severe("An I/O error has occured while reading the stream header.", e);
    		}
            while (socket.isConnected()) {
            	if (objectInputStream.available() > 0) {
            		Object packet = objectInputStream.readObject();
            		
            		if (packet instanceof HandshakePacket) {
            			objectOutputStream.writeObject("Hello World!");
            		}
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				objectInputStream.close();
			} catch (IOException e) {
				log.severe("Failed to close the object input stream.", e);
			}
        	try {
				objectOutputStream.close();
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
}