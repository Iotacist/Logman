package uk.co.iotacist.taskman.client.nio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import uk.co.iotacist.logman.Logger;
import uk.co.iotacist.taskman.common.packet.Packet;
import uk.co.iotacist.taskman.common.packet.handshake.HandshakePacket;
import uk.co.iotacist.taskman.common.socket.SocketState;

public class SocketHandler implements Runnable {
	/*
	 * 
	 */
    protected Socket socket = null;
    protected SocketState socketState;
    
    protected ObjectOutputStream txo;
    protected ObjectInputStream rxo;
    
    /**
	 * Session universally unique identifier (UUID) number.
	 */
	protected final String sessionID;
	
	/*
	 * 	HashMap of UUID > Socket to keep track of clients.
	 *  
	 *  Needs to be quick and async.
	 */
    
    protected Logger log;

    public SocketHandler(Socket clientSocket, String uuid) {
        socket = clientSocket;
        socketState = SocketState.INIT;
        sessionID = uuid;
        log = Logger.getLogger(SocketHandler.class);
        log.setUseParentAppenders(true);
        log.setUseParentFilter(true);
        log.setUseParentFormatter(true);
		log.setUseParentLevel(true);
    }

    @Override
    public void run() {
        try {
        	try {
    			txo = new ObjectOutputStream(socket.getOutputStream());
    		} catch (IOException e) {
    			log.severe("An I/O error has occured while initializing the stream writer header.", e);
    		}
        	try {
    			rxo = new ObjectInputStream(socket.getInputStream());
    		} catch (IOException e) {
    			log.severe("An I/O error has occured while initializing the stream reader header.", e);
    		}
            while (socket.isConnected()) {
            	/* RX */
            	if (rxo.available() > 0) {
            		Packet dPacket = (Packet) rxo.readObject();
            	} else {
            	/* TX */
            		Packet packet = new HandshakePacket();
            		packet.setSessionID(sessionID);
            		txo.writeObject(packet);
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				rxo.close();
			} catch (IOException e) {
				log.severe("Failed to close the object input stream.", e);
			}
        	try {
				txo.close();
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