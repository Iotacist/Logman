package uk.co.iotacist.taskman.client.nio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

import uk.co.iotacist.logman.Logger;
import uk.co.iotacist.taskman.common.net.NetworkUtils;
import uk.co.iotacist.taskman.common.packet.Packet;
import uk.co.iotacist.taskman.common.socket.SocketState;

public class SocketClient extends Thread {

	protected int address;
	protected int port;
	protected Socket socket;
    protected SocketState state;
    protected ObjectOutputStream txo;
    protected ObjectInputStream rxo;
    protected Logger log;

    public SocketClient(int port) {
    	this(NetworkUtils.getLocalHost().getAddress(), port);
    }
    
	public SocketClient(byte[] address, int port) {
		this(ByteBuffer.wrap(address).getInt(), port);
	}
	
	public SocketClient(InetAddress address, int port) {
		this(ByteBuffer.wrap(address.getAddress()).getInt(), port);
	}
	
	public SocketClient(int address, int port) {
		this.address = address;
		this.port = port;
		this.log = Logger.getLogger(SocketClient.class);
		this.log.setUseParentAppenders(true);
		this.log.setUseParentFilter(true);
		this.log.setUseParentFormatter(true);
		this.log.setUseParentLevel(true);
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
            		
            	}
            }
        } catch (IOException e) {
        	log.severe("An I/O error has occured.", e);
        } catch (ClassNotFoundException e) {
        	log.severe("Unable to find clase.", e);
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